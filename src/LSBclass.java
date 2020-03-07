import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;

import java.nio.file.Paths;



/***
 *     __          _______..______   .__   __.      ___   .___________.  ______   .______
 *    |  |        /       ||   _  \  |  \ |  |     /   \  |           | /  __  \  |   _  \
 *    |  |       |   (----`|  |_)  | |   \|  |    /  ^  \ `---|  |----`|  |  |  | |  |_)  |
 *    |  |        \   \    |   _  <  |  . `  |   /  /_\  \    |  |     |  |  |  | |      /
 *    |  `----.----)   |   |  |_)  | |  |\   |  /  _____  \   |  |     |  `--'  | |  |\  \----.
 *    |_______|_______/    |______/  |__| \__| /__/     \__\  |__|      \______/  | _| `._____|
 *
 */

    //////////////////////////////////////////
    /// Same as the other but in this case ///
    /// we use the LSB to hide the text    ///
    /// Less text can be transfered        ///
    //////////////////////////////////////////
public class LSBclass {
    public static void main (String[] args) throws IOException {
        byte[] pic = null;
        BufferedImage BIm = ImageIO.read(new File(args[0]));// input image
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ImageIO.write(BIm,"bmp", bos);
        pic = bos.toByteArray(); // convert image to byte array.
        String binafied = textToBin(args[1]); //input text
        System.out.println("To Bin " + binafied);
        if(pic.length-100>= binafied.length()){ // 100 is pading to avoid errors in the heading of the image.


            for(int i = 0;i < binafied.length(); i ++){
              int l =  Integer.parseInt(Character.toString(binafied.charAt(i)));
              byte by = pic[i+100];

              byte res = (byte) (by ^ (byte) l);
              if(((by & 0x1) == 0 && (l==1)) ){
                  pic[i+100] = (byte) (by | (byte) l);
              }else if(((by & 0x1) == 1 && (l==1)) ){
                  pic[i+100] = (byte) (by | (byte) l);
              }else if (((by & 0x1) == 0 && (l==0)) ){
                  pic[i+100] = (byte) (by & (byte) l);
              }else if(((by & 0x1) == 1 && (l==0)) ){
                  pic[i+100] = (byte) (by & (byte) l);
              }

            }
            BufferedImage out = ImageIO.read(new ByteArrayInputStream(pic));
            ImageIO.write(out,"bmp",new File("out.bmp"));

        }else{
            System.err.println("ERROR");
        }

        decrypt();


    }

    private static void decrypt() throws IOException {
        BufferedImage BIm = ImageIO.read(new File("out.bmp"));// input image
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ImageIO.write(BIm,"bmp", bos);
        byte[] in = bos.toByteArray();

        String bitefied = "";

        for(int i = 100; i<187; i++){ // grabs the lsb
            if((in[i]&0x1) == 1){
                bitefied = bitefied + "1";
            }else
                bitefied = bitefied + "0";
        }


        String decode = new String(new BigInteger(bitefied, 2).toByteArray());
        System.out.println("As decriptedtxt:"+ decode);
    }

    public static byte[] toByteArray(String in) { // file to byte array
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

    public static String textToBin(String file) throws IOException {
        String text = Files.readString(Paths.get(file));
        System.out.println("Text: "+text);

        String binary = new BigInteger(text.getBytes()).toString(2);

        return binary;
    }
}
