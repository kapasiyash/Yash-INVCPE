package com.elitecore.cpe.web.composer.master.warehouse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class SearchWarehouseComposer  extends BaseSearchComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private Window searchWareHouse;
	private Textbox txtName,txtWarehouseCode;
	private Textbox txtLocation;
	private Combobox cmbWarehouseType;
	private Tab searchTab;
	private Listbox searchResultGrid;
	
	private Tabbox searchWarehouseTabbox;
	
	
	public void onClick$btnCancel(Event event){
		resetComponents(txtName,txtName,txtLocation,txtWarehouseCode,cmbWarehouseType);
	}
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		System.out.println("in SearchWareHouse composer afterComposer");
	//	this.searchWareHouse = comp;
		WareHouseBD  wareHouseBD = new WareHouseBD(getBDSessionContext());
		WarehouseTypeVO warehouseTypeVO=new WarehouseTypeVO();
		List<WarehouseTypeVO> warehouseTypeVOs=wareHouseBD.searchWarehouseTypeData(warehouseTypeVO);
		
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		if(warehouseTypeVOs != null && !warehouseTypeVOs.isEmpty()){
			
			for(WarehouseTypeVO data : warehouseTypeVOs){
				comboBoxDatas.add(new ComboData(data.getWarehouseTypeId(), data.getName()));
			}
		}
		
		cmbWarehouseType.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbWarehouseType.setItemRenderer(new ComboItemDataRenderer());
		searchResultGrid.setVisible(false);
		addViewTab("-100", "Create Warehouse", searchWarehouseTabbox, Pages.CREATE_WAREHOUSE_EVENT,null,false);
		searchTab.setSelected(true);
	}

	public void onClick$btnCreate(Event event) {
		
		/*System.out.println("in onClick$btnCreate");
		
		if (moduleContent != null
				&& moduleContent.getChildren() != null) {
			moduleContent.getChildren().clear();
		}
		
		Executions.createComponents(Pages.CREATE_WAREHOUSE_EVENT, moduleContent, null);*/
			
	}

	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		
		
	}
	
	public void onClick$btnSearch(Event event) {
		
		Logger.logTrace("WAREHOUSE", "Inside btnSearch ");
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		
		WarehouseVO data = new WarehouseVO();
		WareHouseBD  wareHouseBD = new WareHouseBD(getBDSessionContext());
		data.setName(txtName.getValue());
		data.setLocation(txtLocation.getValue());
		data.setWarehouseCode(txtWarehouseCode.getValue());
		if (cmbWarehouseType.getSelectedItem() != null) {
			ComboData selectedData = cmbWarehouseType.getSelectedItem().getValue();
			
			data.setWarehouseTypeId(selectedData.getId());
		}
		Logger.logTrace("WAREHOUSE", "calling BD ");
		List<WarehouseVO> listWarehouseData = wareHouseBD.searchWarehouseData(data);
		Logger.logTrace("WAREHOUSE", "after call "+listWarehouseData);
		if(listWarehouseData != null && !listWarehouseData.isEmpty()){
			searchResultGrid.setModel(new ListModelList<WarehouseVO>(listWarehouseData));
			
			searchResultGrid.setItemRenderer(new SearchListItemRenderer());
		}else{
			searchResultGrid.setModel(new ListModelList<WarehouseVO>());
		}
	}
	
	public void clickEdit(){
		Logger.logTrace("WAREHOUSE", "in clickEdit function...");
		
		if(searchResultGrid.getSelectedItem()!=null){
			WarehouseVO wrapperVO =(WarehouseVO) searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			
			argMap.put(SEARCH_COMPOSER_REF, this);
			addViewTab(wrapperVO.getWarehouseId(), wrapperVO.getName(), searchWarehouseTabbox, Pages.VIEW_WAREHOUSE_EVENT,argMap);
		}
		
	}
	
	private class SearchListItemRenderer implements ListitemRenderer<WarehouseVO>{

		private EventListener<Event> editItemListener,editHoverListner,editOutListener;
		SimpleDateFormat dateFormat = new SimpleDateFormat(getDateTimeFormat());
		public SearchListItemRenderer() {
			
			editItemListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
				//	Image img = (Image) event.getTarget();
					clickEdit();
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
		}
		
		@Override
		public void render(Listitem item, WarehouseVO data, int index)throws Exception {
			
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(data.getName()));
			item.appendChild(new Listcell(data.getParentWarehouseName()));
			item.appendChild(new Listcell(data.getWarehouseType().getName()));
			item.appendChild(new Listcell(data.getLocation()));
			item.appendChild(new Listcell(data.getOwner()));
			item.appendChild(new Listcell(data.getContactNo()));
			
			item.appendChild(new Listcell(data.getEmailId()));
			item.appendChild(new Listcell(dateFormat.format(data.getCreateDate())));
			
			Listcell operationCell = new Listcell();
			Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
			
			edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
			edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
			edit.addEventListener(Events.ON_CLICK, editItemListener);
			
			operationCell.appendChild(edit);
			item.appendChild(operationCell);
			item.setValue(data);
		}
		
	}
	
}
