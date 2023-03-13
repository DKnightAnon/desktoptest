package com.example.nfcburnerguiv3;

import javafx.scene.control.Alert;

public class ErrorWarnings {


    public void databasenotconnected() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Connection failed");
        alert.setContentText("Unable to connect to database." + "\n" + "Please check server settings and login info.");

        alert.showAndWait();
    }

    public void NoCOMselected() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No COM port selcted.");
        alert.setContentText("Please select a COM port before clicking the connect button.");

        alert.showAndWait();
    }

    public void SerialNotOpen() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Connection failed");
        alert.setContentText("The serialport of the selected COM port is not open." + "\n" + "Please click the connect button.");

        alert.showAndWait();
    }
}
