package com.elitecore.cpe.bl.session.system.audit;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.elitecore.cpe.bl.entity.inventory.system.audit.AuditEntry;
import com.elitecore.cpe.bl.entity.inventory.system.audit.SystemAudit;
import com.elitecore.cpe.bl.entity.inventory.system.audit.ViewAuditData;
import com.elitecore.cpe.bl.entity.inventory.system.audit.WebServiceAuditEntry;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditPaggingVO;

@Local
public interface SystemAuditSessionBeanLocal extends Serializable  {

	public List<SystemAudit> searchSystemAudit(String moduleGroupId, String moduleId,
			String actionId, Timestamp auditFromDate, Timestamp auditToDate,
			int pageNo, int pageSize) throws SearchBLException;

	public  SystemAudit createSystemAudit(SystemAudit systemAudit)
			throws CreateBLException;

	public List<SystemAudit> searchSystemAuditByDate(Timestamp auditFrom, Timestamp auditTo) throws SearchBLException;

	public SystemAudit viewSystemAuditById(Long viewEntityId) throws SearchBLException;

	public List<AuditEntry> searchAuditEntryBySysId(Long viewEntityId) throws SearchBLException;

	public List<SystemAudit> searchAllAuditData() throws SearchBLException;

	public List<ViewAuditData> searchSystemAudit(SearchAuditPaggingVO searchAuditPaggingVO) throws SearchBLException;
	
	public List getFilterDataBy(String entityName,Map<String,Object> fieldValueMap);

	public void createWebServiceAudit(WebServiceAuditEntry auditEntry) throws CreateBLException;

	public List<WebServiceAuditEntry> searchWebServiceAudit(Date fromDate,Date toDate, Long eventId,String inputParam,String outputParam,String eventStatus) throws SearchBLException;

	public WebServiceAuditEntry viewWsAuditById(Long viewEntityId) throws SearchBLException;

	public void createSystemAudit(List<SystemAudit> systemAudits) throws CreateBLException;

	public int searchSystemAuditCount(SearchAuditPaggingVO searchAuditPaggingVO) throws SearchBLException;
}
