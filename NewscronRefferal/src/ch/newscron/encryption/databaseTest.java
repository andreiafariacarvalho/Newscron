/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.encryption;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Din
 */
public class databaseTest {
    
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    
    private static final String JDBCDriver = "jdbc:mysql://%s:%s/%s";
    private static final String server = "localhost";
    private static final String port = "3306";
    private static final String database = "try";
    private static final String DBurl = String.format(JDBCDriver, server, port, database);
    
    private static final String username = "root";
    private static final String password = "";
    
    public static void main(String[] args) {
        connect();
        createTableShortURLs();
//        insertShortURL(112323, "www.blewfah.ch");
        selectAllShortURLs();
        selectSingularCustomerShortURLs(123);
        disconnect();
    }
    
    public static void connect() {
        System.out.println("Connecting database...");
        try {
            connection = DriverManager.getConnection(DBurl, username, password);
            System.out.println("Database connected!");
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }        
    }
    
    public static void createTableShortURLs() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS ShortURLs(CustID Integer, ShortUrl CHAR(75), PRIMARY KEY(CustID, ShortUrl));");
        } catch (SQLException ex) {
            Logger.getLogger(databaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void insertShortURL(int CustId, String shortURL) {
        try {
            String sql = "INSERT INTO ShortURLs VALUES('%d', '%s');";
            sql = String.format(sql, CustId, shortURL);
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(databaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void selectAllShortURLs() {
        try {
            resultSet = statement.executeQuery("SELECT * FROM ShortURLs;");
            writeResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(databaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void selectSingularCustomerShortURLs(int CustId) {
        try {
            String sql = "SELECT * FROM ShortURLs WHERE CustID = '%d';";
            sql = String.format(sql, CustId);
            resultSet = statement.executeQuery(sql);
            writeResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(databaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void disconnect() {
        DbUtils.closeQuietly(connection, statement, resultSet);
    }
    
    private static void writeResultSet(ResultSet resultSet){
        try {
            while (resultSet.next()) {
                String CustID = resultSet.getString("CustID");
                System.out.println("CustID: " + CustID);
                String ShortUrl = resultSet.getString("ShortUrl");
                System.out.println("ShortUrl: " + ShortUrl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(databaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
