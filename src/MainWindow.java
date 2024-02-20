package src;

import src.database.ReadFromDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class MainWindow implements ActionListener {
    JComboBox<String> selectGigOptions;
    JComboBox<String> selectOtherOptions;
    JComboBox<String> selectFestivalOptions;
    JButton refreshButton;
    JTable bandTable;
    JPanel mainPanel;
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
        JPanel gigOptionsPanel = MyFrame.createPanel("Gig Settings");
        gigOptionsPanel.add(selectGigOptions, BorderLayout.CENTER);

        //Select festival options
        this.selectFestivalOptions = new JComboBox<>(makeJComboBoxArray("Festival"));
        this.selectFestivalOptions.addActionListener(this);
        JPanel festivalOptionsPanel = MyFrame.createPanel("Festival Settings");
        festivalOptionsPanel.add(selectFestivalOptions, BorderLayout.CENTER);

        //Select general options
        String[] otherOptionsArray = {"Select", "Friends", "Bands", "Venues"};
        this.selectOtherOptions = new JComboBox<>(otherOptionsArray);
        this.selectOtherOptions.addActionListener(this);
        JPanel otherOptionsPanel = MyFrame.createPanel("Edit");
        otherOptionsPanel.add(selectOtherOptions, BorderLayout.CENTER);

        //Refresh button
        this.refreshButton = new JButton("🔄");
        this.refreshButton.addActionListener(this);
        JPanel refreshPanel = MyFrame.createPanel("Refresh Table");
        refreshPanel.add(this.refreshButton);

        //The table
        String[] columnNames = {"Artist", "Date", "Venue", "Rating"};
        String[][] tableData = ReadFromDatabase.selectPerformances(this.jdbcConnection);
        this.bandTable = new JTable(tableData, columnNames);
        this.bandTable.setAutoCreateRowSorter(true);
        JScrollPane tableScrollPane = new JScrollPane(bandTable);

        JPanel backPanel = new JPanel();
        backPanel.setBackground(Color.darkGray);
        backPanel.setLayout(new BorderLayout(5,5));

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

        this.mainPanel = new JPanel();
        this.mainPanel.setBackground(Color.lightGray);
        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        MyFrame frame = new MyFrame();
        backPanel.add(topPanel,BorderLayout.NORTH);
        backPanel.add(leftPanel,BorderLayout.WEST);
        backPanel.add(this.mainPanel,BorderLayout.CENTER);
        frame.add(backPanel);
    }

    public void updateTable() {
        try {
            String[] columnNames = {"Artist", "Date", "Venue", "Rating"};
            String[][] tableData = ReadFromDatabase.selectPerformances(this.jdbcConnection);
            DefaultTableModel model = new DefaultTableModel(tableData, columnNames);
            this.bandTable.setModel(model);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectOtherOptions) {
            switch (this.selectOtherOptions.getSelectedIndex()) {
                case 0 -> {
                }
                case 1 -> {
                    System.out.println("Friends");
                    this.selectOtherOptions.setSelectedIndex(0);
                }
                case 2 -> {
                    System.out.println("Band");
                    this.selectOtherOptions.setSelectedIndex(0);
                }
                case 3 -> {
                    System.out.println("Venue");
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
            System.out.println("Work in progress");
            this.selectFestivalOptions.setSelectedIndex(0);
        }
        else if (e.getSource() == this.refreshButton) {
            updateTable();
        }
    }
}
