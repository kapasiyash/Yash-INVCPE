package com.elitecore.cpe.bl.vo.core.expr;

import java.io.Serializable;

public class ConstraintExpressionVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String expressionId;
	private String name;
	private String regEx;
	
	
	
	public ConstraintExpressionVO(String expressionId, String name, String regEx) {
		super();
		this.expressionId = expressionId;
		this.name = name;
		this.regEx = regEx;
	}
	public String getExpressionId() {
		return expressionId;
	}
	public void setExpressionId(String expressionId) {
		this.expressionId = expressionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegEx() {
		return regEx;
	}
	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}
	
	

}
