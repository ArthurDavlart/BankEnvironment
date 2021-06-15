package com.bank.api.db.jdbc;

import java.sql.SQLException;
import java.util.List;

public interface JdbcTemplate {
    void query(String sql, String... parameters) throws SQLException;
     <T> T getObject(RowMapper<T> rowMapper, String sql, String... parameters) throws SQLException;
     <T> List<T> getObjects(RowMapper<T> rowMapper, String sql, String... parameters) throws SQLException;
}
