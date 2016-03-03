package com.excilys.formation.java.computerdb.dao.implementation;

import com.excilys.formation.java.computerdb.dao.ComputerDao;
import com.excilys.formation.java.computerdb.dao.exception.ComputerDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.exception.ComputerInvalidException;
import com.excilys.formation.java.computerdb.model.mapper.ComputerMapper;
import com.excilys.formation.java.computerdb.model.validation.ComputerValidator;
import com.excilys.formation.java.computerdb.order.OrderSearch;
import com.mysql.jdbc.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * Data Access Object for the class Computer.
 * 
 * @author Cédric Cousseran
 *
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {

  @Autowired
  DataSource dataSource;

  @Autowired
  ComputerMapper computerMapper;

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

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
  private static final String findByNameQuery = "SELECT computer.id as computerId, computer.name"
      + " as computerName, computer.introduced, computer.discontinued, company.id AS companyId,"
      + " company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id ="
      + " company.id  WHERE computer.name LIKE ?";
  private static final String selectCountQuery = "SELECT COUNT(distinct computer.id) as "
      + "countProduct FROM computer LEFT JOIN company ON computer.company_id= company.id"
      + " WHERE computer.name LIKE ? OR company.name LIKE ?";

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * Delete all computers associated to the company.
   * 
   * @param obj
   *          the company
   */
  @Override
  public void deleteByCompany(Company obj) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    PreparedStatementCreator preparedStatement = new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(deleteByCompanyQuery);
        statement.setLong(1, obj.getId());
        return statement;
      }
    };
    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(preparedStatement, holder);
  }

  @Override
  public long create(Computer obj) {
    try {
      ComputerValidator.validate(obj);
    } catch (ComputerInvalidException e) {
      LOGGER.error("Error while creating a new Computer, computer invalid ");
      throw new ComputerDaoInvalidException("Error while creating the coputer, computer invalid");
    }

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    PreparedStatementCreator preparedStatement = new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(createQuery,
            Statement.RETURN_GENERATED_KEYS);
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
        return statement;
      }
    };
    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(preparedStatement, holder);
    LOGGER.info("Computer created with name {}", obj.getName());
    return holder.getKey().longValue();
  }

  @Override
  public void delete(Computer obj) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.update(deleteQuery, obj.getId());
    LOGGER.info("Computer deleted, id {}, name {}", obj.getId(), obj.getName());
  }

  @Override
  public void update(Computer obj) {
    try {
      ComputerValidator.validate(obj);
    } catch (ComputerInvalidException e) {
      LOGGER.error("Error while updating the computer, computer invalid ");
      throw new ComputerDaoInvalidException("Error while updating the computer, computer invalid");
    }
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    PreparedStatementCreator preparedStatement = new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(updateQuery);
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
        statement.setLong(5, obj.getId());
        return statement;
      }
    };
    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(preparedStatement, holder);
  }

  @Override
  public Computer find(long id) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    try {
      Computer computer = jdbcTemplate.queryForObject(findQuery, new Object[] { id },
          new RowMapper<Computer>() {
            public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
              return computerMapper.fromResultSet(rs);
            }
          });

      LOGGER.info(
          "Computer found, id {}, name {}, company {}, introduced date {}, discontinued date {}.",
          computer.getId(), computer.getName(), computer.getCompany(), computer.getIntroduced(),
          computer.getDiscontinued());
      return computer;
    } catch (EmptyResultDataAccessException e) {
      throw new ComputerNotFoundException("Computer not found with the id " + id);
    }

  }

  @Override
  public List<Computer> list() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    List<Computer> computers = jdbcTemplate.queryForObject(listQuery,
        new RowMapper<List<Computer>>() {
          public List<Computer> mapRow(ResultSet rs, int rowNum) throws SQLException {
            List<Computer> computers = new ArrayList<Computer>();
            while (rs.next()) {
              Computer computer = computerMapper.fromResultSet(rs);
              computers.add(computer);

            }
            return computers;
          }
        });

    return computers;
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public List<Computer> listPageByName(int indexBegin, int pageSize, String name,
      OrderSearch order) {
    NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    String query = "SELECT computer.id AS computerId, computer.name AS computerName,"
        + " computer.introduced, computer.discontinued, company.id AS companyId, company.name AS"
        + " companyName FROM computer LEFT JOIN company ON computer.company_id= company.id WHERE"
        + " computer.name LIKE :nameComputer OR company.name LIKE :nameCompany ORDER BY ISNULL( "
        + ":orderIsNull), :orderColumn " + order.getOrder() + " LIMIT :indexBegin, :pageSize";

    Map namedParameters = new HashMap();
    namedParameters.put("nameComputer", name + '%');
    namedParameters.put("nameCompany", name + '%');
    namedParameters.put("orderIsNull", order.getColumn());
    namedParameters.put("orderColumn", order.getColumn());
    namedParameters.put("indexBegin", indexBegin);
    namedParameters.put("pageSize", pageSize);

    List<Computer> computers = (List<Computer>) jdbcTemplate.queryForObject(query, namedParameters,
        new RowMapper<List<Computer>>() {
          public List<Computer> mapRow(ResultSet rs, int rowNum) throws SQLException {
            List<Computer> computers = new ArrayList<Computer>();
            while (rs.next()) {
              Computer computer = computerMapper.fromResultSet(rs);
              computers.add(computer);

            }
            return computers;
          }
        });
    LOGGER.info("List of computers found, size of the list: {}", computers.size());
    return computers;
  }

  @Override
  public List<Computer> findByName(String name) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    try {
      List<Computer> computers = jdbcTemplate.queryForObject(findByNameQuery, new Object[] { name },
          new RowMapper<List<Computer>>() {
            public List<Computer> mapRow(ResultSet rs, int rowNum) throws SQLException {
              List<Computer> computers = new ArrayList<Computer>();
              while (rs.next()) {
                Computer computer = computerMapper.fromResultSet(rs);
                computers.add(computer);

              }
              return computers;
            }
          });

      return computers;
    } catch (EmptyResultDataAccessException e) {
      throw new ComputerNotFoundException("Computer not found with the name " + name);
    }
  }

  @Override
  public int selectCount(String name) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    return jdbcTemplate.queryForObject(selectCountQuery, int.class, name + '%', name + '%');
  }

}
