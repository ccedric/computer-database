package com.excilys.formation.java.computerdb.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


/**
 * Base manager for Spring datasource.
 * 
 * @author Cédric Cousseran
 *
 */
@Component
public class BaseManager {
  @Autowired
  private DataSource dataSource;

  public BaseManager() {
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  protected final Connection getConnection() throws SQLException {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("pop");
    }
  }
}
