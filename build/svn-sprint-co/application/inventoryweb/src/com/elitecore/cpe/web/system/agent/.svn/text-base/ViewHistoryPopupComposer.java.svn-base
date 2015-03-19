package com.elitecore.cpe.web.system.agent;

import java.text.SimpleDateFormat;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.vo.system.agent.ViewAgentRunHistoryVO;
import com.elitecore.cpe.web.base.ui.core.BaseComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewHistoryPopupComposer extends BaseComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Window History;
	private Label lbstartdate,lbstopdate,lbRemarks,lbMaster,lbEntity,lbMasterSucced,lbEntitySucced,lbStatus;
	public static final String CREATE_HISTORY_POPUP_REF = "_createHistoryRef";
	



	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		ViewAgentRunHistoryVO historyVO = null;
		
		if(Executions.getCurrent().getArg().containsKey(CREATE_HISTORY_POPUP_REF)){
			historyVO = (ViewAgentRunHistoryVO) Executions.getCurrent().getArg().get(CREATE_HISTORY_POPUP_REF);
			
			populateData(historyVO);
		}
		
		
		
	}

	private void populateData(ViewAgentRunHistoryVO historyVO) {
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(getDateTimeFormat());
		
		lbstartdate.setValue(dateFormat.format(historyVO.getExecutionstartdate()));
		lbstopdate.setValue(dateFormat.format(historyVO.getExecutionstopate()));
		lbRemarks.setValue(historyVO.getRemarks());
		lbMaster.setValue(historyVO.getTotalMasterEntities().toString());
		lbEntity.setValue(historyVO.getTotalEntities().toString());
		lbMasterSucced.setValue(historyVO.getTotalSuccessMasterEntities().toString());
		lbEntitySucced.setValue(historyVO.getTotalSuccessEntities().toString());
		lbStatus.setValue(historyVO.getStatus());
		
	}
	
	
	public void onClick$btnOk(Event event){
		History.detach();
	}

}
