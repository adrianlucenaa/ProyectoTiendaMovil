package org.example.Model.DAO;

import java.sql.SQLException;
import java.util.List;

interface DAO<T> extends AutoCloseable{
    List<T> findAll() throws SQLException ;
    T findById(String id) throws SQLException ;
    T save(T entity) throws SQLException ;
    void delete(T entity) throws SQLException ;
}

