package com.elitecore.cpe.web.system.agent;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.system.agent.ViewAgentRunHistoryVO;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewHistoryComposer extends BaseModuleViewComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static final String SEARCH_AGENT_HISTORY_COMPOSER_REF = "_searchagenthistoryComp";
	private Datebox startdate,enddate;
	public static final String VIEW_ENTITY_ID_KEY = "_viewEntityId";
//	private static final String MODULE = "VIEW-HISTORY";
	private Listbox historyLB;
	


	
	
	
	
	public void onDoubleClick$historyLB(Event event) throws Exception {
		
		if(historyLB.getSelectedItem()!=null){
			ViewAgentRunHistoryVO historyVO=  historyLB.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put(ViewHistoryPopupComposer.CREATE_HISTORY_POPUP_REF, historyVO);
			Window window = (Window)Executions.createComponents(Pages.VIEW_AGENT_HISTORY, null, argMap );
			window.doModal();

		}
	}

	
		public void onClick$btnSearch(Event event) {
		SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		try {
			historyLB.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
			historyLB.setVisible(true);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(enddate.getValue());
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			List<ViewAgentRunHistoryVO> historyVO = agentBD.findAgentRunHistory(getViewEntityId(),new Timestamp(startdate.getValue().getTime()),new Timestamp((calendar.getTime()).getTime()));
			
			ListModelList<ViewAgentRunHistoryVO> modelList = new ListModelList<ViewAgentRunHistoryVO>(historyVO);
			if(historyVO!=null && !historyVO.isEmpty()) {
				
				historyLB.setModel(modelList);
				historyLB.setItemRenderer(new HistoryRenderer());
				historyLB.setVisible(true);
			} else {
				historyLB.setVisible(true);
				historyLB.setModel(new ListModelList<ViewAgentRunHistoryVO>());
				
			}
			
			
			
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	private class HistoryRenderer implements ListitemRenderer<ViewAgentRunHistoryVO> {

		SimpleDateFormat dateFormat = new SimpleDateFormat(getDateTimeFormat());
		
		@Override
		public void render(Listitem item, ViewAgentRunHistoryVO data, int index)
				throws Exception {
			
			item.appendChild(new Listcell(dateFormat.format(data.getExecutionstartdate())));
			item.appendChild(new Listcell(dateFormat.format(data.getExecutionstopate())));
			item.appendChild(new Listcell(data.getTotalMasterEntities().toString()));
			item.appendChild(new Listcell(data.getTotalEntities().toString()));
			item.appendChild(new Listcell(data.getTotalSuccessMasterEntities().toString()));
			item.appendChild(new Listcell(data.getTotalSuccessEntities().toString()));

			item.setValue(data);
			} 
		}




	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {

	}


}
