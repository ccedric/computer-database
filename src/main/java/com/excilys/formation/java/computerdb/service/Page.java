package com.excilys.formation.java.computerdb.service;

import com.excilys.formation.java.computerdb.order.OrderSearch;

/**
 * This class represents a Page of computers
 * @author Cédric Cousseran
 */
public class Page {
	private int page;
	private int pageSize;
	private String search;
	private OrderSearch orderSearch;
	
	public Page(int page, int pageSize, String search) {
		super();
		this.page = page;
		this.pageSize = pageSize;
		this.search = search;
	}
	
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the name
	 */
	public String getSearch() {
		return search;
	}
	/**
	 * @param name the name to set
	 */
	public void setSearch(String name) {
		this.search = name;
	}
	
	public int getPreviousPage(){
		if (page > 1) {
			return page-1;
		} else {
			return 0;
		}
	}
	/**
	 * Determines whether there is a next page and gets the page number.
	 *
	 * @return  the next page number, or 0
	 */
	public int getNextPage() {
		return page+1;
	}
	
	public int getStartingIndex() {
		return (getPage()-1)*getPageSize();
	}

	/**
	 * @return the orderSearch
	 */
	public OrderSearch getOrderSearch() {
		return orderSearch;
	}

	/**
	 * @param orderSearch the orderSearch to set
	 */
	public void setOrderSearch(OrderSearch orderSearch) {
		this.orderSearch = orderSearch;
	}
}
