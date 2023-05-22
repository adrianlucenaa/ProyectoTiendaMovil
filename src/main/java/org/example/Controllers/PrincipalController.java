package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.App;

import java.io.IOException;

public class PrincipalController {
    @FXML
    private Button iphoneButton;

    @FXML
    private Button samsungButton;

    @FXML
    private Button xiaomiButton;

    @FXML
    public void switchToIphone() throws IOException {
        App.setRoot("Iphone");
    }

    @FXML
    public void switchToSamsung() throws IOException {
        App.setRoot("Samsung");
    }


    public PrincipalController() {

    }

}








