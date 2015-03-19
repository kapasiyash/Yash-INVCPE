package com.elitecore.cpe.bl.session.master.warehouse;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;


import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.entity.inventory.core.PrimaryKey;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.ConfigureThresholdData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseTypeData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.bl.vo.master.ConfigureThresholdVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.core.IBLSession;
import com.sun.rowset.CachedRowSetImpl;

@Local
public interface WarehouseSessionBeanLocal {

	public void saveWarehouse(WarehouseData warehouseData) throws CreateBLException;
	
	public List<WarehouseData> searchWarehouseData(WarehouseVO warehouseVO);
	
	public void updateWarehouse(WarehouseData warehouseData) throws UpdateBLException;
		
	public List getFilterDataBy(String entityName,Map<String,Object> fieldValueMap);

	public void deleteWarehouse(WarehouseData warehouseData) throws UpdateBLException;
	
	public void saveWarehouseType(WarehouseTypeData warehouseTypeData) throws CreateBLException;
	
	public List<WarehouseTypeData> searchWarehouseTypeData(WarehouseTypeVO warehouseTypeVO);
	
	public void updateWarehouseType(WarehouseTypeData warehouseTypeData) throws UpdateBLException;
	
	public void saveThreshold(List<ConfigureThresholdData> configureThresholdDatas,IBLSession iblSession) throws CreateBLException;
	public List<ConfigureThresholdData> searchThresholdData (ConfigureThresholdVO configureThresholdVO);
	
	public CachedRowSetImpl searchThresholdStatus();

	public ConfigureThresholdData findThresholdValue(Long wareHouseId, Long resourceTypeId,Long resourceSubTypeId) throws SearchBLException;
	public CachedRowSetImpl getAllResourceTypeWithResource (Long warehouseid);
	public Boolean isWarehouseExist(String name);
	public Boolean isWarehouseTypeExist(String name);

	public List checkInventoryOnSerialNumber(String serialNumber) ;
	
	public List searchQuery(String query) throws SearchBLException;

	public List<WarehouseData> findAllCentralWareHouses() throws SearchBLException;

	public List<WarehouseData> findChildWareHouses(Long warehouseId) throws SearchBLException;

	public void deleteThreshold(ConfigureThresholdData thresholdData, IBLSession blSession) throws UpdateBLException;

	public ConfigureThresholdData findConfigureThresholdDataByThresholdID(
			Long thresholdID) throws SearchBLException;


	
}
