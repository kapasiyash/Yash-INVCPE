package com.elitecore.cpe.bl.controller.system;

import java.util.Map;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.facade.bss.user.BSSUserFacadeLocal;
import com.elitecore.cpe.bl.facade.bss.user.IBSSUserFacade;
import com.elitecore.cpe.bl.vo.system.user.UserVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.core.controller.BaseController;


public class BSSUserAuthController extends BaseController {
	
	
	
	@Override
	public UserVO findUserById(String userId) throws SearchBLException {
		try{
			IBSSUserFacade bssUserFacade = (IBSSUserFacade) lookup(BSSUserFacadeLocal.class);
			return bssUserFacade.findBSSUserById(userId);
		}catch(NamingException e){
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}

	@Override
	public IBDSessionContext doLogin(String username, String password,String remoteIpAddr) throws AccessDeniedException,SearchBLException {
		try {
			IBSSUserFacade bssUserFacade = (IBSSUserFacade) lookup(BSSUserFacadeLocal.class);
			return bssUserFacade.doLogin(username, password, remoteIpAddr);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new AccessDeniedException(e.getMessage());
		}
	}

	@Override
	public Map<String, UserVO> findAllUser() throws SearchBLException {
		
		try{
			IBSSUserFacade bssUserFacade = (IBSSUserFacade) lookup(BSSUserFacadeLocal.class);
			return bssUserFacade.findAllUser();
		}catch(NamingException e){
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
		
	}

}
