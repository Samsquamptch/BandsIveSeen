package src;

import javax.swing.*;
import java.awt.*;

public class Gui {

    public static void main(String[] args) {
        ImageIcon image = new ImageIcon("meirl.png");

        JButton woofButton = new JButton();
        woofButton.setText("Woof");
        woofButton.addActionListener(new WoofPress());

        JButton baaButton = new JButton();
        baaButton.setText("Baa");
        baaButton.addActionListener(new BaaPress());

        JLabel label = new JLabel();
        label.setText("Yo the dog has a beer");
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(0, 0, 250, 250);

        JPanel redPanel = new JPanel();
        redPanel.setBackground(Color.lightGray);
        redPanel.setPreferredSize(new Dimension(100,150));
        redPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        redPanel.add(woofButton);
        redPanel.add(baaButton);

        JPanel bluePanel = new JPanel();
        bluePanel.setBackground(Color.lightGray);
        bluePanel.setPreferredSize(new Dimension(250,100));
        bluePanel.setLayout(new BorderLayout());

        JPanel greenPanel = new JPanel();
        greenPanel.setBackground(Color.lightGray);
        greenPanel.setLayout(new BorderLayout());
        greenPanel.add(label);

        MyFrame frame = new MyFrame();
        frame.add(redPanel,BorderLayout.NORTH);
        frame.add(bluePanel,BorderLayout.WEST);
        frame.add(greenPanel,BorderLayout.CENTER);
    }
}
