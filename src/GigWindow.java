package src;

import javax.swing.*;
import java.awt.*;

public class GigWindow extends JFrame {
    GigWindow(String title){
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(true);
        this.setSize(500, 500);
        this.setLayout(new BorderLayout(5,5));
        this.setVisible(true);
    }
}
