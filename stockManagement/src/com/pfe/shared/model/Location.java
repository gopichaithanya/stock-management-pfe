package com.pfe.shared.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "Locations")
public class Location implements Serializable{

	private UUID id;
	private String name;
	private LocationType type;
	private List<Stock> stocks;

	@Id
	@Type(type = "pg-uuid")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name="type_id")
	public LocationType getType() {
		return type;
	}

	public void setType(LocationType type) {
		this.type = type;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}
	
	/**
	 * Returns stock by product type
	 * 
	 * @param pType
	 * @return
	 */
	public Stock getStockByType(ProductType pType){
		int i = 0;
		Boolean found = false;
		int size = stocks.size();
		Stock s = null;
		
		while(!found && i < size){
			Stock currentStock = stocks.get(i);
			if(currentStock.getType().getId().equals(pType.getId())){
				found = true;
				s = currentStock;
			}
			i++;
		}
		return s;
	}
	
	/**
	 * 
	 * @param pType
	 * @param quantity
	 */
	public void receiveProducts(ProductType pType, int quantity){
		Stock stock = getStockByType(pType);
		if(stock == null){
			stock = new Stock();
			stock.setType(pType);
			stock.setQuantity(quantity);
			getStocks().add(stock);
		} else{
			int newStoreQty = stock.getQuantity() + quantity;
			stock.setQuantity(newStoreQty);
		}
	}
	
	/**
	 * Contract: the stock of the given type exists in the
	 * location and its quantity is sufficient
	 * 
	 * @param pType
	 * @param quantity
	 */
	public void removeProducts(ProductType pType, int quantity){
		Stock stock = getStockByType(pType);
		int newQuantity = stock.getQuantity() - quantity;
		stock.setQuantity(newQuantity);
	}

}