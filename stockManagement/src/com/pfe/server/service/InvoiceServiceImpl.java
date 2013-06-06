package com.pfe.server.service;

import java.math.BigDecimal;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.InvoiceService;
import com.pfe.server.dao.invoice.InvoiceDao;
import com.pfe.server.dao.supplier.SupplierDao;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.model.Invoice;
import com.pfe.shared.model.Supplier;

@Service("invoiceService") 
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public InvoiceDTO update(InvoiceDTO initial, InvoiceDTO buffer)
			throws BusinessException {
		//id, code, payment type not editable
		
//		Boolean OK = true;
//		if(OK){
//			throw new BusinessException("Unable to update Invoice");
//		}
		
		Invoice invoice = dozerMapper.map(initial,Invoice.class, "fullInvoice");
		
		//supplier update
		Long initialId = initial.getSupplier().getId();
		Long updatedId = buffer.getSupplier().getId();
		if(!initialId.equals(updatedId)){
			
			Supplier updatedSupplier = supplierDao.get(updatedId);
			invoice.setSupplier(updatedSupplier);
			updatedSupplier.getInvoices().add(invoice);
			
			Supplier initialSupplier = supplierDao.get(initialId);
			initialSupplier.getInvoices().remove(invoice);
		
			supplierDao.merge(initialSupplier);
			supplierDao.merge(updatedSupplier);
		}
		//rest to pay update
		int initialDebt = initial.getRestToPay();
		int updatedDebt = buffer.getRestToPay();
		if(initialDebt != updatedDebt){
			BigDecimal debt = new BigDecimal(updatedDebt);
			invoice.setRestToPay(debt);
		}
		
		invoiceDao.merge(invoice);	
		InvoiceDTO dto = dozerMapper.map(invoice, InvoiceDTO.class, "fullInvoice");
		return dto;
	}

}
