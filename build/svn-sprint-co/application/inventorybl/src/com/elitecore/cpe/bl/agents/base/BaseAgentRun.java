package com.elitecore.cpe.bl.agents.base;

import java.util.List;

import com.elitecore.utility.agentframework.entities.IAgentRunEntity;
import com.elitecore.utility.agentframework.entities.IAgentRunMasterEntity;

/**
 * This class should be extend in order to run agent. Note that parameters like concurrencyLimit, MaxChunkSize etc. should not be override unless if it requires. 
 * The default configuration is the optimal configuration set for the system. Override such parameters only after having understanding of agentframework.
 *
 */

public abstract class BaseAgentRun extends AgentBase implements java.io.Serializable{
		
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * Starts agent run.
		 */
		public abstract void agentRunStarted();
		
		
		/**
		 * This method will be called before agent run master entity starts it's process.
		 * When master entity will be sent for processing this method will be called. 
		 * @param schedule
		 * @param runMasterEntity
		 * @param agentRunEnvParamList
		 * @param context
		 * @throws Exception
		 */
		public abstract void preAgentRunMasterEntity(BaseSchedule schedule, BaseMasterEntity runMasterEntity, BaseEnityEnvParameter agentRunEnvParamList ) throws Exception;
		/**
		 * Get Master Entities list. 
		 * Note that List should not be null. if null list is sent then chances of getting NullPointerException which will affect overall flow of agentframework.
		 * @param schedule
		 * @param agentRunEnvParamList
		 * @param context
		 * @return List
		 */
		public abstract List<IAgentRunMasterEntity> getAgentRunMasterEntities(BaseSchedule schedule,BaseEnityEnvParameter agentRunEnvParamList);
		/**
		 * Get master entity environment parameter.
		 * This method retrives parameters relavent for Master entity for execution. 
		 * Method should not return null;
		 * 
		 * @param schedule
		 * @param runMasterEntity
		 * @param agentRunEnvParamList
		 * @param context
		 * @return BaseMasterEnityEnvParameter
		 */
		public abstract BaseMasterEnityEnvParameter getAgentRunMasterEntityData(BaseSchedule schedule, BaseMasterEntity runMasterEntity, BaseEnityEnvParameter agentRunEnvParamList );
		/**
		 * This method will be called when master entity run gets started.
		 * @param runMasterEntity
		 * @param context
		 */
		public abstract void agentMasterEntityRunStarted(BaseMasterEntity runMasterEntity);
	    
		/**
		 *This method will be called before agent run process starts.
		 * @param schedule
		 * @param context
		 * @throws Exception
		 */
		public abstract void preAgentRunProcess(BaseSchedule schedule) throws Exception;
/*		*//**
		 * Get agent run entity run parameter.
		 * @param schedule
		 * @return
		 *//*
		public abstract BaseEnityEnvParameter getAgentRunData(BaseSchedule schedule);*/
		
	    /**
	     * <p> Gets agent run entities. This is child entities. </p>
	     * @param schedule
	     * @param runMasterEntity
	     * @param enityEnvParameter
	     * @param masterEnityEnvParameter
	     * @param context
	     * @return List
	     */
	    public abstract List<IAgentRunEntity> getAgentRunEntities(BaseSchedule schedule, BaseMasterEntity runMasterEntity, BaseEnityEnvParameter enityEnvParameter, BaseMasterEnityEnvParameter masterEnityEnvParameter);
	    /**
	     * 
	     * <p> Runs Agent task for BaseEnity. Returns true if operation is successful else return false. </p>
	     * @param schedule
	     * @param agentRunEntity
	     * @param enityEnvParameter
	     * @param masterEnityEnvParameter
	     * @param context
	     * @return boolean 
	     */
	    public abstract boolean runAgentTask(BaseSchedule schedule, BaseEntity agentRunEntity, BaseEnityEnvParameter enityEnvParameter, BaseMasterEnityEnvParameter masterEnityEnvParameter);
	    /**
	     * Post Agent run master entity. Once master entity and it's child entites proccessed successfully this method will be called.
	     * @param schedule
	     * @param agentProcessEntity
	     * @param gParam
	     * @param aParam
	     * @param context
	     * @throws Exception
	     */
	    public abstract void postAgentRunMasterEntity(BaseSchedule schedule, BaseMasterEntity agentProcessEntity, BaseEnityEnvParameter gParam, BaseMasterEnityEnvParameter aParam)throws Exception;
	    /**
	     * 
	     * This method will be called after agent run.
	     * @param schedule
	     * @param agentRunEnvParamList
	     * @param context
	     * @throws Exception
	     */
	    public abstract void postAgentRun(BaseSchedule schedule, BaseEnityEnvParameter agentRunEnvParamList) throws Exception;

	    /**
	     * This method will be called when agent run gets ended.
	     * @param context
	     */
	    public  abstract void agentRunEnded();
	    /**
	     * This method will be called when agent master entity run ends.
	     * @param runMasterEntity
	     * @param context
	     */
	    public  abstract void agentMasterEntityRunEnded(BaseMasterEntity runMasterEntity);

	    
	    
	    protected  String parseValue(String value){
	    	String retString = value;
	    	
	    /*	if(value!=null && !value.isEmpty()){
	    		if(value.contains("$")){
	    			String[] splitedValue = value.split("$");
	    			if(splitedValue.length>1){
	    				retString = splitedValue[1];
	    			}
	    		}
	    	}*/
	    	
	    	return retString;
	    }
	    
	    protected   String[] getCommaSeperated(String value){
	    	if(value.contains(",")){
	    		return value.split(",");
	    	}else{
	    		return new String[]{value};
	    	}
	    }
	    
	   /* public static void main(String[] args) {
			String testString = "VAL,ALL";
			System.out.println(parseValue(testString));
			String[] array = getCommaSeperated(parseValue(testString));
			if(array!=null && array.length>0){
				for(int i=0;i<array.length;i++){
					System.out.println(array[i]+"\t");
				}
			}
		}*/
}
