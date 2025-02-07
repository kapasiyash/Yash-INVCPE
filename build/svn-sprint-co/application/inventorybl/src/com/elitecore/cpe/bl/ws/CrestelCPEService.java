package com.elitecore.cpe.bl.ws;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.elitecore.cpe.bl.constants.inventorymgt.InventoryStatusConstants;
import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.user.UserConstants;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceTypeData;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.facade.master.attribute.AttributeFacadeLocal;
import com.elitecore.cpe.bl.facade.system.user.UserFacadeLocal;
import com.elitecore.cpe.bl.facade.ws.WSFacadeLocal;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.session.inventorymgt.InventoryManagementSessionBeanLocal;
import com.elitecore.cpe.bl.session.master.attribute.AttributeSessionBeanLocal;
import com.elitecore.cpe.bl.session.master.item.ItemSessionBeanLocal;
import com.elitecore.cpe.bl.session.master.warehouse.WarehouseSessionBeanLocal;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.bl.vo.system.user.UserVO;
import com.elitecore.cpe.bl.ws.data.input.request.BookCPERequestData;
import com.elitecore.cpe.bl.ws.data.input.request.MarkCPEAsFaultyRequestVO;
import com.elitecore.cpe.bl.ws.data.input.request.ReleaseCPERequestVO;
import com.elitecore.cpe.bl.ws.data.input.request.ResourceAvailibilityRequestData;
import com.elitecore.cpe.bl.ws.data.input.request.ResourceRequestData;
import com.elitecore.cpe.bl.ws.data.input.response.BookCPEResponseData;
import com.elitecore.cpe.bl.ws.data.input.response.CPEResponseVO;
import com.elitecore.cpe.bl.ws.data.input.response.ChangeInventoryStatusResponseData;
import com.elitecore.cpe.bl.ws.data.input.response.InventoryStatusResponseVO;
import com.elitecore.cpe.bl.ws.data.input.response.InventoryStatusVO;
import com.elitecore.cpe.bl.ws.data.input.response.ReleaseCPEResponseVO;
import com.elitecore.cpe.bl.ws.data.input.response.ResourceAttributeResponseData;
import com.elitecore.cpe.bl.ws.data.input.response.ResourceAvailibilityResponseData;
import com.elitecore.cpe.bl.ws.data.input.vo.CPEInventoryVO;
import com.elitecore.cpe.bl.ws.data.input.vo.InventoryRequestVO;
import com.elitecore.cpe.bl.ws.data.input.vo.ReserveAllocateRequestVO;
import com.elitecore.cpe.bl.ws.data.input.vo.ResourceAvailibilityVO;
import com.elitecore.cpe.bl.ws.data.util.InventoryMgtResponseCode;
import com.elitecore.cpe.bl.ws.data.util.WsUtil;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.core.base.BaseWebServiceBL;
import com.elitecore.cpe.util.logger.Logger;

/**
 * Crestel CPE Web Service
 * @author yash.kapasi
 *
 */
/**
 * @author Yash.Kapasi
 *
 */
@WebService
@Stateless
public class CrestelCPEService extends BaseWebServiceBL {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String MODULE = "CRESTEL_INVENTORY_WS";


	@EJB private WarehouseSessionBeanLocal warehouseSessionBeanLocal;
	@EJB private ItemSessionBeanLocal itemSessionBeanLocal;
	@EJB private AttributeSessionBeanLocal attributeSessionBeanLocal;
	@EJB private InventoryManagementSessionBeanLocal inventoryManagementSessionBeanLocal;
	
//	@EJB private InventoryManagementFacadeLocal inventoryManagementFacadeLocal;
	@EJB private WSFacadeLocal wsFacadeLocal;
	@EJB private AttributeFacadeLocal attributeFacadeLocal;
	@EJB private UserFacadeLocal userFacadeLocal;
	
	
	
	/**
	 * CheckCPEResource Webservice API    
	 * @author yash.kapasi
	 * @param {@link ResourceAvailibilityRequestData} requestData
	 * @return {@link ResourceAvailibilityResponseData} responseData
	 */
	@WebMethod
	public ResourceAvailibilityResponseData checkCPEResource(ResourceAvailibilityRequestData requestData){
		Long startTime = System.currentTimeMillis();
		ResourceAvailibilityResponseData responseData = new ResourceAvailibilityResponseData();
		try {
			Logger.logInfo(MODULE, "In checkCPEResource method called...");
			
			if(requestData==null || requestData.getResourceId() == null ||  requestData.getResourceId().isEmpty()){
				throw new SearchBLException(InventoryMgtResponseCode.RESOURCE_NOT_FOUND);
			}
			if(requestData.getResourceId() != null){
				Map<String,Object> fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("resourceNumber", requestData.getResourceId());
				List filterList = itemSessionBeanLocal.getFilterDataBy(EntityConstants.ITEM_DATA, fieldValueMap);
				if(filterList == null || filterList.isEmpty()){
					throw new SearchBLException(InventoryMgtResponseCode.INVALIAD_RESOURCE);
				}
			}
			if(requestData.getWarehouseName() != null && !requestData.getWarehouseName().isEmpty() ){
				Map<String,Object> fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("name", requestData.getWarehouseName());
				if(requestData.getWarehouseCode()!=null && !requestData.getWarehouseCode().isEmpty()) {
					fieldValueMap.put("warehouseCode",requestData.getWarehouseCode());
					List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
					if(filterList == null || filterList.isEmpty()){
						throw new SearchBLException(InventoryMgtResponseCode.WAREHOUSE_CODE_AND_NAME_MISMATCH);
					}
				}
				List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
				if(filterList == null || filterList.isEmpty()){
					throw new SearchBLException(InventoryMgtResponseCode.INVALIAD_WAREHOUSE);
				}
				
			}
			if(requestData.getWarehouseCode()!=null && !requestData.getWarehouseCode().isEmpty()) {
				Map<String,Object> fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("warehouseCode", requestData.getWarehouseCode());
				List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
				if(filterList == null || filterList.isEmpty()){
					throw new SearchBLException(InventoryMgtResponseCode.INVALIAD_WAREHOUSECODE);
				}
			}
			
			responseData = wsFacadeLocal.checkCPEResource(requestData);
			
			/*Map<String,Object> fieldValueMap = new HashMap<String, Object>();
			List filterList = null;
			List<WarehouseData> warehouseDatas = new ArrayList<WarehouseData>();
			if(requestData.getWarehouseName() != null){
				fieldValueMap.put("name", requestData.getWarehouseName());
				filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
				if(filterList != null && !filterList.isEmpty()){
					WarehouseData warehouseData = (WarehouseData)filterList.get(0);
					warehouseDatas.add(warehouseData);
				}	
			}
				
				fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("name", requestData.getResourceType());
				filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCETYPE_DATA, fieldValueMap);
				if(filterList != null && !filterList.isEmpty()){
				
					ResourceTypeData resourceTypeData = (ResourceTypeData)filterList.get(0);
					
					fieldValueMap = new HashMap<String, Object>();
					fieldValueMap.put("resoureceType", resourceTypeData);
					if(requestData.getResourceName() != null && !requestData.getResourceName().equals("")){
						fieldValueMap.put("name", requestData.getResourceName());
					}if(requestData.getModel() != null && !requestData.getModel().equals("")){
						fieldValueMap.put("modelnumber", requestData.getModel());
					}if( requestData.getVendor() != null && !requestData.getVendor().equals("")){
						fieldValueMap.put("vendor", requestData.getVendor());
					}if(requestData.getResourceId() != null && !requestData.getResourceId().equals("")){
						fieldValueMap.put("referenceID", requestData.getVendor());
					}
					
					filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.ITEM_DATA, fieldValueMap);
					if(filterList != null && !filterList.isEmpty()){
						
						List<ResourceAvailibilityVO> resourceAvailibilityVOs = new ArrayList<ResourceAvailibilityVO>();
						for(Object obj : filterList){
						
							ItemData itemData = (ItemData)obj;
							
							if(requestData.getWarehouseName() == null){
								fieldValueMap = new HashMap<String, Object>();
								fieldValueMap.put("resource", itemData);
								List filterList1 =  inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSEINVENTORYSTATUS_DATA, fieldValueMap);
								if(filterList1 != null && !filterList1.isEmpty()){
									for(Object obj1  : filterList1){
										WarehouseInventoryStatusData warehouseInventoryStatusData = (WarehouseInventoryStatusData)obj1;
										resourceAvailibilityVOs.add(getAvailableStock(warehouseInventoryStatusData.getWarehouseData(), itemData));
									}
								}
							}else{
								if(!warehouseDatas.isEmpty()){
									resourceAvailibilityVOs.add(getAvailableStock(warehouseDatas.get(0), itemData));
								}else{
									throw new SearchBLException(InventoryMgtResponseCode.SUCCESS_RESPONSE_CODE,"No Records Found");
								}
							}
							SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO = new SearchWarehouseInventoryStatusVO(); 
//							searchWarehouseInventoryStatusVO.setCurrentWarehouseId(warehouseData.getWarehouseId());
							searchWarehouseInventoryStatusVO.setResourceId(itemData.getItemId());
							
							Long avaialbleQty = inventoryManagementFacadeLocal.getAvailableStock(searchWarehouseInventoryStatusVO);
							
							Logger.logInfo(MODULE, "availibility :: "+avaialbleQty);
	
							ResourceAvailibilityVO resourceAvailibilityVO = new ResourceAvailibilityVO();
							resourceAvailibilityVO.setAvailableResourceCount(avaialbleQty);
							resourceAvailibilityVO.setModel(itemData.getModelnumber());
							resourceAvailibilityVO.setResourceName(itemData.getName());
							resourceAvailibilityVO.setResourceType(itemData.getResourceType().getName());
							resourceAvailibilityVO.setVendor(itemData.getVendor());
//							resourceAvailibilityVO.setWarehouseName(warehouseData.getName());
							resourceAvailibilityVOs.add(resourceAvailibilityVO);
						}
						responseData.setResourceAvailibilityVOs(resourceAvailibilityVOs);
					}else{
						throw new SearchBLException(InventoryMgtResponseCode.SUCCESS_RESPONSE_CODE,"No Records Found");
					}
				}else{
					throw new SearchBLException(InventoryMgtResponseCode.SUCCESS_RESPONSE_CODE,"No Records Found");
				}
			
			
			responseData.setResponseCode(0L);
			responseData.setResponseMessage("Success");*/
			
		}catch(SearchBLException ex){
			responseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(ex.getErrorCode()));
			if(ex.getErrorMessage() == null){
				responseData.setResponseMessage(InventoryMgtResponseCode.responseCodeToMessage(ex.getErrorCode()));
			}else{
				responseData.setResponseMessage(ex.getErrorMessage());
			}
		}catch (Exception e) {
			e.printStackTrace();
			responseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(-1));
			responseData.setResponseMessage("Some Internal error occurred, contact your system administrator.");
		}
		
		
		webServiceAuditLog(requestData, responseData, responseData.getResponseCode(), responseData.getResponseMessage(), "checkCPEResource");
		
		Long endTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "time taken checkCPEResource : "+(endTime - startTime));
		return responseData;
	}
	
//	@WebMethod
	public ResourceAttributeResponseData getAttributes(ResourceRequestData resourceRequestData){
		Long startTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "getAttributes method called");
		ResourceAttributeResponseData responseData = new ResourceAttributeResponseData();
		try {
			if(resourceRequestData.getModel() == null || resourceRequestData.getModel().equals("") ||
					resourceRequestData.getResourceName() == null || resourceRequestData.getResourceName().equals("") ||
					resourceRequestData.getResourceType() == null || resourceRequestData.getResourceType().equals("")){
				throw new SearchBLException("Invalid Relation Name");
			}
			
			Map<String, Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("name", resourceRequestData.getResourceType());
			List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.RESOURCETYPE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				ResourceTypeData resourceTypeData = (ResourceTypeData)filterList.get(0);
				
			
			}
			List<AttributeVO> listattributeVO = attributeFacadeLocal.getAttributesByRef("s");
			List<com.elitecore.cpe.bl.ws.data.input.vo.AttributeVO> attributeVOs = new ArrayList<com.elitecore.cpe.bl.ws.data.input.vo.AttributeVO>();
			
			for(AttributeVO attributeVO : listattributeVO){
				
				com.elitecore.cpe.bl.ws.data.input.vo.AttributeVO newAttributeVO = new com.elitecore.cpe.bl.ws.data.input.vo.AttributeVO();
				newAttributeVO.setAttributeId(attributeVO.getAttributeId());
				newAttributeVO.setAttributeName(attributeVO.getName());
				newAttributeVO.setMandatory(String.valueOf(attributeVO.getMandatory()));
				newAttributeVO.setUnique(String.valueOf(attributeVO.getUnique()));
				
				attributeVOs.add(newAttributeVO);
			}
			responseData.setListAttributes(attributeVOs);
			
			responseData.setResponseCode(0L);
			responseData.setResponseMessage("Success");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			responseData.setResponseCode(-1L);
			responseData.setResponseMessage("Fail on getAttributes");
		}
		
		Long endTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "Time taken in getAttributes :"+(endTime-startTime));
		return responseData;
	}
	
	/*private ResourceAvailibilityVO getAvailableStock(WarehouseData warehouseData,ItemData itemData){
		
		
		SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO = new SearchWarehouseInventoryStatusVO(); 
		searchWarehouseInventoryStatusVO.setCurrentWarehouseId(warehouseData.getWarehouseId());
		searchWarehouseInventoryStatusVO.setResourceId(itemData.getItemId());
		
		Long avaialbleQty = inventoryManagementFacadeLocal.getAvailableStock(searchWarehouseInventoryStatusVO);
		
		Logger.logInfo(MODULE, "availibility :: "+avaialbleQty);

		ResourceAvailibilityVO resourceAvailibilityVO = new ResourceAvailibilityVO();
		resourceAvailibilityVO.setAvailableResourceCount(avaialbleQty);
		resourceAvailibilityVO.setModel(itemData.getModelnumber());
		resourceAvailibilityVO.setResourceName(itemData.getName());

		resourceAvailibilityVO.setVendor(itemData.getVendor());
		resourceAvailibilityVO.setWarehouseName(warehouseData.getName());
		return resourceAvailibilityVO;
	}*/


	/**
	 * Change Inventory Status Webservice API    
	 * @author yash.kapasi
	 * @param {@link List}<{@link InventoryRequestVO}> requestData
	 * @return {@link ChangeInventoryStatusResponseData} responseData
	 */
	@WebMethod
	public ChangeInventoryStatusResponseData changeInventoryStatus(List<InventoryRequestVO> requestData){
		Long startTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "changeInventoryStatus method called");
		ChangeInventoryStatusResponseData statusResponseData = new ChangeInventoryStatusResponseData();
		
		List<InventoryStatusVO> responseData = new ArrayList<InventoryStatusVO>();
		
		try {
			
			IBDSessionContext sessionContext = null;
			Map<String, UserVO> map = UserFactory.findAllUser();
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
			}
			
			if(requestData == null || requestData.isEmpty()){
				throw new SearchBLException(InventoryMgtResponseCode.INVENTORY_DETAILS_REQUEST_NOT_FOUND);
			}else{
				for(InventoryRequestVO inventoryRequestVO:requestData){
					if(inventoryRequestVO.getInventoryNo()==null ||inventoryRequestVO.getInventoryNo().isEmpty()){
						throw new SearchBLException(InventoryMgtResponseCode.INVENTORYNO_NOT_FOUND);
					}
				}
			}
			responseData =  wsFacadeLocal.changeInventoryStatus(requestData,sessionContext.getBLSession());
			
			
			if(responseData!=null && !responseData.isEmpty()) {
				int totalCount = 0,failed = 0;
				StringBuilder builder = new StringBuilder("");
				for(InventoryStatusVO inventoryStatusVO : responseData) {
					totalCount ++;
					if(inventoryStatusVO.getResponseCode()!=null){
							if(inventoryStatusVO.getResponseCode().startsWith("err") || inventoryStatusVO.getResponseCode().equals("-1")) {
								failed++;
								builder.append(inventoryStatusVO.getInventoryNumber() +": "+inventoryStatusVO.getResponseMessage()+",\n");
							}
					}
				}
				if(!builder.toString().isEmpty()) {
					 builder= new StringBuilder(builder.toString().substring(0, builder.toString().lastIndexOf(",")));
				}
				if(totalCount==failed || failed >0) {
					throw new SearchBLException(InventoryMgtResponseCode.CHANGE_INVENTORIES_FAILED,InventoryMgtResponseCode.CHANGE_INVENTORIES_FAILED_MESSAGE+builder.toString());
				}
				}
			statusResponseData.setResponseMessage("Success");
			statusResponseData.setResponseCode("0");
			statusResponseData.setInventoryvos(responseData);
		}catch(SearchBLException ex){
			ex.printStackTrace();
			if(ex.getErrorCode() == -1){
				statusResponseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(-1));
				statusResponseData.setResponseMessage("Some Internal error occurred, contact your system administrator.");
			}else{
				statusResponseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(ex.getErrorCode()));
				if(ex.getErrorMessage() == null){
					statusResponseData.setResponseMessage(InventoryMgtResponseCode.responseCodeToMessage(ex.getErrorCode()));
				}else{
					statusResponseData.setResponseMessage(ex.getErrorMessage());
				}
			}

			/*
			statusResponseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(ex.getErrorCode()));
			if(ex.getErrorMessage() == null){
				statusResponseData.setResponseMessage(InventoryMgtResponseCode.responseCodeToMessage(ex.getErrorCode()));
			}else{
				statusResponseData.setResponseMessage(ex.getMessage());
				
			}*/
		}
		catch(Exception e) {
			e.printStackTrace();
			responseData = null;
			statusResponseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(-1));
			statusResponseData.setResponseMessage("Some Internal error occurred, contact your system administrator.");
//			return null;
		}
		Long endTime = System.currentTimeMillis();
		Object reqObj = requestData;
		Object resObj = responseData;
		webServiceAuditLog(reqObj, resObj, statusResponseData.getResponseCode(), statusResponseData.getResponseMessage(), "changeInventoryStatus");
		Logger.logInfo(MODULE, "Time taken in getAttributes :"+(endTime-startTime));
		return statusResponseData;
	}
	
	/*
	@WebMethod
	public InventoryDetailsResponseData getInventoryDetails(InventoryDetailsRequestData requestData) {
		Long startTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "getInventoryDetails method called");
		
		InventoryDetailsResponseData responseData = new InventoryDetailsResponseData();
		
		try {
			if(requestData!=null) {
				
				boolean result = validateAttributeVO(requestData.getAttributeVOs());
				if(!result) {
					throw new SearchBLException(InventoryMgtResponseCode.ATTRIBUTESVO_VALIDATION_ERROR);
				}
				boolean validate  = false;
				if(requestData.getInventoryNumber()!=null && !requestData.getInventoryNumber().isEmpty()) {
					validate = true;
				}
				if(requestData.getResourceId()!=null && !requestData.getResourceId().isEmpty()) {
					validate = true;
				}
				if(requestData.getWareHouseName()!=null && !requestData.getWareHouseName().isEmpty() && 
						requestData.getResourceName()!=null && !requestData.getResourceName().isEmpty() && 
						requestData.getResourceType()!=null && !requestData.getResourceType().isEmpty() && 
						requestData.getModel()!=null && !requestData.getModel().isEmpty()) {
					validate = true;
				}
				if(!validate) {
					throw new SearchBLException(InventoryMgtResponseCode.INVENTORY_DETAILS_ERROR_VALIDATION);
				}
				
				responseData = inventoryManagementFacadeLocal.getInventoryDetails(requestData,1000);
				
				
				
			} else {
				throw new SearchBLException(InventoryMgtResponseCode.INVENTORY_DETAILS_REQUEST_NOT_FOUND);
			}
			
		}catch(SearchBLException ex){
			responseData.setResponseCode(ex.getErrorCode());
			responseData.setResponseMessage(InventoryMgtResponseCode.responseCodeToMessage(ex.getErrorCode()));
		}catch(Exception e) {
			e.printStackTrace();
			responseData.setResponseCode(-1L);
			responseData.setResponseMessage("Fail on getAttributes");
		}
		
		Long endTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "time taken getInventoryDetails : "+(endTime - startTime));
		return responseData;
		
	}*/

	private boolean validateAttributeVO(
			List<com.elitecore.cpe.bl.ws.data.input.vo.InventoryAttributeVO> attributeVOs) {
		
		boolean result = true;
		if(attributeVOs!=null && !attributeVOs.isEmpty()) {
			for(com.elitecore.cpe.bl.ws.data.input.vo.InventoryAttributeVO attributeVO : attributeVOs) {
				if(attributeVO.getAttributeName()!=null && !attributeVO.getAttributeName().isEmpty()) {
					if(attributeVO.getAttributeValue()==null || attributeVO.getAttributeValue().isEmpty()) {
						result = false;
					}
				}
			}
		}
		
		
		return result;
	}
	
	
	/**
	 * book CPE Resource Webservice API with operation of Allocate and Reserve CPE    
	 * @author yash.kapasi
	 * @param {@link BookCPERequestData} requestData
	 * @return {@link BookCPEResponseData} responseData
	 */
	@WebMethod
	public BookCPEResponseData bookCPEResource(BookCPERequestData requestData){
		
		Long startTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "BookCPEResource method called");
		BookCPEResponseData responseData = new BookCPEResponseData();
		try {
			
			//validation
			Logger.logDebug(MODULE, "validation called");
			if(requestData.getOperationType() == null ){
				throw new SearchBLException(InventoryMgtResponseCode.OPERATIONTYPE_NOT_FOUND);
			}
			
			
			if(requestData.getOperationType().equals(WsUtil.RESERVE_INT)){
				
				Logger.logDebug(MODULE, "requestData.getReserveAllocateRequestVO().toString() :"+requestData.getReserveAllocateRequestVO().toString());
		//		Logger.logDebug(MODULE, "noOfResource :"+requestData.getReserveAllocateRequestVO().toString());
				/*if(requestData.getWarehouseName() == null || requestData.getWarehouseName().isEmpty() ){
					throw new SearchBLException(InventoryMgtResponseCode.WAREHOUSE_NOT_FOUND);
				}else if(requestData.getWarehouseCode() == null || requestData.getWarehouseCode().isEmpty() ){
					throw new SearchBLException(InventoryMgtResponseCode.WAREHOUSECODE_NOT_FOUND);
				}*//*else if(requestData.getResourceId() == null || requestData.getResourceId().isEmpty()){
					throw new SearchBLException(InventoryMgtResponseCode.RESOURCE_NOT_FOUND);
				}*//*else if(requestData.getNoOfResource() == null ){
					throw new SearchBLException(InventoryMgtResponseCode.NOOFRESOURCE_NOT_FOUND);
			}*/	 
		/*		
				//Added -start
				else if(requestData.getInventoryNumber() == null || requestData.getInventoryNumber().isEmpty()){
					throw new SearchBLException(InventoryMgtResponseCode.INVENTORY_NUMBER_NOT_FOUND);
				}
				//Added -end
	*/			
/*				else if(requestData.getResourceId() != null && !requestData.getResourceId().isEmpty()){
					Map<String,Object> fieldValueMap = new HashMap<String, Object>();
					fieldValueMap.put("resourceNumber", requestData.getResourceId());
					List filterList = itemSessionBeanLocal.getFilterDataBy(EntityConstants.ITEM_DATA, fieldValueMap);
					if(filterList == null || filterList.isEmpty()){
						throw new SearchBLException(InventoryMgtResponseCode.INVALIAD_RESOURCE);
					}
				}
		*/		
		/*		if(requestData.getNoOfResource() == null ){
					requestData.setNoOfResource(1);
				} else {
					if(requestData.getNoOfResource() <= 0){
						throw new SearchBLException(InventoryMgtResponseCode.INVALID_NOOFRESOURCE);
					}
				}
	*/			
				
				/*Map<String,Object> fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("name", requestData.getWarehouseName());
				List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
				if(filterList == null || filterList.isEmpty()){
					throw new SearchBLException(InventoryMgtResponseCode.INVALIAD_WAREHOUSE);
				}
				
				if(requestData.getWarehouseCode()!=null && !requestData.getWarehouseCode().isEmpty()) {
					Map<String,Object> fieldValueMap1 = new HashMap<String, Object>();
					fieldValueMap1.put("warehouseCode", requestData.getWarehouseCode());
					List filterList1 = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap1);
					if(filterList1 == null || filterList1.isEmpty()){
						throw new SearchBLException(InventoryMgtResponseCode.INVALIAD_WAREHOUSECODE);
					}
				}
				//added -start
				if(requestData.getInventoryNumber()!=null && !requestData.getInventoryNumber().isEmpty()) {
					Map<String,Object> fieldValueMap1 = new HashMap<String, Object>();
					fieldValueMap1.put("inventoryNo", requestData.getInventoryNumber());
					List filterList1 = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap1);
					if(filterList1 == null || filterList1.isEmpty()){
						throw new SearchBLException(InventoryMgtResponseCode.INVALIAD_INVENTORY);
					}
				}

				//added -end
		*/
				//added start
				if(requestData.getOrderLineItemID() == null || requestData.getOrderLineItemID().isEmpty()){
					throw new SearchBLException(InventoryMgtResponseCode.ORDERLINEITEM_NOT_FOUND);
				}
				else if(requestData.getReserveAllocateRequestVO()== null || requestData.getReserveAllocateRequestVO().isEmpty()){
					throw new SearchBLException(InventoryMgtResponseCode.INVENTORYNO_NOT_FOUND);
				}
				
				for(ReserveAllocateRequestVO reserveAllocateRequestVO :  requestData.getReserveAllocateRequestVO()){
					if((reserveAllocateRequestVO.getInventoryNo() == null || reserveAllocateRequestVO.getInventoryNo().trim().equals("")) ){
						throw new SearchBLException(InventoryMgtResponseCode.INVENTORY_NUMBER_NOT_FOUND);
					}
				}
				//added end

				IBDSessionContext sessionContext = null;
				Map<String, UserVO> map = UserFactory.findAllUser();
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
				}
				
				
				List<com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO> inventoryVOs = wsFacadeLocal.reserveInventory(requestData,sessionContext.getBLSession());
				
				ResourceAvailibilityVO resourceAvailibilityVO = new ResourceAvailibilityVO();
				if(inventoryVOs!=null) {
					for(com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO vo : inventoryVOs){
						//vo.setInventoryStaus(InventoryStatusConstants.RESERVED_STATUS);
						
					}
				}
				resourceAvailibilityVO.setInventoryList(inventoryVOs);
				responseData.setResourceAvailibilityVO(resourceAvailibilityVO);
				
			}else if(requestData.getOperationType().equals(WsUtil.ALLOCATE_INT)){
				if(requestData.getReserveAllocateRequestVO() == null || requestData.getReserveAllocateRequestVO().isEmpty())
				{
					throw new SearchBLException(InventoryMgtResponseCode.INVENTORYNO_NOT_FOUND);
				}
				
				if(requestData.getIsResourceRecoverable() == null || requestData.getIsResourceRecoverable().isEmpty()) {
					throw new SearchBLException(InventoryMgtResponseCode.ISRESOURCERECOVERABLE_NOT_FOUND);
				} else {
					if(requestData.getIsResourceRecoverable().equals("Yes") || requestData.getIsResourceRecoverable().equals("No")) {
						Logger.logTrace(MODULE, "IsResourceRecoverable() : "+requestData.getIsResourceRecoverable());
					} else {
						throw new SearchBLException(InventoryMgtResponseCode.ISRESOURCERECOVERABLE_INVALID);
					}
				}
				
				
				
				for(ReserveAllocateRequestVO reserveAllocateRequestVO :  requestData.getReserveAllocateRequestVO()){
					if((reserveAllocateRequestVO.getInventoryNo() == null || reserveAllocateRequestVO.getInventoryNo().trim().equals("")) 
							&& (reserveAllocateRequestVO.getSerialNumber()==null || reserveAllocateRequestVO.getSerialNumber().isEmpty())){
						throw new SearchBLException(InventoryMgtResponseCode.INVENTORYNO_OR_SERIALNUMBER_NOT_FOUND);
					}
				}
				IBDSessionContext sessionContext = null;
				Map<String, UserVO> map = UserFactory.findAllUser();
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
				}
				wsFacadeLocal.allocateInventory(requestData, sessionContext.getBLSession());
			}else{
				throw new SearchBLException(InventoryMgtResponseCode.INVALID_OPERATIONTYPE);
			}
			
			
			
			responseData.setResponseCode("0");
			responseData.setResponseMessage("Success");
			
		} catch (SearchBLException e) {
			e.printStackTrace();
			if(e.getErrorCode() == -1){
				responseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(-1));
				responseData.setResponseMessage("Some Internal error occurred, contact your system administrator.");
			}else{
				responseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(e.getErrorCode()));
				if(e.getErrorMessage() == null){
					responseData.setResponseMessage(InventoryMgtResponseCode.responseCodeToMessage(e.getErrorCode()));
				}else{
					responseData.setResponseMessage(e.getErrorMessage());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			responseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(-1));
			responseData.setResponseMessage("Some Internal error occurred, contact your system administrator.");
		}
		
		webServiceAuditLog(requestData, responseData, responseData.getResponseCode(), responseData.getResponseMessage(), "BookCPEResource");
		
		Long endTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "time taken BookCPEResource : "+(endTime - startTime));
		return responseData;
	}

	/**
	 * releaseCPEResource  Webservice API with operation  release CPE 
	 * @author Rinkal Sadariya
	 * @param {@link ReleaseCPERequestVO} requestData
	 * @return {@link BookCPEResponseData} responseData
	 */
	@WebMethod

	public ReleaseCPEResponseVO releaseCPEResource(ReleaseCPERequestVO cpeRequestVO){
		
		Long startTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "ReleaseCPEResource method called");
		ReleaseCPEResponseVO responseData = new ReleaseCPEResponseVO();
		List<InventoryStatusResponseVO> responseVo = new ArrayList<InventoryStatusResponseVO>();
		List<CPEInventoryVO> inventoryVOs=new ArrayList<CPEInventoryVO>();
		try {
			
			IBDSessionContext sessionContext = null;
			Map<String, UserVO> map = UserFactory.findAllUser();
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
			}
			
			
			//validation
			Logger.logDebug(MODULE, "validation called");
			if(cpeRequestVO.getOperationType() == null ){
				throw new SearchBLException(InventoryMgtResponseCode.INVALID_OPERATIONTYPE_RELEASE);
			}

			if(cpeRequestVO.getOperationType().equals(WsUtil.RELEASE_INT)){
				Logger.logDebug(MODULE, "cpeRequestVO.toString() :"+cpeRequestVO.toString());
				inventoryVOs=cpeRequestVO.getInventoryVOs();
				if(inventoryVOs == null || inventoryVOs.isEmpty()){
					throw new SearchBLException(InventoryMgtResponseCode.INVENTORY_DETAILS_REQUEST_NOT_FOUND);
				}
				else{
					for(CPEInventoryVO releaseCPEInventoryVO:inventoryVOs){
						if(releaseCPEInventoryVO.getInventoryNo()==null || releaseCPEInventoryVO.getInventoryNo().isEmpty()){
							throw new SearchBLException(InventoryMgtResponseCode.INVENTORY_NUMBER_NOT_FOUND);
							
						}
					}
				}
				responseVo =  wsFacadeLocal.releaseCPEResource(cpeRequestVO,sessionContext.getBLSession());
				if(responseVo!=null && !responseVo.isEmpty()){
					responseData.setResponseVo(responseVo);
					responseData.setResponseCode("0");
					responseData.setResponseMessage("Success");
				}
		}
			else{
				throw new SearchBLException(InventoryMgtResponseCode.INVALID_OPERATIONTYPE_RELEASE);
			}
	
		}catch (SearchBLException e) {
			e.printStackTrace();
			if(e.getErrorCode() == -1){
				responseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(-1));
				responseData.setResponseMessage("Some Internal error occurred, contact your system administrator.");
			}else{
				responseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(e.getErrorCode()));
				if(e.getErrorMessage() == null){
					responseData.setResponseMessage(InventoryMgtResponseCode.responseCodeToMessage(e.getErrorCode()));
				}else{
					responseData.setResponseMessage(e.getErrorMessage());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			responseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(-1));
			responseData.setResponseMessage("Some Internal error occurred, contact your system administrator.");
		}
		webServiceAuditLog(cpeRequestVO, responseData, responseData.getResponseCode(), responseData.getResponseMessage(), "releaseCPEResource");
		Long endTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "time taken releaseCPEResource : "+(endTime - startTime));

		return responseData;
	}
	
	/**
	 * releaseCPEResource  Webservice API with operation  release CPE 
	 * @author Rinkal Sadariya
	 * @param {@link ReleaseCPERequestVO} requestData
	 * @return {@link BookCPEResponseData} responseData
	 */
	@WebMethod

	public CPEResponseVO markCPEAsFaultyWithOwnerChang(MarkCPEAsFaultyRequestVO mAsFaultyRequestVO){
		
		Long startTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "BookCPEResource method called");
		CPEResponseVO responseVO=new CPEResponseVO();
		List<InventoryStatusResponseVO> statusResponseVOs=new ArrayList<InventoryStatusResponseVO>();
		try{
			IBDSessionContext sessionContext = null;
			Map<String, UserVO> map = UserFactory.findAllUser();
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
			}
			List<CPEInventoryVO> cpeInventoryVOs = mAsFaultyRequestVO.getListCpeInventoryVOs();
			if(mAsFaultyRequestVO.getWarehouseCode()==null || mAsFaultyRequestVO.getWarehouseCode().isEmpty()){
					throw new SearchBLException(InventoryMgtResponseCode.WAREHOUSECODE_NOTFOUND);
			}
			if(mAsFaultyRequestVO.getWarehouseCode()!=null && !mAsFaultyRequestVO.getWarehouseCode().isEmpty()){
				Map<String,Object> fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("warehouseCode", mAsFaultyRequestVO.getWarehouseCode());
				List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap);
				if(filterList == null || filterList.isEmpty()){
					throw new SearchBLException(InventoryMgtResponseCode.INVALIAD_WAREHOUSECODE);
				}
			}
			if(cpeInventoryVOs==null || cpeInventoryVOs.isEmpty()){
						throw new SearchBLException(InventoryMgtResponseCode.INVENTORYNO_NOT_FOUND);
		    }
			else{
				for(CPEInventoryVO releaseCPEInventoryVO:cpeInventoryVOs){
					if(releaseCPEInventoryVO.getInventoryNo()==null || releaseCPEInventoryVO.getInventoryNo().isEmpty()){
						System.out.println("value of releaseCPEInventoryVO.getInventoryNo() :::::::::"+releaseCPEInventoryVO.getInventoryNo());
						throw new SearchBLException(InventoryMgtResponseCode.INVENTORYNO_NOT_FOUND);
						
					}
				}
			}
				statusResponseVOs = wsFacadeLocal.markCPEAsFaultyWithOwnerChange(mAsFaultyRequestVO,sessionContext.getBLSession());
				if(statusResponseVOs!=null && !statusResponseVOs.isEmpty())
					{
					responseVO.setResponseVo(statusResponseVOs);
					responseVO.setResponseCode("0");
					responseVO.setResponseMessage("Success");
					}
		}catch (SearchBLException e) {
			e.printStackTrace();
			if(e.getErrorCode() == -1){
				responseVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(-1));
				responseVO.setResponseMessage("Some Internal error occurred, contact your system administrator.");
			}else{
				responseVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(e.getErrorCode()));
				if(e.getErrorMessage() == null){
					responseVO.setResponseMessage(InventoryMgtResponseCode.responseCodeToMessage(e.getErrorCode()));
				}else{
					responseVO.setResponseMessage(e.getErrorMessage());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			responseVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(-1));
			responseVO.setResponseMessage("Some Internal error occurred, contact your system administrator.");
		}
		webServiceAuditLog(mAsFaultyRequestVO, responseVO, responseVO.getResponseCode(), responseVO.getResponseMessage(), "markCPEAsFaultyWithOwnerChang");
		Long endTime = System.currentTimeMillis();
		Logger.logInfo(MODULE, "time taken markCPEAsFaultyWithOwnerChang : "+(endTime - startTime));
		return responseVO;
	}
}
