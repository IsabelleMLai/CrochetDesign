import javax.swing.*;

public class Stitch {

    private MyImage img;

    private ImageIcon img_icon;
    private int scaled_dim;
    private int x_co, y_co;
    private double angle;

    /*
     * Set image
     *      - sets MyImage img and ImageIcon img_icon
     */
    private void Set_MyImage(String img_name, double angle, int scaled_dim) {
        img = new MyImage(img_name, angle, scaled_dim);
        img_icon = img.GetImgIcon();
    }


    /*
     * sets  the scaled dimension for each image type
     */
    private void Set_ScaleDim(String img_name) {
        switch(img_name) {
            case ("Center_10x10.png"):
                scaled_dim = 3;
            break;
            case("SC.png"):
                scaled_dim = 40;
            break;
            case("HDC.png"):
                scaled_dim = 20;
            break;
            case("CH.png"):
                scaled_dim = 40;
            break;
            default:
                scaled_dim = 100;
            
        }
    } 


    public Stitch(String img_name, int x_co, int y_co, double angle) {
        this.x_co = x_co;
        this.y_co = y_co;
        this.angle = angle;

        Set_ScaleDim(img_name);
        Set_MyImage(img_name, angle, scaled_dim);
    }

    public Stitch(String img_name) {
        x_co = 0;
        y_co = 0;
        angle = 0.0;

        Set_ScaleDim(img_name);
        Set_MyImage(img_name, angle, scaled_dim);
    }

    public int GetScaleDim() {
        return scaled_dim;
    }

    public ImageIcon GetImgIcon() {
        return img_icon;
    }

    public int GetXCoord() {
        return x_co;
    }

    public int GetYCoord() {
        return y_co;
    }

    public double GetAngle() {
        return angle;
    }
    public static void main(String[] args) {

    }
}
