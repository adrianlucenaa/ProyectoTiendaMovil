package org.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import org.example.Model.DAO.CustomerDAO;
import org.example.Model.Domain.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerController {

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> idColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> mailColumn;

    @FXML
    private TableColumn<Customer, String> numberColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;
    //Este es el comentario de prueba
    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField addressField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    private CustomerDAO customerDAO;

    public CustomerController() {
        customerDAO = new CustomerDAO();
    }

    @FXML
    public void initialize() {
        // Configurar la tabla y cargar los datos de los Customer existentes en ella
        configureTable();
        loadCustomerData();
    }

    @FXML
    void handleAddButton(ActionEvent event) {
        // Obtener los valores ingresados en los campos de texto
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String number = numberField.getText();
        String mail = mailField.getText();
        String address = addressField.getText();

        // Crear un nuevo objeto Customer
        Customer customer = new Customer();
        customer.setIdCustomer(id);
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

    private void configureTable() {
        // Configurar las columnas de la tabla y asignar los valores correspondientes a cada columna
        // ...
    }

    private void loadCustomerData() {
        try {
            // Obtener la lista de Customers desde la base de datos
            List<Customer> customers = customerDAO.findAll();

            // Actualizar los datos de la tabla con la lista de Customers
            // ...
        } catch (SQLException e) {
            // Manejar la excepción en caso de error al obtener los datos de la base de datos
            e.printStackTrace();
        }
    }

    private void clearFields() {
        // Limpiar los campos de texto después de agregar un Customer
        idField.clear();
        nameField.clear();
        numberField.clear();
        mailField.clear();
        addressField.clear();
    }

    // Otros métodos y lógica de la interfaz de usuario...

}
