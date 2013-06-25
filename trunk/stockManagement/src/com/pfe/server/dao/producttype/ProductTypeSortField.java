package com.pfe.server.dao.producttype;

import com.pfe.server.dao.SortField;

/**
 * Class used to order product type entities.
 * 
 * @author Alexandra
 *
 */
public class ProductTypeSortField extends SortField {
	
	private static final long serialVersionUID = 1L;
	
	public ProductTypeSortField(String field, boolean ascending) {
		super(field, ascending);
	}

}
