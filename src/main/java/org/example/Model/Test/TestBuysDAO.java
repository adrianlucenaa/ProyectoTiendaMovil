package org.example.Model.Test;


import org.example.Model.DAO.BuysDAO;
import org.example.Model.Domain.Buys;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;


/*
public class TestBuysDAO {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/proyecto";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(url, user, pwd)) {
            BuysDAO buysDAO = new BuysDAO(con);

            try {
                // Obtener todas las compras
                List<Buys> buysList = buysDAO.findAll();
                System.out.println("Lista de compras:");
                for (Buys buys : buysList) {
                    System.out.println(buys);
                }

                // Obtener una compra por su ID
                int buysId = 1; // Reemplaza con un ID válido de tu base de datos
                Buys buys = buysDAO.findById(String.valueOf(buysId));
                if (buys != null) {
                    System.out.println("Compra encontrada: " + buys);
                } else {
                    System.out.println("Compra no encontrada");
                }

                // Insertar una nueva compra
                Buys newBuys = new Buys();
                newBuys.setIdBuys(4); // Reemplaza con un ID válido utilizando el método correspondiente
                newBuys.setIdCustomer(1); // Reemplaza con un ID de cliente válido
                newBuys.setIdPhone(1); // Reemplaza con un ID de teléfono válido
                newBuys.setCustomerName("Nombre del cliente"); // Reemplaza con el nombre del cliente adecuado
                newBuys.setPrice(100.0); // Reemplaza con el precio adecuado
                Buys savedBuys = buysDAO.save(newBuys);
                System.out.println("Nueva compra insertada: " + savedBuys);

                // Actualizar una compra existente
                Buys existingBuys = new Buys();
                existingBuys.setIdBuys(1); // Reemplaza con un ID válido
                existingBuys.setIdCustomer(1); // Reemplaza con un ID de cliente válido
                existingBuys.setIdPhone(1); // Reemplaza con un ID de teléfono válido
                existingBuys.setCustomerName("Nuevo nombre del cliente"); // Reemplaza con el nombre del cliente adecuado
                existingBuys.setPrice(200.0); // Reemplaza con el precio adecuado
                Buys updatedBuys = buysDAO.save(existingBuys);
                System.out.println("Compra actualizada: " + updatedBuys);

                // Eliminar una compra
                Buys buysToDelete = new Buys();
                buysToDelete.setIdBuys(3); // Reemplaza con un ID válido
                buysDAO.delete(buysToDelete);
                System.out.println("Compra eliminada");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    buysDAO.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
