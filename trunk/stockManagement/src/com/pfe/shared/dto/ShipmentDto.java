package com.pfe.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShipmentDto implements Serializable {

	private Long id;
	private ProductTypeDto productType;
	private BigDecimal unitPrice;
	private int initialQuantity;
	private int currentQuantity;
	private Boolean paid;
	private Date created;
	private InvoiceDto invoice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProductTypeDto getProductType() {
		return productType;
	}
	public void setProductType(ProductTypeDto productType) {
		this.productType = productType;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
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
	public InvoiceDto getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceDto invoice) {
		this.invoice = invoice;
	}
	
}
