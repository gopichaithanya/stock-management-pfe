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
	public InvoiceDTO update(InvoiceDTO updatedInvoice)
			throws BusinessException {
		//id, code, payment type not editable
		
//		Boolean OK = true;
//		if(OK){
//			throw new BusinessException("Unable to update Invoice");
//		}
		
		Long id = updatedInvoice.getId();
		Invoice invoice = invoiceDao.get(id);
		
		//supplier update
		Long initialId = invoice.getSupplier().getId();
		Long updatedId = updatedInvoice.getSupplier().getId();
		if(!initialId.equals(updatedId)){
			
			Supplier updatedSupplier = supplierDao.get(updatedId);
			invoice.setSupplier(updatedSupplier);
			updatedSupplier.getInvoices().add(invoice);
			
			Supplier initialSupplier = invoice.getSupplier();
			initialSupplier.getInvoices().remove(invoice);
		
			supplierDao.merge(initialSupplier);
			supplierDao.merge(updatedSupplier);
		}
		//rest to pay update
		BigDecimal initialDebt = invoice.getRestToPay();
		BigDecimal updatedDebt = new BigDecimal(updatedInvoice.getRestToPay());
		if(!(initialDebt.compareTo(updatedDebt) == 0)){
			invoice.setRestToPay(updatedDebt);
		}
		
		//TODO get updated shipments from buffer; if shipment id = null => it's newly added
		//TODO if missing ids in buffer when compared to initial list of shipments => some shipments have been deleted
		
		invoiceDao.merge(invoice);	
		InvoiceDTO dto = dozerMapper.map(invoice, InvoiceDTO.class, "fullInvoice");
		return dto;
	}

}
