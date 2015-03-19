/**
 * 
 */
package com.elitecore.cpe.web.composer.inventory.search;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryStatusLogVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.util.GeneralUtility;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.core.BaseViewComposer;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

/**
 * @author Joel.Macwan
 *
 */
public class ViewInventoryComposer extends BaseCPEViewComposer{

	private Label lbCreatedDate,lbCreatedBy,lbUpdateDate,lbUpdatedBy,lbInventoryNumber,lbBatchNumber,lbStatus,lbWarehouseName,lbResourceType,lbResourceSubType,lbSubStatus;
	private Label lbTransferStatusName,lbExternalBatchNumber/*lbAcceptedSubStatus,lbRefurbishedSubstatus,lbStandbySubStatus,lbNewSubstatus,*/;
	private Rows viewAttributerows;
	private Row rowTable;

	private Label lbResourceNumber,lbResourceName;
	private Label lbGuaranteeWarrantymode,lbWarrantydate,lbWarrantytype;
	
//	private Row statusRow,viewHistoryrows;
//	private Listbox searchResultGrid;

	private LinkedList<ActionMenuItem> actionItemList;
	private String status,updatedBy;
	private Timestamp updatedDate;
	private InventoryDetailVO inventoryDetailVO = null;
	
	@Override
	protected List<ActionMenuItem> getActionItemList() {
		if(!(arg.get("transfer")!=null)){
		if (actionItemList == null) {
			actionItemList = new LinkedList<ActionMenuItem>();
			
			if(inventoryDetailVO!=null ) {
				
				if("WAITING".equals(inventoryDetailVO.getTransferStatus()) || "REJECTED".equals(inventoryDetailVO.getTransferStatus())) {
					Logger.logTrace("INVENTORY", "Not Valid Status");
				} else {
					if(isPermittedAction(ActionAlias.CHANGE_INVENTORY_STATUS)) {
						SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.CHANGE_INVENTORY_STATUS);
						actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
					}
					
					if(isPermittedAction(ActionAlias.CHANGE_INVENTORY_SUBSTATUS)) {
						SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.CHANGE_INVENTORY_SUBSTATUS);
						actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
					}
				}
				
				
			}
			
			/**/
			
			if(isPermittedAction(ActionAlias.INVENTORY_HISTORY_DETAIL)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.INVENTORY_HISTORY_DETAIL);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}
		}
		}
		return actionItemList;
	}

	/* 
	 * 
	 */
	@Override
	protected List<ActionMenuItem> getViewItemList() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void onClick$lbResourceNumber(Event event) {
		Map<String,Object> argMap = new HashMap<String, Object>();
		addViewTab(lbResourceNumber.getAttribute("RESOURCEID")+"", lbResourceNumber.getValue(), (Tabbox) arg.get(BaseViewComposer.VIEW_COMPOSER_PARENT), Pages.VIEW_ITEM_EVENT,argMap);
	}
	//--added start
	public void onClick$lbWarehouseName(Event event) {
		Map<String,Object> argMap = new HashMap<String, Object>();
		addViewTab(lbWarehouseName.getAttribute("wareHouseId")+"", lbWarehouseName.getValue(), (Tabbox) arg.get(BaseViewComposer.VIEW_COMPOSER_PARENT), Pages.VIEW_WAREHOUSE_EVENT,argMap);

	}
	//--added end
	
	/* 
	 * 
	 */
	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		fetchViewEntity();
		SearchInventoryVO data = new SearchInventoryVO();
		data.setInventoryId(getStrViewEntityId());
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		List<InventoryDetailVO> listInventoryDetailData = inventoryManagementBD.searchInventoryDetailData(data);
		if(listInventoryDetailData != null && !listInventoryDetailData.isEmpty()){
			populateData(listInventoryDetailData.get(0));
		}
	}

	public void refreshViewLatest() { // TODO Auto-generated method stub
		
		
		SearchInventoryVO data1 = new SearchInventoryVO();
		data1.setInventoryId(getStrViewEntityId());
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		List<InventoryDetailVO> listInventoryDetailData = inventoryManagementBD.searchInventoryDetailData(data1);
		if(listInventoryDetailData != null && !listInventoryDetailData.isEmpty()){
			
			InventoryDetailVO data = listInventoryDetailData.get(0);
			
			lbWarehouseName.setValue(data.getWareHouseName());
			lbInventoryNumber.setValue(data.getInventoryId());
			lbBatchNumber.setValue(data.getBatchId());
			lbStatus.setValue(data.getStatus());
			lbResourceType.setValue(data.getResourceType());
			lbResourceSubType.setValue(data.getResourceSubType());
			lbCreatedBy.setValue(GeneralUtility.displayValueIfNull(data.getCreatedby()));
			lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(data.getUpdatedby()));
			lbCreatedDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getCreateDate()));
			lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getUpdatedDate()));
/*			lbAcceptedSubStatus.setValue(String.valueOf(data.getAccepted()));
			lbRefurbishedSubstatus.setValue(String.valueOf(data.getRefurbished()));
			lbStandbySubStatus.setValue(String.valueOf(data.getStandby()));
			lbNewSubstatus.setValue(String.valueOf(data.getNewSubStatus()));
*/			lbExternalBatchNumber.setValue(GeneralUtility.displayValueIfNull(data.getExternalBatchNumber()));
			lbResourceNumber.setValue(GeneralUtility.displayValueIfNull(data.getResourceNumber()));
			lbResourceName.setValue(GeneralUtility.displayValueIfNull(data.getResourceName()));
			lbResourceNumber.setAttribute("RESOURCEID", data.getResourceNo());
			//--added start
			lbWarehouseName.setAttribute("wareHouseId",data.getWarehouseId());
			//--added end
			//Added By Rinkal --Start
			lbSubStatus.setValue(GeneralUtility.displayValueIfNull(data.getSubStatus()));
			//Added By Rinkal --End

			if(inventoryDetailVO!=null) {
				String transferStatus = inventoryDetailVO.getTransferStatus();
				if(transferStatus==null){
					lbTransferStatusName.setValue(GeneralUtility.displayValueIfNull(null));
				}
				else if(transferStatus.equalsIgnoreCase("WAITING")){
					lbTransferStatusName.setValue("Inprocess");
				}else if(transferStatus.equalsIgnoreCase("REJECTED")){
					lbTransferStatusName.setValue("Rejected");
				}
			}
		}
	}
	
	
	/* 
	 * 
	 */
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		// TODO Auto-generated method stub
		 fetchViewEntity();
	}
private void fetchViewEntity(){
	InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
	SearchInventoryVO data = new SearchInventoryVO();
	
		inventoryDetailVO=(InventoryDetailVO)arg.get("InventoryDetailVO");
		data.setInventoryId(inventoryDetailVO.getInventoryId());
		System.out.println(":: "+data);
		InventoryDetailVO listInventoryDetailData = inventoryManagementBD.searchInventoryDetailDataById(data.getInventoryId());
		System.out.println("list data :: "+listInventoryDetailData);
		if(status!=null && !status.isEmpty()) {
			inventoryDetailVO.setStatus(getStatus());
			inventoryDetailVO.setUpdatedDate(getUpdatedDate());
			inventoryDetailVO.setUpdatedby(getUpdatedBy());
		}
		if(listInventoryDetailData != null ){
			populateData(listInventoryDetailData);
		}
	//	populateData(inventoryDetailVO);
		
	}
	
	private void populateData(InventoryDetailVO data){
		Logger.logTrace("Inventory", "Data received:::::JM-->"+data);
		if(data != null){
			//SimpleDateFormat dateFormat = new SimpleDateFormat(getDateFormat());
			lbWarehouseName.setValue(data.getWareHouseName());
			lbInventoryNumber.setValue(data.getInventoryId());
			lbBatchNumber.setValue(data.getBatchId());
			lbStatus.setValue(data.getStatus());
			lbResourceType.setValue(data.getResourceType());
			lbResourceSubType.setValue(data.getResourceSubType());
			lbCreatedBy.setValue(GeneralUtility.displayValueIfNull(data.getCreatedby()));
			lbUpdatedBy.setValue(GeneralUtility.displayValueIfNull(data.getUpdatedby()));
			lbCreatedDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getCreateDate()));
			lbUpdateDate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getUpdatedDate()));
/*			lbAcceptedSubStatus.setValue(String.valueOf(data.getAccepted()));
			lbRefurbishedSubstatus.setValue(String.valueOf(data.getRefurbished()));
			lbStandbySubStatus.setValue(String.valueOf(data.getStandby()));
			lbNewSubstatus.setValue(String.valueOf(data.getNewSubStatus()));
*/			lbExternalBatchNumber.setValue(GeneralUtility.displayValueIfNull(data.getExternalBatchNumber()));
			
			lbWarrantytype.setValue(GeneralUtility.displayValueIfNull(data.getWarrantyType()));
			lbGuaranteeWarrantymode.setValue(GeneralUtility.displayValueIfNull(data.getGurrantyWarrantyMode()));
			lbWarrantydate.setValue(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getWarrantyDate()));
			
			lbResourceNumber.setValue(GeneralUtility.displayValueIfNull(data.getResourceNumber()));
			lbResourceName.setValue(GeneralUtility.displayValueIfNull(data.getResourceName()));
			lbResourceNumber.setAttribute("RESOURCEID", data.getResourceNo());
			//--added start
			lbWarehouseName.setAttribute("wareHouseId",data.getWarehouseId());
			//--added end
			//Added By Rinkal --Start
			System.out.println("::::::data.getSubStatus()::::"+data.getSubStatus());
			lbSubStatus.setValue(GeneralUtility.displayValueIfNull(data.getSubStatus()));
			//Added By Rinkal --End
			if(inventoryDetailVO!=null) {
				String transferStatus = inventoryDetailVO.getTransferStatus();
				if(transferStatus==null){
					lbTransferStatusName.setValue(GeneralUtility.displayValueIfNull(null));
				}
				else if(transferStatus.equalsIgnoreCase("WAITING")){
					lbTransferStatusName.setValue("Inprocess");
				}else if(transferStatus.equalsIgnoreCase("REJECTED")){
					lbTransferStatusName.setValue("Rejected");
				}
			}
			
			if(data.getAttribute()!= null){
				Map<String,String> map=data.getAttribute();
				boolean flag=(map.size()%2==0);
				 Iterator it = data.getAttribute().entrySet().iterator();
				 int count=0;
				    while (it.hasNext()) {
				    	if(count==0){
				    	rowTable = new Row();
				    	
				    	count=2;
				    	}
				        Map.Entry<String,String> entry = (Map.Entry<String,String>)it.next();
				        Label attributelabel = new Label(entry.getKey());
				       // attributelabel.setStyle("font-weight:bold;");
				        rowTable.appendChild(attributelabel);
				        rowTable.appendChild(new Label(entry.getValue()));
				      //  rowTable.appendChild(new Label());
				     //   rowTable.appendChild(new Label());
				        viewAttributerows.appendChild(rowTable);
				        count--;
				    }
				    if(!flag){
				    	rowTable.appendChild(new Label());
				        rowTable.appendChild(new Label());
				        viewAttributerows.appendChild(rowTable);
				    }
				  
			}
		}
		
	}
	
	
	
	

	/*
	private void populateHistoryData(InventoryDetailVO data){
		Logger.logTrace("Inventory", "Inside populateHistoryData:"+data.getInventoryId());
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		List<InventoryStatusLogVO> inventoryStatusLogVOs= inventoryManagementBD.viewInventoryHistoryData(data.getInventoryId());
		searchResultGrid.setVisible(true);
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		if(inventoryStatusLogVOs!=null && !inventoryStatusLogVOs.isEmpty()){
			searchResultGrid.setModel(new ListModelList<InventoryStatusLogVO>(inventoryStatusLogVOs));
			searchResultGrid.setItemRenderer(new SearchListItemRenderer());
			
		}else{
			searchResultGrid.setModel(new ListModelList<InventoryStatusLogVO>());
		}
	}*/


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	
private static class SearchListItemRenderer implements ListitemRenderer<InventoryStatusLogVO>{

		
		
		public SearchListItemRenderer() {
			
				}
		
		@Override
		public void render(Listitem item, InventoryStatusLogVO data, int index)throws Exception {
			
			Logger.logTrace("Inventory", "Data received:"+data);
			
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			
			item.appendChild(new Listcell(data.getOldStatus()));
			item.appendChild(new Listcell(data.getNewStatus()));
			item.appendChild(new Listcell(data.getChangedby()));
			
			item.appendChild(new Listcell(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getChangeDate())));
			item.appendChild(new Listcell(data.getRemarks()));
			
			

			
		}
		
	}

}
