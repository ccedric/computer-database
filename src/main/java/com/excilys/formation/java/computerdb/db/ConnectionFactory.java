package com.excilys.formation.java.computerdb.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Create a connection to the database, singleton pattern
 * @author Cédric Cousseran
 *
 */
public class ConnectionFactory {
	private static ConnectionFactory instance = new ConnectionFactory();
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

	private Connection createConnection() throws DatabaseConnectionException {
		Connection connection = null;
		
		Properties prop = new Properties();
		InputStream input = null;
		String url=new String();
		String user = new String();
		String password = new String();
		
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("db.properties"));

			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password  = prop.getProperty("password");

		} catch (IOException ex) {
			ex.printStackTrace();
			throw new DatabaseConnectionException("Error while reading db properties file");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new DatabaseConnectionException("Error while reading db properties file");
				}
			}
		}
		
		
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseConnectionException("Couldn't connect to the database, check URL/USER/PASS");
		}
		return connection;
	}   

	/**
	 * Create and return a new connection to the database
	 * @return a connection to the database
	 * @throws DatabaseConnectionException 
	 */
	public static Connection getConnection() throws DatabaseConnectionException {
		return instance.createConnection();
	}
}
