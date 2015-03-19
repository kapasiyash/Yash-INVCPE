package com.elitecore.cpe.bl.session.master.attribute;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.exception.ConstraintViolationException;

import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeTransData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.util.logger.Logger;
import com.sun.rowset.CachedRowSetImpl;

@Stateless
public class AttributeSessionBean extends BaseSessionBean implements AttributeSessionBeanLocal{

	private static final String MODULE = "ATTRIBUTE-SB";
	 

	/**
	 * Save Attribute
	 * @author yash.kapasi
	 * @param {@link AttributeData} attributeData
	 * @throws CreateBLException
	 */
	public void saveAttribute(AttributeData attributeData) throws CreateBLException{
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside saveAttribute");
		}
		try {
			
			getEntityManager().persist(attributeData);
			getEntityManager().flush();
			
		}catch (Exception e) {
			
			e.printStackTrace();
				
			getSessionContext().setRollbackOnly();
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("NAMEREL")){
	        			throw new CreateBLException("Attribute Name :" + attributeData.getName() +" Already Exists");
	        	   }
	    	   }
			
	    	   throw new CreateBLException("Create Attribute Failed, Reason : " + e.getMessage(), e);
		}
		
	}
	
	/**
	 * Search Attribute Data
	 * @author yash.kapasi
	 * @param {@link AttributeVO} attributeVO
	 * @return {@link List}<{@link AttributeData}> data.
	 */
	public List<AttributeData> searchAttributeData(AttributeVO attributeVO){
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside searchAttributeData");
		}
		List<AttributeData> data = null;
		try {
			
			String hql = "from AttributeData w where upper(w.systemgenerated) = 'N'";
			if(attributeVO.getName() != null && !attributeVO.getName().equalsIgnoreCase("")){
				hql +=" and upper(w.name) like upper('"+attributeVO.getName()+"%')";
			}if(attributeVO.getUsedBy() != null && !attributeVO.getUsedBy().equalsIgnoreCase("")){
				hql +=" and upper(w.usedBy) like upper('"+attributeVO.getUsedBy()+"%')";
			}
			

			
			Query query = getEntityManager().createQuery(hql);
			data = (List<AttributeData>)query.getResultList();
			
			Logger.logTrace(MODULE, data.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}

	
	/**
	 * Update Attribute Data
	 * @author yash.kapasi
	 * @param {@link AttributeData} attributeData
	 * @throws UpdateBLException
	 */
	public void updateAttribute(AttributeData attributeData) throws UpdateBLException{
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateAttribute");
		}
		
		try {
				
				getEntityManager().merge(attributeData);
				getEntityManager().flush();
			
		}catch(Exception e){
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("NAMEREL")){
	        			throw new UpdateBLException("Attribute Name :" + attributeData.getName() +" Already Exists with this Used By");
	        	   }
	    	   }
			
	    	   throw new UpdateBLException("Update Attribute Failed, Reason : " + e.getMessage(), e);
		}
		
	}

	public boolean isAttributeValueUnique(Long attributeId,String attributeValue,String tableRefName){
		
		Logger.logDebug(MODULE, "In isAttributeValueUnique ::");
		String sql = "select * from tbltattributetrans where attributeid = "+attributeId+" and value = '"+attributeValue+"' and tablerefname is "+tableRefName;
		 Query query = getEntityManager().createNativeQuery(sql);
		 List list = query.getResultList();
		 if(list != null && !list.isEmpty()){
			 return false;
		 }
		return true;
	}
	
public Boolean isAttributeExist(String name) {
		
		Logger.logDebug(MODULE, "inside  isAttributeExist");
		Boolean flag=false;
		AttributeData data=null;
		try {
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder = queryBuilder.append("from AttributeData o where o.name<>null and lower(o.name)= lower(:name) ");
			data =(AttributeData) getEntityManager().createQuery(queryBuilder.toString())
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

public Boolean isAttributeAttached(Long attributeId) {
	
	Logger.logDebug(MODULE, "inside  isAttributeAttached");
	Boolean flag=false;
	AttributeTransData data=null;
	try {
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder = queryBuilder.append("from AttributeTransData o where o.attributeId=:attributeId and tableRefName is null");
		data =(AttributeTransData) getEntityManager().createQuery(queryBuilder.toString())
				  .setParameter("attributeId", attributeId).setMaxResults(1).getSingleResult();
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


	/**
	 * Search Attribute Data by Inventory Id
	 * @author yash.kapasi
	 * @param {@link Long} id
	 * @eturn {@link List}<{@link AttributeData}> attributeDatas.
	 * @throws SearchBLException
	 */
	@Override
	public List<AttributeData> searchAttributeDataByInventoryId(Long id)
			throws SearchBLException {
		
		String query = "select o from AttributeData o where o.attributeId in (select a.attributeId from AttributeTransData a where a.referenceId=:referenceId and a.tableRefName is null )";
		
		try {
			
			List<AttributeData> attributeDatas =  getEntityManager().createQuery(query)
					.setParameter("referenceId", id).getResultList();
			
			return attributeDatas;
			
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	
	@Override
	public List<AttributeTransData> searchAttributeTransDataByInventoryId(Long id)
			throws SearchBLException {
		
		String query = "select a from AttributeTransData a where a.referenceId=:referenceId and a.tableRefName is null ";
		
		try {
			
			List<AttributeTransData> attributeDatas =  getEntityManager().createQuery(query)
					.setParameter("referenceId", id).getResultList();
			
			return attributeDatas;
			
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}
	

	@Override
	public List<Object[]> searchAttributeDataByTransferOrderId(String transferOrderNumber)
			throws SearchBLException {
		
		String query = "select unique b.* from tbltattributetrans a " +
				" inner join tblmattribute b on b.attributeid=a.attributeid " +
				" inner join tblminventory c on c.inventoryid=a.referenceid " +
				" inner join tblttransferorderdetail d on d.inventoryno=c.inventoryno " +
				" inner join tblmtransferorder e on e.transferorderid = d.transferorderid "+
				" where a.tablerefname is null and b.attributerel='Resource'  and e.transferorderno='"+transferOrderNumber+"'";
		
		try {
			
			List<Object[]> attributeDatas =  getEntityManager().createNativeQuery(query).getResultList();
			
			return attributeDatas;
			
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
		
	}

	@Override
	public List<Object[]> searchAttributeDataByResourceId(String resourceNo)
			throws SearchBLException {
		
		
		String query = "select unique b.* from tbltattributetrans a " +
				" inner join tblmattribute b on b.attributeid=a.attributeid " +
				" inner join tblminventory c on c.inventoryid=a.referenceid " +
				" inner join tblmresource d on d.resourceid = c.resourceid "+
				" where a.tablerefname is null and b.attributerel='Resource'  and d.resourcenumber='"+resourceNo+"'";
		
		try {
			
			List<Object[]> attributeDatas =  getEntityManager().createNativeQuery(query).getResultList();
			
			return attributeDatas;
			
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public List<Object[]> searchAttributeDataByBatchId(String batchId)
			throws SearchBLException {
		
		String query = "select unique b.* from tbltattributetrans a " +
				" inner join tblmattribute b on b.attributeid=a.attributeid " +
				" inner join tblminventory c on c.inventoryid=a.referenceid " +
				" inner join tblmresource d on d.resourceid = c.resourceid "+
				" inner join tblmbatchsummary e on e.resourceid=d.resourceid "+
				" inner join tblmbatch f on f.batchid=e.batchid "+
				" where a.tablerefname is null and b.attributerel='Resource'  and f.batchnumber='"+batchId+"'";
		
		try {
			
			List<Object[]> attributeDatas =  getEntityManager().createNativeQuery(query).getResultList();
			
			return attributeDatas;
			
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
		
	}
}