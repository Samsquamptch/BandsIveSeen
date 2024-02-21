package src;

import java.sql.Connection;

public class EditFestivalWindow {
    CreateWindow addWindow;
    private final Connection jdbcConnection;

    public EditFestivalWindow(Connection conn) {
        this.jdbcConnection = conn;
    }

    public void newWindow(){
        this.addWindow = new CreateWindow("Edit Festival", 1000, 600);
    }
}
