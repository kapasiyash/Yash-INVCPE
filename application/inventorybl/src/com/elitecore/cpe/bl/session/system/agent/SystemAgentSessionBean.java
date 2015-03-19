package com.elitecore.cpe.bl.session.system.agent;



import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;

import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.entity.inventory.system.agent.AgentParam;
import com.elitecore.cpe.bl.entity.inventory.system.agent.ExecutionStatus;
import com.elitecore.cpe.bl.entity.inventory.system.agent.ExecutionType;
import com.elitecore.cpe.bl.entity.inventory.system.agent.SystemAgent;
import com.elitecore.cpe.bl.entity.inventory.system.agent.SystemAgentParameter;
import com.elitecore.cpe.bl.entity.inventory.system.agent.history.SystemAgentRunDetail;
import com.elitecore.cpe.bl.entity.inventory.system.agent.history.SystemAgentRunQueue;
import com.elitecore.cpe.bl.entity.inventory.system.agent.schedule.SystemAgentSchedule;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.util.logger.Logger;



@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class SystemAgentSessionBean extends BaseSessionBean implements SystemAgentSessionBeanLocal {

    private String MODULE = "SYSTEMAGENTSESSIONBEAN";

    /**
	 * finds all active system agent currently configured in system.
	 * @author yash.kapasi
	 * @return {@link List}<{@link SystemAgent}> activesystemagents.
	 * @throws SearchBLException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemAgent> findAllActiveSystemAgents() throws SearchBLException {
		
		logTrace(MODULE, "In findAllActiveSystemAgents");
		List<SystemAgent> systemAgentList = null;
		try {

			systemAgentList = getEntityManager().createNamedQuery("SystemAgent.findAllActiveAgents")
			.getResultList();
			
		}catch(NoResultException e) {
			systemAgentList = new ArrayList<SystemAgent>();
		}catch(Exception e) {
			throw new SearchBLException("Find active system agents operation failed, reason: " + e.getMessage(), e);
		}
		logTrace(MODULE,"Returning from findAllActiveSystemAgents");
		return systemAgentList;
	}

	
	
	 	/**
		 * creates system agent schedule to schedule the agent.
		 * @author yash.kapasi
		 * @throws CreateBLException
		 */
	@Override
	public void createSystemAgentSchedule(SystemAgentSchedule systemAgentSchedule) throws CreateBLException {

		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createSystemAgentSchedule");
		}
		try {			
			getEntityManager().persist(systemAgentSchedule);
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning createSystemAgentSchedule");
			}
			
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in createSystemAgentSchedule Reason" +e.getMessage());
			getSessionContext().setRollbackOnly();
			throw new CreateBLException("Create SystemAgent Schedule Operation Failed, Reason : " + e.getMessage(), e);
		}
	}


	/**
	 * Updates system agent schedule to schedule the agent.
	 * @author yash.kapasi
	 * @throws UpdateBLException
	 */
	@Override
	public void updateSystemAgentSchedule(SystemAgentSchedule systemAgentSchedule) throws UpdateBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  updateSystemAgentSchedule");
		try {			
			getEntityManager().merge(systemAgentSchedule);
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in updateSystemAgentSchedule Reason" +e.getMessage());
			getSessionContext().setRollbackOnly();
			throw new UpdateBLException("Update updateSystemAgentSchedule Operation Failed, Reason : " + e.getMessage(), e);
		}
		
	}


	/**
	 * finds all system agent schedule of the system
	 * @author yash.kapasi
	 * @return {@link List}<{@link SystemAgentSchedule}> agentSchedules.
	 * @throws SearchBLException
	 */
	@Override
	public List<SystemAgentSchedule> findAllSystemAgentSchedule()throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAllMode");
		try {
			List<SystemAgentSchedule>  agentSchedules = getEntityManager().createNamedQuery("SystemAgentSchedule.findAllSystemAgentSchedule").getResultList();
		 return agentSchedules;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAllSystemAgentSchedule Reason" +e.getMessage());
			throw new SearchBLException("Find SystemAgentSchedule operation failed, reason: " + e.getMessage(), e);
		}
	}

	
	/**
	 * Find system agent schedule by ScheduleId.
	 * @author yash.kapasi
	 * @param scheduleId
	 * @return SystemAgentSchedule systemAgentSchedule.
	 * @throws SearchBLException
	 */
	@Override
	public SystemAgentSchedule findSystemAgentScheduleById(Long scheduleId)
			throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findSystemAgentScheduleById agentrundetailid :" +scheduleId);
		try {
			SystemAgentSchedule  runDetail = (SystemAgentSchedule) getEntityManager().createNamedQuery("SystemAgentSchedule.findSystemAgentScheduleById")
					.setParameter("agentscheduleid", scheduleId).getSingleResult();
		 return runDetail;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findSystemAgentScheduleById Reason" +e.getMessage());
			throw new SearchBLException("Find findSystemAgentScheduleById operation failed, reason: " + e.getMessage(), e);
		}
	}
	
	
	/**
	 * Find system agent schedule by executionTypeId.
	 * @author yash.kapasi
	 * @param executionTypeId
	 * @return SystemAgentSchedule systemAgentSchedule.
	 * @throws SearchBLException
	 */
	@Override
	public SystemAgentSchedule findAgentScheduleByExecutionType(String executionTypeId) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentScheduleByExecutionType systemagent :" +executionTypeId);
		try {
			SystemAgentSchedule  agentSchedule = (SystemAgentSchedule) getEntityManager().createNamedQuery("SystemAgentSchedule.findAgentScheduleByExecutionType")
					.setParameter("executiontypeid", executionTypeId).getSingleResult();
		 return agentSchedule;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentScheduleByExecutionType Reason" +e.getMessage());
			throw new SearchBLException("Find findAgentScheduleByExecutionType operation failed, reason: " + e.getMessage(), e);
		}
	}


	/**
	 * Find system agent schedule by system Agent Id.
	 * @author yash.kapasi
	 * @param systemAgentId
	 * @return SystemAgentSchedule systemAgentSchedule.
	 * @throws SearchBLException
	 */
	@Override
	public SystemAgentSchedule findAgentScheduleBySystemAgent(String systemAgentId) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentScheduleBySystemAgent systemagent :" +systemAgentId);
		try {
			SystemAgentSchedule  agentSchedule = (SystemAgentSchedule) getEntityManager().createNamedQuery("SystemAgentSchedule.findAgentScheduleBySystemAgent")
					.setParameter("systemAgent", systemAgentId).getSingleResult();
		 return agentSchedule;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentScheduleBySystemAgent Reason" +e.getMessage());
			throw new SearchBLException("Find findAgentScheduleBySystemAgent operation failed, reason: " + e.getMessage(), e);
		}
	}


	

	//System Agent
	/**
	 * Creates a New System Agent in the System
	 * @author yash.kapasi
	 * @param systemAgent
	 * @throws CreateBLException
	 */
	@Override
	public void createSystemAgent(SystemAgent systemAgent)throws CreateBLException {
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createSystemAgent");
		}
		try {			
			getEntityManager().persist(systemAgent);
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning createSystemAgent");
			}
			
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in createSystemAgent Reason" +e.getMessage());
			getSessionContext().setRollbackOnly();
			throw new CreateBLException("Create createSystemAgent Operation Failed, Reason : " + e.getMessage(), e);
		}
	}


	/**
	 * Updates the existing System Agent in the System
	 * @author yash.kapasi
	 * @param systemAgent
	 * @throws UpdateBLException
	 */
	@Override
	public void updateSystemAgent(SystemAgent systemAgent)throws UpdateBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  updateSystemAgent");
		try {			
			getEntityManager().merge(systemAgent);
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in updateSystemAgent Reason" +e.getMessage());
			getSessionContext().setRollbackOnly();
			throw new UpdateBLException("Update updateSystemAgent Operation Failed, Reason : " + e.getMessage(), e);
		}
		
	}

	/**
	 * Finds all System Agents in the System
	 * @author yash.kapasi
	 * @return {@link List}<{@link SystemAgent}> agents.
	 * @throws SearchBLException
	 */
	@Override
	public List<SystemAgent> findAllSystemAgent() throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAllSystemAgent");
		try {
			List<SystemAgent>  agents = getEntityManager().createNamedQuery("SystemAgent.findAllSystemAgent").getResultList();
		 return agents;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAllSystemAgent Reason" +e.getMessage());
			throw new SearchBLException("Find SystemAgent operation failed, reason: " + e.getMessage(), e);
		}
	}

	/**
	 * Finds a System Agent in the System by AgentId
	 * @author yash.kapasi
	 * @param agentId
	 * @return SystemAgent agent.
	 * @throws SearchBLException
	 */
	@Override
	public SystemAgent findSystemAgentById(String agentId) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findSystemAgentById agentrundetailid :" +agentId);
		try {
			SystemAgent  agent = (SystemAgent) getEntityManager().createNamedQuery("SystemAgent.findSystemAgentById")
					.setParameter("agentid", agentId).getSingleResult();
		 return agent;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findSystemAgentById Reason" +e.getMessage());
			throw new SearchBLException("Find findSystemAgentById operation failed, reason: " + e.getMessage(), e);
		}
	}


	/**
	 * Finds a System Agent in the System by ExecutionType Id
	 * @author yash.kapasi
	 * @param executionTypeid
	 * @return SystemAgent agent.
	 * @throws SearchBLException
	 */
	@Override
	public SystemAgent findSystemAgentByExecutionType(String executionTypeid) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findSystemAgentByExecutionType systemagent :" +executionTypeid);
		try {
			SystemAgent  agent = (SystemAgent) getEntityManager().createNamedQuery("SystemAgentSchedule.findSystemAgentByExecutionType")
					.setParameter("executiontypeid", executionTypeid).getSingleResult();
		 return agent;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findSystemAgentByExecutionType Reason" +e.getMessage());
			throw new SearchBLException("Find findSystemAgentByExecutionType operation failed, reason: " + e.getMessage(), e);
		}
	}

	
	
	
	
	
	
	
	//SystemAgent Run Details
	

	/**
	 * Creates Agent Run Detail in the System
	 * @author yash.kapasi
	 * @param SystemAgentRunDetail agentRunDetail
	 * @return SystemAgentRunDetail agentRunDetail.
	 * @throws CreateBLException
	 */
	@Override
	public SystemAgentRunDetail createSystemAgentRunDetail(SystemAgentRunDetail agentRunDetail) throws CreateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createSystemAgentRunDetail , agentRunDetail : "+agentRunDetail);
		}
		try {			
			getEntityManager().persist(agentRunDetail);
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning createSystemAgentRunDetail");
			}
			return agentRunDetail;
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in createSystemAgentRunDetail Reason" +e.getMessage());
			throw new CreateBLException("Create createSystemAgentRunDetail Operation Failed, Reason : " + e.getMessage(), e);
		}
		
	}


	
	/**
	 * Updates an existing Agent Run Detail in the System
	 * @author yash.kapasi
	 * @param SystemAgentRunDetail agentRunDetail
	 * @throws UpdateBLException
	 */
	@Override
	public void updateSystemAgentRunDetail(SystemAgentRunDetail agentRunDetail)
			throws UpdateBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  updateSystemAgentRunDetail");
		try {			
			getEntityManager().merge(agentRunDetail);
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in updateSystemAgentRunDetail Reason" +e.getMessage());
			getSessionContext().setRollbackOnly();
			throw new UpdateBLException("Update updateSystemAgentRunDetail Operation Failed, Reason : " + e.getMessage(), e);
		}
		
	}


	/**
	 * Finds all System Agent Run detail in the system
	 * @author yash.kapasi
	 * @return {@link List}<{@link SystemAgentRunDetail}> agentRunDetails.
	 * @throws SearchBLException
	 */
	@Override
	public List<SystemAgentRunDetail> findAllSystemAgentRunDetail()
			throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAllSystemAgentRunDetail");
		try {
			List<SystemAgentRunDetail>  agentRunDetails = getEntityManager().createNamedQuery("SystemAgentRunDetail.findAllSystemAgentRunDetail").getResultList();
		 return agentRunDetails;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAllSystemAgentRunDetail Reason" +e.getMessage());
			throw new SearchBLException("Find SystemAgentRunDetail operation failed, reason: " + e.getMessage(), e);
		}
	}

	
	/**
	 * Finds System Agent Run detail in the system by Agent run Id
	 * @author yash.kapasi
	 * @param Long agentRunDetailId
	 * @return SystemAgentRunDetail agentRunDetail.
	 * @throws SearchBLException
	 */
	@Override
	public SystemAgentRunDetail findSystemAgentRunDetailById(Long agentRunDetailId) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findSystemAgentRunDetailById agentrundetailid :" +agentRunDetailId);
		try {
			SystemAgentRunDetail  runDetail = (SystemAgentRunDetail) getEntityManager().createNamedQuery("SystemAgentRunDetail.findSystemAgentRunDetailById")
					.setParameter("agentrundetailid", agentRunDetailId).getSingleResult();
		 return runDetail;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findSystemAgentRunDetailById Reason" +e.getMessage());
			throw new SearchBLException("Find SystemAgentRunDetailById operation failed, reason: " + e.getMessage(), e);
		}
	}


	/**
	 * Finds System Agent Run detail in the system by Agent Id
	 * @author yash.kapasi
	 * @param String systemAgentId
	 * @return SystemAgentRunDetail agentRunDetail.
	 * @throws SearchBLException
	 */
	@Override
	public SystemAgentRunDetail findAgentRunDetailBySystemAgent(String systemAgentId) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentRunDetailBySystemAgent systemagent :" +systemAgentId);
		try {
			SystemAgentRunDetail  runDetail = (SystemAgentRunDetail) getEntityManager().createNamedQuery("SystemAgentRunDetail.findAgentRunDetailBySystemAgent")
					.setParameter("systemAgent", systemAgentId).getSingleResult();
		 return runDetail;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentRunDetailBySystemAgent Reason" +e.getMessage());
			throw new SearchBLException("Find findAgentRunDetailBySystemAgent operation failed, reason: " + e.getMessage(), e);
		}
	}


	/**
	 * Finds System Agent Run detail in the system by AgentSchedule Id
	 * @author yash.kapasi
	 * @param Long systemAgentScheduleId
	 * @return {@link List}<{@link SystemAgentRunDetail}> runDetail.
	 * @throws SearchBLException
	 */
	@Override
	public List<SystemAgentRunDetail> findAgentRunDetailBySystemAgentSchedule(
			Long systemAgentScheduleId) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentRunDetailBySystemAgentSchedule systemagent :" +systemAgentScheduleId);
		try {
			 List<SystemAgentRunDetail>  runDetail =  getEntityManager().createNamedQuery("SystemAgentRunDetail.findAgentRunDetailBySystemAgentSchedule")
					.setParameter("systemAgentSchedule", systemAgentScheduleId).getResultList();
		 return runDetail;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentRunDetailBySystemAgentSchedule Reason" +e.getMessage());
			throw new SearchBLException("Find findAgentRunDetailBySystemAgentSchedule operation failed, reason: " + e.getMessage(), e);
		}
	}

	
	
	
	/**
	 * Finds System Agent Parameters in the system by Agent Id
	 * @author yash.kapasi
	 * @param String agentId
	 * @return {@link List}<{@link SystemAgentParameter}> agentParams.
	 * @throws SearchBLException
	 */
	@Override
	public List<SystemAgentParameter> findSystemParameters(String agentId) throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findSystemParameters systemagent :" +agentId);
		try {
			List<SystemAgentParameter>  agent = (List<SystemAgentParameter>) getEntityManager().createNamedQuery("SystemAgentParameter.findSystemParameters")
					.setParameter("agentid", agentId).getResultList();
		 return agent;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findSystemParameters Reason" +e.getMessage());
			throw new SearchBLException("Find findSystemParameters operation failed, reason: " + e.getMessage(), e);
		}
	}


	/**
	 * Finds Agent Execution Type in the system by executionId
	 * @author yash.kapasi
	 * @param String executionId
	 * @return ExecutionType executionType.
	 * @throws SearchBLException
	 */
	@Override
	public ExecutionType findExecutionTypeById(String executionId)
			throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findExecutionTypeById agentrundetailid :" +executionId);
		try {
			ExecutionType  executionType = (ExecutionType) getEntityManager().createNamedQuery("ExecutionType.findExecutionTypeById")
					.setParameter("executiontypeid", executionId).getSingleResult();
		 return executionType;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findExecutionTypeById Reason" +e.getMessage());
			throw new SearchBLException("Find findExecutionTypeById operation failed, reason: " + e.getMessage(), e);
		}
	}


	/**
	 * Finds Agent Schedule in the system by name and status
	 * @author yash.kapasi
	 * @param String name
	 * @param String statusId
	 * @return {@link List}<{@link SystemAgentSchedule}> systemAgentSchduleList.
	 * @throws SearchBLException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemAgentSchedule> findSystemAgentSchedule(String name,String statusId)throws SearchBLException {
		logTrace(MODULE, "In findSystemAgentScheduleByName");
		List<SystemAgentSchedule> systemAgentSchduleList = null;
		try {

			systemAgentSchduleList = getEntityManager().createNamedQuery("SystemAgentSchedule.findSystemAgentSchedule")
					.setParameter("name",formatForUpperLikeSearch(name))
					.setParameter("executionStatus", formatForUpperLikeSearch(statusId))
			.getResultList();
			
		}catch(NoResultException e) {
			systemAgentSchduleList = new ArrayList<SystemAgentSchedule>();
		}catch(Exception e) {
			throw new SearchBLException("Find findSystemAgentScheduleByName operation failed, reason: " + e.getMessage(), e);
		}
		logTrace(MODULE,"Returning from findSystemAgentScheduleByName");
		return systemAgentSchduleList;
	}


	/**
	 * Finds Agent Run Details in the system by namedQuery and current Timestamp
	 * @author yash.kapasi
	 * @param String namedQuery
	 * @param Timestamp currentTimestamp
	 * @return {@link List}<{@link SystemAgentRunQueue}> systemAgentRunQueues.
	 * @throws SearchBLException
	 */
	@Override
	public List<SystemAgentRunQueue> findSystemAgentRunDetailByNativeQuery(String namedQuery,Timestamp currentTimestamp) throws SearchBLException {
		try{
			Logger.logTrace(MODULE, "inside findSystemAgentRunDetailByNativeQuery , namedQuery : " + namedQuery);
			
			List<SystemAgentRunQueue> systemAgentRunQueues = getEntityManager().createQuery(namedQuery).setParameter("currtime", currentTimestamp).getResultList();
			return systemAgentRunQueues;
		}catch(NoResultException ex ){
			return null;
		}catch(Exception ex){
			if(isErrorLevel()){
				Logger.logError(MODULE, "Unable to find Agent run in queue. Possible reason: " + ex.getMessage());
				Logger.logTrace(MODULE, ex);
			}
			throw new SearchBLException("Unable to find Agent run in queue. Possible reason: " + ex.getMessage()); 
		}
	}

	public static void main(String[] args) {
		Timestamp t = new Timestamp(new Date().getTime());
		System.out.println(t);
	}

	/*
	  @Override
	  public List<SystemAgentSchedule> getAgentScheduleListForQueue()
			throws SearchBLException {
		try{
			Timestamp currentTimestamp = getCurrentTimestamp();
			SimpleDateFormat sdf = new SimpleDateFormat(getDateTimeFormat24());
			String timeStampString = "to_timestamp('"+sdf.format(currentTimestamp)+"','dd-MON-yyyy hh24:mi:ss')";
		String query ="SELECT AGENTSCHEDULEID FROM tblmagentschedule WHERE (lastexecutiondate + exeperiodinmin/1440 <= "+timeStampString+" or lastexecutiondate is null )"+
				" AND (executionstartdate  <= "+timeStampString+" ) " + " AND executiontypeid   ='"+AgentConstants.EXECUTION_TYPE_AUTOMATIC_EXECUTION+"' " +
				" AND (requirednumberofexecutions = -1 OR requirednumberofexecutions > 0)";
		if(isInfoLevel()){
			Logger.logInfo(MODULE, "query for agent schedule : " + query);
		}
		List<BigDecimal> agentScheduleIds = getEntityManager().createNativeQuery(query).getResultList();
		List<SystemAgentSchedule> schs = new ArrayList<SystemAgentSchedule>(); 
		if(agentScheduleIds!=null && !agentScheduleIds.isEmpty()){
			for(BigDecimal scheduleId : agentScheduleIds){
				SystemAgentSchedule agentSchedule = findSystemAgentScheduleById(scheduleId.longValue());
				schs.add(agentSchedule);
			}
		}
		return schs;
		}catch(NoResultException ex ){
			return null;
		}catch(Exception ex){
			if(isErrorLevel()){
				Logger.logError(MODULE, "Unable to find Agent Schedule For queue. Possible reason: " + ex.getMessage());
				Logger.logTrace(MODULE, ex);
			}
			throw new SearchBLException("Unable to find Agent Schedule For  queue. Possible reason: " + ex.getMessage()); 
		}
	}*/

	/**
	 * Finds Agent Schedule List For Queue
	 * @author yash.kapasi
	 * @return {@link List}<{@link SystemAgentSchedule}> schedules.
	 * @throws SearchBLException
	 */
	@Override
	public List<SystemAgentSchedule> getAgentScheduleListForQueue()	throws SearchBLException {
		try{
			List<SystemAgentSchedule> schedules = getEntityManager().createNamedQuery("SystemAgentSchedule.findAllActiveSchedule").setParameter("status", CPECommonConstants.SCHEDULE_STATUS_ACTIVE).getResultList();
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning getAgentScheduleListForQueue" + schedules.size());
			}
			return schedules;
		}catch(NoResultException ex ){
			return null;
		}catch(Exception ex){
			if(isErrorLevel()){
				Logger.logError(MODULE, "Unable to find Agent Schedule For queue. Possible reason: " + ex.getMessage());
				Logger.logTrace(MODULE, ex);
			}
			throw new SearchBLException("Unable to find Agent Schedule For  queue. Possible reason: " + ex.getMessage()); 
		}
	}
	
	
	/**
	 * create System Agent Run Queue in system
	 * @author yash.kapasi
	 * @param SystemAgentRunQueue agentRunQueue.
	 * @throws CreateBLException
	 */
	@Override
	public void createSystemAgentRunQueue(SystemAgentRunQueue agentRunQueue)
			throws CreateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createSystemAgentRunQueue");
		}
		try {			
			getEntityManager().persist(agentRunQueue);
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning createSystemAgentRunQueue");
			}
			
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in createSystemAgentRunQueue Reason" +e.getMessage());
			getSessionContext().setRollbackOnly();
			throw new CreateBLException("Create SystemAgentRunQueue Operation Failed, Reason : " + e.getMessage(), e);
		}
		
	}

	/**
	 * change System Agent Run Queue Status in system
	 * @author yash.kapasi
	 * @return Long agentRunQueueId.
	 * @return String executionStatusId.
	 */
	@Override
	public void changeSystemAgentRunQueueStatus(Long agentRunQueueId,
			String executionStatusId) {
		try{
			SystemAgentRunQueue runQueue = getEntityManager().find(SystemAgentRunQueue.class, agentRunQueueId);
			if(runQueue!=null){
				runQueue.setExecutionstatusid(executionStatusId);
				getEntityManager().merge(runQueue);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	/**
	 * find System Agent Schedule By Id
	 * @author yash.kapasi
	 * @param Long scheduleId
	 * @param Timestamp start
	 * @param Timestamp stop
	 * @return SystemAgentSchedule agentRunQueueId.
	 * @throws SearchBLException
	 */
	@Override
	public SystemAgentSchedule findSystemAgentScheduleById(Long scheduleId,Timestamp start, Timestamp stop) throws SearchBLException {
		
		try { 
			
			return  (SystemAgentSchedule) getEntityManager().createNamedQuery("SystemAgentSchedule.searchSystemScheduleByDate")
			.setParameter("start", start)
			.setParameter("stop", stop)
			.setParameter("agentscheduleid", scheduleId)
			.getSingleResult();
		}catch(Exception e) {
			Logger.logTrace(MODULE, e);
			Logger.logError(MODULE, "Error in user search, " + e.getMessage());
			throw new SearchBLException("Could not locate SystemAudit data for " + e.getMessage(), e);
		}
	}


	/**
	 * find System Agent Schedule By Agent Schedule and Start/Stop Execution Time
	 * @author yash.kapasi
	 * @param Long scheduleId
	 * @param Timestamp start
	 * @param Timestamp stop
	 * @return {@link List}<{@link SystemAgentRunDetail}> runDetails.
	 * @throws SearchBLException
	 */
	@Override
	public List<SystemAgentRunDetail> findAgentRunDetailBySystemAgentSchedule(Long systemAgentScheduleId, Timestamp start, Timestamp stop)
			throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentRunDetailBySystemAgentSchedule systemagent :" +systemAgentScheduleId);
		try {
			 List<SystemAgentRunDetail>  runDetail =  getEntityManager().createNamedQuery("SystemAgentRunDetail.findAgentRunDetailBySystemAgentScheduleByDate")
					.setParameter("systemAgentSchedule", systemAgentScheduleId)
					.setParameter("executionstartdate", start)
					.setParameter("executionstopdate", stop)
					.getResultList();
		 return runDetail;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentRunDetailBySystemAgentSchedule Reason" +e.getMessage());
			throw new SearchBLException("Find findAgentRunDetailBySystemAgentSchedule operation failed, reason: " + e.getMessage(), e);
		}
	}

	/**
	 * find System Agent Run in Queue By agentName,ScheduleName and execution status
	 * @author yash.kapasi
	 * @param String agentName
	 * @param String scheduleName
	 * @param String status
	 * @return {@link List}<{@link SystemAgentRunQueue}> queues.
	 * @throws SearchBLException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemAgentRunQueue> findAgentRunInQueue(String agentName,String scheduleName, String status) throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentRunInQueue systemagent :");
		try {
			 
			List<SystemAgentRunQueue>  queues =  getEntityManager().createNamedQuery("SystemAgentRunQueue.searchAgentRun")
					.setParameter("agentName", formatForUpperLikeSearch(agentName))
					.setParameter("agentScheduleName", formatForUpperLikeSearch(scheduleName))
					.setParameter("executionstatus", formatForUpperLikeSearch(status))
					.getResultList();
		 return queues;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentRunInQueue Reason" +e.getMessage());
			throw new SearchBLException("Find findAgentRunInQueue operation failed, reason: " + e.getMessage(), e);
		}
	}

	
	
	@Override
	public List<SystemAgentRunQueue>   findAgentRunInQueue() throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentRunInQueue systemagent :");
		try {
			 
			List<SystemAgentRunQueue>  queue =  getEntityManager().createNamedQuery("SystemAgentRunQueue.findAll").getResultList();
		 return queue;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentRunInQueue Reason" +e.getMessage());
			throw new SearchBLException("Find findAgentRunInQueue operation failed, reason: " + e.getMessage(), e);
		}
	}
	@Override
	public SystemAgentRunQueue findAgentRunInQueue(Long viewEntityId) throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentRunInQueue systemagent :");
		try {
			 
			SystemAgentRunQueue  queue =  (SystemAgentRunQueue) getEntityManager().createNamedQuery("SystemAgentRunQueue.findAgentRunInQueueById")
					.setParameter("agentrunqueueid", viewEntityId)
					.getSingleResult();
		 return queue;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentRunInQueue Reason" +e.getMessage());
			throw new SearchBLException("Find findAgentRunInQueue operation failed, reason: " + e.getMessage(), e);
		}
	}
	@Override
	public 	List<SystemAgentRunQueue> findAgentRunInQueueByAgentScheduleId(Long agentScheduleId) throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentRunInQueueByAgentScheduleId agentScheduleId :"+agentScheduleId);
		try {
			 
			List<SystemAgentRunQueue>  queue =  getEntityManager().createNamedQuery("SystemAgentRunQueue.findByAgentSchedule")
					.setParameter("agentscheduleid", agentScheduleId)
					.getResultList();
		 return queue;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentRunInQueue Reason" +e.getMessage());
			throw new SearchBLException("Find findAgentRunInQueue operation failed, reason: " + e.getMessage(), e);
		}
	}
	@Override
	public List<SystemAgent> findAgentByName(String agentname) throws SearchBLException {
		
		logTrace(MODULE, "In findAgentByName");
		List<SystemAgent> systemAgentList = null;
		try {

			systemAgentList = getEntityManager().createNamedQuery("SystemAgent.findSystemAgentByName")
					.setParameter("name",formatForUpperLikeSearch(agentname))
			.getResultList();
			
		}catch(NoResultException e) {
			systemAgentList = new ArrayList<SystemAgent>();
		}catch(Exception e) {
			throw new SearchBLException("Find findAgentByName operation failed, reason: " + e.getMessage(), e);
		}
		logTrace(MODULE,"Returning from findAgentByName");
		return systemAgentList;
	}


	@Override
	public List<SystemAgentRunQueue> findAgentRunInQueueByName(String agentname, String scheduleName) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentRunInQueue systemagent :");
		try {
			 
			List<SystemAgentRunQueue>  queues =  getEntityManager().createNamedQuery("SystemAgentRunQueue.findAgentRunInQueueByName")
					.setParameter("agentName", formatForUpperLikeSearch(agentname))
					.setParameter("agentScheduleName", formatForUpperLikeSearch(scheduleName))
					.getResultList();
		 return queues;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentRunInQueue Reason" +e.getMessage());
			throw new SearchBLException("Find findAgentRunInQueue operation failed, reason: " + e.getMessage(), e);
		}
	}


	@Override
	public List<SystemAgentRunQueue> findAgentRunInQueueByStatus(String status)
			throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentRunInQueue systemagent :");
		try {
			 
			List<SystemAgentRunQueue>  queues =  getEntityManager().createNamedQuery("SystemAgentRunQueue.findAgentRunInQueueByStatus")
					.setParameter("executionstatusid", status)
					.getResultList();
		 return queues;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentRunInQueue Reason" +e.getMessage());
			throw new SearchBLException("Find findAgentRunInQueue operation failed, reason: " + e.getMessage(), e);
		}
	}
	
	@Override
	public void deleteSystemAgentRunQueue(Long agentRunQueueId) throws UpdateBLException{
		try{
			SystemAgentRunQueue runQueue = getEntityManager().find(SystemAgentRunQueue.class, agentRunQueueId);
			getEntityManager().remove(runQueue);
		}catch(Exception e){
			if(isErrorLevel()){
				e.printStackTrace();
			}
			throw new UpdateBLException("Cannot remove agentrun from queue. Possible reason :  " + e.getMessage());
		}
	}
	
	
	/**
	 * Find value source for given sqlquery. Sql query must be in standard and must return minimum two value i.e string id and string name. 
	 * @author yash.kapasi
	 * @param String sqlQuery
	 * 
	 * */
	@Override
	public List<Object[]> findValueSource(String sqlQuery) throws SearchBLException{
		Logger.logTrace(MODULE, "inside findValueSource()=" +sqlQuery);
		try {
			@SuppressWarnings("unchecked")
			List<Object[]> objectList =  getEntityManager().createNativeQuery(sqlQuery).getResultList();
			return objectList;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			Logger.logTrace(MODULE, e);
			throw new SearchBLException("Search operation failed. reason : " + e.getMessage());
		}
	}


	/*@Override
	public List<PolicyStatus> findAgentScheduleStatus()
			throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAgentScheduleStatus");
		try {
			List<PolicyStatus>  policyStatus = getEntityManager().createNamedQuery("PolicyStatus.findAllMode").getResultList();
		 return policyStatus;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentScheduleStatus Reason" +e.getMessage());
			throw new SearchBLException("Find AgentScheduleStatus operation failed, reason: " + e.getMessage(), e);
		}
	}*/


	@Override
	public void updateSystemAgentRunQueue(SystemAgentRunQueue queue) throws UpdateBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  updateSystemAgentRunQueue , queue : " + queue.getAgentrunqueueid());
		try{
			getEntityManager().merge(queue);
		}catch(Exception ex){
			if(isErrorLevel()){
				Logger.logError(MODULE, "Error Occured while reschedualing agent in queue. Possible Reason" + ex.getMessage());
				ex.printStackTrace();
			}
			throw new UpdateBLException(ex.getMessage());
		}

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SystemAgentSchedule> findSystemAgentScheduleByUniqueName(
			String scheduleName) throws SearchBLException {
		logTrace(MODULE, "In findSystemAgentScheduleByUniqueName");
		List<SystemAgentSchedule> systemAgentSchduleList = null;
		try {

			systemAgentSchduleList = getEntityManager().createNamedQuery("SystemAgentSchedule.findSystemAgentScheduleByUniqueName")
					.setParameter("name",scheduleName.toUpperCase())
			.getResultList();
			
		}catch(NoResultException e) {
			systemAgentSchduleList = new ArrayList<SystemAgentSchedule>();
		}catch(Exception e) {
			throw new SearchBLException("Find findSystemAgentScheduleByUniqueName operation failed, reason: " + e.getMessage(), e);
		}
		logTrace(MODULE,"Returning from findSystemAgentScheduleByUniqueName");
		return systemAgentSchduleList;
	}


	@Override
	public List<ExecutionStatus> findAllScheduleStatus()
			throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAllScheduleStatus");
		try {
			List<ExecutionStatus>  policyStatus = getEntityManager().createNamedQuery("ExecutionStatus.findAllStatus").getResultList();
		 return policyStatus;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAllScheduleStatus Reason" +e.getMessage());
			throw new SearchBLException("Find AgentScheduleStatus operation failed, reason: " + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AgentParam findAgentParamById(String agentid) throws SearchBLException
	{
		Logger.logDebug(MODULE, "inside  findAgentParamById");
		Logger.logDebug(MODULE, "AgentId::" + agentid);
		
		try {
			if(agentid !=null)
			{
				AgentParam agentParam =(AgentParam)getEntityManager().createNamedQuery("AgentParam.findAgentParamById")
				 .setParameter("agentid",agentid).getSingleResult();
				return agentParam;
			}else
			{
				Logger.logDebug(MODULE, "agentid is null.....");
				return null;
			}
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAgentParamById Reason" +e.getMessage());
			throw new SearchBLException(" findAgentParamById operation failed, reason: " + e.getMessage(), e);
		}
		
	}
	
	@Override
	public AgentParam updateAgentParam(AgentParam agentParam) throws UpdateBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  updateAgentParam");
		try {			
			return getEntityManager().merge(agentParam);
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in updateAgentParam Reason" +e.getMessage());
			getSessionContext().setRollbackOnly();
			throw new UpdateBLException("Update updateAgentParam Operation Failed, Reason : " + e.getMessage(), e);
		}
		
	}
	
}
