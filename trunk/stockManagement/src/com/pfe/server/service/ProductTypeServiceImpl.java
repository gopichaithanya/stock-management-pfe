package com.pfe.server.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfe.client.service.ProductTypeService;
import com.pfe.server.dao.ProductTypeDao;
import com.pfe.shared.model.ProductType;

@Service("productTypeService") //declares service bean
public class ProductTypeServiceImpl implements ProductTypeService {
	
	@Autowired
	private ProductTypeDao pTypeDao;
	
	@PostConstruct
	public void init() throws Exception {}
	
	@PreDestroy
	public void destroy() {}

	@Override
	public List<ProductType> getProductTypes() {
		return pTypeDao.getProductTypes();
	}

}
