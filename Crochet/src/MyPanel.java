import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/*
 * borrowed code from:
 *      - override graphics = https://www.w3docs.com/snippets/java/how-to-add-an-image-to-a-jpanel.html 
 *     
 * spring layout tutorial:
 *      https://www.youtube.com/watch?v=eosxG6M0hMA  
 * /

 
/*
 * have to .setContentPanel in frame 
 */
public class MyPanel extends JPanel{
    private int panel_dim, center_co, padding;
    private Rows row;
    private ArrayList<JLabel> img_labels;
    
    private SpringLayout layout;
    

    // @Override
    // protected void paintComponent(Graphics g) {
    //     super.paintComponent(g);
    //     g.drawImage(img, 0, 0, this);
    // }
    // @Override
    //   public void paintComponent(Graphics g) {
    //         super.paintComponent(g);
    //         Graphics2D g2d = (Graphics2D) g;
    //         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    //                 RenderingHints.VALUE_ANTIALIAS_ON);
    //         g2d.setColor(Color.BLUE);
    //         g2d.rotate(Math.toRadians(20), 100, 185);
    //         g2d.fillRect(40, 125, 120, 120);
    //         g2d.rotate(-Math.toRadians(20), 100, 185);
    //         g2d.fillRect(225, 125, 120, 120);
    //     }


    /*
     * convert imageIcons to JLabels to bee able to add to panel
     */
    private void ConvertToJLabel() {
        
        for(int i=0; i<row.GetSize(); i++) {
            JLabel temp = new JLabel(row.GetStitch(i).GetImgIcon());
            // temp.setBounds(1, 1, 100, 100);
            // temp.setLocation((i+5), (i+5));
            img_labels.add(temp);       //add to JLabel arraylist
            this.add(temp);             //add to panel
        }
    }

    /*
     * Set up  panel, used in constructor
     */
    private void SetUpPanel() {
        this.setSize(panel_dim, panel_dim);
        this.setBackground(Color.WHITE);
        this.setLayout(layout);
        
        ConvertToJLabel();
        // this.repaint();
    }

    /*
     * sets up  layout of stitchees
     *      - clockwise rotation
     *      - sets center, and then  calculates absolute coords  of each  stitch
     *      to place eeach st in the correct  location  relative to the top left corneer
     *      of the screen
     */
    private void SetUpLayout() {
        //set up center first
        center_co = center_co+padding;
        layout.putConstraint(SpringLayout.WEST, img_labels.get(0), center_co, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, img_labels.get(0), center_co, SpringLayout.NORTH, this);

        for(int i=1; i<img_labels.size(); i++) {
            int x_co_abs = 0;
            int y_co_abs = 0;
            Stitch curr_stitch = row.GetStitch(i);
            // System.out.println("x co = "+x_co.get(i-1)+" y co = "+y_co.get(i-1)+" angles = "+angles.get(i-1));  //
            if(curr_stitch.GetAngle() <=90) {
                x_co_abs = center_co + (curr_stitch.GetXCoord());
                y_co_abs = center_co - (curr_stitch.GetYCoord());
            } else if(curr_stitch.GetAngle()<=180) {
                x_co_abs = center_co + (curr_stitch.GetXCoord());
                y_co_abs = center_co + (curr_stitch.GetYCoord());
            } else if(curr_stitch.GetAngle()<=270) {
                x_co_abs = center_co - (curr_stitch.GetXCoord());
                y_co_abs = center_co + (curr_stitch.GetYCoord());
            } else {
                x_co_abs = center_co - (curr_stitch.GetXCoord());
                y_co_abs = center_co -(curr_stitch.GetYCoord());
            }
            layout.putConstraint(SpringLayout.WEST, img_labels.get(i), x_co_abs, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, img_labels.get(i), y_co_abs, SpringLayout.NORTH, this);
        }           
    }

    /*
     * Constructor
     *      - gives  the panel  the image and its details
     */
    public MyPanel(int panel_dim, Rows row, int padding) {

        this.panel_dim = panel_dim;
        this.row = row;
        this.padding = padding;
        this.center_co = row.GetCenterCoord();
        
        img_labels = new ArrayList<JLabel>();
        this.layout = new SpringLayout();
        
        SetUpPanel();
        SetUpLayout();
    }

    public static void main(String[] args) {

    }
}
