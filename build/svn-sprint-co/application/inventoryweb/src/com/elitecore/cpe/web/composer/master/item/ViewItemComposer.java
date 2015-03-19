package com.elitecore.cpe.web.composer.master.item;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.vo.inventorymgt.ResourceAttributeVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.util.GeneralUtility;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.core.BaseViewComposer;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.constants.CommonConstants;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewItemComposer extends BaseCPEViewComposer{

	private static final long serialVersionUID = 1L;
	
	private LinkedList<ActionMenuItem> actionItemList;
//	private Row prefixRow;
	
	private Label lbItemName,lbModelNumber,lbVendor,lbItemDesc,lbReferenceId,lbResourceType,lbPrefix,
	lbExtReferenceId,lbResourceSubType,lbAttribute,lbInventoryNoType,prefixlb1;
	private Label lbCreatedDate,lbCreatedBy,lbUpdateDate,lbUpdatedBy;

	@Override
	protected List<ActionMenuItem> getActionItemList() {
		// TODO Auto-generated method stub
		if (actionItemList == null) {
			actionItemList = new LinkedList<ActionMenuItem>();
			
			
			if(isPermittedAction(ActionAlias.UPDATE_RESOURCE)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.UPDATE_RESOURCE);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}	
			
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
		fetchViewEntity();
		
	}
	//--added start
	public void onClick$lbResourceType(Event event) {
		Map<String,Object> argMap = new HashMap<String, Object>();
		addViewTab(lbResourceType.getAttribute("ResourceTypeId")+"", lbResourceType.getValue(), (Tabbox) arg.get(BaseViewComposer.VIEW_COMPOSER_PARENT), Pages.VIEW_RESOURCE_TYPE,argMap);
	}
	
	public void onClick$lbResourceSubType(Event event) {
		Map<String,Object> argMap = new HashMap<String, Object>();
		addViewTab(lbResourceSubType.getAttribute("ResourceSubTypeId")+"", lbResourceSubType.getValue(), (Tabbox) arg.get(BaseViewComposer.VIEW_COMPOSER_PARENT), Pages.VIEW_RESOURCE_SUBTYPE,argMap);
	}

	//--added end

	
	private void fetchViewEntity(){
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());

		ItemVO itemVo=new ItemVO();
		Long id = getViewEntityId();
		if(id==0) {
			id = Long.parseLong(getStrViewEntityId());
		}
		itemVo.setItemId(id);
		itemVo = itemBD.viewItem(itemVo);
		populateData(itemVo);
	}
	
	private void populateData(ItemVO itemVo){
		
		if(itemVo != null){
			
			lbItemName.setValue(itemVo.getName());
			lbModelNumber.setValue(itemVo.getModelnumber());
			lbVendor.setValue(itemVo.getVendor());
			lbItemDesc.setValue(itemVo.getDescription());
			lbReferenceId.setValue(itemVo.getReferenceID());
			lbResourceType.setValue(itemVo.getResourceTypeVO().getResourceTypeName());
			//--added start
			lbResourceType.setAttribute("ResourceTypeId",itemVo.getResourceTypeVO().getResourceTypeId());
			//--added end
			if(itemVo.getPrefix()!=null && !itemVo.getPrefix().isEmpty()) {
				lbPrefix.setValue(itemVo.getPrefix());
				lbInventoryNoType.setValue(CommonConstants.INVENTORY_AUTOMATIC);
				prefixlb1.setVisible(true);
				lbPrefix.setVisible(true);
			} else {
				lbInventoryNoType.setValue(CommonConstants.INVENTORY_MANUAL);
				prefixlb1.setVisible(false);
				lbPrefix.setVisible(false);
			}
			
			lbResourceSubType.setValue(itemVo.getResourceTypeVO().getResourceSubTypeVO().getResourceSubTypeName());
			//--added start
			lbResourceSubType.setAttribute("ResourceSubTypeId",itemVo.getResourceTypeVO().getResourceSubTypeVO().getResourceSubTypeId());
			//--added end
			//			lbCategory.setValue(itemVo.getResourceCategoryVO().getResourceCategoryName());
			lbExtReferenceId.setValue(itemVo.getResourceNumber());
			lbAttribute.setValue(prepareCommaSeparatedValues(itemVo.getAttributeVO()));
			lbCreatedBy.setValue(GeneralUtility.displayValueIfNull(itemVo.getCreatedby()));
			
			lbCreatedDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(itemVo.getCreatedate()));
			if(itemVo.getUpdatedby()!=null && itemVo.getUpdatedate()!=null){
			lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(itemVo.getUpdatedate()));
			lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(itemVo.getUpdatedby()));
			}else{
				lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(null));
				lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(null));
				
			}
			
		}
		
	}
	
	private String prepareCommaSeparatedValues(List<ResourceAttributeVO> attributes) {
		String retString ="";
		if(attributes!=null && !attributes.isEmpty()){
			StringBuilder builder = new StringBuilder();
			for(ResourceAttributeVO segment:attributes){
				builder.append(" "+segment.getAttributeName() + ",");
			}
			retString = builder.toString().substring(0, builder.toString().lastIndexOf(","));
		}
		return retString;
	}
	
	
}
