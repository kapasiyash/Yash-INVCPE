package com.elitecore.cpe.bl.session;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.core.base.BaseBL;
import com.elitecore.cpe.util.logger.Logger;


/**
 * 
 * @author yash.kapasi
 */
public abstract class BaseSessionBean extends BaseBL {

	@PersistenceContext(unitName="CrestelARPU")
	private EntityManager entityManager;
	private static final String MODULE = "BASE-SB";
	
	@Resource
	private SessionContext sessionContext;
	

	
	
	
	protected final EntityManager getEntityManager() {
		return entityManager;
	}
	
	

	protected final SessionContext getSessionContext() {
		return sessionContext;
	}
		
	
	protected static final String formatForLikeSearch(String field) {
		if (field == null || field.trim().length() == 0) {
			field = "%";
		}else {
			field = "%" + field.trim() + "%";
		}
		
		return field;
	}
	
	protected static final String formatForUpperLikeSearch(String field) {
		if (field == null || field.equals("") || field.trim().length() == 0) {
			field = "%";
		}else {
			field = "%" + field.trim().toUpperCase() + "%";
		}
		
		return field;
	}
	
	protected static final String formatFieldForLikePercentAfter(String field) {
		if (field == null || field.trim().length() == 0 || field.equals("")) {
			field = "%";
		}else {
			field = field.trim() + "%";
		}
		return field;
	}
	
	protected static final String formatFieldForUpperLikePercentAfter(String field) {
		if (field == null || field.trim().length() == 0) {
			field = "%";
		}else {
			field = field.trim().toUpperCase() + "%";
		}
		return field;
	}
		
	protected final Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}
	
	public String getDateFormat(){
		return "dd-MMM-yyyy";
	}
	
	public String getDateTimeFormat(){
		return "dd-MMM-yyyy hh:mm:ss";
	}
	
	public String getDateTimeFormat24(){
		return "dd-MMM-yyyy HH:mm:ss";
	}
	
	
	public Object updateEntity(Object entity) throws UpdateBLException {
		try {
			
			Object obj = getEntityManager().merge(entity);
			getEntityManager().flush();
			
			return obj;
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage());
		}
		
	}
	
	
	public List getFilterDataBy(String entityName,Map<String,Object> fieldValueMap){
		
		List list = null;
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside getFilterDataBy"); 
		}
		try {
			
			StringBuilder hql = new StringBuilder();
			hql.append("from ").append(entityName);
			
			if(null != fieldValueMap  && !fieldValueMap.isEmpty()){
				hql.append( " where ");
				int counter = 0;
				
				for(String fieldName : fieldValueMap.keySet()){
					if(fieldValueMap.keySet().size()-1 ==  counter){
						hql.append(fieldName).append(" = :").append(fieldName);
					}else{
						hql.append( fieldName).append(" = :").append(fieldName).append( " and ");
					}
					counter++;
				}
			
			
//			Logger.logTrace(MODULE, "Final HQL :"+hql); 
			
				Query query = getEntityManager().createQuery(hql.toString());
				for(Entry<String, Object> entrykey : fieldValueMap.entrySet()){
					query.setParameter(entrykey.getKey(), entrykey.getValue());
				}
				list = query.getResultList();
			}		
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
