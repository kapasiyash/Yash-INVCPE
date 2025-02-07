package com.elitecore.cpe.bl.session.master.warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.exception.ConstraintViolationException;

import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.system.DataSourceConstant;
import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.data.system.audit.AuditSummaryDetail;
import com.elitecore.cpe.bl.entity.inventory.master.ConfigureThresholdData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseTypeData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.system.audit.AuditDataConversionUtilities;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.bl.vo.master.ConfigureThresholdVO;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;
import com.sun.rowset.CachedRowSetImpl;

@Stateless
public class WarehouseSessionBean extends BaseSessionBean implements WarehouseSessionBeanLocal{

	private static final String MODULE = "WAREHOUSE-SB";
	
	/**
	 * save Warehouse Data   
	 * @author yash.kapasi
	 * @param {@link WarehouseData} warehouseData
	 * @throws CreateBLException
	 */
	@Override
	public void saveWarehouse(WarehouseData warehouseData) throws CreateBLException{
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside saveWarehouse");
		}
		try {			
			getEntityManager().persist(warehouseData);
			getEntityManager().flush();
			
		}catch (Exception e) {
			
			e.printStackTrace();
				
			getSessionContext().setRollbackOnly();
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("ALIAS")){
	        			throw new CreateBLException("Name " + warehouseData.getName() +" Already Exists");
	        	   }else if(constraintViolationException.getConstraintName().toUpperCase().contains("WHNAME")){
	        		   throw new CreateBLException("Name " + warehouseData.getName() +" Already Exists");
	        	   }else if(constraintViolationException.getConstraintName().toUpperCase().contains("TBLMWAREHOUSE_UK_WHCODE")){
	        		   throw new CreateBLException("Warehouse Code" + warehouseData.getWarehouseCode() +" Already Exists");
	        	   }
	    	   }
			
	    	   throw new CreateBLException("Create Warehouse Failed, Reason : " + e.getMessage(), e);
		}
		
	}
	
	/**
	 * search  Warehouse Data from system    
	 * @author yash.kapasi
	 * @param {@link WarehouseVO} warehouseVO
	 * @return {@link List}<{@link WarehouseData}> data.
	 */
	@Override
	public List<WarehouseData> searchWarehouseData(WarehouseVO warehouseVO){
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside searchWarehouseData");
		}
		List<WarehouseData> data = null;
		try {
			
			String hql = "from WarehouseData w where upper(w.systemgenerated) = 'N'";
			if(warehouseVO.getName() != null && !warehouseVO.getName().equalsIgnoreCase("")){
				hql +=" and upper(w.name) like upper('"+warehouseVO.getName()+"%')";
			}
			if(warehouseVO.getWarehouseCode() != null && !warehouseVO.getWarehouseCode().equalsIgnoreCase("")){
				hql +=" and upper(w.warehouseCode) like upper('"+warehouseVO.getWarehouseCode()+"%')";
			}
			if(warehouseVO.getLocation() != null && !warehouseVO.getLocation().equalsIgnoreCase("")){
				hql +=" and upper(w.location) like upper('"+warehouseVO.getLocation()+"%')";
			}if(warehouseVO.getWarehouseTypeId() != null ){
				
				hql +=" and w.warehouseTypeData.warehouseTypeId=" +warehouseVO.getWarehouseTypeId();
			}
			hql +=" order by w.createdate desc";
			Logger.logTrace(MODULE, hql.toString());
			Query query = getEntityManager().createQuery(hql);
			data = (List<WarehouseData>)query.getResultList();
			
			Logger.logTrace(MODULE, data.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * Update  Warehouse Data in system    
	 * @author yash.kapasi
	 * @param {@link WarehouseData} warehouseData
	 * @throws UpdateBLException
	 */
	public void updateWarehouse(WarehouseData warehouseData) throws UpdateBLException{
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateWarehouse");
		}
		
		try {
				
			getEntityManager().merge(warehouseData);
			getEntityManager().flush();
			
		}catch(Exception e){
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("ALIAS")){
	        			throw new UpdateBLException("Name " + warehouseData.getName() +" Already Exists");
	        	   }
	        	   else if(constraintViolationException.getConstraintName().toUpperCase().contains("WHCODE")){
	        			throw new UpdateBLException("Warehouse Code " + warehouseData.getWarehouseCode() +" Already Exists");
	        	   }
	    	   }
			
	    	   throw new UpdateBLException("Update Warehouse Failed, Reason : " + e.getMessage(), e);
		}
		
	}
	
	/**
	 * Delete  Warehouse Data in system    
	 * @author yash.kapasi
	 * @param {@link WarehouseData} warehouseData
	 * @throws UpdateBLException
	 */
	public void deleteWarehouse(WarehouseData warehouseData) throws UpdateBLException{
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside deleteWarehouse");
		}
		
		try {
			
			warehouseData.setSystemgenerated("D");
			
			getEntityManager().merge(warehouseData);
			getEntityManager().flush();
			
		}catch(Exception e){
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
		
			throw new UpdateBLException("Delete Warehouse Failed, Reason : " + e.getMessage());
		}
	}
	
	/**
	 * Save  WarehouseType Data in system    
	 * @author yash.kapasi
	 * @param {@link WarehouseTypeData} warehouseTypeData
	 * @throws CreateBLException
	 */
	@Override
	public void saveWarehouseType(WarehouseTypeData warehouseTypeData) throws CreateBLException{
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside saveWarehouseType");
		}
		try {			
			getEntityManager().persist(warehouseTypeData);
			getEntityManager().flush();
			
		}catch (Exception e) {
			
			e.printStackTrace();
				
			getSessionContext().setRollbackOnly();
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("ALIAS")){
	        			throw new CreateBLException("Name " + warehouseTypeData.getName() +" Already Exists");
	        	   }else if(constraintViolationException.getConstraintName().toUpperCase().contains("WHNAME")){
	        		   throw new CreateBLException("Name " + warehouseTypeData.getName() +" Already Exists");
	        	   }
	    	   }
			
	    	   throw new CreateBLException("Create WarehouseType Failed, Reason : " + e.getMessage(), e);
		}
		
	}

	@Override
	public List<WarehouseTypeData> searchWarehouseTypeData(WarehouseTypeVO warehouseTypeVO){
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside searchWarehouseTypeData");
		}
		List<WarehouseTypeData> data = null;
		try {
			
			String hql = "from WarehouseTypeData w where upper(w.systemgenerated) = 'N'";
			if(warehouseTypeVO.getName() != null && !warehouseTypeVO.getName().equalsIgnoreCase("")){
				hql +=" and upper(w.name) like upper('"+warehouseTypeVO.getName()+"%')";
			}
			hql +=" order by w.createdate desc ";
			Query query = getEntityManager().createQuery(hql);
			data = (List<WarehouseTypeData>)query.getResultList();
			
			Logger.logTrace(MODULE, data.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}

	
	/**
	 * Update  WarehouseType Data in system    
	 * @author yash.kapasi
	 * @param {@link WarehouseTypeData} warehouseTypeData
	 * @throws UpdateBLException
	 */
	@Override
	public void updateWarehouseType(WarehouseTypeData warehouseTypeData) throws UpdateBLException{
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateWarehouseType");
		}
		
		try {
				
			getEntityManager().merge(warehouseTypeData);
			getEntityManager().flush();
			
		}catch(Exception e){
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("ALIAS")){
	        			throw new UpdateBLException("Name " + warehouseTypeData.getName() +" Already Exists");
	        	   }
	    	   }
			
	    	   throw new UpdateBLException("Update Warehouse Type Failed, Reason : " + e.getMessage(), e);
		}
		
	}
	
	
	/**
	 * Save Warehouse Threshold in system    
	 * @author yash.kapasi
	 * @param {@link List}<{@link ConfigureThresholdData}> configureThresholdDatas.
	 * @param {@link IBLSession}
	 * @throws CreateBLException
	 */
	public void saveThreshold(List<ConfigureThresholdData> configureThresholdDatas,IBLSession iblSession) throws CreateBLException{
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside saveThreshold");
			if(configureThresholdDatas!=null){
				Logger.logTrace(MODULE, "inside saveThreshold size of data list:"+configureThresholdDatas.size());
			}
		}
		
		
		
		try {
			
			if(configureThresholdDatas!=null && !configureThresholdDatas.isEmpty()) {
				
				for(ConfigureThresholdData thresholdData:configureThresholdDatas){
					//Audit 
					//Getting warehouse name for audit
					Logger.logTrace(MODULE, "Inside Configure threshold audit:::::");
					Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
					fieldValueMap.put("systemgenerated", "N");
					//fieldValueMap.put("warehousedata",configureThresholdData.getWarehousedata() );
					fieldValueMap.put("warehouseId", thresholdData.getWarehouseId());
					List warehousefilterList = (List<WarehouseData>)getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
					WarehouseData warehouseData = null;
					ResourceTypeData resourceTypeData = null;
					if(warehousefilterList != null && !warehousefilterList.isEmpty()){
						
						warehouseData = ((List<WarehouseData>)warehousefilterList).get(0);
					}
					//Getting Resource Type name for audit
					Map<String,Object> fieldValueMap2 = new LinkedHashMap<String, Object>();
					fieldValueMap2.put("systemgenerated", "N");
					//fieldValueMap.put("warehousedata",configureThresholdData.getWarehousedata() );
					fieldValueMap2.put("resourceTypeId", thresholdData.getResourceTypeId());
					List resourcefilterList = (List<ResourceTypeData>)getFilterDataBy(EntityConstants.RESOURCETYPE_DATA, fieldValueMap2);
					if(resourcefilterList != null && !resourcefilterList.isEmpty()){
						
						resourceTypeData = ((List<ResourceTypeData>)resourcefilterList).get(0);
					}
					if(thresholdData.getThresholdID()==null){
						if(thresholdData.getResourceTypeId()!=null) {
							getEntityManager().persist(thresholdData);
							
							//preparing entries for configure threshold audit
							Map<String,Object> mapAudit = new HashMap<String, Object>();
							if(warehouseData!=null) {
								mapAudit.put(AuditTagConstant.NAME,warehouseData.getName());
								mapAudit.put(AuditTagConstant.RESOURCE,resourceTypeData.getName());
							}
							mapAudit.put(AuditTagConstant.THRESHOLVALUE,thresholdData.getThresholdValue());
							
							addToAuditDynamicMessage(AuditConstants.CONFIGURE_THRESHOLD, "Configuring Threshold",AuditConstants.CREATE_AUDIT_TYPE, mapAudit,iblSession);
							
						}
					}
					else{
						if(thresholdData.getResourceTypeId()!=null) {
							Long  thresholdid=thresholdData.getThresholdID();
							Map<String,Object> fieldValueMap1 = new LinkedHashMap<String, Object>();
							fieldValueMap1.put("systemgenerated", "N");
							fieldValueMap1.put("thresholdID", thresholdid);
							List thresholdVoList = (List<ConfigureThresholdData>)getFilterDataBy(EntityConstants.THRESHOLD_DATA, fieldValueMap1);
							ConfigureThresholdData oldThresholdVo = new ConfigureThresholdData();;
							if(thresholdVoList != null && !thresholdVoList.isEmpty()){
								oldThresholdVo = ((List<ConfigureThresholdData>)thresholdVoList).get(0);
							}
							AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareThresholdUpdateAudit(oldThresholdVo.getThresholdValue(),thresholdData);
							getEntityManager().merge(thresholdData);
							Logger.logTrace(MODULE,":::::::old  thresholdVo.toString()"+oldThresholdVo.getThresholdValue());
							Logger.logTrace(MODULE,":::::::new  thresholdVo.toString()"+thresholdData.getThresholdValue());
							//preparing entries for configure threshold audit
							Map<String,Object> mapAudit = new HashMap<String, Object>();
							
							if(warehouseData!=null) {
								mapAudit.put(AuditTagConstant.NAME,warehouseData.getName());
								mapAudit.put(AuditTagConstant.RESOURCE,resourceTypeData.getName());
							}
							mapAudit.put(AuditTagConstant.THRESHOLVALUE,thresholdData.getThresholdValue());
							System.out.println("mapAudit :: Audit :: "+mapAudit);
							addToAuditDynamicMessage(AuditConstants.UPDATE_THRESHOLD, "Updating Threshold",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), iblSession);
							
						}
					}
				}
			}
			

/*			//Rinkal End
			List<ConfigureThresholdData> filterList=null;
//			Map <String,ConfigureThresholdData> hashMap=new LinkedHashMap<String,ConfigureThresholdData>();
			if(configureThresholdDatas!=null){
//				for(ConfigureThresholdData configureThresholdData:configureThresholdDatas){
					Logger.logTrace(MODULE, "inside First for loop in SessionBean");	
					
					
					String query = "delete from ConfigureThresholdData where systemgenerated='N' and warehouseId='"+configureThresholdDatas.get(0).getWarehouseId()+"'";
					Logger.logTrace(MODULE, "::::query::::::::"+query);	
					getEntityManager().createQuery(query).executeUpdate();
					Logger.logTrace(MODULE, "::::query executed successfully::::::::"+query);	

					Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
					fieldValueMap.put("systemgenerated", "N");
					fieldValueMap.put("warehouseId",configureThresholdDatas.get(0).getWarehouseId() );
					filterList = (List<ConfigureThresholdData>)getFilterDataBy(EntityConstants.THRESHOLD_DATA, fieldValueMap);
//					break;
//				}
			}
			
			
			
			
			if(filterList!=null && !filterList.isEmpty()){
				for(ConfigureThresholdData configureThresholdData:filterList){
					getEntityManager().detach(configureThresholdData);
				}
			}
			
			
			
			if(configureThresholdDatas!=null && !configureThresholdDatas.isEmpty()) {
				for(ConfigureThresholdData thresholdData : configureThresholdDatas) {
					if(thresholdData.getResourceTypeId()!=null) {
						getEntityManager().merge(thresholdData);
						//Audit 
						//Getting warehouse name for audit
						Logger.logTrace(MODULE, "Inside Configure threshold audit:::::");
						Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
						fieldValueMap.put("systemgenerated", "N");
						//fieldValueMap.put("warehousedata",configureThresholdData.getWarehousedata() );
						fieldValueMap.put("warehouseId", thresholdData.getWarehouseId());
						Logger.logTrace(MODULE, "Inside Configure threshold audit:::::thresholdData.getWarehouseId()"+thresholdData.getWarehouseId());
						List warehousefilterList = (List<WarehouseData>)getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
						WarehouseData warehouseData = null;
						ResourceTypeData resourceTypeData = null;
						if(warehousefilterList != null && !warehousefilterList.isEmpty()){
							
							Logger.logInfo(MODULE, "getting data in filter data ::warehousefilterList"+warehousefilterList);
							warehouseData = ((List<WarehouseData>)warehousefilterList).get(0);
						}
						Logger.logInfo(MODULE, "::::::After If");
						//Getting Resource Type name for audit
						Map<String,Object> fieldValueMap2 = new LinkedHashMap<String, Object>();
						fieldValueMap2.put("systemgenerated", "N");
						//fieldValueMap.put("warehousedata",configureThresholdData.getWarehousedata() );
						fieldValueMap2.put("resourceTypeId", thresholdData.getResourceTypeId());
						Logger.logInfo(MODULE, "::::::thresholdData.getResourceTypeId()::::::::"+thresholdData.getResourceTypeId());
						List resourcefilterList = (List<ResourceTypeData>)getFilterDataBy(EntityConstants.RESOURCETYPE_DATA, fieldValueMap2);
						if(resourcefilterList != null && !resourcefilterList.isEmpty()){
							
							Logger.logInfo(MODULE, "getting data in filter data :: resourcefilterList"+resourcefilterList);
							resourceTypeData = ((List<ResourceTypeData>)resourcefilterList).get(0);
						}
						
						//preparing entries for configure threshold audit
						Map<String,Object> mapAudit = new HashMap<String, Object>();
						if(warehouseData!=null) {
							mapAudit.put(AuditTagConstant.NAME,warehouseData.getName());
							System.out.println("::::::::::resourceTypeData::::::::::::::::"+resourceTypeData.toString());
							mapAudit.put(AuditTagConstant.RESOURCE,resourceTypeData.getName());
						}
						mapAudit.put(AuditTagConstant.THRESHOLVALUE,thresholdData.getThresholdValue());
						
						addToAuditDynamicMessage(AuditConstants.CONFIGURE_THRESHOLD, "Configuring Threshold",AuditConstants.CREATE_AUDIT_TYPE, mapAudit,iblSession);
						
					}
				}
			}
			
			if(filterList!=null && !filterList.isEmpty()){
				for(ConfigureThresholdData configureThresholdData:filterList){
					String key=configureThresholdData.getResourceTypedata().getResourceTypeId().toString()+"_";
					if(configureThresholdData.getResourceSubTypeData()!=null){
						key+=configureThresholdData.getResourceSubTypeData().getResourceSubTypeId().toString();
					}
//					hashMap.put(configureThresholdData.getResourceTypedata().getResourceTypeId().toString()+"_"+
//							configureThresholdData.getResourceSubTypeData().getResourceSubTypeId().toString()
//							, configureThresholdData);
					hashMap.put(key, configureThresholdData);
					
					
				}
				Logger.logTrace(MODULE, "Size of resource hashmap Before update:"+hashMap.size()+": Hash map:"+hashMap);
			}
			
//			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
//			fieldValueMap.put("systemgenerated", "N");
//			fieldValueMap.put("warehousedata",configureThresholdData.getWarehousedata() );
//			fieldValueMap.put("resourcedata",configureThresholdData.getResourcedata() );
//			
//			List filterList = getFilterDataBy(EntityConstants.THRESHOLD_DATA, fieldValueMap);

			if(configureThresholdDatas!=null && (!configureThresholdDatas.isEmpty()||configureThresholdDatas.size()<hashMap.size())){
				for(ConfigureThresholdData configureThresholdData:configureThresholdDatas){
					Logger.logTrace(MODULE, "Data to be update in DB-----:"+configureThresholdData);
					
					ConfigureThresholdData configureThresholdDataTarget=null;
					Logger.logTrace(MODULE, " Before configureThresholdDataTarget is:"+configureThresholdDataTarget);
					//Logic for key creation
					String key2=null;
					if(configureThresholdData.getResourceTypedata()!=null){
					 key2=configureThresholdData.getResourceTypedata().getResourceTypeId().toString()+"_";
					}
					if(configureThresholdData.getResourceSubTypeData()!=null){
						key2+=configureThresholdData.getResourceSubTypeData().getResourceSubTypeId().toString();
					}
					//Logic for key creation end
					
					if(key2!=null){
//					if(hashMap.get(configureThresholdData.getResourceTypedata().getResourceTypeId().toString()+"_"+
//							configureThresholdData.getResourceSubTypeData().getResourceSubTypeId().toString())!=null){
						
//					 configureThresholdDataTarget=hashMap.get(configureThresholdData.getResourceTypedata().getResourceTypeId().toString()+"_"+
//							configureThresholdData.getResourceSubTypeData().getResourceSubTypeId().toString());
					 
					 configureThresholdDataTarget=hashMap.get(key2);
//					}
					}
					 Logger.logTrace(MODULE, " configureThresholdDataTarget is:"+configureThresholdDataTarget);
					if(configureThresholdDataTarget !=null){
						Logger.logTrace(MODULE, "Inside configureThresholdDataTarget not null:::::");
						if(!configureThresholdDataTarget.getThresholdValue().equals(configureThresholdData.getThresholdValue())){
						AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareThresholdUpdateAudit(configureThresholdDataTarget,configureThresholdData);
						configureThresholdDataTarget.setThresholdValue(configureThresholdData.getThresholdValue());
						getEntityManager().merge(configureThresholdDataTarget);
//						hashMap.remove(configureThresholdData.getResourceTypedata().getResourceTypeId().toString()+"_"+
//								configureThresholdData.getResourceSubTypeData().getResourceSubTypeId().toString());
						hashMap.remove(key2);
						
						//Audit 
						Logger.logTrace(MODULE, "Inside update threshold audit:::::");
						Map<String,Object> mapAudit = new HashMap<String, Object>();
						mapAudit.put(AuditTagConstant.NAME,configureThresholdDataTarget.getWarehousedata().getName());
						mapAudit.put(AuditTagConstant.RESOURCE,configureThresholdDataTarget.getResourceTypedata().getName());
						mapAudit.put(AuditTagConstant.THRESHOLVALUE,configureThresholdDataTarget.getThresholdValue());
						addToAuditDynamicMessage(AuditConstants.UPDATE_THRESHOLD, "Updating Threshold",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), iblSession);
						
						
						Logger.logTrace(MODULE, "Data to be removed form hashmap:"+configureThresholdData+": Hash map in update:"+hashMap);
						}else{
							continue;
						}
					}else if(configureThresholdData.getThresholdValue()!=null){
						getEntityManager().persist(configureThresholdData);
						//Audit 
						//Getting warehouse name for audit
						Logger.logTrace(MODULE, "Inside Configure threshold audit:::::");
						Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
						fieldValueMap.put("systemgenerated", "N");
						//fieldValueMap.put("warehousedata",configureThresholdData.getWarehousedata() );
						fieldValueMap.put("warehouseId", configureThresholdData.getWarehousedata().getWarehouseId());
						List warehousefilterList = (List<WarehouseData>)getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
						WarehouseData warehouseData = null;
						ResourceTypeData resourceTypeData = null;
						if(warehousefilterList != null && !warehousefilterList.isEmpty()){
							
							Logger.logInfo(MODULE, "getting data in filter data ::");
							warehouseData = ((List<WarehouseData>)warehousefilterList).get(0);
						}
						
						//Getting Resource Type name for audit
						Map<String,Object> fieldValueMap2 = new LinkedHashMap<String, Object>();
						fieldValueMap2.put("systemgenerated", "N");
						//fieldValueMap.put("warehousedata",configureThresholdData.getWarehousedata() );
						fieldValueMap2.put("resourceTypeId", configureThresholdData.getResourceTypedata().getResourceTypeId());
						List resourcefilterList = (List<ResourceTypeData>)getFilterDataBy(EntityConstants.RESOURCETYPE_DATA, fieldValueMap2);
						if(resourcefilterList != null && !resourcefilterList.isEmpty()){
							
							Logger.logInfo(MODULE, "getting data in filter data ::");
							resourceTypeData = ((List<ResourceTypeData>)resourcefilterList).get(0);
						}
						
						//preparing entries for configure threshold audit
						Map<String,Object> mapAudit = new HashMap<String, Object>();
						if(warehouseData!=null) {
							mapAudit.put(AuditTagConstant.NAME,warehouseData.getName());
							mapAudit.put(AuditTagConstant.RESOURCE,resourceTypeData.getName());
						}
						mapAudit.put(AuditTagConstant.THRESHOLVALUE,configureThresholdData.getThresholdValue());
						
						addToAuditDynamicMessage(AuditConstants.CONFIGURE_THRESHOLD, "Configuring Threshold",AuditConstants.CREATE_AUDIT_TYPE, mapAudit,iblSession);
					}
				}
				Logger.logTrace(MODULE, "Size of resource hashmap after update:"+hashMap.size()+": Hash map:"+hashMap);
				if(!hashMap.keySet().isEmpty()){
				for(Entry<String,ConfigureThresholdData> entry : hashMap.entrySet()){
					
					Logger.logTrace(MODULE, "inside saveThreshold for delete from table");
					Logger.logTrace(MODULE, "Data to be removed form table:"+entry.getValue());
					ConfigureThresholdData configureThresholdData=entry.getValue();
					Logger.logTrace(MODULE, "inside Delete Entity section:"+configureThresholdData );
					getEntityManager().remove(entry.getValue());
					Logger.logTrace(MODULE, "inside saveThreshold for delete after getEntityManager().remove()");
					//Audit 
					Logger.logTrace(MODULE, "Inside Delete threshold audit:::::");
					Map<String,Object> mapAudit = new HashMap<String, Object>();
					mapAudit.put(AuditTagConstant.NAME,configureThresholdData.getWarehousedata().getName());
					mapAudit.put(AuditTagConstant.RESOURCE,configureThresholdData.getResourceTypedata().getName());
					mapAudit.put(AuditTagConstant.THRESHOLVALUE,configureThresholdData.getThresholdValue());
					Logger.logTrace(MODULE, "Inside Delete threshold audit:::::calling addToAuditDynamicMessage() ");
					addToAuditDynamicMessage(AuditConstants.DELETE_THRESHOLD, "Deleting Threshold",AuditConstants.DELETE_AUDIT_TYPE, mapAudit,iblSession);
					Logger.logTrace(MODULE, "Inside Delete threshold audit:::::After calling addToAuditDynamicMessage() ");
					}
				}
			}
			else{
				Logger.logTrace(MODULE, "List of Datas are empty:"+configureThresholdDatas);
			}
			
	*/		getEntityManager().flush();
			
		}catch (Exception e) {
			
			e.printStackTrace();
				
			getSessionContext().setRollbackOnly();
				
	    	   throw new CreateBLException("Create Thresholdvalue Failed, Reason : " + e.getMessage(), e);
		}
		
	}
	
	
	/**
	 * Search Warehouse Threshold in system    
	 * @author yash.kapasi
	 * @param {@link configureThresholdVO} configureThresholdVO
	 * @return {@link List}<{@link ConfigureThresholdData}> data.
	 */
	@Override
	public List<ConfigureThresholdData> searchThresholdData(ConfigureThresholdVO configureThresholdVO){
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside searchWarehouseData");
		}
		List<ConfigureThresholdData> data = null;
		try {
			WarehouseData warehouseData=new WarehouseData();
			warehouseData.setWarehouseId(configureThresholdVO.getWarehouseID());
			ItemData itemData=new ItemData();
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("warehousedata",warehouseData );
			
			
			 data  = (List<ConfigureThresholdData>)getFilterDataBy(EntityConstants.THRESHOLD_DATA, fieldValueMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	
	public CachedRowSetImpl searchThresholdStatus(){
	//	Map <String,ThersholdStatusVO> map=new LinkedHashMap<String, ThersholdStatusVO>();
		
			 Connection con = null;
			 CachedRowSetImpl cachedRowSetImpl=null;
			if (isTraceLevel()) {
				Logger.logTrace(MODULE, "inside searchThresholdStatus");
			}
			
			try{
				 
					javax.sql.DataSource ds = getDataSource(DataSourceConstant.INVENTORY_DS);

					if (ds != null) {
						con = ds.getConnection();
					}
					
					 StringBuilder query;
					 query=new StringBuilder(" select d.name as warehouse,a.WAREHOUSEID,c.name as resourcename,a.RESOURCEID,a.THRESHOLDVALUE,b.available, " 
				        		+" case when(b.available-a.THRESHOLDVALUE)<0 then (b.available-a.THRESHOLDVALUE) " 
				        		+ " else null end as total from tblmwarehousealert a  "
				        		+" inner join TBLMWAREHOUSEINVENTORYSTATUS b on a.WAREHOUSEID=b.WAREHOUSEID "
				        		+" inner join TBLMRESOURCE c on a.RESOURCEID=c.RESOURCEID "
				        		+" inner join TBLMWAREHOUSE d on a.WAREHOUSEID=d.warehouseid "
				        		+" where b.RESOURCEID=a.RESOURCEID ");
				        
					 	if(con!=null) {
					 		PreparedStatement stat =con.prepareStatement(query.toString());
					 		ResultSet rs = stat.executeQuery(); 
					 		cachedRowSetImpl=new CachedRowSetImpl();
					 		cachedRowSetImpl.populate(rs);
					 		stat.close();
					 	}
		}catch(SQLException sqlException){
			sqlException.printStackTrace();
		}
		catch(Exception e){
			Logger.logError(MODULE, "Error in searchThresholdStatus()");
			e.printStackTrace();
			
			
		}finally{
			try {
				if(con!=null)
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Logger.logError(MODULE, "Error in closing connection");
				e.printStackTrace();
			}
		}
		
		
		
		return cachedRowSetImpl;
		
	
	}

	
	@Override
	public ConfigureThresholdData findThresholdValue(Long thresholdId) throws SearchBLException {

		try {
			ConfigureThresholdData data = null;
			String query = "select o from ConfigureThresholdData o where o.systemgenerated = 'N' and o.thresholdID=:thresholdID ";
			
			
		/*	if(resourceSubTypeId!=null) {
				query = query + " and o.resourceSubTypeId='"+resourceSubTypeId+"' ";
			}
			
			if(resourceTypeId!=null) {
				query = query + " and o.resourceTypeId='"+resourceTypeId+"' ";
			}
			
			if(resourceId!=null) {
				query = query + " and o.itemId='"+resourceId+"' ";
			}*/
			
			Logger.logTrace(MODULE, query);
			data = (ConfigureThresholdData) getEntityManager().createQuery(query).setParameter("thresholdID", thresholdId).getSingleResult();
			
			/*try {
				
				if(resourceSubTypeId!=null) {
					
					query = query + " and o.resourceSubTypeId='"+resourceSubTypeId+"' ";

					data =(ConfigureThresholdData) getEntityManager().createNamedQuery("ConfigureThresholdData.findByThreeId")
							.setParameter("warehouseId",wareHouseId)
							.setParameter("resourceTypeId",resourceTypeId)
							.setParameter("resourceSubTypeId",resourceSubTypeId)
							.getSingleResult();
				}
				
			} catch (NoResultException e) {
				e.printStackTrace();
			}
		 
			if(data==null) {
				 data =(ConfigureThresholdData) getEntityManager().createNamedQuery("ConfigureThresholdData.findByTwoId")
						 .setParameter("warehouseId",wareHouseId)
						 .setParameter("resourceTypeId",resourceTypeId)
						 .getSingleResult();
			}*/
			
			
		 return data;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in viewResourceSubType Reason" +e.getMessage());
			throw new SearchBLException(" viewResourceSubType operation failed, reason: " + e.getMessage(), e);
		}
	}
	public CachedRowSetImpl getAllResourceTypeWithResource (Long warehouseid){
		 Connection con = null;
		 ResultSet rs = null;
		 CachedRowSetImpl cachedRowSetImpl=null;
		if (isTraceLevel()) {
			Logger.logTrace(MODULE, "inside getAllResourceTypeWithResource");
		}
		if(warehouseid == null){
			return null;
		}
		try{
				javax.sql.DataSource ds = getDataSource(DataSourceConstant.INVENTORY_DS);

				if (ds != null) {
					con = ds.getConnection();
				}
				
		StringBuilder query=new StringBuilder("select * from tblsresourcetype where resourcetypeid  " 
				+" in(select resourcetypeid from tblmresource  "
				+" where resourceid in (select distinct(resourceid) from tblminventory " 
				+" where warehouseid= " + warehouseid 
				+" and statusid=(select inventorystatusid from tblsinventorystatus  " 
				+" where alias='AVAILABLE'))" 
				+")");
		Logger.logTrace(MODULE, "inside getAllResourceTypeWithResource query:"+query.toString());
		if(con!=null) {
			PreparedStatement stat =con.prepareStatement(query.toString());
			rs = stat.executeQuery(); 
			cachedRowSetImpl=new CachedRowSetImpl();
			cachedRowSetImpl.populate(rs);
			stat.close();
		}
		}catch(SQLException sqlException){
			sqlException.printStackTrace();
		}
		catch(Exception e){
			Logger.logError(MODULE, "Error in searchThresholdStatus()");
			e.printStackTrace();
			
			
		}finally{
			try {
				if(con!=null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Logger.logError(MODULE, "Error in closing connection");
				e.printStackTrace();
			}
		}
		
		
		
		return cachedRowSetImpl;
	}
	public Boolean isWarehouseExist(String name) {
		
		Logger.logDebug(MODULE, "inside  isWarehouseExist");
		Boolean flag=false;
		WarehouseData data=null;
		try {
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder = queryBuilder.append("from WarehouseData o where o.name<>null and lower(o.name)= lower(:name) ");
			data =(WarehouseData) getEntityManager().createQuery(queryBuilder.toString())
					  .setParameter("name", name).setMaxResults(1).getSingleResult();
				if(data!=null){
					flag=true;
				}
			
		 
		
		}catch(NoResultException e) {
			return flag;
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return flag;
		
	}
public Boolean isWarehouseTypeExist(String name) {
		
		Logger.logDebug(MODULE, "inside  isWarehouseTypeExist");
		Boolean flag=false;
		WarehouseTypeData data=null;
		try {
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder = queryBuilder.append("from WarehouseTypeData o where o.name<>null and lower(o.name)= lower(:name) ");
			data =(WarehouseTypeData) getEntityManager().createQuery(queryBuilder.toString())
					  .setParameter("name", name).setMaxResults(1).getSingleResult();
				if(data!=null){
					flag=true;
				}
			
		 
		
		}catch(NoResultException e) {
			return flag;
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return flag;
		
	}

	@Override
	public List checkInventoryOnSerialNumber(String serialNumber) {
		
		try {
		
			String query = "select  distinct a.referenceid  from tbltattributetrans a " +
					" inner join tblminventory b on b.inventoryid= a.referenceid " +
					" inner join tblmattribute c on c.attributeid=a.attributeid " +
					" where c.name='Serial Number' and a.value='"+serialNumber+"'";
			
			 List result = getEntityManager().createNativeQuery(query).getResultList();
		
			 return result;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		
	}

	@Override
	public List searchQuery(String query) throws SearchBLException {
	
		try {
			
			return getEntityManager().createQuery(query).getResultList();
			
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			return null;
		}
		
	}

	@Override
	public List<WarehouseData> findAllCentralWareHouses()
			throws SearchBLException {
		
		try {
			return getEntityManager().createNamedQuery("WarehouseData.findAllCentralTypeWH").getResultList();
			
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			return null;
		}
		
	}

	@Override
	public List<WarehouseData> findChildWareHouses(Long warehouseId)
			throws SearchBLException {

		try {
			return getEntityManager().createNamedQuery("WarehouseData.findChildWH")
					.setParameter("parentWarehouseId", warehouseId)
					.getResultList();
			
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public ConfigureThresholdData findConfigureThresholdDataByThresholdID(Long thresholdID) throws SearchBLException {

		try {
			ConfigureThresholdData data = null;
			data =(ConfigureThresholdData) getEntityManager().createNamedQuery("ConfigureThresholdData.findConfigureThresholdDataByThresholdID")
				 .setParameter("thresholdID",thresholdID)				
				 .getSingleResult();
		 return data;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in viewResourceSubType Reason" +e.getMessage());
			throw new SearchBLException(" viewResourceSubType operation failed, reason: " + e.getMessage(), e);
		}
	}
	
	@Override
	public void deleteThreshold(ConfigureThresholdData thresholdData,IBLSession iblSession)throws UpdateBLException {
		try{
		//	String query = "delete from ConfigureThresholdData where systemgenerated='N' and thresholdID='"+thresholdData.getThresholdID()+"'";
		//	Logger.logTrace(MODULE, "::::query::::::::"+query);	
			Logger.logTrace(MODULE, "::::in Session-Bean ::::::::"+thresholdData.getThresholdID());	
			getEntityManager().remove(thresholdData);
			getEntityManager().flush();
			Logger.logTrace(MODULE, "::::flush successfully::::::::");	
		Map<String,Object> mapAudit = new HashMap<String, Object>();
			if( (thresholdData.getWarehousedata()!=null) && (thresholdData.getResourceTypedata()!=null) ){
				mapAudit.put(AuditTagConstant.RESOURCE,thresholdData.getResourceTypedata().getName());
				mapAudit.put(AuditTagConstant.THRESHOLVALUE,thresholdData.getThresholdValue());
				mapAudit.put(AuditTagConstant.NAME,thresholdData.getWarehousedata().getName());
				addToAuditDynamicMessage(AuditConstants.DELETE_THRESHOLD, "Delete Threshold",AuditConstants.DELETE_AUDIT_TYPE, mapAudit,iblSession);
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
	    	   throw new UpdateBLException("Delete Thresholdvalue Failed, Reason : " + e.getMessage(), e);

		}
		
	}
	
}