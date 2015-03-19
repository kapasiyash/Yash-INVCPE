package com.elitecore.cpe.bl.agents.manager;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.agents.base.AgentBase;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.system.agent.SystemAgentFacadeRemote;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.utility.agentframework.IAgentSchedule;
import com.elitecore.utility.agentframework.managers.IAgentManager;

public class AgentManager extends AgentBase implements  IAgentManager{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MODULE = "AGENT-MANAGER";
	
	public AgentManager() {
		super();
		System.out.println("Initializing Agent Manager");
	}
	
	
	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.managers.IAgentManager#getAgentServiceStatus()
	 */
	@Override
	public boolean getAgentServiceStatus() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.managers.IAgentManager#getExternalProperties()
	 */
	@Override
	public Hashtable getExternalProperties() {
		return getProperties();
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.managers.IAgentManager#getNextAgentsSchedule()
	 */
	@Override
	public IAgentSchedule getNextAgentsSchedule() {
		try {
			SystemAgentFacadeRemote systemAgentFacadeRemote = (SystemAgentFacadeRemote) lookup(SystemAgentFacadeRemote.class);
			return systemAgentFacadeRemote.getNextAgentsScheduleExcluding();
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.managers.IAgentManager#getNextAgentsScheduleExcluding(java.util.ArrayList)
	 */
	@Override
	public IAgentSchedule getNextAgentsScheduleExcluding(ArrayList excludeList) {
		
		try {
			SystemAgentFacadeRemote systemAgentFacadeRemote = (SystemAgentFacadeRemote) lookup(SystemAgentFacadeRemote.class);
			return systemAgentFacadeRemote.getNextAgentsScheduleExcluding(excludeList);
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.managers.IAgentManager#initAgentService()
	 */
	@Override
	public boolean initAgentService() {
		Logger.logTrace(MODULE, "initilizing Agent Service");
		//TODO : Writer logic for init Agent Service.
		// Re-scheduling agent schedules which were stopped without completing complete actions.
		try {
			SystemAgentFacadeRemote systemAgentFacadeRemote = (SystemAgentFacadeRemote) lookup(SystemAgentFacadeRemote.class);
			systemAgentFacadeRemote.rescheduleInprocessAgents();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public void setExternalProperties(Hashtable properties) {
		
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.managers.IAgentManager#updateAgentSchedule()
	 */
	@Override
	public void updateAgentSchedule() {
		Logger.logTrace(MODULE, "Updating Agent Schedule");
		try {
			SystemAgentFacadeRemote systemAgentFacadeRemote = (SystemAgentFacadeRemote) lookup(SystemAgentFacadeRemote.class);
			systemAgentFacadeRemote.updateAgentSchedule();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UpdateBLException e) {
			e.printStackTrace();
		}
	}

	
	
}
