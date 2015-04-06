package com.elitecore.cpe.web.composer.master.warehouse;

import java.util.LinkedList;
import java.util.List;

import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.cpe.util.GeneralUtility;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer.ActionMenuItem;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewPlaceOrderDetailComposer extends BaseModuleComposer{

	private static final long serialVersionUID = 1L;
	private static final String MODULE = "VIEW_PLACE_ORDER_DETAIL";
	private Window viewPlaceOrderDetailWin;
	
	private Label lbResourceTypeName,lbResourceSubTypeName,lbResourceName;
	private Label lbCreatedDate,lbCreatedBy,lbUpdateDate,lbUpdatedBy;
	private Label lbOrderNo,lbFrmWh,lbToWh,	lbTotalQty,	lbStatus,lbacceptDate,lbTransferOrderNo,lbAcceptQty,lbRemark,lbTransferRemark;
	
//	private LinkedList<ActionMenuItem> actionItemList;
	
	

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		
		System.out.println("here");
		fetchViewEntity();
	}

	private void fetchViewEntity(){
		
		PlaceOrderVO placeOrderVO=null;
		try {
		
			if(arg.get("PLACEORDERVO") != null){
					 placeOrderVO=(PlaceOrderVO)arg.get("PLACEORDERVO");
				}
			Logger.logDebug(MODULE, "after composer::: "+placeOrderVO);
			
			populateData(placeOrderVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		
	}
	
	private void populateData(PlaceOrderVO data){
		
		if(data != null){
			viewPlaceOrderDetailWin.setTitle(data.getOrderNo());
			lbOrderNo.setValue(data.getOrderNo());
			lbFrmWh.setValue(data.getFromwarehouse());
			lbToWh.setValue(data.getTowarehouse());
			lbTotalQty.setValue(data.getQuantity().toString());
			lbStatus.setValue(data.getStatus());
			if(data.getAcceptRejectDate()!=null){
			lbacceptDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getAcceptRejectDate()));
			}
			if(data.getTransferOrderNo()!=null){
			lbTransferOrderNo.setValue(data.getTransferOrderNo());
			}
			if(data.getAcceptquantity()!=null){
				lbAcceptQty.setValue(data.getAcceptquantity().toString());
				}
			
			lbRemark.setValue(data.getRemark());
			if(data.getTransferRemark()!=null){
			lbTransferRemark.setValue(data.getTransferRemark());
			}
			lbResourceName.setValue(data.getResourceName());
			lbResourceSubTypeName.setValue(data.getResourceSubtype());
			lbResourceTypeName.setValue(data.getResourceType());
			lbCreatedBy.setValue(GeneralUtility.displayValueIfNull(data.getCreatedby()));
			lbCreatedDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getCreateDate()));
			if(data.getUpdatedby()!=null && data.getUpdatedDate()!=null){
			lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(data.getUpdatedby()));
			lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getUpdatedDate()));
			}else{
				lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(null));
				lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(null));
				}
			
			}
		
	}

	
	
}
