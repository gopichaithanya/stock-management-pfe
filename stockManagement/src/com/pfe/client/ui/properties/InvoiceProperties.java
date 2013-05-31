package com.pfe.client.ui.properties;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.pfe.shared.dto.InvoiceDto;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface InvoiceProperties extends PropertyAccess<InvoiceDto> {

	@Path("id")
	ModelKeyProvider<InvoiceDto> key();
	
	@Path("code")
	LabelProvider<InvoiceDto> codeLabel();	

	@Path("code")
	ValueProvider<InvoiceDto, Integer> code();
	
	@Path("supplier.name")
	ValueProvider<InvoiceDto, String> supplier();
	
	@Path("shipments.size")
	ValueProvider<InvoiceDto, Integer> shipments();
	
	@Path("restToPay")
	ValueProvider<InvoiceDto, BigDecimal> restToPay();
	
	@Path("created")
	ValueProvider<InvoiceDto, Date> created();
	
}
