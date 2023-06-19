package org.example.Model.DAO;

import org.example.Model.Connections.ConnectionMySQL;
import org.example.Model.Domain.Buys;
import org.example.Model.Domain.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuysDAO implements DAO<Buys> {
    private final static String FINDALL = "SELECT * FROM buys";
    private final static String FINDBYID = "SELECT * FROM buys WHERE idBuys=?";
    private final static String INSERT = "INSERT INTO buys (CustomerName, PhoneName, price) " +
            "SELECT c.customerName, p.phoneName, ? " +
            "FROM customer c " +
            "JOIN phone p ON c.idCustomer = p.customerId " +
            "WHERE c.customerName = ? AND p.phoneName = ?";
    private final static String UPDATE = "UPDATE buys SET CustomerName=?, PhoneName=?,  price=? WHERE idBuys=?";
    private final static String DELETE = "DELETE FROM buys WHERE idBuys=?";

    private Connection conn;

    public BuysDAO(Connection conn) {
        this.conn = conn;
    }

    public BuysDAO() {
        this.conn = ConnectionMySQL.getConnect();
    }

    @Override
    public List<Buys> findAll() throws SQLException {
        List<Buys> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Buys buys = new Buys();
                    buys.setIdBuys(res.getInt("IdBuys"));
                    buys.setPrice(res.getDouble("price"));
                    buys.setCustomerName(res.getString("CustomerName"));
                    buys.setPhoneName(res.getString("PhoneName"));
                    result.add(buys);
                }
            }
        }
        return result;
    }

    @Override
    public Buys findById(String id) throws SQLException {
        Buys result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Buys buys = new Buys();
                    buys.setIdBuys(res.getInt("IdBuys"));
                    buys.setCustomerName(res.getString("CustomerName"));
                    buys.setPhoneName(res.getString("PhoneName"));
                    buys.setPrice(res.getDouble("price"));
                    result = buys;
                }
            }
        }
        return result;
    }

    @Override
    public  Buys save(Buys entity) throws SQLException {
        if (entity != null) {
            Buys buys = findById(String.valueOf(entity.getIdBuys()));
            if (buys == null) {
                // INSERT
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setString(1, entity.getCustomerName());
                    pst.setString(2, entity.getPhoneName());
                    pst.setDouble(3, entity.getPrice());
                    pst.executeUpdate();
                }
            } else {
                // UPDATE
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getCustomerName());
                    pst.setString(2, entity.getPhoneName());
                    pst.setDouble(3, entity.getPrice());
                    pst.setInt(4, entity.getIdBuys());
                    pst.executeUpdate();
                }
            }
            return entity;
        }
        return null;
    }

    @Override
    public void delete(Buys entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getIdBuys());
                pst.executeUpdate();
            }
        }
    }

    @Override
    public void close() throws Exception {

    }



    public List<Buys> searchByPhoneBrand(String brand) throws SQLException {
        List<Buys> result = new ArrayList<>();
        String query = "SELECT * FROM buys b JOIN phone p ON b.PhoneName = p.PhoneName WHERE LOWER(p.brand) LIKE ?";
        try (PreparedStatement pst = this.conn.prepareStatement(query)) {
            pst.setString(1, "%" + brand + "%");
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Buys buys = new Buys();
                    buys.setIdBuys(res.getInt("IdBuys"));
                    buys.setPrice(res.getDouble("price"));
                    buys.setCustomerName(res.getString("CustomerName"));
                    buys.setPhoneName(res.getString("PhoneName"));
                    result.add(buys);
                }
            }
        }
        return result;
    }




}
