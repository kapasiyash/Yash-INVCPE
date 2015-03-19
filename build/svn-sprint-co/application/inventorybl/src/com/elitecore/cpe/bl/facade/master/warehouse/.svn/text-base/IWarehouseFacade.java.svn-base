package com.elitecore.cpe.bl.facade.master.warehouse;

import java.util.List;
import java.util.Map;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.master.ConfigureThresholdVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.WareHouseSummaryVO;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.bl.vo.master.warehouse.CreateWareHouseTreeVO;
import com.elitecore.cpe.core.IBLSession;

public interface IWarehouseFacade {

	public void saveWarehouse(WarehouseVO warehouseVO,IBLSession iblSession) throws CreateBLException;
	
	public List<WarehouseVO> searchWarehouseData(WarehouseVO warehouseVO,IBLSession iblSession);
	
	public WarehouseVO viewWarehouse(WarehouseVO warehouseVO,IBLSession iblSession);
	
	public void updateWarehouse(WarehouseVO warehouseVO,IBLSession iblSession) throws UpdateBLException;
	
	public List<ComboData> getAllWarehouseData();

	public List<ComboData> getAllWarehouseDataExceptBy(Long warehouseId);
	
	public void deleteWarehouse(WarehouseVO warehouseVO,IBLSession iblSession) throws UpdateBLException;
	
	public void saveWarehouseType(WarehouseTypeVO warehouseTypeVO,IBLSession iblSession) throws CreateBLException;
	
	public List<WarehouseTypeVO> searchWarehouseTypeData(WarehouseTypeVO warehouseTypeVO,IBLSession iblSession);
	
	public WarehouseTypeVO viewWarehouseType(WarehouseTypeVO WarehouseTypeVO,IBLSession iblSession);
	
	public void updateWarehouseType(WarehouseTypeVO warehouseVO,IBLSession iblSession) throws UpdateBLException;
	
	public List<ComboData> getAllWarehouseTypeData();
	public Map<ComboData, List<ComboData>> getAllResourceTypeWithResource (Long warehouseid);
	public void saveThreshold(List<ConfigureThresholdVO> configureThresholdVOs,IBLSession iblSession) throws CreateBLException;
	public List<ConfigureThresholdVO> searchThresholdData(ConfigureThresholdVO configureThresholdVO,IBLSession iblSession);
	
	public void searchThresholdStatus(IBLSession iblSession);
	
	public List<WarehouseVO> getAllWareHouseData() throws SearchBLException;
	
	public List<ItemVO> getResourcesWithWareHouseId(Long wareHouseId) throws SearchBLException;
	
	public NotificationData calculateThreasholdValue(Long wareHouseId, Long resourceId, Long resourceTypeId,Long resourceSubTypeId) throws Exception;

	public List<ComboData> getAllResourceTypeWithWareHouseId(Long wareHouseId) throws SearchBLException;

	public List<WareHouseSummaryVO> searchWarehouseSummaryData(Long wareHouseId) throws SearchBLException;

	public List<CreateWareHouseTreeVO> createWareHouseTree() throws SearchBLException;

	public CreateWareHouseTreeVO findChildWareHouses(Long wareHouseId) throws SearchBLException;

	public void deleteThreshold(List<ConfigureThresholdVO> configureThresholdVOs,IBLSession blSession) throws UpdateBLException;
	
}
