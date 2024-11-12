package io.images;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class ImageShower {

    public JFrame showFrame(Path imagePath) throws IOException {
        if (!imagePath.getFileName().toString().endsWith(".png")) {
            throw new IllegalArgumentException(imagePath.toString());
        }
        var image = ImageIO.read(imagePath.toFile());
        var imageIcon = new ImageIcon(image);
        JLabel jLabel = new JLabel(imageIcon);

        JFrame frame = new JFrame();
        frame.add(jLabel);
        frame.setLayout(new FlowLayout());
        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    public void closeFrame(JFrame frame) {
        frame.setVisible(false);
        frame.dispose();
    }

}
