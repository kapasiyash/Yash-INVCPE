package com.elitecore.cpe.web.base.ui.module;



import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.core.BaseViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;




public abstract class BaseModuleViewComposer extends GenericForwardComposer<Hlayout> implements BaseComposerOperations{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_ENTITY_ID_KEY = "_viewEntityId";
	public static final String VIEW_ENTITY_APP_ID_KEY = "_viewEntityAppId";
	public static final String VIEW_ENTITY_NODE_ID_KEY = "_viewEntityNodeId";
	public static final String VIEW_COMPOSER_KEY = "_baseViewComposer";
	public static final String PARENT_TAB = "_parenttab";
	private Long viewEntityId;
	private String strViewEntityId;
	
	
	// Composer Implementation
	public void doAfterCompose(Hlayout comp) throws Exception {
		super.doAfterCompose(comp);
		
		if (arg.containsKey(VIEW_ENTITY_ID_KEY)) {
			if(arg.get(VIEW_ENTITY_ID_KEY) instanceof Long)
				viewEntityId = (Long)arg.get(VIEW_ENTITY_ID_KEY);
			else
				strViewEntityId = (String)arg.get(VIEW_ENTITY_ID_KEY);
		}	
		if (viewEntityId == null){
			viewEntityId = Long.valueOf(0);
		}
		afterCompose(comp);
	}
	
	public String getStrViewEntityId(){
		return this.strViewEntityId;
	}
	
	protected final Long getViewEntityId() {
		return viewEntityId;
	}
	
	
	public static void resetComponents(Component focusElement, Component... components) {
		for (Component component : components) {
			if (component instanceof Textbox)
				((Textbox) component).setRawValue("");
			else if (component instanceof Combobox)
				((Combobox) component).setSelectedItem(null);
			else if (component instanceof Datebox)
				((Datebox) component).setRawValue(null);
			else if (component instanceof Label)
				((Label) component).setValue("");
			else if (component instanceof Longbox)
				((Longbox) component).setRawValue(null);
			else if (component instanceof Intbox)
				((Intbox) component).setRawValue(null);
			else if(component instanceof Radiogroup)
				((Radiogroup)component).setSelectedItem(null);
			component.invalidate();
		}
		((InputElement) focusElement).setFocus(true);
	}
	
	protected void internalAfterCompose(Window comp) {
		// !important - null implementation to avoid multiple calls to afterCompose
	}
	
	public int getPageSize() {
		return Integer.parseInt(getSystemParamterValue(SystemParameterConstants.DEFAULT_PAGE_SIZE));//   Integer.valueOf(getSystemParamterValue(PAGESIZE_ALIASNAME));
	}
	
	
	public void setBDSessionContext(IBDSessionContext ibdSessionContext) {
		BaseComposerOperationImpl.setBDSessionContext(ibdSessionContext);
	}

	public IBDSessionContext getBDSessionContext() {
		return BaseComposerOperationImpl.getBDSessionContext();
	}

	public ServletContext getBDServletContext() {
		return BaseComposerOperationImpl.getBDServletContext();
	}

	public abstract void afterCompose(Hlayout comp) throws ModuleInitializationException;

	public final void logError(String module, String strMessage) {
		BaseComposerOperationImpl.logError(module, strMessage);
	}

	public final void logDebug(String module, String strMessage) {
		BaseComposerOperationImpl.logDebug(module, strMessage);

	}

	public final void logInfo(String module, String strMessage) {
		BaseComposerOperationImpl.logInfo(module, strMessage);
	}

	public final void logWarn(String module, String strMessage) {
		BaseComposerOperationImpl.logWarn(module, strMessage);
	}

	public final void logFatal(String module, String strMessage) {
		BaseComposerOperationImpl.logFatal(module, strMessage);
	}

	public final void logTrace(String module, String strMessage) {
		BaseComposerOperationImpl.logTrace(module, strMessage);
	}

	public final void logStackTrace(String module, Throwable t) {
		BaseComposerOperationImpl.logStackTrace(module, t);
	}

	public final boolean isFatalLevel() {
		return BaseComposerOperationImpl.isFatalLevel();
	}

	public final boolean isErrorLevel() {
		return BaseComposerOperationImpl.isErrorLevel();
	}

	public final boolean isWarnLevel() {
		return BaseComposerOperationImpl.isWarnLevel();
	}

	public final boolean isInfoLevel() {
		return BaseComposerOperationImpl.isInfoLevel();
	}

	public final boolean isDebugLevel() {
		return BaseComposerOperationImpl.isDebugLevel();
	}

	public final boolean isTraceLevel() {
		return BaseComposerOperationImpl.isTraceLevel();
	}

	public final boolean isAllLevel() {
		return BaseComposerOperationImpl.isAllLevel();
	}

	public void showErrorDialog(String errorMessage, Throwable error) {
		BaseComposerOperationImpl.showErrorDialog(errorMessage, error);
	}

	public final void showErrorDialog(String errorMessage, Throwable error, Window parent) {
		BaseComposerOperationImpl.showErrorDialog(errorMessage, error, parent);
	}

	public String getDateFormat() {
		return BaseComposerOperationImpl.getDateFormat();
	}

	public String getDateTimeFormat() {
		return BaseComposerOperationImpl.getDateTimeFormat();
	}

	public String getSystemParamterValue(String aliasName) {
		return BaseComposerOperationImpl.getSystemParamterValue(aliasName);
	}
	public static class ComboItemDataRenderer implements ComboitemRenderer<ComboData> {
		
		private Combobox combobox;
		private String name;

		public ComboItemDataRenderer() {

		}

		public ComboItemDataRenderer(Combobox combobox, String name) {
			this.combobox = combobox;
			this.name = name;
		}

		@Override
		public void render(Comboitem comboItem, ComboData generalComboItemEntity, int index) throws Exception {
			comboItem.setValue(generalComboItemEntity);
			comboItem.setLabel(generalComboItemEntity.getName());
			if(name != null && name.equals(generalComboItemEntity.getName())) {
				combobox.setSelectedIndex(index);
			}
		}

	}
	
	public static class ComboBoxItemDataRenderer implements ComboitemRenderer<ComboBoxData>{
		@Override
		public void render(Comboitem comboItem, ComboBoxData comboBoxData,int index) throws Exception {
			comboItem.setValue(comboBoxData);
			comboItem.setLabel(comboBoxData.getName());
		}

	}
	protected void addViewTab(Long id, String name, Tabbox tabbox, String viewFile, Map<String, Object> arg) {
		boolean tabExists = false;
		List<Component> tabs = tabbox.getTabs().getChildren();
		for (Component comp : tabs) {
			Tab currentTab = (Tab) comp;
			if (currentTab.getId().equals(id.toString())) {
				currentTab.setSelected(true);
				tabExists = true;
				break;
			}
		}

		if (!tabExists) {
			Tab newTab = new Tab(name);
			newTab.setId(id.toString());
			newTab.setSelected(true);
			newTab.setClosable(true);

			Tabpanel newTabpanel = new Tabpanel();

			newTabpanel.setStyle("padding-top: 10px; padding-left: 15px; overflow: auto;");
			newTabpanel.setSclass("main-cont");
			newTabpanel.setId(generateRandomId());
			
			if(arg == null){
				arg = new HashMap<String, Object>();
			}

			arg.put(BaseViewComposer.VIEW_ENTITY_ID_KEY, id);
			arg.put(BaseViewComposer.VIEW_ENTITY_ID_VALUE, id + "_" + (new Date()).getTime());
			arg.put(BaseViewComposer.VIEW_COMPOSER_PARENT, tabbox);
			arg.put(BaseViewComposer.PARENT_TAB_PANEL, newTabpanel);
			arg.put(BaseViewComposer.PARENT_TAB, newTab);

			Executions.createComponents(viewFile, newTabpanel, arg);

			tabbox.getTabs().appendChild(newTab);
			newTabpanel.setParent(tabbox.getTabpanels());

		}
	}
	
	
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
	
	
	private String generateRandomId(){
		Random random = new Random();
		return random.nextInt()*10000 + "";
//		return (int)(Math.random()*10000) + "";
	}
	
	public static class EmptyListItemRender implements ListitemRenderer<String>, Serializable {

		private static final long serialVersionUID = 1L;
		public int colspan;

		public EmptyListItemRender(int colspan) {
			this.colspan = colspan;
		}

		@Override
		public void render(Listitem item, String message, int index) throws Exception {
			item.setValue(message);
			Listcell listCell = new Listcell();
			listCell.setSpan(colspan);
			listCell.setStyle("text-align:center;");
			listCell.appendChild(new Label(message));
			item.appendChild(listCell);
		}
	}
	    
}
