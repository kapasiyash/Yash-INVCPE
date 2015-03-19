package com.elitecore.cpe.core.controller;

import java.util.Map;

import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.system.user.UserVO;
import com.elitecore.cpe.core.IBDSessionContext;

public interface Controller {

	/**
	 * @param userId
	 * @return {@link UserVO}
	 * @throws SearchBLException
	 */
	public UserVO findUserById(String userId) throws SearchBLException;
	
	
	
	/**
	 * Do Login Method to create session Context
	 * @param username
	 * @param password
	 * @param remoteIpAddr
	 * @return {@link IBDSessionContext}
	 * @throws AccessDeniedException
	 * @throws SearchBLException
	 */
	public IBDSessionContext doLogin(String username,String password,String remoteIpAddr)throws AccessDeniedException,SearchBLException;
	
	
	/**
	 * Finds all User and retrieve it in a Map
	 * @return {@link Map}<{@link String,UserVO}>
	 * @throws SearchBLException
	 */
	public Map<String, UserVO> findAllUser() throws SearchBLException;

}
