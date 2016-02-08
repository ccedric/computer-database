package com.excilys.formation.java.computerdb.model;

import java.sql.Timestamp;

public class Computer {
	private int id;
	private String name;
	private Company company;
	private Timestamp introduced;
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
