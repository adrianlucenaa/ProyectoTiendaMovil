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
    private final static String INSERT = "INSERT INTO customer (id, name) VALUES (?, ?)";
    private final static String UPDATE = "UPDATE customer SET name=? WHERE id=?";
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
                    result = customer;
                }
            }
        }
        return result;
    }

    @Override
    public Customer save(Customer entity) throws SQLException {
        Customer result = new Customer();
        if (entity != null) {
            Customer customer = findById(String.valueOf(entity.getIdCustomer()));
            if (customer == null) {
                // INSERT
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getIdCustomer());
                    pst.setString(2, entity.getName());
                    pst.executeUpdate();
                }
            } else {
                // UPDATE
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getName());
                    pst.setInt(2, entity.getIdCustomer());
                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
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
        // TODO Auto-generated method stub
    }
}
