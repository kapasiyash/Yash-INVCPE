package com.elitecore.cpe.bl.delegates.system.agent;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.system.agent.AgentScheduleProcedureWrapperData;
import com.elitecore.cpe.bl.delegates.BaseBusinessDelegate;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.exception.agent.AgentServiceException;
import com.elitecore.cpe.bl.facade.system.agent.ISystemAgentFacade;
import com.elitecore.cpe.bl.facade.system.agent.SystemAgentFacadeLocal;
import com.elitecore.cpe.bl.facade.system.agent.SystemAgentFacadeRemote;
import com.elitecore.cpe.bl.vo.system.agent.AgentParameterVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentServiceVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentVO;
import com.elitecore.cpe.bl.vo.system.agent.PreUpdateAgentParamVO;
import com.elitecore.cpe.bl.vo.system.agent.SearchAgentRunInQueueVO;
import com.elitecore.cpe.bl.vo.system.agent.SearchAgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.agent.UpdateAgentParameterVO;
import com.elitecore.cpe.bl.vo.system.agent.UpdateAgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.agent.ViewAgentRunHistoryVO;
import com.elitecore.cpe.bl.vo.system.agent.ViewAgentScheduleVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.utility.agentframework.data.AgentServiceSummaryData;

public class SystemAgentBD extends BaseBusinessDelegate {

	public SystemAgentBD(IBDSessionContext context) {
		super(context);
		
	}

	private static ISystemAgentFacade facade;
	
	private ISystemAgentFacade getFacade() throws NamingException {
 		if (facade == null) {
 			if (isLocalMode()) {
 				facade = (ISystemAgentFacade)lookupLocal(SystemAgentFacadeLocal.class);
 			}else {
 				facade = (ISystemAgentFacade)lookup(SystemAgentFacadeRemote.class);
 			}
 		}
 		return facade;
 	}

	public List<AgentParameterVO> getSystemAgentParameters(String agentId) throws SearchBLException,TechnicalException {
		
		try {
			return getFacade().getSystemParameters(agentId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public List<AgentVO> getAllAgentsList() throws SearchBLException,TechnicalException{
		
		try {
			return getFacade().getAllAgentsList();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public void createAgentSchedule(AgentScheduleVO scheduleVO) throws CreateBLException,TechnicalException {
		
		try {
			getFacade().createAgentSchedule(scheduleVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
		
	}

	public List<SearchAgentScheduleVO> findSystemAgentSchedule(String name, String statusid) throws SearchBLException,TechnicalException {
		
		try {
			return getFacade().findSystemAgentSchedule(name,statusid);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public ViewAgentScheduleVO findSystemAgentScheduleViewData(Long scheduleId) throws SearchBLException, TechnicalException {
		
		try {
			return getFacade().findSystemAgentScheduleViewData(scheduleId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public List<ViewAgentRunHistoryVO> findAgentRunHistory(Long scheduleId, Timestamp start, Timestamp stop) throws SearchBLException, TechnicalException {
		
		try {
			return getFacade().findAgentRunHistory(scheduleId,start,stop);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public List<SearchAgentRunInQueueVO> findAgentRunInQueue(String agentname,String scheduleName,String status) throws SearchBLException, TechnicalException {
		
		try {
			return getFacade().findAgentRunInQueue(agentname,scheduleName,status);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public SearchAgentRunInQueueVO findSystemAgentRunQueue(Long viewEntityId) throws SearchBLException, TechnicalException {
		
		try {
			return getFacade().findSystemAgentRunQueue(viewEntityId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public List<AgentVO> getAllAgentsListByName(String agentName) throws SearchBLException, TechnicalException {
		try {
			return getFacade().getAllAgentsListByName(agentName);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public AgentVO findSystemAgentById(String agentId) throws SearchBLException, TechnicalException {
		try {
			return getFacade().findSystemAgentById(agentId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
	
	public PreUpdateAgentParamVO preUpdateAgentParam(String agentId) throws SearchBLException, TechnicalException {
		try {
			return getFacade().preUpdateAgentParamById(agentId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
	
	public void updateAgentParam(UpdateAgentParameterVO updateAgentParamVO) throws UpdateBLException, TechnicalException {
		try {
			getFacade().updateAgentParameter(updateAgentParamVO, getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	/*public UpdateAgentScheduleVO findAgentScheduleById(Long agentScheduleId) throws SearchBLException, TechnicalException {
		try {
			return getFacade().findAgentScheduleById(agentScheduleId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public void updateAgentSchedule(AgentScheduleVO scheduleVO) throws UpdateBLException, TechnicalException {
		try {
			getFacade().updateAgentSchedule(scheduleVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public List<ComboData> findAgentScheduleStatus() throws SearchBLException, TechnicalException {
		try {
			return getFacade().findAgentScheduleStatus();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

	public void deleteAgentSchedule(Long scheduleId, Long statusId) throws UpdateBLException, TechnicalException {
		
		try {
			 getFacade().deleteAgentSchedule(scheduleId,statusId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}*/

	public void updateAgentSchedule(UpdateAgentScheduleVO scheduleVO) throws UpdateBLException, TechnicalException {
		try {
			 getFacade().updateAgentSchedule(scheduleVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}
	
	public void cancelAgentSchedule(Long scheduleId, String  reason) throws UpdateBLException, TechnicalException {
		try {
			 getFacade().cancelAgentSchedule(scheduleId,reason);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}
	
	public void changeAgentServiceStatus(AgentServiceVO agentServiceVO) throws TechnicalException,UpdateBLException{
		try {
			 getFacade().changeAgentServiceStatus(agentServiceVO);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public boolean startService() throws AgentServiceException, TechnicalException{
		try {
			return getFacade().startService();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
	
    public boolean stopService() throws AgentServiceException, TechnicalException{
		try {
			return getFacade().stopService();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
    
    public boolean stopAgentRunByName(String agentRunId)  throws AgentServiceException, TechnicalException{
		try {
			return getFacade().stopAgentRunByName(agentRunId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
    public boolean stopAgentRunByScheduleId(String agentScheduleId) throws AgentServiceException, TechnicalException{
		try {
			return getFacade().stopAgentRunByScheduleId(agentScheduleId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
    public Hashtable getAgentServiceThreadPools()  throws AgentServiceException, TechnicalException{
		try {
			return getFacade().getAgentServiceThreadPools();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
    public String getMonitorShortDescription()  throws AgentServiceException, TechnicalException{
		try {
			return getFacade().getMonitorShortDescription();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
    public Hashtable getAgentManagerDetails()  throws AgentServiceException, TechnicalException{
		try {
			return getFacade().getAgentManagerDetails();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
    public Collection getAgentRunStates()  throws AgentServiceException, TechnicalException{
		try {
			return getFacade().getAgentRunStates();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
    public boolean doThreadMonitoring()  throws AgentServiceException, TechnicalException{
		try {
			return getFacade().doThreadMonitoring();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
    public String getServiceState()  throws AgentServiceException, TechnicalException{
		try {
			return getFacade().getServiceState();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
    public AgentServiceSummaryData getAgentServiceSummary() throws AgentServiceException, TechnicalException{
		try {
			return getFacade().getAgentServiceSummary();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
    public boolean stopScheduleByAgentRunId(String strAgentRunId)  throws AgentServiceException, TechnicalException{
		try {
			return getFacade().stopScheduleByAgentRunId(strAgentRunId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public List<ComboBoxData> findAllScheduleStatus() throws SearchBLException, TechnicalException {
		try {
			return getFacade().findAllScheduleStatus();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}

	public AgentScheduleProcedureWrapperData findAgentScheduleForProcedure(
			String entityId) throws SearchBLException, TechnicalException {
		
		try {
			return getFacade().findAgentScheduleForProcedure(entityId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
}
