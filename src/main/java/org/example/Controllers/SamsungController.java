package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SamsungController {

    @FXML
    private Button movil1;

    @FXML
    private Button movil2;

    @FXML
    private Button movil3;

    @FXML
    private void initialize() {

    }



    @FXML
    private void handleBuyNowButtonAction() {
        // Lógica para comprar el iPhone
        System.out.println("El iPhone ha sido comprado.");

        realizarCompra();

        // Ejemplo de operaciones con datos
        double precio = obtenerPrecio();
        int cantidad = obtenerCantidad();
        double total = precio * cantidad;
        System.out.println("El total a pagar es: " + total);
    }

    private int obtenerCantidad() {
        return 0;
    }

    private double obtenerPrecio() {
       return 0;
    }

    // Ejemplo de método en otra clase
    private void realizarCompra() {
        // Lógica para procesar la compra del iPhone
        System.out.println("Procesando compra...");
        // Aquí puedes agregar tu propia lógica para procesar la compra del iPhone
    }

}