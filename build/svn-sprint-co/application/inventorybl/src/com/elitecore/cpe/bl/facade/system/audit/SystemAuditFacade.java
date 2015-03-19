package com.elitecore.cpe.bl.facade.system.audit;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.elitecore.cpe.bl.constants.master.EntityConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.data.system.audit.AuditEntryData;
import com.elitecore.cpe.bl.data.system.audit.AuditSummary;
import com.elitecore.cpe.bl.data.system.audit.WebServiceAuditData;
import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleGroupData;
import com.elitecore.cpe.bl.entity.inventory.system.audit.AuditEntry;
import com.elitecore.cpe.bl.entity.inventory.system.audit.DataTagsData;
import com.elitecore.cpe.bl.entity.inventory.system.audit.MessageTemplateData;
import com.elitecore.cpe.bl.entity.inventory.system.audit.SystemAudit;
import com.elitecore.cpe.bl.entity.inventory.system.audit.ViewAuditData;
import com.elitecore.cpe.bl.entity.inventory.system.audit.WebServiceAuditEntry;
import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemAction;
import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemModule;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.system.systemparameter.SystemParameterFacadeLocal;
import com.elitecore.cpe.bl.session.system.audit.SystemAuditSessionBeanLocal;
import com.elitecore.cpe.bl.session.system.internal.SystemInternalSessionBeanLocal;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditPaggingVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditWrapper;
import com.elitecore.cpe.bl.vo.system.audit.SearchWsAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewAuditEntryVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewWsAuditEntryVO;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;

/**
 * Session Bean implementation class SystemAuditFacade
 */
/**
 * 
 * @author Yash.Kapasi
 *
 */
@Stateless
public class SystemAuditFacade extends BaseFacade implements SystemAuditFacadeRemote, SystemAuditFacadeLocal {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB private SystemAuditSessionBeanLocal systemAuditSessionBeanLocal;
	@EJB private SystemInternalSessionBeanLocal systemInternalSessionBeanLocal;
	
	@EJB private SystemParameterFacadeLocal systemParameterFacadeLocal; 
//	@EJB private UserSessionBeanLocal userSessionBeanLocal; 
	
	private String MODULE = "SystemAuditFacade";
	
	
	@Override
	@TransactionAttribute (TransactionAttributeType.REQUIRED)
	public void doAuditEntry(AuditSummary auditSummary) throws CreateBLException {
		try{
			
			
			if(auditSummary!=null){
				SystemAction systemAction = systemInternalSessionBeanLocal.findSystemActionByAlias(auditSummary.getActionAlias());
				
				if(systemAction!=null){
				if(!(("Y").charAt(0)==(systemAction.getEnableaudit()))){
					Logger.logDebug(MODULE, "doAuditEntry ActionAlias = "+auditSummary.getActionAlias()+" is not Auditable, So Discarding Audit");	
				}else{
					SystemAudit systemAudit = new SystemAudit();
					
					systemAudit.setUserId(auditSummary.getUserId());
					systemAudit.setSystemActionId(systemAction.getActionId());
					systemAudit.setSystemAuditTypeId(auditSummary.getAuditTypeId());
					systemAudit.setAuditdate(auditSummary.getAuditDate());
					systemAudit.setIpaddress(auditSummary.getIpAddress());
					systemAudit.setReason(auditSummary.getReason());
					String strDynamicMessage = getDynamicMessage(systemAction,auditSummary.getRemarks(),auditSummary.getTagMap());
					
					
					Logger.logDebug(MODULE, "final dynamic msg :"+strDynamicMessage);
					if(strDynamicMessage!=null && !strDynamicMessage.equals("")){
						systemAudit.setRemarks(strDynamicMessage);
					}else{
						systemAudit.setRemarks(auditSummary.getRemarks());
					}
					
					
					if(auditSummary.getAuditEntryDatas()!=null && !auditSummary.getAuditEntryDatas().isEmpty()){
						Set<AuditEntry> auditEntries = new HashSet<AuditEntry>();
						
						for(AuditEntryData auditEntryData : auditSummary.getAuditEntryDatas()){
							AuditEntry auditEntry = new AuditEntry();
							
							auditEntry.setFieldName(auditEntryData.getFieldName());
							auditEntry.setNewValue(auditEntryData.getNewValue());
							auditEntry.setOldValue(auditEntryData.getOldValue());
							auditEntry.setTableName(auditEntryData.getTableName());
							auditEntry.setSystemAudit(systemAudit);
							
							auditEntries.add(auditEntry);
						}
						
						systemAudit.setAuditEntries(auditEntries);
					}
					systemAuditSessionBeanLocal.createSystemAudit(systemAudit);
					
				}
				}else{
					Logger.logDebug(MODULE, "auditSummary is null");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new CreateBLException(e.getMessage());
		}
	}
	/**
	 * This method will find all auditable system module data 
	 * @return List<SystemModuleGroupData>
	 * @throws SearchBLException 
	 * @author Yash.Kapasi
	 */
	@Override
	public List<SystemModuleGroupData> getAuditableSystemModuleData() throws SearchBLException {
		// TODO Auto-generated method stub
		List<SystemModuleGroupData> systemModuleGroupDatas = new ArrayList<SystemModuleGroupData>();		
		List<SystemModule> systemModuleGroups = systemInternalSessionBeanLocal.findAllSystemModules();		
		if(systemModuleGroups!=null && !systemModuleGroups.isEmpty()) {
			systemModuleGroupDatas.add(getSystemModuleGroupData(systemModuleGroups));
		}
		Collections.sort(systemModuleGroupDatas);
		return systemModuleGroupDatas;
	}
	
	
	private SystemModuleGroupData getSystemModuleGroupData(List<SystemModule> systemModules){
		SystemModuleGroupData moduleGroupData = new SystemModuleGroupData();
		
			for(SystemModule systemModule : systemModules){			
				moduleGroupData.addSystemModule(getSystemModuleData(systemModule));
			}
			Collections.sort(moduleGroupData.getSystemModules());

			return moduleGroupData;
	}
	
	
	/*private SystemModuleGroupData getSystemModuleGroupData(SystemModuleGroup systemModuleGroup){
		SystemModuleGroupData moduleGroupData = new SystemModuleGroupData();
		moduleGroupData.setAlias(systemModuleGroup.getAlias());
		moduleGroupData.setDescription(systemModuleGroup.getDescription());
		moduleGroupData.setModuleGroupId(systemModuleGroup.getModuleGroupId());
		moduleGroupData.setName(systemModuleGroup.getName());
		moduleGroupData.setSequenceNumber(systemModuleGroup.getSequenceNumber());
		moduleGroupData.setActiveIconUrl(systemModuleGroup.getActiveIconUrl());
		moduleGroupData.setHomeURL(systemModuleGroup.getHomeURL());
		moduleGroupData.setIconUrl(systemModuleGroup.getIconUrl());
		
		if(systemModuleGroup.getSystemModules()!=null && !systemModuleGroup.getSystemModules().isEmpty()){
			for(SystemModule systemModule : systemModuleGroup.getSystemModules()){			
				moduleGroupData.addSystemModule(getSystemModuleData(systemModule));
			}
			Collections.sort(moduleGroupData.getSystemModules());
		}
		return moduleGroupData;
	}*/
	
	private SystemModuleData getSystemModuleData(SystemModule systemModule){
		SystemModuleData moduleData = new SystemModuleData();
		moduleData.setAlias(systemModule.getAlias());
		moduleData.setDescription(systemModule.getDescription());		
		moduleData.setModuleId(systemModule.getModuleId());
		moduleData.setName(systemModule.getName());
		moduleData.setSequenceNumber(systemModule.getSequenceNumber());
		if(systemModule.getSystemActions()!=null && !systemModule.getSystemActions().isEmpty()){
			for(SystemAction systemAction : systemModule.getSystemActions()){
				SystemActionData systemActionData =  getSystemActionData(systemAction);
				if(systemActionData!=null){
					moduleData.addSystemAction(systemActionData);
				}
			}
			Collections.sort(moduleData.getSystemActions());
		}
		return moduleData;
	}
	
	private SystemActionData getSystemActionData(SystemAction systemAction){
		SystemActionData systemActionData = null;
		if(systemAction.getParentAction()==null){
			systemActionData= new SystemActionData();
			systemActionData.setName(systemAction.getName());
			systemActionData.setActionAlias(systemAction.getActionAlias());
			systemActionData.setActionId(systemAction.getActionId());					
			systemActionData.setParentAction(true);			
			systemActionData.setSequencenumber(systemAction.getSequenceNumber());
			systemActionData.setZulPageUrl(systemAction.getPageUrl());
			systemActionData.setIsAuditable(systemAction.getIsAuditable());
			systemActionData.setEnableAudit(systemAction.getEnableaudit());
			if(systemAction.getChildActions()!=null && !systemAction.getChildActions().isEmpty()){
				for(SystemAction childSystemAction : systemAction.getChildActions()){
					systemActionData.addChildAction(getChildSystemActionData(childSystemAction));	
				}
				Collections.sort(systemActionData.getChildActions());
			}									
		}					
		return systemActionData;
	}
	
	private SystemActionData getChildSystemActionData(SystemAction childSystemAction){
		SystemActionData systemActionData = new SystemActionData();
		systemActionData.setName(childSystemAction.getName());
		systemActionData.setActionAlias(childSystemAction.getActionAlias());
		systemActionData.setActionId(childSystemAction.getActionId());		
		systemActionData.setSequencenumber(childSystemAction.getSequenceNumber());
		systemActionData.setZulPageUrl(childSystemAction.getPageUrl());	
		systemActionData.setIsAuditable(childSystemAction.getIsAuditable());
		systemActionData.setEnableAudit(childSystemAction.getEnableaudit());
		systemActionData.setParentAction(false);					
		if(childSystemAction.getChildActions()!=null && !childSystemAction.getChildActions().isEmpty()){
			for(SystemAction systemAction : childSystemAction.getChildActions()){
				systemActionData.addChildAction(getChildSystemActionData(systemAction));	
			}				
		}
		return systemActionData;
	}
	
	/**
	 * This method will update System Action
	 * @param Set<SystemActionData> 
	 * @throws UpdateBLException
	 * @author Yash.Kapasi
	 */
	@Override
	public void updateAudidableSystemAction(Set<SystemActionData> auditableActionsList) throws UpdateBLException {
		// TODO Auto-generated method stub
			SystemAction systemAction;
			try {
				for(SystemActionData systemActionData:auditableActionsList) {
					systemAction = systemInternalSessionBeanLocal.findSystemActionById(systemActionData.getActionId());
					systemAction.setEnableaudit(systemActionData.getEnableAudit());
					systemInternalSessionBeanLocal.updateSystemAction(systemAction);
				}
			} catch (SearchBLException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * This method will Search System Audit By date
	 * @param Timestamp 
	 * @throws SearchBLException
	 * @author Yash.Kapasi
	 */
	@Override
	public List<SearchAuditVO> getSearchAuditByDate(Timestamp auditfrom,Timestamp auditTo, IBLSession iblSession) throws SearchBLException {
		
		List<SystemAudit> systemAudits = systemAuditSessionBeanLocal.searchSystemAuditByDate(auditfrom, auditTo);
		List<SearchAuditVO> searchAuditVOs = new ArrayList<SearchAuditVO>();
		if(systemAudits != null && !systemAudits.isEmpty()) {
			searchAuditVOs = AuditDataConversionUtilities.prepareSerachAuditDataByDate(systemAudits,iblSession);
		}
		
		
		return searchAuditVOs;
	}
	@Override
	public ViewAuditVO getViewAuditById(Long viewEntityId, IBLSession blSession)throws SearchBLException {
		
		ViewAuditVO auditVO = new ViewAuditVO();
		SystemAudit systemAudit = systemAuditSessionBeanLocal.viewSystemAuditById(viewEntityId);
		auditVO = AuditDataConversionUtilities.prepareViewAuditDataBy(systemAudit,blSession);
		
		return auditVO;
	}
	@Override
	public List<ViewAuditEntryVO> getViewAuditEntryBySysId(Long viewEntityId,IBLSession blSession) throws SearchBLException {
		
		List<AuditEntry> auditEntries = systemAuditSessionBeanLocal.searchAuditEntryBySysId(viewEntityId);
		List<ViewAuditEntryVO> auditEntryVOs = new ArrayList<ViewAuditEntryVO>();
		if(auditEntries != null && !auditEntries.isEmpty()) {
			auditEntryVOs = AuditDataConversionUtilities.prepareViewAuditEntryData(auditEntries,blSession);
		}
		
		return auditEntryVOs;
	}
	@Override
	public List<SearchAuditVO> searchAllAuditData() throws SearchBLException {
		List<SystemAudit> systemAudits = systemAuditSessionBeanLocal.searchAllAuditData();
		List<SearchAuditVO> searchAuditVOs = new ArrayList<SearchAuditVO>();
		if(systemAudits != null && !systemAudits.isEmpty()) {
			searchAuditVOs = AuditDataConversionUtilities.prepareSearchAllAuditData(systemAudits);
		}
		return searchAuditVOs;
	}
	@Override
	public SearchAuditWrapper searchAudit(SearchAuditPaggingVO searchAuditPaggingVO)
			throws SearchBLException {
		
		int count = 0;
		
		SearchAuditWrapper auditWrapper = new SearchAuditWrapper();
		
		count = systemAuditSessionBeanLocal.searchSystemAuditCount(searchAuditPaggingVO);
		
		List<ViewAuditData> systemAudits = systemAuditSessionBeanLocal.searchSystemAudit(searchAuditPaggingVO);
		List<SearchAuditVO> searchAuditVOs = new ArrayList<SearchAuditVO>();
		if(systemAudits != null && !systemAudits.isEmpty()) {
			searchAuditVOs = AuditDataConversionUtilities.prepareSerachAuditDataView(systemAudits);
		}
		auditWrapper.setSearchAuditVOs(searchAuditVOs);
		auditWrapper.setTotalRecords(count);
		
		return auditWrapper;
	}
	
	public String getDynamicMessage(SystemAction sysActivityData,String strRemark,Map mapData) 	
	{
		Long strActivityId = 0L;
		String strDynamicMessage = null ;
		
		Map<String,Object> fieldValueMap = new HashMap<String, Object>();
		Collection colMessageTemplates = new ArrayList();
		try
		{
					
				if(sysActivityData!=null)
				{
					strActivityId = sysActivityData.getActionId();
					if(strActivityId!=null)
					{
						Logger.logDebug(MODULE,"actionId ::"+strActivityId);
						fieldValueMap.put("actionId",strActivityId);
						
						Collection colMessageTemplateData  = systemAuditSessionBeanLocal.getFilterDataBy(EntityConstants.MESSAGETEMPLATE_DATA, fieldValueMap);
						Logger.logDebug(MODULE,"In getDynamicMessage :: colMessageTemplateData :: " + colMessageTemplateData);
						if(colMessageTemplateData!=null && !colMessageTemplateData.isEmpty())
						{
							Iterator itrTemplateData = colMessageTemplateData.iterator();
							while(itrTemplateData.hasNext())
							{
								MessageTemplateData mesgTemplateData = (MessageTemplateData)itrTemplateData.next();
								Logger.logDebug(MODULE,"In getDynamicMessage :: colMessageTemplateData :: " + colMessageTemplateData);
								if(mesgTemplateData!=null)
								{
										
										colMessageTemplates.add(mesgTemplateData);
										
										if(colMessageTemplates!=null && !colMessageTemplates.isEmpty())
										{
											strDynamicMessage = getFormattedMessage(colMessageTemplates,mapData);
											//debugLog("In getDynamicMessage :: strDynamicMessage is : " + strDynamicMessage);
											if(strRemark!=null && !strRemark.equals(""))
//												strDynamicMessage = strDynamicMessage + " " + strRemark ;
												strDynamicMessage = strRemark ;
											else
												strDynamicMessage = strDynamicMessage ;
											//debugLog("In getDynamicMessage ::FINAL strDynamicMessage TO RETURN IS : " + strDynamicMessage);
										}
										else
											Logger.logDebug(MODULE,"In getDynamicMessage :: colMessageTemplates is null.");
										
											
									
								}
								else
									Logger.logDebug(MODULE,"In getDynamicMessage :: mesgTemplateData is null.");
							}//ends while.
						}
						else
							Logger.logDebug(MODULE,"In getDynamicMessage :: colMessageTemplateData is null .");
					}							
					else
						Logger.logDebug(MODULE,"In strActivityId :: sysActivityData is null.");
				}
				else
					Logger.logDebug(MODULE,"In getDynamicMessage :: sysActivityData is null.");
				
				
		}
		catch(Exception e)
		{
			Logger.logDebug(MODULE,"In getDynamicMessage : Exception Occured :: "+e.getMessage());
			e.printStackTrace();
		}	
		//debugLog("In getDynamicMessage :: Before Returning :: " + strDynamicMessage);
		return strDynamicMessage ;

	}
	
	public String getFormattedMessage(Collection colMessageTemplate ,Map mapData) 	
	{					
	
		String strDynamicMessage = "" ;
		String strMessage = null ;		
		String strToken = null ;
		String strFormattedString = null ;
		Collection colTokens = new ArrayList();
		try
		{
			if(colMessageTemplate!=null && !colMessageTemplate.isEmpty())
			{
				Iterator itr = colMessageTemplate.iterator();
				while(itr.hasNext())
				{
					MessageTemplateData msgTemplateData = (MessageTemplateData)itr.next();	
					strMessage = msgTemplateData.getTemplate() ;					
					String strTemplateTags = msgTemplateData.getSupportedTags();
				
					
					if(strTemplateTags!=null && !strTemplateTags.equals(""))
					{
						StringTokenizer st = new StringTokenizer(strTemplateTags,";");			
						while(st.hasMoreTokens()) 
						{
							strToken="" ;
							strToken = st.nextToken() ;
							colTokens.add(strToken); 
						}
						//debugLog("In getFormattedMessage : colTokens ::: " + colTokens);
						
						if(mapData!=null && !mapData.isEmpty())
						{
							Set setTags = mapData.keySet();
							if(colTokens!=null && !colTokens.isEmpty())
							{
								Iterator itrTokens = colTokens.iterator();
								while(itrTokens.hasNext())
								{
									String strTag = (String)itrTokens.next();
									if(strTag!=null && !strTag.equals("") && setTags.contains(strTag))
									{
										//strFormattedString = getMetaDataFormats(strTag,(mapData.get(strTag)).toString(),strHyperLinkedTag);
										strFormattedString = getMetaDataFormats(strTag,(mapData.get(strTag)).toString());
										int iEnd = strTag.indexOf("}");
										String strTempTag = strTag.substring(2,iEnd);									
										strTempTag = "\\$\\{" + strTempTag + "\\}" ;									
										strMessage = strMessage.replaceAll(strTempTag,strFormattedString);
										//debugLog("strMessage ::::: " + strMessage);
									}
								}//ends while.
							}					
							else
								Logger.logDebug(MODULE,"In getFormattedMessage : colTokens is null." );
						}		
					}
					else
					{
						Logger.logDebug(MODULE,"In getFormattedMessage : strTemplateTags is null." );
					}	
					strDynamicMessage = strDynamicMessage + " " + strMessage ;
				}//ends while
			}
			else
				Logger.logDebug(MODULE,"In getFormattedMessage : colMessageTemplate is null." );
		}
		catch(Exception e)
		{
			Logger.logDebug(MODULE,"In getFormattedMessage : Exception Occured :: " + e);
			e.printStackTrace();
		}	
		//debugLog("In getFormattedMessage :: Before Returning :: " + strDynamicMessage);
		return strDynamicMessage ;
	}

	public String getMetaDataFormats(String strTag,String strTagValue)
	{		
		
		Collection colFormatTokens = new ArrayList();
		String strFormattedString = "" ;
		String strValue = "" ;
		String strToken= "" ;
		StringBuffer strBegin = new StringBuffer("") ;
		StringBuffer strEnd = new StringBuffer("") ;		
		StringBuffer strFont = new StringBuffer("") ;
		String strFontStart = "<Font" ;	
		String strFontStartEnd = ">" ;						
		String strFontEnd = "</font>" ;	
		String strFormat = "" ;
		//String strHyperLink = "" ;		
		try
		{
			Map<String,Object> fieldValueMap =  new HashMap<String, Object>();
				if(strTag!=null && !strTag.equals(""))
				{
					fieldValueMap.put("tagName", strTag);
					
					Collection colMetadata = systemAuditSessionBeanLocal.getFilterDataBy(EntityConstants.DATATAG_DATA, fieldValueMap);
					Logger.logDebug(MODULE,"In getMetaDataFormats : colMetadata : " + colMetadata);
					if(colMetadata!=null && !colMetadata.isEmpty())
					{
						Iterator itrMetaData = colMetadata.iterator();
						while(itrMetaData.hasNext())
						{
							DataTagsData tagData = (DataTagsData)itrMetaData.next();
							
							String strParam1 = tagData.getParam1();
							if(strParam1!=null && !strParam1.equals("") && strParam1.equalsIgnoreCase(AuditTagConstant.MESSAGE_TEMPLATE_HTMLFORMATTING))
							{									
								strFormat = tagData.getParam2();
							}
							if(strFormat!=null && !strFormat.equals(""))
							{
								StringTokenizer st = new StringTokenizer(strFormat,";");			
								while(st.hasMoreTokens()) 
								{
									strToken="" ;
									strToken = st.nextToken() ;
									colFormatTokens.add(strToken); 
								}
								
								if(colFormatTokens!=null && !colFormatTokens.isEmpty())			
								{
									Iterator itr = colFormatTokens.iterator();
									while(itr.hasNext())
									{
										strValue=(String)itr.next();	
										if(strValue.equalsIgnoreCase("BOLD"))							
										{
											strBegin.append( "<B>");
											strEnd.append( "</B>" );
										}	
										else if(strValue.equalsIgnoreCase("ITALIC")) 
										{
											strBegin.append( "<I>" );
											strEnd.append( "</I>" );						
										}
										else if(strValue.equalsIgnoreCase("UNDERLINE")) 
										{
											strBegin.append(strBegin + "<U>" );
											strEnd.append( "</U>" );						
										}
										else if( (strValue.substring(0,10)).equalsIgnoreCase("DATEFORMAT")) 
										{
											Collection colDate = getTokens(strValue,"=");
											if(colDate!=null && !colDate.isEmpty())
											{
												Iterator itrDate = colDate.iterator();
												while(itrDate.hasNext())
												{
													String strDate = (String)itrDate.next();
													if(strDate!=null && !strDate.equals("") && strDate.equalsIgnoreCase("DATEFORMAT"))
													{
														SimpleDateFormat sdf = new SimpleDateFormat();	
														String strPattern = (String)itrDate.next() ; 											
														sdf.applyPattern(strPattern);
														//Date tempDate = DateUtilities.dateFromString(strTagValue,"dd/MMM/yyyy");
														//debugLog("URVI:::::::::::strTagValue :::::::::::::: " + strTagValue );
					    									//strTagValue = sdf.format(java.sql.Date.valueOf(strTagValue));
					    									//strTagValue = sdf.format(tempDate);
					    									strTagValue = sdf.format(sdf.parse(strTagValue));
													}
												}
											}							
										}
										else 
										{
											String strSubString = strValue.substring(0,4);
											if(strSubString.equalsIgnoreCase("FONT"))
											{											
												Collection colFonts = getTokens(strValue,"=");
												if(colFonts!=null && !colFonts.isEmpty())
												{
													Iterator itr1 = colFonts.iterator();
													while(itr1.hasNext())
													{
														String strText = (String)itr1.next();
														if(strText!=null && !strText.equals("") && strText.equalsIgnoreCase("FONTSIZE"))
														{
															strFont.append( " size=" + (String)itr1.next());
														}
														else if(strText!=null && !strText.equals("") && strText.equalsIgnoreCase("FONTFACE"))
														{
															strFont.append( " face=" + (String)itr1.next());	
														}
														else if(strText!=null && !strText.equals("") && strText.equalsIgnoreCase("FONTCOLOR"))
														{
															strFont.append( " color=" + (String)itr1.next());	
														}										
													}//ends while.									
												}								
											}																																																												
										}
									}//ends while.
								}
								else
								{
									Logger.logDebug(MODULE,"In getMetaDataFormats : colFormatTokens is null.");
								}
								
								//if(strHyperLink!=null && !strHyperLink.equals(""))									
								//	strFormattedString = strBegin+strFontStart+strFont+strFontStartEnd+strHyperLink+strFontEnd+strEnd ; 
								//else
									strFormattedString = strBegin.toString()+strFontStart+strFont.toString()+strFontStartEnd+strTagValue+strFontEnd+strEnd.toString() ;
							}													
							else
							{
								strFormattedString = strTagValue ;
							}									
						}//ends while.						
					}
					else
						Logger.logDebug(MODULE,"In getMetaDataFormats : colMetadata is null.");
				}
				else
					Logger.logDebug(MODULE,"In getMetaDataFormats : strTag is null.");
			
		}
		catch(Exception e)
		{
			Logger.logDebug(MODULE,"In getMetaDataFormats : Exception Occured :: " + e.getMessage());
			e.printStackTrace();
		}	
		Logger.logDebug(MODULE,"In getMetaDataFormats :: Before Returning :: " + strFormattedString);
		return strFormattedString ;						
	}
	
	private Collection getTokens(String strText,String strSeperator)
	{
		Collection colTokens = new ArrayList();
		String strToken = "" ;
		if(strText!=null && !strText.equals(""))		
		{
			StringTokenizer st = new StringTokenizer(strText,strSeperator);			
			while(st.hasMoreTokens()) 
			{
				strToken="" ;
				strToken = st.nextToken() ;
				colTokens.add(strToken); 
			}			
		}		
		return colTokens ;
	}
	
	
	@Override
	@TransactionAttribute (TransactionAttributeType.REQUIRED)
	public void doWebSerivceAuditEntry(WebServiceAuditData auditSummary)
			throws CreateBLException {
		
		try{
			if(auditSummary!=null){
				
				String value = systemParameterFacadeLocal.getSystemParameterValue(auditSummary.getActionAlias());
				if(value == null || value.equals("") || value.equalsIgnoreCase("No")){
					Logger.logDebug(MODULE, "doWebSerivceAuditEntry System parameter not configured as YES, So Discarding WS Audit");
					return;
				}
				WebServiceAuditEntry auditEntry = new WebServiceAuditEntry();
				auditEntry.setEntityTypeId(auditSummary.getEntityTypeId());
				auditEntry.setEventName(auditSummary.getEventName());
				auditEntry.setEventProcessStatus(auditSummary.getEventProcessStatus());
				auditEntry.setResponseCode(auditSummary.getResponseCode());
				auditEntry.setResponseMessage(auditSummary.getResponseMessage());
				auditEntry.setProcessDate(new Date());
				if(auditSummary.getInputParam()!=null && !auditSummary.getInputParam().isEmpty()) {
					auditEntry.setInputParam(auditSummary.getInputParam());
				}
				if(auditSummary.getOutputParam()!=null && !auditSummary.getOutputParam().isEmpty()) {
					auditEntry.setOutputParam(auditSummary.getOutputParam());
				}
				
				systemAuditSessionBeanLocal.createWebServiceAudit(auditEntry);
				
				Logger.logTrace(MODULE, auditSummary.toString());
				
			}else{
				Logger.logDebug(MODULE, "auditSummary is null");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new CreateBLException(e.getMessage());
		}
		
		
	}
	@Override
	public List<SearchWsAuditVO> searchWsAudit(Date fromDate, Date toDate, Long eventId,String inputParam,String outputParam,String eventStatus)
			throws SearchBLException {
		
		List<WebServiceAuditEntry> systemAudits = systemAuditSessionBeanLocal.searchWebServiceAudit(fromDate, toDate, eventId,inputParam,outputParam,eventStatus);
		List<SearchWsAuditVO> searchAuditVOs = new ArrayList<SearchWsAuditVO>();
		if(systemAudits != null && !systemAudits.isEmpty()) {
			searchAuditVOs = AuditDataConversionUtilities.prepareSerachWsAuditDataView(systemAudits);
		}
		
		
		return searchAuditVOs;
	}
	@Override
	public ViewWsAuditEntryVO getViewWsAuditById(Long viewEntityId,
			IBLSession blSession) throws SearchBLException {
	
		
		ViewWsAuditEntryVO auditVO = new ViewWsAuditEntryVO();
		WebServiceAuditEntry systemAudit = systemAuditSessionBeanLocal.viewWsAuditById(viewEntityId);
		Logger.logTrace(MODULE, systemAudit+"");
		auditVO = AuditDataConversionUtilities.prepareViewWsAuditDataBy(systemAudit);
		
		return auditVO;
		
	}
	@Override
	public void doAuditEntry(List<AuditSummary> summaries)
			throws CreateBLException {
		
		if(summaries!=null && !summaries.isEmpty()) {
			
			List<SystemAudit> systemAudits = new ArrayList<SystemAudit>();
			
			for(AuditSummary auditSummary : summaries) {
				
				try{
					if(auditSummary!=null){
						SystemAction systemAction = systemInternalSessionBeanLocal.findSystemActionByAlias(auditSummary.getActionAlias());
						if(systemAction!=null){
						if(!(("Y").charAt(0)==(systemAction.getEnableaudit()))){
							Logger.logDebug(MODULE, "doAuditEntry ActionAlias = "+auditSummary.getActionAlias()+" is not Auditable, So Discarding Audit");	
						}else{
							SystemAudit systemAudit = new SystemAudit();
							
							systemAudit.setUserId(auditSummary.getUserId());
							systemAudit.setSystemActionId(systemAction.getActionId());
							systemAudit.setSystemAuditTypeId(auditSummary.getAuditTypeId());
							systemAudit.setAuditdate(auditSummary.getAuditDate());
							systemAudit.setIpaddress(auditSummary.getIpAddress());
							systemAudit.setReason(auditSummary.getReason());
							String strDynamicMessage = getDynamicMessage(systemAction,auditSummary.getRemarks(),auditSummary.getTagMap());
							
							Logger.logDebug(MODULE, "final dynamic msg :"+strDynamicMessage);
							if(strDynamicMessage!=null && !strDynamicMessage.equals("")){
								systemAudit.setRemarks(strDynamicMessage);
							}else{
								systemAudit.setRemarks(auditSummary.getRemarks());
							}
							
							
							if(auditSummary.getAuditEntryDatas()!=null && !auditSummary.getAuditEntryDatas().isEmpty()){
								Set<AuditEntry> auditEntries = new HashSet<AuditEntry>();
								
								for(AuditEntryData auditEntryData : auditSummary.getAuditEntryDatas()){
									AuditEntry auditEntry = new AuditEntry();
									
									auditEntry.setFieldName(auditEntryData.getFieldName());
									auditEntry.setNewValue(auditEntryData.getNewValue());
									auditEntry.setOldValue(auditEntryData.getOldValue());
									auditEntry.setTableName(auditEntryData.getTableName());
									auditEntry.setSystemAudit(systemAudit);
									
									auditEntries.add(auditEntry);
								}
								
								systemAudit.setAuditEntries(auditEntries);
							}
//							systemAuditSessionBeanLocal.createSystemAudit(systemAudit);
							systemAudits.add(systemAudit);
							
						}
						}else{
							Logger.logDebug(MODULE, "auditSummary is null");
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
					throw new CreateBLException(e.getMessage());
				}
				
			}
			
			systemAuditSessionBeanLocal.createSystemAudit(systemAudits);
			
		}
		
		
		
		
	}
	
}
