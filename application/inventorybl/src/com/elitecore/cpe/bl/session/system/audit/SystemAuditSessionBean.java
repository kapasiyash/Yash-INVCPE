package com.elitecore.cpe.bl.session.system.audit;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;

import com.elitecore.cpe.bl.entity.inventory.system.audit.AuditEntry;
import com.elitecore.cpe.bl.entity.inventory.system.audit.SystemAudit;
import com.elitecore.cpe.bl.entity.inventory.system.audit.ViewAuditData;
import com.elitecore.cpe.bl.entity.inventory.system.audit.WebServiceAuditEntry;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditPaggingVO;
import com.elitecore.cpe.util.logger.Logger;

/**
 * Session Bean implementation class SystemAuditSessionBean
 */
@Stateless
public class SystemAuditSessionBean extends BaseSessionBean implements SystemAuditSessionBeanLocal {

	private String MODULE = "SystemAuditSessionBean";

	@TransactionAttribute (TransactionAttributeType.REQUIRED)
	@Override
	public SystemAudit createSystemAudit( SystemAudit systemAudit) throws CreateBLException {

		try {
			getEntityManager().persist(systemAudit);
			return systemAudit;

		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateBLException("Failed to do Auditing");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemAudit> searchSystemAudit(String moduleGroupId,String moduleId, String actionId, Timestamp auditFromDate,Timestamp auditToDate, int pageNo, int pageSize)
			throws SearchBLException {

		try { 
			Logger.logTrace(MODULE, "In searchSystemAudit moduleGroupId = "+moduleGroupId+",moduleId = "+ moduleId+",actionId = "+actionId+", auditFromDate = "+auditFromDate +", auditToDate = "+auditToDate +",pageSize "+ pageSize);
			return (List<SystemAudit>)getEntityManager().createNamedQuery("SystemAudit.searchSystemAudit")
			.setParameter("auditFromDate", auditFromDate)
			.setParameter("auditToDate", auditToDate).setFirstResult((pageNo - 1) * pageSize)
			.setMaxResults(pageSize)
			.getResultList();
		}catch(Exception e) {
			Logger.logTrace(MODULE, e);
			Logger.logError(MODULE, "Error in user search, " + e.getMessage());
			throw new SearchBLException("Could not locate SystemAudit data for " + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemAudit> searchSystemAuditByDate(Timestamp auditFrom,Timestamp auditTo) throws SearchBLException {
		
		try { 
			
			return (List<SystemAudit>)getEntityManager().createNamedQuery("SystemAudit.searchSystemAuditByDate")
			.setParameter("auditFromDate", auditFrom)
			.setParameter("auditToDate", auditTo)
			.getResultList();
		}catch(Exception e) {
			Logger.logTrace(MODULE, e);
			Logger.logError(MODULE, "Error in user search, " + e.getMessage());
			throw new SearchBLException("Could not locate SystemAudit data for " + e.getMessage(), e);
		}
	}

	@Override
	public SystemAudit viewSystemAuditById(Long viewEntityId)throws SearchBLException {
		try { 
			
			SystemAudit systemAudit=(SystemAudit)getEntityManager().createNamedQuery("SystemAudit.searchSystemAuditById").setParameter("systemauditid", viewEntityId).getSingleResult();
			Logger.logTrace(MODULE, systemAudit.getSystemActionId().toString());
			return systemAudit;
		}catch(Exception e) {
			Logger.logTrace(MODULE, e);
			Logger.logError(MODULE, "Error in user search, " + e.getMessage());
			throw new SearchBLException("Could not locate SystemAudit data for " + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditEntry> searchAuditEntryBySysId(Long systemAuditId) throws SearchBLException {
try { 
			
			return (List<AuditEntry>)getEntityManager().createNamedQuery("AuditEntry.searchAuditEntryBysysId")
			.setParameter("systemAuditId", systemAuditId)
			.getResultList();
		}catch(Exception e) {
			Logger.logTrace(MODULE, e);
			Logger.logError(MODULE, "Error in user search, " + e.getMessage());
			throw new SearchBLException("Could not locate AuditEntry data for " + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemAudit> searchAllAuditData() throws SearchBLException {

		try { 
			
			return (List<SystemAudit>)getEntityManager().createNamedQuery("SystemAudit.searchAllAuditData")
			.getResultList();
		}catch(Exception e) {
			Logger.logTrace(MODULE, e);
			Logger.logError(MODULE, "Error in audit search, " + e.getMessage());
			throw new SearchBLException("Could not locate SystemAudit data for " + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewAuditData> searchSystemAudit(SearchAuditPaggingVO searchAuditPaggingVO)
			throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  searchSystemAudit");
		try {
		
			boolean isdate = false;
			boolean fromdate = false;
			StringBuilder queryBuilder = new StringBuilder();
			
			queryBuilder = queryBuilder.append("select o from ViewAuditData o where o.systemauditid<>null");
			
			if(searchAuditPaggingVO.getActionAlias()!=null && !searchAuditPaggingVO.getActionAlias().isEmpty()) {
				queryBuilder = queryBuilder.append(" and o.actionAlias='"+searchAuditPaggingVO.getActionAlias()+"'");
			}
			
			if(searchAuditPaggingVO.getModuleId()!=null) {
				queryBuilder = queryBuilder.append(" and o.systemAction.systemModules.moduleId='"+searchAuditPaggingVO.getModuleId()+"'");
			}
			if(searchAuditPaggingVO.getFromDate()!=null && searchAuditPaggingVO.getToDate()!=null) {
				queryBuilder = queryBuilder.append(" and o.auditdate between :auditFromDate and :auditToDate");
				isdate = true;
			}else if(searchAuditPaggingVO.getFromDate()==null && searchAuditPaggingVO.getToDate()!=null){
				queryBuilder = queryBuilder.append(" and o.auditdate <= :auditToDate");
				fromdate = true;
			}
			
			queryBuilder.append(" ORDER BY o.auditdate DESC");
			List<ViewAuditData> audits = null;
			if(isdate) {
				audits = (List<ViewAuditData>)getEntityManager().createQuery(queryBuilder.toString())
						  .setParameter("auditFromDate", searchAuditPaggingVO.getFromDate())
						  .setParameter("auditToDate", searchAuditPaggingVO.getToDate())
						 .setMaxResults(searchAuditPaggingVO.getPageSize()).setFirstResult(searchAuditPaggingVO.getItemStartNumber()) 
						 .getResultList();
			} else  if(fromdate) {
				audits = (List<ViewAuditData>)getEntityManager().createQuery(queryBuilder.toString())
						  .setParameter("auditToDate", searchAuditPaggingVO.getToDate())
						  .setMaxResults(searchAuditPaggingVO.getPageSize()).setFirstResult(searchAuditPaggingVO.getItemStartNumber()) 
							 .getResultList();
			}
			else {
				audits = (List<ViewAuditData>)getEntityManager().createQuery(queryBuilder.toString())
						.setMaxResults(searchAuditPaggingVO.getPageSize()).setFirstResult(searchAuditPaggingVO.getItemStartNumber()) 
						 .getResultList();
			}
			
			 
		 return audits;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in searchSystemAudit Reason" +e.getMessage());
			throw new SearchBLException("Search SystemAudit operation failed, reason: " + e.getMessage(), e);
		}
	}

	@Override
	public void createWebServiceAudit(WebServiceAuditEntry auditEntry)
			throws CreateBLException {
		try {
			getEntityManager().persist(auditEntry);

		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateBLException("Failed to do Web ServiceAuditing");
		}
		
	}

	@Override
	public List<WebServiceAuditEntry> searchWebServiceAudit(Date fromDate,
			Date toDate, Long eventId,String inputParam,String outputParam,String eventStatus) throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  searchWsAudit");
		try {
		
			boolean isdate = false,isTodate=false;
			StringBuilder queryBuilder = new StringBuilder();
			
			queryBuilder = queryBuilder.append("select o from WebServiceAuditEntry o where o.wsAuditId<>null");
			
			
			if(eventId!=null) {
				queryBuilder = queryBuilder.append(" and o.entityTypeId='"+eventId+"'");
			}
			
			if(eventStatus!=null && !eventStatus.isEmpty()) {
				queryBuilder = queryBuilder.append(" and o.eventProcessStatus='"+eventStatus+"'");
			}
			
			if(inputParam!=null && !inputParam.isEmpty()) {
				queryBuilder = queryBuilder.append(" and upper(o.inputParam) like '"+formatForUpperLikeSearch(inputParam)+"'");
			}
			if(outputParam!=null && !outputParam.isEmpty()) {
				queryBuilder = queryBuilder.append(" and upper(o.outputParam) like '"+formatForUpperLikeSearch(outputParam)+"'");
			}
			if(fromDate!=null && toDate!=null) {
				queryBuilder = queryBuilder.append(" and o.processDate between :auditFromDate and :auditToDate");
				isdate = true;
			}
			if(!isdate) {
				if(toDate!=null) {
					queryBuilder = queryBuilder.append(" and o.processDate <= :auditToDate");
					isTodate = true;
				}
			}
			queryBuilder = queryBuilder.append(" order by o.processDate desc ");
			Logger.logTrace(MODULE, "Search WSAudit:"+queryBuilder.toString());
			List<WebServiceAuditEntry> audits = null;
			if(isdate) {
				audits = (List<WebServiceAuditEntry>)getEntityManager().createQuery(queryBuilder.toString())
						  .setParameter("auditFromDate", fromDate)
						  .setParameter("auditToDate", toDate)
						 .getResultList();
			} else if(isTodate) {
				audits = (List<WebServiceAuditEntry>)getEntityManager().createQuery(queryBuilder.toString())
						  .setParameter("auditToDate", toDate)
						 .getResultList();
			}else {
				audits = (List<WebServiceAuditEntry>)getEntityManager().createQuery(queryBuilder.toString())
						 .getResultList();
			}
			
			 
		 return audits;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in searchSystemAudit Reason" +e.getMessage());
			throw new SearchBLException("Search SystemAudit operation failed, reason: " + e.getMessage(), e);
		}
		
	}

	@Override
	public WebServiceAuditEntry viewWsAuditById(Long viewEntityId)
			throws SearchBLException {
		try { 
			
			WebServiceAuditEntry systemAudit=(WebServiceAuditEntry)getEntityManager().createNamedQuery("WebServiceAuditEntry.searchWsAuditById").setParameter("wsAuditId", viewEntityId).getSingleResult();
			return systemAudit;
		}catch(Exception e) {
			Logger.logTrace(MODULE, e);
			Logger.logError(MODULE, "Error in user search, " + e.getMessage());
			throw new SearchBLException("Could not locate WebServiceAuditEntry data for " + e.getMessage(), e);
		}
	}

	@Override
	public void createSystemAudit(List<SystemAudit> systemAudits)
			throws CreateBLException {
		Logger.logTrace(MODULE, "Inside Create System Audit List");

		try {
			int count = 0;
			if(systemAudits!=null && !systemAudits.isEmpty()) {
				for(SystemAudit systemAudit : systemAudits) {
					count++;
					getEntityManager().persist(systemAudit);
					
					
				}
				
				getEntityManager().flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateBLException("Failed to do Auditing");
		}
		
		
	}

	@Override
	public int searchSystemAuditCount(SearchAuditPaggingVO searchAuditPaggingVO)
			throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  searchSystemAudit");
		try {
		
			boolean isdate = false;
			boolean fromdate = false;
			StringBuilder queryBuilder = new StringBuilder();
			
			queryBuilder = queryBuilder.append("select count(o) from ViewAuditData o where o.systemauditid<>null");
			
			 
			if(searchAuditPaggingVO.getActionAlias()!=null && !searchAuditPaggingVO.getActionAlias().isEmpty()) {
				queryBuilder = queryBuilder.append(" and o.actionAlias='"+searchAuditPaggingVO.getActionAlias()+"'");
			}
			
			if(searchAuditPaggingVO.getModuleId()!=null) {
				queryBuilder = queryBuilder.append(" and o.systemAction.systemModules.moduleId='"+searchAuditPaggingVO.getModuleId()+"'");
			}
			if(searchAuditPaggingVO.getFromDate()!=null && searchAuditPaggingVO.getToDate()!=null) {
				queryBuilder = queryBuilder.append(" and o.auditdate between :auditFromDate and :auditToDate");
				isdate = true;
			}else if(searchAuditPaggingVO.getFromDate()==null && searchAuditPaggingVO.getToDate()!=null){
				queryBuilder = queryBuilder.append(" and o.auditdate <= :auditToDate");
				fromdate = true;
			}
			
			queryBuilder.append(" ORDER BY o.auditdate DESC");
			if(isdate) {
				return ((Long)getEntityManager().createQuery(queryBuilder.toString())						  .setParameter("auditFromDate", searchAuditPaggingVO.getFromDate())
						  .setParameter("auditToDate", searchAuditPaggingVO.getToDate())
						 .getSingleResult()).intValue();
			} else  if(fromdate) {
				return ((Long)getEntityManager().createQuery(queryBuilder.toString())
						  .setParameter("auditToDate", searchAuditPaggingVO.getToDate())
						  .getSingleResult()).intValue();
			}
			else {
				return ((Long)getEntityManager().createQuery(queryBuilder.toString())
						 .getSingleResult()).intValue();
			}
			
			 
		}catch(NoResultException e) {
			return 0;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in searchSystemAudit Reason" +e.getMessage());
			throw new SearchBLException("Search SystemAudit operation failed, reason: " + e.getMessage(), e);
		}
		
	}

}
