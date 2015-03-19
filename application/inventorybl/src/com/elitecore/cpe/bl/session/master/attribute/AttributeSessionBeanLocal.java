package com.elitecore.cpe.bl.session.master.attribute;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.elitecore.cpe.bl.entity.inventory.core.PrimaryKey;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeTransData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;

@Local
public interface AttributeSessionBeanLocal {

	
	public void saveAttribute(AttributeData attributeData) throws CreateBLException;

	public List<AttributeData> searchAttributeData(AttributeVO attributeVO);

	public void updateAttribute(AttributeData attributeData) throws UpdateBLException;
	
	public List getFilterDataBy(String entityName,Map<String,Object> fieldValueMap);

	public boolean isAttributeValueUnique(Long attributeId,String attributeValue,String tableRefName);
	public Boolean isAttributeExist(String name);
	public Boolean isAttributeAttached(Long attributeId);

	public List<AttributeData> searchAttributeDataByInventoryId(Long id) throws SearchBLException;
	
	public List<Object[]> searchAttributeDataByTransferOrderId(String transferOrderNumber) throws SearchBLException;

	public List<Object[]> searchAttributeDataByResourceId(String resourceId) throws SearchBLException;

	public List<Object[]> searchAttributeDataByBatchId(String attributeNames) throws SearchBLException;
	
	public List<AttributeTransData> searchAttributeTransDataByInventoryId(Long id) throws SearchBLException;
	
}
