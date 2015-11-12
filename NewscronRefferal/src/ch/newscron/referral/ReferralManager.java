/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.referral;

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
 * @author Din & Andreia
 */
public class ReferralManager {
   
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
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return null;
    }
    
    /**
     * Given a customer id and a generated short URL, inserts these two as a new row in the ShortURL table of the database
     * @param CustId an int representing the unique customer id
     * @param shortURL a String representing the short goo.gl generated URL
     */
    public static boolean insertShortURL(int CustId, String shortURL) {
        try {
            System.out.println("custID - " + CustId);
            System.out.println("shortUrl - " + shortURL);
            Connection connection = connect();
            PreparedStatement query = null;
            query = connection.prepareStatement("INSERT IGNORE INTO ShortURL (custId, shortUrl) VALUES(?, ?)");
            
            query.setInt(1, CustId);
            query.setString(2, shortURL);
            int newShortURL = query.executeUpdate();
            disconnect(connection, query);
            return newShortURL == 1;
        }  catch (Exception ex) {
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Calls the database to query for all rows in ShortURL table of database
     * @return a List of ShortURLDataHolder objects, consisting of all shortURL entries in table
     */
    public static List<ShortURLDataHolder> selectAllShortURLs() {
        try {
            Connection connection = connect();
            PreparedStatement query = null;
            ResultSet rs = null;
            query = connection.prepareStatement("SELECT * FROM ShortURL");
            rs = query.executeQuery();
            List<ShortURLDataHolder> shortURLList = writeResultSetToList(rs);
            disconnect(connection, query, rs);
            return shortURLList;

        } catch (Exception ex) {
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Calls the database to query for all rows in ShortURL table of database in relation to CustId
     * @param CustId an int representing the unique customer id
     * @return a List of ShortURLDataHolder objects, consisting of the shortURL table entries corresponding to the given CustId 
     */
    public static List<ShortURLDataHolder> selectSingularCustomerShortURLs(int CustId) {
        try {
            Connection connection = connect();
            PreparedStatement query = null;
            ResultSet rs = null;
            query = connection.prepareStatement("SELECT * FROM ShortURL WHERE custId = ?");
            query.setInt(1, CustId);
            rs = query.executeQuery();
            List<ShortURLDataHolder> shortURLList = writeResultSetToList(rs);
            disconnect(connection, query, rs);
            return shortURLList;
        } catch (Exception ex) {
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
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
    
    /**
     * Helper function: given a resultSet, processes the data into a list of ShortURLDataHolder objects
     * @param resultSet a ResultSet returned from a database query
     * @return a List of ShortURLDataHolder objects
     */
    private static List<ShortURLDataHolder> writeResultSetToList(ResultSet resultSet) {
        List<ShortURLDataHolder> shortURLList = new ArrayList<>(); 
        try {
            ShortURLDataHolder newShortURL;
            while (resultSet.next()) {
                newShortURL = new ShortURLDataHolder(Integer.parseInt(resultSet.getString("custId")), resultSet.getString("shortUrl"));
                shortURLList.add(newShortURL);     
            }
            return shortURLList;
        } catch (SQLException ex) {
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
