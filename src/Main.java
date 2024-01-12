package src;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Band otobokeBeaver = new Band("Otoboke Beaver", "Punk", "Japan");
        System.out.println(otobokeBeaver.getBandName());
    }
}
