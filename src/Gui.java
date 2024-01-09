package src;

import javax.swing.*;
import java.awt.*;

public class Gui {

    public static void main(String[] args) {
        ImageIcon image = new ImageIcon("meirl.png");

        JButton button = new JButton();
        button.setBounds(75, 75, 100, 50);
        button.setText("Woof");
        button.addActionListener(new ButtonPress());

        JLabel label = new JLabel();
        label.setText("Yo the dog has a beer");
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(0, 0, 250, 250);

        JPanel redPanel = new JPanel();
        redPanel.setBackground(Color.red);
        redPanel.setBounds(0, 0, 250, 250);
        redPanel.setLayout(null);
        redPanel.add(button);

        JPanel bluePanel = new JPanel();
        bluePanel.setBackground(Color.blue);
        bluePanel.setBounds(250, 0, 250, 250);
        bluePanel.setLayout(new BorderLayout());

        JPanel greenPanel = new JPanel();
        greenPanel.setBackground(Color.green);
        greenPanel.setBounds(0, 250, 500, 400);
        greenPanel.setLayout(new BorderLayout());
        greenPanel.add(label);

        MyFrame frame = new MyFrame();
        frame.add(redPanel);
        frame.add(bluePanel);
        frame.add(greenPanel);
    }
}
