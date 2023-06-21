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
    /**
     * Consulatas a realizar en la base de datos
     */

    // Consulta para buscar todas las compras
    private final static String FINDALL = "SELECT * FROM buys";
    // Consulta para buscar una compra por su ID
    private final static String FINDBYID = "SELECT * FROM buys WHERE idBuys=?";
    // Consulta para insertar una nueva compra
    private final static String INSERT = "INSERT INTO buys (CustomerName, PhoneName, Price) VALUES (?, ?, ?)";
    // Consulta para actualizar una compra existente
    private final static String UPDATE = "UPDATE buys SET  CustomerName=?, PhoneName=?, Price=? WHERE IdBuys=?";
    // Consulta para eliminar una compra
    private final static String DELETE = "DELETE FROM buys WHERE idBuys=?";

    // Conexión a la base de datos
    /**
     * Conexion a la base de datos
     */
    private Connection conn;

    /**
     * Conectar DaO con mi SQL
     * @param conn
     */
    public BuysDAO(Connection conn) {
        this.conn = conn;
    }

    public BuysDAO() {
        this.conn = ConnectionMySQL.getConnect();
    }

    /**
     * Implementación del método para buscar todas las compras
     * @return
     * @throws SQLException
     */

    @Override
    public List<Buys> findAll() throws SQLException {
        // Lista para almacenar las compras encontradas
        List<Buys> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    // Crear un objeto Buys y establecer sus atributos
                    Buys buys = new Buys();
                    buys.setIdBuys(res.getInt("idBuys"));
                    buys.setPrice(res.getDouble("Price"));
                    buys.setCustomerName(res.getString("CustomerName"));
                    buys.setPhoneName(res.getString("PhoneName"));
                    result.add(buys);
                }
            }
        }
        return result;
    }

    /**
     * // Implementación del método para buscar una compra por su ID
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Buys findById(String id) throws SQLException {
        Buys result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    // Crear un objeto Buys y establecer sus atributos
                    Buys buys = new Buys();
                    buys.setIdBuys(res.getInt("idBuys"));
                    buys.setCustomerName(res.getString("CustomerName"));
                    buys.setPhoneName(res.getString("PhoneName"));
                    buys.setPrice(res.getDouble("Price"));
                    result = buys;
                }
            }
        }
        return result;
    }

    /**
     * // Implementación del método para guardar una compra
     * @param entity
     * @return
     * @throws SQLException
     */

    @Override
    public Buys save(Buys entity) throws SQLException {
        if (entity != null) {
            // Verificar si la compra ya existe en la base de datos
            Buys buys = findById(String.valueOf(entity.getIdBuys()));
            if (buys == null) {
                // Si no existe, realizar una inserción
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setString(1, entity.getCustomerName());
                    pst.setString(2, entity.getPhoneName());
                    pst.setDouble(3, entity.getPrice());
                    pst.executeUpdate();
                }
            } else {
                // Si existe, realizar una actualización
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

    /**
     * // Implementación del método para eliminar una compra
     * @param entity
     * @throws SQLException
     */

    // Implementación del método para eliminar una compra
    @Override
    public void delete(Buys entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getIdBuys());
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
        if (conn != null) {
            conn.close();
        }
    }


    /**
     * // Método para buscar teléfonos según un texto de búsqueda
     * @param searchText
     * @return
     * @throws SQLException
     */
    public List<Phone> searchPhones(String searchText) throws SQLException {
        // Lista para almacenar los teléfonos encontrados
        List<Phone> phoneList = new ArrayList<>();
        String SEARCH_QUERY = "SELECT id_phone, brand, PhoneName, price FROM Phone " +
                "WHERE id_phone = ? OR brand LIKE ? OR PhoneName LIKE ?";
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
                    // Crear un objeto Phone y establecer sus atributos
                    Phone phone = new Phone();
                    phone.setIdPhone(rs.getInt("id_phone"));
                    phone.setBrand(rs.getString("brand"));
                    phone.setPhoneName(rs.getString("model"));
                    phone.setPrice(rs.getDouble("price"));
                    phoneList.add(phone);
                }
            }
        }
        return phoneList;
    }

    /**
     * // Método para actualizar una compra
     * @param selectedBuys
     * @throws SQLException
     */

    public void update(Buys selectedBuys) throws SQLException {
        if (selectedBuys != null) {
            String UPDATE_QUERY = "UPDATE Buys SET CustomerName = ?, PhoneName = ?, price = ? WHERE IdBuys = ?";
            try (PreparedStatement pst = conn.prepareStatement(UPDATE_QUERY)) {
                pst.setString(1, selectedBuys.getCustomerName());
                pst.setString(2, selectedBuys.getPhoneName());
                pst.setDouble(3, selectedBuys.getPrice());
                pst.setInt(4, selectedBuys.getIdBuys());
                pst.executeUpdate();
            }
        }
    }
}
