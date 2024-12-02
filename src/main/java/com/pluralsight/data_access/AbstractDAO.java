package com.pluralsight.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDAO {

    protected Connection getConnection() {
        try {
            return DriverManager.getConnection(DBConfig.get("db.url"), DBConfig.get("db.user"), DBConfig.get("db.password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
