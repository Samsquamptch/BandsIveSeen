package src;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import src.database.DeleteFromDatabase;
import src.database.InsertToDatabase;
import src.database.ReadFromDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class EditFestivalWindow implements ActionListener, DateChangeListener {
    DatePicker festivalDate;
    JTextField festivalNameBox;
    JTextField festivalLocationBox;
    JPanel sidePanel;
    JPanel editPanel;
    JPanel selectedDayDatePanel;
    JPanel wentWithPanel;
    JComboBox<String> headlineSelect;
    JComboBox<String> headlineRating;
    JComboBox<String> bandSelect;
    JComboBox<String> bandRating;
    JComboBox<String> editSelect;
    JComboBox<String> editRating;
    JComboBox<String> selectFestivalDay;
    JComboBox<String> removeFriend;
    JComboBox<String> addFriend;
    JComboBox<String> festivalList;
    JTable festivalDayTable;
    JButton addDayButton;
    JButton removeDayButton;
    JButton addBandButton;
    JButton removeBandButton;
    JButton saveButton;
    JButton deleteButton;
    CreateWindow addWindow;
    Festival selectedFestival;
    int festivalDatabaseId;
    private final Connection jdbcConnection;

    public EditFestivalWindow(Connection conn){
        this.jdbcConnection = conn;
        this.selectedFestival = new Festival();
    }

    public void newWindow() {
        //Gig select menu
        String[] festivalData = ReadFromDatabase.selectFestival(this.jdbcConnection);
        this.festivalList = new JComboBox<>(festivalData);
        this.festivalList.setPreferredSize(new Dimension(400,30));
        this.festivalList.addActionListener(this);
        JPanel searchPanel = new JPanel();
        searchPanel.add(this.festivalList);

        //Delete gig button
        this.deleteButton = new JButton("Delete");
        this.deleteButton.setPreferredSize(new Dimension(200,30));
        this.deleteButton.addActionListener(this);
        JPanel deleteButtonPanel = CreateWindow.createPanel("Delete Festival");
        deleteButtonPanel.add(this.deleteButton, BorderLayout.CENTER);

        //Save changes button
        this.saveButton = new JButton("Save");
        this.saveButton.setPreferredSize(new Dimension(200,30));
        this.saveButton.addActionListener(this);
        JPanel saveButtonPanel = CreateWindow.createPanel("Save Changes");
        saveButtonPanel.add(this.saveButton, BorderLayout.CENTER);

        JPanel optionPanel = new JPanel();
        optionPanel.add(this.deleteButton);
        optionPanel.add(new JPanel());
        optionPanel.add(this.saveButton);

        this.sidePanel = new JPanel();
        setSidePanel();
        this.editPanel = new JPanel();
        this.editPanel.add(new JLabel("Select a festival for more options"));

        this.addWindow = new CreateWindow("Add Festival", 1000, 650);
        this.addWindow.add(searchPanel, BorderLayout.NORTH);
        this.addWindow.add(this.sidePanel, BorderLayout.WEST);
        this.addWindow.add(this.editPanel, BorderLayout.CENTER);
        this.addWindow.add(optionPanel, BorderLayout.SOUTH);
    }

    public void setEditPanel() {
        String[] bandData = ReadFromDatabase.selectBands(this.jdbcConnection, true);
        String[] rating = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        this.editPanel.removeAll();
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4,3,10,0));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        this.editPanel.setLayout(new GridLayout(2,1));
        this.editPanel.add(topPanel);
        this.editPanel.add(bottomPanel);

        //Headline select panel
        this.headlineSelect = new JComboBox<>(bandData);
        this.headlineSelect.removeItem("Remove Band");
        this.headlineSelect.addActionListener(this);
        JPanel headlineSelectPanel = CreateWindow.createPanel("Festival Headline");
        headlineSelectPanel.add(this.headlineSelect, BorderLayout.CENTER);

        //Headline rating select panel
        this.headlineRating = new JComboBox<>(rating);
        this.headlineRating.addActionListener(this);
        JPanel headlineRatingPanel = CreateWindow.createPanel("Set Rating");
        headlineRatingPanel.add(this.headlineRating, BorderLayout.CENTER);

        //Band select panel
        this.bandSelect = new JComboBox<>(bandData);
        this.bandSelect.removeItem("Remove Band");
        this.bandSelect.addActionListener(this);
        JPanel bandSelectPanel = CreateWindow.createPanel("Add Band");
        bandSelectPanel.add(this.bandSelect, BorderLayout.CENTER);

        //Band rating select panel
        this.bandRating = new JComboBox<>(rating);
//        this.bandRating.addActionListener(this);
        JPanel bandRatingPanel = CreateWindow.createPanel("Set Rating");
        bandRatingPanel.add(this.bandRating, BorderLayout.CENTER);

        //Add band button
        this.addBandButton = new JButton("Add");
        this.addBandButton.addActionListener(this);
        JPanel addBandPanel = CreateWindow.createPanel("Add Band");
        addBandPanel.add(this.addBandButton, BorderLayout.CENTER);

        //Edit select panel
        String[] addedBands = getAddedBands();
        this.editSelect = new JComboBox<>(addedBands);
        this.editSelect.addActionListener(this);
        JPanel editSelectPanel = CreateWindow.createPanel("Edit/Remove Band");
        editSelectPanel.add(this.editSelect, BorderLayout.CENTER);

        //Band rating select panel
        this.editRating = new JComboBox<>(rating);
        this.editRating.addActionListener(this);
        JPanel editRatingPanel = CreateWindow.createPanel("Edit Rating");
        editRatingPanel.add(this.editRating, BorderLayout.CENTER);

        //Edit or delete panel
        this.removeBandButton = new JButton("Remove");
        this.removeBandButton.addActionListener(this);
        JPanel setDaysPanel = CreateWindow.createPanel("Edit/Remove Band");
        setDaysPanel.add(this.removeBandButton, BorderLayout.CENTER);

        //Table
        String[] columnNames = {"Artist", "Country", "Genre", "Rating"};
        String[][] tableData = getTableData(this.selectFestivalDay.getSelectedIndex());
        this.festivalDayTable = new JTable(tableData, columnNames);
        this.festivalDayTable.setAutoCreateRowSorter(true);
        JScrollPane tableScrollPane = new JScrollPane(festivalDayTable);

        revalidateOtherFields();

        topPanel.add(headlineSelectPanel);
        topPanel.add(headlineRatingPanel);
        topPanel.add(new JPanel());
        topPanel.add(bandSelectPanel);
        topPanel.add(bandRatingPanel);
        topPanel.add(addBandPanel);
        topPanel.add(editSelectPanel);
        topPanel.add(editRatingPanel);
        topPanel.add(setDaysPanel);
        topPanel.add(new JPanel());
        topPanel.add(new JPanel());
        topPanel.add(new JPanel());

        bottomPanel.add(tableScrollPane, BorderLayout.CENTER);

        this.editPanel.revalidate();
        this.editPanel.repaint();
    }

    public void setSidePanel() {
        sidePanel.removeAll();
        sidePanel.setPreferredSize(new Dimension(220,150));
        sidePanel.setLayout(new GridLayout(9,1));

        //Name field
        this.festivalNameBox = new JTextField();
        JPanel namePanel = CreateWindow.createPanel("   Festival Name");
        this.festivalNameBox.setPreferredSize(new Dimension(200, 40));
        namePanel.add(this.festivalNameBox, BorderLayout.CENTER);
        namePanel.add(new JPanel(), BorderLayout.WEST);

        //Location field
        this.festivalLocationBox = new JTextField();
        JPanel locationPanel = CreateWindow.createPanel("   Festival Location");
        this.festivalLocationBox.setPreferredSize(new Dimension(200, 40));
        locationPanel.add(this.festivalLocationBox, BorderLayout.CENTER);
        locationPanel.add(new JPanel(), BorderLayout.WEST);

        //Date picker panel
        this.festivalDate = new DatePicker();
        this.festivalDate.enableInputMethods(false);
        if (this.selectedFestival.getEventDay().isEmpty()) {
            this.festivalDate.setDateToToday();
            this.selectedFestival.setEventDay(this.festivalDate.toString());
        } else {
            festivalDate.setDate(this.selectedFestival.getLocalDate());
        }
        this.festivalDate.addDateChangeListener(this::dateChanged);
        JPanel datePickerPanel = CreateWindow.createPanel("   Start Date");
        datePickerPanel.add(this.festivalDate, BorderLayout.CENTER);
        datePickerPanel.add(new JPanel(), BorderLayout.WEST);

        //Add or remove day Buttons
        this.addDayButton = new JButton("Add");
        this.removeDayButton = new JButton("Remove");
        this.addDayButton.addActionListener(this);
        this.removeDayButton.addActionListener(this);
        JPanel setDaysPanel = CreateWindow.createPanel("   Add/Remove Days");
        JPanel buttonSectionPanel = new JPanel();
        buttonSectionPanel.setLayout(new GridLayout(1,2));
        buttonSectionPanel.add(this.addDayButton);
        buttonSectionPanel.add(this.removeDayButton);
        setDaysPanel.add(buttonSectionPanel, BorderLayout.CENTER);
        setDaysPanel.add(new JPanel(), BorderLayout.WEST);

        //Festival day selector
        this.selectFestivalDay = new JComboBox<>();
        this.selectFestivalDay.addActionListener(this);
        JPanel selectDayPanel = CreateWindow.createPanel("   Select Festival Day");
        selectDayPanel.add(this.selectFestivalDay);
        selectDayPanel.add(new JPanel(), BorderLayout.WEST);

        //Add Friends panel
        String[] friendArray = ReadFromDatabase.selectFriends(this.jdbcConnection,
                this.selectedFestival.getWentWith().toArray(new String[0]), true);
        this.addFriend = new JComboBox<>(friendArray);
        this.addFriend.addActionListener(this);
        JPanel addFriendPanel = CreateWindow.createPanel("   Add Friend");
        addFriendPanel.add(this.addFriend, BorderLayout.CENTER);
        addFriendPanel.add(new JPanel(), BorderLayout.WEST);

        //Remove Friends panel
        String[] removeArray = new String[this.selectedFestival.getWentWith().size()+1];
        removeArray[0] = "Remove a Friend";
        for (int i = 1; i <= this.selectedFestival.getWentWith().size(); i++) {
            removeArray[i] = this.selectedFestival.getWentWith().get(i-1);
        }
        this.removeFriend = new JComboBox<>(removeArray);
        this.removeFriend.addActionListener(this);
        JPanel removeFriendPanel = CreateWindow.createPanel("   Remove Friend");
        removeFriendPanel.add(this.removeFriend, BorderLayout.CENTER);
        removeFriendPanel.add(new JPanel(), BorderLayout.WEST);

        //Went with panel
        JPanel friendPanel = CreateWindow.createPanel("   Went with:");
        this.wentWithPanel = new JPanel();
        friendPanel.add(wentWithPanel, BorderLayout.CENTER);
        this.wentWithPanel.add(new JPanel(), BorderLayout.WEST);
        refreshFriendPanel();

        if (!this.selectedFestival.getFestivalName().isEmpty()) {
            revalidateSidePanel();
        }

        this.sidePanel.add(namePanel);
        this.sidePanel.add(locationPanel);
        this.sidePanel.add(datePickerPanel);
        this.sidePanel.add(setDaysPanel);
        this.sidePanel.add(selectDayPanel);
        this.sidePanel.add(new JPanel());
        this.sidePanel.add(addFriendPanel);
        this.sidePanel.add(removeFriendPanel);
        this.sidePanel.add(friendPanel);
    }

    public String[] getAddedBands() {
        ArrayList<Band> performanceList =
                this.selectedFestival.getFestivalDays().get(this.selectFestivalDay.getSelectedIndex()).getPerformances();
        ArrayList<String> addedBandsList = new ArrayList<>();
        addedBandsList.add("Select a Band");
        for (Band performance : performanceList) {
            if (!Objects.equals(performance.getBandName(), this.selectedFestival.getFestivalDays().get(
                    this.selectFestivalDay.getSelectedIndex()).getHeadlineAct().getBandName())) {
                addedBandsList.add(performance.toString());
            }
        }
        String[] bandArray = new String[addedBandsList.size()];
        bandArray = addedBandsList.toArray(bandArray);
        return bandArray;
    }

    public String[][] getTableData(int dayNumber) {
        FestivalDay tableDay = this.selectedFestival.getFestivalDays().get(dayNumber);
        int arrayLength = tableDay.getPerformances().size();
        String[][] bandTable = new String[arrayLength][4];
        for (int i = 0; i < arrayLength; i++) {
            bandTable[i][0] = tableDay.getPerformances().get(i).getBandName();
            bandTable[i][1] = tableDay.getPerformances().get(i).getFromCountry();
            bandTable[i][2] = tableDay.getPerformances().get(i).getBandGenre();
            bandTable[i][3] = Integer.toString(tableDay.getPerformances().get(i).getRating());
        }
        return bandTable;
    }

    public void updateTable() {
        String[] columnNames = {"Artist", "Country", "Genre", "Rating"};
        String[][] tableData = getTableData(this.selectFestivalDay.getSelectedIndex());
        DefaultTableModel model = new DefaultTableModel(tableData, columnNames);
        this.festivalDayTable.setModel(model);
    }

    public void revalidateSidePanel() {
        String newName = this.selectedFestival.getFestivalName().substring(0, this.selectedFestival.getFestivalName().length() -5);
        this.festivalNameBox.setText(newName);
        this.festivalLocationBox.setText(this.selectedFestival.getLocation().getVenueLocation());
        for (int i = 1; i <= this.selectedFestival.getNumberOfDays(); i++) {
            this.selectFestivalDay.addItem("Day " + i);
        }
        for (String friend : this.selectedFestival.getWentWith()) {
            this.addFriend.removeItem(friend);
            this.removeFriend.addItem(friend);
        }
        this.addFriend.revalidate();
        this.addFriend.repaint();
        this.removeFriend.revalidate();
        this.removeFriend.repaint();
        this.selectFestivalDay.revalidate();
        this.selectFestivalDay.repaint();
    }

    public void refreshFriendPanel() {
        this.wentWithPanel.removeAll();
        this.wentWithPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel friendLabel = new JLabel(this.selectedFestival.getFriendsString());
        this.wentWithPanel.add(friendLabel);
        this.wentWithPanel.revalidate();
        this.wentWithPanel.repaint();
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
                this.selectedFestival.addWentWith(this.addFriend.getSelectedItem().toString());
                this.removeFriend.addItem(this.addFriend.getSelectedItem().toString());
                this.addFriend.removeItem(this.addFriend.getSelectedItem());
                this.addFriend.setSelectedIndex(0);
            } else {
                this.selectedFestival.removeWentWith(this.removeFriend.getSelectedItem().toString());
                this.addFriend.removeItem("Add New Friend");
                this.addFriend.addItem(this.removeFriend.getSelectedItem().toString());
                this.addFriend.addItem("Add New Friend");
                this.removeFriend.removeItem(this.removeFriend.getSelectedItem());
                this.removeFriend.setSelectedIndex(0);
            }
        }
        this.addFriend.revalidate();
        this.addFriend.repaint();
        this.removeFriend.revalidate();
        this.removeFriend.repaint();
        refreshFriendPanel();
    }

    public String setFestivalName() {
        int selectedIndex = this.selectFestivalDay.getSelectedIndex();
        return this.selectedFestival.getFestivalName() + ": " +
                this.selectedFestival.getFestivalDays().get(selectedIndex).getDay();
    }

    public void revalidateOtherFields() {
        if (this.selectedFestival.getFestivalDays().get(this.selectFestivalDay.getSelectedIndex()).getHeadlineAct() != null) {
            this.headlineRating.setSelectedIndex(this.selectedFestival.getFestivalDays().get(
                    this.selectFestivalDay.getSelectedIndex()).getHeadlineAct().getRating());
            this.headlineSelect.setSelectedItem(this.selectedFestival.getFestivalDays().get(
                    this.selectFestivalDay.getSelectedIndex()).getHeadlineAct().toString());
            this.headlineRating.setEnabled(true);
            this.bandSelect.setEnabled(true);
            this.bandRating.setEnabled(true);
            this.editSelect.setEnabled(true);
            this.editRating.setEnabled(true);
        } else {
            this.headlineRating.setEnabled(false);
            this.bandSelect.setEnabled(false);
            this.bandRating.setEnabled(false);
            this.editSelect.setEnabled(false);
            this.editRating.setEnabled(false);
        }
    }

    public void bandSelectSettings(JComboBox<String> selectorItem, int checkState) {
        FestivalDay selectedFestivalDay = this.selectedFestival.getFestivalDays().get(this.selectFestivalDay.getSelectedIndex());
        JComboBox[] bandSelectArray = new JComboBox[]{this.headlineSelect, this.bandSelect};
        if (selectorItem.getSelectedItem().equals("Add New Band")) {
            String bandName = JOptionBand.addBand(this.jdbcConnection);
            if (bandName.isEmpty()) {
                return;
            }
            for (JComboBox<String> bandSelector : bandSelectArray) {
                bandSelector.removeItem("Add New Band");
                bandSelector.addItem(bandName);
                bandSelector.addItem("Add New Band");
                bandSelector.revalidate();
                bandSelector.repaint();
            }
            selectorItem.setSelectedItem(bandName);
            bandSelectSettings(selectorItem, 0);
        } else if (selectorItem.getSelectedIndex()==0) {
            if (selectorItem == this.headlineSelect && !selectedFestivalDay.getPerformances().isEmpty()) {
                this.headlineSelect.setSelectedItem(selectedFestivalDay.getPerformances().get(0).toString());
            }
            return;
        }
        String[] bandDetails = selectorItem.getSelectedItem().toString().split(" - ");
        if (selectorItem.getSelectedIndex() != 0 & checkState == 2) {
            selectedFestivalDay.removePerformance(this.editSelect.getSelectedIndex());
            this.editSelect.removeItem(this.editSelect.getSelectedItem());
            this.editSelect.setSelectedIndex(0);
            this.editRating.setSelectedIndex(0);
        } else if (selectorItem == this.headlineSelect) {
            selectedFestivalDay.setHeadlineAct(new Band(bandDetails[0],
                    bandDetails[2],
                    bandDetails[1],
                    this.headlineRating.getSelectedIndex()));
            revalidateOtherFields();
        } else if (selectorItem == this.bandSelect && checkState == 1) {
            selectedFestivalDay.addPerformance(new Band(bandDetails[0],
                    bandDetails[2],
                    bandDetails[1],
                    this.bandRating.getSelectedIndex()));
            this.editSelect.addItem(selectorItem.getSelectedItem().toString());
            this.bandSelect.setSelectedIndex(0);
            this.bandRating.setSelectedIndex(0);
        } else if (selectorItem == this.editSelect) {
            this.editRating.setSelectedIndex(
                    selectedFestivalDay.getPerformances().get(this.editSelect.getSelectedIndex()).getRating());
        }
        this.editSelect.revalidate();
        this.editSelect.repaint();
        updateTable();
    }

    public void removeBand() {
        if (this.selectedFestival.getNumberOfDays() <= 1) {
            JOptionPane.showMessageDialog(null,
                    "You can't remove the only day of a festival!",
                    "Can't remove day!", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!this.selectedFestival.getFestivalDays().get(
                this.selectedFestival.getNumberOfDays()-1).getPerformances().isEmpty()) {
            int removeResponse = JOptionPane.showConfirmDialog(null,
                    "Festival day has performances added, do you wish to remove it?",
                    "Confirm remove day", JOptionPane.YES_NO_OPTION);
            if (removeResponse != 0) {
                return;
            }
        }
        this.selectFestivalDay.removeItem("Day " + this.selectedFestival.getNumberOfDays());
        this.selectedFestival.removeDays();
        this.selectFestivalDay.revalidate();
        this.selectFestivalDay.repaint();
    }

    public void deleteFestival() throws SQLException {
        int deleteResponse = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this festival permanently?",
                "Confirm delete festival", JOptionPane.YES_NO_OPTION);
        if (deleteResponse==0) {
            String[] festivalDayIds = ReadFromDatabase.getFestivalDayIds(this.jdbcConnection, this.festivalDatabaseId);
            for (String dayId : festivalDayIds) {
                DeleteFromDatabase.deleteGig(this.jdbcConnection, Integer.parseInt(dayId));
            }
            DeleteFromDatabase.deleteVenue(this.jdbcConnection, this.festivalDatabaseId);
            JOptionPane.showMessageDialog(null, "Festival has been deleted");
            this.addWindow.dispose();
        }
    }

    public void saveChanges() {
        Festival updatedFestival = this.selectedFestival;
        if (this.festivalNameBox.getText().isEmpty() || this.festivalLocationBox.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please ensure the name and location boxes aren't empty",
                    "Field is Empty!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (FestivalDay festivalDay : this.selectedFestival.getFestivalDays()) {
            if (festivalDay.getHeadlineAct() == null) {
                JOptionPane.showMessageDialog(null,
                        "Please ensure all festival days have set headlines",
                        "Headline is empty!", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        //Set location for festival and add it to each sub day
        this.selectedFestival.setLocation(new Venue(this.selectedFestival.getFestivalName(),
                this.festivalLocationBox.getText(), true));
        for (Gig festivalDay : this.selectedFestival.getFestivalDays()) {
            festivalDay.setLocation(this.selectedFestival.getLocation());
        }
        //Adds friends to the first day
        for (String friend : this.selectedFestival.getWentWith()) {
            this.selectedFestival.getFestivalDays().get(0).addWentWith(friend);
        }
        try {
            InsertToDatabase.insertVenue(this.jdbcConnection, this.selectedFestival.getLocation());
            for (FestivalDay currentDay : this.selectedFestival.getFestivalDays()) {
                InsertToDatabase.insertGig(this.jdbcConnection, currentDay);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        JOptionPane.showMessageDialog(null, "Festival has been added！");
        this.addWindow.dispose();
    }

    public void setSelectedFestival() throws SQLException {
        String[] gigDetails = this.festivalList.getSelectedItem().toString().split(" - ");
        String[] festivalDayIds = ReadFromDatabase.getFestivalDayIds(this.jdbcConnection, this.festivalDatabaseId);
        //Creates the festival object
        this.selectedFestival = new Festival(gigDetails[0]);
        this.selectedFestival.setLocation(new Venue(gigDetails[0], gigDetails[1], true));
        this.selectedFestival.setEventDay(ReadFromDatabase.getGigDate(this.jdbcConnection, Integer.parseInt(festivalDayIds[0])));
        //Iterates through the days of the festival
        for (int i = 0; i < festivalDayIds.length; i++) {
            this.selectedFestival.addDays();
            String[] festivalDayDetails = ReadFromDatabase.getFestivalDayDetails(this.jdbcConnection, Integer.parseInt(festivalDayIds[i]));
            String[] bandDetails = ReadFromDatabase.getGigHeadlineDetails(this.jdbcConnection,
                    Integer.parseInt(festivalDayDetails[1]), Integer.parseInt(festivalDayDetails[0]));
            Band selectedHeadline = new Band(bandDetails[0], bandDetails[1], bandDetails[2],
                    Integer.parseInt(bandDetails[3]));
            this.selectedFestival.getFestivalDays().get(i).setHeadlineAct(selectedHeadline);
            ArrayList<Band> gigPerformances = ReadFromDatabase.getGigPerformances(this.jdbcConnection,
                    Integer.parseInt(festivalDayDetails[0]));
            //Adds band to the current day
            for (Band performance : gigPerformances) {
                if (!Objects.equals(performance.getBandName(), this.selectedFestival.getFestivalDays().get(
                        i).getHeadlineAct().getBandName())) {
                    this.selectedFestival.getFestivalDays().get(i).addPerformance(performance);
                }
            }
        }
        //Adds friends to the festival
        String[] festivalFriends = ReadFromDatabase.getGigFriends(this.jdbcConnection, Integer.parseInt(festivalDayIds[0]));
        for (String friend : festivalFriends) {
            this.selectedFestival.addWentWith(friend);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == this.deleteButton || e.getSource() == this.saveButton) && this.festivalList.getSelectedIndex()==0) {
            JOptionPane.showMessageDialog(null,"Please select a festival before choosing this option",
                    "No Festival Selected!", JOptionPane.WARNING_MESSAGE);
        } else if (e.getSource() == this.deleteButton) {
            try { deleteFestival();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == this.saveButton) {
//            saveChanges();
            assert true;
        }
        else if (e.getSource() == this.addDayButton) {
            if (this.selectedFestival.getNumberOfDays() >= 7) {
                return;
            }
            this.selectedFestival.addDays();
            this.selectFestivalDay.addItem("Day " + this.selectedFestival.getNumberOfDays());
            this.selectFestivalDay.revalidate();
            this.selectFestivalDay.repaint();
        } else if (e.getSource() == this.removeDayButton) {
            removeBand();
        } else if (e.getSource() == this.headlineSelect) {
            bandSelectSettings(this.headlineSelect, 0);
        } else if (e.getSource() == this.headlineRating) {
            this.selectedFestival.getFestivalDays().get(
                    this.selectFestivalDay.getSelectedIndex()).getHeadlineAct().setRating(this.headlineRating.getSelectedIndex());
            updateTable();
        } else if (e.getSource() == this.addBandButton) {
            bandSelectSettings(this.bandSelect, 1);
        } else if (e.getSource() == this.bandSelect) {
            bandSelectSettings(this.bandSelect, 0);
        } else if (e.getSource() == this.editSelect) {
            bandSelectSettings(this.editSelect, 0);
        } else if (e.getSource() == this.editRating && this.editSelect.getSelectedIndex()!=0) {
            this.selectedFestival.getFestivalDays().get(
                    this.selectFestivalDay.getSelectedIndex()).getPerformances().get(
                    this.editSelect.getSelectedIndex()).setRating(this.editRating.getSelectedIndex());
            updateTable();
        } else if (e.getSource() == this.removeBandButton) {
            bandSelectSettings(this.editSelect, 2);
        } else if (e.getSource() == this.selectFestivalDay) {
            this.selectedFestival.setFestivalName(this.festivalNameBox.getText() + " " + this.selectedFestival.getEventYear());
            this.addWindow.setTitle("Add Festival | " + setFestivalName());
            setEditPanel();
            updateTable();
        } else if (e.getSource() == this.addFriend) {
            friendSelectSettings(this.addFriend);
        } else if (e.getSource() == this.removeFriend) {
            friendSelectSettings(this.removeFriend);
        } else if (e.getSource() == this.festivalList) {
            if (this.festivalList.getSelectedIndex()==0){
                this.selectedFestival = new Festival();
                this.editPanel.removeAll();
                this.editPanel.add(new JLabel("Select a festival for more options"));
                this.editPanel.revalidate();
                this.editPanel.repaint();
                return;
            }
            String[] festivalDetails = this.festivalList.getSelectedItem().toString().split(" - ");
            try {
                Venue addVenue = new Venue(festivalDetails[0], festivalDetails[1], true);
                this.festivalDatabaseId = ReadFromDatabase.getVenueId(this.jdbcConnection, addVenue);
                setSelectedFestival();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            setSidePanel();
        }
    }

    @Override
    public void dateChanged(DateChangeEvent dateChangeEvent) {
        this.selectedFestival.setEventDay(this.festivalDate.toString());
        this.selectedFestival.updateDaysDate();
    }
}
