package com.elitecore.cpe.bl.session.system.systemparameter;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;

import com.elitecore.cpe.bl.entity.inventory.system.systemparameter.SystemParameter;
import com.elitecore.cpe.bl.entity.inventory.system.systemparameter.SystemParameterGroup;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.util.logger.Logger;


/**
 * 
 * @author Yash.Kapasi
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SystemParameterSessionBean extends BaseSessionBean implements	SystemParameterSessionBeanLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MODULE = "SYSTEM-PARAMETER-SB";

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemParameter> findAllSystemParameter()
			throws SearchBLException {
		if (isTraceLevel())
			logTrace(MODULE, "Inside findAllSystemParameterGroup");
		try {
			return (List<SystemParameter>) getEntityManager().createNamedQuery(
					"SystemParameter.findAllSystemParameter").getResultList();
		} catch (Exception e) {
			if (isErrorLevel())
				logError(MODULE,
						"Search Operation Failed : Reason :- " + e.getMessage());
			throw new SearchBLException("Search SystemModuleGroup Fail");

		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemParameterGroup> findAllSystemParameterGroup() throws SearchBLException {	
		
		if (isTraceLevel())
			logTrace(MODULE, "Inside findAllSystemParameterGroup");
		
		try {
			return (List<SystemParameterGroup>) getEntityManager().createNamedQuery("SystemParameterGroup.findAllSystemParameterGroup").getResultList();
		} catch (Exception e) {
			if (isErrorLevel())
				logError(MODULE,
						"Search Operation Failed : Reason :- " + e.getMessage());
			throw new SearchBLException("Search SystemModuleGroup Fail");

		}	
	}
	
	@Override
	public SystemParameter findSystemParameterById(Long systemParameterId) throws SearchBLException {
		if (isTraceLevel())
			logTrace(MODULE, "Inside findSystemParameterById");
		try {
			return getEntityManager().find(SystemParameter.class, systemParameterId);
		} catch (Exception e) {
			if (isErrorLevel())
				logError(MODULE,"Search Operation Failed : Reason :- " + e.getMessage());
			throw new SearchBLException("Search system parameter operation Failed");

		}			
	}

	@Override
	public SystemParameter updateSystemParameter(SystemParameter systemParameter) throws SearchBLException {
		if (isTraceLevel())
			logTrace(MODULE, "Inside findSystemParameterById");
		try {
			return getEntityManager().merge(systemParameter);
		} catch (Exception e) {
			if (isErrorLevel())
				logError(MODULE,"Search Operation Failed : Reason :- " + e.getMessage());
			throw new SearchBLException("Search system parameter operation Failed");

		}			
	}

	/**
	 * Find value source for given sqlquery. Sql query must be in standard and must return minimum two value i.e string id and string name. 
	 * @author Yash.Kapasi
	 * @param String sqlQuery
	 * 
	 * */
	@Override
	public List<Object[]> findValueSource(String sqlQuery) throws SearchBLException{
		Logger.logTrace(MODULE, "inside findValueSource()=" +sqlQuery);
		try {
			@SuppressWarnings("unchecked")
			List<Object[]> objectList =  getEntityManager().createNativeQuery(sqlQuery).getResultList();
			return objectList;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			Logger.logTrace(MODULE, e);
			throw new SearchBLException("Search operation failed. reason : " + e.getMessage());
		}
	}

	@Override
	public SystemParameter getSystemParameter(String systemParameterAlias)
			throws SearchBLException {
		
		if (isTraceLevel())
			logTrace(MODULE, "Inside getSystemParameter");
		try {
			return  (SystemParameter) getEntityManager().createNamedQuery(
					"SystemParameter.findSystemParameterByAlias").setParameter("alias", systemParameterAlias)
					.getSingleResult();
		
			
		} catch(NoResultException e) {
			return null;
		} catch (Exception e) {
			if (isErrorLevel())
				logError(MODULE,
						"Search Operation Failed : Reason :- " + e.getMessage());
			throw new SearchBLException("Search getSystemParameter Fail");

		}
		
	}
	
}
