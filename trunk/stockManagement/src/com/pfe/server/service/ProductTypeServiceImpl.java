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
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@Service("productTypeService")
// declares service bean
public class ProductTypeServiceImpl implements ProductTypeService {

	@Autowired
	private ProductTypeDao dao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	public List<ProductTypeDTO> getAll() {
		List<ProductType> types = dao.findAll();
		List<ProductTypeDTO> dtos = new ArrayList<ProductTypeDTO>();

		if (types.size() > 0) {
			for (ProductType type : types) {
				dtos.add(dozerMapper.map(type, ProductTypeDTO.class));
			}
		}
		return dtos;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ProductType create(ProductType productType) throws BusinessException {

		ProductType pt = dao.search(productType.getName());
		if (pt != null) {
			throw new BusinessException("The name you chose is already in use.");
		}
		return dao.merge(productType);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ProductType update(ProductType initial, ProductType updatedBuffer)
			throws BusinessException {

		ProductType duplicate = dao.getDuplicateName(initial.getId(),
				updatedBuffer.getName());

		if (duplicate != null) {
			BusinessException ex = new BusinessException(
					"The name you chose is already in use.");
			throw ex;
		}

		initial.setDescription(updatedBuffer.getDescription());
		initial.setName(updatedBuffer.getName());
		return dao.merge(initial);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(ProductType productType) throws BusinessException {
		// TODO check if at least one shipment with this type in DB and throw
		// exception
		dao.delete(productType);

	}

	@Override
	public PagingLoadResult<ProductType> search(FilterPagingLoadConfig config) {

		int size = (int) dao.count();
		int start = config.getOffset();
		int limit = config.getLimit();
		List<ProductType> sublist = dao.search(start, limit, null);
		return new PagingLoadResultBean<ProductType>(sublist, size,
				config.getOffset());

	}

	@Override
	public PagingLoadResult<ProductType> filter(FilterPagingLoadConfig config, String filterValue) {

		int start = config.getOffset();
		int limit = config.getLimit();
		List<ProductType> sublist = dao.searchLike(start, limit,filterValue);
		int size = (int) dao.countLike(filterValue);
		return new PagingLoadResultBean<ProductType>(sublist, size, config.getOffset());
	}

}
