package com.elitecore.cpe.web.composer.master.item;

import java.util.LinkedList;
import java.util.List;

import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.master.SearchResourceTypeVO;
import com.elitecore.cpe.util.GeneralUtility;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewResourceTypeComposer extends BaseCPEViewComposer{

	private static final long serialVersionUID = 1L;
	
	private LinkedList<ActionMenuItem> actionItemList;
	
	private Label lbResourceTypeName,lbResourceTypeDesc;
	private Label lbCreatedDate,lbCreatedBy,lbUpdateDate,lbUpdatedBy;
	
	@Override
	protected List<ActionMenuItem> getActionItemList() {
		if (actionItemList == null) {
			actionItemList = new LinkedList<ActionMenuItem>();
						
			if(isPermittedAction(ActionAlias.UPDATE_RESOURCETYPE)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.UPDATE_RESOURCETYPE);
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

	private void fetchViewEntity(){
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		SearchResourceTypeVO data;
		try {
			//--added start 
			Long id=getViewEntityId();
			if(id==0){
				id=Long.parseLong(getStrViewEntityId());
			}
			System.out.println("id:::"+id);
			//--added end
			//data = itemBD.viewResourceTypeData(getViewEntityId());
			data = itemBD.viewResourceTypeData(id);

			populateData(data);
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private void populateData(SearchResourceTypeVO data){
		
		if(data != null){
			lbResourceTypeName.setValue(data.getName());
		//	lbResourceTypeAlias.setValue(data.getAlias());
			lbResourceTypeDesc.setValue(data.getDescription());
			
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
