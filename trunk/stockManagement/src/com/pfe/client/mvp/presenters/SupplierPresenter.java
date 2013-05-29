package com.pfe.client.mvp.presenters;

import com.pfe.shared.model.Supplier;

/**
 * Controls SupplierListView
 * 
 * @author Alexandra
 *
 */
public interface SupplierPresenter extends Presenter {
	
	/**
	 * Calls RPC service to add new supplier
	 * 
	 * @param supplier
	 */
	public void create(Supplier supplier);

	
	/**
	 * Updates supplier
	 * 
	 * @param initial
	 * @param updatedBuffer
	 */
	public void update(Supplier initial, Supplier updatedBuffer);

	/***
	 * Deletes supplier
	 * 
	 * @param supplier
	 */
	public void delete(Supplier supplier);

	/**
	 * Filters list by name. Creates new paging load configuration corresponding
	 * to the filtered data
	 * 
	 * @param name
	 */
	public void filter(String name);

	/**
	 * Clears filters. Loads pages without filters
	 * 
	 */
	public void clearFilter();

	/**
	 * Loads selected type data in details panel
	 * 
	 * @param productType
	 */
	public void displayDetailsView(Supplier supplier);

}
