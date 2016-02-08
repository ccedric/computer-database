package com.excilys.formation.java.computerdb.dao;

import java.util.List;


public abstract class DAO<T> {	   
	  public DAO(){
	  }
	   
	  public abstract int create(T obj);

	  public abstract boolean delete(T obj);

	  public abstract boolean update(T obj);

	  public abstract T find(int id);
	  
	  public abstract List<T> list();
	}
