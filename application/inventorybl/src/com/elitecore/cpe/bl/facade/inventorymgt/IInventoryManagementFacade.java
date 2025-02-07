package com.elitecore.cpe.bl.facade.inventorymgt;

import java.util.List;
import java.util.Map;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.BatchSummaryVO;
import com.elitecore.cpe.bl.vo.inventorymgt.BulkChangeInventoryStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.ChangeInventoryStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.ChangeInventorySubStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryBatchViewVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryStatusLogVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchWarehouseInventoryStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.TransferInventorySummaryViewVO;
import com.elitecore.cpe.bl.vo.inventorymgt.migration.InventoryMigrationResponseVO;
import com.elitecore.cpe.bl.vo.inventorymgt.migration.InventoryMigrationVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.PartialAcceptRejectTransferOrderVO;
import com.elitecore.cpe.bl.vo.order.OrderDetailVo;
import com.elitecore.cpe.bl.vo.order.TransferOrderVO;
import com.elitecore.cpe.bl.ws.data.input.request.InventoryDetailsRequestData;
import com.elitecore.cpe.bl.ws.data.input.response.InventoryDetailsResponseData;
import com.elitecore.cpe.core.IBLSession;
public interface IInventoryManagementFacade {

	
	public InventoryUploadVO uploadInventory(InventoryUploadVO inventoryUploadVO,IBLSession iblSession) throws CreateBLException;
	public List<ComboData> getAllInventoryStatusData();	
	public List<InventoryDetailVO> searchInventoryDetailData(SearchInventoryVO inventoryDetailVO);
	public List<InventoryBatchViewVO> searchInventoryBatchData(SearchInventoryVO searchInventoryVO);
	public void searchInventoryUploadData(String batchNumber,Boolean status,String destinationpath);
		
	public Long getAvailableStock(SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO);
	
	public void  transferInventory(SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO,IBLSession iblSession) throws CreateBLException;
	public String  transferInventory(List<InventoryDetailVO> inventoryDetailVOs,Long fromWarehouseId,Long towarehouseId,IBLSession iblSession,String flag) throws CreateBLException;
	public InventoryDetailsResponseData getInventoryDetails(InventoryDetailsRequestData requestData,int noOfRecords) throws SearchBLException;
	
	public void transferInventoryBatch(Map<String,Integer> batchMap,SearchInventoryVO searchInventoryVO,Long fromWarehouseId,Long toWarehouseId,IBLSession iblSession)throws CreateBLException;
	public List<TransferInventorySummaryViewVO> searchTransferInventorySummary(SearchTransferInventory searchTransferInventory,IBLSession iblsession);
	
	public InventoryUploadVO getInventoryDetails(SearchTransferInventory searchTransferInventory);
	
	public void acceptTransferInventory(PartialAcceptRejectTransferOrderVO transferOrderVO,IBLSession iblSession)throws CreateBLException;
	public List<BatchSummaryVO> searchBatchSummaryData (BatchSummaryVO batchsummaryVO,IBLSession iblSession);
	
	/*public ResourceAvailibilityResponseData checkCPEResource(ResourceAvailibilityRequestData requestData) throws SearchBLException;
	
	public List<InventoryStatusVO> changeInventoryStatus(List<InventoryRequestVO> inventoryRequestVO,IBLSession iblSession) throws UpdateBLException;
	
	public List<com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO> reserveInventory(BookCPERequestData requestData,IBLSession iblSession)throws SearchBLException;
	
	public void allocateInventory(List<ReserveAllocateRequestVO> inventoryList,IBLSession iblSession) throws SearchBLException;*/

	
	public InventoryUploadVO getBatchDetailInventoryData(String batchNo,boolean status);

	public List<InventoryStatusLogVO>viewInventoryHistoryData(String inventorynumber,IBLSession iblSession);

	
	public List<ComboData> getAllowedStatus(String inventoryNo) throws SearchBLException;

	public void changeInventoryStatus(ChangeInventoryStatusVO statusVO,IBLSession blSession) throws UpdateBLException;

	public List<ComboBoxData> getAllowedSubStatus(String inventoryNo) throws SearchBLException;
	
	public void changeInventorySubStatus(ChangeInventorySubStatusVO statusVO,IBLSession blSession) throws UpdateBLException;
	public List<InventoryDetailVO> searchInventory(SearchInventoryVO inventoryDetailVO);
	public String placeOrder(PlaceOrderVO placeOrderVO,IBLSession blSession)throws CreateBLException;
	public List<PlaceOrderVO> searchPlaceOrderData(PlaceOrderVO placeOrderVO,IBLSession iblSession);
	public void acceptPlaceOrder(SearchTransferInventory searchTransferInventory,IBLSession iblSession)throws CreateBLException;
	public PlaceOrderVO searchPlaceOrderDataByOrderNo(PlaceOrderVO placeOrderVO,IBLSession iblSession);
	public boolean searchTransferOrder(String orderNo);
	public void transferPlaceOrder(PlaceOrderVO placeOrderVO,IBLSession iblSession)throws CreateBLException;
	public List<ComboData> searchTransferrableStatus() throws SearchBLException;
	public List<PlaceOrderVO> searchPlaceOrderDetail(PlaceOrderVO data,IBLSession blSession) throws SearchBLException;
	
	public InventoryMigrationResponseVO uploadMigrationInventory(List<InventoryMigrationVO> inventoryMigrationVOs, boolean isValidate);
	public InventoryDetailVO searchInventoryDetailDataById(String inventoryId) throws SearchBLException;
	public int updateInventoryStatusInBulk(BulkChangeInventoryStatusVO statusVO, IBLSession iblSession) throws UpdateBLException;
	
	
	public List<Long> getPendingPlaceOrderMaster() throws SearchBLException;
	
	public List<Long> getPendingTransferOrderMaster() throws SearchBLException;

	public Boolean saveOrderNotificationAgentHistory(OrderDetailVo orderDetailVo) throws CreateBLException;

	public List<PlaceOrderVO> getPendingPlaceOrderChild(Long towarehouseid) throws SearchBLException;

	public List<TransferOrderVO> getPendingTransferOrderChild(Long towarehouseid) throws SearchBLException;
	
}
