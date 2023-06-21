package org.example.Model.DAO;

import org.example.Model.Connections.ConnectionMySQL;
import org.example.Model.Domain.Customer;
import org.example.Model.Domain.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements DAO<Customer> {


    /**
     * // Consultas para realizar en la base de datos
     */
    // Consulta para buscar todos los clientes
    private final static String FINDALL = "SELECT * FROM customer";
    // Consulta para buscar un cliente por su ID
    private final static String FINDBYID = "SELECT * FROM customer WHERE IdCustomer=?";
    // Consulta para insertar un nuevo cliente
    private final static String INSERT = "INSERT INTO customer (name, number, mail, address) VALUES (?, ?, ?, ?)";
    // Consulta para actualizar un cliente existente
    private final static String UPDATE = "UPDATE customer SET name=?, number=?, mail=?, address=? WHERE IdCustomer=?";
    // Consulta para eliminar un cliente
    private final static String DELETE = "DELETE FROM customer WHERE IdCustomer=?";

    /**
     * / Conexión a la base de datos
     */
    private Connection conn;

    /**
     * Conecta los dao a mi base de datos
     * @param conn
     */

    public CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    public CustomerDAO() {
        this.conn = ConnectionMySQL.getConnect();
    }

    /**
     * // Implementación del método para buscar todos los clientes
     * @return
     * @throws SQLException
     */
    @Override
    public List<Customer> findAll() throws SQLException {
        // Lista para almacenar los clientes encontrados
        List<Customer> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    // Crear un objeto Customer y establecer sus atributos
                    Customer customer = new Customer();
                    customer.setIdCustomer(res.getInt("IdCustomer"));
                    customer.setName(res.getString("name"));
                    customer.setNumber(res.getString("number"));
                    customer.setMail(res.getString("mail"));
                    customer.setAddress(res.getString("address"));
                    result.add(customer);
                }
            }
        }
        return result;
    }

    /**
     * // Implementación del método para buscar un cliente por su ID
     * @param id
     * @return
     * @throws SQLException
     */

    @Override
    public Customer findById(String id) throws SQLException {
        Customer result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    // Crear un objeto Customer y establecer sus atributos
                    Customer customer = new Customer();
                    customer.setIdCustomer(res.getInt("IdCustomer"));
                    customer.setName(res.getString("name"));
                    customer.setNumber(res.getString("number"));
                    customer.setMail(res.getString("mail"));
                    customer.setAddress(res.getString("address"));
                    result = customer;
                }
            }
        }
        return result;
    }

    /**
     * // Implementación del método para guardar un cliente
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public Customer save(Customer entity) throws SQLException {
        if (entity != null) {
            // Verificar si el cliente ya existe en la base de datos
            Customer customer = findById(String.valueOf(entity.getIdCustomer()));
            if (customer == null) {
                // Si no existe, realizar una inserción
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setString(1, entity.getName());
                    pst.setString(2, entity.getNumber());
                    pst.setString(3, entity.getMail());
                    pst.setString(4, entity.getAddress());
                    pst.executeUpdate();
                }
            } else {
                // Si existe, realizar una actualización
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getName());
                    pst.setString(2, entity.getNumber());
                    pst.setString(3, entity.getMail());
                    pst.setString(4, entity.getAddress());
                    pst.setInt(5, entity.getIdCustomer());
                    pst.executeUpdate();
                }
            }
            return entity;
        }
        return null;
    }

    /**
     * // Implementación del método para eliminar un cliente
     * @param entity
     * @throws SQLException
     */
    @Override
    public void delete(Customer entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getIdCustomer());
                pst.executeUpdate();
            }
        }
    }

    /**
     * // Implementación del método para cerrar la conexión a la base de datos
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * // Método para actualizar un cliente
     * @param selectedCustomer
     * @throws SQLException
     */
    public void update(Customer selectedCustomer) throws SQLException {
        if (selectedCustomer != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                pst.setString(1, selectedCustomer.getName());
                pst.setString(2, selectedCustomer.getNumber());
                pst.setString(3, selectedCustomer.getMail());
                pst.setString(4, selectedCustomer.getAddress());
                pst.setInt(5, selectedCustomer.getIdCustomer());
                pst.executeUpdate();
            }
        }
    }

    /**
     * // Método para buscar clientes según un texto de búsqueda
     * @param searchText
     * @return
     * @throws SQLException
     */
    public List<Customer> searchCustomer(String searchText) throws SQLException {
        // Lista para almacenar los clientes encontrados
        List<Customer> customerList = new ArrayList<>();
        String SEARCH_QUERY = "SELECT * FROM Customer WHERE IdCustomer = ? OR Name LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(SEARCH_QUERY)) {
            int searchId;
            try {
                searchId = Integer.parseInt(searchText);
            } catch (NumberFormatException e) {
                searchId = -1; // Valor inválido para el ID
            }
            ps.setInt(1, searchId);
            String searchPattern = "%" + searchText + "%";
            ps.setString(2, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Crear un objeto Customer y establecer sus atributos
                    Customer customer = new Customer();
                    customer.setIdCustomer(rs.getInt("IdCustomer"));
                    customer.setName(rs.getString("Name"));
                    customer.setNumber(rs.getString("Number"));
                    customer.setMail(rs.getString("Mail"));
                    customer.setAddress(rs.getString("Address"));
                    customerList.add(customer);
                }
            }
        }
        return customerList;
    }
}


