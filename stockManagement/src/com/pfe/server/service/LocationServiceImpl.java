package com.pfe.server.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.LocationService;
import com.pfe.server.dao.location.LocationDAO;
import com.pfe.server.dao.locationtype.LocationTypeDAO;
import com.pfe.server.dao.stock.StockDAO;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.model.Location;
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
		
		//TODO retrieve ordered by date
		int size = (int) locationDao.count();
		int start = config.getOffset();
		int limit = config.getLimit();
		List<Location> sublist = locationDao.search(start, limit);
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

}