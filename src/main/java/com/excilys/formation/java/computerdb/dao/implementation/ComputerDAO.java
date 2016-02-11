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
import com.excilys.formation.java.computerdb.db.ConnectionFactory;
import com.excilys.formation.java.computerdb.db.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.db.DbUtil;
import com.excilys.formation.java.computerdb.mapper.ComputerMapper;
import com.excilys.formation.java.computerdb.model.Computer;
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
	public int create(Computer obj) throws DatabaseConnectionException {
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
				statement.setTimestamp(2, Timestamp.valueOf(obj.getIntroduced()));
			}
			if(obj.getDiscontinued()==null){
				statement.setNull(3,Types.TIMESTAMP);
			}else{
				statement.setTimestamp(3, Timestamp.valueOf(obj.getDiscontinued()));
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
				System.out.println(numero);
				obj.setId(numero);
				LOGGER.info("New computer created, id {}, name {}, company {}, introduced date {}, discontinued date {}.", numero, obj.getName(), obj.getCompany(), obj.getIntroduced(),obj.getDiscontinued());
				return numero;
			}
			return 0;

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while creating the computer");
			return 0;
		} finally{
			DbUtil.close(statement);
			DbUtil.close(rs);
			DbUtil.close(connect);
		}
	}

	@Override
	public boolean delete(Computer obj) throws DatabaseConnectionException {
		Connection connect = ConnectionFactory.getConnection();
		PreparedStatement statement = null;
		String sql = "DELETE FROM computer WHERE id=?";

		try {
			statement = connect.prepareStatement(sql);

			statement.setInt(1, obj.getId());
			int rows = statement.executeUpdate();
			if (rows>0){
				LOGGER.info("Computer deleted, id {}, name {}", obj.getId(), obj.getName());
				return true;
			} else{
				LOGGER.info("Computer couldn't be deleted, check if he exists in the database, id {}, name {}", obj.getId(), obj.getName());
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Error while deleting the computer");
			return false;
		} finally{
			DbUtil.close(statement);
			DbUtil.close(connect);
		}
	}

	@Override
	public boolean update(Computer obj) throws DatabaseConnectionException {
		String sql = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
		Connection connect = ConnectionFactory.getConnection();

		PreparedStatement statement = null;
		try {
			statement = connect.prepareStatement(sql);
			statement.setString(1, obj.getName());
			if(obj.getIntroduced()==null){
				statement.setNull(2,Types.TIMESTAMP);
			}else{
				statement.setTimestamp(2, Timestamp.valueOf(obj.getIntroduced()));
			}
			if(obj.getDiscontinued()==null){
				statement.setNull(3,Types.TIMESTAMP);
			}else{
				statement.setTimestamp(3, Timestamp.valueOf(obj.getDiscontinued()));
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
				return true;
			}
			LOGGER.error("Error while updating the computer, id: {}, name: {}",obj.getId(), obj.getName());
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Error while updating the computer");
			return false;
		} finally{
			DbUtil.close(statement);
			DbUtil.close(connect);
		}

	}

	@Override
	public Computer find(int id) throws DatabaseConnectionException {
		Connection connect = ConnectionFactory.getConnection();

		ResultSet result = null;
		PreparedStatement statement = null;
		Computer computer;
		String sql = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id AS companyId, company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id= company.id  WHERE computer.id=?";
		try {
			statement = connect.prepareStatement(sql);
			statement.setInt(1, id);
			result = statement.executeQuery();    
			if (result.next()){
				computer = ComputerMapper.map(result);
				LOGGER.info("Computer found, id {}, name {}, company {}, introduced date {}, discontinued date {}.",  computer.getId(),computer.getName(), computer.getCompany(), computer.getIntroduced(),computer.getDiscontinued());
				return computer;		
			} else{
				LOGGER.info("No computer found with the id: {}.",  id);
				return null;
			}


		} catch (SQLException e) {
			LOGGER.error("Error while finding the computer");
			e.printStackTrace();
		} finally{
			DbUtil.close(result);
			DbUtil.close(statement);
			DbUtil.close(connect);
		}
		return null;
	}

	@Override
	public List<Computer> list() throws DatabaseConnectionException {
		Connection connect = ConnectionFactory.getConnection();
		ResultSet result = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			result = connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY
					).executeQuery(
							"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id AS companyId, company.name AS companyName FROM computer "+
									"LEFT JOIN company ON computer.company_id= company.id"							
							);  
			while (result.next()){
				try{	
					if (result.getInt("companyId")==0){
						Computer computer = ComputerMapper.map(result);

						computers.add(computer);
					} else{
						Computer computer = ComputerMapper.map(result);
						computers.add(computer);
					}
				}
				catch(Exception e){
					LOGGER.error("Error while retrieving the list of computers");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			LOGGER.error("Error sql while retrieving the list of computers");
			e.printStackTrace();
		} finally{
			DbUtil.close(result);
			DbUtil.close(connect);
		}
		LOGGER.info("List of computers found, size of the list: {}",computers.size());

		return computers;

	}

	@Override
	public List<Computer> listPage(int indexBegin, int pageSize) throws DatabaseConnectionException  {
		Connection connect = ConnectionFactory.getConnection();
		ResultSet result = null;
		List<Computer> computers = new ArrayList<Computer>();
		PreparedStatement statement = null;
		String sql = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id AS companyId, company.name AS companyName FROM computer "+
				"LEFT JOIN company ON computer.company_id= company.id LIMIT ?, ? ";
		try {

			statement = connect.prepareStatement(sql);
			statement.setInt(1, indexBegin);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();    
			while (result.next()){
				try{	
					if (result.getInt("companyId")==0){
						Computer computer = ComputerMapper.map(result);

						computers.add(computer);
					} else{
						Computer computer = ComputerMapper.map(result);
						computers.add(computer);
					}
				}
				catch(Exception e){
					LOGGER.error("Error while retrieving the list of computers");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			LOGGER.error("Error sql while retrieving the list of computers");
			e.printStackTrace();
		} finally{
			DbUtil.close(result);
			DbUtil.close(connect);
		}
		LOGGER.info("List of computers found, size of the list: {}",computers.size());

		return computers;
	}

}
