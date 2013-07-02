package com.pfe.shared.dto;

import java.io.Serializable;

/**
 * DTO corresponding to LocationType entity. The dozerMapping file specifies the
 * mapping between the two classes.
 * 
 * @author Alexandra
 * 
 */
public class LocationTypeDTO implements Serializable {

	public static final String warehouseDescription = "warehouse";
	public static final String storeDescription = "store";
	
	private static final long serialVersionUID = -3888513144445378457L;
	private Long id;
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
