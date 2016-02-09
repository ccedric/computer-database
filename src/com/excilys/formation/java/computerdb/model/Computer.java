package com.excilys.formation.java.computerdb.model;

import java.sql.Timestamp;

/**
 * A computer is represented by an id, a name, a company (manufacturer), an introduced date and discontinued date
 * @author Cédric Cousseran
 *
 */
public class Computer {
	/**
	 * id of the computer
	 */
	private int id;
	/**
	 * name of the computer
	 */
	private String name;
	/**
	 * manufacturer of the computer
	 */
	private Company company;
	/**
	 * date when the computer was introduced
	 */
	private Timestamp introduced;
	/**
	 * date when the computer was discontinued
	 */
	private Timestamp discontinued;

	public Computer(){}

	public Computer(int id, String name, Company company, Timestamp introduced, Timestamp discontinued) {
		super();
		this.id = id;
		this.name = name;
		this.company = company;
		this.introduced = introduced;
		this.discontinued = discontinued;
	}

	public Computer( String name, Company company, Timestamp introduced, Timestamp discontinued) {
		super();
		this.name = name;
		this.company = company;
		this.introduced = introduced;
		this.discontinued = discontinued;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Timestamp getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}
	public Timestamp getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	@Override
	public String toString(){
		return "Computer n°"+this.id+",name: "+this.name+", date introduced: "+this.introduced+", date discontinued: "+this.discontinued+", company: "+this.company;
	}
}
