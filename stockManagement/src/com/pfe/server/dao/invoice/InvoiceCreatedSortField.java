package com.pfe.server.dao.invoice;

import com.pfe.server.dao.OrderAlias;

public class InvoiceCreatedSortField extends InvoiceSortField {

	private static final long serialVersionUID = 1382705453835889154L;
	private static final String ENTITY_SORT_COLUMN = "created";

	public InvoiceCreatedSortField() {
		//order invoices from the most recent to the oldest
		this(false);
	}
	
	public InvoiceCreatedSortField(boolean ascending) {
		super(ENTITY_SORT_COLUMN, ascending);
	}
	
	@Override
	public OrderAlias getOrderAlias() {
		return new OrderAlias(ENTITY_SORT_COLUMN, null);
	}
}
