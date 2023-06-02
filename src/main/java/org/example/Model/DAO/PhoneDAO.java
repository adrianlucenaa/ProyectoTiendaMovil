package org.example.Model.DAO;


import org.example.Model.Connections.ConnectionMySQL;
import org.example.Model.Domain.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAO implements DAO<Phone> {
    private final static String FINDALL = "SELECT * FROM phone";
    private final static String FINDBYID = "SELECT * FROM phone WHERE id=?";
    private final static String INSERT = "INSERT INTO phone (id, name,brand,yearrelease,price) VALUES (?, ?,?,?,?)";
    private final static String UPDATE = "UPDATE phone SET brand=? WHERE id=?";
    private final static String DELETE = "DELETE FROM phone WHERE id=?";

    private Connection conn;

    public PhoneDAO(Connection conn) {
        this.conn = conn;
    }

    public PhoneDAO() {
        this.conn = ConnectionMySQL.getConnect();
    }

    @Override
    public List<Phone> findAll() throws SQLException {
        List<Phone> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Phone phone = new Phone();
                    phone.setIdPhone(res.getInt("id"));
                    phone.setBrand(res.getString("brand"));
                    result.add(phone);
                }
            }
        }
        return result;
    }

    @Override
    public  Phone findById(String id) throws SQLException {
        Phone result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Phone phone = new Phone();
                    phone.setIdPhone(res.getInt("id"));
                    phone.setBrand(res.getString("brand"));
                    phone.setName(res.getString("name"));
                    phone.setPrice(res.getDouble("precio"));
                    phone.setYearrelease(String.valueOf(res.getInt("yearrelease")));
                    result = phone;
                }
            }
        }
        return result;
    }

    @Override
    public Phone save(Phone entity) throws SQLException {
        Phone result = new Phone();
        if (entity != null) {
            Phone phone = findById(String.valueOf(entity.getIdPhone()));
            if (phone == null) {
                // INSERT
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getIdPhone());
                    pst.setString(2, entity.getName());
                    pst.setString(3, entity.getBrand());
                    pst.setString(4, entity.getYearrelease());
                    pst.setDouble(5, entity.getPrice());
                    pst.executeUpdate();
                }
            } else {
                // UPDATE
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getBrand());
                    pst.setInt(2, entity.getIdPhone());
                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }

    @Override
    public void delete(Phone entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getIdPhone());
                pst.executeUpdate();
            }
        }
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
    }
}
