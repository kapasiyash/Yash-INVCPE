package com.elitecore.cpe.bl.delegates.master;

import java.util.List;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.BaseBusinessDelegate;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.master.item.IItemFacade;
import com.elitecore.cpe.bl.facade.master.item.ItemFacadeLocal;
import com.elitecore.cpe.bl.facade.master.item.ItemFacadeRemote;
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
import com.elitecore.cpe.core.IBDSessionContext;

public class ItemBD extends BaseBusinessDelegate{

	private static final String MODULE ="ITEM-BD";
	
	private static IItemFacade facade;
	
	public ItemBD(IBDSessionContext context){
		super(context);
	}
	
	private IItemFacade getFacade()  throws NamingException{
		if (facade == null) {
			if(isLocalMode()){
				facade = (IItemFacade)lookupLocal(ItemFacadeLocal.class);
			}else{
				facade = (IItemFacade)lookup(ItemFacadeRemote.class);
			}
		}
		return facade;
	}
	
	/**
	 * Create Item/Resource  
	 * @param {@link ItemVO} itemVo
	 * @return {@link ItemVO} itemVo
	 * @throws CreateBLException
	 */
	public ItemVO createItem(ItemVO itemVo) throws CreateBLException {
		// TODO Auto-generated method stub
		try {
			System.out.println("in WareHouse BD for Create Item");
			return getFacade().createItem(itemVo,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch(CreateBLException ex){
			throw ex;
		}
		return itemVo;
	}
	
	/**
	 * Get All Resource Type Data  
	 * @return {@link List}<{@link ComboData}> comboBoxDatas
	 */
	public List<ComboData> getAllResourceTypeData(){
		List<ComboData> comboBoxDatas = null;
		try {
			comboBoxDatas = getFacade().getAllResourceTypeData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comboBoxDatas;
	}

	
	/**
	 * Search Item Data 
	 * @param {@link ItemVO}  itemVo
	 * @return {@link List}<{@link ItemVO}> list
	 */
	public List<ItemVO> searchItemData(ItemVO itemVo) 
	{
		List<ItemVO> list = null;
		try {
			list = getFacade().searchItemData(itemVo);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * View Item Data 
	 * @param {@link ItemVO}  itemVo
	 * @return {@link ItemVO} itemVo
	 */
	public ItemVO viewItem(ItemVO itemVo) {
		ItemVO itemVo2 = null;
		try {
			itemVo2 = getFacade().viewItem(itemVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return itemVo2;
		}

	
	/**
	 * Update Item Data 
	 * @param {@link ItemVO}  itemVo
	 * @throws UpdateBLException
	 */
	public void updateItem(ItemVO itemVo) throws UpdateBLException {
		// TODO Auto-generated method stub
	try {
			getFacade().updateItem(itemVo,getBLSession());
				} catch (NamingException e) {
			e.printStackTrace();
		}catch(UpdateBLException ex){
			throw ex;
		}

	}

	/**
	 * Get All Resource Sub Type Data by resource Type Id 
	 * @param Long typeId
	 * @return {@link List}<{@link ComboData}>
	 */
	public List<ComboData> getAllResourceSubTypeDataByResourceTypeId(Long typeId) {
		try {
			return  getFacade().getAllResourceSubTypeDataByResourceTypeId(typeId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get All Resource Categories
	 * @return {@link List}<{@link ComboData}> 
	 */
	public List<ComboData> getAllResourceCategories() {
		try {
			return  getFacade().getAllResourceCategories();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get All Resource Attributes
	 * @return {@link List}<{@link ComboData}> 
	 */
	public List<ComboData> getAllResourceAttributes() {
		try {
			return  getFacade().getAllResourceAttributes();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create resource Type
	 * @param {@link ResourceTypeVO} prepareResourceTypeVO
	 * @throws CreateBLException 
	 */
	public void createResourceType(ResourceTypeVO prepareResourceTypeVO) throws CreateBLException {
		
		try {
			getFacade().createResourceType(prepareResourceTypeVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch(CreateBLException ex){
			throw ex;
		}
	}

	/**
	 * Search resource Type
	 * @param {@link String} name
	 * @return {@link List}<{@link SearchResourceTypeVO}>
	 * @throws SearchBLException 
	 */
	public List<SearchResourceTypeVO> searchResourceTypeData(String name) throws SearchBLException,  TechnicalException {
		
		
		try {
			return getFacade().searchResourceTypeData(name);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
		
		
	}

	/**
	 * Create resource Type
	 * @param {@link ResourceSubTypeVO} ResourceSubTypeVO
	 * @throws CreateBLException 
	 */
	public void createResourceSubType(ResourceSubTypeVO prepareResourceTypeVO) throws CreateBLException {
		try {
			getFacade().createResourceSubType(prepareResourceTypeVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch(CreateBLException ex){
			throw ex;
		}
		
	}

	/**
	 * Search resource SubType
	 * @param {@link String} name
	 * @param {@link Long} resourceTypeId
	 * @return {@link List}<{@link SearchResourceSubTypeVO}>
	 * @throws SearchBLException 
	 */
	public List<SearchResourceSubTypeVO> searchResourceSubTypeData(String name, Long resourceTypeId) throws SearchBLException, TechnicalException {
		try {
			return getFacade().searchResourceSubTypeData(name,resourceTypeId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

	/**
	 * View resource Type
	 * @param {@link Long} viewEntityId
	 * @return {@link SearchResourceTypeVO}
	 * @throws SearchBLException 
	 */
	public SearchResourceTypeVO viewResourceTypeData(Long viewEntityId) throws SearchBLException, TechnicalException {
		try {
			return getFacade().viewResourceTypeData(viewEntityId,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

	/**
	 * Update resource Type
	 * @param {@link UpdateResourceTypeVO} typeVO
	 * @throws UpdateBLException 
	 */
	public void updateResourceType(UpdateResourceTypeVO typeVO) throws UpdateBLException {
		try {
			getFacade().updateResourceType(typeVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch(UpdateBLException ex){
			throw ex;
		}
		
	}

	/**
	 * View resource SubType
	 * @param {@link Long} resourceSubTypeId
	 * @return {@link SearchResourceSubTypeVO}
	 * @throws SearchBLException 
	 */
	public SearchResourceSubTypeVO viewResourceSubTypeData(Long resourceSubTypeId) throws SearchBLException, TechnicalException {
		try {
			return getFacade().viewResourceSubTypeData(resourceSubTypeId);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

	
	/**
	 * Update resource SubType
	 * @param {@link UpdateResourceSubTypeVO} typeVO
	 * @throws UpdateBLException 
	 */
	public void updateResourceSubType(UpdateResourceSubTypeVO typeVO) throws UpdateBLException {
		try {
			getFacade().updateResourceSubType(typeVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch(UpdateBLException ex){
			throw ex;
		}
		
	}

	/**
	 * Get All resource SubType Data
	 * @return {@link List}<{@link ComboData}> comboBoxDatas
	 */
	public List<ComboData> getAllResourceSubTypeData() {
		List<ComboData> comboBoxDatas = null;
		try {
			comboBoxDatas = getFacade().getAllResourceSubTypeData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comboBoxDatas;
	}

	public List<ItemVO> searchItemDataComposer(ItemVO itemVo) {
		List<ItemVO> list = null;
		try {
			list = getFacade().searchItemDataComposer(itemVo);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	
	public InventoryMigrationResponseVO migrateInventory(
			List<InventoryMigrationVO> inventoryMigrationVOs,boolean isValidate) {
		try {
			return getFacade().migrateInventory(inventoryMigrationVOs,isValidate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
		
	public List<ComboBoxData> getAllResourceData() {
		
		try {
			return  getFacade().getAllResourceData();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<AttributeVO> getAllAttributesfromResourceId(String resourceNumber) {
		
		try {
			return  getFacade().getAllAttributesfromResourceId(resourceNumber);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get All Inventory Status Data by  Id 
	 * @param Long typeId
	 * @return {@link List}<{@link ComboData}>
	 */
	public List<ComboData> getAllInventoryStatusDataByStatusId(Long typeId) {
		try {
			return  getFacade().getAllInventoryStatusDataByStatusId(typeId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public CheckInventoryVO checkInventoryInWarehouse(String inventoryNumber,
			Long warehouseId,List<Integer> inventoryStatus) {
		
		try {
			return  getFacade().checkInventoryInWarehouse(inventoryNumber,warehouseId,inventoryStatus);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}