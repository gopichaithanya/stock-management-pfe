package com.pfe.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class ShipmentDTO implements Serializable {

	private Long id;
	private ProductTypeDTO productType;
	private Double unitPrice;
	private int initialQuantity;
	private int currentQuantity;
	private Boolean paid;
	private Date created;
	private InvoiceDTO invoice;
	
	public ShipmentDTO(){
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProductTypeDTO getProductType() {
		return productType;
	}
	public void setProductType(ProductTypeDTO productType) {
		this.productType = productType;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getInitialQuantity() {
		return initialQuantity;
	}
	public void setInitialQuantity(int initialQuantity) {
		this.initialQuantity = initialQuantity;
	}
	public int getCurrentQuantity() {
		return currentQuantity;
	}
	public void setCurrentQuantity(int currentQuantity) {
		this.currentQuantity = currentQuantity;
	}
	public Boolean getPaid() {
		return paid;
	}
	public void setPaid(Boolean paid) {
		this.paid = paid;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public InvoiceDTO getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceDTO invoice) {
		this.invoice = invoice;
	}
	
}
