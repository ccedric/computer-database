package com.excilys.formation.java.computerdb.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.excilys.formation.java.computerdb.dao.exception.DAOSqlException;

/**
 * @author Cédric Cousseran
 *
 */
public class TransactionManager {
	private static TransactionManager INSTANCE = new TransactionManager();
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>(){
		protected Connection initialValue() {
			return ConnectionFactory.getConnection();
		}
	};

	private TransactionManager(){}

	public static TransactionManager getInstance(){
		return INSTANCE;
	}

	public static void set(Connection connection) {
		threadLocal.set(connection);
	}

	public Connection get() {
		return threadLocal.get();
	}

	public void remove() {
		DbUtil.close(threadLocal.get());
		threadLocal.remove();
	}
	
	public void commit() {
		try {
			get().commit();
		} catch (SQLException e) {
			throw new DAOSqlException();
		}
	}
	
	public void rollback() {
		try {
			get().rollback();
		} catch (SQLException e) {
			throw new DAOSqlException();
		}
	}
	
	public void setAutoCommit(boolean commit) {
		try {
			get().setAutoCommit(commit);
		} catch (SQLException e) {
			throw new DAOSqlException();
		}
	}
}
