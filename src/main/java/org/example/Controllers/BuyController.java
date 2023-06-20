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
    private TableView<Phone> tableView;

    @FXML
    private TableColumn<Phone, String> brandColumn;

    @FXML
    private TableColumn<Phone, String> PhoneNameColumn;

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

    @FXML
    private ListView<Phone> phonesSearched;

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

        //configurePhoneTable();

        configureSearch();


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
    public void handleSearchButton() {
        String brand = searchField.getText(); // Obtener el texto de búsqueda desde un campo de texto

        // Realizar la búsqueda en la base de datos utilizando el PhoneDAO
        try {
            List<Phone> phones = phoneDAO.search(brand);
            tableView.getItems().clear(); // Limpiar los elementos existentes en la tabla
            tableView.getItems().addAll(phones); // Agregar los teléfonos encontrados a la tabla
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configurePhoneTable() {
        // Configurar columnas personalizadas para el TableView de Phone
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        PhoneNameColumn.setCellValueFactory(new PropertyValueFactory<>("phoneName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        // Agrega más columnas personalizadas según tus necesidades

        // Configurar el TableView de Phone
        tableView.setEditable(false); // Configurar si se permite editar las celdas
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Configurar política de redimensionamiento de columnas
    }


    /*
    @FXML
    void handleSearchButton() {
        String query = searchField.getText();

        if (query.isEmpty()) {
            loadPhonesData(); // Cargar todos los teléfonos si la búsqueda está vacía
        } else {
            try {
                PhoneDAO phoneDAO = new PhoneDAO(); // Crear una instancia de PhoneDAO
                List<Phone> searchResults = phoneDAO.searchByBrand(query);
                showSearchResults(searchResults);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void showSearchResults(List<Phone> searchResults) {
        if (searchResults.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Búsqueda de Teléfono");
            alert.setHeaderText("No se encontraron resultados.");
            alert.setContentText("No se encontraron teléfonos que coincidan con la marca especificada.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Búsqueda de Teléfono");
            alert.setHeaderText("Resultados de búsqueda:");

            // Crear un ListView para mostrar los resultados
            ListView<Phone> listView = new ListView<>(FXCollections.observableArrayList(searchResults));
            listView.setCellFactory(param -> new ListCell<Phone>() {
                @Override
                protected void updateItem(Phone phone, boolean empty) {
                    super.updateItem(phone, empty);
                    if (empty || phone == null) {
                        setText(null);
                    } else {
                        setText(phone.getPhoneName());
                    }
                }
            });

            alert.getDialogPane().setContent(listView);
            alert.showAndWait();
        }
    }




    private Phone selectPhoneFromList(List<Phone> phones) {
        // Crear una nueva ventana de diálogo para mostrar la lista de teléfonos
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Seleccionar Teléfono");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        // Crear una tabla y sus columnas para mostrar la lista de teléfonos
        TableView<Phone> phoneTable = new TableView<>();
        TableColumn<Phone, String> brandColumn = new TableColumn<>("Marca");
        TableColumn<Phone, String> modelColumn = new TableColumn<>("Modelo");
        // Agrega aquí las demás columnas necesarias para mostrar la información del teléfono

        // Configura las celdas de las columnas con los valores de las propiedades del teléfono
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        // Configura aquí las celdas de las demás columnas

        // Agrega las columnas a la tabla
        phoneTable.getColumns().addAll(brandColumn, modelColumn);
        // Agrega aquí las demás columnas a la tabla

        // Crea una lista observable con los teléfonos
        ObservableList<Phone> phoneList = FXCollections.observableArrayList(phones);

        // Agrega los teléfonos a la tabla
        phoneTable.setItems(phoneList);

        // Configura la selección de una única fila en la tabla
        phoneTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Crea un botón para seleccionar el teléfono y cierra la ventana de diálogo
        Button selectButton = new Button("Seleccionar");
        selectButton.setOnAction(event -> {
            Phone selectedPhone = phoneTable.getSelectionModel().getSelectedItem();
            if (selectedPhone != null) {
                dialogStage.close();
            } else {
                // Muestra un mensaje de error o realiza alguna acción adicional en caso de que no se haya seleccionado ningún teléfono
            }
        });

        // Crea un contenedor para los elementos de la ventana de diálogo
        VBox dialogContent = new VBox(phoneTable, selectButton);
        dialogContent.setSpacing(10);
        dialogContent.setPadding(new Insets(10));

        // Crea una escena y asigna el contenido al escenario de diálogo
        Scene dialogScene = new Scene(dialogContent);
        dialogStage.setScene(dialogScene);

        // Muestra la ventana de diálogo y espera a que se cierre
        dialogStage.showAndWait();

        // Retorna el teléfono seleccionado
        return phoneTable.getSelectionModel().getSelectedItem();
    }

     */


}

