package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.elitecore.cpe.bl.ws.data.util.DateFormatterAdapter;


public class ValidateFinancialPeriodRequestData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Date financialDate;

	
	@XmlSchemaType(name="dateTime")
    @XmlJavaTypeAdapter(DateFormatterAdapter.class)
    @XmlElement(required=true)
	public Date getFinancialDate() {
		return financialDate;
	}

	public void setFinancialDate(Date financialDate) {
		this.financialDate = financialDate;
	}

	@Override
	public String toString() {
		return "ValidateFinancialPeriodRequestData [financialDate="
				+ financialDate + "]";
	}
	
	
	

}
