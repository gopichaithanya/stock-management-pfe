package com.pfe.server.model;

import java.math.BigDecimal;
import java.util.List;

public class Invoice {

	private int id;
	private int code;
	private Supplier supplier;
	private String paymentType;
	private List<Product> products;
	private BigDecimal restToPay;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public BigDecimal getRestToPay() {
		return restToPay;
	}
	public void setRestToPay(BigDecimal restToPay) {
		this.restToPay = restToPay;
	}

}