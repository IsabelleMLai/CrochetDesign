
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/* 
 * borrowed code from:
 *      loadImage() = https://coderanch.com/t/338802/java/getScaledInstance-scale-images
 *      Rotate() = https://stackoverflow.com/questions/8639567/java-rotating-images 
 *      Resize() = https://stackoverflow.com/questions/9417356/bufferedimage-resize 
        MakeColorTransparent() and  ImgToBuffImg() 
            = https://stackoverflow.com/questions/665406/how-to-make-a-color-transparent-in-a-bufferedimage-and-save-as-png 
 */


public class MyImage {
    // private static int abs_x_co, abs_y_co;
    private static double angle;
    private static int scale_dim;
    private static int orig_img_dim;

    private Image img_clear_bkgnd;
    private static BufferedImage orig_buff_img, final_buff_img;
    private ImageIcon img_icon;
    
    
    /*****************************************************
     * FUNCTIONS
     * ***************************************************
     */

     /*
      * loads image
      */
    private static BufferedImage LoadImage(String img_name){
        File img1 = new File(img_name);
        BufferedImage buff = null;

        try
        {
            buff = ImageIO.read(img1);
        } catch(IOException ioe)  {
            System.err.println("read: " + ioe.getMessage());
        }
        return buff;
    }

    private static void SaveNewFile() {
        try {
             // retrieve image
             BufferedImage bi = final_buff_img;
             File outputfile = new File("NEWSTITCH.png");
             ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
             System.out.println("EERROR");
        }
    }

    private int OrigDim(BufferedImage buff)  {
        int longest = scale_dim;
        if(buff.getWidth()>buff.getHeight()) {
            longest = buff.getWidth();
        } else {
            longest = buff.getHeight();
        }
        return longest;
    }

    /*
     * resize img 
     */
    public static Image Resize(BufferedImage img) { 
        return img.getScaledInstance(scale_dim, scale_dim, Image.SCALE_SMOOTH);
    }  

    private static BufferedImage ImgToBuffImg(Image img, int dim) {
        BufferedImage dest = new BufferedImage(dim, dim, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dest.createGraphics();
        // g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        return  dest;
    }

    

    public static Image MakeColorTransparent(Image im, final Color color) {

        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    


    // /*
    //  * rotate the img
    //  *      - angle param in degrees, rotates clock wise?
    //  *      - size of new image is the size of original square image's diagonal diameter
    //  *          - rotation angle doesn't matter
    //  *      - point of rotation is the center of the image
    //  */
    private static BufferedImage Rotate(Image img) {
        //images will always be square
        BufferedImage buff_img  = ImgToBuffImg(img, orig_img_dim);
        
        int new_diameter = (int) (Math.sqrt(orig_img_dim*orig_img_dim*2));    //diagonal diameter of a square
        int new_diam_center = (new_diameter-orig_img_dim)/2;

        //set dimensions  of final adjusted image
        
        BufferedImage rotated = new BufferedImage(new_diameter, new_diameter, buff_img.getType());
        Graphics2D graphic = rotated.createGraphics();

        // graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        graphic.rotate(Math.toRadians((angle)), orig_img_dim/2, orig_img_dim/2);
        graphic.drawImage(buff_img, new_diam_center,  new_diam_center, orig_img_dim, orig_img_dim,null);
        graphic.dispose();
        

        return rotated;
    }


    /*
     * Constructors
     *      img = final image as imageicon, scaled
     *      
     */
    public MyImage(String img_name, double angle, int scale_dim) {
        
        this.angle = angle;
        this.scale_dim = scale_dim;

        // img_clear_bkgnd = Resize(LoadImage(img_name), scale_dim, scale_dim); //creates an img
        // final_buff_img = Rotate(ImgToBuffImg(img_clear_bkgnd)); //createes buff img
        
        // img_icon = new ImageIcon(final_buff_img);

        orig_buff_img = LoadImage(img_name);
        orig_img_dim  = OrigDim(orig_buff_img);

        img_clear_bkgnd = MakeColorTransparent(orig_buff_img, Color.WHITE);
        // final_buff_img = ImgToBuffImg(img_clear_bkgnd, orig_img_dim);

        final_buff_img = (Rotate(img_clear_bkgnd)); //createes buff img
        final_buff_img = ImgToBuffImg(Resize(final_buff_img), scale_dim);
        SaveNewFile();
        img_icon = new ImageIcon(final_buff_img);
    }

    /*
     * used for center image
     */
    public MyImage(String img_name) {
        
        angle = 0.0;
        scale_dim = 10;

        orig_buff_img = LoadImage(img_name);
        orig_img_dim  = OrigDim(orig_buff_img);

        img_clear_bkgnd = MakeColorTransparent(orig_buff_img, Color.WHITE);
        // final_buff_img = ImgToBuffImg(img_clear_bkgnd, orig_img_dim);

        final_buff_img = (Rotate(img_clear_bkgnd)); //createes buff img
        final_buff_img = ImgToBuffImg(Resize(final_buff_img), scale_dim);
        SaveNewFile();
        img_icon = new ImageIcon(final_buff_img);
    }


    public ImageIcon GetImgIcon() {
        return img_icon;
    }

    public static void main(String[] args)  {

    }


}
