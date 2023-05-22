package org.example.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.App;

public class LoginController {

    @FXML
    private TextField user;

    @FXML
    private PasswordField contraseña;


    @FXML
    public void switchToPrincipal() throws IOException {
        App.setRoot("principal");
    }

    public void switchToRegistro() throws IOException {
        App.setRoot("registro");
    }



}
