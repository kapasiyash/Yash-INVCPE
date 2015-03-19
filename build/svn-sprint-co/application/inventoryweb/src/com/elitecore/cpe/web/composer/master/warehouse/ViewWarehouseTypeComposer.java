package com.elitecore.cpe.web.composer.master.warehouse;

import java.util.LinkedList;
import java.util.List;

import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.util.GeneralUtility;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer.ActionMenuItem;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewWarehouseTypeComposer extends BaseCPEViewComposer{

	private static final long serialVersionUID = 1L;
	
	private LinkedList<ActionMenuItem> actionItemList;
	
	private Label lbWarehouseName,lbWarehouseDesc;
	private Label lbCreatedDate,lbCreatedBy,lbUpdateDate,lbUpdatedBy,lblMessage;
	
	@Override
	protected List<ActionMenuItem> getActionItemList() {
		// TODO Auto-generated method stub
		if (actionItemList == null) {
			actionItemList = new LinkedList<ActionMenuItem>();
			
//			SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.UPDATE_AR_EVENT);
			
			if(!lbWarehouseName.getValue().equalsIgnoreCase("Central")) {
				lblMessage.setVisible(false);
				if(isPermittedAction(ActionAlias.UPDATE_WAREHOUSETYPE)) {
					SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.UPDATE_WAREHOUSETYPE);
					actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
				}
			} else {
				lblMessage.setVisible(true);
			}
			
			
			
		}
		return actionItemList;
	}

	@Override
	protected List<ActionMenuItem> getViewItemList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshView() {
		fetchViewEntity();
		
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		fetchViewEntity();
	}

	private void fetchViewEntity(){
		
		WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
		WarehouseTypeVO warehouseVO = new WarehouseTypeVO();
		warehouseVO.setWarehouseTypeId(getViewEntityId());
		WarehouseTypeVO data = wareHouseBD.viewWarehouseType(warehouseVO);
		
		populateData(data);
		
	}
	
	private void populateData(WarehouseTypeVO data){
		
		if(data != null){
			lbWarehouseName.setValue(data.getName());
			//lbWarehouseAlias.setValue(data.getAlias());
			lbWarehouseDesc.setValue(data.getDescription());
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
