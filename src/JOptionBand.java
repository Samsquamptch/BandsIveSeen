package src;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class JOptionBand {
    public static void addBand(Connection conn) {
        //The Text Fields
        JTextField bandName = new JTextField(15);
        JTextField bandGenre = new JTextField(15);
        JTextField bandCountry = new JTextField(10);

        //The Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("Name"));
        mainPanel.add(bandName);
        mainPanel.add(Box.createHorizontalStrut(5));
        mainPanel.add(new JLabel("Genre"));
        mainPanel.add(bandGenre);
        mainPanel.add(Box.createHorizontalStrut(5));
        mainPanel.add(new JLabel("Country"));
        mainPanel.add(bandCountry);

        int result = JOptionPane.showConfirmDialog(null, mainPanel,
                "Please Enter Band Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if(bandName.getText().isEmpty() || bandGenre.getText().isEmpty() ||
                    bandCountry.getText().isEmpty()) {
                System.out.println("field is empty");
                JOptionPane.showMessageDialog(null, "Field was empty, band not added",
                        "Input Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                try {
                    WriteDatabase.addBand(conn, bandName.getText(),
                            bandGenre.getText(), bandCountry.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
