package com.elitecore.cpe.web.composer.master.warehouse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hlayout;
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
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorymgt.TransferInventorySummaryViewVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.InvalidDataException;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class TransferInventorySummaryComposer extends BaseModuleViewComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Hlayout transferInventorySummary;
	
	
	private Textbox txtOrderNo;
	
	private Datebox orderfrom,orderto;
	private Listbox searchResultGrid,searchResultGrid1;
	private Tabbox viewTransferInventorySummaryTabBox;
	
	private static final String MODULE ="TRANSFR_INVENTORY_SUMMARY";
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		this.transferInventorySummary = comp;
		
	}
	
	public void onClick$btnSearch(Event event){
		
		Logger.logDebug(MODULE, "current warehoouseId : "+getViewEntityId());
		
		try {
			
			SearchTransferInventory searchTransferInventory = new SearchTransferInventory();
			searchTransferInventory.setWarehouseId(getViewEntityId());
			
			if(txtOrderNo.getValue() != null && !txtOrderNo.getValue().equals("")){
				searchTransferInventory.setOrderNo(txtOrderNo.getValue());
			}
			
			if(orderfrom.getValue()!=null && orderto.getValue()!=null) {
				if(orderfrom.getValue().after(orderto.getValue())) {
					throw new InvalidDataException("FromDate can not be greater than toDate");
				}
			}
			
			if(orderfrom.getValue() != null){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(orderfrom.getValue());
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.HOUR, 0);
				
				searchTransferInventory.setFromDate(new Timestamp(calendar.getTimeInMillis()));
			}if(orderto.getValue() != null){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(orderto.getValue());
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				searchTransferInventory.setToDate(new Timestamp(calendar.getTimeInMillis()));
			}
			
			Logger.logDebug(MODULE, "calling bd");
			System.out.println(searchTransferInventory.getFromDate());
			System.out.println(searchTransferInventory.getToDate());
			InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
			List<TransferInventorySummaryViewVO> list = inventoryManagementBD.searchTransferInventorySummary(searchTransferInventory);
			
			searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
			searchResultGrid1.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
			searchResultGrid.setVisible(true);
			searchResultGrid1.setVisible(true);
			viewTransferInventorySummaryTabBox.setVisible(true);
			
			if(list != null && !list.isEmpty()){
				
//				Collections.sort(list);
				
				List<TransferInventorySummaryViewVO> fromDetailList = new ArrayList<TransferInventorySummaryViewVO>();
				List<TransferInventorySummaryViewVO> toDetailList = new ArrayList<TransferInventorySummaryViewVO>();
				
				for(TransferInventorySummaryViewVO transferInventorySummaryViewVO: list){
					if(getViewEntityId().equals(transferInventorySummaryViewVO.getFromWarehouseId())){
						fromDetailList.add(transferInventorySummaryViewVO);
					}else{
						toDetailList.add(transferInventorySummaryViewVO);
					}
				}
				
				Logger.logDebug(MODULE, fromDetailList.size() + " : "+toDetailList.size());
				
				searchResultGrid.setModel(new ListModelList<TransferInventorySummaryViewVO>(fromDetailList));
				searchResultGrid.setItemRenderer(new SearchListItemRenderer());
				
				searchResultGrid1.setModel(new ListModelList<TransferInventorySummaryViewVO>(toDetailList));
				searchResultGrid1.setItemRenderer(new SearchListItemRenderer());
				
				
			}else{
				searchResultGrid.setModel(new ListModelList<TransferInventorySummaryViewVO>());
				searchResultGrid1.setModel(new ListModelList<TransferInventorySummaryViewVO>());
			}
			
		}catch(InvalidDataException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("Invalid Data", e.getMessage());
		}
		
	}
	
	private class SearchListItemRenderer implements ListitemRenderer<TransferInventorySummaryViewVO>{

		private EventListener<Event> editItemListener,editHoverListner,editOutListener,viewTotalListner;
		
		public SearchListItemRenderer() {
			
			editItemListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
			//		Image img = (Image) event.getTarget();
					clickAction(event);
				}

			};
			
			editHoverListner = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					Image img = (Image) event.getTarget();
					img.setSrc(BaseConstants.EDIT_ITEM_HOVER_IMAGE);
				}
			};
			
			editOutListener = new EventListener<Event>() {
				
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
		public void render(Listitem item, TransferInventorySummaryViewVO data, int index)throws Exception {
			
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			
			item.appendChild(new Listcell(data.getOrderNo()));
			item.appendChild(new Listcell(data.getFromWarehouseName()));
			item.appendChild(new Listcell(data.getToWarehouseName()));
			
			Listcell totalcell = new Listcell();
			A totalHyperlink = new A(String.valueOf(data.getTotal()));
			//added style sheet for hyperlink.
			if(data.getTotal()>0){
				totalHyperlink.setStyle("color: blue;text-decoration: underline");
			}
			totalHyperlink.setId(data.getOrderNo()+"_ _"+data.getTotal()+"_"+data.getFromWarehouseName()+"_"+data.getToWarehouseName());
			totalHyperlink.addEventListener(Events.ON_CLICK, viewTotalListner);
			totalcell.appendChild(totalHyperlink);
			item.appendChild(totalcell);
			

			Listcell acceptcell = new Listcell();
			A acceptHyperlink = new A(String.valueOf(data.getAccepted()));
			if(data.getAccepted()>0){
				acceptHyperlink.setStyle("color: blue;text-decoration: underline");
			}
			acceptHyperlink.setId(data.getOrderNo()+"_Accepted"+"_"+data.getAccepted()+"_"+data.getFromWarehouseName()+"_"+data.getToWarehouseName());
			acceptHyperlink.addEventListener(Events.ON_CLICK, viewTotalListner);
			acceptcell.appendChild(acceptHyperlink);
			item.appendChild(acceptcell);

			Listcell rejectcell = new Listcell();
			A rejectHyperlink = new A(String.valueOf(data.getRejected()));
			if(data.getRejected()>0){
				rejectHyperlink.setStyle("color: blue;text-decoration: underline");
			}

			rejectHyperlink.setId(data.getOrderNo()+"_Rejected"+"_"+data.getRejected()+"_"+data.getFromWarehouseName()+"_"+data.getToWarehouseName());
			rejectHyperlink.addEventListener(Events.ON_CLICK, viewTotalListner);
			rejectcell.appendChild(rejectHyperlink);
			item.appendChild(rejectcell);

			item.appendChild(new Listcell(data.getOrderStatus()));
			
			if(!TransferInventorySummaryComposer.this.getViewEntityId().equals(data.getFromWarehouseId()) 
					&& !"Cancelled".equals(data.getOrderStatus()) &&  data.getTotal() > (data.getAccepted()+data.getRejected()))
			{
				Listcell operationCell = new Listcell();
				Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
				
				edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
				edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
				edit.addEventListener(Events.ON_CLICK, editItemListener);
				
				edit.setAttribute("ACTION",  "TRANSFEREE");
				edit.setId(data.getOrderNo()+"_"+data.getFromWarehouseName()+"_"+data.getToWarehouseName());
				operationCell.appendChild(edit);
				item.appendChild(operationCell);
			} else {
				boolean isCancel = false;
				if(!"Cancelled".equals(data.getOrderStatus()) && (data.getAccepted()+data.getRejected())==0) {
					Listcell operationCell = new Listcell();
					Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
					
					edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
					edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
					edit.addEventListener(Events.ON_CLICK, editItemListener);
					
					edit.setAttribute("ACTION",  "TRANSFEROR");
					edit.setId(data.getOrderNo()+"_"+data.getFromWarehouseName()+"_"+data.getToWarehouseName());
				
					operationCell.appendChild(edit);
					item.appendChild(operationCell);
					isCancel = true;
				}
				
				if(data.isRejected()) {
					if(!isCancel) {
						item.appendChild(new Listcell(""));
					} 
					Listcell operationCell = new Listcell();
					Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
					
					edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
					edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
					edit.addEventListener(Events.ON_CLICK, editItemListener);
					
					edit.setAttribute("ACTION",  "REJECT_TRANSFEROR");
					edit.setId(data.getOrderNo()+"_"+data.getFromWarehouseName()+"_"+data.getToWarehouseName()+"_REJECT");
					operationCell.appendChild(edit);
					item.appendChild(operationCell);
				}
			}
			
			
			
			item.setValue(data);
		}

	}
	
	private void onViewInventory(Event event){
		Logger.logDebug(MODULE, "on hyper click :");
		Logger.logDebug(MODULE, "data :"+event.getTarget().getId());
		
		String id = event.getTarget().getId();
		String[] ids = id.split("_");
		String orderNo = ids[0];
		String status = ids[1].trim();
		String total = ids[2].trim();
		String fromWHName = ids[3].trim();
		String toWHName = ids[4].trim();
		
		if(total != null && Integer.parseInt(total) > 0){
			SearchTransferInventory searchTransferInventory = new SearchTransferInventory();
			searchTransferInventory.setOrderNo(orderNo);
			searchTransferInventory.setOrderStatus(status);
			searchTransferInventory.setFromWarehouseName(fromWHName);
			searchTransferInventory.setToWarehouseName(toWHName);
	
			Logger.logDebug(MODULE, "calling bd");
			InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
			InventoryUploadVO uploadVO  = inventoryManagementBD.getInventoryDetails(searchTransferInventory);
			
			try {
				if(uploadVO != null){
					Filedownload.save(uploadVO.getUploadbyteData(), "", orderNo+status.toUpperCase()+".csv");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void clickAction(Event event)
	{
		
		Logger.logDebug(MODULE, "on action click");
		Logger.logDebug(MODULE, "Data :"+event.getTarget().getId());
		
		if(event.getTarget().getAttribute("ACTION").equals("TRANSFEROR")) {
			String id = event.getTarget().getId();
			String[] ids = id.split("_");
			
			Map<String,Object> argMap = new HashMap<String, Object>();
			
			argMap.put("ORDERNO",ids[0]);
			argMap.put("FROMWHNAME",ids[1]);
			argMap.put("TOWHNAME",ids[2]);
			argMap.put("transferInventorySummaryObj",this);
			
			Set<Long> userWarehouses = getBDSessionContext().getBLSession().getUserWarehouseMappings();
			if(userWarehouses!=null && userWarehouses.contains(getViewEntityId())) {
				Window window = (Window)Executions.createComponents(Pages.CANCEL_TRANSFERINVENTORY_ACTION, this.transferInventorySummary, argMap);
				window.doModal();
			} else {
				MessageUtility.failureInformation("No Permit", "You dont have permission to this Action");
			}
			
			
			
		} else  if(event.getTarget().getAttribute("ACTION").equals("TRANSFEREE")) {
			
			String id = event.getTarget().getId();
			String[] ids = id.split("_");
			
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put("ORDERNO",ids[0]);
			argMap.put("FROMWHNAME",ids[1]);
			argMap.put("TOWHNAME",ids[2]);
			argMap.put("transferInventorySummaryObj",this);
			Set<Long> userWarehouses = getBDSessionContext().getBLSession().getUserWarehouseMappings();
			if(userWarehouses!=null && userWarehouses.contains(getViewEntityId())) {
				Window window = (Window)Executions.createComponents(Pages.VIEW_TRANSFERINVENTORY_ACTION, this.transferInventorySummary, argMap);
				window.doModal();
			} else {
				MessageUtility.failureInformation("No Permit", "You dont have permission to this Action");
			}
			
		} else if(event.getTarget().getAttribute("ACTION").equals("REJECT_TRANSFEROR")) {
			
			String id = event.getTarget().getId();
			String[] ids = id.split("_");
			
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put("ORDERNO",ids[0]);
			argMap.put("FROMWHNAME",ids[1]);
			argMap.put("TOWHNAME",ids[2]);
			argMap.put("transferInventorySummaryObj",this);
			Set<Long> userWarehouses = getBDSessionContext().getBLSession().getUserWarehouseMappings();
			if(userWarehouses!=null && userWarehouses.contains(getViewEntityId())) {
				Window window = (Window)Executions.createComponents(Pages.REJECT_TRANSFERINVENTORY_ACTION, this.transferInventorySummary, argMap);
				window.doModal();
			} else {
				MessageUtility.failureInformation("No Permit", "You dont have permission to this Action");
			}
		}
	    
	}
}
