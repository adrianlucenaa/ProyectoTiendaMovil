package org.example.Model.DAO;

import java.sql.SQLException;
import java.util.List;

interface DAO<T> extends AutoCloseable {
    // Método para buscar todos los elementos de tipo T en el almacén de datos
    List<T> findAll() throws SQLException;

    // Método para buscar un elemento de tipo T por su ID en el almacén de datos
    T findById(String id) throws SQLException;

    // Método para guardar un elemento de tipo T en el almacén de datos
    T save(T entity) throws SQLException;

    // Método para eliminar un elemento de tipo T del almacén de datos
    void delete(T entity) throws SQLException;

    // Método para cerrar cualquier recurso asociado al DAO (por ejemplo, una conexión a la base de datos)
    void close() throws Exception;
}

