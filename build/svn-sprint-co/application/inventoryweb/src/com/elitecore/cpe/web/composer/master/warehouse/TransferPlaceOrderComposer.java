package com.elitecore.cpe.web.composer.master.warehouse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.notification.NotificationConstants;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.CheckInventoryVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.InvalidDataException;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class TransferPlaceOrderComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Window transferPlaceOrderWin;
//	private Grid viewwsauditgrid;
	private Label lblPlaceOrderNoValue,lblPlaceOrderStatus,lblQuantity,lblAcceptQuantity,lblQuantity1,lblAcceptQuantity1;
	private Textbox txtRemark;
//	private SearchWsAuditVO auditVO;
//	lblTransferNo
	private Textbox txtTransferNo;
	private Vlayout page1,page2;
	
	
	private Combobox cmbWarehouse,cmbTransferwarehouse;
	private Listbox searchResultGrid;
	private Textbox txtAddInventoryId,txtRemoveInventoryId;
//	Map<ComboData,List<ComboData>> resultMap=null;
//	private Grid transferInventorytargetgrid;
//	private Hbox hboxTransfer;
	private ListModelList<CheckInventoryVO> modelList;
	
	public static final String SEARCH_COMPOSER_REF="_searchComposerRef";
	private static final String MODULE = "TRANSFER_PLACE_ORDER";

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		init();

		if(arg.containsKey("FROMWHNAME")) {
			String fromWarehouse = (String) arg.get("FROMWHNAME");
			cmbTransferwarehouse.setValue(fromWarehouse);
			
			
		}
		if(arg.containsKey("TOWHNAME")) {
			String toWarehouse = (String) arg.get("TOWHNAME");
			cmbWarehouse.setValue(toWarehouse);
			
		}
		
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
		
		/*if (arg.containsKey("RESOURCETYPE")) {
			String resourceType = (String) arg.get("RESOURCETYPE");
			cmbResourceType.setValue(resourceType);
			
			List<ComboData> comboBoxDatas = null;
			if (cmbResourceType.getSelectedItem() != null) {

			} else {

				if (cmbResourceType.getValue() != null && !cmbResourceType.getValue().isEmpty()) {
					
					for(Entry<ComboData, List<ComboData>> entry : resultMap.entrySet()) {
						if (cmbResourceType.getValue().equals(entry.getKey().getName())) {
							comboBoxDatas = entry.getValue();
							cmbResourceSubType.setModel(new ListModelList<ComboData>(comboBoxDatas));
							cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
							break;
						}
					}
					
				}
				
			}

		}
		if(arg.containsKey("RESOURCESUBTYPE")) {
			String resourceSubType = (String) arg.get("RESOURCESUBTYPE");
			if(resourceSubType!=null && resourceSubType.length()!=0 && !resourceSubType.equals("null")) {
				cmbResourceSubType.setValue(resourceSubType);
				cmbResourceSubType.setDisabled(true);
			}
			
		}*/
		
		
		
		Logger.logDebug(MODULE, "after composer ");
		Logger.logDebug(MODULE, "order No : "+arg.get("ORDERNO"));
		PlaceOrderVO placeOrderVO=new PlaceOrderVO();
		placeOrderVO.setOrderNo(arg.get("ORDERNO").toString());
		InventoryManagementBD inventoryMgtBD = new InventoryManagementBD(getBDSessionContext());
		placeOrderVO=inventoryMgtBD.searchPlaceOrderDataByOrderNo(placeOrderVO);
		
		populateData(placeOrderVO);
		final Long acceptedQuantity = placeOrderVO.getAcceptquantity();
		
		txtAddInventoryId.addEventListener(Events.ON_OK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				if(txtAddInventoryId.getValue()!=null && !txtAddInventoryId.getValue().isEmpty()) {
					
					if(cmbWarehouse.getSelectedItem()!=null) {
						ComboData comboData = cmbWarehouse.getSelectedItem().getValue();
						Long warehouseId = comboData.getId();
						CheckInventoryVO checkInventoryVO = itemBD.checkInventoryInWarehouse(txtAddInventoryId.getValue(),warehouseId,inventoryStatus);
						if(checkInventoryVO.getResponseCode()==0) {
							
							if(modelList.size()==acceptedQuantity) {
								MessageUtility.failureInformation("Error", "You can not add Inventories more than Accept Quantity.");
								return;
							}
							
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
						while(iterator.hasNext()) {
							if(iterator.next().getInventoryNumber().equals(txtRemoveInventoryId.getValue())) {
								iterator.remove();
							}
							
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
			item.appendChild(new Listcell(data.getResourceType()));
			item.appendChild(new Listcell(data.getResourceSubtype()));
		}
		
	}
	
	private void populateData(PlaceOrderVO data){
		lblPlaceOrderNoValue.setValue(data.getOrderNo());
		lblPlaceOrderStatus.setValue(data.getStatus());
		lblQuantity.setValue(data.getQuantity().toString());
		lblAcceptQuantity.setValue(data.getAcceptquantity().toString());
		lblQuantity1.setValue(data.getQuantity().toString());
		lblAcceptQuantity1.setValue(data.getAcceptquantity().toString());
	}
	
	
	public void onClick$btnNext1(Event event) {
		page1.setVisible(false);
		page2.setVisible(true);
	}
	
	public void onClick$btnBack2(Event event) {
		page1.setVisible(true);
		page2.setVisible(false);
	}
	
	public void onClick$btnCancel(Event event){
//		resetComponents(txtInventoryId,txtInventoryId,cmbResourceSubType,cmbTransferwarehouse,cmbAttribute,txtAttributeValue);
		
	}
	
	
	public void onClick$btnSave(Event event){
		
		Logger.logDebug(MODULE, "onSubmit cllick");
		
			
		try {
			
			InventoryManagementBD inventoryMgtBD = new InventoryManagementBD(getBDSessionContext());
			PlaceOrderVO placeOrderVO=new PlaceOrderVO();
			
			if(txtTransferNo.getValue().trim()!=null){
				if(inventoryMgtBD.searchTransferOrder(txtTransferNo.getValue().trim())){
					placeOrderVO.setTransferOrderNo(txtTransferNo.getValue().trim());
				}else{
					MessageUtility.failureInformation("ERROR", "Please enter valid Transfer Order Number");
					return;
				}
			}
			placeOrderVO.setTransferRemark(txtRemark.getValue());
			placeOrderVO.setOrderNo(lblPlaceOrderNoValue.getValue());
			inventoryMgtBD.transferPlaceOrder(placeOrderVO);
			
			MessageUtility.successInformation("Success", "Action Performed Successfully");
			transferPlaceOrderWin.detach();
			if(arg.get("placeOrderSummaryObj") != null){
				SearchPlaceOrderDetailComposer obj = (SearchPlaceOrderDetailComposer)arg.get("placeOrderSummaryObj");
				obj.onClick$btnSearch(null);
			}
		} catch (Exception e) {
			MessageUtility.failureInformation("Error",e.getMessage());
		}
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
						
						String reason = txtRemark.getValue();
						if(reason==null || reason.isEmpty()) {
							MessageUtility.failureInformation("ERROR", "Please enter Remarks");
							return;
						}
						String orderNo=	inventoryManagementBD.transferInventory(inventoryDetailVOs,fromWarehouseId, toWarehouseId,NotificationConstants.TRANSFER_ORDER_PLACEORDER);
						txtTransferNo.setValue(orderNo);
						onClick$btnSave(null);
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
	
	
	
	
	
	private void init(){
		// get all warehouseData
		
		
		modelList = new ListModelList<CheckInventoryVO>();
		
		// get all warehouseData
		IBDSessionContext sessionContext = getBDSessionContext();
		WareHouseBD  wareHouseBD = new WareHouseBD(sessionContext);
		List<ComboData> comboBoxDatas = wareHouseBD.getAllWarehouseData();
		cmbWarehouse.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbWarehouse.setItemRenderer(new ComboItemDataRenderer());
		
		cmbTransferwarehouse.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbTransferwarehouse.setItemRenderer(new ComboItemDataRenderer());
		
	}
	
	
}
