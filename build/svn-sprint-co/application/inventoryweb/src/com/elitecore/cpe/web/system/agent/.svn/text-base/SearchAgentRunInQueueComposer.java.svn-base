package com.elitecore.cpe.web.system.agent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.system.AgentConstants;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.system.agent.SearchAgentRunInQueueVO;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class SearchAgentRunInQueueComposer extends BaseSearchComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private static final String SEARCH_AGENT_INQUEUE_COMPOSER_REF = "_searchAgentInQueueComp";
	
	private Listbox searchResultGrid;
	private Textbox txtAgentName,txtSchedueName;
	private Combobox statusCombo;
	
	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {

	}
	
	public void onClick$btnCancel(Event event) {
		resetComponents(txtAgentName,txtAgentName,txtSchedueName,statusCombo);
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		
		
		List<ComboData> status = new ArrayList<ComboData>();
		status.add(new ComboData(1l, AgentConstants.NOT_STARTED));
		status.add(new ComboData(2l, AgentConstants.IN_PROGRESS));
		
			if(status!=null){
				sortComboDatas(status);
				statusCombo.setModel(new ListModelList<ComboData>(status));
				statusCombo.setItemRenderer(new ComboItemDataRenderer());
			}
		
		
	}
	
	
public void onClick$btnSearch(Event event) {
		
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		
		SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		try {
			Long id= 0l;
			if(statusCombo.getSelectedItem()!=null){
				ComboData category = statusCombo.getSelectedItem().getValue();
				id = category.getId();
			}
			String status = null;
			if(id.equals(1L)) {
				status=AgentConstants.EXECUTION_STATUS_NOT_STARTED;
			} else if(id.equals(2L)) {
				status=AgentConstants.EXECUTION_STATUS_IN_PROGRESS;
			}
			
			
			List<SearchAgentRunInQueueVO> list = agentBD.findAgentRunInQueue(txtAgentName.getValue(),txtSchedueName.getValue(),status);
			
			if(list!=null && !list.isEmpty()) {
				ListModelList<SearchAgentRunInQueueVO> model = new ListModelList<SearchAgentRunInQueueVO>(list);
				searchResultGrid.setModel(model);
				
				searchResultGrid.setItemRenderer(new ListitemRenderer<SearchAgentRunInQueueVO>() {

					private SimpleDateFormat dateFormat  = new SimpleDateFormat(getDateTimeFormat());
					
					
					@Override
					public void render(Listitem item, SearchAgentRunInQueueVO data, int index)
							throws Exception {
						int no = index + 1;
						item.appendChild(new Listcell(String.valueOf(no)));
						item.appendChild(new Listcell(dateFormat.format(data.getScheduleDueDate())));
						item.appendChild(new Listcell(data.getAgentName()));
						item.appendChild(new Listcell(data.getScheduleName()));
						item.appendChild(new Listcell(data.getStatus()));
						item.appendChild(new Listcell(data.getPriority()));
						item.appendChild(new Listcell(data.getExecutionType()));
						item.setValue(data);
					}
				});
			} else {
				searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
				searchResultGrid.setModel(new ListModelList<SearchAgentRunInQueueVO>());
			}
			
			
			
			
			
		} catch (WrongValueException e) {
			e.printStackTrace();
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
