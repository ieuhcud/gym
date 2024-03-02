package data;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public static Connection connecDb() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/gym","root", "");
            return connect;
        } catch (Exception e) {e.printStackTrace();}
        return null;
    }
    public static String username ;
}
