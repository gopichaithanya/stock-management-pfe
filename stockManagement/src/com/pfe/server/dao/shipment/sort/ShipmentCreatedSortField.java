package com.pfe.server.dao.shipment.sort;


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
	
}
