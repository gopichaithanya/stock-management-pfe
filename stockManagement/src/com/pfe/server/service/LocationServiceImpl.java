package com.pfe.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.LocationService;
import com.pfe.server.dao.location.LocationDAO;
import com.pfe.server.dao.locationtype.LocationTypeDAO;
import com.pfe.server.dao.stock.StockDAO;
import com.pfe.server.model.Location;
import com.pfe.server.model.LocationType;
import com.pfe.server.model.Stock;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.LocationDTO;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

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
		
		//Get name filter value 
		FilterConfig nameFilter = config.getFilters().get(0);
		String filterValue = nameFilter.getValue();	
		if(filterValue != null){
			filterValue.trim();
		}
		
		int size = (int) locationDao.countByCriteria(filterValue);
		
		int start = config.getOffset();
		int limit = config.getLimit();
		List<Location> sublist = locationDao.search(start, limit, filterValue);
		List<LocationDTO> dtos = new ArrayList<LocationDTO>();

		if (sublist.size() > 0) {
			for (Location invoice : sublist) {
				dtos.add(dozerMapper.map(invoice, LocationDTO.class, "miniLocation"));
			}
		}
		return new PagingLoadResultBean<LocationDTO>(dtos, size, config.getOffset());
	}

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public LocationDTO find(Long id) {
		Location entity = locationDao.get(id);
		return dozerMapper.map(entity, LocationDTO.class, "fullLocation");	 
	}

	@Override
	public List<LocationDTO> getAll() {
		List<Location> locations = locationDao.findAll();
		List<LocationDTO> dtos = new ArrayList<LocationDTO>();
		for(Location location : locations){
			dtos.add(dozerMapper.map(location, LocationDTO.class, "miniLocation"));
		}
		return dtos;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public LocationDTO create(LocationDTO location) throws BusinessException {
		
		Location entity = dozerMapper.map(location, Location.class);
		Location merged = locationDao.merge(entity);
		return dozerMapper.map(merged, LocationDTO.class, "miniLocation");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(LocationDTO location) throws BusinessException {
		Location entity = locationDao.get(location.getId());
		if(entity.getType().getDescription().equals(LocationType.warehouseDescription)){
			throw new BusinessException("You are not allowed to delete the warehouse");
		}
		
		SortedSet<Stock> stocks = entity.getStocks();
		if(stocks.size() > 0){
			throw new BusinessException("Please clear stocks before deleting location.");
		}
		locationDao.delete(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public LocationDTO update(LocationDTO updatedLocation) throws BusinessException {
	
		//Update relates to name or location type
		Location entity = dozerMapper.map(updatedLocation, Location.class);
		Location merged = locationDao.merge(entity);
		
		return dozerMapper.map(merged, LocationDTO.class, "miniLocation");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(List<LocationDTO> locations) throws BusinessException {
		for(LocationDTO location : locations){
			delete(location);
		}
		
	}

}
