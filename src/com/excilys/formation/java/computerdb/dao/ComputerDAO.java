package com.excilys.formation.java.computerdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.java.computerdb.db.ConnectionFactory;
import com.excilys.formation.java.computerdb.db.DbUtil;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import java.sql.Connection;


public class ComputerDAO extends DAO<Computer> {

	public ComputerDAO() {
		super();
	}

	@Override
	public int create(Computer obj) {
		Connection connect = ConnectionFactory.getConnection();

		String sql = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES ( ?, ?, ?,?)";

		PreparedStatement statement=null;
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
			if (obj.getCompany().getId()==0){
				statement.setNull(4,Types.BIGINT);
			}else{
				statement.setInt(4, obj.getCompany().getId());
			}
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()){
				int numero = rs.getInt(1);
				System.out.println(numero);
				obj.setId(numero);

				DbUtil.close(rs);
				DbUtil.close(statement);
				DbUtil.close(connect);
				return numero;
			}
			DbUtil.close(rs);
			DbUtil.close(statement);
			DbUtil.close(connect);
			return 0;

		} catch (SQLException e) {
			e.printStackTrace();
			DbUtil.close(statement);
			DbUtil.close(connect);
			return 0;
		}
	}

	@Override
	public boolean delete(Computer obj) {
		Connection connect = ConnectionFactory.getConnection();

		String sql = "DELETE FROM computer WHERE id=?";

		try {
			PreparedStatement statement = connect.prepareStatement(sql);

			statement.setInt(1, obj.getId());
			statement.executeUpdate();
			DbUtil.close(statement);
			DbUtil.close(connect);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			DbUtil.close(connect);
			return false;
		}

	}

	@Override
	public boolean update(Computer obj) {
		String sql = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
		Connection connect = ConnectionFactory.getConnection();

		PreparedStatement statement;
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
			if (obj.getCompany()==null){
				statement.setNull(4,Types.BIGINT);
			}else{
				statement.setInt(4, obj.getCompany().getId());
			}
			statement.setInt(5, obj.getId());

			int rowsUpdated = statement.executeUpdate();

			DbUtil.close(statement);
			DbUtil.close(connect);

			if (rowsUpdated > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			DbUtil.close(connect);
			return false;
		}

	}

	@Override
	public Computer find(int id) {
		Connection connect = ConnectionFactory.getConnection();

		Computer computer = new Computer();            
		try {
			ResultSet result = connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY
					).executeQuery(
							"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id AS companyId, company.name AS companyName FROM computer "+
									"LEFT JOIN company ON computer.company_id= company.id  WHERE computer.id="+id
							);    
			if(result.first()){
				computer = new Computer(id, result.getString("name"),new Company(result.getInt("companyId"), result.getString("companyName")),result.getTimestamp("introduced"), result.getTimestamp("discontinued"));  
			}
			DbUtil.close(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DbUtil.close(connect);

		return computer;		
	}

	@Override
	public List<Computer> list() {
		Connection connect = ConnectionFactory.getConnection();

		List<Computer> computers = new ArrayList<Computer>();
		try {
			ResultSet result = connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY
					).executeQuery(
							"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id AS companyId, company.name AS companyName FROM computer "+
									"LEFT JOIN company ON computer.company_id= company.id"							
							);  
			while (result.next()){
				try{
					computers.add(new Computer(result.getInt("id"), result.getString("name"),new Company(result.getInt("companyId"), result.getString("companyName")),result.getTimestamp("introduced"), result.getTimestamp("discontinued")));
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			DbUtil.close(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DbUtil.close(connect);

		return computers;

	}

}
