package org.example.Model.DAO;

import org.example.Model.Domain.Buys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuysDAO implements DAO<Buys> {
    private final static String FINDALL = "SELECT * FROM buys";
    private final static String FINDBYID = "SELECT * FROM buys WHERE id=?";
    private final static String INSERT = "INSERT INTO buys (id, customer_id, product_id) VALUES (?, ?, ?)";
    private final static String UPDATE = "UPDATE buys SET customer_id=?, product_id=? WHERE id=?";
    private final static String DELETE = "DELETE FROM buys WHERE id=?";

    private Connection conn;

    public BuysDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Buys> findAll() throws SQLException {
        List<Buys> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Buys buys = new Buys();
                    buys.setIdBuys(res.getInt("buys_id"));
                    buys.setIdCustomer(res.getInt("customer_id"));
                    buys.setIdProduct(res.getInt("product_id"));
                    result.add(buys);
                }
            }
        }
        return result;
    }

    @Override
    public Buys findById(String id) throws SQLException {
        return null;
    }

    public Buys findById(int id) throws SQLException {
        Buys result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Buys buys = new Buys();
                    buys.setIdBuys(res.getInt("buys_id"));
                    buys.setIdCustomer(res.getInt("customer_id"));
                    buys.setIdProduct(res.getInt("product_id"));
                    result = buys;
                }
            }
        }
        return result;
    }

    public Buys save(Buys entity) throws SQLException {
        Buys result = new Buys();
        if (entity != null) {
            Buys buys = findById(entity.getIdBuys());
            if (buys == null) {
                // INSERT
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getIdBuys());
                    pst.setInt(2, entity.getIdCustomer());
                    pst.setInt(3, entity.getIdProduct());
                    pst.executeUpdate();
                }
            } else {
                // UPDATE
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setInt(1, entity.getIdCustomer());
                    pst.setInt(2, entity.getIdProduct());
                    pst.setInt(3, entity.getIdBuys());
                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }

    public void delete(Buys entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getIdBuys());
                pst.executeUpdate();
            }
        }
    }

    public void close() throws Exception {
        // TODO Auto-generated method stub
    }
}
