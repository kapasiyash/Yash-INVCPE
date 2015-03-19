/**
 * 
 */
package com.elitecore.cpe.web.composer.inventory.search;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.master.AttributeBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
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
//import com.elitecore.cpe.bl.delegates.warehouse.WareHouseBD;
//import com.elitecore.cpe.bl.entity.inventory.warehouse.AttributeData;
//import com.elitecore.cpe.bl.entity.inventory.warehouse.WarehouseData;

/**
 * @author Joel.Macwan
 * 
 */
public class SearchInventoryComposer extends BaseSearchComposer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.elitecore.cpe.web.base.ui.core.BaseSearchComposer#
	 * onDoubleClickedSearchItem(org.zkoss.zk.ui.event.Event)
	 */
	private static final long serialVersionUID = 1L;

//	private Window searchInventory;
	private Combobox cmbWarhouse;
	private Combobox cmbInventoryStatus,cmbResourceType,cmbResourceSubType,cmbAttribute;
	private Textbox txtBatchId;
	private Textbox txtInventoryId,txtAttributeValue,txtExternalBatchNumber;
	private Textbox txtResourceNumber,txtResourceName;
	private Tabbox searchInventoryTabbox;
	private Listbox searchResultGrid;
//	List<AttributeVO> attributeDatas;
	Map<ComboData,List<ComboData>> resultMap=null; 
	private ListModelList<InventoryDetailVO> modelList;
	private Button btnDownload;

	public void onClick$btnCancel(Event event){
		resetComponents(cmbWarhouse,cmbInventoryStatus,cmbResourceType,cmbResourceSubType,cmbWarhouse,cmbAttribute,txtBatchId,txtInventoryId,txtAttributeValue,txtExternalBatchNumber,txtResourceNumber,txtResourceName);
		searchResultGrid.setVisible(false);
		btnDownload.setDisabled(true);
		if(resultMap!=null && !resultMap.isEmpty()) {
			Collection<ComboData> resourceTypecomboBoxDatas = resultMap.keySet();
			if(resourceTypecomboBoxDatas!=null) {
				cmbResourceType.setModel(new ListModelList<ComboData>(resourceTypecomboBoxDatas));
				cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
			}
		}
		cmbResourceSubType.setModel(null);
	}
	
	
	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.elitecore.cpe.web.base.ui.core.BaseComposer#afterCompose(org.zkoss
	 * .zul.Window)
	 */
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		// TODO Auto-generated method stub
		System.out.println("in SearchInventoryComposer  afterComposer");
	//	this.searchInventory = comp;

		searchResultGrid.setVisible(false);
		init();
		//Map<String,Object> argMap = new HashMap<String, Object>();
		btnDownload.setDisabled(true);
		// /////////////
		
		
		cmbWarhouse.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				if (cmbWarhouse.getSelectedItem() != null) {
					ComboData selectedData = cmbWarhouse.getSelectedItem().getValue();
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
					}
				}
				
			}
		});
		

	}

	private void init() {

		IBDSessionContext sessionContext = getBDSessionContext();
		WareHouseBD wareHouseBD = new WareHouseBD(sessionContext);
//		AttributeBD attributeBD = new AttributeBD(sessionContext);
		resultMap = wareHouseBD.getAllResourceTypeWithResource(null);
		Logger.logTrace("Warehouse","Received data from DB for combodata:"+resultMap);
		InventoryManagementBD inventoryBD = new InventoryManagementBD(sessionContext);
		List<ComboData> comboBoxDatas = wareHouseBD.getAllWarehouseData();
		//attributeDatas = attributeBD.getAttributesByRef(CommonConstants.RESOURCE);
		List<ComboData> inventoryStatuscomboBoxDatas = inventoryBD.getAllInventoryStatusData();
		// System.out.println("Inventory status data:"+inventoryStatuscomboBoxDatas);
		cmbWarhouse.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbWarhouse.setItemRenderer(new ComboItemDataRenderer());
		cmbInventoryStatus.setModel(new ListModelList<ComboData>(inventoryStatuscomboBoxDatas));
		cmbInventoryStatus.setItemRenderer(new ComboItemDataRenderer());
		Collection<ComboData> resourceTypecomboBoxDatas = resultMap.keySet();
		cmbResourceType.setModel(new ListModelList<ComboData>(resourceTypecomboBoxDatas));
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
		Listhead lhd = new Listhead();
		Listheader lh1 = new Listheader();
		Listheader lh2 = new Listheader();
		Listheader lh3 = new Listheader();
		Listheader lh31 = new Listheader();
		Listheader lh4 = new Listheader();
		Listheader lh5 = new Listheader();
		Listheader lh6 = new Listheader();
		Listheader lh7 = new Listheader();
		Listheader lh8 = new Listheader();
		Listheader lh9 = new Listheader();
		lh1.setLabel("Sr.No");
		lh1.setWidth("55px");
		//lh8.setLabel("WareHouse");
		lh2.setLabel("Batch No");
		lh2.setSort("auto(batchId)");
		lh3.setLabel("Inventory No");
		lh31.setLabel("External BatchNumber");
		lh4.setLabel("Inventory Status");
		lh5.setLabel("Warehouse Name");
		lh6.setLabel("Resource Type");
		lh7.setLabel("Resource Subtype");
		lh8.setLabel("View");
		lh9.setLabel("Transfer Status");
		lh8.setWidth("55px");
		lh3.setSort("auto(inventoryId)");
		lh4.setSort("auto(status)");
		lh5.setSort("auto(wareHouseName)");
		lh6.setSort("auto(resourceType)");
		lh7.setSort("auto(resourceSubType)");
		lh9.setSort("auto(transferStatus)");
		//lh5.setSort("auto(distributorNumber)");
	//	lh6.setSort("auto(customerRefNumber)");
	//	lh7.setSort("auto(orderNumber)");
		lh1.setParent(lhd);
		lh2.setParent(lhd);
		lh3.setParent(lhd);
		lh31.setParent(lhd);
		lh4.setParent(lhd);
		lh5.setParent(lhd);
		lh6.setParent(lhd);
		lh7.setParent(lhd);
		lh9.setParent(lhd);
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
	}

	public void onChange$cmbResourceType(Event e) throws InterruptedException{
			
		Logger.logTrace("INVENTORY", "Inside onChange$cmbResourceType event handler ");
		reset();
			List<ComboData> comboBoxDatas=null;
		try{
			if(cmbResourceType.getSelectedItem() != null){
			ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
			Logger.logInfo("WAREHOUSE", "ComboData :"+selectedData.getId()+"::"+selectedData.getName());
			 comboBoxDatas = resultMap.get(selectedData);
			for(ComboData comboData1:comboBoxDatas){
				Logger.logInfo("Threshold", "ComboData :"+comboData1.getId()+"::"+comboData1.getName());
			}
		
			cmbResourceSubType.setModel(new ListModelList<ComboData>(comboBoxDatas));
			cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
			
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
			
}
	
	
	public void onClick$btnSearch(Event event) {
		try{
		Logger.logTrace("INVENTORY", "Inside btnSearch ");
		
		SearchInventoryVO data = new SearchInventoryVO();
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
	//	String wareHouseName = cmbWarhouse.getValue();
		if (cmbWarhouse.getSelectedItem() != null) {
			ComboData selectedData = cmbWarhouse.getSelectedItem().getValue();
			data.setWareHouseId(selectedData.getId());
		}
		if (cmbInventoryStatus.getSelectedItem() != null) {
			ComboData selectedData = cmbInventoryStatus.getSelectedItem().getValue();
			data.setInventoryStatusId(selectedData.getId());
			data.setInventoryStatus(cmbInventoryStatus.getValue());
		}
		if (cmbResourceType.getSelectedItem() != null) {
			ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
			data.setResourceTypeId(selectedData.getId());
			
		}
		
		if (cmbResourceSubType.getSelectedItem() != null) {
			ComboData selectedData = cmbResourceSubType.getSelectedItem().getValue();
			data.setResourceSubtypeId(selectedData.getId());
		}
		
		if(txtResourceNumber.getValue()!=null && !txtResourceNumber.getValue().isEmpty()) {
			data.setResourceNumber(txtResourceNumber.getValue());
		}
		if(txtResourceName.getValue()!=null && !txtResourceName.getValue().isEmpty()) {
			data.setResourceName(txtResourceName.getValue());
		}
		
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(
				getBDSessionContext());
		// data.setWareHouseId(selectedData.);
		// data.setWareHouseName(cmbWarhouse.getValue());
		System.out.println("Selected Warehouse name:" + data.getWareHouseName());
		if (txtBatchId.getValue().trim() != null) {
			data.setBatchId(txtBatchId.getValue().trim());
		}
		if(txtExternalBatchNumber.getValue()!=null && !txtExternalBatchNumber.getValue().isEmpty()) {
			data.setExternalBatchNumber(txtExternalBatchNumber.getValue());
		}
		if (txtInventoryId.getValue().trim() != null) {
			data.setInventoryId(txtInventoryId.getValue().trim());
		}
		if (cmbAttribute.getSelectedItem() != null && txtAttributeValue.getValue().trim() !=null && !txtAttributeValue.getValue().trim().equals("") ) {
			ComboData selectedData = cmbAttribute.getSelectedItem().getValue();
			data.setAttributeId(selectedData.getId());
			if(txtAttributeValue.getValue().trim() !=null){
				data.setAttributeValue(txtAttributeValue.getValue().trim());
			}
		}
	
		System.out.println("Selected Status name:" + data.getInventoryStatus());
		Logger.logTrace("INVENTORY", "calling BD ");
		System.out.println("Sending data to BD:" + data);
		List<InventoryDetailVO> listInventoryDetailData = inventoryManagementBD.searchInventory(data);
		Logger.logTrace("INVENTORY", "after call " + listInventoryDetailData);
		//Logger.logTrace("INVENTORY", "listInventoryDetailData.isEmpty()"+listInventoryDetailData.isEmpty());
	//	Logger.logTrace("INVENTORY", "listInventoryDetailData.size"+listInventoryDetailData.size());
		if (listInventoryDetailData != null && !listInventoryDetailData.isEmpty()) {
			modelList = new ListModelList<InventoryDetailVO>(listInventoryDetailData);
			searchResultGrid.setModel(new ListModelList<InventoryDetailVO>(listInventoryDetailData));

			searchResultGrid.setItemRenderer(new SearchListItemRenderer());
		} else {
			modelList = null;
			searchResultGrid.setModel(new ListModelList<InventoryDetailVO>());
		}
		if(listInventoryDetailData!=null && listInventoryDetailData.size()>0){
			btnDownload.setDisabled(false);
		}else{
			btnDownload.setDisabled(true);
		}
	//	reset();
		}catch(Exception e){
			e.printStackTrace();
			//reset();
		}
	}
	public void clickEdit(){
		Logger.logTrace("Inventory", "in clickEdit function...");
		
		if(searchResultGrid.getSelectedItem()!=null){
			InventoryDetailVO inventoryDetailVO =(InventoryDetailVO) searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			
			argMap.put(SEARCH_COMPOSER_REF, this);
			argMap.put("title", "Inventory");
			argMap.put("InventoryDetailVO", inventoryDetailVO);
			addViewTab(inventoryDetailVO.getInventoryId(), inventoryDetailVO.getInventoryId(), searchInventoryTabbox, Pages.VIEW_INVENTORY_EVENT,argMap);
		}
		
	}

	private class SearchListItemRenderer implements
			ListitemRenderer<InventoryDetailVO> {
		private EventListener<Event> editItemListener,editHoverListner,editOutListener;
	//	SimpleDateFormat dateFormat = new SimpleDateFormat(getDateTimeFormat());
public SearchListItemRenderer() {
			
			editItemListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					//Image img = (Image) event.getTarget();
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

			System.out.println("Data in Render method:" + data);
			if(data.getInventoryId()!=null){
			int no = index + 1;
			String transferStatus=null;
			if(data.getTransferStatus()!=null){
			 transferStatus=data.getTransferStatus();
			}
			item.appendChild(new Listcell(String.valueOf(no)));
			//item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getWareHouseName())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getBatchId())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getInventoryId())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getExternalBatchNumber())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getStatus())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getWareHouseName())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourceType())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourceSubType())));
			if(transferStatus==null){
				item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(null)));
				}
			else if(transferStatus.equalsIgnoreCase("WAITING")){
				item.appendChild(new Listcell("Inprocess"));
			}else if(transferStatus.equalsIgnoreCase("REJECTED")){
				item.appendChild(new Listcell("Rejected"));
			}
		//dateFormat.format(data.getCreateDate());
			
			
		//	item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getDistributorNumber())));
		//	item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getCustomerRefNumber())));
		//	item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getOrderNumber())));
		//	Map attributeMap = data.getAttribute();
//			if (!attributeMap.isEmpty()) {
//				for (AttributeVO attributeVO : attributeDatas) {
//					if(attributeMap.get(attributeVO.getName())!=null ){
//					item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(String.valueOf(attributeMap.get(attributeVO.getName())))));
//					}
//					else {
//						
//						item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(null)));
//						
//						}
//					}
//				}
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
	private void reset(){
		Logger.logTrace("INVENTORY", "Inside  reset()");
		cmbResourceSubType.setSelectedItem(null);
		//resetComponents(cmbResourceSubType);
		cmbResourceSubType.setModel(null);
		
	}
	
	public void onClick$btnDownload(Event event){
		
		Listhead listhead = searchResultGrid.getListhead();
		
		List<Component> allchildren = listhead.getChildren();
		Logger.logDebug("INVENTORY", "size : "+allchildren.size());
		StringBuilder builder = new StringBuilder();
		
		if(allchildren.size() > 0)
		{
			for(Component c : allchildren ){
				Listheader header = (Listheader)c;
				
				if(header.getLabel().equalsIgnoreCase("view"))  continue;
				
				Logger.logDebug("INVENTORY", header.getLabel());
				builder.append(header.getLabel()+",");
			}
			builder.append("GuaranteeWarrantyMode"+",");
			builder.append("Warranty Date"+",");
			builder.append("Warranty Type"+",");
		}
		builder.append("\n");
		
		if(modelList != null){
			
			int no= 1;
			for(InventoryDetailVO inventoryDetailVO  : modelList){
				
				builder.append(no).append(",")	
				.append(inventoryDetailVO.getBatchId()).append(",")
				.append(inventoryDetailVO.getInventoryId()).append(",")
				.append(GeneralUtility.displayValueIfNull(inventoryDetailVO.getExternalBatchNumber())).append(",")
				.append(inventoryDetailVO.getStatus()).append(",")
				.append(inventoryDetailVO.getWareHouseName()).append(",")
				.append(inventoryDetailVO.getResourceType()).append(",")
				.append(inventoryDetailVO.getResourceSubType()).append(",")
				.append(gettransferStatus(inventoryDetailVO.getTransferStatus())).append(",")
				.append(GeneralUtility.displayValueIfNull(inventoryDetailVO.getGurrantyWarrantyMode())).append(",")
				.append(GeneralUtility.displayINDateTimeFormat(inventoryDetailVO.getWarrantyDate())).append(",")
				.append(GeneralUtility.displayValueIfNull(inventoryDetailVO.getWarrantyType())).append(",")
				
				.append("\n");
				no++;
			}
			
		}
		
		Filedownload.save(builder.toString().getBytes(), "", "Inventory.csv");
	}
	public static String gettransferStatus(String transferStatus) {
		String status="";
		if(transferStatus==null){
			status="";
			}
		else if(transferStatus.equalsIgnoreCase("WAITING")){
			status = "Inprocess";
		}else if(transferStatus.equalsIgnoreCase("REJECTED")){
			status = "Rejected";
		}
		
		return status;
	}
}
