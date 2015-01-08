package com.example.base64.android.base64client;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String BASE_URL = "http://10.0.2.2:9990/base64service/images/";
    private static final String[] IMAGES = new String[] {
            "image-01",
            "image-02",
            "image-03",
            "image-04",
            "image-05",
            "image-06"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // create dataset
        List<DataItem> dataset = new ArrayList<DataItem>();
        for(int i = 0; i < IMAGES.length; i++) {
            dataset.add(new DataItem(BASE_URL + IMAGES[i], IMAGES[i]));
        }

        // specify an adapter
        mAdapter = new MyAdapter(dataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    private static class DataItem {
        String imageUrl;
        String title;

        public DataItem(String imageUrl, String title) {
            this.imageUrl = imageUrl;
            this.title = title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getTitle() {
            return title;
        }
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<DataItem> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView mImageView;
            public TextView mTitleView;

            public ViewHolder(View v) {
                super(v);
                mImageView = (ImageView) v.findViewById(R.id.info_image);
                mTitleView = (TextView) v.findViewById(R.id.info_text);
            }
        }

        public MyAdapter(List<DataItem> dataset) {
            mDataset = dataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_card_item, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            DataItem item = mDataset.get(position);
            viewHolder.mTitleView.setText(item.getTitle());

            Picasso base64Picasso = new Picasso.Builder(viewHolder.mTitleView.getContext())
                    .downloader(new Base64ImageDownloader())
                    .build();
            base64Picasso.load(item.getImageUrl())
                    .into(viewHolder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
