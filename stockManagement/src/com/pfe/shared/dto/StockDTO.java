package com.pfe.shared.dto;

import java.io.Serializable;

public class StockDTO implements Serializable {

	private Long id;
	private int quantity;
	private ProductTypeDTO type;
	private LocationDTO location;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public ProductTypeDTO getType() {
		return type;
	}
	public void setType(ProductTypeDTO type) {
		this.type = type;
	}
	public LocationDTO getLocation() {
		return location;
	}
	public void setLocation(LocationDTO location) {
		this.location = location;
	}
	
}
