package com.elitecore.cpe.web.composer.master.warehouse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class CreateWarehouseComposer extends BaseModuleComposer {

private static final long serialVersionUID = 1L;
	
//	private Window createWarehouse;
	private Textbox txtName;
	private Textbox txtWareHouseCodeName;
	private Textbox txtLocation;
	private Textbox txtDesc,txtContactNo,txtEmailId,txtOwner;
	private Combobox cmbParentWHname,cmbWHTypename;
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		System.out.println("in SearchWareHouse composer afterCompose");
	//	this.createWarehouse = comp;
		
		init();
	}
	
	public void onClick$btnCancel(Event event){
		reset();
	}

	private void init(){
		
		IBDSessionContext sessionContext = getBDSessionContext();
		WareHouseBD  wareHouseBD = new WareHouseBD(sessionContext);
		List<ComboData> comboBoxDatas = wareHouseBD.getAllWarehouseData();
		
		cmbParentWHname.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbParentWHname.setItemRenderer(new ComboItemDataRenderer());
		
		List<ComboData> comboBoxDatas1 = wareHouseBD.getAllWarehouseTypeData();
		if(comboBoxDatas1!=null && !comboBoxDatas1.isEmpty()) {
			List<ComboData> comboDatas = filterData(comboBoxDatas1);
			cmbWHTypename.setModel(new ListModelList<ComboData>(comboDatas));
			cmbWHTypename.setItemRenderer(new ComboItemDataRenderer());
		}
		//added start 
		txtName.addEventListener(Events.ON_BLUR, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("::txtName.getValue()"+txtName.getValue());
				if(txtName.getValue()!=null){
					System.out.println("::txtName.getValue()"+txtName.getValue());
					String whCode = txtName.getValue().toUpperCase();
					whCode = whCode.replace(" ", "_");
					txtWareHouseCodeName.setValue(whCode);
				}
			}
		});
		//added end
		
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

	public void onClick$btnCreate(Event event) {
		System.out.println("in onClick$btnCreate");
		
		try {
			String alias = txtName.getValue().toUpperCase();
			alias = alias.replace(" ", "_");
			
			IBDSessionContext sessionContext = getBDSessionContext();
			
			System.out.println("staff :"+sessionContext.getBLSession().getSessionUserId());
			
			
			WareHouseBD  wareHouseBD = new WareHouseBD(sessionContext);
			
			WarehouseVO warehouseVO = new WarehouseVO();
			warehouseVO.setName(txtName.getValue());
			
			if(txtWareHouseCodeName.getValue()!=null && !txtWareHouseCodeName.getValue().isEmpty()) {
				warehouseVO.setWarehouseCode(txtWareHouseCodeName.getValue());
			} else {
				MessageUtility.failureInformation( "Error", "Plese Enter warehouse code");
				return;
			}
			
			warehouseVO.setDescription(txtDesc.getValue());
			warehouseVO.setLocation(txtLocation.getValue());
			warehouseVO.setAlias(alias);
			
			warehouseVO.setCreatedby(sessionContext.getBLSession().getSessionUserId());
			warehouseVO.setUpdatedby(sessionContext.getBLSession().getSessionUserId());
			warehouseVO.setCreateDate(new Timestamp(new Date().getTime()));
			
			warehouseVO.setOwner(txtOwner.getValue());
			warehouseVO.setContactNo(txtContactNo.getValue());
			warehouseVO.setEmailId(txtEmailId.getValue());
			
			if(cmbParentWHname.getSelectedItem() != null){
				ComboData selectedData = cmbParentWHname.getSelectedItem().getValue();
				warehouseVO.setParentWarehouseId(selectedData.getId());
			}else{
				MessageUtility.successInformation("Success", "Plese Select Parent Warehouse");
				return;
			}
			
			if(cmbWHTypename.getSelectedItem() != null){
				ComboData selectedData = cmbWHTypename.getSelectedItem().getValue();
				WarehouseTypeVO warehouseTypeVO = new WarehouseTypeVO();
				warehouseTypeVO.setWarehouseTypeId(selectedData.getId());
				warehouseVO.setWarehouseType(warehouseTypeVO);
			}else{
				MessageUtility.successInformation("Success", "Plese Select WarehouseType");
				return;
			}
			
			wareHouseBD.saveWarehouse(warehouseVO);
			
			MessageUtility.successInformation("Success", "Warehouse Created Successfully");
			reset();
		} catch (CreateBLException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
	
	private void reset(){
		
		resetComponents(txtName, txtName,txtWareHouseCodeName,txtLocation,txtDesc,cmbParentWHname,cmbWHTypename,txtContactNo,txtEmailId,txtOwner);
	}
}
