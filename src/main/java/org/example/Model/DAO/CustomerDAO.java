package org.example.Model.DAO;

import org.example.Model.Connections.ConnectionMySQL;
import org.example.Model.Domain.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements DAO<Customer> {
    private final static String FINDALL = "SELECT * FROM customer";
    private final static String FINDBYID = "SELECT * FROM customer WHERE id=?";
    private final static String INSERT = "INSERT INTO customer (id, name, number, mail, address) VALUES (?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE customer SET name=?, number=? WHERE id=?";
    private final static String DELETE = "DELETE FROM customer WHERE id=?";

    private Connection conn;

    public CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    public CustomerDAO() {
        this.conn = ConnectionMySQL.getConnect();
    }

    @Override
    public List<Customer> findAll() throws SQLException {
        List<Customer> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Customer customer = new Customer();
                    customer.setIdCustomer(res.getInt("id"));
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

    @Override
    public Customer findById(String id) throws SQLException {
        Customer result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Customer customer = new Customer();
                    customer.setIdCustomer(res.getInt("id"));
                    customer.setName(res.getString("name"));
                    customer.setNumber(res.getString("number"));
                    result = customer;
                }
            }
        }
        return result;
    }

    @Override
    public Customer save(Customer entity) throws SQLException {
        if (entity != null) {
            Customer customer = findById(String.valueOf(entity.getIdCustomer()));
            if (customer == null) {
                // INSERT
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getIdCustomer());
                    pst.setString(2, entity.getName());
                    pst.setString(3, entity.getNumber());
                    pst.setString(4, entity.getMail());
                    pst.setString(5, entity.getAddress());
                    pst.executeUpdate();
                }
            } else {
                // UPDATE
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getName());
                    pst.setString(2, entity.getNumber());
                    pst.setInt(3, entity.getIdCustomer());
                    pst.executeUpdate();
                }
            }
            return entity;
        }
        return null;
    }

    @Override
    public void delete(Customer entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getIdCustomer());
                pst.executeUpdate();
            }
        }
    }

    @Override
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }
}

