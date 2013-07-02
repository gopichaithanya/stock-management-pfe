package com.pfe.shared.dto;

import java.io.Serializable;

/**
 * DTO corresponding to ProductType entity. The dozerMapping file specifies the
 * mapping between the two classes.
 * 
 * @author Alexandra
 *
 */
public class ProductTypeDTO implements Serializable {

	private static final long serialVersionUID = -7903133210912274141L;
	private Long id;
	private String name;
	private String description;
	
	public ProductTypeDTO(){
		
	}
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
