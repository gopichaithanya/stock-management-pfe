package com.pfe.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.InvoiceDTO;

/**
 * RPC Service that handles invoice CRUD
 * 
 * @author Alexandra
 * 
 */
@RemoteServiceRelativePath("gxt3/invoiceService")
public interface InvoiceService extends RemoteService {

	/**
	 * Updates invoice
	 * 
	 * @param initial
	 * @param buffer
	 * @return
	 * @throws BusinessException
	 */
	public InvoiceDTO update(InvoiceDTO initial, InvoiceDTO buffer) throws BusinessException;
}
