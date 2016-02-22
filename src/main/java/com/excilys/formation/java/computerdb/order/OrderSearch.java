package com.excilys.formation.java.computerdb.order;

/**
 * Class containing the information concerning the order by in the web ui
 * @author Cédric Cousseran
 */
public class OrderSearch {
	/**
	 * The column the order by is on
	 */
	private Column column = Column.computerId;
	/**
	 * Order by ascending or descending
	 */
	private Order order = Order.ASC;
		
	public OrderSearch(Column column, Order order) {
		super();
		this.column = column;
		this.order = order;
	}
	
	/**
	 * 
	 */
	public OrderSearch() {
	}
	
	
	/**
	 * @return the column
	 */
	public Column getColumn() {
		return column;
	}
	/**
	 * @param column the column to set
	 */
	public void setColumn(Column column) {
		this.column = column;
	}
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
