import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.nio.file.Files;
import java.util.Random;
public class main {
    public static void main(String[] args) throws Exception {
        byte[] bytefiedImage = toByteArray(ran(ImageIO.read(new File(args[0])))); // image
        byte[] bytefiedKey = toByteArray(ImageIO.read(new File(args[0]))); // key
        byte[] bytefiedText = toByteArray(args[1]); // input file
        if (bytefiedText.length > bytefiedImage.length) {
            System.err.println("ERROR");
        } else {
            for (int i = 0; i < bytefiedText.length; i++) {
                bytefiedImage[i + 100] = (byte) (bytefiedText[i] ^ bytefiedKey[i+100]);
            }

            BufferedImage out = ImageIO.read(new ByteArrayInputStream(bytefiedImage));
            ImageIO.write(out, "bmp", new File(bytefiedText.length + ".bmp"));
          //  ran(ImageIO.read(new File(args[0]))); // create RandomNoise
         //   toTxt(bytefiedImage, bytefiedKey, bytefiedText.length);
        }

    }

    public static byte[] toByteArray(BufferedImage in) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(in, "bmp", bos);

        return bos.toByteArray();
    }

    public static byte[] toByteArray(String in) {
        FileInputStream fis = null;
        byte[] out = null;
        try {
            File file = new File(in);
            out = new byte[(int) file.length()];
            fis = new FileInputStream(file);
            fis.read(out);

        } catch (Exception e) {

        }
        return out;
    }



    public static BufferedImage ran(BufferedImage em) throws IOException {
        int height = em.getHeight();
        int with = em.getWidth();
        Random rn = new Random();
        BufferedImage ret = em;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < with; j++) {
                ret.setRGB(j, i, (int) rn.nextInt());
            }

        }
        ImageIO.write(ret,"bmp", new File("RandomNoice.bmp"));
        return ret;
    }

}