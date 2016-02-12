package com.excilys.formation.java.computerdb.ui;

import java.util.List;

import com.excilys.formation.java.computerdb.db.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.service.Service;

/**
 * Class which transform a list into multiple pages
 * @author Cédric Cousseran
 *
 * @param <T> Class to implement for the Page, ie: Computer
 */
public class Page<T> {
	/**
	 * The page size, number of elements to show in a page
	 */
	private int pageSize;
	/**
	 * The current page
	 */
	private int page;
	/**
	 * the list this class is paging
	 */
	private List<T> list;
	/**
	 * the starting index in the list of the page
	 */
	private int startingIndex;
	private int maxPages;
	private String name;
	
	/**
	 * Service corresponding to the class implemented
	 */
	private Service<T> service;
	
	/**
	 * @param list the list this class is paging
	 * @param pageSize The page size, number of elements to show in a page
	 */
	public Page( int pageSize, Service<T> service, String name){
		this.service = service;
		this.pageSize=pageSize;
		this.page=0;
		this.name=name;
		
		maxPages = service.selectCount(name)/pageSize;
	}

	public List<T> getList(){
		return this.list;
	}


	/**
	 * Determines whether there is a previous page and gets the page number.
	 * @return the previous page number, or 0
	 */
	public int getPreviousPage() {
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
		if (page==maxPages-1){
			return page;
		}else{
			return page+1;
		}
	}

	/**
	 * Sets the page size
	 * @param p the page number
	 */
	public void setPage(int p) {
		if (p <= 1) {
			this.page = 1;
		} else {
			this.page = p;
		}

		// now work out where the sub-list should start and end
		startingIndex = pageSize * (page-1);
		if (startingIndex < 0) {
			startingIndex = 0;
		}
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
		maxPages = service.selectCount(name)/pageSize;
	}

	/**
	 * Gets the subset of the list for the current page.
	 * 
	 * @return the list of elements for this page
	 * @throws DatabaseConnectionException 
	 */
	public List<T> getListForPage() throws DatabaseConnectionException {
		return service.listPage(startingIndex,pageSize);
	}

	/**
	 * Gets the subset of the list of the research by name for the current page.
	 * 
	 * @return the list of elements for this page
	 * @throws DatabaseConnectionException 
	 */
	public List<T> getListByNameForPage() throws DatabaseConnectionException {
		return service.listPageByName(startingIndex,pageSize,name);
	}
	
	/**
	 * @return the maxPages
	 */
	public int getMaxPages() {
		return maxPages;
	}

	/**
	 * @param maxPages the maxPages to set
	 */
	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	
	
}
