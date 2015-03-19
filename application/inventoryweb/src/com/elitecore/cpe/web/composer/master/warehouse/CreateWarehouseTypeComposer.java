package com.elitecore.cpe.web.composer.master.warehouse;

import java.sql.Timestamp;
import java.util.Date;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class CreateWarehouseTypeComposer extends BaseModuleComposer {

private static final long serialVersionUID = 1L;
	
//	private Window createWarehouseType;
	private Textbox txtName;
	private Textbox txtDesc;
	
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		System.out.println("in SearchWareHouse composer afterCompose");
	//	this.createWarehouseType = comp;
		

	}
	
	public void onClick$btnCancel(Event event){
		reset();
	}

	public void onClick$btnCreate(Event event) {
		System.out.println("in onClick$btnCreate");
		
		try {
			String alias = txtName.getValue().toUpperCase();
			alias = alias.replace(" ", "_");
			
			IBDSessionContext sessionContext = getBDSessionContext();
			
			System.out.println("staff :"+sessionContext.getBLSession().getSessionUserId());
			
			
			WareHouseBD  wareHouseBD = new WareHouseBD(sessionContext);
			
			WarehouseTypeVO warehouseTypeVO = new WarehouseTypeVO();
			warehouseTypeVO.setName(txtName.getValue());
			warehouseTypeVO.setDescription(txtDesc.getValue());
			warehouseTypeVO.setAlias(alias);
			warehouseTypeVO.setCreatedby(sessionContext.getBLSession().getSessionUserId());
			warehouseTypeVO.setUpdatedby(sessionContext.getBLSession().getSessionUserId());
			warehouseTypeVO.setCreateDate(new Timestamp(new Date().getTime()));
			wareHouseBD.saveWarehouseType(warehouseTypeVO);
			
			MessageUtility.successInformation("Success", "WarehouseType Created Successfully");
			reset();
		} catch (CreateBLException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
	
	private void reset(){
		
		resetComponents(txtName, txtName,txtDesc);
	}
}
