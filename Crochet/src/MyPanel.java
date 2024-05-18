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
    private int panel_dim;
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
        
        for(int i=1; i<img.size(); i++) {
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
        // layout.putConstraint(SpringLayout.WEST, img_labels.get(0), 50, SpringLayout.WEST, this);
        // layout.putConstraint(SpringLayout.WEST, img_labels.get(1), 10, SpringLayout.EAST, img_labels.get(0));
        // layout.putConstraint(SpringLayout.WEST, img_labels.get(2), 10, SpringLayout.WEST, img_labels.get(1));
        
    }

    /*
     * Constructor
     *      - gives  the panel  the image and its details
     */
    public MyPanel(int panel_dim, ArrayList<ImageIcon> img, 
            ArrayList<Integer> x_co, ArrayList<Integer> y_co, 
            ArrayList<Double> angles) {

        this.panel_dim = panel_dim;
        this.img = img;
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
