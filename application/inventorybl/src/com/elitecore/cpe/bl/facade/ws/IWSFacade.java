package com.elitecore.cpe.bl.facade.ws;

import java.util.List;

import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.ws.data.input.request.BookCPERequestData;
import com.elitecore.cpe.bl.ws.data.input.request.MarkCPEAsFaultyRequestVO;
import com.elitecore.cpe.bl.ws.data.input.request.ReleaseCPERequestVO;
import com.elitecore.cpe.bl.ws.data.input.request.ResourceAvailibilityRequestData;
import com.elitecore.cpe.bl.ws.data.input.response.CPEResponseVO;
import com.elitecore.cpe.bl.ws.data.input.response.InventoryStatusResponseVO;
import com.elitecore.cpe.bl.ws.data.input.response.InventoryStatusVO;
import com.elitecore.cpe.bl.ws.data.input.response.ReleaseCPEResponseVO;
import com.elitecore.cpe.bl.ws.data.input.response.ResourceAvailibilityResponseData;
import com.elitecore.cpe.bl.ws.data.input.vo.InventoryRequestVO;
import com.elitecore.cpe.bl.ws.data.input.vo.ReserveAllocateRequestVO;
import com.elitecore.cpe.core.IBLSession;

public interface IWSFacade {

public ResourceAvailibilityResponseData checkCPEResource(ResourceAvailibilityRequestData requestData) throws SearchBLException;
	
	public List<InventoryStatusVO> changeInventoryStatus(List<InventoryRequestVO> inventoryRequestVO,IBLSession iblSession) throws UpdateBLException;
	
	public List<com.elitecore.cpe.bl.ws.data.input.vo.InventoryVO> reserveInventory(BookCPERequestData requestData,IBLSession iblSession)throws SearchBLException;
	
	public void allocateInventory( BookCPERequestData requestData,IBLSession iblSession) throws SearchBLException;

	public List<InventoryStatusVO> changeInventoryStatusforInventory(List<InventoryRequestVO> inventoryRequestVO,int reserveListsize,IBLSession iblSession) throws UpdateBLException;
	
	public List<InventoryStatusResponseVO> releaseCPEResource(ReleaseCPERequestVO cpeRequestVO,IBLSession iblSession) throws UpdateBLException,SearchBLException;
	public List<InventoryStatusResponseVO>  markCPEAsFaultyWithOwnerChange(MarkCPEAsFaultyRequestVO mAsFaultyRequestVO, IBLSession blSession)throws UpdateBLException,SearchBLException;
	
}
