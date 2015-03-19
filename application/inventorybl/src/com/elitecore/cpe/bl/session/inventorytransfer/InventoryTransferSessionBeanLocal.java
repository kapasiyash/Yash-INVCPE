package com.elitecore.cpe.bl.session.inventorytransfer;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryStatusLogData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.OrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.TransferOrderDetailData;
import com.elitecore.cpe.bl.entity.inventory.transfer.InventoryTransferOrderStatus;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorymgt.TransferInventorySummaryViewVO;

@Local
public interface InventoryTransferSessionBeanLocal {

	public Object updateEntity(Object entity) throws UpdateBLException;
	public List getFilterDataBy(String entityName,Map<String,Object> fieldValueMap);

	public void updateTransferOrder(TransferOrderData transferOrderData) throws UpdateBLException;

	public List<Object> searchInventoryDataFromOrderNo(Long tarnsferNo) throws SearchBLException;

	public InventoryTransferOrderStatus searchOrderStatusByAlias(String string) throws SearchBLException;

	public void changeInventoryStatusAfterAcceptRejected(List<InventoryDetailVO> inventoryList, Map<String, InventoryStatusLogData> map) throws UpdateBLException;
	
	public void updatePlaceOrder(OrderData orderData) throws UpdateBLException ;

	public List<TransferInventorySummaryViewVO> searchTransferInventoryOrderDetail(SearchTransferInventory searchTransferInventory) throws SearchBLException;
	public List<TransferOrderDetailData> checkInventoryForTransfer(String inventoryNumber, String orderNumber) throws SearchBLException;
	public List<TransferOrderDetailData> checkInventoryForAcceptRejectedInventory(String inventoryNumber, String orderNumber) throws SearchBLException;
	
}
