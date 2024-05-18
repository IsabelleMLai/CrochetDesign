
import java.awt.*;
import javax.swing.*;
//import javax.swing.;
//child class of MainFrame

public class MyFrame extends JFrame{
    private int dim;
    private MyPanel panel;
    /*
     * Set up frame
     */

    private void SetupFrame() {
        this.setTitle("hi");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(dim,  dim);
        
        this.getContentPane().setBackground(Color.BLACK);
        this.setContentPane(panel);
    }
    /*
    * constructor = set  default values
    *      values can be changed  in MainFrame
    *          frameName.function(newvals)
    */
    public MyFrame(int dim, MyPanel panel) {
        this.dim = dim;
        this.panel = panel;

        SetupFrame();
    }

    public static void main(String[] args) {

    }
    
        
        
}
