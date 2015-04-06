package com.elitecore.cpe.web.composer.master.item;

import java.text.SimpleDateFormat;
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
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;

public class SearchItemComposer  extends BaseSearchComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private Window searchItem;
	private Textbox txtName;
	private Textbox txtreferenceid,txtresourceNumber;
	private Combobox cmbResourceType,cmbResourceSubType;
	private Tab searchTab;
	private Listbox searchResultGrid;
	
	private Tabbox searchItemTabbox;
	
	
	public void onClick$btnCancel(Event event){
		resetComponents(txtName, txtName,txtreferenceid,txtresourceNumber,cmbResourceSubType,cmbResourceType);
		cmbResourceSubType.setModel(null);
	}
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		System.out.println("in SearcItem composer after Compose");
		//this.searchItem = comp;
		
		
		final ItemBD  itemBD = new ItemBD(getBDSessionContext());
		
		List<ComboData> comboBoxDatas = itemBD.getAllResourceTypeData();
		if(comboBoxDatas!=null && !comboBoxDatas.isEmpty()) {
			cmbResourceType.setModel(new ListModelList<ComboData>(comboBoxDatas));
			cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
		}
		
		cmbResourceType.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				

				if(cmbResourceType.getSelectedItem()!=null) {
					ComboData comboData = cmbResourceType.getSelectedItem().getValue();
					Long typeId = comboData.getId();
					List<ComboData> comboBoxDatas = itemBD.getAllResourceSubTypeDataByResourceTypeId(typeId);
					resetComponents(cmbResourceSubType, cmbResourceSubType);
					if(comboBoxDatas!=null && !comboBoxDatas.isEmpty()) {
						cmbResourceSubType.setModel(new ListModelList<ComboData>(comboBoxDatas));
						cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
					} else {
						cmbResourceSubType.setModel(new ListModelList<ComboData>());
					}
				}
				
			}
		});
		
		/*List<ComboData> comboDatas = itemBD.getAllResourceSubTypeData();
		if(comboDatas!=null && !comboDatas.isEmpty()) {
			cmbResourceSubType.setModel(new ListModelList<ComboData>(comboDatas));
			cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
		}*/
		
		
		searchResultGrid.setVisible(false);
		
		if(isPermittedAction(ActionAlias.CREATE_RESOURCE)) {
			addViewTab("-100", "Create Resource", searchItemTabbox, Pages.CREATE_ITEM_EVENT,null,false);
		}
		searchTab.setSelected(true);
	}

	
	
	
	

	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		
		
	}
	
	public void onClick$btnSearch(Event event) {
	
		Logger.logTrace("WAREHOUSE", "Inside btn Search of Search Item ");
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		
		
		ItemVO itemVo=new ItemVO();
		
		ItemBD  itemBD = new ItemBD(getBDSessionContext());
		
		
		itemVo.setName(txtName.getValue());
		itemVo.setReferenceID(txtreferenceid.getValue());
		itemVo.setResourceNumber(txtresourceNumber.getValue());
		 ResourceTypeVO resourceTypeVO=new ResourceTypeVO();
		if(cmbResourceType.getSelectedItem() != null){
			ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
			resourceTypeVO.setResourceTypeId(selectedData.getId());
			resourceTypeVO.setResourceTypeName(selectedData.getName());
		}
		if(cmbResourceSubType.getSelectedItem() != null){
			ResourceSubTypeVO subTypeVO = new ResourceSubTypeVO();
			ComboData selectedData = cmbResourceSubType.getSelectedItem().getValue();
			subTypeVO.setResourceSubTypeId(selectedData.getId());
			resourceTypeVO.setResourceSubTypeVO(subTypeVO);
		}
		itemVo.setResourceTypeVO(resourceTypeVO);
		Logger.logTrace("ITEM", "calling BD ");
		
		List<ItemVO> listItemData=itemBD.searchItemDataComposer(itemVo);
		
//		List<WarehouseData> listWarehouseData = wareHouseBD.searchWarehouseData(data);
		Logger.logTrace("ITEM", "after call "+listItemData);
		if(listItemData != null && !listItemData.isEmpty()){
			searchResultGrid.setModel(new ListModelList<ItemVO>(listItemData));
			
			searchResultGrid.setItemRenderer(new SearchListItemRenderer());
		}else{
			searchResultGrid.setModel(new ListModelList<ItemVO>());
		}

	}
	
	public void clickEdit(){
		
		Logger.logTrace("WAREHOUSE", "in clickEdit function...");
		
		if(searchResultGrid.getSelectedItem()!=null){
			
			ItemVO wrapperData=searchResultGrid.getSelectedItem().getValue();
//			WarehouseData wrapperData =(WarehouseData) searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			
			argMap.put(SEARCH_COMPOSER_REF, this);
			addViewTab(wrapperData.getItemId(), wrapperData.getName(), searchItemTabbox, Pages.VIEW_ITEM_EVENT,argMap);
		}
		
	}
	
	private class SearchListItemRenderer implements ListitemRenderer<ItemVO>{

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
		public void render(Listitem item, ItemVO data, int index)throws Exception 
		{
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(data.getResourceNumber()));
			item.appendChild(new Listcell(data.getName()));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getReferenceID())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getModelnumber())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourceTypeVO().getResourceTypeName())));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourceTypeVO().getResourceSubTypeVO().getResourceSubTypeName())));
			
			item.appendChild(new Listcell(dateFormat.format(data.getCreatedate())));
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
