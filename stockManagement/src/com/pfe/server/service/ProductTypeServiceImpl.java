package com.pfe.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.ProductTypeService;
import com.pfe.server.dao.ProductTypeDaoImpl;
import com.pfe.shared.model.BusinessException;
import com.pfe.shared.model.ProductType;

@Service("productTypeService")
// declares service bean
public class ProductTypeServiceImpl implements ProductTypeService {

	@Autowired
	private ProductTypeDaoImpl pTypeDao;

	@Override
	public List<ProductType> getProductTypes() {
		return pTypeDao.getProductTypes();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ProductType createProductType(ProductType productType)
			throws BusinessException {

		ProductType pt = pTypeDao.getPTypeByName(productType.getName());
		if (pt != null) {
			throw new BusinessException("The name you chose is already in use.");
		}
		return pTypeDao.createProductType(productType);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ProductType updateProductType(ProductType initial,
			ProductType updatedBuffer) throws BusinessException {

		ProductType duplicate = pTypeDao.getDuplicateName(initial.getId(),
				updatedBuffer.getName());
		if (duplicate != null) {

			BusinessException ex = new BusinessException();
			ex.setMessage("The name you chose is already in use.");
			throw ex;

		}

		initial.setDescription(updatedBuffer.getDescription());
		initial.setName(updatedBuffer.getName());

		return pTypeDao.updateProductType(initial);

	}

}
