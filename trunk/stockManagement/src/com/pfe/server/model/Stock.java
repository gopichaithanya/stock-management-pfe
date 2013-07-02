package com.pfe.server.model;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pfe.shared.model.Location;

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
	public static class StockComparator implements Comparator<Stock> {

		public int compare(Stock s1, Stock s2) {

			if (s1 != null && s2 != null) {

				ProductType t1 = s1.getType();
				ProductType t2 = s2.getType();

				if (t1 != null && t2 != null) {

					String n1 = t1.getName();
					String n2 = t2.getName();

					if (n1 == null && n2 == null) {
						return 0;
					} else if (n1 == null) {
						return -1;
					} else if (n2 == null) {
						return 1;
					} else {
						return n1.toLowerCase().compareTo(n2.toLowerCase());
					}

				} else
					return 0;

			} else
				return 0;
		}
	}

}
