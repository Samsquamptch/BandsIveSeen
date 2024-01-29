package src;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection conn = DatabaseConnector.connect();
        MainWindow userInterface = new MainWindow(conn);
        userInterface.newUI();
    }
}
