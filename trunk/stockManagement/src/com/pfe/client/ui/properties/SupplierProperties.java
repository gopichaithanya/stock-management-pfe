package com.pfe.client.ui.properties;

import com.google.gwt.editor.client.Editor.Path;
import com.pfe.shared.dto.SupplierDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface SupplierProperties extends PropertyAccess<SupplierDTO> {
	
	@Path("id")
	ModelKeyProvider<SupplierDTO> key();
	
	@Path("name")
	LabelProvider<SupplierDTO> nameLabel();	

	@Path("name")
	ValueProvider<SupplierDTO, String> name();
	
	@Path("description")
	ValueProvider<SupplierDTO, String> description();
}
