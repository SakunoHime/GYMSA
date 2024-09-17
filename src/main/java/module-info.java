module com.gymsa.systems.gymsa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires jbcrypt;


    opens com.gymsa.systems.gymsa to javafx.fxml;
    exports com.gymsa.systems.gymsa;
}