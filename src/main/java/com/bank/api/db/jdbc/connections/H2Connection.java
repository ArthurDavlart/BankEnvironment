package com.bank.api.db.jdbc.connections;

import com.bank.api.db.jdbc.tablecreator.TableCreator;
import org.h2.command.ddl.CreateTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Connection implements ConnectionDB {
    private static H2Connection instance;
    private final DBConnectionSettings dbConnectionSettings;
    private Connection connection;

    private H2Connection(DBConnectionSettings dbConnectionSettings) {
        this.dbConnectionSettings = dbConnectionSettings;
    }

    public static synchronized H2Connection getInstance(DBConnectionSettings dbConnectionSettings){
        if (instance == null){
            synchronized (H2Connection.class){
                instance = new H2Connection(dbConnectionSettings);
            }

        }

        return instance;
    }

    @Override
    public synchronized Connection get() throws SQLException {
        if (this.connection == null){
            this.connection = DriverManager.getConnection(
                    dbConnectionSettings.getUrl(),
                    dbConnectionSettings.getUser(),
                    dbConnectionSettings.getPassword());
        }
        return connection;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
        connection = null;
    }
}
