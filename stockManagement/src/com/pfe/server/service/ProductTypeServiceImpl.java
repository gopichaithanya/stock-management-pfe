package com.pfe.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.ProductTypeService;
import com.pfe.server.dao.producttype.ProductTypeDao;
import com.pfe.shared.BusinessException;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@Service("productTypeService")
// declares service bean
public class ProductTypeServiceImpl implements ProductTypeService {

	@Autowired
	private ProductTypeDao pTypeDao;

	@Override
	public List<ProductType> getProductTypes() {
		return pTypeDao.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ProductType createProductType(ProductType productType)
			throws BusinessException {

		ProductType pt = pTypeDao.getProductTypeByName(productType.getName());
		if (pt != null) {
			throw new BusinessException("The name you chose is already in use.");
		}
		return pTypeDao.merge(productType);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ProductType updateProductType(ProductType initial,
			ProductType updatedBuffer) throws BusinessException {

		ProductType duplicate = pTypeDao.getDuplicateName(initial.getId(),
				updatedBuffer.getName());
		if (duplicate != null) {

			BusinessException ex = new BusinessException(
					"The name you chose is already in use.");
			throw ex;

		}

		initial.setDescription(updatedBuffer.getDescription());
		initial.setName(updatedBuffer.getName());

		return pTypeDao.merge(initial);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteProductType(ProductType productType)
			throws BusinessException {
		// TODO check if at least one shipment with this type in DB and throw
		// exception
		pTypeDao.deleteProductType(productType);

	}

	@Override
	public PagingLoadResult<ProductType> getTypesWithPaging(PagingLoadConfig config) {
		
		List<ProductType> list = pTypeDao.findAll();

		List<ProductType> sublist = new ArrayList<ProductType>();
		int start = config.getOffset();
		int limit = list.size();
		if (config.getLimit() > 0) {
			limit = Math.min(start + config.getLimit(), limit);
		}
		for (int i = config.getOffset(); i < limit; i++) {
			sublist.add(list.get(i));
		}
		return new PagingLoadResultBean<ProductType>(sublist, list.size(),
				config.getOffset());

	}

}
