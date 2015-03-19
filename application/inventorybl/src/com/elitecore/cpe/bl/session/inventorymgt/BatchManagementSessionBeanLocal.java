package com.elitecore.cpe.bl.session.inventorymgt;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.elitecore.cpe.bl.entity.inventory.inventorymgt.BatchData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.BatchSummaryData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;




@Local
public interface BatchManagementSessionBeanLocal {

	public BatchData createBatch(BatchData batchData) throws CreateBLException;
	
	public void createBatchSummary(Collection<BatchSummaryData> batchSummaryDatas)  throws CreateBLException;
	
	public void updateBatch(BatchData batchData) throws UpdateBLException;
	
	public List getFilterDataBy(String entityName,Map<String,Object> fieldValueMap);

	public List<BatchSummaryData>searchBatchSummaryData(Long batchid);

	
	public List<BatchData> searchBatch(SearchInventoryVO searchInventoryVO);

}
