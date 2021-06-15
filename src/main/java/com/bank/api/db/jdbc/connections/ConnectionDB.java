package com.bank.api.db.jdbc.connections;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionDB{
    Connection get() throws SQLException;
    void close() throws SQLException;
}
