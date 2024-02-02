package src;

import javax.swing.*;
import java.awt.*;

public class GigWindow extends JFrame {

    GigWindow(String title){
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.setSize(900, 500);
        this.setLayout(new BorderLayout(10,10));
        this.setVisible(true);
        this.add(new JPanel(), BorderLayout.SOUTH);
        this.add(new JPanel(), BorderLayout.EAST);
        this.add(new JPanel(), BorderLayout.WEST);
    }
}
