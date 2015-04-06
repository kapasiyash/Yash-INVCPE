package com.elitecore.cpe.web.system.userwarehousemapping;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.delegates.system.internal.SystemInternalBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class UserWareHouseMappingComposer extends BaseModuleComposer  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MODULE = "USER-WAREHOUSE-MAPPING";
	
	private Combobox comboUser;
	  private DualListboxComposer dualLBox;
	

	  
	public void afterCompose(Window comp) throws ModuleInitializationException {
		 final SystemInternalBD systemInternalBD = new  SystemInternalBD(getBDSessionContext());
		
		
		 
		try {
			List<ComboBoxData> comboBoxDatas = systemInternalBD.getAllUsers();
			if(comboBoxDatas!=null && !comboBoxDatas.isEmpty()) {
				comboUser.setModel(new ListModelList<ComboBoxData>(comboBoxDatas));
				comboUser.setItemRenderer(new ComboBoxItemDataRenderer());
			}
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
		
		WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
		final List<ComboData> wareHouseDatas = wareHouseBD.getAllWarehouseData();
		if(wareHouseDatas!=null && !wareHouseDatas.isEmpty()) {
			Collections.sort(wareHouseDatas,new WarehouseComparator());
			dualLBox.setModel(wareHouseDatas);
		}

		comboUser.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				if(comboUser.getSelectedItem()!=null) {
					ComboBoxData comboBoxData = comboUser.getSelectedItem().getValue();
					String userId = comboBoxData.getId();
					List<ComboData> warehouseVOs = systemInternalBD.findUserWareHouseMapping(userId);
					if(warehouseVOs!=null && !warehouseVOs.isEmpty()) {
						Collections.sort(wareHouseDatas,new WarehouseComparator());
						dualLBox.setModel(wareHouseDatas); 
						dualLBox.chooseOne(new HashSet<ComboData>(warehouseVOs));
						 
					} else {
						if(wareHouseDatas!=null) {
							Collections.sort(wareHouseDatas,new WarehouseComparator());
						}
						dualLBox.setModel(wareHouseDatas);
					}
				}
				
			}
		});
		
	}

	
	public void onClick$btnUpdate(Event event) {
		
		if(comboUser.getSelectedItem()!=null) {
			ComboBoxData comboBoxData = comboUser.getSelectedItem().getValue();
			String userId = comboBoxData.getId();
			String name=comboBoxData.getName();
			if(name!=null){
				name=name.substring(name.indexOf("(")+1, name.indexOf(")"));
			}
			System.out.println("Name:::::::"+name);
			List<ComboData> selectedData = dualLBox.getChosenDataList();
			
			SystemInternalBD systemInternalBD = new  SystemInternalBD(getBDSessionContext());
			try {
				systemInternalBD.updateUserWarehouseMapping(name,userId,selectedData);
				MessageUtility.successInformation("Success", "User Warehouse Mapping Updated Successfully");
				
			} catch (UpdateBLException e) {
				e.printStackTrace();
				MessageUtility.failureInformation("ERROR", "Reason : "+e.getMessage());
			}
			
		}
		
	}
	
	
	private static class WarehouseComparator implements Comparator<ComboData>,Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(ComboData o1, ComboData o2) {
			
			return o1.getName().compareTo(o2.getName());
		}
		
	}
	
	
}
