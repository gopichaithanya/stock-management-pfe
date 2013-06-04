package com.pfe.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class SupplierDTO implements Serializable {

	private Long id;
	private String name;
	private String description;
	private ArrayList<InvoiceDTO> invoices;
	
	public SupplierDTO(){
		
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
	public ArrayList<InvoiceDTO> getInvoices() {
		return invoices;
	}
	public void setInvoices(ArrayList<InvoiceDTO> invoices) {
		this.invoices = invoices;
	}
}
