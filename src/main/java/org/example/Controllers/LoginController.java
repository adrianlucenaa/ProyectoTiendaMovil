package org.example.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
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
    private void handleLoginButtonAction(ActionEvent event) {
        // Aquí puedes agregar la lógica para procesar el inicio de sesión
        // Puedes acceder a los valores de usuario y contraseña usando los métodos getText()
        String username = user.getText();
        String password = contraseña.getText();



        // Aquí puedes agregar la lógica para cambiar a la vista "Principal"
        try {
            // Llama al método setRoot de la clase App para cambiar la vista a "Principal"
            App.setRoot("Principal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switchToPrincipal() throws IOException {
        App.setRoot("principal");
    }

    public void switchToRegistro() throws IOException {
        App.setRoot("registro");
    }



}
