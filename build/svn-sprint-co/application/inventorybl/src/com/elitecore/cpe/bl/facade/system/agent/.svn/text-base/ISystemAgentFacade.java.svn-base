package com.elitecore.cpe.bl.facade.system.agent;




import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import com.elitecore.cpe.bl.agents.base.BaseSchedule;
import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.system.agent.AgentScheduleProcedureWrapperData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.exception.agent.AgentServiceException;
import com.elitecore.cpe.bl.vo.system.agent.AgentParameterVO;
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
import com.elitecore.utility.agentframework.AgentRunDetails;
import com.elitecore.utility.agentframework.data.AgentServiceSummaryData;


public interface ISystemAgentFacade {
    
	public List<SystemAgentVO> findAllActiveSystemAgents() throws SearchBLException;
	public void updateAgentRunCompleted(AgentRunDetails agentRunDetails, Long agentRunQueueId) throws UpdateBLException;
	public void updateAgentRunDetails(AgentRunDetails agentRunDetails) throws UpdateBLException;

	public List<AgentParameterVO> getSystemParameters(String agentId) throws SearchBLException;

	public List<AgentVO> getAllAgentsList() throws SearchBLException;

	public void createAgentSchedule(AgentScheduleVO scheduleVO,IBLSession iblSession) throws CreateBLException;

	public List<SearchAgentScheduleVO> findSystemAgentSchedule(String name,String statusid) throws SearchBLException;

	public ViewAgentScheduleVO findSystemAgentScheduleViewData(Long scheduleId) throws SearchBLException;

	
	public List<ViewAgentRunHistoryVO> findAgentRunHistory(Long scheduleId, Timestamp start, Timestamp stop) throws SearchBLException;

	

	public List<SearchAgentRunInQueueVO> findAgentRunInQueue(String agentname,String scheduleName, String status) throws SearchBLException;

	

	public SearchAgentRunInQueueVO findSystemAgentRunQueue(Long viewEntityId) throws SearchBLException;
	public List<AgentVO> getAllAgentsListByName(String agentName) throws SearchBLException;
	public AgentVO findSystemAgentById(String agentId) throws SearchBLException;
	public Long createSystemAgentRunDetail() throws CreateBLException;
	
	
	public SystemAgentParamVO findSystemAgentData(Long scheduleId);

	
	/**
	 * Scheduling Methods
	 * */
	
	
	public void updateAgentSchedule() throws UpdateBLException;
	public BaseSchedule getNextAgentsScheduleExcluding(ArrayList excludeList);
	public BaseSchedule getNextAgentsScheduleExcluding();
	public void changeAgentRunStatusInQueue(Long agentRunQueueId,String executionStatusId);
	public void changeAgentServiceStatus(AgentServiceVO agentServiceVO) throws UpdateBLException;
	public void updateAgentSchedule(UpdateAgentScheduleVO scheduleVO, IBLSession session) throws UpdateBLException;
	
	public void cancelAgentSchedule(Long agentScheduleId,String reason) throws UpdateBLException;
	public void updateAgentScheduleExecutionStatus(Long agentScheduleId,String executionStatusId) throws UpdateBLException;
	public void rescheduleInprocessAgents();

	
	// AgentService Methods.
		public boolean startService() throws AgentServiceException;
	    public boolean stopService() throws AgentServiceException;
	    public boolean stopAgentRunByName(String agentRunId) throws AgentServiceException;
	    public boolean stopAgentRunByScheduleId(String agentScheduleId) throws AgentServiceException;
	    public Hashtable getAgentServiceThreadPools() throws AgentServiceException;
	    public String getMonitorShortDescription() throws AgentServiceException;
	    public Hashtable getAgentManagerDetails() throws AgentServiceException;
	    public Collection getAgentRunStates() throws AgentServiceException ;
	    public boolean doThreadMonitoring() throws AgentServiceException;
	    public String getServiceState() throws AgentServiceException;
	    public AgentServiceSummaryData getAgentServiceSummary()throws AgentServiceException;
	    public boolean stopScheduleByAgentRunId(String strAgentRunId) throws AgentServiceException;
		void cleanAgentActivities();
		public List<ComboBoxData> findAllScheduleStatus() throws SearchBLException;
		
		
		
		public void updateAgentParameter(UpdateAgentParameterVO updateAgentParamVO,IBLSession session) throws UpdateBLException;
		
		public PreUpdateAgentParamVO preUpdateAgentParamById(String agentId) throws SearchBLException;
		public AgentScheduleProcedureWrapperData findAgentScheduleForProcedure(String entityId) throws SearchBLException;
	
}
