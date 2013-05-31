package com.pfe.client.ui.properties;

import com.google.gwt.editor.client.Editor.Path;
import com.pfe.shared.dto.SupplierDto;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface SupplierProperties extends PropertyAccess<SupplierDto> {

	
	@Path("id")
	ModelKeyProvider<SupplierDto> key();
	
	@Path("name")
	LabelProvider<SupplierDto> nameLabel();	

	@Path("name")
	ValueProvider<SupplierDto, String> name();
	
	@Path("description")
	ValueProvider<SupplierDto, String> description();
}
