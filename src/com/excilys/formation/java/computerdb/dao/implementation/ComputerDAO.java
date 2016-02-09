package com.excilys.formation.java.computerdb.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.computerdb.dao.DAO;
import com.excilys.formation.java.computerdb.db.ConnectionFactory;
import com.excilys.formation.java.computerdb.db.DbUtil;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import java.sql.Connection;


/**
 * Data Access Object for the class Computer
 * @author Cédric Cousseran
 *
 */
public class ComputerDAO implements DAO<Computer> {
	private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	public ComputerDAO() {
		super();
	}

	@Override
	public int create(Computer obj) {
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
				statement.setTimestamp(2, obj.getIntroduced());
			}
			if(obj.getDiscontinued()==null){
				statement.setNull(3,Types.TIMESTAMP);
			}else{
				statement.setTimestamp(3, obj.getDiscontinued());
			}
			if((obj.getIntroduced()!=null)&&(obj.getDiscontinued()!=null)&&(obj.getIntroduced().after(obj.getDiscontinued()))){
				throw new IllegalArgumentException();
			}
			if (obj.getCompany().getId()==0){
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
				logger.info("New computer created, id {}, name {}, company {}, introduced date {}, discontinued date {}.", numero, obj.getName(), obj.getCompany(), obj.getIntroduced(),obj.getDiscontinued());
				return numero;
			}
			return 0;

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error while creating the computer");
			return 0;
		} finally{
			DbUtil.close(statement);
			DbUtil.close(rs);
			DbUtil.close(connect);
		}
	}

	@Override
	public boolean delete(Computer obj) {
		Connection connect = ConnectionFactory.getConnection();
		PreparedStatement statement = null;
		String sql = "DELETE FROM computer WHERE id=?";

		try {
			statement = connect.prepareStatement(sql);

			statement.setInt(1, obj.getId());
			statement.executeUpdate();
			logger.info("Computer deleted, id {}, name {}", obj.getId(), obj.getName());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error while deleting the computer");
			return false;
		} finally{
			DbUtil.close(statement);
			DbUtil.close(connect);
		}
	}

	@Override
	public boolean update(Computer obj) {
		String sql = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
		Connection connect = ConnectionFactory.getConnection();

		PreparedStatement statement = null;
		try {
			statement = connect.prepareStatement(sql);
			statement.setString(1, obj.getName());
			if(obj.getIntroduced()==null){
				statement.setNull(2,Types.TIMESTAMP);
			}else{
				statement.setTimestamp(2, obj.getIntroduced());
			}
			if(obj.getDiscontinued()==null){
				statement.setNull(3,Types.TIMESTAMP);
			}else{
				statement.setTimestamp(3, obj.getDiscontinued());
			}
			if((obj.getIntroduced()!=null)&&(obj.getDiscontinued()!=null)&&(obj.getIntroduced().after(obj.getDiscontinued()))){
				throw new IllegalArgumentException();
			}
			if (obj.getCompany()==null){
				statement.setNull(4,Types.BIGINT);
			}else{
				statement.setInt(4, obj.getCompany().getId());
			}
			statement.setInt(5, obj.getId());

			int rowsUpdated = statement.executeUpdate();

			if (rowsUpdated > 0) {
				logger.info("Computer updated, id {}, name {}, company {}, introduced date {}, discontinued date {}.", obj.getId(), obj.getName(), obj.getCompany(), obj.getIntroduced(),obj.getDiscontinued());
				return true;
			}
			logger.error("Error while updating the computer");
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error while updating the computer");
			return false;
		} finally{
			DbUtil.close(statement);
			DbUtil.close(connect);
		}

	}

	@Override
	public Computer find(int id) {
		Connection connect = ConnectionFactory.getConnection();

		Computer computer = new Computer();  
		ResultSet result = null;
		try {
			result = connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY
					).executeQuery(
							"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id AS companyId, company.name AS companyName FROM computer "+
									"LEFT JOIN company ON computer.company_id= company.id  WHERE computer.id="+id
							);    
			if(result.first()){
				if (result.getInt("companyId")==0){
					computer = new Computer(id, result.getString("name"),null,result.getTimestamp("introduced"), result.getTimestamp("discontinued"));  
				} else{
					computer = new Computer(id, result.getString("name"),new Company(result.getInt("companyId"), result.getString("companyName")),result.getTimestamp("introduced"), result.getTimestamp("discontinued"));  
				}
			}
		} catch (SQLException e) {
			logger.error("Error while finding the computer");
			e.printStackTrace();
		} finally{
			DbUtil.close(result);
			DbUtil.close(connect);
		}
		logger.info("Computer found, id {}, name {}, company {}, introduced date {}, discontinued date {}.",  computer.getId(),computer.getName(), computer.getCompany(), computer.getIntroduced(),computer.getDiscontinued());
		return computer;		
	}

	@Override
	public List<Computer> list() {
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
						computers.add(new Computer(result.getInt("id"), result.getString("name"),null,result.getTimestamp("introduced"), result.getTimestamp("discontinued")));
					} else{
						computers.add(new Computer(result.getInt("id"), result.getString("name"),new Company(result.getInt("companyId"), result.getString("companyName")),result.getTimestamp("introduced"), result.getTimestamp("discontinued")));
					}
				}
				catch(Exception e){
					logger.error("Error while retrieving the list of computers");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			logger.error("Error sql while retrieving the list of computers");
			e.printStackTrace();
		} finally{
			DbUtil.close(result);
			DbUtil.close(connect);
		}
		logger.info("List of computers found, size of the list: {}",computers.size());

		return computers;

	}

}
