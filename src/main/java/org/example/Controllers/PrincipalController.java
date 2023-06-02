package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.App;

import java.io.IOException;

public class PrincipalController {
    @FXML
    private Button CustomerButton;

    @FXML
    private Button PhoneButton;

    @FXML
    private Button BuysButton;

    @FXML
    public void switchToCustomer() throws IOException {
        App.setRoot("Customer");
    }

    @FXML
    public void switchToPhone() throws IOException {
        App.setRoot("Phone");
    }

    @FXML
    public void switchToBuys() throws IOException {
        App.setRoot("Buys");
    }

    public PrincipalController() {

    }

    public void initializeData() {
    }
}








