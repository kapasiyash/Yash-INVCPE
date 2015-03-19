package com.elitecore.cpe.bl.controller.system;


import java.util.Map;
import java.util.UUID;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.facade.system.user.IUserFacade;
import com.elitecore.cpe.bl.facade.system.user.UserFacadeLocal;
import com.elitecore.cpe.bl.vo.system.user.UserVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.core.InternalBLSession;
import com.elitecore.cpe.core.SessionAccessControl;
import com.elitecore.cpe.core.controller.BaseController;
import com.elitecore.cpe.core.controller.Controller;
import com.elitecore.cpe.util.logger.Logger;

public class CPEUserAuthController extends BaseController implements Controller {

	
	

	@Override
	public IBDSessionContext doLogin(String username, String password,String remoteIpAddr) throws AccessDeniedException, SearchBLException {
		IBDSessionContext blSessionContextImp = null;
		SessionAccessControl sessionAccessControl = null;
		if(isTraceLevel()){
			Logger.logTrace("USER-AUTH", "inside doLogin of Local Authentication");
		}
		IUserFacade userFacadeLocal;
		try {
			userFacadeLocal = (UserFacadeLocal) lookup(UserFacadeLocal.class);
			
			InternalBLSession internalBLSession = createAdminBLSession(String.valueOf("1"),"name","admin", "admin","1", "23",userFacadeLocal.getActions());
			blSessionContextImp = internalBLSession.prepareBLSessionContextImp(internalBLSession, sessionAccessControl);
			return blSessionContextImp;
		} catch (Exception e) {
			throw new AccessDeniedException("Access Denied.");
		}
		
	}
	
	public void getSystemMap() {
		
	}
	
	
	protected final InternalBLSession createAdminBLSession(String userId, String name,String username, String password, String userType, String ipAddress,Map<String, SystemActionData> permitedActions) {

		String sessionId = UUID.randomUUID().toString();
		InternalBLSession internalBLSession = new InternalBLSession(userId,name, username, password, sessionId, ipAddress, userType,permitedActions,null);
		return internalBLSession;
	}

	@Override
	public UserVO findUserById(String userId) throws SearchBLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, UserVO> findAllUser() throws SearchBLException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
