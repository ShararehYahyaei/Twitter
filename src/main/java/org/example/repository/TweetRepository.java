package org.example.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.example.config.DatabaseConnection;


public class TweetRepository {
    private static String CREATE_TWEET_TABLE = """
            CREATE TABLE  IF NOT EXISTS TWEET (
            ID SERIAL PRIMARY KEY ,
            CONTENT VARCHAR(280) NOT NULL  ,
            USER_ID SERIAL NOT NULL REFERENCES user_account(id)  ,
            LIKE_COLUMN INTEGER  ,
            DISLIKE INTEGER )
            """;

    public TweetRepository() {
        createTweet();
    }

    public void createTweet() {
     Connection connetion= DatabaseConnection.getConnection();
        try{
            PreparedStatement statement=connetion.prepareStatement(CREATE_TWEET_TABLE);
            statement.execute();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
