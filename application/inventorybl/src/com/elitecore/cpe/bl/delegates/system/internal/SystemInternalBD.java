package com.elitecore.cpe.bl.delegates.system.internal;

import java.util.List;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleGroupData;
import com.elitecore.cpe.bl.delegates.BaseBusinessDelegate;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.system.internal.ISystemInternalFacade;
import com.elitecore.cpe.bl.facade.system.internal.SystemInternalFacadeLocal;
import com.elitecore.cpe.bl.facade.system.internal.SystemInternalFacadeRemote;
import com.elitecore.cpe.core.IBDSessionContext;



/**
 * @author Yash.Kapasi
 *
 */
public class SystemInternalBD extends BaseBusinessDelegate {

	private static ISystemInternalFacade facade;
	private static final String MODULE = "SYSTEMINTERNAL-BD";
	
	
	public SystemInternalBD(IBDSessionContext context){
		super(context);
	}
	
	
	/**
	 * Method can be Use find All SystemModuleGroup With positive Id 
	 * @param 
	 * @return List OF SystemModuleGroupData
	 * @throws  SearchBLException,TechnicalException
	 * @author Yash.Kapasi
	 */
	public List<SystemModuleGroupData> getSystemModuleData() throws SearchBLException, TechnicalException{
		if(isTraceLevel())
			logTrace(MODULE, "Inside getSystemModuleData");
		try {
			return getFacade().getSystemModuleData(getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}
	
	private ISystemInternalFacade getFacade() throws NamingException {
 		if (facade == null) {
 			if (isLocalMode()) {
 				facade = (ISystemInternalFacade)lookupLocal(SystemInternalFacadeLocal.class);
 			}else {
 				facade = (ISystemInternalFacade)lookup(SystemInternalFacadeRemote.class);
 			}
 		}
 		return facade;
 	}
	
	/**
	 * Get All System Actions
	 * @return List<ComboBoxData>
	 * @throws SearchBLException
	 */
	public List<ComboBoxData> getAllSystemAction() throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside getAllSystemAction");
		try {
			return getFacade().getAllSystemAction();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}


	/**
	 * Get All System Modules
	 * @return {@link List} <{@link ComboData}>
	 * @throws SearchBLException
	 */
	public List<ComboData> getAllSystemModules() throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside getAllSystemModules");
		try {
			return getFacade().getAllSystemModules();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}


	/**
	 * Get All System Action by Module Id
	 * @param typeId
	 * @return {@link List} <{@link ComboBoxData}>
	 * @throws SearchBLException
	 */
	public List<ComboBoxData> getAllSystemActionByModuleId(Long typeId) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside getAllSystemActionByModuleId");
		try {
			return getFacade().getAllSystemActionByModuleId(typeId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}


	/**
	 * Get All USers
	 * @return {@link List} <{@link ComboBoxData}>
	 * @throws SearchBLException
	 */
	public List<ComboBoxData> getAllUsers() throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside getAllUsers");
		try {
			return getFacade().getAllUsers();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}


	/**
	 * Find user-warehouse mapping of User
	 * @param userId
	 * @return {@link List} <{@link ComboData}>
	 * @throws SearchBLException
	 */
	public List<ComboData> findUserWareHouseMapping(String userId) throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "Inside findUserWareHouseMapping");
		try {
			return getFacade().findUserWareHouseMapping(userId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}


	/**
	 * Update user-warehouse mapping
	 * @param userId
	 * @param selectedData
	 * @throws UpdateBLException
	 */
	public void updateUserWarehouseMapping(String name,String userId,
			List<ComboData> selectedData) throws UpdateBLException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside updateUserWarehouseMapping");
		try {
			getFacade().updateUserWarehouseMapping(name,userId,selectedData,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage());
		}
	}	


}
