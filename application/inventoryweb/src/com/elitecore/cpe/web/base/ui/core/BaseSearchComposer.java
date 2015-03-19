package com.elitecore.cpe.web.base.ui.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;



/**
 * @author yash.kapasi
 *  
 * */
public abstract class BaseSearchComposer extends BaseComposer {
	
	public static final String SEARCH_COMPOSER_REF="_searchComposerRef";
	
	public abstract void onDoubleClickedSearchItem(Event event) throws Exception ;
	private static final long serialVersionUID = 1L; 	
	protected  void addViewTab(Long id, String name, Tabbox tabbox, String viewFile) {
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
           
            newTabpanel.setStyle("padding-top: 10px; padding-left: 0px; overflow: auto;");
            newTabpanel.setSclass("main-cont");            
            Map<String, Object> arg = new HashMap<String, Object>();
            
            arg.put(BaseViewComposer.VIEW_ENTITY_ID_KEY, id);
            arg.put(BaseViewComposer.VIEW_ENTITY_ID_VALUE, id +  "_"  + (new Date()).getTime());                        
            arg.put(BaseViewComposer.VIEW_COMPOSER_PARENT, tabbox);
            arg.put(BaseViewComposer.PARENT_TAB_PANEL, newTabpanel);
            arg.put(BaseViewComposer.PARENT_TAB, newTab);
            
            
            Executions.createComponents(viewFile, newTabpanel, arg);
            
            tabbox.getTabs().appendChild(newTab);
            newTabpanel.setParent(tabbox.getTabpanels());
            
		}
	}

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
           
            newTabpanel.setStyle("padding-top: 10px; padding-left: 0px; overflow: auto;");
            newTabpanel.setSclass("main-cont");            
            Map<String, Object> arg = new HashMap<String, Object>();
            
            arg.put(BaseViewComposer.VIEW_ENTITY_ID_KEY, id);
            arg.put(BaseViewComposer.VIEW_ENTITY_ID_VALUE, id +  "_"  + (new Date()).getTime());                        
            arg.put(BaseViewComposer.VIEW_COMPOSER_PARENT, tabbox);
            arg.put(BaseViewComposer.PARENT_TAB_PANEL, newTabpanel);
            arg.put(BaseViewComposer.PARENT_TAB, newTab);
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
	protected  void addViewTab(String id, String name, Tabbox tabbox, String viewFile,Map<String,Object> argMap) {
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
           
            newTabpanel.setStyle("padding-top: 10px; padding-left: 0px; overflow: auto;");
            newTabpanel.setSclass("main-cont");            
            Map<String, Object> arg = new HashMap<String, Object>();
            
            arg.put(BaseViewComposer.VIEW_ENTITY_ID_KEY, id);
            arg.put(BaseViewComposer.VIEW_ENTITY_ID_VALUE, id +  "_"  + (new Date()).getTime());                        
            arg.put(BaseViewComposer.VIEW_COMPOSER_PARENT, tabbox);
            arg.put(BaseViewComposer.PARENT_TAB_PANEL, newTabpanel);
            arg.put(BaseViewComposer.PARENT_TAB, newTab);
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
	
	
	
	protected  void addViewTab(String id, String name, Tabbox tabbox, String viewFile,Map<String,Object> argMap,boolean closable) {
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
            newTab.setClosable(closable);
            
            Tabpanel newTabpanel = new Tabpanel();           
           
            newTabpanel.setStyle("padding-top: 10px; padding-left: 0px; overflow: auto;");
            newTabpanel.setSclass("main-cont");            
            Map<String, Object> arg = new HashMap<String, Object>();
            
            arg.put(BaseViewComposer.VIEW_ENTITY_ID_KEY, id);
            arg.put(BaseViewComposer.VIEW_ENTITY_ID_VALUE, id +  "_"  + (new Date()).getTime());                        
            arg.put(BaseViewComposer.VIEW_COMPOSER_PARENT, tabbox);
            arg.put(BaseViewComposer.PARENT_TAB_PANEL, newTabpanel);
            arg.put(BaseViewComposer.PARENT_TAB, newTab);
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
	
	public void onClick$btnSearch(Event event){
		
	}
	
	
	
	
	public void onOK(Event event){
		onClick$btnSearch(event);
	}
	
	public boolean isPermittedAction(String actionAlias) {
		
		return getBDSessionContext().getBLSession().isPermittedAction(actionAlias);
		
	}
}
