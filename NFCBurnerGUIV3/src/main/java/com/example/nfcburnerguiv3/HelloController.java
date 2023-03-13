package com.example.nfcburnerguiv3;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;


public class HelloController {
    String startmarker = "<";
    String endmarker = ">";

    SerialReader serialReader = new SerialReader();
    PortListener portListener = new PortListener();
    ErrorWarnings warning = new ErrorWarnings();

    String[] portlist = serialReader.getAvailablePortNames();
    OutputStream output;
    //String message = "<schrijf, Hello, from, arduino>";
    //String message2 = "<lees>";
    @FXML
    private Button ConnectButton;
    @FXML
    private Button BurnButton;
    @FXML
    private Button ReadButton;
    @FXML
    private TextField TagEigenaarTField;
    @FXML
    private TextField ContactPersoon1TField;
    @FXML
    private TextField TelConPer1TField;
    @FXML
    private TextField SelectedCOM;
    @FXML
    private ComboBox COMselect;
    @FXML
    private TextArea TAreaConsole;
    String SerialRevstring = "";

    public void initialize (URL url, ResourceBundle resourceBundle){
        for (String port : portlist) { //De combobox(dropdownlist) wordt gevuld met de beschibkare comporten op de computer.
            COMselect.getItems().add(port);
        }
    }
    public void OnConnectButtonClick(ActionEvent actionEvent) {
        if (COMselect.getSelectionModel().getSelectedIndex() == -1) {
            warning.NoCOMselected();


        } else {
            if (ConnectButton.getText().equals("Connect")) {

                ConnectButton.setText("Disconnect");
                int portIndex = COMselect.getSelectionModel().getSelectedIndex();
                portListener.serialString = SerialRevstring;
                serialReader.setPort(portIndex);
                serialReader.startListening(portListener);
                output = serialReader.activePort.getOutputStream();
                //input = serialReader.activeport.getInputStream();
                TAreaConsole.appendText(SerialRevstring + "\n");
                if (serialReader.activePort.openPort()) {
                    System.out.println("Connected!");
                    SelectedCOM.setText((String) COMselect.getSelectionModel().getSelectedItem());
                } else {
                    System.out.println("Failed to connect");
                    SelectedCOM.clear();
                }

            } else if (ConnectButton.getText().equals("Disconnect")) {
                serialReader.closePort();
                ConnectButton.setText("Connect");
                if (serialReader.activePort.closePort()) {
                    System.out.println("Disconnected!");
                    SelectedCOM.clear();
                } else {
                    System.out.println("Failed to disconnect");
                }
            }}}


    public void OnLoadDataButtonClick(ActionEvent actionEvent) {
    }

    public void OnReadButtonClick(ActionEvent actionEvent) {
    }

    public void OnBurnButtonClick(ActionEvent actionEvent) {
    }



    class SerialReader {

        SerialPort activePort;
        SerialPort availablePorts[] = SerialPort.getCommPorts();

        public String[] getAvailablePortNames() {
            String portNames[] = new String[availablePorts.length];
            for (int i = 0; i < availablePorts.length; i++)
                portNames[i] = availablePorts[i].getDescriptivePortName();
            return portNames;
        }

        public void setPort(int portIndex) {
            activePort = availablePorts[portIndex];
            activePort.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
            activePort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0); // block until bytes can be written

        }

        public void closePort() {
            if (activePort != null)
                activePort.closePort();
        }

        public void startListening(PortListener portListener) {
            if (activePort != null) {
                activePort.addDataListener(portListener);
                activePort.openPort();
            }
        }
    }

    class PortListener implements SerialPortDataListener {

        String serialString = "";
        //   Tile tile;

        @Override
        public int getListeningEvents() {
            // TODO Auto-generated method stub
            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
        }


        @Override
        public void serialEvent(SerialPortEvent event) {
            // TODO Auto-generated method stub
            byte buffer[] = new byte[event.getSerialPort().bytesAvailable()];
            event.getSerialPort().readBytes(buffer, buffer.length);
            String temp = new String(buffer);
            serialString = serialString.concat(temp);

            String strValue = "";
            if (serialString.indexOf(10) > 0) {
                int index = serialString.indexOf(10);
                strValue = serialString.substring(0, index + 1).trim();
                serialString = serialString.substring(index + 1);

//            if (Pattern.matches("\\d{0,2}\\.\\d{0,2}", strValue) && tile != null) {
//                tile.setValue(Double.parseDouble(strValue));
            }

            System.out.println(strValue);
        }
    }
}

 class RetrieveFromSQL {

    ErrorWarnings warning = new ErrorWarnings();
    public void /*Connection*/ connectmain() {
        //Try to connect to database
        try {
            Connection conn = null;
            String driver = "com.mysql.cj.jdbc.Driver";
            String connection = "jdbc:mysql://localhost:3306/testjdbc";//databaseurl
            String user = "root";
            String password = "";
            Class.forName(driver);
            conn = DriverManager.getConnection(connection, user, password);
            System.out.println("Database connected!");
            //return conn;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            warning.databasenotconnected();
        }

        // return null;
    }
}