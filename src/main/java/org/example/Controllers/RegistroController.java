package org.example.Controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.App;

import java.io.IOException;

public class RegistroController {
    @FXML
    private TextField nametexto;

    @FXML
    private TextField telephonenumber;

    @FXML
    private TextField mailtexto ;

    @FXML
    private TextField adrres;


    @FXML
    private Button button;


    public RegistroController() {

    }

    @FXML
    public void switchToLogin() throws IOException {
        App.setRoot("login");
    }




    @FXML
    private void initialize() {

    }


    @FXML
    private void handleButtonAction() {
        // Lógica para procesar el evento del botón
        String name = nametexto.getText();
        String telephone = telephonenumber.getText();
        String email = mailtexto.getText();
        String address = adrres.getText();



        // Limpiar los campos después de procesar el evento
        nametexto.clear();
        telephonenumber.clear();
        mailtexto.clear();
        adrres.clear();
    }


}


