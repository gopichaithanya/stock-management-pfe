package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.client.ui.CloseWindowButonHandler;
import com.pfe.shared.dto.ProductTypeDTO;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;

public class CreateProductTypeViewImpl extends Window implements CreateProductTypeView {

	private TextField nameField;
	private HtmlEditor descriptionEditor;
	private ProductTypePresenter presenter;

	public CreateProductTypeViewImpl() {
		
		setHeadingText("New product type");
		setMinHeight(350);
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
		nameField.addValidator(new MinLengthValidator(2));
		nameField.addValidator(new EmptyValidator<String>());
		container.add(new FieldLabel(nameField, "Name"), new HtmlData(".name"));

		descriptionEditor = new HtmlEditor();
		descriptionEditor.setHeight(200);
		FieldLabel descriptor = new FieldLabel(descriptionEditor, "Description");
		container.add(descriptor, new HtmlData(".description"));

		TextButton cancelBtn = new TextButton("Cancel");
		TextButton submitBtn = new TextButton("Save");
		submitBtn.addSelectHandler(new SubmitBtnHandler());
		cancelBtn.addSelectHandler(new CloseWindowButonHandler(this));

		fpanel.addButton(cancelBtn);
		fpanel.addButton(submitBtn);
		fpanel.setButtonAlign(BoxLayoutPack.CENTER);
		// need to call after everything is constructed
		List<FieldLabel> labels = FormPanelHelper.getFieldLabels(fpanel);
		for (FieldLabel lbl : labels) {
			lbl.setLabelAlign(LabelAlign.TOP);
		}

		vp.add(fpanel);
		this.add(vp);

	}

	/**
	 * Save new product type handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class SubmitBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			if (nameField.isValid()) {
				ProductTypeDTO productType = new ProductTypeDTO();
				productType.setName(nameField.getValue());
				productType.setDescription(descriptionEditor.getValue());
				presenter.create(productType);
			}
		}
	}

	/**
	 * HTML table
	 * 
	 * @return
	 */
	private native String getTableMarkup() /*-{
		return [ '<table width=100% cellpadding=0 cellspacing=0>',
				'<tr><td class=name></td></tr>',
				'<tr><td class=description></td></tr>', '</table>'

		].join("");
	}-*/;

	@Override
	public void setPresenter(ProductTypePresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void clearData() {
		nameField.clear();
		descriptionEditor.clear();

	}

}
