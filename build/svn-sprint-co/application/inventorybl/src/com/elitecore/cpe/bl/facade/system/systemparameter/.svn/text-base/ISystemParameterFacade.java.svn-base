package com.elitecore.cpe.bl.facade.system.systemparameter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.elitecore.cpe.bl.data.system.systemparameter.SystemParameterGroupWrapperData;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.core.IBLSession;

/**
 * @author Yash.Kapasi
 */
public interface ISystemParameterFacade extends Serializable {

	
	public Map<String, String> getAllSystemParameterValue() throws SearchBLException;
	public List<SystemParameterGroupWrapperData> findAllSystemParameterGroups()throws SearchBLException;
	public void updateSystemParameters(List<SystemParameterGroupWrapperData> systemParameterGroupWrapperDatas,IBLSession iblSession) throws UpdateBLException, SearchBLException;
	public String getSystemParameterValue(String systemParameterAlias)	throws SearchBLException;
	
	

}
