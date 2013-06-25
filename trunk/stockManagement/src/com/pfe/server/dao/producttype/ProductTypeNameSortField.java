package com.pfe.server.dao.producttype;

/**
 * Class used for ordering product types in ascending alphabetical order by name.
 * 
 * @author Alexandra
 *
 */
public class ProductTypeNameSortField extends ProductTypeSortField {

	private static final long serialVersionUID = 1L;
	private static final String ENTITY_SORT_COLUMN = "name";
	
	public ProductTypeNameSortField() {
		this(true);
	}
	
	public ProductTypeNameSortField(boolean ascending) {
		super(ENTITY_SORT_COLUMN, ascending);
	}
}
