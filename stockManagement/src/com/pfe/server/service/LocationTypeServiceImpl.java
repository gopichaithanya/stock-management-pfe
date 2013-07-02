package com.pfe.server.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfe.client.service.LocationTypeService;
import com.pfe.server.dao.locationtype.LocationTypeDAO;
import com.pfe.server.model.LocationType;
import com.pfe.shared.dto.LocationTypeDTO;

@Service("locationTypeService")
public class LocationTypeServiceImpl implements LocationTypeService {
	
	@Autowired
	private LocationTypeDAO dao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	public List<LocationTypeDTO> getAll() {
		List<LocationType> types = dao.findAll();
		List<LocationTypeDTO> dtos = new ArrayList<LocationTypeDTO>();
		for(LocationType type : types){
			dtos.add(dozerMapper.map(type, LocationTypeDTO.class));
		}
		
		return dtos;
	}

}
