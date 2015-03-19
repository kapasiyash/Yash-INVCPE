package com.elitecore.cpe.bl.delegates.system.systemparameter;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.data.system.systemparameter.SystemParameterGroupWrapperData;
import com.elitecore.cpe.bl.delegates.BaseBusinessDelegate;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.system.systemparameter.ISystemParameterFacade;
import com.elitecore.cpe.bl.facade.system.systemparameter.SystemParameterFacadeLocal;
import com.elitecore.cpe.bl.facade.system.systemparameter.SystemParameterFacadeRemote;
import com.elitecore.cpe.core.IBDSessionContext;




public class SystemParameterBD extends BaseBusinessDelegate {

	private ISystemParameterFacade facade;
	private static final String MODULE="SystemParamterBD";
	
	
	public SystemParameterBD(IBDSessionContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	

	public SystemParameterBD() {
		super(null);
	}

	private ISystemParameterFacade getFacade() throws NamingException {
 		if (facade == null) {
 			if (isLocalMode()) {
 				facade = (ISystemParameterFacade)lookupLocal(SystemParameterFacadeLocal.class);
 			}else {
 				facade = (ISystemParameterFacade)lookup(SystemParameterFacadeRemote.class);
 			}
 		}
 		return facade;
 	}
	
	/**
	 * Get A;; System Parameter Value
	 * @return {@link Map} <{@link String,String}>
	 * @throws SearchBLException
	 * @throws TechnicalException
	 */
	public Map<String,String> getSystemParamterValue() throws SearchBLException,TechnicalException{
		if (isDebugLevel())
			logDebug(MODULE, "In SystemParameterBD getSystemParamterValue() method");
		try {
			return getFacade().getAllSystemParameterValue();
		}  catch (NamingException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * Find all System Parameter Groups
	 * @return {@link List} <{@link SystemParameterGroupWrapperData}>
	 * @throws SearchBLException
	 * @throws TechnicalException
	 */
	public List<SystemParameterGroupWrapperData> findAllSystemParameterGroups() throws SearchBLException,TechnicalException {
		if (isDebugLevel())
			logDebug(MODULE, "In SystemParameterBD getSystemParamterValue() method");
		try {
			return getFacade().findAllSystemParameterGroups();
		}  catch (NamingException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * Update System Parameters
	 * @param systemParameterGroupWrapperDatas
	 * @throws SearchBLException
	 * @throws TechnicalException
	 * @throws UpdateBLException
	 */
	public void updateSystemParameters(List<SystemParameterGroupWrapperData> systemParameterGroupWrapperDatas) throws SearchBLException,TechnicalException, UpdateBLException {
		if (isDebugLevel())
			logDebug(MODULE, "In SystemParameterBD getSystemParamterValue() method");
		try {
			getFacade().updateSystemParameters(systemParameterGroupWrapperDatas,getBLSession());
		}  catch (NamingException e) {
			throw new TechnicalException(e);
		}
	}
	
}
