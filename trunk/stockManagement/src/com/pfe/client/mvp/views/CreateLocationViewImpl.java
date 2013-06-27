package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.client.ui.CloseWindowButonHandler;
import com.pfe.client.ui.properties.LocationTypeProperties;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.LocationTypeDTO;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.TextField;

public class CreateLocationViewImpl extends Window implements CreateLocationView {

	private static final LocationTypeProperties properties = GWT.create(LocationTypeProperties.class);
	private LocationPresenter presenter;
	
	private TextField nameField;
	private ListStore<LocationTypeDTO> types;
	private ComboBox<LocationTypeDTO> typeCombo;
	
	public CreateLocationViewImpl(){
		
		setHeadingText("New location");
		setMinHeight(200);
		setModal(true);
		setResizable(false);
		setClosable(false);
		
		VerticalPanel vp = new VerticalPanel();
		FramedPanel fpanel = new FramedPanel();
		HtmlLayoutContainer container = new HtmlLayoutContainer(getTableMarkup());
		fpanel.setWidget(container);
		fpanel.setHeaderVisible(false);
		fpanel.setBorders(false);
		vp.add(fpanel);
		
		nameField = new TextField();
		nameField.addValidator(new EmptyValidator<String>());
		FieldLabel nameLabel = new FieldLabel(nameField, "Name");
		container.add(nameLabel, new HtmlData(".name"));
		
		types = new ListStore<LocationTypeDTO>(properties.key());
		typeCombo = new ComboBox<LocationTypeDTO>(types, properties.nameLabel());
		typeCombo.setForceSelection(true);
		typeCombo.setAllowBlank(false);
		FieldLabel locationLabel = new FieldLabel(typeCombo, "Type");
		container.add(locationLabel, new HtmlData(".type"));
		
		TextButton cancelBtn = new TextButton("Cancel");
		TextButton submitBtn = new TextButton("Save");
		submitBtn.addSelectHandler(new SubmitBtnHandler());
		cancelBtn.addSelectHandler(new CloseWindowButonHandler(this));
		fpanel.setButtonAlign(BoxLayoutPack.CENTER);
		fpanel.addButton(submitBtn);
		fpanel.addButton(cancelBtn);
		List<FieldLabel> labels = FormPanelHelper.getFieldLabels(fpanel);
		for (FieldLabel lbl : labels) { lbl.setLabelAlign(LabelAlign.TOP);}

		this.add(vp);
	}
	
	@Override
	public void setPresenter(LocationPresenter presenter) {
		this.presenter = presenter;

	}

	/**
	 * Save new location type handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class SubmitBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			if (nameField.isValid() && typeCombo.isValid()) {
				LocationDTO location = new LocationDTO();
				location.setName(nameField.getValue());
				location.setType(typeCombo.getValue());
				presenter.create(location);
			}
		}
	}
	
	/**
	 * HTML table used to display the window components
	 * 
	 * @return
	 */
	private native String getTableMarkup() /*-{
		return [
				'<table width=100% cellpadding=10 cellspacing=20>',
				'<tr><td class=name></td></tr>',
				'<tr><td class=type></td></tr>', '</table>'

		].join("");
	}-*/;

	@Override
	public void setLocationTypes(List<LocationTypeDTO> locations) {
		this.types.clear();
		this.types.addAll(locations);
		
		//Remove warehouse
		for(LocationTypeDTO type : locations){
			if(type.getDescription().equals("warehouse")){
				types.remove(type);
				break;
			}
		}
	}

}
