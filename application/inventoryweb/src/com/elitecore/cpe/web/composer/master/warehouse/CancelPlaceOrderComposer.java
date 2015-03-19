package com.elitecore.cpe.web.composer.master.warehouse;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.transfer.InventoryTransferBD;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorytransfer.CancelTransferOrderVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class CancelPlaceOrderComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Window cancelPlaceOrderWin;
	private Label lblOrderNo;
	private Textbox txtRemark;

//	private Label lblNote;
	
	private static final String MODULE = "CANCEL_PLACE_ORDER";

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
		
		Logger.logDebug(MODULE, "remark:"+txtRemark.getValue());
		Logger.logDebug(MODULE, "orderNo :"+lblOrderNo.getValue());
		
		
		
		
		
		
		CancelTransferOrderVO transferOrderVO = new CancelTransferOrderVO();
		transferOrderVO.setTarnsferNo(lblOrderNo.getValue());
		transferOrderVO.setRemarks(txtRemark.getValue());
		
		try {
			
			InventoryTransferBD inventoryTransferBD = new InventoryTransferBD(getBDSessionContext());
			inventoryTransferBD.cancelPlaceOrder(transferOrderVO);
			
			MessageUtility.successInformation("Success", "PlaceOrder Cancelled Successfully");
			cancelPlaceOrderWin.detach();
			if(arg.get("placeOrderSummaryObj") != null){
				SearchPlaceOrderDetailComposer obj = (SearchPlaceOrderDetailComposer)arg.get("placeOrderSummaryObj");
				obj.onClick$btnSearch(null);
			}
		
		} catch (UpdateBLException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("Error",e.getMessage());
		}
	}
	
	

}

