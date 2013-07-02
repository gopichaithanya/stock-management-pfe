package com.pfe.server.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.ProductTypeService;
import com.pfe.server.dao.producttype.ProductTypeDao;
import com.pfe.server.dao.shipment.ShipmentDao;
import com.pfe.server.model.ProductType;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.model.Shipment;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@Service("productTypeService")
// declares service bean
public class ProductTypeServiceImpl implements ProductTypeService {

	@Autowired
	private ProductTypeDao typeDao;
	@Autowired
	private ShipmentDao shipmentDao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	public List<ProductTypeDTO> getAll() {
		List<ProductType> types = typeDao.findAll();
		List<ProductTypeDTO> dtos = new ArrayList<ProductTypeDTO>();

		if (types.size() > 0) {
			for (ProductType type : types) {
				dtos.add(dozerMapper.map(type, ProductTypeDTO.class));
			}
		}
		return dtos;
	}
	
	@Override
	public ProductTypeDTO find(Long id) {
		ProductType entity = typeDao.get(id);
		return dozerMapper.map(entity, ProductTypeDTO.class);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ProductTypeDTO create(ProductTypeDTO productType) throws BusinessException {

		ProductType pt = typeDao.search(productType.getName());
		if (pt != null) {
			throw new BusinessException("The name you chose is already in use.");
		}
		ProductType entity = dozerMapper.map(productType, ProductType.class);
		ProductType merged = typeDao.merge(entity);
		return dozerMapper.map(merged, ProductTypeDTO.class);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ProductTypeDTO update(ProductTypeDTO updatedType) throws BusinessException {

		ProductType duplicate = typeDao.getDuplicateName(updatedType.getId(), updatedType.getName());
		if (duplicate != null) {
			throw new BusinessException("The name you chose is already in use.");
		}
		ProductType entity = dozerMapper.map(updatedType, ProductType.class);
		ProductType merged = typeDao.merge(entity);

		return dozerMapper.map(merged, ProductTypeDTO.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(ProductTypeDTO productType) throws BusinessException {
		
		ProductType entity = dozerMapper.map(productType, ProductType.class);
		
		//Retrieve first shipment of given type, irrespective if its current quantity
		List<Shipment> shipments = shipmentDao.search(0, 1, entity, true);
		if(shipments.size() > 0){
			//If there are shipments of the given type in the database, even if items are sold,
			//i.e. current quantity is 0, the type cannot be deleted
			throw new BusinessException("Cannot delete type, shipments with products of this type exist.");
		}
		typeDao.delete(entity);
	}

	@Override
	public PagingLoadResult<ProductTypeDTO> search(FilterPagingLoadConfig config) {
		
		//Get filter value 
		FilterConfig codeFilter = config.getFilters().get(0);
		String filterValue = codeFilter.getValue();
		if(filterValue != null){
			filterValue.trim();
		}

		int size = (int) typeDao.countByCriteria(filterValue);
		int start = config.getOffset();
		int limit = config.getLimit();
		List<ProductType> sublist = typeDao.search(start, limit, filterValue);
		List<ProductTypeDTO> dtos = new ArrayList<ProductTypeDTO>();
		
		if(sublist.size() > 0){
			for(ProductType type : sublist){
				dtos.add(dozerMapper.map(type, ProductTypeDTO.class));
			}
		}
		return new PagingLoadResultBean<ProductTypeDTO>(dtos, size, config.getOffset());

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(List<ProductTypeDTO> productTypes) throws BusinessException {
		for(ProductTypeDTO type : productTypes){
			delete(type);
		}
		
	}

}
