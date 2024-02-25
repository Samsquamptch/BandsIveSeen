package src;

import src.database.DatabaseConnector;
import src.database.EditDatabase;
import src.guiWindow.MainWindow;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        File database = new File("bisDatabase.db");
        if(!database.exists()) {
            Connection conn = DatabaseConnector.connect();
            DatabaseConnector.buildDb(conn);
        }
        Connection conn = DatabaseConnector.connect();
        EditDatabase.setPragma(conn);
        MainWindow userInterface = new MainWindow(conn);
        userInterface.newUI();
    }
}
