package com.elitecore.cpe.bl.session.system.internal;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.entity.inventory.bss.user.BSSUser;
import com.elitecore.cpe.bl.entity.inventory.core.PrimaryKey;
import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemAction;
import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemModule;
import com.elitecore.cpe.bl.entity.inventory.system.userwarehouse.UserWarehouseMapping;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.util.logger.Logger;

/**
 * 
 * @author Yash.Kapasi
 *
 */
@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class SystemInternalSessionBean extends BaseSessionBean implements SystemInternalSessionBeanLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MODULE = "SYSTEM_ACTION_SB";
	
	
	/**
	 * Method will find primary key for unique key generation.
	 * @param String alias 
	 * @return String key
	 * @throws  SearchBLException
	 * @author Yash.Kapasi
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	@Override
	public final String getPrimaryKey(String alias) throws SearchBLException {
		StringBuffer newPK = null;
		 try{
			PrimaryKey primaryKey = (PrimaryKey) getEntityManager().createNamedQuery("PrimaryKey.findByAlias").setParameter("alias", alias)
					.getSingleResult();
			getEntityManager().lock(primaryKey, LockModeType.PESSIMISTIC_READ);
			getEntityManager().refresh(primaryKey);
			String currentValue = primaryKey.getCurrentValue().toString();
			String formattedValue = String.format("%"+primaryKey.getLength()+"s", currentValue).replace(' ', '0');		
			newPK = new StringBuffer();
			if(primaryKey.getPreFix()!=null && !primaryKey.getPreFix().equals("")){
				newPK.append(primaryKey.getPreFix());	
			}		
			newPK.append(formattedValue);
			if(primaryKey.getPostFix()!=null && !primaryKey.getPostFix().equals("")){
				newPK.append(primaryKey.getPostFix() );
			}
			
			//Persisting Primary Key
			primaryKey.setCurrentValue(primaryKey.getCurrentValue()+1);
			getEntityManager().merge(primaryKey);
			getEntityManager().flush();
			getEntityManager().lock(primaryKey, LockModeType.NONE);
		 }catch(NoResultException e){
			 if(isTraceLevel())
				 logError(MODULE, "Error in getPrimaryKey, " + e.getMessage());
			 return null;
		 }		
		 return newPK.toString();
		}
	

	
	/**
	 * Method Use find All SystemModuleGroup With positive Id 
	 * @param 
	 * @return List OF SystemModuleGroup
	 * @throws  SearchBLException
	 * @author Yash.Kapasi
	 */	
	/*@SuppressWarnings("unchecked")
	@Override
	public List<SystemModuleGroup> findAllSystemModuleGroup()
			throws SearchBLException {
		if(isTraceLevel())
				logTrace(MODULE, "Inside findAllSystemModuleGroup");
		try{
			return (List<SystemModuleGroup>) getEntityManager().createNamedQuery("SystemModuleGroup.findAll").getResultList();
		}catch (Exception e) {
			if(isErrorLevel())
				logError(MODULE, "Search Operation Failed : Reason :- " +e.getMessage());
			throw new SearchBLException("Search SystemModuleGroup Fail");
		}
	}*/

	/**
	 * Find {@link SystemAction} by Id
	 * @author Yash.Kapasi
	 * @param actionId
	 * @return {@link SystemAction}
	 * @throws SearchBLException
	 */
	@Override
	public SystemAction findSystemActionById(Long actionId) throws SearchBLException{
		if(isTraceLevel()){
			logTrace(MODULE, "inside findSystemActionById : actionId " + actionId);
		}
		try{
			return (SystemAction) getEntityManager().createNamedQuery("SystemAction.findById").setParameter("actionId", actionId).getSingleResult();
		}catch (Exception e) {
			if(isErrorLevel())
				logError(MODULE, "Search Operation Failed : Reason :- " +e.getMessage());
			throw new SearchBLException("Search SystemAction Fail");
		}
	}

	@Override
	public SystemAction findSystemActionByAlias(String actionAlias) throws SearchBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside findSystemActionByAlias : actionAlias " + actionAlias);
		}
		try{
			return (SystemAction) getEntityManager().createNamedQuery("SystemAction.findByAlias").setParameter("actionAlias", actionAlias).getSingleResult();
		}catch(NoResultException e){
			return null;
		}catch (Exception e) {
			if(isErrorLevel())
				logError(MODULE, "Search Operation Failed : Reason :- " +e.getMessage());
			throw new SearchBLException("Search SystemAction Fail");
		}
	}

	@Override
	public void updateSystemAction(SystemAction systemAction)
			throws UpdateBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside updateSystemAction ");
		}
		try{
			getEntityManager().merge(systemAction);
		}catch (Exception e) {
			if(isErrorLevel())
				logError(MODULE, "Update Operation Failed : Reason :- " +e.getMessage());
			throw new UpdateBLException("Update SystemAction Fail possible reason :" +e.getMessage());
		}
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<SystemModule> findAllSystemModules() throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "Inside findAllSystemModules");
	try{
		return (List<SystemModule>) getEntityManager().createNamedQuery("SystemModule.findAll").getResultList();
	}catch (Exception e) {
		if(isErrorLevel())
			logError(MODULE, "Search Operation Failed : Reason :- " +e.getMessage());
		throw new SearchBLException("Search SystemModuleGroup Fail");
	}
	}
	
	
	@Override
	public List<SystemAction> findAllSystemAction() throws SearchBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside findAllSystemAction ");
		}
		try{
			return (List<SystemAction>) getEntityManager().createNamedQuery("SystemAction.findAll").getResultList();
		}catch(NoResultException e){
			return null;
		}catch (Exception e) {
			if(isErrorLevel())
				logError(MODULE, "Search Operation Failed : Reason :- " +e.getMessage());
			throw new SearchBLException("Search SystemAction Fail");
		}
	}



	@Override
	public SystemModule findSystemModuleById(Long typeId)
			throws SearchBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside findSystemModuleById : moduleId " + typeId);
		}
		try{
			return (SystemModule) getEntityManager().createNamedQuery("SystemModule.findById").setParameter("moduleId", typeId).getSingleResult();
		}catch (Exception e) {
			if(isErrorLevel())
				logError(MODULE, "Search Operation Failed : Reason :- " +e.getMessage());
			throw new SearchBLException("Search findSystemModuleById Fail");
		}
	}
	
	@Override
	public boolean isPrefixAvailable(String prefix){
		
		boolean isAvailable = false;
		try {
			
			Query que = getEntityManager().createQuery("from PrimaryKey where upper(preFix) =:preFix and alias like 'RS_%' ");
			que.setParameter("preFix", prefix.toUpperCase().trim());
			
			
			List list = que.getResultList();
			if(list != null && !list.isEmpty()){
				
				isAvailable = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isAvailable;
	}
	
	@Override
	public String getAliasByPrefix(String prefix) throws SearchBLException{
		String alias = null;
		
		logDebug(MODULE, "inside getAliasByPrefix : prefix " + prefix);
		
		try {
			Query que = getEntityManager().createQuery("from PrimaryKey where upper(preFix) =:preFix and alias like 'RS_%' ");
			que.setParameter("preFix", prefix.toUpperCase().trim());
			
			
			List list = que.getResultList();
			logDebug(MODULE, "inside getAliasByPrefix : list : " + list);
			if(list != null && !list.isEmpty()){
				PrimaryKey primaryKey = (PrimaryKey)list.get(0);
				alias = primaryKey.getAlias();
			}else{
				throw new SearchBLException("Resource Inventory No prefix not found");
			}
		}catch(SearchBLException ex){
			ex.printStackTrace();
			throw ex;
		}catch (Exception e) {
			e.printStackTrace();
			throw new SearchBLException("Resource Inventory No prefix not found");
		}
		return alias;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<BSSUser> findAllUsers() throws SearchBLException {
		
		if(isTraceLevel()){
			logTrace(MODULE, "inside findAllUsers ");
		}
		try{
			return (List<BSSUser>) getEntityManager().createNamedQuery("BSSUser.findAll").getResultList();
		}catch(NoResultException e){
			return null;
		}catch (Exception e) {
			if(isErrorLevel())
				logError(MODULE, "Search Operation Failed : Reason :- " +e.getMessage());
			throw new SearchBLException("Search All Users Fail");
		}
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<UserWarehouseMapping> findUserWareHouseMapping(String userId)
			throws SearchBLException {
		
		if(isTraceLevel()){
			logTrace(MODULE, "inside findUserWareHouseMapping ");
		}
		try{
			return (List<UserWarehouseMapping>) getEntityManager().createNamedQuery("UserWarehouseMapping.findByUserId")
					.setParameter("userId", userId)
					.getResultList();
		}catch(NoResultException e){
			return null;
		}catch (Exception e) {
			if(isErrorLevel())
				logError(MODULE, "Search Operation Failed : Reason :- " +e.getMessage());
			throw new SearchBLException("Search All User-Warehouse Mapping Fail");
		}
	}



	@Override
	public void deleteUserWareHouseMapping(String userId)
			throws UpdateBLException {

		if(isTraceLevel()){
			logTrace(MODULE, "inside deleteUserWareHouseMapping ");
		}
		
		try{
			
			getEntityManager().createQuery("delete from UserWarehouseMapping o where o.userId=:userId")
			.setParameter("userId", userId).executeUpdate();
			
		}catch(NoResultException e){
		}catch (Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "delete Operation Failed : Reason :- " +e.getMessage());
			throw new UpdateBLException("delete All User-Warehouse Mapping Fail");
		}
		
	}



	@Override
	public void persistUserWarehouseMapping(UserWarehouseMapping mapping)
			throws CreateBLException {
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside persistUserWarehouseMapping");
		}
		try {			
			getEntityManager().persist(mapping);
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning persistUserWarehouseMapping");
			}
			
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in persistUserWarehouseMapping Reason" +e.getMessage());
			getSessionContext().setRollbackOnly();
			throw new CreateBLException("Create UserWarehouseMapping  Operation Failed, Reason : " + e.getMessage(), e);
		}
		
		
	}
}

