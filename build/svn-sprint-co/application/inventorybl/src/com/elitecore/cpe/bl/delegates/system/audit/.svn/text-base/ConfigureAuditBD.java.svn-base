package com.elitecore.cpe.bl.delegates.system.audit;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleGroupData;
import com.elitecore.cpe.bl.delegates.BaseBusinessDelegate;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.system.audit.ISystemAuditFacade;
import com.elitecore.cpe.bl.facade.system.audit.SystemAuditFacadeLocal;
import com.elitecore.cpe.bl.facade.system.audit.SystemAuditFacadeRemote;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditPaggingVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditWrapper;
import com.elitecore.cpe.bl.vo.system.audit.SearchWsAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewAuditEntryVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewWsAuditEntryVO;
import com.elitecore.cpe.core.IBDSessionContext;

public class ConfigureAuditBD extends BaseBusinessDelegate {

	public ConfigureAuditBD(IBDSessionContext context) {
		super(context);
		
	}
	
	private static ISystemAuditFacade facade;
	private static final String MODULE = "SYSTEMAUDIT-BD";

	
	public List<SystemModuleGroupData> getSystemModuleData() throws SearchBLException, TechnicalException{
		if(isTraceLevel())
			logTrace(MODULE, "Inside getSystemModuleData");
		try {
			return getFacade().getAuditableSystemModuleData();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}
	
	
	
	private ISystemAuditFacade getFacade() throws NamingException {
 		if (facade == null) {
 			if (isLocalMode()) {
 				facade = (ISystemAuditFacade)lookupLocal(SystemAuditFacadeLocal.class);
 			}else {
 				facade = (ISystemAuditFacade)lookup(SystemAuditFacadeRemote.class);
 			}
 		}
 		return facade;
 	}



	public void updateSystemAction(Set<SystemActionData> auditableActionsList) throws UpdateBLException, TechnicalException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside getSystemModuleData");
		try {
			getFacade().updateAudidableSystemAction(auditableActionsList);
		} catch (NamingException e) {
			e.printStackTrace();
			
		}
	}



	public List<SearchAuditVO> searchAuditByDate(Timestamp datefrom, Timestamp dateto) throws SearchBLException, TechnicalException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside getSystemAuditData");
		try {
			return getFacade().getSearchAuditByDate(datefrom, dateto,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}



	public ViewAuditVO findAuditDataById(Long viewEntityId) throws SearchBLException,TechnicalException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside getSystemAuditData");
		try {
			return getFacade().getViewAuditById(viewEntityId,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}



	public List<ViewAuditEntryVO> findAuditEntryDataById(Long viewEntityId) throws SearchBLException, TechnicalException {
		
		if(isTraceLevel())
			logTrace(MODULE, "Inside getAuditEntryData");
		try {
			return getFacade().getViewAuditEntryBySysId(viewEntityId,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}



	public List<SearchAuditVO> searchAllAuditData() throws SearchBLException, TechnicalException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside getSystemAuditData");
		try {
			return getFacade().searchAllAuditData();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}



	public SearchAuditWrapper searchAudit(SearchAuditPaggingVO searchAuditPaggingVO) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside searchAudit");
		try {
			return getFacade().searchAudit(searchAuditPaggingVO);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}



	public List<SearchWsAuditVO> searchWsAudit(Date from, Date to,
			Long eventId,String inputParam,String outputParam,String eventStatus) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside searchWsAudit");
		try {
			return getFacade().searchWsAudit(from,to,eventId,inputParam,outputParam,eventStatus);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
	}



	public ViewWsAuditEntryVO findWsAuditDataById(Long viewEntityId) throws SearchBLException {

		
		if(isTraceLevel())
			logTrace(MODULE, "Inside findWsAuditDataById");
		try {
			return getFacade().getViewWsAuditById(viewEntityId,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SearchBLException(e.getMessage());
		}
		
	}	
	
	
}
