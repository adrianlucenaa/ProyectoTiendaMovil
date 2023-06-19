package org.example.Model.Test;

import org.example.Model.DAO.PhoneDAO;
import org.example.Model.Domain.Phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class TestPhoneDAO {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/proyecto";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(url, user, pwd)) {

            PhoneDAO phoneDAO = new PhoneDAO(con);

            try {
                // Obtener todos los teléfonos
                List<Phone> phones = phoneDAO.findAll();
                System.out.println("Lista de teléfonos:");
                for (Phone phone : phones) {
                    System.out.println(phone);
                }

                // Obtener un teléfono por su ID
                String phoneId = "1"; // Reemplaza con un ID válido de tu base de datos
                Phone phone = phoneDAO.findById(phoneId);
                if (phone != null) {
                    System.out.println("Teléfono encontrado: " + phone);
                } else {
                    System.out.println("Teléfono no encontrado");
                }

                // Insertar un nuevo teléfono
                Phone newPhone = new Phone();
                newPhone.setIdPhone(4); // Reemplaza con un ID válido
                newPhone.setBrand("Samsung"); // Reemplaza con una marca válida
                newPhone.setPhoneName("Xiaomi A10"); // Reemplaza "Nombre del teléfono" con el nombre adecuado
                Phone savedPhone = phoneDAO.save(newPhone);
                System.out.println("Nuevo teléfono insertado: " + savedPhone);

                // Actualizar un teléfono existente
                Phone existingPhone = new Phone();
                existingPhone.setIdPhone(1); // Reemplaza con un ID válido
                existingPhone.setBrand("iPhone"); // Reemplaza con una marca válida
                existingPhone.setPhoneName("Nuevo nombre"); // Reemplaza "Nuevo nombre" con el nombre adecuado
                Phone updatedPhone = phoneDAO.save(existingPhone);
                System.out.println("Teléfono actualizado: " + updatedPhone);

                // Eliminar un teléfono
                Phone phoneToDelete = new Phone();
                phoneToDelete.setIdPhone(3); // Reemplaza con un ID válido
                phoneDAO.delete(phoneToDelete);
                System.out.println("Teléfono eliminado");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    phoneDAO.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}