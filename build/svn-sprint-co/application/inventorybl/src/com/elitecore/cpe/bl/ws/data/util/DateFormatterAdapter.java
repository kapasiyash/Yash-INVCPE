package com.elitecore.cpe.bl.ws.data.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateFormatterAdapter extends XmlAdapter<String, Date> {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public Date unmarshal(final String v)  {
        Date tempd=null;
        try{

        	Date duedate = null;
                System.out.println("DateU :: "+v);
                if(v==null || v.isEmpty()) {
                	duedate = null;
                } else {
                	duedate = formatter.parse(v);
                	tempd=formatter.parse(formatter.format(duedate));
                	
                }

        }catch(Exception e){
            e.printStackTrace();
        }
        return tempd;
    }

    @Override
    public String marshal(final Date v) {
        try{
            System.out.println("DateM :: "+v);
            
            String tempStr = "";
            
            if(v==null ) {
            	return tempStr;
            } else {
            	 
                Date tempd=formatter.parse(formatter.format(v));
                tempStr=formatter.format(tempd);
                return tempStr;	
            }
           
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}