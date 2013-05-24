package com.pfe.server.dao;

import java.io.Serializable;

public abstract class SortField implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7964143981611801754L;
	
	private String 	field;
	private boolean ascending;
	
	@SuppressWarnings("unused")
	private SortField() {}
	
	public SortField(String field) {
		this(field, true);
	}
	
	public SortField(String field, boolean ascending) {
		setField(field);
		setAscending(ascending);
	}
	
	/**
	 * @param field the field to set
	 */
	private void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param ascending the ascending to set
	 */
	private void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	/**
	 * @return the ascending
	 */
	public boolean isAscending() {
		return ascending;
	}
	
	public boolean equals(Object other) {
	    if (this == other)
	    	return true;
	    if((other == null) || (other.getClass() != this.getClass()))
	    	return false;
	    
	    // The objects are of the same runtime class -> they are equals (in this specific case of a SortField)	    
	    return true;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + getField().hashCode();
		hash = 31 * hash + (isAscending() ? 0 : 1);
		return hash;
	}
	
	/**
	 * This method provides an OrderAlias for the more complex sorts that need to use a relation entity.
	 * It is meant to be overridden in the children classes
	 * @return
	 */
	public OrderAlias getOrderAlias() {
		return null;
	}
		
}
