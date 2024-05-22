import java.util.ArrayList;

public class Rows {
    private ArrayList<Stitch> stitches;
    private String stitch_type;

    private int center_co;
    private int num_stitches;
    private int sc_dim;
    private int spacing = 0;        //dependeent on sizing  of the st
    private Double inner_rad;
    private Double outer_rad;

    /*  
     * Calculate the image radii = bounds of the row, and absolute center coordinate
     * if we want the image aligned at top left of frame
     *      - square = x and y  are the same
     *      - will use top left of the imageicons in layout
     */
    private void CalcRadii_Center() {
        int inner_circumf = num_stitches * (sc_dim-spacing);
        inner_rad= ((Double.valueOf(inner_circumf))/(2*Math.PI));
        outer_rad= (inner_rad+(sc_dim/2));
        center_co = (int)(Math.ceil(outer_rad));
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
     *      - class variables updated to correct values at this point with 
     *        CalcRadii_Center call
     */
    private void AddCenterImg() {
        CalcRadii_Center();
        stitches.add(new Stitch("Center_10x10.png", center_co, center_co, 0.0));
    }
    
    
    /*
     * math used to solve for relative sc coords in CalcCoords
     *      - find the horizontal  and vertical distances (always positive, absolute val)
     *      from  the center
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

        Double curr_angle = 0.0;    //in degrees
        Double relative_angle = curr_angle;     //angle to use sin and cos on

        for(int i=0; i<num_stitches; i++) {
            stitches.add(new Stitch(stitch_type, curr_x, curr_y, curr_angle));
            
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
     * Constructor
     */
    public Rows(String  stitch_type, int num_stitches) {
        stitches = new ArrayList<Stitch>();
        center_co = 0;
        inner_rad =  0.0;
        outer_rad = 0.0;

        this.num_stitches = num_stitches;
        this.stitch_type = stitch_type;

        Stitch sc = new  Stitch(stitch_type);
        sc_dim = sc.GetScaleDim();      //need the scaled img dimensions to find correct coords
        
        CalcCoords();
    }

    public ArrayList<Stitch> GetRow() {
        return stitches;
    }

    public Stitch GetStitch(int index) {
        return stitches.get(index);
    }

    public int GetSize() {
        return stitches.size();
    }

    public int  GetCenterCoord() {
        return center_co;
    }

    public static void main(String[] args)  {

    }
    

}
