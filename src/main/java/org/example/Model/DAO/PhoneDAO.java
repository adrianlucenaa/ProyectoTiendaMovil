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

public class PhoneDAO implements DAO<Phone> {
    /**
     * Consultas a reliazar en la base de datos
     */

    private final static String FINDBYID = "SELECT * FROM phone WHERE idPhone=?";
    private final static String INSERT = "INSERT INTO phone ( PhoneName, brand, yearrelease, price) VALUES ( ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE phone SET PhoneName=?, brand=?, yearrelease=?, price=? WHERE idPhone=?";
    private final static String DELETE = "DELETE FROM phone WHERE idPhone=?";

    /**
     * Conexion a la base datos
     */
    private Connection conn;

    /**
     * Conectando el DAO a la base datos
     * @param conn
     */
    public PhoneDAO(Connection conn) {
        this.conn = conn;
    }

    public PhoneDAO() {
        this.conn = ConnectionMySQL.getConnect();
    }

    /**
     * Implementación del método para buscar todos los moviles
     * @return
     * @throws SQLException
     */
    @Override
    public List<Phone> findAll() throws SQLException {
        List<Phone> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement("SELECT * FROM phone")) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Phone phone = new Phone();
                    phone.setIdPhone(res.getInt("idPhone"));
                    phone.setPhoneName(res.getString("PhoneName"));
                    phone.setBrand(res.getString("brand"));
                    phone.setYearrelease(res.getInt("yearrelease"));
                    phone.setPrice(res.getDouble("price"));
                    result.add(phone);
                }
            }
        }
        return result;
    }


    /**
     * Implementación del método para buscar un cliente por su ID
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Phone findById(String id) throws SQLException {
        Phone result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Phone phone = new Phone();
                    phone.setIdPhone(res.getInt("idPhone"));
                    phone.setBrand(res.getString("brand"));
                    phone.setPhoneName(res.getString("PhoneName"));
                    phone.setPrice(res.getDouble("price"));
                    phone.setYearrelease(res.getInt("yearrelease"));
                    result = phone;
                }
            }
        }
        return result;
    }

    /**
     * Metodo para guardar un dato de la tabla
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public Phone save(Phone entity) throws SQLException {
        if (entity != null) {
            Phone phone = findById(String.valueOf(entity.getIdPhone()));
            if (phone == null) {
                // INSERT
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setString(1, entity.getPhoneName());
                    pst.setString(2, entity.getBrand());
                    pst.setInt(3, entity.getYearrelease());
                    pst.setDouble(4, entity.getPrice());
                    pst.executeUpdate();
                }
            } else {
                // UPDATE
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getPhoneName());
                    pst.setString(2, entity.getBrand());
                    pst.setInt(3, entity.getYearrelease());
                    pst.setDouble(4, entity.getPrice());
                    pst.setInt(5, entity.getIdPhone());
                    pst.executeUpdate();
                }
            }
            return entity;
        }
        return null;
    }

    /**
     * Metodo para eliminar un movil
     * @param entity
     * @throws SQLException
     */
    @Override
    public void delete(Phone entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getIdPhone());
                pst.executeUpdate();
            }
        }
    }

    /**
     * Implementación del método para cerrar la conexión a la base de datos
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
    }

    /**
     * Metodo Actualiza EL movil
     * @param selectedPhone
     * @throws SQLException
     */
    public void update(Phone selectedPhone) throws SQLException {
        if (selectedPhone != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                pst.setString(1, selectedPhone.getPhoneName());
                pst.setString(2, selectedPhone.getBrand());
                pst.setInt(3, selectedPhone.getYearrelease());
                pst.setDouble(4, selectedPhone.getPrice());
                pst.setInt(5, selectedPhone.getIdPhone());
                pst.executeUpdate();
            }
        }
    }

    /**
     * Implatacion del metodo para que me busque un movil por marca en mi vusta Buys
     * @param searchText
     * @return
     * @throws SQLException
     */
    public List<Phone> search(String searchText) throws SQLException {
        List<Phone> phoneList = new ArrayList<>();
        String SEARCH_QUERY = "SELECT * FROM Phone WHERE IdPhone = ? OR PhoneName LIKE ? OR brand LIKE ?";
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
            ps.setString(3, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Phone phone = new Phone(
                            rs.getString("PhoneName"),
                            rs.getString("brand"),
                            rs.getInt("yearrelease"),
                            rs.getDouble("price")
                    );
                    phone.setIdPhone(rs.getInt("IdPhone"));
                    phoneList.add(phone);
                }
            }
        }
        return phoneList;
    }


    public List<Phone> searchByBrand(String brand) throws SQLException {
        List<Phone> phones = new ArrayList<>();

        String query = "SELECT * FROM Phone WHERE brand LIKE ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + brand + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String phoneBrand = resultSet.getString("brand");
                    String phoneName = resultSet.getString("PhoneName");
                    int yearRelease = resultSet.getInt("yearrelease");
                    double price = resultSet.getDouble("price");

                    Phone phone = new Phone(phoneBrand, phoneName,yearRelease, price);
                    phones.add(phone);
                }
            }
        }

        return phones;
    }
}

