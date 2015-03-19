package com.elitecore.cpe.bl.facade.system.audit;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.elitecore.cpe.bl.data.system.audit.AuditSummary;
import com.elitecore.cpe.bl.data.system.audit.WebServiceAuditData;
import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleGroupData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditPaggingVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditWrapper;
import com.elitecore.cpe.bl.vo.system.audit.SearchWsAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewAuditEntryVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewWsAuditEntryVO;
import com.elitecore.cpe.core.IBLSession;

public interface ISystemAuditFacade extends Serializable {

	public void doAuditEntry(AuditSummary auditSummary) throws CreateBLException;
	
	public void doAuditEntry(List<AuditSummary> summaries) throws CreateBLException;

	public List<SystemModuleGroupData> getAuditableSystemModuleData() throws SearchBLException;

	public void updateAudidableSystemAction(Set<SystemActionData> auditableActionsList) throws UpdateBLException;

	public List<SearchAuditVO> getSearchAuditByDate(Timestamp auditfrom, Timestamp auditTo, IBLSession iblSession ) throws SearchBLException;

	public ViewAuditVO getViewAuditById(Long viewEntityId, IBLSession blSession) throws SearchBLException;

	public List<ViewAuditEntryVO> getViewAuditEntryBySysId(Long viewEntityId,IBLSession blSession) throws SearchBLException;

	public List<SearchAuditVO> searchAllAuditData() throws SearchBLException;

	public SearchAuditWrapper searchAudit(SearchAuditPaggingVO searchAuditPaggingVO) throws SearchBLException;

	
	public void doWebSerivceAuditEntry(WebServiceAuditData auditData) throws CreateBLException;

	public List<SearchWsAuditVO> searchWsAudit(Date from, Date to, Long eventId,String inputParam,String outputParam,String eventStatus) throws SearchBLException;

	public ViewWsAuditEntryVO getViewWsAuditById(Long viewEntityId,IBLSession blSession) throws SearchBLException;
	
}
