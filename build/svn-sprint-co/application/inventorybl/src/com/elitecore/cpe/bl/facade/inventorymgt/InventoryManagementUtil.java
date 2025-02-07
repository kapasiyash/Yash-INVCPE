package com.elitecore.cpe.bl.facade.inventorymgt;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.ejb.EJB;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.rowset.CachedRowSet;

import oracle.sql.TIMESTAMP;






import com.sun.rowset.CachedRowSetImpl;
import com.elitecore.cpe.bl.constants.inventorymgt.InventoryStatusConstants;
import com.elitecore.cpe.bl.constants.notification.NotificationConstants;
import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.BatchData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.BatchSummaryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryReserveData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryReserveDetailData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusLogData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.OrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderDetailData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.WarehouseInventoryStatusData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.WarehouseInventoryStatusHistoryData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeTransData;
import com.elitecore.cpe.bl.entity.inventory.master.ConfigureThresholdData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.exception.NoSuchControllerException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.facade.master.warehouse.WarehouseUtil;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.session.system.internal.SystemInternalSessionBeanLocal;
import com.elitecore.cpe.bl.vo.inventorymgt.BatchSummaryVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryBatchViewVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryStatusLogVO;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderNotificationEmailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.bl.vo.inventorymgt.ThresholdNotificationEmailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.WarehouseInventoryStatusVO;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.bl.vo.master.ThresholdStatusVO;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.system.user.UserVO;
import com.elitecore.cpe.bl.ws.data.input.response.InventoryDetailsResponseData;
import com.elitecore.cpe.bl.ws.data.input.vo.InventoryAttributeVO;
import com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.CSVDataExporter;
import com.elitecore.cpe.util.logger.Logger;


public class InventoryManagementUtil {

	
	public static final String strModule="InventoryManagementUtil";
	@EJB private SystemInternalSessionBeanLocal systemInternalSessionBeanLocal;
	
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}

	public static List<InventoryDetailVO> getInventoryDetailVO(List<Object> data) {
		Logger.logTrace(strModule, "Inside getInventoryDetailVO method");
		List<InventoryDetailVO> inventoryDetailVOs=null;
		if(data != null && data.size()>0){
		 inventoryDetailVOs  = new ArrayList<InventoryDetailVO>();
		}
			
		
		
	try{	
		
		if (data != null && (data.size()>0) ) {
			Logger.logTrace(strModule, "Inside getInventoryDetailVO method DATA Size:"+data.size());
			String temp="";
			InventoryDetailVO inventoryDetailVO = new InventoryDetailVO();
			for (int cnt = 0; cnt < data.size(); cnt++) {
				
				Object[] obj = (Object[]) data.get(cnt);
				//System.out.println("In First for loop Util:"+obj);
				String temp2=obj[1].toString();
				
				if (!temp.equals(temp2)) 
				{
					if(inventoryDetailVO.getInventoryId()!=null)
					{
						inventoryDetailVOs.add(inventoryDetailVO);
						inventoryDetailVO = new InventoryDetailVO();
					}
					
					for (int cnt2 = 0; cnt2 < obj.length; cnt2++) 
					{
						if (cnt2 == 0) {
							Logger.logTrace(strModule,"Status 0:"+obj[cnt2].toString());
							inventoryDetailVO.setBatchId(obj[cnt2].toString());
						} else if (cnt2 == 1) {
							Logger.logTrace(strModule,"Status 1:"+obj[cnt2].toString());
							inventoryDetailVO.setInventoryId(obj[cnt2].toString());
							
						} else if (cnt2 == 2) {
							Logger.logTrace(strModule,"Status 2:"+obj[cnt2].toString());
							inventoryDetailVO.setStatus(obj[cnt2].toString());
						}  
						else if(cnt2 == 3){
							
							Logger.logTrace(strModule,"Size of obeject array:"+obj.length);
							if(obj[cnt2]!=null){
								Logger.logTrace(strModule,"Status 3:"+obj[cnt2].toString());
							inventoryDetailVO.setDistributorNumber(obj[cnt2].toString());
							}
							
						}else if(cnt2 == 4){
							if(obj[cnt2]!=null){
								Logger.logTrace(strModule,"Status 4:"+obj[cnt2].toString());
							inventoryDetailVO.setCustomerRefNumber(obj[cnt2].toString());
							}
							
						}
						else if(cnt2 == 5){
							
							if(obj[cnt2]!=null){
								Logger.logTrace(strModule,"Status 5:"+obj[cnt2].toString());
							inventoryDetailVO.setOrderNumber(obj[cnt2].toString());
							}
						}else if(cnt2 == 6){
							
							if(obj[cnt2]!=null){
								Logger.logTrace(strModule,"Status 6:"+obj[cnt2].toString());
							inventoryDetailVO.setWareHouseName(obj[cnt2].toString());
							}
						}else if(cnt2 == 7){
							
							if(obj[cnt2]!=null){
								Logger.logTrace(strModule,"Status 7:"+obj[cnt2].toString());
							inventoryDetailVO.setWarehouseId(Long.parseLong(obj[cnt2].toString()));
							}
						}else if(cnt2 == 8){
							
							if(obj[cnt2]!=null){
								Logger.logTrace(strModule,"Status 8:"+obj[cnt2].toString());
							inventoryDetailVO.setResourceType(obj[cnt2].toString());
							}
						}else if(cnt2 == 9){
							
							if(obj[cnt2]!=null){
								Logger.logTrace(strModule,"Status 9:"+obj[cnt2].toString());
							inventoryDetailVO.setResourceSubType(obj[cnt2].toString());
							}
						}else if(cnt2 == 10){
							
							if(obj[cnt2]!=null){
								Logger.logTrace(strModule,"Status 10:"+obj[cnt2].toString());
							inventoryDetailVO.setCreateDate((Timestamp)obj[cnt2]);
							}
						}else if(cnt2 == 11){
							
							if(obj[cnt2]!=null){
								Logger.logTrace(strModule,"Status 11:"+obj[cnt2].toString());
							inventoryDetailVO.setCreatedby(UserFactory.findUserById(obj[cnt2].toString()).getName());
							}
						}else if(cnt2 == 12){
							
							if(obj[cnt2]!=null){
								Logger.logTrace(strModule,"Status 12:"+obj[cnt2].toString());
							inventoryDetailVO.setUpdatedDate((Timestamp)obj[cnt2]);
							}else{
								inventoryDetailVO.setUpdatedDate((Timestamp)obj[cnt2]);
							}
						}else if(cnt2 == 13){
							
							if(obj[cnt2]!=null){
								Logger.logTrace(strModule,"Status 13:"+obj[cnt2].toString());
								inventoryDetailVO.setUpdatedby(UserFactory.findUserById(obj[cnt2].toString()).getName());
							}else{
								inventoryDetailVO.setUpdatedby("-");
							}
						}else if(cnt2 == 14){
							
							if(obj[cnt2]!=null){
							Logger.logTrace(strModule,"Status 14:"+obj[cnt2].toString());
							inventoryDetailVO.setTransferStatus(obj[cnt2].toString());
							}
						}/*else if(cnt2 == 15){
							Logger.logTrace(strModule,"Status 15:"+obj[cnt2]);
							if(obj[cnt2]!=null){
								if(obj[cnt2].toString().equals("Y")){
									inventoryDetailVO.setAccepted(obj[cnt2].toString().charAt(0));
								}
							}
						}
						else if(cnt2 == 16){
							Logger.logTrace(strModule,"Status 16:"+obj[cnt2]);
							if(obj[cnt2]!=null){
								if(obj[cnt2].toString().equals("Y")){
									inventoryDetailVO.setRefurbished(obj[cnt2].toString().charAt(0));
								}
							}
						}else if(cnt2 == 17){
							Logger.logTrace(strModule,"Status 17:"+obj[cnt2]);
							if(obj[cnt2]!=null){
								if(obj[cnt2].toString().equals("Y")){
									inventoryDetailVO.setStandby(obj[cnt2].toString().charAt(0));
								}
							}
						}else if(cnt2 == 18){
							Logger.logTrace(strModule,"Status 18:"+obj[cnt2]);
							if(obj[cnt2]!=null){
								if(obj[cnt2].toString().equals("Y")){
									inventoryDetailVO.setNewSubStatus(obj[cnt2].toString().charAt(0));
								}
							}
						}*/ else if(cnt2 == 15){
							Logger.logTrace(strModule,"Status 15:"+obj[cnt2]);
							if(obj[cnt2]!=null){
								inventoryDetailVO.setExternalBatchNumber(obj[cnt2].toString());
							}
						} else if(cnt2 == 16){
							Logger.logTrace(strModule,"Status 16:"+obj[cnt2]);
							if(obj[cnt2]!=null){
								inventoryDetailVO.setResourceName(obj[cnt2].toString());
							}
						} else if(cnt2 == 17){
							Logger.logTrace(strModule,"Status 17:"+obj[cnt2]);
							if(obj[cnt2]!=null){
								inventoryDetailVO.setResourceNumber(obj[cnt2].toString());
							}
						} else if(cnt2 == 18){
							Logger.logTrace(strModule,"Status 18:"+obj[cnt2]);
							if(obj[cnt2]!=null){
								inventoryDetailVO.setResourceNo(Long.parseLong(obj[cnt2].toString()));
							}
						}
						//Added By Rinkal--start
						 else if(cnt2 == 19){
								Logger.logTrace(strModule,"Status 19:"+obj[cnt2]);
								if(obj[cnt2]!=null){
									inventoryDetailVO.setSubStatus((obj[cnt2].toString()));
								}
						 }
						//Added By Rinkal--end
						else {
							Logger.logTrace(strModule,"Status counter ::"+cnt2);
							Logger.logTrace(strModule,"Status 20:"+obj[cnt2].toString());
							temp = inventoryDetailVO.getInventoryId();
							HashMap<String, String> attributeMap = new HashMap();
							attributeMap.put(obj[cnt2].toString(),obj[++cnt2].toString());
							Logger.logTrace(strModule,"Attribute map:"+attributeMap);
						//	attributeList.add(obj[cnt2].toString());
							inventoryDetailVO.setAttribute(attributeMap);
							
						}
					}
					
				}
				else 
				{
						inventoryDetailVO.getAttribute().put(obj[20].toString(),obj[21].toString());
				}
			}
			inventoryDetailVOs.add(inventoryDetailVO);

		}
	}catch (SearchBLException e) {
		e.printStackTrace();
	} catch (NoSuchControllerException e) {
		e.printStackTrace();
	}
		return inventoryDetailVOs;
	}
	
		
	public static List<InventoryBatchViewVO> getInventoryBatchViewVO(List<Object> data){
		List<InventoryBatchViewVO> inventoryBatchViewVOs=null;
		if(data !=null && data.size()>0){
			inventoryBatchViewVOs  = new ArrayList<InventoryBatchViewVO>();
			}
		if (data != null && (data.size()>0) ) {
			
			for (int cnt = 0; cnt < data.size(); cnt++) {
				InventoryBatchViewVO inventoryBatchViewVO=new InventoryBatchViewVO();
				Object[] obj = (Object[]) data.get(cnt);
				inventoryBatchViewVO.setBatchNumber(obj[0].toString());
				inventoryBatchViewVO.setValidcount(new BigDecimal(obj[1].toString()).longValue());
				inventoryBatchViewVOs.add(inventoryBatchViewVO);
			}
		}
		return inventoryBatchViewVOs;
		
	}
	
	public static List<InventoryBatchViewVO> prepareInventoryBatchViewVO(List<BatchData> data){
		 List<InventoryBatchViewVO> inventoryBatchViewVOs =  new ArrayList<InventoryBatchViewVO>();
		if(data != null && !data.isEmpty()){
			
			for(BatchData batchData : data){
				InventoryBatchViewVO inventoryBatchViewVO=new InventoryBatchViewVO();
				inventoryBatchViewVO.setBatchNumber(batchData.getBatchNo());
				inventoryBatchViewVO.setValidcount(batchData.getTotalValidInventory());
				inventoryBatchViewVO.setInvalidcount(batchData.getTotalInvalidInventory());
				
				inventoryBatchViewVOs.add(inventoryBatchViewVO);
			}
			
		}
		
		return inventoryBatchViewVOs;
	}
	public static InventoryStatusLogData prepareInventoryStatusLogData(Long inventoryId,int oldStatusId,int newStatusId,String oldStatusName,String newStatusName,String remark,IBLSession iblSession){
		InventoryStatusLogData inventoryStatusLogData = new InventoryStatusLogData();
		inventoryStatusLogData.setInventoryId(inventoryId);
		inventoryStatusLogData.setOldStatusId(oldStatusId);
		inventoryStatusLogData.setNewStatusId(newStatusId);
		inventoryStatusLogData.setOldStatusName(oldStatusName);
		inventoryStatusLogData.setNewStatusName(newStatusName);
		inventoryStatusLogData.setRemark(remark);
		inventoryStatusLogData.setStatusChangedDate(WarehouseUtil.getCurrentTimestamp());
		inventoryStatusLogData.setStaffId(iblSession.getSessionUserId());
		
		return inventoryStatusLogData;
	}
	
	
	public static void getUploadInventoryVO(String path,CachedRowSetImpl cachedRowSet,Set <String> headerSet){
		Logger.logTrace(strModule, "In side getUploadInventoryVO() Path:"+path);
		Logger.logTrace(strModule, "In side getUploadInventoryVO() Path:"+path);
		Logger.logTrace(strModule, "In side getUploadInventoryVO() headerSet:"+headerSet);
		CSVDataExporter exporter=null;
		long time = Calendar.getInstance().getTimeInMillis();
	
	try{
		if(path == null || cachedRowSet == null || headerSet == null){
			throw new Exception("path or cachedRowSet or headerSet getting null");
		}
		
		CachedRowSet rs=cachedRowSet;
		String fullpath=null;
		int count=1;
		if(!headerSet.contains("Remarks")){
			 fullpath= path + "/Success";
			createDirectory(fullpath);
			exporter = new CSVDataExporter(fullpath,  "Success_"+time +".csv");
		}else{
			 fullpath= path + "/Failed";
			createDirectory(fullpath);
			exporter = new CSVDataExporter(fullpath,  "Fail_"+time +".csv");
		}
		
		 
		 List<String> list=new ArrayList<String>(headerSet);
		exporter.append(list);
		Logger.logTrace(strModule, "After header list send to print:");
		while(rs.next()){
				list.clear();
				list.add(String.valueOf(count++));
				for (String column : headerSet) {
					Logger.logTrace(strModule, "column:"+column);
					if (!column.equalsIgnoreCase("Sr.No")) {
						Logger.logTrace(strModule, "column value from DB:"+rs.getString(column));
						list.add((rs.getString(column)!=null)?rs.getString(column):"");
					}
					
			 }
				exporter.append(list);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally{
		if(exporter != null)  exporter.close();
	}
		
	}
	
	public static void createDirectory(String dirLocation) {
		System.out.println(" In Method :" + "createDirectory");
		System.out.println("Creating Directory..." + dirLocation);
		try {
			File file = new File(dirLocation);

			if (!file.exists()) {

				if (file.mkdir()) {
					System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}

			} else {
				System.out.println("Directory Already Exist:" + dirLocation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static WarehouseInventoryStatusVO getWarehouseInventoryStatusVO(WarehouseInventoryStatusData warehouseInventoryStatusData){
		
		WarehouseInventoryStatusVO warehouseInventoryStatusVO = null;
		if(warehouseInventoryStatusData != null){
			warehouseInventoryStatusVO = new WarehouseInventoryStatusVO();
			
			warehouseInventoryStatusVO.setAvailable(warehouseInventoryStatusData.getAvailable()); 
			warehouseInventoryStatusVO.setDelivered(warehouseInventoryStatusData.getDelivered());
			warehouseInventoryStatusVO.setFaulty(warehouseInventoryStatusData.getFaulty());
			warehouseInventoryStatusVO.setInUse(warehouseInventoryStatusData.getInUse());
			warehouseInventoryStatusVO.setReleased(warehouseInventoryStatusData.getReleased());
			warehouseInventoryStatusVO.setRepaired(warehouseInventoryStatusData.getRepaired());
			warehouseInventoryStatusVO.setReserved(warehouseInventoryStatusData.getReserved());
			warehouseInventoryStatusVO.setScrapped(warehouseInventoryStatusData.getScrapped());
			warehouseInventoryStatusVO.setVoided(warehouseInventoryStatusData.getVoided());
		}
		
		return warehouseInventoryStatusVO;
	}
	
	public static WarehouseInventoryStatusHistoryData prepareWarehouseInventoryStatusHistoryData(WarehouseInventoryStatusData warehouseInventoryStatusData,WarehouseData fromWHdata,WarehouseData toWHData,ItemData resource,Long numberCount,String remark,IBLSession iblSession){
		
		WarehouseInventoryStatusHistoryData warehouseInventoryStatusHistoryData = new WarehouseInventoryStatusHistoryData();
		warehouseInventoryStatusHistoryData.setWarehouseInventoryStatus(warehouseInventoryStatusData);
		warehouseInventoryStatusHistoryData.setFromWarehouse(fromWHdata);
		warehouseInventoryStatusHistoryData.setToWarehouse(toWHData);
		warehouseInventoryStatusHistoryData.setResource(resource);
		warehouseInventoryStatusHistoryData.setNumbercount(numberCount);
		warehouseInventoryStatusHistoryData.setRemark(remark);
		warehouseInventoryStatusHistoryData.setUpdatedby(iblSession.getSessionUserId());
		warehouseInventoryStatusHistoryData.setUpdatedate(getCurrentTimestamp());
		return warehouseInventoryStatusHistoryData;
		
	}

	public static InventoryDetailsResponseData getInventoryDetailsResponseData(
			CachedRowSetImpl cachedRowSetImpl,Set<String> headerSet) {
		InventoryDetailsResponseData responseData = new InventoryDetailsResponseData();
		
		try {
			CachedRowSet rs=cachedRowSetImpl;
			
			List<com.elitecore.cpe.bl.ws.data.input.vo.InventoryDetailVO> inventoryDetailVO = new ArrayList<com.elitecore.cpe.bl.ws.data.input.vo.InventoryDetailVO>();
			Set<String> wareHouse = new HashSet<String>();
			boolean isAdded = false;
			while(rs.next()) {
				if(!wareHouse.contains(rs.getString("WAREHOUSE"))) {
					com.elitecore.cpe.bl.ws.data.input.vo.InventoryDetailVO detailVO = new com.elitecore.cpe.bl.ws.data.input.vo.InventoryDetailVO();
					wareHouse.add(rs.getString("WAREHOUSE"));
					detailVO.setWarehouseName(rs.getString("WAREHOUSE"));
					detailVO.setResourceName(rs.getString("ResourceReferance"));
					detailVO.setResourceType(rs.getString("resourceType"));
					inventoryDetailVO.add(detailVO);
					isAdded = true;
					System.out.println("inside add :: "+inventoryDetailVO);
					
				}
			}
			
			if(isAdded) {
				rs.first();
				
				do {
					InventoryVO inventoryVO = new InventoryVO(); 
					
					inventoryVO.setInventoryNumber(rs.getString("inventoryno"));
					inventoryVO.setBatchNumber(rs.getString("BATCHNUMBER"));
					inventoryVO.setInventoryStaus(rs.getString("status"));
//					inventoryVO.setCustomerRefId(rs.getString("cutromerrefid"));
//					inventoryVO.setPartnerRefId(rs.getString("partnerrefid"));
					
					
					if(inventoryDetailVO!=null && !inventoryDetailVO.isEmpty()) {
						for(com.elitecore.cpe.bl.ws.data.input.vo.InventoryDetailVO vo : inventoryDetailVO) {
							if(rs.getString("WAREHOUSE").equals(vo.getWarehouseName())) {
								vo.addInventory(inventoryVO);
							}
						}
					}
					if(headerSet!=null && !headerSet.isEmpty()) {
						List<InventoryAttributeVO> attributeVOs = new ArrayList<InventoryAttributeVO>();
						for(String column : headerSet) {
							InventoryAttributeVO attributeVO = new InventoryAttributeVO();
//							Logger.logTrace("Column :: ", column);
							attributeVO.setAttributeName(column);
							
							if(rs.getString(column.replace(" ", "_")) == null || rs.getString(column.replace(" ", "_")).trim().equals(""))  continue;
							
							attributeVO.setAttributeValue(rs.getString(column.replace(" ", "_")));
							attributeVOs.add(attributeVO);
						}
						inventoryVO.setAttributeVOs(attributeVOs);
						
					}
					
					
				} while(rs.next());
				
			}
			
			responseData.setInventoryDetailVO(inventoryDetailVO);
			responseData.setResponseCode(0l);
			responseData.setResponseMessage("Success");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return responseData;
	}
	
	public static TransferOrderData getTransferOrderData(List<InventoryDetailVO> inventoryDetailVOs,Long warehouseId,String orderNo,IBLSession iblSession){
		TransferOrderData transferOrderData=new TransferOrderData();
		try{
			Logger.logTrace(strModule, "Inside getTransferOrderData method");
			transferOrderData.setRemarks("Creating Transfer Order");
				transferOrderData.setTransferOrderNo(orderNo);
				transferOrderData.setToWarehouseId(warehouseId);
				
				transferOrderData.setCreatedby(iblSession.getSessionUserId());
				transferOrderData.setCreatedate(getCurrentTimestamp());
				transferOrderData.setUpdatedby(iblSession.getSessionUserId());
				transferOrderData.setUpdatedate(getCurrentTimestamp());
				for(InventoryDetailVO inventoryDetailVO:inventoryDetailVOs){
					transferOrderData.setFromWarehouseId(inventoryDetailVO.getWarehouseId());
					break;
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		return transferOrderData;
	}
	public static List<TransferOrderDetailData> getTransferOrderDetailData(List<InventoryDetailVO> inventoryDetailVOs,TransferOrderData transferOrderData,IBLSession iblSession){
		List<TransferOrderDetailData> transferOrderDetailDatas=new ArrayList<TransferOrderDetailData>();
		try{
			Logger.logTrace(strModule, "Inside getTransferOrderDetailData method");
				for(InventoryDetailVO inventoryDetailVO:inventoryDetailVOs){
					TransferOrderDetailData transferOrderDetailData=new TransferOrderDetailData();
					transferOrderDetailData.setTransferOrderId(transferOrderData.getTransferOrderId());
					transferOrderDetailData.setInventoryNo(inventoryDetailVO.getInventoryId());
					transferOrderDetailData.setBatchNo(inventoryDetailVO.getBatchId());
					transferOrderDetailData.setPreviousStatus(Long.valueOf(inventoryDetailVO.getStatusId()));
					if(inventoryDetailVO.getSubStatusId()!=0) {
						transferOrderDetailData.setPreviousSubStatus(Long.valueOf(inventoryDetailVO.getSubStatusId()));
					}
					transferOrderDetailDatas.add(transferOrderDetailData);
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		Logger.logTrace(strModule, "Size of List in getTransferOrderDetailData method:"+transferOrderDetailDatas.size());
		return transferOrderDetailDatas;
	}
	
	public static List<InventoryDetailVO> getInventoryDetails(
			CachedRowSetImpl cachedRowSetImpl,Set<String> headerSet) {
		
		List<InventoryDetailVO> inventoryDetailVOs = new ArrayList<InventoryDetailVO>();
		
		try {
			CachedRowSet rs=cachedRowSetImpl;
			
			while(rs.next()) {
					InventoryDetailVO inventoryDetailVO = new InventoryDetailVO();
					
//					detailVO.setResourceName(rs.getString("ResourceReferance"));
//					detailVO.setResourceType(rs.getString("resourceType"));
					
					inventoryDetailVO.setWareHouseName(rs.getString("WAREHOUSE"));
					inventoryDetailVO.setInventoryId(rs.getString("inventoryno"));
					inventoryDetailVO.setBatchId(rs.getString("BATCHNUMBER"));
					inventoryDetailVO.setStatus(rs.getString("status"));
					inventoryDetailVO.setTransferStatus(rs.getString("transferstatus"));
					inventoryDetailVO.setRemark(rs.getString("remark"));
					
					Map<String, String> attributeMap = new HashMap<String, String>();
					if(headerSet!=null && !headerSet.isEmpty()) {
						
						for(String column : headerSet) {
							attributeMap.put(column, rs.getString(column.replace(" ", "_")));
						}
						inventoryDetailVO.setAttribute(attributeMap);
					}
					inventoryDetailVOs.add(inventoryDetailVO);
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return inventoryDetailVOs;
	}
	
	public static InventoryReserveData prepareInventoryReserveData(String reservationNo,Long totalResource,String orderLineItemId,IBLSession iblSession){
		
		InventoryReserveData reserveData = new InventoryReserveData();
		reserveData.setReservationNo(reservationNo);
		reserveData.setTotalResource(totalResource); 
		reserveData.setCreatedby("STF0001");
		reserveData.setCreatedate(getCurrentTimestamp());
		reserveData.setOrderLineItemId(orderLineItemId);
		
		return reserveData;
	}
	
	public static InventoryReserveDetailData prepareInventoryReserveDetailData(String inventoryNo,Long reservationId){
		
		InventoryReserveDetailData inventoryReserveDetailData = new InventoryReserveDetailData();
		inventoryReserveDetailData.setInventoryNo(inventoryNo);
		inventoryReserveDetailData.setReservationId(reservationId);
		return inventoryReserveDetailData;
	}
	public static List<BatchSummaryVO> getBatchSummaryVO(List<BatchSummaryData> batchSummaryDatas){
		List<BatchSummaryVO> batchSummaryVOs=new ArrayList<BatchSummaryVO>();
		try{
			Logger.logTrace(strModule, "Inside getBatchSummaryVO method");
				for(BatchSummaryData batchSummaryData:batchSummaryDatas){
					BatchSummaryVO batchSummaryVO=new BatchSummaryVO();
					batchSummaryVO.setResource(batchSummaryData.getResource().getName());
					batchSummaryVO.setResourcetype(batchSummaryData.getResource().getResourceType().getName());
					batchSummaryVO.setResourcesubtype(batchSummaryData.getResource().getResourceType().getName());
					batchSummaryVO.setInventoryFrom(batchSummaryData.getNumberFrom());
					batchSummaryVO.setInventoryTo(batchSummaryData.getNumberTo());
					batchSummaryVO.setTotal(batchSummaryData.getTotalInvetoryByItem());
					batchSummaryVO.setBatchnumber(batchSummaryData.getBatchData().getBatchNo());
					
					batchSummaryVOs.add(batchSummaryVO);
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		Logger.logTrace(strModule, "Size of List in getBatchSummaryVO method:"+batchSummaryVOs.size());
		Logger.logTrace(strModule, "Util  getBatchSummaryVO method:"+batchSummaryVOs);
		return batchSummaryVOs;
		
	}
	public static List<InventoryStatusLogVO> getInventoryStatusLogVo (List<InventoryStatusLogData> inventoryStatusLogDatas){
		Logger.logTrace(strModule, "Inside getInventoryStatusLogVo method");
		List<InventoryStatusLogVO> inventoryStatusLogVOs=null;
		if(inventoryStatusLogDatas !=null && inventoryStatusLogDatas.size()>0){
			inventoryStatusLogVOs  = new ArrayList<InventoryStatusLogVO>();
			
			Logger.logTrace(strModule, "inventoryStatusLogDatas not null");
				for(InventoryStatusLogData inveStatusLogData:inventoryStatusLogDatas){
					InventoryStatusLogVO inventoryStatusLogVO=new InventoryStatusLogVO();
					inventoryStatusLogVO.setInventoryId(inveStatusLogData.getInventoryId());
					inventoryStatusLogVO.setChangeDate(inveStatusLogData.getStatusChangedDate());

				try{
					inventoryStatusLogVO.setChangedby(UserFactory.findUserById(inveStatusLogData.getStaffId()).getName());
				} catch (SearchBLException e) {
					e.printStackTrace();
				} catch (NoSuchControllerException e) {
					e.printStackTrace();
				}
					inventoryStatusLogVO.setOldStatus(inveStatusLogData.getOldStatusName());
					inventoryStatusLogVO.setNewStatus(inveStatusLogData.getNewStatusName());
					inventoryStatusLogVO.setRemarks(inveStatusLogData.getRemark());
					inventoryStatusLogVOs.add(inventoryStatusLogVO);
				}
			}
		return inventoryStatusLogVOs;
	}
	public static List<InventoryDetailVO> getInventoryDetailVO(CachedRowSetImpl cachedRowSetImpl,Map<String, UserVO> map) {
		List<InventoryDetailVO> inventoryDetailVOs=new ArrayList<InventoryDetailVO>();
		try {
		CachedRowSet rs=cachedRowSetImpl;
		while(rs.next()) {
			InventoryDetailVO inventoryDetailVO = new InventoryDetailVO();
			inventoryDetailVO.setBatchId(rs.getString("BATCHNUMBER"));
			inventoryDetailVO.setWareHouseName(rs.getString("WAREHOUSE"));
			inventoryDetailVO.setWarehouseId(Long.parseLong(rs.getString("WAREHOUSEID")));
			inventoryDetailVO.setInventoryId(rs.getString("inventoryno"));
			inventoryDetailVO.setStatus(rs.getString("status"));
			inventoryDetailVO.setTransferStatus(rs.getString("transferstatus"));
			inventoryDetailVO.setResourceType(rs.getString("TYPE"));
			inventoryDetailVO.setResourceSubType(rs.getString("SUBTYPE"));
			inventoryDetailVO.setCreateDate(rs.getTimestamp("CREATEDATE"));
			inventoryDetailVO.setUpdatedDate(rs.getTimestamp("LASTMODIFIEDDATE"));
			inventoryDetailVO.setExternalBatchNumber(rs.getString("EXTERNALBATCHNUMBER"));
			inventoryDetailVO.setGurrantyWarrantyMode(rs.getString("GuranteeWarrantyMode"));
			oracle.sql.TIMESTAMP ts =  (oracle.sql.TIMESTAMP) rs.getObject("warrantydate");
			if(ts!=null) {
				inventoryDetailVO.setWarrantyDate(new Timestamp(ts.dateValue().getTime()));
			}
			inventoryDetailVO.setWarrantyType(rs.getString("warrantytype"));
			try{
				if(rs.getString("CREATEDBYSTAFFID")!=null){
					if(map.containsKey(rs.getString("CREATEDBYSTAFFID"))) {
						inventoryDetailVO.setCreatedby(map.get(rs.getString("CREATEDBYSTAFFID")).getName());
					}
					
				}
				if(rs.getString("LASTMODIFIEDDATEBYSTAFFID")!=null){
					if(map.containsKey(rs.getString("LASTMODIFIEDDATEBYSTAFFID"))) {
						inventoryDetailVO.setCreatedby(map.get(rs.getString("LASTMODIFIEDDATEBYSTAFFID")).getName());
					}
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			inventoryDetailVOs.add(inventoryDetailVO);
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return inventoryDetailVOs;
	}
	public static OrderData getOrderData(PlaceOrderVO placeOrderVO,String orderNo,IBLSession iblSession){
		OrderData orderData=new OrderData();
		try{
			Logger.logTrace(strModule, "Inside getOrderData method");
			orderData.setOrderNo(orderNo);
			orderData.setFromWarehouseId(placeOrderVO.getFromwarehouseId());
			orderData.setToWarehouseId(placeOrderVO.getTowarehouseId());
			orderData.setResourceTypeId(placeOrderVO.getResourceTypeId());
			orderData.setResourceSubTypeId(placeOrderVO.getResourceSubTypeId());
			orderData.setQuantity(placeOrderVO.getQuantity());
			orderData.setCreatedby(iblSession.getSessionUserId());
			orderData.setCreatedate(getCurrentTimestamp());
			orderData.setRemarks(placeOrderVO.getRemark());
			//orderData.setUpdatedby(iblSession.getSessionUserId());
			//orderData.setUpdatedate(getCurrentTimestamp());
				
		}catch(Exception e){
			e.printStackTrace();
		}
		return orderData;
	}
	
	
	
	
	
	
	public static NotificationData convertPlaceOrderEmailVOForPlaceOrder(OrderData orderData,String alias) {
		
		
		NotificationData notificationData = new NotificationData();
		notificationData.setAlias(alias);
		Map<String, String> map = new HashMap<String, String>();
		
		
		Logger.logTrace(strModule, "Inside convertPlaceOrderEmailVO method.");
		System.out.println("alias:::"+alias);
		ArrayList <String> emailIds=new ArrayList<String>();
		map.put(NotificationConstants.CPE_FROM_WAREHOUSE, orderData.getFromWarehouseData().getName());
		map.put(NotificationConstants.CPE_TO_WAREHOUSE, orderData.getToWarehouseData().getName());
		map.put(NotificationConstants.CPE_QUANTITY, orderData.getQuantity()+"");
		map.put(NotificationConstants.CPE_TO_WAREHOUSE, orderData.getToWarehouseData().getName());
		map.put(NotificationConstants.CPE_RESOURCE_TYPENAME, orderData.getResourceType().getName());
		
		if(orderData.getResourceSubTypeData()!=null){
			map.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME, orderData.getResourceSubTypeData().getName());
		}else{
			map.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME," ");
		}
		
		
		
		emailIds.add(orderData.getFromWarehouseData().getEmailId());
		emailIds.add(orderData.getToWarehouseData().getEmailId());
		notificationData.setToEmails(emailIds);
		
		map.put(NotificationConstants.CPE_ORDER_NUMBER, orderData.getOrderNo());
		if(orderData.getAcceptQuantity()!=null){
			map.put(NotificationConstants.CPE_ACCEPTED_QUANTITY, orderData.getAcceptQuantity()+"");
		}
		
		notificationData.setValueMap(map);
		
		return notificationData;
	}
	
	
public static Properties readEmailProperty(String filename){
	Logger.logTrace(strModule, "Inside readEmailProperty method");
		Properties  props = new Properties();
		try{
			ResourceBundle rb = ResourceBundle.getBundle(filename);
			Enumeration <String> keys = rb.getKeys();
		
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				if(key.contains("smtp.host")){
					props.put("smtp.host", rb.getString(key));
				}else if(key.contains("smtp.port")){
					props.put("smtp.port", rb.getString(key));
				}else if(key.contains("admin.emailId")){
					props.put("admin.emailId", rb.getString(key));
				}else{
					props.put("admin.passwd", rb.getString(key));
				}
				
				String value = rb.getString(key);
				Logger.logTrace(strModule, "Inside readEmailProperty method::Key::"+key+" Value::"+value);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return props;
	}

public static void processPlaceOrderNotificationEmailCommon(PlaceOrderNotificationEmailVO emailVO){
	
	Map <String,ThresholdStatusVO> map=new LinkedHashMap<String, ThresholdStatusVO>();
	try{
		Logger.logTrace(strModule,"processPlaceOrderNotificationEmailCommon() called");
		boolean flag=true;
		final Properties propsEmailconfig =readEmailProperty("EmailConfig");
		
	
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", propsEmailconfig.getProperty("smtp.host"));
		props.put("mail.smtp.port", propsEmailconfig.getProperty("smtp.port"));
		
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(propsEmailconfig.getProperty("admin.emailId"),propsEmailconfig.getProperty("admin.passwd"));
					}
				});
		StringBuffer body=new StringBuffer();
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(propsEmailconfig.getProperty("admin.emailId")));
		InternetAddress[] address = new InternetAddress[emailVO.getEmailId().size()];
		
		  for(int i =0; i< emailVO.getEmailId().size(); i++)
		  {
		      address[i] = new InternetAddress(emailVO.getEmailId().get(i));
		  }

		  message.setRecipients(Message.RecipientType.TO, address);
		//message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(commonEmail));
		message.setSentDate(new Date());
		
		String header="";
		List<String> headerList=new ArrayList<String>();
		headerList.add("Order Number");
		headerList.add("From WareHouse");
		headerList.add("To WareHouse");
		headerList.add("Resource Type");
		headerList.add("Resource Subtype");
		headerList.add("Quantity");
		StringBuffer content=new StringBuffer("<tr><td>"+emailVO.getOrderNumber()+"</td>"
							+"<td>"+emailVO.getFromwareHouseName()+"</td>"+"<td>"+emailVO.getTowareHouseName()
							+"</td>"+"<td>"+emailVO.getResourceTypeName()+"</td>"
							+"<td>"+((emailVO.getResourceSubTypeName()!=null)?emailVO.getResourceSubTypeName():"-")+"</td>"
							+"<td>"+emailVO.getQuantity()+"</td></tr> ");
		
	
			
		
		
		message.setSubject("Place Order Status Report");
		body.append("<html>");
		body.append("Hello Owner,"+ "</br></br>");
		body.append("<table border=\"1\">");
		body.append("<tr>");
		for(String str:headerList){
			body.append("<td>");
			body.append(str);
			body.append("</td>");
		}
		body.append("</tr>");
			body.append(content.toString());
			body.append("</table>");
			body.append("</html>");
			
			
			message.setContent(body.toString(), "text/html");
			
		//	message.setText(body.toString());
			if(flag){
			Transport.send(message);
			}else{
				Logger.logTrace(strModule,"No Need to send email");
			}

			Logger.logTrace(strModule,"processPlaceOrderNotificationEmailCommon() Completed");
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
}


public static void processAcceptPlaceOrderNotificationEmail(PlaceOrderNotificationEmailVO emailVO){
	
	Map <String,ThresholdStatusVO> map=new LinkedHashMap<String, ThresholdStatusVO>();
	try{
		Logger.logTrace(strModule,"processAcceptPlaceOrderNotificationEmail() called");
		boolean flag=true;
		final Properties propsEmailconfig =readEmailProperty("EmailConfig");
		
	
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", propsEmailconfig.getProperty("smtp.host"));
		props.put("mail.smtp.port", propsEmailconfig.getProperty("smtp.port"));
		
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(propsEmailconfig.getProperty("admin.emailId"),propsEmailconfig.getProperty("admin.passwd"));
					}
				});
		StringBuffer body=new StringBuffer();
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(propsEmailconfig.getProperty("admin.emailId")));
		InternetAddress[] address = new InternetAddress[emailVO.getEmailId().size()];
		
		  for(int i =0; i< emailVO.getEmailId().size(); i++)
		  {
		      address[i] = new InternetAddress(emailVO.getEmailId().get(i));
		  }

		  message.setRecipients(Message.RecipientType.TO, address);
		//message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(commonEmail));
		message.setSentDate(new Date());
		
		String header="";
		List<String> headerList=new ArrayList<String>();
		headerList.add("Order Number");
		headerList.add("From WareHouse");
		headerList.add("To WareHouse");
		headerList.add("Resource Type");
		headerList.add("Resource Subtype");
		headerList.add("Total Quantity");
		headerList.add("Accepted Quantity");
		StringBuffer content=new StringBuffer("<tr><td>"+emailVO.getOrderNumber()+"</td>"
							+"<td>"+emailVO.getFromwareHouseName()+"</td>"+"<td>"+emailVO.getTowareHouseName()
							+"</td>"+"<td>"+emailVO.getResourceTypeName()+"</td>"
							+"<td>"+((emailVO.getResourceSubTypeName()!=null)?emailVO.getResourceSubTypeName():"-")+"</td>"
							+"<td>"+emailVO.getQuantity()+"</td>"+"<td>"+emailVO.getAcceptQuantity()+"</td></tr> ");
		
	
			
		
		
		message.setSubject("Accept/Reject Place Order Status");
		body.append("<html>");
		body.append("Hello Owner,"+ "</br></br>");
		body.append("<table border=\"1\">");
		body.append("<tr>");
		for(String str:headerList){
			body.append("<td>");
			body.append(str);
			body.append("</td>");
		}
		body.append("</tr>");
			body.append(content.toString());
			body.append("</table>");
			body.append("</html>");
			
			
			message.setContent(body.toString(), "text/html");
			
		//	message.setText(body.toString());
			if(flag){
			Transport.send(message);
			}else{
				Logger.logTrace(strModule,"No Need to send email");
			}

			Logger.logTrace(strModule,"processAcceptPlaceOrderNotificationEmail() Completed");
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
}



public static PlaceOrderVO getPlaceOrderVO(OrderData orderData){
	PlaceOrderVO placeOrderVO = new PlaceOrderVO();
	Logger.logTrace(strModule,"getPlaceOrderVO() called");
	if(orderData != null){
		placeOrderVO.setOrderNo(orderData.getOrderNo());
		placeOrderVO.setFromwarehouseId(orderData.getFromWarehouseId());
		placeOrderVO.setTowarehouseId(orderData.getToWarehouseId());
		placeOrderVO.setFromwarehouse(orderData.getFromWarehouseData().getName());
		placeOrderVO.setTowarehouse(orderData.getToWarehouseData().getName());
		try{
			placeOrderVO.setCreatedby(UserFactory.findUserById(orderData.getCreatedby()).getName());
			placeOrderVO.setCreateDate(orderData.getCreatedate());
			if(orderData.getUpdatedby()!=null && orderData.getUpdatedate()!=null){
				placeOrderVO.setUpdatedby(UserFactory.findUserById(orderData.getUpdatedby()).getName());
				placeOrderVO.setUpdatedDate(orderData.getUpdatedate());
					
			}
		
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (NoSuchControllerException e) {
			e.printStackTrace();
		}
		placeOrderVO.setResourceType(orderData.getResourceType().getName());
		if(orderData.getResourceSubTypeData()!=null){
		placeOrderVO.setResourceSubtype(orderData.getResourceSubTypeData().getName());
		}
		placeOrderVO.setStatus(orderData.getOrderStatus().getName());
		placeOrderVO.setQuantity(orderData.getQuantity());
		if(orderData.getAcceptQuantity()!=null){
			placeOrderVO.setAcceptquantity(orderData.getAcceptQuantity());
		}
		if(orderData.getAcceptRejectDate()!=null){
			placeOrderVO.setAcceptRejectDate(orderData.getAcceptRejectDate());
		}
		if(orderData.getRemarks()!=null){
			placeOrderVO.setRemark(orderData.getRemarks());
		}
		if(orderData.getTransferRemarks()!=null){
			placeOrderVO.setTransferRemark(orderData.getTransferRemarks());
		}
		if(orderData.getTransferOrderNo()!=null){
			placeOrderVO.setTransferOrderNo(orderData.getTransferOrderNo());
		}
		
	}
	Logger.logTrace(strModule,"getPlaceOrderVO() Completed::"+placeOrderVO);
	return placeOrderVO;
}

	public static InventoryDetailVO getInventoryDetailForSearch(InventoryData data,
			List<AttributeTransData> attributeData) {
	
		InventoryDetailVO detailVO = new InventoryDetailVO();
		if(data!=null ) {
/*			if(data.getAccepted()!=null && !data.getAccepted().isEmpty()) {
				detailVO.setAccepted(data.getAccepted().charAt(0));
			}
*/			detailVO.setBatchId(data.getBatchData().getBatchNo());
			detailVO.setCreateDate(data.getCreatedate());
			detailVO.setCustomerRefNumber(data.getCustomerRefId());
			detailVO.setDistributorNumber(data.getDistributorId());
			detailVO.setInventoryId(data.getInventoryNo());
			detailVO.setExternalBatchNumber(data.getExternalBatchNumber());
	/*		if(data.getNewed()!=null && !data.getNewed().isEmpty()) {
				detailVO.setNewSubStatus(data.getNewed().charAt(0));
			}
			if(data.getRefurbished()!=null && !data.getRefurbished().isEmpty()) {
				detailVO.setRefurbished(data.getRefurbished().charAt(0));
			}
*/			
			if(data.getItemData().getResourceSubTypeData()!=null) {
				detailVO.setResourceSubType(data.getItemData().getResourceSubTypeData().getName());
			}
			if(data.getItemData().getResourceType()!=null) {
				detailVO.setResourceType(data.getItemData().getResourceType().getName());
			}
			
			if(data.getItemData().getResourceNumber()!=null) {
				detailVO.setResourceNumber(data.getItemData().getResourceNumber());
			}
			
			if(data.getItemData().getItemId()!=null) {
				detailVO.setResourceNo(data.getItemData().getItemId());
			}
			
			if(data.getItemData().getName()!=null) {
				detailVO.setResourceName(data.getItemData().getName());
			}
			
	/*		if(data.getStandBy()!=null && !data.getStandBy().isEmpty()) {
				detailVO.setStandby(data.getStandBy().charAt(0));
			}
	*/		
			detailVO.setGurrantyWarrantyMode(data.getGurrantyWarrantyMode());
			detailVO.setWarrantyType(data.getWarrantyType());
			if(data.getWarrantyDate()!=null) {
				detailVO.setWarrantyDate(data.getWarrantyDate());
			}
//Added By Rinkal Start
			if(data.getSubStatusData()!=null){
			detailVO.setSubStatus(data.getSubStatusData().getName());
			}
//Added By Rinkal End

			detailVO.setStatus(data.getStatusData().getName());
			detailVO.setStatusId(data.getInventoryStatusId());
			detailVO.setTransferStatus(data.getTransferInventoryStatus());
			detailVO.setUpdatedby(data.getUpdatedby());
			detailVO.setUpdatedDate(data.getUpdatedate());
			detailVO.setWarehouseId(data.getWarehouseId());
			detailVO.setWareHouseName(data.getWarehousedata().getName());
			
			try{
				detailVO.setCreatedby(UserFactory.findUserById(data.getCreatedby()).getName());
				if(data.getUpdatedby()!=null)
				detailVO.setUpdatedby(UserFactory.findUserById(data.getUpdatedby()).getName());
			} catch (SearchBLException e) {
				e.printStackTrace();
			} catch (NoSuchControllerException e) {
				e.printStackTrace();
			}
			
		}
		
		if(attributeData!=null && !attributeData.isEmpty())  {
			Map<String,String> map = new HashMap<String,String>();
			for(AttributeTransData attribute : attributeData) {
				map.put(attribute.getAttributeData().getName(), attribute.getValue());
			}
			detailVO.setAttribute(map);
		}
		
		
		
		return detailVO;
	}
//added for accept-reject PlaceOrder
	public static NotificationData convertPlaceOrderEmailVOForPlaceOrder(OrderData orderData,String alias,long totalinventories) {
		
		
		NotificationData notificationData = new NotificationData();
		notificationData.setAlias(alias);
		Map<String, String> map = new HashMap<String, String>();
		
		
		Logger.logTrace(strModule, "Inside convertPlaceOrderEmailVO method.");
		System.out.println("alias:::"+alias);
		ArrayList <String> emailIds=new ArrayList<String>();
		map.put(NotificationConstants.CPE_FROM_WAREHOUSE, orderData.getFromWarehouseData().getName());
		map.put(NotificationConstants.CPE_TO_WAREHOUSE, orderData.getToWarehouseData().getName());
		map.put(NotificationConstants.CPE_QUANTITY, orderData.getQuantity()+"");
		map.put(NotificationConstants.CPE_TO_WAREHOUSE, orderData.getToWarehouseData().getName());
		map.put(NotificationConstants.CPE_RESOURCE_TYPENAME, orderData.getResourceType().getName());
		map.put(NotificationConstants.CPE_AVAILABLE_QUANTITY, totalinventories+"");

		if(orderData.getResourceSubTypeData()!=null){
			map.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME, orderData.getResourceSubTypeData().getName());
		}else{
			map.put(NotificationConstants.CPE_RESOURCE_SUBTYPENAME," ");
		}
		
		
		emailIds.add(orderData.getFromWarehouseData().getEmailId());
		emailIds.add(orderData.getToWarehouseData().getEmailId());
		notificationData.setToEmails(emailIds);
		
		map.put(NotificationConstants.CPE_ORDER_NUMBER, orderData.getOrderNo());
		if(orderData.getAcceptQuantity()!=null){
			map.put(NotificationConstants.CPE_ACCEPTED_QUANTITY, orderData.getAcceptQuantity()+"");
		}
		
		notificationData.setValueMap(map);
		
		return notificationData;
	}
	
}
