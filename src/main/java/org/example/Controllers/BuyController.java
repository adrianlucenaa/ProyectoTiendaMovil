package org.example.Controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.example.Model.DAO.BuysDAO;
import org.example.Model.DAO.CustomerDAO;
import org.example.Model.DAO.PhoneDAO;
import org.example.Model.Domain.Buys;
import org.example.Model.Domain.Customer;
import org.example.Model.Domain.Phone;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BuyController {
    @FXML
    private TextField searchField;

    @FXML
    private TextField searchcustomerfield;

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
    private Button UpdateButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<Phone> phonesSearched;

    @FXML
    private  ListView<Customer> searchCustomerList;



    private PhoneDAO phoneDAO;

    private CustomerDAO customerDAO;

    private BuysDAO buysDAO;
    private ObservableList<Buys> buysList;

    @FXML
    public void initialize() {
        buysDAO = new BuysDAO();
        buysList = FXCollections.observableArrayList();
        phoneDAO = new PhoneDAO();

        configureTable();
        loadBuysData();

        //configurePhoneTable();

        configureSearch();

        configureSearchCustomer();


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

    private void configureSearch(){
        this.phonesSearched.visibleProperty().set(false);
        phonesSearched.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Actualizar los campos de texto
                    if(newValue != null)
                        phoneField.setText(newValue.getPhoneName());

                }
        );

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            //esto se ejecuta cada vez que alguien escribe algo
            //buscar teléfonos que coincidan en nombre con la variable newVale
            PhoneDAO phoneDAO = new PhoneDAO();

            try {
                List<Phone> phones = phoneDAO.search(newValue);
                if(phones.size()==0 || newValue.equals((""))){
                    this.phonesSearched.visibleProperty().set(false);
                }else{
                    this.phonesSearched.visibleProperty().set(true);
                    ObservableList<Phone> phonesO = FXCollections.observableArrayList(phones);                System.out.println(phones);
                    this.phonesSearched.setItems(phonesO);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private void configureSearchCustomer() {
        this.searchCustomerList.setVisible(false);
        searchCustomerList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Actualizar los campos de texto
                    if (newValue != null) {
                        customerField.setText(newValue.getName());
                    }
                }
        );

        searchcustomerfield.textProperty().addListener((observable, oldValue, newValue) -> {
            // Esto se ejecuta cada vez que alguien escribe algo
            // Buscar clientes que coincidan en nombre con el valor newValue
            CustomerDAO customerDAO = new CustomerDAO();

            try {
                List<Customer> customers = customerDAO.searchCustomer(newValue);
                if (customers.size() == 0 || newValue.equals("")) {
                    this.searchCustomerList.setVisible(false);
                } else {
                    this.searchCustomerList.setVisible(true);
                    ObservableList<Customer> customersO = FXCollections.observableArrayList(customers);
                    this.searchCustomerList.setItems(customersO);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
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

    @FXML
    private void seleccionarElemento(MouseEvent event) {
        // Obtener el Buy seleccionado en la tabla
        Buys selectedBuy = buysTable.getSelectionModel().getSelectedItem();

        // Verificar si hay un Buy seleccionado
        if (selectedBuy != null) {
            // Obtener los datos del Buy seleccionado
            String customerName = selectedBuy.getCustomerName();
            String phoneName = selectedBuy.getPhoneName();
            double price = selectedBuy.getPrice();

            // Actualizar los campos de texto
            customerField.setText(customerName);
            phoneField.setText(phoneName);
            priceField.setText(String.valueOf(price));
        }
    }

    private void updateBuysTable(List<Phone> phonesByBrand) {
        buysList.clear();
        for (Phone phone : phonesByBrand) {
            buysList.add(new Buys(phone.getBrand(), phone.getPhoneName(), phone.getPrice()));
        }
    }

    private void loadPhonesData() {
        try {
            List<Phone> allPhones = phoneDAO.findAll();  // Obtener todos los teléfonos
            updateBuysTable(allPhones);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleUpdateButton(ActionEvent event) {
        // Obtener la compra seleccionada en la tabla
        Buys selectedBuy = buysTable.getSelectionModel().getSelectedItem();

        if (selectedBuy != null) {
            // Obtener los nuevos valores ingresados en los campos de texto
            String customerName = customerField.getText();
            String phoneName = phoneField.getText();
            double price = Double.parseDouble(priceField.getText());

            // Actualizar los valores de la compra seleccionada
            selectedBuy.setCustomerName(customerName);
            selectedBuy.setPhoneName(phoneName);
            selectedBuy.setPrice(price);

            try {
                // Actualizar la compra en la base de datos
                buysDAO.update(selectedBuy);

                // Actualizar la tabla y limpiar los campos de texto
                loadBuysData();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

