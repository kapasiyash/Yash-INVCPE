package com.elitecore.cpe.web.composer.master.item;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.web.base.ui.core.BaseComposer.ComboItemDataRenderer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class CreateResourceSubTypeComposer extends  BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Textbox txtName,txtDescription;
	private Combobox comboResourceType;
//	private Window createResourceSubType;
	
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
	//	this.createResourceSubType = comp;
		
		ItemBD itemBD = new ItemBD(getBDSessionContext()); 
		List<ComboData> comboData = itemBD.getAllResourceTypeData();
		if(comboData!=null && !comboData.isEmpty()) {
			comboResourceType.setModel(new ListModelList<ComboData>(comboData));
			comboResourceType.setItemRenderer(new ComboItemDataRenderer());
		}
		
	}
	
	
	public void onClick$btnCancel(Event event){
		resetComponents(txtName, txtName,txtDescription,comboResourceType);
	}
	
	
	public void onClick$btnCreate(Event event) {
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		try {
			itemBD.createResourceSubType(prepareResourceTypeVO());
			MessageUtility.successInformation("Success", "Resource Subtype Created Successfully");
			resetComponents(txtName, txtName,txtDescription,comboResourceType);
		} catch (CreateBLException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("Error", e.getMessage());
		}
		
	}


	private ResourceSubTypeVO prepareResourceTypeVO() {
		
		ResourceSubTypeVO typeVO = new ResourceSubTypeVO();
		typeVO.setResourceSubTypeName(txtName.getValue());
		typeVO.setDescription(txtDescription.getValue());
		if(comboResourceType.getSelectedItem()!=null) {
			ComboData comboData = comboResourceType.getSelectedItem().getValue();
			typeVO.setResourceTypeId(comboData.getId());
		}
		
		return typeVO;
	}
	

}
