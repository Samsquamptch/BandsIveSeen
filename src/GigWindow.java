package src;

import javax.swing.*;
import java.awt.*;

public class GigWindow extends JFrame {
    JPanel mainPanel;
    GigWindow(String title){
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayout(6,3, 5, 10));
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.setSize(750, 450);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.add(this.mainPanel, BorderLayout.CENTER);
        this.add(new JPanel(), BorderLayout.SOUTH);
    }
}
