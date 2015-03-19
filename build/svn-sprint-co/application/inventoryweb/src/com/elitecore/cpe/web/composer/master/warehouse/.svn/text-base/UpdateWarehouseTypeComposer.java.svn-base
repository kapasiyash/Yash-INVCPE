package com.elitecore.cpe.web.composer.master.warehouse;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class UpdateWarehouseTypeComposer extends BaseModuleViewComposer{

	private Hlayout updatesWarehouseType;
	
	private Textbox txtName;
//	private Textbox txtLocation;
	private Textbox txtDesc,txtReason;
	
//	private Combobox cmbParentWHname;
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		this.updatesWarehouseType = comp;
		
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
			txtName.setValue(data.getName());
			txtDesc.setValue(data.getDescription());

		}
		
	}
	
	public void onClick$btnUpdate(Event event){
		
		WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
		try {
			
			IBDSessionContext sessionContext = getBDSessionContext();
			WarehouseTypeVO warehouseVO = new WarehouseTypeVO();
			warehouseVO.setWarehouseTypeId(getViewEntityId());
			warehouseVO.setName(txtName.getValue());
			warehouseVO.setDescription(txtDesc.getValue());
			warehouseVO.setUpdatedby(sessionContext.getBLSession().getSessionUserId());
			warehouseVO.setReason(txtReason.getValue());
			
			wareHouseBD.updateWarehouseType(warehouseVO);
		
			MessageUtility.successInformation("Success", "WarehouseType Updated Successfully");
			updatesWarehouseType.detach();
			
			if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
				BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
				viewComposer.refreshView();
			}
			
		} catch (UpdateBLException e) {
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
}
