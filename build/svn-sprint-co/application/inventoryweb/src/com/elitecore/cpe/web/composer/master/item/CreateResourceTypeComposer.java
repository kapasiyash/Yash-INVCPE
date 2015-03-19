package com.elitecore.cpe.web.composer.master.item;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class CreateResourceTypeComposer extends  BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Textbox txtName,txtDescription;
//	private Window createResourceType;
	
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
	//	this.createResourceType = comp;
	}
	
	
	public void onClick$btnCancel(Event event) {
		resetComponents(txtName, txtName,txtDescription);
	}
	
	public void onClick$btnCreate(Event event) {
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		try {
			itemBD.createResourceType(prepareResourceTypeVO());
			MessageUtility.successInformation("Success", "ResourceType Created Successfully");
			resetComponents(txtName, txtName,txtDescription);
		} catch (CreateBLException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("Error", e.getMessage());
		}
		
	}


	private ResourceTypeVO prepareResourceTypeVO() {
		
		ResourceTypeVO typeVO = new ResourceTypeVO();
		typeVO.setResourceTypeName(txtName.getValue());
		typeVO.setDescription(txtDescription.getValue());
		
		return typeVO;
	}
	

}
