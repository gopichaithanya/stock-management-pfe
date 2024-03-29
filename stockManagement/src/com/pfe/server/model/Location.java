package com.pfe.server.model;

import java.util.SortedSet;

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

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;


/**
 * The location stores products for sale. Attributes : a unique name, the type
 * (store, warehouse) and a list of stocks. Products can be sold from a location
 * in the limits of the available quantity in the stocks. Locations can ship or
 * receive products from other locations.
 * 
 * @author Alexandra
 * 
 */
@Entity
@Table(name = "Locations")
public class Location{

	private Long id;
	private String name;
	private LocationType type;
	private SortedSet<Stock> stocks;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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

	@ManyToOne
	@JoinColumn(name="type_id")
	public LocationType getType() {
		return type;
	}

	public void setType(LocationType type) {
		this.type = type;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "location")
	@Sort(type = SortType.COMPARATOR, comparator = Stock.StockComparator.class)
	public SortedSet<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(SortedSet<Stock> stocks) {
		this.stocks = stocks;
	}

}