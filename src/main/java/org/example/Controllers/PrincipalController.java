package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.App;

import java.io.IOException;

public class PrincipalController {


    /**
     * Declaracion Botones
     */
    @FXML
    private Button CustomerButton;

    @FXML
    private Button PhoneButton;

    @FXML
    private Button BuysButton;

    /**
     * metodo que te dirige entre vistas
     * @throws IOException
     */

    //metodo que te dirige a la vista customer
    @FXML
    public void switchToCustomer() throws IOException {
        App.setRoot("Customer");
    }

    //metodo que te dirige a la vista Phone
    @FXML
    public void switchToPhone() throws IOException {
        App.setRoot("Phone");
    }

    //metodo que te dirige a la vista buys
    @FXML
    public void switchToBuys() throws IOException {
        App.setRoot("Buys");
    }

}








