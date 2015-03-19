package com.elitecore.cpe.bl.session.inventorymgt;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.exception.ConstraintViolationException;

import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.BatchData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.BatchSummaryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeTransData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryWrapperVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.util.logger.Logger;

@Stateless
public class BatchManagementSessionBean extends BaseSessionBean implements BatchManagementSessionBeanLocal{

	private static final String MODULE = "BATCH-SB";

	/**
	 * Create Batch  
	 * @author yash.kapasi
	 * @param {@link BatchData} batchData
	 * @eturn {@link BatchData} batchData
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public BatchData createBatch(BatchData batchData) throws CreateBLException {
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createBatch");
		}
		try {
			Map<String,Object> fieldValueMap = new HashMap<String, Object>();
			fieldValueMap.put("batchNo",batchData.getBatchNo() );
			
			List filterList = getFilterDataBy(EntityConstants.BATCH_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				throw new CreateBLException("BatchNo is already exists");
			}
			
			getEntityManager().persist(batchData);
			getEntityManager().flush();
			
		}catch(CreateBLException ex){
			throw ex;
		}catch(Exception e){
			e.printStackTrace();
			
			getSessionContext().setRollbackOnly();
			
			throw new CreateBLException("Create Batch Failed, Reason : " + e.getMessage(), e);
		}
		
		return batchData;
	}

	/**
	 * Create Batch Summary  
	 * @author yash.kapasi
	 * @param {@link Collection}<{@link BatchSummaryData}> batchSummaryDatas.
	 * @throws CreateBLException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public void createBatchSummary(Collection<BatchSummaryData> batchSummaryDatas)  throws CreateBLException{
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createBatchSummary");
		}
		try {
			Iterator<BatchSummaryData> iterator = batchSummaryDatas.iterator();
			while(iterator.hasNext()){
				getEntityManager().persist(iterator.next());
			}
			
			getEntityManager().flush();
			
		}catch(Exception e){
			e.printStackTrace();
			
			getSessionContext().setRollbackOnly();
			
			throw new CreateBLException("Create Batch Summary Failed, Reason : " + e.getMessage(), e);
		}
		
	}
	
	/**
	 * Updates Batch Data  
	 * @author yash.kapasi
	 * @param {@link BatchData} batchData
	 * @throws UpdateBLException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED ) 
	public void updateBatch(BatchData batchData) throws UpdateBLException {
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createBatch");
		}
		try {
			getEntityManager().merge(batchData);
			getEntityManager().flush();
			
		}catch(Exception e){
			e.printStackTrace();
			
			getSessionContext().setRollbackOnly();
			
			throw new UpdateBLException("Update Batch Failed, Reason : " + e.getMessage(), e);
		}
		
	}

	/**
	 * search Batch Summary Data  
	 * @author yash.kapasi
	 * @param {@link Long} batchid
	 * @return {@link List}<{@link BatchSummaryData}> batchSummaryDatas.
	 */
	public List<BatchSummaryData> searchBatchSummaryData(Long batchid){
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside searchBatchSummaryData");
		}
		List<BatchSummaryData> batchSummaryDatas=new ArrayList<BatchSummaryData>();
		try{
		Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
		
		fieldValueMap.put("batchId",batchid );
		 batchSummaryDatas = getFilterDataBy(EntityConstants.BATCH_SUMMARY_DATA, fieldValueMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return batchSummaryDatas;
	}

	
	/**
	 * search Batch  Data  
	 * @author yash.kapasi
	 * @param {@link SearchInventoryVO} searchInventoryVO
	 * @return {@link List}<{@link BatchData}> batchDataList.
	 */
	public List<BatchData> searchBatch(SearchInventoryVO searchInventoryVO){
		
		Logger.logInfo(MODULE, "inside searchInventoryVO");
		List<BatchData> batchDataList = null;
		try {
			
			/*if(searchInventoryVO.getExternalBatchNumber()!=null && !searchInventoryVO.getExternalBatchNumber().isEmpty()) {
				try {
					String sql = "select unique batchid from tblminventory where upper(externalbatchnumber) like upper('%"+searchInventoryVO.getExternalBatchNumber()+"%')";
					List list = getEntityManager().createNativeQuery(sql).getResultList();
					if(list!=null && !list.isEmpty()) {
						String batchNumber = (String) list.get(0);
						searchInventoryVO.setBatchId(batchNumber);
					}
				}catch(Exception e) {
				}
				
			}*/
			
			String hql = "from BatchData ";
			if(searchInventoryVO.getBatchId() != null && !searchInventoryVO.getBatchId().equals("")){
				hql += " where batchNo like '%"+searchInventoryVO.getBatchId()+"%'";
			}
			
			hql += " order by createdate desc ";
			
			List batchList = getEntityManager().createQuery(hql).getResultList();
			batchDataList = (List<BatchData>)batchList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return batchDataList;
	}

}
