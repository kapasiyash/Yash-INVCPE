package com.elitecore.cpe.bl.factory.system.user;

import java.util.Map;

import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.NoSuchControllerException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.system.user.UserVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.core.base.BaseBL;
import com.elitecore.cpe.core.controller.Controller;

public class UserFactory extends BaseBL {

	public static final String AR_USER_AUTHENTICATION = "LOCAL_AUTHENTICATION";
	public static final String EXTERNAL_USER_AUTHENTICATION = "EXTERNAL_AUTHENTICATION";
	
	/**
	 * Get Dynamic Controller for login purpose
	 * @param controllerAlias
	 * @return {@link Controller}
	 * @throws NoSuchControllerException
	 */
	public static Controller getController(String controllerAlias) throws NoSuchControllerException{
		String classname = null;
		if(controllerAlias.equals(AR_USER_AUTHENTICATION)){
			classname = "com.elitecore.cpe.bl.controller.system.CPEUserAuthController";
		}else if(controllerAlias.equals(EXTERNAL_USER_AUTHENTICATION)){
			classname="com.elitecore.cpe.bl.controller.system.BSSUserAuthController";
		}else{
			throw new NoSuchControllerException("No such controller found.");
		}
		
		try {
			Controller controller = (Controller) Class.forName(classname).newInstance();
			return controller;
		} catch (InstantiationException e) {
			throw new NoSuchControllerException(e.getMessage());
		} catch (IllegalAccessException e) {
			
			throw new NoSuchControllerException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new NoSuchControllerException(e.getMessage());
		}
	}
	
	
	public static final IBDSessionContext doLogin(String controllerAlias,String username,String password,String remoteIpAddr) throws AccessDeniedException, SearchBLException, NoSuchControllerException{
		return getController(controllerAlias).doLogin(username, password, remoteIpAddr);
	}
	
	public static final UserVO findUserById(String controllerAlias,String userId) throws SearchBLException, NoSuchControllerException{
		return getController(controllerAlias).findUserById(userId);
	}
	
	public static final IBDSessionContext doLogin(String username,String password,String remoteIpAddr) throws AccessDeniedException, SearchBLException, NoSuchControllerException{
		return doLogin(getDefaultAuthenticationController(), username, password, remoteIpAddr);
	}
	
	public static final UserVO findUserById(String userId) throws SearchBLException, NoSuchControllerException{
		return getController(getDefaultAuthenticationController()).findUserById(userId);
	}
	
	public static final Map<String, UserVO> findAllUser() throws SearchBLException, NoSuchControllerException{
		return getController(getDefaultAuthenticationController()).findAllUser();
	}
	
	
	public static final String getDefaultAuthenticationController(){
		return EXTERNAL_USER_AUTHENTICATION;
	}
}
