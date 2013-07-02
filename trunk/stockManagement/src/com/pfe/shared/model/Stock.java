package com.pfe.shared.model;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pfe.server.model.ProductType;

/**
 * A stock contains information on products stored in a certain location : the
 * type of products, the quantity and the location.
 * 
 * @author Alexandra
 * 
 */
@Entity
@Table(name = "Stocks")
public class Stock {

	private Long id;
	private int quantity;
	private ProductType type;
	private Location location;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@ManyToOne
	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "location_id")
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * Compares two Stock entities on the basis of their product type name. Used
	 * to order stocks alphabetically by type name.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class StockComparator implements Comparator<Stock>{

		public int compare(Stock s1, Stock s2){
			String t1 = s1.getType().getName();
			String t2 = s2.getType().getName();

			if (t1 == null && t2 == null){
				return 0;
			} else if (t1 == null){
				return -1;
			} else if (t2 == null){
				return 1;
			} else{
				return t1.toLowerCase().compareTo(t2.toLowerCase());
			}

		}
	}

}
