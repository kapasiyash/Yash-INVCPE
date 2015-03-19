package com.elitecore.cpe.web.base.ui.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;

public abstract class BaseCPEViewComposer extends BaseComposer {

	private static final long serialVersionUID = 1L;
			
	private Long viewEntityId;	
	private String strViewEntityId;
	
	public static final String VIEW_ENTITY_ID_KEY = "_viewEntityId";
	public static final String VIEW_ENTITY_ID_VALUE = "_viewEntityValue";	
	public static final String VIEW_COMPOSER_PARENT ="_viewParent";
	public static final String VIEW_COMPOSER_KEY = "_baseViewComposer";
	public static final String VIEW_ENTITY_TABBOX_REF = "_viewEntityTabboxRef";
	public static final String PARENT_TAB_PANEL = "_parentTabpanel";
	public static final String MENU = "_menu";
	public static final String MENU_ITEM = "_menu_Item";
	public static final String PARENT_TAB = "_parenttab";
	public static final String SEARCH_COMPOSER_REF="_searchComposerRef";
	
	private Div viewActionContent;
	private Menubar actionMenubar;

	
	public BaseCPEViewComposer() {
		
	}

	protected abstract List<ActionMenuItem> getActionItemList();
	protected abstract List<ActionMenuItem> getViewItemList();
	
	
	protected  void addViewTab(String id, String name, Tabbox tabbox, String viewFile,Map<String,Object> argMap) {
		boolean tabExists = false;
		List<Component> tabs = tabbox.getTabs().getChildren();
		for(Component comp:tabs){
			Tab currentTab=(Tab)comp;					
			if(currentTab.getId().equals(id.toString()+viewFile))
			{
				currentTab.setSelected(true);
				tabExists = true;
				break;
			}
		}
		
		if(!tabExists){
			Tab newTab = new Tab(name);
        	newTab.setId(id.toString()+viewFile);
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
	
	
	public ComponentInfo doBeforeCompose(Page page, Component parent,ComponentInfo compInfo) {
		ComponentInfo componentInfo =  super.doBeforeCompose(page, parent, compInfo);
		if (arg.containsKey(VIEW_ENTITY_ID_KEY)) {
			if(arg.get(VIEW_ENTITY_ID_KEY) instanceof Long)
				viewEntityId = (Long)arg.get(VIEW_ENTITY_ID_KEY);
			else
				strViewEntityId = (String)arg.get(VIEW_ENTITY_ID_KEY);
		}	
		if (viewEntityId == null){
			viewEntityId = Long.valueOf(0);
		}			
		return componentInfo;				
	}		
	
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);										
		addViewActionItems();
		
	}
	
	public String getStrViewEntityId(){
		return this.strViewEntityId;
	}

	public Long getViewEntityId() {
		return viewEntityId;
	}

	public void setViewEntityId(Long viewEntityId) {
		this.viewEntityId = viewEntityId;
	}			

	public abstract void refreshView();
	
	public boolean isPermittedAction(String actionAlias) {
		
		return getBDSessionContext().getBLSession().isPermittedAction(actionAlias);
		
	}
	
	public  SystemActionData getSystemActionData(String strActionAlias){
		return getBDSessionContext().getBLSession().getSystemAction(strActionAlias);
	}
	
	public void addViewActionItems() {
		Boolean actionEmpty=true;
		Boolean viewEmpty=true;
		
		Menu actionMenu = new Menu("Actions");
		Menupopup actionMenupopup = new Menupopup();
		actionMenu.setStyle("font-weight: bold; font-size: 10px;");
		actionMenu.setClass("abc");
		
		Menu viewMenu = new Menu("Views");
		Menupopup viewMenupopup = new Menupopup();
		viewMenu.setStyle("font-weight: bold; font-size: 10px;");
		viewMenu.setClass("abc");
		
		List<ActionMenuItem> availableList = getActionItemList();
		LinkedList<ActionMenuItem> finalActionItemList = new LinkedList<ActionMenuItem>();
		if(availableList!=null && !availableList.isEmpty()){
			for(ActionMenuItem actionMenuItem : availableList) {
//				if(BaseComposerOperationImpl.isPermitedAction(actionMenuItem.getActionAlias())){
					finalActionItemList.add(actionMenuItem);
//				}
			}
		}
		
		if(finalActionItemList!=null && !finalActionItemList.isEmpty()){
			for(ActionMenuItem actionItem : finalActionItemList){
				Component component = prepareMenuItem(actionItem);
				if(component!=null){
					actionMenupopup.appendChild(component);
				}
			}
		}
		
		
		List<ActionMenuItem> availableViewList = getViewItemList();
		LinkedList<ActionMenuItem> finalViewItemList = new LinkedList<ActionMenuItem>();
		
		if(availableViewList!=null && !availableViewList.isEmpty()){
			for(ActionMenuItem actionMenuItem:availableViewList) {
//				if(BaseComposerOperationImpl.isPermitedAction(actionMenuItem.getActionAlias())){
					finalViewItemList.add(actionMenuItem);
//				}
			}
		}
		
		if(finalViewItemList!=null && !finalViewItemList.isEmpty()){
			for(ActionMenuItem actionItem : finalViewItemList){
				Component component = prepareMenuItem(actionItem);
				if(component!=null){
					viewMenupopup.appendChild(component);
				}
			}
		}
		
		if(availableList!=null && !availableList.isEmpty()){
		actionMenu.appendChild(actionMenupopup);
		actionMenubar.appendChild(actionMenu);
		actionEmpty=false;
		}
		if(availableViewList!=null && !availableViewList.isEmpty()){
		viewMenu.appendChild(viewMenupopup);
		actionMenubar.appendChild(viewMenu);
		viewEmpty=false;
		}
		if(actionEmpty && viewEmpty){
			actionMenubar.setVisible(false);
		}
		
			
		
	}
	
	public void closeParentTab(){
		if(arg.containsKey(PARENT_TAB)){
			if(arg.get(PARENT_TAB)!=null && arg.get(PARENT_TAB) instanceof Tab){
				if(arg.containsKey(BaseSearchComposer.SEARCH_COMPOSER_REF) && arg.get(BaseSearchComposer.SEARCH_COMPOSER_REF) !=null  && arg.get (BaseSearchComposer.SEARCH_COMPOSER_REF) instanceof BaseSearchComposer ){
					BaseSearchComposer baseSearch = (BaseSearchComposer) arg.get(BaseSearchComposer.SEARCH_COMPOSER_REF); // Should be an instance of BaseSearchComposer
					baseSearch.onClick$btnSearch(new Event(Events.ON_CLICK));
				}
				Tab tab = (Tab) arg.get(PARENT_TAB);
				tab.close();
			}
		}
	}
	
	private Component prepareMenuItem(ActionMenuItem actionItem){
		if(actionItem ==null){
			return null;
		}else if(MENU.equals(actionItem.getMenuType())){
			Menu menu = new Menu(actionItem.getLabel());
			if(actionItem.getActionMenuItems()!=null && !actionItem.getActionMenuItems().isEmpty()){
				Menupopup menupopup = new Menupopup();
				menu.appendChild(menupopup);
				for(ActionMenuItem menuItem :actionItem.getActionMenuItems()){
				    Component component =	prepareMenuItem(menuItem);
				    if(component !=null){
				    		menupopup.appendChild(component);
				    }
				}
			}
			return menu;
		}else{
			Menuitem actionMenuitem = new Menuitem(actionItem.getLabel());
			actionMenuitem.addEventListener(Events.ON_CLICK, new ActionItemSelectionListener(actionItem));
			return actionMenuitem;
		}
	}
	
	public static class ActionMenuItem implements java.io.Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private String actionAlias;
		private String file;
		private String label;
		private String menuType;
		private List<ActionMenuItem> actionMenuItems;
		
		public ActionMenuItem(String actionAlias, String label, String file,String menuType) {
			this.actionAlias = actionAlias;
			this.file = file;
			this.label = label;
			this.menuType = menuType;
			this.actionMenuItems = new ArrayList<ActionMenuItem>();
		}
		public String getActionAlias() {
			return actionAlias;
		}
		public String getFile() {
			return file;
		}	
		public String getLabel() {
			return label;
		}	
		public void addActionMenuItem(ActionMenuItem actionMenuItem){
			this.actionMenuItems.add(actionMenuItem);
		}
		public String getMenuType() {
			return menuType;
		}
		public void setMenuType(String menuType) {
			this.menuType = menuType;
		}
		
		public List<ActionMenuItem> getActionMenuItems() {
			return actionMenuItems;
		}
		public String toString() {
			return "[ActionMenuItem:" + actionAlias +", "+file+']';
		}
	}


	private static class ActionMenuItemRender implements ListitemRenderer<ActionMenuItem>, java.io.Serializable {
		
		private static final long serialVersionUID = 1L;

		public void render(Listitem item, ActionMenuItem di, int index) {
			Listcell lc = new Listcell();
			item.setValue(di);
			lc.setHeight("16px");
			item.setId(di.getActionAlias());
			lc.setLabel(di.getLabel());
			lc.setParent(item);
			
		}
	}
	
	private class ActionItemSelectionListener implements EventListener<Event> {
		
		private ActionMenuItem selectedItem;
		
		public ActionItemSelectionListener(ActionMenuItem selectedItem){
			this.selectedItem = selectedItem;
		}
		
		@Override
		public void onEvent(Event event) throws Exception {
			
			if(viewActionContent!=null){
				viewActionContent.getChildren().clear();
			}
			
			Map<String, Object> baseViewComposerParams = new HashMap<String, Object>();
			baseViewComposerParams.put(VIEW_COMPOSER_KEY, BaseCPEViewComposer.this);
			baseViewComposerParams.put(VIEW_ENTITY_ID_KEY,arg.get(VIEW_ENTITY_ID_KEY));
			baseViewComposerParams.put(BaseViewComposer.PARENT_TAB_PANEL, (Tabpanel) arg.get(BaseViewComposer.PARENT_TAB_PANEL));
			baseViewComposerParams.put(BaseViewComposer.VIEW_COMPOSER_PARENT, (Tabbox)arg.get(BaseViewComposer.VIEW_COMPOSER_PARENT));
			if(arg!=null && !arg.isEmpty()){
            	for(Entry<?, ?> entrySet : arg.entrySet()){
            		baseViewComposerParams.put((String) entrySet.getKey(), entrySet.getValue());
            	}
            }
			
			Executions.createComponents(selectedItem.getFile(), viewActionContent, baseViewComposerParams);
		}
	}

	

}
