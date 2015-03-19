package com.elitecore.cpe.web.composer.audit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.data.system.audit.EntityTypeEnum;
import com.elitecore.cpe.bl.delegates.system.audit.ConfigureAuditBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.system.audit.SearchWsAuditVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.InvalidDataException;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class SearchWsAuditComposer extends BaseSearchComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String SEARCH_BUCKET_TYPE_COMPOSER_REF = "_searchBucketTypeComp";

	private static final String MODULE = "SEARCH-AUDIT";
	public static final String SEARCH_AUDIT_COMPOSER_REF = "_searchAuditComp";
	
	private Datebox auditfrom,auditto;
	private Listbox searchResultGrid;
	private Textbox txtInputParam,txtOutputParam;
//	private Tabbox searchAuditTabbox;
	private Combobox eventTypes,status;
	private Window searchWsAuditWin;
	
	
	public void onClick$btnCancel(Event event){
		resetComponents(txtInputParam,txtInputParam,txtOutputParam,eventTypes,status,auditfrom,auditto);
	}
	
	
	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		
		
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		this.searchWsAuditWin = comp;
		fecthData();
	}
	
	
	public void fecthData() {
		//SystemInternalBD internalBD = new SystemInternalBD(getBDSessionContext());
		try {
			List<ComboData> comboDatas = new ArrayList<ComboData>();
			
			for(EntityTypeEnum modeAction : EntityTypeEnum.values()) {
				comboDatas.add(new ComboData(modeAction.getId(), modeAction.name()));
			}
			
			
			if(comboDatas != null) {
				eventTypes.setModel(new ListModelList<ComboData>(comboDatas));
				eventTypes.setItemRenderer(new ComboItemDataRenderer());
			}
			
			
			List<ComboBoxData> datas = new ArrayList<ComboBoxData>();
			datas.add(new ComboBoxData(CPECommonConstants.SUCCESS, CPECommonConstants.SUCCESS));
			datas.add(new ComboBoxData(CPECommonConstants.ERROR, CPECommonConstants.ERROR));
			status.setModel(new ListModelList<ComboBoxData>(datas));
			status.setItemRenderer(new ComboBoxItemDataRenderer());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void onClick$btnSearch(Event event) {
		
				
		ConfigureAuditBD configureAuditBD = new ConfigureAuditBD(getBDSessionContext());
		
		List<SearchWsAuditVO> auditsearch = null;
		try {

			Long eventId = null;String eventStatus = null;
			if(eventTypes.getSelectedItem()!=null) {
				ComboData comboData = eventTypes.getSelectedItem().getValue();
				eventId = comboData.getId();
			}
			
			if(status.getSelectedItem()!=null) {
				ComboBoxData comboData = status.getSelectedItem().getValue();
				eventStatus = comboData.getId();
			}
			
			if(auditfrom.getValue()!=null && auditto.getValue()!=null) {
				if(auditfrom.getValue().after(auditto.getValue())) {
					throw new InvalidDataException("FromDate can not be greater than toDate");
				}
			}
			searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
			searchResultGrid.setVisible(true);
			
			Date from = null;
			Date to = null;
			boolean isTodate = false;
			if(auditfrom.getValue()!=null) {
				from =auditfrom.getValue();
				
				if(auditto.getValue()!=null) {
					isTodate  = true;
					Calendar cal = Calendar.getInstance();
					cal.setTime(auditto.getValue());
					
					cal.set(Calendar.HOUR_OF_DAY, 23);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.SECOND, 59);
					
					to = cal.getTime();
				} else {
					Calendar cal = Calendar.getInstance();
					Calendar currcal = Calendar.getInstance();
					
					cal.set(Calendar.HOUR_OF_DAY, currcal.get(Calendar.HOUR_OF_DAY));
					cal.set(Calendar.MINUTE, currcal.get(Calendar.MINUTE));
					cal.set(Calendar.SECOND, currcal.get(Calendar.SECOND));
					cal.set(Calendar.MILLISECOND, currcal.get(Calendar.MILLISECOND));
					
					to = cal.getTime();
				}
				
				
			}
			
			if(!isTodate) {
				if(auditto.getValue()!=null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(auditto.getValue());
					
					cal.set(Calendar.HOUR_OF_DAY, 23);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.SECOND, 59);
					
					to = cal.getTime();
				}
			}
			
			
			Logger.logTrace(MODULE, "from :::" +from);
			Logger.logTrace(MODULE, "to :::" +to);
			
			auditsearch = configureAuditBD.searchWsAudit(from,to,eventId,txtInputParam.getValue(),txtOutputParam.getValue(),eventStatus);
//			}
			if(auditsearch!=null && !auditsearch.isEmpty()) {
				searchResultGrid.setModel(new ListModelList<SearchWsAuditVO>(auditsearch));
				searchResultGrid.setItemRenderer(new SearchListItemRenderer());
				
			} else {
				searchResultGrid.setModel(new ListModelList<SearchWsAuditVO>());
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
			SearchWsAuditVO auditWrapper=  searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put(SEARCH_AUDIT_COMPOSER_REF, auditWrapper);
//			addViewTab(auditWrapper.getWsAuditId(), auditWrapper.getEventName(), searchAuditTabbox, Pages.VIEW_AUDIT,argMap);
			Window window = (Window)Executions.createComponents(Pages.VIEW_WS_AUDIT, this.searchWsAuditWin, argMap);
	        window.doModal();
		}
	}
	
	
		private class SearchListItemRenderer implements ListitemRenderer<SearchWsAuditVO>{
		
		private SimpleDateFormat dateFormat ;
		private EventListener<Event> inputItemListener,inputHoverListner,inputOutListener;
		
		public SearchListItemRenderer() {
			dateFormat = new SimpleDateFormat(getDateTimeFormat());
			
			inputItemListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
				//	Image img = (Image) event.getTarget();
					clickEdit();
				}

			};
			
			inputHoverListner = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					Image img = (Image) event.getTarget();
					img.setSrc(BaseConstants.EDIT_ITEM_HOVER_IMAGE);
				}
			};
			
			inputOutListener = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					Image img = (Image) event.getTarget();
					img.setSrc(BaseConstants.EDIT_ITEM_IMAGE);
				}
			};
			
	/*		outputItemListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					Image img = (Image) event.getTarget();
					clickEdit();
				}

			};
			
			outputHoverListner = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					Image img = (Image) event.getTarget();
					img.setSrc(BaseConstants.EDIT_ITEM_HOVER_IMAGE);
				}
			};
			
			outputOutListener = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					Image img = (Image) event.getTarget();
					img.setSrc(BaseConstants.EDIT_ITEM_IMAGE);
				}
			};
			*/
			
		}
		
		
		@Override
		public void render(Listitem item, SearchWsAuditVO data, int index)
				throws Exception {
			int no = index + 1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(data.getEventName()));
			item.appendChild(new Listcell(data.getStatus()));
			item.appendChild(new Listcell(data.getResponseCode()));
			item.appendChild(new Listcell(data.getResponseMessage()));
			item.appendChild(new Listcell(dateFormat.format(data.getProcessedDate())));
			
			Listcell operationCell = new Listcell();
			Image input = new Image(BaseConstants.EDIT_ITEM_IMAGE);
			input.addEventListener(Events.ON_MOUSE_OVER, inputHoverListner);
			input.addEventListener(Events.ON_MOUSE_OUT, inputOutListener);
			input.addEventListener(Events.ON_CLICK, inputItemListener);
			
			/*Listcell outputCell = new Listcell();
			Image output = new Image(BaseConstants.EDIT_ITEM_IMAGE);
			input.addEventListener(Events.ON_MOUSE_OVER, outputHoverListner);
			input.addEventListener(Events.ON_MOUSE_OUT, outputOutListener);
			input.addEventListener(Events.ON_CLICK, outputItemListener);*/
			
			operationCell.appendChild(input);
//			outputCell.appendChild(output);
			
			item.appendChild(operationCell);
//			item.appendChild(outputCell);
			item.setValue(data);
			
		}
		
	}

}
