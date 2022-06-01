package com.example.sa_g7_tw2_spring.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public class ReadFileInstanceTime {

    public static LocalDateTime process() throws IOException {
        Path path = Paths.get("src/voice1.wav");
        BasicFileAttributes attributes = Files.readAttributes(path,BasicFileAttributes.class);
        LocalDateTime t = LocalDateTime.ofInstant(attributes.lastModifiedTime().toInstant(), ZoneId.systemDefault());
        return t;

    }
}
