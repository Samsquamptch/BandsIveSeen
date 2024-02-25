package src;

import com.github.lgooddatepicker.components.DatePicker;
import src.database.ReadFromDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class MainWindow implements ActionListener {
    JComboBox<String> selectGigOptions;
    JComboBox<String> selectOtherOptions;
    JComboBox<String> selectFestivalOptions;
    JComboBox<String> bandSelect;
    JComboBox<String> venueSelect;
    JComboBox<String> friendSelect;
    DatePicker startDate;
    DatePicker endDate;
    JRadioButton allButton;
    JRadioButton gigButton;
    JRadioButton festivalButton;
    ButtonGroup filterButtons;
    JButton refreshButton;
    JButton filterButton;
    JTable bandTable;
    JPanel mainPanel;
    String filterString;
    private final Connection jdbcConnection;

    public MainWindow(Connection connection){
        this.jdbcConnection = connection;
    }

    public String[] makeJComboBoxArray(String subject) {
        String[] subjectArray = new String[3];
        subjectArray[0] = "Select";
        subjectArray[1] = "Add a " + subject;
        subjectArray[2] = "Edit/Delete a " + subject;
        return subjectArray;
    }

    public void newUI() throws SQLException {

        //Select gig options
        this.selectGigOptions = new JComboBox<>(makeJComboBoxArray("Gig"));
        this.selectGigOptions.addActionListener(this);
        JPanel gigOptionsPanel = CreateWindow.createPanel("Gig Settings");
        gigOptionsPanel.add(selectGigOptions, BorderLayout.CENTER);

        //Select festival options
        this.selectFestivalOptions = new JComboBox<>(makeJComboBoxArray("Festival"));
        this.selectFestivalOptions.addActionListener(this);
        JPanel festivalOptionsPanel = CreateWindow.createPanel("Festival Settings");
        festivalOptionsPanel.add(selectFestivalOptions, BorderLayout.CENTER);

        //Select general options
        String[] otherOptionsArray = {"Select", "Friends", "Bands", "Venues"};
        this.selectOtherOptions = new JComboBox<>(otherOptionsArray);
        this.selectOtherOptions.addActionListener(this);
        JPanel otherOptionsPanel = CreateWindow.createPanel("Edit");
        otherOptionsPanel.add(selectOtherOptions, BorderLayout.CENTER);

        //Refresh button
        this.refreshButton = new JButton("🔄");
        this.refreshButton.addActionListener(this);
        JPanel refreshPanel = CreateWindow.createPanel("Refresh Table");
        refreshPanel.add(this.refreshButton);

        //Start date
        this.startDate = new DatePicker();
        this.startDate.setPreferredSize(new Dimension(200, 30));
        JPanel startDatePanel = CreateWindow.createPanel("From");
        startDatePanel.add(this.startDate);

        //End date
        this.endDate = new DatePicker();
        this.endDate.setPreferredSize(new Dimension(200, 30));
        JPanel endDatePanel = CreateWindow.createPanel("To");
        endDatePanel.add(this.endDate);

        //Band Select
        String[] bandData = ReadFromDatabase.selectBands(this.jdbcConnection, false);
        this.bandSelect = new JComboBox<>(bandData);
        this.bandSelect.setPreferredSize(new Dimension(200, 30));
        JPanel filterBandPanel = CreateWindow.createPanel("Band");
        filterBandPanel.add(this.bandSelect);

        //VenueSelect
        String[] venueData = ReadFromDatabase.selectAllVenues(this.jdbcConnection);
        this.venueSelect = new JComboBox<>(venueData);
        this.venueSelect.setPreferredSize(new Dimension(200, 30));
        JPanel filterVenuePanel = CreateWindow.createPanel("Venue");
        filterVenuePanel.add(this.venueSelect);

        //FriendSelect
        String[] friendData = ReadFromDatabase.selectFriends(this.jdbcConnection, new String[0], false);
        this.friendSelect = new JComboBox<>(friendData);
        this.friendSelect.setPreferredSize(new Dimension(200, 30));
        JPanel filterFriendPanel = CreateWindow.createPanel("Friend");
        filterFriendPanel.add(this.friendSelect);

        //Filter Button
        this.filterButton = new JButton("Update");
        this.filterButton.addActionListener(this);
        this.filterString = "";

        //The table
        String[] columnNames = {"Artist", "Date", "Venue", "Rating"};
        String[][] tableData = ReadFromDatabase.selectPerformances(this.jdbcConnection, "");
        this.bandTable = new JTable(tableData, columnNames);
        this.bandTable.setAutoCreateRowSorter(true);
        JScrollPane tableScrollPane = new JScrollPane(bandTable);

        //Table filters
        this.allButton = new JRadioButton("All");
        this.gigButton = new JRadioButton("Gigs Only");
        this.festivalButton = new JRadioButton("Festivals Only");
        this.allButton.addActionListener(this);
        this.gigButton.addActionListener(this);
        this.festivalButton.addActionListener(this);

        this.filterButtons = new ButtonGroup();
        this.filterButtons.add(this.allButton);
        this.filterButtons.add(this.gigButton);
        this.filterButtons.add(this.festivalButton);

        JLabel filterLabel = new JLabel("Filter Table Settings", SwingConstants.CENTER);
        JPanel optionsPanel = new JPanel();

        JPanel backPanel = new JPanel();
        backPanel.setBackground(Color.darkGray);
        backPanel.setLayout(new BorderLayout(2,2));

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(100,75));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        topPanel.add(gigOptionsPanel);
        topPanel.add(festivalOptionsPanel);
        topPanel.add(otherOptionsPanel);
        topPanel.add(refreshPanel);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(250,100));
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(filterLabel, BorderLayout.NORTH);
        leftPanel.add(optionsPanel, BorderLayout.CENTER);

        optionsPanel.add(allButton);
        optionsPanel.add(gigButton);
        optionsPanel.add(festivalButton);
        optionsPanel.add(startDatePanel);
        optionsPanel.add(endDatePanel);
        optionsPanel.add(filterBandPanel);
        optionsPanel.add(filterVenuePanel);
        optionsPanel.add(filterFriendPanel);
        optionsPanel.add(filterButton);

        this.mainPanel = new JPanel();
        this.mainPanel.setBackground(Color.lightGray);
        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        CreateWindow frame = new CreateWindow();
        backPanel.add(topPanel,BorderLayout.NORTH);
        backPanel.add(leftPanel,BorderLayout.WEST);
        backPanel.add(this.mainPanel,BorderLayout.CENTER);
        frame.add(backPanel);
    }

    public void updateTable(String inputString) {
        try {
            String[] columnNames = {"Artist", "Date", "Venue", "Rating"};
            String[][] tableData = ReadFromDatabase.selectPerformances(this.jdbcConnection, inputString);
            if (this.bandSelect.getSelectedIndex()!=0) {
                String[] bandDetails = Objects.requireNonNull(this.bandSelect.getSelectedItem()).toString().split(" - ");
                tableData = filterDetails(tableData, 0, bandDetails[0]);
            }
            if (this.venueSelect.getSelectedIndex()!=0 && tableData.length >= 1) {
                String[] venueDetails = Objects.requireNonNull(this.venueSelect.getSelectedItem()).toString().split(" - ");
                tableData = filterDetails(tableData, 2, venueDetails[0] + " - " + venueDetails[1]);
            }
            if (this.friendSelect.getSelectedIndex()!=0) {
                tableData = filterFriends(tableData, Objects.requireNonNull(this.friendSelect.getSelectedItem()).toString());
            }
            if (!this.startDate.getText().isEmpty() || !this.endDate.getText().isEmpty()) {
                tableData = filterDates(tableData);
            }
            DefaultTableModel model = new DefaultTableModel(tableData, columnNames);
            this.bandTable.setModel(model);
            this.bandTable.revalidate();
            this.bandTable.repaint();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String[][] filterFriends(String[][] tableData, String friendName) throws SQLException {
        ArrayList<Integer> friendMatches = new ArrayList<>();
        for (int i = 0; i < tableData.length; i++) {
            if (tableData[i][4] == null) {
                continue;
            }
            String friendString = ReadFromDatabase.getGigFriendString(this.jdbcConnection, Integer.parseInt(tableData[i][4]));
            if (friendString != null && friendString.contains(friendName)) {
                friendMatches.add(i);
            }
        }
        String[][] friendTable = new String[friendMatches.size()][5];
        for (int i = 0; i < friendMatches.size(); i++) {
            friendTable[i] = tableData[friendMatches.get(i)];
        }
        return friendTable;
    }

    public String[][] filterDetails(String[][] tableData, int position, String checkString) {
        ArrayList<Integer> detailMatches = new ArrayList<>();
        for (int i = 0; i < tableData.length; i++) {
            if (tableData[i][position] != null && tableData[i][position].contains(checkString)) {
                detailMatches.add(i);
            }
        }
        String[][] detailTable = new String[detailMatches.size()][5];
        for (int i = 0; i < detailMatches.size(); i++) {
            detailTable[i] = tableData[detailMatches.get(i)];
        }
        return detailTable;
    }

    public String[][] filterDates(String[][] tableData) {
        if (this.startDate.getText().isEmpty()) {
            this.startDate.setDate(LocalDate.parse("1900-01-01"));
        }
        if (this.endDate.getText().isEmpty()) {
            this.endDate.setDateToToday();
        }
        ArrayList<Integer> dateMatches = new ArrayList<>();
        for (int i = 0; i < tableData.length; i++) {
            if (tableData[i][1] != null && LocalDate.parse(tableData[i][1]).isAfter(this.startDate.getDate()) &&
                    LocalDate.parse(tableData[i][1]).isBefore(this.endDate.getDate())) {
                dateMatches.add(i);
            }
        }
        String[][] dateTable = new String[dateMatches.size()][5];
        for (int i = 0; i < dateMatches.size(); i++) {
            dateTable[i] = tableData[dateMatches.get(i)];
        }
        return dateTable;
    }

    public void revalidateVenueSelect() {
        this.venueSelect.removeAllItems();
        String[] venueData;
        if (this.filterString.contains("0")) {
            venueData = ReadFromDatabase.selectVenues(this.jdbcConnection, false);
        } else if (this.filterString.contains("1")) {
            venueData = ReadFromDatabase.selectFestival(this.jdbcConnection);
        } else {
            venueData = ReadFromDatabase.selectAllVenues(this.jdbcConnection);
        }
        for (String item : venueData) {
            this.venueSelect.addItem(item);
        }
        this.venueSelect.revalidate();
        this.venueSelect.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectOtherOptions) {
            switch (this.selectOtherOptions.getSelectedIndex()) {
                case 0 -> {
                }
                case 1 -> {
                    new EditFriendWindow(this.jdbcConnection).newWindow();
                    this.selectOtherOptions.setSelectedIndex(0);
                }
                case 2 -> {
                    new EditBandWindow(this.jdbcConnection).newWindow();
                    this.selectOtherOptions.setSelectedIndex(0);
                }
                case 3 -> {
                    new EditVenueWindow(this.jdbcConnection).newWindow();
                    this.selectOtherOptions.setSelectedIndex(0);
                }
            }
        }
        else if (e.getSource() == selectGigOptions) {
            switch (this.selectGigOptions.getSelectedIndex()) {
                case 0 -> {
                }
                case 1 -> {
                    new AddGigWindow(this.jdbcConnection).newWindow();
                    this.selectGigOptions.setSelectedIndex(0);
                }
                case 2 -> {
                    new EditGigWindow(this.jdbcConnection).newWindow();
                    this.selectGigOptions.setSelectedIndex(0);
                }
        }
        } else if (e.getSource() == this.selectFestivalOptions) {
            switch (this.selectFestivalOptions.getSelectedIndex()) {
                case 0 -> {
                }
                case 1 -> {
                    new AddFestivalWindow(this.jdbcConnection).newWindow();
                    this.selectGigOptions.setSelectedIndex(0);
                }
                case 2 -> {
                    new EditFestivalWindow(this.jdbcConnection).newWindow();
                    this.selectGigOptions.setSelectedIndex(0);
                }
            }
            this.selectFestivalOptions.setSelectedIndex(0);
        } else if (e.getSource() == this.refreshButton) {
            this.startDate.clear();
            this.endDate.clear();
            this.bandSelect.setSelectedIndex(0);
            this.venueSelect.setSelectedIndex(0);
            this.friendSelect.setSelectedIndex(0);
            updateTable("");
            this.filterButtons.clearSelection();
        } else if (e.getSource() == this.allButton) {
            this.filterString = "";
            revalidateVenueSelect();
        } else if (e.getSource() == this.gigButton) {
            this.filterString = "WHERE Venue.isFestival = 0 ";
            revalidateVenueSelect();
        } else if (e.getSource() == this.festivalButton) {
            this.filterString = "WHERE Venue.isFestival = 1 ";
            revalidateVenueSelect();
        } else if (e.getSource() == this.filterButton) {
            updateTable(this.filterString);
        }
    }
}
