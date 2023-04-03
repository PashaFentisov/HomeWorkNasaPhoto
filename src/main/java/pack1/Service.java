package pack1;

import annotation.Timetest;
import lombok.SneakyThrows;
import pojo.Image;
import pojo.Photo;
import pojo.Photos;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Comparator;


@org.springframework.stereotype.Service
public class Service {

    @SneakyThrows
    private Image findPhotoLocation(String img_src) {
        String host = new URL(img_src).getHost();
        String path = new URL(img_src).getPath();
        Image image = new Image();
        image.setUrl(getImageParam(host, path, "Location").replace("Location: ", "").trim());
        return image;
    }

    private static String getImageParam(String host, String path, String cookieParam) {
        String temp = "";
        try (Socket socket = new Socket(host, 80);
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            writer.println("GET " + path + " HTTP/1.1");
            writer.println("Host: " + host);
            writer.println();
            writer.flush();
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.startsWith(cookieParam)){
                    temp = line;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @SneakyThrows
    private Image findPhotoSize(Image image) {
        String host = new URL(image.getUrl()).getHost();
        String path = new URL(image.getUrl()).getPath();
        String size = getImageParam(host, path, "Content-Length");
        image.setSize(Long.valueOf(size.replace("Content-Length: ", "").trim()));
        return image;
    }


    @Timetest
    public void getMapWithPictures3(Photos ph) {
        ph.getPhotos().parallelStream()
                .map(Photo::getImg_src)
                .map(this::findPhotoLocation)
                .map(this::findPhotoSize)
                .max(Comparator.comparing(Image::getSize))
                .ifPresent(System.out::println);
    }

    @SneakyThrows
    @Timetest
    public void fillJson(File file) {
        URL url = new URL("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=300&api_key=rWlMG7Gr6HGgY9WHWXlH1lgKLeYFJ8nMNjCRsK5x");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            while (reader.ready()) {
                writer.write(reader.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
