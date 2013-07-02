package com.pfe.shared.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pfe.server.model.Supplier;

/**
 * An invoice is created when merchandise is received. Attributes: a unique
 * code, the supplier of the products, the type of payment (immediate or on
 * product sale), a list of shipments, the debt and the date of creation. All
 * shipments come from the same supplier.
 * 
 * @author Alexandra
 * 
 */
@Entity
@Table(name = "Invoices")
public class Invoice {

	public static final String IMMEDIATE_PAY = "immediate";
	public static final String ONSALE_PAY = "onSale";
	private Long id;
	private int code;
	private Supplier supplier;
	private String paymentType;
	private List<Shipment> shipments;
	private BigDecimal restToPay;
	private Date created;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@ManyToOne
	@JoinColumn(name = "supplier_id")
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

	public BigDecimal getRestToPay() {
		return restToPay;
	}

	public void setRestToPay(BigDecimal restToPay) {
		this.restToPay = restToPay;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice", cascade = CascadeType.ALL)
	public List<Shipment> getShipments() {
		return shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}