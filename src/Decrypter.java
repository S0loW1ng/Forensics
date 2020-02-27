import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.DrbgParameters;

/***
 *    ██╗    ██╗ █████╗ ███╗   ██╗███╗   ██╗ █████╗ ██████╗ ███████╗ ██████╗██████╗ ██╗   ██╗
 *    ██║    ██║██╔══██╗████╗  ██║████╗  ██║██╔══██╗██╔══██╗██╔════╝██╔════╝██╔══██╗╚██╗ ██╔╝
 *    ██║ █╗ ██║███████║██╔██╗ ██║██╔██╗ ██║███████║██║  ██║█████╗  ██║     ██████╔╝ ╚████╔╝ 
 *    ██║███╗██║██╔══██║██║╚██╗██║██║╚██╗██║██╔══██║██║  ██║██╔══╝  ██║     ██╔══██╗  ╚██╔╝  
 *    ╚███╔███╔╝██║  ██║██║ ╚████║██║ ╚████║██║  ██║██████╔╝███████╗╚██████╗██║  ██║   ██║   
 *     ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═════╝ ╚══════╝ ╚═════╝╚═╝  ╚═╝   ╚═╝   
 *                                                                                           
 */



public class Decrypter {
    public static void main(String[] args) throws Exception {
	// Remember to change the arguments of the files to be able to decript the text file.
	
        byte[] bytefiedImage = toByteArray((ImageIO.read(new File(args[0])))); // image
        byte[] bytefiedKey = toByteArray(ImageIO.read(new File(args[1]))); // key
        toTxt(bytefiedImage, bytefiedKey, Integer.valueOf(args[2])); // decrypt text in terminal 

    }
    public static void toTxt ( byte[] in, byte[] key, int len) throws IOException {
        byte[] out = new byte[len];

        for (int i = 0; i < len; i++) {
            out[i] = (byte) (in[i + 100] ^ key[i + 100]);

        }
        System.out.println(new String(out));
    }
    public static byte[] toByteArray(BufferedImage in) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(in, "bmp", bos);

        return bos.toByteArray();
    }


}
