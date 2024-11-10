package org.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Predicate;

import org.example.config.DatabaseConnection;

public class JunctionTable {
    private static String CREATE_JUNCTION_TABLE = """
            CREATE TABLE IF NOT EXISTS JUNCTION (
            ID SERIAL PRIMARY KEY NOT NULL,
            TWEET_ID SERIAL NOT NULL REFERENCES TWEET(ID),
            TAG_ID SERIAL NOT NULL REFERENCES TAG(ID)
            )
            """;

    public JunctionTable() {
        createTable();
    }

    public void createTable() {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_JUNCTION_TABLE);
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
