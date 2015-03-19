package com.elitecore.cpe.web.composer.inventory.transfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryBatchViewVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;
import com.elitecore.cpe.web.utils.MessageUtility;

public class TransferInventoryBatch  extends BaseSearchComposer{

	
//	private Window transferInventoryBatch;
	private Tabbox transferInventoryBatchTabbox;
	
	private Combobox cmbWarehouse,cmbTransferwarehouse,cmbResourceType,cmbResourceSubType;
	
	private Textbox txtBatchNo;
	private Listbox searchResultGrid;
	
	private ListModelList<InventoryBatchViewVO> lstModel;
	private String resourceType,resourceSubType;
	private HashMap<String, Integer> reservedQtyForTransfer;
	
	private  final static String MODULE = "TRANSFER_INVENTORY_BATCH";
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		System.out.println("in TransferInventoryBatch composer afterCompose");
	//	this.transferInventoryBatch = comp;
		init();
	}

	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		
	}
	
	private void init(){
		// get all warehouseData
		
		IBDSessionContext sessionContext = getBDSessionContext();
		WareHouseBD  wareHouseBD = new WareHouseBD(sessionContext);
		List<ComboData> comboBoxDatas = wareHouseBD.getAllWarehouseData();
		
		cmbWarehouse.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbWarehouse.setItemRenderer(new ComboItemDataRenderer());
		
		cmbTransferwarehouse.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbTransferwarehouse.setItemRenderer(new ComboItemDataRenderer());
		
		ItemBD itemBD = new ItemBD(sessionContext);
		List<ComboData> resourceTypeData = itemBD.getAllResourceTypeData();
		
		cmbResourceType.setModel(new ListModelList<ComboData>(resourceTypeData));
		cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
	}
	
	public void onChange$cmbResourceType(){
		
		 if(cmbResourceType.getSelectedItem() != null){
			 
			 resetComponents(cmbResourceSubType, cmbResourceSubType);
			 ComboData selData = cmbResourceType.getSelectedItem().getValue();
			 
			 ItemBD itemBD = new ItemBD(getBDSessionContext());
			 List<ComboData> resourceSubTypeData = itemBD.getAllResourceSubTypeDataByResourceTypeId(selData.getId());
			 cmbResourceSubType.setModel(new ListModelList<ComboData>(resourceSubTypeData));
			 cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
		 }
		
	}
	
	public void onClick$btnSearch(Event event){
		
		Logger.logDebug(MODULE, "in onClick$btnSearch function");
		
			
			SearchInventoryVO searchInventoryVO = new SearchInventoryVO();
			
			if(cmbWarehouse.getSelectedItem() == null){
				MessageUtility.failureInformation("Error", "Please Select Current Warehouse");
				return;
			}
			if(cmbResourceType.getSelectedItem() == null){
				MessageUtility.failureInformation("Error", "Please Select ResourceType");
				return;
			}
			else if(cmbResourceSubType.getSelectedItem() == null){
				MessageUtility.failureInformation("Error", "Please Select ResourceSubType");
				return;
			}
			
			ComboData selResourceTypeData = cmbResourceType.getSelectedItem().getValue();
			ComboData selResourceSubTypeData = cmbResourceSubType.getSelectedItem().getValue();
			ComboData currentWarehouseData = cmbWarehouse.getSelectedItem().getValue();
			
			resourceType = cmbResourceType.getValue();
			resourceSubType = cmbResourceSubType.getValue();
			
			if(txtBatchNo.getValue() != null && !txtBatchNo.getValue().equals("")){
				searchInventoryVO.setBatchId(txtBatchNo.getValue());
			}
			
			searchInventoryVO.setInventoryStatusId(2L);
			
			
			searchInventoryVO.setResourceTypeId(selResourceTypeData.getId());
			searchInventoryVO.setResourceSubtypeId(selResourceSubTypeData.getId());
			searchInventoryVO.setTransferInventoryStatus("");
			searchInventoryVO.setWareHouseId(currentWarehouseData.getId());
			
			InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
			Logger.logDebug(MODULE, "calling BD");
			List<InventoryBatchViewVO> listBatchData = inventoryManagementBD.searchInventoryBatchData(searchInventoryVO);
			
			if(listBatchData != null){
				Logger.logDebug(MODULE, "got response : "+listBatchData.size());
				
				reservedQtyForTransfer = new HashMap<String, Integer>();
				searchResultGrid.setVisible(true);
				ListModelList<InventoryBatchViewVO> lstModel  = new ListModelList<InventoryBatchViewVO>(listBatchData);
				lstModel.setMultiple(true);
				this.lstModel = lstModel;
				searchResultGrid.setModel(lstModel);
				searchResultGrid.setItemRenderer(new SearchListInventoryBatchRenderer());
				
			
				Logger.logDebug(MODULE, "called renderer");
			}
	}
	
	public void onClick$btnTransfer(Event event){
		
		Logger.logDebug(MODULE, "in onClick$btnTransfer function");
		
		if(cmbTransferwarehouse.getSelectedItem() == null){
			MessageUtility.failureInformation("Error", "Please Select New Warehouse");
			return;
		}else if(cmbTransferwarehouse.getSelectedItem().getValue().equals(cmbWarehouse.getSelectedItem().getValue())){
			MessageUtility.failureInformation("Error", "Cannot Transfer Inventrory to Same Warehouse");
			return;
		}
		
		ComboData currentWarehouseData = cmbWarehouse.getSelectedItem().getValue();
		ComboData newWarehouseData = cmbTransferwarehouse.getSelectedItem().getValue();
		ComboData selResourceTypeData = cmbResourceType.getSelectedItem().getValue();
		ComboData selResourceSubTypeData = cmbResourceSubType.getSelectedItem().getValue();
		
		if(lstModel != null){
			Set<InventoryBatchViewVO> selectedSet = ((ListModelList<InventoryBatchViewVO>)lstModel).getSelection();
			if(selectedSet == null || selectedSet.isEmpty() || selectedSet.size() == 0){
				MessageUtility.failureInformation("ERROR", "Please Select Some Records from list");
				return;
			}else{
				Logger.logDebug(MODULE, "selected list :"+selectedSet.size());
				Set<Listitem> selectedItems = searchResultGrid.getSelectedItems();
				
				Map<String,Integer> finalBatchTransferMap = new HashMap<String, Integer>();
				for(Listitem selItem : selectedItems)
				{
					Listcell cell1 = (Listcell)selItem.getChildren().get(2);
					Listcell cell2 = (Listcell)selItem.getChildren().get(6);
					Listcell cell3 = (Listcell)selItem.getChildren().get(5);
					Intbox intBox = (Intbox)cell2.getChildren().get(0);
					if(intBox.getValue() != null && intBox.getValue() > 0){
						cell1.getLabel(); 
						if(intBox.getValue() > Integer.parseInt(cell3.getLabel())){
							MessageUtility.failureInformation("ERROR", "Transfer Qty : "+intBox.getValue()+" is more than avaialble Qty : "+cell3.getLabel()+" For Batch :"+cell1.getLabel());
							return;
						}else{
							finalBatchTransferMap.put(cell1.getLabel(),intBox.getValue() );
						}
					}else{
						MessageUtility.failureInformation("ERROR","Please Enter Proper Transfer Qty");
					}
				}
				
				if(!finalBatchTransferMap.isEmpty()){
					Logger.logDebug(MODULE, "on call BD :"+finalBatchTransferMap);
					
					InventoryManagementBD managementBD = new InventoryManagementBD(getBDSessionContext());
					SearchInventoryVO searchInventoryVO = new SearchInventoryVO(); 
					searchInventoryVO.setResourceTypeId(selResourceTypeData.getId());
					searchInventoryVO.setResourceSubtypeId(selResourceSubTypeData.getId());
					
					try {
						managementBD.transferInventoryBatch(finalBatchTransferMap, searchInventoryVO, currentWarehouseData.getId(), newWarehouseData.getId());
						
						searchResultGrid.setVisible(false);
						resetComponents(cmbWarehouse,cmbWarehouse,txtBatchNo,cmbResourceType,cmbResourceSubType,cmbTransferwarehouse);
						cmbResourceSubType.setModel(new ListModelList<ComboData>());
						cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
					} catch (CreateBLException e) {
						MessageUtility.failureInformation("ERROR",e.getMessage());
					}
				}
			}
			
		}else{
			MessageUtility.failureInformation("ERROR", "Please Search Batch Data");
		}
			
		
	}
	
	private class SearchListInventoryBatchRenderer implements ListitemRenderer<InventoryBatchViewVO> {
			private EventListener<Event> viewItemListener,viewHoverListner,viewOutListener;

			public SearchListInventoryBatchRenderer() {
	
				viewItemListener = new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
					//	Image img = (Image) event.getTarget();
						clickView(event);
					}
			
				};
				
				viewHoverListner = new EventListener<Event>() {
					
					@Override
					public void onEvent(Event event) throws Exception {
						Image img = (Image) event.getTarget();
						img.setSrc(BaseConstants.EDIT_ITEM_HOVER_IMAGE);
					}
				};
				
				viewOutListener = new EventListener<Event>() {
					
					@Override
					public void onEvent(Event event) throws Exception {
						Image img = (Image) event.getTarget();
						img.setSrc(BaseConstants.EDIT_ITEM_IMAGE);
					}
				};
			}
			
			@Override
			public void render(Listitem item, InventoryBatchViewVO data, int index)
					throws Exception {
				
				int no = index + 1;
				item.appendChild((new Listcell()));
				item.appendChild(new Listcell(String.valueOf(no)));
				item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getBatchNumber())));
				item.appendChild(new Listcell(TransferInventoryBatch.this.resourceType));
				item.appendChild(new Listcell(TransferInventoryBatch.this.resourceSubType));
				item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getValidcount().toString())));
				
				Listcell cell = new Listcell();
				Intbox intBox = new Intbox();
				if(TransferInventoryBatch.this.reservedQtyForTransfer.keySet().contains(data.getBatchNumber())){
					intBox.setValue(TransferInventoryBatch.this.reservedQtyForTransfer.get(data.getBatchNumber()));
				}
				cell.appendChild(intBox);
				
				item.appendChild(cell);
			
				Listcell operationCell = new Listcell();
				Image view = new Image(BaseConstants.EDIT_ITEM_IMAGE);
				view.setId(data.getBatchNumber());
				
				view.addEventListener(Events.ON_MOUSE_OVER, viewHoverListner);
				view.addEventListener(Events.ON_MOUSE_OUT, viewOutListener);
				view.addEventListener(Events.ON_CLICK, viewItemListener);
				
				operationCell.appendChild(view);
				item.appendChild(operationCell);
				item.setValue(data);
				
				
			}

	}
	

	
	public void clickView(Event event){
		Logger.logTrace(MODULE, "in clickView function...");
		
		Logger.logTrace(MODULE, "getData :"+event.getTarget().getId());
		
		ComboData selResourceTypeData = cmbResourceType.getSelectedItem().getValue();
		ComboData selResourceSubTypeData = cmbResourceSubType.getSelectedItem().getValue();
		
		Map<String,Object> argMap = new HashMap<String, Object>();
		
		argMap.put(SEARCH_COMPOSER_REF, this);
		argMap.put("TRANSFER_BATCH", true);
		argMap.put("RESOURCE_TYPEID", selResourceTypeData.getId());
		argMap.put("RESOURCE_SUBTYPEID", selResourceSubTypeData.getId());
		
		Logger.logDebug(MODULE, "Batch no :"+event.getTarget().getId());
		

	     argMap.put("BATCHNO", event.getTarget().getId());
		addViewTab(event.getTarget().getId(),event.getTarget().getId(), transferInventoryBatchTabbox, Pages.VIEW_BATCHDETAIL_EVENT,argMap);
	     
	     /*Window window = (Window)Executions.createComponents(Pages.VIEW_BATCHDETAIL_EVENT, this.transferInventoryBatch, argMap);
	     window.setWidth("800px");
	     window.doModal();*/
		
	}
	
	
	public void onPaging$searchResultGrid(Event event){
		Logger.logTrace("INVENTORY", "onPaging function called...");
		
		ForwardEvent pe = (ForwardEvent) event;
		
		Logger.logTrace(MODULE, " : "+pe.getOrigin().getTarget());
		
		List<Listitem> selectedItems = searchResultGrid.getItems();
		for(Listitem selItem : selectedItems)
		{
			Listcell cell1 = (Listcell)selItem.getChildren().get(2);
			Listcell cell2 = (Listcell)selItem.getChildren().get(6);
			Intbox intBox = (Intbox)cell2.getChildren().get(0);
			if(selItem.isSelected() && intBox.getValue() != null && intBox.getValue() > 0){
				reservedQtyForTransfer.put(cell1.getLabel(), intBox.getValue());
			}else{
				reservedQtyForTransfer.remove(cell1.getLabel());
			}
//			Logger.logTrace(MODULE, cell1.getLabel()+" : "+intBox.getValue());
		}
		
	}
}
