package src;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AddGigWindow implements ActionListener {
    JComboBox chooseVenue;
    JComboBox chooseHeadline;
    DatePicker gigDate;
    JButton testButton;
    Venue gigVenue;

    public void newWindow() {
        //Set Date Panel
        this.gigDate = new DatePicker();
        JLabel dateLabel = new JLabel();
        dateLabel.setText("Gig Date");
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BorderLayout());
        datePanel.add(dateLabel, BorderLayout.NORTH);
        datePanel.add(this.gigDate, BorderLayout.CENTER);

        //Set Venue Panel
        Connection conn = DatabaseConnector.connect();
        String[] venueData = ReadDatabase.selectVenues(conn);
        this.chooseVenue = new JComboBox(venueData);
        this.chooseVenue.addActionListener(this);
        JLabel venueLabel = new JLabel();
        venueLabel.setText("Select Venue");
        JPanel venuePanel = new JPanel();
        venuePanel.setLayout(new BorderLayout());
        venuePanel.add(venueLabel, BorderLayout.NORTH);
        venuePanel.add(this.chooseVenue, BorderLayout.CENTER);

        //Add Friend Panel
        JPanel addFriendPanel = new JPanel();

        //Set Headliner Panel

        JPanel headlinePanel = new JPanel();

        GigWindow addWindow = new GigWindow("Add Gig");
        addWindow.add(datePanel);
        addWindow.add(venuePanel);
        addWindow.add(addFriendPanel);
        addWindow.add(new JPanel());
        addWindow.add(new JPanel());
        addWindow.add(new JPanel());
        addWindow.add(new JPanel());
        addWindow.add(new JPanel());
        addWindow.add(new JPanel());
        addWindow.add(new JPanel());
        addWindow.add(new JPanel());
        addWindow.add(new JPanel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.chooseVenue) ;
        {
            if (this.chooseVenue.getSelectedItem() =="Add New Venue") {
                System.out.println("New Venue Needs to be Added");
            }
            else if (this.chooseVenue.getSelectedIndex()!=0) {
                String[] venueDetails = this.chooseVenue.getSelectedItem().toString().split(" - ");
                this.gigVenue = new Venue(venueDetails[0], venueDetails[1], false);
                System.out.println(this.gigVenue);
            }
        }
        if (e.getSource() == this.testButton){
            System.out.println(this.gigDate);
        }
    }
}
