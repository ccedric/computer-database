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

	// builder pattern
	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.company = builder.company;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
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

	/**
	 * Builder of the Computer class, use it like this: 
	 * new Computer.ComputerBuilder(name).id(id).company(Company).introduced(introduced).discontinued(discontinued).build()
	 * @author Cédric Cousseran
	 *
	 */
	public static class ComputerBuilder {
		private int id;
		private String name;
		private Company company;
		private Timestamp introduced;
		private Timestamp discontinued;

		public ComputerBuilder(String name) {
			this.name = name;
		}

		public ComputerBuilder id(int id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder company(Company company) {
			this.company = company;
			return this;
		}

		public ComputerBuilder introduced(Timestamp introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder discontinued(Timestamp discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}

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
