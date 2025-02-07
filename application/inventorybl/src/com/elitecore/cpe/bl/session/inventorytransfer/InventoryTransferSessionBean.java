package com.elitecore.cpe.bl.session.inventorytransfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.system.DataSourceConstant;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.BatchData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusLogData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.OrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderDetailData;
import com.elitecore.cpe.bl.entity.inventory.transfer.InventoryTransferOrderStatus;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementUtil;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorymgt.TransferInventorySummaryViewVO;
import com.elitecore.cpe.util.logger.Logger;

@Stateless
public class InventoryTransferSessionBean extends BaseSessionBean implements InventoryTransferSessionBeanLocal {

	private static final String MODULE = "INVENTORY-TRANSFER-SB";

	
	/**
	 * Update Transfer Order  
	 * @author yash.kapasi
	 * @param {@link TransferOrderData} transferOrderData
	 * @throws UpdateBLException
	 */
	@Override
	public void updateTransferOrder(TransferOrderData transferOrderData)
			throws UpdateBLException {
		
		Logger.logTrace(MODULE, "Inside updateOprder");
		try {
			
			getEntityManager().merge(transferOrderData);
			
			Logger.logTrace(MODULE, "returning updateOprder");	
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage());
		}
		
	}

	@Override
	public List<Object> searchInventoryDataFromOrderNo(Long tarnsferId)
			throws SearchBLException {
		
		Logger.logTrace(MODULE, "inside searchInventoryDataFromOrderNo()");
		try {
			String query = "select a from InventoryData a where a.inventoryNo in (select b.inventoryNo from TransferOrderDetailData b where b.transferOrderId='"+tarnsferId+"')";
			
			List<Object> data = getEntityManager().createQuery(query).getResultList();
			
			
			return data;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	/**
	 * Search Transfer Order Status by Alias  
	 * @author yash.kapasi
	 * @param alias
	 * @return {@link InventoryTransferOrderStatus} data
	 * @throws SearchBLException
	 */
	@Override
	public InventoryTransferOrderStatus searchOrderStatusByAlias(String alias)
			throws SearchBLException {
		
		Logger.logTrace(MODULE, "inside searchInventoryDataFromOrderNo()");
		try {
						
			InventoryTransferOrderStatus data = (InventoryTransferOrderStatus) getEntityManager().createNamedQuery("InventoryTransferOrderStatus.searchOrderStatusByAlias")
					.setParameter("alias", alias).getSingleResult();
			
			
			return data;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	
	/**
	 * Change Inventory Status After Accept-Reject Flow  
	 * @author yash.kapasi
	 * @param {@link List}<{@link InventoryDetailVO}> inventoryList
	 * @param {@link Map}<<String,{@link InventoryStatusLogData}> map
	 * @throws UpdateBLException
	 */
	@Override
	public void changeInventoryStatusAfterAcceptRejected(
			List<InventoryDetailVO> inventoryList,Map<String, InventoryStatusLogData> map) throws UpdateBLException {
		
		Logger.logTrace(MODULE, "Inside changeInventoryStatusAfterAcceptRejected");
		try {
			
			for(InventoryDetailVO detailVO : inventoryList) {
				
				Map<String, Object> fieldValueMap = new HashMap<String, Object>();
				fieldValueMap.put("inventoryNo", detailVO.getInventoryId());
				List filterList = getFilterDataBy(EntityConstants.INVENTORY_DATA, fieldValueMap);
				if(filterList!=null && !filterList.isEmpty()) {
					InventoryData inventoryData = (InventoryData) filterList.get(0);
					if(detailVO.getStatusId()!=0) {
						inventoryData.setInventoryStatusId(detailVO.getStatusId());
					}
					inventoryData.setTransferInventoryStatus(null);
					
					if(detailVO.getSubStatusId()!=0) {
						inventoryData.setInventorySubStatusId(Long.valueOf(detailVO.getSubStatusId()));
					}
					
					getEntityManager().merge(inventoryData);
					
					if(map.containsKey(detailVO.getInventoryId())) {
						InventoryStatusLogData statusog = map.get(detailVO.getInventoryId());
						statusog.setInventoryId(inventoryData.getInventoryId());
						getEntityManager().persist(statusog);
					}
					
				}
				
			}
			
			
			Logger.logTrace(MODULE, "returning changeInventoryStatusAfterAcceptRejected");	
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage());
		}
		
		
	}
	
	/**
	 * Update Place Order  
	 * @author yash.kapasi
	 * @param {@link OrderData} ordarData
	 * @throws UpdateBLException
	 */
	@Override
	public void updatePlaceOrder(OrderData orderData)
			throws UpdateBLException {
		
		Logger.logTrace(MODULE, "Inside updatePlaceOrder");
		try {
			
			getEntityManager().merge(orderData);
			getEntityManager().flush();
			
			Logger.logTrace(MODULE, "returning updatePlaceOrder");	
		}catch(Exception e) {
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage());
		}
		
	}

	/**
	 * search Transfer Order Detail   
	 * @author yash.kapasi
	 * @param {@link SearchTransferInventory} searchTransferInventory
	 *  @return {@link List}<{@link TransferInventorySummaryViewVO}> inventorySummaryViewVOs.
	 * @throws SearchBLException
	 */
	@Override
	public List<TransferInventorySummaryViewVO> searchTransferInventoryOrderDetail(SearchTransferInventory searchTransferInventory)
			throws SearchBLException {
		
		
		Connection con = null;
		List<TransferInventorySummaryViewVO> inventorySummaryViewVOs = new ArrayList<TransferInventorySummaryViewVO>();
		try {
			javax.sql.DataSource ds = getDataSource(DataSourceConstant.INVENTORY_DS);

			if (ds != null) {
				con = ds.getConnection();
			}
			
			if(searchTransferInventory == null ){
				return inventorySummaryViewVOs;
			}
			
			String hql = "from TransferOrderData where transferOrderId is not null ";
			
			if(searchTransferInventory.getOrderNo() != null && !searchTransferInventory.getOrderNo().isEmpty()){
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

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferOrderDetailData> checkInventoryForTransfer(
			String inventoryNumber, String orderNumber)
			throws SearchBLException {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside checkInventoryForTransfer()");
		try {
						
			List<TransferOrderDetailData> data = (List<TransferOrderDetailData>) getEntityManager().createNamedQuery("TransferOrderDetailData.isAvailable")
					.setParameter("transferOrderNo", orderNumber)
					.setParameter("inventoryNo", inventoryNumber).getResultList();
			
			
			return data;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<TransferOrderDetailData> checkInventoryForAcceptRejectedInventory(
			String inventoryNumber, String orderNumber)
			throws SearchBLException {
		
		Logger.logTrace(MODULE, "inside checkInventoryForAcceptRejectedInventory()");
		try {
						
			List<TransferOrderDetailData> data = (List<TransferOrderDetailData>) getEntityManager().createNamedQuery("TransferOrderDetailData.isAvailableForAcceptRejected")
					.setParameter("transferOrderNo", orderNumber)
					.setParameter("transferStatus", "REJECTED")
					.setParameter("inventoryNo", inventoryNumber).getResultList();
			
			
			return data;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	
	
}
