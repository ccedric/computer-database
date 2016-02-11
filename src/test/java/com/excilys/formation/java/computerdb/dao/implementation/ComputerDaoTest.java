/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.implementation;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import com.excilys.formation.java.computerdb.dao.implementation.ComputerDAO;
import com.excilys.formation.java.computerdb.model.Computer;


/**
 * @author Cédric Cousseran
 *
 */
public class ComputerDaoTest {
	private ComputerDAO dao = new ComputerDAO();
	
	@Test
	public void testCreateFind(){
		Computer computer = new Computer.ComputerBuilder("test").build();
		int id =dao.create(computer);
		computer.setId(id);
		assertEquals(computer, dao.find(id));
	}
	
	@Test
	public void testCreateFalse(){
		assertEquals(0,dao.create(null));
	}
	
	@Test
	public void testCreateDelete(){
		Computer computer = new Computer.ComputerBuilder("test").build();
		int id =dao.create(computer);
		computer.setId(id);
		assertTrue(dao.delete(computer));
		assertNull(dao.find(id));
	}
	
	@Test
	public void testFindUnknown(){
		assertNull(dao.find(999999999));
	}
	
	@Test
	public void testDeleteNotInDb(){
		Computer computer = new Computer.ComputerBuilder("test").build();
		assertFalse(dao.delete(computer));
	}
	
	@Test
	public void testUpdate(){
		Computer computer = new Computer.ComputerBuilder("test").build();
		int id =dao.create(computer);
		computer.setId(id);
		assertEquals(computer, dao.find(id));
		computer.setName("autre test");
		assertTrue(dao.update(computer));
		assertEquals(computer,dao.find(id));
	}

	@Test
	public void testUpdateNotInDb(){
		Computer computer = new Computer.ComputerBuilder("test update false").build();
		assertFalse(dao.update(computer));
	}
	
	@Test
	public void testList(){
		Computer computer = new Computer.ComputerBuilder("test list").build();
		int id =dao.create(computer);
		computer.setId(id);
		List<Computer> computers = dao.list();
		boolean isInList = false;
		for (Computer computerInList : computers){
			if (computerInList.equals(computer)){
				isInList=true;
			}
		}
		assertTrue(isInList);
	}
	
}
