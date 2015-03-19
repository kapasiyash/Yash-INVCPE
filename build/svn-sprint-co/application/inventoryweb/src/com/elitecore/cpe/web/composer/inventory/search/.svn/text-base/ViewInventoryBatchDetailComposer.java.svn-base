/**
 * 
 */
package com.elitecore.cpe.web.composer.inventory.search;

import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.master.AttributeBD;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.constants.CommonConstants;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;

/**
 * @author Joel.Macwan
 *
 */
public class ViewInventoryBatchDetailComposer extends BaseSearchComposer{



	/*
	 * 
	 * 
	 * @see com.elitecore.cpe.web.base.ui.core.BaseSearchComposer#
	 * onDoubleClickedSearchItem(org.zkoss.zk.ui.event.Event)
	 */
	private static final long serialVersionUID = 1L;
	public static final String VIEW_ENTITY_ID_KEY = "_viewEntityId";
//	private Window viewInventoryBatchDetail;
	
//	private Textbox txtBatchId;
	

	private Listbox searchResultGrid;
	List<AttributeVO> attributeDatas;

	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		// TODO Auto-generated method stub

	}

		
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		// TODO Auto-generated method stub
		System.out.println("in SearchInventoryComposer  afterComposer");
	//	this.viewInventoryBatchDetail = comp;
		
		searchResultGrid.setVisible(false);
		init();
		// /////////////
		
	}

	private void init() {

		IBDSessionContext sessionContext = getBDSessionContext();
		
		AttributeBD attributeBD = new AttributeBD(sessionContext);
	//	InventoryManagementBD inventoryBD = new InventoryManagementBD(sessionContext);
		
		attributeDatas = attributeBD.getAttributesByRef(CommonConstants.RESOURCE);
		

		Listhead lhd = new Listhead();
		Listheader lh1 = new Listheader();
		Listheader lh2 = new Listheader();
		Listheader lh3 = new Listheader();
		Listheader lh4 = new Listheader();
		Listheader lh5 = new Listheader();
		Listheader lh6 = new Listheader();
		Listheader lh7 = new Listheader();
		lh1.setLabel("Sr.No");

		lh2.setLabel("CPE Batch");
		lh3.setLabel("Inventory ID");
		lh4.setLabel("Status");
		lh5.setLabel("Distributor Number");
		lh6.setLabel("Reference Number");
		lh7.setLabel("Order Number");
		lh1.setParent(lhd);
		lh2.setParent(lhd);
		lh3.setParent(lhd);
		lh4.setParent(lhd);
		lh5.setParent(lhd);
		lh6.setParent(lhd);
		lh7.setParent(lhd);

		// lh5.setParent(lhd);

		// /////////////////////////////
		for (AttributeVO attributeVO : attributeDatas) {
			Listheader lh = new Listheader();
			lh.setLabel(attributeVO.getName());
			lh.setParent(lhd);
		}
		lhd.setSizable(true);
		lhd.setParent(searchResultGrid);
		populateData();
	}

	public void populateData() {

		Logger.logTrace("INVENTORY", "Inside populateData() ");
		SearchInventoryVO data = new SearchInventoryVO();
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		String batchnumber="";

		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		if (arg.containsKey(VIEW_ENTITY_ID_KEY)) {
			if(arg.get(VIEW_ENTITY_ID_KEY) instanceof String)
				 batchnumber= (String)arg.get(VIEW_ENTITY_ID_KEY);
		}if(arg.containsKey("BATCHNO")){
			batchnumber = (String)arg.get("BATCHNO");
		}
		
		data.setBatchId(batchnumber);
		if(arg.containsKey("TRANSFER_BATCH")){
			Logger.logTrace("INVENTORY","going to more filter :::  Transfer Inventory called");
			data.setInventoryStatusId(2L);
			data.setResourceTypeId((Long)arg.get("RESOURCE_TYPEID"));
			data.setResourceSubtypeId((Long)arg.get("RESOURCE_SUBTYPEID"));
			data.setTransferInventoryStatus("");
		}
		
		Logger.logTrace("INVENTORY","Sending data to BD:" + data);
		List<InventoryDetailVO> listInventoryDetailData = inventoryManagementBD.searchInventoryDetailData(data);
		Logger.logTrace("INVENTORY", "after call " + listInventoryDetailData);
		//Logger.logTrace("INVENTORY", "listInventoryDetailData.isEmpty()"+listInventoryDetailData.isEmpty());
	//	Logger.logTrace("INVENTORY", "listInventoryDetailData.size"+listInventoryDetailData.size());
		if (listInventoryDetailData != null && !listInventoryDetailData.isEmpty()) {
			searchResultGrid.setModel(new ListModelList<InventoryDetailVO>(listInventoryDetailData));

			searchResultGrid.setItemRenderer(new SearchListItemRenderer());
		} else {
			searchResultGrid.setModel(new ListModelList<InventoryDetailVO>());
		}
	}

	private class SearchListItemRenderer implements
			ListitemRenderer<InventoryDetailVO> {

		@Override
		public void render(Listitem item, InventoryDetailVO data, int index)
				throws Exception {

			System.out.println("Data in Render method:" + data);
			if(data.getInventoryId()!=null){
			int no = index + 1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getBatchId())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getInventoryId())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getStatus())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getDistributorNumber())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getCustomerRefNumber())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getOrderNumber())));
			Map attributeMap = data.getAttribute();
			if (!attributeMap.isEmpty()) {
				for (AttributeVO attributeVO : attributeDatas) {
					if(attributeMap.get(attributeVO.getName())!=null ){
						item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(String.valueOf(attributeMap.get(attributeVO.getName())))));
						}
						else {
							
							item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(null)));
							
							}
				}
				}
			item.setValue(data);
			}
			else{
				searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
			}
			// for(String str: data.getAttribute()){
			// item.appendChild(new Listcell(str));
			// }

			// item.appendChild(new Listcell(data.getAttribute()));
			// Listcell operationCell = new Listcell();
			// Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
			//
			// edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
			// edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
			// edit.addEventListener(Events.ON_CLICK, editItemListener);
			//
			// operationCell.appendChild(edit);
			// item.appendChild(operationCell);
			
		}

	}

		
	

}
