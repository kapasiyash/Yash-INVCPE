package com.elitecore.cpe.web.composer.inventory.transfer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.notification.NotificationConstants;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.CheckInventoryVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.core.exception.InvalidDataException;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class TransferInventoryComposer extends BaseSearchComposer{
	private static final long serialVersionUID = 1L;
	
	private Combobox cmbWarehouse,cmbTransferwarehouse;
	private Listbox searchResultGrid;
	private Textbox txtAddInventoryId,txtRemoveInventoryId;
	
	private final static String MODULE = "TRANSFER INVENTORY";
//	Map<ComboData,List<ComboData>> resultMap=null;
	private ListModelList<CheckInventoryVO> modelList;
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		Logger.logInfo(MODULE,"in TransferInventoryComposer composer afterComposer");
	
		init();
		
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		
		final ItemBD itemBD = new ItemBD(getBDSessionContext());
		
		final List<Integer> inventoryStatus = new ArrayList<Integer>();
		
		try {
			List<ComboData> comboDatas =  inventoryManagementBD.searchTransferrableStatus();
			if(comboDatas!=null && !comboDatas.isEmpty()) {
				for(ComboData comboData : comboDatas) {
					inventoryStatus.add(comboData.getId().intValue());
				}
			}
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
		
		cmbWarehouse.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				modelList = new ListModelList<CheckInventoryVO>();
				searchResultGrid.setModel(modelList);
				searchResultGrid.setItemRenderer(new InventoryTransferRenderer());
				resetComponents(txtAddInventoryId, txtAddInventoryId,txtRemoveInventoryId,cmbTransferwarehouse);
			}
		});
		
		
		txtAddInventoryId.addEventListener(Events.ON_OK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				if(txtAddInventoryId.getValue()!=null && !txtAddInventoryId.getValue().isEmpty()) {
					
					if(cmbWarehouse.getSelectedItem()!=null) {
						ComboData comboData = cmbWarehouse.getSelectedItem().getValue();
						Long warehouseId = comboData.getId();
						CheckInventoryVO checkInventoryVO = itemBD.checkInventoryInWarehouse(txtAddInventoryId.getValue(),warehouseId,inventoryStatus);
						if(checkInventoryVO.getResponseCode()==0) {
							
							boolean isPresent = false;
							for(CheckInventoryVO inventoryVO : modelList) {
								if(inventoryVO.getInventoryNumber().equals(txtAddInventoryId.getValue())) {
									isPresent = true;
									MessageUtility.failureInformation("Error", "Inventory Already Added");
								}
							}
							
							if(!isPresent) {
								modelList.add(checkInventoryVO);
								searchResultGrid.setModel(modelList);
								searchResultGrid.setItemRenderer(new InventoryTransferRenderer());
								resetComponents(txtAddInventoryId, txtAddInventoryId);
							}
							
						} else {
							MessageUtility.failureInformation("Error", checkInventoryVO.getResponseMessage());
							resetComponents(txtAddInventoryId, txtAddInventoryId,txtRemoveInventoryId);
						}
					}
					
				} else {
					MessageUtility.failureInformation("Error", "Inventoty No can not be null or empty");
				}
				
			}
		});
		
		
		
		txtRemoveInventoryId.addEventListener(Events.ON_OK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				if(txtRemoveInventoryId.getValue()!=null && !txtRemoveInventoryId.getValue().isEmpty()) {
					
					if(modelList!=null && !modelList.isEmpty()) {
						Iterator<CheckInventoryVO> iterator =  modelList.iterator();
						boolean isPresent = false;
						while(iterator.hasNext()) {
							if(iterator.next().getInventoryNumber().equals(txtRemoveInventoryId.getValue())) {
								iterator.remove();
								isPresent = true;
								resetComponents(txtRemoveInventoryId, txtRemoveInventoryId);
							}
							
						}
						
						if(!isPresent) {
							MessageUtility.failureInformation("Error", "No Inventory Added with this Inventory No");
							resetComponents(txtRemoveInventoryId, txtRemoveInventoryId);
						}
						
						searchResultGrid.setModel(modelList);
						searchResultGrid.setItemRenderer(new InventoryTransferRenderer());
					}
					
					
				} else {
					MessageUtility.failureInformation("Error", "Inventoty No can not be null or empty");
				}
				
			}
		});
		
	}
	
	
	public void onClick$btnTransfer(Event event) {
		
		try {
			
			Long fromWarehouseId = null,toWarehouseId = null;
			
			if(cmbWarehouse.getSelectedItem()!=null) {
				ComboData comboData = cmbWarehouse.getSelectedItem().getValue();
				fromWarehouseId = comboData.getId();
			} else {
				throw new InvalidDataException("Please select fromwarehouse");
			}
			
			if(cmbTransferwarehouse.getSelectedItem()!=null) {
				ComboData comboData = cmbTransferwarehouse.getSelectedItem().getValue();
				 toWarehouseId = comboData.getId();
			} else {
				throw new InvalidDataException("Please select towarehouse");
			}
			
			List<InventoryDetailVO> inventoryDetailVOs = new ArrayList<InventoryDetailVO>();
			
			if(modelList!=null && !modelList.isEmpty()) {
				for(CheckInventoryVO checkInventoryVO : modelList) {
					InventoryDetailVO detailVO = new InventoryDetailVO();
					detailVO.setBatchId(checkInventoryVO.getBatchNumber());
					detailVO.setInventoryId(checkInventoryVO.getInventoryNumber());
					detailVO.setStatus(checkInventoryVO.getInventoryStatus());
					detailVO.setWareHouseName(checkInventoryVO.getWarehouseName());
					detailVO.setResourceType(checkInventoryVO.getResourceType());
					detailVO.setWarehouseId(fromWarehouseId);
					detailVO.setResourceSubType(checkInventoryVO.getResourceSubtype());
					inventoryDetailVOs.add(detailVO);
				}
			}
			
			try {	
				InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
				if(!(fromWarehouseId.equals(toWarehouseId))){
					if(inventoryDetailVOs!= null && !inventoryDetailVOs.isEmpty()){
					String orderNo=	inventoryManagementBD.transferInventory(inventoryDetailVOs,fromWarehouseId, toWarehouseId,NotificationConstants.TRANSFER_ORDER);
						MessageUtility.successInformation("Success", "Transfer Inventory succussfully done \n Order Number : "+orderNo );
						modelList = new ListModelList<CheckInventoryVO>();
						searchResultGrid.setModel(modelList);
						searchResultGrid.setItemRenderer(new InventoryTransferRenderer());
						resetComponents(txtAddInventoryId, txtAddInventoryId,txtRemoveInventoryId,cmbTransferwarehouse,cmbWarehouse);
						
					}else{
						MessageUtility.successInformation("Transfer Inventory", "Please add Inventories in List" );
						return;
					}
				}else{
					MessageUtility.successInformation("Transfer Inventory", "Cannot Transfer Inventrory to Same Warehouse" );
					return;
				}
			}catch(Exception ex){
				ex.printStackTrace();
				MessageUtility.failureInformation("ERROR",ex.getMessage());
			}
			
			
		} catch(InvalidDataException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("Error", e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			MessageUtility.failureInformation("Error", e.getMessage());
		}
		
	}
	
	
	private static class InventoryTransferRenderer implements ListitemRenderer<CheckInventoryVO>{

		@Override
		public void render(Listitem item, CheckInventoryVO data, int index)
				throws Exception {
			
			item.setValue(data);
			item.appendChild(new Listcell((index+1)+""));
			item.appendChild(new Listcell(data.getBatchNumber()));
			item.appendChild(new Listcell(data.getInventoryNumber()));
			item.appendChild(new Listcell(data.getInventoryStatus()));
			item.appendChild(new Listcell(data.getWarehouseName()));
			item.appendChild(new Listcell(data.getResource()));
			item.appendChild(new Listcell(data.getResourceType()));
			item.appendChild(new Listcell(data.getResourceSubtype()));
		}
		
	}
	

	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		
	}
	
	private void init(){
		
		modelList = new ListModelList<CheckInventoryVO>();
		
		// get all warehouseData
		IBDSessionContext sessionContext = getBDSessionContext();
		WareHouseBD  wareHouseBD = new WareHouseBD(sessionContext);
		List<ComboData> comboBoxDatas = wareHouseBD.getAllWarehouseData();
		
		comboBoxDatas = filterDataByWarehouseMapping(comboBoxDatas);
		
		cmbWarehouse.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbWarehouse.setItemRenderer(new ComboItemDataRenderer());
		
		cmbTransferwarehouse.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbTransferwarehouse.setItemRenderer(new ComboItemDataRenderer());
		
	}
	
	
	
	
	
	
	
	
	
	private List<ComboData> filterDataByWarehouseMapping(
			List<ComboData> comboBoxDatas) {
		
		List<ComboData> list = new ArrayList<ComboData>();
		Set<Long> userWarehouses = getBDSessionContext().getBLSession().getUserWarehouseMappings();
		
		if(comboBoxDatas!=null && !comboBoxDatas.isEmpty()) {
			for(ComboData comboData : comboBoxDatas) {
				if(userWarehouses!=null && userWarehouses.contains(comboData.getId())) {
					list.add(comboData);
				}
			}
		}
		
		return list;
	}


	private void callTransfer(List<InventoryDetailVO> finalInventoryDetailVOs){
		Long newWarehouseId=null,currentwarehouseId = null;
		if(cmbWarehouse.getSelectedItem() != null){
			ComboData selectedData = cmbWarehouse.getSelectedItem().getValue();
			currentwarehouseId = selectedData.getId();
		}else{
			MessageUtility.successInformation("Transfer Inventory", "Plese Select New Warehouse");
			return;
		}
		
		if(cmbTransferwarehouse.getSelectedItem() != null){
			ComboData selectedData = cmbTransferwarehouse.getSelectedItem().getValue();
			 newWarehouseId = selectedData.getId();
		}else{
			MessageUtility.successInformation("Transfer Inventory", "Plese Select New Warehouse");
			return;
		}
		
		try {	
			InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		//	inventoryManagementBD.transferInventory(searchWarehouseInventoryStatusVO);
			if(!(currentwarehouseId.equals(newWarehouseId))){
				if(finalInventoryDetailVOs!= null && !finalInventoryDetailVOs.isEmpty()){
				String orderNo=	inventoryManagementBD.transferInventory(finalInventoryDetailVOs,currentwarehouseId, newWarehouseId,NotificationConstants.TRANSFER_ORDER);
					MessageUtility.successInformation("Success", "Transfer Inventory succussfully done \n Order Number : "+orderNo );
					onClick$btnSearch(null);
				}else{
					MessageUtility.successInformation("Transfer Inventory", "Please Select Inventories From List" );
					return;
				}
			}else{
				MessageUtility.successInformation("Transfer Inventory", "Cannot Transfer Inventrory to Same Warehouse" );
				return;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			MessageUtility.failureInformation("ERROR",ex.getMessage());
		}
	}
	
	
}
