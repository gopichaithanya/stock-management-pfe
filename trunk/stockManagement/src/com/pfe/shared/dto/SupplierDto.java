package com.pfe.shared.dto;

import java.io.Serializable;
import java.util.List;

public class SupplierDto implements Serializable {

	private Long id;
	private String name;
	private String description;
	private List<InvoiceDto> invoices;
	
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
	public List<InvoiceDto> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<InvoiceDto> invoices) {
		this.invoices = invoices;
	}
}
