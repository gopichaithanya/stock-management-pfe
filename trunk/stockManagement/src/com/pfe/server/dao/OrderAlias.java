package com.pfe.server.dao;

import java.io.Serializable;

public class OrderAlias implements Serializable {

	public static final int INNER_JOIN = 0;
	public static final int FULL_JOIN = 4;
	public static final int LEFT_OUTER_JOIN = 1;
	public static final int RIGHT_OUTER_JOIN = 2;
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8524175841437709026L;
	
	private String name;
	private String associationPath;
	private int type = LEFT_OUTER_JOIN;
	
	/**
	 * @param name
	 * @param associationPath
	 */
	public OrderAlias(String name, String associationPath) {
		this(name, associationPath, LEFT_OUTER_JOIN);
	}
	
	/**
	 * @param name
	 * @param associationPath
	 * @param type
	 */
	public OrderAlias(String name, String associationPath, int type) {
		this.name = name;
		this.associationPath = associationPath;
		this.type = type;
	}	
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param associationPath the associationPath to set
	 */
	public void setAssociationPath(String associationPath) {
		this.associationPath = associationPath;
	}
	/**
	 * @return the associationPath
	 */
	public String getAssociationPath() {
		return associationPath;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		switch(type) {
			case INNER_JOIN:
			case FULL_JOIN:
			case LEFT_OUTER_JOIN:
			case RIGHT_OUTER_JOIN:
				this.type = type;
		}		
	}
	
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	
	
}
