package org.example.Controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import org.example.Model.DAO.BuysDAO;
import org.example.Model.DAO.PhoneDAO;
import org.example.Model.Domain.Buys;
import org.example.Model.Domain.Phone;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BuyController {
    @FXML
    private TextField searchField;

    @FXML
    private TableView<Buys> buysTable;

    @FXML
    private TableColumn<Buys, String> customerColumn;

    @FXML
    private TableColumn<Buys, String> phoneColumn;

    @FXML
    private TableColumn<Buys, Double> priceColumn;

    @FXML
    private TextField customerField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField priceField;

    @FXML
    private Button backButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    private PhoneDAO phoneDAO;
    private BuysDAO buysDAO;
    private ObservableList<Buys> buysList;

    @FXML
    public void initialize() {
        buysDAO = new BuysDAO();
        buysList = FXCollections.observableArrayList();
        phoneDAO = new PhoneDAO();

        configureTable();
        loadBuysData();
    }

    @FXML
    void handleAddButton() {
        String CustomerName = customerField.getText();
        String PhoneName = phoneField.getText();
        double price = Double.parseDouble(priceField.getText());

        Buys buys = new Buys(CustomerName, PhoneName, price);

        try {
            buysDAO.save(buys);
            loadBuysData();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteButton() {
        Buys selectedBuys = buysTable.getSelectionModel().getSelectedItem();

        if (selectedBuys != null) {
            try {
                buysDAO.delete(selectedBuys);
                loadBuysData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
    void handleSearchButton() {
        String searchTerm = searchField.getText().toLowerCase();

        try {
            List<Buys> searchResults = buysDAO.searchByPhoneBrand(searchTerm);
            buysList.setAll(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ...



    private void configureTable() {
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        customerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    }

    private void loadBuysData() {
        try {
            buysList = FXCollections.observableArrayList(buysDAO.findAll());
            buysTable.setItems(buysList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        customerField.clear();
        phoneField.clear();
        priceField.clear();
    }
}    /*
    @FXML
    void handleSearchButton() {
        String query = searchField.getText();

        if (query.isEmpty()) {
            // La barra de búsqueda está vacía, cargar todos los phones
            loadPhonesData();
        } else {
            try {
                // Realizar la búsqueda de phones según el criterio de búsqueda "query"
                List<Phone> searchResults = PhoneDAO.searchPhones(query);

                // Actualizar la tabla con los resultados de la búsqueda
                updatePhonesTable(searchResults);
            } catch (SQLException e) {
                // Manejar la excepción en caso de error en la búsqueda
                e.printStackTrace();
            }
        }
    }

    private void updatePhonesTable(List<Phone> phones) {
        phoneTable.getItems().clear();
        phoneTable.getItems().addAll(phones);
    }

    private void loadPhonesData() {
        try {
            List<Phone> phones = PhoneDAO.findAll();
            updatePhonesTable(phones);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    @FXML
    void handleSearchButton() {
        String query = searchField.getText();

        if (query.isEmpty()) {
            // La barra de búsqueda está vacía, cargar todas las compras
            loadBuysData();
        } else {
            try {
                // Realizar la búsqueda de compras según el criterio de búsqueda "query"
                List<Buys> searchResults = buysDAO.searchBuys(query);

                // Actualizar la tabla con los resultados de la búsqueda
                updateBuysTable(searchResults);
            } catch (SQLException e) {
                // Manejar la excepción en caso de error en la búsqueda
                e.printStackTrace();
            }
        }
    }

    @FXML
    void handleUpdateButton(ActionEvent event) throws SQLException {
        // Obtener el Phone seleccionado en la tabla
        Buys selectedBuys = buysTable.getSelectionModel().getSelectedItem();

        if (selectedBuys != null) {
            // Obtener los nuevos valores ingresados en los campos de texto
            String name = PhoneField.getText();
            String brand = CustomerField.getText();
            double price = Double.parseDouble(priceField.getText());

            // Actualizar los valores del teléfono seleccionado
            selectedBuys.setName(name);
            selectedBuys.setBrand(brand);
            selectedBuys.setPrice(price);

            // Actualizar el teléfono en la base de datos
            phoneDAO.update(selectedPhone);

            // Actualizar la tabla y los campos de texto
            loadPhoneData();
            clearFields();
        }
    }

     */


