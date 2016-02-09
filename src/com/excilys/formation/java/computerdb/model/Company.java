package com.excilys.formation.java.computerdb.model;

/**
 * A company is represented by an id, and a name
 * @author Cédric Cousseran
 *
 */
public class Company {
	/**
	 * id of the company
	 */
	private int id;
	/**
	 * name of the company
	 */
	private String name;

	public Company(){}

	public Company(int id, String name){
		this.id = id;
		this.name=name;
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

	@Override
	public String toString(){
		return "Company n°"+this.id+" de nom "+this.name;
	}
}
