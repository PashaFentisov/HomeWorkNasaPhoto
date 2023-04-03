package com.example.homeworkphoto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pack1.Service;
import pojo.Photos;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\idea project\\HomeWorkPhoto\\src\\main\\resources\\temp.json");
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        Service sv = ac.getBean("service", Service.class);
//        sv.fillJson(file);
        ObjectMapper mapper = new ObjectMapper();
        Photos photos = mapper.readValue(file, Photos.class);
        sv.getMapWithPictures3(photos);
    }

}



