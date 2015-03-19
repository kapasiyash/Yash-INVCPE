package com.elitecore.cpe.web.system.agent;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.system.agent.AgentVO;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer.ActionMenuItem;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;


public class ViewAgentComposer extends BaseCPEViewComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Label lbName;
	private Label lbDescription;
	private Label lbCreatedDate;
	private Label lbCreatedBy;
	private Label lbUpdatedBy;
	private Label lbUpdateDate;
	private LinkedList<ActionMenuItem> actionItemList;
	public static final String VIEW_AGENT_COMPOSER_REF = "_viewAgentComp";
	private Label lbReason;
	private Label lboverrulemechunksize,lboverruleechunksize;
	private Label lbMEConCurrencyLimit,lbEConCurrencyLimit,lbMEChunkSize,lbMEChunkSizeLimit,lbEChunkSize,lbEChunkSizeLimit;
	

	@Override
	protected List<ActionMenuItem> getActionItemList() {
		if (actionItemList == null) {
			actionItemList = new LinkedList<ActionMenuItem>();
			
			if(isPermittedAction(ActionAlias.UPDATE_AGENT_PARAMETER)) {
				SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.UPDATE_AGENT_PARAMETER);
				actionItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}
			
			/*ActionMenuItem menuItemLookupUpdate = new ActionMenuItem("UPDATE", "Update Agent Parameter", Pages.UPDATE_AGENT_PARAMETER,MENU_ITEM);
			actionItemList.add(menuItemLookupUpdate);*/
			/*ActionMenuItem menuItemLookupDelete = new ActionMenuItem("DELETE", "Remove Agent", Pages.DELETE_AGENT,MENU_ITEM);
			actionItemList.add(menuItemLookupDelete);*/
		}
		return actionItemList;
	}

	@Override
	protected List<ActionMenuItem> getViewItemList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		String agentId = null;
		if(Executions.getCurrent().getArg().containsKey(VIEW_AGENT_COMPOSER_REF)){
			agentId = (String)Executions.getCurrent().getArg().get(VIEW_AGENT_COMPOSER_REF);
		}
		
		SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		
			try {
				AgentVO agentVO = agentBD.findSystemAgentById(agentId);
				populateData(agentVO);
			} catch (SearchBLException e) {
				e.printStackTrace();
			} catch (TechnicalException e) {
				e.printStackTrace();
			}
		
	}
	
	private void populateData(AgentVO agentVO) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(getDateTimeFormat());
		
		lbName.setValue(agentVO.getName());
		lbDescription.setValue(agentVO.getDescription());
		lbCreatedBy.setValue(agentVO.getCreatedBy());
		lbUpdatedBy.setValue(agentVO.getModifiedBy());
		lbCreatedDate.setValue(dateFormat.format(agentVO.getCreatedDate()));
		lbUpdateDate.setValue(dateFormat.format(agentVO.getModifiedDate()));
		lbEChunkSize.setValue(agentVO.getPareChunkSize());
		lbEChunkSizeLimit.setValue(agentVO.getAreChunkSizeELimit());
		lbEConCurrencyLimit.setValue(agentVO.getAreConcurrencyLimit());
		lbMEChunkSize.setValue(agentVO.getParmeChunkSize());
		lbMEChunkSizeLimit.setValue(agentVO.getArmeChunkSizeELimit());
		lbMEConCurrencyLimit.setValue(agentVO.getArmeConcurrencyLimit());
		lboverruleechunksize.setValue(agentVO.getOverRuleAEChunkSize().toString());
		lboverrulemechunksize.setValue(agentVO.getOverRuleAMEChunkSize().toString());
		lbReason.setValue(agentVO.getReason());
	
	}

}
