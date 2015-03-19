package com.elitecore.cpe.bl.facade.bss.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.constants.user.UserConstants;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleGroupData;
import com.elitecore.cpe.bl.entity.inventory.bss.acl.BSSACLGroup;
import com.elitecore.cpe.bl.entity.inventory.bss.user.BSSUser;
import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemAction;
import com.elitecore.cpe.bl.entity.inventory.user.EncryptionType;
import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.system.internal.SystemInternalFacadeLocal;
import com.elitecore.cpe.bl.session.bss.user.BSSUserSessionBeanLocal;
import com.elitecore.cpe.bl.session.user.UserSessionBeanLocal;
import com.elitecore.cpe.bl.vo.system.user.UserVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.core.InternalBLSession;
import com.elitecore.cpe.core.SessionAccessControl;
import com.elitecore.cpe.util.encryption.NoSuchEncryptionException;
import com.elitecore.cpe.util.encryption.PasswordEncryption;
import com.elitecore.cpe.util.logger.Logger;

/**
 * Session Bean implementation class BSSUserFacade
 */
@Stateless
public class BSSUserFacade extends BaseFacade implements BSSUserFacadeRemote, BSSUserFacadeLocal {

    private static final String MODULE = "BSS-USER-FACADE";

    @EJB private UserSessionBeanLocal userSessionBeanLocal;
    @EJB private BSSUserSessionBeanLocal bssUserSessionBeanLocal; 
	@EJB protected SystemInternalFacadeLocal systemInternalFacadeLocal;
	/**
     * Default constructor. 
     */
    public BSSUserFacade() {
    }
    
    public static UserVO prepareUserVO(BSSUser user){
    	UserVO userVO = new UserVO(user.getUserId(),user.getUsername(),user.getPassword(),user.getEncryptionType(),user.getEncryptionType(),user.getName(),"",true);
    	return userVO;
    }
    
    
    /**
	 * Method to Find User by its Id
	 * @author yash.kapasi
	 * @param {@link String} userId
	 * @return {@link UserVO} userVO
	 * @throws SearchBLException
	 */
	@Override
	public UserVO findBSSUserById(String userId) throws SearchBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside findBSSUserById userId= "+userId);
		}
		
		BSSUser user = bssUserSessionBeanLocal.findBSSUserById(userId);
		if(user==null)
			throw new SearchBLException("No such user found");
		UserVO userVO = prepareUserVO(user);
		Map<String, SystemActionData> actions = getSystemActionMap(user);
		userVO.setPermittedActions(new HashSet<SystemActionData>(actions.values()));
		return userVO;
	}

	
	/**
	 * Logins in the system and returns the session context prepared after successfull login
	 * @author yash.kapasi
	 * @param {@link String} password
	 * @param {@link String} remoteIpAddr
	 * @return {@link IBDSessionContext} blSessionContextImp
	 * @throws SearchBLException
	 */
	@Override
	public IBDSessionContext doLogin(String username, String password,
			String remoteIpAddr) throws SearchBLException, AccessDeniedException {
		
		if(isTraceLevel())
			Logger.logTrace(MODULE, "Inside doLogin operation for user:");
		
		IBDSessionContext blSessionContextImp = null;
		SessionAccessControl sessionAccessControl = null;
		
		try{
			BSSUser user = bssUserSessionBeanLocal.findByUsername(username);
			if(user == null) {
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				addToAuditDynamicMessage(AuditConstants.LOGIN_ACTION, "Logging Failed in CPE","Failed to Login in CPE with username : "+username,AuditConstants.CREATE_AUDIT_TYPE, mapAudit,null, null,remoteIpAddr);
				throw new AccessDeniedException("Invalid Userame / Password");
			}
			
			boolean isAuthenticated = false;
			if(password!=null){
				 try {
					EncryptionType encryptionType  =  userSessionBeanLocal.findEncryptionTypeById(user.getEncryptionType());
					if(encryptionType!=null){
						isAuthenticated = PasswordEncryption.matches(user.getPassword(), password, encryptionType.getAlias());
					}
				} catch (NoSuchEncryptionException e) {
					throw new AccessDeniedException("Invalid Userame / Password");
				}
			}else{
				throw new AccessDeniedException("Invalid Userame / Password");
			}
			if(isAuthenticated && user.getCommonstatusid().equals(UserConstants.BSS_USER_ACTIVEID) && user.getSystemgenerated().equals('N')){
				String userId = user.getUserId();
				String name = user.getName() ;
				String userName=user.getUsername();
				String passwrd=getEncryptedPassword(password);
				String userTypeId= null; 
				if(UserConstants.BSS_ADMIN_STAFFID.equals(user.getUserId())){
					userTypeId = UserConstants.ADMIN_USER_TYPE;
				}else{
					userTypeId = UserConstants.AR_USER_TYPE;
				}
				String ipAddress=remoteIpAddr;
				Map<String,SystemActionData> permitedActions = getSystemActionMap(user);
				Set<Long> userWarehouseMappings = getUserWarehouseMapping(user);
				InternalBLSession internalBLSession = createAdminBLSession(userId,name,userName, passwrd,userTypeId, ipAddress,permitedActions,userWarehouseMappings);
				blSessionContextImp = internalBLSession.prepareBLSessionContextImp(internalBLSession, sessionAccessControl);
				
				
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.NAME,name);
				
				addToAuditDynamicMessage(AuditConstants.LOGIN_ACTION, "Logging in CPE",AuditConstants.CREATE_AUDIT_TYPE, mapAudit,null, blSessionContextImp.getBLSession());
				
				
			}else{
				
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				addToAuditDynamicMessage(AuditConstants.LOGIN_ACTION, "Logging Failed in CPE","Failed to Login in CPE with username : "+username,AuditConstants.CREATE_AUDIT_TYPE, mapAudit,null, null,remoteIpAddr);
				
				throw new AccessDeniedException("Invalid Userame / Password");
			}
			if(isTraceLevel())
				logTrace(MODULE, "return doLogin operation for user:");
			return blSessionContextImp;
		}catch (RuntimeException e) {
			if(isErrorLevel())
				logError(MODULE, "RuntimeException Error Inside doLogin operation Reason:" +e.getMessage());
			e.printStackTrace();
			throw new SearchBLException("Access Denied: " + e.getMessage());	
		} catch (SearchBLException e) {
			if(isErrorLevel())
				logError(MODULE, "SearchBLException Error Inside doLogin operation Reason:" +e.getMessage());
			e.printStackTrace();
			throw new SearchBLException("Access Denied: " + e.getMessage());
		}
	}
	
	
	/**
	 * Gets User warehouse Mapping from User entity
	 * @author yash.kapasi
	 * @param {@link BSSUser} user
	 * @return {@link Set}<{@link Long}> userwarehouses
	 */
	private Set<Long> getUserWarehouseMapping(BSSUser user) {
		
		Set<Long> userwarehouses = new HashSet<Long>();
		if(user!=null) {
			try {
				List<ComboData> datas = systemInternalFacadeLocal.findUserWareHouseMapping(user.getUserId());
				if(datas!=null && !datas.isEmpty()) {
					for(ComboData comboData : datas) {
						userwarehouses.add(comboData.getId());
					}
					return userwarehouses;
				}
				
			} catch (SearchBLException e) {
				e.printStackTrace();
			}
		}
		
		return userwarehouses;
	}

	
	
	private String getEncryptedPassword(String unEncrypted){
		String encPassword="";
		try {
			encPassword =PasswordEncryption.crypt(unEncrypted, PasswordEncryption.STR_ELITECRYPT);
		} catch (NoSuchEncryptionException e) {
			e.printStackTrace();
		}
		return encPassword;
	}
	
	
	/**
	 * Gets User System Action according to the ACL
	 * @author yash.kapasi
	 * @param {@link BSSUser} user
	 * @return {@link Map}<{@link String,SystemActionData}> actionMap
	 * @throws SearchBLException
	 */
	private Map<String, SystemActionData> getSystemActionMap(BSSUser user) throws SearchBLException {
		 Map<String, SystemActionData> actionMap = new HashMap<String, SystemActionData>();
		 if(user.getUserId().equals(UserConstants.BSS_ADMIN_STAFFID)){
				List<SystemModuleGroupData> moduleGroupData = systemInternalFacadeLocal.getSystemModuleData();
				for(SystemModuleGroupData groupData : moduleGroupData){
					for(SystemModuleData moduleData : groupData.getSystemModules()){
						for(SystemActionData actionData : moduleData.getSystemActions()){
							actionMap.put(actionData.getActionAlias(), actionData);
							if(actionData.getChildActions()!=null && !actionData.getChildActions().isEmpty()){
								for(SystemActionData childActionData : actionData.getChildActions()){
									if(childActionData.getChildActions()!=null && !childActionData.getChildActions().isEmpty()) {
										for(SystemActionData childActionDataChild : childActionData.getChildActions()){
											actionMap.put(childActionDataChild.getActionAlias(), childActionDataChild);
										}
									}
									actionMap.put(childActionData.getActionAlias(), childActionData);
								}
							}
						}
					}
				}
			}else{
					
				if(user.getUsername().equalsIgnoreCase("agent")) return actionMap;
				
				if(user.getAclGroups()!=null && !user.getAclGroups().isEmpty()){
					Set<String> actionAliases = new HashSet<String>();
						for(BSSACLGroup aclGroup : user.getAclGroups()){
							if(aclGroup.getPermittedActions()!=null && !aclGroup.getPermittedActions().isEmpty()){
								for(SystemAction actionRel : aclGroup.getPermittedActions()){
									actionAliases.add(actionRel.getActionAlias());
								}
								
							}
						}
						// Getting Action Data from Action Alias
						List<SystemActionData> actionset = systemInternalFacadeLocal.findSystemActionData(new ArrayList<String>(actionAliases));
						if(actionset!=null && !actionset.isEmpty()){
							for(SystemActionData actionData : actionset){
								if(actionData!=null) {
									actionMap.put(actionData.getActionAlias(), actionData);
								}
								
							}
						}
					}
			}
		return actionMap ;
	}

	
	/**
	 * Find all Users
	 * @author yash.kapasi
	 * @return {@link Map}<{@link String,UserVO}> map
	 * @throws SearchBLException
	 */
	@Override
	public Map<String, UserVO> findAllUser() throws SearchBLException {
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside findAllUser");
		}
		
		Map<String, UserVO> map = new HashMap<String, UserVO>();
		
		List<BSSUser> user = bssUserSessionBeanLocal.findAllUser();
		
		
		
		map = prepareUsersVO(user);
		
		
		return map;
	}

	private Map<String, UserVO> prepareUsersVO(List<BSSUser> userData) {
		
		Map<String, UserVO> map = new HashMap<String, UserVO>(); 
		
		for(BSSUser user : userData) {
			UserVO userVO = new UserVO(user.getUserId(),user.getUsername(),user.getPassword(),user.getEncryptionType(),user.getEncryptionType(),user.getName(),"",true);
			map.put(user.getUserId(), userVO);
		}
		
		return map;
	}

}
