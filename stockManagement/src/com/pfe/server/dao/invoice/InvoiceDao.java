package com.pfe.server.dao.invoice;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.server.model.Invoice;
import com.pfe.server.model.Supplier;

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
	 * The code parameter is ignored if its value == -1
	 * 
	 * @param start
	 * @param limit
	 * @param showAll if false, retrieves only unpaid invoices
	 * @param code filter value
	 * @return
	 */
	public List<Invoice> search(int start, int limit, Boolean showAll, int code); 
	
	/**
	 * Retrieves invoices applying limit of results and filter by supplier name.
	 * The supplier name parameter is ignored if null or blank.
	 * 
	 * @param start
	 * @param limit
	 * @param supplierName
	 * @return
	 */
	public List<Invoice> search(int start, int limit, String supplierName); 
	
	/**
	 * Counts records applying criteria for filter on invoice code and show all/
	 * show unpaid only option. The code parameter is ignored if its value == -1
	 * 
	 * @param countAll
	 * @param code
	 * @return
	 */
	public long countByCriteria(Boolean countAll, int code);
	

	/**
	 * Counts invoice records where supplier name like given parameter. The
	 * boolean parameter indicates whether to count or not the paid invoices.
	 * The supplier name parameter is ignored if null or blank.
	 * 
	 * @param showAll
	 * @param supplierName
	 * @return
	 */
	public int countByCriteria(Boolean showAll, String supplierName);
}
