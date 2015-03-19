/**
 * 
 */
package com.elitecore.cpe.web.composer.inventory.search;

import java.util.List;

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
import com.elitecore.cpe.bl.vo.inventorymgt.BatchSummaryVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;

/**
 * @author Joel.Macwan
 *
 */
public class ViewInventoryBatchSummaryComposer extends BaseSearchComposer{



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.elitecore.cpe.web.base.ui.core.BaseSearchComposer#
	 * onDoubleClickedSearchItem(org.zkoss.zk.ui.event.Event)
	 */
	private static final long serialVersionUID = 1L;
	public static final String VIEW_ENTITY_ID_KEY = "_viewEntityId";
//	private Window viewInventoryBatchDetail;
	
	//private Textbox txtBatchId;
	

	private Listbox searchResultGrid;
	//List<AttributeVO> attributeDatas;

	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		// TODO Auto-generated method stub

	}

		
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		// TODO Auto-generated method stub
		System.out.println("in SearchInventoryComposer  afterComposer");
		//this.viewInventoryBatchDetail = comp;
		
		searchResultGrid.setVisible(false);
		init();
		// /////////////
		
	}

	private void init() {

	//	IBDSessionContext sessionContext = getBDSessionContext();
		
	//	AttributeBD attributeBD = new AttributeBD(sessionContext);
	//	InventoryManagementBD inventoryBD = new InventoryManagementBD(sessionContext);
		
		//attributeDatas = attributeBD.getAttributesByRef(CommonConstants.RESOURCE);
		

		Listhead lhd = new Listhead();
		Listheader lh1 = new Listheader();
		Listheader lh2 = new Listheader();
		Listheader lh3 = new Listheader();
		Listheader lh4 = new Listheader();
		Listheader lh5 = new Listheader();
		Listheader lh6 = new Listheader();
		Listheader lh7 = new Listheader();
		Listheader lh8 = new Listheader();
		lh1.setLabel("Sr.No");
		lh1.setWidth("55px");
		lh2.setLabel("Batch Number");
		lh3.setLabel("Resource Type");
		lh4.setLabel("Resource Subtype");
		lh5.setLabel("Resource");
		lh6.setLabel("Inventory From");
		lh7.setLabel("Inventory To");
		lh8.setLabel("Total");
		lh2.setSort("auto(batchnumber)");
		lh4.setSort("auto(resourcetype)");
		lh5.setSort("auto(resourcesubtype)");
		lh6.setSort("auto(inventoryFrom)");
		lh7.setSort("auto(inventoryTo)");
		lh8.setSort("auto(total)");
		
		lh1.setParent(lhd);
		lh2.setParent(lhd);
		lh3.setParent(lhd);
		lh4.setParent(lhd);
		lh5.setParent(lhd);
		lh6.setParent(lhd);
		lh7.setParent(lhd);
		lh8.setParent(lhd);

		
		lhd.setSizable(true);
		lhd.setParent(searchResultGrid);
		populateData();
	}

	public void populateData() {

		Logger.logTrace("INVENTORY", "Inside populateData() ");
		BatchSummaryVO data = new BatchSummaryVO();
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
		
		data.setBatchnumber(batchnumber);
//		if(arg.containsKey("TRANSFER_BATCH")){
//			Logger.logTrace("INVENTORY","going to more filter :::  Transfer Inventory called");
//			data.setInventoryStatusId(2L);
//			data.setResourceTypeId((Long)arg.get("RESOURCE_TYPEID"));
//			data.setResourceSubtypeId((Long)arg.get("RESOURCE_SUBTYPEID"));
//			data.setTransferInventoryStatus("");
//		}
		
		Logger.logTrace("INVENTORY","Sending data to BD:" + data);
		List<BatchSummaryVO> listBatchSummaryData = inventoryManagementBD.searchBatchSummaryData(data);
		Logger.logTrace("INVENTORY", "after call " + listBatchSummaryData);
		//Logger.logTrace("INVENTORY", "listInventoryDetailData.isEmpty()"+listInventoryDetailData.isEmpty());
	//	Logger.logTrace("INVENTORY", "listInventoryDetailData.size"+listInventoryDetailData.size());
		if (listBatchSummaryData != null && !listBatchSummaryData.isEmpty()) {
			searchResultGrid.setModel(new ListModelList<BatchSummaryVO>(listBatchSummaryData));

			searchResultGrid.setItemRenderer(new SearchListItemRenderer());
		} else {
			searchResultGrid.setModel(new ListModelList<BatchSummaryVO>());
		}
	}

	private class SearchListItemRenderer implements
			ListitemRenderer<BatchSummaryVO> {

		@Override
		public void render(Listitem item, BatchSummaryVO data, int index)
				throws Exception {

			System.out.println("Data in Render method:" + data);
			if(data.getBatchnumber()!=null){
			int no = index + 1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getBatchnumber())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourcetype())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourcesubtype())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResource())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getInventoryFrom())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getInventoryTo())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getTotal().toString())));
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
