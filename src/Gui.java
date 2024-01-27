package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Gui implements ActionListener {
    JButton addGigButton;
    JButton editGigButton;
    JButton delGigButton;
    private final Connection jdbcConnection;

    public Gui(Connection connection){
        this.jdbcConnection = connection;
    }

    public void newUI() {
        ImageIcon image = new ImageIcon("meirl.png");

        this.addGigButton = new JButton();
        this.addGigButton.setText("Add New Gig");
        this.addGigButton.addActionListener(this);

        this.editGigButton = new JButton();
        this.editGigButton.setText("Edit a Gig");
        this.editGigButton.addActionListener(this);

        this.delGigButton = new JButton();
        this.delGigButton.setText("Delete a Gig");
        this.delGigButton.addActionListener(this);

        JLabel label = new JLabel();
        label.setText("Yo the dog has a beer");
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(0, 0, 250, 250);

        JPanel backPanel = new JPanel();
        backPanel.setBackground(Color.darkGray);
        backPanel.setLayout(new BorderLayout(5,5));

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
        backPanel.add(topPanel,BorderLayout.NORTH);
        backPanel.add(leftPanel,BorderLayout.WEST);
        backPanel.add(mainPanel,BorderLayout.CENTER);
        frame.add(backPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addGigButton){
            new AddGigWindow(this.jdbcConnection).newWindow();
        }
        else if(e.getSource()==editGigButton){
            new GigWindow("Edit Gig");
        } else if(e.getSource()==delGigButton){
            new GigWindow("Delete Gig");
        }
    }
}
