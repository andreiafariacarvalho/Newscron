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
    
    public static void main(String[] args) {
        connect();
        disconnect();
    }
    
    public static void connect() {
        String url = "jdbc:mysql://localhost:3307/bla1";
        String username = "root";
        String password = "";
        System.out.println("Connecting database...");
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from EMPLOYEE");
            writeResultSet(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }        
    }
    
    public static void disconnect() {
        DbUtils.closeQuietly(connection, statement, resultSet);
    }
    
    private static void writeResultSet(ResultSet resultSet){
        try {
            while (resultSet.next()) {
                String user = resultSet.getString("NAME");
                System.out.println("NAME: " + user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(databaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
