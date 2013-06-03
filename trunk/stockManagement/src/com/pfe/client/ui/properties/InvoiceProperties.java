package com.pfe.client.ui.properties;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.pfe.shared.dto.InvoiceDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface InvoiceProperties extends PropertyAccess<InvoiceDTO> {

	@Path("id")
	ModelKeyProvider<InvoiceDTO> key();
	
	@Path("code")
	LabelProvider<InvoiceDTO> codeLabel();	

	@Path("code")
	ValueProvider<InvoiceDTO, Integer> code();
	
	@Path("supplier.name")
	ValueProvider<InvoiceDTO, String> supplier();
	
	@Path("shipments.size")
	ValueProvider<InvoiceDTO, Integer> shipments();
	
	@Path("restToPay")
	ValueProvider<InvoiceDTO, BigDecimal> restToPay();
	
	@Path("created")
	ValueProvider<InvoiceDTO, Date> created();
	
}
