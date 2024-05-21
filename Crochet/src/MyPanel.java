import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/*
 * borrowed code from:
 *      - override graphics = https://www.w3docs.com/snippets/java/how-to-add-an-image-to-a-jpanel.html 
 
 * spring layout tutorial:
 *      https://www.youtube.com/watch?v=eosxG6M0hMA  
 * /

 
/*
 * have to .setContentPanel in frame 
 */
public class MyPanel extends JPanel{
    private int panel_dim, center_co, padding;
    private ArrayList<ImageIcon> img;
    private ArrayList<JLabel> img_labels;
    private ArrayList<Integer> x_co, y_co;
    private ArrayList<Double> angles;

    private SpringLayout layout;
    

    // @Override
    // protected void paintComponent(Graphics g) {
    //     super.paintComponent(g);
    //     g.drawImage(img, 0, 0, this);
    // }


    /*
     * convert imageIcons to JLabels to bee able to add to panel
     */
    private void ConvertToJLabel() {
        
        for(int i=0; i<img.size(); i++) {
            JLabel temp = new JLabel(img.get(i));
            // temp.setBounds(1, 1, 100, 100);
            // temp.setLocation((i+5), (i+5));
            img_labels.add(temp);
            this.add(temp);
            //System.out.println("angles = "+angles.get(i));
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

    private void SetUpLayout() {
        //set up center first
        layout.putConstraint(SpringLayout.WEST, img_labels.get(0), center_co, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, img_labels.get(0), center_co, SpringLayout.NORTH, this);
        // JLabel center = new JLabel(img.get(0));

        for(int i=1; i<img_labels.size(); i++) {
            // img_labels.size()
            System.out.println("x co = "+x_co.get(i-1)+" y co = "+y_co.get(i-1)+" angles = "+angles.get(i-1));  //
            if(angles.get(i-1)<=90) {
                // System.out.println()

                // layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, img_labels.get(i), x_co.get(i-1), SpringLayout.HORIZONTAL_CENTER, img_labels.get(0));
                // layout.putConstraint(SpringLayout.VERTICAL_CENTER, img_labels.get(i), y_co.get(i-1), SpringLayout.NORTH, img_labels.get(0));
                System.out.println("1!");
                int x_coord = center_co - (x_co.get(i-1));
                int y_coord = center_co - (y_co.get(i-1));
                layout.putConstraint(SpringLayout.WEST, img_labels.get(i), x_coord, SpringLayout.WEST, this);
                layout.putConstraint(SpringLayout.NORTH, img_labels.get(i), y_coord, SpringLayout.NORTH, this);
            } else if(angles.get(i-1)<=180) {
                // layout.putConstraint(SpringLayout.EAST, img_labels.get(i), y_co.get(i-1), SpringLayout.WEST, img_labels.get(0));
                // layout.putConstraint(SpringLayout.NORTH, img_labels.get(i), x_co.get(i-1), SpringLayout.SOUTH, img_labels.get(0));
                int x_coord = center_co - (x_co.get(i-1));
                int y_coord = center_co + (y_co.get(i-1) );
                layout.putConstraint(SpringLayout.WEST, img_labels.get(i), x_coord, SpringLayout.WEST, this);
                layout.putConstraint(SpringLayout.NORTH, img_labels.get(i), y_coord, SpringLayout.NORTH, this);
            } else if(angles.get(i-1)<=270) {
                // layout.putConstraint(SpringLayout.WEST, img_labels.get(i), y_co.get(i-1), SpringLayout.EAST, img_labels.get(0));
                // layout.putConstraint(SpringLayout.NORTH, img_labels.get(i), x_co.get(i-1), SpringLayout.SOUTH, img_labels.get(0));
                int x_coord = center_co + (x_co.get(i-1));
                int y_coord = center_co + (y_co.get(i-1) );
                layout.putConstraint(SpringLayout.WEST, img_labels.get(i), x_coord, SpringLayout.WEST, this);
                layout.putConstraint(SpringLayout.NORTH, img_labels.get(i), y_coord, SpringLayout.NORTH, this);
           
            } else {
                // layout.putConstraint(SpringLayout.WEST, img_labels.get(i), y_co.get(i-1), SpringLayout.EAST, img_labels.get(0));
                // layout.putConstraint(SpringLayout.SOUTH, img_labels.get(i), x_co.get(i-1), SpringLayout.NORTH, img_labels.get(0));
                int x_coord = center_co + (x_co.get(i-1));
                int y_coord = center_co -(y_co.get(i-1) );
                layout.putConstraint(SpringLayout.WEST, img_labels.get(i), x_coord, SpringLayout.WEST, this);
                layout.putConstraint(SpringLayout.NORTH, img_labels.get(i), y_coord, SpringLayout.NORTH, this);
            }
            
        }        
        
    }

    /*
     * Constructor
     *      - gives  the panel  the image and its details
     */
    public MyPanel(int panel_dim, ArrayList<ImageIcon> img, int center_co, int padding,
            ArrayList<Integer> x_co, ArrayList<Integer> y_co, 
            ArrayList<Double> angles) {

        this.panel_dim = panel_dim;
        this.img = img;
        this.center_co = center_co;
        this.padding = padding;
        this.x_co = x_co;
        this.y_co = y_co;
        this.angles = angles;
        img_labels = new ArrayList<JLabel>();
        this.layout = new SpringLayout();
        

        SetUpPanel();
        SetUpLayout();
    }

    public static void main(String[] args) {

    }
}
