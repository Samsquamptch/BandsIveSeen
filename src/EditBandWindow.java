package src;

import src.database.DeleteFromDatabase;
import src.database.EditDatabase;
import src.database.ReadFromDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class EditBandWindow implements ActionListener {
    JComboBox<String> bandList;
    JTextField bandNameBox;
    JTextField bandGenreBox;
    JTextField bandCountryBox;
    JPanel editPanel;
    JButton deleteButton;
    JButton saveButton;
    Band selectedBand;
    int bandDatabaseId;
    CreateWindow addWindow;
    private final Connection jdbcConnection;

    public EditBandWindow(Connection connection){
        this.jdbcConnection = connection;
        this.selectedBand = new Band();
    }

    public void newWindow() {
        //Created the band selector
        String[] bandData = ReadFromDatabase.selectBands(this.jdbcConnection, false);
        this.bandList = new JComboBox<>(bandData);
        this.bandList.setPreferredSize(new Dimension(400,30));
        this.bandList.addActionListener(this);
        JPanel searchPanel = new JPanel();
        searchPanel.add(this.bandList);

        //Delete band button
        this.deleteButton = new JButton("Delete");
        this.deleteButton.setPreferredSize(new Dimension(200,30));
        this.deleteButton.addActionListener(this);
        JPanel deleteButtonPanel = CreateWindow.createPanel("Delete Band");
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

        this.editPanel = new JPanel();
        setEditPanel();

        this.addWindow = new CreateWindow("Edit Band", 500, 350);
        this.addWindow.add(searchPanel, BorderLayout.NORTH);
        this.addWindow.add(this.editPanel, BorderLayout.CENTER);
        this.addWindow.add(optionPanel, BorderLayout.SOUTH);
    }

    public void setEditPanel() {
        this.editPanel.removeAll();
        //Name field
        this.bandNameBox = new JTextField(this.selectedBand.getBandName());
        JPanel namePanel = CreateWindow.createPanel("Name");
        this.bandNameBox.setPreferredSize(new Dimension(250, 40));
        namePanel.add(this.bandNameBox);

        //Genre field
        this.bandGenreBox = new JTextField(this.selectedBand.getBandGenre());
        JPanel genrePanel = CreateWindow.createPanel("Genre");
        this.bandGenreBox.setPreferredSize(new Dimension(250, 40));
        genrePanel.add(this.bandGenreBox);

        //Country field
        this.bandCountryBox = new JTextField(this.selectedBand.getFromCountry());
        JPanel countryPanel = CreateWindow.createPanel("Country");
        this.bandCountryBox.setPreferredSize(new Dimension(250, 40));
        countryPanel.add(this.bandCountryBox);

        revalidateEditPanel();

        this.editPanel.add(namePanel);
        this.editPanel.add(genrePanel);
        this.editPanel.add(countryPanel);
        this.editPanel.revalidate();
        this.editPanel.repaint();
    }

    public void revalidateEditPanel() {
        if (this.selectedBand.getBandName().isEmpty()) {
            this.bandNameBox.setEnabled(false);
            this.bandGenreBox.setEnabled(false);
            this.bandCountryBox.setEnabled(false);
        } else {
            this.bandNameBox.setEnabled(true);
            this.bandGenreBox.setEnabled(true);
            this.bandCountryBox.setEnabled(true);
        }
    }

    public void deleteBand() throws SQLException {
        int deleteResponse = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this gig permanently?",
                "Confirm delete gig", JOptionPane.YES_NO_OPTION);
        if (deleteResponse!=0) {
            return;
        }
        if (ReadFromDatabase.checkIfUsed(this.jdbcConnection, "Headline", this.bandDatabaseId)) {
            JOptionPane.showMessageDialog(null,"Cannot delete a band which is linked to a gig or festival!",
                    "Band is in use!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DeleteFromDatabase.deleteBand(this.jdbcConnection, this.bandDatabaseId);
        JOptionPane.showMessageDialog(null, "Band has been deleted");
        this.addWindow.dispose();
    }

    public void saveBandChanges() throws SQLException {
        if (this.bandNameBox.getText().isEmpty() || this.bandCountryBox.getText().isEmpty() || this.bandGenreBox.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Please ensure no fields are empty",
                    "Fields are empty!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.selectedBand = new Band(this.bandNameBox.getText(), this.bandGenreBox.getText(),
                this.bandCountryBox.getText(), 5);
        EditDatabase.editBand(this.jdbcConnection, this.selectedBand.getBandName(), this.selectedBand.getBandGenre(),
                this.selectedBand.getFromCountry(), this.bandDatabaseId);
        JOptionPane.showMessageDialog(null, "Changes have been saved");
        this.addWindow.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == this.deleteButton || e.getSource() == this.saveButton) && this.selectedBand.getBandName().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Please select a band before choosing this option",
                    "No Band Selected!", JOptionPane.WARNING_MESSAGE);
        }
        else if (e.getSource() == this.deleteButton) {
            try { deleteBand();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == this.saveButton) {
            try { saveBandChanges();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == this.bandList && this.bandList.getSelectedIndex()!=0) {
            String[] bandDetails = this.bandList.getSelectedItem().toString().split(" - ");
            this.selectedBand = new Band(bandDetails[0], bandDetails[2], bandDetails[1], 5);
            try {
                this.bandDatabaseId = ReadFromDatabase.getBandId(this.jdbcConnection, bandDetails[0], bandDetails[1]);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            setEditPanel();
            }
        else if (e.getSource() == this.bandList) {
            this.selectedBand = new Band();
            setEditPanel();
        }
        }
}
