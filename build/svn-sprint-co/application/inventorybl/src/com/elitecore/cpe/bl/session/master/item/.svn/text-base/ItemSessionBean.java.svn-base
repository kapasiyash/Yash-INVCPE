package com.elitecore.cpe.bl.session.master.item;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.exception.ConstraintViolationException;

import com.elitecore.cpe.bl.entity.inventory.core.PrimaryKey;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusTransition;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.ResourceAttributeRel;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceSubTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceTypeData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.bl.session.system.internal.SystemInternalSessionBeanLocal;
import com.elitecore.cpe.bl.vo.inventorytransfer.CheckInventoryVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.util.logger.Logger;

@Stateless
public class ItemSessionBean extends BaseSessionBean implements ItemSessionBeanLocal{

	private static final String MODULE = "ITEM-SB";
	
	@EJB private SystemInternalSessionBeanLocal systemInternalSessionBeanLocal;
	
	@Override
	public ItemData createItem(ItemData itemdata) throws CreateBLException {
		
		PrimaryKey primaryKey= null;
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside item data");
		}
		try {			
			System.out.println("ReferenceID:="+itemdata.getReferenceID());
		//	System.out.println("ItemID:="+itemdata.getItemId());
			if(itemdata.getPrefix()!=null) {
				
				 primaryKey = new PrimaryKey();
				primaryKey.setAlias(itemdata.getResourceNumber());
				primaryKey.setPreFix(itemdata.getPrefix().trim());
			}
			getEntityManager().persist(itemdata);
			getEntityManager().flush();
			getEntityManager().refresh(itemdata);
			
			if(primaryKey!=null && primaryKey.getPreFix()!=null) {
				boolean isaAvailable = systemInternalSessionBeanLocal.isPrefixAvailable(primaryKey.getPreFix().trim());
				if(!isaAvailable){
					savePrimaryKey(primaryKey);
				}
			}
		}catch (Exception e) {
			
			e.printStackTrace();
				
			//getSessionContext().setRollbackOnly();
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   System.out.println("Name or reference id is not unique:="+itemdata.getReferenceID());
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("TBLMITEM_NAME")){
	        			throw new CreateBLException("Name " + itemdata.getName() +" Already Exists");
	        	   }
	        	   else if(constraintViolationException.getConstraintName().toUpperCase().contains("UK_REFERENCEID")){
	        			throw new CreateBLException("ReferenceID " + itemdata.getReferenceID() +" Already Exists");
	        			
	        	   }else if(constraintViolationException.getConstraintName().toUpperCase().contains("UK_PREFIX")){
	        			throw new CreateBLException("Prefix " + itemdata.getPrefix() +" Already Exists");
	        	   }
	    	   }
	    	   throw new CreateBLException("Create Item Failed, Reason : " + e.getMessage(), e);
		}
		return itemdata;
		
	}

	@Override
	public List<ItemData> searchItemData(ItemVO itemVo)
	{
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside searchItemData");
		}
		List<ItemData> data = null;
		try {
//			ResourceTypeData resourceTypeData = null;
			
			String querySQL = "select o from ItemData o  where o.systemgenerated='N' ";
			
			if(itemVo.getName() != null && !itemVo.getName().isEmpty()){
				querySQL += " and upper(o.name) like '"+formatForUpperLikeSearch(itemVo.getName())+"'";
			}
			
			if(itemVo.getReferenceID() != null && !itemVo.getReferenceID().isEmpty()){
				querySQL += " and upper(o.referenceID) like '"+formatForUpperLikeSearch(itemVo.getReferenceID())+"'";
			}
			
			if(itemVo.getResourceNumber() != null && !itemVo.getResourceNumber().isEmpty()){
				querySQL += " and upper(o.resourceNumber) like '"+formatForUpperLikeSearch(itemVo.getResourceNumber())+"'";
			}
			
			if(itemVo.getResourceTypeVO() !=null){
				if(itemVo.getResourceTypeVO().getResourceTypeId()!=null) {
					querySQL += " and o.resourceTypeId = '"+itemVo.getResourceTypeVO().getResourceTypeId()+"'";
				}
				if(itemVo.getResourceTypeVO().getResourceSubTypeVO()!=null && itemVo.getResourceTypeVO().getResourceSubTypeVO().getResourceSubTypeId()!=null) {
					querySQL += " and o.resourceSubTypeId = '"+itemVo.getResourceTypeVO().getResourceSubTypeVO().getResourceSubTypeId()+"'";
				}
			}
			
			/*String hql = "from ItemData w where upper(w.systemgenerated) = 'N'";
			if(itemVo.getName() != null && !itemVo.getName().equalsIgnoreCase("")){
				hql +=" and upper(w.name) like upper('%"+itemVo.getName()+"%')";
			}if(itemVo.getReferenceID() != null && !itemVo.getReferenceID().equalsIgnoreCase("")){
				hql +=" and upper(w.referenceID) like upper('%"+itemVo.getReferenceID()+"%')";
			}if(itemVo.getResourceTypeVO() !=null){
				resourceTypeData = new ResourceTypeData();
				resourceTypeData.setResourceTypeId(itemVo.getResourceTypeVO().getResourceTypeId());
				hql +=" and resoureceType = :resoureceType";
			}
			
			Query query = getEntityManager().createQuery(hql);
			System.out.println("Search Item Query :-"+query);
			if(resourceTypeData != null){
				query.setParameter("resoureceType", resourceTypeData);
			}*/
			querySQL +=" ORDER BY o.createdate DESC";
			Query query = getEntityManager().createQuery(querySQL);
			
			data = (List<ItemData>)query.getResultList();
			
			Logger.logTrace(MODULE, data.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}

	@Override
	public ItemData viewItem(ItemVO itemVo) {
		
		ItemData itemData = null;
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside viewItem");
			Logger.logTrace(MODULE, "itemId ::"+itemVo.getItemId());
		}
		try {
			Query query = getEntityManager().createQuery("from ItemData w where w.itemId = :Id and upper(w.systemgenerated) = 'N'");
			query.setParameter("Id", itemVo.getItemId());
			List<ItemData> data = (List<ItemData>)query.getResultList();
			System.out.println("JM data from DB ViewItem:"+data);
			if(data != null && !data.isEmpty()){
				itemData = data.get(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return itemData;
	}

	@Override
	public void updateItem(ItemData itemData) throws UpdateBLException {
		// TODO Auto-generated method stub
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateItem");
			
		}
		try {
			if(itemData!=null){
		/*	ItemData newItemData = viewItem(itemVo) ;
		//	ResourceTypeData resourceTypeData=
			if(newItemData != null){
				Logger.logTrace(MODULE, "itemVo.getResourceTypeVO().getResourceTypeId() : "+itemVo.getResourceTypeVO().getResourceTypeId());
				
				ResourceTypeData resourceTypeData=newItemData.getResoureceType();
				ResourceTypeData typeData = new ResourceTypeData();
				typeData.setResourceTypeId(itemVo.getResourceTypeVO().getResourceTypeId());
//				resourceTypeData.setResourceTypeId(itemVo.getResourceTypeVO().getResourceTypeId());
				newItemData.setName(itemVo.getName());
				newItemData.setModelnumber(itemVo.getModelnumber());
				newItemData.setVendor(itemVo.getVendor());
				newItemData.setDescription(itemVo.getDescription());
				newItemData.setReferenceID(itemVo.getReferenceID());
				newItemData.setResoureceType(typeData);
				newItemData.setReason(itemVo.getReason()); */
			//	newItemData.setPrefix(itemVo.getPrefix());
				getEntityManager().merge(itemData);
				getEntityManager().flush();
				
				PrimaryKey primaryKey = null;
				if(itemData.getPrefix()!=null) {
					
					 primaryKey  = new PrimaryKey();
					primaryKey.setAlias(itemData.getResourceNumber());
					primaryKey.setPreFix(itemData.getPrefix().trim());
				}
				
				if(primaryKey!=null && primaryKey.getPreFix()!=null) {
					boolean isaAvailable = systemInternalSessionBeanLocal.isPrefixAvailable(primaryKey.getPreFix().trim());
					if(!isaAvailable){
						savePrimaryKey(primaryKey);
					}
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("UK")){
	        			throw new UpdateBLException("ReferenceID " + itemData.getReferenceID() +" Already Exists");
	        	   }
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("NAME")){
	        			throw new UpdateBLException("Name " + itemData.getName() +" Already Exists");
	        	   }

	    	   }
	    	   throw new UpdateBLException("Update Item Failed, Reason : " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.session.warehouse.WarehouseSessionBeanLocal#savePrimaryKey(com.elitecore.cpe.bl.entity.inventory.core.PrimaryKey)
	 */
	@Override
	public void savePrimaryKey(PrimaryKey primaryData) throws CreateBLException {
		// TODO Auto-generated method stub
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside savePrimaryKey");
		}
		try {	
			primaryData.setAlias("RS_".concat(primaryData.getAlias().trim()));
			primaryData.setLength((12L-primaryData.getPreFix().length()));
			primaryData.setCurrentValue(1L);
			System.out.println("Primary Data Before persist:"+primaryData.toString());
			getEntityManager().persist(primaryData);
			getEntityManager().flush();
			
		}catch (Exception e) {
			
			e.printStackTrace();
				
			getSessionContext().setRollbackOnly();
			
//			Object object = e.getCause();
//	    	   if(object instanceof ConstraintViolationException){
//	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
//	        	   
//	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("ALIAS")){
//	        			throw new CreateBLException("Name " + primaryData.getAlias() +" Already Exists");
//	        	   }
//	    	   }
//			
//	    	   throw new CreateBLException("Create Primary key Failed, Reason : " + e.getMessage(), e);
		}
		
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List findResourceSubTypeByTypeId(Long typeId)
			throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findResourceSubTypeByTypeId");
		try {
		 List<ResourceSubTypeData> subTypes =(List<ResourceSubTypeData>)getEntityManager().createNamedQuery("ResourceSubTypeData.findByResourceTypeId")
				 .setParameter("resourceTypeId", typeId).getResultList();
		 return subTypes;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findResourceSubTypeByTypeId Reason" +e.getMessage());
			throw new SearchBLException("Search findResourceSubTypeByTypeId operation failed, reason: " + e.getMessage(), e);
		}
	}

	@Override
	public void removeAttributeRel(ResourceAttributeRel rel)
			throws UpdateBLException {
		
		try {
			
			getEntityManager().remove(rel);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage());
		}
		
		
	}

	@Override
	public void createResourceType(ResourceTypeData typeData)
			throws CreateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createResourceType");
		}
		try {			
			getEntityManager().persist(typeData);
			getEntityManager().flush();
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("TBLSRESOURCETYPE_UK_NAME")){
	        			throw new CreateBLException("Name " + typeData.getName() +" Already Exists");
	        	   }
	        	   else if(constraintViolationException.getConstraintName().toUpperCase().contains("TBLSRESOURCETYPE_UK_ALIAS")){
	        			throw new CreateBLException("Name " + typeData.getName() +" Already Exists");
	        			
	        	   }
	    	   }
	    	   throw new CreateBLException("createResourceType Failed, Reason : " + e.getMessage(), e);
		}
		
	}

	@Override
	public List<ResourceTypeData> searchResourceTypeData(String name)
			throws SearchBLException {
		Logger.logDebug(MODULE, "inside  searchResourceTypeData");
		
		
		try {
			List<ResourceTypeData> arEventDatas =(List<ResourceTypeData>) getEntityManager().createNamedQuery("ResourceTypeData.searchResourceTypeData")
				 .setParameter("name",formatForUpperLikeSearch(name)).getResultList();
		 
		 return arEventDatas;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in searchResourceTypeData Reason" +e.getMessage());
			throw new SearchBLException(" searchResourceTypeData operation failed, reason: " + e.getMessage(), e);
		}
	}

	@Override
	public void createResourceSubType(ResourceSubTypeData typeData)
			throws CreateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createResourceSubType");
		}
		try {			
			getEntityManager().persist(typeData);
			getEntityManager().flush();
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("TBLSRESOURCESUBTYPE_UK_NAME")){
	        			throw new CreateBLException("Name " + typeData.getName() +" Already Exists");
	        	   }
	        	   else if(constraintViolationException.getConstraintName().toUpperCase().contains("TBLSRESOURCESUBTYPE_UK_ALIAS")){
	        			throw new CreateBLException("Name " + typeData.getName() +" Already Exists");
	        			
	        	   }
	    	   }
	    	   throw new CreateBLException("createResourceSubType Failed, Reason : " + e.getMessage(), e);
		}
		
	}

	@Override
	public List<ResourceSubTypeData> searchResourceSubTypeData(String name,
			Long resourceTypeId) throws SearchBLException {
		
		Logger.logDebug(MODULE, "inside  searchResourceSubTypeData");
	
		
		try {
			
			String query = "select o from ResourceSubTypeData o where o.systemgenerated='N' and upper(o.name) like :name ";
			
			if(resourceTypeId!=null && resourceTypeId!=0L) {
				query+=" and o.resourceType.resourceTypeId='"+resourceTypeId+"' ";
			}
			
			query+=" ORDER BY o.createdate DESC";
			
			List<ResourceSubTypeData> datas =(List<ResourceSubTypeData>) getEntityManager().createQuery(query)
				 .setParameter("name",formatForUpperLikeSearch(name)).getResultList();
		 
		 return datas;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in searchResourceSubTypeData Reason" +e.getMessage());
			throw new SearchBLException(" searchResourceSubTypeData operation failed, reason: " + e.getMessage(), e);
		}
	}

	@Override
	public ResourceTypeData viewResourceType(Long resourceTypeId)
			throws SearchBLException {
		Logger.logDebug(MODULE, "inside  viewResourceType");
		
		
		try {
			ResourceTypeData data =(ResourceTypeData) getEntityManager().createNamedQuery("ResourceTypeData.searchResourceTypeById")
				 .setParameter("resourceTypeId",resourceTypeId).getSingleResult();
		 
		 return data;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in viewResourceType Reason" +e.getMessage());
			throw new SearchBLException(" viewResourceType operation failed, reason: " + e.getMessage(), e);
		}
	}

	@Override
	public void updateResourceType(ResourceTypeData typeData)
			throws UpdateBLException {
		
		try {
			
			getEntityManager().merge(typeData);
			getEntityManager().flush();
			
		}catch(Exception e) {
			e.printStackTrace();
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("TBLSRESOURCETYPE_UK_NAME")){
	        			throw new UpdateBLException("Name " + typeData.getName() +" Already Exists");
	        	   }
	        	   else if(constraintViolationException.getConstraintName().toUpperCase().contains("TBLSRESOURCETYPE_UK_ALIAS")){
	        			throw new UpdateBLException("Name " + typeData.getName() +" Already Exists");
	        			
	        	   }
	    	   }
			
			
			throw new UpdateBLException(e.getMessage());
		}
		
	}

	@Override
	public ResourceSubTypeData viewResourceSubType(Long resourceSubTypeId)
			throws SearchBLException {
	
		Logger.logDebug(MODULE, "inside  viewResourceSubType");
		
		
		try {
			ResourceSubTypeData data =(ResourceSubTypeData) getEntityManager().createNamedQuery("ResourceSubTypeData.findById")
				 .setParameter("resourceSubTypeId",resourceSubTypeId).getSingleResult();
		 
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
	public void updateResourceSubType(ResourceSubTypeData typeData)
			throws UpdateBLException {
		
		try {
			
			getEntityManager().merge(typeData);
			getEntityManager().flush();
			
		}catch(Exception e) {
			e.printStackTrace();
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("TBLSRESOURCESUBTYPE_UK_NAME")){
	        			throw new UpdateBLException("Name " + typeData.getName() +" Already Exists");
	        	   }
	        	   else if(constraintViolationException.getConstraintName().toUpperCase().contains("TBLSRESOURCESUBTYPE_UK_ALIAS")){
	        			throw new UpdateBLException("Name " + typeData.getName() +" Already Exists");
	        			
	        	   }
	    	   }
			
			
			throw new UpdateBLException(e.getMessage());
		}
		
	}
	public Boolean isResourceExist(String name) {
	
		Logger.logDebug(MODULE, "inside  isResourceExist");
		Boolean flag=false;
		ItemData data=null;
		try {
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder = queryBuilder.append("from ItemData o where o.name<>null and lower(o.name)= lower(:name) ");
			data =(ItemData) getEntityManager().createQuery(queryBuilder.toString())
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

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemData> findResource(String resourceName, String resourceType,
			String resourceSubType) throws SearchBLException {
		
		try {
			
			return getEntityManager().createNamedQuery("ItemData.findItem").setParameter("name", resourceName)
					.setParameter("resourceType", resourceType).setParameter("resourceSubTypeData", resourceSubType)
					.getResultList();
			
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			throw new SearchBLException(" findResource operation failed, reason: " + e.getMessage(), e);
		}
		
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List findInventoryStatusById(Long typeId)
			throws SearchBLException {
	
		if(isTraceLevel())
			logTrace(MODULE, "inside  findInventoryStatusById");
		try {
			 List<InventoryStatusTransition> subTypes =(List<InventoryStatusTransition>)getEntityManager().createNamedQuery("InventoryStatusTransition.findStatusById")
					 .setParameter("statusId", typeId).getResultList();
		 return subTypes;
		}catch(NoResultException e) {
				return null;
			}catch(Exception e) {
				e.printStackTrace();
				if(isErrorLevel())
					logError(MODULE, "Error in findInventoryStatusById Reason" +e.getMessage());
				throw new SearchBLException("Search findInventoryStatusById operation failed, reason: " + e.getMessage(), e);
			}		
	}

	@Override
	public CheckInventoryVO checkInventoryInWarehouse(String inventoryNumber,Long warehouseId, List<Integer> inventoryStatus)
			throws SearchBLException {
		
		CheckInventoryVO checkInventoryVO = new CheckInventoryVO();
		
		String transferQuery = "select transferinventorystatus from tblminventory where inventoryno=:inventoryno";
		
		String result = null;
		try {
			result = (String) getEntityManager().createNativeQuery(transferQuery).setParameter("inventoryno", inventoryNumber).getSingleResult();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(result!=null) {
			checkInventoryVO.setResponseCode(-1L);
			checkInventoryVO.setResponseMessage("Please select another Inventory as Inventory is in Transfer Status");
			return checkInventoryVO;
		}
		
		String query = "select o from InventoryData o where o.transferInventoryStatus is null and o.warehouseId='"+warehouseId+"' and o.inventoryNo='"+inventoryNumber+"' ";
		
		if(inventoryStatus!=null && !inventoryStatus.isEmpty()) {
			StringBuilder builder  = new StringBuilder(" and ( ");
			for(Integer status : inventoryStatus) {
				builder.append(" o.inventoryStatusId='"+status+"' or ");
			}
			builder = new StringBuilder(builder.toString().substring(0, builder.toString().lastIndexOf("or")));
			builder.append(" )");
			query=query.concat(builder.toString());
		}
		
		List<InventoryData> inventoryDatas = null;
		try {
			inventoryDatas = getEntityManager().createQuery(query).getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(inventoryDatas!=null && !inventoryDatas.isEmpty()) {
			
			InventoryData inventoryData = inventoryDatas.get(0);
			
			checkInventoryVO.setBatchNumber(inventoryData.getBatchData().getBatchNo());
			checkInventoryVO.setInventoryNumber(inventoryData.getInventoryNo());
			checkInventoryVO.setInventoryStatus(inventoryData.getStatusData().getName());
			if(inventoryData.getItemData().getResourceSubTypeData()!=null) {
				checkInventoryVO.setResourceSubtype(inventoryData.getItemData().getResourceSubTypeData().getName());
			}
			checkInventoryVO.setResourceType(inventoryData.getItemData().getResourceType().getName());
			checkInventoryVO.setWarehouseName(inventoryData.getWarehousedata().getName());
			
			checkInventoryVO.setResponseCode(0L);
			checkInventoryVO.setResponseMessage("Inventory Available");
		} else {
			checkInventoryVO.setResponseCode(-1L);
			checkInventoryVO.setResponseMessage("Invalid Inventory Number or Inventory not present in Warehouse");
		}
		
		
		return checkInventoryVO;
	}

}