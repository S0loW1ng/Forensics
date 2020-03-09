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



        //pic = bos.toByteArray(); // convert image to byte array.
        String binafied = textToBin(args[1]); //input text
        System.out.println("To Bin " + binafied);
        // AAAAAAAA|RRRRRRRR|GGGGGGGG|BBBBBBBBB
        int maxW = BIm.getWidth();
        int maxH = BIm.getHeight();
        int w = 0;
        int h = 2;
        for (int i = 0; i<binafied.length(); i++){
            int pixelCol = BIm.getRGB(w,h);
            int l =  Integer.parseInt(Character.toString(binafied.charAt(i)));
            // get the first byte propely and set it
            System.out.print(BIm.getRGB(w,h)+ " ");
            if(((pixelCol & 0x1) == 0 && (l==1)) ){
               // pic[i+100] = (byte) (pixelCol | (byte) l);
                System.out.print(BIm.getRGB(w,h)+ " ");
                BIm.setRGB(w,h,(pixelCol | l));
            }else if(((pixelCol & 0x1) == 1 && (l==1)) ){
              ////  pic[i+100] = (byte) (pixelCol | (byte) l);
                System.out.print(BIm.getRGB(w,h) + " ");
                BIm.setRGB(w,h,(pixelCol | l));
            }else if (((pixelCol & 0x1) == 0 && (l==0)) ){
              //  pic[i+100] = (byte) (pixelCol & (byte) l);
               System.out.print(BIm.getRGB(w,h)+ " ");
                BIm.setRGB(w,h,(pixelCol & 0xFFFFFFFE));
            }else if(((pixelCol & 0x1) == 1 && (l==0)) ){
             //   pic[i+100] = (byte) (pixelCol & (byte) l);
                System.out.print(BIm.getRGB(w,h)+ " ");
                BIm.setRGB(w,h,(pixelCol & 0xFFFFFFFE));

            }

            if(w< maxW){
                w++;
            }else{
                w=0;
                if(h< maxH){
                    h++;
                }else {
                    System.out.println("REACHED LIMIT BREAKING");
                    break;
                }
            }

        }
        ImageIO.write(BIm,"bmp", new File("Out.bmp"));
        System.out.println();
        System.out.println("To Bin " + binafied);
        decrypt(binafied, "Out.bmp");

    }

    private static void decrypt(String binafied, String args) throws IOException {
        BufferedImage BIm = ImageIO.read(new File(args));// input image


        String bitefied = "";
        int maxW = BIm.getWidth();
        int maxH = BIm.getHeight();
        int w = 0;
        int h = 2;

        for (int i = 0; i<binafied.length(); i++){
            if(w< maxW){
                w++;
            }else{
                w=0;
                if(h< maxH){
                    h++;
                }else {
                    System.out.println("REACHED LIMIT BREAKING");
                    break;
                }
            }

            int pixel = BIm.getRGB(w,h);
           // System.out.print(pixel + " ");
            if((pixel&0x1) == 1){
                bitefied = bitefied + "1";
            }else
                bitefied = bitefied + "0";



        }
        //rotatethe btes for NO REASON CUZ JAVA IS SOOOOO STUPID OMG CAN YOU LIKE NOT?????
         char first =   bitefied.charAt(0);
        char last = bitefied.charAt(bitefied.length()-1);


        StringBuilder stb = new StringBuilder(bitefied);
         stb.insert(0,'1');
        stb.deleteCharAt(stb.length()-1);

        String decode = new String(new BigInteger(stb.toString(), 2).toByteArray());

        System.out.println("As Dbi:"+ stb.toString());
        System.out.println(decode);


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
