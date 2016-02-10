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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + id;
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
