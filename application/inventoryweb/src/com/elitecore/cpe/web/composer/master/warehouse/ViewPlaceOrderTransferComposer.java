package com.elitecore.cpe.web.composer.master.warehouse;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.system.audit.ViewWsAuditEntryVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class ViewPlaceOrderTransferComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Window viewPlaceOrderWin;
//	private Grid viewwsauditgrid;
	private Label lblOrderNo,lblQuantity,lblQuantitytitle,lblAcceptQuantity,lblmandatory;
	private Textbox txtRemark;
//	private SearchWsAuditVO auditVO;
	private Radiogroup actiongp;
	private Intbox txtAcceptQuantity;

	
	
	
//	private Label lblNote;
	
	private static final String MODULE = "VIEW_TRANSFER_INVENTORY";

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		Logger.logDebug(MODULE, "after composer ");
		Logger.logDebug(MODULE, "order No : "+arg.get("ORDERNO"));
		
		if(arg.get("ORDERNO") != null){
			lblOrderNo.setValue(arg.get("ORDERNO").toString());
		}
		if(arg.get("QUANTITY") != null){
			lblQuantity.setValue(arg.get("QUANTITY").toString());
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
		
		if(actiongp.getSelectedItem().getValue().toString().equalsIgnoreCase("partial") && 
				(txtAcceptQuantity.getValue()==null || txtAcceptQuantity.getValue()==0 
				|| (txtAcceptQuantity.getValue()>Integer.parseInt(lblQuantity.getValue()))) ){
			MessageUtility.failureInformation("ERROR", "Please enter proper Quantity");
			return;
			
		}
		
		
		
		
		SearchTransferInventory searchTransferInventory = new SearchTransferInventory();
		searchTransferInventory.setOrderNo(lblOrderNo.getValue());
		searchTransferInventory.setRemark(txtRemark.getValue());
		searchTransferInventory.setOrderStatus(actiongp.getSelectedItem().getValue().toString());
		if((actiongp.getSelectedItem().getValue().toString().equalsIgnoreCase("partial")) && txtAcceptQuantity.getValue()!=null){
		searchTransferInventory.setAcceptQuantity(Long.parseLong(txtAcceptQuantity.getValue().toString()));
		}else if((actiongp.getSelectedItem().getValue().toString().equalsIgnoreCase("rejectall"))){
			searchTransferInventory.setAcceptQuantity(0L);
		}else{
			searchTransferInventory.setAcceptQuantity(Long.parseLong(lblQuantity.getValue()));
		}
//		searchTransferInventory.setFromWarehouseName(arg.get("FROMWHNAME").toString());
//		searchTransferInventory.setToWarehouseName(arg.get("TOWHNAME").toString());
		
		try {
			
			InventoryManagementBD inventoryMgtBD = new InventoryManagementBD(getBDSessionContext());
			inventoryMgtBD.acceptPlaceOrder(searchTransferInventory);
			
			MessageUtility.successInformation("Success", "Action Performed Successfully");
			viewPlaceOrderWin.detach();
			if(arg.get("placeOrderSummaryObj") != null){
				SearchPlaceOrderDetailComposer obj = (SearchPlaceOrderDetailComposer)arg.get("placeOrderSummaryObj");
				obj.onClick$btnSearch(null);
			}
		} catch (CreateBLException e) {
			MessageUtility.failureInformation("Error",e.getMessage());
		}
	}
	
	
	
	public void onCheck$actiongp(Event event){
		if(actiongp.getSelectedItem().getValue().toString().equalsIgnoreCase("partial")){
			txtAcceptQuantity.setVisible(true);
			lblmandatory.setVisible(true);
			lblQuantity.setVisible(true);
			lblQuantitytitle.setVisible(true);
			lblAcceptQuantity.setVisible(true);
		}else{
			txtAcceptQuantity.setVisible(false);
			lblmandatory.setVisible(false);
			lblQuantity.setVisible(false);
			lblQuantitytitle.setVisible(false);
			lblAcceptQuantity.setVisible(false);
			
		}
	}
	
	
	
}
