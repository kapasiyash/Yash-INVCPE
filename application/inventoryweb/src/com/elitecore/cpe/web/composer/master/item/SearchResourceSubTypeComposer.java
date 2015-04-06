package com.elitecore.cpe.web.composer.master.item;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
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
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class SearchResourceSubTypeComposer extends BaseSearchComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Textbox txtName;
//	private Window searchResourceSubTypeWin;
	private Tabbox searchResourceSubTypeTabbox;
	private Listbox searchResultGrid;
	private Combobox comboResourceType;
	private Tab searchTab;


	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
	//	this.searchResourceSubTypeWin = comp;
		ItemBD itemBD = new ItemBD(getBDSessionContext()); 
		List<ComboData> comboData = itemBD.getAllResourceTypeData();
		if(comboData!=null && !comboData.isEmpty()) {
			comboResourceType.setModel(new ListModelList<ComboData>(comboData));
			comboResourceType.setItemRenderer(new ComboItemDataRenderer());
		}
		
		if(isPermittedAction(ActionAlias.CREATE_RESOURCESUBTYPE)) {
			//changes require to resolve JIRA MTCBSS-229
			addViewTab("-1", "Create Resource Subtype", searchResourceSubTypeTabbox, Pages.CREATE_RESOURCE_SUBTYPE,null,false);
			
		}
		
		searchTab.setSelected(true);
	}
	
	public void onClick$btnCreate(Event event) {
		if (moduleContent != null
				&& moduleContent.getChildren() != null) {
			moduleContent.getChildren().clear();
		}
		
		Executions.createComponents(Pages.CREATE_RESOURCE_TYPE, moduleContent, null);
	}
	
	
	public void onClick$btnCancel(Event event){
		resetComponents(txtName, txtName,comboResourceType);
	}
	

	public void onClick$btnSearch(Event event){
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		
		
		try {
			Long resourceTypeId = null;
			if(comboResourceType.getSelectedItem()!=null) {
				ComboData comboData = comboResourceType.getSelectedItem().getValue();
				resourceTypeId = comboData.getId();
			}
			
				List<SearchResourceSubTypeVO> searchData = itemBD.searchResourceSubTypeData(txtName.getValue(),resourceTypeId);
			
				System.out.println(searchData);
				if(searchData!=null && !searchData.isEmpty()){
					searchResultGrid.setModel(new ListModelList<SearchResourceSubTypeVO>(searchData));
					searchResultGrid.setItemRenderer(new SearchItemRenderer());
					searchResultGrid.setVisible(true);
				}else{
					searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
					searchResultGrid.setModel(new ListModelList<SearchResourceSubTypeVO>());
					searchResultGrid.setVisible(true);
				}
						
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void clickEdit() {
		
		if(searchResultGrid.getSelectedItem()!=null){
			SearchResourceSubTypeVO wrapperData =(SearchResourceSubTypeVO) searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			
			argMap.put(SEARCH_COMPOSER_REF, this);
			addViewTab(wrapperData.getResourceSubTypeId(), wrapperData.getName(), searchResourceSubTypeTabbox, Pages.VIEW_RESOURCE_SUBTYPE,argMap);
		}
		
	}
	
	
	private class SearchItemRenderer implements ListitemRenderer<SearchResourceSubTypeVO>{

		private EventListener<Event> editItemListener,editHoverListner,editOutListener;
		SimpleDateFormat dateFormat = new SimpleDateFormat(getDateTimeFormat());
		
		public SearchItemRenderer() {
			editItemListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
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
		public void render(Listitem item, SearchResourceSubTypeVO data, int index)
				throws Exception {
			int no = index + 1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(data.getName()));
			item.appendChild(new Listcell(data.getResourceTypeName()));
//			item.appendChild(new Listcell(data.getAlias()));
			item.appendChild(new Listcell(data.getDescription()));
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
