package com.pfe.server.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfe.client.service.LocationService;
import com.pfe.server.dao.location.LocationDAO;
import com.pfe.server.dao.locationtype.LocationTypeDAO;
import com.pfe.server.dao.stock.StockDAO;
import com.pfe.shared.dto.LocationDTO;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

@Service("locationService") 
public class LocationServiceImpl implements LocationService {
	
	@Autowired
	private LocationDAO locationDao;
	@Autowired
	private LocationTypeDAO locationTypeDao;
	@Autowired
	private StockDAO stockDao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	public PagingLoadResult<LocationDTO> search(FilterPagingLoadConfig config) {
		// TODO Auto-generated method stub
		return null;
	}

}
