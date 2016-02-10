package com.excilys.formation.java.computerdb.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class to interact with the database
 * @author Cédric Cousseran
 *
 */
public class DbUtil {
    /**
     * Close a connection to the database
     * @param connection connection to the database
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * Close a statement
     * @param statement statement opened
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * Close a ResultSet
     * @param resultSet ResultSet
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
