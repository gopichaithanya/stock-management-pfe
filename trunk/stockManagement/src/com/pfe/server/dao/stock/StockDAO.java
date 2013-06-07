package com.pfe.server.dao.stock;

import com.pfe.server.dao.IBaseDao;
import com.pfe.shared.model.Location;
import com.pfe.shared.model.ProductType;
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
}
