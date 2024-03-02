module org.example.gymm {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires mysql.connector.j;
    requires java.sql;
    requires java.sql.rowset;
    requires jfreechart;


    opens org.example.gymm to javafx.fxml;
    exports org.example.gymm;
    exports data;
    opens data to javafx.fxml;
    exports Controller;
    opens Controller to javafx.fxml;
}