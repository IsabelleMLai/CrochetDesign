
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

    private Image resized_img;
    private static BufferedImage final_buff_img;
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

    /*
     * resize img 
     */
    public static Image Resize(BufferedImage img, int new_w, int new_h) { 
        return img.getScaledInstance(scale_dim, scale_dim, Image.SCALE_SMOOTH);
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

    private BufferedImage ImgToBuffImg(Image img) {
        BufferedImage dest = new BufferedImage( scale_dim, scale_dim, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dest.createGraphics();
        // g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        return  dest;
    }


    /*
     * rotate the img
     *      - angle param in degrees, rotates clock wise?
     *      - size of new image is the size of original square image's diagonal diameter
     *          - rotation angle doesn't matter
     *      - point of rotation is the center of the image
     */
    private static BufferedImage Rotate(BufferedImage buff_img) {
        
        //  =  AdjustRotationDeg(x_co, y_co);
         // int dim = buff_img.getWidth();              //images will always be square
        
        int new_diameter = (int) (Math.sqrt(scale_dim*scale_dim*2));    //diagonal diameter of a square
            
        //set dimensions  of final adjusted image
        BufferedImage rotated = new BufferedImage(new_diameter, new_diameter, buff_img.getType());
        Graphics2D graphic = rotated.createGraphics();

        // graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphic.rotate(Math.toRadians((angle)), scale_dim/2, scale_dim/2);
        // graphic.transform(AffineTransform.getRotateInstance(angle, scale_dim, scale_dim));
        graphic.drawRenderedImage(buff_img, null);
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

        resized_img = Resize(LoadImage(img_name), scale_dim, scale_dim); //creates an img
        final_buff_img = Rotate(ImgToBuffImg(resized_img)); //createes buff img
        
        img_icon = new ImageIcon(final_buff_img);

    }

    /*
     * used for center image
     */
    public MyImage(String img_name) {
        
        angle = 0.0;
        scale_dim = 10;

        resized_img = Resize(LoadImage(img_name), scale_dim, scale_dim); //creates an img
        final_buff_img = Rotate(ImgToBuffImg(resized_img)); //createes buff img
        
        img_icon = new ImageIcon(final_buff_img);
    }


    public ImageIcon GetImgIcon() {
        return img_icon;
    }

    public static void main(String[] args)  {

    }


}
