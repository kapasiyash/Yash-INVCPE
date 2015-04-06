package com.elitecore.cpe.bl.facade.system.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.constants.user.UserConstants;
import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleGroupData;
import com.elitecore.cpe.bl.entity.inventory.bss.user.BSSUser;
import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemAction;
import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemModule;
import com.elitecore.cpe.bl.entity.inventory.system.userwarehouse.UserWarehouseMapping;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.session.system.internal.SystemInternalSessionBeanLocal;
import com.elitecore.cpe.bl.vo.system.userwarehousemapping.UserWarehouseVO;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;






/**
 * 
 * @author Yash.Kapasi
 *
 */
@Stateless
@TransactionManagement( TransactionManagementType.CONTAINER)
public class SystemInternalFacade extends BaseFacade implements SystemInternalFacadeLocal,SystemInternalFacadeRemote {

	
	@EJB private SystemInternalSessionBeanLocal systemInternalSessionBeanLocal;
//	@EJB private  UserSessionBeanLocal userSessionBeanLocal;
	/**
	 * Method Use find All SystemModuleGroup With positive Id 
	 * @param 
	 * @return List OF SystemModuleGroupData
	 * @throws  SearchBLException
	 * @author Yash.Kapasi
	 */
	public List<SystemModuleGroupData> getSystemModuleData() throws SearchBLException{
		List<SystemModuleGroupData> systemModuleGroupDatas = null;
		List<SystemModule> systemModules = systemInternalSessionBeanLocal.findAllSystemModules();
		if(systemModules!=null && !systemModules.isEmpty()) {
				systemModuleGroupDatas = new ArrayList<SystemModuleGroupData>();
				SystemModuleGroupData moduleGroupData = getSystemModuleGroupDatabyModule(systemModules, null);
				if(moduleGroupData != null && moduleGroupData.getSystemModules() != null && !moduleGroupData.getSystemModules().isEmpty()) {
					systemModuleGroupDatas.add(moduleGroupData);
				}
		}
		/*List<SystemModuleGroup> systemModuleGroups = systemInternalSessionBeanLocal.findAllSystemModuleGroup();	
		if(systemModuleGroups!=null && !systemModuleGroups.isEmpty()){
			systemModuleGroupDatas = new ArrayList<SystemModuleGroupData>();
			for(SystemModuleGroup systemModuleGroup :  systemModuleGroups){
				systemModuleGroupDatas.add(getSystemModuleGroupData(systemModuleGroup));
			}
			Collections.sort(systemModuleGroupDatas);
		}*/
		return systemModuleGroupDatas;
	}
	
	/*private SystemModuleGroupData getSystemModuleGroupData(SystemModuleGroup systemModuleGroup){
		SystemModuleGroupData moduleGroupData = new SystemModuleGroupData();
		moduleGroupData.setAlias(systemModuleGroup.getAlias());
		moduleGroupData.setDescription(systemModuleGroup.getDescription());
		moduleGroupData.setModuleGroupId(systemModuleGroup.getModuleGroupId());
		moduleGroupData.setName(systemModuleGroup.getName());
		moduleGroupData.setSequenceNumber(systemModuleGroup.getSequenceNumber());
		moduleGroupData.setActiveIconUrl(systemModuleGroup.getActiveIconUrl());
		moduleGroupData.setHomeURL(systemModuleGroup.getHomeURL());
		moduleGroupData.setIconUrl(systemModuleGroup.getIconUrl());
		
		if(systemModuleGroup.getSystemModules()!=null && !systemModuleGroup.getSystemModules().isEmpty()){
			for(SystemModule systemModule : systemModuleGroup.getSystemModules()){			
				moduleGroupData.addSystemModule(getSystemModuleData(systemModule));
			}
			Collections.sort(moduleGroupData.getSystemModules());
		}
		return moduleGroupData;
	}*/
	
	/*private SystemModuleData getSystemModuleData(SystemModule systemModule){
		SystemModuleData moduleData = new SystemModuleData();
		moduleData.setAlias(systemModule.getAlias());
		moduleData.setDescription(systemModule.getDescription());		
		moduleData.setModuleId(systemModule.getModuleId());
		moduleData.setName(systemModule.getName());
		moduleData.setSequenceNumber(systemModule.getSequenceNumber());
		if(systemModule.getSystemActions()!=null && !systemModule.getSystemActions().isEmpty()){
			for(SystemAction systemAction : systemModule.getSystemActions()){
				SystemActionData systemActionData =  getSystemActionData(systemAction);
				if(systemActionData!=null){
					moduleData.addSystemAction(systemActionData);
				}
			}
			Collections.sort(moduleData.getSystemActions());
		}
		return moduleData;
	}*/
	
	private SystemActionData getSystemActionData(SystemAction systemAction){
		SystemActionData systemActionData = null;
		if(systemAction.getParentAction()==null){
			systemActionData= new SystemActionData();
			systemActionData.setName(systemAction.getName());
			systemActionData.setActionAlias(systemAction.getActionAlias());
			systemActionData.setActionId(systemAction.getActionId());					
			systemActionData.setParentAction(true);			
			systemActionData.setSequencenumber(systemAction.getSequenceNumber());
			systemActionData.setZulPageUrl(systemAction.getPageUrl());
			systemAction.setIsAuditable(systemAction.getIsAuditable());
			systemAction.setEnableaudit(systemAction.getEnableaudit());
			if(systemAction.getChildActions()!=null && !systemAction.getChildActions().isEmpty()){
				for(SystemAction childSystemAction : systemAction.getChildActions()){
					systemActionData.addChildAction(getChildSystemActionData(childSystemAction));	
				}
				Collections.sort(systemActionData.getChildActions());
			}									
		}					
		return systemActionData;
	}
	
	private SystemActionData getSystemActionDataForAudit(SystemAction systemAction) {
		SystemActionData systemActionData = null;
		if(systemAction.getParentAction()!=null){
			systemActionData= new SystemActionData();
			systemActionData.setName(systemAction.getName());
			systemActionData.setActionAlias(systemAction.getActionAlias());
			systemActionData.setActionId(systemAction.getActionId());					
			systemActionData.setParentAction(true);			
			systemActionData.setSequencenumber(systemAction.getSequenceNumber());
			systemActionData.setZulPageUrl(systemAction.getPageUrl());
			systemAction.setIsAuditable(systemAction.getIsAuditable());
			systemAction.setEnableaudit(systemAction.getEnableaudit());
			if(systemAction.getChildActions()!=null && !systemAction.getChildActions().isEmpty()){
				for(SystemAction childSystemAction : systemAction.getChildActions()){
					systemActionData.addChildAction(getChildSystemActionData(childSystemAction));	
				}
				Collections.sort(systemActionData.getChildActions());
			}									
		}					
		return systemActionData;
	}
	
	private SystemActionData getSystemActionDataByForgettingParent(SystemAction systemAction){
		SystemActionData systemActionData = null;
		
			systemActionData= new SystemActionData();
			systemActionData.setName(systemAction.getName());
			systemActionData.setActionAlias(systemAction.getActionAlias());
			systemActionData.setActionId(systemAction.getActionId());					
			systemActionData.setSequencenumber(systemAction.getSequenceNumber());
			systemActionData.setZulPageUrl(systemAction.getPageUrl());
			systemAction.setIsAuditable(systemAction.getIsAuditable());
			systemAction.setEnableaudit(systemAction.getEnableaudit());
			if(systemAction.getChildActions()!=null && !systemAction.getChildActions().isEmpty()){
				for(SystemAction childSystemAction : systemAction.getChildActions()){
					systemActionData.addChildAction(getChildSystemActionData(childSystemAction));	
				}
				Collections.sort(systemActionData.getChildActions());
			} else {
				systemActionData.setParentAction(true);			
			}									
							
		return systemActionData;
	}
	
	
	private SystemActionData getChildSystemActionData(SystemAction childSystemAction){
		SystemActionData systemActionData = new SystemActionData();
		systemActionData.setName(childSystemAction.getName());
		systemActionData.setActionAlias(childSystemAction.getActionAlias());
		systemActionData.setActionId(childSystemAction.getActionId());		
		systemActionData.setSequencenumber(childSystemAction.getSequenceNumber());
		systemActionData.setZulPageUrl(childSystemAction.getPageUrl());		
		systemActionData.setParentAction(false);
		systemActionData.setIsAuditable(childSystemAction.getIsAuditable());
		systemActionData.setEnableAudit(childSystemAction.getEnableaudit());
		systemActionData.setEnableVisible(childSystemAction.getEnableVisible());
		if(childSystemAction.getChildActions()!=null && !childSystemAction.getChildActions().isEmpty()){
			for(SystemAction systemAction : childSystemAction.getChildActions()){
					systemActionData.addChildAction(getChildSystemActionData(systemAction));	
			}				
		}
		return systemActionData;
	}
	

	/*@Override
	public List<SystemModuleGroupData> getSystemModuleData(IBLSession blSession) throws SearchBLException{
		List<SystemModuleGroupData> systemModuleGroupDatas = new ArrayList<SystemModuleGroupData>();
		List<SystemModuleGroup> systemModuleGroups = systemInternalSessionBeanLocal.findAllSystemModuleGroup();		
		User user= userSessionBeanLocal.findUserById(blSession.getSessionUserId());
		if(user.getUserTypeId().equals(UserConstants.ADMIN_USER_TYPE)) {
			for(SystemModuleGroup systemModuleGroup :  systemModuleGroups){
				systemModuleGroupDatas.add(getSystemModuleGroupData(systemModuleGroup, null));
			}
		} else {
			ACLGroup aclGroup = user.getAclGroup();
			if(aclGroup != null ) {
				Set<SystemAction> permittedSystemActions = new java.util.HashSet<SystemAction>();
				
					if(aclGroup.getPermittedActions() != null && !aclGroup.getPermittedActions().isEmpty()) {
						permittedSystemActions.addAll(aclGroup.getPermittedActions());
					}
				
				for(SystemModuleGroup systemModuleGroup :  systemModuleGroups){
					SystemModuleGroupData moduleGroupData = getSystemModuleGroupData(systemModuleGroup, permittedSystemActions);
					if(moduleGroupData != null && moduleGroupData.getSystemModules() != null && !moduleGroupData.getSystemModules().isEmpty()) {
						systemModuleGroupDatas.add(moduleGroupData);
					}
				}
			}
			
		}
		Collections.sort(systemModuleGroupDatas);
		return systemModuleGroupDatas;
	}*/
	@Override
	public List<SystemModuleGroupData> getSystemModuleData(IBLSession blSession) throws SearchBLException{
		List<SystemModuleGroupData> systemModuleGroupDatas = new ArrayList<SystemModuleGroupData>();
//		List<SystemModuleGroup> systemModuleGroups = systemInternalSessionBeanLocal.findAllSystemModuleGroup();
		List<SystemModule> systemModules = systemInternalSessionBeanLocal.findAllSystemModules();
		/*if(systemModules!=null && !systemModules.isEmpty()) {
				SystemModuleGroupData moduleGroupData = getSystemModuleGroupDatabyModule(systemModules, blSession.getPermittedAction());
				if(moduleGroupData != null && moduleGroupData.getSystemModules() != null && !moduleGroupData.getSystemModules().isEmpty()) {
					systemModuleGroupDatas.add(moduleGroupData);
				}
		}
		*/
		if(UserConstants.ADMIN_USER_TYPE.equals(blSession.getUserType())) {
				systemModuleGroupDatas.add(getSystemModuleGroupDatabyModule(systemModules, null));
		} else {
			if(blSession.getPermittedAction() != null ) {
				Set<SystemAction> permittedSystemActions = findSystemActions(blSession.getPermittedAction());
					SystemModuleGroupData moduleGroupData = getSystemModuleGroupDatabyModule(systemModules, permittedSystemActions);
					if(moduleGroupData != null && moduleGroupData.getSystemModules() != null && !moduleGroupData.getSystemModules().isEmpty()) {
						systemModuleGroupDatas.add(moduleGroupData);
					}
			}
		}
		Collections.sort(systemModuleGroupDatas);
		return systemModuleGroupDatas;
	}
	
	private SystemModuleGroupData getSystemModuleGroupDatabyModule(List<SystemModule> systemModuleData, Set<SystemAction> permittedSystemActions){
		SystemModuleGroupData moduleGroupData = new SystemModuleGroupData();
		/*moduleGroupData.setAlias(systemModuleData.getAlias());
		moduleGroupData.setDescription(systemModuleData.getDescription());
		moduleGroupData.setModuleGroupId(systemModuleData.getModuleGroupId());
		moduleGroupData.setName(systemModuleData.getName());
		moduleGroupData.setSequenceNumber(systemModuleData.getSequenceNumber());
		moduleGroupData.setHomeURL(systemModuleData.getHomeURL());
		
		if(systemModuleData.getIconUrl()!=null){
			moduleGroupData.setIconUrl(systemModuleData.getIconUrl());
		}
		
		if(systemModuleData.getActiveIconUrl()!=null){
			moduleGroupData.setActiveIconUrl(systemModuleData.getActiveIconUrl());	
		}*/
		
		for(SystemModule systemModule : systemModuleData){
			SystemModuleData moduleData = getSystemModuleData(systemModule, permittedSystemActions);
			if(moduleData != null && moduleData.getSystemActions() != null && !moduleData.getSystemActions().isEmpty()) {
				if(moduleData.getPanel().equals(CPECommonConstants.ADMIN_PANEL)) {
					moduleGroupData.setIconUrl("/images/icons/16/configuration.png");
					moduleGroupData.setActiveIconUrl("/images/icons/16/configuration-active.png");
					moduleGroupData.setName("System");
				}
				moduleGroupData.addSystemModule(moduleData);
			}
		}
		
		if(moduleGroupData.getSystemModules()!=null && !moduleGroupData.getSystemModules().isEmpty()){
			Collections.sort(moduleGroupData.getSystemModules());	
		}
		
		return moduleGroupData;
	}
	
	
	/*private SystemModuleGroupData getSystemModuleGroupData(SystemModuleGroup systemModuleGroup, Set<SystemAction> permittedSystemActions){
		SystemModuleGroupData moduleGroupData = new SystemModuleGroupData();
		moduleGroupData.setAlias(systemModuleGroup.getAlias());
		moduleGroupData.setDescription(systemModuleGroup.getDescription());
		moduleGroupData.setModuleGroupId(systemModuleGroup.getModuleGroupId());
		moduleGroupData.setName(systemModuleGroup.getName());
		moduleGroupData.setSequenceNumber(systemModuleGroup.getSequenceNumber());
		moduleGroupData.setHomeURL(systemModuleGroup.getHomeURL());
		
		if(systemModuleGroup.getIconUrl()!=null){
			moduleGroupData.setIconUrl(systemModuleGroup.getIconUrl());
		}
		
		if(systemModuleGroup.getActiveIconUrl()!=null){
			moduleGroupData.setActiveIconUrl(systemModuleGroup.getActiveIconUrl());	
		}
		
		for(SystemModule systemModule : systemModuleGroup.getSystemModules()){
			SystemModuleData moduleData = getSystemModuleData(systemModule, permittedSystemActions);
			if(moduleData != null && moduleData.getSystemActions() != null && !moduleData.getSystemActions().isEmpty()) {
				moduleGroupData.addSystemModule(moduleData);
			}
		}
		
		if(moduleGroupData.getSystemModules()!=null && !moduleGroupData.getSystemModules().isEmpty()){
			Collections.sort(moduleGroupData.getSystemModules());	
		}
		
		return moduleGroupData;
	}*/
	

	private SystemModuleData getSystemModuleData(SystemModule systemModule, Set<SystemAction> permittedSystemActions){
		SystemModuleData moduleData = new SystemModuleData();
		if(systemModule!=null) {
			moduleData.setAlias(systemModule.getAlias());
			moduleData.setDescription(systemModule.getDescription());		
			moduleData.setModuleId(systemModule.getModuleId());
			moduleData.setName(systemModule.getName());
			moduleData.setPanel(systemModule.getPanel());
			moduleData.setSequenceNumber(systemModule.getSequenceNumber());
		}
		if(systemModule != null && systemModule.getSystemActions() != null && !systemModule.getSystemActions().isEmpty())
		{
			for(SystemAction systemAction : systemModule.getSystemActions()){
				if(systemAction.getParentAction()==null && (permittedSystemActions == null || permittedSystemActions.contains(systemAction))) {
						SystemActionData systemActionData =  getSystemActionData(systemAction, permittedSystemActions);
						moduleData.addSystemAction(systemActionData);
					
				}
			}		
		}
		if(moduleData.getSystemActions()!=null && !moduleData.getSystemActions().isEmpty()){
			Collections.sort(moduleData.getSystemActions());	
		}
		
		return moduleData;
	}
	
	private SystemActionData getSystemActionData(SystemAction systemAction, Set<SystemAction> permittedSystemActions){
		SystemActionData systemActionData = null;
		systemActionData= new SystemActionData();
		systemActionData.setName(systemAction.getName());
		systemActionData.setActionAlias(systemAction.getActionAlias());
		systemActionData.setActionId(systemAction.getActionId());					
		systemActionData.setParentAction(true);			
		systemActionData.setSequencenumber(systemAction.getSequenceNumber());
		systemActionData.setZulPageUrl(systemAction.getPageUrl());
		systemActionData.setEnableVisible(systemAction.getEnableVisible());
		if(systemAction.getChildActions()!=null && !systemAction.getChildActions().isEmpty()){
			for(SystemAction childSystemAction : systemAction.getChildActions()){
					systemActionData.addChildAction(getChildSystemActionData(childSystemAction));	
			}
			Collections.sort(systemActionData.getChildActions());
		}									
		return systemActionData;
	}
	
	private Set<SystemAction> findSystemActions(List<SystemActionData> actionDatas) throws SearchBLException{
		Set<SystemAction>  actions = new HashSet<SystemAction>();
		if(actionDatas!=null && !actionDatas.isEmpty()){
			for(SystemActionData actionData : actionDatas){
				SystemAction action = systemInternalSessionBeanLocal.findSystemActionByAlias(actionData.getActionAlias());
				if(action!=null){
					actions.add(action);
				}
			}
		}
		return actions;
	}
	
	
	@Override
	public List<SystemActionData> findSystemActionData(List<String> actionAliases) throws SearchBLException{
		List<SystemActionData>  actions = new ArrayList<SystemActionData>();
		if(actionAliases!=null && !actionAliases.isEmpty()){
			for(String alias : actionAliases){
				SystemAction action = systemInternalSessionBeanLocal.findSystemActionByAlias(alias);
				if(action!=null){
//					SystemActionData systemActionData = getSystemActionData(action);getSystemActionDataByForgettingParent
					SystemActionData systemActionData = getSystemActionDataByForgettingParent(action);
					actions.add(systemActionData);
				}
			}
		}
		return actions;
	}
	
	@Override
	public List<SystemActionData> findAllSystemActions()
			throws SearchBLException {
		List<SystemActionData> actionDatas = new ArrayList<SystemActionData>();
		List<SystemAction> actions = systemInternalSessionBeanLocal.findAllSystemAction();
		for(SystemAction systemAction : actions) {
			SystemActionData data = getSystemActionData(systemAction);
			actionDatas.add(data);
		}
		
		return actionDatas;
	}
	
	
	public List<SystemActionData> findAllSystemActionsForAudit() throws SearchBLException {
		List<SystemActionData> actionDatas = new ArrayList<SystemActionData>();
		List<SystemAction> actions = systemInternalSessionBeanLocal.findAllSystemAction();
		for(SystemAction systemAction : actions) {
			SystemActionData data = getSystemActionDataForAudit(systemAction);
			actionDatas.add(data);
		}
		
		return actionDatas;
	}
	
	
	@Override
	public List<ComboBoxData> getAllSystemAction() throws SearchBLException {
		
		List<SystemActionData> actionDatas = findAllSystemActionsForAudit();
		List<ComboBoxData> comboDatas = new ArrayList<ComboBoxData>();
		for(SystemActionData actionData : actionDatas) {
			if(actionData!=null) {
				comboDatas.add(new ComboBoxData(actionData.getActionAlias(), actionData.getName()));
			}
		}
		return comboDatas;
	}

	@Override
	public List<ComboData> getAllSystemModules() throws SearchBLException {
		
		
		 List<SystemModule> modules = systemInternalSessionBeanLocal.findAllSystemModules();
		
		List<ComboData> comboDatas = new ArrayList<ComboData>();
		for(SystemModule moduleData : modules) {
			if(moduleData!=null) {
				comboDatas.add(new ComboData(moduleData.getModuleId(), moduleData.getName()));
			}
		}
		return comboDatas;
	}

	@Override
	public List<ComboBoxData> getAllSystemActionByModuleId(Long typeId)
			throws SearchBLException {
		
		Logger.logDebug("SYSTEM", "in getAllSystemActionByModuleId");
		SystemModule module = systemInternalSessionBeanLocal.findSystemModuleById(typeId);
		
		List<ComboBoxData> comboDatas = new ArrayList<ComboBoxData>();
		
		if(module!=null && module.getSystemActions()!=null) {
			
			for(SystemAction actionData : module.getSystemActions()) {

				if(actionData!=null && actionData.getIsAuditable().equals('Y') && (actionData.getParentAction() != null || actionData.getActionAlias().equals(AuditConstants.UPDATE_SYSTEM_PARAMETER))) {
					comboDatas.add(new ComboBoxData(actionData.getActionAlias(), actionData.getName()));
				}
			}
		}
		return comboDatas;
	}

	@Override
	public List<ComboBoxData> getAllUsers() throws SearchBLException {
		
		Logger.logDebug("SYSTEM", "in getAllUsers");
		List<BSSUser> users = systemInternalSessionBeanLocal.findAllUsers();
		
		List<ComboBoxData> comboDatas = new ArrayList<ComboBoxData>();
		if(users!=null && !users.isEmpty()) {
			for(BSSUser bssUser : users) {
				comboDatas.add(new ComboBoxData(bssUser.getUserId(), bssUser.getName()+"("+bssUser.getUsername()+")"));
			}
		}
		
		
		return comboDatas;
		
	}

	@Override
	public List<ComboData> findUserWareHouseMapping(String userId)
			throws SearchBLException {
		
		Logger.logDebug("SYSTEM", "in findUserWareHouseMapping");
		List<UserWarehouseMapping> userWarehouseMapping = systemInternalSessionBeanLocal.findUserWareHouseMapping(userId);
//		System.out.println(userWarehouseMapping);
		
		List<ComboData> userWarehouseVos = new ArrayList<ComboData>();
		if(userWarehouseMapping!=null && !userWarehouseMapping.isEmpty()) {
			for(UserWarehouseMapping userwareMap : userWarehouseMapping) {
//				System.out.println("addingds ds ds");
				userWarehouseVos.add(new ComboData(userwareMap.getWarehouseId(), userwareMap.getWarehouseData().getName()));
			}
		}
		
		
		return userWarehouseVos;
		
	}

	@Override
	public void updateUserWarehouseMapping(String name,String userId,List<ComboData> selectedData, IBLSession blSession)
			throws UpdateBLException {
		
		
		systemInternalSessionBeanLocal.deleteUserWareHouseMapping(userId);
		if(selectedData!=null && !selectedData.isEmpty()) {
			
			for(ComboData comboData : selectedData) {
				UserWarehouseMapping mapping = new UserWarehouseMapping();
				mapping.setUserId(userId);
				mapping.setWarehouseId(comboData.getId());
				try {
					systemInternalSessionBeanLocal.persistUserWarehouseMapping(mapping);
				} catch (CreateBLException e) {
					e.printStackTrace();
					throw new UpdateBLException(e.getMessage());
				}
			}
			
			// Audit entry for TransferOrderData
			Map<String,Object> mapAudit = new HashMap<String, Object>();
			mapAudit.put(AuditTagConstant.NAME,name);
			addToAuditDynamicMessage(AuditConstants.USER_WAREHOUSE_MAPPING, "Creating User-Warehouse Mapping",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, blSession);
			
		}
		
	}
}
