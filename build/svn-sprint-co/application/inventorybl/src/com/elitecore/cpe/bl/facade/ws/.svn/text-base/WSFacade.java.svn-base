package com.elitecore.cpe.bl.facade.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.elitecore.cpe.bl.constants.inventorymgt.InventoryStatusConstants;
import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryReserveData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryReserveDetailData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.entity.inventory.system.systemparameter.SystemParameter;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementFacadeLocal;
import com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementUtil;
import com.elitecore.cpe.bl.facade.master.item.ItemFacadeLocal;
import com.elitecore.cpe.bl.facade.system.systemparameter.SystemParameterFacadeLocal;
import com.elitecore.cpe.bl.session.inventorymgt.BatchManagementSessionBeanLocal;
import com.elitecore.cpe.bl.session.inventorymgt.InventoryManagementSessionBeanLocal;
import com.elitecore.cpe.bl.session.master.attribute.AttributeSessionBeanLocal;
import com.elitecore.cpe.bl.session.master.warehouse.WarehouseSessionBeanLocal;
import com.elitecore.cpe.bl.session.system.internal.SystemInternalSessionBeanLocal;
import com.elitecore.cpe.bl.session.system.systemparameter.SystemParameterSessionBeanLocal;
import com.elitecore.cpe.bl.ws.data.input.request.BookCPERequestData;
import com.elitecore.cpe.bl.ws.data.input.request.MarkCPEAsFaultyRequestVO;
import com.elitecore.cpe.bl.ws.data.input.request.ReleaseCPERequestVO;
import com.elitecore.cpe.bl.ws.data.input.request.ResourceAvailibilityRequestData;
import com.elitecore.cpe.bl.ws.data.input.response.CPEResponseVO;
import com.elitecore.cpe.bl.ws.data.input.response.InventoryStatusResponseVO;
import com.elitecore.cpe.bl.ws.data.input.response.InventoryStatusVO;
import com.elitecore.cpe.bl.ws.data.input.response.ResourceAvailibilityResponseData;
import com.elitecore.cpe.bl.ws.data.input.vo.CPEInventoryVO;
import com.elitecore.cpe.bl.ws.data.input.vo.InventoryAttributeVO;
import com.elitecore.cpe.bl.ws.data.input.vo.InventoryRequestVO;
import com.elitecore.cpe.bl.ws.data.input.vo.ReserveAllocateRequestVO;
import com.elitecore.cpe.bl.ws.data.input.vo.ResourceAvailibilityVO;
import com.elitecore.cpe.bl.ws.data.util.InventoryMgtResponseCode;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;


@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class WSFacade extends BaseFacade implements WSFacadeLocal,WSFacadeRemote {

	private static final String MODULE = "WEBSERVICE-FC";	
	
	@EJB private SystemInternalSessionBeanLocal systemInternalSessionBeanLocal;
	@EJB private WarehouseSessionBeanLocal warehouseSessionBeanLocal;
	@EJB private InventoryManagementSessionBeanLocal inventoryManagementSessionBeanLocal;
	@EJB private BatchManagementSessionBeanLocal batchManagementSessionBeanLocal;
	@EJB private AttributeSessionBeanLocal attributeSessionBeanLocal;
	
	@EJB private SystemParameterFacadeLocal systemParameterFacadeLocal;
	@EJB private InventoryManagementFacadeLocal inventoryManagementFacadeLocal;
	@EJB private SystemParameterSessionBeanLocal systemParameterSessionBeanLocal;
	@EJB private ItemFacadeLocal itemFacadeLocal;
	
	
	/**
	 * reserve Inventory Operation via webservice call    
	 * @author yash.kapasi
	 * @param {@link BookCPERequestData} requestData
	 * @param {@link IBLSession}
	 * @return {@link List}<{@link InventoryVO}> responseData
	 * @throws SearchBLException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public List<com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO> reserveInventory(BookCPERequestData requestData,IBLSession iblSession) throws SearchBLException{
		Logger.logDebug(MODULE, " in reserveInventory method called");
		List<com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO> inventoryVOs = new ArrayList<com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO>();
		List<ReserveAllocateRequestVO> reserveList=requestData.getReserveAllocateRequestVO();
		int reserveListsize=reserveList.size();
		Logger.logTrace(MODULE, ":::reserveListsize"+reserveListsize);
		List<InventoryRequestVO> inventoryRequestVOs = new ArrayList<InventoryRequestVO>();
		List<InventoryAttributeVO> wsAttributeV0s=new ArrayList<InventoryAttributeVO>();
		List<InventoryStatusVO> statusVoList=new ArrayList<InventoryStatusVO>();
		try{
			//--added today start
			for(ReserveAllocateRequestVO reserveRequestVO:reserveList){
				com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO detailVO = inventoryManagementFacadeLocal.searchInventoryDetailDataById(reserveRequestVO.getInventoryNo());
				
				if(detailVO!=null){
					SystemParameter systemParameter = systemParameterSessionBeanLocal.getSystemParameter(SystemParameterConstants.INVENTORY_FOR_NEW_ORDER);
					List<InventoryData> inventoryDatas =  inventoryManagementSessionBeanLocal.checkCPEInventory(reserveRequestVO.getInventoryNo(),systemParameter);
					
					if(inventoryDatas!=null && !(inventoryDatas.isEmpty())){
						InventoryData inventoryData = inventoryDatas.get(0);
						InventoryRequestVO inventoryRequestVO = new InventoryRequestVO();
						inventoryRequestVO.setInventoryNo(inventoryData.getInventoryNo());
						inventoryRequestVO.setOldStatus(WSFacadeUtil.getStatusFromWSStatus(InventoryStatusConstants.AVAILABLE_STATUS));
						inventoryRequestVO.setNewStatus(WSFacadeUtil.getStatusFromWSStatus(InventoryStatusConstants.RESERVED_STATUS));
						inventoryRequestVO.setRemarks("WS:BookCPE, Mark for Reserved");
						inventoryRequestVO.setOrderLineItemID(requestData.getOrderLineItemID());
						inventoryRequestVOs.add(inventoryRequestVO);
						Logger.logTrace(MODULE,"::inventoryRequestVO:::"+inventoryRequestVO.toString());
						Logger.logTrace(MODULE,"::InventoryStatusId():::"+inventoryData.getInventoryStatusId());
						//inventoryVO.setInventoryNumber(inventoryData.getInventoryNo());
						//inventoryVOs.add(inventoryVO);
					}else{
						if(detailVO.getTransferStatus()!=null){
							InventoryStatusVO statusVo=new InventoryStatusVO(reserveRequestVO.getInventoryNo(),InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUS_TRANSITION_NOTALLOWED),InventoryMgtResponseCode.STATUS_TRANSITION_NOTALLOWED_FOR_INVENTORY_INTRANSFER_MESSAGE);
							statusVoList.add(statusVo);
							
						}
						else{
							InventoryStatusVO statusVo=new InventoryStatusVO(reserveRequestVO.getInventoryNo(),InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUS_TRANSITION_NOTALLOWED),"Status change not allowed from "+detailVO.getStatus()+" to "+InventoryStatusConstants.RESERVED_STATUS);
							statusVoList.add(statusVo);
						}
					}
				}
				else{
					InventoryStatusVO statusVo=new InventoryStatusVO();
					statusVo.setInventoryNumber(reserveRequestVO.getInventoryNo());
					statusVo.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVALID_INVENTORY_NO));
					statusVo.setResponseMessage(InventoryMgtResponseCode.INVALID_INVENTORY_NO_MESSAGE);
					statusVoList.add(statusVo);
				}
			}
			List<InventoryStatusVO> inventoryStatusVOs=new ArrayList<InventoryStatusVO>();
			//	change Inventory status
			if(inventoryRequestVOs!=null && !(inventoryRequestVOs.isEmpty())){
				inventoryStatusVOs = changeInventoryStatusforInventory(inventoryRequestVOs,reserveListsize,iblSession);
				Logger.logDebug(MODULE, "\n\n Response from changeStatus :"+inventoryStatusVOs );
				
				statusVoList.addAll(inventoryStatusVOs);
				Logger.logDebug(MODULE, "\n\n Consolidate List::::"+statusVoList );
			}
			int countTotal = 0,failed = 0,passed = 0;
			StringBuilder builder = new StringBuilder("");
			if(statusVoList!=null && !statusVoList.isEmpty()) {
				for(InventoryStatusVO inventoryStatusVO : statusVoList) {
					countTotal++;
					if(inventoryStatusVO.getResponseCode().startsWith("err")) {
						failed++;
						builder.append(inventoryStatusVO.getInventoryNumber()+":"+inventoryStatusVO.getResponseMessage()+"\n,");
					} else {
						passed++;
					}
				}
				
				if(!builder.toString().isEmpty()) {
					 builder= new StringBuilder(builder.toString().substring(0, builder.toString().lastIndexOf(",")));
				}
				
				if((countTotal==failed) || (failed>0)) {
					
					throw new SearchBLException(InventoryMgtResponseCode.INVENTORY_NUMBER_RESERVE_FAILED,InventoryMgtResponseCode.INVENTORY_NUMBER_RESERVE_FAILED_MESSAGE+builder.toString());
				}
			}
			
			if(failed==0){
				if(inventoryStatusVOs!=null && !inventoryStatusVOs.isEmpty()){
					//initial request insert into reserve and reserveDetail table
					String reservationNo = systemInternalSessionBeanLocal.getPrimaryKey(CPECommonConstants.RESERVE_DATA);
					Logger.logDebug(MODULE, "reservationNo :"+reservationNo);
					InventoryReserveData inventoryReserveData  = InventoryManagementUtil.prepareInventoryReserveData(reservationNo,1L,requestData.getOrderLineItemID(),null);
					Logger.logDebug(MODULE, "::inventoryReserveData.toString():::"+inventoryReserveData.toString());
					InventoryReserveData reserveData = inventoryManagementSessionBeanLocal.reserveInventory(inventoryReserveData);
					List<InventoryReserveDetailData> inventoryReserveDetailDatas = new ArrayList<InventoryReserveDetailData>();
					for(InventoryStatusVO inventoryStatusVO : inventoryStatusVOs)
					{	
						com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO inventoryVO=new com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO();
						if(inventoryStatusVO.getResponseCode().equals("0")){
							com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO detailVO= inventoryManagementFacadeLocal.searchInventoryDetailDataById(inventoryStatusVO.getInventoryNumber());
							Map<String,String> attributeMap = new HashMap<String,String>();
							attributeMap = detailVO.getAttribute();
							for (Map.Entry<String, String> attributeEntry : attributeMap.entrySet()) {
								Logger.logTrace(MODULE,"Attribute Name = " + attributeEntry.getKey() + ", Attribute Value = " + attributeEntry.getValue());
								InventoryAttributeVO invAttributeVO=new InventoryAttributeVO();
								invAttributeVO.setAttributeName(attributeEntry.getKey());
								invAttributeVO.setAttributeValue(attributeEntry.getValue());
								wsAttributeV0s.add(invAttributeVO);
							}
							inventoryVO.setAttributeVOs(wsAttributeV0s);
							inventoryVO.setInventoryStaus(InventoryStatusConstants.RESERVED_STATUS);
						}
						inventoryVO.setInventoryNumber(inventoryStatusVO.getInventoryNumber());
						inventoryVOs.add(inventoryVO);
						if(inventoryStatusVO.getResponseCode().equals("0")){
							inventoryReserveDetailDatas.add(InventoryManagementUtil.prepareInventoryReserveDetailData(inventoryStatusVO.getInventoryNumber(), reserveData.getReservationId()));
							//throw new SearchBLException("Reserved Inventory Failed");
						}
					}
					inventoryManagementSessionBeanLocal.reserveInventoryDetail(inventoryReserveDetailDatas);
				}		
			}
	}
		catch(UpdateBLException ex1){
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw new SearchBLException("Reserved Inventory Failed");
			
		}
		catch(SearchBLException ex) {
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw ex;
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return inventoryVOs;
	}
	
	/**
	 * reserve Inventory Operation via webservice call    
	 * @author yash.kapasi
	 * @param {@link BookCPERequestData} requestData
	 * @param {@link IBLSession}
	 * @return {@link List}<{@link InventoryVO}> responseData
	 * @throws SearchBLException
	 */
	/*@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public List<com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO> reserveInventory_old(BookCPERequestData requestData,IBLSession iblSession) throws SearchBLException{
		
		Logger.logDebug(MODULE, " in reserveInventory method called");
		List<com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO> inventoryVOs = null;
		try {
			
			ResourceAvailibilityRequestData resourceAvailibilityRequestData = new ResourceAvailibilityRequestData();
			resourceAvailibilityRequestData.setResourceId(requestData.getResourceId());
			resourceAvailibilityRequestData.setWarehouseName(requestData.getWarehouseName());
			//added -start
			resourceAvailibilityRequestData.setInventoryNumber(requestData.getInventoryNumber());
			//added -end

			ResourceAvailibilityResponseData availibilityResponseData = checkCPEResource(resourceAvailibilityRequestData);
			if(availibilityResponseData.getResourceAvailibilityVOs()  == null || availibilityResponseData.getResourceAvailibilityVOs().isEmpty()){
				throw new SearchBLException(InventoryMgtResponseCode.RESERVE_MORETHAN_AVAILABLE,InventoryMgtResponseCode.RESERVE_MORETHAN_AVAILABLE_MESSAGE+"0");
			}
			if(availibilityResponseData.getResourceAvailibilityVOs().get(0).getAvailableResourceCount() < requestData.getNoOfResource()){
				throw new SearchBLException(InventoryMgtResponseCode.RESERVE_MORETHAN_AVAILABLE,InventoryMgtResponseCode.RESERVE_MORETHAN_AVAILABLE_MESSAGE+availibilityResponseData.getResourceAvailibilityVOs().get(0).getAvailableResourceCount());
			}

			InventoryDetailsRequestData inventoryDetailsRequestData = new InventoryDetailsRequestData();
			inventoryDetailsRequestData.setResourceId(requestData.getResourceId());
			inventoryDetailsRequestData.setWareHouseName(requestData.getWarehouseName());
			inventoryDetailsRequestData.setWareHouseCode(requestData.getWarehouseCode());
			inventoryDetailsRequestData.setInventoryStatus(InventoryStatusConstants.AVAILABLE_STATUS);
			inventoryDetailsRequestData.setTransferStatus("");
			InventoryDetailsResponseData inventoryDetailsResponseData = inventoryManagementFacadeLocal.getInventoryDetails(inventoryDetailsRequestData,requestData.getNoOfResource());
			
			List<InventoryRequestVO> inventoryRequestVOs = new ArrayList<InventoryRequestVO>();
			
			if(inventoryDetailsResponseData.getInventoryDetailVO()!=null && !inventoryDetailsResponseData.getInventoryDetailVO().isEmpty()) {
				Logger.logDebug(MODULE, "\n\n total inventories found :"+inventoryDetailsResponseData.getInventoryDetailVO().get(0).getInventoryVOs().size() + "\n\n");
				Logger.logDebug(MODULE, "Are going to change :"+inventoryDetailsResponseData.getInventoryDetailVO().get(0).getInventoryVOs() + "\n\n");
				
				for(com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO inventoryVO : inventoryDetailsResponseData.getInventoryDetailVO().get(0).getInventoryVOs()){
					InventoryRequestVO inventoryRequestVO = new InventoryRequestVO();
					inventoryRequestVO.setInventoryNo(inventoryVO.getInventoryNumber());
					inventoryRequestVO.setOldStatus(WSFacadeUtil.getStatusFromWSStatus(InventoryStatusConstants.AVAILABLE_STATUS));
					inventoryRequestVO.setNewStatus(WSFacadeUtil.getStatusFromWSStatus(InventoryStatusConstants.RESERVED_STATUS));
					inventoryRequestVO.setRemarks("WS:BookCPE, Mark for Reserved");
					inventoryRequestVOs.add(inventoryRequestVO);
				}
			}
			
			
//			change Inventory status
			
			List<InventoryStatusVO> inventoryStatusVOs = changeInventoryStatus(inventoryRequestVOs,iblSession);
			
			Logger.logDebug(MODULE, "\n\n Response from changeStatus :"+inventoryStatusVOs );
			//initial request insert into reserve and reserveDetail table
			String reservationNo = systemInternalSessionBeanLocal.getPrimaryKey(CPECommonConstants.RESERVE_DATA);
			Logger.logDebug(MODULE, "reservationNo :"+reservationNo);
			InventoryReserveData inventoryReserveData  = InventoryManagementUtil.prepareInventoryReserveData(reservationNo, requestData.getNoOfResource().longValue(),requestData.getOrderLineItemID(),null);
			Logger.logDebug(MODULE, "inventoryReserveData :"+inventoryReserveData);
			InventoryReserveData reserveData = inventoryManagementSessionBeanLocal.reserveInventory(inventoryReserveData);
			
			List<InventoryReserveDetailData> inventoryReserveDetailDatas = new ArrayList<InventoryReserveDetailData>();
			for(InventoryStatusVO inventoryStatusVO : inventoryStatusVOs)
			{
				if(!inventoryStatusVO.getResponseCode().equals("0")){
					throw new SearchBLException("Reserved Inventory Failed");
				}
				inventoryReserveDetailDatas.add(InventoryManagementUtil.prepareInventoryReserveDetailData(inventoryStatusVO.getInventoryNumber(), reserveData.getReservationId()));
			}
			inventoryManagementSessionBeanLocal.reserveInventoryDetail(inventoryReserveDetailDatas);

			if(inventoryDetailsResponseData.getInventoryDetailVO()!=null && !inventoryDetailsResponseData.getInventoryDetailVO().isEmpty()) {
				inventoryVOs = inventoryDetailsResponseData.getInventoryDetailVO().get(0).getInventoryVOs();
			}
			
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
*/

	/**
	 * check CPE resource
	 * @author yash.kapasi
	 * @param {@link ResourceAvailibilityRequestData} requestData
	 * @return {@link ResourceAvailibilityResponseData} responseData
	 * @throws SearchBLException
	 */
	@Override
	public ResourceAvailibilityResponseData checkCPEResource(ResourceAvailibilityRequestData requestData)
			throws SearchBLException {
		ResourceAvailibilityResponseData responseData = new ResourceAvailibilityResponseData();

		//THRESHOLD_COUNT or INVENTORY_FOR_NEW_ORDER
		SystemParameter systemParameter = systemParameterSessionBeanLocal.getSystemParameter(SystemParameterConstants.INVENTORY_FOR_NEW_ORDER);
		
		List<InventoryData> inventoryDatas =  inventoryManagementSessionBeanLocal.checkCPEResource(requestData.getResourceId(),requestData.getWarehouseName(),requestData.getWarehouseCode(),systemParameter);
		responseData.setResponseCode("0");
		responseData.setResponseMessage("Success");
		Logger.logTrace(MODULE,":::::::::::::inventoryDatas::::::::::::::"+inventoryDatas);
		//added start
		if(requestData.getWarehouseCode()!=null && !requestData.getWarehouseName().isEmpty()) {
			if(inventoryDatas==null || inventoryDatas.isEmpty()) {
			inventoryDatas =  inventoryManagementSessionBeanLocal.checkCPEResource(requestData.getResourceId(),null,null,systemParameter);
				
				responseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.RESOURCE_NOT_FOUND_WITHWAREHOUSECODE));
				responseData.setResponseMessage(InventoryMgtResponseCode.RESOURCE_NOT_FOUND_WITHWAREHOUSECODE_MESSAGE
						+requestData.getWarehouseName()+". Find detial of resource available in other warehouse. ");
			}

		}
		//added end
		if(requestData.getWarehouseCode()!=null && !requestData.getWarehouseCode().isEmpty()) {
			if(inventoryDatas==null || inventoryDatas.isEmpty()) {
				inventoryDatas =  inventoryManagementSessionBeanLocal.checkCPEResource(requestData.getResourceId(),requestData.getWarehouseName(),null,systemParameter);
				
				responseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.RESOURCE_NOT_FOUND_WITHWAREHOUSECODE));
				responseData.setResponseMessage(InventoryMgtResponseCode.RESOURCE_NOT_FOUND_WITHWAREHOUSECODE_MESSAGE
						+requestData.getWarehouseCode()+". Find detial of resource available in other warehouse. ");
			}
		} 
		
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
				availibilityVO.setWarehouseCode(inventoryData.getWarehousedata().getWarehouseCode());
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
			
			if(requestData.getWarehouseCode()!=null && !requestData.getWarehouseCode().isEmpty()) {
				
			} else {
				responseData.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.NO_RESOURCE_FOUND));
				responseData.setResponseMessage(InventoryMgtResponseCode.NO_RESOURCE_FOUND_MESSAGE);
			}
			
			
		}
		
		
		return responseData;
	}


	/**
	 * change Inventory Status
	 * @author yash.kapasi
	 * @param reserveListsize 
	 * @param {@link List}<{@link InventoryRequestVO}> inventoryRequestVOs
	 * @param {@link IBLSession }
	 * @return {@link List}<{@link InventoryStatusVO}> responseData
	 * @throws UpdateBLException
	 */
	@Override
	public List<InventoryStatusVO> changeInventoryStatusforInventory(List<InventoryRequestVO> inventoryRequestVOs,int reserveListsize, IBLSession iblSession) throws UpdateBLException {
		
		List<InventoryStatusVO> inventoryStatusVOs = new ArrayList<InventoryStatusVO>();
		List<InventoryRequestVO> validRequestVO = new ArrayList<InventoryRequestVO>();
		Logger.logTrace(MODULE, " In changeInventoryStatus:::::::");

		List<String> notAllowedForCentral = new ArrayList<String>();
		notAllowedForCentral.add("Reserved");
		notAllowedForCentral.add("Allocated");
		notAllowedForCentral.add("Delivered");
		notAllowedForCentral.add("Recovered");
		
		for(InventoryRequestVO inventoryRequestVO : inventoryRequestVOs) {
			boolean isAdd = false;
			
			InventoryStatusVO statusVO = new InventoryStatusVO();
			if(inventoryRequestVO!=null) {
				if(inventoryRequestVO.getInventoryNo()==null || inventoryRequestVO.getInventoryNo().isEmpty())  {
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_NUMBER_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_NUMBER_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				} 
				/*if(!isAdd && (inventoryRequestVO.getOldStatus()==null)) {
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.INVENTORY_OLDSTATUS_NOT_FOUND);
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_OLDSTATUS_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}*/
				if(!isAdd && (inventoryRequestVO.getNewStatus()==null )){
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_NEWSTATUS_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_NEWSTATUS_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				if(!isAdd && (inventoryRequestVO.getRemarks() == null || inventoryRequestVO.getRemarks().isEmpty())){
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.REMARKS_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.REMARKS_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				
				
				if(!isAdd) {
					
					if(inventoryRequestVO.getInventoryNo()!=null && !inventoryRequestVO.getInventoryNo().isEmpty()) {
						
						Map<String,Object> fieldValueMap = new HashMap<String, Object>();
						fieldValueMap.put("inventoryNo", inventoryRequestVO.getInventoryNo());
						List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
						if(filterList == null || filterList.isEmpty()){
							statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
							statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVALID_INVENTORY_NO));
							statusVO.setResponseMessage(InventoryMgtResponseCode.INVALID_INVENTORY_NO_MESSAGE);
							inventoryStatusVOs.add(statusVO);
							isAdd = true;
						}
						
						if(!isAdd) {
							if(filterList!=null && !filterList.isEmpty()) {
								InventoryData data = (InventoryData) filterList.get(0);
								if(data.getWarehousedata().getWarehouseTypeData().getName().equals("Central")) {
									if(notAllowedForCentral.contains(WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getNewStatus()))) {
										statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
										statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUSCHANGE_FOR_CENTRAL));
										statusVO.setResponseMessage(InventoryMgtResponseCode.STATUSCHANGE_FOR_CENTRAL_MESSAGE);
										inventoryStatusVOs.add(statusVO);
										isAdd = true;
									}
								}
							}
						}
						
						/*if(!isAdd) {
							boolean isChangeStatusAllowed = inventoryManagementSessionBeanLocal.isChangeStatusAllowed(inventoryRequestVO.getInventoryNo(),inventoryRequestVO.getSerialNumber());
							if(!isChangeStatusAllowed) {
								statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
								statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVALID_TRANSFERSTATUS));
								statusVO.setResponseMessage(InventoryMgtResponseCode.INVALID_TRANSFERSTATUS_MESSAGE);
								inventoryStatusVOs.add(statusVO);
								isAdd = true;
							}
						}
*/
					} /*else if(inventoryRequestVO.getSerialNumber()!=null && !inventoryRequestVO.getSerialNumber().isEmpty()) {
						boolean isChangeStatusAllowed = inventoryManagementSessionBeanLocal.isChangeStatusAllowedWithSerialNumber(inventoryRequestVO.getSerialNumber());
						if(!isChangeStatusAllowed) {
							statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
							statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVALID_SERIALNUMBER));
							statusVO.setResponseMessage(InventoryMgtResponseCode.INVALID_SERIALNUMBER_MESSAGE);
							inventoryStatusVOs.add(statusVO);
							isAdd = true;
						}
					}
					*/
				}
				
				
				
				if(!isAdd) {
					
					String oldStatus = null;
					
					if(inventoryRequestVO.getOldStatus()==null || inventoryRequestVO.getOldStatus()==0) {
						Map<String,Object> fieldValueMap = new HashMap<String, Object>();
						fieldValueMap.put("inventoryNo", inventoryRequestVO.getInventoryNo());
						List<InventoryData> filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
						if(filterList!=null && !filterList.isEmpty()) {
							InventoryData data = filterList.get(0);
							oldStatus = data.getStatusData().getName();
							Logger.logTrace(MODULE, "Find Status :: "+oldStatus+" :: "+inventoryRequestVO.getOldStatus());
						}
					}
					
					if(inventoryRequestVO.getOldStatus()!=null && inventoryRequestVO.getOldStatus()!=0) {
						oldStatus = WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getOldStatus());
					}
					String newStatus = WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getNewStatus());

					if(InventoryStatusConstants.AVAILABLE_STATUS.equals(oldStatus) && 
							(newStatus.equals(InventoryStatusConstants.ALLOCATED_STATUS) || newStatus.equals(InventoryStatusConstants.DELIVERED_STATUS))) {
						
						List<InventoryRequestVO> statusChangeList = new ArrayList<InventoryRequestVO>();
						inventoryRequestVO.setNewStatus(WSFacadeUtil.RESERVED_STATUS_CODE);
						statusChangeList.add(inventoryRequestVO);
						inventoryManagementSessionBeanLocal.changeInventoryStatus(statusChangeList,iblSession);
						inventoryRequestVO.setOldStatus(WSFacadeUtil.RESERVED_STATUS_CODE);
						inventoryRequestVO.setNewStatus(WSFacadeUtil.getStatusFromWSStatus(newStatus));
						oldStatus = InventoryStatusConstants.RESERVED_STATUS;
						
					}
					inventoryRequestVO.setOldStatus(WSFacadeUtil.getStatusFromWSStatus(oldStatus));
					boolean validate = inventoryManagementSessionBeanLocal.isValidInventoryChangeStatus(oldStatus, newStatus);
					if(!validate) {
						inventoryStatusVOs.add(new InventoryStatusVO(inventoryRequestVO.getInventoryNo(),InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUS_TRANSITION_NOTALLOWED), "Status change not from "+oldStatus+" to "+newStatus));
					} else {
						validRequestVO.add(inventoryRequestVO);
					}
				}
				
			}
		}
		
		if( (inventoryRequestVOs.size()==validRequestVO.size()) && (inventoryRequestVOs.size()==reserveListsize)){
			if(validRequestVO!=null && !validRequestVO.isEmpty()) {
				List<InventoryStatusVO> statusVOs = inventoryManagementSessionBeanLocal.changeInventoryStatus(validRequestVO,iblSession);
				if(statusVOs!=null && !statusVOs.isEmpty()) {
					inventoryStatusVOs.addAll(statusVOs);
				}
			}
			
		}
		return inventoryStatusVOs;

	}
	
	@Override
	public List<InventoryStatusVO> changeInventoryStatus(List<InventoryRequestVO> inventoryRequestVOs,IBLSession iblSession) throws UpdateBLException {
		
		List<InventoryStatusVO> inventoryStatusVOs = new ArrayList<InventoryStatusVO>();
		List<InventoryRequestVO> validRequestVO = new ArrayList<InventoryRequestVO>();
		
		List<String> notAllowedForCentral = new ArrayList<String>();
		notAllowedForCentral.add("Reserved");
		notAllowedForCentral.add("Allocated");
		notAllowedForCentral.add("Delivered");
		notAllowedForCentral.add("Recovered");
		
		for(InventoryRequestVO inventoryRequestVO : inventoryRequestVOs) {
			boolean isAdd = false;
			
			InventoryStatusVO statusVO = new InventoryStatusVO();
			if(inventoryRequestVO!=null) {
				if(inventoryRequestVO.getInventoryNo()==null || inventoryRequestVO.getInventoryNo().isEmpty() ) {
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_NUMBER_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_NUMBER_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				} 
				if(!isAdd && inventoryRequestVO.getInventoryNo()!=null){
					Map<String,Object> fieldValueMap = new HashMap<String, Object>();
					fieldValueMap.put("inventoryNo", inventoryRequestVO.getInventoryNo());
					List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
						if(filterList == null || filterList.isEmpty()){
							statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
							statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORYNO_NOT_FOUND));
							statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORYNO_NOT_FOUND_MESSAGE);
							inventoryStatusVOs.add(statusVO);
							isAdd = true;
						}		
				}
				Logger.logTrace(MODULE, "Old status:::: "+inventoryRequestVO.getOldStatus());
		/*		if(!isAdd && (inventoryRequestVO.getOldStatus()==null)) {
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_OLDSTATUS_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_OLDSTATUS_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}*/
				if(!isAdd && (inventoryRequestVO.getNewStatus()<0  || inventoryRequestVO.getNewStatus()>11)){
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_INVALID_NEW_STATUS));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_INVALID_NEW_STATUS_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				Logger.logTrace(MODULE,"::::::::::::New Status::"+inventoryRequestVO.getNewStatus());
				if(!isAdd && (inventoryRequestVO.getNewStatus()==null || inventoryRequestVO.getNewStatus()==0)){
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_NEWSTATUS_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_NEWSTATUS_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				if(!isAdd && (inventoryRequestVO.getRemarks() == null || inventoryRequestVO.getRemarks().isEmpty())){
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.REMARKS_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.REMARKS_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				//added Start for status validation
				if(!isAdd && (inventoryRequestVO.getOldStatus()<0  || inventoryRequestVO.getOldStatus()>11)){
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_INVALID_OLD_STATUS));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_INVALID_OLD_STATUS_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				if(!isAdd && (inventoryRequestVO.getOldStatus()!=0)){
					Map<String,Object> fieldValueMap = new HashMap<String, Object>();
					fieldValueMap.put("inventoryNo", inventoryRequestVO.getInventoryNo());
					fieldValueMap.put("inventoryStatusId", inventoryRequestVO.getOldStatus());
					List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
						if(filterList == null || filterList.isEmpty()){
									
							statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
							statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_INVALID_OLD_STATUS));
							statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_INVALID_OLD_STATUS_MESSAGE);
							inventoryStatusVOs.add(statusVO);
							isAdd = true;
					}
				}
				//added end
				
				if(!isAdd) {
					
					if(inventoryRequestVO.getInventoryNo()!=null && !inventoryRequestVO.getInventoryNo().isEmpty()) {
						
						Map<String,Object> fieldValueMap = new HashMap<String, Object>();
						fieldValueMap.put("inventoryNo", inventoryRequestVO.getInventoryNo());
						List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
						
							if(filterList!=null && !filterList.isEmpty()) {
								InventoryData data = (InventoryData) filterList.get(0);
								if(data.getWarehousedata().getWarehouseTypeData().getName().equals("Central")) {
									if(notAllowedForCentral.contains(WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getNewStatus()))) {
										statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
										statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUSCHANGE_FOR_CENTRAL));
										statusVO.setResponseMessage(InventoryMgtResponseCode.STATUSCHANGE_FOR_CENTRAL_MESSAGE);
										inventoryStatusVOs.add(statusVO);
										isAdd = true;
									}
								}
							
						}
						
						if(!isAdd) {
							boolean isChangeStatusAllowed = inventoryManagementSessionBeanLocal.isChangeStatusAllowed(inventoryRequestVO.getInventoryNo(),inventoryRequestVO.getSerialNumber());
							if(!isChangeStatusAllowed) {
								statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
								statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVALID_TRANSFERSTATUS));
								statusVO.setResponseMessage(InventoryMgtResponseCode.INVALID_TRANSFERSTATUS_MESSAGE);
								inventoryStatusVOs.add(statusVO);
								isAdd = true;
							}
						}

					} /*else if(inventoryRequestVO.getSerialNumber()!=null && !inventoryRequestVO.getSerialNumber().isEmpty()) {
						boolean isChangeStatusAllowed = inventoryManagementSessionBeanLocal.isChangeStatusAllowedWithSerialNumber(inventoryRequestVO.getSerialNumber());
						if(!isChangeStatusAllowed) {
							statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
							statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVALID_SERIALNUMBER));
							statusVO.setResponseMessage(InventoryMgtResponseCode.INVALID_SERIALNUMBER_MESSAGE);
							inventoryStatusVOs.add(statusVO);
							isAdd = true;
						}
					}*/
					
				}
				
				if(!isAdd) {
					
					String oldStatus = null;
					
					if(inventoryRequestVO.getOldStatus()==null || inventoryRequestVO.getOldStatus()==0) {
						Map<String,Object> fieldValueMap = new HashMap<String, Object>();
						fieldValueMap.put("inventoryNo", inventoryRequestVO.getInventoryNo());
						List<InventoryData> filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
						if(filterList!=null && !filterList.isEmpty()) {
							InventoryData data = filterList.get(0);
							oldStatus = data.getStatusData().getName();
							Logger.logTrace(MODULE, "Find Status :: "+oldStatus+" :: "+inventoryRequestVO.getOldStatus());
						}
					}
					
					if(inventoryRequestVO.getOldStatus()!=null && inventoryRequestVO.getOldStatus()!=0) {
						oldStatus = WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getOldStatus());
					}
					String newStatus = WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getNewStatus());

					if(InventoryStatusConstants.AVAILABLE_STATUS.equals(oldStatus) && (newStatus.equals(InventoryStatusConstants.ALLOCATED_STATUS) || newStatus.equals(InventoryStatusConstants.DELIVERED_STATUS))) {
						
						List<InventoryRequestVO> statusChangeList = new ArrayList<InventoryRequestVO>();
						inventoryRequestVO.setNewStatus(WSFacadeUtil.RESERVED_STATUS_CODE);
						statusChangeList.add(inventoryRequestVO);
						inventoryManagementSessionBeanLocal.changeInventoryStatus(statusChangeList,iblSession);
						inventoryRequestVO.setOldStatus(WSFacadeUtil.RESERVED_STATUS_CODE);
						inventoryRequestVO.setNewStatus(WSFacadeUtil.getStatusFromWSStatus(newStatus));
						oldStatus = InventoryStatusConstants.RESERVED_STATUS;
						
					}
					inventoryRequestVO.setOldStatus(WSFacadeUtil.getStatusFromWSStatus(oldStatus));
					boolean validate = inventoryManagementSessionBeanLocal.isValidInventoryChangeStatus(oldStatus, newStatus);
					if(!validate) {
						inventoryStatusVOs.add(new InventoryStatusVO(inventoryRequestVO.getInventoryNo(),InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUS_TRANSITION_NOTALLOWED), "Change status not allowed  from "+oldStatus+" to "+newStatus));
					} else {
						validRequestVO.add(inventoryRequestVO);
					}
				}
				
			}
		}
		Logger.logTrace(MODULE,"::::::::validRequestVO::"+validRequestVO);
		if(validRequestVO!=null && !validRequestVO.isEmpty()) {
			if(validRequestVO.size()==inventoryRequestVOs.size()){	
			List<InventoryStatusVO> statusVOs = inventoryManagementSessionBeanLocal.changeInventoryStatus(validRequestVO,iblSession);
				if(statusVOs!=null && !statusVOs.isEmpty()) {
				inventoryStatusVOs.addAll(statusVOs);
				}
			}
		}
		return inventoryStatusVOs;

	}
	
	
	/**
	 * change Inventory Status for Allocate
	 * @author yash.kapasi
	 * @param {@link List}<{@link InventoryRequestVO}> inventoryRequestVOs
	 * @param {@link IBLSession }
	 * @return {@link List}<{@link InventoryStatusVO}> responseData
	 * @throws UpdateBLException
	 */
	public List<InventoryStatusVO> changeInventoryStatusForAllocate(List<InventoryRequestVO> inventoryRequestVOs,IBLSession iblSession) throws UpdateBLException {
		
		List<InventoryStatusVO> inventoryStatusVOs = new ArrayList<InventoryStatusVO>();
		
		List<InventoryRequestVO> validRequestVO = new ArrayList<InventoryRequestVO>();
		List<String> notAllowedForCentral = new ArrayList<String>();
		notAllowedForCentral.add("Reserved");
		notAllowedForCentral.add("Allocated");
		notAllowedForCentral.add("Delivered");
		notAllowedForCentral.add("Recovered");
		
		for(InventoryRequestVO inventoryRequestVO : inventoryRequestVOs) {
			boolean isAdd = false;
			
			InventoryStatusVO statusVO = new InventoryStatusVO();
			if(inventoryRequestVO!=null) {
				if((inventoryRequestVO.getInventoryNo()==null || inventoryRequestVO.getInventoryNo().isEmpty()) 
						&& (inventoryRequestVO.getSerialNumber()==null || inventoryRequestVO.getSerialNumber().isEmpty())) {
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_NUMBER_OR_SERIALNUMBER_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_NUMBER_OR_SERIALNUMBER_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				} 
				if(!isAdd && (inventoryRequestVO.getOldStatus()==null )) {
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_OLDSTATUS_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_OLDSTATUS_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				if(!isAdd && (inventoryRequestVO.getNewStatus()==null )){
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_NEWSTATUS_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_NEWSTATUS_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				if(!isAdd && (inventoryRequestVO.getRemarks() == null || inventoryRequestVO.getRemarks().isEmpty())){
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.REMARKS_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.REMARKS_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				if(!isAdd) {
					
					if(inventoryRequestVO.getInventoryNo()!=null && !inventoryRequestVO.getInventoryNo().isEmpty()) {
						boolean isChangeStatusAllowed = inventoryManagementSessionBeanLocal.isChangeStatusAllowed(inventoryRequestVO.getInventoryNo(),inventoryRequestVO.getSerialNumber());
						if(!isChangeStatusAllowed) {
							statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
							statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVALID_TRANSFERSTATUS_ALLOCATE));
							statusVO.setResponseMessage(InventoryMgtResponseCode.INVALID_TRANSFERSTATUS_ALLOCATE_MESSAGE);
							inventoryStatusVOs.add(statusVO);
							isAdd = true;
						}

					} else if(inventoryRequestVO.getSerialNumber()!=null && !inventoryRequestVO.getSerialNumber().isEmpty()) {
						boolean isChangeStatusAllowed = inventoryManagementSessionBeanLocal.isChangeStatusAllowedWithSerialNumber(inventoryRequestVO.getSerialNumber());
						if(!isChangeStatusAllowed) {
							statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
							statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVALID_SERIALNUMBER));
							statusVO.setResponseMessage(InventoryMgtResponseCode.INVALID_SERIALNUMBER_MESSAGE);
							inventoryStatusVOs.add(statusVO);
							isAdd = true;
						}
					}
					
				}
				//Added--start for to put the check of Central Warehouse
				if(!isAdd){
					Map<String,Object> fieldValueMap = new HashMap<String, Object>();
					fieldValueMap.put("inventoryNo", inventoryRequestVO.getInventoryNo());
					List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
					
						if(filterList!=null && !filterList.isEmpty()) {
							InventoryData data = (InventoryData) filterList.get(0);
							if(data.getWarehousedata().getWarehouseTypeData().getName().equals("Central")) {
								if(notAllowedForCentral.contains(WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getNewStatus()))) {
									statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
									statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUSCHANGE_FOR_CENTRAL));
									statusVO.setResponseMessage(InventoryMgtResponseCode.STATUSCHANGE_FOR_CENTRAL_MESSAGE);
									inventoryStatusVOs.add(statusVO);
									isAdd = true;
								}
							}
						
					}
				}
				//Added--end for to put the check of Central Warehouse
				if(!isAdd) {
					
					String oldStatus = WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getOldStatus());
					String newStatus = WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getNewStatus());
					boolean validate = inventoryManagementSessionBeanLocal.isValidInventoryChangeStatus(oldStatus, newStatus);
					//Added start  for to change inventory status from Available to Allocated
					if(inventoryRequestVO.getOldStatus()==InventoryStatusConstants.AVAILABLE){
						validate=true;
					}
					//Added end for to change inventory status from Available to Allocated

					if(!validate) {
						inventoryStatusVOs.add(new InventoryStatusVO(inventoryRequestVO.getInventoryNo(),InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUS_TRANSITION_NOTALLOWED), "Status change not from "+oldStatus+" to "+newStatus));
					} else {
						
						validRequestVO.add(inventoryRequestVO);
					}
				}
				
			}
		}
		Logger.logTrace(MODULE, "validRequestVO::"+validRequestVO);
		if(inventoryRequestVOs.size()==validRequestVO.size()) {
			if(validRequestVO!=null && !validRequestVO.isEmpty()) {
				List<InventoryStatusVO> statusVOs = inventoryManagementSessionBeanLocal.changeInventoryStatusForAllocate(validRequestVO,iblSession);
				if(statusVOs!=null && !statusVOs.isEmpty()) {
					inventoryStatusVOs.addAll(statusVOs);
				}
			}
		} else {
			Logger.logTrace(MODULE, "not equal");
		}
		
		
		
		return inventoryStatusVOs;

	}
	
	/**
	 * Allocate Inventory
	 * @author yash.kapasi
	 * @param {@link BookCPERequestData} requestData
	 * @param {@link IBLSession }
	 * @throws SearchBLException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public void allocateInventory(BookCPERequestData requestData,IBLSession iblSession) throws SearchBLException{
		
		try {
			List<ReserveAllocateRequestVO> inventoryList = requestData.getReserveAllocateRequestVO();
			
			List<InventoryRequestVO> inventoryRequestVOs = new ArrayList<InventoryRequestVO>();
			for(ReserveAllocateRequestVO reserveAllocateRequestVO : inventoryList)
			{
				Map<String,Object> fieldValueMap = new HashMap<String, Object>();
				
				String oldStatus = "";
				if(reserveAllocateRequestVO.getInventoryNo()!=null && !reserveAllocateRequestVO.getInventoryNo().isEmpty()) {
					fieldValueMap.put("inventoryNo", reserveAllocateRequestVO.getInventoryNo());
					List filterList = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
					if(filterList != null && !filterList.isEmpty()){
						InventoryData inventoryData = (InventoryData)filterList.get(0);
						oldStatus = inventoryData.getStatusData().getName();
						/**
						 * Commenting as Allocation should be allowed without reservation
						 */
						/*if(inventoryData.getInventoryStatusId() != (InventoryStatusConstants.RESERVED)){
							throw new SearchBLException(InventoryMgtResponseCode.INVENTORY_RESERVE_NOT_FOUND);
						}*/
					}else{
						throw new SearchBLException(InventoryMgtResponseCode.INVENTORYNO_NOT_FOUND);
					}
				} else if(reserveAllocateRequestVO.getSerialNumber()!=null && !reserveAllocateRequestVO.getSerialNumber().isEmpty()) {
					
					 List result = warehouseSessionBeanLocal.checkInventoryOnSerialNumber(reserveAllocateRequestVO.getSerialNumber());
					 
					 if(result!=null && result.size()==1) {
						 Logger.logTrace(MODULE, "Serial Number valid");
					 } else {
						 throw new SearchBLException(InventoryMgtResponseCode.INVALID_SERIALNUMBER);
					 }
				}
				InventoryRequestVO inventoryRequestVO = new InventoryRequestVO();
				inventoryRequestVO.setInventoryNo(reserveAllocateRequestVO.getInventoryNo());
				inventoryRequestVO.setSerialNumber(reserveAllocateRequestVO.getSerialNumber());
//				inventoryRequestVO.setOldStatus(InventoryStatusConstants.RESERVED_STATUS);
				inventoryRequestVO.setOldStatus(WSFacadeUtil.getStatusFromWSStatus(oldStatus));

				if(requestData.getIsResourceRecoverable().equals("Yes")) {
					inventoryRequestVO.setNewStatus(WSFacadeUtil.getStatusFromWSStatus(InventoryStatusConstants.ALLOCATED_STATUS));
				} else if(requestData.getIsResourceRecoverable().equals("No")) {
					inventoryRequestVO.setNewStatus(WSFacadeUtil.getStatusFromWSStatus(InventoryStatusConstants.DELIVERED_STATUS));
				}
				Logger.logTrace(MODULE, inventoryRequestVO+"");
				inventoryRequestVO.setRemarks("WS:BookCPE, Mark for Allocate");
				inventoryRequestVOs.add(inventoryRequestVO);
				
			}
			
			List<InventoryStatusVO> inventoryStatusVOs = changeInventoryStatusForAllocate(inventoryRequestVOs,iblSession);
			StringBuilder builder = new StringBuilder("");
			if(inventoryStatusVOs!=null && !inventoryStatusVOs.isEmpty()) {
				int countTotal = 0,failed = 0,passed = 0;
				for(InventoryStatusVO inventoryStatusVO : inventoryStatusVOs) {
					countTotal++;
					if(inventoryStatusVO.getResponseCode().startsWith("err")) {
						failed++;
						builder.append(inventoryStatusVO.getInventoryNumber()+",");
					} else {
						passed++;
					}
				}
				
				if(!builder.toString().isEmpty()) {
					 builder= new StringBuilder(builder.toString().substring(0, builder.toString().lastIndexOf(",")));
				}
				
				if((countTotal==failed) || (failed>0)) {
					
					throw new SearchBLException(InventoryMgtResponseCode.ALLOCATE_INVENTORIES_FAILED,InventoryMgtResponseCode.ALLOCATE_INVENTORIES_FAILED_MESSAGE+builder.toString());
				}
			}
			
			
			
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

	@Override
	public List<InventoryStatusResponseVO> releaseCPEResource(ReleaseCPERequestVO cpeRequestVO, IBLSession iblSession) throws UpdateBLException, SearchBLException{
		List<InventoryStatusResponseVO> inventoryStatusVOs = new ArrayList<InventoryStatusResponseVO>();
		List<InventoryRequestVO> validRequestVO = new ArrayList<InventoryRequestVO>();
		List<CPEInventoryVO> inventoryVos =new ArrayList<CPEInventoryVO>();
		inventoryVos =  cpeRequestVO.getInventoryVOs();
	try{
			
		for(CPEInventoryVO releaseCPEInventoryVO : inventoryVos) {
			boolean isAdd = false;
			InventoryStatusResponseVO statusVO = new InventoryStatusResponseVO();
			if(releaseCPEInventoryVO!=null) {
				if((releaseCPEInventoryVO.getInventoryNo()==null || releaseCPEInventoryVO.getInventoryNo().isEmpty()) ) {
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_NUMBER_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_NUMBER_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				if(!isAdd){
					Map<String,Object> fieldValueMap = new HashMap<String, Object>();
					fieldValueMap.put("inventoryNo", releaseCPEInventoryVO.getInventoryNo());
					List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
					if(filterList == null || filterList.isEmpty()){
						statusVO.setInventoryNumber(releaseCPEInventoryVO.getInventoryNo());
						statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVALID_INVENTORY_NO));
						statusVO.setResponseMessage(InventoryMgtResponseCode.INVALID_INVENTORY_NO_MESSAGE);
						inventoryStatusVOs.add(statusVO);
						isAdd = true;
					}if(!isAdd) {
							InventoryData data = (InventoryData) filterList.get(0);
							String oldStatus = data.getStatusData().getName();
							Logger.logTrace(MODULE,":::::::::oldStatus:::::::::"+oldStatus);
							if(!(InventoryStatusConstants.RESERVED_STATUS.equals(oldStatus)|| InventoryStatusConstants.ALLOCATED_STATUS.equals(oldStatus) || InventoryStatusConstants.DELIVERED_STATUS.equals(oldStatus))) {
								statusVO.setInventoryNumber(releaseCPEInventoryVO.getInventoryNo());
								statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUS_TRANSITION_NOTALLOWED_RELEASECPE));
								statusVO.setResponseMessage(InventoryMgtResponseCode.STATUS_TRANSITION_NOTALLOWED_RELEASECPE_MESSAGE);
								inventoryStatusVOs.add(statusVO);
								isAdd = true;
							}
							if(!isAdd && (data.getTransferInventoryStatus()!=null)) {
								statusVO.setInventoryNumber(releaseCPEInventoryVO.getInventoryNo());
								statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUS_TRANSITION_NOTALLOWED_FOR_INVENTORY_INTRANSFER));
								statusVO.setResponseMessage(InventoryMgtResponseCode.STATUS_TRANSITION_NOTALLOWED_FOR_INVENTORY_INTRANSFER_MESSAGE);
								inventoryStatusVOs.add(statusVO);
								isAdd = true;
							}
							if(!isAdd && data.getWarehousedata().getWarehouseTypeData().getName().equals("Central")) {
										if(InventoryStatusConstants.ALLOCATED_STATUS.equals(oldStatus) ||InventoryStatusConstants.DELIVERED_STATUS.equals(oldStatus)){
											statusVO.setInventoryNumber(releaseCPEInventoryVO.getInventoryNo());
											statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUSCHANGE_FOR_CENTRAL));
											statusVO.setResponseMessage(InventoryMgtResponseCode.STATUSCHANGE_FOR_CENTRAL_MESSAGE);
											inventoryStatusVOs.add(statusVO);
											isAdd = true;
									}
							}
							
					}

				}
				
		}
		

	}
		if(inventoryStatusVOs.isEmpty()){
			inventoryStatusVOs	= inventoryManagementSessionBeanLocal.releaseCPEResource(inventoryVos, iblSession);
		}
		else{
		int countTotal = 0,failed = 0,passed = 0;
		StringBuilder builder = new StringBuilder("");
		if(inventoryStatusVOs!=null && !inventoryStatusVOs.isEmpty()) {
			for(InventoryStatusResponseVO inventoryStatusVO : inventoryStatusVOs) {
				countTotal++;
				if(inventoryStatusVO.getResponseCode().startsWith("err")) {
					failed++;
					builder.append(inventoryStatusVO.getInventoryNumber()+":"+inventoryStatusVO.getResponseMessage()+"\n,");
				} else {
					passed++;
				}
			}
			if(!builder.toString().isEmpty()) {
				 builder= new StringBuilder(builder.toString().substring(0, builder.toString().lastIndexOf(",")));
			}
			
			if((countTotal==failed) || (failed>0)) {
				throw new SearchBLException(InventoryMgtResponseCode.INVENTORY_NUMBER_RELEASE_FAILED,InventoryMgtResponseCode.INVENTORY_NUMBER_RELEASE_FAILED_MESSAGE+builder.toString());
			}
		}
			
		}
		}catch (SearchBLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new SearchBLException(e.getErrorCode(),e.getErrorMessage());
		}
		return inventoryStatusVOs;
	}

	@Override
	public List<InventoryStatusResponseVO> markCPEAsFaultyWithOwnerChange(MarkCPEAsFaultyRequestVO mAsFaultyRequestVO, IBLSession blSession)throws SearchBLException ,UpdateBLException{
		// TODO Auto-generated method stub
		CPEResponseVO cpeResponseVO=new CPEResponseVO();
		List<InventoryRequestVO> inventoryRequestVOs=new ArrayList<InventoryRequestVO>();
		List<InventoryStatusResponseVO> inventoryStatusVOs = new ArrayList<InventoryStatusResponseVO>();
		List<InventoryRequestVO> validRequestVO = new ArrayList<InventoryRequestVO>();
		try{
			List<CPEInventoryVO> listInventoryVO = mAsFaultyRequestVO.getListCpeInventoryVOs();
			for(CPEInventoryVO inventoryVO:listInventoryVO){
				boolean isAdd = false;
				InventoryStatusResponseVO statusVO = new InventoryStatusResponseVO();
				if((inventoryVO.getInventoryNo()==null || inventoryVO.getInventoryNo().isEmpty()) ) {
					statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVENTORY_NUMBER_NOT_FOUND));
					statusVO.setResponseMessage(InventoryMgtResponseCode.INVENTORY_NUMBER_NOT_FOUND_MESSAGE);
					inventoryStatusVOs.add(statusVO);
					isAdd = true;
				}
				if(!isAdd){
					Map<String,Object> fieldValueMap = new HashMap<String, Object>();
					fieldValueMap.put("inventoryNo", inventoryVO.getInventoryNo());
					List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
					if(filterList == null || filterList.isEmpty()){
						statusVO.setInventoryNumber(inventoryVO.getInventoryNo());
						statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVALID_INVENTORY_NO));
						statusVO.setResponseMessage(InventoryMgtResponseCode.INVALID_INVENTORY_NO_MESSAGE);
						inventoryStatusVOs.add(statusVO);
						isAdd = true;
					}
				}
				if(!isAdd) {
					boolean isChangeStatusAllowed = inventoryManagementSessionBeanLocal.isChangeStatusAllowed(inventoryVO.getInventoryNo(),null);
					if(!isChangeStatusAllowed) {
						statusVO.setInventoryNumber(inventoryVO.getInventoryNo());
						statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.INVALID_TRANSFERSTATUS));
						statusVO.setResponseMessage(InventoryMgtResponseCode.INVALID_TRANSFERSTATUS_MESSAGE);
						inventoryStatusVOs.add(statusVO);
						isAdd = true;
					}
				}
				if(!isAdd){
					Map<String,Object> fieldValueMap = new HashMap<String, Object>();
					fieldValueMap.put("inventoryNo", inventoryVO.getInventoryNo());
					List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
					InventoryData inventoryData =(InventoryData)filterList.get(0);
					boolean validate = inventoryManagementSessionBeanLocal.isValidInventoryChangeStatus(inventoryData.getStatusData().getName(),InventoryStatusConstants.FAULTY_STATUS);
					if(!validate) {
						statusVO.setInventoryNumber(inventoryVO.getInventoryNo());
						statusVO.setResponseCode(InventoryMgtResponseCode.responceCodeToE2ECode(InventoryMgtResponseCode.STATUS_TRANSITION_NOTALLOWED));
						statusVO.setResponseMessage("Status change not allowed from "+inventoryData.getStatusData().getName()+" to "+InventoryStatusConstants.FAULTY_STATUS);
						inventoryStatusVOs.add(statusVO);
						} 
				}
				
			}
			if(inventoryStatusVOs!=null && !inventoryStatusVOs.isEmpty()){
				int countTotal = 0,failed = 0,passed = 0;
				StringBuilder builder = new StringBuilder("");
				if(inventoryStatusVOs!=null && !inventoryStatusVOs.isEmpty()) {
					for(InventoryStatusResponseVO inventoryStatusVO : inventoryStatusVOs) {
						countTotal++;
						if(inventoryStatusVO.getResponseCode().startsWith("err")) {
							failed++;
							builder.append(inventoryStatusVO.getInventoryNumber()+":"+inventoryStatusVO.getResponseMessage()+"\n,");
						} else {
							passed++;
						}
					}
					if(!builder.toString().isEmpty()) {
						 builder= new StringBuilder(builder.toString().substring(0, builder.toString().lastIndexOf(",")));
					}
					if((countTotal==failed) || (failed>0)) {
						throw new SearchBLException(InventoryMgtResponseCode.MARK_INVENTORY_AS_FAULTY_FAILED,InventoryMgtResponseCode.MARK_INVENTORY_AS_FAULTY_FAILED_MESSAGE+builder.toString());
					}
				}
			}
			else{
				Map<String,Object> fieldValueMap1 = new HashMap<String, Object>();
				fieldValueMap1.put("warehouseCode", mAsFaultyRequestVO.getWarehouseCode());
				List filterList1 = warehouseSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap1);
				WarehouseData whData = (WarehouseData)filterList1.get(0);
				Long wareHouseId = whData.getWarehouseId();
				inventoryStatusVOs	= inventoryManagementSessionBeanLocal.markCPEAsFaultyWithOwnerChange(listInventoryVO,mAsFaultyRequestVO.getWarehouseCode(),blSession);
			}
			
		}
		catch (SearchBLException e) {
		// TODO: handle exception
			e.printStackTrace();
			throw new SearchBLException(e.getErrorCode(),e.getErrorMessage());
	}catch (Exception e) {
		e.printStackTrace();
	}
		return inventoryStatusVOs;
	}
}
