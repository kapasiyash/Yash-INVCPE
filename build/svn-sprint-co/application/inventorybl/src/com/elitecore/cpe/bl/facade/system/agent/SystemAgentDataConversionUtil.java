package com.elitecore.cpe.bl.facade.system.agent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.elitecore.cpe.bl.agents.AgentData;
import com.elitecore.cpe.bl.agents.base.BaseAgent;
import com.elitecore.cpe.bl.agents.base.BaseSchedule;
import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.constants.system.AgentConstants;
import com.elitecore.cpe.bl.entity.inventory.core.expr.ConstraintExpression;
import com.elitecore.cpe.bl.entity.inventory.system.agent.AgentParam;
import com.elitecore.cpe.bl.entity.inventory.system.agent.SystemAgent;
import com.elitecore.cpe.bl.entity.inventory.system.agent.SystemAgentParameter;
import com.elitecore.cpe.bl.entity.inventory.system.agent.history.SystemAgentRunDetail;
import com.elitecore.cpe.bl.entity.inventory.system.agent.history.SystemAgentRunQueue;
import com.elitecore.cpe.bl.entity.inventory.system.agent.schedule.SystemAgentSchedule;
import com.elitecore.cpe.bl.entity.inventory.system.agent.schedule.SystemAgentScheduleParameter;
import com.elitecore.cpe.bl.exception.NoSuchControllerException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.facade.BaseDataConversionUtils;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.vo.core.expr.ConstraintExpressionVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentParameterVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentScheduleParamVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentVO;
import com.elitecore.cpe.bl.vo.system.agent.PreUpdateAgentParamVO;
import com.elitecore.cpe.bl.vo.system.agent.SearchAgentRunInQueueVO;
import com.elitecore.cpe.bl.vo.system.agent.SearchAgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.agent.SystemAgentParamVO;
import com.elitecore.cpe.bl.vo.system.agent.UpdateAgentParameterVO;
import com.elitecore.cpe.bl.vo.system.agent.UpdateAgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.agent.ViewAgentRunHistoryVO;
import com.elitecore.cpe.bl.vo.system.agent.ViewAgentScheduleVO;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.expr.cron.Predictor;
import com.elitecore.cpe.util.expr.cron.exception.InvalidPatternException;
import com.elitecore.utility.agentframework.AgentRunDetails;


public class SystemAgentDataConversionUtil extends BaseDataConversionUtils {

	public static SystemAgentRunDetail prepareSystemAgentRunDetail(AgentRunDetails agentRunDetails,Long priority,String executionTypeid,String executionstatusid){
		if(agentRunDetails!=null){
			Timestamp startTime = (agentRunDetails.getExecutionStartDate() != null ? new Timestamp(agentRunDetails.getExecutionStartDate().getTime()): getCurrentTimestamp());
			Timestamp stopTime = (agentRunDetails.getExecutionStopDate() != null ? new Timestamp(agentRunDetails.getExecutionStopDate().getTime()): getCurrentTimestamp());
			SystemAgentRunDetail systemAgentRunDetail = new SystemAgentRunDetail(
					agentRunDetails.getAgentRunId(),
					Long.valueOf(agentRunDetails.getAgentScheduleId()),
					executionTypeid,
					priority,
					startTime,
					stopTime,
					agentRunDetails.getTotalMasterEntitiesProcessed(),
					agentRunDetails.getTotalEntitiesProcessed(),
					agentRunDetails.getTotalSuccessfulEntities(),
					agentRunDetails.getParameterDetail(), executionstatusid,
					agentRunDetails.getTotalSuccessfulMasterEntities(),
					agentRunDetails.getExecutionStopReason());
		return systemAgentRunDetail;
		}else{
			return null;
		}
	}
	
	public static SystemAgentRunDetail updateSystemAgentRunDetail(SystemAgentRunDetail systemAgentRunDetail,AgentRunDetails agentRunDetails,Long priority,String executionTypeid,String executionstatusid){
		if(agentRunDetails!=null){
			Timestamp startTime = (agentRunDetails.getExecutionStartDate() != null ? new Timestamp(agentRunDetails.getExecutionStartDate().getTime()): getCurrentTimestamp());
			Timestamp stopTime = (agentRunDetails.getExecutionStopDate() != null ? new Timestamp(agentRunDetails.getExecutionStopDate().getTime()): getCurrentTimestamp());
			systemAgentRunDetail.setSystemAgentId(agentRunDetails.getAgentId());
			systemAgentRunDetail.setSystemAgentScheduleId(Long.valueOf(agentRunDetails.getAgentScheduleId()));
			systemAgentRunDetail.setPriority(priority);
			systemAgentRunDetail.setExecutiontypeid(executionTypeid);
			systemAgentRunDetail.setExecutionstartdate(startTime);		
			systemAgentRunDetail.setExecutionstopdate(stopTime);
			systemAgentRunDetail.setTotalmasterentitieprocessed(agentRunDetails.getTotalMasterEntitiesProcessed());
			systemAgentRunDetail.setTotalentitiesprocessed(agentRunDetails.getTotalEntitiesProcessed());
			systemAgentRunDetail.setTotalsuccessfulentities(agentRunDetails.getTotalSuccessfulEntities());
			systemAgentRunDetail.setParameterdetail(agentRunDetails.getParameterDetail());
			systemAgentRunDetail.setExecutionstatusid(executionstatusid);
			systemAgentRunDetail.setTotalsuccessfulmasterentities(agentRunDetails.getTotalSuccessfulMasterEntities());
			systemAgentRunDetail.setRemarks(agentRunDetails.getExecutionStopReason());
		return systemAgentRunDetail;
		}else{
			return systemAgentRunDetail;
		}
	}
	
	public static BaseSchedule prepareSchedule(SystemAgentSchedule systemAgentSchedule){
		
		 BaseSchedule schedule = new BaseSchedule(systemAgentSchedule.getAgentscheduleid()+"",systemAgentSchedule.getName() ,prepareAgent(systemAgentSchedule.getSystemAgent()),systemAgentSchedule.getPriority());
		 if(systemAgentSchedule.getSystemAgentScheduleParameters()!=null && !systemAgentSchedule.getSystemAgentScheduleParameters().isEmpty()){
			 for(SystemAgentScheduleParameter parameter : systemAgentSchedule.getSystemAgentScheduleParameters()){
				 schedule.addScheduleParameter(parameter.getParameteralias(), parameter.getTextvalue());
			 }
		 }
		 return schedule;
	}
	
	/*public static BaseSchedule prepareSchedule(SystemAgentSchedule systemAgentSchedule ,Long agentRunDetailID){
		BaseSchedule scheduel = prepareSchedule(systemAgentSchedule);
		scheduel.setAgentRunQueueId(agentRunDetailID);
		return scheduel;
	}*/

	public static BaseAgent prepareAgent(SystemAgent systemAgent) {
		AgentData agent = new AgentData(systemAgent.getAgentid(), systemAgent.getName(), systemAgent.getImplclass());
		return agent;
	}

	public static SystemAgentRunQueue prepareAgentRunQueue(SystemAgentSchedule schedule) {
		SystemAgentRunQueue runQueue = new SystemAgentRunQueue(schedule.getAgentscheduleid(),getCurrentTimestamp(),schedule.getSystemAgent().getAgentid(),schedule.getExecutionType().getExecutiontypeid(),schedule.getPriority(),AgentConstants.EXECUTION_STATUS_NOT_STARTED);
		return runQueue;
	}
	
	
	
public static AgentParameterVO prepareAgentParameter(SystemAgentParameter parameter) {
		
		AgentParameterVO parameterVO = new AgentParameterVO();
		parameterVO.setAlias(parameter.getAlias());
		parameterVO.setCustomfieldtypeid(parameter.getCustomfieldtypeid());
		parameterVO.setName(parameter.getName());
//		parameterVO.setValuesource(parameter.getValuesource());
		parameterVO.setAgentparamid(parameter.getAgentparamid());
		if(parameter.getConstraintExpression()!=null){
			parameterVO.setExpressonVO(prepareConstraintExpressionVO(parameter.getConstraintExpression()));
		}
		
		
		return parameterVO;
	}

	
	public static ConstraintExpressionVO prepareConstraintExpressionVO(ConstraintExpression expression){
		return new ConstraintExpressionVO(expression.getConstraintExpressionId(), expression.getName(), expression.getRegEx());
	}


	public static List<AgentVO> prepareAgentsVO(List<SystemAgent> agents) {
		
		List<AgentVO> agentVOs = new ArrayList<AgentVO>();
		AgentVO agentVO = null;
		for(SystemAgent systemAgent : agents) {
			agentVO = new AgentVO();
			agentVO.setAgentId(systemAgent.getAgentid());
			agentVO.setName(systemAgent.getName());
			agentVO.setDescription(systemAgent.getDescription());
			agentVO.setCreatedDate(systemAgent.getCreatedate());
			agentVO.setModifiedDate(systemAgent.getLastmodidate());
			agentVOs.add(agentVO);
		}
		
		
		return agentVOs;
	}

	
	public static SystemAgentSchedule prepareSystemAgentScheduleData(AgentScheduleVO scheduleVO,IBLSession iblSession) {
		
		SystemAgentSchedule agentSchedule = new SystemAgentSchedule();
		
		agentSchedule.setExeperiodinmin(scheduleVO.getExecutionInterval());
		agentSchedule.setRequiredNumberofExecutions(scheduleVO.getRequiredNoOfExecutions());
		agentSchedule.setName(scheduleVO.getScheduleName());
		if(scheduleVO.getPriority().equals(AgentConstants.HIGH)) {
			agentSchedule.setPriority(AgentConstants.PRIORITY_HIGH);
		} else if(scheduleVO.getPriority().equals(AgentConstants.LOW)) {
			agentSchedule.setPriority(AgentConstants.PRIORITY_LOW);
		} else if(scheduleVO.getPriority().equals(AgentConstants.MEDIUM)) {
			agentSchedule.setPriority(AgentConstants.PRIORITY_MEDIUM);
		}
		
		if(scheduleVO.getSchedulePattern()!=null)
			agentSchedule.setSchedulePattern(scheduleVO.getSchedulePattern());
		
		agentSchedule.setReasonforschedule(scheduleVO.getReason());
		agentSchedule.setExecutionStartDate(scheduleVO.getExecutionStartDate());
		agentSchedule.setExecutionstatusid(AgentConstants.EXECUTION_STATUS_NOT_STARTED);
		agentSchedule.setCreatedate(getCurrentTimestamp());
		agentSchedule.setCreatedby(iblSession.getSessionUserId());
		agentSchedule.setOwnedby(iblSession.getSessionUserId());
		agentSchedule.setLastmodifieddate(getCurrentTimestamp());
		agentSchedule.setLastmodifiedby(iblSession.getSessionUserId());
		agentSchedule.setStatus(CPECommonConstants.SCHEDULE_STATUS_ACTIVE);
		
		return agentSchedule;
	}


	public static SystemAgentScheduleParameter prepareScheduleParam(AgentScheduleParamVO scheduleParamVO, IBLSession iblSession) {
		
		SystemAgentScheduleParameter scheduleParameter = new SystemAgentScheduleParameter();
		scheduleParameter.setTextvalue(scheduleParamVO.getValueField());
		scheduleParameter.setParameteralias(scheduleParamVO.getParamAlias());
		scheduleParameter.setCreatedate(getCurrentTimestamp());
		
		return scheduleParameter;
	}


	public static SearchAgentScheduleVO prepareScheduleVO(SearchAgentScheduleVO agentScheduleVO, SystemAgentSchedule agentSchedule) {
		
			
			agentScheduleVO.setAgentScheduleId(agentSchedule.getAgentscheduleid());
			agentScheduleVO.setScheduleName(agentSchedule.getName());
			if(agentSchedule.getStatus()!=null){
				
				if(agentSchedule.getStatus().equals(CPECommonConstants.SCHEDULE_STATUS_ACTIVE)){
					agentScheduleVO.setActive(true);
				}else{
					agentScheduleVO.setActive(false);
				}
			}
			
			if(agentSchedule.getExecutionStatus()!=null){
				agentScheduleVO.setStatus(agentSchedule.getExecutionStatus().getName());
			}
			agentScheduleVO.setExecutionType(agentSchedule.getExecutionType().getExecutiontypeid());
			
			if(agentSchedule.getLastexecutiondate()!=null) {
				agentScheduleVO.setLastExecutionDate(agentSchedule.getLastexecutiondate());
			}
			agentScheduleVO.setExecutionStartDate(agentSchedule.getExecutionStartDate());
			agentScheduleVO.setCreatedDate(agentSchedule.getCreatedate());
			agentScheduleVO.setLastModifiedDate(agentSchedule.getLastmodifieddate());
			agentScheduleVO.setAgentName(agentSchedule.getSystemAgent().getName());
		return agentScheduleVO;
	}


	public static ViewAgentScheduleVO prepareScheduleViewData(SystemAgentSchedule agentSchedule) {
		
		ViewAgentScheduleVO agentScheduleVO = new ViewAgentScheduleVO();
		
		
		agentScheduleVO.setScheduleName(agentSchedule.getName());
		
		if(agentSchedule.getPriority().equals(AgentConstants.PRIORITY_HIGH) || agentSchedule.getPriority() > AgentConstants.PRIORITY_HIGH) {
			agentScheduleVO.setPriority(AgentConstants.HIGH);
		}else if(agentSchedule.getPriority().equals(AgentConstants.PRIORITY_MEDIUM)) {
			agentScheduleVO.setPriority(AgentConstants.MEDIUM);
		} else if(agentSchedule.getPriority().equals(AgentConstants.PRIORITY_LOW) || agentSchedule.getPriority() < AgentConstants.PRIORITY_LOW) {
			agentScheduleVO.setPriority(AgentConstants.LOW);
		}
		if(agentSchedule.getExecutionType().getExecutiontypeid().equals(AgentConstants.EXECUTION_TYPE_MANUAL_EXECUTION)) {
			agentScheduleVO.setExecutionType("Manual");
		} else if(agentSchedule.getExecutionType().getExecutiontypeid().equals(AgentConstants.EXECUTION_TYPE_AUTOMATIC_EXECUTION)) {
			agentScheduleVO.setExecutionType("Automatic");
		}

		agentScheduleVO.setLastExecutionDate(agentSchedule.getLastexecutiondate());
		agentScheduleVO.setRequiredNumOfExecutions(agentSchedule.getRequiredNumberofExecutions());
		if((agentSchedule.getRequiredNumberofExecutions().equals(Long.valueOf(-1)) || agentSchedule.getRequiredNumberofExecutions().intValue() > 0 ) && agentSchedule.getSchedulePattern()!=null && agentSchedule.getStatus()!=null && agentSchedule.getStatus().equals(CPECommonConstants.SCHEDULE_STATUS_ACTIVE)){
			Predictor predictor;
			try {
				predictor = new Predictor(agentSchedule.getSchedulePattern());
				agentScheduleVO.setNextExecutionDate(new Timestamp(predictor.nextMatchingTime()));
			} catch (InvalidPatternException e) {
				e.printStackTrace();
			}
			
		}
		
		agentScheduleVO.setExecutionStartDate(agentSchedule.getExecutionStartDate());
		agentScheduleVO.setReason(agentSchedule.getReasonforschedule());
		
		agentScheduleVO.setCreatedDate(agentSchedule.getCreatedate());
		try {
			agentScheduleVO.setCreatedBy(UserFactory.findUserById(agentSchedule.getCreatedby()).getName());
			agentScheduleVO.setLastModifiedBy(UserFactory.findUserById(agentSchedule.getLastmodifiedby()).getName());
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (NoSuchControllerException e) {
			e.printStackTrace();
		}
		agentScheduleVO.setLastModifiedDate(agentSchedule.getLastmodifieddate());
		return agentScheduleVO;
	}


	public static ViewAgentRunHistoryVO prepareAgentRunDetail(SystemAgentRunDetail agentRunDetail) {
		
		ViewAgentRunHistoryVO runHistoryVO = new ViewAgentRunHistoryVO();
		runHistoryVO.setAgentRunId(agentRunDetail.getAgentrundetailid());
		runHistoryVO.setExecutionstartdate(agentRunDetail.getExecutionstartdate());
		runHistoryVO.setExecutionstopate(agentRunDetail.getExecutionstopdate());
		runHistoryVO.setParamDetail(agentRunDetail.getParameterdetail());
		runHistoryVO.setRemarks(agentRunDetail.getRemarks());
//		runHistoryVO.setStatus(agentRunDetail.getExecutionstatusid());
		runHistoryVO.setTotalEntities(agentRunDetail.getTotalentitiesprocessed());
		runHistoryVO.setTotalMasterEntities(agentRunDetail.getTotalmasterentitieprocessed());
		runHistoryVO.setTotalSuccessEntities(agentRunDetail.getTotalsuccessfulentities());
		runHistoryVO.setTotalSuccessMasterEntities(agentRunDetail.getTotalsuccessfulmasterentities());
		if(agentRunDetail.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_DEFAULT)) {
			runHistoryVO.setStatus("Default");
		} else if(agentRunDetail.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_IN_PROGRESS)) {
			runHistoryVO.setStatus("In Progress");
		} else if(agentRunDetail.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_COMPLETED_SUCCESSFULLY)) {
			runHistoryVO.setStatus("Completed");
		} else if(agentRunDetail.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_EXPIRED)) {
			runHistoryVO.setStatus("Expired");
		} else if(agentRunDetail.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_NOT_STARTED)) {
			runHistoryVO.setStatus("Not Started");
		} else if(agentRunDetail.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_RE_SCHEDULED)) {
			runHistoryVO.setStatus("Re-sheduled");
		}
		
		
		return runHistoryVO;
	}


	public static SearchAgentRunInQueueVO prepareQueueVO(SearchAgentRunInQueueVO queueVO, SystemAgentRunQueue agentRunQueue) {
		
		queueVO.setScheduleDueDate(agentRunQueue.getExecutionduedatetime());
		queueVO.setAgentRunInQueueId(agentRunQueue.getAgentrunqueueid());
		queueVO.setAgentName(agentRunQueue.getSystemAgent().getName());
		queueVO.setScheduleName(agentRunQueue.getSystemAgentSchedule().getName());
		
		if(agentRunQueue.getPriority().equals(AgentConstants.PRIORITY_HIGH)) {
			queueVO.setPriority("High");
		} else if(agentRunQueue.getPriority().equals(AgentConstants.PRIORITY_MEDIUM)) {
			queueVO.setPriority("Medium");
		} else if(agentRunQueue.getPriority().equals(AgentConstants.PRIORITY_LOW)) {
			queueVO.setPriority("Low");
		}
		
		if(agentRunQueue.getExecutionType()!=null){
			queueVO.setExecutionType(agentRunQueue.getExecutionType().getName());
		}else{
			queueVO.setExecutionType("-");
		}
		
		if(agentRunQueue.getExecutionStatus() !=null){
			queueVO.setStatus(agentRunQueue.getExecutionStatus().getName());
		}else{
			queueVO.setStatus("-");
		}
		/*if(agentRunQueue.getExecutiontypeid().equals(AgentConstants.EXECUTION_TYPE_AUTOMATIC_EXECUTION)) {
			queueVO.setExecutionType("Automatic");
		} else if(agentRunQueue.getExecutiontypeid().equals(AgentConstants.EXECUTION_TYPE_MANUAL_EXECUTION)) {
			queueVO.setExecutionType("Manual");
		}
		
		if(agentRunQueue.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_NOT_STARTED)) {
			queueVO.setStatus("Not Started");
		} else if(agentRunQueue.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_IN_PROGRESS)) {
			queueVO.setStatus("In Progress");
		} else if(agentRunQueue.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_DEFAULT)) {
			queueVO.setStatus("Default");
		} else if(agentRunQueue.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_COMPLETED_SUCCESSFULLY)) {
			queueVO.setStatus("Completed with Success");
		} else if(agentRunQueue.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_COMPLETED_WITH_ERROR)) {
			queueVO.setStatus("Completed With Error");
		}*/
		
		return queueVO;
	}


	public static AgentVO prepareAgentVO(SystemAgent systemAgent) {
		
		
			AgentVO agentVO = new AgentVO();
			agentVO.setAgentId(systemAgent.getAgentid());
			agentVO.setName(systemAgent.getName());
			agentVO.setDescription(systemAgent.getDescription());
			agentVO.setCreatedDate(systemAgent.getCreatedate());
			agentVO.setModifiedDate(systemAgent.getLastmodidate());
			
			AgentParam agentParam = systemAgent.getAgentParams();
			agentVO.setAreChunkSizeELimit(agentParam.getAreChunkSizeELimit() != null ? agentParam.getAreChunkSizeELimit().toString():"-");
			agentVO.setAreConcurrencyLimit(agentParam.getAreConcurrencyLimit() != null ? agentParam.getAreConcurrencyLimit().toString():"-");
			agentVO.setArmeChunkSizeELimit(agentParam.getArmeChunkSizeELimit() != null ? agentParam.getArmeChunkSizeELimit().toString():"-");
			agentVO.setArmeConcurrencyLimit(agentParam.getArmeConcurrencyLimit() != null ? agentParam.getArmeConcurrencyLimit().toString():"-");
			agentVO.setOverRuleAEChunkSize(agentParam.getOverRuleAEChunkSize() != null ? agentParam.getOverRuleAEChunkSize():'-');
			agentVO.setOverRuleAMEChunkSize(agentParam.getOverRuleAMEChunkSize() != null ? agentParam.getOverRuleAMEChunkSize():'-');
			agentVO.setPareChunkSize(agentParam.getPareChunkSize() != null ? agentParam.getPareChunkSize().toString():"-");
			agentVO.setParmeChunkSize(agentParam.getParmeChunkSize() != null ? agentParam.getParmeChunkSize().toString():"-");
			agentVO.setReason(agentParam.getReason());
			try {
				agentVO.setCreatedBy(UserFactory.findUserById(systemAgent.getCreatedby()).getName());
				agentVO.setModifiedBy(UserFactory.findUserById(systemAgent.getLastmodiby()).getName());
			} catch (SearchBLException e) {
				e.printStackTrace();
			} catch (NoSuchControllerException e) {
				e.printStackTrace();
			}
			
			
		return agentVO;
	}

	public static SystemAgentParamVO prepareSystemAgentParamVO(SystemAgent systemAgent) {
		
		SystemAgentParamVO paramVO = null;
		if(systemAgent.getAgentParams()!=null) {
			paramVO = new SystemAgentParamVO();
			paramVO.setAgentId(systemAgent.getAgentid());
			AgentParam agentParam = systemAgent.getAgentParams();
			paramVO.setReason(agentParam.getReason());
			paramVO.setAreChunkSizeELimit(agentParam.getAreChunkSizeELimit());
			paramVO.setAreConcurrencyLimit(agentParam.getAreConcurrencyLimit());
			paramVO.setArmeChunkSizeELimit(agentParam.getArmeChunkSizeELimit());
			paramVO.setArmeConcurrencyLimit(agentParam.getArmeConcurrencyLimit());
			if(agentParam.getOverRuleAEChunkSize().equals('Y')) {
				paramVO.setOverRuleAEChunkSize(true);
			} else {
				paramVO.setOverRuleAEChunkSize(false);
			}
			if(agentParam.getOverRuleAMEChunkSize().equals('Y')) {
				paramVO.setOverRuleAMEChunkSize(true);
			} else {
				paramVO.setOverRuleAMEChunkSize(false);
			}
			
			paramVO.setPreareChunkSize(agentParam.getPareChunkSize());
			paramVO.setPrearmeChunkSize(agentParam.getParmeChunkSize());
		}
		
		return paramVO;
	}

	/*public static UpdateAgentScheduleVO prepareSystemScheduleVOData(SystemAgentSchedule agentSchedule) {
		
		UpdateAgentScheduleVO scheduleVO = new UpdateAgentScheduleVO();
		
		scheduleVO.setAgentName(agentSchedule.getSystemAgent().getName());
		scheduleVO.setAgentId(agentSchedule.getSystemAgent().getAgentid());
		scheduleVO.setAgentScheduleId(agentSchedule.getAgentscheduleid());
		scheduleVO.setExecutionInterval(agentSchedule.getExeperiodinmin());
		scheduleVO.setExecutionStartDate(agentSchedule.getExecutionStartDate());
		scheduleVO.setExecutionType(agentSchedule.getExecutionType().getExecutiontypeid());
		scheduleVO.setPriority(agentSchedule.getPriority());
		scheduleVO.setReason(agentSchedule.getReasonforschedule());
		scheduleVO.setRequiredNoOfExecutions(agentSchedule.getRequiredNumberofExecutions());
		scheduleVO.setScheduleName(agentSchedule.getName());
		
		if(agentSchedule.getSystemAgentScheduleParameters()!=null && !agentSchedule.getSystemAgentScheduleParameters().isEmpty()) {
			for(SystemAgentScheduleParameter parameter : agentSchedule.getSystemAgentScheduleParameters()) {
				AgentScheduleParamVO scheduleParamVO = new AgentScheduleParamVO();
				scheduleParamVO.setValueField(parameter.getTextvalue());
				scheduleVO.setScheduleParamVO(scheduleParamVO);
			}
		}
		
		
		
		return scheduleVO;
	}*/

	public static SystemAgentSchedule prepareUpdateSystemAgentScheduleData(SystemAgentSchedule agentSchedule,AgentScheduleVO scheduleVO, IBLSession iblSession) {
		
		
		agentSchedule.setExeperiodinmin(scheduleVO.getExecutionInterval());
		agentSchedule.setRequiredNumberofExecutions(scheduleVO.getRequiredNoOfExecutions());
		agentSchedule.setName(scheduleVO.getScheduleName());
		if(scheduleVO.getPriority().equals(AgentConstants.HIGH)) {
			agentSchedule.setPriority(AgentConstants.PRIORITY_HIGH);
		} else if(scheduleVO.getPriority().equals(AgentConstants.LOW)) {
			agentSchedule.setPriority(AgentConstants.PRIORITY_LOW);
		} else if(scheduleVO.getPriority().equals(AgentConstants.MEDIUM)) {
			agentSchedule.setPriority(AgentConstants.PRIORITY_MEDIUM);
		}
		
//		agentSchedule.setScheduleType(AgentConstants.SCHEDULE_TYPE_INTERVAL);
		agentSchedule.setReasonforschedule(scheduleVO.getReason());
		agentSchedule.setExecutionStartDate(scheduleVO.getExecutionStartDate());
		agentSchedule.setExecutionstatusid(AgentConstants.EXECUTION_STATUS_NOT_STARTED);
		agentSchedule.setLastmodifieddate(getCurrentTimestamp());
		agentSchedule.setLastmodifiedby(iblSession.getSessionUserId());
		
		
		return agentSchedule;
	}

	public static SystemAgentSchedule updateAgentSchedule(SystemAgentSchedule agentSchedule,UpdateAgentScheduleVO scheduleVO, IBLSession session) {
		
		agentSchedule.setSchedulePattern(scheduleVO.getCronExpression());
		agentSchedule.setReasonforschedule(scheduleVO.getReason());
		agentSchedule.setLastmodifiedby(session.getSessionUserId());
		agentSchedule.setLastmodifieddate(getCurrentTimestamp());
		
		return agentSchedule;
		
	}

	public static BaseSchedule prepareSchedule(SystemAgentRunQueue agentRun) {
		
		BaseSchedule scheduel = prepareSchedule(agentRun.getSystemAgentSchedule());
		scheduel.setAgentRunQueueId(agentRun.getAgentrunqueueid());
		scheduel.setExecutiondueDate(agentRun.getExecutionduedatetime());
		
		return scheduel;
	}
	
	public static AgentParam convertToUpdateAgentParamData(AgentParam agentParam,UpdateAgentParameterVO agentParameterVO,IBLSession session)
	{
		agentParam.setAgentid(agentParameterVO.getAgentid());
		agentParam.setAreChunkSizeELimit(agentParameterVO.getAreChunkSizeELimit());
		agentParam.setAreConcurrencyLimit(agentParameterVO.getAreConcurrencyLimit());
		agentParam.setArmeChunkSizeELimit(agentParameterVO.getArmeChunkSizeELimit());
		agentParam.setArmeConcurrencyLimit(agentParameterVO.getArmeConcurrencyLimit());
		agentParam.setOverRuleAEChunkSize(agentParameterVO.getOverRuleAEChunkSize());
		agentParam.setOverRuleAMEChunkSize(agentParameterVO.getOverRuleAMEChunkSize());
		agentParam.setPareChunkSize(agentParameterVO.getPareChunkSize());
		agentParam.setParmeChunkSize(agentParameterVO.getParmeChunkSize());
		agentParam.setReason(agentParameterVO.getReason());
		
		return agentParam;
	}

	public static PreUpdateAgentParamVO convertToPreUpdateAgentParam(AgentParam agentParam)
	{
		PreUpdateAgentParamVO preUpdateAgentParamVO = new PreUpdateAgentParamVO();
		
		preUpdateAgentParamVO.setAreChunkSizeELimit(agentParam.getAreChunkSizeELimit());
		preUpdateAgentParamVO.setAreConcurrencyLimit(agentParam.getAreConcurrencyLimit());
		preUpdateAgentParamVO.setArmeChunkSizeELimit(agentParam.getArmeChunkSizeELimit());
		preUpdateAgentParamVO.setArmeConcurrencyLimit(agentParam.getArmeConcurrencyLimit());
		preUpdateAgentParamVO.setOverRuleAEChunkSize(agentParam.getOverRuleAEChunkSize());
		preUpdateAgentParamVO.setOverRuleAMEChunkSize(agentParam.getOverRuleAMEChunkSize());
		preUpdateAgentParamVO.setPareChunkSize(agentParam.getPareChunkSize());
		preUpdateAgentParamVO.setParmeChunkSize(agentParam.getParmeChunkSize());
		preUpdateAgentParamVO.setReason(agentParam.getReason());
		
		return preUpdateAgentParamVO;
	}
}
