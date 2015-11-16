/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private static final String port = "3306";
    private static final String database = "try";
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
            System.out.println("DBurl: " + DBurl);
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
        try {
            Connection connection = connect();
            PreparedStatement query = null;
            ResultSet rs = null;
            query = connection.prepareStatement("SELECT id FROM ShortURL WHERE shortUrl=?");
            query.setString(1, shortURL);
            rs = query.executeQuery();
            rs.next();
            int shortURLId = Integer.parseInt(rs.getString("id"));
            disconnect(connection, query, rs);
            
            connection = connect();
            query = null;
            query = connection.prepareStatement("INSERT INTO User VALUES(DEFAULT, ?, ?, ?, ?)");
            query.setString(1, name);
            query.setString(2, lastName);
            query.setString(3, email);
            query.setInt(4, shortURLId);
            query.executeUpdate();
            disconnect(connection, query);
        }  catch (Exception ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
