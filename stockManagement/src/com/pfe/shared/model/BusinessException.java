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
	
	public BusinessException() {
	}
	
	public BusinessException(String msg) {
		this.message = msg;
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
