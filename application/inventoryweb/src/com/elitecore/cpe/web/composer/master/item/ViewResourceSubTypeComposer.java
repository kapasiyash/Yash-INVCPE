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
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.cpe.util.GeneralUtility;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.core.BaseViewComposer;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewResourceSubTypeComposer extends BaseCPEViewComposer{

	private static final long serialVersionUID = 1L;
	
	private LinkedList<ActionMenuItem> actionItemList;
	
	private Label lbResourceTypeName,lbResourceSubTypeName,lbResourceSubTypeDesc;
	private Label lbCreatedDate,lbCreatedBy,lbUpdateDate,lbUpdatedBy;
	
	@Override
	protected List<ActionMenuItem> getActionItemList() {
		if (actionItemList == null) {
			actionItemList = new LinkedList<ActionMenuItem>();
						
			if(isPermittedAction(ActionAlias.UPDATE_RESOURCESUBTYPE)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.UPDATE_RESOURCESUBTYPE);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}
		}
		return actionItemList;
	}

	//--added start
	public void onClick$lbResourceTypeName(Event event) {
		Map<String,Object> argMap = new HashMap<String, Object>();
		System.out.println(":::::::::IN ViewResourceSubTypeComposer ResourceTypeId:::::::::;"+lbResourceTypeName.getAttribute("ResourceTypeId"));
		System.out.println(":::::::::::lbResourceTypeName::::::::"+ lbResourceTypeName.getValue());
		addViewTab(lbResourceTypeName.getAttribute("ResourceTypeId")+"", lbResourceTypeName.getValue(), (Tabbox) arg.get(BaseViewComposer.VIEW_COMPOSER_PARENT), Pages.VIEW_RESOURCE_TYPE,argMap);
	}
	//--added end

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
		System.out.println(":::ViewResourceSubTypeComposer:::");
		fetchViewEntity();
	}

	private void fetchViewEntity(){
		System.out.println(":::ViewResourceSubTypeComposer:::");
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		SearchResourceSubTypeVO data;
		try {
			//added start
			Long id=getViewEntityId();
			if(id==0){
				id = Long.parseLong(getStrViewEntityId());
			}
			System.out.println("id:::"+id);
			//added end
			//data = itemBD.viewResourceSubTypeData(getViewEntityId());
			data = itemBD.viewResourceSubTypeData(id);
			populateData(data);
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private void populateData(SearchResourceSubTypeVO data){
		
		if(data != null){
			lbResourceSubTypeName.setValue(data.getName());
			lbResourceTypeName.setValue(data.getResourceTypeName());
			//--added start
			lbResourceTypeName.setAttribute("ResourceTypeId", data.getResourceTypeId());
			//--added end

			//	lbResourceSubTypeAlias.setValue(data.getAlias());
			lbResourceSubTypeDesc.setValue(data.getDescription());
			
			lbCreatedBy.setValue(GeneralUtility.displayValueIfNull(data.getCreatedBy()));
			
			lbCreatedDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getCreateDate()));
			if(data.getUpdatedBy()!=null && data.getUpdatedDate()!=null){
			lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(data.getUpdatedBy()));
			lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getUpdatedDate()));
			}else{
				lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(null));
				lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(null));
			}
			}
		
	}
	
}
