package com.elitecore.cpe.web.composer.master.item;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceSubTypeVO;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class UpdateResourceSubTypeComposer extends BaseModuleViewComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Hlayout updateResourceSubType;
	
	private Textbox txtName;
	private Textbox txtDesc,txtReason;
	private Combobox comboResourceType;
	
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		this.updateResourceSubType = comp;
		
		fetchViewEntity();
		
	}
	
	private void fetchViewEntity(){
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());

		List<ComboData> comboData = itemBD.getAllResourceTypeData();
		if(comboData!=null && !comboData.isEmpty()) {
			comboResourceType.setModel(new ListModelList<ComboData>(comboData));
			comboResourceType.setItemRenderer(new ComboItemDataRenderer());
		}
		SearchResourceSubTypeVO data;
		try {
			data = itemBD.viewResourceSubTypeData(getViewEntityId());
			populateData(data);
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
		
		
				
		
	}
	
	private void populateData(SearchResourceSubTypeVO data){
		
		if(data != null){
			txtName.setValue(data.getName());
			txtDesc.setValue(data.getDescription());
			comboResourceType.setValue(data.getResourceTypeName());
		}
		
	}
	
	public void onClick$btnUpdate(Event event){
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		try {
			
			UpdateResourceSubTypeVO typeVO = new UpdateResourceSubTypeVO();
			typeVO.setResourceSubTypeId(getViewEntityId());
			typeVO.setResourceSubTypeName(txtName.getValue());
			typeVO.setDescription(txtDesc.getValue());
			
			typeVO.setReason(txtReason.getValue());
			if(comboResourceType.getSelectedItem()!=null) {
				ComboData comboData = comboResourceType.getSelectedItem().getValue();
				typeVO.setResourceTypeId(comboData.getId());
			}
			
			itemBD.updateResourceSubType(typeVO);
		
			MessageUtility.successInformation("Success", "Resource Subtype Updated Successfully");
			updateResourceSubType.detach();
			
			if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
				BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
				viewComposer.refreshView();
			}
			
		} catch (UpdateBLException e) {
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
}
