package com.excilys.formation.java.computerdb.order;

/**
 * Class containing the information concerning the order by in the web ui.
 * 
 * @author Cédric Cousseran
 */
public class OrderSearch {
  /**
   * The column the order by is on.
   */
  private Column column = Column.computerId;
  /**
   * Order by ascending or descending.
   */
  private Order order = Order.ASC;

  /**
   * Constructor with column and order.
   * @param column the column
   * @param order the order
   */
  public OrderSearch(Column column, Order order) {
    super();
    this.column = column;
    this.order = order;
  }

  public OrderSearch() {
  }

  public Column getColumn() {
    return column;
  }


  public void setColumn(Column column) {
    this.column = column;
  }


  public Order getOrder() {
    return order;
  }


  public void setOrder(Order order) {
    this.order = order;
  }

}
