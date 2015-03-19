package com.elitecore.cpe.bl.facade.system.internal;

import java.util.List;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleGroupData;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.system.userwarehousemapping.UserWarehouseVO;
import com.elitecore.cpe.core.IBLSession;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public interface ISystemInternalFacade {

	public List<SystemModuleGroupData> getSystemModuleData() throws SearchBLException;

	public List<SystemModuleGroupData> getSystemModuleData(IBLSession blSession)throws SearchBLException;

	public List<SystemActionData> findSystemActionData(List<String> actionAliases)	throws SearchBLException;
	
	public List<SystemActionData> findAllSystemActions() throws SearchBLException;

	public List<ComboBoxData> getAllSystemAction() throws SearchBLException;

	public List<ComboData> getAllSystemModules() throws SearchBLException;

	public List<ComboBoxData> getAllSystemActionByModuleId(Long typeId) throws SearchBLException;

	public List<ComboBoxData> getAllUsers() throws SearchBLException;

	public List<ComboData> findUserWareHouseMapping(String userId) throws SearchBLException;

	public void updateUserWarehouseMapping(String userId,List<ComboData> selectedData, IBLSession blSession) throws UpdateBLException;
}
