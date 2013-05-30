package com.pfe.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.pfe.shared.model.Invoice;

public class SupplierDto implements Serializable {

	private Long id;
	private String name;
	private String description;
	private List<Invoice> invoices;
	
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
	public List<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
}
