# PicassoBase64Images
A sample project that shows how you can use the Picasso Android library to download and display images that are served as Base64 Strings.

The whole idea is a based on using a custom _Picasso_ instance and a custom _Download_ implementation. 

## *Note*  
The Anroid app does not implement any caching mechanism (memory or disk) so do not consider it as a complete example. It just shows how you can decode a Base64 String and convert into a Bitmap.

## Service
The Service is a simple HttpServer implementation that exposes a REST service. 

The client can request an image by quering the /base64service/images/{id} URL with a GET request. 
The service encodes the image as a Base64 String and sends the result back to the client.

To run the service run the _Base64ImageService_ class which contains the main method.

## Android Client
You can test the app using an emulator as the app queries the localhost (10.0.2.2).
