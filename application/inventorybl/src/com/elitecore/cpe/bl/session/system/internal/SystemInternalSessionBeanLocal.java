package com.elitecore.cpe.bl.session.system.internal;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.entity.inventory.bss.user.BSSUser;
import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemAction;
import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemModule;
import com.elitecore.cpe.bl.entity.inventory.system.userwarehouse.UserWarehouseMapping;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;




@Local
public interface SystemInternalSessionBeanLocal extends Serializable {

//	public List<SystemModuleGroup> findAllSystemModuleGroup() throws SearchBLException;
	public String getPrimaryKey(String alias) throws SearchBLException;
	public SystemAction findSystemActionById(Long actionId) throws SearchBLException;
	public SystemAction findSystemActionByAlias(String actionAlias) throws SearchBLException;
	public void updateSystemAction(SystemAction systemAction) throws UpdateBLException;
	public List<SystemModule> findAllSystemModules() throws SearchBLException;
	public List<SystemAction> findAllSystemAction() throws SearchBLException;
	public SystemModule findSystemModuleById(Long typeId) throws SearchBLException;
	
	public boolean isPrefixAvailable(String prefix);
	public String getAliasByPrefix(String prefix) throws SearchBLException;
	public List<BSSUser> findAllUsers() throws SearchBLException;
	public List<UserWarehouseMapping> findUserWareHouseMapping(String userId) throws SearchBLException;
	public void deleteUserWareHouseMapping(String userId) throws UpdateBLException;
	public void persistUserWarehouseMapping(UserWarehouseMapping mapping) throws CreateBLException;
}
