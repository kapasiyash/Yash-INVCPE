package com.elitecore.cpe.web.configuration.notification;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
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
import com.elitecore.cpe.bl.delegates.notification.NotificationBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.configuration.notification.NotificationCategoryVO;
import com.elitecore.cpe.bl.vo.configuration.notification.SearchDocumentTemplateVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public class SearchNotificationTemplateComposer extends BaseSearchComposer {


	private static final long serialVersionUID = 1L;
	private static final String MODULE = "SEARCH-DOCUMENT-TEMPLATE";

	private static final String SEARCH_DOCUMENT_TEMPLATE_COMPOSER_REF = "_searchDocumentTemplateComp";
	private Tab searchDocumentTemplateTab;
	private Combobox docCategoryList;
	private Textbox txtName;
	private Tabbox searchDocTemplateTabbox;
	private Listbox searchResultGrid;
	
	
	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		/*if(searchResultGrid.getSelectedItem()!=null){
			SearchDocumentTemplateVO wrapperData =(SearchDocumentTemplateVO) searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put(SEARCH_COMPOSER_REF, this);
			addViewTab(wrapperData.getDocumentId(), wrapperData.getName(), searchDocTemplateTabbox, Pages.VIEW_DOCUMENT_TEMPLATE,argMap);
		}*/
		
	}
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		populatedata();
		addViewTab("-1", "Create Document Template", searchDocTemplateTabbox, Pages.CREATE_DOCUMENT_TEMPLATE,null,false);
		searchDocumentTemplateTab.setSelected(true);
		
	}
	
	
	private void populatedata() {
		System.out.println("In Populate Data");
		NotificationBD notificationBD = new NotificationBD(getBDSessionContext());
		try {
			List<NotificationCategoryVO> categoryComboDatas = notificationBD.findAllTemplateCategory();
			
			if(categoryComboDatas!=null && !categoryComboDatas.isEmpty()) {
				docCategoryList.setModel(new ListModelList<NotificationCategoryVO>(categoryComboDatas));
				docCategoryList.setItemRenderer(new NotificationItemDataRenderer());
			}
			
		
			
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
	}
	public static class NotificationItemDataRenderer implements ComboitemRenderer<NotificationCategoryVO>{

		private Combobox combobox ;
		private Long id;
		

		public NotificationItemDataRenderer() {
			super();
		}
		public NotificationItemDataRenderer(Combobox combobox, Long id) {
			super();
			this.combobox = combobox;
			this.id = id;
		}


		@Override
		public void render(Comboitem comboItem, NotificationCategoryVO comboData,int index) throws Exception {
			comboItem.setValue(comboData);
			comboItem.setLabel(comboData.getName());
			if(combobox !=null && id !=null && id.equals(comboData.getId())){
				combobox.setSelectedItem(comboItem);
			}
		}

	}
	
	public void onClick$btnSearch(Event event) {
		NotificationBD notificationBD = new NotificationBD(getBDSessionContext());
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		Long categoryId= 0L;
		if(docCategoryList.getSelectedItem()!=null) {
			NotificationCategoryVO comboData = docCategoryList.getSelectedItem().getValue();
			categoryId = comboData.getId();
		}
		try {
			System.out.println("::txtName.getValue()"+txtName.getValue()+":::categoryId:::"+categoryId);
			List<SearchDocumentTemplateVO> documentTemplateVOs = notificationBD.searchDocumentTemplate(txtName.getValue(),categoryId);
				

			if(documentTemplateVOs!=null && !documentTemplateVOs.isEmpty()){
				
				ListModelList<SearchDocumentTemplateVO> modelList = new ListModelList<SearchDocumentTemplateVO>(documentTemplateVOs);
				searchResultGrid.setModel(modelList);
				searchResultGrid.setItemRenderer(new SearchItemRenderer());

			}
			else{
				searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
				searchResultGrid.setModel(new ListModelList<SearchDocumentTemplateVO>());
				searchResultGrid.setVisible(true);

			}
			
		} catch (WrongValueException e) {
			e.printStackTrace();
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void clickEdit() {
		
		if(searchResultGrid.getSelectedItem()!=null){
			SearchDocumentTemplateVO wrapperData =(SearchDocumentTemplateVO) searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put(SEARCH_COMPOSER_REF, this);
			addViewTab(wrapperData.getDocumentId(), wrapperData.getName(), searchDocTemplateTabbox, Pages.VIEW_DOCUMENT_TEMPLATE,argMap);
		}
		
	}
	
	
	private class SearchItemRenderer implements ListitemRenderer<SearchDocumentTemplateVO> {

		private SimpleDateFormat dateFormat;
		private EventListener<Event> editItemListener,editHoverListner,editOutListener;
		
		public SearchItemRenderer() {
			dateFormat = new SimpleDateFormat(getDateFormat());
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
			public void render(Listitem item, SearchDocumentTemplateVO data,int index) throws Exception {
				
				item.setValue(data);
				item.appendChild(new Listcell(data.getName()));
				item.appendChild(new Listcell(data.getDocumentCategory()));
				item.appendChild(new Listcell(data.getDescription()));
				item.appendChild(new Listcell(dateFormat.format(data.getValidFormDate())));
				item.appendChild(new Listcell(dateFormat.format(data.getValidToDate())));
				item.appendChild(new Listcell(dateFormat.format(data.getCreatedDate())));
				item.appendChild(new Listcell(dateFormat.format(data.getLastModifiedDate())));
				item.appendChild(new Listcell(data.getEnableEmail().toString()));
				item.appendChild(new Listcell(data.getEnableSms().toString()));
				Listcell operationCell = new Listcell();
				Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
				
				edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
				edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
				edit.addEventListener(Events.ON_CLICK, editItemListener);
				
				operationCell.appendChild(edit);
				item.appendChild(operationCell);
			}
		}
	public void onClick$btnCancel(Event event){
			resetComponents(txtName, txtName,docCategoryList);
		}

}
