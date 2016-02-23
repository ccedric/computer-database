package com.excilys.formation.java.computerdb.db;

import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * TransactionManager used to have a connection in a ThreadLocal.
 * @author Cédric Cousseran
 *
 */
public class TransactionManager {
  private static TransactionManager INSTANCE = new TransactionManager();
  private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>() {
    protected Connection initialValue() {
      return ConnectionFactory.getConnection();
    }
  };

  private TransactionManager() {
  }

  public static TransactionManager getInstance() {
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

  /**
   * Commit if it is a transaction.
   */
  public void commit() {
    try {
      get().commit();
    } catch (SQLException e) {
      throw new DaoSqlException();
    }
  }

  /**
   * Rollback if it is a transaction.
   */
  public void rollback() {
    try {
      get().rollback();
    } catch (SQLException e) {
      throw new DaoSqlException();
    }
  }

  /**
   * Change the auto commit value.
   * @param commit auto commi value
   */
  public void setAutoCommit(boolean commit) {
    try {
      get().setAutoCommit(commit);
    } catch (SQLException e) {
      throw new DaoSqlException();
    }
  }
}
