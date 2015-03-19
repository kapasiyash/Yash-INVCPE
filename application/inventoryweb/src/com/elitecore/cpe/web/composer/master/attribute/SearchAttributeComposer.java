package com.elitecore.cpe.web.composer.master.attribute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Selectbox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.master.AttributeBD;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.CommonConstants;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class SearchAttributeComposer  extends BaseSearchComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private Window searchAttribute;
	private Textbox txtName;
	private Selectbox selRel;
	private Tab searchTab;
	
	private Listbox searchResultGrid;
	
	private Tabbox searchAttributeTabbox;
	private ListModelList<String> usedbylist = new ListModelList<String>();
	
	private final static String MODULE = "ATTRIBUTE";
	
	
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		System.out.println("in SearchAttribute composer afterCompose");
	//	this.searchAttribute = comp;
		init();
		
		searchResultGrid.setVisible(false);
		addViewTab("-100", "Create Attribute", searchAttributeTabbox, Pages.CREATE_ATTRIBUTE_EVENT,null,false);
		searchTab.setSelected(true);
	}

	private void init(){
		
		usedbylist = new ListModelList<String>();
		usedbylist.add(CommonConstants.RESOURCE);
//		usedbylist.add(CommonConstants.WAREHOUSE);
		
		selRel.setModel(usedbylist);
		
		List<String> selList = new ArrayList<String>();
		selList.add(CommonConstants.RESOURCE);
		usedbylist.setSelection(selList);
		
	}
	public void onClick$btnCreate(Event event) {
		
			
	}

	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		
		
	}
	
	public void onClick$btnSearch(Event event) {
		
		Logger.logTrace(MODULE, "Inside btnSearch ");
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		
//		Logger.logTrace(MODULE, "selection... "+usedbylist.getSelection());
		
		AttributeVO data = new AttributeVO();
		AttributeBD  attributeBD = new AttributeBD(getBDSessionContext());
		data.setName(txtName.getValue());
		
		if(usedbylist.getSelection().size() != 0){
			Set<String> list = usedbylist.getSelection();
			Iterator<String> itr = list.iterator();
			String usedBy = itr.next();
			data.setUsedBy(usedBy);
		}
		data.setUsedBy(CommonConstants.RESOURCE);
		
		Logger.logTrace(MODULE, "calling BD ");
		List<AttributeVO> listWarehouseData = attributeBD.searchAttributeData(data);
		Logger.logTrace(MODULE, "after call "+listWarehouseData);
		if(listWarehouseData != null && !listWarehouseData.isEmpty()){
			searchResultGrid.setModel(new ListModelList<AttributeVO>(listWarehouseData));
			
			searchResultGrid.setItemRenderer(new SearchListItemRenderer());
		}else{
			searchResultGrid.setModel(new ListModelList<AttributeVO>());
		}
	}
	
	public void onClick$btnCancel(Event event) {
		resetComponents(txtName, txtName,selRel);
	}
	public void clickEdit(){
		Logger.logTrace("MODULE", "in clickEdit function...");
		
		if(searchResultGrid.getSelectedItem()!=null){
			AttributeVO wrapperData =(AttributeVO) searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			
			argMap.put(SEARCH_COMPOSER_REF, this);
			Logger.logTrace("MODULE", "getAttributeId : "+wrapperData.getAttributeId());
			addViewTab(wrapperData.getAttributeId(), wrapperData.getName(), searchAttributeTabbox, Pages.VIEW_ATTRIBUTE_EVENT,argMap);
			
		}
		
	}
	
	private class SearchListItemRenderer implements ListitemRenderer<AttributeVO>{

		private EventListener<Event> editItemListener,editHoverListner,editOutListener;
		SimpleDateFormat dateFormat = new SimpleDateFormat(getDateTimeFormat());
		
		public SearchListItemRenderer() {
			
			editItemListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
			//		Image img = (Image) event.getTarget();
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
		public void render(Listitem item, AttributeVO data, int index)throws Exception {
			
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(data.getName()));
			item.appendChild(new Listcell(data.getUsedBy()));
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
