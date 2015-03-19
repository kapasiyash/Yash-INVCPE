package com.elitecore.cpe.web.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

import com.elitecore.cpe.web.base.ui.module.BaseComposerOperationImpl;

public class GeneralUtility {

	public static final int stringToInt(String value, int defaultValue) {
		try { 
			defaultValue = Integer.parseInt(value);
		}catch(Exception e) {
		}
		return defaultValue;
	}

	public static final long stringToLong(String value, long defaultValue) {
		try { 
			defaultValue = Long.parseLong(value);
		}catch(Exception e) {
		}
		return defaultValue;
	}
	
	public static final String displayValueIfNull(String value) {
		return value == null ? "-" : value;
	}
	public static final String blankDisplayValueIfNull(String value) {
		return value == null ? "" : value;
	}
	
	//Added by Jignesh Patel
	public static final String displayValueIfNullOrEmpty(String value) {
		return (value == null || value.equals("") || value.equals("null")) ? "-" : value;
	}
	
	public static final Double round(double unrounded, int precision, int roundingMode)
	{
	    BigDecimal bd = new BigDecimal(unrounded);
	    BigDecimal rounded = bd.setScale(precision, roundingMode);
	    return rounded.doubleValue();
	}
	
	public static final String displayINDateFormate(Timestamp value) {
		if(value==null){
			return "-";
		}else{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getDateFormat());
			return simpleDateFormat.format(new Date(value.getTime()));
		}
	}
	
	public static final String displayINDateTimeFormat(Timestamp value) {
		if(value==null){
			return "-";
		}else{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getDateTimeFormat());
			return simpleDateFormat.format(new Date(value.getTime()));
		}
	}
	
	private static String getDateFormat(){
		return BaseComposerOperationImpl.getDateFormat();
//		return "dd/mm/yyyy";
	}
	
	private static String getDateTimeFormat(){
		return BaseComposerOperationImpl.getDateTimeFormat();
	}
	
    public static void resetComponents(Component focusElement,Component... components){
    	for(Component component:components){
    		if(component instanceof Textbox)
    			((Textbox)component).setRawValue("");
    		else if(component instanceof Combobox)
    			((Combobox)component).setSelectedItem(null);
    		else if(component instanceof Longbox)
    			((Longbox)component).setRawValue(null);
    		else if(component instanceof Datebox)
    			((Datebox)component).setRawValue(null);
    		else if(component instanceof Label)
    			((Label)component).setValue("");
    		
    	}
    	((InputElement)focusElement).setFocus(true);
    }

    public static String generateAlias(String alias){
    	String retString = null;
    	if(alias!=null && !alias.isEmpty()){
    		retString = alias.replace(" ", "_").toUpperCase();
    	}
    	return retString;
    }
    
}
