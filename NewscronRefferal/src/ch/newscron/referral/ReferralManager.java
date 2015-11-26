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
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Given a user id and a generated short URL, inserts these two as a new row in the referralURL table of the database
     * @param userId an long representing the unique user id
     * @param referralURL a String representing the short goo.gl generated URL
     */
    public static boolean insertReferralURL(long userId, String referralURL) {
        Connection connection = null;
        PreparedStatement query = null;
        try {
            connection = connect();
            query = connection.prepareStatement("INSERT IGNORE INTO referralURL (userId, referralUrl) VALUES(?, ?)");
            query.setLong(1, userId);
            query.setString(2, referralURL);
            int newReferralURL = query.executeUpdate();
            return newReferralURL == 1;
        } catch (Exception ex) {
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect(connection, query);
        }
        return false;
    }
    
    /**
     * Calls the database to query for all rows in referralURL table of database
     * @return a List of UserReferralURL objects, consisting of all referralURL entries in table
     */
    public static List<UserReferralURL> getAllReferralURLs() {
        Connection connection = null;
        PreparedStatement query = null;
        ResultSet rs = null;
        try {
            connection = connect();
            query = connection.prepareStatement("SELECT * FROM referralURL");
            rs = query.executeQuery();
            List<UserReferralURL> referralURLList = parseResultSet(rs);
            return referralURLList;
        } catch (Exception ex) {
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect(connection, query, rs);
        }
        return null;
    }
    
    /**
     * Calls the database to query for all rows in referralURL table of database in relation to userId
     * @param userId a long representing the unique user id
     * @return a List of UserReferralURL objects, consisting of the referralURL table entries corresponding to the given userId 
     */
    public static List<UserReferralURL> getUserReferralURLs(long userId) {
        Connection connection = null;
        PreparedStatement query = null;
        ResultSet rs = null;
        try {
            connection = connect();
            query = connection.prepareStatement("SELECT * FROM referralURL WHERE userId = ?");
            query.setLong(1, userId);
            rs = query.executeQuery();
            List<UserReferralURL> referralURLList = parseResultSet(rs);
            return referralURLList;
        } catch (Exception ex) {
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect(connection, query, rs);
        }
        return null;
    }
    
    public static int numberOfRegisteredUsers(String referralURL) {
        Connection connection = null;
        PreparedStatement query = null;
        ResultSet rs = null;
        try {
            connection = connect();
            query = connection.prepareStatement("SELECT COUNT(*) as total FROM User, referralURL WHERE User.campaignId = referralURL.id AND referralURL.referralUrl = ?");
            query.setString(1, referralURL);
            rs = query.executeQuery();
            rs.next();
            int totalNumbUsers = rs.getInt("total");
            return totalNumbUsers;
        } catch (Exception ex) {
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect(connection, query, rs);
        }
        return -1;
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
     * Helper function: given a resultSet, processes the data into a list of UserReferralURL objects
     * @param resultSet a ResultSet returned from a database query
     * @return a List of UserReferralURL objects
     */
    private static List<UserReferralURL> parseResultSet(ResultSet resultSet) {
        List<UserReferralURL> referralURLList = new ArrayList<>(); 
        try {
            while (resultSet.next()) {
                UserReferralURL newReferralURL = new UserReferralURL(Long.parseLong(resultSet.getString("userId")), resultSet.getString("referralUrl"));
                referralURLList.add(newReferralURL);     
            }
            return referralURLList;
        } catch (SQLException ex) {
            Logger.getLogger(ReferralManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
