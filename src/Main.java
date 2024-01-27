package src;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseConnector.connect();
        Gui userInterface = new Gui(conn);
        userInterface.newUI();
    }
}
