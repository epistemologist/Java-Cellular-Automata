import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 * Utility class that allows for saving 2D array as image
 * Code by: Husnain Raza
 */
public class Image
{
    public static int intFromRGB(int r, int g, int b) {
        int out = r;
        out = (out << 8) + g;
        out = (out << 8) + b;
        return out;
    }

    public static int getRed(int rgb) {
        return (rgb >> 16) & 0xff;
    }

    public static int getGreen(int rgb) {
        return (rgb >> 8) & 0xff;
    }
    
    public static int getBlue(int rgb) {
        return rgb & 0xff;
    }
    
    public static BufferedImage imFromArray(int[][] pixels) {
        BufferedImage out = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                out.setRGB(i,j,pixels[i][j]);
            }
        }
        return out;
    }
    
    public static void saveImage(String filename, BufferedImage im) {
        try {
            File f = new File(filename);
            ImageIO.write(im,"PNG",f);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void saveImageFromArr(String filename, int[][] pixels) {
        saveImage(filename, imFromArray(pixels));
    }
    public static void main(String[] args) {
        int[][] pixels = new int[256][256];
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                pixels[i][j] = intFromRGB(i,j,j);
            }
        }
        saveImageFromArr("test.png",pixels);
    }
}
