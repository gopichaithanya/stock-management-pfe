package com.pfe.server.dao.supplier;

import com.pfe.server.dao.SortField;

/**
 * Class used to order supplier entities.
 * 
 * @author Alexandra
 *
 */
public class SupplierSortField extends SortField {

	private static final long serialVersionUID = 1L;
	
	public SupplierSortField(String field, boolean ascending) {
		super(field, ascending);
	}

}
