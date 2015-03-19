package com.elitecore.cpe.bl.facade.bss.user;

import java.util.Map;

import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.system.user.UserVO;
import com.elitecore.cpe.core.IBDSessionContext;

/**
 * @author Yash.Kapasi
 *
 */
public interface IBSSUserFacade {
	
	public UserVO findBSSUserById(String userId) throws SearchBLException;
	public IBDSessionContext doLogin(String username,String password,String remoteIpAddr) throws SearchBLException, AccessDeniedException;
	public Map<String, UserVO> findAllUser() throws SearchBLException;
	
}
