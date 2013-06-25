package com.pfe.client.mvp.presenters;

import java.util.List;

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
	 * Sets paging parameters and loads product types pages. Takes into account
	 * the filter on product name
	 */
	public void search();

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
	 * Deletes types from database
	 * 
	 * @param productTypes
	 */
	public void delete(List<ProductTypeDTO> productTypes);

	/**
	 * Loads selected type data in details panel
	 * 
	 * @param productType
	 */
	public void displayDetailsView(ProductTypeDTO productType);

}
