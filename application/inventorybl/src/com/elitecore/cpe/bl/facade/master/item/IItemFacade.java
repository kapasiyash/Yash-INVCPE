package com.elitecore.cpe.bl.facade.master.item;

import java.util.List;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.migration.InventoryMigrationResponseVO;
import com.elitecore.cpe.bl.vo.inventorymgt.migration.InventoryMigrationVO;
import com.elitecore.cpe.bl.vo.inventorytransfer.CheckInventoryVO;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceTypeVO;
import com.elitecore.cpe.core.IBLSession;



public interface IItemFacade {

	public ItemVO createItem(ItemVO itemVo,IBLSession iblSession) throws CreateBLException;

	public List<ItemVO> searchItemData(ItemVO itemVo);

	public ItemVO viewItem(ItemVO itemVo);

	public void updateItem(ItemVO itemVo,IBLSession iblSession)throws UpdateBLException;

	public List<ComboData> getAllResourceTypeData();

	public List<ComboData> getAllResourceSubTypeDataByResourceTypeId(Long typeId) throws SearchBLException;

	public List<ComboData> getAllResourceCategories() throws SearchBLException;

	public List<ComboData> getAllResourceAttributes() throws SearchBLException;

	public void createResourceType(ResourceTypeVO prepareResourceTypeVO, IBLSession blSession) throws CreateBLException;

	public List<SearchResourceTypeVO> searchResourceTypeData(String name) throws SearchBLException;

	public void createResourceSubType(ResourceSubTypeVO prepareResourceTypeVO, IBLSession blSession) throws CreateBLException;

	public List<SearchResourceSubTypeVO> searchResourceSubTypeData(String name,Long resourceTypeId) throws SearchBLException;

	public SearchResourceTypeVO viewResourceTypeData(Long viewEntityId, IBLSession blSession) throws SearchBLException;

	public void updateResourceType(UpdateResourceTypeVO typeVO,IBLSession blSession) throws UpdateBLException;

	public SearchResourceSubTypeVO viewResourceSubTypeData(Long resourceSubTypeId) throws SearchBLException;

	public void updateResourceSubType(UpdateResourceSubTypeVO typeVO,IBLSession blSession) throws UpdateBLException;

	public List<ComboData> getAllResourceSubTypeData() throws SearchBLException;

	public List<ItemVO> searchItemDataComposer(ItemVO itemVo) ;

	public InventoryMigrationResponseVO migrateInventory(List<InventoryMigrationVO> inventoryMigrationVOs,boolean isValidate) throws SearchBLException;

	public List<ComboBoxData> getAllResourceData() throws SearchBLException;

	public List<AttributeVO> getAllAttributesfromResourceId(String resourceNumber) throws SearchBLException;

	public List<ComboData> getAllInventoryStatusDataByStatusId(Long typeId) throws SearchBLException;

	public CheckInventoryVO checkInventoryInWarehouse(String inventoryNumber,Long warehouseId,List<Integer> inventoryStatus) throws SearchBLException;

	public List<ComboData> getAllResourceTypeDataByResourceTypeId(Long resourceTypeId,Long warehouseId) throws SearchBLException;

	public List<ComboData> getAllResourceTypeDataByResourceTypeAndSubTypeId(Long resourceTypeId, Long resourceSubTypeId,Long warehouseId) throws SearchBLException;

}
