package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.presenters.InvoicePresenter;
import com.pfe.client.service.InvoiceServiceAsync;
import com.pfe.shared.dto.InvoiceDTO;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.IntegerPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;

public class EditInvoiceViewImpl extends Window implements EditInvoiceView {

	private Widget parent;
	private InvoicePresenter presenter;
	private ClientFactory clientFactory;

	private InvoiceDTO invoice;
	private NumberField<Integer> codeField;
	private DateField dateField;
	private TextField paymentField;
	private NumberField<Integer> debtField;
	private TextField supplierField;

	public EditInvoiceViewImpl() {

		setBodyBorder(false);
		setWidth(550);
		setHeight(350);
		setMinHeight(350);
		setModal(true);
		setResizable(false);
		setClosable(false);

		VerticalPanel vp = new VerticalPanel();
		FramedPanel fpanel = new FramedPanel();
		HtmlLayoutContainer container = new HtmlLayoutContainer(
				getTableMarkup());
		fpanel.setWidget(container);
		fpanel.setHeaderVisible(false);
		fpanel.setBorders(false);

		codeField = new NumberField<Integer>(new IntegerPropertyEditor());
		codeField.isReadOnly();
		container.add(new FieldLabel(codeField, "Code"), new HtmlData(".code"));
		dateField = new DateField();
		container.add(new FieldLabel(dateField, "Created On"), new HtmlData(
				".date"));
		paymentField = new TextField();
		container.add(new FieldLabel(paymentField, "Payment Type"),
				new HtmlData(".payment"));
		debtField = new NumberField<Integer>(new IntegerPropertyEditor());
		container.add(new FieldLabel(debtField, "Rest to pay"), new HtmlData(
				".debt"));
		supplierField = new TextField();
		container.add(new FieldLabel(supplierField, "Supplier"), new HtmlData(
				".supplier"));

		TextButton cancelBtn = new TextButton("Cancel");
		TextButton submitBtn = new TextButton("Save");
		submitBtn.addSelectHandler(new SubmitBtnHandler());
		cancelBtn.addSelectHandler(new CancelBtnHandler(this));

		fpanel.setButtonAlign(BoxLayoutPack.CENTER);
		fpanel.addButton(submitBtn);
		fpanel.addButton(cancelBtn);
		List<FieldLabel> labels = FormPanelHelper.getFieldLabels(fpanel);
		for (FieldLabel lbl : labels) {
			lbl.setLabelAlign(LabelAlign.TOP);
		}

		vp.add(fpanel);
		this.add(vp);

	}

	/**
	 * Save updates
	 * 
	 * @author Alexandra
	 * 
	 */
	private class SubmitBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			// if window parent is null, it means the window was opened from the
			// invoice list
			if (parent == null) {
				// TODO call presenter simple update method here
			}
			// if parent is edit supplier window
			else if (parent instanceof EditSupplierView) {
				InvoiceServiceAsync rpcService = clientFactory
						.getInvoiceService();
				// call service here directly
			}

		}
	}

	/**
	 * Close window
	 * 
	 * @author Alexandra
	 * 
	 */
	private class CancelBtnHandler implements SelectHandler {

		private Window w;

		public CancelBtnHandler(Window w) {
			this.w = w;
		}

		@Override
		public void onSelect(SelectEvent event) {
			w.hide();
		}
	}

	/**
	 * HTML table
	 * 
	 * @return
	 */
	private native String getTableMarkup() /*-{
		return [
				'<table width=100% cellpadding=10 cellspacing=10>',
				'<tr><td class=code width=30%></td> <td class=date width=30%></td><td class=payment width=30%></tr>',
				'<tr><td class=debt width=30%><td class=supplier width=30%></td></tr>',
				'<tr><td class=shipments colspan=2></tr>', '</table>'

		].join("");
	}-*/;

	@Override
	public void setData(InvoiceDTO invoice) {
		this.invoice = invoice;
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPresenter(InvoicePresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setParent(Window w) {
		this.parent = w;

	}

	@Override
	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;

	}

	@Override
	public void show() {
		codeField.setValue(invoice.getCode());
		dateField.setValue(invoice.getCreated());
		paymentField.setValue(invoice.getPaymentType());
		debtField.setValue(invoice.getRestToPay().intValue());
		supplierField.setValue(invoice.getSupplier().getName());
		setHeadingText("Invoice " + invoice.getCode());
		super.show();
	}

}
