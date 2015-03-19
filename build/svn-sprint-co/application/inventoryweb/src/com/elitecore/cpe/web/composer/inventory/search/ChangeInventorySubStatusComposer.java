package com.elitecore.cpe.web.composer.inventory.search;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.ChangeInventoryStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.ChangeInventorySubStatusVO;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.composer.inventory.search.ViewInventoryComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class ChangeInventorySubStatusComposer extends BaseModuleViewComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Hlayout changeInventorySubStatus;
	
	private Textbox txtRemarks;
	private Combobox comboStatus;
	
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		this.changeInventorySubStatus = comp;
		
		fetchViewEntity();
		
	}
	
	private void fetchViewEntity(){
		
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		
		try {
			
			System.out.println(getViewEntityId());
			System.out.println(getStrViewEntityId());
			
			List<ComboBoxData> comboDatas =  inventoryManagementBD.getAllowedSubStatus(getStrViewEntityId());
			if(comboDatas!=null && !comboDatas.isEmpty()) {
				comboStatus.setModel(new ListModelList<ComboBoxData>(comboDatas));
				comboStatus.setItemRenderer(new ComboBoxItemDataRenderer());
			}
			
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
				
		
	}
	
	public void onClick$btnUpdate(Event event){
		
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		try {
			
			ChangeInventorySubStatusVO statusVO = new ChangeInventorySubStatusVO();
			statusVO.setInventoryNo(getStrViewEntityId());
			statusVO.setReason(txtRemarks.getValue());
			
			if(comboStatus.getSelectedItem()!=null) {
				ComboBoxData comboData = comboStatus.getSelectedItem().getValue();
				statusVO.setSubStatus(comboData.getId());
				statusVO.setSubstatusId(Long.parseLong(comboData.getId()));
				statusVO.setStatus(comboData.getName());
			}
			inventoryManagementBD.changeInventorySubStatus(statusVO);
			MessageUtility.successInformation("Success", "SubStatus Updated Successfully");
			changeInventorySubStatus.detach();
			
			if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
				BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
				ViewInventoryComposer composer = (ViewInventoryComposer) viewComposer;
//				composer.setStatus(statusVO.getStatusName());
				composer.setUpdatedDate(new Timestamp(new Date().getTime()));
				composer.setUpdatedBy(getBDSessionContext().getBLSession().getName());
				composer.refreshViewLatest();
			}
			
		} catch (UpdateBLException e) {
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
}
