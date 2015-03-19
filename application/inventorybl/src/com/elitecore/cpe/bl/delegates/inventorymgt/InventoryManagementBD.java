package com.elitecore.cpe.bl.delegates.inventorymgt;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.BaseBusinessDelegate;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.inventorymgt.IInventoryManagementFacade;
import com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementFacadeLocal;
import com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementFacadeRemote;
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
import com.elitecore.cpe.bl.vo.inventorytransfer.PartialAcceptRejectTransferOrderVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.core.IBDSessionContext;

public class InventoryManagementBD extends BaseBusinessDelegate{

	private static final String MODULE ="INVENTORY-BD";
	
	private static IInventoryManagementFacade facade;
	
	public InventoryManagementBD(IBDSessionContext context){
		super(context);
	}
	
	private IInventoryManagementFacade getFacade()  throws NamingException{
		if (facade == null) {
			if(isLocalMode()){
				facade = (IInventoryManagementFacade)lookupLocal(InventoryManagementFacadeLocal.class);
			}else{
				facade = (IInventoryManagementFacade)lookup(InventoryManagementFacadeRemote.class);
			}
		}
		return facade;
	}
	
	
	/**
	 * Upload Inventory from GUI
	 * @param {@link InventoryUploadVO} inventoryUploadVO
	 * @return {@link InventoryUploadVO} inventoryUploadVO
	 * @throws CreateBLException
	 */
	public InventoryUploadVO uploadInventory(InventoryUploadVO inventoryUploadVO) throws CreateBLException{
		
		InventoryUploadVO inventoryUploadVO2 = null;
		try {
			inventoryUploadVO2 = getFacade().uploadInventory(inventoryUploadVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(CreateBLException ex){
			throw ex;
		}
		return inventoryUploadVO2;
	}
	
	
	
	/**
	 * Get All Inventory Status Data to fill COmbo Boxes
	 * @return {@link List}<{@link ComboData}> comboDatas
	 */
	public List<ComboData> getAllInventoryStatusData(){
		List<ComboData> comboDatas = null;
		try {
			comboDatas = getFacade().getAllInventoryStatusData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comboDatas;
	}
	
	
	/**
	 * search Inventory Detail Data
	 * @param {@link SearchInventoryVO} inventoryDetailVO
	 * @return {@link List}<{@link InventoryDetailVO}> inventoryDetailVOs
	 */
	public List<InventoryDetailVO> searchInventoryDetailData(SearchInventoryVO inventoryDetailVO){
		List<InventoryDetailVO>  inventoryDetailVOs = null;
		try {
			inventoryDetailVOs = getFacade().searchInventoryDetailData(inventoryDetailVO);
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return inventoryDetailVOs;
	}
	
	
	/**
	 * search Inventory Batch Data
	 * @param {@link SearchInventoryVO} searchInventoryVO
	 * @return {@link List}<{@link InventoryBatchViewVO}> inventoryBatchViewVOs
	 */
	public List<InventoryBatchViewVO> searchInventoryBatchData(SearchInventoryVO searchInventoryVO){
		List<InventoryBatchViewVO>  inventoryBatchViewVOs = null;
		try {
			System.out.println("[JM] Inside searchInventoryBatchData() BD:");
			inventoryBatchViewVOs = getFacade().searchInventoryBatchData(searchInventoryVO);
			System.out.println("[JM] List size in BD:"+inventoryBatchViewVOs.size());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return inventoryBatchViewVOs;
	}
	
	
	/**
	 * Get Available Stocks
	 * @param {@link SearchWarehouseInventoryStatusVO} searchWarehouseInventoryStatusVO
	 * @return {@link Long} avaialble
	 */
	public Long getAvailableStock(SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO){
		
		Long avaialble = 0L;
		try{
			avaialble = getFacade().getAvailableStock(searchWarehouseInventoryStatusVO);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return avaialble;
	}
	
	
	/**
	 * Transfer Inventory
	 * @param {@link SearchWarehouseInventoryStatusVO} searchWarehouseInventoryStatusVO
	 * @throws CreateBLException
	 */
	public void  transferInventory(SearchWarehouseInventoryStatusVO searchWarehouseInventoryStatusVO) throws CreateBLException{
		try {
			getFacade().transferInventory(searchWarehouseInventoryStatusVO,getBLSession());
		}catch (NamingException e) {
			e.printStackTrace();
		}catch (CreateBLException e) {
			throw e;
		}
	}
	
	
	/**
	 * Transfer Inventory
	 * @param {@link List}<{@link InventoryDetailVO}> inventoryDetailVOs
	 * @param Long fromwarehouseId
	 * @param Long towarehouseId
	 * @param String flag
	 * @return String orderNo
	 * @throws CreateBLException
	 */
	public String  transferInventory(List<InventoryDetailVO> inventoryDetailVOs,Long fromwarehouseId,Long towarehouseId,String flag ) throws CreateBLException{
		String orderNo=null;
		try {
			 orderNo=	getFacade().transferInventory(inventoryDetailVOs,fromwarehouseId,towarehouseId,getBLSession(),flag);
			
		}catch (NamingException e) {
			e.printStackTrace();
		}catch (CreateBLException e) {
			throw e;
		}
		return orderNo;
	}
	
	
	/**
	 * Transfer Inventory batch
	 * @param {@link Map}<{@link String,Integer}> batchMap
	 * @param {@link SearchInventoryVO} SearchInventoryVO
	 * @param Long towarehouseId
	 * @param Long fromWarehouseId
	 * @throws CreateBLException
	 */
	public void transferInventoryBatch(Map<String,Integer> batchMap,SearchInventoryVO searchInventoryVO,Long fromWarehouseId,Long toWarehouseId) throws CreateBLException{
		try {
			getFacade().transferInventoryBatch(batchMap,searchInventoryVO,fromWarehouseId,toWarehouseId,getBLSession());
		}catch (NamingException e) {
			e.printStackTrace();
		}catch (CreateBLException e) {
			throw e;
		}
	}
	
	
	/**
	 * Search Transfer Inventory Summary
	 * @param {@link SearchTransferInventory} searchTransferInventory
	 * @return {@link List}<{@link TransferInventorySummaryViewVO}> inventorySummaryViewVOs
	 */
	public List<TransferInventorySummaryViewVO> searchTransferInventorySummary(SearchTransferInventory searchTransferInventory){
		List<TransferInventorySummaryViewVO> inventorySummaryViewVOs = null;
		try {
			inventorySummaryViewVOs =getFacade().searchTransferInventorySummary(searchTransferInventory, getBLSession());
		}catch (NamingException e) {
			e.printStackTrace();
		}
		return inventorySummaryViewVOs;
	}
	
	
	/**
	 * Get Inventory Details
	 * @param {@link SearchTransferInventory} searchTransferInventory
	 * @return {@link InventoryUploadVO} uploadVO
	 */
	public InventoryUploadVO getInventoryDetails(SearchTransferInventory searchTransferInventory){
		InventoryUploadVO uploadVO = null;
		try {
			uploadVO =getFacade().getInventoryDetails(searchTransferInventory);
		}catch (NamingException e) {
			e.printStackTrace();
		}
		return uploadVO;
	}
	
	
	/**
	 * Accept Transfer Inventory
	 * @param {@link SearchTransferInventory} searchTransferInventory
	 * @throws CreateBLException
	 */
	public void acceptTransferInventory(PartialAcceptRejectTransferOrderVO transferOrderVO)throws CreateBLException{
		
		try {
			getFacade().acceptTransferInventory(transferOrderVO,getBLSession());
		} catch (NamingException e) {
			// TODO: handle exception
		}catch(CreateBLException ex){
			throw ex;
		}
	}
	
	
	/**
	 * Search Batch Summary Data
	 * @param {@link BatchSummaryVO} batchsummaryVO
	 * @return {@link List}<{@link BatchSummaryVO}> batchSummaryVOs
	 */
	public List<BatchSummaryVO> searchBatchSummaryData(BatchSummaryVO batchsummaryVO){
		List<BatchSummaryVO>  batchSummaryVOs = null;
		try {
			batchSummaryVOs=getFacade().searchBatchSummaryData( batchsummaryVO,getBLSession());
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return batchSummaryVOs;
	}
	
	
	/**
	 * Get Batch Detail Inventory Data
	 * @param {@link String} batchNo
	 * @param boolean status
	 * @return {@link InventoryUploadVO} uploadVO
	 */
	public InventoryUploadVO getBatchDetailInventoryData(String batchNo,boolean status){
		InventoryUploadVO uploadVO = null;
		try {
			uploadVO =getFacade().getBatchDetailInventoryData(batchNo, status);
		} catch (NamingException e) {
			// TODO: handle exception
		}catch(Exception ex){
			
		}
		return uploadVO;
	}

	
	/**
	 * View Inventory History Data
	 * @param {@link String} inventorynumber
	 * @return {@link List}<{@link InventoryStatusLogVO}> inventoryStatusLogVOs
	 */
	public List<InventoryStatusLogVO> viewInventoryHistoryData(String inventorynumber) {
		List<InventoryStatusLogVO>  inventoryStatusLogVOs = null;
		try {
			inventoryStatusLogVOs=getFacade().viewInventoryHistoryData(inventorynumber,getBLSession());
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return inventoryStatusLogVOs;
		
	}

	/**
	 * Get Allowed Status of the Inventory
	 * @param {@link String} inventoryNo
	 * @return {@link List}<{@link ComboData}> comboBoxDatas
	 * @throws SearchBLException
	 */
	public List<ComboData> getAllowedStatus(String inventoryNo) throws SearchBLException {
		List<ComboData> comboBoxDatas = null;
		try {
			comboBoxDatas = getFacade().getAllowedStatus(inventoryNo);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch(SearchBLException ex){
			throw ex;
		}
		return comboBoxDatas;
	}
	

	/**
	 * Change Inventory Status
	 * @param {@link ChangeInventoryStatusVO} statusVO
	 * @throws UpdateBLException
	 */
	public void changeInventoryStatus(ChangeInventoryStatusVO statusVO) throws UpdateBLException {
	
		try {
			getFacade().changeInventoryStatus(statusVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch(UpdateBLException ex){
			throw ex;
		}
		
	}
	
	
	/**
	 * Get Allowed Sub Status of the Inventory
	 * @param {@link String} inventoryNo
	 * @return {@link List}<{@link ComboData}> comboBoxDatas
	 * @throws SearchBLException
	 */
	public List<ComboBoxData> getAllowedSubStatus(String inventoryNo) throws SearchBLException {
		List<ComboBoxData> comboBoxDatas = null;
		try {
			comboBoxDatas = getFacade().getAllowedSubStatus(inventoryNo);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch(SearchBLException ex){
			throw ex;
		}
		return comboBoxDatas;
	}
	
	
	/**
	 * Change Inventory SubStatus
	 * @param {@link ChangeInventorySubStatusVO} statusVO
	 * @throws UpdateBLException
	 */
	public void changeInventorySubStatus(ChangeInventorySubStatusVO statusVO) throws UpdateBLException {
		
		try {
			getFacade().changeInventorySubStatus(statusVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch(UpdateBLException ex){
			throw ex;
		}
		
	}
	
	/**
	 * Search Inventory
	 * @param {@link SearchInventoryVO} inventoryDetailVO
	 * @return {@link List} <{@link InventoryDetailVO}> inventoryDetailVOs
	 */
	public List<InventoryDetailVO> searchInventory(SearchInventoryVO inventoryDetailVO){
		List<InventoryDetailVO>  inventoryDetailVOs = null;
		try {
			inventoryDetailVOs = getFacade().searchInventory(inventoryDetailVO);
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return inventoryDetailVOs;
	}
	
	
	/**
	 * Place Order Data
	 * @param {@link PlaceOrderVO} placeOrderVO
	 * @return String orderNo
	 * @throws CreateBLException
	 */
	public String  placeOrder(PlaceOrderVO placeOrderVO ) throws CreateBLException{
		String orderNo=null;
		try {
			 orderNo=getFacade().placeOrder(placeOrderVO,getBLSession());
			
		}catch (NamingException e) {
			e.printStackTrace();
		}catch (CreateBLException e) {
			throw e;
		}
		return orderNo;
	}
	
	
	/**
	 * Search Place Order Data
	 * @param {@link PlaceOrderVO} placeOrderVO
	 * @return {@link List} <{@link PlaceOrderVO}> placeOrderVOs
	 */
	public List<PlaceOrderVO>  searchPlaceOrderData(PlaceOrderVO placeOrderVO ) {
		List<PlaceOrderVO> placeOrderVOs=null;
		try {
			placeOrderVOs=getFacade().searchPlaceOrderData(placeOrderVO,getBLSession());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return placeOrderVOs;
	}
	
	
	/**
	 * Accept Place Order
	 * @param {@link SearchTransferInventory} searchTransferInventory
	 * @throws CreateBLException
	 */
	public void acceptPlaceOrder(SearchTransferInventory searchTransferInventory)throws CreateBLException{
		
		try {
			getFacade().acceptPlaceOrder(searchTransferInventory,getBLSession());
		} catch (NamingException e) {
			// TODO: handle exception
		}catch(CreateBLException ex){
			throw ex;
		}
	}
	
	/**
	 * Search Place Order by Order No
	 * @param {@link PlaceOrderVO} placeOrderVO
	 * @return {@link PlaceOrderVO} placeOrderVO
	 */
	public PlaceOrderVO searchPlaceOrderDataByOrderNo(PlaceOrderVO placeOrderVO){
		PlaceOrderVO placeOrderVO2=null;
		try {
	
		placeOrderVO2=getFacade().searchPlaceOrderDataByOrderNo(placeOrderVO,getBLSession());
		
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return placeOrderVO2;
	}
	
	
	/**
	 * Search Transfer Order by Order No
	 * @param {@link String} orderNo
	 * @return {@link boolean} flag
	 */
	public boolean searchTransferOrder(String orderNo){
		boolean flag=false;
		try {
	
			flag=getFacade().searchTransferOrder(orderNo);
		
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * Transfer Place Order 
	 * @param {@link PlaceOrderVO} placeOrderVO
	 * @throws CreateBLException
	 */
	public void transferPlaceOrder(PlaceOrderVO placeOrderVO)throws CreateBLException{
		
		try {
			getFacade().transferPlaceOrder(placeOrderVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(CreateBLException ex){
			throw ex;
		}
	}

	public List<ComboData> searchTransferrableStatus() throws SearchBLException {
		
		List<ComboData> comboBoxDatas = null;
		try {
			comboBoxDatas = getFacade().searchTransferrableStatus();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch(SearchBLException ex){
			throw ex;
		}
		return comboBoxDatas;
		
	}

	
	/**
	 * Search Place Order Detail 
	 * @param {@link PlaceOrderVO} data
	 * @return {@link List}<{@link PlaceOrderVO}> placeOrderVOs
	 */
	public List<PlaceOrderVO> searchPlaceOrderDetail(PlaceOrderVO data) {
		
		List<PlaceOrderVO> placeOrderVOs=null;
		try {
			placeOrderVOs=getFacade().searchPlaceOrderDetail(data,getBLSession());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return placeOrderVOs;
		
	}

	/**
	 * Search Inventory Detail Data by Inventory Id 
	 * @param {@link String} inventoryId
	 * @return {@link InventoryDetailVO} inventoryDetailVO
	 */
	public InventoryDetailVO searchInventoryDetailDataById(
			String inventoryId) {
		
		InventoryDetailVO  inventoryDetailVOs = null;
		try {
			inventoryDetailVOs = getFacade().searchInventoryDetailDataById(inventoryId);
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return inventoryDetailVOs;
	}

	/**
	 * Update Inventory Data 
	 * @param {@link ItemVO}  itemVo
	 * @throws UpdateBLException
	 */
	public int updateInventoryStatusInBulk(BulkChangeInventoryStatusVO statusVO) throws UpdateBLException {
		// TODO Auto-generated method stub
		int updatecount=0;
	try {
			 updatecount= getFacade().updateInventoryStatusInBulk(statusVO,getBLSession());
				} catch (NamingException e) {
			e.printStackTrace();
		}catch(UpdateBLException ex){
			throw ex;
		}
	return updatecount;

	}
}
