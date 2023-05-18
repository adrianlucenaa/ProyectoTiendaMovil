package org.example.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import org.example.App;

public class SecondaryController {

    @FXML
    public void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}