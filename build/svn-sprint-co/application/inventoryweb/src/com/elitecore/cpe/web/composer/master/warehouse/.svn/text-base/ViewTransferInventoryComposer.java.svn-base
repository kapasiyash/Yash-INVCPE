package com.elitecore.cpe.web.composer.master.warehouse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.transfer.InventoryTransferBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorytransfer.PartialAcceptRejectTransferOrderVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.PartialAcceptRejectTransferOrderVO.InventoryVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewWsAuditEntryVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;
import com.elitecore.cpe.web.utils.MessageUtility;

public class ViewTransferInventoryComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Window viewTransferInvnetoryWin;
	private Label lblOrderNo;
	private Textbox txtRemark;
	private Radiogroup actiongp,actionRadioGroup;
	private Listbox searchResultGrid;
	private Row barCodeRow,rejectRemarksRow,acceptRejectActionRow;
	
	private Textbox txtAddInventoryId,txtRemoveInventoryId,txtRejectRemark;
	private ListModelList<InventoryVO> modelList;
	
	
	private static final String MODULE = "VIEW_TRANSFER_INVENTORY";

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		Logger.logDebug(MODULE, "after composer ");
		Logger.logDebug(MODULE, "order No : "+arg.get("ORDERNO"));
		
		if(arg.get("ORDERNO") != null){
			lblOrderNo.setValue(arg.get("ORDERNO").toString());
		}
		
		modelList = new ListModelList<InventoryVO>();
		
		final InventoryTransferBD transferBD = new InventoryTransferBD(getBDSessionContext());
		
		txtAddInventoryId.addEventListener(Events.ON_OK, new EventListener<Event>() {

			
			@Override
			public void onEvent(Event event) throws Exception {
				
				
				if(actionRadioGroup.getSelectedItem() != null && actionRadioGroup.getSelectedItem().getValue() != null){
					if(actionRadioGroup.getSelectedItem().getValue().equals("accept")) {
						rejectRemarksRow.setVisible(false);
					} else if(actionRadioGroup.getSelectedItem().getValue().equals("reject")) {
						rejectRemarksRow.setVisible(true);
					}
				}
				
				if(txtAddInventoryId.getValue()!=null && !txtAddInventoryId.getValue().isEmpty()) {
					
					InventoryVO checkInventoryVO = transferBD.checkInventoryForTransfer(txtAddInventoryId.getValue(),lblOrderNo.getValue());
					if(checkInventoryVO.getResponseCode()==0) {
						
						boolean isPresent = false;
						for(InventoryVO inventoryVO : modelList) {
							if(inventoryVO.getInventoryNo().equals(txtAddInventoryId.getValue())) {
								isPresent = true;
								MessageUtility.failureInformation("Error", "Inventory Already Added");
							}
						}
						
						if(!isPresent) {
							
							if(actionRadioGroup.getSelectedItem().getValue().toString().equalsIgnoreCase("accept")){
								checkInventoryVO.setRemarks("");
								checkInventoryVO.setTransferStatus("ACCEPTED");
							} else { 
								if(txtRejectRemark.getValue()!=null && !txtRejectRemark.getValue().isEmpty()) {
									checkInventoryVO.setRemarks(txtRejectRemark.getValue());
									checkInventoryVO.setTransferStatus("REJECTED");
								} else {
									MessageUtility.failureInformation("Error", "Please enter Remarks for Rejected Inventories");
									return;
								}
							}
							
							modelList.add(checkInventoryVO);
							searchResultGrid.setModel(modelList);
							searchResultGrid.setItemRenderer(new InventoryTransferRenderer());
							resetComponents(txtAddInventoryId, txtAddInventoryId);
						}
						
					} else {
						MessageUtility.failureInformation("Error", checkInventoryVO.getResponseMessage());
						resetComponents(txtAddInventoryId, txtAddInventoryId,txtRemoveInventoryId);
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
						Iterator<InventoryVO> iterator =  modelList.iterator();
						while(iterator.hasNext()) {
							if(iterator.next().getInventoryNo().equals(txtRemoveInventoryId.getValue())) {
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

	
	private static class InventoryTransferRenderer implements ListitemRenderer<InventoryVO>{

		@Override
		public void render(Listitem item, InventoryVO data, int index)
				throws Exception {
			
			item.setValue(data);
			item.appendChild(new Listcell((index+1)+""));
			item.appendChild(new Listcell(data.getBatchNumber()));
			item.appendChild(new Listcell(data.getInventoryNo()));
			item.appendChild(new Listcell(data.getTransferStatus()));
			item.appendChild(new Listcell(data.getWarehouseName()));
			item.appendChild(new Listcell(data.getResourceType()));
			item.appendChild(new Listcell(data.getResourceSubtype()));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNullOrEmpty(data.getRemarks())));
		}
		
	}
	
	
	private void populateData(ViewWsAuditEntryVO data) {
		
		
		
	}
	
	public void onClick$btnSave(Event event){
		
		Logger.logDebug(MODULE, "onSubmit cllick");
		
		Logger.logDebug(MODULE, "action label:"+actiongp.getSelectedItem().getLabel());
		Logger.logDebug(MODULE, "remark:"+txtRemark.getValue());
		Logger.logDebug(MODULE, "orderNo :"+lblOrderNo.getValue());
		
		if(actiongp.getSelectedItem() == null || actiongp.getSelectedItem().getValue() == null
				|| actiongp.getSelectedItem().getValue().toString().equals("")){
			MessageUtility.failureInformation("Error", "Please Select Any action.");
			return;
		}
		
		PartialAcceptRejectTransferOrderVO transferOrderVO = new PartialAcceptRejectTransferOrderVO();
		transferOrderVO.setOrderNo(lblOrderNo.getValue());
		transferOrderVO.setRemark(txtRemark.getValue());
		transferOrderVO.setOrderStatus(actiongp.getSelectedItem().getValue().toString());
		transferOrderVO.setFromWarehouseName(arg.get("FROMWHNAME").toString());
		transferOrderVO.setToWarehouseName(arg.get("TOWHNAME").toString());
		
		if(modelList!=null && !modelList.isEmpty()) {
			List<InventoryVO> checkInventoryVOs =  getInventories(modelList);
			transferOrderVO.setInventoryVOs(checkInventoryVOs);
			
		}
		
		try {
			
			InventoryManagementBD inventoryMgtBD = new InventoryManagementBD(getBDSessionContext());
			inventoryMgtBD.acceptTransferInventory(transferOrderVO);
			
			MessageUtility.successInformation("Success", "Action Performed Successfully");
			viewTransferInvnetoryWin.detach();
			if(arg.get("transferInventorySummaryObj") != null){
				TransferInventorySummaryComposer obj = (TransferInventorySummaryComposer)arg.get("transferInventorySummaryObj");
				obj.onClick$btnSearch(null);
			}
		} catch (CreateBLException e) {
			MessageUtility.failureInformation("Error",e.getMessage());
		}
	}
	
	private List<InventoryVO> getInventories(ListModelList<InventoryVO> modelList) {
		
		List<InventoryVO> checkInventoryVOs = new ArrayList<InventoryVO>();
		if(modelList!=null && !modelList.isEmpty()) {
			for(InventoryVO checkInventoryVO : modelList) {
				checkInventoryVOs.add(checkInventoryVO);
			}
		}
		
		return checkInventoryVOs;
	}
	
	
	
	public void onClick$btnDownload(Event event){
		
		SearchTransferInventory searchTransferInventory = new SearchTransferInventory();
		searchTransferInventory.setOrderNo(lblOrderNo.getValue());
		searchTransferInventory.setOrderStatus("");
		searchTransferInventory.setPartial(true);

		Logger.logDebug(MODULE, "calling bd");
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		InventoryUploadVO uploadVO  = inventoryManagementBD.getInventoryDetails(searchTransferInventory);
		
		try {
			if(uploadVO != null){
				Filedownload.save(uploadVO.getUploadbyteData(), "", lblOrderNo.getValue()+".csv");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void onCheck$actionRadioGroup(Event event){
		
		if(actionRadioGroup.getSelectedItem().getValue().toString().equalsIgnoreCase("accept")){
			rejectRemarksRow.setVisible(false);
		} else {
			rejectRemarksRow.setVisible(true);
		}
		
	}
	
	public void onCheck$actiongp(Event event){
		if(actiongp.getSelectedItem().getValue().toString().equalsIgnoreCase("partial")){
			searchResultGrid.setVisible(true);
			barCodeRow.setVisible(true);
			acceptRejectActionRow.setVisible(true);
		}else{
			barCodeRow.setVisible(false);
			searchResultGrid.setVisible(false);
			acceptRejectActionRow.setVisible(false);
		}
	}
	
	
	
}
