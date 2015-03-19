package com.elitecore.cpe.bl.facade.inventorytransfer;

import java.util.List;

import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorymgt.TransferInventorySummaryViewVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.CancelTransferOrderVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.PartialAcceptRejectTransferOrderVO.InventoryVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.ViewTransferInventoryDetailVO;
import com.elitecore.cpe.core.IBLSession;

public interface IInventoryTransferFacade {

	public void cancelTransferInventory(CancelTransferOrderVO transferOrderVO,IBLSession blSession) throws UpdateBLException;

	public InventoryUploadVO getInventoryDetailsForRejectedInventory(SearchTransferInventory searchTransferInventory);

	public void acceptRejectedTransferInventory(SearchTransferInventory searchTransferInventory,IBLSession blSession) throws CreateBLException;
	
	public void cancelPlaceOrder(CancelTransferOrderVO transferOrderVO,IBLSession blSession) throws UpdateBLException ;

	public List<TransferInventorySummaryViewVO> searchTransferInventoryOrderDetail(SearchTransferInventory searchTransferInventory, IBLSession blSession) throws SearchBLException;

	public ViewTransferInventoryDetailVO searchTransferInventorySummary(String orderNumber) throws SearchBLException;

	public InventoryVO checkInventoryForTransfer(String inventoryNumber,String orderNumber) throws SearchBLException;
}
