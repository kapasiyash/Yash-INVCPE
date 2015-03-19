package com.elitecore.cpe.web.utils;

import java.util.ArrayList;
import java.util.List;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleData;
import com.elitecore.cpe.web.constants.Pages;

public class DefaultActionUtil {

	public static List<SystemModuleData> getDefaultModule(){
		List<SystemModuleData>  defaultModules = new ArrayList<SystemModuleData>();
//		SystemModuleData module = DefaultActionUtil.createNewModule(-100L, "Operation Manager", "OPERATION_MANAGER", 1L);
//		module.addSystemAction(DefaultActionUtil.createNewAction(-100L, "Back to OM", "BACK_TO_OM", Pages.BACK_TO_OM));
//		defaultModules.add(module);
		return defaultModules;
	}
	
	private static SystemModuleData createNewModule(Long moduleId,String moduleName,String alias,Long sequenceNumber){
		return new SystemModuleData(moduleId, moduleName, sequenceNumber, alias, "");
	}
	
	private static SystemActionData createNewAction(Long actionId, String name, String alias, String url){
		return new SystemActionData(actionId,true,1l,name,alias,new ArrayList<SystemActionData>(),url);
	}
}
