package org.example.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.example.Model.DAO.PhoneDAO;
import org.example.Model.Domain.Buys;
import org.example.Model.Domain.Phone;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PhoneController {

    @FXML
    private TableView<Phone> phoneTable;


    @FXML
    private TableColumn<Phone, String> nameColumn;

    @FXML
    private TableColumn<Phone, String> brandColumn;

    @FXML
    private TableColumn<Phone, Integer> yearColumn;

    @FXML
    private TableColumn<Phone, Double> priceColumn;



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
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

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
        String name = nameField.getText();
        String brand = brandField.getText();
        int yearRelease = Integer.parseInt(yearReleaseField.getText());
        double price = Double.parseDouble(priceField.getText());

        // Crear un nuevo objeto Phone
        Phone phone = new Phone(name, brand, yearRelease, price);

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
    void handleUpdateButton(ActionEvent event) throws SQLException {
        // Obtener el Phone seleccionado en la tabla
        Phone selectedPhone = phoneTable.getSelectionModel().getSelectedItem();

        if (selectedPhone != null) {
            // Obtener los nuevos valores ingresados en los campos de texto
            String name = nameField.getText();
            String brand = brandField.getText();
            int yearRelease = Integer.parseInt(yearReleaseField.getText());
            double price = Double.parseDouble(priceField.getText());

            // Actualizar los valores del teléfono seleccionado
            selectedPhone.setPhoneName(name);
            selectedPhone.setBrand(brand);
            selectedPhone.setYearrelease(yearRelease);
            selectedPhone.setPrice(price);

            // Actualizar el teléfono en la base de datos
            phoneDAO.update(selectedPhone);

            // Actualizar la tabla y los campos de texto
            loadPhoneData();
            clearFields();
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

                // Actualizar la tabla y los campos de texto
                loadPhoneData();
                clearFields();
            } catch (SQLException e) {
                // Manejar la excepción en caso de error al eliminar de la base de datos
                e.printStackTrace();
            }
        }
    }

    private void configureTable() {
        // Asignar las propiedades de las columnas a los atributos de la clase Phone
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearrelease"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Habilitar la selección de una sola fila a la vez en la tabla
        phoneTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void loadPhoneData() {
        try {
            // Obtener la lista de todos los Phone desde la base de datos
            List<Phone> phones = phoneDAO.findAll();

            // Limpiar la tabla y agregar los Phone obtenidos
            phoneTable.getItems().clear();
            phoneTable.getItems().addAll(phones);
        } catch (SQLException e) {
            // Manejar la excepción en caso de error al obtener los Phone desde la base de datos
            e.printStackTrace();
        }
    }

    private void clearFields() {
        // Limpiar los campos de texto
        nameField.clear();
        brandField.clear();
        yearReleaseField.clear();
        priceField.clear();
    }

    @FXML
    void handleBackButton(ActionEvent event) {
        try {
            Parent principalView = FXMLLoader.load(getClass().getResource("Principal.fxml"));
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(principalView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void seleccionarElemento(MouseEvent event) {
        // Obtener el Phone seleccionado en la tabla
        Phone selectedPhone = phoneTable.getSelectionModel().getSelectedItem();

        // Verificar si hay un Phone seleccionado
        if (selectedPhone != null) {
            // Obtener los datos del Phone seleccionado
            String name = selectedPhone.getName();
            String brand = selectedPhone.getBrand();
            int yearRelease = selectedPhone.getYearrelease();
            double price = selectedPhone.getPrice();

            // Actualizar los campos de texto
            nameField.setText(name);
            brandField.setText(brand);
            yearReleaseField.setText(String.valueOf(yearRelease));
            priceField.setText(String.valueOf(price));
        }
    }

}