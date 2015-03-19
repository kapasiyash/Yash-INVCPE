package com.elitecore.cpe.web.composer.master.warehouse;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class PlaceOrderComposer extends BaseModuleViewComposer{

	private Combobox cmbWarhouse;
	private Combobox cmbResourceType,cmbResourceSubType;
	Map<ComboData,List<ComboData>> resultMap=null;
	private Intbox txtQuantity;
	private Listbox viewThresholdGrid;
	private Div viewThresholdGridDiv;
	private Hlayout placeOrder;
//	private Listbox viewPlaceOrderGrid;
	private final static String module="PLACE ORDER";
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		// TODO Auto-generated method stub
		this.placeOrder=comp;
		System.out.println("in PlaceOrderComposer  afterComposer");
		System.out.println("in PlaceOrderComposer  getViewEntityId()::"+getViewEntityId());
		viewThresholdGrid.setVisible(false);
		viewThresholdGridDiv.setVisible(false);
	//	viewPlaceOrderGrid.setVisible(true);
		init();
		cmbWarhouse.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				if (cmbWarhouse.getSelectedItem() != null) {
					ComboData selectedData = cmbWarhouse.getSelectedItem().getValue();
					WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
					List<ComboData> comboDatas =  wareHouseBD.getAllResourceTypeWithWareHouseId(selectedData.getId());
					cmbResourceType.setSelectedItem(null);
					cmbResourceSubType.setSelectedItem(null);
					if(comboDatas!=null && !comboDatas.isEmpty()) {
						cmbResourceType.setModel(new ListModelList<ComboData>(comboDatas));
						cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
					} else {
						cmbResourceType.setModel(new ListModelList<ComboData>());
						cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
					}
				}
				
			}
		});
	}
	
	private void init() {

		IBDSessionContext sessionContext = getBDSessionContext();
		WareHouseBD wareHouseBD = new WareHouseBD(sessionContext);
		//viewPlaceOrderGrid.setEmptyMessage(Labels.getLabel("gen.noplaceorder"));
		resultMap = wareHouseBD.getAllResourceTypeWithResource(null);
		Logger.logTrace(module,"Received data from DB for combodata:"+resultMap);
//		InventoryManagementBD inventoryBD = new InventoryManagementBD(sessionContext);
		List<ComboData> comboBoxDatas = wareHouseBD.getAllWarehouseData();
		Iterator<ComboData> iterator=comboBoxDatas.iterator();
		while(iterator.hasNext()){
			if(iterator.next().getId().equals(getViewEntityId())){
				iterator.remove();
			}
			
		}
		cmbWarhouse.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbWarhouse.setItemRenderer(new ComboItemDataRenderer());
		
		Collection<ComboData> resourceTypecomboBoxDatas = resultMap.keySet();
		cmbResourceType.setModel(new ListModelList<ComboData>(resourceTypecomboBoxDatas));
		cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
		
/*		PlaceOrderVO data = new PlaceOrderVO();
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		data.setFromwarehouseId(getViewEntityId());
		
		Logger.logTrace(module, "calling BD ");
		List<PlaceOrderVO> listPlaceOrderVOs = inventoryManagementBD.searchPlaceOrderData(data);
		Logger.logTrace(module, "after call "+listPlaceOrderVOs);
		if(listPlaceOrderVOs != null && !listPlaceOrderVOs.isEmpty()){
			viewPlaceOrderGrid.setModel(new ListModelList<PlaceOrderVO>(listPlaceOrderVOs));
			
			viewPlaceOrderGrid.setItemRenderer(new SearchListItemRenderer());
		}else{
			viewPlaceOrderGrid.setModel(new ListModelList<PlaceOrderVO>());
		}  */
		
	}
	
	
	public void onChange$cmbResourceType(Event e) throws InterruptedException{
		
		Logger.logTrace(module, "Inside onChange$cmbResourceType event handler ");
		//reset();
			List<ComboData> comboBoxDatas=null;
		try{
			if(cmbResourceType.getSelectedItem() != null){
			ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
			Logger.logInfo(module, "ComboData :"+selectedData.getId()+"::"+selectedData.getName());
			 comboBoxDatas = resultMap.get(selectedData);
			for(ComboData comboData1:comboBoxDatas){
				Logger.logInfo(module, "ComboData :"+comboData1.getId()+"::"+comboData1.getName());
			}
		
			cmbResourceSubType.setModel(new ListModelList<ComboData>(comboBoxDatas));
			cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
			
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
			
	}
	public void onClick$btnCancel(Event event){
		//resetComponents(cmbWarhouse,cmbResourceType,cmbResourceSubType);
		Logger.logTrace(module, "Inside onClick$btnCancel event handler ");
		cmbWarhouse.setModel(null);
		cmbResourceType.setModel(null);
		cmbResourceSubType.setModel(null);
		//txtQuantity.setValue(0);
	}
	public void onClick$btnSubmit(Event event){
		Logger.logTrace(module, "Inside onClick$btnSubmit event handler ");
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		PlaceOrderVO placeOrderVO=new PlaceOrderVO();
		
	try{
		if (cmbWarhouse.getSelectedItem() != null) {
			ComboData selectedData = cmbWarhouse.getSelectedItem().getValue();
			placeOrderVO.setTowarehouseId(selectedData.getId());
		}
		placeOrderVO.setFromwarehouseId(getViewEntityId());
		if(cmbResourceType.getSelectedItem() != null) {
			ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
			placeOrderVO.setResourceTypeId(selectedData.getId());
				if (cmbResourceSubType.getSelectedItem() != null) {
					ComboData selectedData2 = cmbResourceSubType.getSelectedItem().getValue();
					placeOrderVO.setResourceSubTypeId(selectedData2.getId());
				}
			}
			if (txtQuantity.getValue() != null) {
				placeOrderVO.setQuantity(Long.parseLong(txtQuantity.getValue().toString()));
			}
			if(!(placeOrderVO.getTowarehouseId().equals(placeOrderVO.getFromwarehouseId()))){
				Logger.logTrace(module, "Sending Data to BD: "+placeOrderVO);
				
				placeOrderVO.setOrderType(CPECommonConstants.MANUAL_PLACEORDER);	
			String orderNo	= inventoryManagementBD.placeOrder(placeOrderVO);
			MessageUtility.successInformation("Success", "Place Order succussfully done \n Order Number : "+orderNo );
			placeOrder.detach();
			}else{
				MessageUtility.successInformation("Place Order", "Cannot Place Order to Same Warehouse" );
				return;
				}
			}catch(Exception ex){
			ex.printStackTrace();
			MessageUtility.failureInformation("ERROR",ex.getMessage());
			}
		}
	
/*	private class SearchListItemRenderer implements ListitemRenderer<PlaceOrderVO>{

	
	//	SimpleDateFormat dateFormat = new SimpleDateFormat(getDateTimeFormat());
		
		
		@Override
		public void render(Listitem item, PlaceOrderVO data, int index)throws Exception {
			Logger.logTrace(module, "Inside render:::: "+data);
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(data.getOrderNo()));
			item.appendChild(new Listcell(data.getFromwarehouse()));
			item.appendChild(new Listcell(data.getTowarehouse()));
			item.appendChild(new Listcell(data.getResourceType()));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull((data.getResourceSubtype()))));
			item.appendChild(new Listcell(data.getQuantity().toString()));
			
			item.appendChild(new Listcell(data.getStatus()));
			//item.appendChild(new Listcell(dateFormat.format(data.getCreateDate())));
			
			
			item.setValue(data);
		}
		
	}  */
}
