package com.pluralsight.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDAO {
    private Connection connection = initConnection();

    private Connection initConnection() {
        try {
            return connection = DriverManager.getConnection(DBConfig.get("db.url"), DBConfig.get("db.user"), DBConfig.get("db.password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    private Connection getConnection() {
        if (connection != null) {
            return this.connection;
        } else {
            return initConnection();
        }
    }


}
