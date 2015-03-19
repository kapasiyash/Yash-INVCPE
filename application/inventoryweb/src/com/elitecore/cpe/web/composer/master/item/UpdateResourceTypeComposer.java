package com.elitecore.cpe.web.composer.master.item;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.master.SearchResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceTypeVO;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class UpdateResourceTypeComposer extends BaseModuleViewComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Hlayout updateResourceType;
	
	private Textbox txtName;
	private Textbox txtDesc,txtReason;
	
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		this.updateResourceType = comp;
		
		fetchViewEntity();
		
	}
	
	private void fetchViewEntity(){
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		
		SearchResourceTypeVO data;
		try {
			data = itemBD.viewResourceTypeData(getViewEntityId());
			populateData(data);
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
		
		
				
		
	}
	
	private void populateData(SearchResourceTypeVO data){
		
		if(data != null){
			txtName.setValue(data.getName());
			txtDesc.setValue(data.getDescription());

		}
		
	}
	
	public void onClick$btnUpdate(Event event){
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		try {
			
			UpdateResourceTypeVO typeVO = new UpdateResourceTypeVO();
			typeVO.setResourceTypeId(getViewEntityId());
			typeVO.setResourceTypeName(txtName.getValue());
			typeVO.setDescription(txtDesc.getValue());
			
			typeVO.setReason(txtReason.getValue());
			
			itemBD.updateResourceType(typeVO);
		
			MessageUtility.successInformation("Success", "ResourceType Updated Successfully");
			updateResourceType.detach();
			
			if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
				BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
				viewComposer.refreshView();
			}
			
		} catch (UpdateBLException e) {
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
}
