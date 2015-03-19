package com.elitecore.cpe.web.composer.inventory.transfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.inventorymgt.InventoryStatusConstants;
import com.elitecore.cpe.bl.constants.notification.NotificationConstants;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.master.AttributeBD;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.core.BaseComposer.ComboItemDataRenderer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.CommonConstants;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;
import com.elitecore.cpe.web.utils.MessageUtility;

public class TransferInventoryP extends BaseSearchComposer{
	private static final long serialVersionUID = 1L;
	
//	private Window transferInventoryP;
	private Combobox cmbWarehouse,cmbTransferwarehouse,cmbResourceType,cmbResourceSubType,cmbAttribute;
//	private Intbox txtTransferedQty;
	private Listbox searchResultGrid;
//	private Label lbIAvailableQty;
	private Textbox txtInventoryId,txtBatchId,txtAttributeValue;
	
	private final static String MODULE = "TRANSFER INVENTORY";
//	private Long availableQty = 0L;
//	List<AttributeVO> attributeDatas;
	Map<ComboData,List<ComboData>> resultMap=null;
	private ListModelList<InventoryDetailVO> modelList;
	private Tabbox TransferInventoryTabbox;
	private Grid transferInventorytargetgrid;
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		Logger.logInfo(MODULE,"in TransferInventoryP composer afterComposer");
	//	this.transferInventoryP = comp;
		searchResultGrid.setVisible(false);
	
		init();
		
		
		cmbWarehouse.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				if (cmbWarehouse.getSelectedItem() != null) {
					ComboData selectedData = cmbWarehouse.getSelectedItem().getValue();
					WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
					List<ComboData> comboDatas =  wareHouseBD.getAllResourceTypeWithWareHouseId(selectedData.getId());
					cmbResourceType.setSelectedItem(null);
					cmbResourceSubType.setSelectedItem(null);
					if(comboDatas!=null && !comboDatas.isEmpty()) {
						cmbResourceType.setModel(new ListModelList<ComboData>(comboDatas));
						cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
					} else {
						cmbResourceType.setModel(new ListModelList<ComboData>());
						cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
						cmbResourceSubType.setModel(new ListModelList<ComboData>());
						cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
						cmbResourceSubType.setModel(new ListModelList<ComboData>());
						cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
					}
				}
				
			}
		});
		
	}

	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		
	}
	
	private void init(){
		// get all warehouseData
		
		IBDSessionContext sessionContext = getBDSessionContext();
		WareHouseBD  wareHouseBD = new WareHouseBD(sessionContext);
		resultMap = wareHouseBD.getAllResourceTypeWithResource(null);
	//	AttributeBD attributeBD = new AttributeBD(sessionContext);
		List<ComboData> comboBoxDatas = wareHouseBD.getAllWarehouseData();
	//	attributeDatas = attributeBD.getAttributesByRef(CommonConstants.RESOURCE);
		cmbWarehouse.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbWarehouse.setItemRenderer(new ComboItemDataRenderer());
		
		cmbTransferwarehouse.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbTransferwarehouse.setItemRenderer(new ComboItemDataRenderer());
		
		ItemBD itemBD = new ItemBD(sessionContext);
		List<ComboData> comboBoxDatas1 = itemBD.getAllResourceTypeData();
		
		cmbResourceType.setModel(new ListModelList<ComboData>(comboBoxDatas1));
		cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
		AttributeVO data = new AttributeVO();
		AttributeBD  attributeBD = new AttributeBD(getBDSessionContext());
		data.setUsedBy(CommonConstants.RESOURCE);
		List<AttributeVO> listAttributeData = attributeBD.searchAttributeData(data);
		List<ComboData> comboBoxAttributeDatas = new ArrayList<ComboData>();
		
		for(AttributeVO attributeVO:listAttributeData){
			comboBoxAttributeDatas.add(new ComboData(attributeVO.getAttributeId(), attributeVO.getName()));
		}
		cmbAttribute.setModel(new ListModelList<ComboData>(comboBoxAttributeDatas));
		cmbAttribute.setItemRenderer(new ComboItemDataRenderer());
	//	InventoryManagementBD inventoryBD = new InventoryManagementBD(sessionContext);
	//	List<ComboData> inventoryStatuscomboBoxDatas = inventoryBD.getAllInventoryStatusData();
//		cmbInventoryStatus.setModel(new ListModelList<ComboData>(inventoryStatuscomboBoxDatas));
//		cmbInventoryStatus.setItemRenderer(new ComboItemDataRenderer());
		
		////////////////////////////////////////////////////////////////
		
		Listhead lhd = new Listhead();
		Listheader lh1 = new Listheader();
		Listheader lh2 = new Listheader();
		Listheader lh3 = new Listheader();
		Listheader lh4 = new Listheader();
		Listheader lh5 = new Listheader();
		Listheader lh_inventory_status = new Listheader();
		Listheader lh_resource_type = new Listheader();
		Listheader lh_resource_subtype = new Listheader();
		Listheader lh8 = new Listheader();
		lh1.setLabel("Sr.No");
		lh1.setWidth("55px");
		lh4.setLabel("Warehouse Name");
		lh2.setLabel("Batch No");
		lh2.setSort("auto(batchId)");
		lh2.setSort("auto(batchId)");
		lh3.setLabel("Inventory No");
		lh_inventory_status.setLabel("Inventory Status");
		lh_inventory_status.setSort("auto(status)");
		lh1.setWidth("55px");
		lh3.setSort("auto(inventoryId)");
		lh8.setLabel("View");
		lh8.setWidth("55px");
		lh4.setSort("auto(wareHouseName)");
		lh_resource_type.setLabel("Resource Type");
		lh_resource_subtype.setLabel("Resource Subtype");
//		lh2.setWidth("");
//		lh3.setWidth("");
//		lh4.setWidth("22%");
		lh5.setWidth("32px");
	//	lh5.setParent(lhd);
		lh1.setParent(lhd);
		lh2.setParent(lhd);
		lh3.setParent(lhd);
		lh_inventory_status.setParent(lhd);
		lh4.setParent(lhd);
		
		lh_resource_type.setParent(lhd);
		lh_resource_subtype.setParent(lhd);
		lh8.setParent(lhd);
		// lh5.setParent(lhd);

		// /////////////////////////////
//		for (AttributeVO attributeVO : attributeDatas) {
//			Listheader lh = new Listheader();
//			lh.setLabel(attributeVO.getName());
//			lh.setParent(lhd);
//		}
		lhd.setSizable(true);
		lhd.setParent(searchResultGrid);
		searchResultGrid.setMultiple(true);
		searchResultGrid.setCheckmark(true);
		
	}
	public void onClick$btnSearch (Event event) {
		Logger.logInfo(MODULE, "In onClick$btnSearch Event ");
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		SearchInventoryVO data = new SearchInventoryVO();
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		if (cmbWarehouse.getSelectedItem() != null) {
			ComboData selectedData = cmbWarehouse.getSelectedItem().getValue();
			data.setWareHouseId(selectedData.getId());
		}
		if (cmbResourceType.getSelectedItem() != null) {
			ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
			data.setResourceTypeId(selectedData.getId());
		}
		if (cmbResourceSubType.getSelectedItem() != null) {
			ComboData selectedData = cmbResourceSubType.getSelectedItem().getValue();
			data.setResourceSubtypeId(selectedData.getId());
		}
//		if (cmbInventoryStatus.getSelectedItem() != null) {
//			ComboData selectedData = cmbInventoryStatus.getSelectedItem().getValue();
//			data.setInventoryStatusId(selectedData.getId());
//			data.setInventoryStatus(cmbInventoryStatus.getValue());
//		}
//		data.setInventoryStatusId((long) InventoryStatusConstants.AVAILABLE);
		data.setTransferInventoryStatus("");
		
		if(txtInventoryId.getValue().trim() != null && !txtInventoryId.getValue().equals("")){
			data.setInventoryId(txtInventoryId.getValue().trim() );
		}
		if(txtBatchId.getValue().trim() != null && !txtBatchId.getValue().equals("")){
			data.setBatchId(txtBatchId.getValue().trim());
		}
		if (cmbAttribute.getSelectedItem() != null && txtAttributeValue.getValue().trim() !=null && !txtAttributeValue.getValue().trim().equals("") ) {
			ComboData selectedData = cmbAttribute.getSelectedItem().getValue();
			data.setAttributeId(selectedData.getId());
			if(txtAttributeValue.getValue().trim() !=null){
				data.setAttributeValue(txtAttributeValue.getValue());
			}
		}
		
		List<Integer> inventoryStatus = new ArrayList<Integer>();
		/*
		inventoryStatus.add(InventoryStatusConstants.AVAILABLE);
		inventoryStatus.add(InventoryStatusConstants.FAULTY);
		inventoryStatus.add(InventoryStatusConstants.RELEASED);
		inventoryStatus.add(InventoryStatusConstants.REPAIRED);
		inventoryStatus.add(InventoryStatusConstants.SCRAPPED);
		*/
		
		
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
		
		data.setInventoryStatuses(inventoryStatus);
		
		
		
		List<InventoryDetailVO> listInventoryDetailData = inventoryManagementBD.searchInventory(data);
		Logger.logTrace("INVENTORY", "after call " + listInventoryDetailData);
		//Logger.logTrace("INVENTORY", "listInventoryDetailData.isEmpty()"+listInventoryDetailData.isEmpty());
	//	Logger.logTrace("INVENTORY", "listInventoryDetailData.size"+listInventoryDetailData.size());
		if (listInventoryDetailData != null && !listInventoryDetailData.isEmpty()) {
			modelList = new ListModelList<InventoryDetailVO>(listInventoryDetailData);
			modelList.setMultiple(true);
			searchResultGrid.setModel(modelList);
	
			searchResultGrid.setItemRenderer(new SearchListItemRenderer());
			transferInventorytargetgrid.setVisible(true);
			resetComponents(cmbTransferwarehouse, cmbTransferwarehouse);
		} else {
			modelList = null;
			searchResultGrid.setModel(new ListModelList<InventoryDetailVO>());
			transferInventorytargetgrid.setVisible(false);
		}
		
	}
	
	
	public void clickEdit(){
		Logger.logTrace("Inventory", "in clickEdit function...");
		
		if(searchResultGrid.getSelectedItem()!=null){
			InventoryDetailVO inventoryDetailVO =(InventoryDetailVO) searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			boolean flag=true;
			argMap.put(SEARCH_COMPOSER_REF, this);
			argMap.put("title", "Inventory");
			argMap.put("InventoryDetailVO", inventoryDetailVO);
			argMap.put("transfer", flag);
			addViewTab(inventoryDetailVO.getInventoryId(), inventoryDetailVO.getInventoryId(), TransferInventoryTabbox, Pages.VIEW_INVENTORY_EVENT,argMap);
		}
		
	}
	
	
	private class SearchListItemRenderer implements
	ListitemRenderer<InventoryDetailVO> {
		private EventListener<Event> editItemListener,editHoverListner,editOutListener;
		public SearchListItemRenderer() {
		editItemListener = new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
			//	Image img = (Image) event.getTarget();
				clickEdit();
			}

		};
		
		editHoverListner = new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				Image img = (Image) event.getTarget();
				img.setSrc(BaseConstants.EDIT_ITEM_HOVER_IMAGE);
			}
		};
		
		editOutListener = new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				Image img = (Image) event.getTarget();
				img.setSrc(BaseConstants.EDIT_ITEM_IMAGE);
			}
		};
	}

@Override
public void render(Listitem item, InventoryDetailVO data, int index)
		throws Exception {

	Logger.logInfo(MODULE,"Data in Render method:" + data);
	if(data.getInventoryId()!=null){
		int no = index + 1;
		item.appendChild(new Listcell(String.valueOf(no)));
		//item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getWareHouseName())));
		item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getBatchId())));
		item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getInventoryId())));
		item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getStatus())));
		item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getWareHouseName())));
		item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourceType())));
		item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourceSubType())));
	
//	Map attributeMap = data.getAttribute();
//	if (!attributeMap.isEmpty()) {
//		for (AttributeVO attributeVO : attributeDatas) {
//			if(attributeMap.get(attributeVO.getName())!=null ){
//			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(String.valueOf(attributeMap.get(attributeVO.getName())))));
//			}
//			else {
//				
//				item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(null)));
//				
//				}
//			}
//		}
		Listcell operationCell = new Listcell();
		Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
		
		edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
		edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
		edit.addEventListener(Events.ON_CLICK, editItemListener);
		
		operationCell.appendChild(edit);
		item.appendChild(operationCell);
	item.setValue(data);
	}
	else{
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
	}
	// for(String str: data.getAttribute()){
	// item.appendChild(new Listcell(str));
	// }

	// item.appendChild(new Listcell(data.getAttribute()));
	// Listcell operationCell = new Listcell();
	// Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
	//
	// edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
	// edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
	// edit.addEventListener(Events.ON_CLICK, editItemListener);
	//
	// operationCell.appendChild(edit);
	// item.appendChild(operationCell);
	
}

}
	
	
	public void onClick$btnTransfer(Event event) {
		Logger.logInfo(MODULE, "In click of Transfer ");
		
		Long newWarehouseId=null,currentwarehouseId = null;
			
			// call for Transfer
			
			
				Set<Listitem> inventoryDetailVOs=null;
				inventoryDetailVOs=searchResultGrid.getSelectedItems();
				List<InventoryDetailVO> finalInventoryDetailVOs=new ArrayList<InventoryDetailVO>();
				if(inventoryDetailVOs!=null && !inventoryDetailVOs.isEmpty()){
					for(Listitem item : inventoryDetailVOs){
						Logger.logTrace(MODULE, (item.getValue()).toString());
						InventoryDetailVO inventoryDetailVO=(InventoryDetailVO)item.getValue();
						finalInventoryDetailVOs.add(inventoryDetailVO);
					}
			
				}
				Logger.logTrace(MODULE,"Final List Size:"+finalInventoryDetailVOs.size());
				Logger.logTrace(MODULE,"Final List:"+finalInventoryDetailVOs.toString());
				
				callTransfer(finalInventoryDetailVOs);
		
	}
	
	public void onChange$cmbWarehouse(Event event){
		Logger.logInfo(MODULE, "In change of warehouse event");
		
		// get all batch based on selected warehouse
		
		if(cmbWarehouse.getSelectedItem() != null){
		//	ComboData selectedData = cmbWarehouse.getSelectedItem().getValue();
			//Long warehouseId = selectedData.getId();
			
			resetComponents(cmbResourceType);
		}else{
			searchResultGrid.setVisible(false);
		}
	}
	
	public void onChange$cmbResourceType(Event event){
		Logger.logInfo(MODULE, "In change of resourceType event");
		
//		if(cmbResourceType.getSelectedItem() != null){
//			resetComponents(cmbResource, cmbResource,lbIAvailableQty);
//			
//			ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
//		
//			ItemBD itemBD = new ItemBD(getBDSessionContext());
//			
//			ItemVO itemVo = new ItemVO();
//			ResourceTypeVO resourceTypeVO = new ResourceTypeVO();
//			resourceTypeVO.setResourceTypeId(selectedData.getId());
//			itemVo.setResourceTypeVO(resourceTypeVO);
//			
//			List<ItemVO> resourceList = itemBD.searchItemData(itemVo);
//			Logger.logDebug(MODULE, "from server total resource : "+resourceList.size());
//			List<ComboData> comboDatas = new ArrayList<ComboData>();
//			for(ItemVO itemVO2 : resourceList)
//			{
//				comboDatas.add(new ComboData(itemVO2.getItemId(), itemVO2.getName()));
//			}
//		
//			cmbResource.setModel(new ListModelList<ComboData>(comboDatas));
//			cmbResource.setItemRenderer(new ComboItemDataRenderer());
//			
//		}
		
		List<ComboData> comboBoxDatas=null;
		//	comboBoxDatas.clear();
			//String selectedValue=cmbResourceType.getSelectedItem().getLabel();
			if(cmbResourceType.getSelectedItem() != null){
			ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
			Logger.logInfo(MODULE, "ComboData :"+selectedData.getId()+"::"+selectedData.getName());
			 comboBoxDatas = resultMap.get(selectedData);
			for(ComboData comboData1:comboBoxDatas){
				Logger.logInfo(MODULE, "ComboData :"+comboData1.getId()+"::"+comboData1.getName());
			}
		
			cmbResourceSubType.setModel(new ListModelList<ComboData>(comboBoxDatas));
			cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
			
		}
	}
	
	public void onClick$btnallTransfer(Event event){
		Logger.logInfo(MODULE, "In click of all Transfer ");
		
		if(modelList != null){
			
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
	            public void onEvent(ClickEvent event) throws Exception {
	                if(Messagebox.Button.YES.equals(event.getButton())) {
	                    
	                	List<InventoryDetailVO> finalInventoryDetailVOs=new ArrayList<InventoryDetailVO>();
	                	for(InventoryDetailVO inventoryDetailVO : modelList){
	                		finalInventoryDetailVOs.add(inventoryDetailVO);
	                	}
	                	
	                	// call for transfer
	                	Logger.logDebug(MODULE, "size : "+finalInventoryDetailVOs.size());
	                	callTransfer(finalInventoryDetailVOs);
	                }
	            }
	        };
	        if(cmbTransferwarehouse.getSelectedItem()!=null){
	        Messagebox.show("Are you sure you want to transfer all Inventory?", "Transfer Inventory", new Messagebox.Button[]{
	                Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);
	        }
			
			
		}
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
	
	public void onClick$btnCancel(Event event){
		resetComponents(cmbWarehouse, cmbWarehouse,txtInventoryId,txtBatchId,cmbResourceType,cmbResourceSubType,cmbTransferwarehouse,cmbAttribute,txtAttributeValue);
		
		cmbResourceSubType.setModel(new ListModelList<ComboData>());
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		List<ComboData> comboBoxDatas1 = itemBD.getAllResourceTypeData();
		
		if(comboBoxDatas1!=null && !comboBoxDatas1.isEmpty()) {
			cmbResourceType.setModel(new ListModelList<ComboData>(comboBoxDatas1));
			cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
		}
		
	}
}
