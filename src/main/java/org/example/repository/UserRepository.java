package org.example.repository;

import org.example.config.DatabaseConnection;
import org.example.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    private static String CREATE_TABLE_USER = """
            CREATE TABLE IF NOT EXISTS USER_ACCOUNT(
            ID SERIAL PRIMARY KEY NOT NULL ,
            USERNAME VARCHAR(50) NOT NULL UNIQUE,
            PASSWORD VARCHAR(50) NOT NULL ,
            EMAIL VARCHAR(50) NOT NULL UNIQUE,
            BIO VARCHAR(50) NOT NULL ,
            DISPLAY_NAME VARCHAR(50) NOT NULL ,
            CREATE_USER TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL )
            """;

    public UserRepository() {
        createTable();
    }

    public void createTable() {
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(CREATE_TABLE_USER);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String INIT_DATA_USER = """
            INSERT INTO USER_ACCOUNT(USERNAME,PASSWORD,EMAIL,BIO,DISPLAY_NAME)
            VALUES (?,?,?,?,?)
            """;

    public void save(User user) {
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(INIT_DATA_USER);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getBio());
            statement.setString(5, user.getDisplayName());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    private static String CHECK_EXISTED_USER = """
            SELECT COUNT(ID) FROM USER_ACCOUNT WHERE USERNAME=?
            """;


    public boolean checkExistedUser(String userName) {
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(CHECK_EXISTED_USER);
            statement.setString(1, userName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Long count = rs.getLong(1);
                if (count > 0) {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static String CHECK_EXISTED_USER_BY_EMAIL = """     
            SELECT COUNT(ID) FROM USER_ACCOUNT WHERE EMAIL=?
            """;


    public boolean checkExistEmail(String email) {
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(CHECK_EXISTED_USER_BY_EMAIL);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Long count = rs.getLong(1);
                if (count > 0) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    private static String LOGIN_WITH_USERNAME_PASSWORD = """
            SELECT * FROM USER_ACCOUNT
             WHERE USERNAME=? AND PASSWORD=?
            """;

    public User login(String username, String password) {
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(LOGIN_WITH_USERNAME_PASSWORD);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("USERNAME");
                String pass = rs.getString("PASSWORD");
                String email = rs.getString("EMAIL");
                String bio = rs.getString("BIO");
                String displayName = rs.getString("DISPLAY_NAME");
                User user = new User(name, pass, email, bio, displayName);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String UPDATE_USER = """
            UPDATE USER_ACCOUNT SET
            USERNAME=?,
            PASSWORD=?,
            BIO=?,
            DISPLAY_NAME=?
            WHERE EMAIL=?
            """;


    public User update(User user) {
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getBio());
            statement.setString(4, user.getDisplayName());
            statement.setString(5, user.getEmail());
            statement.execute();
            statement.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String SELECT_ALL_INFORMATION_FOR_ONE_USER = """
            SELECT * FROM USER_ACCOUNT
            WHERE EMAIL=?
            """;


    public User getInformationForOneUser(String email) {
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(SELECT_ALL_INFORMATION_FOR_ONE_USER);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("USERNAME");
                String pass = rs.getString("PASSWORD");
                String bio = rs.getString("BIO");
                String displayName = rs.getString("DISPLAY_NAME");
                User user = new User(name, pass, email, bio, displayName);
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
