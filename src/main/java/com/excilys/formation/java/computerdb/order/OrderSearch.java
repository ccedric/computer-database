/**
 * 
 */
package com.excilys.formation.java.computerdb.order;

/**
 * @author Cédric Cousseran
 *
 */
public class OrderSearch {
	private Column column = Column.computerId;
	private Order order = Order.ASC;
		
	public OrderSearch(Column column, Order order) {
		super();
		this.column = column;
		this.order = order;
	}
	
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
