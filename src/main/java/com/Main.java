package com;

import com.comparators.FuzzyComparator;
import com.images.ImageShower;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Scanner;

@Slf4j
@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) throws IOException {
        log.info("Started application");
        try (var applicationContext = new AnnotationConfigApplicationContext(Main.class)) {
            var properties = new Properties();
            properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));
            try (var signImagePaths = Files.list(Path.of(properties.getProperty("cyprus.signs.imageDirectory")))) {
                var signImagePathsList = signImagePaths.toList();
                log.info("Read image files");

                var imageShower = applicationContext.getBean(ImageShower.class);
                var scanner = applicationContext.getBean(Scanner.class);
                var comparator = applicationContext.getBean(FuzzyComparator.class);

                for (Path imagePath : signImagePathsList) {
                    JFrame jFrame = imageShower.showFrame(imagePath);
                    System.out.print("What sign is this: ");
                    var inputLine = scanner.nextLine();
                    if (inputLine.equals("exit")) {
                        imageShower.closeFrame(jFrame);
                        break;
                    }
                    var correctMeaning = imagePath.getFileName().toString().replace(".png", "");
                    System.out.println(comparator.compare(correctMeaning,
                            inputLine) ? "Correct" : "Incorrect (" + correctMeaning + ")");
                    imageShower.closeFrame(jFrame);
                }
            }
        }
    }
}
