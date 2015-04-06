package com.elitecore.cpe.web.composer.inventory.orderdetail;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.transfer.InventoryTransferBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorymgt.TransferInventorySummaryViewVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.ViewTransferInventoryDetailVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;
import com.elitecore.cpe.web.utils.MessageUtility;

public class SearchOrderDetailComposer extends BaseSearchComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private static final String MODULE = "SEARCH_ORDER_DETAIL";
	
	private Datebox orderfrom,orderto;
	private Textbox txtOrderNo;
	private Window searchOrderDetail;
	private Listbox searchPlaceOrderGrid;
	private Listbox searchResultGrid;
	private Tabbox viewPlaceOrderSummaryTabBox,viewTransferInventorySummaryTabBox;
	
//	private Radiogroup orderType;
	private Radio RadioTransfer,RadioPlaceOrder;
	
	private Vlayout placeOrderLayout,tranferOrderLayout;
	
	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		
	}

	
public void onClick$btnReset(Event event) {
		
		resetComponents(txtOrderNo, txtOrderNo,orderfrom,orderto);
		
	}
	
	public void onClick$btnSearch(Event event){
		
		Timestamp fromDate = null;
		Timestamp toDate = null;
		
		if(orderfrom.getValue()!=null && orderto.getValue()!=null) {
			if(orderfrom.getValue().after(orderto.getValue())) {
				MessageUtility.failureInformation("Error", "FromDate can not be greater than toDate");
				return;
			}
		}
		
		if(orderfrom.getValue() != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(orderfrom.getValue());
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.HOUR, 0);
			
			fromDate = new Timestamp(calendar.getTimeInMillis());
		}if(orderto.getValue() != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(orderto.getValue());
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			toDate = new Timestamp(calendar.getTimeInMillis());
		}
		
		
		if(RadioPlaceOrder.isSelected()) {
			
			
			try {
				
				PlaceOrderVO  data=new PlaceOrderVO();
				data.setFromwarehouseId(0L);
				data.setTowarehouseId(0L);
				if(txtOrderNo.getValue().trim() != null && !txtOrderNo.getValue().equals("")){
					data.setOrderNo(txtOrderNo.getValue().trim());
				}
				
				data.setFromDate(fromDate);
				data.setToDate(toDate);
				
				InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
				List<PlaceOrderVO> list = inventoryManagementBD.searchPlaceOrderDetail(data);
				
				Collections.sort(list);
				
				searchPlaceOrderGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
				searchPlaceOrderGrid.setVisible(true);
				viewPlaceOrderSummaryTabBox.setVisible(true);
				
				if(list != null && !list.isEmpty()){
					
					searchPlaceOrderGrid.setModel(new ListModelList<PlaceOrderVO>(list));
					searchPlaceOrderGrid.setItemRenderer(new SearchListItemRenderer());
					
					placeOrderLayout.setVisible(true);
					tranferOrderLayout.setVisible(false);
					
				}else{
					searchPlaceOrderGrid.setModel(new ListModelList<PlaceOrderVO>());
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				MessageUtility.failureInformation("Invalid Data", e.getMessage());
			}
			
		} else if (RadioTransfer.isSelected()) {
			
			SearchTransferInventory searchTransferInventory = new SearchTransferInventory();
			searchTransferInventory.setOrderNo(txtOrderNo.getValue());
			searchTransferInventory.setFromDate(fromDate);
			searchTransferInventory.setToDate(toDate);
			try {
				InventoryTransferBD inventoryManagementBD = new InventoryTransferBD(getBDSessionContext());
				
				List<TransferInventorySummaryViewVO> list = inventoryManagementBD.searchTransferInventoryOrderDetail(searchTransferInventory);
				
				searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
				searchResultGrid.setVisible(true);
				viewTransferInventorySummaryTabBox.setVisible(true);
				
				Logger.logTrace(MODULE, "after Search : "+list);
				
				if(list != null && !list.isEmpty()){
					
					Logger.logTrace(MODULE, "List not empty ");
					
					searchResultGrid.setModel(new ListModelList<TransferInventorySummaryViewVO>(list));
					searchResultGrid.setItemRenderer(new SearchListTranferItemRenderer());
					
					placeOrderLayout.setVisible(false);
					tranferOrderLayout.setVisible(true);
					
				}else{
					placeOrderLayout.setVisible(false);
					tranferOrderLayout.setVisible(true);
					searchResultGrid.setModel(new ListModelList<TransferInventorySummaryViewVO>());
				}
				
			} catch (SearchBLException e) {
				e.printStackTrace();
			}
			
		} else {
			Logger.logTrace(MODULE, "No radio selected");
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
	
	
	private class SearchListTranferItemRenderer implements ListitemRenderer<TransferInventorySummaryViewVO>{

		private EventListener<Event> viewTotalListner,viewTransferOrderListner;
		
		public SearchListTranferItemRenderer() {
			viewTotalListner = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					
					onViewInventory(event);
				}
			};
			
			viewTransferOrderListner = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
	
				onViewTransferOrder(event);
			}
			};
			
			
		}
		
		@Override
		public void render(Listitem item, TransferInventorySummaryViewVO data, int index)throws Exception {
			
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			
			
			//Adding Link to Place order number
			Listcell totalcell1 = new Listcell();
			A totalHyperlink1 = new A(String.valueOf(data.getOrderNo()));
			totalHyperlink1.setId(data.getOrderNo());
			totalHyperlink1.addEventListener(Events.ON_CLICK, viewTransferOrderListner);
			totalcell1.appendChild(totalHyperlink1);
			item.appendChild(totalcell1);
			
//			item.appendChild(new Listcell(data.getOrderNo()));
			item.appendChild(new Listcell(data.getFromWarehouseName()));
			item.appendChild(new Listcell(data.getToWarehouseName()));
			
			Listcell totalcell = new Listcell();
			A totalHyperlink = new A(String.valueOf(data.getTotal()));
			totalHyperlink.setId(data.getOrderNo()+"_ _"+data.getTotal()+"_"+data.getFromWarehouseName()+"_"+data.getToWarehouseName());
			totalHyperlink.addEventListener(Events.ON_CLICK, viewTotalListner);
			totalcell.appendChild(totalHyperlink);
			item.appendChild(totalcell);
			

			Listcell acceptcell = new Listcell();
			A acceptHyperlink = new A(String.valueOf(data.getAccepted()));
			acceptHyperlink.setId(data.getOrderNo()+"_Accepted"+"_"+data.getAccepted()+"_"+data.getFromWarehouseName()+"_"+data.getToWarehouseName());
			acceptHyperlink.addEventListener(Events.ON_CLICK, viewTotalListner);
			acceptcell.appendChild(acceptHyperlink);
			item.appendChild(acceptcell);

			Listcell rejectcell = new Listcell();
			A rejectHyperlink = new A(String.valueOf(data.getRejected()));
			rejectHyperlink.setId(data.getOrderNo()+"_Rejected"+"_"+data.getRejected()+"_"+data.getFromWarehouseName()+"_"+data.getToWarehouseName());
			rejectHyperlink.addEventListener(Events.ON_CLICK, viewTotalListner);
			rejectcell.appendChild(rejectHyperlink);
			item.appendChild(rejectcell);

			item.appendChild(new Listcell(data.getOrderStatus()));
			
			
			
			
			
			item.setValue(data);
		}

	}
	
	
	private class SearchListItemRenderer implements ListitemRenderer<PlaceOrderVO>{

	private EventListener<Event> viewPlaceOrderListner;
	
	public SearchListItemRenderer() {
		
		viewPlaceOrderListner = new EventListener<Event>() {
			
							@Override
							public void onEvent(Event event) throws Exception {
				
							onViewPlaceOrder(event);
			}
		};
		
	}
	
	
	
	
	@Override
	public void render(Listitem item, PlaceOrderVO data, int index)throws Exception {
		int no = index+1;
		item.appendChild(new Listcell(String.valueOf(no)));
		//Adding Link to Place order number
		Listcell totalcell = new Listcell();
		A totalHyperlink = new A(String.valueOf(data.getOrderNo()));
		totalHyperlink.setId(data.getOrderNo());
		totalHyperlink.addEventListener(Events.ON_CLICK, viewPlaceOrderListner);
		totalcell.appendChild(totalHyperlink);
		item.appendChild(totalcell);
		
		
		
		item.appendChild(new Listcell(data.getFromwarehouse()));
		item.appendChild(new Listcell(data.getTowarehouse()));
		if(data.getOrderType()!=null) {
			if(data.getOrderType() == CPECommonConstants.AUTOMATIC_PLACEORDER) {
				item.appendChild(new Listcell("Automatic"));
			} else if(data.getOrderType() == CPECommonConstants.MANUAL_PLACEORDER) {
				item.appendChild(new Listcell("Manual"));
			}
		} else {
			item.appendChild(new Listcell("-"));
		}
		
		item.appendChild(new Listcell(data.getResourceType()));
		item.appendChild(new Listcell(GeneralUtility.displayValueIfNull((data.getResourceSubtype()))));
		item.appendChild(new Listcell(data.getQuantity().toString()));
		
		item.appendChild(new Listcell(data.getStatus()));
		
		item.setValue(data);
	}
	
	
	}
	
	
	private void onViewTransferOrder(Event event){
		
		String orderNumber = event.getTarget().getId();
		
		if(orderNumber != null ){
			SearchTransferInventory placeOrderVO = new SearchTransferInventory();
			
			InventoryTransferBD transferBD = new InventoryTransferBD(getBDSessionContext());
			try {
				ViewTransferInventoryDetailVO detailVO = transferBD.searchTransferInventorySummary(orderNumber);
				
				Map<String,Object> argMap = new HashMap<String, Object>();
				argMap.put("ORDERVO",detailVO);
				Window window = (Window)Executions.createComponents(Pages.VIEW_TRANSFERORDER_DETAIL_ACTION, this.searchOrderDetail, argMap);
				window.doModal();
				
			} catch (SearchBLException e1) {
				e1.printStackTrace();
			}
			
			
			try {
				if(placeOrderVO != null){
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void onViewPlaceOrder(Event event){
		
		String id = event.getTarget().getId();
		
		if(id != null ){
			PlaceOrderVO placeOrderVO = new PlaceOrderVO();
			placeOrderVO.setOrderNo(id);
			
	
			InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
			placeOrderVO  = inventoryManagementBD.searchPlaceOrderDataByOrderNo(placeOrderVO);
			
			try {
				if(placeOrderVO != null){
					Map<String,Object> argMap = new HashMap<String, Object>();
					argMap.put("PLACEORDERVO",placeOrderVO);
					Window window = (Window)Executions.createComponents(Pages.VIEW_PLACEORDER_DETAIL_ACTION, this.searchOrderDetail, argMap);
					window.doModal();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
