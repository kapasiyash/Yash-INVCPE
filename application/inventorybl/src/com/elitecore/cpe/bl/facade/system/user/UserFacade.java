package com.elitecore.cpe.bl.facade.system.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleGroupData;
import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.NoSuchControllerException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.system.internal.SystemInternalFacadeLocal;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;





/**
 * @author yash.kapasi
 *
 */
@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class UserFacade extends BaseFacade  implements UserFacadeRemote,UserFacadeLocal  {
	
	private static final String MODULE = "USER-FACADE";
	@EJB private SystemInternalFacadeLocal systemInternalFacadeLocal;
	
	/**
	 * Method can be use for user login according system policy  
	 * @param username,password.IpAddress
	 * @return IBLSessionContext 
	 * @throws  SearchBLException,AccessDeniedException
	 * @author yash.kapasi
	 */
	
	public IBDSessionContext doLogin(String username, String password,String strIpAddress) throws AccessDeniedException, SearchBLException {
		try {
			IBDSessionContext context = null;
			context = UserFactory.doLogin(username, password, strIpAddress);
			if (context!=null) {
					Logger.logTrace(MODULE, "in before calling");
				
				return context; 
			} 	else {
				return null;
			}
		} catch (NoSuchControllerException e) {
			throw new SearchBLException(e.getMessage());
		}
}

	@Override
	public Map<String, SystemActionData> getActions() throws Exception {
		Map<String, SystemActionData> actionMap = new HashMap<String, SystemActionData>();
		List<SystemModuleGroupData> moduleGroupData = systemInternalFacadeLocal.getSystemModuleData();
		for(SystemModuleGroupData groupData : moduleGroupData){
			for(SystemModuleData moduleData : groupData.getSystemModules()){
				for(SystemActionData actionData : moduleData.getSystemActions()){
					actionMap.put(actionData.getActionAlias(), actionData);
					if(actionData.getChildActions()!=null && !actionData.getChildActions().isEmpty()){
						for(SystemActionData childActionData : actionData.getChildActions()){
							actionMap.put(childActionData.getActionAlias(), childActionData);
						}
					}
				}
			}
		}
		return actionMap;
	}

	@Override
	public void doLogout(IBLSession iblSession) {
		
		Map<String,Object> mapAudit = new HashMap<String, Object>();
		mapAudit.put(AuditTagConstant.NAME,iblSession.getName());
		
		addToAuditDynamicMessage(AuditConstants.LOGOUT_ACTION, "Logged out from CPE",AuditConstants.CREATE_AUDIT_TYPE, mapAudit,null, iblSession);
		
	}

	/*
	private Map<String, SystemActionData> getSystemActionMap(User user) {
		Map<String,SystemActionData> actionMap = new HashMap<String,SystemActionData>();
		if(user.getAclGroup()!=null && user.getAclGroup().getPermittedActions()!=null && !user.getAclGroup().getPermittedActions().isEmpty() ){
			for(SystemAction action: user.getAclGroup().getPermittedActions()){
				actionMap.put(action.getActionAlias(), SystemInternalDataConversionUtil.getSystemActionData(action));
			}
		}
		return actionMap;
	}*/



	
	
	
	
}
