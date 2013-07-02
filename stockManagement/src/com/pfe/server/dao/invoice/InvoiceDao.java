package com.pfe.server.dao.invoice;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.server.model.Supplier;
import com.pfe.shared.model.Invoice;

public interface InvoiceDao extends IBaseDao<Long, Invoice> {

	/**
	 * Retrieves invoices belonging to given supplier 
	 * 
	 * @param supplier
	 * @return
	 */
	public List<Invoice> getBySupplier(Supplier supplier);
	

	/**
	 * Retrieves invoices applying limit of results and filter by code.
	 * 
	 * @param start
	 * @param limit
	 * @param showAll if false, retrieves only unpaid invoices
	 * @param code filter value
	 * @return
	 */
	public List<Invoice> search(int start, int limit, Boolean showAll, int code); 
	
	
	/**
	 * Counts records applying criteria for filter code and show all/ show
	 * unpaid only option
	 * 
	 * @param countAll
	 * @param code
	 * @return
	 */
	public long countByCriteria(Boolean countAll, int code);
}
