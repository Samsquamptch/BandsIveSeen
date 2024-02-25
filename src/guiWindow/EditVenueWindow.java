package src.guiWindow;

import src.database.DeleteFromDatabase;
import src.database.EditDatabase;
import src.database.ReadFromDatabase;
import src.eventObjects.Venue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class EditVenueWindow implements ActionListener {
    JComboBox<String> venueList;
    JTextField venueNameBox;
    JTextField venueLocationBox;
    JPanel editPanel;
    JButton deleteButton;
    JButton saveButton;
    Venue selectedVenue;
    int venueDatabaseId;
    CreateWindow addWindow;
    private final Connection jdbcConnection;

    public EditVenueWindow(Connection connection){
        this.jdbcConnection = connection;
        this.selectedVenue = new Venue();
    }

    public void newWindow() {
        //Created the friend selector
        String[] venueData = ReadFromDatabase.selectVenues(this.jdbcConnection, false);
        this.venueList = new JComboBox<>(venueData);
        this.venueList.setPreferredSize(new Dimension(400,30));
        this.venueList.addActionListener(this);
        JPanel searchPanel = new JPanel();
        searchPanel.add(this.venueList);

        //Delete band button
        this.deleteButton = new JButton("Delete");
        this.deleteButton.setPreferredSize(new Dimension(200,30));
        this.deleteButton.addActionListener(this);
        JPanel deleteButtonPanel = CreateWindow.createPanel("Delete Friend");
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

        this.addWindow = new CreateWindow("Edit Band", 500, 300);
        this.addWindow.add(searchPanel, BorderLayout.NORTH);
        this.addWindow.add(this.editPanel, BorderLayout.CENTER);
        this.addWindow.add(optionPanel, BorderLayout.SOUTH);
    }

    public void setEditPanel() {
        this.editPanel.removeAll();
        //Name field
        this.venueNameBox = new JTextField(this.selectedVenue.getVenueName());
        JPanel namePanel = CreateWindow.createPanel("Name");
        this.venueNameBox.setPreferredSize(new Dimension(250, 40));
        namePanel.add(this.venueNameBox);

        //Location field
        this.venueLocationBox = new JTextField(this.selectedVenue.getVenueLocation());
        JPanel locationPanel = CreateWindow.createPanel("Location");
        this.venueLocationBox.setPreferredSize(new Dimension(250, 40));
        locationPanel.add(this.venueLocationBox);

        revalidateEditPanel();

        this.editPanel.add(namePanel);
        this.editPanel.add(locationPanel);
        this.editPanel.revalidate();
        this.editPanel.repaint();
    }

    public void revalidateEditPanel() {
        if (this.selectedVenue.getVenueName().isEmpty()) {
            this.venueNameBox.setEnabled(false);
            this.venueLocationBox.setEnabled(false);
        } else {
            this.venueNameBox.setEnabled(true);
            this.venueLocationBox.setEnabled(true);
        }
    }

    public void deleteVenue() throws SQLException {
        int deleteResponse = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this venue permanently?",
                "Confirm delete venue", JOptionPane.YES_NO_OPTION);
        if (deleteResponse!=0) {
            return;
        }
        if (ReadFromDatabase.checkIfUsed(this.jdbcConnection, "Venue_Id", this.venueDatabaseId)) {
            JOptionPane.showMessageDialog(null,"Cannot delete a Venue which is linked to a gig or festival!",
                    "Venue is in use!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DeleteFromDatabase.deleteVenue(this.jdbcConnection, this.venueDatabaseId);
        JOptionPane.showMessageDialog(null, "Venue has been deleted");
        this.addWindow.dispose();
    }

    public void saveVenueChanges() throws SQLException {
        if (this.venueNameBox.getText().isEmpty() || this.venueLocationBox.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Please ensure no fields are empty",
                    "Fields are empty!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.selectedVenue = new Venue(this.venueNameBox.getText(), this.venueLocationBox.getText(), false);
        EditDatabase.editVenue(this.jdbcConnection, this.selectedVenue, this.venueDatabaseId);
        JOptionPane.showMessageDialog(null, "Changes have been saved");
        this.addWindow.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == this.deleteButton || e.getSource() == this.saveButton) && this.selectedVenue.getVenueName().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Please select a venue before choosing this option",
                    "No Venue Selected!", JOptionPane.WARNING_MESSAGE);
        }
        else if (e.getSource() == this.deleteButton) {
            try { deleteVenue();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == this.saveButton) {
            try { saveVenueChanges();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == this.venueList && this.venueList.getSelectedIndex()!=0) {
            String[] venueDetails = Objects.requireNonNull(this.venueList.getSelectedItem()).toString().split(" - ");
            this.selectedVenue = new Venue(venueDetails[0], venueDetails[1], false);
            try {
                this.venueDatabaseId = ReadFromDatabase.getVenueId(this.jdbcConnection, this.selectedVenue);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            setEditPanel();
            }
        else if (e.getSource() == this.venueList) {
            this.selectedVenue = new Venue();
            setEditPanel();
        }
        }
}
