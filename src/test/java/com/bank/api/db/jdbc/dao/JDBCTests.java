package com.bank.api.db.jdbc.dao;

import com.bank.api.db.jdbc.connections.DBConnectionSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JDBCTests {
//    private DBConnectionSettings dbConnectionSettings;
//    @BeforeEach
//    void init() throws SQLException {
//        dbConnectionSettings =
//                new DBConnectionSettings(
//                        "jdbc:h2:mem:test",
//                        "sa",
//                        "sa"
//                );
//
//
//    }
//
//    @Test
//    void test1() throws SQLException {
//        Connection connection1 = DriverManager.getConnection(
//                dbConnectionSettings.getUrl(),
//                dbConnectionSettings.getUser(),
//                dbConnectionSettings.getPassword());
//
//        PreparedStatement preparedStatement = connection1.prepareStatement("Create table test(id int);");
//        preparedStatement.execute();
//
//
//        Connection connection2 = DriverManager.getConnection(
//                dbConnectionSettings.getUrl(),
//                dbConnectionSettings.getUser(),
//                dbConnectionSettings.getPassword());
//
//        preparedStatement = connection2.prepareStatement("Create table test2(id int);");
//        preparedStatement.execute();
//        connection1.close();
//        connection2.close();
//    }
//
//    @Test
//    void test2() throws SQLException {
//        Connection connection1 = DriverManager.getConnection(
//                dbConnectionSettings.getUrl(),
//                dbConnectionSettings.getUser(),
//                dbConnectionSettings.getPassword());
//
//        connection1.close();
//        PreparedStatement preparedStatement = connection1.prepareStatement("Create table test(id int);");
//        preparedStatement.execute();
//
//        Connection connection2 = DriverManager.getConnection(
//                dbConnectionSettings.getUrl(),
//                dbConnectionSettings.getUser(),
//                dbConnectionSettings.getPassword());
//
//
//        connection2.close();
//    }
//
//    @Test
//    void test3() throws SQLException {
//        Connection connection1 = DriverManager.getConnection(
//                dbConnectionSettings.getUrl(),
//                dbConnectionSettings.getUser(),
//                dbConnectionSettings.getPassword());
//
//        PreparedStatement preparedStatement = connection1.prepareStatement("Create table test(id int);");
//        preparedStatement.execute();
//        connection1.close();
//
//        Connection connection2 = DriverManager.getConnection(
//                dbConnectionSettings.getUrl(),
//                dbConnectionSettings.getUser(),
//                dbConnectionSettings.getPassword());
//
//        connection2.setAutoCommit(false);
//
//        preparedStatement = connection2.prepareStatement("Create table test2(id int);");
//        preparedStatement.execute();
//
//        connection2.commit();
//        connection2.close();
//
//    }
//
//    @Test
//    void test4() throws SQLException{
//        Connection connection = DriverManager.getConnection(
//                dbConnectionSettings.getUrl(),
//                dbConnectionSettings.getUser(),
//                dbConnectionSettings.getPassword());
//
//        PreparedStatement preparedStatement = connection.prepareStatement("Create table test(id int);");
//        preparedStatement.execute();
//
//        PreparedStatement preparedStatement1 = connection.prepareStatement("Insert into test(id) values ( ? )");
//
//        preparedStatement1.setString(1, String.valueOf(42));
//
//        preparedStatement1.execute();
//
//        PreparedStatement preparedStatement2 = connection.prepareStatement("Select * from test;");
//        preparedStatement2.execute();
//
//        ResultSet resultSet = preparedStatement2.getResultSet();
//        assertTrue(resultSet.next());
//        assertEquals("42", resultSet.getString("id"));
//    }
//
//    @Test
//    void leftOuterJoinTests() throws SQLException{
//        Connection connection = DriverManager.getConnection(
//                dbConnectionSettings.getUrl(),
//                dbConnectionSettings.getUser(),
//                dbConnectionSettings.getPassword());
//        String createTablesSql = "Create table users(id int primary key );" +
//                "CREATE TABLE accounts(id int, user_id int, foreign key (user_id) references users(id));" +
//                "Insert into users(id) values (1), (2);";
//
//        PreparedStatement preparedStatement = connection.prepareStatement(createTablesSql);
//        preparedStatement.execute();
//
//        String leftOuterJoin = "Select * from users as u left outer join accounts a on a.user_id = u.id " +
//                "where u.id = 1;";
//
//        preparedStatement = connection.prepareStatement(leftOuterJoin);
//        preparedStatement.execute();
//
//        ResultSet resultSet = preparedStatement.getResultSet();
//
//        while (resultSet.next()){
//            System.out.printf("%s %s %s\n",
//                    resultSet.getString("users.id"),
//                    resultSet.getString("accounts.id"),
//                    resultSet.getString("accounts.user_id"));
//        }
//    }
//
//    @Test
//    void test5() throws SQLException {
//        Connection connection = DriverManager.getConnection(
//                dbConnectionSettings.getUrl(),
//                dbConnectionSettings.getUser(),
//                dbConnectionSettings.getPassword());
//
//        String createTablesSql = "Create table users(id int primary key );" +
//                "CREATE TABLE accounts(id int, user_id int, foreign key (user_id) references users(id));" +
//                "Insert into users(id) values (1), (2);";
//
//        PreparedStatement preparedStatement = connection.prepareStatement(createTablesSql);
//        preparedStatement.execute();
//
//        String insertSql = "Insert into accounts(id, user_id) values (?, ?)";
//
//        preparedStatement = connection.prepareStatement(insertSql);
//        preparedStatement.setString(1, String.valueOf(1));
//        preparedStatement.setString(2, String.valueOf(1));
//
//        preparedStatement.execute();
//    }
}
