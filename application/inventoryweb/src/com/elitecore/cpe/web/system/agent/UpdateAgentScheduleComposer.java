package com.elitecore.cpe.web.system.agent;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.system.agent.UpdateAgentScheduleVO;
import com.elitecore.cpe.util.expr.cron.SchedulingPattern;
import com.elitecore.cpe.util.expr.cron.exception.InvalidPatternException;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.system.configuration.cronexpr.CronExpressionBuilderComposer;
import com.elitecore.cpe.web.utils.MessageUtility;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public class UpdateAgentScheduleComposer extends BaseModuleViewComposer {


	private static final long serialVersionUID = 1L;
	private static final String MODULE = "UPDATE-AGENT-SCHEDULE";
	private Hlayout updateschedule;
	private Div scheduleContent;
	private Textbox reasontb;
	private CronExpressionBuilderComposer builderComposer;
	
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		Map<String,Object> hmap = new HashMap<String, Object>();
		hmap.put(CronExpressionBuilderComposer.COMP_REF, this);
		Executions.createComponents(Pages.CRON_EXPRESSION_BUILDER, scheduleContent, hmap);
		
	}
	
	public void onClick$btnUpdate(Event event) throws InvalidPatternException {
		
		
		try {
			SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
			UpdateAgentScheduleVO scheduleVO = new UpdateAgentScheduleVO();
			if(builderComposer!=null) {
				Logger.logTrace(MODULE, builderComposer.prepareFinalExpression());
				if(SchedulingPattern.validate(builderComposer.prepareFinalExpression())) {
					scheduleVO.setCronExpression(builderComposer.prepareFinalExpression());
					if(reasontb!=null && !reasontb.getText().isEmpty()) {
						scheduleVO.setReason(reasontb.getText());
					}
					scheduleVO.setScheduleId(getViewEntityId());
					agentBD.updateAgentSchedule(scheduleVO);
					MessageUtility.successInformation("Success", "System Agent Scheduling Pattern Updated Successfully.");
					updateschedule.detach();
					if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
						BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
						viewComposer.refreshView();
					}
				} else {
					throw new InvalidPatternException("Invalid Scheduling Pattern");
				}
			}
		} catch (UpdateBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void setCronComposer(
			CronExpressionBuilderComposer cronExpressionBuilderComposer) {
		this.builderComposer = cronExpressionBuilderComposer;
		
	}
	
	
}
