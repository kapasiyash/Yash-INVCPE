package com.elitecore.cpe.web.composer.master.warehouse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class UpdateWarehouseComposer extends BaseModuleViewComposer{

	private Hlayout updatesWarehouse;
	
	private Textbox txtName,txtWareHouseCodeName;
	private Textbox txtLocation;
	private Textbox txtDesc,txtReason,txtContactNo,txtEmailId,txtOwner;
	private Row parentRow;
	private Combobox cmbParentWHname,cmbWHTypename;
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		this.updatesWarehouse = comp;
		
		fetchViewEntity();
		
	}
	
	private List<ComboData> filterData(List<ComboData> comboBoxDatas1) {
		
		List<ComboData> comboDatas = new ArrayList<ComboData>();
		for(ComboData comboData : comboBoxDatas1) {
			if(!comboData.getName().equalsIgnoreCase("Central")) {
				comboDatas.add(comboData);
			}
		}
		return comboDatas;
	}
	
	private void fetchViewEntity(){
		
		WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
		
		
		WarehouseVO warehouseVO = new WarehouseVO();
		//--added start
		Long warehouseId=getViewEntityId();
		if(warehouseId==0){
			warehouseId=Long.parseLong(getStrViewEntityId());
		}
		//--added end
		//warehouseVO.setWarehouseId(getViewEntityId());
		warehouseVO.setWarehouseId(warehouseId);
		WarehouseVO data = wareHouseBD.viewWarehouse(warehouseVO);
		
		System.out.println("Data Here in update : "+data.getWarehouseType());
		
		boolean isCentral = false;
		if(data.getWarehouseType() != null){
			if("Central".equals(data.getWarehouseType().getName())) {
				isCentral = true;
				parentRow.setVisible(false);
				txtName.setDisabled(true);
				cmbWHTypename.setDisabled(true);
				
			} 
		}
		
		if(!isCentral) {
			
			List<ComboData> comboBoxDatas = wareHouseBD.getAllWarehouseDataExceptBy(getViewEntityId());
			
			Iterator<ComboData> itrComboData = comboBoxDatas.iterator();
			while(itrComboData.hasNext()){
				ComboData comboData = itrComboData.next();
				
				if(comboData.getId().equals(data.getWarehouseId())){
					itrComboData.remove();
					
				}
			}
			cmbParentWHname.setModel(new ListModelList<ComboData>(comboBoxDatas));
			cmbParentWHname.setItemRenderer(new ComboItemDataRenderer());
			
			List<ComboData> comboBoxDatas1 = wareHouseBD.getAllWarehouseTypeData();
			if(comboBoxDatas1!=null && !comboBoxDatas1.isEmpty()) {
				List<ComboData> comboDatas = filterData(comboBoxDatas1);
				cmbWHTypename.setModel(new ListModelList<ComboData>(comboDatas));
				cmbWHTypename.setItemRenderer(new ComboItemDataRenderer());
			}
		}
		
		populateData(data);
		
		
		
	}
	
	private void populateData(WarehouseVO data){
		
		if(data != null){
			txtName.setValue(data.getName());
			txtLocation.setValue(data.getLocation());
			txtDesc.setValue(data.getDescription());
			txtWareHouseCodeName.setValue(data.getWarehouseCode());
			txtContactNo.setValue(data.getContactNo());
			txtOwner.setValue(data.getOwner());
			txtEmailId.setValue(data.getEmailId());
			
			cmbParentWHname.setValue(data.getParentWarehouseName());
			if(data.getWarehouseType() != null){
				cmbWHTypename.setValue(data.getWarehouseType().getName());
			}
		}
		
	}
	
	public void onClick$btnUpdate(Event event){
		
		WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
		try {
			
			IBDSessionContext sessionContext = getBDSessionContext();
			WarehouseVO warehouseVO = new WarehouseVO();
			warehouseVO.setWarehouseId(getViewEntityId());
			warehouseVO.setName(txtName.getValue());
			warehouseVO.setWarehouseCode(txtWareHouseCodeName.getValue());
			warehouseVO.setDescription(txtDesc.getValue());
			warehouseVO.setLocation(txtLocation.getValue());
//			warehouseVO.setUpdatedate(new Timestamp(new Date().getTime()));
			warehouseVO.setUpdatedby(sessionContext.getBLSession().getSessionUserId());
			warehouseVO.setReason(txtReason.getValue());
			if(parentRow.isVisible()) {
				if(cmbParentWHname.getSelectedItem() != null){
					ComboData selectedData = cmbParentWHname.getSelectedItem().getValue();
					warehouseVO.setParentWarehouseId(selectedData.getId());
					warehouseVO.setParentWarehouseName(cmbParentWHname.getValue());
				}else{
					MessageUtility.successInformation("Success", "Plese Select Parent Warehouse");
					return;
				}
			}
			
			if(!cmbWHTypename.isDisabled()) {
				if(cmbWHTypename.getSelectedItem() != null){
					ComboData selectedData = cmbWHTypename.getSelectedItem().getValue();
					
					WarehouseTypeVO warehouseTypeVO = new WarehouseTypeVO();
					warehouseTypeVO.setWarehouseTypeId(selectedData.getId());
					warehouseTypeVO.setName(cmbWHTypename.getValue());
					warehouseVO.setWarehouseType(warehouseTypeVO);
				}else{
					MessageUtility.successInformation("Success", "Plese Select WarehouseType");
					return;
				}
			}
			
			warehouseVO.setOwner(txtOwner.getValue());
			if(txtContactNo.getValue()!=null && !txtContactNo.getValue().toString().equalsIgnoreCase("") ){
			warehouseVO.setContactNo(txtContactNo.getValue());
			}
			warehouseVO.setEmailId(txtEmailId.getValue());
			
			wareHouseBD.updateWarehouse(warehouseVO);
		
			MessageUtility.successInformation("Success", "Warehouse Updated Successfully");
			updatesWarehouse.detach();
			
			if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
				BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
				viewComposer.refreshView();
			}
			
		} catch (UpdateBLException e) {
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
}
