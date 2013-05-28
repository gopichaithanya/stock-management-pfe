package com.pfe.client.mvp.views;

import com.pfe.client.mvp.views.images.ImageResources;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class GridToolbar extends ToolBar {

	private TextButton addBtn;
	private TextButton editBtn;
	private TextButton deleteBtn;
	private TextButton filterBtn;
	private TextField filterText;
	
	public GridToolbar(){
		super();
		
		addBtn = new TextButton("Add", ImageResources.INSTANCE.addCreateIcon());
		editBtn = new TextButton("Edit", ImageResources.INSTANCE.addEditIcon());
		deleteBtn = new TextButton("Delete",
				ImageResources.INSTANCE.addDeleteIcon());
		filterBtn = new TextButton("Find");
		filterText = new TextField();
		
		setSpacing(5);
		setPadding(new Padding(5));
		add(addBtn);
		add(new SeparatorToolItem());
		add(editBtn);
		add(new SeparatorToolItem());
		add(deleteBtn);
		add(new SeparatorToolItem());
		add(filterBtn);
		add(filterText);

	}
	
	public TextButton getAddBtn() {
		return addBtn;
	}

	public void setAddBtn(TextButton addBtn) {
		this.addBtn = addBtn;
	}

	public TextButton getEditBtn() {
		return editBtn;
	}

	public void setEditBtn(TextButton editBtn) {
		this.editBtn = editBtn;
	}

	public TextButton getDeleteBtn() {
		return deleteBtn;
	}

	public void setDeleteBtn(TextButton deleteBtn) {
		this.deleteBtn = deleteBtn;
	}

	public TextButton getFilterBtn() {
		return filterBtn;
	}

	public void setFilterBtn(TextButton filterBtn) {
		this.filterBtn = filterBtn;
	}

	public TextField getFilterText() {
		return filterText;
	}

	public void setFilterText(TextField filterText) {
		this.filterText = filterText;
	}
	
}
