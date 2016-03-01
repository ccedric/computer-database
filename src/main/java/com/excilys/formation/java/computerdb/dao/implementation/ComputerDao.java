package com.excilys.formation.java.computerdb.dao.implementation;

import com.excilys.formation.java.computerdb.dao.Dao;
import com.excilys.formation.java.computerdb.dao.exception.ComputerDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.db.DbUtil;
import com.excilys.formation.java.computerdb.db.TransactionManager;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.exception.ComputerInvalidException;
import com.excilys.formation.java.computerdb.model.mapper.ComputerMapper;
import com.excilys.formation.java.computerdb.model.validation.ComputerValidator;
import com.excilys.formation.java.computerdb.order.OrderSearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the class Computer.
 * 
 * @author Cédric Cousseran
 *
 */
public class ComputerDao implements Dao<Computer> {
  private static ComputerDao INSTANCE = new ComputerDao();
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

  private static final String deleteByCompanyQuery = "DELETE FROM computer where company_id = ?";
  private static final String createQuery = "INSERT INTO computer (name, introduced, "
      + "discontinued,company_id) VALUES ( ?, ?, ?,?)";
  private static final String deleteQuery = "DELETE FROM computer WHERE id=?";
  private static final String updateQuery = "UPDATE computer SET name=?, "
      + "introduced=?, discontinued=?, company_id=? WHERE id=?";
  private static final String findQuery = "SELECT computer.id as computerId, computer.name "
      + "as computerName, computer.introduced, computer.discontinued, company.id AS companyId,"
      + " company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id "
      + "= company.id  WHERE computer.id=?";
  private static final String listQuery = "SELECT computer.id as computerId, computer.name "
      + "as computerName, computer.introduced, computer.discontinued, company.id AS companyId,"
      + " company.name AS companyName FROM computer "
      + "LEFT JOIN company ON computer.company_id= company.id";
  private static final String listPageQuery = "SELECT computer.id as computerId, computer.name"
      + " as computerName, computer.introduced, computer.discontinued, company.id AS companyId,"
      + " company.name AS companyName FROM computer "
      + "LEFT JOIN company ON computer.company_id= company.id LIMIT ?, ? ";
  private static final String findByNameQuery = "SELECT computer.id as computerId, computer.name"
      + " as computerName, computer.introduced, computer.discontinued, company.id AS companyId,"
      + " company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id ="
      + " company.id  WHERE computer.name LIKE ?";
  private static final String selectCountQuery = "SELECT COUNT(distinct computer.id) as "
      + "countProduct FROM computer LEFT JOIN company ON computer.company_id= company.id"
      + " WHERE computer.name LIKE ? OR company.name LIKE ?";

  private ComputerDao() {
  }

  public static ComputerDao getInstance() {
    return INSTANCE;
  }

  /**
   * Delete all computers associated to the company.
   * 
   * @param obj
   *          the company
   */
  public void deleteByCompany(Company obj) {
    PreparedStatement statementComputer = null;

    try {
      statementComputer = TransactionManager.getInstance().get()
          .prepareStatement(deleteByCompanyQuery);
      statementComputer.setLong(1, obj.getId());
      statementComputer.executeUpdate();
    } catch (SQLException e) {
      LOGGER.error("Error while deleting the computers of a company, rolling back");
      throw new DaoSqlException("SQL error while deleting the computers of a company");
    } finally {
      DbUtil.close(statementComputer);
    }
  }

  @Override
  public int create(Computer obj)
      throws DatabaseConnectionException, ComputerDaoInvalidException, DaoSqlException {
    try {
      ComputerValidator.validate(obj);
    } catch (ComputerInvalidException e) {
      LOGGER.error("Error while creating a new Computer, computer invalid ");
      throw new ComputerDaoInvalidException("Error while creating the coputer, computer invalid");
    }

    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      statement = TransactionManager.getInstance().get().prepareStatement(createQuery);
      statement.setString(1, obj.getName());
      if (obj.getIntroduced() == null) {
        statement.setNull(2, Types.TIMESTAMP);
      } else {
        statement.setTimestamp(2, Timestamp.valueOf(obj.getIntroduced().atStartOfDay()));
      }
      if (obj.getDiscontinued() == null) {
        statement.setNull(3, Types.TIMESTAMP);
      } else {
        statement.setTimestamp(3, Timestamp.valueOf(obj.getDiscontinued().atStartOfDay()));
      }
      if (null == obj.getCompany()) {
        statement.setNull(4, Types.BIGINT);
      } else {
        statement.setLong(4, obj.getCompany().getId());
      }
      statement.executeUpdate();
      rs = statement.getGeneratedKeys();
      if (rs.next()) {
        int numero = rs.getInt(1);
        obj.setId(numero);
        LOGGER.info(
            "New computer created, id {}, name {}, company {},"
                + " introduced date {}, discontinued date {}.",
            numero, obj.getName(), obj.getCompany(), obj.getIntroduced(), obj.getDiscontinued());
        return numero;
      }
      return 0;

    } catch (Exception e) {
      LOGGER.error("Error while creating the computer");
      throw new DaoSqlException("SQL error while creating the computer");
    } finally {
      DbUtil.close(statement);
      DbUtil.close(rs);
    }
  }

  @Override
  public void delete(Computer obj)
      throws DatabaseConnectionException, ComputerNotFoundException, DaoSqlException {
    PreparedStatement statement = null;

    try {
      statement = TransactionManager.getInstance().get().prepareStatement(deleteQuery);

      statement.setLong(1, obj.getId());
      int rows = statement.executeUpdate();
      if (rows > 0) {
        LOGGER.info("Computer deleted, id {}, name {}", obj.getId(), obj.getName());
      } else {
        LOGGER.info(
            "Computer couldn't be deleted, check if he exists in the database, id {}, name {}",
            obj.getId(), obj.getName());
        throw new ComputerNotFoundException("Error while deleting the computer,computer not found");
      }
    } catch (SQLException e) {
      LOGGER.error("Error while deleting the computer");
      throw new DaoSqlException("SQL error while deleting the computer");
    } finally {
      DbUtil.close(statement);
    }
  }

  @Override
  public void update(Computer obj)
      throws DatabaseConnectionException, ComputerNotFoundException, DaoSqlException {
    PreparedStatement statement = null;
    try {
      statement = TransactionManager.getInstance().get().prepareStatement(updateQuery);
      statement.setString(1, obj.getName());
      if (obj.getIntroduced() == null) {
        statement.setNull(2, Types.TIMESTAMP);
      } else {
        statement.setTimestamp(2, Timestamp.valueOf(obj.getIntroduced().atStartOfDay()));
      }
      if (obj.getDiscontinued() == null) {
        statement.setNull(3, Types.TIMESTAMP);
      } else {
        statement.setTimestamp(3, Timestamp.valueOf(obj.getDiscontinued().atStartOfDay()));
      }

      if (obj.getCompany() == null) {
        statement.setNull(4, Types.BIGINT);
      } else {
        statement.setLong(4, obj.getCompany().getId());
      }
      statement.setLong(5, obj.getId());

      int rowsUpdated = statement.executeUpdate();

      if (rowsUpdated > 0) {
        LOGGER.info(
            "Computer updated, id {}, name {}, company {},"
                + " introduced date {}, discontinued date {}.",
            obj.getId(), obj.getName(), obj.getCompany(), obj.getIntroduced(),
            obj.getDiscontinued());
      } else {
        LOGGER.error("Error while updating the computer, id: {}, name: {}", obj.getId(),
            obj.getName());
        throw new ComputerNotFoundException("Error while updating the computer,computer not found");
      }
    } catch (SQLException e) {
      LOGGER.error("Error while updating the computer");
      throw new DaoSqlException("SQL error while updating the computer");
    } finally {
      DbUtil.close(statement);
    }

  }

  @Override
  public Computer find(long id)
      throws DatabaseConnectionException, DaoSqlException, ComputerNotFoundException {
    ResultSet result = null;
    PreparedStatement statement = null;
    Computer computer;
    try {
      statement = TransactionManager.getInstance().get().prepareStatement(findQuery);
      statement.setLong(1, id);
      result = statement.executeQuery();
      if (result.next()) {
        computer = ComputerMapper.fromResultSet(result);
        LOGGER.info(
            "Computer found, id {}, name {}, company {}, introduced date {}, discontinued date {}.",
            computer.getId(), computer.getName(), computer.getCompany(), computer.getIntroduced(),
            computer.getDiscontinued());
        return computer;
      } else {
        LOGGER.info("No computer found with the id: {}.", id);
        throw new ComputerNotFoundException("Error while updating the computer,computer not found");
      }

    } catch (SQLException e) {
      LOGGER.error("Error while finding the computer");
      throw new DaoSqlException("SQL error while finding the computer");
    } finally {
      DbUtil.close(result);
      DbUtil.close(statement);
    }
  }

  @Override
  public List<Computer> list() throws DatabaseConnectionException, DaoSqlException {
    ResultSet result = null;
    List<Computer> computers = new ArrayList<Computer>();

    try {
      result = TransactionManager.getInstance().get()
          .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
          .executeQuery(listQuery);
      while (result.next()) {
        if (result.getInt("companyId") == 0) {
          Computer computer = ComputerMapper.fromResultSet(result);

          computers.add(computer);
        } else {
          Computer computer = ComputerMapper.fromResultSet(result);
          computers.add(computer);
        }
      }
    } catch (SQLException e) {
      LOGGER.error("Error sql while retrieving the list of computers");
      throw new DaoSqlException("SQL error while finding the list of computer");
    } finally {
      DbUtil.close(result);
    }
    LOGGER.info("List of computers found, size of the list: {}", computers.size());

    return computers;

  }

  @Override
  public List<Computer> listPage(int indexBegin, int pageSize)
      throws DatabaseConnectionException, DaoSqlException {
    ResultSet result = null;
    List<Computer> computers = new ArrayList<Computer>();
    PreparedStatement statement = null;
    try {

      statement = TransactionManager.getInstance().get().prepareStatement(listPageQuery);
      statement.setInt(1, indexBegin);
      statement.setInt(2, pageSize);
      result = statement.executeQuery();
      while (result.next()) {
        Computer computer = ComputerMapper.fromResultSet(result);
        computers.add(computer);
      }
    } catch (SQLException e) {
      LOGGER.error("Error sql while retrieving the list of computers");
      throw new DaoSqlException("SQL error while finding the list for one page of computer");

    } finally {
      DbUtil.close(result);
    }
    LOGGER.info("List of computers found, size of the list: {}", computers.size());

    return computers;
  }

  @Override
  public List<Computer> listPageByName(int indexBegin, int pageSize, String name, OrderSearch order)
      throws DatabaseConnectionException, DaoSqlException {
    ResultSet result = null;
    List<Computer> computers = new ArrayList<Computer>();
    PreparedStatement statement = null;
    StringBuilder request = new StringBuilder();
    request.append(
        "SELECT computer.id AS computerId, computer.name AS computerName, computer.introduced,");
    request.append(
        " computer.discontinued, company.id AS companyId, company.name AS companyName FROM ");
    request.append("computer LEFT JOIN company ON computer.company_id= company.id WHERE ");
    request.append("computer.name LIKE ? OR company.name LIKE ? ORDER BY ISNULL(");
    request.append(order.getColumn());
    request.append("), ");
    request.append(order.getColumn());
    request.append(" ");
    request.append(order.getOrder());
    request.append(" LIMIT ?, ? ");

    String sql = request.toString();
    try {

      statement = TransactionManager.getInstance().get().prepareStatement(sql);
      statement.setInt(3, indexBegin);
      statement.setInt(4, pageSize);
      statement.setString(1, name + '%');
      statement.setString(2, name + '%');

      result = statement.executeQuery();
      while (result.next()) {
        Computer computer = ComputerMapper.fromResultSet(result);
        computers.add(computer);
      }
    } catch (SQLException e) {
      LOGGER.error("Error sql while retrieving the list of computers");
      throw new DaoSqlException(
          "SQL error while finding the page of computer with a search by name");

    } finally {
      DbUtil.close(result);
    }
    LOGGER.info("List of computers found, size of the list: {}", computers.size());

    return computers;
  }

  @Override
  public List<Computer> findByName(String name)
      throws DatabaseConnectionException, DaoSqlException {
    ResultSet result = null;
    PreparedStatement statement = null;
    List<Computer> computers = new ArrayList<Computer>();
    try {
      statement = TransactionManager.getInstance().get().prepareStatement(findByNameQuery);
      statement.setString(1, name + '%');
      result = statement.executeQuery();
      while (result.next()) {
        try {
          Computer computer = ComputerMapper.fromResultSet(result);
          computers.add(computer);
        } catch (Exception e) {
          LOGGER.error("Error while finding computers by name");
        }
      }

    } catch (SQLException e) {
      LOGGER.error("Error while finding computers by name");
      throw new DaoSqlException("SQL error while finding a computer by name");

    } finally {
      DbUtil.close(result);
      DbUtil.close(statement);
    }
    return computers;
  }

  @Override
  public int selectCount(String name) throws DatabaseConnectionException, DaoSqlException {
    ResultSet result = null;
    PreparedStatement statement = null;

    try {
      statement = TransactionManager.getInstance().get().prepareStatement(selectCountQuery);
      statement.setString(1, name + '%');
      statement.setString(2, name + '%');
      result = statement.executeQuery();
      if (result.next()) {
        return result.getInt("countProduct");
      }
    } catch (SQLException e) {
      LOGGER.error("Error sql while retrieving the number of computers");
      throw new DaoSqlException("SQL error while getting the number of computers");

    } finally {
      DbUtil.close(result);
      DbUtil.close(statement);
    }

    return 0;
  }

}
