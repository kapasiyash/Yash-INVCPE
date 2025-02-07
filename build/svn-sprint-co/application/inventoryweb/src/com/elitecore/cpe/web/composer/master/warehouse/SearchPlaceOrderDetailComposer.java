package com.elitecore.cpe.web.composer.master.warehouse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import org.zkoss.zul.Label;
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
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorymgt.TransferInventorySummaryViewVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;

import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.InvalidDataException;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;
import com.elitecore.cpe.web.utils.MessageUtility;

public class SearchPlaceOrderDetailComposer extends BaseModuleViewComposer{

	private Hlayout placeOrderSummary;
	
	
	private Textbox txtOrderNo;
	
	private Datebox orderfrom,orderto;
	private Listbox searchPlaceOrderGrid,searchPlaceOrderGrid1;
	private Tabbox viewPlaceOrderSummaryTabBox;
	
	private static final String MODULE ="SEARCH_PLACE_ORDER_SUMMARY";
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		this.placeOrderSummary = comp;
		
	}
	
	public void onClick$btnSearch(Event event){
		
		Logger.logDebug(MODULE, "current warehoouseId : "+getViewEntityId());
		
		try {
			
			PlaceOrderVO  data=new PlaceOrderVO();
			data.setFromwarehouseId(getViewEntityId());
			data.setTowarehouseId(getViewEntityId());
			if(txtOrderNo.getValue().trim() != null && !txtOrderNo.getValue().equals("")){
				data.setOrderNo(txtOrderNo.getValue().trim());
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
				
				data.setFromDate(new Timestamp(calendar.getTimeInMillis()));
			}if(orderto.getValue() != null){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(orderto.getValue());
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				data.setToDate(new Timestamp(calendar.getTimeInMillis()));
			}
			
			Logger.logDebug(MODULE, "calling bd");
			System.out.println(data.getFromDate());
			System.out.println(data.getToDate());
			InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
			List<PlaceOrderVO> list = inventoryManagementBD.searchPlaceOrderData(data);
			
			Collections.sort(list);
			
			searchPlaceOrderGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
			searchPlaceOrderGrid1.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
			searchPlaceOrderGrid.setVisible(true);
			searchPlaceOrderGrid1.setVisible(true);
			viewPlaceOrderSummaryTabBox.setVisible(true);
			
			if(list != null && !list.isEmpty()){
				
				List<PlaceOrderVO> fromDetailList = new ArrayList<PlaceOrderVO>();
				List<PlaceOrderVO> toDetailList = new ArrayList<PlaceOrderVO>();
				
				for(PlaceOrderVO placeOrderVO: list){
					if(getViewEntityId().equals(placeOrderVO.getFromwarehouseId())){
						fromDetailList.add(placeOrderVO);
					}else{
						toDetailList.add(placeOrderVO);
					}
				}
				
				Logger.logDebug(MODULE, fromDetailList.size() + " : "+toDetailList.size());
				
				searchPlaceOrderGrid.setModel(new ListModelList<PlaceOrderVO>(fromDetailList));
				searchPlaceOrderGrid.setItemRenderer(new SearchListItemRenderer());
				
				searchPlaceOrderGrid1.setModel(new ListModelList<PlaceOrderVO>(toDetailList));
				searchPlaceOrderGrid1.setItemRenderer(new SearchListItemRenderer());
				
				
			}else{
				searchPlaceOrderGrid.setModel(new ListModelList<PlaceOrderVO>());
				searchPlaceOrderGrid1.setModel(new ListModelList<PlaceOrderVO>());
			}
			
		}catch(InvalidDataException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("Invalid Data", e.getMessage());
		}
		
	}
	
	private class SearchListItemRenderer implements ListitemRenderer<PlaceOrderVO>{

		private EventListener<Event> editItemListener,editHoverListner,editOutListener,viewPlaceOrderListner;
		
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
			
			viewPlaceOrderListner = new EventListener<Event>() {
				
								@Override
								public void onEvent(Event event) throws Exception {
					
								onViewPlaceOrder(event);
				}
			};
			
		}
		
		
		
		
		@Override
		public void render(Listitem item, PlaceOrderVO data, int index)throws Exception {
			Logger.logTrace(MODULE, "Inside render:::: "+data);
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			//Adding Link to Place order number
			Listcell totalcell = new Listcell();
			A totalHyperlink = new A(String.valueOf(data.getOrderNo()));
			totalHyperlink.setId(data.getOrderNo());
			totalHyperlink.addEventListener(Events.ON_CLICK, viewPlaceOrderListner);
			totalcell.appendChild(totalHyperlink);
			item.appendChild(totalcell);
			
			
			
			//item.appendChild(new Listcell(data.getOrderNo()));
			item.appendChild(new Listcell(data.getFromwarehouse()));
			item.appendChild(new Listcell(data.getTowarehouse()));
			item.appendChild(new Listcell(data.getResourceType()));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull((data.getResourceSubtype()))));
			item.appendChild(new Listcell(data.getQuantity().toString()));
			
			item.appendChild(new Listcell(data.getStatus()));
			//item.appendChild(new Listcell(dateFormat.format(data.getCreateDate())));
			if( (!SearchPlaceOrderDetailComposer.this.getViewEntityId().equals(data.getFromwarehouseId()))
					&& !"Cancelled".equals(data.getStatus()))
			{	
				if( ("In Progress".equals(data.getStatus()))){
				Logger.logTrace(MODULE, "Inside Provider:::: "+data.getFromwarehouse());
				Listcell operationCell = new Listcell();
				Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
				
				edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
				edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
				edit.addEventListener(Events.ON_CLICK, editItemListener);
				
				edit.setAttribute("ACTION",  "PROVIDER");
				edit.setId(data.getOrderNo()+"_"+data.getFromwarehouse()+"_"+data.getTowarehouse()+"_"+data.getQuantity());
				operationCell.appendChild(edit);
				item.appendChild(operationCell);
				
				item.appendChild(new Listcell(""));
				
				}else if(!"Rejected".equals(data.getStatus()) && !"Completed".equals(data.getStatus())){
					Logger.logTrace(MODULE, "Inside Provider::Accep/partially accept:: "+data.getFromwarehouse());
					Listcell operationCell = new Listcell();
					Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
					
					edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
					edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
					edit.addEventListener(Events.ON_CLICK, editItemListener);
					
					edit.setAttribute("ACTION",  "ACCEPT_TRANSFER");
					
					edit.setId(data.getOrderNo()+"_"+data.getFromwarehouse()+"_"+data.getTowarehouse()+"_"+
					data.getQuantity()+"_"+data.getResourceType()+"_"+data.getResourceSubtype());
					operationCell.appendChild(edit);
					item.appendChild(new Listcell(""));
					item.appendChild(operationCell);
					
				}
			} else {
				//boolean isCancel = false;
				if( ("In Progress".equals(data.getStatus()))){
					Logger.logTrace(MODULE, "Inside Requestor:::: "+data.getFromwarehouse());
					Listcell operationCell = new Listcell();
					Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
					
					edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
					edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
					edit.addEventListener(Events.ON_CLICK, editItemListener);
					
					edit.setAttribute("ACTION",  "REQUESTOR");
					edit.setId(data.getOrderNo()+"_"+data.getFromwarehouse()+"_"+data.getTowarehouse()+"_"+data.getQuantity());
				
					operationCell.appendChild(edit);
					item.appendChild(operationCell);
					//isCancel = true;
				}
			}
			
			item.setValue(data);
		}
		
		
	}
	
	
	private void onViewPlaceOrder(Event event){
		Logger.logDebug(MODULE, "on hyper click :");
		Logger.logDebug(MODULE, "data :"+event.getTarget().getId());
		
		String id = event.getTarget().getId();
//		String[] ids = id.split("_");
//		String orderNo = ids[0];
//		String status = ids[1].trim();
//		String total = ids[2].trim();
//		String fromWHName = ids[3].trim();
//		String toWHName = ids[4].trim();
		
		if(id != null ){
			PlaceOrderVO placeOrderVO = new PlaceOrderVO();
			placeOrderVO.setOrderNo(id);
			
	
			Logger.logDebug(MODULE, "calling bd");
			InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
			placeOrderVO  = inventoryManagementBD.searchPlaceOrderDataByOrderNo(placeOrderVO);
			
			try {
				if(placeOrderVO != null){
					Map<String,Object> argMap = new HashMap<String, Object>();
					Logger.logDebug(MODULE, "After calling bd::PLACEORDERVO:::"+placeOrderVO);
					argMap.put("PLACEORDERVO",placeOrderVO);
					Window window = (Window)Executions.createComponents(Pages.VIEW_PLACEORDER_DETAIL_ACTION, this.placeOrderSummary, argMap);
					window.doModal();
					
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
		
		if(event.getTarget().getAttribute("ACTION").equals("REQUESTOR")) {
			Logger.logDebug(MODULE, "Inside::Requestor::"+event.getTarget().getAttribute("ACTION"));
			String id = event.getTarget().getId();
			String[] ids = id.split("_");
			
			Map<String,Object> argMap = new HashMap<String, Object>();
			
			argMap.put("ORDERNO",ids[0]);
			argMap.put("FROMWHNAME",ids[1]);
			argMap.put("TOWHNAME",ids[2]);
			argMap.put("QUANTITY",ids[3]);
			argMap.put("placeOrderSummaryObj",this);
			
			Set<Long> userWarehouses = getBDSessionContext().getBLSession().getUserWarehouseMappings();
			System.out.println(userWarehouses);
			if(userWarehouses!=null && userWarehouses.contains(getViewEntityId())) {
				Window window = (Window)Executions.createComponents(Pages.CANCEL_PLACEORDER_ACTION, this.placeOrderSummary, argMap);
				window.doModal();
			} else {
				MessageUtility.failureInformation("No Permit", "You dont have permission to this Action");
			}
			
			
		} else  if(event.getTarget().getAttribute("ACTION").equals("PROVIDER")) {
			Logger.logDebug(MODULE, "Inside::Provider::"+event.getTarget().getAttribute("ACTION"));
			String id = event.getTarget().getId();
			String[] ids = id.split("_");
			
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put("ORDERNO",ids[0]);
			argMap.put("FROMWHNAME",ids[1]);
			argMap.put("TOWHNAME",ids[2]);
			argMap.put("QUANTITY",ids[3]);
			argMap.put("placeOrderSummaryObj",this);
			
			Set<Long> userWarehouses = getBDSessionContext().getBLSession().getUserWarehouseMappings();
			if(userWarehouses!=null && userWarehouses.contains(getViewEntityId())) {
				Window window = (Window)Executions.createComponents(Pages.VIEW_PLACEORDER_ACTION, this.placeOrderSummary, argMap);
				window.doModal();
			}else {
				MessageUtility.failureInformation("No Permit", "You dont have permission to this Action");
			}
			
		} else  if(event.getTarget().getAttribute("ACTION").equals("ACCEPT_TRANSFER")) {
			Logger.logDebug(MODULE, "Inside::Accept::"+event.getTarget().getAttribute("ACTION"));
			String id = event.getTarget().getId();
			String[] ids = id.split("_");
			
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put("ORDERNO",ids[0]);
			argMap.put("FROMWHNAME",ids[1]);
			argMap.put("TOWHNAME",ids[2]);
			argMap.put("QUANTITY",ids[3]);
			argMap.put("RESOURCETYPE",ids[4]);
			argMap.put("RESOURCESUBTYPE",ids[5]);
			argMap.put("placeOrderSummaryObj",this);
			
			Set<Long> userWarehouses = getBDSessionContext().getBLSession().getUserWarehouseMappings();
			if(userWarehouses!=null && userWarehouses.contains(getViewEntityId())) {
				Window window = (Window)Executions.createComponents(Pages.TRANSFER_PLACEORDER_ACTION, this.placeOrderSummary, argMap);
				window.doModal();
			}  else {
				MessageUtility.failureInformation("No Permit", "You dont have permission to this Action");
			}
			
			
			
		}
	    
	}
}

