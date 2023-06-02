module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
    requires junit;
    requires org.junit.jupiter.api;

    opens org.example to javafx.fxml;
    opens org.example.Controllers to javafx.fxml;
    opens org.example.Model.Domain to javafx.fxml,javafx.base;
    opens org.example.Model.Connections to java.xml.bind;

    exports org.example;
}
