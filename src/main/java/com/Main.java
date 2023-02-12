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
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) throws IOException {
        log.info("Started application");

        try (var applicationContext = new AnnotationConfigApplicationContext(Main.class)) {

            var properties = new Properties();
            properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));

            try (var signImagePaths = Files.list(Path.of(properties.getProperty("signs.imageDirectory")))) {
                log.info("Read image files");

                var signImagePathsList = signImagePaths.collect(Collectors.toList());
                log.debug("Total images amount: {}", signImagePathsList.size());

                var imageShower = applicationContext.getBean(ImageShower.class);
                var scanner = applicationContext.getBean(Scanner.class);
                var comparator = applicationContext.getBean(FuzzyComparator.class);

                var randomGenerator = new Random();

                int correctAnswers = 0;
                int incorrectAnswers = 0;
                int totalImagesAmount = signImagePathsList.size();

                while (signImagePathsList.size() > 0) {
                    int randomIndex = randomGenerator.nextInt(0, signImagePathsList.size());
                    var imagePath = signImagePathsList.get(randomIndex);
                    signImagePathsList.remove(randomIndex);
                    log.debug("Reading image with path: {}", imagePath);

                    JFrame jFrame = imageShower.showFrame(imagePath);

                    System.out.print("What sign is this: ");
                    var inputLine = scanner.nextLine();
                    if (inputLine.equals("exit")) {
                        imageShower.closeFrame(jFrame);
                        break;
                    }

                    var correctMeaning = imagePath.getFileName().toString().replace(".png", "");
                    boolean linesAreEqual = comparator.compare(correctMeaning, inputLine);

                    if (linesAreEqual) {
                        System.out.println("Correct");
                        correctAnswers++;
                    } else {
                        System.out.println("Incorrect (" + correctMeaning + ")");
                        incorrectAnswers++;
                    }

                    imageShower.closeFrame(jFrame);
                }

                System.out.println("Correct answers: " + correctAnswers);
                System.out.println("Incorrect answers: " + incorrectAnswers);
                System.out.println("Leftover images: " + (totalImagesAmount - correctAnswers - incorrectAnswers));
                System.out.println("Total images: " + totalImagesAmount);

                log.info("Exiting program");
            }
        }
    }
}
