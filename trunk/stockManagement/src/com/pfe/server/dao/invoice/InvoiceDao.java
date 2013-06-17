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
	
	/**
	 * Retrieves invoices applying limit of results
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Invoice> search(int start, int limit); 
	
	/**
	 * Retrieves invoices where restToPay greater than 0 and applying limit of results.
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Invoice> searchUnpaid(int start, int limit); 
	
	/**
	 * Counts invoices with restToPay greater than 0
	 * 
	 * @return
	 */
	public long countUnpaid();
}
