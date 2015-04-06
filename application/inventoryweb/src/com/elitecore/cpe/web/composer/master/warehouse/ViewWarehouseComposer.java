package com.elitecore.cpe.web.composer.master.warehouse;

import java.util.LinkedList;
import java.util.List;

import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.vo.master.ConfigureThresholdVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.util.GeneralUtility;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer.ActionMenuItem;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewWarehouseComposer extends BaseCPEViewComposer{

	private static final long serialVersionUID = 1L;
	
	private LinkedList<ActionMenuItem> actionItemList;
	String isEditable="";
	
	private Label lbWarehouseName,lbWarehouseCode,lbWarehouselocation,lbWarehouseDesc,lbParentWHName,lbWarehouseType,lbOwner,lbContactno,lbEmailid;
	private Label lbCreatedDate,lbCreatedBy,lbUpdateDate,lbUpdatedBy,lblMessage;
	private Listbox viewThresholdGrid;
//	int count=0;
	@Override
	protected List<ActionMenuItem> getActionItemList() {
		if (actionItemList == null) {
			actionItemList = new LinkedList<ActionMenuItem>();
			
	
//			if(!isEditable.equalsIgnoreCase("N")){
			
				if(isPermittedAction(ActionAlias.UPDATE_WAREHOUSE)) {
					SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.UPDATE_WAREHOUSE);
					actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
				}	
				
				
//			}
			
			if(isPermittedAction(ActionAlias.CONFIGURE_THRESHOLD)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.CONFIGURE_THRESHOLD);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}
			
			if(!isEditable.equalsIgnoreCase("N")){
				if(isPermittedAction(ActionAlias.DELETE_WAREHOUSE)) {
					SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.DELETE_WAREHOUSE);
					actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
				}
				
			}
			
			
			if(isPermittedAction(ActionAlias.TRANSFER_INVENTORY_SUMMARY)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.TRANSFER_INVENTORY_SUMMARY);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}
			
			
			//Added For Place Order	
			if(!isEditable.equalsIgnoreCase("N")){
				if(isPermittedAction(ActionAlias.CREATE_PLACE_ORDER)) {
					SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.CREATE_PLACE_ORDER);
					actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
				}
			}

			
			
			//Adding for search Place order
			if(isPermittedAction(ActionAlias.SEARCH_PLACE_ORDER)) {
				SystemActionData actionData1 = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.SEARCH_PLACE_ORDER);
				actionItemList.add(new ActionMenuItem(actionData1.getActionAlias(), actionData1.getName(), actionData1.getZulPageUrl(),MENU_ITEM));
			}

			
			
			//Adding View Hierarchy 
			if(isPermittedAction(ActionAlias.VIEW_WAREHOUSE_HIERARCHY)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.VIEW_WAREHOUSE_HIERARCHY);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}
			
			//Adding Delete Thresholds
			if(isPermittedAction(ActionAlias.DELETE_THRESHOLDS)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.DELETE_THRESHOLDS);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}

			
		}
		Logger.logTrace("WAREHOUSE", "Inside getActionItemList [JM]");
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
		viewThresholdGrid.setWidth("798px");
		
	}

	private void fetchViewEntity(){
		
		WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
		WarehouseVO warehouseVO = new WarehouseVO();
		//--added start
		Long id = getViewEntityId();
		if(id==0){
			id =Long.parseLong(getStrViewEntityId());
		}
		//warehouseVO.setWarehouseId(getViewEntityId());
		warehouseVO.setWarehouseId(id);
		System.out.println("::getViewEntityId()::::::::"+getViewEntityId());
		System.out.println("::getStrViewEntityId()::"+getStrViewEntityId());
		//--added end
		WarehouseVO data = wareHouseBD.viewWarehouse(warehouseVO);
		System.out.println("data in ViewWareHouseComposer:::::::::"+data.toString());
		isEditable=data.getEditable();
		if(!isEditable.equalsIgnoreCase("Y")){
				lblMessage.setVisible(true);
			}
		//--added end
		ConfigureThresholdVO configureThresholdVO = new ConfigureThresholdVO();
		configureThresholdVO.setWarehouseID(getViewEntityId());
		List<ConfigureThresholdVO> listConfigureThresholdVOs = wareHouseBD.searchThresholdData(configureThresholdVO);
		if (listConfigureThresholdVOs != null && !listConfigureThresholdVOs.isEmpty()) {
		//	count=listConfigureThresholdVOs.size();
			viewThresholdGrid.setVisible(true);
			viewThresholdGrid.setModel(new ListModelList<ConfigureThresholdVO>(listConfigureThresholdVOs));
			viewThresholdGrid.setItemRenderer(new SearchListItemRenderer());
			
		} else {
			viewThresholdGrid.setEmptyMessage("No Threashold Detail Configured");
			viewThresholdGrid.setModel(new ListModelList<ConfigureThresholdVO>());
			viewThresholdGrid.setVisible(true);
		}
		populateData(data);
		
	}
	
	private void populateData(WarehouseVO data){
		
		if(data != null){
		//	SimpleDateFormat dateFormat = new SimpleDateFormat(getDateFormat());
			lbWarehouseName.setValue(data.getName());
			lbParentWHName.setValue(GeneralUtility.displayValueIfNull(data.getParentWarehouseName()));
			//lbWarehouseAlias.setValue(data.getAlias());
			lbWarehouselocation.setValue(data.getLocation());
			lbWarehouseCode.setValue(data.getWarehouseCode());
			lbWarehouseDesc.setValue(data.getDescription());
			lbOwner.setValue(data.getOwner());
			lbContactno.setValue(data.getContactNo());
			lbEmailid.setValue(data.getEmailId());
			lbCreatedBy.setValue(GeneralUtility.displayValueIfNull(data.getCreatedby()));
			
			lbCreatedDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getCreateDate()));
			if(data.getUpdatedby()!=null && data.getUpdatedDate()!=null){
			lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getUpdatedDate()));
			lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(data.getUpdatedby()));
			}else{
				lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(null));
				lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(null));
				
			}
			if(data.getWarehouseType() != null){
				lbWarehouseType.setValue(data.getWarehouseType().getName());
			}
		}
		
	}
	
	private static class SearchListItemRenderer implements ListitemRenderer<ConfigureThresholdVO>{

		
		
		public SearchListItemRenderer() {
			
				}
		
		@Override
		public void render(Listitem item, ConfigureThresholdVO data, int index)throws Exception {
			
			Logger.logTrace("WAREHOUSE", "Data received:"+data);
			
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			
			item.appendChild(new Listcell(data.getResourceTypeName()));
			
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourceSubTypeName())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourceName())));
			item.appendChild(new Listcell(data.getThresholdType()));
			item.appendChild(new Listcell(String.valueOf(data.getThresholdValue())));
			item.appendChild(new Listcell(data.getAutomaticOrder()));
			if(data.getQuantity()!=null) {
				item.appendChild(new Listcell(String.valueOf(data.getQuantity())));
			} else {
				item.appendChild(new Listcell(""));
			}
			System.out.println("data.getEmail()"+data.getEmail()+"::data.getMobile()::"+data.getMobile());
			item.appendChild(new Listcell(data.getEmail()));
			item.appendChild(new Listcell(data.getMobile()));
		}
		
	}
	
}
