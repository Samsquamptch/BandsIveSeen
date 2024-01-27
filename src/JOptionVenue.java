package src;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class JOptionVenue {
    public static void addVenue(Connection conn){
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
        if (result == JOptionPane.OK_OPTION) {
            if(venueName.getText().isEmpty() || venueLocation.getText().isEmpty()) {
                System.out.println("field is empty");
                JOptionPane.showMessageDialog(null, "Field was empty, Venue not added",
                        "Input Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                try {
                    WriteDatabase.insertVenue(conn, new Venue(venueName.getText(), venueLocation.getText(),false));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
