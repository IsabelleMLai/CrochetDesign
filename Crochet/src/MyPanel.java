import java.awt.*;
import javax.swing.*;

/*
 * borrowed code from:
 *      - override graphics = https://www.w3docs.com/snippets/java/how-to-add-an-image-to-a-jpanel.html 
 */

 
/*
 * have to .setContentPanel in frame 
 */
public class MyPanel extends JPanel{
    private int x_dim, y_dim;
    private Image img;
    private SpringLayout layout;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }


    /*
     * Set up  panel, used in constructor
     */
    private void SetUpPanel() {
        this.setSize(x_dim, y_dim);
        this.setBackground(Color.WHITE);
        this.setLayout(layout);
    }

    /*
     * Constructor
     *      - gives  the panel  the image
     *      - background is white
     */
    public MyPanel(int x_dim, int y_dim, Image img) {
        this.x_dim = x_dim;
        this.y_dim = y_dim;
        this.img = img;

        this.layout = new SpringLayout();

        SetUpPanel();

    }

    public static void main(String[] args) {

    }
}
