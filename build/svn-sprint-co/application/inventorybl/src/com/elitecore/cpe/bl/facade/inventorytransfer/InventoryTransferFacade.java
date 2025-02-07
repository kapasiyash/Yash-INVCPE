package com.elitecore.cpe.bl.facade.inventorytransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.elitecore.cpe.bl.constants.inventorymgt.InventoryStatusConstants;
import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.data.system.audit.AuditSummaryDetail;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusLogData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.OrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderDetailData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.entity.inventory.transfer.InventoryTransferOrderStatus;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.NoSuchControllerException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementUtil;
import com.elitecore.cpe.bl.facade.master.item.ItemUtil;
import com.elitecore.cpe.bl.facade.system.audit.AuditDataConversionUtilities;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.session.inventorymgt.InventoryManagementSessionBeanLocal;
import com.elitecore.cpe.bl.session.inventorytransfer.InventoryTransferSessionBeanLocal;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorymgt.TransferInventorySummaryViewVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.CancelTransferOrderVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.CheckInventoryVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.PartialAcceptRejectTransferOrderVO.InventoryVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.ViewTransferInventoryDetailVO;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;
import com.sun.rowset.CachedRowSetImpl;


@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class InventoryTransferFacade extends BaseFacade implements InventoryTransferFacadeLocal,InventoryTransferFacadeRemote {

	private static final String MODULE = "INVENTORY-TRANSFER-FC";

	@EJB private InventoryTransferSessionBeanLocal transferSessionBeanLocal;
	@EJB private InventoryManagementSessionBeanLocal inventoryManagementSessionBeanLocal;
	
	
	/**
	 * Cancel Transfer Inventory
	 * @author yash.kapasi
	 * @param {@link CancelTransferOrderVO} transferOrderVO
	 * @param {@link IBLSession}  blSession
	 * @throws UpdateBLException
	 */
	@Override
	public void cancelTransferInventory(CancelTransferOrderVO transferOrderVO,
			IBLSession blSession) throws UpdateBLException {
	
		try {
		
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("transferOrderNo", transferOrderVO.getTarnsferNo());
			
			
		
			InventoryTransferOrderStatus transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_CANCELLED);
			
			List filterList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDER_DATA, fieldValueMap);
			if(filterList!=null && !filterList.isEmpty()) {
				TransferOrderData transferOrderData = (TransferOrderData) filterList.get(0);
				transferOrderData.setInventoryOrderStatusId(transferOrderStatus.getOrderStatusId());
				transferOrderData.setRemarks(transferOrderVO.getRemarks());
				
				List<Object> inventoryDatas = transferSessionBeanLocal.searchInventoryDataFromOrderNo(transferOrderData.getTransferOrderId());
				
				if(inventoryDatas!=null && !inventoryDatas.isEmpty()) {
					for(Object object : inventoryDatas) {
						InventoryData inventoryData = (InventoryData) object;
						
						
						fieldValueMap =new HashMap<String, Object>();
						fieldValueMap.put("transferOrderId",transferOrderData.getTransferOrderId() );
						fieldValueMap.put("inventoryNo",inventoryData.getInventoryNo() );
						filterList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDERDETAIL_DATA, fieldValueMap);
						if(filterList!=null && !filterList.isEmpty()) {
							TransferOrderDetailData detailData =  (TransferOrderDetailData) filterList.get(0);
							if(detailData.getPreviousSubStatus()!=null) {
								inventoryData.setInventorySubStatusId(detailData.getPreviousSubStatus());
							}
						}
						
						inventoryData.setTransferInventoryStatus(null);
						inventoryManagementSessionBeanLocal.updateInventory(inventoryData);
					}
				}
				
				
				transferSessionBeanLocal.updateTransferOrder(transferOrderData);
				
				
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.NAME,transferOrderData.getTransferOrderId());
				
				addToAuditDynamicMessage(AuditConstants.CANCEL_TRANSFER_ORDER, "Cancel Transfer Order",AuditConstants.CREATE_AUDIT_TYPE, mapAudit,null, blSession);
				
				
			} else {
				Logger.logTrace(MODULE, "No Transfer Order found with the Id::"+transferOrderVO.getTarnsferNo());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage());
		}
		
		
	}

	
	/**
	 * Get Inventory Details for rejected Inventory
	 * @author yash.kapasi
	 * @param {@link SearchTransferInventory} searchTransferInventory
	 * @return {@link InventoryUploadVO}  uploadVO
	 */
	@Override
	public InventoryUploadVO getInventoryDetailsForRejectedInventory(
			SearchTransferInventory searchTransferInventory) {
		
		StringBuilder builder = new StringBuilder();
		Logger.logTrace(MODULE, "In getInventoryDetailsForRejectedInventory ");
		try {
			Map<String, Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("transferOrderNo", searchTransferInventory.getOrderNo());
			
			List filterList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDER_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				TransferOrderData orderData = (TransferOrderData)filterList.get(0);
				searchTransferInventory.setOrderId(orderData.getTransferOrderId());
				
				List lst = prepareAttributePivotStr();
				Set<String> headerSet = (Set<String>)lst.get(0);
				String attributeNames = (String)lst.get(1);
				
				//prepare for header
				builder.append("SrNo").append(",")
				.append("OrderNo").append(",")
				.append("From Warehouse").append(",")
				.append("To Warehouse").append(",")
				.append("InventoryNo").append(",")
				.append("Status").append(",")
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
						builder.append(counter).append(",")
						.append(searchTransferInventory.getOrderNo()).append(",")
						.append(searchTransferInventory.getFromWarehouseName()).append(",")
						.append(searchTransferInventory.getToWarehouseName()).append(",")
						.append(inventoryDetailVO.getInventoryId()).append(",")
						.append(inventoryDetailVO.getStatus()).append(",")
						.append(inventoryDetailVO.getBatchId()).append(",");
						
						for(String key: headerSet){
							builder.append(inventoryDetailVO.getAttribute().get(key)).append(",");
						}
						
						builder.append(inventoryDetailVO.getTransferStatus()).append(",");
						builder.append("").append(",");
						builder.append("\n");
						counter++;
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
	
	
	private List<Object> prepareAttributePivotStr(){
		List<Object> list = new ArrayList<Object>();
		Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
		fieldValueMap.put("systemgenerated", "N");
		fieldValueMap.put("ATTRIBUTEREL", "Resource");
		String attributeNames="";
		Set<String> headerSet=new LinkedHashSet<String>();
		
		List filterList = inventoryManagementSessionBeanLocal.getFilterDataBy(EntityConstants.ATTRIBUTE_DATA, fieldValueMap);
		if(filterList != null && !filterList.isEmpty()){
			
			List<AttributeData> attributeDatas = (List<AttributeData>)filterList;
			StringBuilder attributeStrBuider = new StringBuilder(); 
			for(AttributeData attributeData : attributeDatas){
				headerSet.add(attributeData.getName());
//				attributeNames+=attributeData.getAttributeId()+" as "+attributeData.getName().replaceAll(" ","_")+","	;
				attributeStrBuider.append(attributeData.getAttributeId()).append(" as ").append(attributeData.getName().replaceAll(" ","_")).append(",");
			}
			attributeNames = attributeStrBuider.toString();
			attributeNames=attributeNames.substring(0,attributeNames.length()-1);
		}
		
		list.add(headerSet);
		list.add(attributeNames);
		
		return list;
	}

	
	/**
	 * Accept Reject Transfer Inventory
	 * @author yash.kapasi
	 * @param {@link SearchTransferInventory} searchTransferInventory
	 * @param {@link IBLSession}  iblSession
	 * @throws CreateBLException
	 */
	@Override
	public void acceptRejectedTransferInventory(SearchTransferInventory searchTransferInventory,
			IBLSession iblSession) throws CreateBLException {
		
		Logger.logTrace(MODULE, "inside acceptRejectedTransferInventory()");
		try {
			
			Map<String, Object> fieldValueStatusMap = new HashMap<String, Object>();
			fieldValueStatusMap.put("alias", InventoryStatusConstants.FAULTY_STATUS_ALIAS);
			List filterStatusList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORYSTATUS_DATA, fieldValueStatusMap);
			InventoryStatusData statusData = null;
			if(filterStatusList!=null && !filterStatusList.isEmpty()) {
				statusData =  (InventoryStatusData) filterStatusList.get(0);
			}
			
			Map<String, Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("transferOrderNo", searchTransferInventory.getOrderNo());
			List filterList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDER_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				
				TransferOrderData orderData = (TransferOrderData)filterList.get(0);
				// for accept all 
				
				List<InventoryDetailVO> inventoryList = new ArrayList<InventoryDetailVO>();
				Map<String, InventoryStatusLogData> map = new HashMap<String, InventoryStatusLogData>();
				
				int acceptedSize = 0;
				
				if(searchTransferInventory.getOrderStatus().equalsIgnoreCase("acceptall") ){
					fieldValueMap =new HashMap<String, Object>();
					fieldValueMap.put("transferOrderId",orderData.getTransferOrderId() );
					filterList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDERDETAIL_DATA, fieldValueMap);
					if(filterList != null && !filterList.isEmpty()){
						//make a list of inventories which can be transfer from 1 warehouse to another warehouse
						if(searchTransferInventory.getOrderStatus().equalsIgnoreCase("acceptall")){
							acceptedSize = filterList.size();
						}
						/*HashMap<String, Object> fieldValueMap1 = new HashMap<String, Object>();
						fieldValueMap1.put("alias", InventoryStatusConstants.SUBSTATUS_ACCEPTED);
						List filterList1 = transferSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORYSUBSTATUS_DATA, fieldValueMap1);
						InventorySubStatusData  subStatusData = (InventorySubStatusData) filterList1.get(0);*/
						
						for(Object obj : filterList){
							TransferOrderDetailData orderDetailData =  (TransferOrderDetailData)obj;
//							orderDetailData.setPreviousSubStatus(subStatusData.getInventorySubStatusId());
							
							if(orderDetailData.getTransferStatus().equals(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_REJECTED)){
								
								InventoryDetailVO inventoryDetailVO = new InventoryDetailVO();
								inventoryDetailVO.setInventoryId(orderDetailData.getInventoryNo());
								inventoryDetailVO.setStatusId(statusData.getInventoryStatusId().intValue());
								inventoryDetailVO.setTransferStatus(null);
								inventoryDetailVO.setRemark(searchTransferInventory.getRemark());
								
								//Reverting previous Substatus
								if(orderDetailData.getPreviousSubStatus()!=null) {
									inventoryDetailVO.setSubStatusId(orderDetailData.getPreviousSubStatus().intValue());
								}
								
								inventoryList.add(inventoryDetailVO);
								
								InventoryStatusLogData inventoryStatusLogData = InventoryManagementUtil.prepareInventoryStatusLogData(null,
										InventoryStatusConstants.AVAILABLE, statusData.getInventoryStatusId().intValue(), 
										InventoryStatusConstants.AVAILABLE_STATUS_ALIAS, statusData.getAlias(), searchTransferInventory.getRemark(),iblSession);
								map.put(orderDetailData.getInventoryNo(), inventoryStatusLogData);
							}
						}
						
					}
				}else{
					
					
					// call for partial accept/ reject
					String[] readerData = new String(searchTransferInventory.getUploadbyteData()).split("\n") ;
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
		        			
		        			if(!line.contains("InventoryNo") || !line.contains("Transfer Status") || !line.contains("Remark") || !line.contains("Status")){
		        				throw new CreateBLException("Invalid Header Titles or Headers should contain InventoryNo and Status and Transfer Status and Remark");
		        			}
		        		}else{
		        			
		        			String[] rowValues = line.split(",");
		        			Logger.logInfo(MODULE, "column size :"+rowValues.length + " @ "+index+" row");
		        			
		        			InventoryDetailVO inventoryDetailVO = new InventoryDetailVO();
		        			for(int no = 0;no<rowValues.length;no++){
		        				String headerName = columnNoToNameMap.get(no);
		        				if(headerName.equalsIgnoreCase("InventoryNo")){
		        					inventoryDetailVO.setInventoryId(rowValues[no]);
		        				}else if(headerName.equalsIgnoreCase("Transfer Status")){
		        					if(rowValues[no].equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_REJECTED)){
		        						inventoryDetailVO.setTransferStatus(null);
		        						acceptedSize++;
		        					}else{
		        						throw new CreateBLException("Invalid Value for Transfer Status");
		        					}
		        				}else if(headerName.equalsIgnoreCase("Status")){
		        					
		        					if(rowValues[no].equalsIgnoreCase(InventoryStatusConstants.FAULTY_STATUS)) {
		        						inventoryDetailVO.setStatusId(statusData.getInventoryStatusId().intValue());
		        					} else if(rowValues[no].equalsIgnoreCase(InventoryStatusConstants.AVAILABLE_STATUS)) {
		        						Logger.logTrace(MODULE, "Not processing this record as status is blank");
		        					} else {
		        						throw new CreateBLException("Status should be either Faulty or Available");
		        					}
		        					
		        				}else if(headerName.equalsIgnoreCase("Remark")){
		        					inventoryDetailVO.setRemark(rowValues[no]);
		        				}
		        			}	
		        			
		        			if(inventoryDetailVO.getInventoryId() != null  ){
		        				
		        				fieldValueMap =new HashMap<String, Object>();
		    					fieldValueMap.put("transferOrderId",orderData.getTransferOrderId() );
		    					fieldValueMap.put("inventoryNo",inventoryDetailVO.getInventoryId() );
		    					filterList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDERDETAIL_DATA, fieldValueMap);
		    					if(filterList!=null && !filterList.isEmpty()) {
		    						TransferOrderDetailData detailData =  (TransferOrderDetailData) filterList.get(0);
		    						if(detailData.getPreviousSubStatus()!=null) {
		    							inventoryDetailVO.setSubStatusId(detailData.getPreviousSubStatus().intValue());
		    						}
		    					}
		    					
	    						inventoryList.add(inventoryDetailVO);
	    						if(inventoryDetailVO.getStatusId()!=0) {
	    							InventoryStatusLogData inventoryStatusLogData = InventoryManagementUtil.prepareInventoryStatusLogData(null,
	    									InventoryStatusConstants.AVAILABLE, statusData.getInventoryStatusId().intValue(), 
	    									InventoryStatusConstants.AVAILABLE_STATUS_ALIAS, statusData.getAlias(), searchTransferInventory.getRemark(),iblSession);
	    							map.put(inventoryDetailVO.getInventoryId(), inventoryStatusLogData);
	    						}
	    					}
		        		}
		        		
		        		index++;
		        	}
					
				}
				
				
				
				// got final list of inventories which can be transfer
				transferSessionBeanLocal.changeInventoryStatusAfterAcceptRejected(inventoryList,map);
				
				
				
				
				Map<String, Object> fieldValueNotifyMap = new HashMap<String, Object>();
				fieldValueNotifyMap.put("name", searchTransferInventory.getFromWarehouseName());
				List filterNotifyList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueNotifyMap);
				WarehouseData wareHouseData = null;
				if(filterNotifyList!=null && !filterNotifyList.isEmpty()) {
					wareHouseData =  (WarehouseData) filterNotifyList.get(0);
				}
				
				fieldValueNotifyMap = new HashMap<String, Object>();
				fieldValueNotifyMap.put("name", searchTransferInventory.getToWarehouseName());
				List filterNotifyList2 = transferSessionBeanLocal.getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueNotifyMap);
				WarehouseData wareHouseData2 = null;
				if(filterNotifyList2!=null && !filterNotifyList2.isEmpty()) {
					wareHouseData2 =  (WarehouseData) filterNotifyList2.get(0);
				}
				
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
				
				System.out.println("---------------->"+searchTransferInventory);
				
				//Notification Data
				NotificationData data = ItemUtil.prepareNotificationDataOnTransferAcceptReject(searchTransferInventory,wareHouseData,wareHouseData2,inventoryData,acceptedSize);
				sendNotification(data);
				
				
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.NAME,searchTransferInventory.getOrderNo());
				
				addToAuditDynamicMessage(AuditConstants.ACCEPTREJECT_TRANSFER_ORDER, "Accept rejected Transfer Order",AuditConstants.CREATE_AUDIT_TYPE, mapAudit,null, iblSession);
				
				
				// Audit entry
				/*for(InventoryDetailVO  inventoryDetailVO:  inventoryList){
					Map<String,Object> mapAudit = new HashMap<String, Object>();
					mapAudit.put(AuditTagConstant.INVENTORYNO,inventoryDetailVO.getInventoryId());
					mapAudit.put(AuditTagConstant.STATUS,inventoryDetailVO.getTransferStatus());
					mapAudit.put(AuditTagConstant.FROMWAREHOUSE,orderData.getFromWarehouseData().getName());
					mapAudit.put(AuditTagConstant.TOWAREHOUSE,orderData.getToWarehouseData().getName());
					
					addToAuditDynamicMessage(AuditConstants.TRANSFER_INVENTORY_SUMMARY, "Transfer Inventory Summary",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, iblSession);
				}*/
				
			}
			
		} catch (CreateBLException e) {
			Logger.logTrace(MODULE, "Inside CreateBLException");
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			
			if(!getSessionContext().getRollbackOnly())  getSessionContext().setRollbackOnly();
			
			throw new CreateBLException("Sorry!!! Accepting Rejected Inventory Failed");
			
		}
		
	}
	
	
	/**
	 * Cancel Place Order
	 * @author yash.kapasi
	 * @param {@link CancelTransferOrderVO} transferOrderVO
	 * @param {@link IBLSession}  iblSession
	 * @throws UpdateBLException
	 */
	@Override
	public void cancelPlaceOrder(CancelTransferOrderVO transferOrderVO,
			IBLSession blSession) throws UpdateBLException {
		Logger.logTrace(MODULE, "Inside cancelPlaceOrder() with::"+transferOrderVO);
		try {
		
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("orderNo", transferOrderVO.getTarnsferNo());
			
			PlaceOrderVO placeOrderVO=new PlaceOrderVO();
			placeOrderVO.setOrderNo(transferOrderVO.getTarnsferNo());
			InventoryTransferOrderStatus transferOrderStatus = transferSessionBeanLocal.searchOrderStatusByAlias(InventoryStatusConstants.ORDER_CANCELLED);
			placeOrderVO.setStatus(transferOrderStatus.getName());
			placeOrderVO.setRemark(transferOrderVO.getRemarks());
			
			List filterList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.ORDER_DATA, fieldValueMap);
			if(filterList!=null && !filterList.isEmpty()) {
				OrderData orderData = (OrderData) filterList.get(0);
				
				AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.preparePlaceOrderCancelAudit(orderData,placeOrderVO );
				
				orderData.setOrderStatusId(transferOrderStatus.getOrderStatusId());
				orderData.setRemarks(transferOrderVO.getRemarks());
				orderData.setUpdatedate(getCurrentTimestamp());
				orderData.setUpdatedby(blSession.getSessionUserId());
				
				
				
				
				transferSessionBeanLocal.updatePlaceOrder(orderData);
				
				// Audit entry
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.TRANSFER_ORDER_NUMBER,orderData.getOrderNo());
				mapAudit.put(AuditTagConstant.FROMWAREHOUSE,orderData.getFromWarehouseData().getName());
				mapAudit.put(AuditTagConstant.TOWAREHOUSE,orderData.getToWarehouseData().getName());
				addToAuditDynamicMessage(AuditConstants.CANCEL_PLACE_ORDER, "Cancel Place Order",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), blSession);
			} else {
				Logger.logTrace(MODULE, "No Place Order found with the Id::"+transferOrderVO.getTarnsferNo());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage());
		}
		
		
	}

	
	/**
	 * Search Transfer Inventory Order Detail
	 * @author yash.kapasi
	 * @param {@link SearchTransferInventory} searchTransferInventory
	 * @param {@link IBLSession}  iblSession
	 * @return {@link List}<{@link TransferInventorySummaryViewVO}> inventorySummaryViewVOs
	 * @throws SearchBLException
	 */
	@Override
	public List<TransferInventorySummaryViewVO> searchTransferInventoryOrderDetail(SearchTransferInventory searchTransferInventory,
			IBLSession blSession) throws SearchBLException {
		
		
		List<TransferInventorySummaryViewVO> inventorySummaryViewVOs = transferSessionBeanLocal.searchTransferInventoryOrderDetail(searchTransferInventory);
		
		return inventorySummaryViewVOs;
	}

	
	
	/**
	 * Search Transfer Inventory Summary
	 * @author yash.kapasi
	 * @param {@link String} orderNumber
	 * @return {@link ViewTransferInventoryDetailVO} detailVO
	 * @throws SearchBLException
	 */
	@Override
	public ViewTransferInventoryDetailVO searchTransferInventorySummary(
			String orderNumber) throws SearchBLException {
		
		Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
		fieldValueMap.put("transferOrderNo", orderNumber);
		
		ViewTransferInventoryDetailVO detailVO = null;
		List filterList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.TRANSFERORDER_DATA, fieldValueMap);
		if(filterList!=null && !filterList.isEmpty()) {
			TransferOrderData transferOrderData = (TransferOrderData) filterList.get(0);
			if(transferOrderData!=null ) {
				detailVO = new ViewTransferInventoryDetailVO();
				detailVO.setCreateDate(transferOrderData.getCreatedate());
				detailVO.setLastModifiedDate(transferOrderData.getUpdatedate());
				detailVO.setFromWarehouseName(transferOrderData.getFromWarehouseData().getName());
				detailVO.setToWarehouseName(transferOrderData.getToWarehouseData().getName());
				detailVO.setOrderNo(transferOrderData.getTransferOrderNo());
				detailVO.setRemarks(transferOrderData.getRemarks());
				detailVO.setOrderStatus(transferOrderData.getTransferOrderStatus().getName());
				
				try{
					detailVO.setCreatedBy(UserFactory.findUserById(transferOrderData.getCreatedby()).getName());
					detailVO.setLastModifiedBy(UserFactory.findUserById(transferOrderData.getUpdatedby()).getName());
				} catch (SearchBLException e) {
					e.printStackTrace();
				} catch (NoSuchControllerException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return detailVO;
	}


	@Override
	public InventoryVO checkInventoryForTransfer(String inventoryNumber,
			String orderNumber) throws SearchBLException {
		
		InventoryVO checkInventoryVO = new InventoryVO();
		List<TransferOrderDetailData> transferOrderDetailDatas = transferSessionBeanLocal.checkInventoryForTransfer(inventoryNumber,orderNumber);
		if(transferOrderDetailDatas!=null && !transferOrderDetailDatas.isEmpty()) {
			TransferOrderDetailData transferOrderDetailData=transferOrderDetailDatas.get(0);
			
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("inventoryNo", transferOrderDetailData.getInventoryNo());
			
			List<InventoryData> filterList = transferSessionBeanLocal.getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
			if(filterList!=null && !filterList.isEmpty()) {
				InventoryData  inventoryData = filterList.get(0);
				checkInventoryVO.setWarehouseName(inventoryData.getWarehousedata().getName());
				checkInventoryVO.setResourceType(inventoryData.getItemData().getResourceType().getName());
				if(inventoryData.getItemData().getResourceSubTypeData()!=null) {
					checkInventoryVO.setResourceSubtype(inventoryData.getItemData().getResourceSubTypeData().getName());
				}
			}
			
			checkInventoryVO.setInventoryNo(transferOrderDetailData.getInventoryNo());
			checkInventoryVO.setBatchNumber(transferOrderDetailData.getBatchNo());
			checkInventoryVO.setResponseCode(0L);
			checkInventoryVO.setResponseMessage("Added");
		} else {
			checkInventoryVO.setResponseCode(-1L);
			checkInventoryVO.setResponseMessage("Can not add this Inventory as Not present in Transfer");
		}
		
		return checkInventoryVO;
	}
	
	
}

