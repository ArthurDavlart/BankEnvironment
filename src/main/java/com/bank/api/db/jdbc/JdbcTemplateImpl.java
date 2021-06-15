package com.bank.api.db.jdbc;

import com.bank.api.db.jdbc.connections.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JdbcTemplateImpl implements JdbcTemplate{
    private final ConnectionDB connectionDB;

    public JdbcTemplateImpl(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    @Override
    public void query(String sql, String... parameters) throws SQLException {
        executeSql(sql, parameters);
    }

    @Override
    public <T> T getObject(RowMapper<T> rowMapper, String sql, String... parameters) throws SQLException {
        PreparedStatement preparedStatement = executeSql(sql, parameters);

        ResultSet resultSet = preparedStatement.getResultSet();

        return resultSet.next() ? rowMapper.mapRow(resultSet) : null;
    }

    @Override
    public <T> List<T> getObjects(RowMapper<T> rowMapper, String sql, String... parameters) throws SQLException{
        List<T> result = new LinkedList<>();

        PreparedStatement preparedStatement = executeSql(sql, parameters);

        ResultSet resultSet = preparedStatement.getResultSet();

        while (resultSet.next()){
            result.add(rowMapper.mapRow(resultSet));
        }

        return result;
    }

    private PreparedStatement executeSql(String sql, String... parameters) throws SQLException {
        Connection connection = connectionDB.get();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setString(i + 1, parameters[i]);
        }

        preparedStatement.execute();

        return preparedStatement;
    }

}
