package src;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    MyFrame() {
        this.setTitle("Bands I've Seen");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(true);
        this.setSize(1000, 750);
        //I need this BorderLayout to make the BorderLayout in the back panel work
        this.setLayout(new BorderLayout());
        this.setVisible(true);
    }
}
