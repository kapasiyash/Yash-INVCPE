package com.elitecore.cpe.bl.facade.system.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemAction;
import com.elitecore.cpe.bl.facade.BaseDataConversionUtils;


public class SystemInternalDataConversionUtil extends BaseDataConversionUtils{

	public static SystemActionData getSystemActionData(SystemAction systemAction){
			SystemActionData systemActionData = new SystemActionData();
			systemActionData.setName(systemAction.getName());
			systemActionData.setActionAlias(systemAction.getActionAlias());
			systemActionData.setActionId(systemAction.getActionId());
			if(systemAction.getParentActionId() == null)
				systemActionData.setParentAction(true);		
			else
				systemActionData.setParentAction(false);
			systemActionData.setSequencenumber(systemAction.getSequenceNumber());
			systemActionData.setZulPageUrl(systemAction.getPageUrl());
			systemAction.setIsAuditable(systemAction.getIsAuditable());
			systemAction.setEnableaudit(systemAction.getEnableaudit());
		return systemActionData;
	}
	
	private static SystemActionData getChildSystemActionData(SystemAction childSystemAction){
		SystemActionData systemActionData = new SystemActionData();
		systemActionData.setName(childSystemAction.getName());
		systemActionData.setActionAlias(childSystemAction.getActionAlias());
		systemActionData.setActionId(childSystemAction.getActionId());		
		systemActionData.setSequencenumber(childSystemAction.getSequenceNumber());
		systemActionData.setZulPageUrl(childSystemAction.getPageUrl());		
		systemActionData.setParentAction(false);
		systemActionData.setIsAuditable(childSystemAction.getIsAuditable());
		systemActionData.setEnableAudit(childSystemAction.getEnableaudit());
		if(childSystemAction.getChildActions()!=null && !childSystemAction.getChildActions().isEmpty()){
			for(SystemAction systemAction : childSystemAction.getChildActions()){
				systemActionData.addChildAction(getChildSystemActionData(systemAction));	
			}				
		}
		return systemActionData;
	}
	
	public static List<String> convertCommaSeparatedStringToList(String data) {
		
		 List<String> list = new ArrayList<String>();
		
		 if(data!=null && !data.isEmpty()) {
			 String array[] = data.split(",");
			 if(array!=null && array.length!=0) {
				 for(String val : array) {
					 list.add(val);
				 }
			 }
		 }
		 
		 
		 return list;
	}
	
	
	public static Map<String, String> convertStringArrayToMap(String value[]) {
		
		 Map<String, String> map = new HashMap<String, String>();
		
		 if(value!=null && value.length!=0) {
			 for(String valueSource : value) {
				 String colon[]  = valueSource.split(":");
				 map.put(colon[1], colon[0]);
			 }
		 }
		 
		 
		 return map;
	}
	
	
}
