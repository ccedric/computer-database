package com.excilys.formation.java.computerdb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Create a connection to the database, singleton pattern
 * @author Cédric Cousseran
 *
 */
public class ConnectionFactory {
	private static ConnectionFactory instance = new ConnectionFactory();
	/**
	 * url to the database
	 */
	public static final String URL = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";
	/**
	 * user in the database
	 */
	public static final String USER = "admincdb";
	/**
	 * password of the user
	 */
	public static final String PASSWORD = "qwerty1234";
	/**
	 * driver to connect to the database
	 */
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 

	//private constructor
	private ConnectionFactory() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to Connect to Database.");
		}
		return connection;
	}   

	/**
	 * Create and return a new connection to the database
	 * @return a connection to the database
	 */
	public static Connection getConnection() {
		return instance.createConnection();
	}
}
