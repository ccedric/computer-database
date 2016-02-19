package com.excilys.formation.java.computerdb.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.computerdb.dao.DAO;
import com.excilys.formation.java.computerdb.dao.exception.ComputerDAOInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DAOSqlException;
import com.excilys.formation.java.computerdb.db.ConnectionFactory;
import com.excilys.formation.java.computerdb.db.DbUtil;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.exception.ComputerInvalidException;
import com.excilys.formation.java.computerdb.model.mapper.ComputerMapper;
import com.excilys.formation.java.computerdb.model.validation.ComputerValidator;
import com.excilys.formation.java.computerdb.order.OrderSearch;

import java.sql.Connection;


/**
 * Data Access Object for the class Computer
 * @author Cédric Cousseran
 *
 */
public class ComputerDAO implements DAO<Computer> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

	public ComputerDAO() {}

	@Override
	public int create(Computer obj) throws DatabaseConnectionException, ComputerDAOInvalidException, DAOSqlException {
		try {
			ComputerValidator.validate(obj);
		} catch (ComputerInvalidException e){
			LOGGER.error("Error while creating a new Computer, computer invalid ");
			throw new ComputerDAOInvalidException("Error while creating the coputer, computer invalid");
		}


		Connection connect = ConnectionFactory.getConnection();

		String sql = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES ( ?, ?, ?,?)";

		PreparedStatement statement=null;
		ResultSet rs = null;
		try {
			statement = connect.prepareStatement(sql);
			statement.setString(1, obj.getName());
			if(obj.getIntroduced()==null){
				statement.setNull(2,Types.TIMESTAMP);
			}else{
				statement.setTimestamp(2, Timestamp.valueOf(obj.getIntroduced().atStartOfDay()));
			}
			if(obj.getDiscontinued()==null){
				statement.setNull(3,Types.TIMESTAMP);
			}else{
				statement.setTimestamp(3, Timestamp.valueOf(obj.getDiscontinued().atStartOfDay()));
			}
			if (null==obj.getCompany() ){
				statement.setNull(4,Types.BIGINT);
			}else{
				statement.setInt(4, obj.getCompany().getId());
			}
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			if (rs.next()){
				int numero = rs.getInt(1);
				obj.setId(numero);
				LOGGER.info("New computer created, id {}, name {}, company {}, introduced date {}, discontinued date {}.", numero, obj.getName(), obj.getCompany(), obj.getIntroduced(),obj.getDiscontinued());
				return numero;
			}
			return 0;

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while creating the computer");
			throw new DAOSqlException("SQL error while creating the computer");
		} finally{
			DbUtil.close(statement);
			DbUtil.close(rs);
			DbUtil.close(connect);
		}
	}

	@Override
	public void delete(Computer obj) throws DatabaseConnectionException, ComputerNotFoundException, DAOSqlException {
		Connection connect = ConnectionFactory.getConnection();
		PreparedStatement statement = null;
		String sql = "DELETE FROM computer WHERE id=?";

		try {
			statement = connect.prepareStatement(sql);

			statement.setInt(1, obj.getId());
			int rows = statement.executeUpdate();
			if (rows>0){
				LOGGER.info("Computer deleted, id {}, name {}", obj.getId(), obj.getName());
			} else{
				LOGGER.info("Computer couldn't be deleted, check if he exists in the database, id {}, name {}", obj.getId(), obj.getName());
				throw new ComputerNotFoundException("Error while deleting the computer,computer not found");			
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Error while deleting the computer");
			throw new DAOSqlException("SQL error while deleting the computer");
		} finally{
			DbUtil.close(statement);
			DbUtil.close(connect);
		}
	}

	@Override
	public void update(Computer obj) throws DatabaseConnectionException, ComputerNotFoundException, DAOSqlException {
		String sql = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
		Connection connect = ConnectionFactory.getConnection();

		PreparedStatement statement = null;
		try {
			statement = connect.prepareStatement(sql);
			statement.setString(1, obj.getName());
			if(obj.getIntroduced()==null){
				statement.setNull(2,Types.TIMESTAMP);
			}else{
				statement.setTimestamp(2, Timestamp.valueOf(obj.getIntroduced().atStartOfDay()));
			}
			if(obj.getDiscontinued()==null){
				statement.setNull(3,Types.TIMESTAMP);
			}else{
				statement.setTimestamp(3, Timestamp.valueOf(obj.getDiscontinued().atStartOfDay()));
			}

			if (obj.getCompany()==null){
				statement.setNull(4,Types.BIGINT);
			}else{
				statement.setInt(4, obj.getCompany().getId());
			}
			statement.setInt(5, obj.getId());

			int rowsUpdated = statement.executeUpdate();

			if (rowsUpdated > 0) {
				LOGGER.info("Computer updated, id {}, name {}, company {}, introduced date {}, discontinued date {}.", obj.getId(), obj.getName(), obj.getCompany(), obj.getIntroduced(),obj.getDiscontinued());
			} else{
				LOGGER.error("Error while updating the computer, id: {}, name: {}",obj.getId(), obj.getName());
				throw new ComputerNotFoundException("Error while updating the computer,computer not found");					
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Error while updating the computer");
			throw new DAOSqlException("SQL error while updating the computer");
		} finally{
			DbUtil.close(statement);
			DbUtil.close(connect);
		}

	}

	@Override
	public Computer find(int id) throws DatabaseConnectionException, DAOSqlException, ComputerNotFoundException {
		Connection connect = ConnectionFactory.getConnection();

		ResultSet result = null;
		PreparedStatement statement = null;
		Computer computer;
		String sql = "SELECT computer.id as computerId, computer.name as computerName, computer.introduced, computer.discontinued, company.id AS companyId, company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id= company.id  WHERE computer.id=?";
		try {
			statement = connect.prepareStatement(sql);
			statement.setInt(1, id);
			result = statement.executeQuery();    
			if (result.next()){
				computer = ComputerMapper.fromResultSet(result);
				LOGGER.info("Computer found, id {}, name {}, company {}, introduced date {}, discontinued date {}.",  computer.getId(),computer.getName(), computer.getCompany(), computer.getIntroduced(),computer.getDiscontinued());
				return computer;		
			} else{
				LOGGER.info("No computer found with the id: {}.",  id);
				throw new ComputerNotFoundException("Error while updating the computer,computer not found");
			}


		} catch (SQLException e) {
			LOGGER.error("Error while finding the computer");
			e.printStackTrace();
			throw new DAOSqlException("SQL error while finding the computer");
		} finally{
			DbUtil.close(result);
			DbUtil.close(statement);
			DbUtil.close(connect);
		}
	}

	@Override
	public List<Computer> list() throws DatabaseConnectionException, DAOSqlException {
		Connection connect = ConnectionFactory.getConnection();
		ResultSet result = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			result = connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY
					).executeQuery(
							"SELECT computer.id as computerId, computer.name as computerName, computer.introduced, computer.discontinued, company.id AS companyId, company.name AS companyName FROM computer "+
									"LEFT JOIN company ON computer.company_id= company.id"							
							);  
			while (result.next()){
				if (result.getInt("companyId")==0){
					Computer computer = ComputerMapper.fromResultSet(result);

					computers.add(computer);
				} else{
					Computer computer = ComputerMapper.fromResultSet(result);
					computers.add(computer);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("Error sql while retrieving the list of computers");
			e.printStackTrace();
			throw new DAOSqlException("SQL error while finding the list of computer");
		} finally{
			DbUtil.close(result);
			DbUtil.close(connect);
		}
		LOGGER.info("List of computers found, size of the list: {}",computers.size());

		return computers;

	}

	@Override
	public List<Computer> listPage(int indexBegin, int pageSize) throws DatabaseConnectionException, DAOSqlException  {
		Connection connect = ConnectionFactory.getConnection();
		ResultSet result = null;
		List<Computer> computers = new ArrayList<Computer>();
		PreparedStatement statement = null;
		String sql = "SELECT computer.id as computerId, computer.name as computerName, computer.introduced, computer.discontinued, company.id AS companyId, company.name AS companyName FROM computer "+
				"LEFT JOIN company ON computer.company_id= company.id LIMIT ?, ? ";
		try {

			statement = connect.prepareStatement(sql);
			statement.setInt(1, indexBegin);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();    
			while (result.next()){
				Computer computer = ComputerMapper.fromResultSet(result);
				computers.add(computer);
			}
		} catch (SQLException e) {
			LOGGER.error("Error sql while retrieving the list of computers");
			e.printStackTrace();
			throw new DAOSqlException("SQL error while finding the list for one page of computer");

		} finally{
			DbUtil.close(result);
			DbUtil.close(connect);
		}
		LOGGER.info("List of computers found, size of the list: {}",computers.size());

		return computers;
	}

	@Override
	public List<Computer> listPageByName(int indexBegin, int pageSize, String name, OrderSearch order) throws DatabaseConnectionException, DAOSqlException  {
		Connection connect = ConnectionFactory.getConnection();
		ResultSet result = null;
		List<Computer> computers = new ArrayList<Computer>();
		PreparedStatement statement = null;
		StringBuilder request = new StringBuilder();
		request.append("SELECT computer.id AS computerId, computer.name AS computerName, computer.introduced, computer.discontinued, company.id AS companyId, company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id= company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY ");
		request.append(order.getColumn());
		request.append(" ");
		request.append(order.getOrder());
		request.append(" LIMIT ?, ? ");
		
		String sql = request.toString();
		try {

			statement = connect.prepareStatement(sql);
			statement.setInt(3, indexBegin);
			statement.setInt(4, pageSize);
			statement.setString(1, name+'%');
			statement.setString(2, name+'%');

			result = statement.executeQuery();    
			while (result.next()){
				Computer computer = ComputerMapper.fromResultSet(result);
				computers.add(computer);
			}
		} catch (SQLException e) {
			LOGGER.error("Error sql while retrieving the list of computers");
			e.printStackTrace();
			throw new DAOSqlException("SQL error while finding the page of computer with a search by name");

		} finally{
			DbUtil.close(result);
			DbUtil.close(connect);
		}
		LOGGER.info("List of computers found, size of the list: {}",computers.size());

		return computers;
	}

	@Override
	public List<Computer> findByName(String name) throws DatabaseConnectionException, DAOSqlException {
		Connection connect = ConnectionFactory.getConnection();

		ResultSet result = null;
		PreparedStatement statement = null;
		List<Computer> computers = new ArrayList<Computer>();
		String sql = "SELECT computer.id as computerId, computer.name as computerName, computer.introduced, computer.discontinued, company.id AS companyId, company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id= company.id  WHERE computer.name LIKE ?";
		try {
			statement = connect.prepareStatement(sql);
			statement.setString(1, name+'%');
			result = statement.executeQuery();    
			while (result.next()){
				try{	
					Computer computer = ComputerMapper.fromResultSet(result);
					computers.add(computer);
				}
				catch(Exception e){
					LOGGER.error("Error while finding computers by name");
					e.printStackTrace();
				}
			}


		} catch (SQLException e) {
			LOGGER.error("Error while finding computers by name");
			e.printStackTrace();
			throw new DAOSqlException("SQL error while finding a computer by name");

		} finally{
			DbUtil.close(result);
			DbUtil.close(statement);
			DbUtil.close(connect);
		}
		return computers;
	}

	@Override
	public int selectCount(String name) throws DatabaseConnectionException, DAOSqlException {
		Connection connect = ConnectionFactory.getConnection();
		ResultSet result = null;
		String sql = "SELECT COUNT(distinct computer.id) as countProduct FROM computer LEFT JOIN company ON computer.company_id= company.id WHERE computer.name LIKE ? OR company.name LIKE ?";
		PreparedStatement statement = null;

		try {
			statement = connect.prepareStatement(sql);
			statement.setString(1, name+'%');
			statement.setString(2, name+'%');
			result = statement.executeQuery();    
			if (result.next()){
				return result.getInt("countProduct");
			}
		} catch (SQLException e) {
			LOGGER.error("Error sql while retrieving the number of computers");
			e.printStackTrace();
			throw new DAOSqlException("SQL error while getting the number of computers");

		} finally{
			DbUtil.close(result);
			DbUtil.close(statement);
			DbUtil.close(connect);
		}

		return 0;
	}

}
