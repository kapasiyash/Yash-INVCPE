package com.elitecore.cpe.web.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.vo.system.agent.AgentParameterVO;
import com.elitecore.cpe.web.core.listener.LoadCacheDataListener;

public class CustomFieldUtils {
	
	
	public static Component getCustomComponenet(AgentParameterVO parameterVO , String defValue1,Listbox listbox) {
		if(parameterVO.getCustomfieldtypeid().equals(SystemParameterConstants.TEXT_BOX)){
			Textbox textbox = new Textbox();
			if(defValue1!=null){
				textbox.setValue(defValue1);
			}
			if(parameterVO.getExpressonVO()!=null){
				textbox.setConstraint("no empty, /"+parameterVO.getExpressonVO().getRegEx()+"/" + ": Please enter valid value.");
			}
			return textbox;
		}else if(parameterVO.getCustomfieldtypeid().equals(SystemParameterConstants.COMBO_BOX) || parameterVO.getCustomfieldtypeid().equals(SystemParameterConstants.SQL_COMBO_BOX)){
				Combobox combobox = new Combobox();
				if(parameterVO.getValueSource() != null && !parameterVO.getValueSource().isEmpty()){
					
					for(Entry<String, String> entry : parameterVO.getValueSource().entrySet()){
						
						Comboitem comboitem = new Comboitem(entry.getKey());
						comboitem.setValue(entry.getKey());
//						selectedLBData.add(entry.getValue());
						combobox.appendChild(comboitem);
						if(defValue1!=null && entry.getValue().equals(defValue1)){
							combobox.setSelectedItem(comboitem);
						}
					}
//					combobox.setRawValue();
					combobox.setConstraint("no empty : Please Select the Mode.");
					combobox.setReadonly(true);
				}
				return combobox;
			}else if(parameterVO.getCustomfieldtypeid().equals(SystemParameterConstants.LIST_BOX)){
				List<String[]> selectedLBdata = new ArrayList<String[]>();
				if(parameterVO.getValueSource() != null && !parameterVO.getValueSource().isEmpty()){
					
					for(Entry<String, String> entry : parameterVO.getValueSource().entrySet()){
						String[] data = new String[2];
						data[0] = entry.getKey();
						data[1] = entry.getValue();
						selectedLBdata.add(data);
//						selectedLBData.add(entry.getValue());
						
					}
					ListModelList<String[]> model = new ListModelList<String[]>(selectedLBdata);
					listbox.setModel(model);
					
					model.setMultiple(true);
					
					listbox.setItemRenderer(new ListitemRenderer<String[]>() {

						@Override
						public void render(Listitem item, String[] data, int index)
								throws Exception {
							item.appendChild(new Listcell((index+1)+""));
							item.appendChild(new Listcell(data[0]));
							item.appendChild(new Listcell(data[1]));
							item.setValue(data);
						}
					});
//					listbox.setCheckmark(true);
//					listbox.setMultiple(true);
//					combobox.setRawValue();
				}
				return listbox;
			} else if(parameterVO.getCustomfieldtypeid().equals(SystemParameterConstants.DATE_BOX)){
				Datebox dateBox = new Datebox();
				dateBox.setReadonly(true);
				dateBox.setFormat(getDateFormat());
				if(defValue1!=null){
					Long dateVal = Long.valueOf(defValue1);
					dateBox.setValue(new Date(dateVal));
				}
				dateBox.setConstraint("no empty: Please enter date");
				return dateBox;
			}else{
				return null;
			}
	}
	
	
	
	
	public static  String getComponentValue(Component component){
		String strValue = null;
		if(component !=null){
			if(component instanceof Textbox){
				strValue = ((Textbox)component).getValue();
			}else if(component instanceof Datebox){
				Datebox datebox = (Datebox) component;
				long dateinlong = datebox.getValue().getTime();
				strValue = String.valueOf(dateinlong);
			}else if(component instanceof Combobox){
				Combobox combo =  (Combobox) component;
				strValue = combo.getSelectedItem().getLabel();
			}
		}
		return strValue;
	}
	
	
	
	public static String getDateFormat() {
		return getSystemParamterValue("DEFAULT_DATE");
	}
	
	public static String getSystemParamterValue(String aliasName){	
		HttpSession session = (HttpSession)Sessions.getCurrent(true).getNativeSession();
    	Map<String, String> sysMap = (Map<String, String>)session.getServletContext().getAttribute(LoadCacheDataListener.SYSPARAM);
    	return sysMap.get(aliasName);
	}

}
