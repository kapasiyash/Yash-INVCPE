package com.elitecore.cpe.bl.facade.system.agent;




import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.jboss.mx.util.MBeanServerLocator;

import com.elitecore.cpe.bl.agents.base.BaseSchedule;
import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.constants.system.AgentConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.system.agent.AgentScheduleProcedureWrapperData;
import com.elitecore.cpe.bl.data.system.audit.AuditSummaryDetail;
import com.elitecore.cpe.bl.entity.inventory.system.agent.AgentParam;
import com.elitecore.cpe.bl.entity.inventory.system.agent.ExecutionStatus;
import com.elitecore.cpe.bl.entity.inventory.system.agent.ExecutionType;
import com.elitecore.cpe.bl.entity.inventory.system.agent.SystemAgent;
import com.elitecore.cpe.bl.entity.inventory.system.agent.SystemAgentParameter;
import com.elitecore.cpe.bl.entity.inventory.system.agent.history.SystemAgentRunDetail;
import com.elitecore.cpe.bl.entity.inventory.system.agent.history.SystemAgentRunQueue;
import com.elitecore.cpe.bl.entity.inventory.system.agent.schedule.SystemAgentSchedule;
import com.elitecore.cpe.bl.entity.inventory.system.agent.schedule.SystemAgentScheduleParameter;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.exception.agent.AgentServiceException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.system.audit.AuditDataConversionUtilities;
import com.elitecore.cpe.bl.facade.system.audit.SystemAuditFacadeLocal;
import com.elitecore.cpe.bl.session.system.agent.SystemAgentSessionBeanLocal;
import com.elitecore.cpe.bl.vo.system.agent.AgentParameterVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentScheduleParamVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentServiceVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentVO;
import com.elitecore.cpe.bl.vo.system.agent.PreUpdateAgentParamVO;
import com.elitecore.cpe.bl.vo.system.agent.SearchAgentRunInQueueVO;
import com.elitecore.cpe.bl.vo.system.agent.SearchAgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.agent.SystemAgentParamVO;
import com.elitecore.cpe.bl.vo.system.agent.SystemAgentVO;
import com.elitecore.cpe.bl.vo.system.agent.UpdateAgentParameterVO;
import com.elitecore.cpe.bl.vo.system.agent.UpdateAgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.agent.ViewAgentRunHistoryVO;
import com.elitecore.cpe.bl.vo.system.agent.ViewAgentScheduleVO;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.expr.cron.Predictor;
import com.elitecore.cpe.util.expr.cron.SchedulingPattern;
import com.elitecore.cpe.util.expr.cron.exception.InvalidPatternException;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.utility.agentframework.AgentRunDetails;
import com.elitecore.utility.agentframework.data.AgentServiceSummaryData;


	
@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class SystemAgentFacade extends BaseFacade implements SystemAgentFacadeLocal, SystemAgentFacadeRemote {


    private static final String MODULE = "SYSTEM-AGENT-FACADE";
	@EJB private SystemAgentSessionBeanLocal systemAgentSessionBeanLocal;
	@EJB private SystemAuditFacadeLocal systemAuditFacadeLocal;
	//@EJB private AREventSessionBeanLocal eventSessionBeanLocal;
    
	@Override
	public List<SystemAgentVO> findAllActiveSystemAgents() throws SearchBLException {
		
		List<SystemAgentVO> resultList = null;
		List<SystemAgent> systemAgentList = systemAgentSessionBeanLocal.findAllActiveSystemAgents();
		
		if (systemAgentList!=null && !systemAgentList.isEmpty()) {
			resultList = new ArrayList<SystemAgentVO>(systemAgentList.size());  //Expect empty list in case no record found
			for(SystemAgent systemAgent:systemAgentList) {
				/*if(systemAgent.getIsactive().equals(Character.valueOf('Y'))){
					resultList.add(SystemAgentDataConversionUtil.prepareAgentVO(systemAgent));
				}*/
			}
		} else {
			resultList = new ArrayList<SystemAgentVO>(0);
		}
		return resultList;
	}

	@Override
	public List<AgentParameterVO> getSystemParameters(String agentId) throws SearchBLException {

		
		List<AgentParameterVO> parameterVOList = new ArrayList<AgentParameterVO>();
		List<SystemAgentParameter> agentParameter = systemAgentSessionBeanLocal.findSystemParameters(agentId);
		
		for(SystemAgentParameter parameter : agentParameter) {
			
			AgentParameterVO parameterVO = SystemAgentDataConversionUtil.prepareAgentParameter(parameter);
			
			if(parameter.getCustomfieldtypeid()!=null && parameter.getCustomfieldtypeid().equals(SystemParameterConstants.SQL_COMBO_BOX)){
				List<Object[]> objectList =  systemAgentSessionBeanLocal.findValueSource(parameter.getValuesource());
				if(objectList != null && !objectList.isEmpty()){
					Map<String, String> map = new HashMap<String, String>();
					for(Object[] objects : objectList){
						/*Logger.logTrace(objects[0].toString(), objects[1].toString());*/
//						String character = (String)objects[0];
						map.put(objects[0].toString(),objects[1].toString());
						
					}
					parameterVO.setValueSource(map);
				}
			}else if(parameter.getCustomfieldtypeid()!=null && parameter.getCustomfieldtypeid().equals(SystemParameterConstants.LIST_BOX)){
				List<Object[]> objectList =  systemAgentSessionBeanLocal.findValueSource(parameter.getValuesource());
				if(objectList != null && !objectList.isEmpty()){
					Map<String, String> map = new HashMap<String, String>();
					for(Object[] objects : objectList){
						/*Logger.logTrace(objects[0].toString(), objects[1].toString());*/
//						String character = (String)objects[0];
						map.put(objects[0].toString(),objects[1].toString());
						
					}
					parameterVO.setValueSource(map);
				}
			}else if(parameter.getCustomfieldtypeid()!=null && parameter.getCustomfieldtypeid().equals(SystemParameterConstants.COMBO_BOX)){
				
				if(parameter.getValuesource() != null && !parameter.getValuesource().isEmpty()){
					String[] stringList = parameter.getValuesource().split(",");
					Map<String, String> map = new HashMap<String, String>();
					for(String string : stringList){
						map.put(string,string);
					}
					parameterVO.setValueSource(map);
				}
			
			}
			
			parameterVOList.add(parameterVO);
			
		}
		
		
		
		
		return parameterVOList;
	}

	@Override
	public List<AgentVO> getAllAgentsList() throws SearchBLException {
		List<SystemAgent> agents=systemAgentSessionBeanLocal.findAllSystemAgent();
		List<AgentVO> agentVOs = SystemAgentDataConversionUtil.prepareAgentsVO(agents);
		return agentVOs;
	}

	@Override
	public void createAgentSchedule(AgentScheduleVO scheduleVO,IBLSession iblSession)
			throws CreateBLException {
		
		String agentid=scheduleVO.getAgentId();
		SystemAgent agentdetail=null;
		SystemAgentSchedule systemAgentSchedule = SystemAgentDataConversionUtil.prepareSystemAgentScheduleData(scheduleVO,iblSession);
		try {
			 agentdetail=systemAgentSessionBeanLocal.findSystemAgentById(agentid);
			 List<SystemAgentSchedule> uniqueAgentSchedule = systemAgentSessionBeanLocal.findSystemAgentScheduleByUniqueName(scheduleVO.getScheduleName());
			 if(uniqueAgentSchedule.isEmpty()) {
				 SystemAgent  systemAgent = systemAgentSessionBeanLocal.findSystemAgentById(scheduleVO.getAgentId());
					systemAgentSchedule.setSystemAgent(systemAgent);
					ExecutionType executionType = systemAgentSessionBeanLocal.findExecutionTypeById(scheduleVO.getExecutionType());
					systemAgentSchedule.setExecutionType(executionType);
					Set<SystemAgentScheduleParameter>  scheduleParameters= new HashSet<SystemAgentScheduleParameter>();
					
					if(scheduleVO.getScheduleParamVO()!=null && !scheduleVO.getScheduleParamVO().isEmpty()) {
						for(AgentScheduleParamVO scheduleParamVO : scheduleVO.getScheduleParamVO()) {
							if(scheduleVO.getScheduleParamVO()!=null && scheduleParamVO.getValueField()!=null){
								SystemAgentScheduleParameter scheduleParameter = SystemAgentDataConversionUtil.prepareScheduleParam(scheduleParamVO,iblSession);
								scheduleParameter.setSystemAgentSchedule(systemAgentSchedule);
								scheduleParameters.add(scheduleParameter);
							}
						}
					}
					
					
					
					systemAgentSchedule.setSystemAgentScheduleParameters(scheduleParameters);
			 } else {
					throw new CreateBLException("Name already exists.");
				}
			
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
		
		systemAgentSchedule.setStatus(CPECommonConstants.SCHEDULE_STATUS_ACTIVE);
		systemAgentSessionBeanLocal.createSystemAgentSchedule(systemAgentSchedule);
		
		//Prepare Audit Data
//		AuditSummary auditData = null;
//		if(systemAgentSchedule != null) {
//			
//			auditData = AuditDataConversionUtilities.prepareAgentScheduleCreateAudit(systemAgentSchedule,iblSession);
//			if(!scheduleVO.getExecutionType().equals(AgentConstants.EXECUTION_TYPE_MANUAL_EXECUTION))
//			{
//				systemAuditFacadeLocal.doAuditEntry(auditData);
//			}
//		}
		
		// Audit entry
		if(agentdetail!=null){
		Map<String,Object> mapAudit = new HashMap<String, Object>();
		mapAudit.put(AuditTagConstant.AGENTSCHEDULENAME,systemAgentSchedule.getName());
		mapAudit.put(AuditTagConstant.AGENTNAME,agentdetail.getName());
		
		addToAuditDynamicMessage(AuditConstants.CREATE_AGENT_SCHEDULE, "Creating Agent Schedule  ",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, iblSession);
		}

		
		
		if(scheduleVO.getExecutionType().equals(AgentConstants.EXECUTION_TYPE_MANUAL_EXECUTION)) {
			SystemAgentRunQueue runQueue = new SystemAgentRunQueue();
			runQueue.setAgentid(systemAgentSchedule.getSystemAgent().getAgentid());
			runQueue.setAgentscheduleid(systemAgentSchedule.getAgentscheduleid());
			runQueue.setExecutiontypeid(scheduleVO.getExecutionType());
			runQueue.setExecutionduedatetime(scheduleVO.getExecutionStartDate());
			runQueue.setExecutionstatusid(systemAgentSchedule.getExecutionstatusid());
			runQueue.setPriority(systemAgentSchedule.getPriority());
			systemAgentSessionBeanLocal.createSystemAgentRunQueue(runQueue);
//			if(systemAgentSchedule != null && auditData != null) 
//			{
//				auditData = AuditDataConversionUtilities.prepareManualAgentScheduleCreateAudit(runQueue,auditData);
//				systemAuditFacadeLocal.doAuditEntry(auditData);
//			}
		}
		
		
	}

	@Override
	public List<SearchAgentScheduleVO> findSystemAgentSchedule(String name,String statusId)throws SearchBLException {
		
		List<SystemAgentSchedule> schedules = systemAgentSessionBeanLocal.findSystemAgentSchedule(name,statusId);
		List<SearchAgentScheduleVO> agentScheduleVO = new ArrayList<SearchAgentScheduleVO>();
		if(schedules!=null && !schedules.isEmpty()){
			for(SystemAgentSchedule agentSchedule : schedules) {
				SearchAgentScheduleVO scheduleVO = new SearchAgentScheduleVO();
				scheduleVO = SystemAgentDataConversionUtil.prepareScheduleVO(scheduleVO,agentSchedule);
				try {
					if((agentSchedule.getRequiredNumberofExecutions().equals(Long.valueOf(-1)) || agentSchedule.getRequiredNumberofExecutions().intValue() > 0 ) && agentSchedule.getSchedulePattern()!=null && agentSchedule.getStatus()!=null && agentSchedule.getStatus().equals(CPECommonConstants.SCHEDULE_STATUS_ACTIVE)){
						Predictor predictor = new Predictor(agentSchedule.getSchedulePattern());
						scheduleVO.setNextScheduleDate(new Timestamp(predictor.nextMatchingTime()));
					}
				} catch (InvalidPatternException e) {
					e.printStackTrace();
				}
				agentScheduleVO.add(scheduleVO);
			}
		}
		return agentScheduleVO;
	}

	@Override
	public ViewAgentScheduleVO findSystemAgentScheduleViewData(Long scheduleId)throws SearchBLException {
		
		SystemAgentSchedule agentSchedule = systemAgentSessionBeanLocal.findSystemAgentScheduleById(scheduleId);
		ViewAgentScheduleVO agentScheduleVO = SystemAgentDataConversionUtil.prepareScheduleViewData(agentSchedule);
		
		
		return agentScheduleVO;
	}

	@Override
	public void updateAgentRunCompleted(AgentRunDetails agentRunDetails,Long agentRunQueueId) throws UpdateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateAgentRunCompleted ");
		}
		try{
		
			if(agentRunQueueId!=null){
				systemAgentSessionBeanLocal.deleteSystemAgentRunQueue(agentRunQueueId);
			}
			
		if(agentRunDetails.getAgentScheduleId()!=null){
			SystemAgentSchedule schedule = systemAgentSessionBeanLocal.findSystemAgentScheduleById(Long.valueOf(agentRunDetails.getAgentScheduleId()));
			boolean isError = agentRunDetails.isError();
			if(isError){
				schedule.setExecutionstatusid(AgentConstants.EXECUTION_STATUS_COMPLETED_WITH_ERROR);
			}
			
			if(schedule.getRequiredNumberofExecutions()!=null && schedule.getRequiredNumberofExecutions().intValue() ==0){
				schedule.setExecutionstatusid(AgentConstants.EXECUTION_STATUS_EXPIRED);
				schedule.setStatus(CPECommonConstants.SCHEDULE_STATUS_INACTIVE);
			}
			
			Timestamp lastExec = agentRunDetails.getExecutionStopDate()!=null ? new Timestamp(agentRunDetails.getExecutionStopDate().getTime()) : getCurrentTimestamp();
			schedule.setLastexecutiondate(lastExec);
			systemAgentSessionBeanLocal.updateSystemAgentSchedule(schedule);
			SystemAgent systemAgent = systemAgentSessionBeanLocal.findSystemAgentById(schedule.getSystemAgent().getAgentid());
			systemAgent.setLastexetime(lastExec);
			systemAgentSessionBeanLocal.updateSystemAgent(systemAgent);
		}
		
		//updateAgentRunDetails(agentRunDetails);
		}catch(Exception ex){
			if(isErrorLevel()){
				ex.printStackTrace();
			}
		}
	}

	@Override
	public Long createSystemAgentRunDetail()throws CreateBLException{
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside CreateSystemAgentRunDetail ");
		}
		
		try{
			SystemAgentRunDetail agentRunDetail = systemAgentSessionBeanLocal.createSystemAgentRunDetail(new SystemAgentRunDetail());
			return agentRunDetail.getAgentrundetailid();
		}catch(CreateBLException ex){
			throw new CreateBLException(ex.getMessage()) ;
		}
	}
	
	@Override
	public void updateAgentRunDetails(AgentRunDetails agentRunDetails)
			throws UpdateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateAgentRunDetails for agentId : " +agentRunDetails);
		}
		try {
			
		SystemAgentSchedule systemSchedule = systemAgentSessionBeanLocal.findSystemAgentScheduleById(Long.parseLong(agentRunDetails.getAgentScheduleId()));
		String executionStatus = null;
		if(agentRunDetails.isError() || agentRunDetails.isStopRequsted()){
			executionStatus = AgentConstants.EXECUTION_STATUS_COMPLETED_WITH_ERROR;
		}else{
			executionStatus  = AgentConstants.EXECUTION_STATUS_COMPLETED_SUCCESSFULLY;
		}
		if(agentRunDetails.getAgentRunId()!=null){
				SystemAgentRunDetail agentRunDetail =systemAgentSessionBeanLocal.findSystemAgentRunDetailById(Long.valueOf(agentRunDetails.getAgentRunId()));
				agentRunDetail = SystemAgentDataConversionUtil.updateSystemAgentRunDetail(agentRunDetail, agentRunDetails,  systemSchedule.getPriority()	, systemSchedule.getExecutionType().getExecutiontypeid(), executionStatus);
				systemAgentSessionBeanLocal.updateSystemAgentRunDetail(agentRunDetail);
		}else{
			SystemAgentRunDetail systemAgentRunDetail = SystemAgentDataConversionUtil.prepareSystemAgentRunDetail(agentRunDetails, systemSchedule.getPriority()	, systemSchedule.getExecutionType().getExecutiontypeid(), executionStatus);
			systemAgentSessionBeanLocal.createSystemAgentRunDetail(systemAgentRunDetail);
			agentRunDetails.setAgentRunId(systemAgentRunDetail.getAgentrundetailid()+"");
		}
			
			
		} catch (CreateBLException e) {
			e.printStackTrace();
			throw new  UpdateBLException(e.getMessage());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new  UpdateBLException(e.getMessage());
		} catch (SearchBLException e) {
			e.printStackTrace();
			throw new  UpdateBLException(e.getMessage());
		}
	}

	@Override

	public List<ViewAgentRunHistoryVO> findAgentRunHistory(Long scheduleId,Timestamp start,Timestamp stop)throws SearchBLException {

		

		SystemAgentSchedule agentSchedule = systemAgentSessionBeanLocal.findSystemAgentScheduleById(scheduleId);

		List<SystemAgentRunDetail> agentRunDetails = systemAgentSessionBeanLocal.findAgentRunDetailBySystemAgentSchedule(agentSchedule.getAgentscheduleid(),start,stop);

		List<ViewAgentRunHistoryVO> agentRunHistoryVOs = new ArrayList<ViewAgentRunHistoryVO>();

		

		for(SystemAgentRunDetail agentRunDetail : agentRunDetails) {

			
			ViewAgentRunHistoryVO agentRunHistoryVO = SystemAgentDataConversionUtil.prepareAgentRunDetail(agentRunDetail);

			agentRunHistoryVOs.add(agentRunHistoryVO);

		}

		

		return agentRunHistoryVOs;

	}

	@Override

	public List<SearchAgentRunInQueueVO> findAgentRunInQueue(String agentname,String scheduleName,String status) throws SearchBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside findAgentRunInQueue ");
			Logger.logTrace(MODULE, "agentname = " + agentname);
			Logger.logTrace(MODULE, "scheduleName = " + scheduleName);
			Logger.logTrace(MODULE, "status = " + status);
			
		}
		try{
			List<SystemAgentRunQueue> queues = systemAgentSessionBeanLocal.findAgentRunInQueue(agentname,scheduleName,status);
			
			List<SearchAgentRunInQueueVO> agentScheduleVO = new ArrayList<SearchAgentRunInQueueVO>();
			if(queues!=null && !queues.isEmpty()){
				for(SystemAgentRunQueue agentRunQueue : queues) {
		
					SearchAgentRunInQueueVO queueVO = new SearchAgentRunInQueueVO();
		
					queueVO = SystemAgentDataConversionUtil.prepareQueueVO(queueVO,agentRunQueue);
		
					agentScheduleVO.add(queueVO);
		
				}
			}
		return agentScheduleVO;
		}catch(SearchBLException e){
			if(isErrorLevel()){
				Logger.logError(MODULE,e.getMessage());
			}
			throw new SearchBLException(e.getMessage());
		}

	}

	@Override

	public SearchAgentRunInQueueVO findSystemAgentRunQueue(Long viewEntityId) throws SearchBLException {

		SystemAgentRunQueue queue = systemAgentSessionBeanLocal.findAgentRunInQueue(viewEntityId);

		SearchAgentRunInQueueVO queueVO = new SearchAgentRunInQueueVO();

		queueVO = SystemAgentDataConversionUtil.prepareQueueVO(queueVO,queue);

		

		return queueVO;

	}

	@Override
	public List<AgentVO> getAllAgentsListByName(String agentName) throws SearchBLException {
		
		List<SystemAgent> agents=systemAgentSessionBeanLocal.findAgentByName(agentName);
		if(agents!=null){
			List<AgentVO> agentVOs = SystemAgentDataConversionUtil.prepareAgentsVO(agents);
			return agentVOs;
		}else{
			return new ArrayList<AgentVO>();
		}
	}

	@Override
	public AgentVO findSystemAgentById(String agentId) throws SearchBLException {
		AgentVO agentVO = null;
		
		SystemAgent systemAgent = systemAgentSessionBeanLocal.findSystemAgentById(agentId);
		agentVO = SystemAgentDataConversionUtil.prepareAgentVO(systemAgent);
		return agentVO;
	}

	@Override
	public SystemAgentParamVO findSystemAgentData(Long scheduleId) {
		
		SystemAgentParamVO  agentParamVO = null;
		try {
			SystemAgentSchedule agentSchedule = systemAgentSessionBeanLocal.findSystemAgentScheduleById(scheduleId);
			SystemAgent systemAgent = systemAgentSessionBeanLocal.findSystemAgentById(agentSchedule.getSystemAgent().getAgentid());
			agentParamVO = SystemAgentDataConversionUtil.prepareSystemAgentParamVO(systemAgent);
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
		
		return agentParamVO;
	}

/*	@Override
	public UpdateAgentScheduleVO findAgentScheduleById(Long agentScheduleId)
			throws SearchBLException {
	
	}*/
		
	/*
	 * 
	 * 
	 * System Agent Scheduling Methods
	 * 
	 * */
	
	
	/*
	@Override
	public void updateAgentSchedule() throws UpdateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateAgentSchedule");
		}
		
		
		try {
			List<SystemAgentSchedule> systemAgentSchedules = systemAgentSessionBeanLocal.getAgentScheduleListForQueue();
			if(systemAgentSchedules!=null && !systemAgentSchedules.isEmpty()){
				Logger.logTrace(MODULE, "Schedule Size : " + systemAgentSchedules.size());
				for(SystemAgentSchedule schedule : systemAgentSchedules){
					SystemAgentRunQueue agentRunQueue = SystemAgentDataConversionUtil.prepareAgentRunQueue(schedule);
					if(schedule.getRequiredNumberofExecutions() !=null){
						if(schedule.getRequiredNumberofExecutions().intValue() > 0 ){
							schedule.setRequiredNumberofExecutions(Long.valueOf(schedule.getRequiredNumberofExecutions().intValue()-1));
						}
					}
					if(schedule.getLastexecutiondate()!=null && schedule.getExeperiodinmin()!=null){
						Calendar cal = Calendar.getInstance();
						cal.setTimeInMillis(schedule.getLastexecutiondate().getTime());
						cal.add(Calendar.MINUTE, schedule.getExeperiodinmin().intValue());
						agentRunQueue.setExecutionduedatetime(new Timestamp(cal.getTime().getTime()));
						
					}
					try {
						systemAgentSessionBeanLocal.createSystemAgentRunQueue(agentRunQueue);
						systemAgentSessionBeanLocal.updateSystemAgentSchedule(schedule);
					} catch (CreateBLException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SearchBLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}*/

    
    private boolean isEligibleForAgentRunInQueue(String pattern,Date compareDate,Date currentDateTime) throws InvalidPatternException{
    	if(isDebugLevel()){
    		Logger.logDebug(MODULE, "Schedule Pattern : " + pattern);
    		Logger.logDebug(MODULE, "Compare Date : " + compareDate);
    		Logger.logDebug(MODULE, "Current Date : " + currentDateTime);
    	}
    	boolean isValid =false;
			SchedulingPattern schedulePattern= new SchedulingPattern(pattern);
		
			Predictor predictor = new Predictor(schedulePattern,compareDate);
			Date nextExecutionDate = predictor.nextMatchingDate();
			if(isDebugLevel()){
	    		Logger.logDebug(MODULE, "Next Schedule Date : " + nextExecutionDate);
	    	}
			if(currentDateTime.after(nextExecutionDate)){
				isValid = true;
			}
		return isValid;
    }
    private Date getNextDueDate(String pattern,Date startDate) throws InvalidPatternException{
    	if(isDebugLevel()){
    		Logger.logDebug(MODULE, "Schedule Pattern : " + pattern);
    		Logger.logDebug(MODULE, "Compare Date : " + startDate);
    	}
    	SchedulingPattern schedulePattern= new SchedulingPattern(pattern);
		Predictor predictor = new Predictor(schedulePattern,startDate);
		return predictor.nextMatchingDate();
    }
	@Override
	public void updateAgentSchedule() throws UpdateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateAgentSchedule");
		}
		
		
		try {
			List<SystemAgentSchedule> systemAgentSchedules = systemAgentSessionBeanLocal.getAgentScheduleListForQueue();
			if(systemAgentSchedules!=null && !systemAgentSchedules.isEmpty()){
				Logger.logTrace(MODULE, "Schedule Size : " + systemAgentSchedules.size());
				Timestamp currentTimestamp = getCurrentTimestamp();
				Calendar cal = Calendar.getInstance();
				cal.setTime(currentTimestamp);
				cal.add(Calendar.MINUTE, AgentConstants.AGENT_SCHEDULE_ADVANCE_MIN.intValue());
				Timestamp advanceTimestamp = new Timestamp(cal.getTime().getTime());
				for(SystemAgentSchedule schedule : systemAgentSchedules){
					try {
						Timestamp compareDate = null;
						if(schedule.getLastexecutiondate()!=null){
							compareDate = schedule.getLastexecutiondate();
						}else{
							compareDate = schedule.getExecutionStartDate();
						}
						boolean isValid = isEligibleForAgentRunInQueue(schedule.getSchedulePattern(), compareDate, advanceTimestamp);
						Logger.logTrace(MODULE, "Schedule : " + schedule.getAgentscheduleid() + " is valid : " + isValid);
						if(isValid){
							boolean isalreadyinqueue = false; // checks for whether schedule already in queue and not proccessed.
							List<SystemAgentRunQueue> queueList = systemAgentSessionBeanLocal.findAgentRunInQueueByAgentScheduleId(schedule.getAgentscheduleid());
							if(queueList!=null && !queueList.isEmpty()){
								boolean deleleOther=false;
								for(SystemAgentRunQueue queue : queueList){
									if(queue.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_NOT_STARTED)){
										isalreadyinqueue =true;
										if(!deleleOther){
											queue.setExecutionstatusid(AgentConstants.EXECUTION_STATUS_RE_SCHEDULED);
											queue.setExecutionduedatetime(new Timestamp(getNextDueDate(schedule.getSchedulePattern(), queue.getExecutionduedatetime()).getTime()));
											try{
												systemAgentSessionBeanLocal.updateSystemAgentRunQueue(queue);
												deleleOther=true;
											}catch(Exception ex){
												if(isErrorLevel()){
													Logger.logError(MODULE, "Error Occured while updating agent run queue. Possible reason : " + ex.getMessage());
													ex.printStackTrace();
												}
											}
										}else{
											systemAgentSessionBeanLocal.deleteSystemAgentRunQueue(queue.getAgentrunqueueid());
										}
										
									}
									if(queue.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_RE_SCHEDULED)){
										isalreadyinqueue =true;
										queue.setExecutionduedatetime(new Timestamp(getNextDueDate(schedule.getSchedulePattern(), queue.getExecutionduedatetime()).getTime()));
										try{
											systemAgentSessionBeanLocal.updateSystemAgentRunQueue(queue);
										}catch(Exception ex){
											if(isErrorLevel()){
												Logger.logError(MODULE, "Error Occured while updating agent run queue. Possible reason : " + ex.getMessage());
												ex.printStackTrace();
											}
										}
									}
									if(queue.getExecutionstatusid().equals(AgentConstants.EXECUTION_STATUS_IN_PROGRESS)){
										isalreadyinqueue =true;
									}
								} 
								
								
							}
							if(!isalreadyinqueue){
								SystemAgentRunQueue agentRunQueue = SystemAgentDataConversionUtil.prepareAgentRunQueue(schedule);
								if(schedule.getRequiredNumberofExecutions() !=null){
									if(schedule.getRequiredNumberofExecutions().intValue() > 0 ){
										schedule.setRequiredNumberofExecutions(Long.valueOf(schedule.getRequiredNumberofExecutions().intValue()-1));
									}
								}
								agentRunQueue.setExecutionduedatetime(new Timestamp(getNextDueDate(schedule.getSchedulePattern(), compareDate).getTime()));
								systemAgentSessionBeanLocal.createSystemAgentRunQueue(agentRunQueue);
							}
							systemAgentSessionBeanLocal.updateSystemAgentSchedule(schedule);
						}
					} catch (CreateBLException e) {
						e.printStackTrace();
					} catch (InvalidPatternException e) {
						if(isErrorLevel()){
							e.printStackTrace();
						}
						if(isDebugLevel()){
							Logger.logDebug(MODULE, "Invalid Scheduling Pattern "+schedule.getSchedulePattern()+" for agent schedule , ID = " + schedule.getAgentscheduleid());
						}
					}
				}
			}
		} catch (SearchBLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public BaseSchedule getNextAgentsScheduleExcluding(ArrayList excludeList) {
		try{
		StringBuilder ecludeCondition= new StringBuilder();

		if(excludeList!=null && !excludeList.isEmpty()){
			StringBuilder builder= new StringBuilder();
			boolean isFirst = true;
			Logger.logTrace(MODULE, "Exculed List :  " + excludeList);
			Iterator<String> iter = excludeList.iterator();
			while(iter .hasNext()){
				if(!isFirst){
					builder.append(",");
				}else{
					isFirst=false;
				}
				builder.append(iter.next());
			}
			String condition = builder.toString();
			if(condition!=null && !condition.isEmpty()){
				ecludeCondition.append("a.agentscheduleid not in(");
				ecludeCondition.append(condition);
				ecludeCondition.append(")");
				ecludeCondition.append(" AND ");
			}
		}
		
		String strExcludeCondition = ecludeCondition.toString();
		if(isDebugLevel()){
			Logger.logDebug(MODULE, "ecludeCondition : " +strExcludeCondition);
		}
				Timestamp currentTimestamp = getCurrentTimestamp();
				String strQuery1 = "select  a " +
		        " from SystemAgentRunQueue a where "+strExcludeCondition +
		        " a.executionduedatetime < :currtime and a.executionstatusid = '"+AgentConstants.EXECUTION_STATUS_RE_SCHEDULED+
		        "' and  a.executiontypeid =  '"+AgentConstants.EXECUTION_TYPE_MANUAL_EXECUTION+"'  order by a.executionduedatetime,a.priority ";
		        
		        //When agent executionstatusid =EXS06 and executiontypeid=  EXT02
		        String strQuery2 = "select  a " +
		        " from SystemAgentRunQueue a where "+strExcludeCondition +
		        " a.executionduedatetime < :currtime and a.executionstatusid = '"+AgentConstants.EXECUTION_STATUS_RE_SCHEDULED+
		        "' and  a.executiontypeid =  '"+AgentConstants.EXECUTION_TYPE_AUTOMATIC_EXECUTION+"' order by a.executionduedatetime,a.priority ";
		        
		        //When agent executionstatusid =EXS01 and executiontypeid=  EXT01
		        String strQuery3 = "select  a " +
		        " from SystemAgentRunQueue a where "+strExcludeCondition +
		        " a.executionduedatetime < :currtime and a.executionstatusid = '"+AgentConstants.EXECUTION_STATUS_NOT_STARTED+
		        "' and  a.executiontypeid =  '"+AgentConstants.EXECUTION_TYPE_MANUAL_EXECUTION+"' order by a.executionduedatetime,a.priority ";
		        
		        //When agent executionstatusid =EXS01 and executiontypeid=  EXT02
		        String strQuery4 = "select  a " +
		        " from SystemAgentRunQueue a where "+strExcludeCondition +
		        " a.executionduedatetime < :currtime and a.executionstatusid = '"+AgentConstants.EXECUTION_STATUS_NOT_STARTED+
		        "' and  a.executiontypeid =  '"+AgentConstants.EXECUTION_TYPE_AUTOMATIC_EXECUTION+"'order by a.executionduedatetime,a.priority ";
		        
		       boolean issuccess = false;
		       BaseSchedule schedule = null;
		       try{
		    	   List<SystemAgentRunQueue> list = findSystemAgentRunQueue(strQuery1,currentTimestamp);
		    	   if(list!=null&&!list.isEmpty()){
		    		   SystemAgentRunQueue agentRun = list.get(0);
		    		   schedule =  SystemAgentDataConversionUtil.prepareSchedule(agentRun);
		    		   issuccess=true;
		    	   }else{
		    		   issuccess=false;   
		    	   }
		       }catch(SearchBLException ex){
		    	   issuccess=false;
		       }
		       if(!issuccess) {
		    	   try{
			    	   List<SystemAgentRunQueue> list = findSystemAgentRunQueue(strQuery2,currentTimestamp);
			    	   if(list!=null&&!list.isEmpty()){
			    		   SystemAgentRunQueue agentRun = list.get(0);
			    		   
			    		   schedule =  SystemAgentDataConversionUtil.prepareSchedule(agentRun);
			    		   
			    		   issuccess=true;
			    	   }else{
			    		   issuccess=false;   
			    	   }
			       }catch(SearchBLException ex){
			    	   issuccess=false;
			       }
		       }
		       if(!issuccess) {
		    	   try{
			    	   List<SystemAgentRunQueue> list = findSystemAgentRunQueue(strQuery3,currentTimestamp);
			    	   if(list!=null&&!list.isEmpty()){
			    		   SystemAgentRunQueue agentRun = list.get(0);
			    		   schedule =  SystemAgentDataConversionUtil.prepareSchedule(agentRun);
			    		   issuccess=true;
			    	   }else{
			    		   issuccess=false;   
			    	   }
			       }catch(SearchBLException ex){
			    	   issuccess=false;
			       }
		       }
		       if(!issuccess) {
		    	   try{
			    	   List<SystemAgentRunQueue> list = findSystemAgentRunQueue(strQuery4,currentTimestamp);
			    	   if(list!=null&&!list.isEmpty()){
			    		   SystemAgentRunQueue agentRun = list.get(0);
			    		   schedule =  SystemAgentDataConversionUtil.prepareSchedule(agentRun);
			    		   issuccess=true;
			    	   }else{
			    		   issuccess=false;   
			    	   }
			       }catch(SearchBLException ex){
			    	   issuccess=false;
			       }
		       }
		       if(issuccess && schedule!=null){
		    	   Logger.logTrace(MODULE, "schedule : " + schedule);
		    	   return schedule;
		       }else{
		    	   	return null;
		       }
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	
	private List<SystemAgentRunQueue> findSystemAgentRunQueue(String namedQuery,Timestamp currentTimestamp) throws SearchBLException{
		try{
			List<SystemAgentRunQueue> agentRunQueue =systemAgentSessionBeanLocal.findSystemAgentRunDetailByNativeQuery(namedQuery,currentTimestamp);
			return agentRunQueue;
		}catch(Exception ex){
			throw new SearchBLException(ex.getMessage());
		}
	}

	@Override
	public BaseSchedule getNextAgentsScheduleExcluding() {
		return getNextAgentsScheduleExcluding(new ArrayList());
	}

	@Override
	public void changeAgentRunStatusInQueue(Long agentRunQueueId,String executionStatusId) {
		try{
			systemAgentSessionBeanLocal.changeSystemAgentRunQueueStatus(agentRunQueueId,executionStatusId);
		}catch(Exception ex){
			
		}
	}
	
	/**
	 * 
	 * <p>Updating SystemAgentSchedule, schedule pattern. </p>
	 * @param agentScheduleId
	 * @param cronExpression
	 * @throws UpdateBLException
	 * @See {@link SchedulingPattern}
	 * */
	@Override
	public void updateAgentSchedule(UpdateAgentScheduleVO scheduleVO, IBLSession session ) throws UpdateBLException{
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateAgentSchedule , cronExpression :" + scheduleVO.getCronExpression() + " , agentScheduleId :" + scheduleVO.getScheduleId());
		}
		try{
			SystemAgentSchedule agentSchedule = systemAgentSessionBeanLocal.findSystemAgentScheduleById(scheduleVO.getScheduleId());
//			AuditSummary data = AuditDataConversionUtilities.prepareAgentScheduleUpdateAudit(agentSchedule, scheduleVO, session);
			AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareAgentScheduleUpdateAudit(agentSchedule, scheduleVO, session);
			agentSchedule = SystemAgentDataConversionUtil.updateAgentSchedule(agentSchedule,scheduleVO,session);
			systemAgentSessionBeanLocal.updateSystemAgentSchedule(agentSchedule);
			
//			if(agentSchedule != null) {
//				data.mergeDataEntry();
//				systemAuditFacadeLocal.doAuditEntry(data);
//			}
			// Audit entry
			Map<String,Object> mapAudit = new HashMap<String, Object>();
			mapAudit.put(AuditTagConstant.AGENTSCHEDULENAME,agentSchedule.getName());
			
			addToAuditDynamicMessage(AuditConstants.UPDATE_AGENT_SCHEDULE, "Updating Agent Schedule",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), session);
			
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "Agent Schedule Updated Succesfully.");
			}
		}catch(Exception e){
			if(isErrorLevel()){
				Logger.logError(MODULE, "Error occured while updating agent schedule schedule pattern. Possible reason " + e.getMessage());
				e.printStackTrace();
			}
			throw new UpdateBLException(e.getMessage());
		}
	}
	/**
	 * 
	 * <p>Updating SystemAgentSchedule, Execution Status </p>
	 * @param agentScheduleId
	 * @param cronExpression
	 * @throws executionStatusId
	 * */
	@Override
	public void updateAgentScheduleExecutionStatus(Long agentScheduleId,String executionStatusId) throws UpdateBLException{
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateAgentScheduleExecutionStatus ,  agentScheduleId :" + agentScheduleId);
		}
		try{
			SystemAgentSchedule agentSchedule = systemAgentSessionBeanLocal.findSystemAgentScheduleById(agentScheduleId);
			agentSchedule.setExecutionstatusid(executionStatusId);
			systemAgentSessionBeanLocal.updateSystemAgentSchedule(agentSchedule);
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "Agent Schedule Execution status Updated Succesfully.");
			}
		}catch(Exception e){
			if(isErrorLevel()){
				Logger.logError(MODULE, "Error occured while updating agent schedule ExecutionStatus. Possible reason " + e.getMessage());
				e.printStackTrace();
			}
			throw new UpdateBLException(e.getMessage());
		}
	}
	
	/**
	 * <p>Cancel SystemAgentSchedule, Once agent schedule is cancelled then it will not be appear in search and will not come to execution.</p>
	 * @param agentScheduleId
	 * @param reason 
	 * @throws UpdateBLException
	 * */
	@Override
	public void cancelAgentSchedule(Long agentScheduleId,String reason) throws UpdateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside cancelAgentSchedule , agentScheduleId :" + agentScheduleId);
		}
		try{
			SystemAgentSchedule agentSchedule = systemAgentSessionBeanLocal.findSystemAgentScheduleById(agentScheduleId);
			agentSchedule.setStatus(CPECommonConstants.SCHEDULE_STATUS_INACTIVE);
			agentSchedule.setExecutionstatusid(AgentConstants.EXECUTION_STATUS_EXPIRED);
			agentSchedule.setReasonforschedule(reason);
			systemAgentSessionBeanLocal.updateSystemAgentSchedule(agentSchedule);
		}catch(Exception e){
			if(isErrorLevel()){
				Logger.logError(MODULE, "Error occured while canceling agent schedule. Possible reason " + e.getMessage());
				e.printStackTrace();
			}
			throw new UpdateBLException(e.getMessage());
		}
	}
	
	
/**
 * 	Change Agent Service status.
 * @throws UpdateBLException 
 * */
	@Override
	public void changeAgentServiceStatus(AgentServiceVO agentServiceVO) throws UpdateBLException{
		Logger.logTrace(MODULE, "Changing Agent Service Status");
		try{
		if(agentServiceVO.isEnable()){
			startService();
		}else{
			stopService();
		}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new UpdateBLException("Error Occured while updating Service status");
		}
	}
	
	

	@Override
	public boolean startService() throws AgentServiceException {
		boolean returnvalue=false;
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "Starting Service");
		}
		 
		 try {
			 MBeanServer server = MBeanServerLocator.locate();
			 String[] nullSignature = {};
			 Object[] nullParameters = {};
			 ObjectName objectName = new ObjectName("AgentApp:name=EliteAgentServiceManager");
			 if(server.isRegistered(objectName)){
				 Logger.logTrace(MODULE, "Service is Registered");
				 if(server.getAttribute(objectName, "AgentManagerProviderClass")==null){
					 server.setAttribute(objectName,new Attribute("AgentManagerProviderClass","com.elitecore.cpe.bl.agents.provider.AgentManagerProvider"));
				 }
				 if(server.getAttribute(objectName, "AgentWorkerProviderClass")==null){
					 server.setAttribute(objectName,new Attribute("AgentWorkerProviderClass","com.elitecore.cpe.bl.agents.provider.AgentWorkerProvider"));
				 }
				 server.invoke(objectName, AgentConstants.START_LOGICALLY, nullParameters, nullSignature);
				 returnvalue=true;
			 }else{
				 Logger.logTrace(MODULE, "Service is not Registered");
			 }
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReflectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AttributeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception ex){
			throw new AgentServiceException(ex.getMessage());
		}
		 return returnvalue;
	}
	
	private boolean invokeMBeanMethod(String[] signature,Object[] parameters,String methodName) throws AgentServiceException{
		boolean returnval=false;
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "invokeMBeanMethod  methodName : "+methodName );
		}
		 try {
			 MBeanServer server = MBeanServerLocator.locate();
			 ObjectName objectName = new ObjectName(AgentConstants.MBEAN_OBJECT_NAME);
			 server.invoke(objectName, methodName, parameters, signature);
			 Logger.logTrace(MODULE, "Invoked successfully");
			 returnval =true;
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch(Exception e){
			throw new AgentServiceException(e.getMessage());
		}
		return returnval;
	}
	private Object invokeMBeanMethodandGetObject(String[] signature,Object[] parameters,String methodName) throws AgentServiceException{
		Object returnval=null;
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "invokeMBeanMethod  methodName : "+methodName );
		}
		 try {
			 MBeanServer server = MBeanServerLocator.locate();
			 ObjectName objectName = new ObjectName(AgentConstants.MBEAN_OBJECT_NAME);
			 returnval = server.invoke(objectName, methodName, parameters, signature);
			 Logger.logTrace(MODULE, "Invoked successfully");
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch(Exception e){
			throw new AgentServiceException(e.getMessage());
		}
		return returnval;
	}
	@Override
	public boolean stopService() throws AgentServiceException {
		boolean returnval=false;
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "Stopping Service");
		}
		returnval = invokeMBeanMethod(new String[]{}, new Object[]{}, AgentConstants.STOP_LOGICALLY);
		 /*try {
			 MBeanServer server = MBeanServerLocator.locate();
			 String[] nullSignature = {};
			 Object[] nullParameters = {};
			 ObjectName objectName = new ObjectName(AgentConstants.MBEAN_OBJECT_NAME);
			 server.invoke(objectName, AgentConstants.STOP_LOGICALLY, nullParameters, nullSignature);
			 Logger.logTrace(MODULE, "Invoked successfully");
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReflectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			throw new AgentServiceException(e.getMessage());
		}
*/		return returnval;
	}

	@Override
	public boolean stopAgentRunByName(String agentRunId)
			throws AgentServiceException {
		boolean returnvalue= false;
		returnvalue = invokeMBeanMethod(new String[]{"java.lang.String"}, new Object[]{agentRunId}, "stopAgentRunByName");
		return returnvalue;
	}

	@Override
	public boolean stopAgentRunByScheduleId(String agentScheduleId)
			throws AgentServiceException {
		boolean returnvalue= false;
		returnvalue = invokeMBeanMethod(new String[]{"java.lang.String"}, new Object[]{agentScheduleId}, "stopAgentRunByScheduleId");
		return returnvalue;
	}

	@Override
	public Hashtable getAgentServiceThreadPools() throws AgentServiceException {
		Object obj= invokeMBeanMethodandGetObject(new String[]{}, new Object[]{}, "getAgentServiceThreadPools");
		Hashtable table = null;
		if(obj!=null && obj instanceof Hashtable){
			table =(Hashtable) obj;
		}
		return table;
	}

	@Override
	public String getMonitorShortDescription() throws AgentServiceException {
		Object obj= invokeMBeanMethodandGetObject(new String[]{}, new Object[]{}, "getMonitorShortDescription");
		String table = null;
		if(obj!=null && obj instanceof String){
			table =(String) obj;
		}
		return table;
	}

	@Override
	public Hashtable getAgentManagerDetails() throws AgentServiceException {
		Object obj= invokeMBeanMethodandGetObject(new String[]{}, new Object[]{}, "getAgentManagerDetails");
		Hashtable table = null;
		if(obj!=null && obj instanceof Hashtable){
			table =(Hashtable) obj;
		}
		return table;
	}

	@Override
	public Collection getAgentRunStates() throws AgentServiceException {
		Object obj= invokeMBeanMethodandGetObject(new String[]{}, new Object[]{}, "getAgentRunStates");
		Collection table = null;
		if(obj!=null && obj instanceof Collection){
			table =(Collection) obj;
		}
		return table;
	}

	@Override
	public boolean doThreadMonitoring() throws AgentServiceException {
		boolean returnvalue= false;
		returnvalue = invokeMBeanMethod(new String[]{}, new Object[]{}, "doThreadMonitoring");
		return returnvalue;
	}

	@Override
	public String getServiceState() throws AgentServiceException {
		Object obj= invokeMBeanMethodandGetObject(new String[]{}, new Object[]{}, "getAgentRunStates");
		String table = null;
		if(obj!=null && obj instanceof String){
			table =(String) obj;
		}
		return table;
	}

	@Override
	public AgentServiceSummaryData getAgentServiceSummary()
			throws AgentServiceException {
		Object obj= invokeMBeanMethodandGetObject(new String[]{}, new Object[]{}, "getAgentServiceSummary");
		AgentServiceSummaryData table = null;
		if(obj!=null && obj instanceof AgentServiceSummaryData){
			table =(AgentServiceSummaryData) obj;
		}
		return table;
	}

	@Override
	public boolean stopScheduleByAgentRunId(String strAgentRunId)
			throws AgentServiceException {
		boolean returnvalue= false;
		returnvalue = invokeMBeanMethod(new String[]{"java.lang.String"}, new Object[]{strAgentRunId}, "stopScheduleByAgentRunId");
		return returnvalue;
	}

	/**
	 * This method will be called when session bean will get 
	 * */
	@Override
	public void cleanAgentActivities(){
			try {
				List<SystemAgentRunQueue> agentRunInQueue = systemAgentSessionBeanLocal.findAgentRunInQueue();
				if(agentRunInQueue!=null && !agentRunInQueue.isEmpty()){
					for(SystemAgentRunQueue agentINQueue : agentRunInQueue){
						stopAgentRunByScheduleId(agentINQueue.getSystemAgentSchedule().getAgentscheduleid()+"");
					}
				}
				boolean retval = stopService();
				if(retval){
					Logger.logTrace(MODULE, "Service stopeed successfully.");
				}
			} catch (SearchBLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AgentServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public void rescheduleInprocessAgents() {
		try{
			List<SystemAgentRunQueue> inProcessed = systemAgentSessionBeanLocal.findAgentRunInQueueByStatus(AgentConstants.EXECUTION_STATUS_IN_PROGRESS);
			rescheduleAgents(inProcessed);
			
			List<SystemAgentRunQueue> stopInProcess = systemAgentSessionBeanLocal.findAgentRunInQueueByStatus(AgentConstants.EXECUTION_STATUS_STOP_IN_PROGRESS);
			rescheduleAgents(stopInProcess);
			
			/*List<SystemAgentRunQueue> stopInProcess = systemAgentSessionBeanLocal.findAgentRunInQueueByStatus(AgentConstants.EXECUTION_STATUS_STOPPED_ON_USER_REQUEST);
			rescheduleAgents(stopInProcess);*/
			
		}catch(SearchBLException ex){
			
		}
	}
		/** Use for rescheduling agents
		 * @throws UpdateBLException */
	private void rescheduleAgents(List<SystemAgentRunQueue> agentsInQueue){
		if(agentsInQueue!=null && !agentsInQueue.isEmpty()){
			try {
				for(SystemAgentRunQueue agent : agentsInQueue){
					agent.setExecutionstatusid(AgentConstants.EXECUTION_STATUS_RE_SCHEDULED);
						systemAgentSessionBeanLocal.updateSystemAgentRunQueue(agent);
				}
			} catch (UpdateBLException e) {
				e.printStackTrace();
			}
		}
	}

		@Override
		public List<ComboBoxData> findAllScheduleStatus()
				throws SearchBLException {
			
			List<ComboBoxData> boxDatas = new ArrayList<ComboBoxData>();
			List<ExecutionStatus> executionStatus = systemAgentSessionBeanLocal.findAllScheduleStatus();
			
			if(executionStatus!=null && !executionStatus.isEmpty()) {
				for(ExecutionStatus status : executionStatus) {
					boxDatas.add(new ComboBoxData(status.getExecutionstatusid(), status.getName()));
				}
			}
			
			return boxDatas;
		}
		
		@Override
		public PreUpdateAgentParamVO preUpdateAgentParamById(String agentId)throws SearchBLException 
		{
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "inside preUpdateAgentParamById , agentId :" + agentId);
			}
			PreUpdateAgentParamVO preUpdateAgentParamVO = null;
			try {
				 	if(systemAgentSessionBeanLocal != null)
				 	{
				 		AgentParam agentParam = systemAgentSessionBeanLocal.findAgentParamById(agentId);
				 		if(agentParam != null)
				 		{
				 			preUpdateAgentParamVO = SystemAgentDataConversionUtil.convertToPreUpdateAgentParam(agentParam);
					 	}else
				 		{
				 			Logger.logTrace(MODULE, "agentParam is null");
				 		}
				 	}
				 	else
				 	{
				 		Logger.logTrace(MODULE, "systemAgentSessionBeanLocal is null");
				 	}
					Logger.logTrace(MODULE, "returning preUpdateAgentParamById");
					return preUpdateAgentParamVO;
			}catch (SearchBLException e) {
				e.printStackTrace();
				throw new SearchBLException(e.getMessage());
			}
		}
		
		@Override
		public void updateAgentParameter(UpdateAgentParameterVO updateAgentParamVO,IBLSession session)throws UpdateBLException 
		{
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "inside updateAgentParameter , agentId :" + updateAgentParamVO.getAgentid());
			}
			try {
				
					if(systemAgentSessionBeanLocal != null)
				 	{
				 		AgentParam agentParam = systemAgentSessionBeanLocal.findAgentParamById(updateAgentParamVO.getAgentid());
				 		if(agentParam != null)
				 		{
				 			agentParam = SystemAgentDataConversionUtil.convertToUpdateAgentParamData(agentParam,updateAgentParamVO,session);
				 			AgentParam agentParamData = systemAgentSessionBeanLocal.updateAgentParam(agentParam);
				 		}else
				 		{
				 			Logger.logTrace(MODULE, "agentParam is null");
				 		}
				 	}
				 	else
				 	{
				 		Logger.logTrace(MODULE, "systemAgentSessionBeanLocal is null");
				 	}
				 	
			} catch(UpdateBLException e) {
				e.printStackTrace();
				throw e;
			} catch (SearchBLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public AgentScheduleProcedureWrapperData findAgentScheduleForProcedure(
				String entityId) throws SearchBLException {
//			AREvent eventData = eventSessionBeanLocal.findAREventByEventId(entityId);
//			AgentScheduleProcedureWrapperData wrapperData = new AgentScheduleProcedureWrapperData();
//			if(eventData!=null && eventData.getProcedureName()!=null && !eventData.getProcedureName().isEmpty()) {
//				wrapperData.setIsSchedulable(true);
//			} else {
//				wrapperData.setIsSchedulable(false);
//			}
//			List<SystemAgentSchedule> schedules = systemAgentSessionBeanLocal.findSystemAgentSchedule("","");
//			if(schedules!=null && !schedules.isEmpty()) {
//				for(SystemAgentSchedule agentSchedule : schedules) {
//					if(agentSchedule.getSystemAgentScheduleParameters()!=null && !agentSchedule.getSystemAgentScheduleParameters().isEmpty()) {
//						for(SystemAgentScheduleParameter parameter : agentSchedule.getSystemAgentScheduleParameters()) {
//							if(parameter.getParameteralias().equalsIgnoreCase(AgentConstants.EVENTID_PARAM)) {
//								if(parameter.getTextvalue().equals(entityId)) {
//									wrapperData.setEventId(entityId);
//									try {
//										if((agentSchedule.getRequiredNumberofExecutions().equals(Long.valueOf(-1)) || agentSchedule.getRequiredNumberofExecutions().intValue() > 0 ) && agentSchedule.getSchedulePattern()!=null && agentSchedule.getStatus()!=null && agentSchedule.getStatus().equals(CPECommonConstants.SCHEDULE_STATUS_ACTIVE)){
//											Predictor predictor = new Predictor(agentSchedule.getSchedulePattern());
//											wrapperData.setNextScheduleDate(new Timestamp(predictor.nextMatchingTime()));
//										}
//									} catch (InvalidPatternException e) {
//										e.printStackTrace();
//									}
//									return wrapperData;
//								}
//							}
//						}
//					}
//				}
//			}
			return null;
		}	
}
