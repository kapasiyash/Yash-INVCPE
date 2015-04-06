package com.elitecore.cpe.bl.facade.system.systemparameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.data.system.audit.AuditSummary;
import com.elitecore.cpe.bl.data.system.audit.AuditSummaryDetail;
import com.elitecore.cpe.bl.data.system.systemparameter.SystemParameterGroupWrapperData;
import com.elitecore.cpe.bl.data.system.systemparameter.SystemParameterWrapperData;
import com.elitecore.cpe.bl.entity.inventory.system.systemparameter.SystemParameter;
import com.elitecore.cpe.bl.entity.inventory.system.systemparameter.SystemParameterGroup;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.system.audit.AuditDataConversionUtilities;
import com.elitecore.cpe.bl.session.system.systemparameter.SystemParameterSessionBeanLocal;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;

/**
 * @author yash.kapasi
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SystemParameterFacade extends BaseFacade implements SystemParameterFacadeRemote, SystemParameterFacadeLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private static final String MODULE = "SYSTEM_PARAMETER_FACADE";
	@EJB private SystemParameterSessionBeanLocal systemParameterSessionBeanLocal;
	

	/**
	 * Method can be use read all system parameter and store in map key ->Alias
	 * value->systemparameter value
	 * 
	 * @return Map of system parameter /key ->Alias value->systemparameter value
	 * @throws SearchBLException
	 * @author Yash.Kapasi
	 */
	@Override
	public Map<String, String> getAllSystemParameterValue() throws SearchBLException {
		if (isTraceLevel())
			logTrace(MODULE, "Inside getAllSystemParameterValue");
		try {
			
			Map<String, String> systemParamterMap = null;
			List<SystemParameter> resultList = systemParameterSessionBeanLocal.findAllSystemParameter();
			
			if (resultList != null && !resultList.isEmpty()) {
				systemParamterMap = new HashMap<String, String>();
				for (SystemParameter systemParameter : resultList) {
					systemParamterMap.put(systemParameter.getAlias(),systemParameter.getValue());
				}
			}
			
			if (isDebugLevel())
				logDebug(MODULE, "Value AllSystemParameterValue = :"+ systemParamterMap);
			
			return systemParamterMap;
		} catch (RuntimeException e) {
			if (isErrorLevel())
				logError(MODULE,"Find All System Parameter Fail Reason" + e.getMessage());
		}
		throw new SearchBLException("find all systemparamtae read fail, pease try agin");
	}

	@Override
	public List<SystemParameterGroupWrapperData> findAllSystemParameterGroups() throws SearchBLException {
		List<SystemParameterGroupWrapperData> systemParameterGroupWrapperDatas = new ArrayList<SystemParameterGroupWrapperData>();		
		List<SystemParameterGroup> systemParameterGroups = systemParameterSessionBeanLocal.findAllSystemParameterGroup();
		if(systemParameterGroups != null && !systemParameterGroups.isEmpty()) {		
			for(SystemParameterGroup systemParameterGroup : systemParameterGroups) {
				SystemParameterGroupWrapperData systemParameterGroupWrapperData = new SystemParameterGroupWrapperData();
				systemParameterGroupWrapperData.setSystemParameterGroupId(systemParameterGroup.getSystemParameterGroupId());
				systemParameterGroupWrapperData.setName(systemParameterGroup.getName());
				systemParameterGroupWrapperData.setIsVisible(systemParameterGroup.getIsVisible());
				systemParameterGroupWrapperData.setDescription(systemParameterGroup.getDescription());
				systemParameterGroupWrapperData.setAlias(systemParameterGroup.getAlias());
				Set<SystemParameterWrapperData> systemParameterWrapperDatas = new HashSet<SystemParameterWrapperData>();
				if(systemParameterGroup.getSystemParameters() != null && !systemParameterGroup.getSystemParameters().isEmpty()) {
					for(SystemParameter systemParameter : systemParameterGroup.getSystemParameters()) {
						SystemParameterWrapperData systemParameterWrapperData = systemParameter.prepareSystemParameterWrapperData();
						if(systemParameter.getSysPrmCustomFieldType()!=null && systemParameter.getSysPrmCustomFieldType().getCustomfieldtypeid().equals(SystemParameterConstants.SQL_COMBO_BOX)){
							List<Object[]> objectList =  systemParameterSessionBeanLocal.findValueSource(systemParameter.getValueSource());
							if(objectList != null && !objectList.isEmpty()){
								Map<String, String> map = new HashMap<String, String>();
								Logger.logTrace(MODULE, "Data :; "+objectList);
								for(Object[] objects : objectList){
									String character = (String)objects[0];
									Logger.logTrace(MODULE, "Id :; "+character);
									Logger.logTrace(MODULE, "Objects :; "+objects[1]);
									map.put(objects[0].toString(),objects[1].toString());
									
								}
								systemParameterWrapperData.setComboBoxMap(map);
							}
						}else if(systemParameter.getSysPrmCustomFieldType()!=null && systemParameter.getSysPrmCustomFieldType().getCustomfieldtypeid().equals(SystemParameterConstants.COMBO_BOX)){
							
							if(systemParameter.getValueSource() != null && !systemParameter.getValue().isEmpty()){
								String[] stringList = systemParameter.getValueSource().split(",");
								Map<String, String> map = new HashMap<String, String>();
								for(String string : stringList){
									map.put(string,string);
								}
								systemParameterWrapperData.setComboBoxMap(map);
							}
						} else if(systemParameter.getSysPrmCustomFieldType()!=null && systemParameter.getSysPrmCustomFieldType().getCustomfieldtypeid().equals(SystemParameterConstants.BAND_BOX)){
							
							if(systemParameter.getValueSource() != null && !systemParameter.getValue().isEmpty()){
								String[] stringList = systemParameter.getValueSource().split(",");
								Map<String, String> map = new HashMap<String, String>();
								for(String string : stringList){
									String valueSource[] = string.split(":");
									map.put(valueSource[0],valueSource[1]);
								}
								systemParameterWrapperData.setComboBoxMap(map);
							}
						}
						systemParameterWrapperDatas.add(systemParameterWrapperData);
					}
				}
				systemParameterGroupWrapperData.setSystemParameterWrapperDatas(systemParameterWrapperDatas);
				systemParameterGroupWrapperDatas.add(systemParameterGroupWrapperData);
			}			
		}						
		return systemParameterGroupWrapperDatas; 
	}
	
	@Override
	public void updateSystemParameters(List<SystemParameterGroupWrapperData> systemParameterGroupWrapperDatas,IBLSession iblSession) throws UpdateBLException, SearchBLException{				
		try{
			for(SystemParameterGroupWrapperData systemParameterGroupWrapperData : systemParameterGroupWrapperDatas) {
				if(systemParameterGroupWrapperData.getSystemParameterWrapperDatas() != null && !systemParameterGroupWrapperData.getSystemParameterWrapperDatas().isEmpty()) {
					AuditSummaryDetail data = new AuditSummaryDetail();
					for(SystemParameterWrapperData systemParameterWrapperData : systemParameterGroupWrapperData.getSystemParameterWrapperDatas()) {		
						SystemParameter systemParameter = systemParameterSessionBeanLocal.findSystemParameterById(systemParameterWrapperData.getSystemParameterId());
						
						data = AuditDataConversionUtilities.prepareSystemParameterUpdateAudit(data,systemParameter, systemParameterWrapperData, iblSession);

						systemParameter.setValue(systemParameterWrapperData.getValue());
						systemParameterSessionBeanLocal.updateSystemParameter(systemParameter);
					}
					//Audit Data
		 			if(data != null) {
		 				Map<String,Object> mapAudit = new HashMap<String, Object>();
		 				
		 				addToAuditDynamicMessage(AuditConstants.UPDATE_SYSTEM_PARAMETER, "Updating System Parameter",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,data.getAuditEntryWrapper(), iblSession);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new UpdateBLException(e.getMessage());
		}						
	}
	
	/**
	 * Find system parameter value.
	 * @param systemParameterAlias
	 * @return String
	 * @throws SearchBLException
	 */
	@Override
	public String getSystemParameterValue(String systemParameterAlias) throws SearchBLException{
		Logger.logDebug(MODULE,"In getSystemParameterValue");
		Map<String, String> sysParam = getAllSystemParameterValue();
		return sysParam.get(systemParameterAlias);
		
	}
}
