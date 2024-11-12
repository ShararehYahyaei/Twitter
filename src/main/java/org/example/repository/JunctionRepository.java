package org.example.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.config.DatabaseConnection;
import org.example.entity.Tag;
public class JunctionRepository {
    public JunctionRepository() {
        createTable();
    }

    private static String CREATE_JUNCTION_TABLE = """
            CREATE TABLE IF NOT EXISTS JUNCTION (
            ID SERIAL PRIMARY KEY NOT NULL,
            TWEET_ID INTEGER  REFERENCES TWEET(ID) ON DELETE CASCADE,
            TAG_ID INTEGER  REFERENCES TAG(ID)ON DELETE CASCADE
            )
            """;


    private static String INSERT_JUNCTION_TABLE = """
            INSERT INTO JUNCTION ( TWEET_ID,TAG_ID)
            VALUES (?,?);
            """;

    public void insert(Long tweetId, Long tagId) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(INSERT_JUNCTION_TABLE);
            statement.setLong(1, tweetId);
            statement.setLong(2, tagId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private static String GET_TAG_ID = """
            SELECT TAG_ID FROM JUNCTION WHERE TWEET_ID = ?
            """;

    public List<Tag> getTagsByTweetId(Long twee_id) {
        Connection conn = DatabaseConnection.getConnection();
        List<Tag> tags = new ArrayList<Tag>();
        try {
            PreparedStatement statement = conn.prepareStatement(GET_TAG_ID);
            statement.setLong(1, twee_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("TAG_ID");
                Tag tag = new Tag();
                tag.setId(id);
                tags.add(tag);
            }
            resultSet.close();
            return tags;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String  DELETE_WITH_TAG_ID_TWEET_ID= """
            DELETE FROM JUNCTION WHERE TWEET_ID = ?
            AND TAG_ID =?
            """;
    public void deleteWithTweetIdAndTagId(Long tweetId, Long tagId) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement=conn.prepareStatement(DELETE_WITH_TAG_ID_TWEET_ID);
            statement.setLong(1, tweetId);
            statement.setLong(2, tagId);
            statement.execute();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
