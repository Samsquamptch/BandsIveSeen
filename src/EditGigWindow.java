package src;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import src.database.DeleteFromDatabase;
import src.database.EditDatabase;
import src.database.InsertToDatabase;
import src.database.ReadFromDatabase;

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
    JComboBox<String> gigList;
    JComboBox<String> venueSelect;
    JComboBox<String> headlineSelect;
    JComboBox<String> support1Select;
    JComboBox<String> support2Select;
    JComboBox<String> support3Select;
    JComboBox<String> support4Select;
    JComboBox<String> headlineRating;
    JComboBox<String> support1Rating;
    JComboBox<String> support2Rating;
    JComboBox<String> support3Rating;
    JComboBox<String> support4Rating;
    JComboBox<String> addFriend;
    JComboBox<String> removeFriend;
    JButton deleteButton;
    JButton saveButton;
    JPanel sidePanel;
    JPanel editPanel;
    GigWindow addWindow;
    int gigDatabaseId;
    Gig selectedGig;
    private final Connection jdbcConnection;

    public EditGigWindow(Connection connection){
        this.jdbcConnection = connection;
        this.selectedGig = new Gig();
    }

    public void newWindow() {
        //Gig select menu
        String[] gigData = ReadFromDatabase.selectGigs(this.jdbcConnection);
        this.gigList = new JComboBox<>(gigData);
        this.gigList.addActionListener(this);
        JPanel searchPanel = new JPanel();
        searchPanel.add(this.gigList);

        //Delete band button
        this.deleteButton = new JButton("Delete");
        this.deleteButton.setPreferredSize(new Dimension(200,30));
        this.deleteButton.addActionListener(this);
        JPanel deleteButtonPanel = createPanel("Delete gig");
        deleteButtonPanel.add(this.deleteButton, BorderLayout.CENTER);

        //Save changes button
        this.saveButton = new JButton("Save");
        this.saveButton.setPreferredSize(new Dimension(200,30));
        this.saveButton.addActionListener(this);
        JPanel saveButtonPanel = createPanel("Save Changes");
        saveButtonPanel.add(this.saveButton, BorderLayout.CENTER);

        JPanel optionPanel = new JPanel();
        optionPanel.add(this.deleteButton);
        optionPanel.add(new JPanel());
        optionPanel.add(this.saveButton);

        this.sidePanel = new JPanel();
        setSidePanel("", "", "", "", null);
        this.editPanel = new JPanel();
        setEditPanel();

        this.addWindow = new GigWindow("Edit Gig");
        this.addWindow.add(searchPanel, BorderLayout.NORTH);
        this.addWindow.add(this.sidePanel, BorderLayout.WEST);
        this.addWindow.add(this.editPanel, BorderLayout.CENTER);
        this.addWindow.add(optionPanel, BorderLayout.SOUTH);
    }

    public JPanel createPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(labelText), BorderLayout.NORTH);
        return panel;
    }

    public void setEditPanel() {
        String[] bandData = ReadFromDatabase.selectBands(this.jdbcConnection);
        String[] rating = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        this.editPanel.removeAll();
        this.editPanel.setLayout(new GridLayout(7,2, 5, 10));

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
        String[] venueData = ReadFromDatabase.selectVenues(this.jdbcConnection);
        this.venueSelect = new JComboBox<>(venueData);
        this.venueSelect.addActionListener(this);
        this.venueSelect.setEnabled(false);
        if (this.selectedGig.getLocation()!=null){
            this.venueSelect.setEnabled(true);
            this.venueSelect.setSelectedItem(this.selectedGig.getLocation().toString());
        }
        JPanel venuePickerPanel = createPanel("Gig Venue");
        venuePickerPanel.add(this.venueSelect, BorderLayout.CENTER);

        //Add Friends panel
        String[] friendArray = ReadFromDatabase.selectFriends(this.jdbcConnection, this.selectedGig.getWentWith().toArray(new String[0]));
        this.addFriend = new JComboBox<>(friendArray);
        this.addFriend.addActionListener(this);
        JPanel addFriendPanel = createPanel("Add Friend");
        addFriendPanel.add(this.addFriend, BorderLayout.CENTER);

        //Remove Friends panel
        String[] removeArray = new String[this.selectedGig.getWentWith().size()+1];
        removeArray[0] = "Remove a Friend";
        for (int i = 1; i <= this.selectedGig.getWentWith().size(); i++) {
            removeArray[i] = this.selectedGig.getWentWith().get(i-1);
        }
        this.removeFriend = new JComboBox<>(removeArray);
        this.removeFriend.addActionListener(this);
        JPanel removeFriendPanel = createPanel("Remove Friend");
        removeFriendPanel.add(this.removeFriend, BorderLayout.CENTER);

        if (this.selectedGig.getLocation()!=null){
            this.addFriend.setEnabled(true);
            this.removeFriend.setEnabled(true);
        } else {
            this.addFriend.setEnabled(false);
            this.removeFriend.setEnabled(false);
        }

        //Headline select panel
        this.headlineSelect = new JComboBox<>(bandData);
        this.headlineSelect.removeItem("Remove Band");
        this.headlineSelect.addActionListener(this);
        JPanel headlineSelectPanel = createPanel("Gig Headline");
        headlineSelectPanel.add(this.headlineSelect, BorderLayout.CENTER);

        //Headline rating select panel
        this.headlineRating = new JComboBox<>(rating);
        this.headlineRating.addActionListener(this);
        JPanel headlineRatingPanel = createPanel("Set Rating");
        headlineRatingPanel.add(this.headlineRating, BorderLayout.CENTER);

        //Support 1 select panel
        this.support1Select = new JComboBox<>(bandData);
        this.support1Select.addActionListener(this);
        JPanel support1SelectPanel = createPanel("Supporting Band");
        support1SelectPanel.add(this.support1Select, BorderLayout.CENTER);

        //Support 1 rating select panel
        this.support1Rating = new JComboBox<>(rating);
        this.support1Rating.addActionListener(this);
        JPanel support1RatingPanel = createPanel("Set Rating");
        support1RatingPanel.add(this.support1Rating, BorderLayout.CENTER);

        //Support 2 select panel
        this.support2Select = new JComboBox<>(bandData);
        this.support2Select.addActionListener(this);
        JPanel support2SelectPanel = createPanel("Supporting Band");
        support2SelectPanel.add(this.support2Select, BorderLayout.CENTER);

        //Support 2 rating select panel
        this.support2Rating = new JComboBox<>(rating);
        this.support2Rating.addActionListener(this);
        JPanel support2RatingPanel = createPanel("Set Rating");
        support2RatingPanel.add(this.support2Rating, BorderLayout.CENTER);

        //Support 3 select panel
        this.support3Select = new JComboBox<>(bandData);
        this.support3Select.addActionListener(this);
        JPanel support3SelectPanel = createPanel("Supporting Band");
        support3SelectPanel.add(this.support3Select, BorderLayout.CENTER);

        //Support 3 rating select panel
        this.support3Rating = new JComboBox<>(rating);
        this.support3Rating.addActionListener(this);
        JPanel support3RatingPanel = createPanel("Set Rating");
        support3RatingPanel.add(this.support3Rating, BorderLayout.CENTER);

        //Support 4 select panel
        this.support4Select = new JComboBox<>(bandData);
        this.support4Select.addActionListener(this);
        JPanel support4SelectPanel = createPanel("Supporting Band");
        support4SelectPanel.add(this.support4Select, BorderLayout.CENTER);

        //Support 4 rating select panel
        this.support4Rating = new JComboBox<>(rating);
        this.support4Rating.addActionListener(this);
        JPanel support4RatingPanel = createPanel("Set Rating");
        support4RatingPanel.add(this.support4Rating, BorderLayout.CENTER);

        //Initialise the selectors, populate them, and enable/disable them
        revalidateEditPanel(0);

        this.editPanel.add(datePickerPanel);
        this.editPanel.add(venuePickerPanel);
        this.editPanel.add(addFriendPanel);
        this.editPanel.add(removeFriendPanel);
        this.editPanel.add(headlineSelectPanel);
        this.editPanel.add(headlineRatingPanel);
        this.editPanel.add(support1SelectPanel);
        this.editPanel.add(support1RatingPanel);
        this.editPanel.add(support2SelectPanel);
        this.editPanel.add(support2RatingPanel);
        this.editPanel.add(support3SelectPanel);
        this.editPanel.add(support3RatingPanel);
        this.editPanel.add(support4SelectPanel);
        this.editPanel.add(support4RatingPanel);
        this.editPanel.revalidate();
        this.editPanel.repaint();
    }

    public void revalidateEditPanel(int startVal) {
        JComboBox[] bandSelectArray = new JComboBox[]{this.headlineSelect, this.support1Select, this.support2Select,
                this.support3Select, this.support4Select};
        JComboBox[] bandRatingArray = new JComboBox[]{this.headlineRating, this.support1Rating, this.support2Rating,
                this.support3Rating, this.support4Rating};
        if (this.selectedGig.getHeadlineAct()==null) {
            for (int i = startVal; i < 5; i++) {
                bandSelectArray[i].setEnabled(false);
                bandRatingArray[i].setEnabled(false);
            }
        }
        else {
            for (int i = startVal; i < 5; i++) {
                if (this.selectedGig.getPerformances().size() > i) {
                    bandSelectArray[i].setSelectedItem(this.selectedGig.getPerformances().get(i).toString());
                    bandRatingArray[i].setSelectedIndex(this.selectedGig.getPerformances().get(i).getRating());
                    bandSelectArray[i].setEnabled(true);
                    bandRatingArray[i].setEnabled(true);
                } else if (this.selectedGig.getPerformances().size() == i) {
                    bandSelectArray[i].setEnabled(true);
                    bandRatingArray[i].setEnabled(true);
                } else {
                    bandSelectArray[i].setEnabled(false);
                    bandRatingArray[i].setEnabled(false);
                }
                bandRatingArray[i].revalidate();
                bandRatingArray[i].repaint();
            }
        }
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

    public void friendSelectSettings(JComboBox<String> selectorItem) {
        if (selectorItem.getSelectedItem().equals("Add New Friend")) {
            String friendName = JOptionFriend.addFriend(this.jdbcConnection);
            if (friendName.isEmpty()) {
                selectorItem.setSelectedIndex(0);
                return;
            }
            addFriend.removeItem("Add New Friend");
            addFriend.addItem(friendName);
            addFriend.addItem("Add New Friend");
            this.addFriend.setSelectedItem(friendName);
            friendSelectSettings(this.addFriend);
        }
        else if (selectorItem.getSelectedIndex() != 0) {
            if (selectorItem == this.addFriend) {
                this.selectedGig.addWentWith(this.addFriend.getSelectedItem().toString());
                this.removeFriend.addItem(this.addFriend.getSelectedItem().toString());
                this.addFriend.removeItem(this.addFriend.getSelectedItem());
                this.addFriend.setSelectedIndex(0);
            } else {
                this.selectedGig.removeWentWith(this.removeFriend.getSelectedItem().toString());
                this.addFriend.removeItem("Add New Friend");
                this.addFriend.addItem(this.removeFriend.getSelectedItem().toString());
                this.addFriend.addItem("Add New Friend");
                this.removeFriend.removeItem(this.removeFriend.getSelectedItem());
                this.removeFriend.setSelectedIndex(0);
            }
        }
        refreshPanels(false);
        this.addFriend.revalidate();
        this.addFriend.repaint();
        this.removeFriend.revalidate();
        this.removeFriend.repaint();
    }

    public void bandSelectSettings(JComboBox<String> selectorItem, int selectValue) {
        JComboBox[] bandSelectArray = new JComboBox[]{this.headlineSelect, this.support1Select, this.support2Select,
                this.support3Select, this.support4Select};
        if (selectorItem.getSelectedItem().equals("Add New Band")) {
            String bandName = JOptionBand.addBand(this.jdbcConnection);
            if (bandName.isEmpty()) {
                return;
            }
            for (JComboBox<String> bandSelector : bandSelectArray) {
                bandSelector.removeItem("Remove Band");
                bandSelector.removeItem("Add New Band");
                bandSelector.addItem(bandName);
                bandSelector.addItem("Remove Band");
                bandSelector.addItem("Add New Band");
                bandSelector.revalidate();
                bandSelector.repaint();
            }
            selectorItem.setSelectedItem(bandName);
            bandSelectSettings(selectorItem, selectValue);
        }
        else if (selectorItem.getSelectedItem().equals("Remove Band")) {
            if (this.selectedGig.getPerformances().size() < selectValue){
                JOptionPane.showMessageDialog(null,"You cannot remove a band when nothing has been selected!");
                return;
            }
            int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this band?",
                    "Confirm remove band", JOptionPane.YES_NO_OPTION);
            if (answer != 0) {
                selectorItem.setSelectedItem(this.selectedGig.getPerformances().get(selectValue-1).toString());
            }
            else {
                this.selectedGig.removePerformance(this.selectedGig.getPerformances().get(selectValue-1));
                refreshPanels(true);
            }
        }
        else if (selectorItem.getSelectedIndex()!=0) {
            String[] bandDetails = selectorItem.getSelectedItem().toString().split(" - ");
            if (selectValue == 1) {
                this.selectedGig.setHeadlineAct(new Band(bandDetails[0],
                        bandDetails[2],
                        bandDetails[1],
                        this.selectedGig.getHeadlineAct().getRating()));
            }
            else if (this.selectedGig.getPerformances().size() < selectValue) {
                this.selectedGig.addPerformance(new Band(bandDetails[0], bandDetails[2], bandDetails[1], 0));
            } else {
                this.selectedGig.changePerformance(this.selectedGig.getPerformances().get(selectValue-1),
                        new Band(bandDetails[0], bandDetails[2], bandDetails[1],
                                this.selectedGig.getPerformances().get(selectValue-1).getRating()));
            }
            revalidateEditPanel(selectValue);
            refreshPanels(false);
        }
        else if (this.selectedGig.getPerformances().size() < selectValue) {
            selectorItem.setSelectedIndex(0);
        }
        else {
            selectorItem.setSelectedItem(this.selectedGig.getPerformances().get(selectValue-1).toString());
        }
    }

    public void setVenueSelect() {
        if (this.venueSelect.getSelectedItem().equals("Add New Venue")) {
            String addedVenue = JOptionVenue.addVenue(this.jdbcConnection);
            System.out.println(addedVenue);
            if (addedVenue.isEmpty()) {
                this.venueSelect.setSelectedIndex(0);
                return;
            }
            this.venueSelect.removeItem("Add New Venue");
            this.venueSelect.addItem(addedVenue);
            this.venueSelect.addItem("Add New Venue");
            this.venueSelect.setSelectedItem(addedVenue);
            this.venueSelect.revalidate();
            this.venueSelect.repaint();
            setVenueSelect();
        } else if (this.venueSelect.getSelectedIndex()!=0) {
            String[] venueDetails = this.venueSelect.getSelectedItem().toString().split(" - ");
            this.selectedGig.setLocation(new Venue(venueDetails[0], venueDetails[1], false));
        }
        refreshPanels(false);
    }

    public void bandRatingSettings(JComboBox<String> selectorItem, int selectValue) {
        int intRating = Integer.parseInt(selectorItem.getSelectedItem().toString());
        if (selectValue >= this.selectedGig.getPerformances().size()) {
            selectorItem.setSelectedIndex(0);
            return;
        }
        this.selectedGig.getPerformances().get(selectValue).setRating(intRating);
        refreshPanels(false);
    }

    public void setSelectedGig() throws SQLException {
            String[] gigDetails = this.gigList.getSelectedItem().toString().split(" - ");
            String[] venueAndBandId = ReadFromDatabase.getGigDetails(this.jdbcConnection, gigDatabaseId);
            Venue selectedVenue = new Venue(venueAndBandId[1], venueAndBandId[2], false);
            String[] bandDetails = ReadFromDatabase.getGigHeadlineDetails(this.jdbcConnection,
                    Integer.parseInt(venueAndBandId[3]), gigDatabaseId);
            Band selectedHeadline = new Band(bandDetails[0], bandDetails[1], bandDetails[2],
                    Integer.parseInt(bandDetails[3]));
            ArrayList<Band> gigPerformances = ReadFromDatabase.getGigPerformances(this.jdbcConnection, gigDatabaseId);
            this.selectedGig = new Gig(gigDetails[1], selectedVenue, selectedHeadline);
            for (Band performance : gigPerformances) {
                if (!Objects.equals(performance.getBandName(), this.selectedGig.getHeadlineAct().getBandName())) {
                    this.selectedGig.addPerformance(performance);
                }
            }
            String[] gigFriends = ReadFromDatabase.getGigFriends(this.jdbcConnection, gigDatabaseId);
            for (String friend : gigFriends) {
                this.selectedGig.addWentWith(friend);
            }
    }

    public void saveGigChanges() throws SQLException {
        Gig updatedGig = this.selectedGig;
        setSelectedGig();
        if (!updatedGig.getHeadlineAct().equals(this.selectedGig.getHeadlineAct())) {
            EditDatabase.changeGigHeadline(this.jdbcConnection, updatedGig.getHeadlineAct(), this.gigDatabaseId);
        }
        //Checks to see whether the current or updated gig has more performances, then sets the value to the
        //smaller value. This is so an out-of-bounds error doesn't occur when both lists are compared.
        int maxIteration = Math.min(updatedGig.getPerformances().size(), this.selectedGig.getPerformances().size());
        for (int i = 0; i < maxIteration; i++) {
            if (!updatedGig.getPerformances().get(i).equals(this.selectedGig.getPerformances().get(i))) {
            EditDatabase.changePerformanceBand(this.jdbcConnection,
                    updatedGig.getPerformances().get(i), this.selectedGig.getPerformances().get(i), this.gigDatabaseId);
            }
        }
        if (updatedGig.getPerformances().size() > this.selectedGig.getPerformances().size()) {
            for (int i = maxIteration; i < updatedGig.getPerformances().size(); i++) {
                InsertToDatabase.insertPerformance(this.jdbcConnection, updatedGig.getPerformances().get(i), this.gigDatabaseId);
            }
        }
        else if (updatedGig.getPerformances().size() < this.selectedGig.getPerformances().size()) {
            for (int i = maxIteration; i < this.selectedGig.getPerformances().size(); i++) {
                DeleteFromDatabase.deletePerformance(this.jdbcConnection, this.selectedGig.getPerformances().get(i), this.gigDatabaseId);
            }
        }
        maxIteration = Math.min(updatedGig.getWentWith().size(), this.selectedGig.getWentWith().size());
        for (int i = 0; i < maxIteration; i++) {
            if (!updatedGig.getWentWith().get(i).equals(this.selectedGig.getWentWith().get(i))) {
                EditDatabase.changeWentWith(this.jdbcConnection, updatedGig.getWentWith().get(i),
                        this.selectedGig.getWentWith().get(i), this.gigDatabaseId);
            }
        }
        if (updatedGig.getWentWith().size() > this.selectedGig.getWentWith().size()) {
            for (int i = maxIteration; i < updatedGig.getWentWith().size(); i++) {
                InsertToDatabase.insertAttendedWith(this.jdbcConnection, updatedGig.getWentWith().get(i), this.gigDatabaseId);
            }
        }
        else if (updatedGig.getWentWith().size() < this.selectedGig.getWentWith().size()) {
            for (int i = maxIteration; i < updatedGig.getWentWith().size(); i++) {
                DeleteFromDatabase.deleteAttendedWith(this.jdbcConnection, updatedGig.getWentWith().get(i), this.gigDatabaseId);
            }
        }
        if (!updatedGig.getLocation().equals(this.selectedGig.getLocation())) {
            EditDatabase.changeGigVenue(this.jdbcConnection, updatedGig.getLocation(), this.gigDatabaseId);
        }
        if (!updatedGig.getLocalDate().equals(this.selectedGig.getLocalDate())) {
            EditDatabase.changeGigDate(this.jdbcConnection, updatedGig.getEventDay(), this.gigDatabaseId);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == this.deleteButton || e.getSource() == this.saveButton) && this.selectedGig.getHeadlineAct()==null) {
            JOptionPane.showMessageDialog(null,"Please select a gig before choosing this option",
                    "No Gig Selected!", JOptionPane.WARNING_MESSAGE);
        }
        else if (e.getSource() == this.deleteButton) {
            int deleteResponse = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this gig permanently?",
                "Confirm delete gig", JOptionPane.YES_NO_OPTION);
            if (deleteResponse==0) {
                try {
                    DeleteFromDatabase.deleteGig(this.jdbcConnection, this.gigDatabaseId);
                    JOptionPane.showMessageDialog(null, "Gig has been deleted");
                    this.addWindow.dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if (e.getSource() == this.saveButton) {
            try {
                saveGigChanges();
                JOptionPane.showMessageDialog(null, "Changes have been saved");
                this.addWindow.dispose();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == this.headlineSelect) {
            bandSelectSettings(this.headlineSelect, 1);
        }
        else if (e.getSource() == this.support1Select) {
            bandSelectSettings(this.support1Select, 2);
        }
        else if (e.getSource() == this.support2Select) {
            bandSelectSettings(this.support2Select, 3);
        }
        else if (e.getSource() == this.support3Select) {
            bandSelectSettings(this.support3Select, 4);
        }
        else if (e.getSource() == this.support4Select) {
            bandSelectSettings(this.support4Select, 5);
        }
        else if (e.getSource() == this.headlineRating) {
            bandRatingSettings(this.headlineRating, 0);
        }
        else if (e.getSource() == this.support1Rating) {
            bandRatingSettings(this.support1Rating, 1);
        }
        else if (e.getSource() == this.support2Rating) {
            bandRatingSettings(this.support2Rating, 2);
        }
        else if (e.getSource() == this.support3Rating) {
            bandRatingSettings(this.support3Rating, 3);
        }
        else if (e.getSource() == this.support4Rating) {
            bandRatingSettings(this.support4Rating, 4);
        }
        else if (e.getSource() == this.addFriend){
            friendSelectSettings(this.addFriend);
        }
        else if (e.getSource() == this.removeFriend){
            friendSelectSettings(this.removeFriend);
        }
        else if (e.getSource() == this.venueSelect){
            setVenueSelect();
        }
        else if (e.getSource() == this.gigList){
            if (this.gigList.getSelectedIndex()==0){
                this.selectedGig = new Gig();
                setSidePanel("", "", "", "", null);
                setEditPanel();
                return;
            }
            String[] gigDetails = this.gigList.getSelectedItem().toString().split(" - ");
            try {
                this.gigDatabaseId = ReadFromDatabase.getGigId(this.jdbcConnection, gigDetails[0], gigDetails[1]);
                setSelectedGig();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            refreshPanels(true);
        }
    }

    @Override
    public void dateChanged(DateChangeEvent dateChangeEvent) {
        if (this.selectedGig.getHeadlineAct()==null) {
            this.gigDate.clear();
            return;
        }
        this.selectedGig.setEventDay(this.gigDate.toString());
        refreshPanels(false);
    }
}

