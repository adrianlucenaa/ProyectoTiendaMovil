package org.example.Controllers;

import javafx.collections.FXCollections;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import org.example.Model.DAO.CustomerDAO;
import org.example.Model.Domain.Customer;
import org.example.Model.Domain.Phone;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerController {

    // Declaración de textfield y otro elemento.

    @FXML
    private TableView<Customer> customerTable;


    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> mailColumn;

    @FXML
    private TableColumn<Customer, String> numberColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;


    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField addressField;

    //Los botones

    @FXML
    private Button addButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    //Referencia De Mi DAO

    private CustomerDAO customerDAO;



    public CustomerController() {
        customerDAO = new CustomerDAO();
    }

    //Inicializa los metodos
    @FXML
    public void initialize() {
        // Configurar la tabla y cargar los datos de los Customer existentes en ella
        configureTable();
        loadCustomerData();
    }

    //Logica Para Añadir Un elemento a la tabla
    @FXML
    void handleAddButton(ActionEvent event) {
        // Obtener los valores ingresados en los campos de texto
        String name = nameField.getText();
        String number = numberField.getText();
        String mail = mailField.getText();
        String address = addressField.getText();

        // Crear un nuevo objeto Customer
        Customer customer = new Customer();
        customer.setName(name);
        customer.setNumber(number);
        customer.setMail(mail);
        customer.setAddress(address);

        try {
            // Guardar el nuevo Customer en la base de datos
            customerDAO.save(customer);

            // Actualizar la tabla y los campos de texto
            loadCustomerData();
            clearFields();
        } catch (SQLException e) {
            // Manejar la excepción en caso de error al guardar en la base de datos
            e.printStackTrace();
        }
    }

    //Metodo para eliminar un campo de la tabla
    @FXML
    void handleDeleteButton(ActionEvent event) {
        // Obtener el Customer seleccionado en la tabla
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            try {
                // Eliminar el Customer de la base de datos
                customerDAO.delete(selectedCustomer);

                // Actualizar la tabla
                loadCustomerData();
            } catch (SQLException e) {
                // Manejar la excepción en caso de error al eliminar de la base de datos
                e.printStackTrace();
            }
        }
    }

    //Configuracion de la tabla
    private void configureTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    //Cargar los customer y sus datos en la tabla
    private void loadCustomerData() {
        try {
            List<Customer> customers = customerDAO.findAll();
            customerTable.setItems(FXCollections.observableArrayList(customers));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo para limpiar los text field
    private void clearFields() {
        // Limpiar los campos de texto después de agregar un Customer
        nameField.clear();
        numberField.clear();
        mailField.clear();
        addressField.clear();
    }

    //metodo para actualizar un campo de la tabla
    @FXML
    void handleUpdateButton(ActionEvent event) throws SQLException {
        // Obtener el Customer seleccionado en la tabla
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            // Obtener los nuevos valores ingresados en los campos de texto
            String name = nameField.getText();
            String number = numberField.getText();
            String email = mailField.getText();
            String address = addressField.getText();

            // Actualizar los valores del Customer seleccionado
            selectedCustomer.setName(name);
            selectedCustomer.setNumber(number);
            selectedCustomer.setMail(email);
            selectedCustomer.setAddress(address);

            // Actualizar el Customer en la base de datos
            customerDAO.update(selectedCustomer);

            // Actualizar la tabla y los campos de texto
            loadCustomerData();
            clearFields();
        }
    }

    //logica para el boton de volver atras
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

    //metodo para seleccionar un elemento de la tabla
    @FXML
    private void seleccionarElemento(MouseEvent event) {
        // Obtener el Customer seleccionado en la tabla
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        // Verificar si hay un Customer seleccionado
        if (selectedCustomer != null) {
            // Obtener los datos del Customer seleccionado
            String name = selectedCustomer.getName();
            String number = selectedCustomer.getNumber();
            String mail = selectedCustomer.getMail();
            String address = selectedCustomer.getAddress();

            // Actualizar los campos de texto y rellenarlos
            nameField.setText(name);
            numberField.setText(number);
            mailField.setText(mail);
            addressField.setText(address);
        }
    }

}
