package com.elitecore.cpe.web.composer.inventory;


import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.inventorymgt.InventoryStatusConstants;
import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.BulkChangeInventoryStatusVO;
import com.elitecore.cpe.web.base.ui.core.BaseComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class BulkStatusChangeInventoryComposer extends BaseComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private Window inventorybulkstatuschange;
	private Combobox cmbResourceName,cmbstatusfrom,cmbstatusto;
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {

		System.out.println("in inventorybulkstatuschange  composer afterCompose");
//		this.inventorybulkstatuschange = comp;
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		List<ComboBoxData>  comboDatas =  itemBD.getAllResourceData();

		if(comboDatas!=null && !comboDatas.isEmpty()) {
			cmbResourceName.setModel(new ListModelList<ComboBoxData>(comboDatas));
			cmbResourceName.setItemRenderer(new ComboBoxItemDataRenderer());
		}
		InventoryManagementBD inventoryBD = new InventoryManagementBD(getBDSessionContext());
		List<ComboData> inventoryStatuscomboBoxDatas = inventoryBD.getAllInventoryStatusData();
		List<ComboData> inventoryStatuscomboBox = new ArrayList<ComboData>();
		for(ComboData comboData:inventoryStatuscomboBoxDatas){
			if(!((comboData.getName()).equalsIgnoreCase(InventoryStatusConstants.SCRAPPED_STATUS)|| (comboData.getName()).equalsIgnoreCase(InventoryStatusConstants.VOID_STATUS) || (comboData.getName()).equalsIgnoreCase(InventoryStatusConstants.RETIRED_STATUS))){
				inventoryStatuscomboBox.add(comboData);
			}
		}
		if(inventoryStatuscomboBox!=null && !inventoryStatuscomboBox.isEmpty()){
			cmbstatusfrom.setModel(new ListModelList<ComboData>(inventoryStatuscomboBox));
			cmbstatusfrom.setItemRenderer(new ComboItemDataRenderer());
	
			cmbstatusto.setModel(new ListModelList<ComboData>(inventoryStatuscomboBox));
			cmbstatusto.setItemRenderer(new ComboItemDataRenderer());
		}
		cmbstatusfrom.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if(cmbstatusfrom.getSelectedItem()!=null){
					ComboData 	 selectedData = cmbstatusfrom.getSelectedItem().getValue();
					ItemBD itemBD = new ItemBD(getBDSessionContext());
					List<ComboData> inventoryStatuscomboBoxDatas =	itemBD.getAllInventoryStatusDataByStatusId(selectedData.getId());
					cmbstatusto.setModel(new ListModelList<ComboData>(inventoryStatuscomboBoxDatas));
					cmbstatusto.setItemRenderer(new ComboItemDataRenderer());
				}
			}
		});

		
	}
	
	public void onClick$btnReset(Event event){
		resetComponents(cmbResourceName,cmbResourceName,cmbstatusfrom,cmbstatusto);

	}
	public void onClick$btnSearch(Event event){
		try {
		ComboData 	 selectedDatafrom = cmbstatusfrom.getSelectedItem().getValue();
		ComboData 	 selectedData = cmbstatusto.getSelectedItem().getValue();
		ComboBoxData 	 selectedDataresource = cmbResourceName.getSelectedItem().getValue();

		Long statusToId= selectedData.getId();
		Long statusFromId=	selectedDatafrom.getId();
		String resourceId=selectedDataresource.getId();
		String statusNewName=selectedData.getName();
		BulkChangeInventoryStatusVO statusVO=new BulkChangeInventoryStatusVO();
		statusVO.setResourceNumber(resourceId);
		statusVO.setStatusFromId(statusFromId);
		statusVO.setStatusToId(statusToId);
		statusVO.setStatusNewName(statusNewName);
		statusVO.setRemarks("Bulk Status Change For Inventory");
		InventoryManagementBD itemBD=new InventoryManagementBD(getBDSessionContext()) ;
		int updateCount = itemBD.updateInventoryStatusInBulk(statusVO);
		MessageUtility.successInformation("Inventory Status Update Information", updateCount+" Inventory status updated");
		} catch (UpdateBLException e) {
			MessageUtility.failureInformation("ERROR", e.getMessage());
			e.printStackTrace();
		}
	}
}