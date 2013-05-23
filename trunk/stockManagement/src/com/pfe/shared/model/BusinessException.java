package com.pfe.shared.model;

import java.io.Serializable;

/**
 * Business logic exception
 * 
 * @author Alexandra
 * 
 */
@SuppressWarnings("serial")
public class BusinessException extends Exception implements Serializable {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
