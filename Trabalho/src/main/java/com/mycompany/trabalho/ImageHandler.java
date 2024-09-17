/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalho;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author felip
 */
public class ImageHandler {
    public BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }
    
    public void saveImage(BufferedImage image, String format, String path) throws IOException {
        ImageIO.write(image, format, new File(path));
    }
}
