package com.elitecore.cpe.bl.delegates.master;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.BaseBusinessDelegate;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.master.warehouse.IWarehouseFacade;
import com.elitecore.cpe.bl.facade.master.warehouse.WarehouseFacadeLocal;
import com.elitecore.cpe.bl.facade.master.warehouse.WarehouseFacadeRemote;
import com.elitecore.cpe.bl.vo.master.ConfigureThresholdVO;
import com.elitecore.cpe.bl.vo.master.WareHouseSummaryVO;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.bl.vo.master.warehouse.CreateWareHouseTreeVO;
import com.elitecore.cpe.core.IBDSessionContext;

public class WareHouseBD extends BaseBusinessDelegate{

	private static final String MODULE ="WAREHOUSE-BD";
	
	private static IWarehouseFacade facade;
	
	public WareHouseBD(IBDSessionContext context){
		super(context);
	}
	
	private IWarehouseFacade getFacade()  throws NamingException{
		if (facade == null) {
			if(isLocalMode()){
				facade = (IWarehouseFacade)lookupLocal(WarehouseFacadeLocal.class);
			}else{
				facade = (IWarehouseFacade)lookup(WarehouseFacadeRemote.class);
			}
		}
		return facade;
	}
	
	/**
	 * Save warehouse
	 * @param {@link WarehouseVO} warehouseVO
	 * @throws CreateBLException
	 */
	public void saveWarehouse(WarehouseVO warehouseVO) throws CreateBLException{
		
		try {
			getFacade().saveWarehouse(warehouseVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(CreateBLException ex){
			throw ex;
		}
		
	}
	
	/**
	 * Search warehouse Data
	 * @param {@link WarehouseVO} warehouseVO
	 * @return {@link List}<{@link WarehouseVO}>
	 */
	public List<WarehouseVO> searchWarehouseData(WarehouseVO warehouseVO){
		List<WarehouseVO>  list = null;
		try {
			list = getFacade().searchWarehouseData(warehouseVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * View warehouse Data
	 * @param {@link WarehouseVO} warehouseVO
	 * @return {@link WarehouseVO} warehouseVO
	 */
	public WarehouseVO viewWarehouse(WarehouseVO warehouseVO){
		WarehouseVO warehouseVO2 = null;
		try {
			warehouseVO2 = getFacade().viewWarehouse(warehouseVO,getBLSession());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return warehouseVO2;
	}
	
	
	/**
	 * Update warehouse Data
	 * @param {@link WarehouseVO} warehouseVO
	 * @throws UpdateBLException
	 */
	public void updateWarehouse(WarehouseVO warehouseVO)  throws UpdateBLException{
		try {
			getFacade().updateWarehouse(warehouseVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(UpdateBLException ex){
			throw ex;
		}
	}

	/**
	 * Get All Warehouse Data
	 * @return {@link List}<{@link ComboData}> comboBoxDatas
	 */
	public List<ComboData> getAllWarehouseData(){
		List<ComboData> comboBoxDatas = null;
		try {
			comboBoxDatas = getFacade().getAllWarehouseData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comboBoxDatas;
	}

	
	/**
	 * Delete Warehouse Data
	 * @param {@link WarehouseVO} warehouseVO
	 * @throws UpdateBLException
	 */
	public void deleteWarehouse(WarehouseVO warehouseVO)  throws UpdateBLException{
		try {
			getFacade().deleteWarehouse(warehouseVO, getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(UpdateBLException ex){
			throw ex;
		}
	}
	
	
	public List<ComboData> getAllWarehouseDataExceptBy(Long warehouseID){
		List<ComboData> comboBoxDatas = null;
		try {
			comboBoxDatas = getFacade().getAllWarehouseDataExceptBy(warehouseID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comboBoxDatas;
	}
	
	
	/**
	 * Save Warehouse Type Data
	 * @param {@link WarehouseTypeVO} warehouseTypeVO
	 * @throws CreateBLException
	 */
	public void saveWarehouseType(WarehouseTypeVO warehouseTypeVO) throws CreateBLException{
		
		try {
			getFacade().saveWarehouseType(warehouseTypeVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(CreateBLException ex){
			throw ex;
		}
		
	}
	
	/**
	 * Search Warehouse Type Data
	 * @param {@link WarehouseTypeVO} warehouseTypeVO
	 * @return {@link List}<{@link WarehouseTypeVO}> list
	 */
	public List<WarehouseTypeVO> searchWarehouseTypeData(WarehouseTypeVO warehouseTypeVO){
		List<WarehouseTypeVO>  list = null;
		try {
			list = getFacade().searchWarehouseTypeData(warehouseTypeVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * View Warehouse Type Data
	 * @param {@link WarehouseTypeVO} warehouseTypeVO
	 * @return {@link WarehouseTypeVO} warehouseTypeVO
	 */
	public WarehouseTypeVO viewWarehouseType(WarehouseTypeVO warehouseTypeVO){
		WarehouseTypeVO warehouseVO2 = null;
		try {
			warehouseVO2 = getFacade().viewWarehouseType(warehouseTypeVO,getBLSession());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return warehouseVO2;
	}
	
	
	/**
	 * Update Warehouse Type Data
	 * @param {@link WarehouseTypeVO} warehouseTypeVO
	 * @throws UpdateBLException
	 */
	public void updateWarehouseType(WarehouseTypeVO warehouseTypeVO)  throws UpdateBLException{
		try {
			getFacade().updateWarehouseType(warehouseTypeVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(UpdateBLException ex){
			throw ex;
		}
	}

	
	/**
	 * Get All Warehouse Type Data
	 * @return {@link List}<{@link ComboData}> comboBoxDatas
	 */
	public List<ComboData> getAllWarehouseTypeData(){
		List<ComboData> comboBoxDatas = null;
		try {
			comboBoxDatas = getFacade().getAllWarehouseTypeData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comboBoxDatas;
	}
	public Map<ComboData, List<ComboData>> getAllResourceTypeWithResource (Long warehouseid){
		Map map = null;
		try {
			map = getFacade().getAllResourceTypeWithResource(warehouseid) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	

	/**
	 * Save Threshold
	 * @param {@link List}<{@link ConfigureThresholdVO}> configureThresholdVOs
	 * @throws CreateBLException
	 */
	public void saveThreshold(List<ConfigureThresholdVO> configureThresholdVOs) throws CreateBLException{
		
		try {
			
			getFacade().saveThreshold(configureThresholdVOs,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(CreateBLException ex){
			throw ex;
		}
		
	}
	
	
	/**
	 * Search Threshold Data
	 * @param {@link ConfigureThresholdVO} configureThresholdVO
	 * @return {@link List}<{@link ConfigureThresholdVO}> configureThresholdVOs
	 */
	public List<ConfigureThresholdVO> searchThresholdData(ConfigureThresholdVO configureThresholdVO){
		List<ConfigureThresholdVO>  list = null;
		try {
			list = getFacade().searchThresholdData(configureThresholdVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	
	public List<ComboData> getAllResourceTypeWithWareHouseId(Long wareHouseId) {
		
		List<ComboData> comboBoxDatas = null;
		try {
			comboBoxDatas = getFacade().getAllResourceTypeWithWareHouseId(wareHouseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comboBoxDatas;
		
	}
	
	/**
	 * Search Warehouse Summary Data
	 * @param {@link Long} wareHouseId
	 * @return {@link List}<{@link WareHouseSummaryVO}>
	 * @throws SearchBLException
	 */
	public List<WareHouseSummaryVO> searchWarehouseSummaryData(Long wareHouseId) throws SearchBLException {
		
		
		try {
			return getFacade().searchWarehouseSummaryData(wareHouseId);
		}catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e);
		}catch (SearchBLException e) {
			throw e;
		}
	}
	
	
	/**
	 * Create warehouse Tree
	 * @return {@link List}<{@link CreateWareHouseTreeVO}>
	 * @throws SearchBLException
	 */
	public List<CreateWareHouseTreeVO> createWareHouseTree() throws SearchBLException {
		try {
			return getFacade().createWareHouseTree();
		}catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e);
		}catch (SearchBLException e) {
			throw e;
		}
	}

	
	/**
	 * Find Child warehouse by parent warehouse Id
	 * @param Long wareHouseId
	 * @return {@link CreateWareHouseTreeVO}
	 * @throws SearchBLException
	 */
	public CreateWareHouseTreeVO findChildWareHouses(Long wareHouseId) throws SearchBLException {
		
		try {
			return getFacade().findChildWareHouses(wareHouseId);
		}catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e);
		}catch (SearchBLException e) {
			throw e;
		}
		
	}

	public void deleteThreshold(List<ConfigureThresholdVO> configureThresholdVOs)throws UpdateBLException {
		// TODO Auto-generated method stub
		try {
			getFacade().deleteThreshold(configureThresholdVOs, getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(UpdateBLException ex){
			throw ex;
		}
	}
	
	
}