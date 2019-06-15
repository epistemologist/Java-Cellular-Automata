import java.util.Arrays;
import java.awt.Color;

/**
 * Utility class that allows for implementation of various color spectrums
 * Code by: Husnain Raza
 */
public class Colour
{
    public static int intFromRGB(int r, int g, int b) {
        int out = r;
        out = (out << 8) + g;
        out = (out << 8) + b;
        return out;
    }

    public static int intFromRGB(int[] c) {
        return intFromRGB(c[0],c[1],c[2]);
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

    public static int hot(int n) {
        if (n < 256) {
            return intFromRGB(n,0,0);
        } else {
            return intFromRGB(255,n-256,0);
        }
    }

    public static int prism(int n, int period) {
        return Color.HSBtoRGB((float) 1.*n/period,(float) 1.,(float) 1.);
    }

    public static int grayscale(int n, int period) {
        return Color.HSBtoRGB((float) 0.,(float) 0.,(float) 1.*n/period);
    }

    public static int[] genPrismMap(int n) {
        int[] out = new int[n];
        for (int i = 0; i < n; i++) {
            out[i] = prism(i,n);
        }
        return out;
    }

    public static int[] genGrayscaleMap(int n) {
        int[] out = new int[n];
        for (int i = 0; i < n; i++) {
            out[i] = grayscale(i,n);
        }
        return out;
    }

    public static int[] genGradientMap(int n, int r1, int g1, int b1, int r2, int g2, int b2) {
        int[] out = new int[n];
        out[0] = intFromRGB(r1,g1,b1);
        out[n-1] = intFromRGB(r2,g2,b2);
        double rStep = (r2-r1)*1./n;
        double gStep = (g2-g1)*1./n;
        double bStep = (b2-b1)*1./n;
        for (int i = 1; i < n-1; i++) {
            int rCurrent = (int) Math.round(r1 + rStep*i);
            int gCurrent = (int) Math.round(g1 + gStep*i);
            int bCurrent = (int) Math.round(b1 + bStep*i);
            out[i] = intFromRGB(rCurrent, gCurrent, bCurrent);
        }
        return out;
    }
}
