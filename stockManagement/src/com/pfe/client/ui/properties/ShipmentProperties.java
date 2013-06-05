package com.pfe.client.ui.properties;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.ShipmentDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ShipmentProperties extends PropertyAccess<ShipmentDTO> {

	@Path("id")
	ModelKeyProvider<ShipmentDTO> key();

	@Path("productType")
	ValueProvider<ShipmentDTO, ProductTypeDTO> productType();
	
	@Path("unitPrice")
	ValueProvider<ShipmentDTO, Integer> unitPrice();
	
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
