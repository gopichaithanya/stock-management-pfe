package com.pfe.server.dao.supplier;

/**
 * Class used for ordering suppliers in ascending alphabetical order by name.
 * 
 * @author Alexandra
 *
 */
public class SupplierNameSortField extends SupplierSortField {

	private static final long serialVersionUID = 1L;
	private static final String ENTITY_SORT_COLUMN = "name";
	
	public SupplierNameSortField() {
		this(true);
	}
	
	public SupplierNameSortField(boolean ascending) {
		super(ENTITY_SORT_COLUMN, ascending);
	}

}
