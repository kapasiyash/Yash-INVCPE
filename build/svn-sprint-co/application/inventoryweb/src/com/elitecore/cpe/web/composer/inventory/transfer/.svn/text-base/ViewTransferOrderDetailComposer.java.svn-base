package com.elitecore.cpe.web.composer.inventory.transfer;

import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.vo.inventorytransfer.ViewTransferInventoryDetailVO;
import com.elitecore.cpe.util.GeneralUtility;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewTransferOrderDetailComposer extends BaseModuleComposer{

	private static final long serialVersionUID = 1L;
	private static final String MODULE = "VIEW_PLACE_ORDER_DETAIL";
	private Window viewTransferOrderDetailWin;
	
//	private Label lbResourceTypeName,lbResourceSubTypeName;
	private Label lbCreatedDate,lbCreatedBy,lbUpdateDate,lbUpdatedBy;
	private Label lbOrderNo,lbFrmWh,lbToWh,	lbStatus,lbRemark;
//	private Label lbTotalQty,lbacceptDate,lbTransferOrderNo,lbAcceptQty,lbTransferRemark;
	
//	private LinkedList<ActionMenuItem> actionItemList;
	
	

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		fetchViewEntity();
	}

	private void fetchViewEntity(){
		
		ViewTransferInventoryDetailVO OrderVO=null;
		try {
		
			if(arg.get("ORDERVO") != null){
					 OrderVO=(ViewTransferInventoryDetailVO)arg.get("ORDERVO");
				}
			Logger.logDebug(MODULE, "after composer::: "+OrderVO);
			
			populateData(OrderVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		
	}
	
	private void populateData(ViewTransferInventoryDetailVO data){
		
		if(data != null){
			viewTransferOrderDetailWin.setTitle(data.getOrderNo());
			lbOrderNo.setValue(data.getOrderNo());
			lbFrmWh.setValue(data.getFromWarehouseName());
			lbToWh.setValue(data.getToWarehouseName());
			lbStatus.setValue(data.getOrderStatus());
			
			lbRemark.setValue(data.getRemarks());
			
			lbCreatedBy.setValue(GeneralUtility.displayValueIfNull(data.getCreatedBy()));
			lbCreatedDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getCreateDate()));
			if(data.getLastModifiedBy()!=null && data.getLastModifiedDate()!=null){
			lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(data.getLastModifiedBy()));
			lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getLastModifiedDate()));
			}else{
				lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(null));
				lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(null));
				}
			
			}
		
	}

	
	
}
