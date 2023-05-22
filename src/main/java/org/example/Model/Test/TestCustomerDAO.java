package org.example.Model.Test;

import org.example.Model.DAO.CustomerDAO;
import org.example.Model.Domain.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class TestCustomerDAO {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/proyecto";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(url, user, pwd)) {

            CustomerDAO customerDAO = new CustomerDAO(con);

            try {
                // Obtener todos los clientes
                List<Customer> customers = customerDAO.findAll();
                System.out.println("Lista de clientes:");
                for (Customer customer : customers) {
                    System.out.println(customer);
                }

                // Obtener un cliente por su ID
                String customerId = "1"; // Reemplaza con un ID válido de tu base de datos
                Customer customer = customerDAO.findById(customerId);
                if (customer != null) {
                    System.out.println("Cliente encontrado: " + customer);
                } else {
                    System.out.println("Cliente no encontrado");
                }

                // Insertar un nuevo cliente
                Customer newCustomer = new Customer();
                newCustomer.setIdCustomer(4); // Reemplaza con un ID válido
                newCustomer.setName("Juan"); // Reemplaza con un nombre válido
                newCustomer.setNumber("123456789"); // Reemplaza con un número de teléfono válido
                newCustomer.setMail("juan@example.com"); // Reemplaza con un correo válido
                newCustomer.setAddress("Calle Principal 123"); // Reemplaza con una dirección válida
                Customer savedCustomer = customerDAO.save(newCustomer);
                System.out.println("Nuevo cliente insertado: " + savedCustomer);

                // Actualizar un cliente existente
                Customer existingCustomer = new Customer();
                existingCustomer.setIdCustomer(1); // Reemplaza con un ID válido
                existingCustomer.setName("Pedro"); // Reemplaza con un nombre válido
                existingCustomer.setNumber("987654321"); // Reemplaza con un número de teléfono válido
                existingCustomer.setMail("pedro@example.com"); // Reemplaza con un correo válido
                existingCustomer.setAddress("Calle Secundaria 456"); // Reemplaza con una dirección válida
                Customer updatedCustomer = customerDAO.save(existingCustomer);
                System.out.println("Cliente actualizado: " + updatedCustomer);

                // Eliminar un cliente
                Customer customerToDelete = new Customer();
                customerToDelete.setIdCustomer(3); // Reemplaza con un ID válido
                customerDAO.delete(customerToDelete);
                System.out.println("Cliente eliminado");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    customerDAO.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
