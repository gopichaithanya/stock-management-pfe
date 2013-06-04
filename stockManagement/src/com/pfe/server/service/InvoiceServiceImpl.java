package com.pfe.server.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.InvoiceService;
import com.pfe.server.dao.invoice.InvoiceDao;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.InvoiceDTO;

@Service("invoiceService") 
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public InvoiceDTO update(InvoiceDTO initial, InvoiceDTO buffer)
			throws BusinessException {
		
		System.out.println("----------------in update invoice------------------------------");
		return new InvoiceDTO();
	}

}
