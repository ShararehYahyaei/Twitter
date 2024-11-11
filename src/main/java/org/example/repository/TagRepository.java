package org.example.repository;

import org.example.config.DatabaseConnection;
import org.example.entity.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagRepository {

    private static String CREATE_TAG_TABLE = """
            CREATE TABLE IF NOT EXISTS TAG (
            ID SERIAL PRIMARY KEY NOT NULL,
            NAME VARCHAR(50) NOT NULL UNIQUE)
            """;

    public TagRepository() {
        createTag();
    }

    public void createTag() {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_TAG_TABLE);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static String INSERT_TAGS = """
            INSERT INTO TAG(NAME)
            VALUES(?)
            """;


    public void insertTag(String tagName) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_TAGS);
            statement.setString(1, tagName);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String SELECT_ALL_TAGS = """
            SELECT * FROM TAG;
            """;


    public List<Tag> selectAllTags() {
        Connection connection = DatabaseConnection.getConnection();
        List<Tag> tags = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TAGS);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                String name = set.getString("NAME");
                Long id = set.getLong("ID");
                tags.add(new Tag(name, id));
            }
            set.close();
            return tags;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String GET_ALL_TAGS_FOR_ONE_USER = """
            SELECT NAME FROM TAG
            WHERE ID =?
            """;

    public String getNameForTag(Long Id) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_TAGS_FOR_ONE_USER);
            statement.setLong(1, Id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                String name = set.getString("NAME");
                return name;
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
