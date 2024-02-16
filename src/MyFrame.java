package src;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    MyFrame() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("appIcon.png"));
        this.setTitle("Bands I've Seen");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(true);
        this.setSize(1200, 800);
        //I need this BorderLayout to make the BorderLayout in the back panel work
        this.setLayout(new BorderLayout());
        this.setVisible(true);
    }

    public static JPanel createPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(labelText), BorderLayout.NORTH);
        return panel;
    }
}
