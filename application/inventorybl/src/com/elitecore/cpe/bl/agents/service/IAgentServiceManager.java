package com.elitecore.cpe.bl.agents.service;

import java.util.Collection;
import java.util.Hashtable;

import com.elitecore.cpe.bl.exception.agent.AgentServiceException;
import com.elitecore.utility.agentframework.data.AgentServiceSummaryData;

public interface IAgentServiceManager {

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
    
//  Added by Amit jain for remove the Schedules from the running state using the AgentRunId
    
    public boolean stopScheduleByAgentRunId(String strAgentRunId) throws AgentServiceException;
}
