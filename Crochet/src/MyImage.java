
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/* 
 * spring layout tutorial:
 *      https://www.youtube.com/watch?v=eosxG6M0hMA  
 * 
 * borrowed code from:
 *      loadImage() = https://coderanch.com/t/338802/java/getScaledInstance-scale-images
 *      Rotate() = https://stackoverflow.com/questions/8639567/java-rotating-images 
 */


public class MyImage {

    
    private static BufferedImage rotated_img;
    private static int abs_x_co, abs_y_co;
    private static double angle;
    private final int scale_dim = 100;

    public Image img;
    
    

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

    // /* FIXXXX  -Dont need???
    //  * adjust rotation 
    //  *      - returns theta in degrees
    //  *      - x_coord and y_coord are relative to the center of the curr image being worked on
    //  *          - center of curr image = (0,0)
    //  *          - +x = right, +y = up; -x = left, -y = down
    //  */
    // private static Double AdjustRotationDeg(Double x_coord, Double y_coord) {
    //     //check that the rotation is not 0 or on axes
    //     if(x_coord == 0) {
    //         //img directly below origin
    //         if(y_coord>0) {
    //             return 180.0;
    //         } 
    //         // img above or on origin
    //         else  {
    //             return 0.0;
    //         }
    //     } else if(y_coord == 0) {
    //         //img to the right
    //         if(x_coord<0) {
    //             return  90.0;
    //         } 
    //         //img to the left
    //         else if(x_coord>0) {
    //             return -90.0;
    //         }
    //     }

    //     Double theta_final  = 0.0;
    //     Double abs_x_coord = Math.abs(x_coord);
    //     Double abs_y_coord = Math.abs(y_coord);
    
    //     //.atan() returns radians, convert to degrees for easier calc
    //     Double img_center_angle = Math.atan((abs_x_coord/abs_y_coord));
    //     Double rotation_center_angle = Math.atan((abs_y_coord/abs_x_coord));

    //     //top left of circle
    //     if((x_coord>0) && (y_coord<0)) {
    //         theta_final  = -1*(Math.toDegrees(img_center_angle));
    //     }  
    //     //bottom  left of circle
    //     if((x_coord>0) &&  (y_coord>0)) {
            
    //         theta_final = (Math.toDegrees(img_center_angle))-180;
    //     }
    //     //bottom right of circle
    //     if((x_coord<0) && (y_coord>0)) {
    //         theta_final = 90+(Math.toDegrees(rotation_center_angle));
    //     }
    //     //top right of circle
    //     if((x_coord<0) && (y_coord<0)) {
    //         theta_final = Math.toDegrees(rotation_center_angle);
    //     }

    //     return theta_final;
        
    // }



    /*
     * rotate the img
     *      - angle param in degrees
     *      - size of new image is the size of original square image's diagonal diameter
     *          - rotation angle doesn't matter
     */
    private static BufferedImage Rotate(BufferedImage buff_img) {
        double angle_deg  = angle;
                    //  =  AdjustRotationDeg(x_co, y_co);
        int dim = buff_img.getWidth();              //images will always be square
        
        int new_diameter = (int) (2*(Math.ceil(((dim/2) * (Math.sqrt(2))))));
            
        //set dimensions  of final adjusted image
        BufferedImage rotated = new BufferedImage(new_diameter, new_diameter, buff_img.getType());
        Graphics2D graphic = rotated.createGraphics();

        graphic.rotate(Math.toRadians(angle_deg), dim/2, dim/2);
        graphic.drawRenderedImage(buff_img, null);
        graphic.dispose();
        return rotated;
    }



    /*
     * Constructor
     *      img = final image
     *      num_stitches = number of stitches in a loop
     */
    public MyImage(String img_name, int abs_x_co, int abs_y_co, double angle) {
        //private
        this.abs_x_co = abs_x_co;
        this.abs_y_co = abs_y_co;
        this.angle = angle;

        //private
        rotated_img  = Rotate(LoadImage(img_name));

        //public
        img = rotated_img.getScaledInstance(scale_dim, scale_dim, Image.SCALE_SMOOTH);
    }

    public MyImage(String img_name) {
        abs_x_co = 0;
        abs_y_co = 0;
        rotated_img = LoadImage(img_name);
        img = img = rotated_img.getScaledInstance(scale_dim, scale_dim, Image.SCALE_SMOOTH);
    }


    public int GetScaledDim() {
        return scale_dim;
    }
    

    public static void main(String[] args)  {

    }


}