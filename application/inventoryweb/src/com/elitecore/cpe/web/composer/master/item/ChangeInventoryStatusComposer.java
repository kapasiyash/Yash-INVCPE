package com.elitecore.cpe.web.composer.master.item;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.ChangeInventoryStatusVO;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.composer.inventory.search.ViewInventoryComposer;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class ChangeInventoryStatusComposer extends BaseModuleViewComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Hlayout changeInventoryStatus;
	
	private Textbox txtRemarks;
	private Combobox comboStatus;
	
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		this.changeInventoryStatus = comp;
		
		fetchViewEntity();
		
	}
	
	private void fetchViewEntity(){
		
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		
		try {
			
			System.out.println(getViewEntityId());
			System.out.println(getStrViewEntityId());
			
			List<ComboData> comboDatas =  inventoryManagementBD.getAllowedStatus(getStrViewEntityId());
			if(comboDatas!=null && !comboDatas.isEmpty()) {
				comboStatus.setModel(new ListModelList<ComboData>(comboDatas));
				comboStatus.setItemRenderer(new ComboItemDataRenderer());
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
			
			ChangeInventoryStatusVO statusVO = new ChangeInventoryStatusVO();
			statusVO.setInventoryNo(getStrViewEntityId());
			statusVO.setRemarks(txtRemarks.getValue());
			
			if(comboStatus.getSelectedItem()!=null) {
				ComboData comboData = comboStatus.getSelectedItem().getValue();
				statusVO.setStatusId(comboData.getId());
				statusVO.setStatusName(comboData.getName());
			}
			
			inventoryManagementBD.changeInventoryStatus(statusVO);
			MessageUtility.successInformation("Success", "Status Updated Successfully");
			changeInventoryStatus.detach();
			
			if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
				BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
				ViewInventoryComposer composer = (ViewInventoryComposer) viewComposer;
				composer.setStatus(statusVO.getStatusName());
				composer.setUpdatedDate(new Timestamp(new Date().getTime()));
				composer.setUpdatedBy(getBDSessionContext().getBLSession().getName());
				composer.refreshViewLatest();
			}
			
		} catch (UpdateBLException e) {
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
}
