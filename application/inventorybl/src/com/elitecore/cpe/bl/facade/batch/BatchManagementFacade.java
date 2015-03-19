package com.elitecore.cpe.bl.facade.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.elitecore.cpe.bl.constants.inventorymgt.InventoryStatusConstants;
import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.BatchData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeTransData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.master.warehouse.WarehouseUtil;
import com.elitecore.cpe.bl.session.inventorymgt.BatchManagementSessionBeanLocal;
import com.elitecore.cpe.bl.session.master.warehouse.WarehouseSessionBeanLocal;
import com.elitecore.cpe.bl.session.system.internal.SystemInternalSessionBeanLocal;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryWrapperVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.util.logger.Logger;

@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class BatchManagementFacade extends BaseFacade implements BatchManagementFacadeRemote,BatchManagementFacadeLocal{

	private static final String MODULE = "BATCH-FC";
	
	
	@EJB private SystemInternalSessionBeanLocal systemInternalSessionBeanLocal;
	@EJB private BatchManagementSessionBeanLocal batchManagementSessionBeanLocal;
	
	
	public void getBatchData(SearchInventoryVO searchInventoryVO ){
		
		
//		batchManagementSessionBeanLocal.getFilterDataBy(EntityConstants.b, fieldValueMap)
	}

}
