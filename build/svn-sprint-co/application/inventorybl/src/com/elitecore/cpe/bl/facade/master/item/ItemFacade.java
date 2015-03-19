package com.elitecore.cpe.bl.facade.master.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.data.system.audit.AuditSummaryDetail;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusTransition;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.ResourceAttributeRel;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceCategory;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceSubTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceTypeData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementFacadeLocal;
import com.elitecore.cpe.bl.facade.master.attribute.AttributeUtil;
import com.elitecore.cpe.bl.facade.system.audit.AuditDataConversionUtilities;
import com.elitecore.cpe.bl.facade.system.systemparameter.SystemParameterFacadeLocal;
import com.elitecore.cpe.bl.session.master.item.ItemSessionBeanLocal;
import com.elitecore.cpe.bl.session.system.internal.SystemInternalSessionBeanLocal;
import com.elitecore.cpe.bl.vo.inventorymgt.ResourceAttributeVO;
import com.elitecore.cpe.bl.vo.inventorymgt.migration.InventoryMigrationResponseVO;
import com.elitecore.cpe.bl.vo.inventorymgt.migration.InventoryMigrationVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.CheckInventoryVO;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceTypeVO;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;

@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class ItemFacade extends BaseFacade implements ItemFacadeRemote,ItemFacadeLocal{

	private static final String MODULE = "ITEM-FC";
	
	
	@EJB private SystemInternalSessionBeanLocal systemInternalSessionBeanLocal;
	@EJB private ItemSessionBeanLocal ItemSessionBeanLocal;
	@EJB private InventoryManagementFacadeLocal inventoryManagementFacadeLocal;
	@EJB private SystemParameterFacadeLocal systemParameterFacadeLocal;
	
	/**
	 * Create Item/ Resource
	 * @author yash.kapasi
	 * @param {@link ItemVO} itemVo
	 * @param {@link IBLSession} iblSession
	 * @throws CreateBLException
	 */
	@Override
	public ItemVO createItem(ItemVO itemVo,IBLSession iblSession) throws CreateBLException{
		
		Logger.logTrace(MODULE, "inside createITem()");
		try {
			
			//String primaryKey = systemInternalSessionBeanLocal.getPrimaryKey(CPECommonConstants.ITEM_DATA);
		//	System.out.println("Primary Key:- "+primaryKey);
		//	itemVo.setItemId(primaryKey);
			itemVo.setSystemgenerated("N");
			itemVo.setEditable("Y");
			
			ItemData itemdata=	ItemUtil.getItemData(itemVo);
			String pk = systemInternalSessionBeanLocal.getPrimaryKey(CPECommonConstants.RESOURCE_DATA);
			itemdata.setResourceNumber(pk);
			itemVo.setResourceNumber(pk);
			if(itemVo!=null && itemVo.getAttributeVO()!=null && !itemVo.getAttributeVO().isEmpty()) {
				Set<ResourceAttributeRel> resourceAttributeRels = new HashSet<ResourceAttributeRel>();
				for(ResourceAttributeVO attributeVO : itemVo.getAttributeVO()) {
					System.out.println("inside forloop");
					Map<String,Object> fieldValueResourceMap = new LinkedHashMap<String, Object>();
					fieldValueResourceMap.put("systemgenerated", "N");
					fieldValueResourceMap.put("attributeId", attributeVO.getAttributeId());
					List filterAttrList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.ATTRIBUTE_DATA, fieldValueResourceMap);
					
					System.out.println(filterAttrList);
					
					if(filterAttrList!=null && !filterAttrList.isEmpty()) {
						AttributeData newAttrData = ((List<AttributeData>)filterAttrList).get(0);
						System.out.println(newAttrData);
						resourceAttributeRels.add(new ResourceAttributeRel(itemdata, newAttrData));
					}
				}
				itemdata.setResourceAttributeRels(resourceAttributeRels);
			}
			if(!(ItemSessionBeanLocal.isResourceExist(itemdata.getName()))){
			itemdata = ItemSessionBeanLocal.createItem(itemdata);
			itemVo.setItemId(itemdata.getItemId());
			// Audit entry
			Map<String,Object> mapAudit = new HashMap<String, Object>();
			mapAudit.put(AuditTagConstant.NAME,itemVo.getName());
			mapAudit.put(AuditTagConstant.REFERENCEID,itemVo.getReferenceID());
				
			addToAuditDynamicMessage(AuditConstants.CREATE_RESOURCE, "Creating Resource  ",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, iblSession);
			
			String toEmail = systemParameterFacadeLocal.getSystemParameterValue(SystemParameterConstants.EMAIL_ALIAS_RESOURCE_TO);
			String ccEmail = systemParameterFacadeLocal.getSystemParameterValue(SystemParameterConstants.EMAIL_ALIAS_RESOURCE_CC);
			
			//Notification Data
			
			
			NotificationData data = ItemUtil.prepareNotificationDataOnResourceCreation(itemdata,toEmail,ccEmail,"Resource Created in CPE");
			sendNotification(data);
			
			
			}else{
				throw new CreateBLException("Resource Name " + itemdata.getName() +" Already Exists");
			}
		}catch (CreateBLException e) {
			throw e;
		} catch (SearchBLException e) {
			e.printStackTrace();
			throw new CreateBLException(e);
		} finally {
		}
		return itemVo;
		
	}

	/**
	 * Search Item/ Resource
	 * @author yash.kapasi
	 * @param {@link ItemVO} itemVo
	 * @return {@link List}<{@link ItemVO}> data
	 */
	@Override
	public List<ItemVO> searchItemData(ItemVO itemvo) {
		Logger.logTrace(MODULE, "inside searchItemData()");
		List<ItemVO> data = new ArrayList<ItemVO>();
		try {
			List<ItemData> data1 = ItemSessionBeanLocal.searchItemData(itemvo);
			if(data1 != null){
				for(ItemData itemData : data1){
					data.add(ItemUtil.getitemVo(itemData));
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
}

	@Override
	public ItemVO viewItem(ItemVO itemVo) {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside viewITem()");
		ItemVO newItemVo = null;
		try {
			
			
			newItemVo =ItemUtil.getitemVo(ItemSessionBeanLocal.viewItem(itemVo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return newItemVo;
	}

	
	/**
	 * Update Item/ Resource
	 * @author yash.kapasi
	 * @param {@link ItemVO} itemVo
	 * @param {@link IBLSession} iblSession
	 * @throws UpdateBLException
	 */
	@Override
	public void updateItem(ItemVO itemVo,IBLSession iblSession) throws UpdateBLException {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside updateItem()");
		try {
			if(itemVo == null){
				return;
			}
			
			Map map=new HashMap<String, String>();
			Map<String,Object> fieldValueResourceMap = new LinkedHashMap<String, Object>();
			fieldValueResourceMap.put("systemgenerated", "N");
			fieldValueResourceMap.put("RESOURCEID", itemVo.getItemId());
			//fieldValueResourceMap.put("REFERENCEID", itemVo.getReferenceID());
			List filterResourceList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.ITEM_DATA, fieldValueResourceMap);
			
			ItemData newItemData = new ItemData();
			if(filterResourceList != null && !filterResourceList.isEmpty()){
				
				Logger.logInfo(MODULE, "getting data in filter data ::::");
				newItemData = ((List<ItemData>)filterResourceList).get(0);
			}
			if(!newItemData.getName().equalsIgnoreCase(itemVo.getName())){
				if(!(ItemSessionBeanLocal.isResourceExist(itemVo.getName()))){
					
				}else{
					throw new UpdateBLException("Resource Name " + itemVo.getName() +" Already Exists");
				}
			}
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("RESOURCETYPEID",itemVo.getResourceTypeVO().getResourceTypeId());
			List filterList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCETYPE_DATA, fieldValueMap);
			
			ResourceTypeData resourceType = null;
			if(filterList != null && !filterList.isEmpty()){
				
				Logger.logInfo(MODULE, "getting data in filter data ::::");
				resourceType = ((List<ResourceTypeData>)filterList).get(0);
				
				map.put(AuditConstants.TBLMRESOURCE_NEW_REFID,resourceType.getName());
				
			}
			
			AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareResourceUpdateAudit(newItemData,itemVo,map);
			
				if(newItemData != null){
					Logger.logTrace(MODULE, "itemVo.getResourceTypeVO().getResourceTypeId() : "+itemVo.getResourceTypeVO().getResourceTypeId());
					
//					ResourceTypeData resourceTypeData=newItemData.getResourceType();
//					ResourceTypeData typeData = new ResourceTypeData();
//					typeData.setResourceTypeId(itemVo.getResourceTypeVO().getResourceTypeId());
//					resourceTypeData.setResourceTypeId(itemVo.getResourceTypeVO().getResourceTypeId());
					newItemData.setName(itemVo.getName());
					newItemData.setModelnumber(itemVo.getModelnumber());
					newItemData.setVendor(itemVo.getVendor());
					newItemData.setDescription(itemVo.getDescription());
					newItemData.setReferenceID(itemVo.getReferenceID());
//					newItemData.setResourceType(typeData);
					newItemData.setReason(itemVo.getReason());
					
					newItemData.setResourceTypeId(itemVo.getResourceTypeVO().getResourceTypeId());
					newItemData.setResourceCategoryId(itemVo.getResourceCategoryVO().getResourceCategoryId());
					newItemData.setResourceSubTypeId(itemVo.getResourceTypeVO().getResourceSubTypeVO().getResourceSubTypeId());
					newItemData.setUpdatedate(getCurrentTimestamp());
					newItemData.setUpdatedby(iblSession.getSessionUserId());
					
					newItemData.setPrefix(itemVo.getPrefix());
				
			
			
					if(newItemData.getResourceAttributeRels()!=null && !newItemData.getResourceAttributeRels().isEmpty()) {
						for(ResourceAttributeRel rel : newItemData.getResourceAttributeRels()) {
							ItemSessionBeanLocal.removeAttributeRel(rel);
						}
						newItemData.setResourceAttributeRels(null);
						ItemSessionBeanLocal.updateItem(newItemData);
					}
				}
			
				if(itemVo!=null && itemVo.getAttributeVO()!=null && !itemVo.getAttributeVO().isEmpty()) {
					Set<ResourceAttributeRel> resourceAttributeRels = new HashSet<ResourceAttributeRel>();
					for(ResourceAttributeVO attributeVO : itemVo.getAttributeVO()) {
						System.out.println("inside forloop");
						Map<String,Object> valueResourceMap = new LinkedHashMap<String, Object>();
						valueResourceMap.put("systemgenerated", "N");
						valueResourceMap.put("attributeId", attributeVO.getAttributeId());
						List filterAttrList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.ATTRIBUTE_DATA, valueResourceMap);
						
						System.out.println("List --> "+filterAttrList);
						
						if(filterAttrList!=null && !filterAttrList.isEmpty()) {
							AttributeData newAttrData = ((List<AttributeData>)filterAttrList).get(0);
							System.out.println("AttributeData-->"+newAttrData);
							resourceAttributeRels.add(new ResourceAttributeRel(newItemData, newAttrData));
						}
					}
					newItemData.setResourceAttributeRels(resourceAttributeRels);
				}
			
			
				
			
			
			
			
			ItemSessionBeanLocal.updateItem(newItemData);
			
			// Audit entry
			Map<String,Object> mapAudit = new HashMap<String, Object>();
			mapAudit.put(AuditTagConstant.NAME,itemVo.getName());
			mapAudit.put(AuditTagConstant.REFERENCEID,itemVo.getReferenceID());
			
			addToAuditDynamicMessage(AuditConstants.UPDATE_RESOURCE, "Updating Resource",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), iblSession);
		
	}catch(UpdateBLException e){
			throw e;
		}
		
	}
		
	
	@Override
	public List<ComboData> getAllResourceTypeData() {
		// TODO Auto-generated method stub
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside getAllResourceTypeData()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			List filterList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCETYPE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				List<ResourceTypeData> resourceTypeDatas = (List<ResourceTypeData>)filterList;
				for(ResourceTypeData resourceTypeData : resourceTypeDatas){
					comboBoxDatas.add(new ComboData(resourceTypeData.getResourceTypeId(), resourceTypeData.getName()));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
	}

	@Override
	public List<ComboData> getAllResourceSubTypeDataByResourceTypeId(Long typeId)
			throws SearchBLException {
		
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside getAllResourceSubTypeDataByResourceTypeId()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("resourceType.resourceTypeId", typeId);
			
			List filterList = ItemSessionBeanLocal.findResourceSubTypeByTypeId(typeId);
			if(filterList != null && !filterList.isEmpty()){
				
				List<ResourceSubTypeData> resourceTypeDatas = (List<ResourceSubTypeData>)filterList;
				for(ResourceSubTypeData resourceTypeData : resourceTypeDatas){
					comboBoxDatas.add(new ComboData(resourceTypeData.getResourceSubTypeId(), resourceTypeData.getName()));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
		
	}

	
	/**
	 * Get All Resource Categories
	 * @author yash.kapasi
	 * @return {@link List}<{@link ComboData}> comboBoxDatas
	 * @throws SearchBLException
	 */
	@Override
	public List<ComboData> getAllResourceCategories() throws SearchBLException {
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside getAllResourceCategories()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			List filterList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCECATEGORY, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				List<ResourceCategory> resourceCategoriesDatas = (List<ResourceCategory>)filterList;
				for(ResourceCategory resourceTypecategory : resourceCategoriesDatas){
					comboBoxDatas.add(new ComboData(resourceTypecategory.getResourceCategoryId(), resourceTypecategory.getName()));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
	}

	@Override
	public List<ComboData> getAllResourceAttributes() throws SearchBLException {
		
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside getAllResourceAttributes()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("usedBy", "Resource");
			List filterList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.ATTRIBUTE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				List<AttributeData> attributeDatas = (List<AttributeData>)filterList;
				for(AttributeData attr : attributeDatas){
					comboBoxDatas.add(new ComboData(attr.getAttributeId(), attr.getName()));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
	}

	/**
	 * Create Resource Type
	 * @author yash.kapasi
	 * @param {@link ResourceTypeVO} resourceTypeVO
	 * @param {@link IBLSession} blSession
	 * @throws CreateBLException
	 */
	@Override
	public void createResourceType(ResourceTypeVO resourceTypeVO,
			IBLSession blSession) throws CreateBLException {
		
		ResourceTypeData typeData = new ResourceTypeData();
		typeData.setName(resourceTypeVO.getResourceTypeName());
		typeData.setDescription(resourceTypeVO.getDescription());
		typeData.setAlias(resourceTypeVO.getResourceTypeName().toUpperCase());
		typeData.setSystemgenerated("N");
		typeData.setCreatedate(getCurrentTimestamp());
		typeData.setCreatedby(blSession.getSessionUserId());
	//	typeData.setLastmodifiedby(blSession.getSessionUserId());
		ItemSessionBeanLocal.createResourceType(typeData);
		
		// Audit entry
		Map<String,Object> mapAudit = new HashMap<String, Object>();
		mapAudit.put(AuditTagConstant.NAME,typeData.getName());
		
		addToAuditDynamicMessage(AuditConstants.CREATE_RESOURCETYPE, "Creating ResourceType",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, blSession);

		
	}

	/**
	 * Search ResourceType Data by Name
	 * @author yash.kapasi
	 * @param String name
	 * @return {@link List}<{@link SearchResourceTypeVO}> responseInfo
	 * @throws SearchBLException
	 */
	@Override
	public List<SearchResourceTypeVO> searchResourceTypeData(String name)
			throws SearchBLException {
		
		Logger.logTrace(MODULE, "inside searchResourceTypeData()" + name);
		List<SearchResourceTypeVO> responseInfo = new ArrayList<SearchResourceTypeVO>();


		List<ResourceTypeData> typeDatas = ItemSessionBeanLocal.searchResourceTypeData(name);
			if(typeDatas!=null && !typeDatas.isEmpty()) {
				for(ResourceTypeData typeData : typeDatas) {
					responseInfo.add(new SearchResourceTypeVO(typeData.getResourceTypeId(), typeData.getName(), typeData.getAlias(), typeData.getDescription(),typeData.getCreatedate()));
				}
			}
				 
		
		Logger.logTrace(MODULE, "returning searchEvent ");
		return responseInfo;
	}

	
	
	/**
	 * Create Resource Subtype
	 * @author yash.kapasi
	 * @param {@link ResourceSubTypeVO} resourceTypeVO
	 * @param {@link IBLSession}  blSession
	 * @throws CreateBLException
	 */
	@Override
	public void createResourceSubType(ResourceSubTypeVO resourceTypeVO,
			IBLSession blSession) throws CreateBLException {
		
		ResourceSubTypeData typeData = new ResourceSubTypeData();
		typeData.setName(resourceTypeVO.getResourceSubTypeName());
		typeData.setDescription(resourceTypeVO.getDescription());
		typeData.setAlias(resourceTypeVO.getResourceSubTypeName().toUpperCase());
		typeData.setSystemgenerated("N");
		typeData.setCreatedate(getCurrentTimestamp());
		typeData.setCreatedby(blSession.getSessionUserId());
		//typeData.setLastmodifiedby(blSession.getSessionUserId());
		Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
		fieldValueMap.put("systemgenerated", "N");
		fieldValueMap.put("RESOURCETYPEID",resourceTypeVO.getResourceTypeId());
		List filterList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCETYPE_DATA, fieldValueMap);
		
		ResourceTypeData resourceType = null;
		if(filterList != null && !filterList.isEmpty()){

			resourceType = ((List<ResourceTypeData>)filterList).get(0);
			typeData.setResourceType(resourceType);
		}
		

		ItemSessionBeanLocal.createResourceSubType(typeData);
		// Audit entry
		Map<String,Object> mapAudit = new HashMap<String, Object>();
		mapAudit.put(AuditTagConstant.NAME,typeData.getName());

		addToAuditDynamicMessage(AuditConstants.CREATE_RESOURCESUBTYPE, "Creating Resource Subtype",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, blSession);

		
	}

	@Override
	public List<SearchResourceSubTypeVO> searchResourceSubTypeData(String name,
			Long resourceTypeId) throws SearchBLException {
		
		
		Logger.logTrace(MODULE, "inside searchResourceSubTypeData()" + name);
		List<SearchResourceSubTypeVO> responseInfo = new ArrayList<SearchResourceSubTypeVO>();


		List<ResourceSubTypeData> typeDatas = ItemSessionBeanLocal.searchResourceSubTypeData(name,resourceTypeId);
			if(typeDatas!=null && !typeDatas.isEmpty()) {
				for(ResourceSubTypeData typeData : typeDatas) {
					responseInfo.add(ItemUtil.convertResourceSubTypeData(typeData));
				}
			}
				 
		
		Logger.logTrace(MODULE, "returning searchResourceSubTypeData ");
		return responseInfo;
	}

	@Override
	public SearchResourceTypeVO viewResourceTypeData(Long resourceTypeId, IBLSession blSession)
			throws SearchBLException {
		
		Logger.logTrace(MODULE, "inside viewResourceTypeData()" + resourceTypeId);
		SearchResourceTypeVO typeVO = new SearchResourceTypeVO();


		ResourceTypeData typeData = ItemSessionBeanLocal.viewResourceType(resourceTypeId);
		if(typeData!=null) {
			typeVO = ItemUtil.convertResourceTypeData(typeData,blSession);
		}
				 
		
		Logger.logTrace(MODULE, "returning searchResourceSubTypeData ");
		return typeVO;
	}

	
	/**
	 * Update Resource type
	 * @author yash.kapasi
	 * @param {@link UpdateResourceTypeVO} typeVO
	 * @param {@link IBLSession}  blSession
	 * @throws UpdateBLException
	 */
	@Override
	public void updateResourceType(UpdateResourceTypeVO typeVO,
			IBLSession blSession) throws UpdateBLException {

		try {
		
			Map map=new HashMap<String, String>();
			Map<String,Object> fieldValueResourceMap = new LinkedHashMap<String, Object>();
			fieldValueResourceMap.put("systemgenerated", "N");
			fieldValueResourceMap.put("resourceTypeId", typeVO.getResourceTypeId());
			List filterResourceList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCETYPE_DATA, fieldValueResourceMap);
			
			if(filterResourceList!=null && !filterResourceList.isEmpty()) {
				ResourceTypeData typeData = (ResourceTypeData) filterResourceList.get(0);
				AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareResourceTypeUpdateAudit(typeData, typeVO);
				
				typeData.setName(typeVO.getResourceTypeName());
				typeData.setDescription(typeVO.getDescription());
				typeData.setAlias(typeVO.getResourceTypeName().toUpperCase());
				typeData.setLastmodifieddate(getCurrentTimestamp());
				typeData.setLastmodifiedby(blSession.getSessionUserId());
				ItemSessionBeanLocal.updateResourceType(typeData);
				
				
				// Audit entry
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.NAME,typeData.getName());
				addToAuditDynamicMessage(AuditConstants.UPDATE_RESOURCETYPE, "Updating Resource Type",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), blSession);
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage()); 
		}
		
		
	}

	@Override
	public SearchResourceSubTypeVO viewResourceSubTypeData(
			Long resourceSubTypeId) throws SearchBLException {
		
		Logger.logTrace(MODULE, "inside viewResourceSubTypeData()" + resourceSubTypeId);
		SearchResourceSubTypeVO typeVO = new SearchResourceSubTypeVO();


		ResourceSubTypeData typeData = ItemSessionBeanLocal.viewResourceSubType(resourceSubTypeId);
		if(typeData!=null) {
			typeVO = ItemUtil.convertResourceSubTypeData(typeData);
		}
				 
		
		Logger.logTrace(MODULE, "returning viewResourceSubTypeData ");
		return typeVO;
		
	}

	/**
	 * Update Resource SubType
	 * @author yash.kapasi
	 * @param {@link UpdateResourceSubTypeVO} typeVO
	 * @param {@link IBLSession}  blSession
	 * @throws UpdateBLException
	 */
	@Override
	public void updateResourceSubType(UpdateResourceSubTypeVO typeVO,
			IBLSession blSession) throws UpdateBLException {
		
		try {
			
			Map map=new HashMap<String, String>();
			Map<String,Object> fieldValueResourceMap = new LinkedHashMap<String, Object>();
			fieldValueResourceMap.put("systemgenerated", "N");
			fieldValueResourceMap.put("resourceSubTypeId", typeVO.getResourceSubTypeId());
			List filterResourceList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCESUBTYPE_DATA, fieldValueResourceMap);
			
			if(filterResourceList!=null && !filterResourceList.isEmpty()) {
				ResourceSubTypeData typeData = (ResourceSubTypeData) filterResourceList.get(0);
				
				AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareResourceSubTypeUpdateAudit(typeData, typeVO);
				
				typeData.setName(typeVO.getResourceSubTypeName());
				typeData.setDescription(typeVO.getDescription());
				typeData.setAlias(typeVO.getResourceSubTypeName().toUpperCase());
				typeData.setLastmodifieddate(getCurrentTimestamp());
				typeData.setLastmodifiedby(blSession.getSessionUserId());
				
				Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
				fieldValueMap.put("systemgenerated", "N");
				fieldValueMap.put("RESOURCETYPEID",typeVO.getResourceTypeId());
				List filterList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCETYPE_DATA, fieldValueMap);
				
				ResourceTypeData resourceType = null;
				if(filterList != null && !filterList.isEmpty()){

					resourceType = ((List<ResourceTypeData>)filterList).get(0);
					typeData.setResourceType(resourceType);
				}
				
				ItemSessionBeanLocal.updateResourceSubType(typeData);
				
				// Audit entry
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.NAME,typeData.getName());
				addToAuditDynamicMessage(AuditConstants.UPDATE_RESOURCESUBTYPE, "Updating Resource Subtype",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), blSession);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage()); 
		}
		
	}

	
	/**
	 * Get All Resource Sub Type Data
	 * @author yash.kapasi
	 * @return {@link List}<{@link ComboData}> comboBoxDatas
	 * @throws SearchBLException
	 */
	@Override
	public List<ComboData> getAllResourceSubTypeData() throws SearchBLException {
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside getAllResourceSubTypeData()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			List filterList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCESUBTYPE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				List<ResourceSubTypeData> resourceTypeDatas = (List<ResourceSubTypeData>)filterList;
				for(ResourceSubTypeData resourceTypeData : resourceTypeDatas){
					comboBoxDatas.add(new ComboData(resourceTypeData.getResourceSubTypeId(), resourceTypeData.getName()));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
	}

	
	/**
	 * Search Item Data Composer
	 * @author yash.kapasi
	 * @param {@link ItemVO} itemVo
	 * @return {@link List}<{@link ItemVO}> data
	 */
	@Override
	public List<ItemVO> searchItemDataComposer(ItemVO itemVo) {
		Logger.logTrace(MODULE, "inside searchItemDataComposer()");
		List<ItemVO> data = new ArrayList<ItemVO>();
		try {
			List<ItemData> data1 = ItemSessionBeanLocal.searchItemData(itemVo);
			if(data1 != null){
				Logger.logTrace(MODULE, "Data Size :: "+data1.size());
				for(ItemData itemData : data1){
					Logger.logTrace(MODULE, "Calling : ");
					data.add(ItemUtil.getitemVoComposer(itemData));
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public InventoryMigrationResponseVO migrateInventory(
			List<InventoryMigrationVO> inventoryMigrationVOs,boolean isValidate)
			throws SearchBLException {
		
		return inventoryManagementFacadeLocal.uploadMigrationInventory(inventoryMigrationVOs, isValidate);
		
		
	}



	@Override
	public List<ComboBoxData> getAllResourceData() throws SearchBLException {
		
		List<ComboBoxData> comboBoxDatas = new ArrayList<ComboBoxData>();
		Logger.logTrace(MODULE, "inside getAllResourceData()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			List filterList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.ITEM_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				List<ItemData> itemDatas = (List<ItemData>)filterList;
				for(ItemData itemData : itemDatas){
					comboBoxDatas.add(new ComboBoxData(itemData.getResourceNumber(), itemData.getName()));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
		
	}

	@Override
	public List<AttributeVO> getAllAttributesfromResourceId(String resourceNo)
			throws SearchBLException {
		
		
		List<AttributeVO> attributeDatas = new ArrayList<AttributeVO>();
		Logger.logTrace(MODULE, "inside getAllResourceData()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("resourceNumber", resourceNo);
			List filterList = ItemSessionBeanLocal.getFilterDataBy(EntityConstants.ITEM_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				ItemData itemData = (ItemData) filterList.get(0);
				if(itemData.getResourceAttributeRels()!=null && !itemData.getResourceAttributeRels().isEmpty()) {
					for(ResourceAttributeRel attributeRel : itemData.getResourceAttributeRels()) {
						attributeDatas.add(AttributeUtil.getAttributeVO(attributeRel.getAttribute()));
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return attributeDatas;
		
	}
	@Override
	public List<ComboData> getAllInventoryStatusDataByStatusId(Long typeId)
			throws SearchBLException {
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside getAllInventorySubStatusDataByStatusId()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("inventorystatus.inventorystatus", typeId);
			List filterList = ItemSessionBeanLocal.findInventoryStatusById(typeId);
			
			if(filterList != null && !filterList.isEmpty()){
				
				List<InventoryStatusTransition> resourceTypeDatas = (List<InventoryStatusTransition>)filterList;
				for(InventoryStatusTransition statusTransition : resourceTypeDatas){
					comboBoxDatas.add(new ComboData(statusTransition.getAllowedStatusId(), statusTransition.getAllowedStatusData().getName()));
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
	
	}

	@Override
	public CheckInventoryVO checkInventoryInWarehouse(String inventoryNumber,
			Long warehouseId,List<Integer> inventoryStatus) throws SearchBLException {
		
		
		return ItemSessionBeanLocal.checkInventoryInWarehouse(inventoryNumber,warehouseId,inventoryStatus);
		
	}
}
