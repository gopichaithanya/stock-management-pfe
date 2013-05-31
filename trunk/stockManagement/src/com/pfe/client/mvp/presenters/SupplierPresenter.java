package com.pfe.client.mvp.presenters;

import com.google.gwt.place.shared.Place;
import com.pfe.shared.dto.SupplierDto;

/**
 * Controls SupplierListView
 * 
 * @author Alexandra
 *
 */
public interface SupplierPresenter extends Presenter {
	
	/**
	 * Goes to a new place
	 * 
	 * @param place
	 */
	public void goTo(Place place);
	
	/**
	 * Calls RPC service to add new supplier
	 * 
	 * @param supplier
	 */
	public void create(SupplierDto supplier);

	
	/**
	 * Updates supplier
	 * 
	 * @param initial
	 * @param updatedBuffer
	 */
	public void update(SupplierDto initial, SupplierDto updatedBuffer);

	/***
	 * Deletes supplier
	 * 
	 * @param supplier
	 */
	public void delete(SupplierDto supplier);

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
	public void displayDetailsView(SupplierDto supplier);

}
