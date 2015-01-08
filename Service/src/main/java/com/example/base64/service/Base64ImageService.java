package com.example.base64.service;

import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import sun.misc.BASE64Encoder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

// The Java class will be hosted at the URI path "/base64service"
@Path("/base64service")
public class Base64ImageService {
    @Path("/images/{id}")
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getImage(@PathParam("id") String id) {
        return encodeImage(id);
    }

    private static String encodeImage(String id) {
        String imageString = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            File file = new File("src/main/resources/images/" + id + ".jpg");
            if(file.exists()) {
                BufferedImage image = ImageIO.read(file);
                ImageIO.write(image, "jpg", bos);
                byte[] imageBytes = bos.toByteArray();

                BASE64Encoder encoder = new BASE64Encoder();
                imageString = encoder.encode(imageBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageString;
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9990/");
        server.start();

        System.out.println("Server running");
        System.out.println("Visit: http://localhost:9990/base64service");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }
}
