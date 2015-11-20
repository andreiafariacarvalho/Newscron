/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.registration;

import ch.newscron.referral.ReferralManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author andreiafariacarvalho
 */
public class UserRegistration {
    
    //JDBC driver name and database URL construction
    private static final String JDBCDriver = "jdbc:mysql://%s:%s/%s";
    private static final String server = "localhost";
    private static final String port = "3307";
    private static final String database = "bla1";
    private static final String DBurl = String.format(JDBCDriver, server, port, database);
    
    //Credentials for database
    private static final String username = "root";
    private static final String password = "";
    
    /**
     * Provides a connection to the database specified (see fields)
     * @return a Connection for the database
     */
    public static Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance(); 
            Connection connection = DriverManager.getConnection(DBurl, username, password);
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database! ", e);
        }  catch (Exception ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return null;
    }
    
    public static void insertUser(String name, String lastName, String email, String shortURL) {
        long shortUrlId = getShortUrlId(shortURL);
        if (shortUrlId == -1) {
            //check if shortUrlId exists
        } else{
            registerUserByShortURL(name, lastName, email, shortUrlId);
        }
        
    }
    
    public static long getShortUrlId(String shortURL) {
        Connection connection = null;
        PreparedStatement query = null;
        ResultSet rs = null;
        try {
            connection = connect();
            query = connection.prepareStatement("SELECT id FROM ShortURL WHERE shortUrl=?");
            query.setString(1, shortURL);
            rs = query.executeQuery();
            rs.next();
            long shortURLId = rs.getLong("id");           
            return shortURLId;
        }  catch (Exception ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect(connection, query, rs);
        }  
        return -1;
    }
    
    public static void registerUserByShortURL(String name, String lastName, String email, long shortUrlId) {
        Connection connection = null;
        PreparedStatement query = null;
        ResultSet rs = null;
        try {
            connection = connect();
            query = connection.prepareStatement("INSERT INTO User (name, lastName, email, campaignId) VALUES (?, ?, ?, ?)");
            query.setString(1, name);
            query.setString(2, lastName);
            query.setString(3, email);
            query.setLong(4, shortUrlId);
            query.executeUpdate();
        }  catch (Exception ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect(connection, query, rs);
        }      
    }
    
    public static List<User> getAllUsers() {
        Connection connection = null;
        PreparedStatement query = null;
        ResultSet rs = null;
        try {
            connection = connect();
            query = connection.prepareStatement("SELECT * FROM ShortURL, User WHERE User.campaignId=ShortURL.id");
            rs = query.executeQuery();
            List<User> userList = parseResultSet(rs);

            return userList;
        } catch (Exception ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect(connection, query, rs);
        }
        return null;
    }
    
    /**
     * Provided a connection, statement and resultSet, closes all of these by using DbUtils
     * @param connection an open Connection to a database
     * @param statement an object needed to execute a database query/update
     * @param resultSet the returned information of a query
     */
    public static void disconnect(Connection connection, Statement statement, ResultSet resultSet) {
        DbUtils.closeQuietly(connection, statement, resultSet);
    }
    
    /**
     * Provided a connection and statement closes all of these by using DbUtils
     * @param connection an open Connection to a database
     * @param statement an object needed to execute a database query/update
     */
    public static void disconnect(Connection connection, Statement statement) {
        disconnect(connection, statement, null);
    }
    
    private static List<User> parseResultSet(ResultSet resultSet) {
        List<User> userList = new ArrayList<>(); 
        try {      
            while (resultSet.next()) {
                User newUser = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getString("email"), resultSet.getLong("campaignId"), resultSet.getString("custID"), resultSet.getString("shortURL"));
                userList.add(newUser);     
            }
            return userList;
        } catch (SQLException ex) {
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
