package com.pfe.server.dao.stock;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.server.model.ProductType;
import com.pfe.shared.model.Location;
import com.pfe.shared.model.Stock;

public interface StockDAO extends IBaseDao<Long, Stock> {

	/**
	 * Retrieves stock by location and product type
	 * 
	 * @param location
	 * @param type
	 * @return
	 */
	public Stock get(Location location, ProductType type);

	/**
	 * Retrieves stocks by type from all available locations
	 * 
	 * @param type
	 * @return
	 */
	public List<Stock> get(ProductType type);

	/**
	 * Retrieves stocks where product type name like parameter.
	 * 
	 * @param typeName
	 * @param location
	 * @return
	 */
	public List<Stock> search(String typeName);
}
