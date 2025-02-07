package com.elitecore.cpe.bl.session.inventorymgt;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.elitecore.cpe.bl.constants.inventorymgt.InventoryStatusConstants;
import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.system.DataSourceConstant;
import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryReserveData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryReserveDetailData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusLogData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusTransition;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventorySubStatusData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.OrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderDetailData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.WarehouseInventoryStatusData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.WarehouseInventoryStatusHistoryData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeTransData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.entity.inventory.order.OrderAgentHistoryData;
import com.elitecore.cpe.bl.entity.inventory.system.systemparameter.SystemParameter;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementUtil;
import com.elitecore.cpe.bl.facade.system.internal.SystemInternalDataConversionUtil;
import com.elitecore.cpe.bl.facade.ws.WSFacadeUtil;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryWrapperVO;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchWarehouseInventoryStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.TransferInventorySummaryViewVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.PartialAcceptRejectTransferOrderVO;
import com.elitecore.cpe.bl.vo.order.OrderDetailVo;
import com.elitecore.cpe.bl.ws.data.input.request.InventoryDetailsRequestData;
import com.elitecore.cpe.bl.ws.data.input.response.InventoryStatusResponseVO;
import com.elitecore.cpe.bl.ws.data.input.response.InventoryStatusVO;
import com.elitecore.cpe.bl.ws.data.input.vo.CPEInventoryVO;
import com.elitecore.cpe.bl.ws.data.input.vo.InventoryAttributeVO;
import com.elitecore.cpe.bl.ws.data.input.vo.InventoryRequestVO;
import com.elitecore.cpe.bl.ws.data.input.vo.ReleaseInventoryVO;
import com.elitecore.cpe.bl.ws.data.util.InventoryMgtResponseCode;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;
import com.sun.rowset.CachedRowSetImpl;

@Stateless
public class InventoryManagementSessionBean extends BaseSessionBean implements InventoryManagementSessionBeanLocal{

	private static final String MODULE = "INVENTORY-SB";
	
	
	
	private Object statusObj = new Object(); 
	
	
	/**
	 * Upload Inventory in the system
	 * @author yash.kapasi
	 * @param {@link List}<{@link InventoryWrapperVO}> inventoryVOs.
	 * @param IBLSession iblSession
	 * @return InventoryUploadVO inventoryUploadVO.
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute( TransactionAttributeType.REQUIRED )
	public InventoryUploadVO uploadInventory(List<InventoryWrapperVO> inventoryVOs,IBLSession iblSession) throws CreateBLException {
		
		InventoryUploadVO inventoryUploadVO = new InventoryUploadVO();
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside uploadInventory");
		}
		try {
			inventoryUploadVO.setInvalidEntryFromvalidMap(new HashMap<Long, Integer>());
			
			if(inventoryVOs == null || inventoryVOs.isEmpty()){
				throw new CreateBLException("getting inventory Data blank");
			}
			
			Iterator<InventoryWrapperVO> itr = inventoryVOs.iterator();
			Long validEntry = 0L,invalidEntry = 0L;
			
			while(itr.hasNext())
			{
				InventoryWrapperVO inventoryWrapperVO = itr.next();
				Long refId = null;
				boolean isInvalidEntry = false;
				Iterator<AttributeTransData> itr1 = null;
				
				if(inventoryWrapperVO.getInventoryData() != null){
					getEntityManager().persist(inventoryWrapperVO.getInventoryData());
					refId = inventoryWrapperVO.getInventoryData().getInventoryId();
					
					// invnetory status log history
					InventoryStatusLogData inventoryStatusLogData = null;
					if(inventoryWrapperVO.getInventoryData().getInventoryStatusId() == InventoryStatusConstants.AVAILABLE){
						inventoryStatusLogData =	InventoryManagementUtil.prepareInventoryStatusLogData(inventoryWrapperVO.getInventoryData().getInventoryId(), 
								InventoryStatusConstants.NEW,InventoryStatusConstants.AVAILABLE,InventoryStatusConstants.NEW_STATUS,InventoryStatusConstants.AVAILABLE_STATUS,"Inventory Uploaded",iblSession);
					}else{
						inventoryStatusLogData =	InventoryManagementUtil.prepareInventoryStatusLogData(inventoryWrapperVO.getInventoryData().getInventoryId(), 
								InventoryStatusConstants.NEW,InventoryStatusConstants.NEW,InventoryStatusConstants.NEW_STATUS,InventoryStatusConstants.NEW_STATUS,"Inventory Uploaded",iblSession);
					}
					getEntityManager().persist(inventoryStatusLogData);
					validEntry++;
				}else{
					getEntityManager().persist(inventoryWrapperVO.getInvalidInventoryData());
					refId = inventoryWrapperVO.getInvalidInventoryData().getId();
					isInvalidEntry = true;
					invalidEntry++;
				}
				
				itr1 = inventoryWrapperVO.getAttributeTransDatas().iterator();
				while(itr1.hasNext())
				{
					AttributeTransData transData = itr1.next();
					transData.setReferenceId(refId);
					if(isInvalidEntry){
						transData.setTableRefName("TBLMINVALIDINVENTORY");
					}
					
					if(transData.getValue() == null || transData.getValue().trim().equals(""))  continue;  // important for if we get empty value
					
					getEntityManager().persist(transData);
				}
			}
			
			getEntityManager().flush();
			Logger.logTrace(MODULE, "flush called :::::::::::::::");
			Logger.logInfo(MODULE, "------------------------------");
			Logger.logInfo(MODULE, "Valid Entry : "+validEntry);
			Logger.logInfo(MODULE, "InValid Entry : "+invalidEntry);
			Logger.logInfo(MODULE, "------------------------------");
			
			inventoryUploadVO.setValidEntry(validEntry);
			inventoryUploadVO.setInvalidEntry(invalidEntry);
		}catch (Exception e) {
			
			e.printStackTrace();
				
			getSessionContext().setRollbackOnly();
		
	    	throw new CreateBLException("upload Inventory Failed, Reason : " + e.getMessage(), e);
		}
		
		return inventoryUploadVO;
	}
	
	
	/**
	 * save Warehouse inventory Status
	 * @author yash.kapasi
	 * @param {@link Collection}<{@link WarehouseInventoryStatusData}> warehouseInventoryStatusDatas.
	 * @param IBLSession iblSession
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute( TransactionAttributeType.REQUIRED )
	public void saveWarehouseInventoryStatus(Collection<WarehouseInventoryStatusData> warehouseInventoryStatusDatas,IBLSession iblSession) throws CreateBLException{
		if(isInfoLevel())
		Logger.logInfo(MODULE, "in saveWarehouseInventoryStatus method");
		
		try {
			
			if(warehouseInventoryStatusDatas == null || warehouseInventoryStatusDatas.isEmpty() ){
				throw new CreateBLException("getting warehouseInventoryStatusDatas as blank");
			}
			
			Iterator<WarehouseInventoryStatusData> itrWHInventoryStatus = warehouseInventoryStatusDatas.iterator();
			while(itrWHInventoryStatus.hasNext()){
				
				WarehouseInventoryStatusData warehouseInventoryStatusData = itrWHInventoryStatus.next();
				
				Map<String,Object> fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("warehouseData", warehouseInventoryStatusData.getWarehouseData());
				fieldValueMap.put("resource", warehouseInventoryStatusData.getResource());
				
				List filterData =  getFilterDataBy(EntityConstants.WAREHOUSEINVENTORYSTATUS_DATA, fieldValueMap);
				
				Long warehouseInventoryStatusId = null;
				WarehouseInventoryStatusData warehouseInventoryStatusData2 = null;
				if(filterData != null && !filterData.isEmpty()){
					Logger.logDebug(MODULE, "getting existing object in warehouseinventoryData");
					warehouseInventoryStatusData2 = (WarehouseInventoryStatusData)filterData.get(0);
					warehouseInventoryStatusData2.setUploaded(warehouseInventoryStatusData2.getUploaded() + warehouseInventoryStatusData.getUploaded());
					warehouseInventoryStatusData2.setAvailable(warehouseInventoryStatusData2.getAvailable() + warehouseInventoryStatusData.getUploaded());
					warehouseInventoryStatusData2.setUpdatedby(warehouseInventoryStatusData.getUpdatedby());
					warehouseInventoryStatusData2.setUpdatedate(warehouseInventoryStatusData.getUpdatedate());
					getEntityManager().merge(warehouseInventoryStatusData2);
					
					warehouseInventoryStatusId = warehouseInventoryStatusData2.getWarehouseInventoryStatusId();
					
				}else{
					warehouseInventoryStatusData.setAvailable(warehouseInventoryStatusData.getUploaded());
					getEntityManager().persist(warehouseInventoryStatusData);
					
					warehouseInventoryStatusId = warehouseInventoryStatusData.getWarehouseInventoryStatusId();
				}
				
				// make entry also in warehouseInventoryStatusHistory
				String remark = "Inventory uploaded :"+warehouseInventoryStatusData.getUploaded()+" in warehouse :"+warehouseInventoryStatusData.getWarehouseData().getName() + ",Resource :"+warehouseInventoryStatusData.getResource().getName();
				WarehouseInventoryStatusHistoryData warehouseInventoryStatusHistoryData = InventoryManagementUtil.prepareWarehouseInventoryStatusHistoryData((warehouseInventoryStatusData2 == null)?warehouseInventoryStatusData:warehouseInventoryStatusData2, 
						warehouseInventoryStatusData.getWarehouseData(), warehouseInventoryStatusData.getWarehouseData(), 
						warehouseInventoryStatusData.getResource(), warehouseInventoryStatusData.getUploaded(), remark, iblSession);
				
				/*WarehouseInventoryStatusHistoryData warehouseInventoryStatusHistoryData = new WarehouseInventoryStatusHistoryData();
				warehouseInventoryStatusHistoryData.setWarehouseInventoryStatus((warehouseInventoryStatusData2 == null)?warehouseInventoryStatusData:warehouseInventoryStatusData2);
				warehouseInventoryStatusHistoryData.setFromWarehouse(warehouseInventoryStatusData.getWarehouseData());
				warehouseInventoryStatusHistoryData.setToWarehouse(warehouseInventoryStatusData.getWarehouseData());
				warehouseInventoryStatusHistoryData.setResource(warehouseInventoryStatusData.getResource());
				warehouseInventoryStatusHistoryData.setNumbercount(warehouseInventoryStatusData.getUploaded());
				warehouseInventoryStatusHistoryData.setRemark(remark);
				warehouseInventoryStatusHistoryData.setUpdatedby(warehouseInventoryStatusData.getUpdatedby());
				warehouseInventoryStatusHistoryData.setUpdatedate(warehouseInventoryStatusData.getUpdatedate());*/
				
				getEntityManager().persist(warehouseInventoryStatusHistoryData);
				
			}
			
			getEntityManager().flush();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			getSessionContext().setRollbackOnly();
			
	    	throw new CreateBLException("save warehouse Inventory Status Failed, Reason : " + e.getMessage(), e);
		}
	}

	
	/**
	 * Search Inventory Detail Data from the System
	 * @author yash.kapasi
	 * @param SearchInventoryVO inventoryDetailVO.
	 * @return {@link List}<{@link Object}> data.
	 * @throws CreateBLException
	 */
	@Override
	public List<Object> searchInventoryDetailData(
			SearchInventoryVO inventoryDetailVO) {
		
				if(isTraceLevel()){
					Logger.logTrace(MODULE, "inside searchInventoryDetailData");
				}
			List <Object> data=null;
				String sql = " Select f.BATCHNUMBER,a.INVENTORYNO,b.name,a.distributorid,a.cutromerrefid,a.ORDERID,d.name as warehouse,d.WAREHOUSEID,h.NAME as type, "
//					+"	i.NAME as subtype,a.CREATEDATE,a.CREATEDBYSTAFFID,a.LASTMODIFIEDDATE,a.LASTMODIFIEDDATEBYSTAFFID,a.TRANSFERINVENTORYSTATUS,a.ACCEPTED,a.REFURBISHED,a.STANDBY,a.NEW,a.externalbatchnumber,g.name,g.resourcenumber,g.RESOURCEID,(select z.name from tblsinventorysubstatus z where z.inventorysubstatusid=a.substatusid) as substatus,c.name as attributename,e.value  from  tblminventory a  "
					+"	i.NAME as subtype,a.CREATEDATE,a.CREATEDBYSTAFFID,a.LASTMODIFIEDDATE,a.LASTMODIFIEDDATEBYSTAFFID,a.TRANSFERINVENTORYSTATUS,a.externalbatchnumber,g.name,g.resourcenumber,g.RESOURCEID,(select z.name from tblsinventorysubstatus z where z.inventorysubstatusid=a.substatusid) as substatus,c.name as attributename,e.value  from  tblminventory a  "
							+"	inner join tblsinventorystatus b on a.statusid = b.inventorystatusid  "
								+" inner join tbltattributetrans e on a.inventoryid= e.referenceid "
								+"	inner join TBLMATTRIBUTE c on c.attributeid=e.attributeid  "
								+"  inner join TBLMBATCH f on a.batchid=f.batchid "
								+" inner join tblmresource g on a.RESOURCEID=g.RESOURCEID "
								+" inner join tblsresourcetype h on h.RESOURCETYPEID=g.RESOURCETYPEID " 
								+" inner join tblsresourcesubtype i on i.RESOURCESUBTYPEID=g.RESOURCESUBTYPEID " 
								+"	left join TBLMWAREHOUSE d on d.warehouseid = a.warehouseid " ;
				sql +=" where c.attributerel='Resource'   and 	e.tablerefname is null ";
//								+"	where c.attributerel='Resource'  "
//								+"	order by inventoryid ";
				try {
					if(inventoryDetailVO.getWareHouseId() != null ){
						sql+=" and upper(d.WAREHOUSEID) = upper('"+inventoryDetailVO.getWareHouseId()+ "')";
					}
					if(inventoryDetailVO.getBatchId() !=null && !inventoryDetailVO.getBatchId().equalsIgnoreCase("")){
						sql+= " and upper(f.BATCHNUMBER) =  upper('"+inventoryDetailVO.getBatchId()+ "')";
										
					}
					if(inventoryDetailVO.getInventoryId() !=null && !inventoryDetailVO.getInventoryId().equalsIgnoreCase("")){
						sql+= " and upper(a.INVENTORYNO) =  upper('"+inventoryDetailVO.getInventoryId()+ "')";
										
					}
					if(inventoryDetailVO.getInventoryStatusId() !=null ){
						sql+= " and b.INVENTORYSTATUSID =  "+inventoryDetailVO.getInventoryStatusId()+ "";
										
					}
					if(inventoryDetailVO.getTransferInventoryStatus()!=null ){
						if(inventoryDetailVO.getTransferInventoryStatus().equals("")){
						sql+= " and a.TRANSFERINVENTORYSTATUS is null  ";
						}
										
					}
					if(inventoryDetailVO.getResourceTypeId() !=null ){
						
						sql+= " and g.resourcetypeid=  "+inventoryDetailVO.getResourceTypeId()+ " ";
										
					
					if(inventoryDetailVO.getResourceSubtypeId() !=null ){
						sql+= " and  i.RESOURCESUBTYPEID = "+inventoryDetailVO.getResourceSubtypeId()+" ";
										
					}
					

					if(inventoryDetailVO.getExternalBatchNumber() !=null ){
						sql+= " and  a.ExternalBatchNumber = "+inventoryDetailVO.getExternalBatchNumber()+" ";
										
					}
					
				}
					sql+="	order by inventoryid "; 
					
					Logger.logDebug(MODULE, "SQL prepared in sessionbean:"+sql);
//					String hql = "from InventoryData w ";
//					if(inventoryDetailVO.getInventoryId() != null && !inventoryDetailVO.getInventoryId().equalsIgnoreCase("")){
//						hql +=" and upper(w.inventoryid) like upper('"+inventoryDetailVO.getInventoryId()+"%')";
//					}
//					if(inventoryDetailVO.getBatchId() != null && !inventoryDetailVO.getBatchId().equalsIgnoreCase("")){
//						hql +=" and upper(w.batchid) like upper('"+inventoryDetailVO.getBatchId()+"%')";
//					}if(inventoryDetailVO.getStatus() != null && !inventoryDetailVO.getStatus().equalsIgnoreCase("")){
//						hql +=" and upper(w.location) like upper('"+inventoryDetailVO.getStatus()+"%')";
//					}
					
					
					Query query = getEntityManager().createNativeQuery(sql);
					 data = query.getResultList();
					Logger.logDebug(MODULE, "Size of result:"+data.size());
//					String inventoryId="",record="";
//					
					for(int i=0;i<data.size();i++)
					{
						
						Object[] obj = (Object[]) data.get(i);
//						if(!inventoryId.equals(obj[1].toString())){
//							if(record.trim().length()>0){
//							data.add(record);
//							}
//							record = "";
//							inventoryId = obj[1].toString();
//							record += obj[0].toString() +">"+obj[1].toString()+">"+obj[2].toString()+">"+obj[3].toString()+">"+obj[4].toString();
						//System.out.println(obj[0].toString() +">"+obj[1].toString()+">"+obj[2].toString()+">"+obj[3].toString()+">"+obj[4].toString());
//						System.out.println(obj[0].toString());
//						System.out.println(obj[1].toString());
//						System.out.println(obj[2].toString());
//						System.out.println(obj[3].toString());
//						System.out.println(obj[4].toString());
//						System.out.println(obj[5].toString());
//						System.out.println(obj[6].toString());
//						System.out.println(obj[7].toString());
						
						}
//						else{
//							record += ">"+obj[4].toString();
//						}
						//Logger.logDebug(MODULE, data.toString());
//					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return data;
	}

	/**
	 * Search Inventory Detail Data from the System
	 * @author yash.kapasi
	 * @param SearchInventoryVO inventoryDetailVO.
	 * @return {@link List}<{@link Object}> data.
	 * @throws CreateBLException
	 */
	@Override
	public List<Object> searchInventoryBatchData(SearchInventoryVO searchInventoryVO) {
		// TODO Auto-generated method stub

		if (isTraceLevel()) {
			Logger.logTrace(MODULE, "inside searchInventoryBatchData");
		}
		List<Object> data = null;
		String sql = "select a.batchnumber,count(b.inventoryid) as inventorycount from tblmbatch a "
				+ " , tblminventory b where a.batchid=b.batchid " ;

			
		if(searchInventoryVO.getBatchId() !=null && !searchInventoryVO.getBatchId().equalsIgnoreCase("")){
			sql+= "and  a.BATCHNUMBER =  upper('"+searchInventoryVO.getBatchId()+ "')";
		}if(searchInventoryVO.getWareHouseId() != null){
			sql+= " and b.warehouseid = "+searchInventoryVO.getWareHouseId().toString();
		}if(searchInventoryVO.getInventoryStatusId() != null){
			sql+= " and b.statusid  = "+searchInventoryVO.getInventoryStatusId();
		}if(searchInventoryVO.getResourceTypeId() != null && searchInventoryVO.getResourceSubtypeId() != null){
			sql+= " and b.resourceid in (select a.resourceid from tblmresource a where a.resourcetypeid = "+searchInventoryVO.getResourceTypeId()+" and a.resourcesubtypeid = "+searchInventoryVO.getResourceSubtypeId()+")";
		}if(searchInventoryVO.getTransferInventoryStatus() != null){
			if(searchInventoryVO.getTransferInventoryStatus().equals("") ){
				sql+= " and b.TRANSFERINVENTORYSTATUS is null";
			}
		}
		
	//	sql+=" and 	e.tablerefname is null  ";
		sql+= " group by a.BATCHNUMBER ";
		Query query = getEntityManager().createNativeQuery(sql);
		data = query.getResultList();
		Logger.logDebug(MODULE, "Size of result:" + data.size());

		return data;
	}
	
	
	/**
	 * find total Inventory from Warehouse Data
	 * @author yash.kapasi
	 * @param WarehouseData warehouseData.
	 * @return Long total.
	 * @throws SearchBLException
	 */
	public Long getTotalInvnetoriesByWarehouse(WarehouseData warehouseData) throws SearchBLException{
		Long total = 0L;
		try {
			
			Logger.logInfo(MODULE, "warehoouseId :"+warehouseData.getWarehouseId());
			Query que = getEntityManager().createQuery("from InventoryData where warehousedata = :warehousedata ");
		 	que.setParameter("warehousedata", warehouseData);
		 	InventoryData data = (InventoryData)que.setMaxResults(1).getSingleResult();
		 
			if(data != null){
				total = 1L;
			}
			
		}catch(NoResultException ex){
			total = 0L;
		}catch (Exception e) {
			e.printStackTrace();
			throw new SearchBLException("Total Inventory can not be found");
		}
		return total;
	}
	
	
	/**
	 * Search Valid Inventory Upload Data by Batch Number, Attribute Name and Status
	 * @author yash.kapasi
	 * @param String batchNumber.
	 * @param String attributeNames.
	 * @param String status.
	 * @return CachedRowSetImpl cachedRowSetImpl.
	 * @throws SearchBLException
	 */
	@Override
	public CachedRowSetImpl searchValidInventoryUploadData(String batchNumber,String attributeNames,Boolean status) {
		// TODO Auto-generated method stub
		 Connection con = null;
		 ResultSet rs = null;
		 CachedRowSetImpl cachedRowSetImpl=null;
		if (isTraceLevel()) {
			Logger.logTrace(MODULE, "inside searchValidInventoryUploadData");
		}
	try{	
		javax.sql.DataSource ds = getDataSource(DataSourceConstant.INVENTORY_DS);
       
        if(ds != null){
             con = ds.getConnection();
        }

        StringBuffer inventorysql=new StringBuffer("");

        if(isTraceLevel())
        Logger.logTrace(MODULE, " Attribute names from facade:"+attributeNames);
		if(status){
			
		if(attributeNames==null || attributeNames.isEmpty()) {
			
			inventorysql =inventorysql.append("select b.inventoryno,f.BATCHNUMBER as \"Batch No\",d.name as Warehouse,e.RESOURCENUMBER as \"Resource Number\" from tblminventory b " +
					" inner join TBLMBATCH f on b.batchid=f.batchid " +
					" inner join TBLMWAREHOUSE d on d.warehouseid = b.warehouseid " +
					" inner join TBLMRESOURCE e on e.resourceid = b.resourceid" );
			
			inventorysql=inventorysql.append( " where f.BATCHNUMBER='"+batchNumber+"' ");
			
		} else {
			
			inventorysql =inventorysql.append( "select * from ( with pivot_data AS( "
					+" 	select b.inventoryno,f.BATCHNUMBER as \"Batch No\",d.name as Warehouse,e.RESOURCENUMBER as \"Resource Number\", a.attributeid,a.value  from tbltattributetrans a "
					+"  inner join tblminventory b on b.inventoryid= a.referenceid  "
					+" inner join TBLMBATCH f on b.batchid=f.batchid  "
					+"  inner join TBLMWAREHOUSE d on d.warehouseid = b.warehouseid "
					+ " inner join TBLMRESOURCE e on e.resourceid = b.resourceid "
					+ "  where a.tablerefname is null and a.attributeid in  "
					+ "  (select attributeid from tblmattribute where attributerel = 'Resource')  ");
			
			inventorysql=inventorysql.append( " and f.BATCHNUMBER='"+batchNumber+"') ");
			inventorysql=inventorysql.append(" select * from pivot_data PIVOT ( MAX (value ) "
					+" for attributeid in ( "+attributeNames+" ) ) )");
		}
			
        if(isTraceLevel())
			Logger.logTrace(MODULE, " Final validInventory to be called:"+inventorysql.toString());
		} else{
			 inventorysql = inventorysql.append("select * from ( with pivot_data AS( "
					+ "  select b.id,f.BATCHNUMBER as \"Batch No\",b.WAREHOUSENAME as Warehouse,b.ITEMREFNAME as \"Resource Number\", a.attributeid,a.value ,b.errordesc as Remarks  " 
					+"  from TBLMINVALIDINVENTORY b  "
					+ " left join tbltattributetrans a on b.id= a.referenceid and a.tablerefname = 'TBLMINVALIDINVENTORY' and a.attributeid in (select attributeid from tblmattribute where attributerel = 'Resource')  "
					+ " inner join TBLMBATCH f on b.batchid=f.batchid  ");
					

			inventorysql =inventorysql.append( " where  f.BATCHNUMBER='" + batchNumber + "') ");
			inventorysql =inventorysql.append( " select * from pivot_data PIVOT ( MAX (value ) "
					+ " for attributeid in ( " + attributeNames + " ) ) )");
			 if(isTraceLevel())
			Logger.logTrace(MODULE, "Final InvalidvalidInventory to be called:"+ inventorysql.toString());
			}
		if(con!=null) {
			 String query = inventorysql.toString();
			PreparedStatement stat =con.prepareStatement(query);
			rs = stat.executeQuery(); 
			cachedRowSetImpl=new CachedRowSetImpl();
			cachedRowSetImpl.populate(rs);
			stat.close();
		}
	}catch(SQLException sqlException){
		sqlException.printStackTrace();
	}
	catch(Exception e){
		e.printStackTrace();
		
		
	}finally{
		try {
			if(con!=null)
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  
	
//		Query query = getEntityManager().createNativeQuery(sql);
//		rs = query.getResultList();
	//	Logger.logDebug(MODULE, "Size of result:" );

		return cachedRowSetImpl;
	}
	
	
	/**
	 * Transfer Inventory
	 * @author yash.kapasi
	 * @param SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO.
	 * @param IBLSession iblSession.
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute( TransactionAttributeType.REQUIRED )
	public void transferInventory(SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO,IBLSession iblSession) throws CreateBLException{
		
		try {
			
			WarehouseData warehouseData = new WarehouseData();
			warehouseData.setWarehouseId(searchWarehouseInventoryStatusVO.getCurrentWarehouseId());
			ItemData itemData = new ItemData();
			itemData.setItemId(searchWarehouseInventoryStatusVO.getResourceId());
			
			Map<String,Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("warehouseData", warehouseData);
			fieldValueMap.put("resource", itemData);
			List filterList = getFilterDataBy(EntityConstants.WAREHOUSEINVENTORYSTATUS_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				WarehouseInventoryStatusData warehouseInventoryStatusData = (WarehouseInventoryStatusData)filterList.get(0);
				warehouseInventoryStatusData.setAvailable(warehouseInventoryStatusData.getAvailable() - searchWarehouseInventoryStatusVO.getTotalQty());
				if(warehouseInventoryStatusData.getAvailable() < 0){
					throw new CreateBLException("Sorry. You can't transfer inventories more than avaialble");
				}
				
				getEntityManager().merge(warehouseInventoryStatusData);

				
				warehouseData = new WarehouseData();
				warehouseData.setWarehouseId(searchWarehouseInventoryStatusVO.getNewWarehouseId());
				fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("warehouseData", warehouseData);
				fieldValueMap.put("resource", itemData);
				filterList = getFilterDataBy(EntityConstants.WAREHOUSEINVENTORYSTATUS_DATA, fieldValueMap);
				WarehouseInventoryStatusData warehouseInventoryStatusData2 = null;
				if(filterList != null && !filterList.isEmpty()){
					warehouseInventoryStatusData2 = (WarehouseInventoryStatusData)filterList.get(0);
					warehouseInventoryStatusData2.setAvailable(warehouseInventoryStatusData2.getAvailable() + searchWarehouseInventoryStatusVO.getTotalQty());
					getEntityManager().merge(warehouseInventoryStatusData2);
				}else{
					// create new entry
					warehouseInventoryStatusData2 = new WarehouseInventoryStatusData();
					warehouseInventoryStatusData2.setWarehouseData(warehouseData);
					warehouseInventoryStatusData2.setResource(itemData);
					warehouseInventoryStatusData2.setAvailable(searchWarehouseInventoryStatusVO.getTotalQty());
					warehouseInventoryStatusData2.setUpdatedby(iblSession.getSessionUserId());
					warehouseInventoryStatusData2.setUpdatedate(getCurrentTimestamp());
					
					getEntityManager().persist(warehouseInventoryStatusData2);
				}
				
				// make entry also in warehouseInventoryStatusHistory
				String remark = "Transfer Inventory :"+searchWarehouseInventoryStatusVO.getTotalQty()+" of Resource :"+warehouseInventoryStatusData.getResource().getName()
						+ " from :"+warehouseInventoryStatusData.getWarehouseData().getName()+" To :"+warehouseInventoryStatusData2.getWarehouseData().getName();
				
				WarehouseInventoryStatusHistoryData warehouseInventoryStatusHistoryData1 = 
						InventoryManagementUtil.prepareWarehouseInventoryStatusHistoryData(warehouseInventoryStatusData, 
								warehouseInventoryStatusData.getWarehouseData(), warehouseInventoryStatusData2.getWarehouseData(), 
								warehouseInventoryStatusData.getResource(), searchWarehouseInventoryStatusVO.getTotalQty(), remark, iblSession);
				
				WarehouseInventoryStatusHistoryData warehouseInventoryStatusHistoryData2 = 
						InventoryManagementUtil.prepareWarehouseInventoryStatusHistoryData(warehouseInventoryStatusData2, 
								warehouseInventoryStatusData.getWarehouseData(), warehouseInventoryStatusData2.getWarehouseData(), 
								warehouseInventoryStatusData.getResource(), searchWarehouseInventoryStatusVO.getTotalQty(), remark, iblSession);
				
				getEntityManager().persist(warehouseInventoryStatusHistoryData1);
				getEntityManager().persist(warehouseInventoryStatusHistoryData2);
				
				getEntityManager().flush();
				
				//Audit Entry
				Map<String,Object> mapAudit = new HashMap<String, Object>();
        		mapAudit.put(AuditTagConstant.FROMWAREHOUSE,warehouseInventoryStatusData.getWarehouseData().getName());
        		mapAudit.put(AuditTagConstant.TOWAREHOUSE,warehouseInventoryStatusData2.getWarehouseData().getName());
        		mapAudit.put(AuditTagConstant.RESOURCE,warehouseInventoryStatusData2.getResource().getName());
        		mapAudit.put(AuditTagConstant.TRANSFERQTY,searchWarehouseInventoryStatusVO.getTotalQty());
        		addToAuditDynamicMessage(AuditConstants.TRANSFER_INVENTORY, "Transfer Inventory",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, iblSession);
			}
			
		}catch(CreateBLException ex){
			throw ex;
		}catch (Exception e) {
			e.printStackTrace();
			throw new CreateBLException("Transfer Inventories failed due to some reason ");
		}
		
	}

	/**
	 * Get Inventory Details from Inventory Request Data, Attribute Names and no. of records
	 * @author yash.kapasi
	 * @param InventoryDetailsRequestData requestData.
	 * @param String attributeNames.
	 * @param int noOfRecords.
	 * @return CachedRowSetImpl cachedRowSetImpl
	 * @throws SearchBLException
	 */
	@Override
	public CachedRowSetImpl getInventoryDetails(InventoryDetailsRequestData requestData, String attributeNames,int noOfRecords)
			throws SearchBLException {
		
		
		Connection con = null;
		ResultSet rs = null;
		CachedRowSetImpl cachedRowSetImpl = null;
		if (isTraceLevel()) {
			Logger.logTrace(MODULE, "inside getInventoryDetails");
		}
		
		try {
			javax.sql.DataSource ds = getDataSource(DataSourceConstant.INVENTORY_DS);

			if (ds != null) {
				con = ds.getConnection();
			}

			StringBuffer inventorysql = new StringBuffer("");
			StringBuffer attributesql = new StringBuffer("");

			Logger.logTrace(MODULE, "Attribute names from facade:"+ attributeNames);
       
			boolean validate  = false;
			
			if(null == requestData){
				return cachedRowSetImpl;
			}
			
			if(requestData.getAttributeVOs()!=null && !requestData.getAttributeVOs().isEmpty()) {
				for(InventoryAttributeVO attributeVO : requestData.getAttributeVOs()) {
					if(attributeVO.getAttributeName()!=null && !attributeVO.getAttributeName().isEmpty() && 
							attributeVO.getAttributeValue()!=null && !attributeVO.getAttributeValue().isEmpty()) {
						attributesql= attributesql.append(" and name='"+attributeVO.getAttributeName()+"' and a.value='"+attributeVO.getAttributeValue()+"' ");
					}
				}
			}
			
			
			inventorysql = inventorysql.append("select * from ( with pivot_data AS " +
					" ( select b.inventoryno,i.name as status,e.name as resourceName,f.BATCHNUMBER,b.cutromerrefid,b.partnerrefid,d.name as Warehouse, " +
					" e.referenceid as ResourceReferance,e.model,h.name as resourceType, a.attributeid,a.value " +
					" from tbltattributetrans a   inner join tblminventory b on b.inventoryid= a.referenceid " +
					" inner join TBLMBATCH f on b.batchid=f.batchid " +
					" inner join TBLMWAREHOUSE d on d.warehouseid = b.warehouseid " +
					" inner join TBLMRESOURCE e on e.resourceid = b.resourceid " +
					" inner join tblsresourcetype h on h.resourcetypeid = e.resourcetypeid " +
					" inner join tblsinventorystatus i on i.inventorystatusid = b.statusid " +
					" where a.tablerefname is null and a.attributeid in " +
					" (select attributeid from tblmattribute where attributerel = 'Resource' "+attributesql.toString()+") ");
			
			
			if(requestData.getInventoryStatus()!=null && !requestData.getInventoryStatus().isEmpty()) {
				inventorysql=inventorysql.append( " and upper(i.name)=upper('"+requestData.getInventoryStatus()+"') ");
			}
			
			
			if(requestData.getInventoryNumber()!=null && !requestData.getInventoryNumber().isEmpty()) {
				inventorysql=inventorysql.append( " and b.inventoryno='"+requestData.getInventoryNumber()+"' ");
				validate = true;
			}
			if(!validate) {
				if(requestData.getResourceId()!=null && !requestData.getResourceId().isEmpty()) {
					inventorysql=inventorysql.append( " and e.resourceNumber='"+requestData.getResourceId()+"' ");
					validate = true;
				}if(requestData.getWareHouseName()!= null && !requestData.getWareHouseName().isEmpty()){
					inventorysql=inventorysql.append( " and d.name='"+requestData.getWareHouseName()+"'");
					validate = true;
				}
				if(requestData.getWareHouseCode()!= null && !requestData.getWareHouseCode().isEmpty()){
					inventorysql=inventorysql.append( " and d.warehouseCode='"+requestData.getWareHouseCode()+"'");
					validate = true;
				}
			}
			if(!validate) {
				if(requestData.getWareHouseName()!=null && !requestData.getWareHouseName().isEmpty() && 
						requestData.getResourceName()!=null && !requestData.getResourceName().isEmpty() && 
						requestData.getResourceType()!=null && !requestData.getResourceType().isEmpty() && 
						requestData.getModel()!=null && !requestData.getModel().isEmpty()) {
					
					inventorysql=inventorysql.append( " and d.name='"+requestData.getWareHouseName()+"' and " +
							" e.name='"+requestData.getResourceName()+"' and " +
							" h.name='"+requestData.getResourceType()+"' and " +
							" e.model='"+requestData.getModel()+"'  ");
					
					validate = true;
				}
			}
			
			if(requestData.getTransferStatus() != null && requestData.getTransferStatus().equals("")){
				inventorysql=inventorysql.append( " and b.TRANSFERINVENTORYSTATUS is null");
			}
			
			inventorysql=inventorysql.append(" )  select * from pivot_data PIVOT ( MAX (value )  for attributeid " +
					" in ( " + attributeNames + " ) ) )");
			
			if(noOfRecords > 0){
				inventorysql=inventorysql.append( " where rownum <="+noOfRecords);
			}
			
			Logger.logTrace(MODULE, "Query-->"+inventorysql);
			
			/*
			 * if(status){ inventorysql = "select * from ( with pivot_data AS( "
			 * +
			 * " 	select f.BATCHNUMBER,d.name as Warehouse,e.referenceid as ResourceReferance, a.attributeid,a.value  from tbltattributetrans a "
			 * +"  inner join tblminventory b on b.inventoryid= a.referenceid  "
			 * +" inner join TBLMBATCH f on b.batchid=f.batchid  "
			 * +"  inner join TBLMWAREHOUSE d on d.warehouseid = b.warehouseid "
			 * + " inner join TBLMRESOURCE e on e.resourceid = b.resourceid " +
			 * "  where a.tablerefname is null and a.attributeid in  " +
			 * "  (select attributeid from tblmattribute where attributerel = 'Inventory')  "
			 * ;
			 * 
			 * inventorysql+= " and f.BATCHNUMBER='"+batchNumber+"') ";
			 * inventorysql+=" select * from pivot_data PIVOT ( MAX (value ) "
			 * +" for attributeid in ( "+attributeNames+" ) ) )";
			 * 
			 * Logger.logTrace(MODULE,
			 * " Final validInventory to be called:"+inventorysql); } else{
			 * inventorysql = "select * from ( with pivot_data AS( " +
			 * "  select f.BATCHNUMBER,b.WAREHOUSENAME as Warehouse,b.ITEMREFNAME as ResourceReferance, a.attributeid,a.value ,b.errordesc as Remarks  "
			 * +"  from tbltattributetrans a  " +
			 * "   inner join TBLMINVALIDINVENTORY b on b.id= a.referenceid    "
			 * + " inner join TBLMBATCH f on b.batchid=f.batchid  " +
			 * "  where a.tablerefname is not null and a.attributeid in  " +
			 * "  (select attributeid from tblmattribute where attributerel = 'Inventory')  "
			 * ;
			 * 
			 * inventorysql += " and f.BATCHNUMBER='" + batchNumber + "') ";
			 * inventorysql += " select * from pivot_data PIVOT ( MAX (value ) "
			 * + " for attributeid in ( " + attributeNames + " ) ) )";
			 * 
			 * Logger.logTrace(MODULE,
			 * "Final InvalidvalidInventory to be called:"+ inventorysql); }
			 */

			if(con!=null) {
				String query = inventorysql.toString();
				PreparedStatement stat = con.prepareStatement(query);
				rs = stat.executeQuery();
				cachedRowSetImpl = new CachedRowSetImpl();
				cachedRowSetImpl.populate(rs);
				stat.close();
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if(con!=null)
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return cachedRowSetImpl;
	}

	
	
	/**
	 * Get Inventory Details from Inventory Transfer Data and Attribute Names
	 * @author yash.kapasi
	 * @param SearchTransferInventory searchTransferInventory.
	 * @param String attributeNames.
	 * @return CachedRowSetImpl cachedRowSetImpl
	 * @throws SearchBLException
	 */
	@Override
	public CachedRowSetImpl getInventoryDetails(String attributeNames,SearchTransferInventory searchTransferInventory)
			throws SearchBLException {
		
		
		Connection con = null;
		ResultSet rs = null;
		CachedRowSetImpl cachedRowSetImpl = null;
		if (isTraceLevel()) {
			Logger.logTrace(MODULE, "inside getInventoryDetails");
		}
		
		try {
			javax.sql.DataSource ds = getDataSource(DataSourceConstant.INVENTORY_DS);

			if (ds != null) {
				con = ds.getConnection();
			}
			if(searchTransferInventory == null){
				return cachedRowSetImpl;
			}
			
			String inventorysql = null;
			String attributesql = "";

			 if(isTraceLevel())
			Logger.logTrace(MODULE, "Attribute names from facade:"+ attributeNames);
       
			if(attributeNames==null || attributeNames.isEmpty()) {
				
				inventorysql = "select z.transferstatus,z.remark,b.inventoryno,i.name as status, " +
						" e.name as resourceName,f.BATCHNUMBER,b.cutromerrefid,b.partnerrefid,d.name as Warehouse, " +
						" e.referenceid as ResourceReferance,e.model,h.name as resourceType  from tblminventory b " +
						" inner join TBLMBATCH f on b.batchid=f.batchid " +
						" inner join TBLMWAREHOUSE d on d.warehouseid = b.warehouseid " +
						" inner join TBLMRESOURCE e on e.resourceid = b.resourceid " +
						" inner join tblsresourcetype h on h.resourcetypeid = e.resourcetypeid " +
						" inner join tblsinventorystatus i on i.inventorystatusid = b.statusid " +
						" inner join tblttransferorderdetail z on z.inventoryno = b.inventoryno " ;
				
				
				boolean isAnd = false;
				if(searchTransferInventory.getOrderId() != null){
					inventorysql+= "  where z.transferorderid = "+searchTransferInventory.getOrderId();
					isAnd = true;
				}
				if(searchTransferInventory.getOrderStatus() != null){
					
					if(searchTransferInventory.getOrderStatus().equalsIgnoreCase("Accepted")){
						if(isAnd) {
							inventorysql+= " and ";
						}
						inventorysql+= " z.transferstatus = 'ACCEPTED'";
					}else if(searchTransferInventory.getOrderStatus().equalsIgnoreCase("Rejected")){
						if(isAnd) {
							inventorysql+= " and ";
						}
						inventorysql+= " z.transferstatus = 'REJECTED'";
					}
				}
				
				if(isTraceLevel())
					Logger.logTrace(MODULE, "Query-->"+inventorysql);
				
			} else {
				
				inventorysql = "select * from ( with pivot_data AS " +
						" ( select z.transferstatus,z.remark,b.inventoryno,i.name as status,e.name as resourceName,f.BATCHNUMBER,b.cutromerrefid,b.partnerrefid,d.name as Warehouse, " +
						" e.referenceid as ResourceReferance,e.model,h.name as resourceType, a.attributeid,a.value " +
						" from tbltattributetrans a   inner join tblminventory b on b.inventoryid= a.referenceid " +
						" inner join TBLMBATCH f on b.batchid=f.batchid " +
						" inner join TBLMWAREHOUSE d on d.warehouseid = b.warehouseid " +
						" inner join TBLMRESOURCE e on e.resourceid = b.resourceid " +
						" inner join tblsresourcetype h on h.resourcetypeid = e.resourcetypeid " +
						" inner join tblsinventorystatus i on i.inventorystatusid = b.statusid " +
						" inner join tblttransferorderdetail z on z.inventoryno = b.inventoryno"+
						" where a.tablerefname is null and a.attributeid in " +
						" (select attributeid from tblmattribute where attributerel = 'Resource' "+attributesql+") ";
				
				if(searchTransferInventory.getOrderId() != null){
					inventorysql+= " and z.transferorderid = "+searchTransferInventory.getOrderId();
				}if(searchTransferInventory.getOrderStatus() != null){
					
					if(searchTransferInventory.getOrderStatus().equalsIgnoreCase("Accepted")){
						inventorysql+= " and z.transferstatus = 'ACCEPTED'";
					}else if(searchTransferInventory.getOrderStatus().equalsIgnoreCase("Rejected")){
						inventorysql+= " and z.transferstatus = 'REJECTED'";
					}
				}
				
				
				inventorysql+=" )  select * from pivot_data PIVOT ( MAX (value )  for attributeid " +
						" in ( " + attributeNames + " ) ) )";
				
				if(isTraceLevel())
					Logger.logTrace(MODULE, "Query-->"+inventorysql);
			}
			 
			
			
			if(con!=null) {
				
				PreparedStatement stat = con.prepareStatement(inventorysql);
				rs = stat.executeQuery();
				cachedRowSetImpl = new CachedRowSetImpl();
				cachedRowSetImpl.populate(rs);
				stat.close();
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if(con!=null)
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return cachedRowSetImpl;
	}
	
	/**
	 * Get Inventory Status from warehouse Data and resourceId
	 * @author yash.kapasi
	 * @param WarehouseData warehouseData.
	 * @param Long resourceId.
	 * @return int currentStatus
	 */
	public int getStatusOfInventory(WarehouseData warehouseData,Long resourceId){
		int currentStatus = 0;
		 if(isTraceLevel())
		Logger.logTrace(MODULE, "getStatusOfInventory method called");
		try {
			
			Query que = getEntityManager().createQuery("from InventoryData where warehouseId = :warehouseId and itemId = :itemId and inventoryStatusId = :inventoryStatusId");
		 	que.setParameter("warehouseId", warehouseData.getWarehouseId());
		 	que.setParameter("itemId", resourceId);
		 	que.setParameter("inventoryStatusId", InventoryStatusConstants.AVAILABLE);
		 	
		 	InventoryData data = (InventoryData)que.setMaxResults(1).getSingleResult();
		 
			if(data != null){
				Logger.logDebug(MODULE, "got existing record");
				currentStatus = InventoryStatusConstants.AVAILABLE;
			}
			
		}catch(NoResultException ex){
//			ex.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return currentStatus;
	}
	
	/**
	 * Transfer Inventory in System
	 * @author yash.kapasi
	 * @param {@link List}<{@link InventoryDetailVO}> inventoryDetailVOs.
	 * @param IBLSession iblSession.
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute( TransactionAttributeType.REQUIRED )
	public void transferInventory(List<InventoryDetailVO> inventoryDetailVOs,IBLSession iblSession) throws CreateBLException{
		
		if(isTraceLevel()) {
			Logger.logTrace(MODULE, "transferInventory method called");
			Logger.logTrace(MODULE, "Inside transferInventory method::inventoryDetailVOs:"+inventoryDetailVOs);
		}
		InventoryData inventoryData=new InventoryData();
		try{
			for(InventoryDetailVO inventoryDetailVO:inventoryDetailVOs){
				Logger.logTrace(MODULE, "Inside transferInventory method::inventoryDetailVO:"+inventoryDetailVO);
			WarehouseData warehouseData=new WarehouseData();
			warehouseData.setWarehouseId(inventoryDetailVO.getWarehouseId());
		
									
			Map<String,Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("inventoryNo", inventoryDetailVO.getInventoryId());
			fieldValueMap.put("warehouseId", inventoryDetailVO.getWarehouseId());
			//fieldValueMap.put("transferInventoryStatus", null);
			Logger.logTrace(MODULE, "Inside transferInventory method::fieldValueMap:"+fieldValueMap);
			List filterList = getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
			Logger.logTrace(MODULE, "Inside Session Bean transferInventory method::filterList:"+filterList);
			if(filterList != null && !filterList.isEmpty()){
				 inventoryData = (InventoryData)filterList.get(0);
				 
				 inventoryDetailVO.setStatusId(inventoryData.getInventoryStatusId());
				 if(inventoryData.getInventorySubStatusId()!=null) {
					 inventoryDetailVO.setSubStatusId(inventoryData.getInventorySubStatusId().intValue());
				 }
				 
				 Logger.logTrace(MODULE, "Inside transferInventory method::inventoryData:"+inventoryData);
				 if(!(inventoryData.getTransferInventoryStatus()!=null)){
					 Logger.logTrace(MODULE, "Inside transferInventory method::inventoryData.getTransferInventoryStatus():"+inventoryData.getTransferInventoryStatus());
					 	inventoryData.setTransferInventoryStatus(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_WAITING);
					 	
					 	fieldValueMap = new HashMap<String, Object>();
						fieldValueMap.put("alias", InventoryStatusConstants.SUBSTATUS_IN_TRANSFER);
						filterList = getFilterDataBy(EntityConstants.INVENTORYSUBSTATUS_DATA, fieldValueMap);
						InventorySubStatusData  subStatusData = (InventorySubStatusData) filterList.get(0);
						inventoryData.setInventorySubStatusId(subStatusData.getInventorySubStatusId());
					 	
					 	getEntityManager().merge(inventoryData);
				 }else{
					 throw new CreateBLException("Sorry. You can't transfer inventories,Transfer Status is not null ");
				 }
				
			}else{
				throw new CreateBLException("Sorry. You can't transfer inventories");
			}
		}
		getEntityManager().flush();
		}catch(Exception e){
			
			e.printStackTrace();
			
			getSessionContext().setRollbackOnly();
			
	    	throw new CreateBLException("Update Transfer Inventory Status  Failed, Reason : " + e.getMessage(), e);
		}
	}
	
	
	/**
	 * save Transfer Order Data
	 * @author yash.kapasi
	 * @param TransferOrderData.
	 * @param IBLSession 
	 * @return TransferOrderData  data
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute( TransactionAttributeType.REQUIRED )
	public TransferOrderData saveTransferOrderData(TransferOrderData data,IBLSession iblSession)throws CreateBLException{
			
		try{
				getEntityManager().persist(data);
				getEntityManager().flush();
				getEntityManager().refresh(data); 
			}catch(Exception e){
				
				e.printStackTrace();
				
				getSessionContext().setRollbackOnly();
				
		    	throw new CreateBLException("save Transfer Order  Failed, Reason : " + e.getMessage(), e);
			}
		return data;
		
	}
	
	
	
	/**
	 * save Transfer Order Detail Data
	 * @author yash.kapasi
	 * @param {@link List}<{@link TransferOrderDetailData}> transferOrderDetailDatas.
	 * @param {@link IBLSession }
	 * @return TransferOrderData  data
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute( TransactionAttributeType.REQUIRED )
	public void saveTransferOrderDetailData(List<TransferOrderDetailData> transferOrderDetailDatas,IBLSession iblSession)throws CreateBLException{
			
		try{
				for(TransferOrderDetailData transferOrderDetailData:transferOrderDetailDatas){
					getEntityManager().persist(transferOrderDetailData);
				}
				
				getEntityManager().flush();
				 
			}catch(Exception e){
				
				e.printStackTrace();
				
				getSessionContext().setRollbackOnly();
				
		    	throw new CreateBLException("save Transfer Order Detail  Failed, Reason : " + e.getMessage(), e);
			}
		
	}
	
	/**
	 * Get Inventory Detail by Batch
	 * @author yash.kapasi
	 * @param {@link Map}<{@link String,Integer}> batchMap.
	 * @param {@link SearchInventoryVO }
	 * @return {@link Map}<{@link String,List<String>}> batchInventoryMap.
	 * @throws CreateBLException
	 */
	@Override
	public Map<String,List<String>> getInventoryByBatch(Map<String, Integer> batchMap,SearchInventoryVO searchInventoryVO) throws CreateBLException{
		
		if(isInfoLevel())
		Logger.logInfo(MODULE, "getInventoryByBatch method called");
		Connection con = null;
		Map<String,List<String>> batchInventoryMap = new HashMap<String, List<String>>();
		try {
			javax.sql.DataSource ds = getDataSource(DataSourceConstant.INVENTORY_DS);

			if (ds != null) {
				con = ds.getConnection();
			}
			if(batchMap == null || searchInventoryVO == null){
				return batchInventoryMap;
			}
			
			String sql = "select inventoryno from tblminventory where batchId  = (select tblmbatch.batchid from tblmbatch where tblmbatch.batchnumber = ?)"
							 +" and tblminventory.transferinventorystatus is null and tblminventory.resourceid in (select tblminventory.resourceid from tblmresource where" 
							 +" tblmresource.resourcetypeid = ? and tblmresource.resourcesubtypeid = ?) and rownum <= ? ";
			
			PreparedStatement stat = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			for(Entry<String, Integer> entry: batchMap.entrySet()){
				String batchNo = entry.getKey();
				List<String> inventoryNolst = new ArrayList<String>();
				stat.clearParameters();
				
				stat.setString(1, batchNo);		
				stat.setLong(2, searchInventoryVO.getResourceTypeId());
				stat.setLong(3, searchInventoryVO.getResourceSubtypeId());
				stat.setInt(4, entry.getValue());
				ResultSet rs = stat.executeQuery();
				
				if(rs.next()){
					rs.last();
					if(rs.getRow() < entry.getValue() ){
						throw new CreateBLException("Transfer Qty is more than Avaialble Qty for BatchNo :"+batchNo);
					}
					rs.beforeFirst();
				}
				while(rs.next())
				{
//					Logger.logDebug(MODULE, "inventoryNo :"+rs.getString("inventoryno"));
					inventoryNolst.add(rs.getString("inventoryno"));
				}
				batchInventoryMap.put(batchNo, inventoryNolst);
			}
			stat.close();
		}catch(CreateBLException ex){
			throw ex;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return batchInventoryMap;
	}
	
	/**
	 * Search Transfer Inventory Summary
	 * @author yash.kapasi
	 * @param {@link SearchTransferInventory} searchTransferInventory.
	 * @return {@link List}<{@link TransferInventorySummaryViewVO}> inventorySummaryViewVOs.
	 */
	@Override
	public List<TransferInventorySummaryViewVO> searchTransferInventorySummary(SearchTransferInventory searchTransferInventory){
		
		Connection con = null;
		List<TransferInventorySummaryViewVO> inventorySummaryViewVOs = new ArrayList<TransferInventorySummaryViewVO>();
		try {
			javax.sql.DataSource ds = getDataSource(DataSourceConstant.INVENTORY_DS);

			if (ds != null) {
				con = ds.getConnection();
			}
			
			if(searchTransferInventory == null){
				return inventorySummaryViewVOs;
			}
			
			String hql = "from TransferOrderData where (fromWarehouseId = "+searchTransferInventory.getWarehouseId()+ " or toWarehouseId = "+searchTransferInventory.getWarehouseId()+")";
			
			if(searchTransferInventory.getOrderNo() != null){
				hql +=" and transferOrderNo = '"+searchTransferInventory.getOrderNo()+"'";
			}
			
			if(searchTransferInventory.getFromDate() != null && searchTransferInventory.getToDate() != null){
				hql +=" and (createdate between :fromdate  and :todate )";
			}else if(searchTransferInventory.getFromDate() != null){
				hql +=" and createdate >= :fromdate";
			}else if( searchTransferInventory.getToDate() != null){
				hql +=" and createdate <= :todate";
			}
			hql+=" order by transferOrderNo desc ";
			System.out.println(hql);
			Query que = getEntityManager().createQuery(hql);
			if(searchTransferInventory.getFromDate() != null && searchTransferInventory.getToDate() != null){
				que.setParameter("fromdate",new Date(searchTransferInventory.getFromDate().getTime()) );
				que.setParameter("todate",new Date(searchTransferInventory.getToDate().getTime()) );
			}else if(searchTransferInventory.getFromDate() != null){
				que.setParameter("fromdate",new Date(searchTransferInventory.getFromDate().getTime()) );
			}else if( searchTransferInventory.getToDate() != null){
				que.setParameter("todate",new Date(searchTransferInventory.getToDate().getTime()) );
			}
			
			List filterList = que.getResultList();
			String sql = "select transferorderid,(nvl(total,0)+nvl(ACCEPTED,0)+nvl(REJECTED,0)) as finaltotal,nvl(ACCEPTED,0) as ACCEPTEDTOTAL,nvl(REJECTED,0) as REJECTEDTOTAL   from("
					+" with pivot_data AS("
					+" select count(*) as total,transferorderid,nvl(transferstatus,'%') as transferstatus from tblttransferorderdetail" 
					+" where transferorderid = ? ";
			
			if(searchTransferInventory.isPartial()){
				sql+=" and transferstatus is null";
			}
			sql+=" group by transferorderid,transferstatus order by transferorderid)"
					+" select * from pivot_data PIVOT ( max(total )"
					+" for transferstatus in  ( '%' as total,'ACCEPTED' as ACCEPTED,'REJECTED' as REJECTED) ))";
			
			Logger.logTrace(MODULE, "SQL::"+sql);
			PreparedStatement stat = con.prepareStatement(sql);
			if(filterList != null && !filterList.isEmpty()){
				
				for(Object obj : filterList)
				{
					TransferOrderData transferOrderData = (TransferOrderData)obj;
					
					TransferInventorySummaryViewVO viewVO = new TransferInventorySummaryViewVO();
					viewVO.setFromWarehouseId(transferOrderData.getFromWarehouseId());
					viewVO.setFromWarehouseName(transferOrderData.getFromWarehouseData().getName());
					viewVO.setToWarehouseName(transferOrderData.getToWarehouseData().getName());
					viewVO.setOrderNo(transferOrderData.getTransferOrderNo());
					if(transferOrderData.getTransferOrderStatus()!=null) {
						viewVO.setOrderStatus(transferOrderData.getTransferOrderStatus().getName());
					}
					
					
					// call for get total,accepted,rejected
					stat.clearParameters();
					stat.setLong(1,transferOrderData.getTransferOrderId() );
					ResultSet rs = stat.executeQuery();
					if(rs.next()){
						
						viewVO.setTotal(rs.getInt("finaltotal"));
						viewVO.setAccepted(rs.getInt("ACCEPTEDTOTAL"));
						viewVO.setRejected(rs.getInt("REJECTEDTOTAL"));
						
					}
					
					
					int rejectedCount = 0;
					if(transferOrderData.getTransferOrderDetailDatas()!=null && !transferOrderData.getTransferOrderDetailDatas().isEmpty()) {
						for(TransferOrderDetailData data : transferOrderData.getTransferOrderDetailDatas()) {
							
							InventoryData inventoryData = null;
							String query = "select o from InventoryData o where o.inventoryNo='"+data.getInventoryNo()+"'";
							try {
								inventoryData = (InventoryData) getEntityManager().createQuery(query).getSingleResult();
							}catch(NoResultException e) {
							}catch(Exception e) {
								e.printStackTrace();
							}
							
							if(inventoryData!=null) {
								if(inventoryData.getInventoryStatusId()==2 && "REJECTED".equals(inventoryData.getTransferInventoryStatus())) {
									rejectedCount++;
								}
							}
						}
					}
					
					/*String query = "select  count(c.inventoryno) from tblmtransferorder a " +
							" inner join tblttransferorderdetail b on  a.transferorderid=b.transferorderid " +
							" inner join tblminventory c on c.inventoryno=b.inventoryno " +
							" where  c.statusid=2  and c.transferinventorystatus='REJECTED' and a.transferorderno='"+transferOrderData.getTransferOrderNo()+"'";
					
					BigDecimal count = (BigDecimal) getEntityManager().createNativeQuery(query).getSingleResult();
					*/
//					System.out.println(count);
					
					if(rejectedCount>0) {
						viewVO.setRejected(true);
					}
					
					
					inventorySummaryViewVOs.add(viewVO);
				}
			}
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return inventorySummaryViewVOs;
	}
	
	/**
	 * Search Transfer Inventory Summary
	 * @author yash.kapasi
	 * @param {@link SearchTransferInventory} searchTransferInventory.
	 * @param {@link TransferOrderData} transferOrderData.
	 * @param {@link List}<{@link InventoryDetailVO}> inventoryDetailList.
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute( TransactionAttributeType.REQUIRED )
	public void changeTransferInventoryStatus(PartialAcceptRejectTransferOrderVO searchTransferInventory,TransferOrderData transferOrderData,
			List<InventoryDetailVO> inventoryDetailList)throws CreateBLException{
		
		try {
			
			for(InventoryDetailVO inventoryDetailVO : inventoryDetailList){
				
				Map<String, Object> fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("inventoryNo", inventoryDetailVO.getInventoryId());
			
				List filterList = getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
				if(filterList != null && !filterList.isEmpty())
				{
					InventoryData inventoryData = (InventoryData)filterList.get(0);
					if(!inventoryData.getTransferInventoryStatus().equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_WAITING)){
						throw new CreateBLException("Sorry!! Some of inventory are transfered.Please Refresh again and search");
					}
					
					// call on detail table 
					changeTransferOrderDetailStatus(transferOrderData, inventoryDetailVO);
					if(inventoryDetailVO.getTransferStatus().equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_ACCEPTED)){
						inventoryData.setTransferInventoryStatus(null);
						//Commented  below line and added new line
						//inventoryData.setAccepted("Y");
						inventoryData.setInventorySubStatusId(Long.valueOf((InventoryStatusConstants.ACCEPTED_SUBSTATUS)));

						inventoryData.setWarehouseId(transferOrderData.getToWarehouseId());
					}else{
						inventoryData.setTransferInventoryStatus(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_REJECTED);
					}
					
					getEntityManager().merge(inventoryData);
				}
				
			}
			
			getEntityManager().flush();
		}catch(CreateBLException ex) {
			getSessionContext().setRollbackOnly();
			throw ex;
		}catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			throw new CreateBLException("Sorry!!! Accept/Reject Transfer Inventory Failed");
		}
		
	}
	
	/**
	 * Change Inventory Order Detail Status
	 * @author yash.kapasi
	 * @param {@link InventoryDetailVO} inventoryDetailVO.
	 * @param {@link TransferOrderData} transferOrderData.
	 */
	public void changeTransferOrderDetailStatus(TransferOrderData transferOrderData,InventoryDetailVO inventoryDetailVO){
		
		Map<String, Object> fieldValueMap = new HashMap<String, Object>();
		fieldValueMap.put("inventoryNo", inventoryDetailVO.getInventoryId());
		fieldValueMap.put("transferOrderId", transferOrderData.getTransferOrderId());
		
		List filterList = getFilterDataBy(EntityConstants.TRANSFERORDERDETAIL_DATA, fieldValueMap);
		if(filterList != null && !filterList.isEmpty()){
			TransferOrderDetailData transferOrderDetailData = (TransferOrderDetailData)filterList.get(0);
			
			if(inventoryDetailVO.getTransferStatus().equalsIgnoreCase(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_ACCEPTED)){
				transferOrderDetailData.setTransferStatus(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_ACCEPTED);
				transferOrderDetailData.setRemark(inventoryDetailVO.getRemark());
			}else{
				transferOrderDetailData.setTransferStatus(InventoryStatusConstants.TRANSFERINVENTORY_STATUS_REJECTED);
				transferOrderDetailData.setRemark(inventoryDetailVO.getRemark());
			}
			
			getEntityManager().merge(transferOrderDetailData);
		}
		
	}

	/**
	 * Check CPE Resource
	 * @author yash.kapasi
	 * @param {@link String} resourceId
	 * @param {@link String} warehouseName
	 * @param {@link String} warehouseCode
	 * @param {@link SystemParameter} systemParameter
	 * @return {@link List}<{@link InventoryData}> inventoryDatas.
	 * @throws SearchBLException
	 */
	@Override
	public List<InventoryData> checkCPEResource(String resourceId,
			String warehouseName,String warehouseCode,SystemParameter systemParameter) throws SearchBLException {
		
		try {
			
			Logger.logInfo(MODULE, "warehouseName :"+warehouseName);
			//Logger.logInfo(MODULE, "inventoryNumber :"+inventoryNumber);
			StringBuilder builder = new StringBuilder("(");
			
			if(systemParameter!=null) {
				String valueSource[]  = systemParameter.getValueSource().split(",");
				 Map<String, String> map = SystemInternalDataConversionUtil.convertStringArrayToMap(valueSource);
				List<String> list = SystemInternalDataConversionUtil.convertCommaSeparatedStringToList(systemParameter.getValue());
				if(list!=null && !list.isEmpty()) {
					boolean isTrue = false;
					for(String sysParam : list) {
						if(map.containsKey(sysParam)) {
							String id = map.get(sysParam);
							builder.append(" o.inventoryStatusId='"+id+"' or ");
							isTrue = true;
						}
					}
					if(isTrue) {
						builder = new StringBuilder(builder.toString().substring(0, builder.toString().lastIndexOf("or")));
					}
				}
			}
			builder.append(")");
			String query = "Select o from InventoryData o where "+builder.toString()+" and o.transferInventoryStatus is null and o.itemData.resourceNumber='"+resourceId+"' ";
			if(warehouseName!=null && !warehouseName.isEmpty()) {
				query+=" and o.warehousedata.name='"+warehouseName+"' ";
			}
			
			if(warehouseCode!=null && !warehouseCode.isEmpty()) {
				query+=" and o.warehousedata.warehouseCode='"+warehouseCode+"' ";
			}
			
			Logger.logTrace(MODULE, "Query CheckCPEResource : "+query);
			List<InventoryData> inventoryDatas = getEntityManager().createQuery(query).getResultList();
			for(InventoryData datas:inventoryDatas){
				System.out.println(datas.getInventoryNo());
			}
			return inventoryDatas;
			
		}catch(NoResultException ex){
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * change Inventory Status
	 * @author yash.kapasi
	 * @param {@link List}<{@link InventoryRequestVO}> inventoryRequestVOs.
	 * @param {@link IBLSession} iblSession
	 * @return {@link List}<{@link InventoryStatusVO}> inventoryStatusVOs.
	 * @throws UpdateBLException
	 */
	@Override
	public  List<InventoryStatusVO>  changeInventoryStatus(List<InventoryRequestVO>  inventoryRequestVOs,IBLSession iblSession) throws UpdateBLException {
		
		List<InventoryStatusVO> inventoryStatusVOs = new ArrayList<InventoryStatusVO>();
		for(InventoryRequestVO inventoryRequestVO : inventoryRequestVOs) {
			InventoryStatusVO statusVO = new InventoryStatusVO();
			
			String oldStatusWs = WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getOldStatus());
			String newStatusWs = WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getNewStatus());
			
			try {
				Logger.logTrace(MODULE, "init() changeInventoryStatus");
				
				if((inventoryRequestVO.getInventoryNo()==null || inventoryRequestVO.getInventoryNo().isEmpty()) 
						&& (inventoryRequestVO.getSerialNumber()!=null && !inventoryRequestVO.getSerialNumber().isEmpty())) {
					
					String query = "select  distinct b.inventoryno  from tbltattributetrans a " +
							" inner join tblminventory b on b.inventoryid= a.referenceid " +
							" inner join tblmattribute c on c.attributeid=a.attributeid " +
							" where c.name='Serial Number' and a.value='"+inventoryRequestVO.getSerialNumber()+"'";
					
					List result = null;
					try {
						result = getEntityManager().createNativeQuery(query).getResultList();
					}catch(Exception e) {
						e.printStackTrace();
					}
					 
					 if(result!=null && !result.isEmpty()) {
						 String inventoryNumber = (String) result.get(0);
						 inventoryRequestVO.setInventoryNo(inventoryNumber);
					 }
				}
				
				
				statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
				
				String oldStatusQuery = "select o from InventoryStatusData o where o.name=:name";
				String newStatusQuery = "select o from InventoryStatusData o where o.name=:name";
				InventoryStatusData oldStatusData = (InventoryStatusData) getEntityManager().createQuery(oldStatusQuery).setParameter("name", oldStatusWs).getSingleResult();
				InventoryStatusData newStatusData = (InventoryStatusData) getEntityManager().createQuery(newStatusQuery).setParameter("name", newStatusWs).getSingleResult();
				
				Map<String,Object> fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("inventoryNo", inventoryRequestVO.getInventoryNo());
				fieldValueMap.put("inventoryStatusId", oldStatusData.getInventoryStatusId().intValue());
				List filterData =  getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
				synchronized (statusObj) {
					
					if(filterData!=null && !filterData.isEmpty()) {
						
						Logger.logTrace(MODULE, "filterData size :: "+filterData.size());
						InventoryData inventoryData = (InventoryData)filterData.get(0);
						
						inventoryData.setInventoryStatusId(newStatusData.getInventoryStatusId().intValue());

						if(newStatusWs.equalsIgnoreCase(InventoryStatusConstants.RESERVED_STATUS)){
							inventoryData.setReserveddate(getCurrentTimestamp());
						}

						inventoryData.setUpdatedate(getCurrentTimestamp());
						inventoryData.setUpdatedby(iblSession.getSessionUserId());
						if(inventoryRequestVO.getOrderLineItemID()!=null){
							inventoryData.setCustomerRefId(inventoryRequestVO.getOrderLineItemID());
						}
						getEntityManager().merge(inventoryData);
						
						InventoryStatusLogData inventoryStatusLogData = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),(int)(long) oldStatusData.getInventoryStatusId(), (int)(long)newStatusData.getInventoryStatusId(), 
								oldStatusWs, newStatusWs, inventoryRequestVO.getRemarks(), iblSession);
						
						getEntityManager().persist(inventoryStatusLogData);
						
						if(InventoryStatusConstants.RELEASED_STATUS.equals(newStatusWs)) {
							
							
							Map<String,Object> fieldValueMap1 = new LinkedHashMap<String, Object>();
							fieldValueMap1.put("name", InventoryStatusConstants.FAULTY_STATUS);
							List filterStatusList = getFilterDataBy(EntityConstants.INVENTORYSTATUS_DATA, fieldValueMap1);
							if(filterStatusList!=null && !filterStatusList.isEmpty()) {
								InventoryStatusData inventoryStatusData = (InventoryStatusData) filterStatusList.get(0);
								
								InventoryStatusLogData inventoryStatusLogData1 = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),
										newStatusData.getInventoryStatusId().intValue(), inventoryStatusData.getInventoryStatusId().intValue(), 
										newStatusWs, inventoryStatusData.getName(), inventoryRequestVO.getRemarks(), iblSession);
								
								getEntityManager().persist(inventoryStatusLogData1);
								
								inventoryData.setInventoryStatusId(inventoryStatusData.getInventoryStatusId().intValue());
								getEntityManager().merge(inventoryData);
								
							}
						}
						
						
						statusVO.setInventoryStaus(newStatusData.getName());
						statusVO.setResponseCode("0");
						statusVO.setResponseMessage("Success");
						inventoryStatusVOs.add(statusVO);
					} else {
						inventoryStatusVOs.add(new InventoryStatusVO(inventoryRequestVO.getInventoryNo(),InventoryMgtResponseCode.responceCodeToE2ECode(-1L), "Not a Valid Inventory Number or Wrong Old Status"));
					}
				}
				
				
				Logger.logTrace(MODULE, "final() changeInventoryStatus");
				
			}catch (Exception e) {
				e.printStackTrace();
				getSessionContext().setRollbackOnly();
				inventoryStatusVOs.add(new InventoryStatusVO(inventoryRequestVO.getInventoryNo(),InventoryMgtResponseCode.responceCodeToE2ECode(-1L), "Error in changeStatus : "+e.getMessage()));
			}
		}
		
		getEntityManager().flush();
		
		return inventoryStatusVOs;
	}
	
	/**
	 * checks with Inventory that is the new status is valid to be changes for the inventory
	 * @author yash.kapasi
	 * @param {@link String} newStatus
	 * @param {@link String} oldStatus
	 * @return {@link boolean} result.
	 */
	@Override
	public boolean isValidInventoryChangeStatus(String oldStatus,
			String newStatus)  {
		boolean result  = false;
		try {
			
			String query = "select o from InventoryStatusTransition o where o.statusData.name=:statusName and o.allowedStatusData.name=:allowedStatusName";
			InventoryStatusTransition inventoryStatusTransition = (InventoryStatusTransition) getEntityManager().createQuery(query).
					setParameter("statusName", oldStatus).setParameter("allowedStatusName", newStatus).getSingleResult();
			if(inventoryStatusTransition!=null) {
				result  = true;
			}
			
			
		}catch (NoResultException e) {
			result  = false;
		}catch (Exception e) {
			e.printStackTrace();
			result  = false;
		}
		
		return result;
	}
	
	/**
	 * Reserves an Inventory in the System
	 * @author yash.kapasi
	 * @param {@link InventoryReserveData} inventoryReserveData
	 * @return {@link InventoryReserveData} inventoryReserveData.
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute( TransactionAttributeType.REQUIRED )
	public InventoryReserveData reserveInventory(InventoryReserveData inventoryReserveData) throws CreateBLException{
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside reserveInventory");
		}
		try {
			
			getEntityManager().persist(inventoryReserveData);
			
			getEntityManager().flush();
			
			Logger.logDebug(MODULE, "reservationId :"+inventoryReserveData.getReservationId());
		}catch (Exception e){
			
			e.printStackTrace();
			
			getSessionContext().setRollbackOnly();
		
	    	throw new CreateBLException("reserveInventory Failed, Reason : " + e.getMessage(), e);
			
		}
		return inventoryReserveData;
	}
	
	/**
	 * Reserves all Inventory Details in the System
	 * @author yash.kapasi
	 * @param {@link List}<{@link InventoryReserveDetailData}> inventoryReserveDetailDatas
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute( TransactionAttributeType.REQUIRED )
	public void reserveInventoryDetail(List<InventoryReserveDetailData> inventoryReserveDetailDatas)throws CreateBLException{
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside reserveInventoryDetail");
		}
		try {		
			
			for(InventoryReserveDetailData inventoryReserveDetailData:  inventoryReserveDetailDatas){
				getEntityManager().persist(inventoryReserveDetailData);
			}
			
			getEntityManager().flush();
			Logger.logDebug(MODULE, "reservation details saved");
		}catch (Exception e){
			
			e.printStackTrace();
			
			getSessionContext().setRollbackOnly();
		
	    	throw new CreateBLException("reserveInventory Failed, Reason : " + e.getMessage(), e);
			
		}
		
	}
	
	public int getTotalTransferInventoryByStatus(long warehouseId){
		
		int total = 0;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			javax.sql.DataSource ds = getDataSource(DataSourceConstant.INVENTORY_DS);

			if (ds != null) {
				con = ds.getConnection();
			}
			
			
			String query  = "select count(*) as total from tblmtransferorder a inner join tblttransferorderdetail b on b.transferorderid = a.transferorderid"+ 
								" and a.towarehouseid = ? and  b.transferstatus is null";
			
			try {
			
				
				if(con!=null) {
					
					stat = con.prepareStatement(query);
					stat.setLong(1, warehouseId);
					rs = stat.executeQuery();
					if(rs.next())
					{
						total = rs.getInt("total");
					}
//					rs.close();
				}
				
			}catch(SQLException exception) {
				exception.printStackTrace();
			}finally {
				 if(stat!=null)
					 stat.close();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(con != null) {
				try {
					con.close();
				} catch (Exception e2) {
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return total;
	}
	
	
	@Override
	public void updateInventory(InventoryData typeData)
			throws UpdateBLException {
		try {
			
			getEntityManager().merge(typeData);
			getEntityManager().flush();
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage());
		}
		
	}

	@Override
	public void persistInventoryStatusLog(
			InventoryStatusLogData inventoryStatusLogData)
			throws CreateBLException {
		
		try {
			
			getEntityManager().persist(inventoryStatusLogData);
			getEntityManager().flush();
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new CreateBLException(e.getMessage());
		}
		
		
	}

	/**
	 * Checks if the change Status Operation is allowed against the given Inventory or serial Number
	 * @author yash.kapasi
	 * @param {@link String} inventoryNo
	 * @param {@link String} serialNumber
	 * @return boolean result
	 */
	@Override
	public boolean isChangeStatusAllowed(String inventoryNo,String serialNumber) {
		
		try {
			
			if(inventoryNo!=null && !inventoryNo.isEmpty()) {
				String query = "select transferinventorystatus from tblminventory where inventoryno ='"+inventoryNo+"'";
				
				String result = (String) getEntityManager().createNativeQuery(query).getSingleResult();
				if(result==null) {
					return true;
				}
			} else if(serialNumber!=null && !serialNumber.isEmpty()) {
				String Secondquery = "select  distinct b.transferinventorystatus  from tbltattributetrans a " +
						 " inner join tblminventory b on b.inventoryid= a.referenceid " +
						 " inner join tblmattribute c on c.attributeid=a.attributeid " +
						 " where c.name='Serial Number' and a.value='"+serialNumber+"'";
				 
				 String Secondresult = (String) getEntityManager().createNativeQuery(Secondquery).getSingleResult();
				 if(Secondresult==null) {
					 return true;
				 }
			}
			
		}catch(NoResultException e) {
			return false;
		}catch(Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * Search Inventory Operation with various Search Combinations
	 * @author yash.kapasi
	 * @param {@link SearchInventoryVO} inventoryDetailVO
	 * @param {@link String} serialNumber
	 * @return {@link CachedRowSetImpl} result
	 */
	@Override
	 public CachedRowSetImpl searchInventory(SearchInventoryVO inventoryDetailVO) {
		// TODO Auto-generated method stub
			
		 Connection con = null;
		 ResultSet rs = null;
		 CachedRowSetImpl cachedRowSetImpl=null;
				if(isTraceLevel()){
					Logger.logTrace(MODULE, "inside searchInventory");
				}
				
			try { 
				javax.sql.DataSource ds = getDataSource(DataSourceConstant.INVENTORY_DS);
				  
		        if(ds != null){
		             con = ds.getConnection();
		        }

		        StringBuffer inventorysql=new StringBuffer("");
			
		        inventorysql =inventorysql.append("Select f.BATCHNUMBER,a.INVENTORYNO,b.name as status,d.name as warehouse,a.warehouseid,h.NAME as type,i.NAME as subtype, a.TRANSFERINVENTORYSTATUS as transferstatus "
		        									+" ,a.CREATEDATE,a.CREATEDBYSTAFFID,a.LASTMODIFIEDDATE,a.LASTMODIFIEDDATEBYSTAFFID,a.externalbatchnumber,a.GuranteeWarrantyMode,a.warrantydate,a.warrantytype   "
		        								+"	from  tblminventory a  " 
		        								+"	inner join tblsinventorystatus b on a.statusid = b.inventorystatusid "
		        								+" inner join TBLMBATCH f on a.batchid=f.batchid  "
		        								+" inner join tblmresource g on a.RESOURCEID=g.RESOURCEID  "
		        								+" inner join tblsresourcetype h on h.RESOURCETYPEID=g.RESOURCETYPEID  "
		        								+" inner join tblsresourcesubtype i on i.RESOURCESUBTYPEID=g.RESOURCESUBTYPEID  "
		        								+" inner join TBLMWAREHOUSE d on d.warehouseid = a.warehouseid " );
		       
				
					if(inventoryDetailVO.getAttributeId()!=null){
						 inventorysql= inventorysql.append( " inner join  tbltattributetrans e on a.inventoryid= e.referenceid  ");
						 inventorysql= inventorysql.append( " inner join TBLMATTRIBUTE c on c.attributeid=e.attributeid    ");
						 
					}
					if(inventoryDetailVO.getWareHouseId() != null ){
						inventorysql=inventorysql.append( "  and upper(d.WAREHOUSEID) = upper('"+inventoryDetailVO.getWareHouseId()+ "')");
					}
					if(inventoryDetailVO.getBatchId() !=null && !inventoryDetailVO.getBatchId().equalsIgnoreCase("")){
						inventorysql=inventorysql.append( " and upper(f.BATCHNUMBER) like  upper('"+inventoryDetailVO.getBatchId()+ "')");
										
					}
					if(inventoryDetailVO.getInventoryId() !=null && !inventoryDetailVO.getInventoryId().equalsIgnoreCase("")){
						inventorysql=inventorysql.append( " and upper(a.INVENTORYNO) like upper('"+inventoryDetailVO.getInventoryId()+ "')");
										
					}
					
					if(inventoryDetailVO.getExternalBatchNumber() !=null && !inventoryDetailVO.getExternalBatchNumber().equalsIgnoreCase("")){
						inventorysql=inventorysql.append( " and upper(a.externalbatchnumber) like upper('"+inventoryDetailVO.getExternalBatchNumber()+ "')");
										
					}
					
					
					boolean isMultiStatus = false;
					if(inventoryDetailVO.getInventoryStatuses()!=null && !inventoryDetailVO.getInventoryStatuses().isEmpty()) {
						isMultiStatus = true;
						StringBuilder builder  = new StringBuilder(" and ( ");
						for(Integer status : inventoryDetailVO.getInventoryStatuses()) {
							builder.append(" b.INVENTORYSTATUSID='"+status+"' or ");
						}
						builder = new StringBuilder(builder.toString().substring(0, builder.toString().lastIndexOf("or")));
						builder.append(" )");
						inventorysql=inventorysql.append(builder.toString());
					}
					
					if(!isMultiStatus) {
						if(inventoryDetailVO.getInventoryStatusId() !=null ){
							inventorysql=inventorysql.append( " and b.INVENTORYSTATUSID =  "+inventoryDetailVO.getInventoryStatusId()+ "");
							
						}
					}
					if(inventoryDetailVO.getTransferInventoryStatus()!=null ){
						if(inventoryDetailVO.getTransferInventoryStatus().equals("")){
							inventorysql=inventorysql.append( " and a.TRANSFERINVENTORYSTATUS is null  ");
						}
										
					}
					if(inventoryDetailVO.getResourceTypeId() !=null ){
						
						inventorysql=inventorysql.append( " and g.resourcetypeid=  "+inventoryDetailVO.getResourceTypeId()+ " ");
										
					
					if(inventoryDetailVO.getResourceSubtypeId() !=null ){
						inventorysql=inventorysql.append( " and  i.RESOURCESUBTYPEID = "+inventoryDetailVO.getResourceSubtypeId()+" ");
										
					}
				}
					
					
					if(inventoryDetailVO.getResourceNumber()!=null) {
						inventorysql=inventorysql.append( " and upper(g.RESOURCENUMBER) like upper('"+inventoryDetailVO.getResourceNumber()+ "')");
					}
					
					if(inventoryDetailVO.getResourceName()!=null) {
						inventorysql=inventorysql.append( " and upper(g.NAME) like upper('"+inventoryDetailVO.getResourceName()+ "')");
					}
					
					if(inventoryDetailVO.getAttributeId()!=null){
						inventorysql=inventorysql.append( "	and  e.tablerefname is null ");
						inventorysql=inventorysql.append( "  and upper(e.attributeid) = upper('"+inventoryDetailVO.getAttributeId()+ "')");
						inventorysql=inventorysql.append( "  and e.value like '"+inventoryDetailVO.getAttributeValue()+"'");
					}
					inventorysql=inventorysql.append("	order by inventoryid "); 
					
					Logger.logDebug(MODULE, "SQL prepared in sessionbean:"+inventorysql);
					if(con!=null) {
					 String query = inventorysql.toString();
						PreparedStatement stat =con.prepareStatement(query);
						rs = stat.executeQuery(); 
						cachedRowSetImpl=new CachedRowSetImpl();
						cachedRowSetImpl.populate(rs);
						stat.close();
					}
				}catch(SQLException sqlException){
					sqlException.printStackTrace();
				}
				catch(Exception e){
					e.printStackTrace();
					
					
				}finally{
					try {
						if(con!=null)
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				return cachedRowSetImpl;
	}
	
	@Override
	public OrderData saveOrderData(OrderData data,IBLSession iblSession)throws CreateBLException{
			
		try{
				getEntityManager().persist(data);
				getEntityManager().flush();
				getEntityManager().refresh(data);
				 
			}catch(Exception e){
				
				e.printStackTrace();
				
				getSessionContext().setRollbackOnly();
				
		    	throw new CreateBLException("save Place Order  Failed, Reason : " + e.getMessage(), e);
			}
		return data;
		
	}
	@Override
	public List<OrderData> searchPlaceOrderData(PlaceOrderVO placeOrderVO,IBLSession iblSession){
			
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside searchPlaceOrderData");
		}
		List<OrderData> data = null;
		try {
			
//			String hql = "from OrderData o where o.fromWarehouseId="+placeOrderVO.getFromwarehouseId();
//			
//			hql +=" order by o.createdate desc";
			
			String hql = "select o  from OrderData o where (o.fromWarehouseId = "+placeOrderVO.getFromwarehouseId()+ " or o.toWarehouseId = "+placeOrderVO.getTowarehouseId()+")";
			
			if(placeOrderVO.getOrderNo() != null){
				hql +=" and o.orderNo = '"+placeOrderVO.getOrderNo()+"'";
			}
			
			if(placeOrderVO.getFromDate() != null && placeOrderVO.getToDate() != null){
				hql +=" and (o.createdate between :fromdate  and :todate )";
			}else if(placeOrderVO.getFromDate() != null){
				hql +=" and o.createdate >= :fromdate";
			}else if( placeOrderVO.getToDate() != null){
				hql +=" and o.createdate <= :todate";
			}
			
			Query que = getEntityManager().createQuery(hql);
			if(placeOrderVO.getFromDate() != null && placeOrderVO.getToDate() != null){
				que.setParameter("fromdate",new Date(placeOrderVO.getFromDate().getTime()) );
				que.setParameter("todate",new Date(placeOrderVO.getToDate().getTime()) );
			}else if(placeOrderVO.getFromDate() != null){
				que.setParameter("fromdate",new Date(placeOrderVO.getFromDate().getTime()) );
			}else if( placeOrderVO.getToDate() != null){
				que.setParameter("todate",new Date(placeOrderVO.getToDate().getTime()) );
			}
			hql+=" order by o.orderId desc ";
			Logger.logTrace(MODULE, hql.toString());
			data = (List<OrderData>)que.getResultList();
			
			
//			Query query = getEntityManager().createQuery(hql);
//			data = (List<OrderData>)query.getResultList();
			
			Logger.logTrace(MODULE, data.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
		
	}

	@Override
	public boolean isChangeStatusAllowedWithSerialNumber(String serialNumber) {
		
		try {
		
			String query = "select  distinct a.referenceid  from tbltattributetrans a " +
					" inner join tblminventory b on b.inventoryid= a.referenceid " +
					" inner join tblmattribute c on c.attributeid=a.attributeid " +
					" where c.name='Serial Number' and a.value='"+serialNumber+"'";
			
			 List result = getEntityManager().createNativeQuery(query).getResultList();
			 
			 if(result!=null && result.size()==1) {
				return true;
			 }
			 

			 
			
		}catch(NoResultException e) {
			return false;
		}catch(Exception e) {
			return false;
		}
		return false;
		
	}

	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<InventoryStatusVO> changeInventoryStatusForAllocate(List<InventoryRequestVO> inventoryRequestVOs, IBLSession iblSession)
			throws UpdateBLException {
		
		
		List<InventoryStatusVO> inventoryStatusVOs = new ArrayList<InventoryStatusVO>();
		
		try {
			
			for(InventoryRequestVO inventoryRequestVO : inventoryRequestVOs) {
				InventoryStatusVO statusVO = new InventoryStatusVO();
				
				String oldStatusWs = WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getOldStatus());
				String newStatusWs = WSFacadeUtil.getStatusFromWSCode(inventoryRequestVO.getNewStatus());
				
				try {
					Logger.logTrace(MODULE, "init() changeInventoryStatus");
					
					if((inventoryRequestVO.getInventoryNo()==null || inventoryRequestVO.getInventoryNo().isEmpty()) 
							&& (inventoryRequestVO.getSerialNumber()!=null && !inventoryRequestVO.getSerialNumber().isEmpty())) {
						
						String query = "select  distinct b.inventoryno  from tbltattributetrans a " +
								" inner join tblminventory b on b.inventoryid= a.referenceid " +
								" inner join tblmattribute c on c.attributeid=a.attributeid " +
								" where c.name='Serial Number' and a.value='"+inventoryRequestVO.getSerialNumber()+"'";
						
						List result = null;
						try {
							result = getEntityManager().createNativeQuery(query).getResultList();
						}catch(Exception e) {
							e.printStackTrace();
						}
						 
						 if(result!=null && !result.isEmpty()) {
							 String inventoryNumber = (String) result.get(0);
							 inventoryRequestVO.setInventoryNo(inventoryNumber);
						 }
					}
					
					
					statusVO.setInventoryNumber(inventoryRequestVO.getInventoryNo());
					
					String oldStatusQuery = "select o from InventoryStatusData o where o.name=:name";
					String newStatusQuery = "select o from InventoryStatusData o where o.name=:name";
					InventoryStatusData oldStatusData = (InventoryStatusData) getEntityManager().createQuery(oldStatusQuery)
							.setParameter("name", oldStatusWs).getSingleResult();
					
					InventoryStatusData newStatusData = (InventoryStatusData) getEntityManager().createQuery(newStatusQuery)
							.setParameter("name", newStatusWs).getSingleResult();
					
					Map<String,Object> fieldValueMap = new HashMap<String, Object>();
					fieldValueMap.put("inventoryNo", inventoryRequestVO.getInventoryNo());
					fieldValueMap.put("inventoryStatusId", oldStatusData.getInventoryStatusId().intValue());
					List filterData =  getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
					synchronized (statusObj) {
						
						if(filterData!=null && !filterData.isEmpty()) {
							
							Logger.logTrace(MODULE, "filterData size :: "+filterData.size());
							InventoryData inventoryData = (InventoryData)filterData.get(0);
							
							inventoryData.setInventoryStatusId(newStatusData.getInventoryStatusId().intValue());

							if(newStatusWs.equalsIgnoreCase(InventoryStatusConstants.RESERVED_STATUS)){
								inventoryData.setReserveddate(getCurrentTimestamp());
							}

							inventoryData.setUpdatedate(getCurrentTimestamp());
							inventoryData.setUpdatedby(iblSession.getSessionUserId());

							getEntityManager().merge(inventoryData);
							//Added for to change inventory status from Available to Allocated
							if(oldStatusWs.equals( WSFacadeUtil.getStatusFromWSCode(InventoryStatusConstants.AVAILABLE))){
								Logger.logTrace(MODULE,"::::::::::::::oldStatusWs:::::::"+oldStatusWs);
								InventoryStatusLogData inventoryStatusLogData = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),(int)(long) oldStatusData.getInventoryStatusId(),InventoryStatusConstants.RESERVED, 
										oldStatusWs, InventoryStatusConstants.RESERVED_STATUS,inventoryRequestVO.getRemarks(), iblSession);
								getEntityManager().persist(inventoryStatusLogData);
								InventoryStatusLogData inventoryStatusLogData1 = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),InventoryStatusConstants.RESERVED,(int)(long)newStatusData.getInventoryStatusId(), 
										InventoryStatusConstants.RESERVED_STATUS, newStatusWs, inventoryRequestVO.getRemarks(), iblSession);
								getEntityManager().persist(inventoryStatusLogData1);
	
							}else{
							InventoryStatusLogData inventoryStatusLogData = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),(int)(long) oldStatusData.getInventoryStatusId(), (int)(long)newStatusData.getInventoryStatusId(), 
									oldStatusWs, newStatusWs, inventoryRequestVO.getRemarks(), iblSession);
							
							getEntityManager().persist(inventoryStatusLogData);
							}
							
								
							statusVO.setResponseCode("0");
							statusVO.setResponseMessage("Success");
							inventoryStatusVOs.add(statusVO);
						} else {
							inventoryStatusVOs.add(new InventoryStatusVO(inventoryRequestVO.getInventoryNo(),InventoryMgtResponseCode.responceCodeToE2ECode(-1L), "Not a Valid Inventory Number or Wrong Old Status"));
						}
					}
					
					
					Logger.logTrace(MODULE, "final() changeInventoryStatus");
					
				}catch (Exception e) {
					e.printStackTrace();
					inventoryStatusVOs.add(new InventoryStatusVO(inventoryRequestVO.getInventoryNo(),InventoryMgtResponseCode.responceCodeToE2ECode(-1L), "Error in changeStatus : "+e.getMessage()));
				}
			}
			
			for(InventoryStatusVO inventoryStatusVO : inventoryStatusVOs) {
				if(inventoryStatusVO.getResponseCode().startsWith("err") || inventoryStatusVO.getResponseCode().equals("-1")) {
					throw new Exception("Throwing to rollback");
				}
			}
			
			getEntityManager().flush();
			
			return inventoryStatusVOs;
			
			
			
		}catch(Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return inventoryStatusVOs;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderData> searchPlaceOrderDetail(PlaceOrderVO placeOrderVO,
			IBLSession blSession) throws SearchBLException {
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside searchPlaceOrderDetail");
		}
		
		try {
			String hql = "select o  from OrderData o where o.orderId is not null ";
			
			if(placeOrderVO.getOrderNo() != null){
				hql +=" and o.orderNo = '"+placeOrderVO.getOrderNo()+"'";
			}
			
			if(placeOrderVO.getFromDate() != null && placeOrderVO.getToDate() != null){
				hql +=" and (o.createdate between :fromdate  and :todate )";
			}else if(placeOrderVO.getFromDate() != null){
				hql +=" and o.createdate >= :fromdate";
			}else if( placeOrderVO.getToDate() != null){
				hql +=" and o.createdate <= :todate";
			}
			
			Query que = getEntityManager().createQuery(hql);
			if(placeOrderVO.getFromDate() != null && placeOrderVO.getToDate() != null){
				que.setParameter("fromdate",new Date(placeOrderVO.getFromDate().getTime()) );
				que.setParameter("todate",new Date(placeOrderVO.getToDate().getTime()) );
			}else if(placeOrderVO.getFromDate() != null){
				que.setParameter("fromdate",new Date(placeOrderVO.getFromDate().getTime()) );
			}else if( placeOrderVO.getToDate() != null){
				que.setParameter("todate",new Date(placeOrderVO.getToDate().getTime()) );
			}
			
			hql+=" order by o.orderId desc ";
			Logger.logTrace(MODULE, hql.toString());
			return  (List<OrderData>)que.getResultList();
			
			
			/*if(placeOrderVO.getOrderNo()!=null && !placeOrderVO.getOrderNo().isEmpty()) {
				return getEntityManager().createNamedQuery("OrderData.searchOrderDataByOrderNo")
						.setParameter("orderNo", placeOrderVO.getOrderNo()).getResultList();
			} else {
				return getEntityManager().createNamedQuery("OrderData.searchAllOrderData")
						.getResultList();
			}*/
			
			
			
		} catch(NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
		
		
		
	}

	@Override
	public boolean isAvailable(Long warehouseId, Long itemId) {
		
		try {
				
			List list = getEntityManager().createNamedQuery("InventoryData.isAvailable")
					.setParameter("warehouseId", warehouseId).setParameter("itemId", itemId).getResultList();
			
			
			if(list!=null && !list.isEmpty()) {
				return true;
			}
		}catch(NoResultException e) {
			return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}


	@Override
	public List<InventoryData> checkCPEInventory(String inventoryNumber,
			SystemParameter systemParameter) throws SearchBLException {
		// TODO Auto-generated method stub
try {
			
			Logger.logInfo(MODULE, "inventoryNumber :"+inventoryNumber);
			StringBuilder builder = new StringBuilder("(");
			
			if(systemParameter!=null) {
				String valueSource[]  = systemParameter.getValueSource().split(",");
				 Map<String, String> map = SystemInternalDataConversionUtil.convertStringArrayToMap(valueSource);
				List<String> list = SystemInternalDataConversionUtil.convertCommaSeparatedStringToList(systemParameter.getValue());
				if(list!=null && !list.isEmpty()) {
					boolean isTrue = false;
					for(String sysParam : list) {
						if(map.containsKey(sysParam)) {
							String id = map.get(sysParam);
							builder.append(" o.inventoryStatusId='"+id+"' or ");
							isTrue = true;
						}
					}
					if(isTrue) {
						builder = new StringBuilder(builder.toString().substring(0, builder.toString().lastIndexOf("or")));
					}
				}
			}
			builder.append(")");
			//added -start
			String query = "Select o from InventoryData o where "+builder.toString()+" and o.transferInventoryStatus is null and o.inventoryNo='"+inventoryNumber+"' ";
			
			Logger.logTrace(MODULE, "Query CheckCPEResource : "+query);
			List<InventoryData> inventoryDatas = getEntityManager().createQuery(query).getResultList();
			for(InventoryData datas:inventoryDatas){
				System.out.println(datas.getInventoryNo());
			}
			return inventoryDatas;
			
		}catch(NoResultException ex){
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public List<InventoryStatusResponseVO> releaseCPEResource(List<ReleaseInventoryVO> inventoryVos ,IBLSession iblSession) throws UpdateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside releaseCPEResource");
		}
		List<InventoryStatusResponseVO> responseVOList=new ArrayList<InventoryStatusResponseVO>();
		Logger.logInfo(MODULE, "inside releaseCPEResource");
		try {
			if(inventoryVos!=null && !inventoryVos.isEmpty()){
				for(ReleaseInventoryVO inventoryVo:inventoryVos){
					Map<String,Object> fieldValueMap = new HashMap<String, Object>();
					fieldValueMap.put("inventoryNo", inventoryVo.getInventoryNo());
					List filterList = getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
					if(filterList != null  && !filterList.isEmpty()){
						InventoryData inventoryData = (InventoryData) filterList.get(0);
						inventoryData.setUpdatedate(getCurrentTimestamp());
						inventoryData.setUpdatedby(iblSession.getSessionUserId());
						InventoryStatusResponseVO responseVO=new InventoryStatusResponseVO();
						if(inventoryData.getStatusData().getName().equals(InventoryStatusConstants.RESERVED_STATUS)){	
						
							responseVO.setInventoryNumber(inventoryData.getInventoryNo());
							responseVO.setInventoryStatus(InventoryStatusConstants.AVAILABLE_STATUS);
							
							InventoryStatusLogData inventoryStatusLogData = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),inventoryData.getInventoryStatusId(),InventoryStatusConstants.AVAILABLE, 
											inventoryData.getStatusData().getName(), InventoryStatusConstants.AVAILABLE_STATUS,"WS:releaseCPEResource", iblSession);
							inventoryData.setInventoryStatusId(InventoryStatusConstants.AVAILABLE);
							getEntityManager().merge(inventoryData);
							getEntityManager().persist(inventoryStatusLogData);
						}
								else if(inventoryData.getStatusData().getName().equals(InventoryStatusConstants.DELIVERED_STATUS)){
									responseVO.setInventoryNumber(inventoryData.getInventoryNo());
									responseVO.setInventoryStatus(InventoryStatusConstants.RELEASED_STATUS);
									InventoryStatusLogData inventoryStatusLogData1 = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),inventoryData.getInventoryStatusId(),InventoryStatusConstants.FAULTY, 
											inventoryData.getStatusData().getName(), InventoryStatusConstants.FAULTY_STATUS,"WS:releaseCPEResource", iblSession);
									InventoryStatusLogData inventoryStatusLogData2 = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),InventoryStatusConstants.FAULTY,InventoryStatusConstants.RELEASED, 
											InventoryStatusConstants.FAULTY_STATUS, InventoryStatusConstants.RELEASED_STATUS,"WS: releaseCPEResource", iblSession);
									inventoryData.setInventoryStatusId(InventoryStatusConstants.RELEASED);


									getEntityManager().merge(inventoryData);
									getEntityManager().persist(inventoryStatusLogData1);
									getEntityManager().persist(inventoryStatusLogData2);

								}
								else{
								responseVO.setInventoryNumber(inventoryData.getInventoryNo());
								responseVO.setInventoryStatus(InventoryStatusConstants.RELEASED_STATUS);
								InventoryStatusLogData inventoryStatusLogData1 = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),inventoryData.getInventoryStatusId(),InventoryStatusConstants.FAULTY, 
										inventoryData.getStatusData().getName(), InventoryStatusConstants.FAULTY_STATUS,"WS: releaseCPEResource", iblSession);
								InventoryStatusLogData inventoryStatusLogData2 = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),InventoryStatusConstants.FAULTY,InventoryStatusConstants.RELEASED, 
										InventoryStatusConstants.FAULTY_STATUS, InventoryStatusConstants.RELEASED_STATUS,"WS: releaseCPEResource", iblSession);
								inventoryData.setInventoryStatusId(InventoryStatusConstants.RELEASED);


								getEntityManager().merge(inventoryData);
								getEntityManager().persist(inventoryStatusLogData1);
								getEntityManager().persist(inventoryStatusLogData2);
									
								}
						//responseVO.setResponseCode("0");
						//responseVO.setResponseMessage("Success");
						responseVOList.add(responseVO);
					}
				}
			}
			
			getEntityManager().flush();
		}catch(Exception e){
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
	    	   throw new UpdateBLException("Change Inventory Status Failed, Reason : " + e.getMessage(), e);
		}
		return responseVOList;
	}


	@Override
	public List<InventoryStatusResponseVO> markCPEAsFaultyWithOwnerChange(List<CPEInventoryVO> inventoryVOs,String warehouseCode,IBLSession blSession)throws UpdateBLException{
		List<InventoryStatusResponseVO> responseVOList=new ArrayList<InventoryStatusResponseVO>();
		Logger.logInfo(MODULE, "inside markCPEAsFaultyWithOwnerChange");
		if(isTraceLevel()){
			Logger.logInfo(MODULE, "inside markCPEAsFaultyWithOwnerChange");
		}

		try{
			if(inventoryVOs!=null && !inventoryVOs.isEmpty()){
				for(CPEInventoryVO inventoryVo:inventoryVOs){
					Map<String,Object> fieldValueMap = new HashMap<String, Object>();
					fieldValueMap.put("inventoryNo", inventoryVo.getInventoryNo());
					List filterList = getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
					InventoryStatusResponseVO responseVO=new InventoryStatusResponseVO();
					if(filterList != null  && !filterList.isEmpty()){
						InventoryData inventoryData = (InventoryData) filterList.get(0);
						inventoryData.setUpdatedate(getCurrentTimestamp());
						inventoryData.setUpdatedby(blSession.getSessionUserId());
						Logger.logInfo(MODULE, "Old Status Name ::::::::"+inventoryData.getStatusData().getName());
						Map<String,Object> fieldValueMap1 = new HashMap<String, Object>();
						fieldValueMap1.put("warehouseCode",warehouseCode);
						List filterList1 = getFilterDataBy(EntityConstants.WAREHOUSE_DATA, fieldValueMap1);
						WarehouseData whData = (WarehouseData)filterList1.get(0);
						InventoryStatusLogData inventoryStatusLogData=null;
						if(inventoryData.getWarehousedata()!=null){
						 inventoryStatusLogData = InventoryManagementUtil.prepareInventoryStatusLogData(inventoryData.getInventoryId(),inventoryData.getInventoryStatusId(),InventoryStatusConstants.FAULTY, 
									inventoryData.getStatusData().getName(), InventoryStatusConstants.FAULTY_STATUS,"WS:markCPEAsFaulty with WareHouse Updated from "+inventoryData.getWarehousedata().getName()+" to "+whData.getName(), blSession);
							inventoryData.setInventoryStatusId(InventoryStatusConstants.FAULTY);
						}
						inventoryData.setWarehouseId(whData.getWarehouseId());
						getEntityManager().merge(inventoryData);
						getEntityManager().persist(inventoryStatusLogData);
						
						responseVO.setInventoryNumber(inventoryData.getInventoryNo());
						responseVO.setInventoryStatus(InventoryStatusConstants.FAULTY_STATUS);
						//responseVO.setResponseCode("0");
						//responseVO.setResponseMessage("Success");
						Logger.logInfo(MODULE,":::: responseVO"+responseVO.toString());
						responseVOList.add(responseVO);
					}
				}
				Logger.logInfo(MODULE,"::::::::::responseVOList"+responseVOList);
			}
			getEntityManager().flush();
		}catch(Exception e){
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
	    	   throw new UpdateBLException("Mark Inventory Status as Failed, Reason : " + e.getMessage(), e);
		}
	
		return responseVOList;
	}
//added for total count of eligible transferrable inventory 
	public long getTotaltransferrableInventory(Long warehouseId,Long resourceSubTypeId,Long resourceTypeId) throws SearchBLException{
		long total = 0;
		/*Connection con = null;
		ResultSet rs = null;
		PreparedStatement stat = null;
	*/
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside getTotaltransferrableInventory");
		}
		try{
				String Query = "select count(o)  from InventoryData o where o.warehousedata.warehouseId = :warehouseId  and o.inventoryStatusId='2' and o.transferInventoryStatus is null  ";
			
			if(resourceTypeId!=null){
				Query = Query+"  and  o.itemData.resourceTypeId = :resourceTypeId";
			}
			if(resourceSubTypeId!=null){
				Query = Query+" and o.itemData.resourceSubTypeId ='"+resourceSubTypeId+"'";  
			}
			total  =  (Long)getEntityManager().createQuery(Query)
							 .setParameter("warehouseId",warehouseId)
							 .setParameter("resourceTypeId",resourceTypeId).getSingleResult();
		}catch(NoResultException e) {
			e.printStackTrace();
			return total;
		}catch(Exception e) {
			e.printStackTrace();
			return total;
		}
		/*		catch(SQLException exception) {
				exception.printStackTrace();
			}finally {
				 if(stat!=null)
					 stat.close();
			}*/
		/*} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(con != null) {
				try {
					con.close();
				} catch (Exception e2) {
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}*/
		Logger.logTrace(MODULE, "Total Inventory count::"+total);
		return total;
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean isEligiblePlaceOrder(Long warehouseId, Long resourceTypeId,
			Long resourceSubTypeId, Long itemId) throws SearchBLException {
		
		
		boolean result  = true;
		try {
			
			String query = "select o from OrderData o where o.fromWarehouseId=:warehouseId and o.resourceTypeId=:resourceTypeId and o.orderStatusId in ('101','102','103') ";
			
			if(resourceSubTypeId!=null) {
				query = query + " and o.resourceSubTypeId='"+resourceSubTypeId+"' ";
			} else {
				query = query + " and o.resourceSubTypeId is null ";
			}
			
			if(itemId!=null) {
				query = query + " and o.itemId='"+itemId+"' ";
			} else {
				query = query + " and o.itemId is null ";
			}
			
			
			List<OrderData> orderDatas = getEntityManager().createQuery(query)
			.setParameter("warehouseId", warehouseId)
			.setParameter("resourceTypeId", resourceTypeId)
			.getResultList();
			
			if(orderDatas!=null && !orderDatas.isEmpty()) {
				result = false;
			}
			
			
		}catch (NoResultException e) {
			result  = true;
		}catch (Exception e) {
			e.printStackTrace();
			result  = true;
		}
		
		return result;
		
	}
	
	//Jahanvi Code Start
		/**
		 * Get Pending PlaceOrder Child
		 * @author jahanvi.patel
		 * @return {@link List}<{@link Object}> data.
		 * @throws CreateBLException
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<OrderData> getPendingPlaceOrderChild(String minDays, Long warehouseid){
			
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "inside getPendingPlaceOrderChild bean");
			}
			Integer minNumOFDays = Integer.valueOf(minDays);
			List<OrderData> orderData =null;
			
			try {
				
				
				Logger.logTrace(MODULE, "-----------------"+minNumOFDays);
				String hql="select o from OrderData o where o.orderStatusId in ('101','102','103') and trunc(sysdate) - to_date(o.createdate, 'dd-mon-yy')>"+minNumOFDays+" and o.toWarehouseId="+warehouseid;
				
				orderData = getEntityManager().createQuery(hql).getResultList();
				
				
			} catch (NoResultException e) { 
				return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return orderData;
			
		}
		/**
		 * Get Pending PlaceOrder Child
		 * @author jahanvi.patel
		 * @return {@link List}<{@link Object}> data.
		 * @throws CreateBLException
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<TransferOrderData> getPendingTransferOrderChild(String minDays, Long warehouseid){
			
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "inside getPendingTransferOrderChild bean");
			}
			Integer minNumOFDays = Integer.valueOf(minDays);
			List<TransferOrderData> orderData =null;
			
			try {
				
				
				Logger.logTrace(MODULE, "-----------------------------------------------------"+minNumOFDays);
				String hql="select o from TransferOrderData o where o.inventoryOrderStatusId='101' and trunc(sysdate) - to_date(o.createdate, 'dd-mon-yy')>"+minNumOFDays+"and o.toWarehouseId="+warehouseid;
				
				orderData = getEntityManager().createQuery(hql).getResultList();
				
			} catch (NoResultException e) { 
				return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return orderData;
			
		}
		/**
		 * Get Pending PlaceOrder Child
		 * @author jahanvi.patel
		 * @return {@link List}<{@link Object}> data.
		 * @throws CreateBLException
		 */
		@Override
		@TransactionAttribute( TransactionAttributeType.REQUIRED )
		public Boolean saveOrderNotificationAgentHistory(OrderDetailVo orderDetailVo) throws CreateBLException{
			OrderAgentHistoryData agentHistoryData =null;
			try{
				
				agentHistoryData=InventoryManagementUtil.getAgentHistoryData(orderDetailVo);
				
				getEntityManager().persist(agentHistoryData);
				getEntityManager().flush();
				getEntityManager().refresh(agentHistoryData);
				 
			}catch(Exception e){
				
				e.printStackTrace();
				
				getSessionContext().setRollbackOnly();
				
		    	throw new CreateBLException("save Place Order  Failed, Reason : " + e.getMessage(), e);
			}
		return true;
			
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<Long> getPendingPlaceOrderMaster (String minDays){
			
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "inside getPendingPlaceOrderMaster bean");
			}
			Integer minNumOFDays = Integer.valueOf(minDays);
			
			List<Long> result = null;
			try {
				
				
				String hql= "select o.toWarehouseId from OrderData o where o.orderStatusId in ('101','102','103') and trunc(sysdate) - to_date(o.createdate, 'dd-mon-yy')>"+minNumOFDays +" group by o.toWarehouseId";
				
				result = getEntityManager().createQuery(hql).getResultList();
				
			} catch(NoResultException e) {
				return null;
			} catch (Exception e) {
				e.printStackTrace();
			}		
			return result;
		}
		//Jahanvi Code Stop 


		@Override
		public List<Long> getPendingTransferOrderMaster(String minPendingDays)
				throws SearchBLException {
			
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "inside getPendingTransferOrderMaster bean");
			}
			Integer minNumOFDays = Integer.valueOf(minPendingDays);
			
			List<Long> result = null;
			try {
				
				
				
				String hql= "select o.toWarehouseId from TransferOrderData o where o.inventoryOrderStatusId in ('101') and trunc(sysdate) - to_date(o.createdate, 'dd-mon-yy')>"+minNumOFDays +" group by o.toWarehouseId";
				
				result = getEntityManager().createQuery(hql).getResultList();
				
			} catch(NoResultException e) {
				return null;
			} catch (Exception e) {
				e.printStackTrace();
			}		
			return result;
		}
	
	
}

