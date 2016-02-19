/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.implementation;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import com.excilys.formation.java.computerdb.dao.exception.ComputerDAOInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DAOSqlException;
import com.excilys.formation.java.computerdb.dao.implementation.ComputerDAO;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Computer;


/**
 * @author Cédric Cousseran
 *
 */
public class ComputerDaoTest {
	private ComputerDAO dao = new ComputerDAO();

	@Test
	public void testCreateFind() throws DatabaseConnectionException, ComputerDAOInvalidException, DAOSqlException, ComputerNotFoundException{
		Computer computer = new Computer.ComputerBuilder("test").build();
		int id =dao.create(computer);
		computer.setId(id);
		assertEquals(computer, dao.find(id));

		dao.delete(computer);
	}

	@Test
	public void testCreateFalse() throws DatabaseConnectionException, DAOSqlException{
		try {
			dao.create(null);
			fail("An exception should be thrown");
		} catch (ComputerDAOInvalidException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testCreateDelete() throws DatabaseConnectionException, ComputerDAOInvalidException, DAOSqlException, ComputerNotFoundException{
		Computer computer = new Computer.ComputerBuilder("test").build();
		int id =dao.create(computer);
		computer.setId(id);
		dao.delete(computer);
		try{
			dao.find(id);
			fail("An exception should be thrown");
		} catch (ComputerNotFoundException e) {
			assertTrue(true);		
		}
	}

	@Test
	public void testFindUnknown() throws DatabaseConnectionException, DAOSqlException{
		try {
			dao.find(999999999);
			fail("An exception should be thrown");
		} catch (ComputerNotFoundException e) {
			assertTrue(true);		
		}
	}

	@Test
	public void testDeleteNotInDb() throws DatabaseConnectionException, DAOSqlException{
		Computer computer = new Computer.ComputerBuilder("test").build();
		try {
			dao.delete(computer);
			fail("An exception should be thrown");
		} catch (ComputerNotFoundException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testUpdate() throws DatabaseConnectionException, ComputerNotFoundException, DAOSqlException, ComputerDAOInvalidException{
		Computer computer = new Computer.ComputerBuilder("test").build();
		int id =dao.create(computer);
		computer.setId(id);
		assertEquals(computer, dao.find(id));
		computer.setName("autre test");
		dao.update(computer);
		assertEquals(computer,dao.find(id));

		dao.delete(computer);
	}

	@Test
	public void testUpdateNotInDb() throws DatabaseConnectionException, DAOSqlException{
		Computer computer = new Computer.ComputerBuilder("test update false").build();
		try {
			dao.update(computer);
			fail("An exception should be thrown");
		} catch (ComputerNotFoundException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testList() throws DatabaseConnectionException, ComputerDAOInvalidException, DAOSqlException, ComputerNotFoundException{
		Computer computer = new Computer.ComputerBuilder("test list").build();
		computer.setId(dao.create(computer));
		List<Computer> computers = dao.list();
		boolean isInList = false;
		for (Computer computerInList : computers){
			if (computerInList.equals(computer)){
				isInList=true;
			}
		}
		assertTrue(isInList);

		dao.delete(computer);

		computers = dao.list();
		isInList = false;
		for (Computer computerInList : computers){
			if (computerInList.equals(computer)){
				isInList=true;
			}
		}
		assertFalse(isInList);

	}

	@Test
	public void testSelectCount() throws DatabaseConnectionException, DAOSqlException, ComputerDAOInvalidException, ComputerNotFoundException{
		Computer computer = new Computer.ComputerBuilder("test count").build();
		int count = dao.selectCount("test");

		computer.setId(dao.create(computer));
		int countAfter = dao.selectCount("test");
		assertEquals(count+1,countAfter);

		dao.delete(computer);

		assertEquals(count,dao.selectCount("test"));
	}

	@Test
	public void testFindByName() throws DatabaseConnectionException, ComputerDAOInvalidException, DAOSqlException, ComputerNotFoundException{
		Computer computer = new Computer.ComputerBuilder("test find by name").build();
		int id =dao.create(computer);
		computer.setId(id);
		List<Computer> computers = dao.findByName("test find by name");
		boolean isInList = false;
		for (Computer computerInList : computers){
			if (computerInList.equals(computer)){
				isInList=true;
			}
		}
		assertTrue(isInList);

		dao.delete(computer);
		computers = dao.findByName("test find by name");
		isInList = false;
		for (Computer computerInList : computers){
			if (computerInList.equals(computer)){
				isInList=true;
			}
		}
		assertFalse(isInList);
	}

}
