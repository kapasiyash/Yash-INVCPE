package com.elitecore.cpe.bl.delegates.transfer;

import java.util.List;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.delegates.BaseBusinessDelegate;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.inventorytransfer.IInventoryTransferFacade;
import com.elitecore.cpe.bl.facade.inventorytransfer.InventoryTransferFacadeLocal;
import com.elitecore.cpe.bl.facade.inventorytransfer.InventoryTransferFacadeRemote;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchTransferInventory;
import com.elitecore.cpe.bl.vo.inventorymgt.TransferInventorySummaryViewVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.CancelTransferOrderVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.PartialAcceptRejectTransferOrderVO.InventoryVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.ViewTransferInventoryDetailVO;
import com.elitecore.cpe.core.IBDSessionContext;

public class InventoryTransferBD extends BaseBusinessDelegate {

	
private static final String MODULE ="TRANSFER-INVENTORY-BD";
	
	private static IInventoryTransferFacade facade;
	
	public InventoryTransferBD(IBDSessionContext context){
		super(context);
	}
	
	private IInventoryTransferFacade getFacade()  throws NamingException{
		if (facade == null) {
			if(isLocalMode()){
				facade = (IInventoryTransferFacade)lookupLocal(InventoryTransferFacadeLocal.class);
			}else{
				facade = (IInventoryTransferFacade)lookup(InventoryTransferFacadeRemote.class);
			}
		}
		return facade;
	}

	/**
	 * Cancel Transfer Inventory
	 * @param transferOrderVO
	 * @throws UpdateBLException
	 */
	public void cancelTransferInventory(CancelTransferOrderVO transferOrderVO) throws UpdateBLException {
		
		try {
			getFacade().cancelTransferInventory(transferOrderVO,getBLSession());
		}catch (NamingException e) {
			e.printStackTrace();
		}catch (UpdateBLException e) {
			throw e;
		}
		
	}
	
	/**
	 * Get Inventory Details for rejected Inventories
	 * @param searchTransferInventory
	 * @return {@link InventoryUploadVO}
	 */
	public InventoryUploadVO getInventoryDetailsForRejectedInventory(
			SearchTransferInventory searchTransferInventory) {
		
		InventoryUploadVO uploadVO = null;
		try {
			uploadVO =getFacade().getInventoryDetailsForRejectedInventory(searchTransferInventory);
		}catch (NamingException e) {
			e.printStackTrace();
		}
		return uploadVO;
	}

	/**
	 * Accpet-reject Transfer Inventory
	 * @param searchTransferInventory
	 * @throws CreateBLException
	 */
	public void acceptRejectedTransferInventory(
			SearchTransferInventory searchTransferInventory) throws CreateBLException{
		try {
			getFacade().acceptRejectedTransferInventory(searchTransferInventory,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(CreateBLException ex){
			throw ex;
		}
		
	}
	/**
	 * Cancel Place order
	 * @param transferOrderVO
	 * @throws UpdateBLException
	 */
	public void cancelPlaceOrder(CancelTransferOrderVO transferOrderVO) throws UpdateBLException {
			
			try {
				getFacade().cancelPlaceOrder(transferOrderVO,getBLSession());
			}catch (NamingException e) {
				e.printStackTrace();
			}catch (UpdateBLException e) {
				throw e;
			}
			
		}
	
	
	/**
	 * Search Transfer Inventory Order Detail
	 * @param searchTransferInventory
	 * @return {@link List} <{@link TransferInventorySummaryViewVO}>
	 * @throws SearchBLException
	 */
	public List<TransferInventorySummaryViewVO> searchTransferInventoryOrderDetail(SearchTransferInventory searchTransferInventory) throws SearchBLException {
		
		List<TransferInventorySummaryViewVO> inventorySummaryViewVOs = null;
		try {
			inventorySummaryViewVOs =getFacade().searchTransferInventoryOrderDetail(searchTransferInventory, getBLSession());
		}catch (NamingException e) {
			e.printStackTrace();
		}
		return inventorySummaryViewVOs;
		
	}

	/**
	 * Search Transfer Inventory Summary
	 * @param orderNumber
	 * @return {@link ViewTransferInventoryDetailVO}
	 * @throws SearchBLException
	 */
	public ViewTransferInventoryDetailVO searchTransferInventorySummary(
			String orderNumber) throws SearchBLException {
		
		try {
			return getFacade().searchTransferInventorySummary(orderNumber);
		}catch (NamingException e) {
			e.printStackTrace();
			return null;
		}catch (SearchBLException e) {
			throw e;
		}
		
	}

	public InventoryVO checkInventoryForTransfer(String inventoryNumber,
			String orderNumber) throws SearchBLException {
		
		try {
			return getFacade().checkInventoryForTransfer(inventoryNumber,orderNumber);
		}catch (NamingException e) {
			e.printStackTrace();
			return null;
		}catch (SearchBLException e) {
			throw e;
		}
		
	}
	
}
