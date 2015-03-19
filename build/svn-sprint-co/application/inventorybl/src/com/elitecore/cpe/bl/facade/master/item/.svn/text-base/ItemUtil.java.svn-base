package com.elitecore.cpe.bl.facade.master.item;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import com.elitecore.cpe.bl.constants.notification.NotificationConstants;
import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.ResourceAttributeRel;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderDetailData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceSubTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.exception.NoSuchControllerException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.vo.inventorymgt.ResourceAttributeVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorytransfer.PartialAcceptRejectTransferOrderVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.ResourceCategoryVO;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceTypeVO;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.util.notification.NotificationUtil;

public class ItemUtil {
	private static final String MODULE = "ITEM-UTIL";
	 public static ItemVO getitemVo(ItemData itemData){
		 Logger.logTrace(MODULE, "In getitemVo.");
		 ItemVO itemVo=new ItemVO();
		 ResourceTypeVO resourceTypeVO=new ResourceTypeVO();
		 if(itemData!=null){
			 itemVo.setItemId(itemData.getItemId());
			 itemVo.setName(itemData.getName());
			 itemVo.setDescription(itemData.getDescription());
			 itemVo.setReferenceID(itemData.getReferenceID());
			 itemVo.setResourceNumber(itemData.getResourceNumber());
			 itemVo.setModelnumber(itemData.getModelnumber());
			 itemVo.setVendor(itemData.getVendor());
			 itemVo.setCreatedate(itemData.getCreatedate());
			 itemVo.setUpdatedate(itemData.getUpdatedate());
			 
			 
			 try {
				 itemVo.setCreatedby(UserFactory.findUserById(itemData.getCreatedby()).getName());
				 if(itemData.getUpdatedby()!=null){
					 itemVo.setUpdatedby(UserFactory.findUserById(itemData.getUpdatedby()).getName());
				 }
				} catch (SearchBLException e) {
					e.printStackTrace();
				} catch (NoSuchControllerException e) {
					e.printStackTrace();
				}
			 
			 itemVo.setSystemgenerated(itemData.getSystemgenerated());
			 itemVo.setEditable(itemData.getEditable());
			 itemVo.setAlias(itemData.getAlias());
			 itemVo.setReason(itemData.getReason());
			// System.out.println(" JM Resource Type name:"+itemData.getResoureceType().getName());
			 resourceTypeVO.setResourceTypeId(itemData.getResourceType().getResourceTypeId());
			 resourceTypeVO.setResourceTypeName(itemData.getResourceType().getName());
			 
			 if(itemData.getResourceSubTypeData()!=null) {
				 ResourceSubTypeVO subTypeVO = new ResourceSubTypeVO();
				 subTypeVO.setResourceSubTypeId(itemData.getResourceSubTypeData().getResourceSubTypeId());
				 subTypeVO.setResourceSubTypeName(itemData.getResourceSubTypeData().getName());
				 resourceTypeVO.setResourceSubTypeVO(subTypeVO);
			 }
			 if(itemData.getResourceCategory()!=null) {
				 ResourceCategoryVO categoryVO = new ResourceCategoryVO();
				 categoryVO.setResourceCategoryId(itemData.getResourceCategory().getResourceCategoryId());
				 categoryVO.setResourceCategoryName(itemData.getResourceCategory().getName());
				 itemVo.setResourceCategoryVO(categoryVO);
			 }
			 
			 if(itemData.getResourceAttributeRels()!=null && !itemData.getResourceAttributeRels().isEmpty()) {
				 List<ResourceAttributeVO> attributeVOs = new ArrayList<ResourceAttributeVO>();
				 for(ResourceAttributeRel resourceAttributeRel : itemData.getResourceAttributeRels()) {
					 attributeVOs.add(new ResourceAttributeVO(resourceAttributeRel.getAttribute().getAttributeId(), resourceAttributeRel.getAttribute().getName()));
				 }
				 itemVo.setAttributeVO(attributeVOs);
			 }
			 
			 itemVo.setResourceTypeVO(resourceTypeVO);
			 
			 if(itemData.getInventoryDatas()!=null && !itemData.getInventoryDatas().isEmpty()) {
				 itemVo.setAllowedPrefixChange(false);
			 } else {
				 itemVo.setAllowedPrefixChange(true);
			 }
			 
			 if(itemData.getPrefix()!=null && !itemData.getPrefix().isEmpty()) {
				 itemVo.setInventoryGeneration(1L);
			 } else {
				itemVo.setInventoryGeneration(2L); 
			 }
			 itemVo.setPrefix(itemData.getPrefix());
			
		 }
			 return itemVo;
		 
	 }
	
		
	 public static ItemData getItemData(ItemVO itemVo){
		 
		 ItemData itemData=new ItemData();
//		 ResourceTypeData resourceTypeData=new ResourceTypeData();
		 if(itemVo!=null){
			 itemData.setItemId(itemVo.getItemId());
			 itemData.setName(itemVo.getName());
			 itemData.setDescription(itemVo.getDescription());
			 itemData.setReferenceID(itemVo.getReferenceID());
			 itemData.setModelnumber(itemVo.getModelnumber());
			 itemData.setVendor(itemVo.getVendor());
			 itemData.setCreatedate(getCurrentTimestamp());
			 itemData.setUpdatedate(itemVo.getUpdatedate());
			 itemData.setCreatedby(itemVo.getCreatedby());
			 itemData.setUpdatedby(itemVo.getUpdatedby());
			 itemData.setSystemgenerated(itemVo.getSystemgenerated());
			 itemData.setEditable(itemVo.getEditable());
			 itemData.setAlias(itemVo.getAlias());
			/* resourceTypeData.setResourceTypeId(itemVo.getResourceTypeVO().getResourceTypeId());
			 resourceTypeData.setName(itemVo.getResourceTypeVO().getResourceTypeName());
			 itemData.setResourceType(resourceTypeData);*/
			 itemData.setPrefix(itemVo.getPrefix());
			 itemData.setReason(itemVo.getReason());
			 
			 itemData.setResourceTypeId(itemVo.getResourceTypeVO().getResourceTypeId());
			 itemData.setResourceCategoryId(itemVo.getResourceCategoryVO().getResourceCategoryId());
			 itemData.setResourceSubTypeId(itemVo.getResourceTypeVO().getResourceSubTypeVO().getResourceSubTypeId());
			 
		 }
			 return itemData;
		 
	 }

	
	
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}


	public static SearchResourceSubTypeVO convertResourceSubTypeData(
			ResourceSubTypeData typeData) {
		
		SearchResourceSubTypeVO typeVO = new SearchResourceSubTypeVO();
		typeVO.setName(typeData.getName());
		typeVO.setDescription(typeData.getDescription());
		typeVO.setAlias(typeData.getAlias());
		typeVO.setResourceSubTypeId(typeData.getResourceSubTypeId());
		typeVO.setResourceTypeId(typeData.getResourceType().getResourceTypeId());
		typeVO.setResourceTypeName(typeData.getResourceType().getName());
		
		typeVO.setCreateDate(typeData.getCreatedate());
		typeVO.setUpdatedDate(typeData.getLastmodifieddate());
		
		try {
			typeVO.setCreatedBy(UserFactory.findUserById(typeData.getCreatedby()).getName());
			if(typeData.getLastmodifiedby()!=null){
				Logger.logTrace(MODULE, "inside convertResourceSubTypeData() typeData.getLastmodifiedby() not null");
			typeVO.setUpdatedBy(UserFactory.findUserById(typeData.getLastmodifiedby()).getName());
			}
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (NoSuchControllerException e) {
			e.printStackTrace();
		}
		
		return typeVO;
	}


	public static SearchResourceTypeVO convertResourceTypeData(
			ResourceTypeData typeData, IBLSession blSession) {
		
		SearchResourceTypeVO typeVO = new SearchResourceTypeVO();
		typeVO.setName(typeData.getName());
		typeVO.setAlias(typeData.getAlias());
		typeVO.setDescription(typeData.getDescription());
		typeVO.setResourceTypeId(typeData.getResourceTypeId());
		
		typeVO.setCreateDate(typeData.getCreatedate());
		typeVO.setUpdatedDate(typeData.getLastmodifieddate());
		
		try {
			typeVO.setCreatedBy(UserFactory.findUserById(typeData.getCreatedby()).getName());
			typeVO.setUpdatedBy(UserFactory.findUserById(typeData.getLastmodifiedby()).getName());
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (NoSuchControllerException e) {
			e.printStackTrace();
		}
		
		
		
		return typeVO;
	}


	public static ItemVO getitemVoComposer(ItemData itemData) {
		
		 ItemVO itemVo=new ItemVO();
		 ResourceTypeVO resourceTypeVO=new ResourceTypeVO();
		 if(itemData!=null){
			 itemVo.setItemId(itemData.getItemId());
			 itemVo.setName(itemData.getName());
			 itemVo.setDescription(itemData.getDescription());
			 itemVo.setReferenceID(itemData.getReferenceID());
			 itemVo.setResourceNumber(itemData.getResourceNumber());
			 itemVo.setModelnumber(itemData.getModelnumber());
			 itemVo.setVendor(itemData.getVendor());
			 itemVo.setCreatedate(itemData.getCreatedate());
			 itemVo.setUpdatedate(itemData.getUpdatedate());
			 
			 
			 try {
				 itemVo.setCreatedby(UserFactory.findUserById(itemData.getCreatedby()).getName());
				
				} catch (SearchBLException e) {
					e.printStackTrace();
				} catch (NoSuchControllerException e) {
					e.printStackTrace();
				}
			 
			 itemVo.setSystemgenerated(itemData.getSystemgenerated());
			 itemVo.setEditable(itemData.getEditable());
			 itemVo.setAlias(itemData.getAlias());
			 itemVo.setReason(itemData.getReason());
			 resourceTypeVO.setResourceTypeId(itemData.getResourceType().getResourceTypeId());
			 resourceTypeVO.setResourceTypeName(itemData.getResourceType().getName());
			 
			 if(itemData.getResourceSubTypeData()!=null) {
				 ResourceSubTypeVO subTypeVO = new ResourceSubTypeVO();
				 subTypeVO.setResourceSubTypeId(itemData.getResourceSubTypeData().getResourceSubTypeId());
				 subTypeVO.setResourceSubTypeName(itemData.getResourceSubTypeData().getName());
				 resourceTypeVO.setResourceSubTypeVO(subTypeVO);
			 }
			 if(itemData.getResourceCategory()!=null) {
				 ResourceCategoryVO categoryVO = new ResourceCategoryVO();
				 categoryVO.setResourceCategoryId(itemData.getResourceCategory().getResourceCategoryId());
				 categoryVO.setResourceCategoryName(itemData.getResourceCategory().getName());
				 itemVo.setResourceCategoryVO(categoryVO);
			 }
			 
			
			 
			 itemVo.setResourceTypeVO(resourceTypeVO);
			 
			 if(itemData.getInventoryDatas()!=null && !itemData.getInventoryDatas().isEmpty()) {
				 itemVo.setAllowedPrefixChange(false);
			 } else {
				 itemVo.setAllowedPrefixChange(true);
			 }
			 
			 if(itemData.getPrefix()!=null && !itemData.getPrefix().isEmpty()) {
				 itemVo.setInventoryGeneration(1L);
			 } else {
				itemVo.setInventoryGeneration(2L); 
			 }
			 itemVo.setPrefix(itemData.getPrefix());
			
		 }
			 return itemVo;
		
	}


	public static NotificationData prepareNotificationDataOnResourceCreation(ItemData itemdata,String to,String cc, String subject) {
		
		try {
//			Properties properties = readProperty("ResourceTemplate");
			Map<String, String> map = prepareMapForNotify(itemdata);
			
			
			NotificationData data = new NotificationData();
			data.setAlias(NotificationConstants.CREATE_RESOURCE);
			data.setValueMap(map);
			data.setToEmails(Arrays.asList(to));
			data.setCcEmails(Arrays.asList(cc));
			
			
//			String template = properties.getProperty("resource");
			
//			NotificationUtil.sendMail(map, template, to, cc, subject);
			
			return data;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static Map<String, String> prepareMapForNotify(ItemData itemdata) {
		
		Map<String, String> map = new HashMap<String, String>();
		if(itemdata!=null) {
			map.put(NotificationConstants.CPE_RESOURCE_NAME, itemdata.getName());
			map.put(NotificationConstants.CPE_MODEL_NUMBER, itemdata.getModelnumber());
			map.put(NotificationConstants.CPE_VENDOR, itemdata.getVendor());
			map.put(NotificationConstants.CPE_DESCRIPTION, itemdata.getDescription());
			map.put(NotificationConstants.CPE_REFERENCEID, itemdata.getReferenceID());
			map.put(NotificationConstants.CPE_RESOURCE_NUMBER, itemdata.getResourceNumber());
			if(itemdata.getResourceType()!=null) {
				map.put(NotificationConstants.CPE_RESOURCE_TYPENAME, itemdata.getResourceType().getName());
			}
			if(itemdata.getResourceSubTypeData()!=null) {
				map.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME, itemdata.getResourceSubTypeData().getName());
			}else{
				map.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME," ");
			}
			
		}
		
		return map;
	}


	public static Properties readProperty(String filename){
		
		Properties  props = new Properties();
		try{
			ResourceBundle rb = ResourceBundle.getBundle(filename);
			Enumeration <String> keys = rb.getKeys();
		
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				props.put(key, rb.getString(key));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return props;
	}


	public static NotificationData prepareNotificationDataOnTransfer(
			TransferOrderData transferOrderData,String flag,InventoryData inventoryData,int size) {
		NotificationData  notificationData = new NotificationData();
		notificationData.setAlias(flag);
		List<String> toEmail = new ArrayList<String>();
		toEmail.add(transferOrderData.getFromWarehouseData().getEmailId());
		toEmail.add(transferOrderData.getToWarehouseData().getEmailId());
		notificationData.setToEmails(toEmail);
		
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put(NotificationConstants.CPE_FROM_WAREHOUSE, transferOrderData.getFromWarehouseData().getName());
		valueMap.put(NotificationConstants.CPE_TO_WAREHOUSE, transferOrderData.getToWarehouseData().getName());
		valueMap.put(NotificationConstants.CPE_ORDER_NUMBER, transferOrderData.getTransferOrderNo());
		valueMap.put(NotificationConstants.CPE_REMARK, transferOrderData.getRemarks());
		valueMap.put(NotificationConstants.CPE_QUANTITY, size+"");
		
		if(inventoryData!=null) {
			valueMap.put(NotificationConstants.CPE_RESOURCE_NUMBER, inventoryData.getItemData().getResourceNumber());
			valueMap.put(NotificationConstants.CPE_RESOURCE_NAME, inventoryData.getItemData().getName());
			valueMap.put(NotificationConstants.CPE_RESOURCE_TYPENAME, inventoryData.getItemData().getResourceType().getName());
			if(inventoryData.getItemData()!=null && inventoryData.getItemData().getResourceSubTypeData()!=null){
				valueMap.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME, inventoryData.getItemData().getResourceSubTypeData().getName());
			}
			else{
				valueMap.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME," ");
			}
			
		}
		
		notificationData.setValueMap(valueMap);
		
		return notificationData;
	}


	public static NotificationData prepareNotificationDataOnTransferAcceptReject(
			SearchTransferInventory transferOrderVO,WarehouseData wareHouseDataFrom,WarehouseData wareHouseDataTo,InventoryData inventoryData,int acceptedSize) {
		
		NotificationData  notificationData = new NotificationData();
		notificationData.setAlias(NotificationConstants.ACCEPT_REJECT_TRANSFER_ORDER);
		List<String> toEmail = new ArrayList<String>();
		
		if(wareHouseDataFrom!=null) {
			toEmail.add(wareHouseDataFrom.getEmailId());
		}
		if(wareHouseDataTo!=null) {
			toEmail.add(wareHouseDataTo.getEmailId());
		}
		notificationData.setToEmails(toEmail);
		
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put(NotificationConstants.CPE_FROM_WAREHOUSE, transferOrderVO.getFromWarehouseName());
		valueMap.put(NotificationConstants.CPE_TO_WAREHOUSE, transferOrderVO.getToWarehouseName());
		valueMap.put(NotificationConstants.CPE_ORDER_NUMBER, transferOrderVO.getOrderNo());
		valueMap.put(NotificationConstants.CPE_REMARK, transferOrderVO.getRemark());
		valueMap.put(NotificationConstants.CPE_QUANTITY,  acceptedSize+"");
		
		if(inventoryData!=null) {
			valueMap.put(NotificationConstants.CPE_RESOURCE_NUMBER, inventoryData.getItemData().getResourceNumber());
			valueMap.put(NotificationConstants.CPE_RESOURCE_NAME, inventoryData.getItemData().getName());
			valueMap.put(NotificationConstants.CPE_RESOURCE_TYPENAME, inventoryData.getItemData().getResourceType().getName());
		//	valueMap.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME, inventoryData.getItemData().getResourceSubTypeData().getName());
			if(inventoryData.getItemData()!=null && inventoryData.getItemData().getResourceSubTypeData()!=null){
				valueMap.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME, inventoryData.getItemData().getResourceSubTypeData().getName());
			}
			else{
				valueMap.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME," ");
			}
		
		}
		
		notificationData.setValueMap(valueMap);
		
		return notificationData;
	}
	
	public static NotificationData prepareNotificationDataOnTransferAcceptReject(
			PartialAcceptRejectTransferOrderVO transferOrderVO,WarehouseData wareHouseDataFrom,WarehouseData wareHouseDataTo,InventoryData inventoryData,int acceptedSize) {
		
		NotificationData  notificationData = new NotificationData();
		notificationData.setAlias(NotificationConstants.ACCEPT_REJECT_TRANSFER_ORDER);
		List<String> toEmail = new ArrayList<String>();
		
		if(wareHouseDataFrom!=null) {
			toEmail.add(wareHouseDataFrom.getEmailId());
		}
		if(wareHouseDataTo!=null) {
			toEmail.add(wareHouseDataTo.getEmailId());
		}
		notificationData.setToEmails(toEmail);
		
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put(NotificationConstants.CPE_FROM_WAREHOUSE, transferOrderVO.getFromWarehouseName());
		valueMap.put(NotificationConstants.CPE_TO_WAREHOUSE, transferOrderVO.getToWarehouseName());
		valueMap.put(NotificationConstants.CPE_ORDER_NUMBER, transferOrderVO.getOrderNo());
		valueMap.put(NotificationConstants.CPE_REMARK, transferOrderVO.getRemark());
		valueMap.put(NotificationConstants.CPE_QUANTITY, acceptedSize+"");
		
		if(inventoryData!=null) {
			valueMap.put(NotificationConstants.CPE_RESOURCE_NUMBER, inventoryData.getItemData().getResourceNumber());
			valueMap.put(NotificationConstants.CPE_RESOURCE_NAME, inventoryData.getItemData().getName());
			valueMap.put(NotificationConstants.CPE_RESOURCE_TYPENAME, inventoryData.getItemData().getResourceType().getName());
			if(inventoryData.getItemData()!=null && inventoryData.getItemData().getResourceSubTypeData()!=null){
				valueMap.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME, inventoryData.getItemData().getResourceSubTypeData().getName());
			}
			else{
				valueMap.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME," ");
			}
		
			//	valueMap.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME, inventoryData.getItemData().getResourceSubTypeData().getName());
		}
		
		
		notificationData.setValueMap(valueMap);
		
		return notificationData;
	}
	
}
