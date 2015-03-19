package com.elitecore.cpe.bl.agents.worker;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.agents.AgentInitializationException;
import com.elitecore.cpe.bl.agents.base.AgentBase;
import com.elitecore.cpe.bl.agents.base.BaseAgent;
import com.elitecore.cpe.bl.agents.base.BaseAgentRun;
import com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter;
import com.elitecore.cpe.bl.agents.base.BaseEntity;
import com.elitecore.cpe.bl.agents.base.BaseMasterEnityEnvParameter;
import com.elitecore.cpe.bl.agents.base.BaseMasterEntity;
import com.elitecore.cpe.bl.agents.base.BaseSchedule;
import com.elitecore.cpe.bl.constants.system.AgentConstants;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.system.agent.SystemAgentFacadeRemote;
import com.elitecore.cpe.bl.vo.system.agent.SystemAgentParamVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.utility.agentframework.AgentRunDetails;
import com.elitecore.utility.agentframework.IAgentSchedule;
import com.elitecore.utility.agentframework.entities.IAgentRunEntity;
import com.elitecore.utility.agentframework.entities.IAgentRunMasterEntity;
import com.elitecore.utility.agentframework.utils.AgentErrorObject;
import com.elitecore.utility.agentframework.utils.AgentResultObject;
import com.elitecore.utility.agentframework.utils.IAgentMasterEntityEnvParameterList;
import com.elitecore.utility.agentframework.utils.IAgentRunEnvParameterList;
import com.elitecore.utility.agentframework.worker.IAgentWorker;

public class AgentWorker extends AgentBase implements IAgentWorker{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String MODULE= "AGENT-WORKER";
	private IAgentSchedule agentSchedule; 
	private SystemAgentParamVO paramVO;
	private BaseAgentRun agentRun;
	
	/**
	 * @param agentSchedule
	 * @throws AgentInitializationException
	 */
	public AgentWorker(IAgentSchedule agentSchedule) throws AgentInitializationException {
		this.agentSchedule=agentSchedule;
		BaseAgent agent = (BaseAgent) ((BaseSchedule)agentSchedule).getAgent();
		System.out.println(agent);
		
		try {
			System.out.println("Inside find AgentParamVO: "+agentSchedule.getAgentScheduleId());
			SystemAgentFacadeRemote systemAgentFacadeRemote = (SystemAgentFacadeRemote) lookup(SystemAgentFacadeRemote.class);
			paramVO = systemAgentFacadeRemote.findSystemAgentData(Long.valueOf(agentSchedule.getAgentScheduleId()));
			System.out.println("Outside find AgentParamVO :"+ paramVO);
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			Class<BaseAgentRun> forName = (Class<BaseAgentRun>) Class.forName(agent.getClassName());
	         System.out.println("Trying to create instance: " + forName);
             
             BaseAgentRun agentInstance = forName.newInstance();
             this.agentRun  = agentInstance;
             System.out.println("Created instance: " + agentInstance);
             
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new AgentInitializationException("Cannot Load Agent."+e.getMessage());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch(Exception ex){
			throw new AgentInitializationException("Cannot Initialize Agent."+ex.getMessage());
		}
		
		
		
	}
	
	protected BaseAgentRun getAgentRun(){
		return this.agentRun;
	}

	protected SystemAgentParamVO getParamVO() {
		return this.paramVO;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#agentMasterEntityRunEnded(com.elitecore.utility.agentframework.entities.IAgentRunMasterEntity)
	 */
	@Override
	public void agentMasterEntityRunEnded(IAgentRunMasterEntity runMasterEntity) {
		getAgentRun().agentMasterEntityRunEnded((BaseMasterEntity) runMasterEntity);
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#agentMasterEntityRunStarted(com.elitecore.utility.agentframework.entities.IAgentRunMasterEntity)
	 */
	@Override
	public void agentMasterEntityRunStarted(IAgentRunMasterEntity runMasterEntity) {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "Master Entity Run Started");
		getAgentRun().agentMasterEntityRunStarted((BaseMasterEntity) runMasterEntity);
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#agentRunEnded()
	 */
	@Override
	public void agentRunEnded() {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "Agent Run Ended");
		getAgentRun().agentRunEnded();
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#agentRunStarted()
	 */
	@Override
	public void agentRunStarted() {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "Agent Run Started");
		getAgentRun().agentRunStarted();
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#createAgentRunDetails()
	 */
	@Override
	public AgentResultObject createAgentRunDetails() {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "create Agent Run Details");
		Long runid = -1L;
		try{
			SystemAgentFacadeRemote systemAgentFacadeRemote = (SystemAgentFacadeRemote) lookup(SystemAgentFacadeRemote.class);
			runid = systemAgentFacadeRemote.createSystemAgentRunDetail();
		}catch(Exception ex){
			if(isErrorLevel()){
				ex.printStackTrace();
			}
		}
		AgentResultObject resultObject = new AgentResultObject(0L);
		resultObject.setResponseObject(runid+"");
		return resultObject;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getAgentRunData()
	 */
	@Override
	public IAgentRunEnvParameterList getAgentRunData() {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "get Agent Run Data");
		
		BaseSchedule schedule = (BaseSchedule) getAgentSchedule();
		try{
			SystemAgentFacadeRemote agentFacadeRemote = (SystemAgentFacadeRemote) lookup(SystemAgentFacadeRemote.class);
			agentFacadeRemote.changeAgentRunStatusInQueue(schedule.getAgentRunQueueId(),AgentConstants.EXECUTION_STATUS_IN_PROGRESS);
		}catch(Exception ex){
			
		}
		
		BaseEnityEnvParameter baseEnityEnvParameter = new BaseEnityEnvParameter(schedule.getAgent().getAgentId());
		if(schedule.getScheduleValues()!=null && !schedule.getScheduleValues().isEmpty()){
			for(Map.Entry<String,String> item : schedule.getScheduleValues().entrySet()){
				baseEnityEnvParameter.setParameter(item.getKey(), item.getValue());
			}
		
		}
//		baseEnityEnvParameter.setAgentRunId(schedule.getAgent().getAgentId()); // Commented Due to Using it for other purpose.
		
		return baseEnityEnvParameter;
		
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getAgentRunEntities(com.elitecore.utility.agentframework.entities.IAgentRunMasterEntity, com.elitecore.utility.agentframework.utils.IAgentRunEnvParameterList, com.elitecore.utility.agentframework.utils.IAgentMasterEntityEnvParameterList)
	 */
	@Override
	public Collection getAgentRunEntities(IAgentRunMasterEntity arg0,
			IAgentRunEnvParameterList arg1,
			IAgentMasterEntityEnvParameterList arg2) {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "get Agent Run Entities");
		return  getAgentRun().getAgentRunEntities((BaseSchedule)getAgentSchedule(),(BaseMasterEntity)	arg0, (BaseEnityEnvParameter) arg1,(BaseMasterEnityEnvParameter) arg2);
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getAgentRunEntityChunkSizeLimit()
	 */
	@Override
	public int getAgentRunEntityChunkSizeLimit() {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "get Agent Run Entity Chunk Size Limit");
		
		return Integer.valueOf(getParamVO().getAreChunkSizeELimit().toString());
//		?return getAgentRun().getAgentRunEntityChunkSizeLimit();
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getAgentRunEntityConcurrencyLimit()
	 */
	@Override
	public int getAgentRunEntityConcurrencyLimit() {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "get Agent Run Entity Concurreny Limit");
//		return getAgentRun().getAgentRunEntityConcurrencyLimit();
		return getParamVO().getAreConcurrencyLimit().intValue();
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getAgentRunMasterEntities(com.elitecore.utility.agentframework.utils.IAgentRunEnvParameterList)
	 */
	@Override
	public Collection getAgentRunMasterEntities(IAgentRunEnvParameterList arg0) {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "get Agent Run Master Entity sdfasdfas");
		List<IAgentRunMasterEntity> masterEntities = getAgentRun().getAgentRunMasterEntities((BaseSchedule) getAgentSchedule(), (BaseEnityEnvParameter)arg0);
		return masterEntities;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getAgentRunMasterEntityChunkSizeLimit()
	 */
	@Override
	public int getAgentRunMasterEntityChunkSizeLimit() {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "get Agent Run Master Entity Chunck Sikze Limit");
//		return getAgentRun().getAgentRunMasterEntityChunkSizeLimit();
		return getParamVO().getArmeChunkSizeELimit().intValue();
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getAgentRunMasterEntityConcurrencyLimit()
	 */
	@Override
	public int getAgentRunMasterEntityConcurrencyLimit() {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "get Agent Run Master Entity Concurrency Limit");
//		return getAgentRun().getAgentRunMasterEntityConcurrencyLimit();
		return getParamVO().getArmeConcurrencyLimit().intValue();
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getAgentRunMasterEntityData(com.elitecore.utility.agentframework.entities.IAgentRunMasterEntity, com.elitecore.utility.agentframework.utils.IAgentRunEnvParameterList)
	 */
	@Override
	public IAgentMasterEntityEnvParameterList getAgentRunMasterEntityData(IAgentRunMasterEntity runMasterEntity,IAgentRunEnvParameterList agentRunEnvParamList) {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "get Agent Run Master Entity Data");
		return  getAgentRun().getAgentRunMasterEntityData((BaseSchedule) getAgentSchedule(),(BaseMasterEntity)runMasterEntity,(BaseEnityEnvParameter) agentRunEnvParamList);
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getAgentSchedule()
	 */
	@Override
	public IAgentSchedule getAgentSchedule() {
		return this.agentSchedule;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getExternalProperties()
	 */
	@Override
	public Hashtable getExternalProperties() {
		return getProperties();
	}


	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getPreferredAgentRunEntityChunkSize()
	 */
	@Override
	public int getPreferredAgentRunEntityChunkSize() {
//		return getAgentRun().getPreferredAgentRunEntityChunkSize();
		return getParamVO().getPreareChunkSize().intValue();
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#getPreferredAgentRunMasterEntityChunkSize()
	 */
	@Override
	public int getPreferredAgentRunMasterEntityChunkSize() {
//		return getAgentRun().getPreferredAgentRunMasterEntityChunkSize();
		return getParamVO().getPrearmeChunkSize().intValue();
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#overruleAgentRunEntityChunkSize()
	 */
	@Override
	public boolean overruleAgentRunEntityChunkSize() {
//		return getAgentRun().overruleAgentRunEntityChunkSize();
		return getParamVO().isOverRuleAEChunkSize();
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#overruleAgentRunMasterEntityChunkSize()
	 */
	@Override
	public boolean overruleAgentRunMasterEntityChunkSize() {
//		return getAgentRun().overruleAgentRunMasterEntityChunkSize();
		return getParamVO().isOverRuleAMEChunkSize();
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#postAgentRunMasterEntity(com.elitecore.utility.agentframework.entities.IAgentRunMasterEntity, com.elitecore.utility.agentframework.utils.IAgentRunEnvParameterList, com.elitecore.utility.agentframework.utils.IAgentMasterEntityEnvParameterList)
	 */
	@Override
	public AgentResultObject postAgentRunMasterEntity(
			
			IAgentRunMasterEntity runMasterEntity,
			IAgentRunEnvParameterList gParam,
			IAgentMasterEntityEnvParameterList aParam) {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "inside postAgentRunMasterEntity ");
		try {
			getAgentRun().postAgentRunMasterEntity((BaseSchedule) getAgentSchedule(),(BaseMasterEntity)runMasterEntity,(BaseEnityEnvParameter) gParam, (BaseMasterEnityEnvParameter) aParam);
			return new AgentResultObject(0L);
		} catch (Exception e) {
			e.printStackTrace();
			AgentResultObject agentResult = new AgentResultObject(0L);
			agentResult.setErrorObject(new AgentErrorObject(-1L,e.getMessage()));
			return agentResult;
		}
	
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#postAgentRunProcess(com.elitecore.utility.agentframework.utils.IAgentRunEnvParameterList)
	 */
	@Override
	public AgentResultObject postAgentRunProcess(
			IAgentRunEnvParameterList agentRunEnvParamList) {
		BaseSchedule baseSchedule = (BaseSchedule) getAgentSchedule();
		try{
			SystemAgentFacadeRemote agentFacade = (SystemAgentFacadeRemote) lookup(SystemAgentFacadeRemote.class);
			agentFacade.updateAgentScheduleExecutionStatus(Long.valueOf(baseSchedule.getAgentScheduleId()), AgentConstants.EXECUTION_STATUS_COMPLETED_SUCCESSFULLY);
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			getAgentRun().postAgentRun(baseSchedule, (BaseEnityEnvParameter)agentRunEnvParamList);
			return new AgentResultObject(0L);
		} catch (Exception e) {
			e.printStackTrace();
			AgentResultObject agentResult = new AgentResultObject(0L);
			agentResult.setErrorObject(new AgentErrorObject(-1L,e.getMessage()));
			return agentResult;
		}
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#preAgentRunMasterEntity(com.elitecore.utility.agentframework.entities.IAgentRunMasterEntity, com.elitecore.utility.agentframework.utils.IAgentRunEnvParameterList)
	 */
	@Override
	public AgentResultObject preAgentRunMasterEntity(
			IAgentRunMasterEntity runMasterEntity,
			IAgentRunEnvParameterList agentRunEnvParamList) {
			try {
				getAgentRun().preAgentRunMasterEntity((BaseSchedule)getAgentSchedule(),(BaseMasterEntity) runMasterEntity, (BaseEnityEnvParameter) agentRunEnvParamList );
				return new AgentResultObject(0L);
			} catch (Exception e) {
				e.printStackTrace();
				AgentResultObject agentResult = new AgentResultObject(0L);
				agentResult.setErrorObject(new AgentErrorObject(-1L,e.getMessage()));
				return agentResult;
			}
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#preAgentRunProcess()
	 */
	@Override
	public AgentResultObject preAgentRunProcess() {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "Pre Agent Run Process");
		BaseSchedule baseSchedule = (BaseSchedule) getAgentSchedule();
		try{
			SystemAgentFacadeRemote agentFacade = (SystemAgentFacadeRemote) lookup(SystemAgentFacadeRemote.class);
			if(getAgentRun()!=null) {
				agentFacade.updateAgentScheduleExecutionStatus(Long.valueOf(baseSchedule.getAgentScheduleId()), AgentConstants.EXECUTION_STATUS_IN_PROGRESS);
			}
		}catch(Exception e){
			
			e.printStackTrace();
		}
		 try {
			 if(getAgentRun()!=null) {
				 getAgentRun().preAgentRunProcess(baseSchedule);
			 } else {
				 AgentResultObject agentResult = new AgentResultObject(0L);
				agentResult.setErrorObject(new AgentErrorObject(-1L,"Class not Loaded Properly"));
				return agentResult;
			 }
			 
			return new AgentResultObject(0L);
		 } catch (Exception e) {
				e.printStackTrace();
				AgentResultObject agentResult = new AgentResultObject(0L);
				agentResult.setErrorObject(new AgentErrorObject(-1L,e.getMessage()));
				return agentResult;
		}
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#runAgentTaskList(com.elitecore.utility.agentframework.entities.IAgentRunEntity[], com.elitecore.utility.agentframework.utils.IAgentRunEnvParameterList, com.elitecore.utility.agentframework.utils.IAgentMasterEntityEnvParameterList)
	 */
	@Override
	public AgentResultObject runAgentTaskList(IAgentRunEntity[] entities,
			IAgentRunEnvParameterList envParam,
			IAgentMasterEntityEnvParameterList masterEnvParam) {
		if(isDebugLevel())
			Logger.logTrace(MODULE, "Run Agent Task List");
		if(entities!=null && entities.length>=0){
			for(IAgentRunEntity entity : entities){
				boolean isSuccess = getAgentRun().runAgentTask((BaseSchedule)getAgentSchedule(),(BaseEntity)entity,(BaseEnityEnvParameter)envParam,(BaseMasterEnityEnvParameter)masterEnvParam);
				if(isSuccess){
					//TODO Some logic for failed task.
				}
			}
			
			return new AgentResultObject(0L);
		}else{
			AgentResultObject resultObject = new AgentResultObject(-1L);
			resultObject.setErrorObject(new AgentErrorObject(-1L, "Could not find entities to process"));
			return resultObject;
		}
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#setExternalProperties(java.util.Hashtable)
	 */
	@Override
	public void setExternalProperties(Hashtable properties) {
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AgentWorker [agentRun=" + agentRun + "]";
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#updateAgentRunCompleted(com.elitecore.utility.agentframework.AgentRunDetails)
	 */
	@Override
	public AgentResultObject updateAgentRunCompleted(
			AgentRunDetails agentRunDetails) {
		Logger.logTrace(MODULE, "updating Agent Run Completed" );
		try {
			SystemAgentFacadeRemote systemAgentFacadeRemote  = (SystemAgentFacadeRemote) lookup(SystemAgentFacadeRemote.class);
			systemAgentFacadeRemote.updateAgentRunCompleted(agentRunDetails,((BaseSchedule)getAgentSchedule()).getAgentRunQueueId());
			return new AgentResultObject(0L);
		} catch (UpdateBLException e) {
			e.printStackTrace();
			AgentResultObject agentResult = new AgentResultObject(-1L);
			agentResult.setErrorObject(new AgentErrorObject(-1L, e.getMessage()));
			return agentResult;
		} catch (NamingException e) {
			e.printStackTrace();
			AgentResultObject agentResult = new AgentResultObject(-1L);
			agentResult.setErrorObject(new AgentErrorObject(-1L, "Technical exception occured."));
			return agentResult;
		}
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.worker.IAgentWorker#updateAgentRunDetails(com.elitecore.utility.agentframework.AgentRunDetails)
	 */
	@Override
	public AgentResultObject updateAgentRunDetails(
			AgentRunDetails agentRunDetails) {
		Logger.logTrace(MODULE, "updateAgentRunDetails " );
		try {
			SystemAgentFacadeRemote systemAgentFacadeRemote  = (SystemAgentFacadeRemote) lookup(SystemAgentFacadeRemote.class);
			systemAgentFacadeRemote.updateAgentRunDetails(agentRunDetails);
			return new AgentResultObject(0L);
		} catch (UpdateBLException e) {
			e.printStackTrace();
			AgentResultObject agentResult = new AgentResultObject(-1L);
			agentResult.setErrorObject(new AgentErrorObject(-1L, e.getMessage()));
			return agentResult;
		} catch (NamingException e) {
			e.printStackTrace();
			AgentResultObject agentResult = new AgentResultObject(-1L);
			agentResult.setErrorObject(new AgentErrorObject(-1L, "Technical exception occured."));
			return agentResult;
		} catch(Exception e){
			e.printStackTrace();
			AgentResultObject agentResult = new AgentResultObject(-1L);
			agentResult.setErrorObject(new AgentErrorObject(-1L, e.getMessage()));
			return agentResult;
		}
	}
	
	  
}
