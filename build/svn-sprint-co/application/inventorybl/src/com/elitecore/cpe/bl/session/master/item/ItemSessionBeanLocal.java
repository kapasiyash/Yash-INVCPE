package com.elitecore.cpe.bl.session.master.item;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.elitecore.cpe.bl.entity.inventory.core.PrimaryKey;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.ResourceAttributeRel;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceSubTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceTypeData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorytransfer.CheckInventoryVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;

@Local
public interface ItemSessionBeanLocal {

	
	public List getFilterDataBy(String entityName,Map<String,Object> fieldValueMap);

	public ItemData createItem(ItemData itemdata) throws CreateBLException;

	public List<ItemData> searchItemData(ItemVO itemvo);

	public ItemData viewItem(ItemVO itemvo);

	public void updateItem(ItemData itemData)throws UpdateBLException;
	
	public void savePrimaryKey(PrimaryKey primaryData) throws CreateBLException;

	public List findResourceSubTypeByTypeId(Long typeId) throws SearchBLException;

	public void removeAttributeRel(ResourceAttributeRel rel) throws  UpdateBLException;

	public void createResourceType(ResourceTypeData typeData) throws CreateBLException;

	public List<ResourceTypeData> searchResourceTypeData(String name) throws SearchBLException;

	public void createResourceSubType(ResourceSubTypeData typeData) throws CreateBLException;

	public List<ResourceSubTypeData> searchResourceSubTypeData(String name,Long resourceTypeId) throws SearchBLException;

	public ResourceTypeData viewResourceType(Long resourceTypeId) throws SearchBLException;

	public void updateResourceType(ResourceTypeData typeData) throws UpdateBLException;

	public ResourceSubTypeData viewResourceSubType(Long resourceSubTypeId) throws SearchBLException;

	public void updateResourceSubType(ResourceSubTypeData typeData) throws UpdateBLException;
	
	public Boolean isResourceExist(String name);

	public List<ItemData> findResource(String resourceName, String resourceType,String resourceSubType) throws SearchBLException;

	public List findInventoryStatusById(Long typeId)throws SearchBLException;

	public CheckInventoryVO checkInventoryInWarehouse(String inventoryNumber,Long warehouseId, List<Integer> inventoryStatus) throws SearchBLException;

}
