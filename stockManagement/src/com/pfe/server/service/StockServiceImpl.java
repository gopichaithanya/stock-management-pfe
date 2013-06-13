package com.pfe.server.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfe.client.service.StockService;
import com.pfe.server.dao.stock.StockDAO;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.StockDTO;

@Service("stockService") 
public class StockServiceImpl implements StockService {
	
	@Autowired
	private StockDAO stockDao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	public StockDTO sell(StockDTO stock, int quantity) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
