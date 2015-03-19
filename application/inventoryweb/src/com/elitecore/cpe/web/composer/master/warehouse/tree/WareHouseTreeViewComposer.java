package com.elitecore.cpe.web.composer.master.warehouse.tree;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class WareHouseTreeViewComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CreateWareHouseTreeComposer treeComposer;
	 private Div viewActionContent;
	 
	 
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		Logger.logTrace("Audit", "Inside afterCompose [JM]");
		Map<String,Object> typeMap=new HashMap<String,Object>();
		typeMap.put(CreateWareHouseTreeComposer.MODE, "Create");
		typeMap.put(CreateWareHouseTreeComposer.COMPOSER_REF, this);
		Executions.createComponents(Pages.CREATE_WH_TREE_AUDIT, viewActionContent,typeMap);
		
	
	}
	
	public void demo(){System.out.println(treeComposer);}
	
	public void setTreeComposer(CreateWareHouseTreeComposer createTreeComposer) {
		treeComposer= createTreeComposer;
	}
	
}
