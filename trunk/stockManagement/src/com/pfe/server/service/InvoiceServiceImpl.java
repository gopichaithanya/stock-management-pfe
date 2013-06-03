package com.pfe.server.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public InvoiceDTO update(InvoiceDTO initial, InvoiceDTO buffer)
			throws BusinessException {
		// TODO Auto-generated method stub
		System.out.println("in update invoice");
		return null;
	}

}
