package src;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class JOptionFriend {
    public static String addFriend(Connection conn) {
        JTextField friendName = new JTextField(10);

        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("Name"));
        mainPanel.add(friendName);

        int result = JOptionPane.showConfirmDialog(null, mainPanel,
                "Please Enter New Venue", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (friendName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Field was empty, Friend not added",
                        "Input Error", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    WriteDatabase.insertFriend(conn, friendName.getText());
                    return friendName.getText();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
        return "";
    }
}