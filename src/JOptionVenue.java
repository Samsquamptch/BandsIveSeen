package src;

import src.database.InsertToDatabase;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class JOptionVenue {
    public static String addVenue(Connection conn){
        //The Text Fields
        JTextField venueName = new JTextField(15);
        JTextField venueLocation = new JTextField(15);

        //The Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("Name"));
        mainPanel.add(venueName);
        mainPanel.add(Box.createHorizontalStrut(5));
        mainPanel.add(new JLabel("Location"));
        mainPanel.add(venueLocation);

        int result = JOptionPane.showConfirmDialog(null, mainPanel,
                "Please Enter New Venue", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return "";
        }
        else if(venueName.getText().isEmpty() || venueLocation.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Field was empty, Venue not added",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return "";
        }
        else {
            try {
                InsertToDatabase.insertVenue(conn, new Venue(venueName.getText(), venueLocation.getText(),false));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return venueName.getText() + " - " + venueLocation.getText();
    }
}
