package com.pfe.server.dao.shipment.sort;

import com.pfe.server.dao.OrderAlias;

public class ShipmentCreatedSortField extends ShipmentSortField{

	private static final long serialVersionUID = -5698757481184832679L;

	// Attribute from the model Entity on which to apply the sort
	private static final String ENTITY_SORT_COLUMN = "created";
	
	public ShipmentCreatedSortField() {
		this(true);
	}
	
	public ShipmentCreatedSortField(boolean ascending) {
		super(ENTITY_SORT_COLUMN, ascending);
	}
	
	@Override
	public OrderAlias getOrderAlias() {
		return new OrderAlias(ENTITY_SORT_COLUMN, null);
	}

}
