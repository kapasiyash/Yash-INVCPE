package com.elitecore.cpe.web.system.agent;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.system.agent.ViewAgentScheduleVO;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewAgentScheduleComposer extends BaseCPEViewComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Label lbName;
	private Label lbCreatedDate;
	private Label lbCreatedBy;
	private Label lbUpdatedBy;
	private Label lbUpdateDate;
	private Label lbReason,lbPriority,lbExeTypeId,lblastexecutionDate,lbnextexecutionDate,lbstartexecutionDate,lbreqnumofexecution;
	private LinkedList<ActionMenuItem> actionItemList;
	private LinkedList<ActionMenuItem> viewItemList;
	

	@Override
	protected List<ActionMenuItem> getActionItemList() {
		
		if (actionItemList == null) {
			actionItemList = new LinkedList<ActionMenuItem>();
//			actionItemList.add(new ActionMenuItem("View History", "View History", "/WEB-INF/pages/core/system/agent/view-history.zul",MENU_ITEM));
			
			if(isPermittedAction(ActionAlias.UPDATE_AGENT_SCHEDULE)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.UPDATE_AGENT_SCHEDULE);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}

			if(isPermittedAction(ActionAlias.REMOVE_AGENT_SCHEDULE)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.REMOVE_AGENT_SCHEDULE);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}
			
			if(isPermittedAction(ActionAlias.VIEW_AGENT_HISTORY)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.VIEW_AGENT_HISTORY);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}
			
//			actionItemList.add(new ActionMenuItem("UPDATE_AGENT_SCHEDULE", "Update Agent Schedule", Pages.UPDATE_AGENT_SCHEDULE,MENU_ITEM));
//			actionItemList.add(new ActionMenuItem("DELETE_AGENT_SCHEDULE", "Remove Agent Schedule", Pages.DELETE_AGENT_SCHEDULE,MENU_ITEM));
			
		}
		return actionItemList;
	}

	@Override
	protected List<ActionMenuItem> getViewItemList() {
		if (viewItemList == null) {
			viewItemList = new LinkedList<ActionMenuItem>();
			
		}
//		viewItemList.add(new ActionMenuItem("VIEW_AGENT_HISTORY", "View Agent History", Pages.VIEW_AGENT_HISTORY_ACTION,MENU_ITEM));
		return viewItemList;
	}

	@Override
	public void refreshView() {
//		closeParentTab(); //For Closing the Parent Tab and Searching the Search with Updated Result
		fetchViewEntity();
		
	}
	
	public void fetchViewEntity() {
		SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		try {
			ViewAgentScheduleVO scheduleVO = agentBD.findSystemAgentScheduleViewData(getViewEntityId());
			populateData(scheduleVO);
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		fetchViewEntity();
	}

	private void populateData(ViewAgentScheduleVO scheduleVO) {
		
		SimpleDateFormat dateFormat  = new SimpleDateFormat(getDateTimeFormat());
		
		lbName.setValue(scheduleVO.getScheduleName());
//		lbDescription.setValue(scheduleVO.get);
		lbReason.setValue(scheduleVO.getReason());
		lbExeTypeId.setValue(scheduleVO.getExecutionType());
		lbPriority.setValue(scheduleVO.getPriority());
		lbCreatedBy.setValue(scheduleVO.getCreatedBy());
		lbUpdatedBy.setValue(scheduleVO.getLastModifiedBy());
		lbCreatedDate.setValue(dateFormat.format(scheduleVO.getCreatedDate()));
		lbUpdateDate.setValue(dateFormat.format(scheduleVO.getLastModifiedDate()));
		if(scheduleVO.getLastExecutionDate()!=null) {
			lblastexecutionDate.setValue(dateFormat.format(scheduleVO.getLastExecutionDate()));
		} else {
			lblastexecutionDate.setValue("-");
		}
		
		if(scheduleVO.getNextExecutionDate()!=null) {
			lbnextexecutionDate.setValue(dateFormat.format(scheduleVO.getNextExecutionDate()));
		} else {
			lbnextexecutionDate.setValue("-");
		}
		
		lbstartexecutionDate.setValue(dateFormat.format(scheduleVO.getExecutionStartDate()));
		if(scheduleVO.getRequiredNumOfExecutions()==-1L) {
			lbreqnumofexecution.setValue("Unlimited");
		} else {
			lbreqnumofexecution.setValue(scheduleVO.getRequiredNumOfExecutions()+"");
		}
		
		
		
	}

}
