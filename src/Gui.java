package src;

import javax.swing.*;
import java.awt.*;

public class Gui {

    public static void main(String[] args) {
        ImageIcon image = new ImageIcon("meirl.png");

        JButton addGigButton = new JButton();
        addGigButton.setText("Add New Gig");
        addGigButton.addActionListener(new GigButton(1));

        JButton editGigButton = new JButton();
        editGigButton.setText("Edit a Gig");
        editGigButton.addActionListener(new GigButton(2));

        JButton delGigButton = new JButton();
        delGigButton.setText("Delete a Gig");
        delGigButton.addActionListener(new GigButton(3));

        JLabel label = new JLabel();
        label.setText("Yo the dog has a beer");
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(0, 0, 250, 250);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.lightGray);
        topPanel.setPreferredSize(new Dimension(100,75));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        topPanel.add(addGigButton);
        topPanel.add(editGigButton);
        topPanel.add(delGigButton);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.lightGray);
        leftPanel.setPreferredSize(new Dimension(250,100));
        leftPanel.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.lightGray);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(label);

        MyFrame frame = new MyFrame();
        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(leftPanel,BorderLayout.WEST);
        frame.add(mainPanel,BorderLayout.CENTER);
    }
}
