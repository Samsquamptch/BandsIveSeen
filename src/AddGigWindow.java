package src;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

@SuppressWarnings("ALL")
public class AddGigWindow implements ActionListener {
    JComboBox chooseVenue;
    JComboBox chooseHeadline;
    JComboBox chooseSupport1;
    JComboBox chooseSupport2;
    JComboBox chooseSupport3;
    JComboBox chooseSupport4;
    JComboBox headlineRating;
    JComboBox support1Rating;
    JComboBox support2Rating;
    JComboBox support3Rating;
    JComboBox support4Rating;
    JPanel setSupport1Panel;
    JPanel setSupport2Panel;
    JPanel setSupport3Panel;
    JPanel setSupport4Panel;
    JPanel support1RatingPanel;
    JPanel support2RatingPanel;
    JPanel support3RatingPanel;
    JPanel support4RatingPanel;
    String[] rating = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    JButton submitButton;
    DatePicker gigDate;
    Venue gigVenue;
    Band gigHeadline;
    Band gigSupport1;
    Band gigSupport2;
    Band gigSupport3;
    Band gigSupport4;
    Connection conn = DatabaseConnector.connect();

    public void newWindow() {
        String[] bandData = ReadDatabase.selectBands(conn);

        //Set Date Panel
        this.gigDate = new DatePicker();
        this.gigDate.enableInputMethods(false);
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
        this.chooseHeadline = new JComboBox(bandData);
        this.chooseHeadline.addActionListener(this);
        JLabel headlineLabel = new JLabel();
        headlineLabel.setText("Choose Headline Band");
        JPanel setHeadlinePanel = new JPanel();
        setHeadlinePanel.setLayout(new BorderLayout());
        setHeadlinePanel.add(headlineLabel, BorderLayout.NORTH);
        setHeadlinePanel.add(this.chooseHeadline, BorderLayout.CENTER);

        //Rating Label for all rating options


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

        //Remove Friend Panel
        JPanel removeFriendPanel = new JPanel();

        //Supporting Act 1 Select Panel
        this.chooseSupport1 = new JComboBox(bandData);
        this.chooseSupport1.addActionListener(this);
        JLabel support1Label = new JLabel();
        support1Label.setText("Add Supporting Band");
        this.setSupport1Panel = new JPanel();
        this.setSupport1Panel.setVisible(false);
        this.setSupport1Panel.setLayout(new BorderLayout());
        this.setSupport1Panel.add(support1Label, BorderLayout.NORTH);
        this.setSupport1Panel.add(this.chooseSupport1, BorderLayout.CENTER);

        //Supporting Act 1 Rating Panel
        this.support1Rating = new JComboBox(this.rating);
        this.support1Rating.addActionListener(this);
        this.support1Rating.setEnabled(false);
        JLabel support1RatingLabel = new JLabel();
        support1RatingLabel.setText("Set Rating");
        this.support1RatingPanel = new JPanel();
        this.support1RatingPanel.setVisible(false);
        this.support1RatingPanel.setLayout((new BorderLayout()));
        this.support1RatingPanel.add(support1RatingLabel, BorderLayout.NORTH);
        this.support1RatingPanel.add(this.support1Rating, BorderLayout.CENTER);

        //Supporting Act 2 Select Panel
        this.chooseSupport2 = new JComboBox(bandData);
        this.chooseSupport2.addActionListener(this);
        JLabel support2Label = new JLabel();
        support2Label.setText("Add Supporting Band");
        this.setSupport2Panel = new JPanel();
        this.setSupport2Panel.setVisible(false);
        this.setSupport2Panel.setLayout(new BorderLayout());
        this.setSupport2Panel.add(support2Label, BorderLayout.NORTH);
        this.setSupport2Panel.add(this.chooseSupport2, BorderLayout.CENTER);

        //Supporting Act 2 Rating Panel
        this.support2Rating = new JComboBox(this.rating);
        this.support2Rating.addActionListener(this);
        this.support2Rating.setEnabled(false);
        JLabel support2RatingLabel = new JLabel();
        support2RatingLabel.setText("Set Rating");
        this.support2RatingPanel = new JPanel();
        this.support2RatingPanel.setVisible(false);
        this.support2RatingPanel.setLayout((new BorderLayout()));
        this.support2RatingPanel.add(support2RatingLabel, BorderLayout.NORTH);
        this.support2RatingPanel.add(this.support2Rating, BorderLayout.CENTER);

        //Supporting Act 3 Select Panel
        this.chooseSupport3 = new JComboBox(bandData);
        this.chooseSupport3.addActionListener(this);
        JLabel support3Label = new JLabel();
        support3Label.setText("Add Supporting Band");
        this.setSupport3Panel = new JPanel();
        this.setSupport3Panel.setVisible(false);
        this.setSupport3Panel.setLayout(new BorderLayout());
        this.setSupport3Panel.add(support3Label, BorderLayout.NORTH);
        this.setSupport3Panel.add(this.chooseSupport3, BorderLayout.CENTER);

        //Supporting Act 3 Rating Panel
        this.support3Rating = new JComboBox(this.rating);
        this.support3Rating.addActionListener(this);
        this.support3Rating.setEnabled(false);
        JLabel support3RatingLabel = new JLabel();
        support3RatingLabel.setText("Set Rating");
        this.support3RatingPanel = new JPanel();
        this.support3RatingPanel.setVisible(false);
        this.support3RatingPanel.setLayout((new BorderLayout()));
        this.support3RatingPanel.add(support3RatingLabel, BorderLayout.NORTH);
        this.support3RatingPanel.add(this.support3Rating, BorderLayout.CENTER);

        //Supporting Act 4 Select Panel
        this.chooseSupport4 = new JComboBox(bandData);
        this.chooseSupport4.addActionListener(this);
        JLabel support4Label = new JLabel();
        support4Label.setText("Add Supporting Band");
        this.setSupport4Panel = new JPanel();
        this.setSupport4Panel.setVisible(false);
        this.setSupport4Panel.setLayout(new BorderLayout());
        this.setSupport4Panel.add(support4Label, BorderLayout.NORTH);
        this.setSupport4Panel.add(this.chooseSupport4, BorderLayout.CENTER);

        //Supporting Act 4 Rating Panel
        this.support4Rating = new JComboBox(this.rating);
        this.support4Rating.addActionListener(this);
        this.support4Rating.setEnabled(false);
        JLabel support4RatingLabel = new JLabel();
        support4RatingLabel.setText("Set Rating");
        this.support4RatingPanel = new JPanel();
        this.support4RatingPanel.setVisible(false);
        this.support4RatingPanel.setLayout((new BorderLayout()));
        this.support4RatingPanel.add(support4RatingLabel, BorderLayout.NORTH);
        this.support4RatingPanel.add(this.support4Rating, BorderLayout.CENTER);

        //Button for adding the Gig
        this.submitButton = new JButton();
        this.submitButton.addActionListener(this);
        this.submitButton.setText("Submit");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(submitButton, BorderLayout.CENTER);
        buttonPanel.add(new JPanel(), BorderLayout.NORTH);
        buttonPanel.add(new JPanel(), BorderLayout.WEST);
        buttonPanel.add(new JPanel(), BorderLayout.EAST);

        GigWindow addWindow = new GigWindow("Add Gig");
        addWindow.mainPanel.add(datePanel);
        addWindow.mainPanel.add(venuePanel);
        addWindow.mainPanel.add(addFriendPanel);
        addWindow.mainPanel.add(setHeadlinePanel);
        addWindow.mainPanel.add(headlineRatingPanel);
        addWindow.mainPanel.add(removeFriendPanel);
        addWindow.mainPanel.add(setSupport1Panel);
        addWindow.mainPanel.add(support1RatingPanel);
        addWindow.mainPanel.add(new JPanel());
        addWindow.mainPanel.add(setSupport2Panel);
        addWindow.mainPanel.add(support2RatingPanel);
        addWindow.mainPanel.add(new JPanel());
        addWindow.mainPanel.add(setSupport3Panel);
        addWindow.mainPanel.add(support3RatingPanel);
        addWindow.mainPanel.add(new JPanel());
        addWindow.mainPanel.add(setSupport4Panel);
        addWindow.mainPanel.add(support4RatingPanel);
        addWindow.mainPanel.add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.chooseVenue) {
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
            if (this.chooseHeadline.getSelectedItem()=="Add New Band") {
                new AddBandWindow();
            }
            else if (this.chooseHeadline.getSelectedIndex()!=0) {
                String[] bandDetails = this.chooseHeadline.getSelectedItem().toString().split(" - ");
                this.gigHeadline = new Band(bandDetails[0], bandDetails[2], bandDetails[1], 5);
                this.headlineRating.setEnabled(true);
                this.setSupport1Panel.setVisible(true);
                this.support1RatingPanel.setVisible(true);
            }
            else {
                this.headlineRating.setEnabled(false);
                this.setSupport1Panel.setVisible(false);
                this.support1RatingPanel.setVisible(false);
                this.setSupport2Panel.setVisible(false);
                this.support2RatingPanel.setVisible(false);
                this.setSupport3Panel.setVisible(false);
                this.support3RatingPanel.setVisible(false);
                this.setSupport4Panel.setVisible(false);
                this.support4RatingPanel.setVisible(false);
            }
        }
        if (e.getSource() == this.headlineRating){
            int intRating = Integer.parseInt(this.headlineRating.getSelectedItem().toString());
            this.gigHeadline.setRating(intRating);
        }
        if (e.getSource() == this.chooseSupport1) {
            if (this.chooseSupport1.getSelectedItem() == "Add New Band") {
                new AddBandWindow();
            }
            else if (this.chooseSupport1.getSelectedIndex()!=0) {
                String[] bandDetails = this.chooseSupport1.getSelectedItem().toString().split(" - ");
                this.gigSupport1 = new Band(bandDetails[0], bandDetails[2], bandDetails[1], 5);
                this.support1Rating.setEnabled(true);
                this.setSupport2Panel.setVisible(true);
                this.support2RatingPanel.setVisible(true);
            }
        }
    }
}
