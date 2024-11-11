package org.example.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.config.DatabaseConnection;
import org.example.entity.Tweet;
import org.example.entity.User;


public class TweetRepository {
    private static String CREATE_TWEET_TABLE = """
            CREATE TABLE  IF NOT EXISTS TWEET (
            ID SERIAL PRIMARY KEY ,
            CONTENT VARCHAR(280) NOT NULL  ,
            USER_ID INTEGER NOT NULL REFERENCES user_account(id)  ,
            LIKE_COLUMN INTEGER  ,
            DISLIKE INTEGER,
            CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP )
                        
            """;

    public TweetRepository() {
        createTweet();
    }

    public void createTweet() {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_TWEET_TABLE);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static String INSERT_TWEET = """
            INSERT INTO TWEET(CONTENT,USER_ID)
            VALUES  (?,?)
            """;

    public Long insertTweet(String content, User user) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_TWEET, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, content);
            statement.setLong(2, user.getId());
            int rowAffected = statement.executeUpdate();
            if (rowAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    return id;
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String GET_CONTENT_FOR_USER = """
            SELECT ID,CONTENT FROM TWEET
            WHERE USER_ID = ?
            """;

    public List<Tweet> getContentForUser(long userId) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_CONTENT_FOR_USER);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Tweet> tweets = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String content = resultSet.getString("content");
                Tweet tweet = new Tweet(id, content);
                tweets.add(tweet);
            }
            return tweets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String DELETE_TWEET = """
            DELETE FROM TWEET 
            WHERE ID = ?
            """;

    public boolean deleteTweet(long id) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_TWEET);
            statement.setLong(1, id);
            int rowAffected = statement.executeUpdate();
            if (rowAffected > 0) {
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String SELECT_TWEETS = """
            SELECT * FROM TWEET
            """;

    public List<Tweet> getTweetsForAllUsers() {
        Connection connection = DatabaseConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_TWEETS);
            List<Tweet> tweets = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String content = resultSet.getString("content");
                Long userId = resultSet.getLong("USER_ID");
                Tweet tweet = new Tweet(id, content);
                tweet.setUserId(userId);
                tweets.add(tweet);

            }
            return tweets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String UPDATE_LIKE = """
            UPDATE TWEET
            SET LIKE_COLUMN = CASE
            WHEN LIKE_COLUMN IS NULL THEN 1
            ELSE LIKE_COLUMN + 1
            END
            WHERE ID = ?
            """;

    public boolean updateLike(long id) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_LIKE);
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String UPDATE_DISLIKE = """
            UPDATE TWEET
            SET DISLIKE = CASE
            WHEN DISLIKE IS NULL THEN 1
            ELSE DISLIKE + 1
            END
            WHERE ID = ?
            """;

    public boolean updateDislike(long id) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_DISLIKE);
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String UOADTE_CONTENT_WITH_TWEETID = """
            UPDATE TWEET SET CONTENT=?
            WHERE ID=?
             """;

    public  void updateContentByTweetId(String comment, Long tweetId) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UOADTE_CONTENT_WITH_TWEETID);
            statement.setString(1, comment);
            statement.setLong(2, tweetId);
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
