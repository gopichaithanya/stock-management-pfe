package com.pfe.shared.dto;

import java.io.Serializable;

public class LocationTypeDTO implements Serializable {

	/**
	 * 
	 */
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
