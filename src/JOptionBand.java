package src;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class JOptionBand {
    public static void addBand(Connection conn) {
        JTextField bandName = new JTextField(15);
        JTextField bandGenre = new JTextField(15);
        JTextField bandCountry = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Name"));
        myPanel.add(bandName);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("Genre"));
        myPanel.add(bandGenre);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("Country"));
        myPanel.add(bandCountry);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
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
