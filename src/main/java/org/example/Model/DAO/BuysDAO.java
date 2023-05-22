package org.example.Model.DAO;

import org.example.Model.Connections.ConnectionMySQL;
import org.example.Model.Domain.Buys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuysDAO implements DAO<Buys> {
    private final static String FINDALL = "SELECT * FROM buys";
    private final static String FINDBYID = "SELECT * FROM buys WHERE idBuys=?";
    private final static String INSERT = "INSERT INTO buys (idBuys, IdCustomer, IdPhone, customerName, price) VALUES (?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE buys SET IdCustomer=?, IdPhone=?, customerName=?, price=? WHERE idBuys=?";
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
                    buys.setIdBuys(res.getInt("idBuys"));
                    buys.setIdCustomer(res.getInt("IdCustomer"));
                    buys.setIdPhone(res.getInt("IdPhone"));
                    buys.setCustomerName(res.getString("customerName"));
                    buys.setPrice(res.getDouble("price"));
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
                    buys.setIdBuys(res.getInt("idBuys"));
                    buys.setIdCustomer(res.getInt("IdCustomer"));
                    buys.setIdPhone(res.getInt("IdPhone"));
                    buys.setCustomerName(res.getString("customerName"));
                    buys.setPrice(res.getDouble("price"));
                    result = buys;
                }
            }
        }
        return result;
    }

    @Override
    public Buys save(Buys entity) throws SQLException {
        if (entity != null) {
            Buys buys = findById(String.valueOf(entity.getIdBuys()));
            if (buys == null) {
                // INSERT
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getIdBuys());
                    pst.setInt(2, entity.getIdCustomer());
                    pst.setInt(3, entity.getIdPhone());
                    pst.setString(4, entity.getCustomerName());
                    pst.setDouble(5, entity.getPrice());
                    pst.executeUpdate();
                }
            } else {
                // UPDATE
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setInt(1, entity.getIdCustomer());
                    pst.setInt(2, entity.getIdPhone());
                    pst.setString(3, entity.getCustomerName());
                    pst.setDouble(4, entity.getPrice());
                    pst.setInt(5, entity.getIdBuys());
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
}
