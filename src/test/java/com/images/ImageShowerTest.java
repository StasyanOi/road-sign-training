package com.images;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ImageShowerTest {

    ImageShower imageShower = new ImageShower();

    @Test
    void showAndCloseImageSuccessTest() throws IOException, URISyntaxException {
        URL signPng = ClassLoader.getSystemResource("sign.png");
        JFrame jFrame = imageShower.showFrame(Path.of(signPng.toURI()));

        assertTrue(jFrame.isVisible());

        imageShower.closeFrame(jFrame);

        assertFalse(jFrame.isVisible());
    }

    @Test
    void showAndCloseImageFailTest() throws URISyntaxException {
        URL owlJpg = ClassLoader.getSystemResource("owl.jpg");
        var owlJpgPath = Path.of(owlJpg.toURI());
        assertThrows(IllegalArgumentException.class,
                () -> imageShower.showFrame(owlJpgPath), owlJpgPath.toString());

    }
}