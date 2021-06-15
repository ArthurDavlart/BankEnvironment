package com.bank.api.db.jdbc.tablecreator;

import com.bank.api.db.jdbc.connections.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableCreator {
    private final ConnectionDB connectionDB;

    public TableCreator(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    public void createUsersTable() throws SQLException {
        Connection connection = connectionDB.get();

        String createUsersTableSql = "CREATE TABLE IF NOT EXISTS users(\n" +
                "    id BIGINT PRIMARY KEY auto_increment,\n" +
                "    first_name VARCHAR(255) NOT NULL,\n" +
                "    second_name VARCHAR(255) NOT NULL,\n" +
                "    middle_name VARCHAR(255),\n" +
                "    passport_serial INT NOT NULL,\n" +
                "    passport_number INT NOT NULL,\n" +
                "    passport_type VARCHAR(2) NOT NULL);";

        PreparedStatement preparedStatement = connection.prepareStatement(createUsersTableSql);
        preparedStatement.execute();

    }

    public void createAccountsTable() throws SQLException{
        Connection connection = connectionDB.get();

        String createAccountsTableSql = "CREATE TABLE IF NOT EXISTS accounts(\n" +
                "    id BIGINT PRIMARY KEY auto_increment,\n" +
                "    account_number VARCHAR(20) NOT NULL,\n" +
                "    balance DECIMAL NOT NULL,\n" +
                "    currency VARCHAR(3) NOT NULL DEFAULT ('RUB'),\n" +
                "    user_id BIGINT,\n" +
                "    foreign key (user_id) references users(id)\n" +
                ");";

        PreparedStatement preparedStatement = connection.prepareStatement(createAccountsTableSql);
        preparedStatement.execute();
    }

    public void createCardsTable() throws SQLException{
        Connection connection = connectionDB.get();

        String createCardsTableSql = "CREATE TABLE IF NOT EXISTS cards(\n" +
                "    id BIGINT PRIMARY KEY auto_increment,\n" +
                "    card_number VARCHAR(20) NOT NULL,\n" +
                "    account_id BIGINT,\n" +
                "    foreign key (account_id) references accounts(id)\n" +
                ");";

        PreparedStatement preparedStatement = connection.prepareStatement(createCardsTableSql);
        preparedStatement.execute();
    }

    public void createAllTable() throws SQLException {
        createUsersTable();
        createAccountsTable();
        createCardsTable();
    }
}
