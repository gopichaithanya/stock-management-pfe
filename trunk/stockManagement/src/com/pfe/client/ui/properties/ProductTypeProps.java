package com.pfe.client.ui.properties;

import com.google.gwt.editor.client.Editor.Path;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ProductTypeProps extends PropertyAccess<ProductType> {
	
	//TODO remove this class when only dtos will be used on client
	
	@Path("id")
	ModelKeyProvider<ProductType> key();
	
	@Path("name")
	LabelProvider<ProductType> nameLabel();	

	@Path("name")
	ValueProvider<ProductType, String> name();
	

	@Path("description")
	ValueProvider<ProductType, String> description();
}
