package com.elitecore.cpe.web.composer;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

//import com.elitecore.cpe.bl.delegates.event.AREventBD;
import com.elitecore.cpe.bl.data.system.agent.AgentScheduleProcedureWrapperData;
import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.system.configuration.cronexpr.CronExpressionBuilderComposer;
import com.elitecore.cpe.web.utils.MessageUtility;

public class ConfigureProcSchedule extends BaseModuleViewComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MODULE = "CONFIGURE_MV_SCHEDULE";
	private Div scheduleContent;
	private Div proclog;
//	private CronExpressionBuilderComposer builderComposer;
//	private Hlayout updatesProcConfigure;
	private Radio RadioManual,RadioAuto;
//	private Radiogroup executionType;
	private Vlayout schedulePage;
	private Label lbstatusDetail;
	private Row selecttype,scheduleNameRow;
	private Hbox buttons;
//	private Textbox txtScheduleName;
	private Button btnEditSchedule;
	

	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
	//	this.updatesProcConfigure = comp;
		Map<String,Object> hmap = new HashMap<String, Object>();
		hmap.put(CronExpressionBuilderComposer.COMP_REF, this);
		Executions.createComponents(Pages.CRON_EXPRESSION_BUILDER, scheduleContent, hmap);
		RadioManual.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				scheduleContent.setVisible(false);
				scheduleNameRow.setVisible(false);
				
			}
		});
		
		RadioAuto.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				scheduleContent.setVisible(true);
				scheduleNameRow.setVisible(true);
				
			}
			
		});
		
	}

	public void onClick$btnEditSchedule(Event event) {
		selecttype.setVisible(true);
		scheduleNameRow.setVisible(false);
		scheduleContent.setVisible(false);
		buttons.setVisible(true);
		RadioManual.setSelected(true);
	}
	
	
	public void onClick$btnUpdate(Event event) {
		
//		AREventBD eventBD = new AREventBD(getBDSessionContext());
//		String pattern = "";
//		if(RadioAuto.isSelected()) {
//			if(builderComposer!=null) {
//				Logger.logTrace(MODULE, builderComposer.prepareFinalExpression());
//				pattern = builderComposer.prepareFinalExpression();
//			}
//		}
//		
//			try {
//				eventBD.callEventProcedure(pattern,getStrViewEntityId(),txtScheduleName.getValue());
//				MessageUtility.successInformation("Success", "Procedure Called/Scheduled Successfully.");
//				updatesProcConfigure.detach();
//			} catch (UpdateBLException e) {
//				e.printStackTrace();
//				MessageUtility.failureInformation("Failure", e.getMessage());
//			} catch (TechnicalException e) {
//				e.printStackTrace();
//				MessageUtility.failureInformation("Failure", e.getMessage());
//			}
//		
		
	}
	
	
	public void setCronComposer(
			CronExpressionBuilderComposer cronExpressionBuilderComposer) {
	//	this.builderComposer = cronExpressionBuilderComposer;
		
	}
	
	public void onClick$btnProcSchedule(Event event) {
		Logger.logTrace(MODULE, "In side onClick$btnProcSchedule");
		schedulePage.setVisible(true);
		proclog.setVisible(false);
		selecttype.setVisible(false);
		scheduleNameRow.setVisible(false);
		scheduleContent.setVisible(false);
		buttons.setVisible(false);
		SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		try {
			AgentScheduleProcedureWrapperData wrapperData = agentBD.findAgentScheduleForProcedure(getStrViewEntityId());
			if(wrapperData!=null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(getDateTimeFormat());
				if(wrapperData.getIsSchedulable()!=null && !wrapperData.getIsSchedulable()) {
					lbstatusDetail.setValue("Procedure Name not binded with the Event.Please configure Procedure for the Event.");
					btnEditSchedule.setVisible(false);
				} else if(wrapperData.getNextScheduleDate()!=null) {
					lbstatusDetail.setValue("Procedure Scheduled at Time : "+dateFormat.format(wrapperData.getNextScheduleDate()));
				}
				
			}
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
	}
	
	
	public void onClick$btnViewProcLog(Event event) {
		
		schedulePage.setVisible(false);
		proclog.setVisible(true);
		selecttype.setVisible(false);
		scheduleNameRow.setVisible(false);
		scheduleContent.setVisible(false);
		buttons.setVisible(false);
		if(proclog.getChildren().isEmpty()) {
			Map<String,Object> hmap = new HashMap<String, Object>();
			hmap.put("_viewEntityId", getStrViewEntityId());
			Executions.createComponents(Pages.SEARCH_EVENT_PROCEDURE_LOG, proclog, hmap);
		} 
		
		
		
	}
}
