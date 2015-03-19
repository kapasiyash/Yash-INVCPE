package com.elitecore.cpe.web.composer.master.attribute;

import java.util.LinkedList;
import java.util.List;

import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.delegates.master.AttributeBD;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.util.GeneralUtility;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer.ActionMenuItem;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewAttributeComposer extends BaseCPEViewComposer{

	private static final long serialVersionUID = 1L;
	
	private LinkedList<ActionMenuItem> actionItemList;
	
	private Label lbAttributeName,lbAttributeUsedBy,lbDataType,lbDataValue,lbMandatory,lbUnique;
	private Label lbCreatedDate,lbCreatedBy,lbUpdateDate,lbUpdatedBy;
//	private Window viewAttributeWin;
	
	@Override
	protected List<ActionMenuItem> getActionItemList() {
		// TODO Auto-generated method stub
		if (actionItemList == null) {
			actionItemList = new LinkedList<ActionMenuItem>();
			
//			SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.UPDATE_AR_EVENT);
			
			
			if(isPermittedAction(ActionAlias.UPDATE_ATTRIBUTE)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.UPDATE_ATTRIBUTE);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}
			
			/*actionData.setActionAlias("");
			actionData.setName("");
			actionData.setZulPageUrl("");
			actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));*/
			
		}
		return actionItemList;
	}

	@Override
	protected List<ActionMenuItem> getViewItemList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshView() {
		fetchViewEntity();
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
	//	this.viewAttributeWin = comp;
		fetchViewEntity();
		
	}

	private void fetchViewEntity(){
		
		AttributeBD attributeBD = new AttributeBD(getBDSessionContext());
		AttributeVO attributeVO = new  AttributeVO();
		attributeVO.setAttributeId(getViewEntityId());
		
		attributeVO = attributeBD.viewAttribute(attributeVO);
		
		populateData(attributeVO);
		
	}
	
	private void populateData(AttributeVO data){
		
		if(data != null){
			
			lbAttributeName.setValue(data.getName());
			lbAttributeUsedBy.setValue(data.getUsedBy());
			lbDataType.setValue(data.getDataType());
			lbDataValue.setValue(data.getDataValue());
			lbMandatory.setValue(String.valueOf(data.getMandatory()));
			lbUnique.setValue(String.valueOf(data.getUnique()));
			
			lbCreatedBy.setValue(GeneralUtility.displayValueIfNull(data.getCreatedby()));
			
			lbCreatedDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getCreatedate()));
			if(data.getUpdatedby()!=null && data.getUpdatedate()!=null){
			lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getUpdatedate()));
			lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(data.getUpdatedby()));
			}else{
				lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(null));
				lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(null));
				
			}
		}
		
	}
	
}
