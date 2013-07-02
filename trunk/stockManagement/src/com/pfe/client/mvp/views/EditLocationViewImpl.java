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

public class EditLocationViewImpl extends Window implements EditLocationView {
	
	private static final LocationTypeProperties locationTypeProps = GWT.create(LocationTypeProperties.class);
	
	private LocationDTO location;
	private LocationPresenter presenter;
	private TextField nameField;
	private ListStore<LocationTypeDTO> locationTypeStore;
	private ComboBox<LocationTypeDTO> locationTypeCombo;
	
	public EditLocationViewImpl(){
	
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
		
		nameField = new TextField();
		nameField.addValidator(new EmptyValidator<String>());
		container.add(new FieldLabel(nameField, "Name"),new HtmlData(".name"));
		locationTypeStore = new ListStore<LocationTypeDTO>(locationTypeProps.key());
		locationTypeCombo = new ComboBox<LocationTypeDTO>(locationTypeStore, locationTypeProps.nameLabel());
		locationTypeCombo.setForceSelection(true);
		locationTypeCombo.setAllowBlank(false);
		container.add(new FieldLabel(locationTypeCombo, "Type"),new HtmlData(".type"));
		
		
		TextButton cancelBtn = new TextButton("Cancel");
		TextButton submitBtn = new TextButton("Save");
		submitBtn.addSelectHandler(new SubmitBtnHandler());
		cancelBtn.addSelectHandler(new CloseWindowButonHandler(this));
		fpanel.setButtonAlign(BoxLayoutPack.CENTER);
		fpanel.addButton(submitBtn);
		fpanel.addButton(cancelBtn);
		List<FieldLabel> labels = FormPanelHelper.getFieldLabels(fpanel);
		for (FieldLabel lbl : labels) { lbl.setLabelAlign(LabelAlign.TOP);}

		vp.add(fpanel);
		this.add(vp);
		
	}
	
	/**
	 * Displays data with table layout
	 * 
	 * @return
	 */
	private native String getTableMarkup() /*-{
		return [
				'<table width=100% cellpadding=20 cellspacing=20>',
				'<tr><td class=name width=50%></td><td class=type width=50%></td></tr>','</table>'
		].join("");
	}-*/;
	

	/**
	 * Save updates
	 * 
	 * @author Alexandra
	 * 
	 */
	private class SubmitBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			
			if(nameField.isValid() && locationTypeCombo.isValid()){
				location.setName(nameField.getValue());
				location.setType(locationTypeCombo.getValue());
				presenter.update(location);
			}
		}
	}
	
	
	@Override
	public void setPresenter(LocationPresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setData(LocationDTO location) {
		clearData();
		this.location = location;
		nameField.setValue(location.getName());
		locationTypeCombo.setValue(location.getType());
		setHeadingText(location.getName());

	}

	@Override
	public void clearData() {
		nameField.clear();
		locationTypeStore.clear();
	}

	@Override
	public void setLocationTypes(List<LocationTypeDTO> locations) {
		
		if(location.getType().getDescription().equals(LocationTypeDTO.WAREHOUSE_DESCRIPTION)){
			//Not allowed to change the warehouse type
			locationTypeCombo.setEditable(false);
			locationTypeCombo.setValue(location.getType());
		} else{
			
			locationTypeStore.clear();
			locationTypeStore.addAll(locations);
			
			//Remove warehouse
			for(LocationTypeDTO type : locations){
				if(type.getDescription().equals(LocationTypeDTO.WAREHOUSE_DESCRIPTION)){
					locationTypeStore.remove(type);
					break;
				}
			}
		}
		
	}

}
