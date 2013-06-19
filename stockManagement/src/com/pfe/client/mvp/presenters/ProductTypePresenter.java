package com.pfe.client.mvp.presenters;

import com.google.gwt.place.shared.Place;
import com.pfe.shared.dto.ProductTypeDTO;

/**
 * Controls ProductTypeListView
 * 
 * @author Alexandra
 * 
 */
public interface ProductTypePresenter extends Presenter {

	/**
	 * Goes to a new place
	 * 
	 * @param place
	 */
	public void goTo(Place place);

	/**
	 * Calls service to retrieve type by id
	 * 
	 * @param id
	 */
	public void find(Long id);
	
	/**
	 * Calls RPC service to create a new product type
	 * 
	 * @param productType
	 */
	public void create(ProductTypeDTO productType);

	/**
	 * Updates product type
	 * 
	 * @param updatedType
	 */
	public void update(ProductTypeDTO updatedType);

	/**
	 * Deletes type
	 * 
	 * @param productType
	 */
	public void delete(ProductTypeDTO productType);

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
	public void displayDetailsView(ProductTypeDTO productType);

}
