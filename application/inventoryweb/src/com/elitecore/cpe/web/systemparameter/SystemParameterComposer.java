package com.elitecore.cpe.web.systemparameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.data.system.systemparameter.SystemParameterGroupWrapperData;
import com.elitecore.cpe.bl.data.system.systemparameter.SystemParameterWrapperData;
import com.elitecore.cpe.bl.delegates.system.systemparameter.SystemParameterBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.facade.system.internal.SystemInternalDataConversionUtil;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.core.listener.LoadCacheDataListener;
import com.elitecore.cpe.web.utils.MessageUtility;

public class SystemParameterComposer extends BaseModuleComposer {

	private static final long serialVersionUID = 1L;
	private static final String MODULE = "SYSTEM_PARAMETER_COMPOSER";
	
	private Tabs tabs;
	private Tabpanels tabPanels;
	private Button btnUpdateSystemParam;
	private SystemParameterBD systemParameterBD;
	private List<SystemParameterGroupWrapperData> systemParameterGroupWrapperDatas;
	private Map<Long, Component> systemParameterMap = new HashMap<Long, Component>();
	public void afterCompose(Window comp) throws ModuleInitializationException {
		super.afterCompose(comp);
		systemParameterBD = new SystemParameterBD(getBDSessionContext());
		try {
			systemParameterGroupWrapperDatas = systemParameterBD.findAllSystemParameterGroups();

			if (systemParameterGroupWrapperDatas != null && !systemParameterGroupWrapperDatas.isEmpty()) {
				for (SystemParameterGroupWrapperData systemParameterGroupWrapperData : systemParameterGroupWrapperDatas) {
					Grid grid = new Grid();
					Columns columns = new Columns();
					Column srColumn = new Column("Sr.No");
					srColumn.setWidth("55px");
					Column column1 = new Column("Parameter Name");
					column1.setWidth("20%");
					Column column2 = new Column("Value");
					column2.setWidth("20%");
					Column column3 = new Column("Description");
					column3.setWidth("60%");
					columns.appendChild(srColumn);
					columns.appendChild(column1);
					columns.appendChild(column3);
					columns.appendChild(column2);
					grid.appendChild(columns);
					Rows rows = new Rows();

					Tab tab = new Tab(systemParameterGroupWrapperData.getName());
					Tabpanel tabpanel = new Tabpanel();
					if (systemParameterGroupWrapperData.getSystemParameterWrapperDatas() != null && !systemParameterGroupWrapperData.getSystemParameterWrapperDatas().isEmpty()) {
						Collections.sort(new ArrayList<SystemParameterWrapperData>(systemParameterGroupWrapperData.getSystemParameterWrapperDatas()));
						int counter = 1;
						for (SystemParameterWrapperData systemParameterWrapperData : systemParameterGroupWrapperData.getSystemParameterWrapperDatas()) {
							if(systemParameterWrapperData.getIsVisible() == 'Y'){
								Row row = new Row();
								row.setHeight("10px");
								row.setValign("center");
								row.appendChild(new Label(String.valueOf(counter)));
								row.appendChild(new Label(systemParameterWrapperData.getName()));
								row.appendChild(new Label(systemParameterWrapperData.getDescription()));
								if(systemParameterWrapperData.getCustomFieldTypeId().equals(SystemParameterConstants.TEXT_BOX)){
									Textbox textbox = new Textbox(systemParameterWrapperData.getValue());
									if(systemParameterWrapperData.getRegEx()!=null){
										textbox.setConstraint("no empty, /"+systemParameterWrapperData.getRegEx()+"/" + ": Please enter valid value. ");
									}
									systemParameterMap.put(systemParameterWrapperData.getSystemParameterId(), textbox);
									row.appendChild(textbox);
								}
								//--added for password field start
								if(systemParameterWrapperData.getCustomFieldTypeId().equals(SystemParameterConstants.PASSWORD)){
									Textbox textbox = new Textbox(systemParameterWrapperData.getValue());
									textbox.setType("password");
									if(systemParameterWrapperData.getRegEx()!=null){
										textbox.setConstraint("no empty, /"+systemParameterWrapperData.getRegEx()+"/" + ": Please enter valid value. ");
									}
									systemParameterMap.put(systemParameterWrapperData.getSystemParameterId(), textbox);
									row.appendChild(textbox);
								}
								//--added for password field end
								else if(systemParameterWrapperData.getCustomFieldTypeId().equals(SystemParameterConstants.COMBO_BOX) || systemParameterWrapperData.getCustomFieldTypeId().equals(SystemParameterConstants.SQL_COMBO_BOX)){
										Combobox combobox = new Combobox();
										if(systemParameterWrapperData.getComboBoxMap() != null && !systemParameterWrapperData.getComboBoxMap().isEmpty()){
											Logger.logTrace(MODULE, "Size of Encryption Data "+systemParameterWrapperData.getComboBoxMap().size());
											for(Entry<String, String> entry : systemParameterWrapperData.getComboBoxMap().entrySet()){
												
												Comboitem comboitem = new Comboitem(entry.getValue());
												comboitem.setValue(entry.getKey());
												combobox.appendChild(comboitem);
											}
											combobox.setRawValue(systemParameterWrapperData.getValue());
										}
										systemParameterMap.put(systemParameterWrapperData.getSystemParameterId(), combobox);
										row.appendChild(combobox);
								} else if(systemParameterWrapperData.getCustomFieldTypeId().equals(SystemParameterConstants.BAND_BOX)){
									final Bandbox bandbox = new Bandbox();
									bandbox.setReadonly(true);
									bandbox.setValue(systemParameterWrapperData.getValue());
									 List<String> list = SystemInternalDataConversionUtil.convertCommaSeparatedStringToList(systemParameterWrapperData.getValue());
									bandbox.setConstraint("no empty : Please select atleast one value ");
									Bandpopup bandpopup = new Bandpopup();
									final Listbox listbox = new Listbox();
									listbox.setWidth("170px");
									listbox.setMultiple(true);
									listbox.setCheckmark(true);
									for(Entry<String, String> entry : systemParameterWrapperData.getComboBoxMap().entrySet()){
										Listitem listitem = new Listitem(entry.getValue());
										if(list.contains(entry.getValue())) {
											listitem.setSelected(true);
										}
 										listitem.setValue(entry.getKey());
										listbox.appendChild(listitem);
									}
									
									bandpopup.appendChild(listbox);
									bandbox.appendChild(bandpopup);
									
									
									listbox.addEventListener(Events.ON_SELECT, new EventListener<Event>() {

										@Override
										public void onEvent(Event event)throws Exception {
											
											Set<Listitem> listItems = listbox.getSelectedItems();
											if(listItems!=null && !listItems.isEmpty()) {
												StringBuilder value = new StringBuilder("");
												for(Listitem listitem : listItems) {
													value.append(listitem.getLabel()+",");
												}
												String bandValue = value.toString().substring(0, value.toString().lastIndexOf(","));
												bandbox.setValue(bandValue);
											} else {
												bandbox.setValue("");
											}
											
										}
									});
									systemParameterMap.put(systemParameterWrapperData.getSystemParameterId(), bandbox);
									row.appendChild(bandbox);
									
								} 
								rows.appendChild(row);
							}
							counter++;
						}
					}
					grid.appendChild(rows);
					tabpanel.setSclass("main-cont");
					tabpanel.setStyle("padding-top: 10px; padding-left: 20px; overflow: auto;");
					tabpanel.appendChild(grid);
					tabs.appendChild(tab);
					tabPanels.appendChild(tabpanel);
				}
			}
		} catch (SearchBLException e) {
			e.printStackTrace();
			Messagebox.show("Could not load System Parameters.");
		} catch (TechnicalException e) {
			e.printStackTrace();
			Messagebox.show("Could not load System Parameters.");
		}
		btnUpdateSystemParam.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				Logger.logTrace(MODULE, "inside onClick updateSystemParameter");
				if (systemParameterGroupWrapperDatas != null && !systemParameterGroupWrapperDatas.isEmpty()) {
					for (SystemParameterGroupWrapperData systemParameterGroupWrapperData : systemParameterGroupWrapperDatas) {
						if (systemParameterGroupWrapperData.getSystemParameterWrapperDatas() != null && !systemParameterGroupWrapperData.getSystemParameterWrapperDatas().isEmpty()) {
							Map<Long, SystemParameterWrapperData> sysParameterMap = new HashMap<Long, SystemParameterWrapperData>();
							for (SystemParameterWrapperData systemParameterWrapperData : systemParameterGroupWrapperData.getSystemParameterWrapperDatas()) {
								if (systemParameterMap.containsKey(systemParameterWrapperData.getSystemParameterId())) {
									Component component = systemParameterMap.get(systemParameterWrapperData.getSystemParameterId());
									if (component instanceof Textbox) {
										Textbox textbox = (Textbox) component;
										systemParameterWrapperData.setValue(textbox.getValue());
									} 
								}
								sysParameterMap.put(systemParameterWrapperData.getSystemParameterId(), systemParameterWrapperData);
							}
							systemParameterGroupWrapperData.setSystemParameterMap(sysParameterMap);
						}
					}
				}
				try {
					systemParameterBD.updateSystemParameters(systemParameterGroupWrapperDatas);
					MessageUtility.successInformation("Success","System Parameters updated successfully");
					Logger.logTrace(MODULE, "Reloading Cache");
					LoadCacheDataListener cacheDataListener = new LoadCacheDataListener(getBDServletContext());
					cacheDataListener.run();
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("Error updating System Parameters. reason : " + e.getMessage());
				}
			}
		});
	}
	
	
	
}
