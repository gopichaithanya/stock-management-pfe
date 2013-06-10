package com.pfe.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceDTO implements Serializable {
	
	private Long id;
	private int code;
	private SupplierDTO supplier;
	private String paymentType;
	private ArrayList<ShipmentDTO> shipments;
	private Double restToPay;
	private Date created;
	
	public InvoiceDTO(){
		
	}
	
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
	public SupplierDTO getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierDTO supplier) {
		this.supplier = supplier;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public ArrayList<ShipmentDTO> getShipments() {
		return shipments;
	}
	public void setShipments(ArrayList<ShipmentDTO> shipments) {
		this.shipments = shipments;
	}
	public Double getRestToPay() {
		return restToPay;
	}
	public void setRestToPay(Double restToPay) {
		this.restToPay = restToPay;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

}
