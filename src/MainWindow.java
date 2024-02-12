package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class MainWindow implements ActionListener {
    JButton addGigButton;
    JButton editGigButton;
    JButton delGigButton;
    JButton refreshButton;
    JTable bandTable;
    JScrollPane tableScrollPane;
    JPanel mainPanel;
    private final Connection jdbcConnection;

    public MainWindow(Connection connection){
        this.jdbcConnection = connection;
    }

    public void newUI() throws SQLException {

        this.addGigButton = new JButton();
        this.addGigButton.setText("Add New Gig");
        this.addGigButton.addActionListener(this);

        this.editGigButton = new JButton();
        this.editGigButton.setText("Edit a Gig");
        this.editGigButton.addActionListener(this);

        this.delGigButton = new JButton();
        this.delGigButton.setText("Delete a Gig");
        this.delGigButton.addActionListener(this);

        //The table
        String[] columnNames = {"Band Name", "Date", "Venue", "Rating"};
        String[][] tableData = ReadFromDatabase.selectPerformances(this.jdbcConnection);
        this.bandTable = new JTable(tableData, columnNames);
        this.tableScrollPane = new JScrollPane(bandTable);

        //Refresh button
        this.refreshButton = new JButton("Refresh");
        this.refreshButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(this.refreshButton);

        JPanel backPanel = new JPanel();
        backPanel.setBackground(Color.darkGray);
        backPanel.setLayout(new BorderLayout(5,5));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.lightGray);
        topPanel.setPreferredSize(new Dimension(100,75));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        topPanel.add(addGigButton);
        topPanel.add(editGigButton);
        topPanel.add(delGigButton);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.lightGray);
        leftPanel.setPreferredSize(new Dimension(250,100));
        leftPanel.setLayout(new BorderLayout());

        this.mainPanel = new JPanel();
        this.mainPanel.setBackground(Color.lightGray);
        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(buttonPanel, BorderLayout.NORTH);
        this.mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        MyFrame frame = new MyFrame();
        backPanel.add(topPanel,BorderLayout.NORTH);
        backPanel.add(leftPanel,BorderLayout.WEST);
        backPanel.add(this.mainPanel,BorderLayout.CENTER);
        frame.add(backPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==addGigButton) {
            new AddGigWindow(this.jdbcConnection).newWindow();
        }
        else if (e.getSource()==editGigButton) {
            new EditGigWindow(this.jdbcConnection).newWindow();
        } else if (e.getSource()==delGigButton) {
            new GigWindow("Delete Gig");
        } else if (e.getSource()==this.refreshButton) {
            System.out.println("refresh");
        }
    }
}
