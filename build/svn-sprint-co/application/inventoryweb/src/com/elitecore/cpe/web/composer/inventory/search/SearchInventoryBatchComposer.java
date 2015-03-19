/**
 * 
 */
package com.elitecore.cpe.web.composer.inventory.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryBatchViewVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;

/**
 * @author Joel.Macwan
 *
 */
public class SearchInventoryBatchComposer extends BaseSearchComposer {
	
	
	private static final long serialVersionUID = 1L;
//	private Window searchInventoryBatch;
	
	private Textbox txtBatchNumber;//txtExternalBatchNumber
	private Listbox searchResultGrid;
	private Tabbox searchInventoryBatchTabbox;
	
	private static final String MODULE = "SEARCH_INVENTORY_BATCH";
	
	
	public void onClick$btnCancel(Event event){
		resetComponents(txtBatchNumber,txtBatchNumber);
	}
	
	/* (non-Javadoc)
	 * @see com.elitecore.cpe.web.base.ui.core.BaseComposer#afterCompose(org.zkoss.zul.Window)
	 */
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		// TODO Auto-generated method stub
		System.out.println("in SearchInventoryBatchComposer  afterComposer");
		//this.searchInventoryBatch = comp;

		searchResultGrid.setVisible(false);
		
	}
	/* (non-Javadoc)
	 * @see com.elitecore.cpe.web.base.ui.core.BaseSearchComposer#onDoubleClickedSearchItem(org.zkoss.zk.ui.event.Event)
	 */
	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void onClick$btnSearch(Event event) {

		Logger.logTrace("INVENTORY", "Inside btnSearch ");
		SearchInventoryVO data = new SearchInventoryVO();
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		
		if (txtBatchNumber.getValue().trim() != null) {
			data.setBatchId(txtBatchNumber.getValue());
//			data.setExternalBatchNumber(txtExternalBatchNumber.getValue());
			
		}
		

		
		Logger.logTrace("INVENTORY", "calling BD ");
		
		List<InventoryBatchViewVO> listInventoryBatchViewData = inventoryManagementBD.searchInventoryBatchData(data);
		Logger.logTrace("INVENTORY", "After call " + listInventoryBatchViewData);
	//	Logger.logTrace("INVENTORY", "After call Size"+listInventoryBatchViewData.size());
		//Logger.logTrace("INVENTORY", "listInventoryDetailData.isEmpty()"+listInventoryDetailData.isEmpty());
	//	Logger.logTrace("INVENTORY", "listInventoryDetailData.size"+listInventoryDetailData.size());
		if (null!=listInventoryBatchViewData  && !listInventoryBatchViewData.isEmpty()) {
			searchResultGrid.setModel(new ListModelList<InventoryBatchViewVO>(listInventoryBatchViewData));

			searchResultGrid.setItemRenderer(new SearchListInventoryBatchRenderer());
		} else {
			searchResultGrid.setModel(new ListModelList<InventoryDetailVO>());
		}
	}	
	public void clickView(){
		Logger.logTrace("INVENTORY", "in clickView function...");
		
		if(searchResultGrid.getSelectedItem()!=null){
			InventoryBatchViewVO inventoryBatchViewVO =(InventoryBatchViewVO) searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put("title","Inventory");
			argMap.put(SEARCH_COMPOSER_REF, this);
			//addViewTab(inventoryBatchViewVO.getBatchNumber(),inventoryBatchViewVO.getBatchNumber(), searchInventoryBatchTabbox, Pages.VIEW_BATCHDETAIL_EVENT,argMap);
			addViewTab(inventoryBatchViewVO.getBatchNumber(),inventoryBatchViewVO.getBatchNumber(), searchInventoryBatchTabbox, Pages.VIEW_BATCHSUMMARY_EVENT,argMap);
			
		}
		
	}
	private class SearchListInventoryBatchRenderer implements
			ListitemRenderer<InventoryBatchViewVO> {
		private EventListener<Event> viewItemListener,viewHoverListner,viewOutListener,viewTotalListner;
		
		public SearchListInventoryBatchRenderer() {
			
			viewItemListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					//Image img = (Image) event.getTarget();
					clickView();
				}

			};
			
			viewHoverListner = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					Image img = (Image) event.getTarget();
					img.setSrc(BaseConstants.EDIT_ITEM_HOVER_IMAGE);
				}
			};
			
			viewOutListener = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					Image img = (Image) event.getTarget();
					img.setSrc(BaseConstants.EDIT_ITEM_IMAGE);
				}
			};
			
			viewTotalListner = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					
					onViewInventory(event);
				}
			};
		}
		
		@Override
		public void render(Listitem item, InventoryBatchViewVO data, int index)
				throws Exception {

			System.out.println("Data in Render method:" + data);
			
			int no = index + 1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getBatchNumber())));
//			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getValidcount().toString())));
//			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getInvalidcount().toString())));
			
			Listcell validcell = new Listcell();
			A validHyperlink = new A(String.valueOf(data.getValidcount()));
			validHyperlink.setId(data.getBatchNumber()+"_"+"Valid"+"_"+data.getValidcount());
			validHyperlink.addEventListener(Events.ON_CLICK, viewTotalListner);
			validcell.appendChild(validHyperlink);
			item.appendChild(validcell);

			Listcell inValidcell = new Listcell();
			A inValidHyperlink = new A(String.valueOf(data.getInvalidcount()));
			inValidHyperlink.setId(data.getBatchNumber()+"_"+"Invalid"+"_"+data.getInvalidcount());
			inValidHyperlink.addEventListener(Events.ON_CLICK, viewTotalListner);
			inValidcell.appendChild(inValidHyperlink);
			item.appendChild(inValidcell);
			
		
			Listcell operationCell = new Listcell();
			Image view = new Image(BaseConstants.EDIT_ITEM_IMAGE);
			
			view.addEventListener(Events.ON_MOUSE_OVER, viewHoverListner);
			view.addEventListener(Events.ON_MOUSE_OUT, viewOutListener);
			view.addEventListener(Events.ON_CLICK, viewItemListener);
			
			operationCell.appendChild(view);
			item.appendChild(operationCell);
			item.setValue(data);
		}

	}
	
	private void onViewInventory(Event event){
		Logger.logDebug(MODULE, "on hyper click :");
		Logger.logDebug(MODULE, "data :"+event.getTarget().getId());
		
		String id = event.getTarget().getId();
		String[] ids = id.split("_");
		
		String total = ids[2];
		if(Integer.parseInt(total) <= 0){
			return;
		}
		
		boolean status = (ids[1].equalsIgnoreCase("valid"))?true:false;
		
		Logger.logDebug(MODULE, ids[0]+" : "+status + " calling BD");
		
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		InventoryUploadVO  uploadVO = inventoryManagementBD.getBatchDetailInventoryData(ids[0], status);
		if(uploadVO != null && uploadVO.getUploadbyteData() != null){
			Filedownload.save(uploadVO.getUploadbyteData(), "", ids[0]+"_"+ids[1]+".csv");
		}
	}	
	

}
