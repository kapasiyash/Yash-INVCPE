package com.elitecore.cpe.bl.session.system.agent;



import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Local;

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




@Local
public interface SystemAgentSessionBeanLocal {
 
	
	public List<SystemAgent> findAllActiveSystemAgents() throws SearchBLException;
	
	//System Agent Schedule
	public void createSystemAgentSchedule(SystemAgentSchedule systemAgentSchedule) throws CreateBLException;
	public void updateSystemAgentSchedule(SystemAgentSchedule systemAgentSchedule) throws UpdateBLException;
	public List<SystemAgentSchedule> findAllSystemAgentSchedule() throws SearchBLException;
	public SystemAgentSchedule findSystemAgentScheduleById(Long scheduleId) throws SearchBLException;
	public SystemAgentSchedule findAgentScheduleByExecutionType(String executionTypeId) throws SearchBLException;
	public SystemAgentSchedule findAgentScheduleBySystemAgent(String systemAgentId) throws SearchBLException;
	public List<SystemAgentSchedule> findSystemAgentSchedule(String name,String statusid) throws SearchBLException;
	
	
	//System Agent
	public void createSystemAgent(SystemAgent systemAgent) throws CreateBLException;
	public void updateSystemAgent(SystemAgent systemAgent) throws UpdateBLException;
	public List<SystemAgent> findAllSystemAgent() throws SearchBLException;
	public SystemAgent findSystemAgentById(String agentId) throws SearchBLException;
	public SystemAgent findSystemAgentByExecutionType(String executiontypeid) throws SearchBLException;
	
	
	//System Agent Run Details
	public SystemAgentRunDetail createSystemAgentRunDetail(SystemAgentRunDetail agentRunDetail) throws CreateBLException;
	public void updateSystemAgentRunDetail(SystemAgentRunDetail agentRunDetail) throws UpdateBLException;
	public List<SystemAgentRunDetail> findAllSystemAgentRunDetail() throws SearchBLException;
	public SystemAgentRunDetail findSystemAgentRunDetailById(Long agentRunDetailId) throws SearchBLException;
	public SystemAgentRunDetail findAgentRunDetailBySystemAgent(String systemAgent) throws SearchBLException;
	public  List<SystemAgentRunDetail> findAgentRunDetailBySystemAgentSchedule(Long systemAgent) throws SearchBLException;
	public  List<SystemAgentRunDetail> findAgentRunDetailBySystemAgentSchedule(Long systemAgent,Timestamp start,Timestamp stop) throws SearchBLException;

	public List<SystemAgentParameter> findSystemParameters(String agentId) throws SearchBLException;

	public ExecutionType findExecutionTypeById(String string) throws SearchBLException;

	

	public List<SystemAgentRunQueue> findSystemAgentRunDetailByNativeQuery(String namedQuery, Timestamp currentTimestamp) throws SearchBLException;

	public List<SystemAgentSchedule> getAgentScheduleListForQueue() throws SearchBLException;

	public void createSystemAgentRunQueue(SystemAgentRunQueue agentRunQueue) throws CreateBLException;

	public void changeSystemAgentRunQueueStatus(Long agentRunQueueId,	String executionStatusId);

	public void deleteSystemAgentRunQueue(Long agentRunQueueId)	throws UpdateBLException;

	public SystemAgentSchedule findSystemAgentScheduleById(Long scheduleId,Timestamp start, Timestamp stop) throws SearchBLException;

	public List<SystemAgentRunQueue> findAgentRunInQueue(String agentname,String scheduleName, String status) throws SearchBLException;

	public SystemAgentRunQueue findAgentRunInQueue(Long viewEntityId) throws SearchBLException;

	public List<SystemAgent> findAgentByName(String agentname) throws SearchBLException;

	public List<SystemAgentRunQueue> findAgentRunInQueueByName(String agentname, String scheduleName) throws SearchBLException;

	public List<SystemAgentRunQueue> findAgentRunInQueueByStatus(String status) throws SearchBLException;

	public List<Object[]> findValueSource(String valuesource) throws SearchBLException;

//	public List<PolicyStatus> findAgentScheduleStatus() throws SearchBLException;

	public void updateSystemAgentRunQueue(SystemAgentRunQueue queue) throws UpdateBLException;

	public List<SystemAgentRunQueue> findAgentRunInQueueByAgentScheduleId(Long agentScheduleId) throws SearchBLException;

	public List<SystemAgentSchedule> findSystemAgentScheduleByUniqueName(String scheduleName) throws SearchBLException;

	public List<SystemAgentRunQueue> findAgentRunInQueue() throws SearchBLException;

	public List<ExecutionStatus> findAllScheduleStatus() throws SearchBLException;

	public AgentParam updateAgentParam(AgentParam agentParam) throws UpdateBLException;

	public AgentParam findAgentParamById(String agentid) throws SearchBLException;
}
