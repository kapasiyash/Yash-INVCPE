package com.elitecore.cpe.web.system.agent;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.system.agent.AgentVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
//import com.elitecore.cpe.bl.vo.plugins.SearchEventPluginClassVO;

public class SearchAgentComposer extends BaseSearchComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MODULE = "SEARCH-AGENT";

	private static final String SEARCH_AGENT_COMPOSER_REF = "_searchAgentComp";
//	private Window searchAgentWin;
	private Textbox txtName;
	private Listbox searchResultGrid;
	private Tabbox searchAgentTabbox;
	
	
	
	
	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
		/*Map<String,Object> argMap = new HashMap<String, Object>();
//		Long random = 0l;
		if(searchResultGrid.getSelectedItem()!=null && searchResultGrid.getSelectedItem().getValue()!=null){
			AgentVO agentVO = searchResultGrid.getSelectedItem().getValue();
			String agentId = agentVO.getAgentId();
//			random = Long.parseLong(agentId.substring(3, agentId.length()-1));
			argMap.put(ViewAgentComposer.VIEW_AGENT_COMPOSER_REF, agentId);
			argMap.put(SEARCH_AGENT_COMPOSER_REF, this);
			Logger.logTrace(MODULE, "Arg Map " + argMap);
			addViewTab(random, agentVO.getName(), searchAgentTabbox, Pages.VIEW_AGENT, argMap);
			addViewTab(agentId, agentVO.getName(), searchAgentTabbox, Pages.VIEW_AGENT, argMap);
		}*/
		
				
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
	//	this.searchAgentWin = comp;
		onClick$btnSearch(null);
	}
	
	public void onClick$btnCancel(Event event) {
		resetComponents(txtName, txtName);
	}
	
	public void onClick$btnSearch(Event event) {
		
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		
		
		List<AgentVO> agents = null;
		SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		try {
//			agents = agentBD.getAllAgentsListByName(txtName.getValue());
			agents = agentBD.getAllAgentsListByName(null);
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		} 
		
		ListModelList<AgentVO> model = new ListModelList<AgentVO>(agents);
		searchResultGrid.setModel(model);
		searchResultGrid.setItemRenderer(new SearchItemRenderer());


	}
	
	private void clickEdit() {
		Map<String,Object> argMap = new HashMap<String, Object>();
//		Long random = 0l;
		if(searchResultGrid.getSelectedItem()!=null && searchResultGrid.getSelectedItem().getValue()!=null){
			AgentVO agentVO = searchResultGrid.getSelectedItem().getValue();
			String agentId = agentVO.getAgentId();
//			random = Long.parseLong(agentId.substring(3, agentId.length()-1));
			argMap.put(ViewAgentComposer.VIEW_AGENT_COMPOSER_REF, agentId);
			argMap.put(SEARCH_AGENT_COMPOSER_REF, this);
			Logger.logTrace(MODULE, "Arg Map " + argMap);
			/*addViewTab(random, agentVO.getName(), searchAgentTabbox, Pages.VIEW_AGENT, argMap);*/
			addViewTab(agentId, agentVO.getName(), searchAgentTabbox, Pages.VIEW_AGENT, argMap);
		}
		
	}
	
	
	private class SearchItemRenderer implements ListitemRenderer<AgentVO>{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(getDateTimeFormat());
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
		public void render(Listitem item, AgentVO data, int index)
				throws Exception {
			int no = index + 1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(data.getName()));
			item.appendChild(new Listcell(data.getDescription()));
			item.appendChild(new Listcell(dateFormat.format(data.getCreatedDate())));
			if(data.getModifiedDate()!=null) {
				item.appendChild(new Listcell(dateFormat.format(data.getModifiedDate())));
			} else {
				item.appendChild(new Listcell("Not Modified"));
			}
			
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
