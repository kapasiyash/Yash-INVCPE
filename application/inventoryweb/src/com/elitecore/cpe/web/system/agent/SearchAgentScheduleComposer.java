package com.elitecore.cpe.web.system.agent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.elitecore.cpe.bl.constants.system.AgentConstants;
import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.system.agent.SearchAgentScheduleVO;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class SearchAgentScheduleComposer extends BaseSearchComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private static final String MODULE = "SEARCH-AGENT-SCHEDULE";

	private static final String SEARCH_AGENT_SCHEDULE_COMPOSER_REF = "_searchComposerRef";
	private Textbox txtName;
	private Listbox searchResultGrid;
	private Tabbox searchAgentScheduleTabbox;
	private Combobox statusCombo;
//	private Button btnCreate;
	private Tab searchTab;
	
	public void onClick$btnCancel(Event event) {
		resetComponents(txtName, txtName,statusCombo);
	}
	
	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		
		/*if(searchResultGrid.getSelectedItem()!=null){
			SearchAgentScheduleVO scheduleVO=  searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put(SEARCH_AGENT_SCHEDULE_COMPOSER_REF, this);
			addViewTab(scheduleVO.getAgentScheduleId(), scheduleVO.getScheduleName(), searchAgentScheduleTabbox, Pages.VIEW_AGENT_SCHEDULE,argMap);
		}*/
		
	}
	

	public void onClick$btnCreate(Event event) {
		if (moduleContent != null
				&& moduleContent.getChildren() != null) {
			moduleContent.getChildren().clear();
		}
		
		Executions.createComponents(Pages.CREATE_AGENT_SCHEDULE, moduleContent, null);
	}
	

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		/*if(isPermittedAction(ActionAlias.CREATE_AGENT_SCHEDULE)) {
			btnCreate.setVisible(true);
		}*/
		
		addViewTab("-1", "Create Agent Schedule", searchAgentScheduleTabbox, Pages.CREATE_AGENT_SCHEDULE,null,false);
		
		searchTab.setSelected(true);
		SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		try {
			List<ComboBoxData> statusComboList = agentBD.findAllScheduleStatus();
			sortComboBoxDatas(statusComboList);
			statusCombo.setModel(new ListModelList<ComboBoxData>(statusComboList));
			statusCombo.setItemRenderer(new ComboBoxItemDataRenderer());
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
	}
	
	public void onClick$btnSearch(Event event) {
		
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		
		SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		try {
			String statusId = "";
			if(statusCombo.getSelectedItem()!=null){
				ComboBoxData status = statusCombo.getSelectedItem().getValue();
				statusId = status.getId();
			}
			List<SearchAgentScheduleVO> list = agentBD.findSystemAgentSchedule(txtName.getValue(),statusId);
			List<SearchAgentScheduleVO> filteredList = filterAgentSchedule(list);
			
			ListModelList<SearchAgentScheduleVO> model = new ListModelList<SearchAgentScheduleVO>(filteredList);
			searchResultGrid.setModel(model);
			
			searchResultGrid.setItemRenderer(new SearchItemRenderer());
			
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
			SearchAgentScheduleVO scheduleVO=  searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put(SEARCH_AGENT_SCHEDULE_COMPOSER_REF, this);
			addViewTab(scheduleVO.getAgentScheduleId(), scheduleVO.getScheduleName(), searchAgentScheduleTabbox, Pages.VIEW_AGENT_SCHEDULE,argMap);
		}
	}
	
	
	private class SearchItemRenderer implements ListitemRenderer<SearchAgentScheduleVO>{
		
		private SimpleDateFormat dateFormat  = new SimpleDateFormat(getDateTimeFormat());
		private EventListener<Event> editItemListener,editHoverListner,editOutListener;
		
		public SearchItemRenderer() {
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
		public void render(Listitem item, SearchAgentScheduleVO data, int index)
				throws Exception {
			
			int no = index + 1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(data.getScheduleName()));
			item.appendChild(new Listcell(data.getAgentName()));
			item.appendChild(new Listcell(data.getStatus()));
			item.appendChild(new Listcell(dateFormat.format(data.getExecutionStartDate())));
		
			if(data.getLastExecutionDate()!=null) {
				item.appendChild(new Listcell(dateFormat.format(data.getLastExecutionDate())));
			} else {
				item.appendChild(new Listcell("Not Executed"));
			}
			if(data.getNextScheduleDate()!=null){
				item.appendChild(new Listcell(dateFormat.format(data.getNextScheduleDate())));
			}else{
				item.appendChild(new Listcell("-"));
			}
			item.appendChild(new Listcell(dateFormat.format(data.getCreatedDate())));
			
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
	
	
	
	private List<SearchAgentScheduleVO> filterAgentSchedule(List<SearchAgentScheduleVO> list) {
		
		List<SearchAgentScheduleVO> scheduleVOs = new ArrayList<SearchAgentScheduleVO>();
			for(SearchAgentScheduleVO agentScheduleVO : list) {
				if(agentScheduleVO.getExecutionType().equals(AgentConstants.EXECUTION_TYPE_AUTOMATIC_EXECUTION)) {
					scheduleVOs.add(agentScheduleVO);
				}
				if(agentScheduleVO.getExecutionType().equals(AgentConstants.EXECUTION_TYPE_MANUAL_EXECUTION) && !agentScheduleVO.isActive()/*agentScheduleVO.getStatus().equals("Not Started")*/) {
					scheduleVOs.add(agentScheduleVO);
				}
			}
		
		return scheduleVOs;
	}

}
