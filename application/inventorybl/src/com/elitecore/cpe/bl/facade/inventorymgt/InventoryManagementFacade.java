package com.elitecore.cpe.bl.facade.inventorymgt;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.rowset.CachedRowSet;

import com.elitecore.cpe.bl.constants.inventorymgt.InventoryStatusConstants;
import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.notification.NotificationConstants;
import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.data.system.audit.AuditQueueData;
import com.elitecore.cpe.bl.data.system.audit.AuditSummaryDetail;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.BatchData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.BatchSummaryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InvalidInventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusLogData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusTransition;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventorySubStatusTransition;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.OrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.ResourceAttributeRel;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderDetailData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.WarehouseInventoryStatusData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeTransData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.entity.inventory.system.agent.history.SystemAgentRunQueue;
import com.elitecore.cpe.bl.entity.inventory.transfer.InventoryTransferOrderStatus;
import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.master.item.ItemUtil;
import com.elitecore.cpe.bl.facade.master.warehouse.WarehouseUtil;
import com.elitecore.cpe.bl.facade.system.audit.AuditDataConversionUtilities;
import com.elitecore.cpe.bl.facade.system.systemparameter.SystemParameterFacadeLocal;
import com.elitecore.cpe.bl.facade.system.user.UserFacadeLocal;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.session.inventorymgt.BatchManagementSessionBeanLocal;
import com.elitecore.cpe.bl.session.inventorymgt.InventoryManagementSessionBeanLocal;
import com.elitecore.cpe.bl.session.inventorytransfer.InventoryTransferSessionBeanLocal;
import com.elitecore.cpe.bl.session.master.attribute.AttributeSessionBeanLocal;
import com.elitecore.cpe.bl.session.master.item.ItemSessionBeanLocal;
import com.elitecore.cpe.bl.session.master.warehouse.WarehouseSessionBeanLocal;
import com.elitecore.cpe.bl.session.system.agent.SystemAgentSessionBeanLocal;
import com.elitecore.cpe.bl.session.system.internal.SystemInternalSessionBeanLocal;
import com.elitecore.cpe.bl.vo.inventorymgt.BatchSummaryVO;
import com.elitecore.cpe.bl.vo.inventorymgt.BulkChangeInventoryStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.ChangeInventoryStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.ChangeInventorySubStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryBatchViewVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryStatusLogVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryWrapperVO;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderNotificationEmailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchWarehouseInventoryStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.TransferInventorySummaryViewVO;
import com.elitecore.cpe.bl.vo.inventorymgt.migration.AttributeMigrationVO;
import com.elitecore.cpe.bl.vo.inventorymgt.migration.InventoryMigrationResponseVO;
import com.elitecore.cpe.bl.vo.inventorymgt.migration.InventoryMigrationVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.PartialAcceptRejectTransferOrderVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.PartialAcceptRejectTransferOrderVO.InventoryVO;
import com.elitecore.cpe.bl.vo.order.OrderDetailVo;
import com.elitecore.cpe.bl.vo.order.TransferOrderVO;
import com.elitecore.cpe.bl.vo.system.user.UserVO;
import com.elitecore.cpe.bl.ws.data.input.request.InventoryDetailsRequestData;
import com.elitecore.cpe.bl.ws.data.input.response.InventoryDetailsResponseData;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;
import com.sun.rowset.CachedRowSetImpl;

@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class InventoryManagementFacade extends BaseFacade implements InventoryManagementFacadeRemote,InventoryManagementFacadeLocal{

	private static final String MODULE = "INVENTORYMGT-FC";	
	
	@EJB private SystemInternalSessionBeanLocal systemInternalSessionBeanLocal;
	@EJB private WarehouseSessionBeanLocal warehouseSessionBeanLocal;
	@EJB private InventoryManagementSessionBeanLocal inventoryManagementSessionBeanLocal;
	@EJB private BatchManagementSessionBeanLocal batchManagementSessionBeanLocal;
	@EJB private AttributeSessionBeanLocal attributeSessionBeanLocal;
	@EJB private ItemSessionBeanLocal itemSessionBeanLocal;
	@EJB private SystemParameterFacadeLocal systemParameterFacadeLocal;
	@EJB private InventoryTransferSessionBeanLocal transferSessionBeanLocal;
	@EJB private SystemAgentSessionBeanLocal systemAgentSessionBeanLocal;
	@EJB private UserFacadeLocal userFacadeLocal;
	
	private Object transferObj = new Object(); 
	
	
	/**
	 * Upload Migration Inventory used for Migration Purpose only
	 * @author yash.kapasi
	 * @param {@link List}<{@link InventoryMigrationVO}> inventoryMigrationVOs
	 * @param isValidate
	 */
	@Override
	public InventoryMigrationResponseVO uploadMigrationInventory(List<InventoryMigrationVO> inventoryMigrationVOs, boolean isValidate) {
		
		InventoryMigrationResponseVO inventoryMigrationResponseVO = null;
		
		try {
			
			IBDSessionContext sessionContext = null;
			sessionContext = userFacadeLocal.doLogin("webservice", "webservice", "127.0.0.1"); 
			/*Map<String, UserVO> map = UserFactory.findAllUser();
			if(map!=null && map.containsKey(UserConstants.ADMIN_USERID)) {
				UserVO admin = map.get(UserConstants.ADMIN_USERID);
				
				try {
					sessionContext = userFacadeLocal.doLogin(admin.getUsername(), admin.getPassword(), "127.0.0.1");
				} catch (Exception e) {
					e.printStackTrace();
					 sessionContext = userFacadeLocal.doLogin("webservice", "webservice", "127.0.0.1");
				}
			} else {
				 sessionContext = userFacadeLocal.doLogin("webservice", "webservice", "127.0.0.1"); 
			}*/
			
			
			InventoryUploadVO inventoryUploadVO = uploadInventoryGeneric(inventoryMigrationVOs, sessionContext.getBLSession(),isValidate);
			if(inventoryUploadVO!=null) {
				inventoryMigrationResponseVO = new InventoryMigrationResponseVO();
				inventoryMigrationResponseVO.setValidEntry(inventoryUploadVO.getValidEntry());
				inventoryMigrationResponseVO.setInvalidEntry(inventoryUploadVO.getInvalidEntry());
				inventoryMigrationResponseVO.setBatchNo(inventoryUploadVO.getBatchNo());
			}
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return inventoryMigrationResponseVO;
		
	}
	
	
	/**
	 * Generic Method to Upload Inventory
	 * @author yash.kapasi
	 * @param {@link List}<{@link InventoryMigrationVO}> inventoryVOs
	 * @param IBLSession 
	 * @param isValidate
	 * @return InventoryUploadVO
	 * @throws Exception
	 */
	public InventoryUploadVO uploadInventoryGeneric(List<InventoryMigrationVO> inventoryVOs,IBLSession iblSession, boolean isValidate) throws Exception {
		
		InventoryUploadVO uploadresponseVO = null;
		
		Logger.logTrace(MODULE, "inside Upload Generic Inventory");
		
		if(inventoryVOs!=null && !inventoryVOs.isEmpty()) {
		
			Logger.logTrace(MODULE, "inside Upload Generic Inventory isValidate :" +isValidate);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				
				String uploadStatus = systemParameterFacadeLocal.getSystemParameterValue("AUTOMATIC_INVENTORY_UPLOAD");
				List<AttributeData> attriList = null;
	        	LinkedHashMap<String,Object> fieldToValueMap = new LinkedHashMap<String, Object>();
	        	fieldToValueMap.put("systemgenerated", "N");
	        	fieldToValueMap.put("usedBy", "Resource");
	        	List filterData = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.ATTRIBUTE_DATA, fieldToValueMap);
	        	if(filterData != null && !filterData.isEmpty()){
	        		attriList     = (List<AttributeData>)filterData;
	        	}
				
				Map<String, AttributeData> attributeNameToIdMap = new HashMap<String, AttributeData>();
		    	Map<Integer,String> columnNoToNameMap    = new HashMap<Integer, String>();
		    	Map<Long,BatchSummaryData> resourceIdToBatchSummaryMap = new HashMap<Long, BatchSummaryData>();  
		    	ArrayList<InventoryWrapperVO> inventoryDatas = new ArrayList<InventoryWrapperVO>();
		    	HashMap<String, HashSet<String>> attributeUniquevalueMap = new HashMap<String, HashSet<String>>();
		    	Map<String,WarehouseInventoryStatusData> whResourceIdToWarehouseInventoryStatusMap = new HashMap<String, WarehouseInventoryStatusData>();
		    	
		    	if(attriList != null){
		        	for(AttributeData data: attriList){
		        		attributeNameToIdMap.put(data.getName(), data);
		        		if(data.getUnique() == 'Y'){
		        			attributeUniquevalueMap.put(data.getName(), new HashSet<String>());
		        		}
		        		
		        		Logger.logTrace(MODULE,data.getName()+" :: "+data.getAttributeId());
		        	}
	        	}
		    	
		    	Map<String, Object> fieldToValueMapWareHouse = new LinkedHashMap<String, Object>();
		    	fieldToValueMapWareHouse.put("systemgenerated", "N");
				List<WarehouseData> filterDataWh = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldToValueMapWareHouse);
				Map<String, WarehouseData> mapWH = new HashMap<String, WarehouseData>();
		    	if(filterDataWh!=null && !filterDataWh.isEmpty()) {
		    		for(WarehouseData warehouseData : filterDataWh) {
		    			mapWH.put(warehouseData.getName(), warehouseData);
		    		}
		    	}
		    	
		    	Map<String, Object> fieldToValueMapItem = new LinkedHashMap<String, Object>();
		    	fieldToValueMapItem.put("systemgenerated", "N");
				List<ItemData> filterDataItem = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.ITEM_DATA, fieldToValueMapItem);
				Map<String, ItemData> mapItem = new HashMap<String, ItemData>();
		    	if(filterDataItem!=null && !filterDataItem.isEmpty()) {
		    		for(ItemData item : filterDataItem) {
		    			mapItem.put(item.getResourceNumber(), item);
		    		}
		    	}
		    	
		    	
		    	String query = "select o.inventoryNo from InventoryData o";
				List<String> filterDataList1 = warehouseSessionBeanLocal.searchQuery(query);
				Map<String, String> mapInventory = new HashMap<String, String>();
		    	if(filterDataList1!=null && !filterDataList1.isEmpty()) {
		    		for(String item : filterDataList1) {
		    			mapInventory.put(item, item);
		    		}
		    	}
		    	
		    	
		    		BatchData batchData = new BatchData();
					batchData.setBatchNo(systemInternalSessionBeanLocal.getPrimaryKey(CPECommonConstants.BATCH_DATA));
					batchData.setCreatedate(WarehouseUtil.getCurrentTimestamp());
					batchData.setCreatedby(iblSession.getSessionUserId());
					batchData.setTotalValidInventory(0L);
					batchData.setTotalInvalidInventory(0L);
					batchData = batchManagementSessionBeanLocal.createBatch(batchData);
		    	
				if(isValidate)	 {
					
					StringBuilder errorStr = null;
					for(InventoryMigrationVO migrationVO : inventoryVOs) {
						errorStr = new StringBuilder();
						boolean validateRecord = true;
						InventoryWrapperVO inventoryWrapperVO = new InventoryWrapperVO();
						List<AttributeTransData> attributeTransList = new ArrayList<AttributeTransData>();
						WarehouseData warehouseData = null;
						ItemData itemData = null;
						List<String> inventoryList = new ArrayList<String>();
						Map<Long,AttributeTransData> attributeTransMap = new HashMap<Long, AttributeTransData>();
						
						if(migrationVO.getBatchNumber()==null || migrationVO.getBatchNumber().isEmpty()) {
							errorStr.append(" and Batch Number can not be null or empty :"+migrationVO.getBatchNumber());
							validateRecord = false;
						}
						
						if(mapWH.containsKey(migrationVO.getWareHouseName())) {
							warehouseData = mapWH.get(migrationVO.getWareHouseName());
						} else {
							errorStr.append(" and Invalid Warehouse:"+migrationVO.getWareHouseName());
							validateRecord = false;
						}
						
						if(migrationVO.getResourceNumber()==null || migrationVO.getResourceNumber().isEmpty()) {
							if(migrationVO.getResourceName()!=null && migrationVO.getResourceType()!=null 
									&& migrationVO.getResourceSubType()!=null) {
								List<ItemData> itemDatas = itemSessionBeanLocal.findResource(migrationVO.getResourceName(),migrationVO.getResourceType(),migrationVO.getResourceSubType());
								if(itemDatas!=null && !itemDatas.isEmpty()) {
									itemData = itemDatas.get(0);
								} else {
									errorStr.append(" and Resource not found with given name,type and subtype");
									validateRecord = false;
								}
								
							} else {
								errorStr.append(" and ResourceNumber or Resource Subtype or resourcetype can not be null");
								validateRecord = false;
							}
						} else {
							if(mapItem.containsKey(migrationVO.getResourceNumber())) {
								itemData = mapItem.get(migrationVO.getResourceNumber());
							} else {
								errorStr.append(" and Invalid Item:"+migrationVO.getResourceNumber());
								validateRecord = false;
							}
						}
						
						if(itemData!=null && itemData.getPrefix()!=null && !itemData.getPrefix().isEmpty()) {
							
//							System.out.println("Do nothing");
						} else {
							
							boolean isChecked = false;
							
							if(migrationVO.getInventoryNumber()==null || migrationVO.getInventoryNumber().isEmpty()) {
								errorStr.append(" and Inventory Number can not be null or empty in manual resource:"+migrationVO.getInventoryNumber());
								validateRecord = false;
							}
							
							if(mapInventory.containsKey(migrationVO.getInventoryNumber())) {
								errorStr.append(" and Not Unique Inventory Number :"+migrationVO.getInventoryNumber());
								validateRecord = false;
								isChecked = true;
							} 
							
							if(!isChecked) {
								if(inventoryList.contains(migrationVO.getInventoryNumber())) {
									errorStr.append(" and Not Unique Inventory Number :"+migrationVO.getInventoryNumber());
									validateRecord = false;
								} else {
									inventoryList.add(migrationVO.getInventoryNumber());
								}
							}
							
							if(migrationVO.getInventoryNumber().length()>20) {
								errorStr.append(" and Inventory Number must not exceed length 20 :"+migrationVO.getInventoryNumber());
								validateRecord = false;
							}
						}
						
						if(migrationVO.getGuaranteeWarrantyMode()==null || migrationVO.getGuaranteeWarrantyMode().isEmpty()) {
							errorStr.append(" and GuaranteeWarrantyMode can not be null or empty:"+migrationVO.getGuaranteeWarrantyMode());
							validateRecord = false;
						} else {
							if(migrationVO.getGuaranteeWarrantyMode().equals("Guarantee") || migrationVO.getGuaranteeWarrantyMode().equals("Warranty")) {
								if(migrationVO.getGuaranteeWarrantyMode().equals("Warranty")) {
									if(migrationVO.getWarrantyDate()!=null && 
											(migrationVO.getWarrantyType().equals("Supplier date") || migrationVO.getWarrantyType().equals("Purchase date"))  ) {
										
										try {
											 dateFormat.parse(migrationVO.getWarrantyDate());
											
										} catch (Exception e) {
											e.printStackTrace();
											errorStr.append(" and valid Date format for warrantyDate is dd/MM/yyyy");
											validateRecord = false;
										}
										
										Logger.logTrace(MODULE, "Valid Gurantee/Warranty");
									} else {
										errorStr.append(" and with mode as Warranty , warranty date is mandatory and warrantyType can be Supplier date/Purchase date:"+migrationVO.getGuaranteeWarrantyMode());
										validateRecord = false;
									}
								}
							} else {
								errorStr.append(" and GuaranteeWarrantyMode must be either Warranty or Guarantee:"+migrationVO.getGuaranteeWarrantyMode());
								validateRecord = false;
							}
						}
						
						if(migrationVO.getAttributeMigrationVOs()!=null && !migrationVO.getAttributeMigrationVOs().isEmpty()) {
							for(AttributeMigrationVO attributeMigrationVO : migrationVO.getAttributeMigrationVOs()) {
								
								if(attributeNameToIdMap.get(attributeMigrationVO.getKey()) != null){
									
									if(attributeNameToIdMap.containsKey(attributeMigrationVO.getKey())) {
										AttributeTransData transData = new AttributeTransData();
										transData.setAttributeId(attributeNameToIdMap.get(attributeMigrationVO.getKey()).getAttributeId());
										transData.setValue(attributeMigrationVO.getValue());
										attributeTransMap.put(attributeNameToIdMap.get(attributeMigrationVO.getKey()).getAttributeId(), transData);
									}
								}
								
							}
						}
						
						
						if(itemData!=null && itemData.getResourceAttributeRels()!=null)	{
							for(ResourceAttributeRel resourceAttributeRel : itemData.getResourceAttributeRels()){
								boolean validateAttributeData = true;
								
								if(attributeTransMap.containsKey(resourceAttributeRel.getAttribute().getAttributeId())){
									
									AttributeTransData attributeTransData = attributeTransMap.get(resourceAttributeRel.getAttribute().getAttributeId());
									
									String attributeName = resourceAttributeRel.getAttribute().getName();
									String attributeValue = attributeTransData.getValue();
									
									if(resourceAttributeRel.getAttribute().getMandatory() == 'Y' && (attributeTransData.getValue() == null || 
											attributeTransData.getValue().trim().equals("")) ){
										errorStr.append(" and Invalid "+resourceAttributeRel.getAttribute().getName()+ " empty Value" );
										validateAttributeData = false;
										validateRecord = false;
									}
									if(resourceAttributeRel.getAttribute().getUnique() == 'Y'){
										
										if(attributeUniquevalueMap.get(attributeName).contains(attributeValue)){
											errorStr.append(" and Attribute Name : "+attributeName+ " value :"+attributeValue+" as repeated" );
											validateAttributeData = false;
											validateRecord = false;
											
										}else if(!attributeSessionBeanLocal.isAttributeValueUnique(attributeNameToIdMap.get(attributeName).getAttributeId(), attributeValue, null)){
											// check in the database whether attribute value is repeating or not in unique constraint
											errorStr.append(" and Attribute Name : "+attributeName+ " value :"+attributeValue+" already exist in database" );
											validateAttributeData = false;
											validateRecord = false;
											
										}else{
											attributeUniquevalueMap.get(attributeName).add(attributeValue);
										}
									}
									
									
									if(resourceAttributeRel.getAttribute().getDataType() != null && resourceAttributeRel.getAttribute().getDataType().equalsIgnoreCase("Number")){
										for (int chindex = 0; chindex < attributeValue.length(); chindex++) {
											if (!Character.isDigit(attributeValue.charAt(chindex)) && attributeValue.charAt(chindex) != 46) {
												errorStr.append(" and Attribute Name : "+attributeName+ " value :"+attributeValue+" is not Numeric" ); 
												validateAttributeData = false;
												validateRecord = false;
												break;
											}
										}
									}
									attributeTransList.add(attributeTransData);
								}else if(resourceAttributeRel.getAttribute().getMandatory() == 'Y'){
									errorStr.append(" and Invalid "+resourceAttributeRel.getAttribute().getName()+ " empty Value" );
									validateAttributeData = false;
									validateRecord = false;
								}
								
							}
						}
						
						if(!validateRecord){
							InvalidInventoryData invalidInventoryData = new InvalidInventoryData();
							invalidInventoryData.setInventoryStatusId(InventoryStatusConstants.FAILED);
							invalidInventoryData.setBatchId(batchData.getBatchId());
							invalidInventoryData.setErrordesc(errorStr.substring(4, errorStr.length()));
							invalidInventoryData.setWarehouseName(migrationVO.getWareHouseName());
							invalidInventoryData.setItemRefName(migrationVO.getResourceNumber());
							inventoryWrapperVO.setInvalidInventoryData(invalidInventoryData);
						} else {
							InventoryData inventoryData = new InventoryData();
							inventoryData.setBatchId(batchData.getBatchId());
							inventoryData.setExternalBatchNumber(migrationVO.getBatchNumber());
							inventoryData.setWarehouseId(warehouseData.getWarehouseId());
							inventoryData.setItemId(itemData.getItemId());
							inventoryData.setGurrantyWarrantyMode(migrationVO.getGuaranteeWarrantyMode());
							if(migrationVO.getWarrantyDate()!=null) {
								Date date = dateFormat.parse(migrationVO.getWarrantyDate());
								inventoryData.setWarrantyDate(new Timestamp(date.getTime()));
							}
							if(migrationVO.getWarrantyType()!=null) {
								inventoryData.setWarrantyType(migrationVO.getWarrantyType());
							}
							if(migrationVO.getCreatedByStaffId()!=null && !migrationVO.getCreatedByStaffId().isEmpty()) {
								inventoryData.setCreatedby(migrationVO.getCreatedByStaffId());
							} else {
								inventoryData.setCreatedby(iblSession.getSessionUserId());
							}
							if(migrationVO.getCreateDate()!=null) {
								inventoryData.setCreatedate(new Timestamp(migrationVO.getCreateDate().getTime()));
							} else {
								inventoryData.setCreatedate(WarehouseUtil.getCurrentTimestamp());
							}
							if(migrationVO.getLastModifiedBy()!=null && !migrationVO.getLastModifiedBy().isEmpty()) {
								inventoryData.setUpdatedby(migrationVO.getLastModifiedBy());
							}
							
							if(itemData.getPrefix()!=null && !itemData.getPrefix().isEmpty()) {
								String alias = systemInternalSessionBeanLocal.getAliasByPrefix(itemData.getPrefix());
								inventoryData.setInventoryNo(systemInternalSessionBeanLocal.getPrimaryKey(alias));
							} else {
								inventoryData.setInventoryNo(migrationVO.getInventoryNumber());
							}
							
							
							inventoryData.setSystemgenerated('N');
							if(migrationVO.getExternalReferenceId()!=null && !migrationVO.getExternalReferenceId().isEmpty()) {
								inventoryData.setExternalRefId(migrationVO.getExternalReferenceId());
							} else {
								inventoryData.setExternalRefId(itemData.getResourceNumber());
							}
			/*				if(migrationVO.getAccepted()!=null && !migrationVO.getAccepted().isEmpty()) {
								inventoryData.setAccepted(migrationVO.getAccepted());
							}
							if(migrationVO.getRefurnished()!=null && !migrationVO.getRefurnished().isEmpty()) {
								inventoryData.setRefurbished(migrationVO.getRefurnished());
							}
							if(migrationVO.getStandBy()!=null && !migrationVO.getStandBy().isEmpty()) {
								inventoryData.setStandBy(migrationVO.getStandBy());
							}
							if(migrationVO.getNew()!=null && !migrationVO.getNew().isEmpty()) {
								inventoryData.setNewed(migrationVO.getNew());
							}*/
							if(migrationVO.getReservedDate()!=null) {
								inventoryData.setReserveddate(new Timestamp(migrationVO.getReservedDate().getTime()));
							}
							
							if(migrationVO.getStatus()!=null && !migrationVO.getStatus().isEmpty()) {
								Map<String, Object> fieldToValueMapstatus = new LinkedHashMap<String, Object>();
								fieldToValueMapstatus.put("systemGenrated", "N");
								fieldToValueMapstatus.put("name", migrationVO.getStatus());
								List<InventoryStatusData> filterDataStatus = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORYSTATUS_DATA, fieldToValueMapstatus);
						    	if(filterDataStatus!=null && !filterDataStatus.isEmpty()) {
						    		InventoryStatusData data = filterDataStatus.get(0);
						    		inventoryData.setInventoryStatusId(data.getInventoryStatusId().intValue());
						    	}
								
							} else {
								if(uploadStatus != null && !uploadStatus.equals("") 
										&& uploadStatus.equalsIgnoreCase("Automatic")){
									
									int currentStatus = 0;
									
									if(mapInventory!=null && !mapInventory.isEmpty()) {
										boolean isAvailable = false;
										
										isAvailable = inventoryManagementSessionBeanLocal.isAvailable(warehouseData.getWarehouseId(),inventoryData.getItemId());
										if(isAvailable) {
											currentStatus = InventoryStatusConstants.AVAILABLE;
										}
										
										if(currentStatus == InventoryStatusConstants.AVAILABLE) {
											inventoryData.setInventoryStatusId(InventoryStatusConstants.AVAILABLE);
											//Commented  below line and added new line
											//inventoryData.setNewed("Y");
											inventoryData.setInventorySubStatusId(Long.valueOf((InventoryStatusConstants.NEW)));
										}else{
											inventoryData.setInventoryStatusId(InventoryStatusConstants.NEW);
										}
									} else {
										inventoryData.setInventoryStatusId(InventoryStatusConstants.NEW);
									}
								}else{
									inventoryData.setInventoryStatusId(InventoryStatusConstants.NEW);
								}
							}
							
							
							inventoryWrapperVO.setInventoryData(inventoryData);
							
							// prepare data for batchsummary data
							BatchSummaryData batchSummaryData = null;
							if(resourceIdToBatchSummaryMap.get(inventoryData.getItemId()) == null){
								batchSummaryData = new BatchSummaryData();
								batchSummaryData.setResourceId(inventoryData.getItemId());
								batchSummaryData.setBatchId(batchData.getBatchId());
								batchSummaryData.setNumberFrom(inventoryData.getInventoryNo());
								
							}else{
								batchSummaryData = resourceIdToBatchSummaryMap.get(inventoryData.getItemId());
							}
							batchSummaryData.setNumberTo(inventoryData.getInventoryNo());
							if(batchSummaryData.getTotalInvetoryByItem() == null){
								batchSummaryData.setTotalInvetoryByItem(0L);
							}
							batchSummaryData.setTotalInvetoryByItem(batchSummaryData.getTotalInvetoryByItem() + 1);
							resourceIdToBatchSummaryMap.put(inventoryData.getItemId(), batchSummaryData);
						}
						inventoryWrapperVO.setAttributeTransDatas(attributeTransList);
						
						inventoryDatas.add(inventoryWrapperVO);
					}
					
				} else {
					
					
					for(InventoryMigrationVO migrationVO : inventoryVOs) {
						
						InventoryWrapperVO inventoryWrapperVO = new InventoryWrapperVO();
						List<AttributeTransData> attributeTransList = new ArrayList<AttributeTransData>();
						Map<Long,AttributeTransData> attributeTransMap = new HashMap<Long, AttributeTransData>();
						
						
						WarehouseData warehouseData = mapWH.get(migrationVO.getWareHouseName());
						if(warehouseData==null) {
							throw new CreateBLException("Warehouse Not present with given WareHouseName");
						}
						ItemData itemData = null;
						
						if(migrationVO.getResourceNumber()==null || migrationVO.getResourceNumber().isEmpty()) {
							if(migrationVO.getResourceName()!=null && migrationVO.getResourceType()!=null 
									&& migrationVO.getResourceSubType()!=null) {
								List<ItemData> itemDatas = itemSessionBeanLocal.findResource(migrationVO.getResourceName(),migrationVO.getResourceType(),migrationVO.getResourceSubType());
								if(itemDatas!=null && !itemDatas.isEmpty()) {
									itemData = itemDatas.get(0);
								} 
								
							} 
						} else {
							itemData = mapItem.get(migrationVO.getResourceNumber());
						}
						if(itemData==null) {
							throw new CreateBLException("Resource Not present with given resourceNumber");
						}
						
						if(migrationVO.getAttributeMigrationVOs()!=null && !migrationVO.getAttributeMigrationVOs().isEmpty()) {
							for(AttributeMigrationVO attributeMigrationVO : migrationVO.getAttributeMigrationVOs()) {
								
								if(attributeNameToIdMap.get(attributeMigrationVO.getKey()) != null){
									
									if(attributeNameToIdMap.containsKey(attributeMigrationVO.getKey())) {
										AttributeTransData transData = new AttributeTransData();
										transData.setAttributeId(attributeNameToIdMap.get(attributeMigrationVO.getKey()).getAttributeId());
										transData.setValue(attributeMigrationVO.getValue());
										attributeTransMap.put(attributeNameToIdMap.get(attributeMigrationVO.getKey()).getAttributeId(), transData);
									}
								}
								
							}
						}
						
						
						if(itemData!=null && itemData.getResourceAttributeRels()!=null)	{
							for(ResourceAttributeRel resourceAttributeRel : itemData.getResourceAttributeRels()){
								if(attributeTransMap.containsKey(resourceAttributeRel.getAttribute().getAttributeId())){
									AttributeTransData attributeTransData = attributeTransMap.get(resourceAttributeRel.getAttribute().getAttributeId());
									attributeTransList.add(attributeTransData);
								}
								
							}
						}
						
						InventoryData inventoryData = new InventoryData();
						inventoryData.setBatchId(batchData.getBatchId());
						inventoryData.setExternalBatchNumber(migrationVO.getBatchNumber());
						inventoryData.setWarehouseId(warehouseData.getWarehouseId());
						inventoryData.setItemId(itemData.getItemId());
						if(migrationVO.getCreatedByStaffId()!=null && !migrationVO.getCreatedByStaffId().isEmpty()) {
							inventoryData.setCreatedby(migrationVO.getCreatedByStaffId());
						} else {
							inventoryData.setCreatedby(iblSession.getSessionUserId());
						}
						if(migrationVO.getCreateDate()!=null) {
							inventoryData.setCreatedate(new Timestamp(migrationVO.getCreateDate().getTime()));
						} else {
							inventoryData.setCreatedate(WarehouseUtil.getCurrentTimestamp());
						}
						if(migrationVO.getLastModifiedBy()!=null && !migrationVO.getLastModifiedBy().isEmpty()) {
							inventoryData.setUpdatedby(migrationVO.getLastModifiedBy());
						}
						
						if(itemData.getPrefix()!=null && !itemData.getPrefix().isEmpty()) {
							String alias = systemInternalSessionBeanLocal.getAliasByPrefix(itemData.getPrefix());
							inventoryData.setInventoryNo(systemInternalSessionBeanLocal.getPrimaryKey(alias));
						} else {
							inventoryData.setInventoryNo(migrationVO.getInventoryNumber());
						}
						
						
						inventoryData.setSystemgenerated('N');
						if(migrationVO.getExternalReferenceId()!=null && !migrationVO.getExternalReferenceId().isEmpty()) {
							inventoryData.setExternalRefId(migrationVO.getExternalReferenceId());
						} else {
							inventoryData.setExternalRefId(itemData.getResourceNumber());
						}
	/*					if(migrationVO.getAccepted()!=null && !migrationVO.getAccepted().isEmpty()) {
							inventoryData.setAccepted(migrationVO.getAccepted());
						}
						if(migrationVO.getRefurnished()!=null && !migrationVO.getRefurnished().isEmpty()) {
							inventoryData.setRefurbished(migrationVO.getRefurnished());
						}
						if(migrationVO.getStandBy()!=null && !migrationVO.getStandBy().isEmpty()) {
							inventoryData.setStandBy(migrationVO.getStandBy());
						}
						if(migrationVO.getNew()!=null && !migrationVO.getNew().isEmpty()) {
							inventoryData.setNewed(migrationVO.getNew());
						}
*/						if(migrationVO.getReservedDate()!=null) {
							inventoryData.setReserveddate(new Timestamp(migrationVO.getReservedDate().getTime()));
						}
						
						
						if(migrationVO.getStatus()!=null && !migrationVO.getStatus().isEmpty()) {
							Map<String, Object> fieldToValueMapstatus = new LinkedHashMap<String, Object>();
							fieldToValueMapstatus.put("systemGenrated", "N");
							fieldToValueMapstatus.put("name", migrationVO.getStatus());
							List<InventoryStatusData> filterDataStatus = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORYSTATUS_DATA, fieldToValueMapstatus);
					    	if(filterDataStatus!=null && !filterDataStatus.isEmpty()) {
					    		InventoryStatusData data = filterDataStatus.get(0);
					    		inventoryData.setInventoryStatusId(data.getInventoryStatusId().intValue());
					    	}
							
						} else {
							if(uploadStatus != null && !uploadStatus.equals("") 
									&& uploadStatus.equalsIgnoreCase("Automatic")){
								
								int currentStatus = 0;
								
								if(mapInventory!=null && !mapInventory.isEmpty()) {
									boolean isAvailable = false;
									
									isAvailable = inventoryManagementSessionBeanLocal.isAvailable(warehouseData.getWarehouseId(),inventoryData.getItemId());
									if(isAvailable) {
										currentStatus = InventoryStatusConstants.AVAILABLE;
									}
									
									if(currentStatus == InventoryStatusConstants.AVAILABLE) {
										inventoryData.setInventoryStatusId(InventoryStatusConstants.AVAILABLE);
										//Commented  below line and added new line
										//inventoryData.setNewed("Y");
										inventoryData.setInventorySubStatusId(Long.valueOf((InventoryStatusConstants.NEW)));
									}else{
										inventoryData.setInventoryStatusId(InventoryStatusConstants.NEW);
									}
								} else {
									inventoryData.setInventoryStatusId(InventoryStatusConstants.NEW);
								}
							}else{
								inventoryData.setInventoryStatusId(InventoryStatusConstants.NEW);
							}
						}
						
						
						inventoryWrapperVO.setInventoryData(inventoryData);
						
						// prepare data for batchsummary data
						BatchSummaryData batchSummaryData = null;
						if(resourceIdToBatchSummaryMap.get(inventoryData.getItemId()) == null){
							batchSummaryData = new BatchSummaryData();
							batchSummaryData.setResourceId(inventoryData.getItemId());
							batchSummaryData.setBatchId(batchData.getBatchId());
							batchSummaryData.setNumberFrom(inventoryData.getInventoryNo());
							
						}else{
							batchSummaryData = resourceIdToBatchSummaryMap.get(inventoryData.getItemId());
						}
						batchSummaryData.setNumberTo(inventoryData.getInventoryNo());
						if(batchSummaryData.getTotalInvetoryByItem() == null){
							batchSummaryData.setTotalInvetoryByItem(0L);
						}
						batchSummaryData.setTotalInvetoryByItem(batchSummaryData.getTotalInvetoryByItem() + 1);
						resourceIdToBatchSummaryMap.put(inventoryData.getItemId(), batchSummaryData);
					
						inventoryWrapperVO.setAttributeTransDatas(attributeTransList);
						
						inventoryDatas.add(inventoryWrapperVO);
					}

					
				}
					
		    	
		    	uploadresponseVO = new InventoryUploadVO();
	        	
				uploadresponseVO = inventoryManagementSessionBeanLocal.uploadInventory(inventoryDatas,iblSession);
				
	        	Logger.logTrace(MODULE, "completed uploadInventory");
	        	uploadresponseVO.setBatchNo(batchData.getBatchNo());
	        	
	        	// make entry in batch summary
	        	batchManagementSessionBeanLocal.createBatchSummary(resourceIdToBatchSummaryMap.values());
	        	
				//update total count in batch also
	        	Long totalCountInventory = 0L;
	        	for(BatchSummaryData batchSummaryData : resourceIdToBatchSummaryMap.values()){
	        		totalCountInventory += batchSummaryData.getTotalInvetoryByItem();
	        	}
	        	batchData.setTotalValidInventory(totalCountInventory);
	        	batchData.setTotalInvalidInventory(uploadresponseVO.getInvalidEntry());
	        	batchManagementSessionBeanLocal.updateBatch(batchData);
			
	        	AuditQueueData queueData = new AuditQueueData();
	    		
	        	queueData.setActionAlias(AuditConstants.UPLOAD_INVENTORY);
//	        	queueData.setAuditMap(mapAudit);
	        	queueData.setAuditTypeId(AuditConstants.CREATE_AUDIT_TYPE);
	        	queueData.setIpAddress(iblSession.getIpAddress());
	        	queueData.setObjectList(inventoryDatas);
	        	queueData.setReason("Upload Inventory");
	        	queueData.setSessionUserId(iblSession.getSessionUserId());
	        	auditLogSpecific(queueData);
		}
		
		
    	
    	return uploadresponseVO;
	}
	
	
	/**
	 * Upload Inventory  Method used by Agent and GUI to upload Inventory
	 * @author yash.kapasi
	 * @param InventoryUploadVO inventoryUploadVO
	 * @param IBLSession 
	 * @return InventoryUploadVO
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public InventoryUploadVO uploadInventory(InventoryUploadVO inventoryUploadVO,IBLSession iblSession) throws CreateBLException {
		
		Long startTime = System.currentTimeMillis();
		Logger.logTrace(MODULE, "inside uploadInventory()");
		
		InventoryUploadVO uploadresponseVO = null;
		try {
			
			if(inventoryUploadVO == null) {
				throw new CreateBLException("inventoryUploadVO getting null");
			}if(iblSession == null){
				throw new CreateBLException("iblSession getting null");
			}
			
			
			String[] readerData = new String(inventoryUploadVO.getUploadbyteData()).split("\n") ;
        	
        	int index = 0;
        	
        	Map<Integer,String> columnNoToNameMap    = new HashMap<Integer, String>();
        	ArrayList<InventoryWrapperVO> inventoryDatas = new ArrayList<InventoryWrapperVO>();
        	
        	StringBuilder errorStr = null;
        	BatchData batchData = null;
        	int totalHeaders = 0;
        	Map<String, AttributeData> attributeNameToIdMap = new HashMap<String, AttributeData>();
        	HashMap<String, HashSet<String>> attributeUniquevalueMap = new HashMap<String, HashSet<String>>();
        	List<AttributeData> attriList = null;
        	LinkedHashMap<String,Object> fieldToValueMap = new LinkedHashMap<String, Object>();
        	fieldToValueMap.put("systemgenerated", "N");
        	fieldToValueMap.put("usedBy", "Resource");
        	List filterData = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.ATTRIBUTE_DATA, fieldToValueMap);
        	if(filterData != null && !filterData.isEmpty()){
        		attriList     = (List<AttributeData>)filterData;
        	}
        	
        	if(attriList != null){
	        	for(AttributeData data: attriList){
	        		attributeNameToIdMap.put(data.getName(), data);
	        		if(data.getUnique() == 'Y'){
	        			attributeUniquevalueMap.put(data.getName(), new HashSet<String>());
	        		}
	        		
	        		Logger.logTrace(MODULE,data.getName()+" :: "+data.getAttributeId());
	        	}
        	}
        	
        	List<InventoryMigrationVO> inventoryMigrationVOs = new ArrayList<InventoryMigrationVO>();
        	
        	for(int lineno = 0;lineno<readerData.length;lineno++ )
        	{
        		errorStr = new StringBuilder();
        		String line = readerData[lineno];
        		if(line.contains("\"")) line = line.replace("\"", "");
        		
        		line = line.replace("\r", "");

        		if(index == 0){
        			String[] headers = line.split(",");
        			totalHeaders = headers.length;
        			for(int no = 0;no<headers.length;no++){
        				Logger.logTrace(MODULE, "Header :"+headers[no]);
        				columnNoToNameMap.put(no, headers[no]);
        				if(!headers[no].equalsIgnoreCase("Sr.No") 
        						//Header Changed from Batch No to  External Batch No
        						&& !headers[no].equalsIgnoreCase("External Batch No")
        						&& !headers[no].equalsIgnoreCase("Warehouse") 
        						&& !headers[no].equalsIgnoreCase("Resource Number")
        						&& !headers[no].equalsIgnoreCase("Inventory Number")
        						&& !headers[no].equalsIgnoreCase("GuaranteeWarrantyMode")
        						&& !headers[no].equalsIgnoreCase("Warranty Date")
        						&& !headers[no].equalsIgnoreCase("Warranty Type")
        						&& attributeNameToIdMap.get(headers[no]) == null){
        					throw new CreateBLException("Invalid Header Titles for :"+headers[no]);
        				}
        			}
					//Header Changed from Batch No to  External Batch No
        			if(!line.contains("Warehouse") || !line.contains("Resource Number") || !line.contains("Inventory Number") || !line.contains("GuaranteeWarrantyMode")|| !line.contains("Warranty Date")|| !line.contains("Warranty Type") || !line.contains("External Batch No")){
        				throw new CreateBLException("Invalid Header Titles or Headers should contain Warehouse and ItemReferenceId Names and value");
        			}
        		}else{
        			
        			
        			String warehouseName = null,resourceRefName = null, inventoryNumber = null,guaranteeWarrantyMode=null,warrantyDate=null,warrantyType=null,batchNumber = null;
        			
        			String[] rowValues = line.split(",");
        			Logger.logInfo(MODULE, "column size :"+rowValues.length + " @ "+index+" row");
        			InventoryMigrationVO migrationVO = new InventoryMigrationVO();
        			List<AttributeMigrationVO> attributeMigrationVOs = new ArrayList<AttributeMigrationVO>();
        			
        			for(int no = 0;no<rowValues.length;no++){
        				String headerName = columnNoToNameMap.get(no);
        				
        				
        				Logger.logTrace(MODULE, "rowvalue :"+rowValues[no]);
//Header Changed from Batch No to  External Batch No
        				if(headerName.equalsIgnoreCase("External Batch No") ){
        					
        					batchNumber = rowValues[no];
//Added By Rinkal
            				Logger.logTrace(MODULE, "batchNumber :"+batchNumber);
        					migrationVO.setBatchNumber(batchNumber);
        					if(index==1) {
        						if(rowValues[no] == null || rowValues[no].trim().equals("")){
        							throw new CreateBLException("BatchNo can't be blank");
        						}
        						
        					} 
        				}
        				else if(headerName.equalsIgnoreCase("Warehouse")){
        					warehouseName = rowValues[no];
        					migrationVO.setWareHouseName(warehouseName);
        					
        					
        				} else if(headerName.equalsIgnoreCase("Resource Number")){
        					resourceRefName = rowValues[no];
        					migrationVO.setResourceNumber(resourceRefName);
        					
        					
        				} else if(headerName.equalsIgnoreCase("Inventory Number")) {
        					
        					inventoryNumber = rowValues[no];
        					migrationVO.setInventoryNumber(inventoryNumber);
        					
        				}else if(headerName.equalsIgnoreCase("GuaranteeWarrantyMode")) {
        					
        					guaranteeWarrantyMode = rowValues[no];
        					migrationVO.setGuaranteeWarrantyMode(guaranteeWarrantyMode);
        					
        				}else if(headerName.equalsIgnoreCase("Warranty Date")) {
        					
        					warrantyDate = rowValues[no];
        					if(warrantyDate!=null && !warrantyDate.isEmpty()) {
        						migrationVO.setWarrantyDate(warrantyDate);
        					}
        				}else if(headerName.equalsIgnoreCase("Warranty Type")) {
        					
        					warrantyType = rowValues[no];
        					migrationVO.setWarrantyType(warrantyType);
        					
        				}
        				else if(attributeNameToIdMap.get(headerName) != null){
        					
        					AttributeMigrationVO attributeMigrationVO = new AttributeMigrationVO();
        					attributeMigrationVO.setKey(headerName);
        					attributeMigrationVO.setValue(rowValues[no]);
        					attributeMigrationVOs.add(attributeMigrationVO);
        					
        					
        				}
        			}
        			
        			migrationVO.setAttributeMigrationVOs(attributeMigrationVOs);
        			inventoryMigrationVOs.add(migrationVO);
        			
        		}
        		index++;
        	}
//        	Logger.logTrace(MODULE, "New Data Prepared :: "+inventoryMigrationVOs);
        	Logger.logTrace(MODULE, "New Data Prepared Size:: "+inventoryMigrationVOs.size());
        	
        	uploadresponseVO = uploadInventoryGeneric(inventoryMigrationVOs, iblSession,true);
        	
        	Logger.logTrace(MODULE, "calling uploadInventory");
        	
        	/*AuditQueueData queueData = new AuditQueueData();
    		
        	queueData.setActionAlias(AuditConstants.UPLOAD_INVENTORY);
//        	queueData.setAuditMap(mapAudit);
        	queueData.setAuditTypeId(AuditConstants.CREATE_AUDIT_TYPE);
        	queueData.setIpAddress(iblSession.getIpAddress());
        	queueData.setObjectList(inventoryDatas);
        	queueData.setReason("Upload Inventory");
        	queueData.setSessionUserId(iblSession.getSessionUserId());
        	auditLogSpecific(queueData);*/
        	

		}catch (CreateBLException e) {
			Logger.logTrace(MODULE, "Inside CreateBLException");
//			e.printStackTrace();
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw e;
		}/*catch(SearchBLException ex){
			Logger.logTrace(MODULE, "Inside SearchBLException");
//			ex.printStackTrace();
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw new CreateBLException("Upload Inventory Failed.");
		}*/
		catch(Exception e){
			Logger.logTrace(MODULE, "Inside Exception");
			e.printStackTrace();
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw new CreateBLException("Upload Inventory Failed.");
		}
		
		
		Long endTime = System.currentTimeMillis();
		
		Logger.logInfo(MODULE, "Time taken in uploadInventory :"+ (endTime - startTime));
		return uploadresponseVO;
	}






	@Override
	public List<ComboData> getAllInventoryStatusData() {
		
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside getAllInventoryStatusData()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORYSTATUS_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				List<InventoryStatusData> inventoryStatusDatas = (List<InventoryStatusData>)filterList;
				for(InventoryStatusData inventoryStatusData : inventoryStatusDatas){
					if(!(inventoryStatusData.getName().equalsIgnoreCase(InventoryStatusConstants.FAILED_STATUS ))){
						//Logger.logTrace(MODULE,"inventoryStatusData.getInventoryStatusId():"+inventoryStatusData.getInventoryStatusId());
						//Logger.logTrace(MODULE,"inventoryStatusData.getName():"+inventoryStatusData.getName());
					comboBoxDatas.add(new ComboData(inventoryStatusData.getInventoryStatusId(), inventoryStatusData.getName()));
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
	}
	

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.facade.inventorymgt.IInventoryManagementFacade#searchInventoryDetailData(com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO)
	 */
	@Override
	public List<InventoryDetailVO> searchInventoryDetailData(SearchInventoryVO inventoryDetailVO) {
	
		Logger.logTrace(MODULE, "inside searchInventoryDetailData()");
		List<InventoryDetailVO> inventoryDetailVOs = null;
		try {
			List<Object> data = inventoryManagementSessionBeanLocal.searchInventoryDetailData(inventoryDetailVO);
			if(data != null){
				inventoryDetailVOs=InventoryManagementUtil.getInventoryDetailVO(data);
					//warehouseVOs.add(MasterUtil.getWarehouseVO(dataObj));
				Logger.logTrace(MODULE,"Data receive:"+inventoryDetailVOs);
					//System.out.println("[JM]Data  size in facade:"+inventoryDetailVOs.size());
					
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return inventoryDetailVOs;
		
	}
	public List<InventoryBatchViewVO> searchInventoryBatchData(SearchInventoryVO searchInventoryVO) {
		Logger.logTrace(MODULE, "inside searchInventoryBatchData()");
		List<InventoryBatchViewVO> inventoryBatchViewVOs = null;
		try {
			/*List<Object> data = inventoryManagementSessionBeanLocal.searchInventoryBatchData(searchInventoryVO);
			if(data != null){
				inventoryBatchViewVOs=InventoryManagementUtil.getInventoryBatchViewVO(data);
			}*/
			
			List<BatchData> batchDataList = batchManagementSessionBeanLocal.searchBatch(searchInventoryVO);
			inventoryBatchViewVOs = InventoryManagementUtil.prepareInventoryBatchViewVO(batchDataList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return inventoryBatchViewVOs;
		
	}
	
	
	/**
	 * Search Inventory Uplaod Data by batchNumber, Status and Destination Path
	 * @author yash.kapasi
	 * @param String batchNumber
	 * @param Boolean  status
	 * @param String destinationpath
	 */
	public void searchInventoryUploadData(String batchNumber,Boolean status,String destinationpath) {
		Logger.logTrace(MODULE, "inside searchValidInventoryUploadData()");
		Logger.logTrace(MODULE, "inside searchValidInventoryUploadData() batchNumber:"+batchNumber);
		Logger.logTrace(MODULE, "inside searchValidInventoryUploadData() status:"+status);
		Logger.logTrace(MODULE, "inside searchValidInventoryUploadData() destinationpath:"+destinationpath);
		
		try {
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("ATTRIBUTEREL", "Resource");
			String attributeNames="";
			Set<String> headerSet=new LinkedHashSet<String>();
			headerSet.add("Sr.No");
			headerSet.add("Batch No");
			headerSet.add("Warehouse");
			headerSet.add("Resource Number");
			if(status){
				headerSet.add("InventoryNo");
			}
			
			List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.ATTRIBUTE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				List<AttributeData> attributeDatas = (List<AttributeData>)filterList;
				StringBuilder attributeStrBuider = new StringBuilder();
				for(AttributeData attributeData : attributeDatas){
					
//					attributeNames+=attributeData.getAttributeId()+" as "+attributeData.getName().replaceAll(" ","_")+","	;
					attributeStrBuider.append(attributeData.getAttributeId()).append(" as ").append(attributeData.getName().replaceAll(" ","_")).append(",");
					headerSet.add(attributeData.getName().replaceAll(" ","_"));
				}
				if(!status){
					Logger.logTrace(MODULE, "searchValidInventoryUploadData() Adding Remarks");
					headerSet.add("Remarks");
				}
				attributeNames = attributeStrBuider.toString();
				attributeNames=attributeNames.substring(0,attributeNames.length()-1);
			}
			CachedRowSetImpl cachedRowSetImpl = inventoryManagementSessionBeanLocal.searchValidInventoryUploadData(batchNumber,attributeNames,status);
			
			if(cachedRowSetImpl != null){
				Logger.logTrace(MODULE, "searchValidInventoryUploadData() headerSet:"+headerSet);
				InventoryManagementUtil.getUploadInventoryVO(destinationpath,cachedRowSetImpl,headerSet);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public Long getAvailableStock(SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO){
		
		Logger.logTrace(MODULE, "inside getAvailableStock()");
		Long avaialbelStock = 0L;
		try{
			
			WarehouseData warehouseData = new WarehouseData();
			warehouseData.setWarehouseId(searchWarehouseInventoryStatusVO.getCurrentWarehouseId());
			
			ItemData itemData = new ItemData();
			itemData.setItemId(searchWarehouseInventoryStatusVO.getResourceId());
			
			Map<String,Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("warehouseData", warehouseData);
			fieldValueMap.put("resource", itemData);
			
			List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSEINVENTORYSTATUS_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				WarehouseInventoryStatusData warehouseInventoryStatusData = (WarehouseInventoryStatusData)filterList.get(0);
				
				avaialbelStock = warehouseInventoryStatusData.getAvailable();
				/*avaialbelStock = warehouseInventoryStatusData.getAvailable() - warehouseInventoryStatusData.getDelivered()
									- warehouseInventoryStatusData.getFaulty() - warehouseInventoryStatusData.getInUse()
									- warehouseInventoryStatusData.getReleased() - warehouseInventoryStatusData.getRepaired()
									- warehouseInventoryStatusData.getReserved() - warehouseInventoryStatusData.getScrapped()
									- warehouseInventoryStatusData.getVoided();*/
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return avaialbelStock;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public void  transferInventory(SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO,IBLSession iblSession) throws CreateBLException{
		
		try {
			synchronized (transferObj) {
				Long avaialibility = getAvailableStock(searchWarehouseInventoryStatusVO);
				if(avaialibility < searchWarehouseInventoryStatusVO.getTotalQty()){
					throw new CreateBLException("Sorry. You can't transfer inventories more than avaialble");
				}
				
				inventoryManagementSessionBeanLocal.transferInventory(searchWarehouseInventoryStatusVO, iblSession);
				
				
			}
		}catch(CreateBLException ex){
			throw ex;
		}catch (Exception e) {
			e.printStackTrace();
			throw new CreateBLException("Transfer Inventories failed due to some reason");
		}
	}
	
	
	/**
	 * Transfer Inventory from one warehouse to another
	 * @author yash.kapasi
	 * @param {@link List}<{@link InventoryDetailVO}> inventoryDetailVOs
	 * @param Long fromWarehouseId
	 * @param {@link IBLSession}
	 * @param Boolean  status
	 * @param String  flag
	 * @param String destinationpath
	 * @return String orderNo
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public String  transferInventory(List<InventoryDetailVO> inventoryDetailVOs,Long fromWarehouseId,Long toWarehouseId,
			IBLSession iblSession,String flag) throws CreateBLException{
		
		Logger.logTrace(MODULE, "inside transferInventory()");
		try {
				if(inventoryDetailVOs == null){
					throw new CreateBLException("inventoryDetailVO can't be null");
				}if(fromWarehouseId == null || toWarehouseId == null){
					throw new CreateBLException("From warehouse or To warehouse getting null");
				}
			
				String fromwarehouseName="";
				WarehouseData toWarehouseData=new WarehouseData(),fromwarehouseData=null;
				for(InventoryDetailVO inventoryDetailVO:inventoryDetailVOs){
					fromwarehouseName=inventoryDetailVO.getWareHouseName();
					break;
				}
				inventoryManagementSessionBeanLocal.transferInventory(inventoryDetailVOs, iblSession);
				Logger.logTrace(MODULE, "After calling sessionBean transferInventory()");
				String orderNo = systemInternalSessionBeanLocal.getPrimaryKey(CPECommonConstants.TRANSFERORDER_DATA);
				Logger.logTrace(MODULE, " calling InventoryManagementUtil getTransferOrderData() with OrderNo:"+orderNo);
				TransferOrderData transferOrderData=InventoryManagementUtil.getTransferOrderData( inventoryDetailVOs, toWarehouseId,orderNo,iblSession);
				Logger.logTrace(MODULE, "Before calling sessionBean saveTransferOrderData():"+transferOrderData);
				
				InventoryTransferOrderStatus transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_IN_PROGRESS);
				transferOrderData.setInventoryOrderStatusId(transferOrderStatus.getOrderStatusId());
				
				transferOrderData=inventoryManagementSessionBeanLocal.saveTransferOrderData(transferOrderData, iblSession);
				//Getting towarehouse name
				Map<String,Object> fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("warehouseId", transferOrderData.getToWarehouseId());
				Logger.logTrace(MODULE, "Inside transferInventory method::fieldValueMap:"+fieldValueMap);
				List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
				if(filterList != null && !filterList.isEmpty()){
					 toWarehouseData = (WarehouseData)filterList.get(0);
				}
				
				fieldValueMap.put("warehouseId", fromWarehouseId);
				filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
				if(filterList != null && !filterList.isEmpty() && fromwarehouseName == null){
					fromwarehouseData = (WarehouseData)filterList.get(0);
					fromwarehouseName = fromwarehouseData.getName();
				}
				
				//Calling save method for TransferOrderDetailData
				Logger.logTrace(MODULE, "After calling sessionBean saveTransferOrderData():::::::::"+transferOrderData);
				List<TransferOrderDetailData> transferOrderDetailDatas=InventoryManagementUtil.getTransferOrderDetailData(inventoryDetailVOs,transferOrderData,iblSession);
				int size=transferOrderDetailDatas.size();
				Logger.logTrace(MODULE, "Quantity:::::"+size);
				Logger.logTrace(MODULE, "Before calling sessionBean saveTransferOrderDetailData():"+transferOrderDetailDatas);
				inventoryManagementSessionBeanLocal.saveTransferOrderDetailData(transferOrderDetailDatas, iblSession);
				Logger.logTrace(MODULE, "After calling sessionBean saveTransferOrderDetailData()");
				
				// Audit entry for TransferOrderData
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.TRANSFER_ORDER_NUMBER,orderNo);
				mapAudit.put(AuditTagConstant.FROMWAREHOUSE,fromwarehouseName);
				mapAudit.put(AuditTagConstant.TOWAREHOUSE,toWarehouseData.getName());
				addToAuditDynamicMessage(AuditConstants.CREATE_TRANSFER_ORDER, "Creating Transfer Order",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, iblSession);
/*//--added start 
				Map<String,Object> fieldValueMap4 = new HashMap<String, Object>();
				fieldValueMap4.put("transferOrderId", transferOrderData.getTransferOrderId());
				List transferorderList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDER_DATA,fieldValueMap4);
				if(transferorderList!=null && !transferorderList.isEmpty()){
					transferOrderData = (TransferOrderData)transferorderList.get(0);
				}
				System.out.println(" after changing ::::transferOrderData"+transferOrderData);
//--added end
*/				
				InventoryData inventoryData = null;
				if(transferOrderData!=null && transferOrderDetailDatas!=null && !transferOrderDetailDatas.isEmpty()) {
					TransferOrderDetailData detailData = null;
					for(TransferOrderDetailData data : transferOrderDetailDatas) {
						detailData = data;
					}
					HashMap<String, Object> fieldValueNotifyMap = new HashMap<String, Object>();
					fieldValueNotifyMap.put("inventoryNo", detailData.getInventoryNo());
					List filterNotifyList3 = transferSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueNotifyMap);
					if(filterNotifyList3!=null && !filterNotifyList3.isEmpty()) {
						inventoryData =  (InventoryData) filterNotifyList3.get(0);
					}
					
				}
				//Notification Data
				NotificationData data = ItemUtil.prepareNotificationDataOnTransfer(transferOrderData,flag,inventoryData,transferOrderDetailDatas.size());
				Logger.logTrace(MODULE,"notification data::::::::::::"+data.toString());
				sendNotification(data);
				Logger.logTrace(MODULE,"notification sent complete::::::::::::");
				
				
				return orderNo; 
			
		}catch (CreateBLException e) {
			Logger.logTrace(MODULE, "Inside CreateBLException");
//			e.printStackTrace();
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw new CreateBLException("Transfer Inventories failed due to some reason");
		}
		catch(Exception e){
			Logger.logTrace(MODULE, "Inside Exception");
			e.printStackTrace();
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw new CreateBLException("Transfer Inventories failed due to some reason");
		}
	}





	@Override
	public InventoryDetailsResponseData getInventoryDetails(
			InventoryDetailsRequestData requestData,int noOfRecords) throws SearchBLException {
	
		try {
			InventoryDetailsResponseData responseData = null;
			
			List lst = prepareAttributePivotResource(requestData.getResourceId());
			Set<String> headerSet = (Set<String>)lst.get(0);
			String attributeNames = (String)lst.get(1);
			
			CachedRowSetImpl cachedRowSetImpl = inventoryManagementSessionBeanLocal.getInventoryDetails(requestData,attributeNames,noOfRecords);
			if(cachedRowSetImpl != null){
				responseData = InventoryManagementUtil.getInventoryDetailsResponseData(cachedRowSetImpl,headerSet);
				
			}
			
			
			return responseData;
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List prepareAttributePivotResource(String resourceId) throws SearchBLException {
		
		String attributeNames="";
		Set<String> headerSet=new LinkedHashSet<String>();
		List<Object> list = new ArrayList<Object>();
		List<Object[]> attributeDatas = attributeSessionBeanLocal.searchAttributeDataByResourceId(resourceId);
		
		if(attributeDatas != null && !attributeDatas.isEmpty()){
			StringBuilder attributeStrBuider = new StringBuilder(); 
			for(Object[] attributeData : attributeDatas){
				headerSet.add(attributeData[1]+"");
				
				System.out.println(attributeData[0]);
				System.out.println(attributeData[1]);
				
				String name = attributeData[1]+"";
				
				attributeStrBuider.append(attributeData[0]+"").append(" as ").append(name.replaceAll(" ","_")).append(",");
			}
			attributeNames = attributeStrBuider.toString();
			attributeNames=attributeNames.substring(0,attributeNames.length()-1);
		}
		
		System.out.println(attributeNames);
		
		list.add(headerSet);
		list.add(attributeNames);
		
		return list;
		
	}






	private List<Object> prepareAttributePivotStr(String transferOrderNumber) throws SearchBLException{
		List<Object> list = new ArrayList<Object>();
		Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
		fieldValueMap.put("systemgenerated", "N");
		fieldValueMap.put("ATTRIBUTEREL", "Resource");
		String attributeNames="";
		Set<String> headerSet=new LinkedHashSet<String>();
		
		List<Object[]> attributeDatas = attributeSessionBeanLocal.searchAttributeDataByTransferOrderId(transferOrderNumber);
		
		
		
//		List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.ATTRIBUTE_DATA, fieldValueMap);
		if(attributeDatas != null && !attributeDatas.isEmpty()){
			
//			List<AttributeData> attributeDatas = (List<AttributeData>)filterList;
			StringBuilder attributeStrBuider = new StringBuilder(); 
			for(Object[] attributeData : attributeDatas){
				headerSet.add(attributeData[1]+"");
				
				System.out.println(attributeData[0]);
				System.out.println(attributeData[1]);
				
				String name = attributeData[1]+"";
				
				attributeStrBuider.append(attributeData[0]+"").append(" as ").append(name.replaceAll(" ","_")).append(",");
			}
			attributeNames = attributeStrBuider.toString();
			attributeNames=attributeNames.substring(0,attributeNames.length()-1);
		}
		
		System.out.println(attributeNames);
		
		list.add(headerSet);
		list.add(attributeNames);
		
		return list;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public void transferInventoryBatch(Map<String,Integer> batchMap,SearchInventoryVO searchInventoryVO,Long fromWarehouseId,Long toWarehouseId,IBLSession iblSession)throws CreateBLException{
		
		try {
			//get all inventories no based on batchNo
			Map<String,List<String>> batchInventoryMap = inventoryManagementSessionBeanLocal.getInventoryByBatch(batchMap, searchInventoryVO);
			List<InventoryDetailVO> inventoryDetailVOs = new ArrayList<InventoryDetailVO>();
			for(Entry<String,List<String>> entry : batchInventoryMap.entrySet()){
				String batchNo = entry.getKey();
				List<String> inventoryNos = entry.getValue();
				for(String inventoyNo : inventoryNos)
				{
					InventoryDetailVO inventoryDetailVO = new InventoryDetailVO();
					inventoryDetailVO.setInventoryId(inventoyNo);
					inventoryDetailVO.setBatchId(batchNo);
					inventoryDetailVO.setWarehouseId(fromWarehouseId);
					inventoryDetailVOs.add(inventoryDetailVO);
				}
			}
			
			transferInventory(inventoryDetailVOs, fromWarehouseId, toWarehouseId, iblSession,NotificationConstants.TRANSFER_ORDER);
		}catch(CreateBLException ex){
			throw ex;
		}catch (Exception e) {
			e.printStackTrace();
			throw new CreateBLException("Transfer Inventories failed due to some reason");
		}
		
	}
	
	@Override
	public List<TransferInventorySummaryViewVO> searchTransferInventorySummary(SearchTransferInventory searchTransferInventory,IBLSession iblsession){
		
		List<TransferInventorySummaryViewVO> inventorySummaryViewVOs = inventoryManagementSessionBeanLocal.searchTransferInventorySummary(searchTransferInventory);
		
		return inventorySummaryViewVOs;
	}
	
	@Override
	public InventoryUploadVO getInventoryDetails(SearchTransferInventory searchTransferInventory){
		
		StringBuilder builder = new StringBuilder();
		try {
			Map<String, Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("transferOrderNo", searchTransferInventory.getOrderNo());
			
			List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDER_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				TransferOrderData orderData = (TransferOrderData)filterList.get(0);
				searchTransferInventory.setOrderId(orderData.getTransferOrderId());
				
				List lst = prepareAttributePivotStr(searchTransferInventory.getOrderNo());
				Set<String> headerSet = (Set<String>)lst.get(0);
				String attributeNames = (String)lst.get(1);
				
				//prepare for header
				builder.append("SrNo").append(",")
				.append("OrderNo").append(",")
				.append("From Warehouse").append(",")
				.append("To Warehouse").append(",")
				.append("InventoryNo").append(",")
				.append("BatchNo").append(",");
				for(String key : headerSet )
				{
					builder.append(key).append(",");
				}
				builder.append("Transfer Status").append(",")
				.append("Remark");
				builder.append("\n");
				
				//to get Data
				CachedRowSetImpl cachedRowSetImpl = inventoryManagementSessionBeanLocal.getInventoryDetails(attributeNames,searchTransferInventory);
				if(cachedRowSetImpl != null){
					Logger.logDebug(MODULE, "cachedRowSetImpl not getting null"); 
					
					List<InventoryDetailVO> inventoryDetailVOs = InventoryManagementUtil.getInventoryDetails(cachedRowSetImpl, headerSet);
					int counter = 1;
					for(InventoryDetailVO inventoryDetailVO : inventoryDetailVOs)
					{
//						if(inventoryDetailVO.getTransferStatus()==null || "".equals(inventoryDetailVO.getTransferStatus())){
							builder.append(counter).append(",")
							.append(searchTransferInventory.getOrderNo()).append(",")
							.append(searchTransferInventory.getFromWarehouseName()).append(",")
							.append(searchTransferInventory.getToWarehouseName()).append(",")
							.append(inventoryDetailVO.getInventoryId()).append(",")
							.append(inventoryDetailVO.getBatchId()).append(",");
							
							for(String key: headerSet){
								builder.append(inventoryDetailVO.getAttribute().get(key)).append(",");
							}
							
							builder.append(inventoryDetailVO.getTransferStatus()).append(",");
							builder.append(inventoryDetailVO.getRemark()).append(",");
							builder.append("\n");
							counter++;
//						}
					}
				}
			}
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
		
		InventoryUploadVO uploadVO = new InventoryUploadVO();
		uploadVO.setUploadbyteData(builder.toString().replaceAll("null", "").getBytes());
		return uploadVO;
	}
	
	
	/**
	 * Accepts Transfer Inventory Flow
	 * @author yash.kapasi
	 * @param SearchTransferInventory searchTransferInventory
	 * @param {@link IBLSession}
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public void acceptTransferInventory(PartialAcceptRejectTransferOrderVO transferOrderVO,IBLSession iblSession)throws CreateBLException{
		
		Logger.logTrace(MODULE, "inside acceptTransferInventory()");
		try {
			Map<String, Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("transferOrderNo", transferOrderVO.getOrderNo());
			
			List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDER_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				TransferOrderData orderData = (TransferOrderData)filterList.get(0);
				String statusInitial = orderData.getInventoryOrderStatusId();
				// for accept all or reject all
				
				Logger.logTrace(MODULE, "6");
				
				int acceptedSize = 0;
				
				List<InventoryDetailVO> inventoryList = new ArrayList<InventoryDetailVO>();
				if(transferOrderVO.getOrderStatus().equalsIgnoreCase("acceptall") || 
						transferOrderVO.getOrderStatus().equalsIgnoreCase("rejectall")){
					fieldValueMap =new HashMap<String, Object>();
					fieldValueMap.put("transferOrderId",orderData.getTransferOrderId() );
					
					Logger.logTrace(MODULE, "7:: "+orderData.getTransferOrderId());
					filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDERDETAIL_DATA, fieldValueMap);
					if(filterList != null && !filterList.isEmpty()){
						//make a list of inventories which can be transfer from 1 warehouse to another warehouse
						
						Logger.logTrace(MODULE, "8");
						String transferStatus = null;
						if(transferOrderVO.getOrderStatus().equalsIgnoreCase("acceptall")){
							InventoryTransferOrderStatus transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_ACCEPTED);
							orderData.setInventoryOrderStatusId(transferOrderStatus.getOrderStatusId());
							transferStatus = "ACCEPTED";
                         	acceptedSize = filterList.size();
						}else if(transferOrderVO.getOrderStatus().equalsIgnoreCase("rejectall")){
							InventoryTransferOrderStatus transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_REJECTED);
							orderData.setInventoryOrderStatusId(transferOrderStatus.getOrderStatusId());
							transferStatus = "REJECTED";
							Logger.logTrace(MODULE, "5");
						}
						
						for(Object obj : filterList){
							TransferOrderDetailData orderDetailData =  (TransferOrderDetailData)obj;
							if(orderDetailData.getTransferStatus() == null){
								
								InventoryDetailVO inventoryDetailVO = new InventoryDetailVO();
								inventoryDetailVO.setInventoryId(orderDetailData.getInventoryNo());
								inventoryDetailVO.setTransferStatus(transferStatus);
								inventoryDetailVO.setRemark(transferOrderVO.getRemark());
								inventoryList.add(inventoryDetailVO);
								
								
							}
						}
						
					}
					Logger.logTrace(MODULE, "-1");
				}else{
					
					InventoryTransferOrderStatus transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_PARTIALLY_ACCEPTED);
					orderData.setInventoryOrderStatusId(transferOrderStatus.getOrderStatusId());
					int countAccepted = 0;
					int countRejected = 0;
					int countAll = 0;
					if(transferOrderVO.getInventoryVOs()!=null && !transferOrderVO.getInventoryVOs().isEmpty()) {
						for(InventoryVO inventoryVO : transferOrderVO.getInventoryVOs()) {
							InventoryDetailVO inventoryDetailVO = new InventoryDetailVO();
							inventoryDetailVO.setInventoryId(inventoryVO.getInventoryNo());
							inventoryDetailVO.setTransferStatus(inventoryVO.getTransferStatus());
							inventoryDetailVO.setRemark(inventoryVO.getRemarks());
							inventoryList.add(inventoryDetailVO);
							
							countAll++;
							if(inventoryDetailVO.getTransferStatus().equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_REJECTED)) {
								countRejected++;
							} else  if(inventoryDetailVO.getTransferStatus().equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_ACCEPTED)) {
								countAccepted++;
							}
							
						}
					}
					
					// call for partial accept/ reject
					/*String[] readerData = new String(searchTransferInventory.getUploadbyteData()).split("\n") ;
					int index = 0,totalHeaders = 0;
					
					Map<Integer,String> columnNoToNameMap    = new HashMap<Integer, String>();
					
					for(int lineno = 0;lineno<readerData.length;lineno++ )
		        	{
		        		String line = readerData[lineno];
		        		if(line.contains("\"")) line = line.replace("\"", "");
		        		
		        		line = line.replace("\r", "");
		        		
		        		if(index == 0){
		        			String[] headers = line.split(",");
		        			totalHeaders = headers.length;
		        			for(int no = 0;no<headers.length;no++){
		        				Logger.logTrace(MODULE, "Header :"+headers[no]);
		        				columnNoToNameMap.put(no, headers[no]);
		        			}
		        			
		        			if(!line.contains("InventoryNo") || !line.contains("Transfer Status") || !line.contains("Remark")){
		        				throw new CreateBLException("Invalid Header Titles or Headers should contain InventoryNo and Transfer Status and Remark");
		        			}
		        		}else{
		        			countAll++;
		        			
		        			String[] rowValues = line.split(",");
		        			Logger.logInfo(MODULE, "column size :"+rowValues.length + " @ "+index+" row");
		        			
		        			InventoryDetailVO inventoryDetailVO = new InventoryDetailVO();
		        			for(int no = 0;no<rowValues.length;no++){
		        				String headerName = columnNoToNameMap.get(no);
		        				if(headerName!=null) {
		        					
		        					if(headerName.equalsIgnoreCase("InventoryNo")){
		        						inventoryDetailVO.setInventoryId(rowValues[no]);
		        					}else if(headerName.equalsIgnoreCase("Transfer Status")){
		        						if(rowValues[no].equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_ACCEPTED) || 
		        								rowValues[no].equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_REJECTED)){
		        							inventoryDetailVO.setTransferStatus(rowValues[no].toUpperCase());
		        						}else if(rowValues[no].equalsIgnoreCase("")){
		        						} else {
		        							throw new CreateBLException("Invalid Value for Transfer Status");
		        						}
		        					}else if(headerName.equalsIgnoreCase("Remark")){
		        						inventoryDetailVO.setRemark(rowValues[no]);
		        					}
		        				}
		        			}	
		        			
//		        			Logger.logDebug(MODULE, inventoryDetailVO.getInventoryId()+" : "+inventoryDetailVO.getTransferStatus()+" : "+inventoryDetailVO.getRemark());
		        			if(inventoryDetailVO.getInventoryId() != null 
		        					&& inventoryDetailVO.getTransferStatus() != null 
		        					&& ((inventoryDetailVO.getTransferStatus().equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_REJECTED) && inventoryDetailVO.getRemark() != null )
		        							|| (inventoryDetailVO.getTransferStatus().equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_ACCEPTED) ))
		        					 ){
		        				fieldValueMap = new HashMap<String, Object>();
		        				fieldValueMap.put("transferOrderId",orderData.getTransferOrderId() );
		        				fieldValueMap.put("inventoryNo", inventoryDetailVO.getInventoryId());
		    					filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDERDETAIL_DATA, fieldValueMap);
		    					if(filterList != null && !filterList.isEmpty()){
		    						TransferOrderDetailData orderDetailData =  (TransferOrderDetailData)filterList.get(0);
		    						if(orderDetailData.getTransferStatus() == null){
		    							
		    							if(inventoryDetailVO.getTransferStatus().equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_REJECTED)) {
		    								countRejected++;
		    							} else  if(inventoryDetailVO.getTransferStatus().equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_ACCEPTED)) {
		    								countAccepted++;
		    							}
		    							
		    							inventoryList.add(inventoryDetailVO);
//		    							Logger.logDebug(MODULE, "adding into list");
		    						}
		    					}
		        			}
		        		}
		        		
		        		index++;
		        	}*/
					
					if(countAll!=0 && inventoryList!=null && !inventoryList.isEmpty()) {
						if(countAccepted == countAll) {
							transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_ACCEPTED);
							orderData.setInventoryOrderStatusId(transferOrderStatus.getOrderStatusId());
						} else if(countRejected == countAll) {
							transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_REJECTED);
							orderData.setInventoryOrderStatusId(transferOrderStatus.getOrderStatusId());
						} else if(countAccepted==0 && countRejected==0) {
//							orderData.setInventoryOrderStatusId(statusInitial);
						}
					}
					
				}
				
				Logger.logTrace(MODULE, "1");
				// got final list of inventories which can be transfer
				inventoryManagementSessionBeanLocal.changeTransferInventoryStatus(transferOrderVO, orderData, inventoryList);
				
				Logger.logTrace(MODULE, "2");
				//Updating Order Status
				transferSessionBeanLocal.updateTransferOrder(orderData);
				
				// Audit entry
				for(InventoryDetailVO  inventoryDetailVO:  inventoryList){
					Map<String,Object> mapAudit = new HashMap<String, Object>();
					mapAudit.put(AuditTagConstant.INVENTORYNO,inventoryDetailVO.getInventoryId());
					mapAudit.put(AuditTagConstant.STATUS,inventoryDetailVO.getTransferStatus());
					mapAudit.put(AuditTagConstant.FROMWAREHOUSE,orderData.getFromWarehouseData().getName());
					mapAudit.put(AuditTagConstant.TOWAREHOUSE,orderData.getToWarehouseData().getName());
					
					addToAuditDynamicMessage(AuditConstants.TRANSFER_INVENTORY_SUMMARY, "Transfer Inventory Summary",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, iblSession);
				}
				
				
				Map<String, Object> fieldValueNotifyMap = new HashMap<String, Object>();
				fieldValueNotifyMap.put("name", transferOrderVO.getFromWarehouseName());
				List filterNotifyList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueNotifyMap);
				WarehouseData wareHouseData = null;
				if(filterNotifyList!=null && !filterNotifyList.isEmpty()) {
					wareHouseData =  (WarehouseData) filterNotifyList.get(0);
				}
				
				fieldValueNotifyMap = new HashMap<String, Object>();
				fieldValueNotifyMap.put("name", transferOrderVO.getToWarehouseName());
				List filterNotifyList2 = transferSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueNotifyMap);
				WarehouseData wareHouseData2 = null;
				if(filterNotifyList!=null && !filterNotifyList2.isEmpty()) {
					wareHouseData2 =  (WarehouseData) filterNotifyList2.get(0);
				}
				
//				System.out.println("---------------->"+transferOrderVO);
				
				
				InventoryData inventoryData = null;
				if(orderData!=null && orderData.getTransferOrderDetailDatas()!=null && !orderData.getTransferOrderDetailDatas().isEmpty()) {
					TransferOrderDetailData detailData = null;
					for(TransferOrderDetailData data : orderData.getTransferOrderDetailDatas()) {
						detailData = data;
					}
					
					fieldValueNotifyMap = new HashMap<String, Object>();
					fieldValueNotifyMap.put("inventoryNo", detailData.getInventoryNo());
					List filterNotifyList3 = transferSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueNotifyMap);
					if(filterNotifyList3!=null && !filterNotifyList3.isEmpty()) {
						inventoryData =  (InventoryData) filterNotifyList3.get(0);
					}
					
				}
				
				//Notification Data
				NotificationData data = ItemUtil.prepareNotificationDataOnTransferAcceptReject(transferOrderVO,wareHouseData,wareHouseData2,inventoryData,acceptedSize);
				sendNotification(data);
				
				
			}
			
		} catch (CreateBLException e) {
			Logger.logTrace(MODULE, "Inside CreateBLException");
//			e.printStackTrace();
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw new CreateBLException("Sorry!!! Accept/Reject Transfer Inventory Failed");
			
		}
		

	}
	
	/*
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public List<com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO> reserveInventory(BookCPERequestData requestData,IBLSession iblSession) throws SearchBLException{
		
		Logger.logDebug(MODULE, " in reserveInventory method called");
		List<com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO> inventoryVOs = null;
		try {
			
			ResourceAvailibilityRequestData resourceAvailibilityRequestData = new ResourceAvailibilityRequestData();
			resourceAvailibilityRequestData.setResourceId(requestData.getResourceId());
			resourceAvailibilityRequestData.setWarehouseName(requestData.getWarehouseName());
			
			ResourceAvailibilityResponseData availibilityResponseData = checkCPEResource(resourceAvailibilityRequestData);
			if(availibilityResponseData.getResourceAvailibilityVOs()  == null || availibilityResponseData.getResourceAvailibilityVOs().isEmpty()){
				throw new SearchBLException(InventoryMgtResponseCode.RESERVE_MORETHAN_AVAILABLE);
			}
			if(availibilityResponseData.getResourceAvailibilityVOs().get(0).getAvailableResourceCount() < requestData.getNoOfResource()){
				throw new SearchBLException(InventoryMgtResponseCode.RESERVE_MORETHAN_AVAILABLE);
			}

			InventoryDetailsRequestData inventoryDetailsRequestData = new InventoryDetailsRequestData();
			inventoryDetailsRequestData.setResourceId(requestData.getResourceId());
			inventoryDetailsRequestData.setWareHouseName(requestData.getWarehouseName());
			inventoryDetailsRequestData.setInventoryStatus(InventoryStatusConstants.AVAILABLE_STATUS);
			inventoryDetailsRequestData.setTransferStatus("");
			InventoryDetailsResponseData inventoryDetailsResponseData = getInventoryDetails(inventoryDetailsRequestData,(int)requestData.getNoOfResource());
			
			Logger.logDebug(MODULE, "\n\n total inventories found :"+inventoryDetailsResponseData.getInventoryDetailVO().get(0).getInventoryVOs().size() + "\n\n");
			Logger.logDebug(MODULE, "Are going to change :"+inventoryDetailsResponseData.getInventoryDetailVO().get(0).getInventoryVOs() + "\n\n");
			
			List<InventoryRequestVO> inventoryRequestVOs = new ArrayList<InventoryRequestVO>();
//			change Inventory status
			for(com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO inventoryVO : inventoryDetailsResponseData.getInventoryDetailVO().get(0).getInventoryVOs()){
				InventoryRequestVO inventoryRequestVO = new InventoryRequestVO();
				inventoryRequestVO.setInventoryNo(inventoryVO.getInventoryNumber());
				inventoryRequestVO.setOldStatus(InventoryStatusConstants.AVAILABLE_STATUS);
				inventoryRequestVO.setNewStatus(InventoryStatusConstants.RESERVED_STATUS);
				inventoryRequestVO.setRemarks("WS:BookCPE, Mark for Reserved");
				inventoryRequestVOs.add(inventoryRequestVO);
			}
			List<InventoryStatusVO> inventoryStatusVOs = changeInventoryStatus(inventoryRequestVOs,iblSession);
			
			Logger.logDebug(MODULE, "\n\n Response from changeStatus :"+inventoryStatusVOs );
			//initial request insert into reserve and reserveDetail table
			String reservationNo = systemInternalSessionBeanLocal.getPrimaryKey(CPECommonConstants.RESERVE_DATA);
			Logger.logDebug(MODULE, "reservationNo :"+reservationNo);
			InventoryReserveData inventoryReserveData  = InventoryManagementUtil.prepareInventoryReserveData(reservationNo, requestData.getNoOfResource(),requestData.getOrderLineItemID(),null);
			Logger.logDebug(MODULE, "inventoryReserveData :"+inventoryReserveData);
			InventoryReserveData reserveData = inventoryManagementSessionBeanLocal.reserveInventory(inventoryReserveData);
			
			List<InventoryReserveDetailData> inventoryReserveDetailDatas = new ArrayList<InventoryReserveDetailData>();
			for(InventoryStatusVO inventoryStatusVO : inventoryStatusVOs)
			{
				if(inventoryStatusVO.getResponseCode() != 0){
					throw new SearchBLException("Reserved Inventory Failed");
				}
				inventoryReserveDetailDatas.add(InventoryManagementUtil.prepareInventoryReserveDetailData(inventoryStatusVO.getInventoryNumber(), reserveData.getReservationId()));
			}
			inventoryManagementSessionBeanLocal.reserveInventoryDetail(inventoryReserveDetailDatas);

			
			inventoryVOs = inventoryDetailsResponseData.getInventoryDetailVO().get(0).getInventoryVOs();
			
		}catch(UpdateBLException ex1){
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw new SearchBLException("Reserved Inventory Failed");
			
		}
		catch(SearchBLException ex) {
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw ex;
			
		}catch (Exception e){
			e.printStackTrace();
			throw new SearchBLException("Reserved Inventory Failed");
		}
		
		return inventoryVOs;
	}

	
	@Override
	public ResourceAvailibilityResponseData checkCPEResource(ResourceAvailibilityRequestData requestData)
			throws SearchBLException {
		ResourceAvailibilityResponseData responseData = new ResourceAvailibilityResponseData();
		
		List<InventoryData> inventoryDatas =  inventoryManagementSessionBeanLocal.checkCPEResource(requestData.getResourceId(),requestData.getWarehouseName());
		responseData.setResponseCode(0L);
		responseData.setResponseMessage("Success");
		
		if(inventoryDatas!=null && !inventoryDatas.isEmpty()) {
			List<ResourceAvailibilityVO> resourceAvailibilityVOs = new ArrayList<ResourceAvailibilityVO>();
			Map<Long, ResourceAvailibilityVO> map = new HashMap<Long, ResourceAvailibilityVO>();
			for(InventoryData inventoryData : inventoryDatas) {
				
				ResourceAvailibilityVO availibilityVO = null;
				if(map.containsKey(inventoryData.getWarehouseId())) {
					availibilityVO = map.get(inventoryData.getWarehouseId());
					availibilityVO.setAvailableResourceCount(availibilityVO.getAvailableResourceCount()+1);
				} else {
					availibilityVO = new ResourceAvailibilityVO();
					availibilityVO.setAvailableResourceCount(1L);
				}
				availibilityVO.setWarehouseName(inventoryData.getWarehousedata().getName());
				availibilityVO.setVendor(inventoryData.getItemData().getVendor());
				availibilityVO.setResourceType(inventoryData.getItemData().getResourceType().getName());
				if(inventoryData.getItemData().getResourceSubTypeData()!=null) {
					availibilityVO.setResourceSubType(inventoryData.getItemData().getResourceSubTypeData().getName());
				}
				availibilityVO.setResourceName(inventoryData.getItemData().getName());
				availibilityVO.setResourceId(inventoryData.getItemData().getResourceNumber());
				availibilityVO.setModel(inventoryData.getItemData().getModelnumber());
				
				map.put(inventoryData.getWarehouseId(), availibilityVO);
			}
			for(Entry<Long,ResourceAvailibilityVO> entry : map.entrySet()) {
				resourceAvailibilityVOs.add(entry.getValue());
			}
			responseData.setResourceAvailibilityVOs(resourceAvailibilityVOs);
		} else {
			responseData.setResponseMessage("No Resources Found");
		}
		
		
		return responseData;
	}


	@Override
	public List<InventoryStatusVO> changeInventoryStatus(List<InventoryRequestVO> inventoryRequestVOs,IBLSession iblSession) throws UpdateBLException {
		
		List<InventoryStatusVO> inventoryStatusVOs = new ArrayList<InventoryStatusVO>();
		
		List<InventoryRequestVO> validRequestVO = new ArrayList<InventoryRequestVO>();
		
		for(InventoryRequestVO inventoryRequestVO : inventoryRequestVOs) {
			boolean isAdd = false;
			
			InventoryStatusVO statusVO = new InventoryStatusVO();
			if(inventoryRequestVO!=null) {
				if(inventoryRequestVO.getInventoryNo()==null || inventoryRequestVO.getInventoryNo().isEmpty()) {
					statusVO.setResponseCode(InventoryMgtResponseCode.INVENTORY_NUMBER_OR_SERIALNUMBER_NOT_FOUND);
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_NUMBER_OR_SERIALNUMBER_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				} 
				if(!isAdd && (inventoryRequestVO.getOldStatus()==null || inventoryRequestVO.getOldStatus().isEmpty()
						|| inventoryRequestVO.getNewStatus()==null || inventoryRequestVO.getNewStatus().isEmpty())) {
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.INVENTORY_STATUS_NOT_FOUND);
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_STATUS_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				
				if(!isAdd) {
					boolean validate = inventoryManagementSessionBeanLocal.isValidInventoryChangeStatus(inventoryRequestVO.getOldStatus(), inventoryRequestVO.getNewStatus());
					if(!validate) {
						inventoryStatusVOs.add(new InventoryStatusVO(inventoryRequestVO.getInventoryNo(),-1L, "Not a Valid Change Status"));
					} else {
						validRequestVO.add(inventoryRequestVO);
					}
				}
				
			}
		}
		
		
		List<InventoryStatusVO> statusVOs = inventoryManagementSessionBeanLocal.changeInventoryStatus(validRequestVO,iblSession);
		if(statusVOs!=null && !statusVOs.isEmpty()) {
			inventoryStatusVOs.addAll(statusVOs);
		}
		
		return inventoryStatusVOs;

	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public void allocateInventory(List<ReserveAllocateRequestVO> inventoryList,IBLSession iblSession) throws SearchBLException{
		
		try {
			
			
			List<InventoryRequestVO> inventoryRequestVOs = new ArrayList<InventoryRequestVO>();
			for(ReserveAllocateRequestVO reserveAllocateRequestVO : inventoryList)
			{
				Map<String,Object> fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("inventoryNo", reserveAllocateRequestVO.getInventoryNo());
				
				List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
				if(filterList != null && !filterList.isEmpty()){
					InventoryData inventoryData = (InventoryData)filterList.get(0);
					if(inventoryData.getInventoryStatusId() != (InventoryStatusConstants.RESERVED)){
						throw new SearchBLException(InventoryMgtResponseCode.INVENTORY_RESERVE_NOT_FOUND);
					}
				}else{
					throw new SearchBLException(InventoryMgtResponseCode.INVENTORYNO_NOT_FOUND);
				}
				
				InventoryRequestVO inventoryRequestVO = new InventoryRequestVO();
				inventoryRequestVO.setInventoryNo(reserveAllocateRequestVO.getInventoryNo());
				inventoryRequestVO.setOldStatus(InventoryStatusConstants.RESERVED_STATUS);
				inventoryRequestVO.setNewStatus(InventoryStatusConstants.IN_USE_STATUS);
				inventoryRequestVO.setRemarks("WS:BookCPE, Mark for Allocate");
				inventoryRequestVOs.add(inventoryRequestVO);
				
			}
			
			List<InventoryStatusVO> inventoryStatusVOs = changeInventoryStatus(inventoryRequestVOs,iblSession);
			
		} catch (UpdateBLException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}catch(SearchBLException ex){
			throw ex;
		}catch(Exception e){
			e.printStackTrace();
			throw new SearchBLException("Allocate Inventory Failed");
		}
		
		
	}
	*/

	
	/**
	 * search Batch summary Data
	 * @author yash.kapasi
	 * @param BatchSummaryVO batchsummaryVO
	 * @param {@link IBLSession}
	 * @return {@link List}<{@link BatchSummaryVO}> batchSummaryVOs
	 */
	public List<BatchSummaryVO> searchBatchSummaryData (BatchSummaryVO batchsummaryVO,IBLSession iblSession){
		Logger.logTrace(MODULE, "inside searchBatchSummaryData()");
		List<BatchSummaryVO> batchSummaryVOs = null;
		List<BatchSummaryData> batchSummaryDatas=null;
		BatchData batchData=null;
		try {
			if(batchsummaryVO == null) throw new Exception("batchsummaryVO getting null");
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			//fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("batchNo",batchsummaryVO.getBatchnumber() );
			List filterList = batchManagementSessionBeanLocal.getFilterDataBy(EntityConstants.BATCH_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				batchData=((List<BatchData>)filterList).get(0);
				Logger.logTrace(MODULE, "inside searchBatchSummaryData()");
			}
			if(batchData!=null) {
				batchSummaryDatas = batchManagementSessionBeanLocal.searchBatchSummaryData(batchData.getBatchId());
				if(batchSummaryDatas != null && !batchSummaryDatas.isEmpty()){
					batchSummaryVOs=InventoryManagementUtil.getBatchSummaryVO(batchSummaryDatas);
					
					Logger.logTrace(MODULE,"Data receive:"+batchSummaryVOs);
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return batchSummaryVOs;
		
	}

	
	public InventoryUploadVO getBatchDetailInventoryData(String batchNo,boolean status){
		
		Logger.logInfo(MODULE, "in getBatchDetailInventoryData method");
		InventoryUploadVO uploadVO = new InventoryUploadVO();
		try {
			
			
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("ATTRIBUTEREL", "Resource");
			String attributeNames="";
			Set<String> headerSet=new LinkedHashSet<String>();
			headerSet.add("Sr.No");
			headerSet.add("Batch No");
			headerSet.add("Warehouse");
			headerSet.add("Resource Number");
			if(status){
				headerSet.add("InventoryNo");
			}
			
			
			List<Object[]> attributeDatas = attributeSessionBeanLocal.searchAttributeDataByBatchId(batchNo);
			if(attributeDatas != null && !attributeDatas.isEmpty()){
				StringBuilder attributeStrBuider = new StringBuilder(); 
				for(Object[] attributeData : attributeDatas){
					
					String name = attributeData[1]+"";
					headerSet.add(name.replaceAll(" ","_"));
					
					attributeStrBuider.append(attributeData[0]+"").append(" as ").append(name.replaceAll(" ","_")).append(",");
				}
				
				attributeNames = attributeStrBuider.toString();
				attributeNames=attributeNames.substring(0,attributeNames.length()-1);
			}
			
			if(!status){
				Logger.logTrace(MODULE, "searchValidInventoryUploadData() Adding Remarks");
				headerSet.add("Remarks");
			}
			
			CachedRowSetImpl cachedRowSetImpl = inventoryManagementSessionBeanLocal.searchValidInventoryUploadData(batchNo,attributeNames,status);
			
			StringBuilder builder = new StringBuilder(); 
			
			System.out.println(headerSet);
			
			
			for(String header : headerSet){
				builder.append(header).append(",");
			}
			builder.append("\n");
			
			CachedRowSet rs=cachedRowSetImpl;
			int count =0;
			while(rs.next()){
			
				count++;
				builder.append(count).append(",");
					
				for (String column : headerSet) {
						
						if (!column.equalsIgnoreCase("Sr.No")) {
//							Logger.logTrace(MODULE, "column value from DB:"+rs.getString(column));
							builder.append((rs.getString(column)!=null)?rs.getString(column):"").append(",");
						}
						
			       }
				builder.append("\n");	
		   }
			
//			Logger.logDebug(MODULE, "list :::::"+list);
			
			uploadVO.setUploadbyteData(new String(builder).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadVO;
	}

	public List<InventoryStatusLogVO> viewInventoryHistoryData(String inventorynumber,IBLSession iblSession){
		Logger.logInfo(MODULE, "in viewInventoryHistoryData method");
		List<InventoryStatusLogVO> inventoryStatusLogVOs = null;
		List<InventoryStatusLogData> inventoryStatusLogDatas=null;
		try {
			InventoryData inventoryData=null;
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			//fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("inventoryNo",inventorynumber );
			List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				 inventoryData =  (InventoryData)filterList.get(0);
			}
			if(inventoryData != null ){
				fieldValueMap.clear();
				filterList.clear();
				fieldValueMap.put("inventoryId",inventoryData.getInventoryId() );
				filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORYSTATUSLOG_DATA, fieldValueMap);
				if(filterList != null && !filterList.isEmpty()){
					inventoryStatusLogDatas = (List<InventoryStatusLogData>)filterList;
					inventoryStatusLogVOs=InventoryManagementUtil.getInventoryStatusLogVo (inventoryStatusLogDatas);
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return inventoryStatusLogVOs;
	}



	
	@Override
	public List<ComboData> getAllowedStatus(String inventoryNo)
			throws SearchBLException {
		
		List<ComboData> comboDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside getAllowedStatus() ::inventoryNo:: "+inventoryNo);
		try{
			
			List<String> notAllowedForCentral = new ArrayList<String>();
			
			notAllowedForCentral.add("Reserved");
			notAllowedForCentral.add("Allocated");
			notAllowedForCentral.add("Delivered");
			notAllowedForCentral.add("Recovered");
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("inventoryNo", inventoryNo);
			List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				InventoryData itemData = (InventoryData) filterList.get(0);
				int status = itemData.getInventoryStatusId();
				
				Logger.logTrace(MODULE, "StatusId :: "+status);
				
				Map<String,Object> statusValueMap = new LinkedHashMap<String, Object>();
				
				statusValueMap.put("statusId", (long)status);
				String  substatusname = null;
				List<String> notAllowedRepaired=new ArrayList<String>();
				List statusList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_STATUS_TRANSITION, statusValueMap);
				if(statusList!=null && !statusList.isEmpty()) {
					List<InventoryStatusTransition> transitions = (List<InventoryStatusTransition>)statusList;
					
					for(InventoryStatusTransition inventoryStatusTransition : transitions) {
						if(itemData.getWarehousedata().getWarehouseTypeData().getName().equals("Central")) {
							
							if(!notAllowedForCentral.contains(inventoryStatusTransition.getAllowedStatusData().getName())) {
								comboDatas.add(new ComboData(inventoryStatusTransition.getAllowedStatusId(),inventoryStatusTransition.getAllowedStatusData().getName()));
							}
						} else {
							comboDatas.add(new ComboData(inventoryStatusTransition.getAllowedStatusId(),inventoryStatusTransition.getAllowedStatusData().getName()));
						}
					}
				}
				List<ComboData> comboDatas1 = new ArrayList<ComboData>(comboDatas);
				if((InventoryStatusConstants.REPAIRED_STATUS).equals(itemData.getStatusData().getName())){
					if(itemData.getSubStatusData()!=null){
						substatusname = itemData.getSubStatusData().getName();
						if(!((InventoryStatusConstants.NEW_STATUS).equals(substatusname))){
							Logger.logTrace(MODULE, " :substatusname: "+substatusname);
							comboDatas.clear();
							for(ComboData data:comboDatas1){
								if(!(data.getName().equals("Retired"))){
								comboDatas.add(data);
								}
							}
					}


				}
			}
		}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return comboDatas;
		
	}

	@Override
	public void changeInventoryStatus(ChangeInventoryStatusVO statusVO,
			IBLSession blSession) throws UpdateBLException {
	
		
		try {
			
			Map map=new HashMap<String, String>();
			Map<String,Object> fieldValueResourceMap = new LinkedHashMap<String, Object>();

			fieldValueResourceMap.put("inventoryNo", statusVO.getInventoryNo());
			List filterInventoryList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueResourceMap);
			
			if(filterInventoryList!=null && !filterInventoryList.isEmpty()) {
				InventoryData typeData = (InventoryData) filterInventoryList.get(0);
				
				Set<Long> userWarehouses = blSession.getUserWarehouseMappings();
				if(userWarehouses!=null && !userWarehouses.contains(typeData.getWarehouseId())) {
					throw new UpdateBLException("You dont have permission to this Action");
				} 
				
				
				int oldStatus = typeData.getInventoryStatusId();
				Logger.logTrace(MODULE, "Old StatusId :: "+oldStatus);
				Logger.logTrace(MODULE, "new StatusId :: "+statusVO.getStatusId());
				
				AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareChangeStatusUpdateAudit(statusVO.getStatusId().intValue(), typeData,blSession);
				
				InventoryStatusLogData inventoryStatusLogData = InventoryManagementUtil.prepareInventoryStatusLogData(typeData.getInventoryId(),
						oldStatus, statusVO.getStatusId().intValue(), 
						 typeData.getStatusData().getName(), statusVO.getStatusName(), statusVO.getRemarks(), blSession);
				
				inventoryManagementSessionBeanLocal.persistInventoryStatusLog(inventoryStatusLogData);
				
				
				// Audit entry
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.OLD_STATUS,inventoryStatusLogData.getOldStatusName());
				mapAudit.put(AuditTagConstant.NEW_STATUS,inventoryStatusLogData.getNewStatusName());
				
				addToAuditDynamicMessage(AuditConstants.CHANGE_INVENTORY_STATUS, "Changing Inventory Status",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), blSession);
				
				
				
				/**
				 * Update Entity
				 */
				typeData.setUpdatedate(getCurrentTimestamp());
				typeData.setUpdatedby(blSession.getSessionUserId());
				typeData.setInventoryStatusId(statusVO.getStatusId().intValue());
				inventoryManagementSessionBeanLocal.updateInventory(typeData);
				
				
				if(InventoryStatusConstants.RELEASED_STATUS.equals(statusVO.getStatusName())) {
					
					
					Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
					fieldValueMap.put("name", InventoryStatusConstants.FAULTY_STATUS);
					List filterStatusList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORYSTATUS_DATA, fieldValueMap);
					if(filterStatusList!=null && !filterStatusList.isEmpty()) {
						InventoryStatusData inventoryStatusData = (InventoryStatusData) filterStatusList.get(0);
						
						InventoryStatusLogData inventoryStatusLogData1 = InventoryManagementUtil.prepareInventoryStatusLogData(typeData.getInventoryId(),
								statusVO.getStatusId().intValue(), inventoryStatusData.getInventoryStatusId().intValue(), 
								statusVO.getStatusName(), inventoryStatusData.getName(), statusVO.getRemarks(), blSession);
						
						inventoryManagementSessionBeanLocal.persistInventoryStatusLog(inventoryStatusLogData1);
						
						typeData.setInventoryStatusId(inventoryStatusData.getInventoryStatusId().intValue());
						inventoryManagementSessionBeanLocal.updateInventory(typeData);
						
					}
				}
				
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage()); 
		}
		
	}
	
	@Override
	public List<ComboBoxData> getAllowedSubStatus(String inventoryNo)
			throws SearchBLException {
		
		List<ComboBoxData> comboDatas = new ArrayList<ComboBoxData>();
		Logger.logTrace(MODULE, "inside getAllowedSubStatus() ::inventoryNo:: "+inventoryNo);
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("inventoryNo", inventoryNo);
			List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				InventoryData itemData = (InventoryData) filterList.get(0);
				
				Map<String,Object> fieldValueMapStatus = new LinkedHashMap<String, Object>();
				fieldValueMapStatus.put("statusId", itemData.getStatusData().getInventoryStatusId());
				List filterStatusList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_SUBSTATUS_TRANSITION, fieldValueMapStatus);
				if(filterStatusList!=null && !filterStatusList.isEmpty()) {
					List<InventorySubStatusTransition> subStatusTransitions =  filterStatusList;
					for(InventorySubStatusTransition transition : subStatusTransitions) {
					//Added By Rinkal --Commented to add ID instead of alias
					//	comboDatas.add(new ComboBoxData(transition.getAllowedStatusData().getAlias(), transition.getAllowedStatusData().getName()));
						comboDatas.add(new ComboBoxData(transition.getAllowedStatusData().getInventorySubStatusId().toString(), transition.getAllowedStatusData().getName()));
					}
				}
				
				
				/*String status = itemData.getStatusData().getName();
				
				if(status.equals(InventoryStatusConstants.NEW_STATUS)){
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.STANDBY_SUBSTATUS, InventoryStatusConstants.STANDBY_SUBSTATUS));
				}else if(status.equals(InventoryStatusConstants.AVAILABLE_STATUS) || status.equals(InventoryStatusConstants.FAULTY_STATUS)){
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.ACCEPTED_SUBSTATUS, InventoryStatusConstants.ACCEPTED_SUBSTATUS));
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.STANDBY_SUBSTATUS, InventoryStatusConstants.STANDBY_SUBSTATUS));
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.NEW_SUBSTATUS, InventoryStatusConstants.NEW_SUBSTATUS));
					
				}else if(status.equals(InventoryStatusConstants.RESERVED_STATUS) || status.equals(InventoryStatusConstants.RELEASED_STATUS) || status.equals(InventoryStatusConstants.SCRAPPED_STATUS)){
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.STANDBY_SUBSTATUS, InventoryStatusConstants.STANDBY_SUBSTATUS));
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.NEW_SUBSTATUS, InventoryStatusConstants.NEW_SUBSTATUS));
					
				}else if(status.equals(InventoryStatusConstants.IN_USE_STATUS) || status.equals(InventoryStatusConstants.DELIVERED_STATUS)){
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.REFURBISHED_SUBSTATUS, InventoryStatusConstants.REFURBISHED_SUBSTATUS));
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.STANDBY_SUBSTATUS, InventoryStatusConstants.STANDBY_SUBSTATUS));
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.NEW_SUBSTATUS, InventoryStatusConstants.NEW_SUBSTATUS));
					
				}else if(status.equals(InventoryStatusConstants.REPAIRED_STATUS)){
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.REFURBISHED_SUBSTATUS, InventoryStatusConstants.REFURBISHED_SUBSTATUS));
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.STANDBY_SUBSTATUS, InventoryStatusConstants.STANDBY_SUBSTATUS));
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.NEW_SUBSTATUS, InventoryStatusConstants.NEW_SUBSTATUS));
					comboDatas.add(new ComboBoxData(InventoryStatusConstants.ACCEPTED_SUBSTATUS, InventoryStatusConstants.ACCEPTED_SUBSTATUS));
				}*/
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboDatas;
		
	}

	@Override
	public void changeInventorySubStatus(ChangeInventorySubStatusVO statusVO,
			IBLSession blSession) throws UpdateBLException{
		
		Logger.logTrace(MODULE, "inside changeInventorySubStatus()");
		try {
			Map map=new HashMap<String, String>();
			Map<String,Object> fieldValueResourceMap = new LinkedHashMap<String, Object>();

			fieldValueResourceMap.put("inventoryNo", statusVO.getInventoryNo());
			List filterInventoryList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueResourceMap);
			
			Map<String,Object> mapAudit = new HashMap<String, Object>();
			if(filterInventoryList!=null && !filterInventoryList.isEmpty()) {
				
				InventoryData inventoryData = (InventoryData) filterInventoryList.get(0);
				
				Set<Long> userWarehouses = blSession.getUserWarehouseMappings();
				if(userWarehouses!=null && !userWarehouses.contains(inventoryData.getWarehouseId())) {
					throw new UpdateBLException("You dont have permission to this Action");
				} 
				
				int oldStatus = inventoryData.getInventoryStatusId();
				int oldSubStatusId = 0;
				//Long oldSubStatusId=inventoryData.getInventorySubStatusId();
				String oldStatusName=null;
				if(inventoryData.getInventorySubStatusId()!=null){
					oldSubStatusId=(inventoryData.getInventorySubStatusId()).intValue();
					oldStatusName=inventoryData.getSubStatusData().getName();
				}
				InventoryStatusLogData inventoryStatusLogData = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),
						oldSubStatusId, Integer.parseInt(statusVO.getSubStatus()), 
						oldStatusName, statusVO.getStatus(), "SubStatus Changed:"+statusVO.getStatus()+",Reason:"+statusVO.getReason(), blSession);
				
				inventoryManagementSessionBeanLocal.persistInventoryStatusLog(inventoryStatusLogData);
				
				AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareChangeSubStatusUpdateAudit(statusVO, inventoryData,blSession);
				if(statusVO.getSubStatus() != null){
					mapAudit.put(AuditTagConstant.NEW_STATUS,inventoryStatusLogData.getNewStatusName());
				}
				if(inventoryStatusLogData.getOldStatusName()!=null){
					mapAudit.put(AuditTagConstant.OLD_STATUS,inventoryStatusLogData.getOldStatusName());
				}else{
					mapAudit.put(AuditTagConstant.OLD_STATUS,"null");
				}

/*				if(statusVO.getSubStatus() != null && statusVO.getSubStatus().equals(InventoryStatusConstants.ACCEPTED_SUBSTATUS_ALIAS)){
					inventoryData.setAccepted("Y");
					mapAudit.put(AuditTagConstant.SUB_STATUS,"Accepted");
				}else if(statusVO.getSubStatus() != null && statusVO.getSubStatus().equals(InventoryStatusConstants.NEW_SUBSTATUS_ALIAS)){
					inventoryData.setNewed("Y");
					mapAudit.put(AuditTagConstant.SUB_STATUS,"New");
				}else if(statusVO.getSubStatus() != null && statusVO.getSubStatus().equals(InventoryStatusConstants.REFURBISHED_SUBSTATUS_ALIAS)){
					inventoryData.setRefurbished("Y");
					mapAudit.put(AuditTagConstant.SUB_STATUS,"Refurnished");
				}else if(statusVO.getSubStatus() != null && statusVO.getSubStatus().equals(InventoryStatusConstants.STANDBY_SUBSTATUS_ALIAS)){
					inventoryData.setStandBy("Y");
					mapAudit.put(AuditTagConstant.SUB_STATUS,"Standby");
				}
				
*/				Logger.logTrace(MODULE, mapAudit+"");
				Logger.logTrace(MODULE, "::::::::::::::substatusid:::::::::::"+statusVO.getSubStatus()+"");
				inventoryData.setInventorySubStatusId(Long.parseLong(statusVO.getSubStatus()));
				inventoryData.setUpdatedate(getCurrentTimestamp());
				inventoryData.setUpdatedby(blSession.getSessionUserId());
				inventoryManagementSessionBeanLocal.updateInventory(inventoryData);
				
				addToAuditDynamicMessage(AuditConstants.CHANGE_INVENTORY_SUBSTATUS, "Changing Inventory Sub Status",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), blSession);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage()); 
		}
		
	}
	@Override
	public List<InventoryDetailVO> searchInventory(SearchInventoryVO inventoryDetailVO) {
	
		Logger.logTrace(MODULE, "inside searchInventory()");
		List<InventoryDetailVO> inventoryDetailVOs = null;
		try {
			
			Map<String, UserVO> map = UserFactory.findAllUser();
			
			CachedRowSetImpl cachedRowSetImpl= inventoryManagementSessionBeanLocal.searchInventory(inventoryDetailVO);
			if(cachedRowSetImpl != null){
				inventoryDetailVOs=InventoryManagementUtil.getInventoryDetailVO(cachedRowSetImpl,map);
					//warehouseVOs.add(MasterUtil.getWarehouseVO(dataObj));
//				Logger.logTrace(MODULE,"Data receive:"+inventoryDetailVOs);
					//System.out.println("[JM]Data  size in facade:"+inventoryDetailVOs.size());
					
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return inventoryDetailVOs;
		
	}


	public String placeOrder(PlaceOrderVO placeOrderVO,IBLSession iblSession) throws CreateBLException{
		String orderNo=null;
		OrderData orderData;
		PlaceOrderNotificationEmailVO emailVO=new PlaceOrderNotificationEmailVO();
	//	PlaceOrderNotificationEmailVO toEmailVO=new PlaceOrderNotificationEmailVO();
		Logger.logTrace(MODULE, " calling Facade placeOrder()");
		try {
			if(placeOrderVO == null){
				throw new CreateBLException("placeOrderVO can't be null");
			}if(placeOrderVO.getFromwarehouseId() == null || placeOrderVO.getTowarehouseId() == null){
				throw new CreateBLException("From warehouse or To warehouse getting null");
			}
			
			 orderNo = systemInternalSessionBeanLocal.getPrimaryKey(CPECommonConstants.ORDER_DATA);
			Logger.logTrace(MODULE, " calling InventoryManagementUtil getOrderData() with OrderNo:"+orderNo);
			 orderData=InventoryManagementUtil.getOrderData(placeOrderVO,orderNo,iblSession);
			
			
			InventoryTransferOrderStatus transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_IN_PROGRESS);
			orderData.setOrderStatusId(transferOrderStatus.getOrderStatusId());
			Logger.logTrace(MODULE, "Before calling sessionBean saveOrderData():"+orderData);
			orderData=inventoryManagementSessionBeanLocal.saveOrderData(orderData, iblSession);
			
			Logger.logTrace(MODULE, " Before calling sessionBean saveOrderData()::"+orderData);
			
			NotificationData notificationData = InventoryManagementUtil.convertPlaceOrderEmailVOForPlaceOrder(orderData,NotificationConstants.PLACE_ORDER);
			Logger.logTrace(MODULE, " Before calling Util processPlaceOrderNotificationEmailCommon()::"+notificationData);
			sendNotification(notificationData);
//			InventoryManagementUtil.processPlaceOrderNotificationEmailCommon(emailVO);
			
		// Audit entry for TransferOrderData
		Map<String,Object> mapAudit = new HashMap<String, Object>();
		mapAudit.put(AuditTagConstant.TRANSFER_ORDER_NUMBER,orderNo);
		mapAudit.put(AuditTagConstant.FROMWAREHOUSE,orderData.getFromWarehouseData().getName());
		mapAudit.put(AuditTagConstant.TOWAREHOUSE,orderData.getToWarehouseData().getName());
		addToAuditDynamicMessage(AuditConstants.CREATE_PLACE_ORDER, "Creating Place Order",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, iblSession);
		}catch (CreateBLException e) {
			Logger.logTrace(MODULE, "Inside CreateBLException");
			e.printStackTrace();
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			throw new CreateBLException("Place Order failed due to some reason");
		}
		catch(Exception e){
			Logger.logTrace(MODULE, "Inside Exception");
			e.printStackTrace();
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw new CreateBLException("Place Order  failed due to some reason");
		}
		return orderNo;
		
	}

	public List<PlaceOrderVO> searchPlaceOrderData(PlaceOrderVO placeOrderVO,IBLSession iblSession){
		
		List<PlaceOrderVO> placeOrderVOs=new ArrayList<PlaceOrderVO>();
		try {
			List<OrderData> data = inventoryManagementSessionBeanLocal.searchPlaceOrderData(placeOrderVO,iblSession);
			if(data != null){
				for(OrderData dataObj : data){
					placeOrderVOs.add(InventoryManagementUtil.getPlaceOrderVO(dataObj));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		return placeOrderVOs;
		
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public void acceptPlaceOrder(SearchTransferInventory searchTransferInventory,IBLSession iblSession)throws CreateBLException{
		
		Logger.logTrace(MODULE, "inside acceptPlaceOrder()");
		try {
			PlaceOrderNotificationEmailVO emailVO=new PlaceOrderNotificationEmailVO();
			Map<String, Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("orderNo", searchTransferInventory.getOrderNo());
			
			List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.ORDER_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				OrderData orderData = (OrderData)filterList.get(0);
				PlaceOrderVO placeOrderVO=new PlaceOrderVO();
				InventoryTransferOrderStatus inventoryTransferOrderStatus=null;
				// for accept all or reject all
				////for Audit
				placeOrderVO.setRemark(searchTransferInventory.getRemark());
				placeOrderVO.setAcceptquantity(searchTransferInventory.getAcceptQuantity());
				if(searchTransferInventory.getOrderStatus().equalsIgnoreCase("acceptall")){
					inventoryTransferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_ACCEPTED);
				}else if(searchTransferInventory.getOrderStatus().equalsIgnoreCase("rejectall")){
					inventoryTransferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_REJECTED);
				}else{
					inventoryTransferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_PARTIALLY_ACCEPTED);
				}
				placeOrderVO.setStatus(inventoryTransferOrderStatus.getName());
				placeOrderVO.setAcceptRejectDate(getCurrentTimestamp());
				AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.preparePlaceOrderacceptRejectAudit(orderData,placeOrderVO );
				
				
				////
				if(searchTransferInventory.getOrderStatus().equalsIgnoreCase("acceptall") || 
						searchTransferInventory.getOrderStatus().equalsIgnoreCase("rejectall")){
				
						//make a list of inventories which can be transfer from 1 warehouse to another warehouse
						String transferStatus = null;
						if(searchTransferInventory.getOrderStatus().equalsIgnoreCase("acceptall")){
							InventoryTransferOrderStatus transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_ACCEPTED);
							orderData.setOrderStatusId(transferOrderStatus.getOrderStatusId());
							
							orderData.setRemarks(searchTransferInventory.getRemark());
							orderData.setAcceptQuantity(searchTransferInventory.getAcceptQuantity());
							//placeOrderVO.setStatus(transferOrderStatus.getName());
							//placeOrderVO.setAcceptquantity(searchTransferInventory.getAcceptQuantity());
							transferStatus = "ACCEPTED";
						}else if(searchTransferInventory.getOrderStatus().equalsIgnoreCase("rejectall")){
							InventoryTransferOrderStatus transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_REJECTED);
							orderData.setOrderStatusId(transferOrderStatus.getOrderStatusId());
							orderData.setRemarks(searchTransferInventory.getRemark());
							orderData.setAcceptQuantity(0L);
							
							
							transferStatus = "REJECTED";
						}
						
						
						
					
				}else{
					
					InventoryTransferOrderStatus transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_PARTIALLY_ACCEPTED);
					orderData.setOrderStatusId(transferOrderStatus.getOrderStatusId());
					
					orderData.setRemarks(searchTransferInventory.getRemark());
					orderData.setAcceptQuantity(searchTransferInventory.getAcceptQuantity());
					
					
					// call for partial accept/ reject
				}
				orderData.setAcceptRejectDate(getCurrentTimestamp());
				orderData.setUpdatedate(getCurrentTimestamp());
				orderData.setUpdatedby(iblSession.getSessionUserId());
				
				transferSessionBeanLocal.updatePlaceOrder(orderData);
				//for gettting total available inventories for particular warehouse-- added start
				long totalavailableinventories = inventoryManagementSessionBeanLocal.getTotaltransferrableInventory(orderData.getToWarehouseId(),orderData.getResourceSubTypeId(),orderData.getResourceTypeId());
				//int totalinventories =0;
				Logger.logTrace(MODULE, "available totalinventories:::: "+totalavailableinventories);
				//--added end
				//sending Email to from and To warehouse owner
				NotificationData notification = InventoryManagementUtil.convertPlaceOrderEmailVOForPlaceOrder(orderData, NotificationConstants.ACCEPT_REJECT_PLACE_ORDER,totalavailableinventories);
				Logger.logTrace(MODULE, " Before calling Util processPlaceOrderNotificationEmailCommon()::"+notification);
				sendNotification(notification);
//				InventoryManagementUtil.processAcceptPlaceOrderNotificationEmail(emailVO);
				
				//Audit Entry
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.TRANSFER_ORDER_NUMBER,orderData.getOrderNo());
				mapAudit.put(AuditTagConstant.FROMWAREHOUSE,orderData.getFromWarehouseData().getName());
				mapAudit.put(AuditTagConstant.TOWAREHOUSE,orderData.getToWarehouseData().getName());
				mapAudit.put(AuditTagConstant.STATUS,placeOrderVO.getStatus());
				addToAuditDynamicMessage(AuditConstants.ACCEPT_REJECT_PLACE_ORDER, "Accept/Reject Place Order",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), iblSession);
				
		
			}
		}catch (Exception e) {
				e.printStackTrace();
				
				if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
				
				throw new CreateBLException("Sorry!!! Accept/Reject Place Order Failed");
				
			}
	}
	
	public PlaceOrderVO searchPlaceOrderDataByOrderNo(PlaceOrderVO placeOrderVO,IBLSession iblSession){
		
		PlaceOrderVO placeOrderVO2=new PlaceOrderVO ();
		try {
			Logger.logTrace(MODULE, "inside searchPlaceOrderDataByOrderNo()");
			Map<String, Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("orderNo", placeOrderVO.getOrderNo());
			
			List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.ORDER_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				OrderData orderData = (OrderData)filterList.get(0);
				if(orderData!=null){
					placeOrderVO2=InventoryManagementUtil.getPlaceOrderVO(orderData);
				}
			
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		Logger.logTrace(MODULE, "inside searchPlaceOrderDataByOrderNo() completed");
		return placeOrderVO2;
		
	}
	public boolean searchTransferOrder(String orderNo) {
			boolean flag=false;
			Logger.logTrace(MODULE, "inside searchTransferOrder()");
		try {
			Map<String, Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("transferOrderNo", orderNo);
		
			List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDER_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
			TransferOrderData transferOrderData=(TransferOrderData)filterList.get(0);
			Logger.logTrace(MODULE, "inside searchTransferOrder() transferOrderData::"+transferOrderData);
			flag=true;
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		Logger.logTrace(MODULE, "inside searchTransferOrder() completed-->"+flag);
	return flag;
}
	public void transferPlaceOrder(PlaceOrderVO placeOrderVO,IBLSession iblSession)throws CreateBLException{
		Logger.logTrace(MODULE, "inside transferPlaceOrder() started::"+placeOrderVO);
		try {
			Map<String, Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("orderNo", placeOrderVO.getOrderNo());
			
			List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.ORDER_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				OrderData orderData = (OrderData)filterList.get(0);
				InventoryTransferOrderStatus transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_COMPLETED);
				placeOrderVO.setStatus(transferOrderStatus.getName());
				Logger.logTrace(MODULE, "inside transferPlaceOrder() status::"+transferOrderStatus.getName());
				
				AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareTransferPlaceOrderAudit(orderData,placeOrderVO );
				orderData.setOrderStatusId(transferOrderStatus.getOrderStatusId());
				orderData.setTransferOrderNo(placeOrderVO.getTransferOrderNo());
				orderData.setTransferRemarks(placeOrderVO.getTransferRemark());
				orderData.setUpdatedate(getCurrentTimestamp());
				orderData.setUpdatedby(iblSession.getSessionUserId());
				transferSessionBeanLocal.updatePlaceOrder(orderData);
				
				//Audit Entry
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.TRANSFER_ORDER_NUMBER,orderData.getTransferOrderNo());
				mapAudit.put(AuditTagConstant.PLACE_ORDER_NUMBER,orderData.getOrderNo());
				addToAuditDynamicMessage(AuditConstants.UPDATE__PLACE_ORDER, "Update Place Order",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), iblSession);
				
				Logger.logTrace(MODULE, "inside transferPlaceOrder() completed");
			}
				
			
	}catch (Exception e) {
		e.printStackTrace();
		
		if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
		
		throw new CreateBLException("Sorry!!! Transfer Place Order Failed");
		
		}
	}





	/**
	 * search Transferrable Status
	 * @author yash.kapasi
	 * @return {@link List}<{@link ComboData}> comboBoxDatas
	 * @throws SearchBLException
	 */
	@Override
	public List<ComboData> searchTransferrableStatus() throws SearchBLException {
		
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside searchTransferrableStatus()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("transferrable", 'Y');
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORYSTATUS_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				List<InventoryStatusData> inventoryStatusDatas = (List<InventoryStatusData>)filterList;
				for(InventoryStatusData inventoryStatusData : inventoryStatusDatas){
					comboBoxDatas.add(new ComboData(inventoryStatusData.getInventoryStatusId(), inventoryStatusData.getName()));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
		
	}





	/**
	 * search Place Order Detail
	 * @author yash.kapasi
	 * @param {@link PlaceOrderVO} placeOrderVO
	 * @param {@link IBLSession}
	 * @return {@link List}<{@link PlaceOrderVO}> placeOrderVOs
	 * @throws SearchBLException
	 */
	@Override
	public List<PlaceOrderVO> searchPlaceOrderDetail(PlaceOrderVO placeOrderVO,
			IBLSession blSession) throws SearchBLException {
		
		List<PlaceOrderVO> placeOrderVOs=new ArrayList<PlaceOrderVO>();
		try {
			List<OrderData> data = inventoryManagementSessionBeanLocal.searchPlaceOrderDetail(placeOrderVO,blSession);
			if(data != null){
				for(OrderData dataObj : data){
					placeOrderVOs.add(InventoryManagementUtil.getPlaceOrderVO(dataObj));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		return placeOrderVOs;
		
	}


	@Override
	public InventoryDetailVO searchInventoryDetailDataById(
			String inventoryId) throws SearchBLException {
		
		InventoryDetailVO detailVO = null;
		Logger.logTrace(MODULE, "inside searchInventoryDetailDataById()");
		List<InventoryDetailVO> inventoryDetailVOs = null;
		try {
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", 'N');
			fieldValueMap.put("inventoryNo", inventoryId);
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				InventoryData data = (InventoryData) filterList.get(0);
				List<AttributeTransData> attributeData = attributeSessionBeanLocal.searchAttributeTransDataByInventoryId(data.getInventoryId());
				
				detailVO = InventoryManagementUtil.getInventoryDetailForSearch(data,attributeData);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return detailVO;
		
	}	
	@Override
	public int updateInventoryStatusInBulk(BulkChangeInventoryStatusVO statusVO,
			IBLSession blSession) throws UpdateBLException 
			{
		int updateCount=0;
		Logger.logTrace(MODULE, "inside updateInventoryStatusInBulk()");
		try{
		Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
		fieldValueMap.put("systemgenerated", "N");
		fieldValueMap.put("resourceNumber", statusVO.getResourceNumber());
		Logger.logTrace(MODULE, "BulkChangeInventoryStatusVO "+statusVO);
		List<ItemData> filterList = itemSessionBeanLocal.getFilterDataBy(EntityConstants.ITEM_DATA, fieldValueMap);
		if(filterList != null && !filterList.isEmpty()){
			ItemData itemData = (ItemData) filterList.get(0);
			Set<InventoryData> inventoryData  = itemData.getInventoryDatas();
			if(inventoryData!=null &&!inventoryData.isEmpty()){
			for(InventoryData  data:inventoryData){
				if(data.getTransferInventoryStatus()==null){
				if(((data.getInventoryStatusId())==(statusVO.getStatusFromId().intValue()))){
	
					if((statusVO.getStatusNewName()).equals(InventoryStatusConstants.AVAILABLE_STATUS)&&((data.getStatusData().getName()).equals(InventoryStatusConstants.NEW_STATUS)) ){
						data.setInventorySubStatusId(Long.valueOf((InventoryStatusConstants.NEW)));
					}
					AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareChangeStatusUpdateAudit(statusVO.getStatusToId().intValue(), data,blSession);
					data.setInventoryStatusId(statusVO.getStatusToId().intValue());
					inventoryManagementSessionBeanLocal.updateInventory(data);
					updateCount++;
					//--Audit Start 
					InventoryStatusLogData inventoryStatusLogData = InventoryManagementUtil.prepareInventoryStatusLogData(data.getInventoryId(),statusVO.getStatusFromId().intValue(), statusVO.getStatusToId().intValue(), data.getStatusData().getName(),statusVO.getStatusNewName(),statusVO.getRemarks(), blSession);
					inventoryManagementSessionBeanLocal.persistInventoryStatusLog(inventoryStatusLogData);
					Map<String,Object> mapAudit = new HashMap<String, Object>();
					mapAudit.put(AuditTagConstant.OLD_STATUS,inventoryStatusLogData.getOldStatusName());
					mapAudit.put(AuditTagConstant.NEW_STATUS,inventoryStatusLogData.getNewStatusName());

					addToAuditDynamicMessage(AuditConstants.BULK_STATUS_CHANGE_FOR_INVENTORY, "Bulk Status Change For Inventory",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), blSession);
					//--Audit End
				}
			}
			}
		}
			Logger.logTrace(MODULE, "Total Inventory Updated :: "+updateCount);
		}
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage()); 
		}
		return updateCount;
		}
	
	@Override
	public List<Long> getPendingPlaceOrderMaster () throws SearchBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside getPendingPlaceOrderMaster");
		}

		//List<WarehouseVO> data = null;
		String minPendingDays = null;
		try {
			minPendingDays = systemParameterFacadeLocal.getSystemParameterValue(SystemParameterConstants.MIN_DAYS_OF_PENDING_ORDER);
		} catch (SearchBLException e) {
			e.printStackTrace();
		}

		List<Long> warehouseIds = inventoryManagementSessionBeanLocal.getPendingPlaceOrderMaster(minPendingDays);
		
		
		return warehouseIds;
	}
	
	
	@Override
	public List<TransferOrderVO> getPendingTransferOrderChild(Long towarehouseid) throws SearchBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside getPendingTransferOrderChild");
		}
		List<TransferOrderData> childdata = null;
		List<TransferOrderVO> orderDetailVos = new ArrayList<TransferOrderVO>();
		try {
			String minPendingDays = systemParameterFacadeLocal.getSystemParameterValue(SystemParameterConstants.MIN_DAYS_OF_PENDING_ORDER);
			
			childdata = inventoryManagementSessionBeanLocal.getPendingTransferOrderChild(minPendingDays,towarehouseid);
			
			if(childdata!=null && !childdata.isEmpty()){
				for (TransferOrderData orderData : childdata){
					orderDetailVos.add(InventoryManagementUtil.getTransferOrderVO(orderData));				
				}
			}
			
			Logger.logTrace(MODULE, "getPendingPlaceOrderChild");
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
		return orderDetailVos;
		
	}
	@Override
	public List<PlaceOrderVO> getPendingPlaceOrderChild (Long towarehouseid) throws SearchBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside getPendingPlaceOrderChild");
		}
		List<OrderData> childdata = null;
		
		List<PlaceOrderVO> orderDetailVos = new ArrayList<PlaceOrderVO>();
		
		try {
			String minPendingDays = systemParameterFacadeLocal.getSystemParameterValue(SystemParameterConstants.MIN_DAYS_OF_PENDING_ORDER);
			childdata = inventoryManagementSessionBeanLocal.getPendingPlaceOrderChild(minPendingDays,towarehouseid);
			
			if(childdata!=null && !childdata.isEmpty()) {
				for (OrderData orderData : childdata){
					orderDetailVos.add(InventoryManagementUtil.getPlaceOrderVO(orderData));
				}
			}
			Logger.logTrace(MODULE, "getPendingPlaceOrderChild");
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
		return orderDetailVos;
		
	}
	
	@Override
	public Boolean saveOrderNotificationAgentHistory(OrderDetailVo orderDetailVo) throws CreateBLException{
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside saveOrderNotificationAgentHistory");
		}
		Boolean agentHistoryData=null;
		//List<WarehouseVO> data = null;
		
		try {
			
			SystemAgentRunQueue agentRunQueue = systemAgentSessionBeanLocal.findAgentRunInQueue(orderDetailVo.getAgenetRunQueueId());
			orderDetailVo.setAgentRunDetailId(agentRunQueue.getAgentrundetailid());
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
		
		agentHistoryData = inventoryManagementSessionBeanLocal.saveOrderNotificationAgentHistory(orderDetailVo);
		
		return true;
	}


	@Override
	public List<Long> getPendingTransferOrderMaster() throws SearchBLException {
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside getPendingTransferOrderMaster");
		}

		String minPendingDays = null;
		try {
			minPendingDays = systemParameterFacadeLocal.getSystemParameterValue(SystemParameterConstants.MIN_DAYS_OF_PENDING_ORDER);
		} catch (SearchBLException e) {
			e.printStackTrace();
		}

		List<Long> warehouseIds = inventoryManagementSessionBeanLocal.getPendingTransferOrderMaster(minPendingDays);
		
		
		return warehouseIds;
		
	}
	
	
}

