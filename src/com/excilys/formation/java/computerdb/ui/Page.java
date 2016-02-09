package com.excilys.formation.java.computerdb.ui;

import java.util.List;

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
	 * The maximum number of pages
	 */
	private int maxPages;
	/**
	 * the list this class is paging
	 */
	private List<T> list;
	/**
	 * the starting index in the list of the page
	 */
	private int startingIndex;
	/**
	 * the ending index in the list of the page
	 */
	private int endingIndex;

	/**
	 * @param list the list this class is paging
	 * @param pageSize The page size, number of elements to show in a page
	 */
	public Page(List<T> list, int pageSize){
		this.pageSize=pageSize;
		this.list=list;
		this.page=0;
		calculatePages();	
	}

	public List<T> getList(){
		return this.list;
	}

	private void calculatePages() {
		if (pageSize > 0) {
			// calculate how many pages there are
			if (list.size() % pageSize == 0) {
				maxPages = list.size() / pageSize;
			} else {
				maxPages = (list.size() / pageSize) + 1;
			}
		}
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
		if (page < maxPages) {
			return page+1;
		} else {
			return 0;
		}
	}

	/**
	 * Sets the page size
	 * @param p the page number
	 */
	public void setPage(int p) {
		if (p >= maxPages) {
			this.page = maxPages;
		} else if (p <= 1) {
			this.page = 1;
		} else {
			this.page = p;
		}

		// now work out where the sub-list should start and end
		startingIndex = pageSize * (page-1);
		if (startingIndex < 0) {
			startingIndex = 0;
		}
		endingIndex = startingIndex + pageSize;
		if (endingIndex > list.size()) {
			endingIndex = list.size();
		}
	}

	/**
	 * Gets the subset of the list for the current page.
	 * 
	 * @return the list of elements for this page
	 */
	public List<T> getListForPage() {
		return list.subList(startingIndex, endingIndex);
	}

}
