package com.elitecore.cpe.web.composer.master.warehouse;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.transfer.InventoryTransferBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.system.audit.ViewWsAuditEntryVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class RejectTransferInventoryComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Window viewTransferInvnetoryWin;
	private Label lblOrderNo;
	private Textbox txtRemark;
	private Radiogroup actiongp;

	private Textbox txtfileName;
	
	private Media fileMedia;
	
	private Button btnBrowse,btnDownload;
	private Label lblNote;
	
	private static final String MODULE = "VIEW_TRANSFER_INVENTORY";

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		Logger.logDebug(MODULE, "after composer ");
		Logger.logDebug(MODULE, "order No : "+arg.get("ORDERNO"));
		
		if(arg.get("ORDERNO") != null){
			lblOrderNo.setValue(arg.get("ORDERNO").toString());
		}
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
				(txtfileName.getValue() == null || txtfileName.getValue().isEmpty() || fileMedia == null) ){
			MessageUtility.failureInformation("ERROR", "Please Select CSV file");
			return;
			
		}
		
		byte[] uploadbyteData = null;
		try {
			uploadbyteData =fileMedia.getByteData();
		}catch(IllegalStateException illegalStateException){
			uploadbyteData = fileMedia.getStringData().getBytes();
		}
		catch (Exception e) {}
		
		
		SearchTransferInventory searchTransferInventory = new SearchTransferInventory();
		searchTransferInventory.setOrderNo(lblOrderNo.getValue());
		searchTransferInventory.setRemark(txtRemark.getValue());
		searchTransferInventory.setOrderStatus(actiongp.getSelectedItem().getValue().toString());
		searchTransferInventory.setUploadbyteData(uploadbyteData);
		searchTransferInventory.setFromWarehouseName(arg.get("FROMWHNAME").toString());
		searchTransferInventory.setToWarehouseName(arg.get("TOWHNAME").toString());
		
		try {
			
			InventoryTransferBD inventoryTransferBD = new InventoryTransferBD(getBDSessionContext());
			inventoryTransferBD.acceptRejectedTransferInventory(searchTransferInventory);
			
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
	
	public void onClick$btnDownload(Event event){
		
		SearchTransferInventory searchTransferInventory = new SearchTransferInventory();
		searchTransferInventory.setOrderNo(lblOrderNo.getValue());
		searchTransferInventory.setOrderStatus("Rejected");
		searchTransferInventory.setPartial(true);

		Logger.logDebug(MODULE, "calling bd");
		InventoryTransferBD inventoryTransferBD = new InventoryTransferBD(getBDSessionContext());
		InventoryUploadVO uploadVO  = inventoryTransferBD.getInventoryDetailsForRejectedInventory(searchTransferInventory);
		
		try {
			if(uploadVO != null){
				Filedownload.save(uploadVO.getUploadbyteData(), "text/plain", lblOrderNo.getValue()+".csv");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public void onCheck$actiongp(Event event){
		if(actiongp.getSelectedItem().getValue().toString().equalsIgnoreCase("partial")){
			txtfileName.setVisible(true);
			btnBrowse.setVisible(true);
			btnDownload.setVisible(true);
			lblNote.setVisible(true);
		}else{
			txtfileName.setVisible(false);
			btnBrowse.setVisible(false);
			btnDownload.setVisible(false);
			lblNote.setVisible(false);
		}
	}
	
	
	public void onUpload$btnBrowse(UploadEvent event){
		System.out.println(" inside onUpload$btnBrowse function");
		
		fileMedia = event.getMedia();
        
        Logger.logTrace("Inventory","getName :"+fileMedia.getName());
        Logger.logTrace("Inventory","getFormat :"+fileMedia.getFormat());
        Logger.logTrace("Inventory","getContentType :"+fileMedia.getContentType());
        
        System.out.println(fileMedia.getFormat());
        System.out.println(fileMedia.getName());
        
        txtfileName.setValue(fileMedia.getName());
/*        if(fileMedia.getFormat().equalsIgnoreCase("txt") 
        		|| fileMedia.getFormat().equalsIgnoreCase("csv") || fileMedia.getFormat().equalsIgnoreCase("x-download"))
        {
	       
	        
        }else{
        	MessageUtility.failureInformation("ERROR", "Please Select CSV file");
        	fileMedia = null;
        }
*/	}
}
