package org.example.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import org.example.App;

public class PrimaryController {

    @FXML
    public void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
