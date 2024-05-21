

public class Crochet {
    static int frame_dim = 900;
    
    public static void main(String[]  args) {
        // JLabel label= new JLabel();
        // label.setIcon(sc);
        // label.setBackground(Color.WHITE);
        // label.setOpaque(true);
        /*
         * text related basics
         */
        /* 
        label.setText("HI");
        label.setForeground(new Color(100, 1, 20));
        label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        */
        
        // MyLabel label = new MyLabel();
        // MyFrame myFrame = new MyFrame();
        // myFrame.add(label);
        // myFrame.setVisible(true);

        Rows row1 = new Rows(6);
        
        MyPanel panel = new MyPanel(frame_dim, row1.GetRow(), 
                row1.GetCenterCoord(), row1.GetPadding(), row1.GetXCoords(), 
                row1.GetYCoords(), row1.GetAngles());

        MyFrame frame = new MyFrame(frame_dim, panel);
        frame.setVisible(true);

        
    }


    
}
