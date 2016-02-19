package com.excilys.formation.java.computerdb.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;


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
	private BoneCP connectionPool; 

	//private constructor
	private ConnectionFactory() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		String url=new String();
		String user = new String();
		String password = new String();
		String minConnection=new String();
		String maxConnection = new String();
		String nbPartition = new String();

		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("db.properties"));

			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password  = prop.getProperty("password");
			minConnection = prop.getProperty("minConnection");
			maxConnection = prop.getProperty("maxConnection");
			nbPartition  = prop.getProperty("nbPartition");

		} catch (IOException ex) {
			ex.printStackTrace();
			throw new DatabaseConnectionException("Error while reading db properties file");
		} 		
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl(url);
		config.setUsername(user);
		config.setPassword(password);
		
		config.setMinConnectionsPerPartition(Integer.parseInt(minConnection));
		config.setMaxConnectionsPerPartition(Integer.parseInt(maxConnection));
		config.setPartitionCount(Integer.parseInt(nbPartition));
		try {
			this.connectionPool = new BoneCP(config);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseConnectionException("Couldn't connect to the database, check URL/USER/PASS");
		}

	}

	private Connection createConnection() throws DatabaseConnectionException {
		Connection connection = null;
		
		
		try {
			connection = connectionPool.getConnection();
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
