package com.elitecore.cpe.web.base.ui.module;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

import com.elitecore.cpe.web.base.ui.core.BaseViewComposer;

public  class BaseDialogComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected  void addViewTab(Long id, String name, Tabbox tabbox, String viewFile,Map<String,Object> argMap) {
		boolean tabExists = false;
		List<Component> tabs = tabbox.getTabs().getChildren();
		for(Component comp:tabs){
			Tab currentTab=(Tab)comp;						
			if(currentTab.getId().equals(id.toString())){
				currentTab.setSelected(true);
				tabExists = true;
				break;
			}
		}
		
		if(!tabExists){
			Tab newTab = new Tab(name);
        	newTab.setId(id.toString());
            newTab.setSelected(true);
            newTab.setClosable(true);
            
            Tabpanel newTabpanel = new Tabpanel();           
           
            newTabpanel.setStyle("padding-top: 10px; padding-left: 15px; overflow: auto;");
            newTabpanel.setSclass("main-cont");            
            Map<String, Object> arg = new HashMap<String, Object>();
            
            arg.put(BaseViewComposer.VIEW_ENTITY_ID_KEY, id);
            arg.put(BaseViewComposer.VIEW_ENTITY_ID_VALUE, id +  "_"  + (new Date()).getTime());                        
            arg.put(BaseViewComposer.VIEW_COMPOSER_PARENT, tabbox);
            arg.put(BaseViewComposer.PARENT_TAB_PANEL, newTabpanel);
            if(argMap!=null && !argMap.isEmpty()){
            	for(Map.Entry<String,Object> entrySet : argMap.entrySet()){
            		arg.put(entrySet.getKey(), entrySet.getValue());
            	}
            }
            
            Executions.createComponents(viewFile, newTabpanel, arg);
            
            tabbox.getTabs().appendChild(newTab);
            newTabpanel.setParent(tabbox.getTabpanels());
            
		}
	}
}
