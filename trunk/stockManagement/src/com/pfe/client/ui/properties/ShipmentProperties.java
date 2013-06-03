package com.pfe.client.ui.properties;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.pfe.shared.dto.ShipmentDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ShipmentProperties extends PropertyAccess<ShipmentDTO> {

	@Path("id")
	ModelKeyProvider<ShipmentDTO> key();

	@Path("productType.name")
	ValueProvider<ShipmentDTO, String> productType();
	
	@Path("unitPrice")
	ValueProvider<ShipmentDTO, BigDecimal> unitPrice();
	
	@Path("initialQuantity")
	ValueProvider<ShipmentDTO, Integer> initialQty();
	
	@Path("currentQuantity")
	ValueProvider<ShipmentDTO, Integer> currentQty();
	
	@Path("paid")
	ValueProvider<ShipmentDTO, Boolean> paid();
	
	@Path("created")
	ValueProvider<ShipmentDTO, Date> created();
	
	@Path("invoice.code")
	ValueProvider<ShipmentDTO, Integer> invoiceCode();
}
