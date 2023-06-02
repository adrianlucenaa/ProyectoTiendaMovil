package org.example.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Line;
import org.example.Model.DAO.PhoneDAO;
import org.example.Model.Domain.Phone;

import java.sql.SQLException;
import java.util.List;

public class PhoneController {

    @FXML
    private TableView<Phone> phoneTable;

    @FXML
    private TableColumn<Line, Integer> idColumn;

    @FXML
    private TableColumn<Line, String> nameColumn;

    @FXML
    private TableColumn<Line, String> brandColumn;

    @FXML
    private TableColumn<Line, String> yearColumn;

    @FXML
    private TableColumn<Line, Double> priceColumn;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField brandField;

    @FXML
    private TextField yearReleaseField;

    @FXML
    private TextField priceField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    private PhoneDAO phoneDAO;

    public PhoneController() {
        phoneDAO = new PhoneDAO();
    }

    @FXML
    public void initialize() {
        // Configurar la tabla y cargar los datos de los Phone existentes en ella
        configureTable();
        loadPhoneData();
    }

    @FXML
    void handleAddButton(ActionEvent event) {
        // Obtener los valores ingresados en los campos de texto
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String brand = brandField.getText();
        String yearRelease = yearReleaseField.getText();
        double price = Double.parseDouble(priceField.getText());

        // Crear un nuevo objeto Phone
        Phone phone = new Phone();
        phone.setIdPhone(id);
        phone.setName(name);
        phone.setBrand(brand);
        phone.setYearrelease(yearRelease);
        phone.setPrice(price);

        try {
            // Guardar el nuevo Phone en la base de datos
            phoneDAO.save(phone);

            // Actualizar la tabla y los campos de texto
            loadPhoneData();
            clearFields();
        } catch (SQLException e) {
            // Manejar la excepción en caso de error al guardar en la base de datos
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteButton(ActionEvent event) {
        // Obtener el Phone seleccionado en la tabla
        Phone selectedPhone = phoneTable.getSelectionModel().getSelectedItem();

        if (selectedPhone != null) {
            try {
                // Eliminar el Phone de la base de datos
                phoneDAO.delete(selectedPhone);

                // Actualizar la tabla
                loadPhoneData();
            } catch (SQLException e) {
                // Manejar la excepción en caso de error al eliminar de la base de datos
                e.printStackTrace();
            }
        }
    }

    private void configureTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idPhone"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearrelease"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadPhoneData() {
        try {
            List<Phone> phones = phoneDAO.findAll();
            phoneTable.setItems(FXCollections.observableArrayList(phones));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void clearFields() {
        // Limpiar los campos de texto después de agregar un Phone
        idField.clear();
        nameField.clear();
        brandField.clear();
        yearReleaseField.clear();
        priceField.clear();
    }

    // Otros métodos y lógica de la interfaz de usuario...

}
