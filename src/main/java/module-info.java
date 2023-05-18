module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.Controllers;
    opens org.example.Controllers to javafx.fxml;
    //exports org.example.Model;
    //opens org.example.Model to javafx.fxml;
    exports org.example.Model.Domain;
    opens org.example.Model.Domain to javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
}
