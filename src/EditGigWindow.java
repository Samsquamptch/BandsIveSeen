package src;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class EditGigWindow implements ActionListener, DateChangeListener {
    DatePicker gigDate;
    JComboBox gigList;
    JComboBox venueSelect;
    JComboBox headlineSelect;
    JComboBox support1Select;
    JComboBox support2Select;
    JComboBox support3Select;
    JComboBox ratingList;
    JComboBox addFriend;
    JComboBox removeFriend;
    JPanel sidePanel;
    JPanel editPanel;
    String[] rating = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    GigWindow addWindow;
    int gigDatabaseId;
    Gig selectedGig;
    private final Connection jdbcConnection;

    public EditGigWindow(Connection connection){
        this.jdbcConnection = connection;
        this.selectedGig = new Gig();
    }

    public void newWindow() {
        String[] gigData = ReadDatabase.selectGigs(this.jdbcConnection);
        this.gigList = new JComboBox(gigData);
        this.gigList.addActionListener(this);
        JPanel searchPanel = new JPanel();
        searchPanel.add(this.gigList);

        this.sidePanel = new JPanel();
        setSidePanel("", "", "", "", null);
        this.editPanel = new JPanel();
        setEditPanel();

        this.addWindow = new GigWindow("Edit Gig");
        this.addWindow.add(searchPanel, BorderLayout.NORTH);
        this.addWindow.add(this.sidePanel, BorderLayout.WEST);
        this.addWindow.add(this.editPanel, BorderLayout.CENTER);
    }

    public JPanel createPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(labelText), BorderLayout.NORTH);
        return panel;
    }

    public void setEditPanel() {
        String[] bandData = ReadDatabase.selectBands(this.jdbcConnection);
        this.editPanel.removeAll();
        this.editPanel.setLayout(new GridLayout(6,2, 5, 10));

        //Date picker panel
        this.gigDate = new DatePicker();
        this.gigDate.enableInputMethods(false);
        if (!this.selectedGig.getEventDay().isEmpty()) {
            gigDate.setDate(this.selectedGig.getLocalDate());
        }
        this.gigDate.addDateChangeListener(this::dateChanged);
        JPanel datePickerPanel = createPanel("Gig Date");
        datePickerPanel.add(this.gigDate, BorderLayout.CENTER);

        //Venue select panel
        String[] venueData = ReadDatabase.selectVenues(this.jdbcConnection);
        this.venueSelect = new JComboBox(venueData);
        this.venueSelect.addActionListener(this);
        this.venueSelect.setEnabled(false);
        if (this.selectedGig.getLocation()!=null){
            this.venueSelect.setEnabled(true);
            this.venueSelect.setSelectedItem(this.selectedGig.getLocation().toString());
        }
        JPanel venuePickerPanel = createPanel("Gig Venue");
        venuePickerPanel.add(this.venueSelect, BorderLayout.CENTER);

        //Add Friends panel
        String[] friendArray = ReadDatabase.selectFriends(this.jdbcConnection, this.selectedGig.getWentWith().toArray(new String[0]));
        this.addFriend = new JComboBox(friendArray);
        this.addFriend.addActionListener(this);
        JPanel addFriendPanel = createPanel("Add Friend");
        addFriendPanel.add(this.addFriend, BorderLayout.CENTER);

        //Remove Friends panel
        String[] removeArray = new String[this.selectedGig.getWentWith().size()+1];
        removeArray[0] = "Remove a Friend";
        for (int i = 1; i <= this.selectedGig.getWentWith().size(); i++) {
            removeArray[i] = this.selectedGig.getWentWith().get(i-1);
        }
        this.removeFriend = new JComboBox(removeArray);
        this.removeFriend.addActionListener(this);
        JPanel removeFriendPanel = createPanel("Remove Friend");
        removeFriendPanel.add(this.removeFriend);

        //Headline select panel
        this.headlineSelect = new JComboBox(bandData);
        this.headlineSelect.addActionListener(this);

        //Headline rating select panel


        this.editPanel.add(datePickerPanel);
        this.editPanel.add(venuePickerPanel);
        this.editPanel.add(addFriendPanel);
        this.editPanel.add(removeFriendPanel);
        this.editPanel.add(new JLabel("test"));
        this.editPanel.add(new JLabel("test"));
        this.editPanel.add(new JLabel("test"));
        this.editPanel.add(new JLabel("test"));
        this.editPanel.add(new JLabel("test"));
        this.editPanel.add(new JLabel("test"));
        this.editPanel.add(new JLabel("test"));
        this.editPanel.add(new JLabel("test"));
        this.editPanel.revalidate();
        this.editPanel.repaint();
    }

    public void setSidePanel(String dateLabel, String venueLabel, String headlineLabel, String friends,
                             ArrayList<Band> performancesList) {
        sidePanel.removeAll();
        sidePanel.setPreferredSize(new Dimension(300,150));
        sidePanel.setLayout(new GridLayout(11,1));
        sidePanel.add(new JPanel().add(new JLabel("GIG DETAILS")));
        sidePanel.add(new JPanel().add(new JLabel("Date: " + dateLabel)));
        sidePanel.add(new JPanel().add(new JLabel("Venue: " + venueLabel)));
        sidePanel.add(new JPanel().add(new JLabel("Headline: " + headlineLabel)));
        sidePanel.add(new JPanel().add(new JLabel("Friends: " + friends)));
        sidePanel.add(new JPanel().add(new JLabel("Performances: ")));
        int sparePanels = 5;
        if (performancesList != null){
            for (Band performance : performancesList) {
                sidePanel.add(new JPanel().add(new JLabel(performance.performanceDetails())));
                sparePanels--;
            }
        }
        for (int i = 0; i < sparePanels; i++) {
            sidePanel.add(new JPanel());
        }
        sidePanel.revalidate();
        sidePanel.repaint();
    }

    public void refreshPanels(boolean editPanel) {
        if (editPanel) {
            setEditPanel();
        }
        setSidePanel(this.selectedGig.getEventDay(),
                this.selectedGig.getLocation().toString(),
                this.selectedGig.getHeadlineAct().getBandName(),
                this.selectedGig.getFriendsString(),
                this.selectedGig.getPerformances());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.venueSelect){
            if (this.venueSelect.getSelectedItem()=="Add New Venue") {
                JOptionVenue.addVenue(this.jdbcConnection);
                String[] venueData = ReadDatabase.selectVenues(this.jdbcConnection);
                this.venueSelect.removeAllItems();
                for (String venue : venueData) {
                    this.venueSelect.addItem(venue);
                }
                this.venueSelect.setSelectedIndex(venueData.length-2);
                this.venueSelect.revalidate();
                this.venueSelect.repaint();
            } else if (this.venueSelect.getSelectedIndex()!=0) {
                String[] venueDetails = this.venueSelect.getSelectedItem().toString().split(" - ");
                this.selectedGig.setLocation(new Venue(venueDetails[0], venueDetails[1], false));
            }
            refreshPanels(false);
        }
        if (e.getSource() == this.gigList){
            if (this.gigList.getSelectedIndex()==0){
                setSidePanel("", "", "", null, null);
            }
            else {
                String[] gigDetails = this.gigList.getSelectedItem().toString().split(" - ");
                try {
                    this.gigDatabaseId = ReadDatabase.getGigId(this.jdbcConnection, gigDetails[0], gigDetails[1]);
                    String[] venueAndBandId = ReadDatabase.getGigDetails(this.jdbcConnection, gigDatabaseId);
                    Venue selectedVenue = new Venue(venueAndBandId[1], venueAndBandId[2], false);
                    String[] bandDetails = ReadDatabase.getGigHeadlineDetails(this.jdbcConnection,
                            Integer.parseInt(venueAndBandId[3]), gigDatabaseId);
                    Band selectedHeadline = new Band(bandDetails[0], bandDetails[1], bandDetails[2],
                            Integer.parseInt(bandDetails[3]));
                    ArrayList<Band> gigPerformances = ReadDatabase.getGigPerformances(this.jdbcConnection, gigDatabaseId);
                    this.selectedGig = new Gig(gigDetails[1], selectedVenue, selectedHeadline);
                    for (Band performance : gigPerformances) {
                        if (!Objects.equals(performance.getBandName(), this.selectedGig.getHeadlineAct().getBandName())) {
                            this.selectedGig.addPerformance(performance);
                        }
                    }
                    String[] gigFriends = ReadDatabase.getGigFriends(this.jdbcConnection, gigDatabaseId);
                    for (String friend : gigFriends) {
                        this.selectedGig.addWentWith(friend);
                    }
                    refreshPanels(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public void dateChanged(DateChangeEvent dateChangeEvent) {
        this.selectedGig.setEventDay(this.gigDate.toString());
        refreshPanels(false);
    }
}

