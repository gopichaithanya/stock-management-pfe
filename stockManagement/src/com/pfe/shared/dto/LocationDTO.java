package com.pfe.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO corresponding to Location entity. The dozerMapping file specifies two
 * mappings for this object to include or exclude the list of stocks.
 * 
 * @author Alexandra
 *
 */
public class LocationDTO implements Serializable {

	private static final long serialVersionUID = -8294704081505378237L;
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
