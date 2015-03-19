package com.elitecore.cpe.web.base.ui.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;




/**
 * @author yash.kapasi
 *  
 * */
public abstract class BaseViewComposer extends BaseComposer {

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
	
	private static final ListitemRenderer<ActionMenuItem> _defRend = new ActionMenuItemRender();

	
	
	private Listbox currentListBox;
	private Div viewActionContent;
	Tabbox viewActionsTab = new Tabbox();
	
	public BaseViewComposer() {
		
	}

	protected abstract List<ActionMenuItem> getActionItemList();
	protected abstract List<ActionMenuItem> getViewItemList();
	
	
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

	/**
	 * <p>This method will close parent tab and refresh search grid. </p>
	 * */
	protected void closeParentTab(){
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
	
	public abstract void refreshView();

	private void addViewActionItems() {
		
		Tab tab1 = new Tab("Actions");
		Tab tab2 = new Tab("Views");
		
		Tabpanels tabPanels = new Tabpanels();
		tabPanels.setStyle("border-style: solid; border-color: #CFCFCF; border-width: 1px; padding: 0px; margin: 0px;");
		
		
		Tabpanel panel0 = new Tabpanel();
		panel0.setStyle("padding: 0px; margin: 0px; background-color: #EEF8FE; border: 0px;");

		List<BaseViewComposer.ActionMenuItem> availableList = getActionItemList();
		LinkedList<BaseViewComposer.ActionMenuItem> finalActionItemList = new LinkedList<BaseViewComposer.ActionMenuItem>();
		for(BaseViewComposer.ActionMenuItem actionMenuItem:availableList) {
			if(getBDSessionContext().getBLSession().isPermittedAction(actionMenuItem.getActionAlias())){
				finalActionItemList.add(actionMenuItem);
			}
		}
		
		Listbox box0 = new Listbox();
		box0.setOddRowSclass("non-odd");
		box0.setSclass("actionMenuList");
		box0.setStyle("border: 0px;");
		box0.setItemRenderer(_defRend);
		box0.setModel(new ListModelList<BaseViewComposer.ActionMenuItem>(finalActionItemList));
		
		panel0.appendChild(box0);
		tabPanels.appendChild(panel0);
		box0.addEventListener(Events.ON_SELECT, new ActionItemSelectionListener(box0));
		
		
		
		Tabpanel panel1 = new Tabpanel();
		panel1.setStyle("padding: 0px; margin: 0px; background-color: #EEF8FE; border: 0px;");

		List<BaseViewComposer.ActionMenuItem> availableViewList = getViewItemList();
		LinkedList<BaseViewComposer.ActionMenuItem> finalViewItemList = new LinkedList<BaseViewComposer.ActionMenuItem>();
		for(BaseViewComposer.ActionMenuItem actionMenuItem:availableViewList) {
			//check permitted action from session
			finalViewItemList.add(actionMenuItem);
		}
		
		Listbox box1 = new Listbox();
		box1.setOddRowSclass("non-odd");
		box1.setSclass("actionMenuList");
		box1.setStyle("border: 0px;");
		box1.setItemRenderer(_defRend);
		box1.setModel(new ListModelList<BaseViewComposer.ActionMenuItem>(finalViewItemList));
		
		panel1.appendChild(box1);
		tabPanels.appendChild(panel1);
		box1.addEventListener(Events.ON_SELECT, new ActionItemSelectionListener(box1));
		
		
		
		Tabs actionNavTabs = new Tabs();
		
		actionNavTabs.appendChild(tab1);
		actionNavTabs.appendChild(tab2);

		viewActionsTab.setHeight("450px");
		viewActionsTab.appendChild(actionNavTabs);
		viewActionsTab.appendChild(tabPanels);
		
	}
	
	public static class ActionMenuItem implements java.io.Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private String actionAlias;
		private String file;
		private String label;
		
		public ActionMenuItem(String actionAlias, String label, String file) {
			this.actionAlias = actionAlias;
			this.file = file;
			this.label = label;
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
		
		private Listbox source;
		
		public ActionItemSelectionListener(Listbox source) {
			this.source = source;
		}
		
		@Override
		public void onEvent(Event event) throws Exception {

			if (currentListBox != null && currentListBox != source) { //check the reference of current and source
				currentListBox.clearSelection();
			}
			currentListBox = source;
			
			Listitem item = source.getSelectedItem();
			if (viewActionContent != null) {	
				viewActionContent.getChildren().clear();
			}			
		
			Map<String, Object> baseViewComposerParams = new HashMap<String, Object>();
			baseViewComposerParams.put(VIEW_COMPOSER_KEY, BaseViewComposer.this);
			baseViewComposerParams.put(VIEW_ENTITY_ID_KEY, BaseViewComposer.this.getViewEntityId());
			baseViewComposerParams.put(PARENT_TAB_PANEL, (Tabpanel) arg.get(BaseViewComposer.PARENT_TAB_PANEL));
			baseViewComposerParams.put(VIEW_COMPOSER_PARENT, (Tabbox)arg.get(BaseViewComposer.VIEW_COMPOSER_PARENT));
			 
			Executions.createComponents(((ActionMenuItem) item.getValue()).getFile(), viewActionContent, baseViewComposerParams);
		}
		
	}

	
	public boolean isPermittedAction(String actionAlias) {
		
		return getBDSessionContext().getBLSession().isPermittedAction(actionAlias);
		
	}
	
	public SystemActionData getSystemActionData(String actionAlias) {
		
		SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(actionAlias);
		return actionData;
	}
	
	
}
