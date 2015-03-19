 package com.elitecore.cpe.web.composer.audit;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.system.audit.ConfigureAuditBD;
import com.elitecore.cpe.bl.delegates.system.internal.SystemInternalBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditPaggingVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditWrapper;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.InvalidDataException;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class SearchAuditComposer extends BaseSearchComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String SEARCH_BUCKET_TYPE_COMPOSER_REF = "_searchBucketTypeComp";

	private static final String MODULE = "SEARCH-AUDIT";
	public static final String SEARCH_AUDIT_COMPOSER_REF = "_searchAuditComp";
	
	private Datebox auditfrom,auditto;
	private Listbox searchResultGrid;
	private Tabbox searchAuditTabbox;
//	private Textbox txtUser;
	private Combobox modeAction,cmbModule;
	
	
	private Paging userPaging;
	private boolean _needsTotalSizeUpdate;
	private int _startPageNumber = 0;
	private int totalRecords = 0;
	
	public void onClick$btnCancel(Event event){
		resetComponents(modeAction,modeAction,auditfrom,auditto,cmbModule);
		modeAction.setModel(null);
	}
	
	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		/*if(searchResultGrid.getSelectedItem()!=null){
			SearchAuditVO auditWrapper=  searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put(SEARCH_AUDIT_COMPOSER_REF, this);
			addViewTab(auditWrapper.getSystemAuditId(), auditWrapper.getAuditAction(), searchAuditTabbox, Pages.VIEW_AUDIT,argMap);
		}*/
		
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		fecthData();
		
		auditfrom.setValue(new Date());
		auditto.setValue(new Date());
		
		final SystemInternalBD internalBD = new SystemInternalBD(getBDSessionContext());
		cmbModule.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				

				if(cmbModule.getSelectedItem()!=null) {
					ComboData comboData = cmbModule.getSelectedItem().getValue();
					Long typeId = comboData.getId();
					List<ComboBoxData> comboDatas = internalBD.getAllSystemActionByModuleId(typeId);
					resetComponents(modeAction, modeAction);
					if(comboDatas!=null && !comboDatas.isEmpty()) {
						modeAction.setModel(new ListModelList<ComboBoxData>(comboDatas));
						modeAction.setItemRenderer(new ComboBoxItemDataRenderer());
					} else {
						modeAction.setModel(new ListModelList<ComboBoxData>());
					}
				}
				
			}
		});
		
	}
	
	
	
	private <T> void refreshModel(int activePage){
        userPaging.setPageSize(getPageSize());
        PagingListModel<T> model = new PagingListModel<T>(activePage) {

			@Override
			protected List<T> getPageData(int itemStartNumber, int pageSize) {
				ConfigureAuditBD eventBD = new ConfigureAuditBD(getBDSessionContext());
				
				SearchAuditPaggingVO searchEventTrackerVO;
				try {
					searchEventTrackerVO = prepareSearchDataVO();
					searchEventTrackerVO.setPageSize(pageSize);
					searchEventTrackerVO.setItemStartNumber(itemStartNumber);
					
					List<SearchAuditVO> trackerVOs = null;
					
					try {
						SearchAuditWrapper result = eventBD.searchAudit(searchEventTrackerVO);
						trackerVOs = result.getSearchAuditVOs();
						totalRecords = result.getTotalRecords();
					} catch (SearchBLException e) {
						e.printStackTrace();
					} 
					
					return (List<T>) trackerVOs;
					
					
				} catch (InvalidDataException e1) {
					e1.printStackTrace();
					return null;
				}
			}


			
		};
         
        int _totalSize = totalRecords;
		if(_needsTotalSizeUpdate) {
            _totalSize = model.getTotalSize();
            _needsTotalSizeUpdate = false;
        }
         
        userPaging.setTotalSize(_totalSize);
         
        searchResultGrid.setModel(model);  
    }
	

	private SearchAuditPaggingVO prepareSearchDataVO() throws InvalidDataException {
		
		SearchAuditPaggingVO searchAuditPaggingVO = new SearchAuditPaggingVO();
		String actionAlias = null;
		Long moduleId = null;
		if(modeAction.getSelectedItem()!=null) {
			ComboBoxData comboData = modeAction.getSelectedItem().getValue();
			actionAlias = comboData.getId();
		}
		if(cmbModule.getSelectedItem()!=null) {
			ComboData comboData = cmbModule.getSelectedItem().getValue();
			moduleId = comboData.getId();
		}
		
		if(auditfrom.getValue()!=null && auditto.getValue()!=null) {
			if(auditfrom.getValue().after(auditto.getValue())) {
				throw new InvalidDataException("FromDate can not be greater than toDate");
			}
		}
		
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		
		
		Timestamp from = null;
		Timestamp to = null;
		if(auditfrom.getValue()!=null) {
			from = new Timestamp(auditfrom.getValue().getTime());
			
			if(auditto.getValue()!=null) {
				Logger.logTrace(MODULE, "toDatebox not null...");
				Calendar cal = Calendar.getInstance();
				cal.setTime(auditto.getValue());
				
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				
				to = new Timestamp(cal.getTime().getTime());
			} else {
				Calendar cal = Calendar.getInstance();
				Calendar currcal = Calendar.getInstance();
				
				cal.set(Calendar.HOUR_OF_DAY, currcal.get(Calendar.HOUR_OF_DAY));
				cal.set(Calendar.MINUTE, currcal.get(Calendar.MINUTE));
				cal.set(Calendar.SECOND, currcal.get(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, currcal.get(Calendar.MILLISECOND));
				
				to = new Timestamp(cal.getTime().getTime());
			}
			
			
		}else{
			if(auditto.getValue()!=null) {
				Logger.logTrace(MODULE, "toDatebox not null...");
				Calendar cal = Calendar.getInstance();
				cal.setTime(auditto.getValue());
				
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				
				to = new Timestamp(cal.getTime().getTime());
			} else {
				Calendar cal = Calendar.getInstance();
				Calendar currcal = Calendar.getInstance();
				
				cal.set(Calendar.HOUR_OF_DAY, currcal.get(Calendar.HOUR_OF_DAY));
				cal.set(Calendar.MINUTE, currcal.get(Calendar.MINUTE));
				cal.set(Calendar.SECOND, currcal.get(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, currcal.get(Calendar.MILLISECOND));
				
				to = new Timestamp(cal.getTime().getTime());
			}
			
		}
		
		Logger.logTrace(MODULE, "from :::" +from);
		Logger.logTrace(MODULE, "to :::" +to);
		
		searchAuditPaggingVO.setPageSize(getPageSize());
		searchAuditPaggingVO.setItemStartNumber(0);
		searchAuditPaggingVO.setFromDate(from);
		searchAuditPaggingVO.setToDate(to);
		searchAuditPaggingVO.setActionAlias(actionAlias);
		searchAuditPaggingVO.setModuleId(moduleId);
		
		return searchAuditPaggingVO;
	}
	
	
	public void onPaging$userPaging(ForwardEvent event){
        final PagingEvent pe = (PagingEvent) event.getOrigin();
         _startPageNumber = pe.getActivePage();
        refreshModel(_startPageNumber);
    }
	
	public void fecthData() {
		SystemInternalBD internalBD = new SystemInternalBD(getBDSessionContext());
		try {
			
			List<ComboData> comboModuleDatas = internalBD.getAllSystemModules();
			
			
					
			if(comboModuleDatas != null) {
				sortComboDatas(comboModuleDatas);
				cmbModule.setModel(new ListModelList<ComboData>(comboModuleDatas));
				cmbModule.setItemRenderer(new ComboItemDataRenderer());
			}
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void onClick$btnSearch(Event event) {
		
	
		
		ConfigureAuditBD configureAuditBD = new ConfigureAuditBD(getBDSessionContext());
		
		SearchAuditPaggingVO searchAuditPaggingVO = null;
		
		List<SearchAuditVO> auditsearch = null;
		try {

			
			searchAuditPaggingVO = prepareSearchDataVO();
			SearchAuditWrapper wrapper = configureAuditBD.searchAudit(searchAuditPaggingVO);
			
			auditsearch = wrapper.getSearchAuditVOs();
			if(auditsearch!=null && !auditsearch.isEmpty()) {
				searchResultGrid.setModel(new ListModelList<SearchAuditVO>(auditsearch));
				searchResultGrid.setItemRenderer(new SearchListItemRenderer());
				userPaging.setVisible(true);
				refreshModel(userPaging.getActivePage());
			} else {
				searchResultGrid.setVisible(true);
				searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
				searchResultGrid.setModel(new ListModelList<SearchAuditVO>());
				userPaging.setVisible(false);
			}
		} catch (WrongValueException e) {
			searchResultGrid.setVisible(false);
			MessageUtility.failureInformation("Error", "Please Enter Some Date in the Datebox");
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (InvalidDataException e) {
			MessageUtility.failureInformation("Invalid Data", e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private void clickEdit() {
		if(searchResultGrid.getSelectedItem()!=null){
			SearchAuditVO auditWrapper=  searchResultGrid.getSelectedItem().getValue();
			
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put(SEARCH_AUDIT_COMPOSER_REF, this);
			argMap.put("auditWrapper", auditWrapper);
			addViewTab(auditWrapper.getSystemAuditId(), auditWrapper.getAuditAction(), searchAuditTabbox, Pages.VIEW_AUDIT,argMap);
		}
	}
	
	
	/*//Provide below code to validate From Date and To Date as per JIRA MTCBSS 239
	public void onChange$auditto(Event event) {
		//auditto.setConstraint("after"+auditfrom.getValue());
		boolean flag=false;
		Calendar cal=null,cal_from=null;
		if(auditto.getValue()!=null) {
			
			 cal = Calendar.getInstance();
			cal.setTime(auditto.getValue());
//			cal.set(Calendar.HOUR_OF_DAY, 23);
//			cal.set(Calendar.MINUTE, 59);
//			cal.set(Calendar.SECOND, 59);
			
			if(auditfrom.getValue()!=null){
				 cal_from = Calendar.getInstance();
				cal_from.setTime(auditfrom.getValue());
				
				flag=true;
				
			}
		}
		if(flag){
			if(cal.getTime().getDate()<cal_from.getTime().getDate()){
				MessageUtility.successInformation("Message", "Please Enter Valid From Date and To Date ");
				//Messagebox.show("To Date can not be less than From date.");
				//auditto.setText("");
				auditto.setValue(null);
				return;
			}
		}  
		
	}
	public void onChange$auditfrom(Event event) {
		boolean flag=false;
		Calendar cal=null,cal_todate=null;
		if(auditfrom.getValue()!=null) {
			
			 cal = Calendar.getInstance();
			cal.setTime(auditfrom.getValue());
//			cal.set(Calendar.HOUR_OF_DAY, 23);
//			cal.set(Calendar.MINUTE, 59);
//			cal.set(Calendar.SECOND, 59);
			if(auditto.getValue()!=null){
				cal_todate = Calendar.getInstance();
				cal_todate.setTime(auditto.getValue());
				flag=true;
			}
		}
		if(flag){
			if(cal.getTime().getDate()>cal_todate.getTime().getDate()){
				MessageUtility.successInformation("Message", "Please Enter Valid From Date and To Date ");
				//Messagebox.show("To Date can not be less than From date.");
				
				auditfrom.setValue(null);
				
				
				return;
			}
		}
		
	}*/
/////////////////////////////////////////////////////////////////////////////////////////////	
	
		private class SearchListItemRenderer implements ListitemRenderer<SearchAuditVO>{
		
		private SimpleDateFormat dateFormat ;
		private EventListener<Event> editItemListener,editHoverListner,editOutListener;
		
		public SearchListItemRenderer() {
			dateFormat = new SimpleDateFormat(getDateTimeFormat());
			
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
		public void render(Listitem item, SearchAuditVO data, int index)
				throws Exception {
			int no = index + 1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(dateFormat.format(data.getAuditDate())));
			item.appendChild(new Listcell(data.getAuditAction()));
			item.appendChild(new Listcell(data.getUserName()));
			item.appendChild(new Listcell(data.getReason()));
			item.appendChild(new Listcell(data.getIpAddress()));
			item.appendChild(new Listcell(data.getAuditType()));
			
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
