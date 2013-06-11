package com.pfe.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.InvoiceDTO;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * RPC Service that handles invoice CRUD
 * 
 * @author Alexandra
 * 
 */
@RemoteServiceRelativePath("gxt3/invoiceService")
public interface InvoiceService extends RemoteService {
	
	
	/**
	 * Retrieves invoice by id
	 * 
	 * @param id
	 * @return invoice
	 */
	public InvoiceDTO find(Long id);

	/**
	 * Updates invoice
	 * 
	 * @param buffer
	 * @return
	 * @throws BusinessException
	 */
	public InvoiceDTO update(InvoiceDTO updatedInvoice) throws BusinessException;
	
	/**
	 * Retrieves invoices with paging
	 * 
	 * @param config
	 * @return
	 */
	public PagingLoadResult<InvoiceDTO> search(FilterPagingLoadConfig config);
}
