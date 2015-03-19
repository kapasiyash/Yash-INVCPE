package com.elitecore.cpe.bl.facade.master.attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.data.system.audit.AuditSummaryDetail;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.system.audit.AuditDataConversionUtilities;
import com.elitecore.cpe.bl.session.master.attribute.AttributeSessionBeanLocal;
import com.elitecore.cpe.bl.session.system.internal.SystemInternalSessionBeanLocal;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;

@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class AttributeFacade extends BaseFacade implements AttributeFacadeRemote,AttributeFacadeLocal{

	private static final String MODULE = "ATTRIBUTE-FC";
	
	
	@EJB private SystemInternalSessionBeanLocal systemInternalSessionBeanLocal;
	@EJB private AttributeSessionBeanLocal attributeSessionBeanLocal;
	

	/**
	 * Save Attribute
	 * @author yash.kapasi
	 * @param {@link AttributeVO} attributeVO
	 * @param {@link IBLSession}
	 * @throws CreateBLException
	 */
	@Override
	public void saveAttribute(AttributeVO attributeVO,IBLSession iblSession)
			throws CreateBLException {
		
		Logger.logTrace(MODULE, "inside saveAttribute()");
		try {
			if(!(attributeSessionBeanLocal.isAttributeExist(attributeVO.getName()))){
			attributeVO.setSystemgenerated("N");
			
			AttributeData attributeData = AttributeUtil.getAttributeData(attributeVO);
			
			attributeSessionBeanLocal.saveAttribute(attributeData);
			
			// Audit entry
			Map<String,Object> mapAudit = new HashMap<String, Object>();
			mapAudit.put(AuditTagConstant.NAME,attributeVO.getName());
						
			addToAuditDynamicMessage(AuditConstants.CREATE_ATTRIBUTE, "Creating Attribute",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, iblSession);
			
			}else{
				throw new CreateBLException("Attribute Name " + attributeVO.getName() +" Already Exists");
			}
		}catch (CreateBLException e) {
			throw e;
		}
	}

	/**
	 * Search Attribute Datas
	 * @author yash.kapasi
	 * @param {@link AttributeVO} attributeVO
	 * @return {@link List}<{@link AttributeVO}> data
	 */
	@Override
	public List<AttributeVO> searchAttributeData(AttributeVO attributeVO) {
		Logger.logTrace(MODULE, "inside searchAttributeData()");
		List<AttributeVO> data = new ArrayList<AttributeVO>();
		try {
			List<AttributeData> data1 = attributeSessionBeanLocal.searchAttributeData(attributeVO);
			if(data1 != null){
				for(AttributeData vo : data1){
					data.add(AttributeUtil.getAttributeVO(vo));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public AttributeVO viewAttribute(AttributeVO attributeVO) {
		Logger.logTrace(MODULE, "inside viewAttribute()");
		AttributeVO newAttributeVO = null;
		try {
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("attributeId", attributeVO.getAttributeId());
			
			List filterList = attributeSessionBeanLocal.getFilterDataBy(EntityConstants.ATTRIBUTE_DATA, fieldValueMap);
			if(filterList != null && !filterList.isEmpty()){
				newAttributeVO = AttributeUtil.getAttributeVO(((List<AttributeData>)filterList).get(0));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return newAttributeVO;
		
	}

	/**
	 * Update Attribute Data
	 * @author yash.kapasi
	 * @param {@link AttributeVO} attributeVO
	 * @param {@link IBLSession} iblSession
	 * @throws UpdateBLException
	 */
	@Override
	public void updateAttribute(AttributeVO attributeVO,IBLSession iblSession)
			throws UpdateBLException {
		Logger.logTrace(MODULE, "inside updateAttribute()");
		
		try {
			
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			fieldValueMap.put("systemgenerated", "N");
			fieldValueMap.put("attributeId", attributeVO.getAttributeId());
			List filterList = attributeSessionBeanLocal.getFilterDataBy(EntityConstants.ATTRIBUTE_DATA, fieldValueMap);
			
			AttributeData attributeData = null;
			
			if(filterList != null && !filterList.isEmpty()){
				
				attributeData = ((List<AttributeData>)filterList).get(0);
				if(!attributeData.getName().equalsIgnoreCase(attributeVO.getName())){
					if(!(attributeSessionBeanLocal.isAttributeExist(attributeVO.getName()))){
					}else{
						throw new UpdateBLException("Attribute Name " + attributeVO.getName() +" Already Exists");
					}
				}
				if(!(attributeSessionBeanLocal.isAttributeAttached(attributeVO.getAttributeId()))){
				AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareAttributeUpdateAudit(attributeData, attributeVO);
				
				attributeData.setName(attributeVO.getName());
				attributeData.setUsedBy(attributeVO.getUsedBy());
				attributeData.setUpdatedby(attributeVO.getUpdatedby());
				attributeData.setUpdatedate(attributeVO.getUpdatedate());
				attributeData.setDataType(attributeVO.getDataType());
				attributeData.setDataValue(attributeVO.getDataValue());
				attributeData.setMandatory(attributeVO.getMandatory());
				attributeData.setReason(attributeVO.getReason());
				attributeData.setUnique(attributeVO.getUnique());
				
				attributeSessionBeanLocal.updateAttribute(attributeData);
				// Audit entry
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.NAME,attributeVO.getName());
				
				addToAuditDynamicMessage(AuditConstants.UPDATE_ATTRIBUTE, "Updating Attribute",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(), iblSession);
				}else{
					throw new UpdateBLException("Attribute Name:" + attributeData.getName() +" attached with Inventory, cannot be updated");
				}
			}
			
		}catch(UpdateBLException ex){
			throw ex;
		}
	}
	
	public List<AttributeVO> getAttributesByRef(String usedby) {
		Logger.logTrace(MODULE, "inside searchAttributeData()");
		List<AttributeVO> data = new ArrayList<AttributeVO>();
		try {
			Map<String,Object> fieldValueMap = new LinkedHashMap<String, Object>();
			
			fieldValueMap.put("ATTRIBUTEREL",usedby);
			List<AttributeData> data1 = attributeSessionBeanLocal.getFilterDataBy(EntityConstants.ATTRIBUTE_DATA,fieldValueMap);
			if(data1 != null){
				for(AttributeData vo : data1){
					data.add(AttributeUtil.getAttributeVO(vo));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}

}
