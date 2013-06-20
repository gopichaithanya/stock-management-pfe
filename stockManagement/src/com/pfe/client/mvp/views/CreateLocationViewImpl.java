package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.client.ui.properties.LocationTypeProperties;
import com.pfe.shared.dto.LocationTypeDTO;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.TextField;

public class CreateLocationViewImpl extends Window implements CreateLocationView {

	private static final LocationTypeProperties properties = GWT.create(LocationTypeProperties.class);
	private LocationPresenter presenter;
	
	private TextField nameField;
	private ListStore<LocationTypeDTO> locations;
	private ComboBox<LocationTypeDTO> locationCombo;
	
	public CreateLocationViewImpl(){
		
		VerticalPanel vp = new VerticalPanel();
		FramedPanel fpanel = new FramedPanel();
		HtmlLayoutContainer container = new HtmlLayoutContainer(getTableMarkup());
		fpanel.setWidget(container);
		fpanel.setHeaderVisible(false);
		fpanel.setBorders(false);
		vp.add(fpanel);
		
		nameField = new TextField();
		FieldLabel nameLabel = new FieldLabel(nameField, "Name");
		container.add(nameLabel, new HtmlData(".name"));
		
		locations = new ListStore<LocationTypeDTO>(properties.key());
		locationCombo = new ComboBox<LocationTypeDTO>(locations, properties.nameLabel());
		FieldLabel locationLabel = new FieldLabel(locationCombo, "Type");
		container.add(locationLabel, new HtmlData(".type"));
		
		TextButton cancelBtn = new TextButton("Cancel");
		TextButton submitBtn = new TextButton("Save");
		//submitBtn.addSelectHandler(new SubmitBtnHandler());
		//cancelBtn.addSelectHandler(new CancelBtnHandler(this));
		fpanel.setButtonAlign(BoxLayoutPack.CENTER);
		fpanel.addButton(submitBtn);
		fpanel.addButton(cancelBtn);
		List<FieldLabel> labels = FormPanelHelper.getFieldLabels(fpanel);
		for (FieldLabel lbl : labels) { lbl.setLabelAlign(LabelAlign.TOP);}

		this.add(vp);
		setHeadingText("New location");
	}
	
	@Override
	public void setPresenter(LocationPresenter presenter) {
		this.presenter = presenter;

	}
	
	/**
	 * HTML table
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
		this.locations.clear();
		this.locations.addAll(locations);
		
	}

}
