package com.pfe.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class InvoiceDto implements Serializable {
	
	private Long id;
	private int code;
	private SupplierDto supplier;
	private String paymentType;
	private List<ShipmentDto> shipments;
	private BigDecimal restToPay;
	private Date created;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public SupplierDto getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierDto supplier) {
		this.supplier = supplier;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public List<ShipmentDto> getShipments() {
		return shipments;
	}
	public void setShipments(List<ShipmentDto> shipments) {
		this.shipments = shipments;
	}
	public BigDecimal getRestToPay() {
		return restToPay;
	}
	public void setRestToPay(BigDecimal restToPay) {
		this.restToPay = restToPay;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

}
