package com.excilys.formation.java.computerdb.service;

import com.excilys.formation.java.computerdb.order.OrderSearch;

/**
 * This class represents a Page of computers.
 * 
 * @author Cédric Cousseran
 */
public class Page {
  private int page;
  private int pageSize;
  private String search;
  private OrderSearch orderSearch;

  /**
   * Constructor of a Page with his page, pageSize and search attributes.
   * @param page the number of the page
   * @param pageSize the size of the page
   * @param search the search
   */
  public Page(int page, int pageSize, String search) {
    super();
    this.page = page;
    this.pageSize = pageSize;
    this.search = search;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }


  public int getPageSize() {
    return pageSize;
  }


  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }


  public String getSearch() {
    return search;
  }


  public void setSearch(String name) {
    this.search = name;
  }

  /**
   * Return the number of the previous page.
   * @return the number of the page
   */
  public int getPreviousPage() {
    if (page > 1) {
      return page - 1;
    } else {
      return 0;
    }
  }

  /**
   * Determines whether there is a next page and gets the page number.
   *
   * @return the next page number, or 0
   */
  public int getNextPage() {
    return page + 1;
  }

  public int getStartingIndex() {
    return (getPage() - 1) * getPageSize();
  }

  public OrderSearch getOrderSearch() {
    return orderSearch;
  }

  public void setOrderSearch(OrderSearch orderSearch) {
    this.orderSearch = orderSearch;
  }
}
