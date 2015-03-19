package com.elitecore.cpe.bl.entity.inventory.core.expr;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="TBLMCONSTRAINTEXPRESSION")
public class ConstraintExpression implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String constraintExpressionId ;
	private String name;
	private String description;
	private String regEx;
	
	
	
	public ConstraintExpression() {
	}
		
	public ConstraintExpression(String constraintExpressionId, String name,
			String description, String regEx) {
		super();
		this.constraintExpressionId = constraintExpressionId;
		this.name = name;
		this.description = description;
		this.regEx = regEx;
	}
	
	@Id
	@Column(name="CONSTRAINTEXPRESSIONID")
	public String getConstraintExpressionId() {
		return constraintExpressionId;
	}
	public void setConstraintExpressionId(String constraintExpressionId) {
		this.constraintExpressionId = constraintExpressionId;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="REGEX")
	public String getRegEx() {
		return regEx;
	}
	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}

}
