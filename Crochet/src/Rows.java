import java.util.ArrayList;
import javax.swing.*;

public class Rows {
    //row size will be 1> than the other arraylists sizes since it includes center image
    private ArrayList<ImageIcon> row;

    private ArrayList<Integer> img_x_co;
    private ArrayList<Integer> img_y_co;
    private ArrayList<Double> angles;

    private int center_co;
    private int num_stitches;
    private int sc_dim;
    private Double inner_rad;
    private Double outer_rad;

    private final int padding = 1;     //padding around the edge of the row

    
    /*  
     * Calculate the image radii = bounds of the row, and absolute center coordinate
     * if we want the image aligned at top left of frame
     *      - square = x and y  are the same
     *      - will use top left of the imageicons in layout
     */
    private void CalcRadii_Center() {
        int inner_circumf = num_stitches*sc_dim;
        inner_rad= ((Double.valueOf(inner_circumf))/(2*Math.PI));
        outer_rad= (inner_rad+(sc_dim/2));
        center_co = ((int) (outer_rad+(padding/2)));
    }

    /*
     * calculate the angles between each stitch
     *      - in degrees
     */
    private Double AnglePerSt_Deg() {
        return (360.0/(Double.valueOf(num_stitches)));
    }

    
    
    /*
     * Add center image to arraylist
     *      - global variables updated to correct values at this point with 
     *        CalcRadii_Center call
     */
    private void AddCenterImg() {
        CalcRadii_Center();
        MyImage center = new MyImage("Center_10x10.png");
        row.add(center.GetImgIcon());
    }
    
    
    /*
     * math used to solve for relative sc coords in CalcCoords
     *      - top_bottom = 1 means relative is top, 0=relative is bottom
     *      - -x = left of center, +x= right of center, -y = below centre, +y = abovee center
     *      - rotation  is counterclockwise
     */
    private int CurrX(Double relative_angle, Double actual_angle) {

        Double delta_x = Math.abs(inner_rad*((Math.sin(Math.toRadians(relative_angle)))));     //rsin(theta)

        if(actual_angle<=180) {
            
            return ((int)Math.floor(delta_x));
        } 
        return ((int)Math.ceil(delta_x));

        
    }
    private int CurrY(Double relative_angle, Double actual_angle) {

        Double delta_y = Math.abs(inner_rad*((Math.cos(Math.toRadians(relative_angle)))));      //rcos(theta))
       
        if((actual_angle>=90) &&(actual_angle<=270)) {
            
            return ((int)(Math.floor(delta_y)));
        }

        return ((int)(Math.ceil(delta_y)));
    }

    /*
     * Fills arraylists of x and y coords with cords for each st
     *      - -x = left of center, +x= right of center, -y = below centre, +y = abovee center
     *      - rotation  is counterclockwise
     */
    private void CalcCoords() {
        //align  first st as no rotation, centered at the top middle
        Double angle_increment = (AnglePerSt_Deg());

        AddCenterImg();

        int top_x_co = 0;
        int top_y_co = (int) Math.ceil(inner_rad);
        //bottom_x_co is still centered, so don't need
        int bottom_y_co = -1*((int) Math.floor(inner_rad));
    
        int curr_x = top_x_co;
        int curr_y = top_y_co;
        
        int relative_y = curr_y;

        Double curr_angle = 0.0;    //in degrees, >0 = counterclockwise direction
        Double relative_angle = curr_angle;     //angle to use sin and cos on

        for(int i=0; i<num_stitches; i++) {
            
            img_x_co.add(curr_x);
            img_y_co.add(curr_y);
            angles.add(curr_angle);
            // System.out.println("curr x  "+curr_x+ " curr y "+ curr_y);
            
            curr_angle = curr_angle + angle_increment;
            
            if ((curr_angle>=90) && (curr_angle<=270)) {    //below the center
                relative_y = bottom_y_co;
                relative_angle = Math.abs((180.0 - curr_angle));
            } else {                                        //above the center
                relative_y =  top_y_co;
                if(curr_angle>=270) {                       
                    relative_angle = 360.0-curr_angle;
                } else {
                    relative_angle = curr_angle;
                }
            }
            
            curr_x = CurrX(relative_angle, curr_angle);
            curr_y = CurrY(relative_angle, curr_angle);
            
        }

    }


    /*
     * fills out rows arraylist with the correct oriented imgs
     */
    private void CreateStitches() {
        for(int i=0; i<num_stitches; i++) {
            
            MyImage sc = new MyImage("SC.png", (angles.get(i)));
           //creating sc should format the  img to be the correct rotation
           //img_x_coord and y should already be absolute locations and are find
            row.add(sc.GetImgIcon());
        } 
    }
    

    /*
     * Constructor
     */
    public Rows(int num_stitches) {
        row = new ArrayList<ImageIcon>();
        img_x_co = new ArrayList<Integer>();
        img_y_co = new ArrayList<Integer>();
        angles = new ArrayList<Double>();

        center_co = padding;
        inner_rad =  0.0;
        outer_rad = 0.0;

        this.num_stitches = num_stitches;

        MyImage sc = new MyImage("SC.png");
        sc_dim = sc.GetScaledDim();      //need the scaled img dimensions to find correct coords
        System.out.println("scaled dim "+sc_dim);
        
        CalcCoords();
        CreateStitches();
        
    }

    public ArrayList<ImageIcon> GetRow() {
        return row;
    }

    public int GetPadding() {
        return padding;
    }

    public ArrayList<Integer> GetXCoords() {
        return img_x_co;
    }

    public ArrayList<Integer> GetYCoords() {
        return img_y_co;
    }

    public ArrayList<Double> GetAngles() {
       return angles;
    }

    public int  GetCenterCoord() {
        return center_co;
    }

    public static void main(String[] args)  {

    }
    

}
