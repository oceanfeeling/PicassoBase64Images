package com.example.base64.android.base64client;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Downloader;
import java.io.IOException;

public class Base64ImageDownloader implements Downloader {

    @Override
    public Response load(Uri uri, boolean localCacheOnly) throws IOException {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(uri.toString()).build();
            com.squareup.okhttp.Response response = client.newCall(request).execute();

            String imageAsBase64 = response.body().string();

            byte[] imageBytes = Base64.decode(imageAsBase64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            return new Response(bitmap, false);
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public void shutdown() {

    }
}
