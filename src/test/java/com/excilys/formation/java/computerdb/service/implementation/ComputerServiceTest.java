/**
 * 
 */
package com.excilys.formation.java.computerdb.service.implementation;

import org.junit.Test;

import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.TimestampDiscontinuedBeforeIntroducedException;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * @author Cédric Cousseran
 *
 */
public class ComputerServiceTest {
	private ComputerService service = new ComputerService();
	
	@Test
	public void testListPage(){
		List<Computer> computers = service.listPage(1, 20);
		assertEquals(20,computers.size());
	}
	
	@Test
	public void testCreate(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime debut = LocalDateTime.parse("1994-10-10 10:10",formatter);
		LocalDateTime fin = LocalDateTime.parse("1999-10-10 10:10",formatter);
		Computer computer = new Computer.ComputerBuilder("test create").introduced(debut).discontinued(fin).build();
		assertNotEquals(0,service.create(computer));
	}
	
	@Test
	public void testCreateException(){
		try{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime debut = LocalDateTime.parse("2004-10-10 10:10",formatter);
			LocalDateTime fin = LocalDateTime.parse("1989-10-10 10:10",formatter);
			Computer computer = new Computer.ComputerBuilder("test create").introduced(debut).discontinued(fin).build();
			service.create(computer);
			fail("An exception should be thrown");
		} catch (TimestampDiscontinuedBeforeIntroducedException e){
			assertTrue(true);
		}

	}
}
