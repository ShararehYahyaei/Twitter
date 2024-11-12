package org.example.config;
import lombok.Getter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    @Getter
    private static Connection connection;

    public DatabaseConnection() {
    }

    static {
        String jdbc = "jdbc:postgresql://localhost:5432/twitter";
        try {
            connection = DriverManager.getConnection(jdbc, "postgres", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // JVM Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
