package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

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
        setSidePanel("", "", "", null);

        JPanel test = new JPanel();
        test.add(new JLabel("test"));

        this.addWindow = new GigWindow("Edit Gig");
        this.addWindow.add(searchPanel, BorderLayout.NORTH);
        this.addWindow.add(gigDetails, BorderLayout.WEST);
        this.addWindow.add(test, BorderLayout.CENTER);
    }

    public void setSidePanel(String dateLabel, String venueLabel, String headlineLabel, ArrayList<Band> performancesList) {
        gigDetails.removeAll();
        gigDetails.setLayout(new GridLayout(10,1));
        gigDetails.setBounds(0,0,150,100);
        gigDetails.add(new JPanel().add(new JLabel("GIG DETAILS")));
        gigDetails.add(new JPanel().add(new JLabel("Date: " + dateLabel)));
        gigDetails.add(new JPanel().add(new JLabel("Venue: " + venueLabel)));
        gigDetails.add(new JPanel().add(new JLabel("Headline: " + headlineLabel)));
        gigDetails.add(new JPanel().add(new JLabel("Performances: ")));
        int sparePanels = 5;
        if (performancesList != null){
            for (Band performance : performancesList) {
                gigDetails.add(new JPanel().add(new JLabel(performance.toString())));
                sparePanels--;
            }
        }
        for (int i = 0; i < sparePanels; i++) {
            gigDetails.add(new JPanel());
        }
        gigDetails.revalidate();
        gigDetails.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.gigList){
            if (this.gigList.getSelectedIndex()==0){
                setSidePanel("", "", "", null);
            }
            else {
                String[] gigDetails = this.gigList.getSelectedItem().toString().split(" - ");
                try {
                    int gigID = ReadDatabase.getGigId(this.jdbcConnection, gigDetails[0], gigDetails[1]);
                    String[] venueAndBandId = ReadDatabase.getGigDetails(this.jdbcConnection, gigID);
                    Venue selectedVenue = new Venue(venueAndBandId[1], venueAndBandId[2], false);
                    String[] bandDetails = ReadDatabase.getGigHeadlineDetails(this.jdbcConnection, Integer.parseInt(venueAndBandId[3]), gigID);
                    Band selectedHeadline = new Band(bandDetails[0], bandDetails[1], bandDetails[2], Integer.parseInt(bandDetails[3]));
                    this.gigPerformances = ReadDatabase.getGigPerformances(this.jdbcConnection, gigID);
                    this.selectedGig = new Gig(gigDetails[1], selectedVenue, selectedHeadline);
                    for (Band performance : this.gigPerformances) {
                        if (!Objects.equals(performance.getBandName(), this.selectedGig.getHeadlineAct().getBandName())) {
                            this.selectedGig.addPerformance(performance);
                        }
                    }
                    setSidePanel(this.selectedGig.getEventDay(), this.selectedGig.getLocation().toString(),
                            this.selectedGig.getHeadlineAct().getBandName(), this.selectedGig.getPerformances());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}

