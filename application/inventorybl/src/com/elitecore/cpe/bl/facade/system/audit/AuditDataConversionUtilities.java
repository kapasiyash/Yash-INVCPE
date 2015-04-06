package com.elitecore.cpe.bl.facade.system.audit;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.elitecore.cpe.bl.constants.inventorymgt.InventoryStatusConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.data.notification.DocumentTemplateWrapperdata;
import com.elitecore.cpe.bl.data.system.audit.AuditSummary;
import com.elitecore.cpe.bl.data.system.audit.AuditSummaryDetail;
import com.elitecore.cpe.bl.data.system.systemparameter.SystemParameterWrapperData;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentTemplate;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.OrderData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.ConfigureThresholdData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceSubTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseTypeData;
import com.elitecore.cpe.bl.entity.inventory.system.agent.schedule.SystemAgentSchedule;
import com.elitecore.cpe.bl.entity.inventory.system.audit.AuditEntry;
import com.elitecore.cpe.bl.entity.inventory.system.audit.SystemAudit;
import com.elitecore.cpe.bl.entity.inventory.system.audit.ViewAuditData;
import com.elitecore.cpe.bl.entity.inventory.system.audit.WebServiceAuditEntry;
import com.elitecore.cpe.bl.entity.inventory.system.systemparameter.SystemParameter;
import com.elitecore.cpe.bl.exception.NoSuchControllerException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.facade.BaseDataConversionUtils;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.vo.inventorymgt.ChangeInventorySubStatusVO;
import com.elitecore.cpe.bl.vo.inventorymgt.PlaceOrderVO;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.bl.vo.system.agent.UpdateAgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchWsAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewAuditEntryVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewWsAuditEntryVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.core.IBLSession;

public class AuditDataConversionUtilities extends BaseDataConversionUtils {

	
	public static List<SearchAuditVO> prepareSerachAuditDataByDate(List<SystemAudit> systemAudits, IBLSession iblSession) {
		
		List<SearchAuditVO> auditVOList = new ArrayList<SearchAuditVO>();
		SearchAuditVO auditVO = null;
		for(SystemAudit systemAudit : systemAudits) {
			auditVO = new SearchAuditVO();
			auditVO.setReason(systemAudit.getReason());
			auditVO.setSystemAuditId(systemAudit.getSystemauditid());
			auditVO.setIpAddress(systemAudit.getIpaddress());
//			auditVO.setUserName(systemAudit.getBssUser().getUsername());
			auditVO.setUserName(iblSession.getUsername());
			
			
			
			if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.DEFAULT_AUDIT_TYPE)) {
				auditVO.setAuditType("Default");
			} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.CREATE_AUDIT_TYPE)) {
				auditVO.setAuditType("Create");
			} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.UPDATE_AUDIT_TYPE)) {
				auditVO.setAuditType("Update");
			} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.DELETE_AUDIT_TYPE)) {
				auditVO.setAuditType("Delete");
			} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.LOGIN_AUDIT_TYPE)) {
				auditVO.setAuditType("Login");
			} 
			auditVO.setAuditAction(systemAudit.getSystemAction().getName());
			
			auditVO.setAuditDate(systemAudit.getAuditdate());
			auditVOList.add(auditVO);
		}
		
		return auditVOList;
	}

	public static ViewAuditVO prepareViewAuditDataBy(SystemAudit systemAudit,IBLSession blSession) {
		ViewAuditVO auditVO = new ViewAuditVO();
		auditVO.setReason(systemAudit.getReason());
		auditVO.setSystemAuditId(systemAudit.getSystemauditid());
		auditVO.setIpAddress(systemAudit.getIpaddress());
//		auditVO.setUserName(systemAudit.getBssUser().getUsername());
		auditVO.setUserName(blSession.getUsername());
		
		
		/*if(systemAudit.getUser().getLastName()==null) {
			auditVO.setUserName(systemAudit.getUser().getFirstName());
		} else {
			auditVO.setUserName(systemAudit.getUser().getFirstName()+" "+systemAudit.getUser().getLastName());
		}*/
		if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.DEFAULT_AUDIT_TYPE)) {
			auditVO.setAuditType("Default");
		} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.CREATE_AUDIT_TYPE)) {
			auditVO.setAuditType("Create");
		} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.UPDATE_AUDIT_TYPE)) {
			auditVO.setAuditType("Update");
		} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.DELETE_AUDIT_TYPE)) {
			auditVO.setAuditType("Delete");
		} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.LOGIN_AUDIT_TYPE)) {
			auditVO.setAuditType("Login");
		}
		auditVO.setAuditAction(systemAudit.getSystemAction().getName());
		
		auditVO.setAuditDate(systemAudit.getAuditdate());
		auditVO.setRemarks(systemAudit.getRemarks());
		
		return auditVO;
	}

	public static List<ViewAuditEntryVO> prepareViewAuditEntryData(List<AuditEntry> auditEntries, IBLSession blSession) {
		
		List<ViewAuditEntryVO> auditEntryVOs = new ArrayList<ViewAuditEntryVO>();
		ViewAuditEntryVO entryVO = null;
		for(AuditEntry auditEntry : auditEntries) {
			entryVO = new ViewAuditEntryVO();
			entryVO.setAuditEntryId(auditEntry.getAuditInfoId());
			entryVO.setTableName(auditEntry.getTableName());
			entryVO.setFieldName(auditEntry.getFieldName());
			entryVO.setOldValue(auditEntry.getOldValue());
			entryVO.setNewValue(auditEntry.getNewValue());
			auditEntryVOs.add(entryVO);
		}
		
		return auditEntryVOs;
	}

	public static List<SearchAuditVO> prepareSearchAllAuditData(List<SystemAudit> systemAudits) {
		
		List<SearchAuditVO> auditVOList = new ArrayList<SearchAuditVO>();
		SearchAuditVO auditVO = null;
		for(SystemAudit systemAudit : systemAudits) {
			auditVO = new SearchAuditVO();
			auditVO.setReason(systemAudit.getReason());
			auditVO.setSystemAuditId(systemAudit.getSystemauditid());
			auditVO.setIpAddress(systemAudit.getIpaddress());
//			auditVO.setUserName(systemAudit.getBssUser().getUsername());
//			auditVO.setUserName(blSession.getUsername());
			
			/*if(systemAudit.getUser().getLastName()==null) {
				auditVO.setUserName(systemAudit.getUser().getFirstName());
			} else {
				auditVO.setUserName(systemAudit.getUser().getFirstName()+" "+systemAudit.getUser().getLastName());
			}*/
			if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.DEFAULT_AUDIT_TYPE)) {
				auditVO.setAuditType("Default");
			} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.CREATE_AUDIT_TYPE)) {
				auditVO.setAuditType("Create");
			} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.UPDATE_AUDIT_TYPE)) {
				auditVO.setAuditType("Update");
			} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.DELETE_AUDIT_TYPE)) {
				auditVO.setAuditType("Delete");
			}  else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.LOGIN_AUDIT_TYPE)) {
				auditVO.setAuditType("Login");
			}
			auditVO.setAuditAction(systemAudit.getSystemAction().getName());
			
			auditVO.setAuditDate(systemAudit.getAuditdate());
			auditVOList.add(auditVO);
		}
		
		return auditVOList;
	}




	public AuditSummary prepareLoginAudit(IBDSessionContext context) {
		
		AuditSummary data = new AuditSummary();
		data.setUserId(context.getBLSession().getSessionUserId());
		data.setActionAlias(AuditConstants.LOGIN);
		data.setAuditTypeId(AuditConstants.LOGIN_AUDIT_TYPE);
		data.setAuditDate(getCurrentTimestamp());
		data.setReason("Logging in the system");
		data.setRemarks("No Remarks");
		data.setIpAddress(context.getBLSession().getIpAddress());
		return data;
	}
	
	

	public static AuditSummary prepareLogoutAudit(IBLSession context) {
		
		AuditSummary data = new AuditSummary();
		data.setUserId(context.getSessionUserId());
		data.setActionAlias(AuditConstants.LOGOUT);
		data.setAuditTypeId(AuditConstants.LOGIN_AUDIT_TYPE);
		data.setAuditDate(getCurrentTimestamp());
		data.setReason("Logging out the system");
		data.setRemarks("No Remarks");
		data.setIpAddress(context.getIpAddress());
		return data;
	}
	
	

	


	public static List<SearchAuditVO> prepareSerachAuditData(
			List<SystemAudit> systemAudits) {
		List<SearchAuditVO> auditVOList = new ArrayList<SearchAuditVO>();
		SearchAuditVO auditVO = null;
		for(SystemAudit systemAudit : systemAudits) {
			auditVO = new SearchAuditVO();
			auditVO.setReason(systemAudit.getReason());
			auditVO.setSystemAuditId(systemAudit.getSystemauditid());
			auditVO.setIpAddress(systemAudit.getIpaddress());
			/*if(systemAudit.getUser().getLastName()==null) {
				auditVO.setUserName(systemAudit.getUser().getFirstName());
			} else {
				auditVO.setUserName(systemAudit.getUser().getFirstName()+" "+systemAudit.getUser().getLastName());
			}*/
//			auditVO.setUserName(systemAudit.getBssUser().getUsername());
//			auditVO.setUserName(blSession.getUsername());
			
			
			if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.DEFAULT_AUDIT_TYPE)) {
				auditVO.setAuditType("Default");
			} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.CREATE_AUDIT_TYPE)) {
				auditVO.setAuditType("Create");
			} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.UPDATE_AUDIT_TYPE)) {
				auditVO.setAuditType("Update");
			} else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.DELETE_AUDIT_TYPE)) {
				auditVO.setAuditType("Delete");
			}  else if(systemAudit.getSystemAuditTypeId().equals(AuditConstants.LOGIN_AUDIT_TYPE)) {
				auditVO.setAuditType("Login");
			}
			auditVO.setAuditAction(systemAudit.getSystemAction().getName());
			
			auditVO.setAuditDate(systemAudit.getAuditdate());
			auditVOList.add(auditVO);
		}
		
		return auditVOList;
	}
	
	
	public static List<SearchAuditVO> prepareSerachAuditDataView(
			List<ViewAuditData> systemAudits) {
		List<SearchAuditVO> auditVOList = new ArrayList<SearchAuditVO>();
		SearchAuditVO auditVO = null;
		for(ViewAuditData systemAudit : systemAudits) {
			auditVO = new SearchAuditVO();
			auditVO.setReason(systemAudit.getReason());
			auditVO.setSystemAuditId(systemAudit.getSystemauditid());
			auditVO.setIpAddress(systemAudit.getIpaddress());
			/*if(systemAudit.getUser().getLastName()==null) {
				auditVO.setUserName(systemAudit.getUser().getFirstName());
			} else {
				auditVO.setUserName(systemAudit.getUser().getFirstName()+" "+systemAudit.getUser().getLastName());
			}*/
//			auditVO.setUserName(systemAudit.getUserName());
		
			///Code added for MTCBSS 240
			try{
				auditVO.setUserName(UserFactory.findUserById(systemAudit.getSystemAudit().getUserId()).getName());
				} catch (SearchBLException e) {
					e.printStackTrace();
				} catch (NoSuchControllerException e) {
					e.printStackTrace();
				}
			auditVO.setAuditType(systemAudit.getAuditType());
			auditVO.setAuditAction(systemAudit.getSystemAction().getName());
			
			auditVO.setAuditDate(systemAudit.getAuditdate());
			auditVOList.add(auditVO);
		}
		
		return auditVOList;
	}

	public static AuditSummary prepareGeneralAuditSummaryData(IBLSession iblSession,String actionAlias,String auditTypeId,String reason,String remark){
		AuditSummary data = new AuditSummary();
		
		data.setUserId(iblSession.getSessionUserId());
		data.setActionAlias(actionAlias);
		data.setAuditTypeId(auditTypeId);
		data.setAuditDate(getCurrentTimestamp());
		data.setReason(reason);
		data.setRemarks(remark);
		data.setIpAddress(iblSession.getIpAddress());
		
		return data;
		
	}
	
	public static AuditSummaryDetail prepareWarehouseUpdateAudit(WarehouseData warehouseData,WarehouseVO warehouseVO) {
		
		AuditSummaryDetail data = new AuditSummaryDetail();
		data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_NAME,warehouseData.getName(),warehouseVO.getName());
		data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_LOCATION,warehouseData.getLocation(),warehouseVO.getLocation());
		data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_DESC,warehouseData.getDescription(),warehouseVO.getDescription());
		data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_REASON,warehouseData.getReason(),warehouseVO.getReason());
		data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_OWNER,warehouseData.getOwner(),warehouseVO.getOwner());
		data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_CONTACTNO,warehouseData.getContactNo(),warehouseVO.getContactNo());
		data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_EMAILID,warehouseData.getEmailId(),warehouseVO.getEmailId());
		
		
		if(warehouseData.getParentWarehouse()!= null){
			data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_PARENTWAREHOUSE,warehouseData.getParentWarehouse().getName(),warehouseVO.getParentWarehouseName());
		}else{
			data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_PARENTWAREHOUSE,null,warehouseVO.getParentWarehouseName());
		}
		
		if(warehouseVO.getWarehouseType()!=null) {
			if(warehouseData.getWarehouseTypeData() != null){
				data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_WAREHOUSETYPE,warehouseData.getWarehouseTypeData().getName(),warehouseVO.getWarehouseType().getName());
			}else{
				data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_WAREHOUSETYPE,null,warehouseVO.getWarehouseType().getName());
			}
		}
		
		return data;
	}
	
public static AuditSummaryDetail prepareWarehouseTypeUpdateAudit(WarehouseTypeData warehouseTypeData,WarehouseTypeVO warehouseTypeVO) {
		
		AuditSummaryDetail data = new AuditSummaryDetail();
		data.appendChanged(AuditConstants.TBLSWAREHOUSETYPE, AuditConstants.TBLSWAREHOUSETYPE_NAME,warehouseTypeData.getName(),warehouseTypeVO.getName());
		data.appendChanged(AuditConstants.TBLSWAREHOUSETYPE, AuditConstants.TBLSWAREHOUSETYPE_DESC,warehouseTypeData.getDescription(),warehouseTypeVO.getDescription());
		data.appendChanged(AuditConstants.TBLSWAREHOUSETYPE, AuditConstants.TBLSWAREHOUSETYPE_REASON,warehouseTypeData.getReason(),warehouseTypeVO.getReason());
		
		return data;
	}
//Added Start 
public static AuditSummaryDetail prepareWarehouseCodeUpdateAudit(WarehouseData warehouseData,String wareHouseCode) {
	
	AuditSummaryDetail data = new AuditSummaryDetail();
	data.appendChanged(AuditConstants.TBLMWAREHOUSE, AuditConstants.TBLMWAREHOUSE_WAREHOUSECODE,warehouseData.getWarehouseCode(),wareHouseCode);
	return data;
}
//Added End
public static AuditSummaryDetail prepareResourceTypeUpdateAudit(ResourceTypeData warehouseTypeData,UpdateResourceTypeVO warehouseTypeVO) {
	
	AuditSummaryDetail data = new AuditSummaryDetail();
	data.appendChanged(AuditConstants.TBLSRESOURCETYPE, AuditConstants.TBLSRESOURCETYPE_NAME,warehouseTypeData.getName(),warehouseTypeVO.getResourceTypeName());
	data.appendChanged(AuditConstants.TBLSRESOURCETYPE, AuditConstants.TBLSRESOURCETYPE_DESC,warehouseTypeData.getDescription(),warehouseTypeVO.getDescription());
	
	
	return data;
}

public static AuditSummaryDetail prepareSystemParameterUpdateAudit(AuditSummaryDetail data,SystemParameter entity,
		SystemParameterWrapperData wrapperData,
		IBLSession iblSession) {
	
	
	data.appendChanged(AuditConstants.TBLMSYSTEMPARAMETER, AuditConstants.TBLMSYSTEMPARAMETER_VALUE, entity.getName()+" -> "+entity.getValue(),wrapperData.getValue());
//	data.appendChanged(AuditConstants.TBLMSYSTEMPARAMETER, AuditConstants.TBLMSYSTEMPARAMETER_LASTMODIDATE, entity.getLastmodidate(),getCurrentTimestamp());
	
	return data;
}


public static AuditSummaryDetail prepareResourceSubTypeUpdateAudit(ResourceSubTypeData warehouseTypeData,UpdateResourceSubTypeVO warehouseTypeVO) {
	
	AuditSummaryDetail data = new AuditSummaryDetail();
	data.appendChanged(AuditConstants.TBLSRESOURCESUBTYPE, AuditConstants.TBLSRESOURCESUBTYPE_NAME,warehouseTypeData.getName(),warehouseTypeVO.getResourceSubTypeName());
	data.appendChanged(AuditConstants.TBLSRESOURCESUBTYPE, AuditConstants.TBLSRESOURCESUBTYPE_TYPE,warehouseTypeData.getResourceType().getResourceTypeId(),warehouseTypeVO.getResourceTypeId());
	data.appendChanged(AuditConstants.TBLSRESOURCESUBTYPE, AuditConstants.TBLSRESOURCESUBTYPE_DESC,warehouseTypeData.getDescription(),warehouseTypeVO.getDescription());
	
	
	return data;
}


public static AuditSummaryDetail prepareResourceUpdateAudit(ItemData itemData,ItemVO itemVO,Map<String,String> map) {
		
		AuditSummaryDetail data = new AuditSummaryDetail();
		data.appendChanged(AuditConstants.TBLMRESOURCE, AuditConstants.TBLMRESOURCE_NAME,itemData.getName(),itemVO.getName());
		data.appendChanged(AuditConstants.TBLMRESOURCE, AuditConstants.TBLMRESOURCE_MODEL,itemData.getModelnumber(),itemVO.getModelnumber());
		data.appendChanged(AuditConstants.TBLMRESOURCE, AuditConstants.TBLMRESOURCE_DESC,itemData.getDescription(),itemVO.getDescription());
		data.appendChanged(AuditConstants.TBLMRESOURCE, AuditConstants.TBLMRESOURCE_REASON,itemData.getReason(),itemVO.getReason());
		data.appendChanged(AuditConstants.TBLMRESOURCE, AuditConstants.TBLMRESOURCE_VENDOR,itemData.getVendor(),itemVO.getVendor());
		data.appendChanged(AuditConstants.TBLMRESOURCE, AuditConstants.TBLMRESOURCE_REFID,itemData.getReferenceID(),itemVO.getReferenceID());
		data.appendChanged(AuditConstants.TBLMRESOURCE, AuditConstants.TBLMRESOURCE_RESOURCETYPEID,itemData.getResourceType().getName(),map.get(AuditConstants.TBLMRESOURCE_NEW_REFID));
		return data;
	}
	


	public static AuditSummaryDetail prepareDocumentTemplateUpdateBasicDetailsAudit(DocumentTemplate itemData,DocumentTemplateWrapperdata itemVO) {
	
	AuditSummaryDetail data = new AuditSummaryDetail();
	data.appendChanged(AuditConstants.TBLMDOCUMENTTEMPLATE, AuditConstants.TBLMDOCUMENTTEMPLATE_NAME,itemData.getName(),itemVO.getName());
	data.appendChanged(AuditConstants.TBLMDOCUMENTTEMPLATE, AuditConstants.TBLMDOCUMENTTEMPLATE_DESCRIPTION,itemData.getDescription(),itemVO.getDescription());
	data.appendChanged(AuditConstants.TBLMDOCUMENTTEMPLATE, AuditConstants.TBLMDOCUMENTTEMPLATE_VALIDFROMDATE,itemData.getValidFormDate(),itemVO.getValidFormDate());
	data.appendChanged(AuditConstants.TBLMDOCUMENTTEMPLATE, AuditConstants.TBLMDOCUMENTTEMPLATE_VALIDTODATE,itemData.getValidToDate(),itemVO.getValidToDate());
	return data;
}



public static AuditSummaryDetail prepareAttributeUpdateAudit(AttributeData attributeData,AttributeVO attributeVO) {
	
	AuditSummaryDetail data = new AuditSummaryDetail();
	data.appendChanged(AuditConstants.TBLMATTRIBUTE, AuditConstants.TBLMATTRIBUTE_NAME,attributeData.getName(),attributeVO.getName());
	data.appendChanged(AuditConstants.TBLMATTRIBUTE, AuditConstants.TBLMATTRIBUTE_DATATYPE,attributeData.getDataType(),attributeVO.getDataType());
	data.appendChanged(AuditConstants.TBLMATTRIBUTE, AuditConstants.TBLMATTRIBUTE_MANDATORY,String.valueOf(attributeData.getMandatory()),String.valueOf(attributeVO.getMandatory()));
	data.appendChanged(AuditConstants.TBLMATTRIBUTE, AuditConstants.TBLMATTRIBUTE_UNIQUE,String.valueOf(attributeData.getUnique()),String.valueOf(attributeVO.getUnique()));
	data.appendChanged(AuditConstants.TBLMATTRIBUTE, AuditConstants.TBLMATTRIBUTE__REASON,attributeData.getReason(),attributeVO.getReason());
	data.appendChanged(AuditConstants.TBLMATTRIBUTE, AuditConstants.TBLMATTRIBUTE_NEW_USEDBY,attributeData.getUsedBy(),attributeVO.getUsedBy());
	
	return data;
}

public static AuditSummaryDetail prepareAgentScheduleUpdateAudit(SystemAgentSchedule agentScheduleData,UpdateAgentScheduleVO scheduleVO,IBLSession session) {
	
	AuditSummaryDetail data = new AuditSummaryDetail();

	
	data.appendChanged(AuditConstants.TBLMAGENTSCHEDULE, AuditConstants.TBLMAGENTSCHEDULE_SCHEDULEPATTERN, agentScheduleData.getSchedulePattern(),scheduleVO.getCronExpression());
	data.appendChanged(AuditConstants.TBLMAGENTSCHEDULE, AuditConstants.TBLMAGENTSCHEDULE_REASONFORSCHEDULE, agentScheduleData.getReasonforschedule(),scheduleVO.getReason());
	data.appendChanged(AuditConstants.TBLMAGENTSCHEDULE, AuditConstants.TBLMAGENTSCHEDULE_UPDATEDBY,agentScheduleData.getLastmodifiedby(),session.getSessionUserId());
	data.appendChanged(AuditConstants.TBLMAGENTSCHEDULE, AuditConstants.TBLMAGENTSCHEDULE_UPDATEDATE,agentScheduleData.getLastmodifieddate(),getCurrentTimestamp());
	
	return data;
}


public static AuditSummaryDetail prepareChangeStatusUpdateAudit(int newStatus,InventoryData typeData,IBLSession session) {
	
	AuditSummaryDetail data = new AuditSummaryDetail();

	
	data.appendChanged(AuditConstants.TBLMINVENTORY, AuditConstants.TBLMINVENTORY_STATUSID,typeData.getInventoryStatusId(),newStatus);
	data.appendChanged(AuditConstants.TBLMINVENTORY, AuditConstants.TBLMINVENTORY_UPDATEDBY,typeData.getUpdatedby(),session.getUsername());
	data.appendChanged(AuditConstants.TBLMINVENTORY, AuditConstants.TBLMINVENTORY_UPDATEDATE,typeData.getUpdatedate(),getCurrentTimestamp());
	
	return data;
}

public static AuditSummaryDetail prepareChangeSubStatusUpdateAudit(ChangeInventorySubStatusVO statusVO,InventoryData typeData,IBLSession session) {
	
	AuditSummaryDetail data = new AuditSummaryDetail();
/*
	if(statusVO.getSubStatus() != null && statusVO.getSubStatus().equals(InventoryStatusConstants.ACCEPTED_SUBSTATUS)){
		data.appendChanged(AuditConstants.TBLMINVENTORY, AuditConstants.TBLMINVENTORY_ACCEPTED, null,statusVO.getSubStatus());
	}else if(statusVO.getSubStatus() != null && statusVO.getSubStatus().equals(InventoryStatusConstants.NEW_SUBSTATUS)){
		data.appendChanged(AuditConstants.TBLMINVENTORY, AuditConstants.TBLMINVENTORY_NEW, null,statusVO.getSubStatus());
	}else if(statusVO.getSubStatus() != null && statusVO.getSubStatus().equals(InventoryStatusConstants.REFURBISHED_SUBSTATUS)){
		data.appendChanged(AuditConstants.TBLMINVENTORY, AuditConstants.TBLMINVENTORY_REFURNISHED, null,statusVO.getSubStatus());
	}else if(statusVO.getSubStatus() != null && statusVO.getSubStatus().equals(InventoryStatusConstants.STANDBY_SUBSTATUS)){
		data.appendChanged(AuditConstants.TBLMINVENTORY, AuditConstants.TBLMINVENTORY_STANDBY, null,statusVO.getSubStatus());
	}
*/	
	data.appendChanged(AuditConstants.TBLMINVENTORY, AuditConstants.TBLMINVENTORY_SUBSTATUSID, typeData.getInventorySubStatusId(),statusVO.getSubstatusId());
	data.appendChanged(AuditConstants.TBLMINVENTORY, AuditConstants.TBLMINVENTORY_UPDATEDBY,typeData.getUpdatedby(),session.getSessionUserId());
	data.appendChanged(AuditConstants.TBLMINVENTORY, AuditConstants.TBLMINVENTORY_UPDATEDATE,typeData.getUpdatedate(),getCurrentTimestamp());
	
	return data;
}


public static List<SearchWsAuditVO> prepareSerachWsAuditDataView(
		List<WebServiceAuditEntry> systemAudits) {
	
	List<SearchWsAuditVO> wsAuditVOs = new ArrayList<SearchWsAuditVO>();
	if(systemAudits!=null && !systemAudits.isEmpty()) {
		for(WebServiceAuditEntry entry : systemAudits) {
			
			SearchWsAuditVO auditVO = new SearchWsAuditVO();
			auditVO.setEventName(entry.getEventName());
			auditVO.setResponseMessage(entry.getResponseMessage());
			auditVO.setStatus(entry.getEventProcessStatus());
			auditVO.setWsAuditId(entry.getWsAuditId());
			auditVO.setProcessedDate(entry.getProcessDate());
			auditVO.setResponseCode(entry.getResponseCode());
			wsAuditVOs.add(auditVO);
			
			
		}
	}
	
	
	return wsAuditVOs;
}

public static ViewWsAuditEntryVO prepareViewWsAuditDataBy(
		WebServiceAuditEntry entry) {
	ViewWsAuditEntryVO auditVO = new ViewWsAuditEntryVO();
	
	if(entry!=null) {
		auditVO.setEntityTypeName(entry.getEventName());
		auditVO.setResponseMessage(entry.getResponseMessage());
		auditVO.setStatus(entry.getEventProcessStatus());
		auditVO.setWsAuditId(entry.getWsAuditId());
		auditVO.setProcessedDate(entry.getProcessDate());
		auditVO.setInputData(entry.getInputParam());
		auditVO.setOutputData(entry.getOutputParam());
		auditVO.setResponseCode(entry.getResponseCode());
		
	}
	
	return auditVO;
}
/*public static AuditSummaryDetail prepareThresholdDeleteAudit(ConfigureThresholdData configureThresholdData) {
	
	AuditSummaryDetail data = new AuditSummaryDetail();
	data.appendChanged(AuditConstants.TBLMWAREHOUSEALERT, AuditConstants.THRESHOLDVALUE,configureThresholdData.getThresholdValue(),configureThresholdData.getThresholdValue());
	return data;
}
*/
//public static AuditSummaryDetail prepareThresholdUpdateAudit(ConfigureThresholdData configureThresholdData,ConfigureThresholdData configureThresholdVO) {
public static AuditSummaryDetail prepareThresholdUpdateAudit(Long ThresholdValue,ConfigureThresholdData configureThresholdVO) {
	
	AuditSummaryDetail data = new AuditSummaryDetail();
	data.appendChanged(AuditConstants.TBLMWAREHOUSEALERT, AuditConstants.THRESHOLDVALUE,ThresholdValue,configureThresholdVO.getThresholdValue());
	return data;
}
	public static AuditSummaryDetail preparePlaceOrderCancelAudit(OrderData orderData,PlaceOrderVO placeOrderVO){
	AuditSummaryDetail data = new AuditSummaryDetail();
	
	//data.appendChanged(AuditConstants.TBLMORDER, AuditConstants.TBLMORDER_NO,orderData.getOrderNo(),placeOrderVO.getOrderNo());
	data.appendChanged(AuditConstants.TBLMORDER, AuditConstants.TBLMORDER_STATUS,orderData.getOrderStatus().getName(),placeOrderVO.getStatus());
	data.appendChanged(AuditConstants.TBLMORDER, AuditConstants.TBLMORDER_REMARK,orderData.getRemarks(),placeOrderVO.getRemark());
	return data;
	}
	
	public static AuditSummaryDetail preparePlaceOrderacceptRejectAudit(OrderData orderData,PlaceOrderVO placeOrderVO){
		AuditSummaryDetail data = new AuditSummaryDetail();
		
		//data.appendChanged(AuditConstants.TBLMORDER, AuditConstants.TBLMORDER_NO,orderData.getOrderNo(),placeOrderVO.getOrderNo());
		data.appendChanged(AuditConstants.TBLMORDER, AuditConstants.TBLMORDER_STATUS,orderData.getOrderStatus().getName(),placeOrderVO.getStatus());
		data.appendChanged(AuditConstants.TBLMORDER, AuditConstants.TBLMORDER_ACCEPT_QUANTITY,orderData.getAcceptQuantity(),placeOrderVO.getAcceptquantity());
		data.appendChanged(AuditConstants.TBLMORDER, AuditConstants.TBLMORDER_REMARK,orderData.getRemarks(),placeOrderVO.getRemark());
		return data;
		}
	public static AuditSummaryDetail prepareTransferPlaceOrderAudit(OrderData orderData,PlaceOrderVO placeOrderVO){
		AuditSummaryDetail data = new AuditSummaryDetail();
		
		//data.appendChanged(AuditConstants.TBLMORDER, AuditConstants.TBLMORDER_NO,orderData.getOrderNo(),placeOrderVO.getOrderNo());
		data.appendChanged(AuditConstants.TBLMORDER, AuditConstants.TBLMORDER_TRANSFER_ORDER_NO,orderData.getTransferOrderNo(),placeOrderVO.getTransferOrderNo());
		data.appendChanged(AuditConstants.TBLMORDER, AuditConstants.TBLMORDER_STATUS,orderData.getOrderStatus().getName(),placeOrderVO.getStatus());
		data.appendChanged(AuditConstants.TBLMORDER, AuditConstants.TBLMORDER_TRANSFER_REMARK,orderData.getTransferRemarks(),placeOrderVO.getTransferRemark());
		
		return data;
		}
	
}
