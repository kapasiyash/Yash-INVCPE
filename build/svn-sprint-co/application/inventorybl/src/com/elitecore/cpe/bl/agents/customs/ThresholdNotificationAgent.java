/**
 * 
 */
package com.elitecore.cpe.bl.agents.customs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.elitecore.cpe.bl.agents.base.BaseAgentRun;
import com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter;
import com.elitecore.cpe.bl.agents.base.BaseEntity;
import com.elitecore.cpe.bl.agents.base.BaseMasterEnityEnvParameter;
import com.elitecore.cpe.bl.agents.base.BaseMasterEntity;
import com.elitecore.cpe.bl.agents.base.BaseSchedule;
import com.elitecore.cpe.bl.constants.notification.NotificationConstants;
import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.facade.master.warehouse.WarehouseFacadeRemote;
import com.elitecore.cpe.bl.facade.master.warehouse.WarehouseUtil;
import com.elitecore.cpe.bl.facade.notification.NotificationFacadeRemote;
import com.elitecore.cpe.bl.facade.system.user.UserFacadeRemote;
import com.elitecore.cpe.bl.vo.inventorymgt.ThresholdNotificationEmailVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.ElapseCalculator;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.utility.agentframework.entities.IAgentRunEntity;
import com.elitecore.utility.agentframework.entities.IAgentRunMasterEntity;

/**
 * @author Joel.Macwan
 *
 */
public class ThresholdNotificationAgent extends BaseAgentRun implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String MODULE = "THRESHOLDNOTIFICATION-AGENT";
	
	private static final String EXECUTION = "_ThresholdExe";
	private static final String WAREHOUSEID = "_WareHouseId";
	
	private List<NotificationData> emailVOs = new ArrayList<NotificationData>();
	
	@Override
	public void agentRunStarted() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#preAgentRunMasterEntity(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseMasterEntity, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter)
	 */
	@Override
	public void preAgentRunMasterEntity(BaseSchedule schedule,
			BaseMasterEntity runMasterEntity,
			BaseEnityEnvParameter agentRunEnvParamList) throws Exception {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside preAgentRunMasterEntity ");
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#getAgentRunMasterEntities(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter)
	 */
	@Override
	public List<IAgentRunMasterEntity> getAgentRunMasterEntities(
			BaseSchedule schedule, BaseEnityEnvParameter agentRunEnvParamList) {
		List<IAgentRunMasterEntity> retList= new ArrayList<IAgentRunMasterEntity>();
		try{
			
			WarehouseFacadeRemote facadeRemote = (WarehouseFacadeRemote) lookup(WarehouseFacadeRemote.class);
			List<WarehouseVO> warehouseVOs = facadeRemote.getAllWareHouseData();
			if(warehouseVOs!=null && !warehouseVOs.isEmpty()) {
				for(WarehouseVO warehouseVO : warehouseVOs) {
					BaseMasterEntity masterEntity = new BaseMasterEntity();
					masterEntity.setLongEntityId(warehouseVO.getWarehouseId());
					masterEntity.setName(warehouseVO.getAlias());
					retList.add(masterEntity);
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retList;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#getAgentRunMasterEntityData(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseMasterEntity, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter)
	 */
	@Override
	public BaseMasterEnityEnvParameter getAgentRunMasterEntityData(
			BaseSchedule schedule, BaseMasterEntity runMasterEntity,
			BaseEnityEnvParameter agentRunEnvParamList) {
		
		Logger.logTrace(MODULE, "inside getAgentRunMasterEntityData");
		BaseMasterEnityEnvParameter masterEnityEnvParameter = new BaseMasterEnityEnvParameter();
		try{
			if(runMasterEntity!=null){
				WarehouseFacadeRemote facadeRemote = (WarehouseFacadeRemote) lookup(WarehouseFacadeRemote.class);
				List<ItemVO> itemVOs =	facadeRemote.getResourcesWithWareHouseId(runMasterEntity.getLongEntityId());
				masterEnityEnvParameter.setParameter(EXECUTION, itemVOs);
				masterEnityEnvParameter.setParameter(WAREHOUSEID, runMasterEntity.getLongEntityId());
			}
			return masterEnityEnvParameter;
		}catch(Exception ex){
			Logger.logTrace(MODULE, ex);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#agentMasterEntityRunStarted(com.elitecore.cpe.bl.agents.base.BaseMasterEntity)
	 */
	@Override
	public void agentMasterEntityRunStarted(BaseMasterEntity runMasterEntity) {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "agentMasterEntityRunStarted : " );
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#preAgentRunProcess(com.elitecore.cpe.bl.agents.base.BaseSchedule)
	 */
	@Override
	public void preAgentRunProcess(BaseSchedule schedule) throws Exception {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "preAgentRunProcess() : " );
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#getAgentRunEntities(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseMasterEntity, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter, com.elitecore.cpe.bl.agents.base.BaseMasterEnityEnvParameter)
	 */
	@Override
	public List<IAgentRunEntity> getAgentRunEntities(BaseSchedule schedule,
			BaseMasterEntity runMasterEntity,
			BaseEnityEnvParameter enityEnvParameter,
			BaseMasterEnityEnvParameter masterEnityEnvParameter) {
		// TODO Auto-generated method stub
		List<IAgentRunEntity> retList = new ArrayList<IAgentRunEntity>();
		if(masterEnityEnvParameter!=null){
			ElapseCalculator calculator = new ElapseCalculator();
			Logger.logTrace(MODULE, "Beginning process of transfering to child entities ");
			if(masterEnityEnvParameter.getParameter(EXECUTION)!=null) {
				List<ItemVO> executionData = (List<ItemVO>) masterEnityEnvParameter.getParameter(EXECUTION);
				Set<String> resourceIds = new HashSet<String>();
				for(ItemVO itemVO : executionData) {
					resourceIds.add(String.valueOf(itemVO.getItemId()));
				}
				for(ItemVO itemVO : executionData) {
					if(resourceIds.contains(String.valueOf(itemVO.getItemId()))) {
						BaseEntity baseEntity = new BaseEntity(String.valueOf(itemVO.getItemId()));
						baseEntity.setObject(itemVO);
						retList.add(baseEntity);
						resourceIds.remove(String.valueOf(itemVO.getItemId()));
					}
				}
				
			}
			
			
			Logger.logTrace(MODULE, "Transformation Process finished. " + calculator.getElapseMessage());
		}
		Logger.logTrace(MODULE, "returning getAgentRunEntities " + retList.size() );
		return retList;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#runAgentTask(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseEntity, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter, com.elitecore.cpe.bl.agents.base.BaseMasterEnityEnvParameter)
	 */
	@Override
	public boolean runAgentTask(BaseSchedule schedule,
			BaseEntity agentRunEntity, BaseEnityEnvParameter enityEnvParameter,
			BaseMasterEnityEnvParameter masterEnityEnvParameter) {
		boolean retValue = true;
		Logger.logTrace(MODULE, "inside runAgentTask ");
		try {
			WarehouseFacadeRemote facadeRemote = (WarehouseFacadeRemote) lookup(WarehouseFacadeRemote.class);
			if(agentRunEntity!=null && masterEnityEnvParameter!=null && masterEnityEnvParameter.getParameter(EXECUTION)!=null && masterEnityEnvParameter.getParameter(WAREHOUSEID)!=null){
				
				Long wareHouseId =  (Long) masterEnityEnvParameter.getParameter(WAREHOUSEID);
				Long resourceId = Long.parseLong(agentRunEntity.getEntityId());
				Long resourceTypeId = null,resourceSubTypeId = null;
				ItemVO itemVO = (ItemVO) agentRunEntity.getObject();
				if(itemVO.getResourceTypeVO()!=null) {
					resourceTypeId = itemVO.getResourceTypeVO().getResourceTypeId();
					
					if(itemVO.getResourceTypeVO().getResourceSubTypeVO()!=null) {
						resourceSubTypeId = itemVO.getResourceTypeVO().getResourceSubTypeVO().getResourceSubTypeId();
					}
					
				}
				Logger.logTrace(MODULE,"----------------------------------------------");
				
				Logger.logTrace(MODULE,"wareHouseId-->"+wareHouseId);
				Logger.logTrace(MODULE,"resourceId-->"+resourceId);
				
				
				 NotificationData result = facadeRemote.calculateThreasholdValue(wareHouseId,resourceId,resourceTypeId,resourceSubTypeId);
				
				if(result!=null) {
					Logger.logTrace(MODULE," SENT THE MAIL ");
					Logger.logTrace(MODULE, result+"");
					emailVOs.add(result);
					
				} else {
					Logger.logTrace(MODULE, result+"");
					Logger.logTrace(MODULE,"DONT SENT THE MAIL ");
				}
				
				Logger.logTrace(MODULE,"----------------------------------------------");
			}
			
			/*UserFacadeRemote userFacadeRemote = (UserFacadeRemote) lookup(UserFacadeRemote.class);
			IBDSessionContext sessionContext = userFacadeRemote.doLogin(
					"admin", "sysadmin", "127.0.0.1");
			WarehouseFacadeRemote warehouseFacadeRemote = (WarehouseFacadeRemote) lookup(WarehouseFacadeRemote.class);
			Logger.logTrace(MODULE,
					" inside runAgentTask calling searchThresholdStatus() ");
			warehouseFacadeRemote.searchThresholdStatus(sessionContext
					.getBLSession());
			Logger.logTrace(MODULE,
					"inside runAgentTask After calling searchThresholdStatus()");*/

		} catch (Exception e) {
			e.printStackTrace();
			retValue = false;
		}

		return retValue;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#postAgentRunMasterEntity(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseMasterEntity, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter, com.elitecore.cpe.bl.agents.base.BaseMasterEnityEnvParameter)
	 */
	@Override
	public void postAgentRunMasterEntity(BaseSchedule schedule,
			BaseMasterEntity agentProcessEntity, BaseEnityEnvParameter gParam,
			BaseMasterEnityEnvParameter aParam) throws Exception {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside postAgentRunMasterEntity ");
		
		
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#postAgentRun(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter)
	 */
	@Override
	public void postAgentRun(BaseSchedule schedule,
			BaseEnityEnvParameter agentRunEnvParamList) throws Exception {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside postAgentRun ");

		/*WarehouseFacadeRemote facadeRemote = (WarehouseFacadeRemote) lookup(WarehouseFacadeRemote.class);
		String commonEmail = null;
		List<WarehouseVO> data = facadeRemote.getAllWareHouseData();
		if(data!=null && !data.isEmpty()) {
			for(WarehouseVO warehouseVO : data) {
				if(warehouseVO.getAlias().equals("CENTRAL")) {
					commonEmail = warehouseVO.getEmailId();
				}
			}
		}*/
		
		NotificationFacadeRemote notifyFacadeRemote = (NotificationFacadeRemote) lookup(NotificationFacadeRemote.class);
		
		if(emailVOs!=null && !emailVOs.isEmpty()) {
			for(NotificationData emailVO : emailVOs) {
				notifyFacadeRemote.sendNotificationService(emailVO);
			}
//			WarehouseUtil.processThresholdNotificationEmailCommon(emailVOs,commonEmail);
		}
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#agentRunEnded()
	 */
	@Override
	public void agentRunEnded() {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside postAgentRun ");
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#agentMasterEntityRunEnded(com.elitecore.cpe.bl.agents.base.BaseMasterEntity)
	 */
	@Override
	public void agentMasterEntityRunEnded(BaseMasterEntity runMasterEntity) {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside agentMasterEntityRunEnded ");
	}

}
