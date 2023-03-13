module com.example.nfcburnerguiv3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.sql;


    opens com.example.nfcburnerguiv3 to javafx.fxml;
    exports com.example.nfcburnerguiv3;
}