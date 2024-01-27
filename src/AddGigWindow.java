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
    JComboBox headlineRating;
    String[] rating = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    DatePicker gigDate;
    JButton testButton;
    Venue gigVenue;
    Band gigHeadline;
    Connection conn = DatabaseConnector.connect();

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
        String[] bandData = ReadDatabase.selectBands(conn);
        this.chooseHeadline = new JComboBox(bandData);
        this.chooseHeadline.addActionListener(this);
        JLabel headlineLabel = new JLabel();
        headlineLabel.setText("Choose Headline Band");
        JPanel setHeadlinePanel = new JPanel();
        setHeadlinePanel.setLayout(new BorderLayout());
        setHeadlinePanel.add(headlineLabel, BorderLayout.NORTH);
        setHeadlinePanel.add(this.chooseHeadline, BorderLayout.CENTER);

        //Headliner Rating Panel
        this.headlineRating = new JComboBox(this.rating);
        this.headlineRating.addActionListener(this);
        this.headlineRating.setEnabled(false);
        JLabel headlineRatingLabel = new JLabel();
        headlineRatingLabel.setText("Set Rating");
        JPanel headlineRatingPanel = new JPanel();
        headlineRatingPanel.setLayout(new BorderLayout());
        headlineRatingPanel.add(headlineRatingLabel, BorderLayout.NORTH);
        headlineRatingPanel.add(this.headlineRating, BorderLayout.CENTER);

        GigWindow addWindow = new GigWindow("Add Gig");
        addWindow.add(datePanel);
        addWindow.add(venuePanel);
        addWindow.add(addFriendPanel);
        addWindow.add(setHeadlinePanel);
        addWindow.add(headlineRatingPanel);
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
        if (e.getSource() == this.chooseHeadline) {
            if (this.chooseHeadline.getSelectedItem() =="Add New Band") {
                System.out.println("New Band Needs to be Added");
            }
            else if (this.chooseHeadline.getSelectedIndex()!=0) {
                String[] bandDetails = this.chooseHeadline.getSelectedItem().toString().split(" - ");
                this.gigHeadline = new Band(bandDetails[0], bandDetails[2], bandDetails[1], 5);
                this.headlineRating.setEnabled(true);
            }
            else {
                this.headlineRating.setEnabled(false);
            }
        }
        if (e.getSource() == this.headlineRating){
            int intRating = Integer.parseInt(this.headlineRating.getSelectedItem().toString());
            this.gigHeadline.setRating(intRating);
        }
    }
}
