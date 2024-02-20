package src;

import javax.swing.*;
import java.awt.*;

public class CreateWindow extends JFrame {
    CreateWindow() {
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

    CreateWindow(String title, int windowWidth, int windowHeight) {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("appIcon.png"));
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.setSize(windowWidth, windowHeight);
        this.setLayout(new BorderLayout(10,10));
        this.setVisible(true);
        this.add(new JPanel(), BorderLayout.SOUTH);
        this.add(new JPanel(), BorderLayout.EAST);
        this.add(new JPanel(), BorderLayout.WEST);
    }

    public static JPanel createPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(labelText), BorderLayout.NORTH);
        return panel;
    }
}
