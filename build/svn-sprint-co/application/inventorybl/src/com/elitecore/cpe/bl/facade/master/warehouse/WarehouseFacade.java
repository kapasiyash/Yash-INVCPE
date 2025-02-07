package com.elitecore.cpe.bl.facade.master.warehouse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.constants.user.UserConstants;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.data.notification.DocumentTemplateWrapperdata;
import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.data.system.audit.AuditSummaryDetail;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.master.ConfigureThresholdData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceSubTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseTypeData;
import com.elitecore.cpe.bl.entity.inventory.system.systemparameter.SystemParameter;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementFacadeLocal;
import com.elitecore.cpe.bl.facade.master.item.ItemUtil;
import com.elitecore.cpe.bl.facade.system.audit.AuditDataConversionUtilities;
import com.elitecore.cpe.bl.facade.system.audit.SystemAuditFacadeLocal;
import com.elitecore.cpe.bl.facade.system.internal.SystemInternalDataConversionUtil;
import com.elitecore.cpe.bl.facade.system.systemparameter.SystemParameterFacadeLocal;
import com.elitecore.cpe.bl.facade.system.user.UserFacadeLocal;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.session.inventorymgt.InventoryManagementSessionBeanLocal;
import com.elitecore.cpe.bl.session.master.warehouse.WarehouseSessionBeanLocal;
import com.elitecore.cpe.bl.session.system.internal.SystemInternalSessionBeanLocal;
import com.elitecore.cpe.bl.session.system.systemparameter.SystemParameterSessionBeanLocal;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.bl.vo.inventorymgt.ThresholdNotificationEmailVO;
import com.elitecore.cpe.bl.vo.master.ConfigureThresholdVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.ThresholdStatusVO;
import com.elitecore.cpe.bl.vo.master.WareHouseSummaryVO;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.bl.vo.master.warehouse.CreateWareHouseTreeVO;
import com.elitecore.cpe.bl.vo.system.user.UserVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;
import com.sun.rowset.CachedRowSetImpl;

@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class WarehouseFacade extends BaseFacade implements WarehouseFacadeRemote,WarehouseFacadeLocal{

	private static final String MODULE = "WAREHOUSE-FC";
	
	@EJB private WarehouseSessionBeanLocal warehouseSessionBeanLocal;
	@EJB private SystemInternalSessionBeanLocal systemInternalSessionBeanLocal;
	@EJB private SystemAuditFacadeLocal systemAuditFacadeLocal;
	@EJB private InventoryManagementSessionBeanLocal inventoryManagementSessionBeanLocal;
	@EJB private InventoryManagementFacadeLocal inventoryManagementFacadeLocal;
	@EJB private  SystemParameterFacadeLocal systemParameterFacadeLocal;
	@EJB private UserFacadeLocal userFacadeLocal;
	@EJB private SystemParameterSessionBeanLocal systemParameterSessionBeanLocal;
	
	
	
	/**
	 * Save Warehouse Data 
	 * @author yash.kapasi
	 * @param {@link WarehouseVO} warehouseVO
	 * @param {@link IBLSession} iblSession
	 * @throws CreateBLException
	 */
	@Override
	public void saveWarehouse(WarehouseVO warehouseVO,IBLSession iblSession) throws CreateBLException {
		
		Logger.logTrace(MODULE, "inside saveWarehouse()");
		try {

			if(!(warehouseSessionBeanLocal.isWarehouseExist(warehouseVO.getName()))){
			warehouseVO.setSystemgenerated("N");
			warehouseVO.setEditable("Y");
			
			WarehouseData warehouseData = WarehouseUtil.getWarehouseData(warehouseVO);
			
			warehouseSessionBeanLocal.saveWarehouse(warehouseData);

			// Audit entry
			Map<String,Object> mapAudit = new HashMap<String, Object>();
			mapAudit.put(AuditTagConstant.NAME,warehouseVO.getName());
			
			addToAuditDynamicMessage(AuditConstants.CREATE_WAREHOUSE, "Creating Warehouse",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, iblSession);
			}else{
				throw new CreateBLException("Warehose Name " + warehouseVO.getName() +" Already Exists");
			}
		}catch (CreateBLException e) {
			throw e;
		}
	}

	
	/**
	 * Search Warehouse Data 
	 * @author yash.kapasi
	 * @param {@link WarehouseVO} warehouseVO
	 * @param {@link IBLSession} iblSession
	 * @return {@link List} <{@link WarehouseVO}> warehouseVOs
	 */
	@Override
	public List<WarehouseVO> searchWarehouseData(WarehouseVO warehouseVO,IBLSession iblSession){
		Logger.logTrace(MODULE, "inside searchWarehouseData()");
	
		List<WarehouseVO> warehouseVOs = new ArrayList<WarehouseVO>();
		try {
			List<WarehouseData> data = warehouseSessionBeanLocal.searchWarehouseData(warehouseVO);
			if(data != null){
				for(WarehouseData dataObj : data){
					warehouseVOs.add(WarehouseUtil.getWarehouseVO(dataObj));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return warehouseVOs;
	}

	
	/**
	 * View Warehouse Data 
	 * @author yash.kapasi
	 * @param {@link WarehouseVO} warehouseVO
	 * @param {@link IBLSession} iblSession
	 * @return WarehouseVO warehouseVO
	 */
	@Override
	public WarehouseVO viewWarehouse(WarehouseVO warehouseVO,IBLSession iblSession) {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside viewWarehouse()");
		WarehouseVO warehouseVO2 = null;
		try {
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("warehouseId", warehouseVO.getWarehouseId());
			
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				warehouseVO2 = WarehouseUtil.getWarehouseVO(((List<WarehouseData>)filterList).get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return warehouseVO2;
	}
	
	
	/**
	 * Update Warehouse Data 
	 * @author yash.kapasi
	 * @param {@link WarehouseVO} warehouseVO
	 * @param {@link IBLSession} iblSession
	 * @throws UpdateBLException
	 */
	public void updateWarehouse(WarehouseVO warehouseVO,IBLSession iblSession) throws UpdateBLException{
		Logger.logTrace(MODULE, "inside updateWarehouse()");
		try {
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("warehouseId", warehouseVO.getWarehouseId());
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
			
			WarehouseData warehouseData = null;
			if(filterList != null && !filterList.isEmpty()){
				
				Logger.logInfo(MODULE, "getting data in filter data ::::");
				warehouseData = ((List<WarehouseData>)filterList).get(0);
				if(!warehouseData.getName().equalsIgnoreCase(warehouseVO.getName())){
					if(!(warehouseSessionBeanLocal.isWarehouseExist(warehouseVO.getName()))){
						
					}else{
						throw new UpdateBLException("Warehouse Name " + warehouseVO.getName() +" Already Exists");
					}
				}
				if(warehouseData.getContactNo()!=null && !warehouseData.getContactNo().equalsIgnoreCase(null)){
					Logger.logTrace(MODULE, "inside updateWarehouse() DB Contact No is not null::::");
				}
				if(warehouseVO.getContactNo()!=null && !warehouseVO.getContactNo().equalsIgnoreCase(null)){
					Logger.logTrace(MODULE, "inside updateWarehouse() VO Contact No is not null******");
				}
				AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareWarehouseUpdateAudit(warehouseData, warehouseVO);
				
				warehouseData.setName(warehouseVO.getName());
				warehouseData.setLocation(warehouseVO.getLocation());
				warehouseData.setDescription(warehouseVO.getDescription());
				warehouseData.setWarehouseCode(warehouseVO.getWarehouseCode());
				warehouseData.setUpdatedby(warehouseVO.getUpdatedby());
				warehouseData.setUpdatedate(getCurrentTimestamp());
				if(warehouseVO.getParentWarehouseId() != null){
					WarehouseData parentWHData = new WarehouseData();
					parentWHData.setWarehouseId(warehouseVO.getParentWarehouseId());
					warehouseData.setParentWarehouse(parentWHData);
				}if(warehouseVO.getWarehouseType() != null){
					WarehouseTypeData warehouseTypeData = new WarehouseTypeData();
					warehouseTypeData.setWarehouseTypeId(warehouseVO.getWarehouseType().getWarehouseTypeId());
					warehouseData.setWarehouseTypeData(warehouseTypeData);
				}
				warehouseData.setOwner(warehouseVO.getOwner());
				warehouseData.setContactNo(warehouseVO.getContactNo());
				warehouseData.setEmailId(warehouseVO.getEmailId());
				
				warehouseSessionBeanLocal.updateWarehouse(warehouseData);
				
				// Audit entry
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.NAME,warehouseVO.getName());
				
				addToAuditDynamicMessage(AuditConstants.UPDATE_WAREHOUSE, "Updating Warehouse",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), iblSession);
			
			}else{
				Logger.logInfo(MODULE, "getting null data in filter data ::::");
			}
			
			
			
		}catch(UpdateBLException e){
			throw e;
		}
	}
	
	
	/**
	 * Get All Warehouse Data 
	 * @author yash.kapasi
	 * @return {@link List}<{@link ComboData}> comboBoxDatas
	 */
	public List<ComboData> getAllWarehouseData(){
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside getAllWarehouseData()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				List<WarehouseData> warehouseDatas = (List<WarehouseData>)filterList;
				for(WarehouseData warehouseData : warehouseDatas){
					comboBoxDatas.add(new ComboData(warehouseData.getWarehouseId(), warehouseData.getName()));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
	}

	
	/**
	 * Delete Warehouse Data 
	 * @author yash.kapasi
	 * @param {@link WarehouseVO} warehouseVO
	 * @param {@link IBLSession} iblSession
	 * @throws UpdateBLException
	 */
	public void deleteWarehouse(WarehouseVO warehouseVO,IBLSession iblSession) throws UpdateBLException{
		
		Logger.logInfo(MODULE, "inside deleteWarehouse");
		try{
			if(warehouseVO == null){
				return;
			}
			Logger.logInfo(MODULE, "warehouseID ::"+warehouseVO.getWarehouseId());
			
			WarehouseData parentWHData = new WarehouseData();
			parentWHData.setWarehouseId(warehouseVO.getWarehouseId());
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("parentWarehouse", parentWHData);
			fieldValueMap.put("systemgenerated", "N");
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				Logger.logInfo(MODULE, "present child warehouse :"+filterList.size());
				throw new UpdateBLException("Child Warehouse Present,This warehouse can not be deleted !");
			}
			
			Long totalInventory = inventoryManagementSessionBeanLocal.getTotalInvnetoriesByWarehouse(parentWHData);
			Logger.logInfo(MODULE, "Total inventory of this warehouse  :"+totalInventory);
			
			if(totalInventory  != 0){
				throw new UpdateBLException("Invetory Present in this warehouse,This warehouse can not be deleted !");
			}
			
			//check warehouse entry in transfer Inventory
			int totalWaitingInventory = inventoryManagementSessionBeanLocal.getTotalTransferInventoryByStatus(warehouseVO.getWarehouseId());
			if(totalWaitingInventory > 0){
				throw new UpdateBLException("You can't delete this warehouse as Some of Inventory are in Transfer process !");
			}
			
			// finally update entry in database
			fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("warehouseId", warehouseVO.getWarehouseId());
			
			filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
			WarehouseData warehouseData = null;
			if(filterList != null && !filterList.isEmpty()){
				
				warehouseData = ((List<WarehouseData>)filterList).get(0);
				warehouseData.setReason(warehouseVO.getReason());
				warehouseSessionBeanLocal.deleteWarehouse(warehouseData);
			
			
			//audit entry
			Map<String,Object> mapAudit = new HashMap<String, Object>();
			mapAudit.put(AuditTagConstant.NAME,warehouseData.getName());
			addToAuditDynamicMessage(AuditConstants.DELETE_WAREHOUSE, "Deleting Warehouse",AuditConstants.DELETE_AUDIT_TYPE, mapAudit, iblSession);
			}
		}catch(UpdateBLException e){
			throw e;
		}catch(SearchBLException e1){
			throw new UpdateBLException("This warehouse can not be deleted !");
		}
		
	}
	
	
	
	public List<ComboData> getAllWarehouseDataExceptBy(Long warehouseId){
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside getAllWarehouseDataExceptBy()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				List<WarehouseData> warehouseDatas = (List<WarehouseData>)filterList;
				for(WarehouseData warehouseData : warehouseDatas){
					if(!isParentWarehouseInTrack(warehouseId, warehouseData)){
						comboBoxDatas.add(new ComboData(warehouseData.getWarehouseId(), warehouseData.getName()));
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
	}
	
	public boolean isParentWarehouseInTrack(Long warehouseId,WarehouseData warehouseData){
		boolean parentwarehouseIntrack = false;
		
		if(warehouseData.getParentWarehouse() == null){
			parentwarehouseIntrack = false;
		}else{
			if(warehouseId.equals(warehouseData.getParentWarehouse().getWarehouseId())){
				parentwarehouseIntrack = true;
			}else{
				Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
				fieldValueMap.put("systemgenerated", "N");
				fieldValueMap.put("warehouseId", warehouseData.getParentWarehouse().getWarehouseId());
				
				List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
				if(filterList != null && !filterList.isEmpty()){
					List<WarehouseData> warehouseDatas = (List<WarehouseData>)filterList;
					parentwarehouseIntrack = isParentWarehouseInTrack(warehouseId, warehouseDatas.get(0));
				}
			}
		}
		
		return parentwarehouseIntrack;
	}

	
	/**
	 * Save warehouse Type Data 
	 * @author yash.kapasi
	 * @param {@link WarehouseTypeVO} warehouseTypeVO
	 * @param {@link IBLSession} iblSession
	 * @throws CreateBLException
	 */
	@Override
	public void saveWarehouseType(WarehouseTypeVO warehouseTypeVO,IBLSession iblSession) throws CreateBLException {
		
		Logger.logTrace(MODULE, "inside saveWarehouse()");
		try {
			if(!(warehouseSessionBeanLocal.isWarehouseTypeExist(warehouseTypeVO.getName()))){
			warehouseTypeVO.setSystemgenerated("N");

			
			WarehouseTypeData warehouseTypeData = WarehouseUtil.getWarehouseTypeData(warehouseTypeVO);
			
			warehouseSessionBeanLocal.saveWarehouseType(warehouseTypeData);

			// Audit entry
			Map<String,Object> mapAudit = new HashMap<String, Object>();
			mapAudit.put(AuditTagConstant.NAME,warehouseTypeVO.getName());
			
			addToAuditDynamicMessage(AuditConstants.CREATE_WAREHOUSETYPE, "Creating WarehouseType",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, iblSession);
			}else{
				throw new CreateBLException("WarehoseType Name " + warehouseTypeVO.getName() +" Already Exists");
			}	
		}catch (CreateBLException e) {
			throw e;
		}
	}

	
	/**
	 * Search warehouse Type Data 
	 * @author yash.kapasi
	 * @param {@link WarehouseTypeVO} warehouseTypeVO
	 * @param {@link IBLSession} iblSession
	 * @return {@link List} <{@link WarehouseTypeVO}> warehouseVOs
	 */
	@Override
	public List<WarehouseTypeVO> searchWarehouseTypeData(WarehouseTypeVO warehouseTypeVO,IBLSession iblSession){
		Logger.logTrace(MODULE, "inside searchWarehouseData()");
		List<WarehouseTypeVO> warehouseVOs = new ArrayList<WarehouseTypeVO>();
		try {
			List<WarehouseTypeData> data = warehouseSessionBeanLocal.searchWarehouseTypeData(warehouseTypeVO);
			if(data != null){
				for(WarehouseTypeData dataObj : data){
					warehouseVOs.add(WarehouseUtil.getWarehouseTypeVO(dataObj));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return warehouseVOs;
	}

	
	/**
	 * View warehouse Type Data 
	 * @author yash.kapasi
	 * @param {@link WarehouseTypeVO} warehouseTypeVO
	 * @param {@link IBLSession} iblSession
	 * @return {@link WarehouseTypeVO} warehouseTypeVO
	 */
	@Override
	public WarehouseTypeVO viewWarehouseType(WarehouseTypeVO WarehouseTypeVO,IBLSession iblSession) {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside viewWarehouse()");
		WarehouseTypeVO warehouseVO2 = null;
		try {
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("warehouseTypeId", WarehouseTypeVO.getWarehouseTypeId());
			
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSETYPE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				warehouseVO2 = WarehouseUtil.getWarehouseTypeVO(((List<WarehouseTypeData>)filterList).get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return warehouseVO2;
	}
	
	/**
	 * Update warehouse Type Data 
	 * @author yash.kapasi
	 * @param {@link WarehouseTypeVO} warehouseTypeVO
	 * @param {@link IBLSession} iblSession
	 * @throws UpdateBLException
	 */
	@Override
	public void updateWarehouseType(WarehouseTypeVO warehouseVO,IBLSession iblSession) throws UpdateBLException{
		Logger.logTrace(MODULE, "inside updateWarehouse()");
		try {
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("warehouseTypeId", warehouseVO.getWarehouseTypeId());
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSETYPE_DATA, fieldValueMap);
			
			WarehouseTypeData warehouseTypeData = null;
			if(filterList != null && !filterList.isEmpty()){
				
				Logger.logInfo(MODULE, "getting data in filter data ::::");
				warehouseTypeData = ((List<WarehouseTypeData>)filterList).get(0);
				if(!warehouseTypeData.getName().equalsIgnoreCase(warehouseVO.getName())){
					if(!(warehouseSessionBeanLocal.isWarehouseTypeExist(warehouseVO.getName()))){
					}else{
						throw new UpdateBLException("WarehouseType Name " + warehouseVO.getName() +" Already Exists");
					}
					
				}
				AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareWarehouseTypeUpdateAudit(warehouseTypeData, warehouseVO);
				
				warehouseTypeData.setName(warehouseVO.getName());
				warehouseTypeData.setDescription(warehouseVO.getDescription());
				warehouseTypeData.setUpdatedby(warehouseVO.getUpdatedby());
				warehouseTypeData.setUpdatedate(getCurrentTimestamp());
				
				warehouseSessionBeanLocal.updateWarehouseType(warehouseTypeData);
				
				// Audit entry
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.NAME,warehouseVO.getName());
				
				addToAuditDynamicMessage(AuditConstants.UPDATE_WAREHOUSETYPE, "Updating WarehouseType",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), iblSession);
			
			}else{
				Logger.logInfo(MODULE, "getting null data in filter data ::::");
			}
			
		}catch(UpdateBLException e){
			throw e;
		}
	}

	
	/**
	 * Get All warehouse Type Data 
	 * @author yash.kapasi
	 * @return {@link List} <{@link ComboData}> comboBoxDatas
	 */
	public List<ComboData> getAllWarehouseTypeData(){
		List<ComboData> comboBoxDatas = new ArrayList<ComboData>();
		Logger.logTrace(MODULE, "inside getAllWarehouseTypeData()");
		try{
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSETYPE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				List<WarehouseTypeData> warehouseDatas = (List<WarehouseTypeData>)filterList;
				for(WarehouseTypeData warehouseData : warehouseDatas){
					comboBoxDatas.add(new ComboData(warehouseData.getWarehouseTypeId(), warehouseData.getName()));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comboBoxDatas;
	}
	
	public Map<ComboData, List<ComboData>> getAllResourceTypeWithResource (Long warehouseid){
		Map <ComboData, List<ComboData>> map=new HashMap<ComboData, List<ComboData>>();
		List<ResourceTypeData> resourceTypeDatas=null;
		Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
		Logger.logTrace(MODULE, "inside getAllResourceTypeWithResource()");
		try{
			if(warehouseid!=null){
				Logger.logTrace(MODULE, "inside getAllResourceTypeWithResource() warehouseid not null");
				CachedRowSetImpl cachedRowSetImpl=warehouseSessionBeanLocal. getAllResourceTypeWithResource (warehouseid);
				if(cachedRowSetImpl!=null){
				resourceTypeDatas= WarehouseUtil.getResourceTypeDatas(cachedRowSetImpl);
				Logger.logTrace(MODULE, "inside getAllResourceTypeWithResource() resourceTypeDatas:"+resourceTypeDatas);
				Logger.logTrace(MODULE, "inside getAllResourceTypeWithResource() resourceTypeDatas Size:"+resourceTypeDatas.size());
				}else{
					 resourceTypeDatas=new ArrayList<ResourceTypeData>();
				}
			}else{
			//Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
				Logger.logTrace(MODULE, "inside getAllResourceTypeWithResource() warehouseid  null");
			fieldValueMap.put("systemgenerated", "N");
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCETYPE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				 resourceTypeDatas = (List<ResourceTypeData>)filterList;
			}else{
				 resourceTypeDatas=new ArrayList<ResourceTypeData>();
				}
			}
			if(resourceTypeDatas!=null){
				for(ResourceTypeData resourceTypeData : resourceTypeDatas){
					List<ComboData> resoucecomboBoxDatas = new ArrayList<ComboData>();
					Logger.logTrace(MODULE, "inside getAllResourceTypeWithResource() ResourceTypeID:"+resourceTypeData.getResourceTypeId());
					fieldValueMap.put("systemgenerated", "N");
					fieldValueMap.put("RESOURCETYPEID", resourceTypeData.getResourceTypeId());
					List resourceSubTypeList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCESUBTYPE_DATA, fieldValueMap);
					if(resourceSubTypeList != null && !resourceSubTypeList.isEmpty()){
						
						List<ResourceSubTypeData> itemDatas = (List<ResourceSubTypeData>)resourceSubTypeList;
					
						for(ResourceSubTypeData itemData : itemDatas){
							Logger.logTrace(MODULE, "inside getAllResourceTypeWithResource() ResourceSubtypeID:"+itemData.getResourceSubTypeId()+"ResourceSubtypeName:"+itemData.getName());
							resoucecomboBoxDatas.add(new ComboData(itemData.getResourceSubTypeId(), itemData.getName()));
						}
					}
					//resourceList.clear();
					map.put(new ComboData(resourceTypeData.getResourceTypeId(), resourceTypeData.getName()), resoucecomboBoxDatas);
					//resoucecomboBoxDatas.clear();
					Logger.logTrace(MODULE, "--------------------------------------------------------------------------------------");
				}
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	
	
	/**
	 * Save Warehouse Threshold 
	 * @author yash.kapasi
	 * @param {@link List} <{@link ConfigureThresholdVO}> configureThresholdVOs
	 * @param {@link IBLSession}  iblSession
	 * @throws CreateBLException
	 */
	@Override
	public void saveThreshold(List<ConfigureThresholdVO> configureThresholdVOs,IBLSession iblSession) throws CreateBLException {
		
		Logger.logTrace(MODULE, "inside saveThreshold() in Facade");
		Logger.logTrace(MODULE, "inside saveThreshold() in Facade List of VOs:"+configureThresholdVOs);
		try {
			
			List<ConfigureThresholdData> configureThresholdDatas = WarehouseUtil.getThresholdData(configureThresholdVOs);
			Logger.logTrace(MODULE, "inside saveThreshold() after Util method getThresholdData() in Facade:"+configureThresholdDatas);
			warehouseSessionBeanLocal.saveThreshold(configureThresholdDatas,iblSession);
			Logger.logTrace(MODULE, "inside saveThreshold() after Session Bean's method getThresholdData() in Facade");
			// Audit entry
//			Map<String,Object> mapAudit = new HashMap<String, Object>();
//			mapAudit.put(AuditTagConstant.NAME,configureThresholdVO.getName());
//			
//			addToAuditDynamicMessage(AuditConstants.CREATE_WAREHOUSE, "Creating Warehouse",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, iblSession);

		}catch (CreateBLException e) {
			throw e;
		}
	}
	
	
	/**
	 * Search Warehouse Threshold Data
	 * @author yash.kapasi
	 * @param {@link ConfigureThresholdVO} configureThresholdVOs
	 * @param {@link IBLSession}  iblSession
	 * @return {@link List}<{@link ConfigureThresholdVO}> configureThresholdVOs
	 */
	@Override
	public List<ConfigureThresholdVO> searchThresholdData(ConfigureThresholdVO configureThresholdVO,IBLSession iblSession){
		Logger.logTrace(MODULE, "inside searchWarehouseData()");
		List<ConfigureThresholdVO> configureThresholdVOs = new ArrayList<ConfigureThresholdVO>();
		try {
			List<ConfigureThresholdData> data = warehouseSessionBeanLocal.searchThresholdData(configureThresholdVO);
			if(data != null){
				for(ConfigureThresholdData dataObj : data){
					configureThresholdVOs.add(WarehouseUtil.getConfigureThresholdVO(dataObj));
				}
			}
	
		}catch(Exception e){
			e.printStackTrace();
		}
		return configureThresholdVOs;
	}
	
	public void searchThresholdStatus(IBLSession iblSession){
		Logger.logTrace(MODULE, "inside searchThresholdStatus()");
	
		Map <String,ThresholdStatusVO> map=new LinkedHashMap<String, ThresholdStatusVO>();
		try {
			Logger.logTrace(MODULE, "inside searchThresholdStatus() before sessionBean method");
			CachedRowSetImpl cachedRowSetImpl = warehouseSessionBeanLocal.searchThresholdStatus();
			Logger.logTrace(MODULE, "inside searchThresholdStatus() After sessionBean method");
			if(cachedRowSetImpl != null){
				Logger.logTrace(MODULE, "searchThresholdStatus() cachedRowSetImpl not null:");
				WarehouseUtil.processThresholdNotification(cachedRowSetImpl);
				Logger.logTrace(MODULE, "inside searchThresholdStatus() After WarehouseUtil processThresholdNotification method call");
			}
	
		}catch(Exception e){
			e.printStackTrace();
		}
		//return threhStatusVOs;
	}

	
	/**
	 * Search All Warehouse Data
	 * @author yash.kapasi
	 * @return {@link List}<{@link WarehouseVO}> warehouseVOs
	 * @throws SearchBLException
	 */
	@Override
	public List<WarehouseVO> getAllWareHouseData() throws SearchBLException {
		
		Logger.logTrace(MODULE, "inside getAllWareHouseData()");
		
		List<WarehouseVO> warehouseVOs = new ArrayList<WarehouseVO>();
		try {
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
			
			if(filterList != null && !filterList.isEmpty()){
				
				List<WarehouseData> warehouseDatas = (List<WarehouseData>)filterList;
				for(WarehouseData warehouseData : warehouseDatas){
					warehouseVOs.add(WarehouseUtil.getWarehouseVO(warehouseData));
				}
			}
	
		}catch(Exception e){
			e.printStackTrace();
		}
		return warehouseVOs;
		
	}

	
	/**
	 * Search Resources with WarehouseId
	 * @author yash.kapasi
	 * @param Long wareHouseId
	 * @return {@link List}<{@link ItemVO}> itemVOs
	 * @throws SearchBLException
	 */
	@Override
	public List<ItemVO> getResourcesWithWareHouseId(Long wareHouseId)
			throws SearchBLException {
		
		Logger.logTrace(MODULE, "inside getResourcesWithWareHouseId()");
		
		List<ItemVO> itemVOs = new ArrayList<ItemVO>();
		try {
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("warehouseId", wareHouseId);
			
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
			
			if(filterList != null && !filterList.isEmpty()){
				WarehouseData warehouseData = (WarehouseData) filterList.get(0);
				if(warehouseData!=null ){
					if(warehouseData.getInventoryDatas()!=null && !warehouseData.getInventoryDatas().isEmpty()) {
						for(InventoryData inventoryData : warehouseData.getInventoryDatas()) {
							if(inventoryData.getItemData()!=null) {
								itemVOs.add(ItemUtil.getitemVo(inventoryData.getItemData()));
							}
						}
					}
				} 
				
			}
	
		}catch(Exception e){
			e.printStackTrace();
		}
		return itemVOs;
		
	}
	
	
	/**
	 * calculate Threshold Value
	 * @author yash.kapasi
	 * @param Long wareHouseId
	 * @param Long resourceId
	 * @param Long resourceTypeId
	 * @param Long resourceSubTypeId
	 * @return {@link NotificationData} notificationData
	 * @throws Exception
	 */
	@Override
	public NotificationData calculateThreasholdValue(Long wareHouseId,Long resourceId,
			Long resourceTypeId, Long resourceSubTypeId) throws Exception {
		
		try {
			
			NotificationData notificationData = null;
			
			ConfigureThresholdData thresholdData = warehouseSessionBeanLocal.findThresholdValue(wareHouseId,resourceTypeId,resourceSubTypeId);
			if(thresholdData!=null) {
				Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
				fieldValueMap.put("systemgenerated", "N");
				fieldValueMap.put("itemId", resourceId);
				List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.ITEM_DATA, fieldValueMap);
				if(filterList!=null && !filterList.isEmpty()) {
					ItemData itemData = (ItemData) filterList.get(0);
					if(itemData!=null && itemData.getInventoryDatas()!=null && !itemData.getInventoryDatas().isEmpty()) {
						int total = itemData.getInventoryDatas().size();
						int available = 0;
						 SystemParameter thresholdCountBase = systemParameterSessionBeanLocal.getSystemParameter(SystemParameterConstants.THRESHOLD_COUNT_BSAE);
						 
						 String valueSource[]  = thresholdCountBase.getValueSource().split(",");
						 Map<String, String> map = SystemInternalDataConversionUtil.convertStringArrayToMap(valueSource);
						 
						List<String> list = SystemInternalDataConversionUtil.convertCommaSeparatedStringToList(thresholdCountBase.getValue());
						
						for(InventoryData inventoryData : itemData.getInventoryDatas()) {
							
							for(String sysParam : list) {
								if(map.containsKey(sysParam)) {
									Long id = Long.parseLong(map.get(sysParam));
									if(inventoryData.getInventoryStatusId()==id) {
										available++;
									}
								}
							}
							
							/*if(list.contains("Available")){
								Logger.logTrace(MODULE, "ThresholdBase is Available");
							if(inventoryData.getInventoryStatusId()==2L) {
								available++;
								}
							}
							
							if(inventoryData.getInventoryStatusId()==2L || ((inventoryData.getInventoryStatusId()==8L) && (inventoryData.getRefurbished()!=null) && (inventoryData.getRefurbished().equals("Y")))) {
								Logger.logTrace(MODULE, "ThresholdBase is Not Available");
							//	Logger.logTrace(MODULE, "InventoryData:::"+inventoryData);
									available++;
							}*/
							
							
						}
						boolean result =  WarehouseUtil.isThreshold(thresholdData, total, available);
						
						String commonEmail = null;
						List<WarehouseVO> wareHouseData = getAllWareHouseData();
						if(wareHouseData!=null && !wareHouseData.isEmpty()) {
							for(WarehouseVO warehouseVO : wareHouseData) {
								if(warehouseVO.getAlias().equals("CENTRAL")) {
									commonEmail = warehouseVO.getEmailId();
								}
							}
						}
						if(result) {
							Logger.logTrace(MODULE, "FCK :: "+" Adding Threashold"+thresholdData.getWarehousedata()+ " Available :: "+available+" Total : "+total);
							 
							 
							 String orderNo = "";
							//Place Order
								try {
									SystemParameter thresholdAutomaticReorder = systemParameterSessionBeanLocal.getSystemParameter(SystemParameterConstants.THRESHOLD_AUTOMATIC_RE_ORDER);
									if(thresholdAutomaticReorder!=null) {
										if(thresholdAutomaticReorder.getValue().equals("Yes")) {
											
											Integer quantity = 0;
											if(thresholdData.getQuantity()!=null) {
												quantity = thresholdData.getQuantity().intValue();
											} else {
												SystemParameter thresholdReorderQuantity = systemParameterSessionBeanLocal.getSystemParameter(SystemParameterConstants.THRESHOLD_RE_ORDER_QUANTITY);
												if(thresholdReorderQuantity!=null) {
													 quantity = Integer.parseInt(thresholdReorderQuantity.getValue());
												}
											}
											
											PlaceOrderVO orderVO = new PlaceOrderVO();
											orderVO.setQuantity(quantity.longValue());
											orderVO.setFromwarehouseId(thresholdData.getWarehouseId());
											if(thresholdData.getWarehousedata().getParentWarehouse()!=null) {
												orderVO.setTowarehouseId(thresholdData.getWarehousedata().getParentWarehouse().getWarehouseId());
											}
											orderVO.setCreateDate(getCurrentTimestamp());
											orderVO.setResourceTypeId(thresholdData.getResourceTypeId());
											orderVO.setResourceSubTypeId(thresholdData.getResourceSubTypeId());
											orderVO.setRemark("Automatic Place order by Threshold Agent");
											orderVO.setCreatedby(UserConstants.ADMIN_USERID);
											
											IBDSessionContext sessionContext = null;
											Map<String, UserVO> mapUser = UserFactory.findAllUser();
											
											if(mapUser!=null && mapUser.containsKey(UserConstants.ADMIN_USERID)) {
												UserVO admin = mapUser.get(UserConstants.ADMIN_USERID);
												
												try {
													sessionContext = userFacadeLocal.doLogin(admin.getUsername(), admin.getPassword(), "127.0.0.1");
												} catch (Exception e) {
													e.printStackTrace();
													sessionContext = userFacadeLocal.doLogin("agent", "agent", "127.0.0.1");
												}
											} else {
												 sessionContext = userFacadeLocal.doLogin("agent", "agent", "127.0.0.1");
											}
											
											 orderNo = inventoryManagementFacadeLocal.placeOrder(orderVO, sessionContext.getBLSession());
											
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							 
								notificationData = WarehouseUtil.convertThresholdVO(thresholdData,itemData.getName(),available,commonEmail,orderNo);
								
						} else {
							Logger.logTrace(MODULE, "FCK :: "+" Not Threashold"+thresholdData.getWarehousedata()+ " Available :: "+available+" Total : "+total);
						}
						
						
						
//						emailVO.setThreshold(result);
//						emailVO.setResourceName(itemData.getName());
						
						return notificationData;
					}
				}
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	/**
	 * Get All ResourceType With WareHouseId
	 * @author yash.kapasi
	 * @param Long wareHouseId
	 * @return {@link List}  <{@link ComboData}> comboDatas
	 * @throws SearchBLException
	 */
	@Override
	public List<ComboData> getAllResourceTypeWithWareHouseId(Long wareHouseId)
			throws SearchBLException {
		
		List<ComboData> comboDatas = new ArrayList<ComboData>();
		Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
		fieldValueMap.put("warehouseId", wareHouseId);
		
		List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
		if(filterList != null && !filterList.isEmpty()){
			Map<Long, ItemData> map = new HashMap<Long, ItemData>();
			List<InventoryData> inventoryDatas = filterList;
			for(InventoryData inventoryData : inventoryDatas) {
				if(!map.containsKey(inventoryData.getItemData().getResourceTypeId())) {
					map.put(inventoryData.getItemData().getResourceTypeId(),inventoryData.getItemData());
				}
			}
			for(Entry<Long, ItemData> entry : map.entrySet()) {
				comboDatas.add(new ComboData(entry.getValue().getResourceType().getResourceTypeId(), entry.getValue().getResourceType().getName()));
			}
		}
		
		return comboDatas;
		
	}
	
	
	/**
	 * Search Warehouse Summary Data
	 * @author yash.kapasi
	 * @param Long wareHouseId
	 * @return {@link List}  <{@link WareHouseSummaryVO}> wareHouseSummaryVOs
	 * @throws SearchBLException
	 */
	@Override
	public List<WareHouseSummaryVO> searchWarehouseSummaryData(Long wareHouseId)
			throws SearchBLException {
		
		List<WareHouseSummaryVO> wareHouseSummaryVOs = new ArrayList<WareHouseSummaryVO>();
		
		Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
		fieldValueMap.put("warehouseId", wareHouseId);
		
		List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
		if(filterList != null && !filterList.isEmpty()){
			List<InventoryData> inventoryDatas = filterList;
			if(inventoryDatas!=null && !inventoryDatas.isEmpty()) {
				wareHouseSummaryVOs = WarehouseUtil.convertToWareHouseSummaryVO(inventoryDatas);
			}
		}
		
		return wareHouseSummaryVOs;
	}

	
	/**
	 * Create Warehouse Tree
	 * @author yash.kapasi
	 * @return {@link List}  <{@link CreateWareHouseTreeVO}> wareHouseTreeVOs
	 * @throws SearchBLException
	 */
	@Override
	public List<CreateWareHouseTreeVO> createWareHouseTree() throws SearchBLException {
		
		List<CreateWareHouseTreeVO> wareHouseTreeVOs = new ArrayList<CreateWareHouseTreeVO>();
		List<WarehouseData>  warehouseDatas =  warehouseSessionBeanLocal.findAllCentralWareHouses();
		if(warehouseDatas!=null && !warehouseDatas.isEmpty()) {
			for(WarehouseData warehouseData : warehouseDatas) {
				CreateWareHouseTreeVO treeVO = new CreateWareHouseTreeVO();
				treeVO.setWareHouseName(warehouseData.getName());
				treeVO.setWarehouseId(warehouseData.getWarehouseId());
				treeVO.setWareHouseTypeName(warehouseData.getWarehouseTypeData().getName());
				List<CreateWareHouseTreeVO> childWareHouses = findChildWareHouses(warehouseData);
				treeVO.setChildWareHouses(childWareHouses);
				wareHouseTreeVOs.add(treeVO);
			}
		}		
		
		return wareHouseTreeVOs;
	}

	
	/**
	 * Finds Child Warehouse tree
	 * @author yash.kapasi
	 * @param {@link WarehouseData} warehouseData
	 * @return {@link List}  <{@link CreateWareHouseTreeVO}> wareHouseTreeVOs
	 */
	private List<CreateWareHouseTreeVO> findChildWareHouses(WarehouseData warehouseData) {
		
		List<CreateWareHouseTreeVO> wareHouseTreeVOs = new ArrayList<CreateWareHouseTreeVO>();
		
		if(warehouseData!=null) {
			List<WarehouseData> warehouseDatas;
			try {
				warehouseDatas = warehouseSessionBeanLocal.findChildWareHouses(warehouseData.getWarehouseId());
				if(warehouseDatas!=null && !warehouseDatas.isEmpty()) {
					for(WarehouseData data : warehouseDatas) {
						CreateWareHouseTreeVO treeVO = new CreateWareHouseTreeVO();
						treeVO.setWareHouseName(data.getName());
						treeVO.setWarehouseId(data.getWarehouseId());
						treeVO.setWareHouseTypeName(data.getWarehouseTypeData().getName());
						List<CreateWareHouseTreeVO> list = findChildWareHouses(data);
						treeVO.setChildWareHouses(list);
						wareHouseTreeVOs.add(treeVO);
					}
				}
			} catch (SearchBLException e) {
				e.printStackTrace();
			}
		}
		
		return wareHouseTreeVOs;
	}

	
	
	/**
	 * Finds Child Warehouses by parent warehouse Id
	 * @author yash.kapasi
	 * @param {@link Long} wareHouseId
	 * @return {@link CreateWareHouseTreeVO}  wareHouseTreeVO
	 * @throws SearchBLException
	 */
	@Override
	public CreateWareHouseTreeVO findChildWareHouses(Long wareHouseId) throws SearchBLException {
		 
		CreateWareHouseTreeVO  wareHouseTreeVO = null;
		 
		Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
		fieldValueMap.put("systemgenerated", "N");
		fieldValueMap.put("warehouseId", wareHouseId);
		
		List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
		
		if(filterList != null && !filterList.isEmpty()){
			WarehouseData warehouseData = (WarehouseData) filterList.get(0);
			wareHouseTreeVO = new CreateWareHouseTreeVO();
			wareHouseTreeVO.setWarehouseId(warehouseData.getWarehouseId());
			wareHouseTreeVO.setWareHouseName(warehouseData.getName());
			wareHouseTreeVO.setWareHouseTypeName(warehouseData.getWarehouseTypeData().getName());
			List<CreateWareHouseTreeVO> wareHouseTreeVOs = findChildWareHouses(warehouseData);
			wareHouseTreeVO.setChildWareHouses(wareHouseTreeVOs);
		}
		
		return wareHouseTreeVO;
	}


	@Override
	public void deleteThreshold(List<ConfigureThresholdVO> configureThresholdVOs,IBLSession blSession) throws UpdateBLException {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside deleteThreshold() in Facade");
		Logger.logTrace(MODULE, "inside deleteThreshold() in Facade List of VOs:"+configureThresholdVOs);
		try{
			if(configureThresholdVOs != null && !configureThresholdVOs.isEmpty()){
				for(ConfigureThresholdVO configureThresholdVO:configureThresholdVOs){
					if(configureThresholdVO.getThresholdID() != null){
						System.out.println("Threshold idddddddd:::"+configureThresholdVO.getThresholdID());
						ConfigureThresholdData  configureThresholdData = warehouseSessionBeanLocal.findConfigureThresholdDataByThresholdID(configureThresholdVO.getThresholdID());
						warehouseSessionBeanLocal.deleteThreshold(configureThresholdData,blSession);
					}
				}
			}
			/*List<ConfigureThresholdData> configureThresholdDatas = WarehouseUtil.getThresholdData(configureThresholdVOs);
			Logger.logTrace(MODULE, "inside deleteThreshold() after Util method getThresholdData() in Facade:"+configureThresholdDatas);
			for(ConfigureThresholdData thresholdData:configureThresholdDatas){
				Logger.logTrace(MODULE, "inside deleteThreshold() in Facade with thresholdData not null::"+thresholdData);
			}*/
		}catch (SearchBLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (UpdateBLException e) {
			throw e;
			// TODO: handle exception
		} 
		
	}
	
}
