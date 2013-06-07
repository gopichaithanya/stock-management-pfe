package com.pfe.shared.dto;

import java.io.Serializable;
import java.util.List;

public class LocationDTO implements Serializable {

	private Long id;
	private String name;
	private LocationTypeDTO type;
	private List<StockDTO> stocks;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocationTypeDTO getType() {
		return type;
	}
	public void setType(LocationTypeDTO type) {
		this.type = type;
	}
	public List<StockDTO> getStocks() {
		return stocks;
	}
	public void setStocks(List<StockDTO> stocks) {
		this.stocks = stocks;
	}
}
