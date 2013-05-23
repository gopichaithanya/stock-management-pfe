package com.pfe.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public ProductType createProductType(ProductType productType)
			throws BusinessException {

		List<ProductType> l = pTypeDao.getPTypeByName(productType.getName());
		if (l.size() > 0) {
			BusinessException ex = new BusinessException();
			ex.setMessage("The name you chose is already in use.");
			throw ex;
		}
		return pTypeDao.createProductType(productType);

	}

	@Override
	public void updateProductType(ProductType productType)
			throws BusinessException {
		
		List<ProductType> l = pTypeDao.getPTypeByName(productType.getName());
		if (l.size() > 0) {
			ProductType p = l.get(0);
			//if the type having this name is not the one being updated
			if(!p.getId().equals(productType.getId())){ 
				BusinessException ex = new BusinessException();
				ex.setMessage("The name you chose is already in use.");
				throw ex;
			} else {
				pTypeDao.updateProductType(productType);
			}
		}
		
	}

}
