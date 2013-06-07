package com.pfe.server.dao.invoice;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.shared.model.Invoice;
import com.pfe.shared.model.Supplier;

public interface InvoiceDao extends IBaseDao<Long, Invoice> {

	/**
	 * Retrieves invoices belonging to given supplier 
	 * 
	 * @param supplier
	 * @return
	 */
	public List<Invoice> getBySupplier(Supplier supplier);
}