package com.elitecore.cpe.web.composer.inventory.transfer;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchWarehouseInventoryStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.WarehouseInventoryStatusVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.core.BaseComposer.ComboItemDataRenderer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class TransferInventory extends BaseSearchComposer{
	private static final long serialVersionUID = 1L;
	
//	private Window transferInventoryBatch;
	private Combobox cmbWarehouse,cmbTransferwarehouse,cmbResourceType,cmbResource;
	private Intbox txtTransferedQty;
	private Listbox searchResultGrid;
	private Label lbIAvailableQty;
	
	private static final  String MODULE = "TRANSFER INVENTORY";
	private Long availableQty = 0L;
	
	
	public void onClick$btnCancel(Event event){
		resetComponents(cmbWarehouse,cmbWarehouse,cmbTransferwarehouse,cmbResourceType,cmbResource);
	}
	
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
		List<ComboData> comboBoxDatas1 = itemBD.getAllResourceTypeData();
		
		cmbResourceType.setModel(new ListModelList<ComboData>(comboBoxDatas1));
		cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
	}
	
	public void onClick$btnTransfer(Event event) {
		Logger.logInfo(MODULE, "In click of Transfer ");
		
		if(cmbWarehouse.getSelectedItem() != null && cmbTransferwarehouse.getSelectedItem() != null && cmbResource.getSelectedItem() != null){
			Integer tobeTransferQty = txtTransferedQty.getValue();
			ComboData fromWH =  cmbWarehouse.getSelectedItem().getValue();
			ComboData toWH   =  cmbTransferwarehouse.getSelectedItem().getValue();
			ComboData resourceSel   =  cmbResource.getSelectedItem().getValue();
			
			
			if(tobeTransferQty > availableQty){
				MessageUtility.failureInformation("ERROR","Please Enter lower Qty to be transfer than Available");
				return;
			}else if(tobeTransferQty <= 0){
				MessageUtility.failureInformation("ERROR","Please Enter Transfer Qty");
				return;
			}else if(fromWH.getId().equals(toWH.getId())){
				MessageUtility.failureInformation("ERROR","Please Select Different Warehouse for New Warehouse");
				return;
			}
			
			// call for Transfer
			try {
				
				SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO = new SearchWarehouseInventoryStatusVO();
				searchWarehouseInventoryStatusVO.setCurrentWarehouseId(fromWH.getId());
				searchWarehouseInventoryStatusVO.setNewWarehouseId(toWH.getId());
				searchWarehouseInventoryStatusVO.setResourceId(resourceSel.getId());
				searchWarehouseInventoryStatusVO.setTotalQty(tobeTransferQty.longValue());
				
				InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
				inventoryManagementBD.transferInventory(searchWarehouseInventoryStatusVO);
			
				MessageUtility.successInformation("Success", "Transfer Inventory succussfully done" );
				
				resetComponents(cmbWarehouse, cmbWarehouse,cmbTransferwarehouse,cmbResource,cmbResourceType,lbIAvailableQty,txtTransferedQty);
			} catch (CreateBLException e) {
				MessageUtility.failureInformation("ERROR",e.getMessage());
			}
		}
		
	}
	
	public void onChange$cmbWarehouse(Event event){
		Logger.logInfo(MODULE, "In change of warehouse event");
		
		// get all batch based on selected warehouse
		
		if(cmbWarehouse.getSelectedItem() != null){
		//	ComboData selectedData = cmbWarehouse.getSelectedItem().getValue();
		//	Long warehouseId = selectedData.getId();
			
			resetComponents(cmbResourceType, lbIAvailableQty,txtTransferedQty);
		}else{
			searchResultGrid.setVisible(false);
		}
	}
	
	public void onChange$cmbResourceType(Event event){
		Logger.logInfo(MODULE, "In change of resourceType event");
		
		if(cmbResourceType.getSelectedItem() != null){
			resetComponents(cmbResource, cmbResource,lbIAvailableQty);
			
			ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
		
			ItemBD itemBD = new ItemBD(getBDSessionContext());
			
			ItemVO itemVo = new ItemVO();
			ResourceTypeVO resourceTypeVO = new ResourceTypeVO();
			resourceTypeVO.setResourceTypeId(selectedData.getId());
			itemVo.setResourceTypeVO(resourceTypeVO);
			
			List<ItemVO> resourceList = itemBD.searchItemData(itemVo);
			Logger.logDebug(MODULE, "from server total resource : "+resourceList.size());
			List<ComboData> comboDatas = new ArrayList<ComboData>();
			for(ItemVO itemVO2 : resourceList)
			{
				comboDatas.add(new ComboData(itemVO2.getItemId(), itemVO2.getName()));
			}
		
			cmbResource.setModel(new ListModelList<ComboData>(comboDatas));
			cmbResource.setItemRenderer(new ComboItemDataRenderer());
			
		}
	}
	
	public void onChange$cmbResource(Event event){
		Logger.logInfo(MODULE, "In change of resource event");
		
		if(cmbResource.getSelectedItem() != null && cmbWarehouse.getSelectedItem() != null){
			
			// get total avaialble qty from warehouseinventoryStatus
			ComboData resourceSelectedData = cmbResource.getSelectedItem().getValue();
			ComboData warehouseSelectedData = cmbWarehouse.getSelectedItem().getValue();
			
			SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO = new SearchWarehouseInventoryStatusVO();
			searchWarehouseInventoryStatusVO.setCurrentWarehouseId(warehouseSelectedData.getId());
			searchWarehouseInventoryStatusVO.setResourceId(resourceSelectedData.getId());
			
			InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
			Long availableQty  = inventoryManagementBD.getAvailableStock(searchWarehouseInventoryStatusVO);
			
			lbIAvailableQty.setValue(availableQty.toString());
			this.availableQty = availableQty;
		}else{
			lbIAvailableQty.setValue("0");
			availableQty = 0L;
		}
	}
	
	
}
