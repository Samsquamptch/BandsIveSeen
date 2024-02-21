package src;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import src.database.InsertToDatabase;
import src.database.ReadFromDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddGigWindow implements ActionListener, DateChangeListener {
    DatePicker gigDate;
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
    JButton saveButton;
    JPanel sidePanel;
    JPanel editPanel;
    CreateWindow addWindow;
    Gig selectedGig;
    private final Connection jdbcConnection;

    public AddGigWindow(Connection connection){
        this.jdbcConnection = connection;
        this.selectedGig = new Gig();
    }

    public void newWindow() {
        //Save changes button
        this.saveButton = new JButton("Save");
        this.saveButton.setPreferredSize(new Dimension(200,30));
        this.saveButton.addActionListener(this);
        JPanel saveButtonPanel = createPanel("Save Changes");
        saveButtonPanel.add(this.saveButton, BorderLayout.CENTER);

        JPanel optionPanel = new JPanel();
        optionPanel.add(this.saveButton);

        this.sidePanel = new JPanel();
        setSidePanel("", "", "", "", null);
        this.editPanel = new JPanel();
        setEditPanel();

        this.addWindow = new CreateWindow("Add Gig", 900, 500);
        this.addWindow.add(new JPanel(), BorderLayout.NORTH);
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
        String[] bandData = ReadFromDatabase.selectBands(this.jdbcConnection, true);
        String[] rating = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        this.editPanel.removeAll();
        this.editPanel.setLayout(new GridLayout(7,2, 5, 10));

        //Date picker panel
        this.gigDate = new DatePicker();
        this.gigDate.enableInputMethods(false);
        if (this.selectedGig.getEventDay().isEmpty()) {
            this.gigDate.setDateToToday();
            this.selectedGig.setEventDay(this.gigDate.toString());
        } else {
            gigDate.setDate(this.selectedGig.getLocalDate());
        }
        this.gigDate.addDateChangeListener(this);
        JPanel datePickerPanel = createPanel("Gig Date");
        datePickerPanel.add(this.gigDate, BorderLayout.CENTER);

        //Venue select panel
        String[] venueData = ReadFromDatabase.selectVenues(this.jdbcConnection, true);
        this.venueSelect = new JComboBox<>(venueData);
        this.venueSelect.addActionListener(this);
        if (this.selectedGig.getLocation()!=null){
            this.venueSelect.setSelectedItem(this.selectedGig.getLocation().toString());
        }
        JPanel venuePickerPanel = createPanel("Gig Venue");
        venuePickerPanel.add(this.venueSelect, BorderLayout.CENTER);

        //Add Friends panel
        String[] friendArray = ReadFromDatabase.selectFriends(this.jdbcConnection,
                this.selectedGig.getWentWith().toArray(new String[0]), true);
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
            this.headlineSelect.setEnabled(true);
            this.headlineRating.setEnabled(true);
            for (int i = 1; i < 5; i++) {
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
        String bandName = "";
        String venueName = "";
        if (this.selectedGig.getHeadlineAct() != null) {
            bandName = this.selectedGig.getHeadlineAct().getBandName();
        }
        if (this.selectedGig.getLocation() != null) {
            venueName = this.selectedGig.getLocation().toString();
        }
        setSidePanel(this.selectedGig.getEventDay(),
                venueName,
                bandName,
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
            if (this.selectedGig.getHeadlineAct() == null) {
                this.selectedGig.setHeadlineAct(new Band(bandDetails[0],
                        bandDetails[2],
                        bandDetails[1],
                        this.headlineRating.getSelectedIndex()));
            }
            else if (selectValue == 1) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.saveButton) {
            if (!this.selectedGig.checkIfNull()){
                JOptionPane.showMessageDialog(null,
                        "Please ensure all required fields have been filled",
                        "Gig details missing!",JOptionPane.WARNING_MESSAGE);
                return;
            }
            int saveResponse = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to save your changes to this gig?",
                    "Confirm save gig", JOptionPane.YES_NO_OPTION);
            if (saveResponse == 0) {
                try {
                    InsertToDatabase.insertGig(this.jdbcConnection, this.selectedGig);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                this.addWindow.dispose();
            }
        } else if (e.getSource() == this.headlineSelect) {
            bandSelectSettings(this.headlineSelect, 1);
        } else if (e.getSource() == this.support1Select) {
            bandSelectSettings(this.support1Select, 2);
        } else if (e.getSource() == this.support2Select) {
            bandSelectSettings(this.support2Select, 3);
        } else if (e.getSource() == this.support3Select) {
            bandSelectSettings(this.support3Select, 4);
        } else if (e.getSource() == this.support4Select) {
            bandSelectSettings(this.support4Select, 5);
        } else if (e.getSource() == this.headlineRating) {
            bandRatingSettings(this.headlineRating, 0);
        } else if (e.getSource() == this.support1Rating) {
            bandRatingSettings(this.support1Rating, 1);
        } else if (e.getSource() == this.support2Rating) {
            bandRatingSettings(this.support2Rating, 2);
        } else if (e.getSource() == this.support3Rating) {
            bandRatingSettings(this.support3Rating, 3);
        } else if (e.getSource() == this.support4Rating) {
            bandRatingSettings(this.support4Rating, 4);
        } else if (e.getSource() == this.addFriend) {
            friendSelectSettings(this.addFriend);
        } else if (e.getSource() == this.removeFriend) {
            friendSelectSettings(this.removeFriend);
        } else if (e.getSource() == this.venueSelect) {
            setVenueSelect();
        }
    }

    @Override
    public void dateChanged(DateChangeEvent dateChangeEvent) {
        this.selectedGig.setEventDay(this.gigDate.toString());
        refreshPanels(false);
    }
}