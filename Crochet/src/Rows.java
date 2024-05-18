import java.awt.*;
import java.util.ArrayList;

public class Rows {
    private ArrayList<Image> row;
    private ArrayList<Integer> img_x_co;
    private ArrayList<Integer> img_y_co;
    private ArrayList<Double> angles;
    private int num_stitches;

    //private Double x_co, y_co;
    private int sc_dim;
    private final int padding = 5;     //padding around the edge of the row

    
    /*  
     * Calculate the image radii = bounds of the row
     */
    private Double InnerRadius() {
        int inner_circumf = num_stitches*sc_dim;
        return ((Double.valueOf(inner_circumf))/(2*Math.PI));
    }
    private Double OuterRadius() {
        return (InnerRadius()+sc_dim);
    }
    private Double CenterRadius() {
        return  (InnerRadius()+(sc_dim/2));
    }

    /*
     * calculate the angles between each stitch
     *      - in degrees
     */
    private Double AnglePerSt_Deg() {
        return (360.0/(Double.valueOf(num_stitches)));
    }

    /*
     * calculate the coords of the center of the row
     * if we want the image aligned at top left of frame
     *      - square = x and y  are the same
     */
    private int AlignTopLeft_CenterCoords() {
        return ((int) (OuterRadius()+padding));
    }

    
    
    
    /*
     * math used to solve for absolute coords in CalcCoords
     */
    private int CurrX(Double curr_angle, int  relative_x) {
        int curr_x = 0;
        Double delta_x = CenterRadius()*(Math.sin(curr_angle));
        if(curr_angle <=180) {
            curr_x = relative_x+((int)(Math.ceil(delta_x)));
        } else if(curr_angle<360) {
            curr_x = relative_x-((int)(Math.ceil(delta_x)));
        }
        return curr_x;
    }

    private int CurrY(Double curr_angle, int relative_y) {
        Double delta_y = CenterRadius()*(1-(Math.cos(curr_angle)));
        return (relative_y-((int)(Math.ceil(delta_y))));
    }

    /*
     * Fills arraylists of x and y coords with cords for each st
     *      - absolute coords
     *      - go around clockwise
     */
    private void CalcCoords() {
        //align  first st as no rotation, centered at the top middle
        
        Double angle_increment = AnglePerSt_Deg();

        int top_x_co = AlignTopLeft_CenterCoords();
        int top_y_co = (padding+(sc_dim/2));

        int bottom_x_co = top_x_co;
        int bottom_y_co = AlignTopLeft_CenterCoords()+ ((int)Math.ceil(CenterRadius()));

        int curr_x = top_x_co;
        int curr_y = top_y_co;
        int relative_x = curr_x;
        int relative_y = curr_y;

        Double curr_angle = 0.0;    //in degrees
        Double delta_x = 0.0;
        Double delta_y = 0.0;

        for(int i=0; i<num_stitches; i++) {
            img_x_co.add(curr_x);
            img_y_co.add(curr_y);

            curr_angle += angle_increment;
            if(curr_angle>90) {
                if(curr_angle<270) {
                    curr_angle = Math.abs((180.0-curr_angle));
                }
                else {
                    curr_angle = 360-curr_angle;
                }
            } 
            
            if(curr_angle<=90 || curr_angle>=270)  {
                relative_x = top_x_co;
                relative_y = top_y_co;
            } else {
                relative_x = bottom_x_co;
                relative_y = bottom_y_co;
            }

            curr_x = CurrX(curr_angle, relative_x);
            curr_y = CurrY(curr_angle, relative_y);
        }

    }


    /*
     * fills out rows arraylist with the correct oriented imgs
     */
    private void CreateStitches() {
        for(int i=0; i<num_stitches; i++) {

            MyImage sc = new MyImage("SC.png", (img_x_co.get(i)), 
                (img_y_co.get(i)), (angles.get(i)));

           //creating sc should format the  img to be the correct rotation
           //img_x_coord and y should already be absolute locations and are find
            row.add(sc.img);
        } 
    }
    

    /*
     * Constructor
     */
    public Rows(int num_stitches) {
        row = new ArrayList<>();
        img_x_co = new ArrayList<>();
        img_y_co = new ArrayList<>();
        angles = new ArrayList<>();
        this.num_stitches = num_stitches;

        MyImage sc = new MyImage("SC.png");
        sc_dim = sc.GetScaledDim();      //need the scaled img dimensions to find correct coords
    }

    public ArrayList<Image> GetRow() {
        return row;
    }

}
