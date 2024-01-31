package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EditGigWindow implements ActionListener {
    JComboBox gigList;
    JComboBox bandsList;
    JComboBox performanceList;
    JComboBox ratingList;
    JComboBox addFriend;
    JComboBox removeFriend;
    JPanel gigDetails;
    String[] rating = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    GigWindow addWindow;
    int gigDatabaseId;
    String gigDate;
    String gigHeadline;
    String gigVenue;
    ArrayList<Band> gigPerformances;
    ArrayList<String> attendedWith;
    Gig selectedGig;
    Gig editedGig;
    private final Connection jdbcConnection;

    public EditGigWindow(Connection connection){
        this.jdbcConnection = connection;
    }

    public void newWindow() {
        String[] gigData = ReadDatabase.selectGigs(this.jdbcConnection);
        this.gigList = new JComboBox(gigData);
        this.gigList.addActionListener(this);
        JPanel searchPanel = new JPanel();
        searchPanel.add(this.gigList);

        this.gigDetails = new JPanel();
        gigDetails.setLayout(new GridLayout(9,1));
        gigDetails.setBounds(0,0,100,100);
        gigDetails.add(new JPanel().add(new JLabel("GIG DETAILS")));
        gigDetails.add(new JPanel().add(new JLabel("Date: ")));
        gigDetails.add(new JPanel().add(new JLabel("Venue: ")));
        gigDetails.add(new JPanel().add(new JLabel("Headline: ")));
        gigDetails.add(new JPanel().add(new JLabel("Performances: ")));
        gigDetails.add(new JPanel());
        gigDetails.add(new JPanel());
        gigDetails.add(new JPanel());

        this.addWindow = new GigWindow("Edit Gig");
        this.addWindow.add(searchPanel, BorderLayout.NORTH);
        this.addWindow.add(gigDetails, BorderLayout.WEST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.gigList){
            String[] gigDetails = this.gigList.getSelectedItem().toString().split(" - ");
            try {
                int gigID = ReadDatabase.getGigId(this.jdbcConnection, gigDetails[0], gigDetails[1]);
                String[] venueAndBandId = ReadDatabase.getGigDetails(this.jdbcConnection, gigID);
                Venue selectedVenue = new Venue(venueAndBandId[1], venueAndBandId[2], false);
                String[] bandDetails = ReadDatabase.getGigHeadlineDetails(this.jdbcConnection, Integer.parseInt(venueAndBandId[3]), gigID);
                Band selectedHeadline = new Band(bandDetails[0], bandDetails[1], bandDetails[2], Integer.parseInt(bandDetails[3]));
                this.selectedGig = new Gig(gigDetails[1], selectedVenue, selectedHeadline);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        }
    }

