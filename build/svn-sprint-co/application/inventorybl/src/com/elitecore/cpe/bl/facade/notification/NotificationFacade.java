package com.elitecore.cpe.bl.facade.notification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.data.notification.DocumentTemplateDetailWrapperdata;
import com.elitecore.cpe.bl.data.notification.DocumentTemplateWrapperdata;
import com.elitecore.cpe.bl.data.notification.MessageTagWrapperData;
import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.data.notification.SMSDocumentTemplateDetailWrapperData;
import com.elitecore.cpe.bl.data.system.audit.AuditSummaryDetail;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentCategory;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentTemplate;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentTemplateDetail;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.MessageTag;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.NotificationAudit;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.SMSDocumentTemplateDetail;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.BaseFacade;
import com.elitecore.cpe.bl.facade.system.audit.AuditDataConversionUtilities;
import com.elitecore.cpe.bl.facade.system.systemparameter.SystemParameterFacadeLocal;
import com.elitecore.cpe.bl.session.notification.NotificationSessionBeanLocal;
import com.elitecore.cpe.bl.vo.configuration.notification.CheckValidDateForTemplateVO;
import com.elitecore.cpe.bl.vo.configuration.notification.NotificationCategoryVO;
import com.elitecore.cpe.bl.vo.configuration.notification.SearchDocumentTemplateVO;
import com.elitecore.cpe.bl.vo.configuration.notification.ViewDocumentTemplateVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.util.notification.NotificationUtil;

/**
 * 
 * @author Yash.Kapasi
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotificationFacade extends BaseFacade implements NotificationFacadeLocal,NotificationFacadeRemote {

	private static final String MODULE = "NOTIFICATION-FACADE";
	@EJB private NotificationSessionBeanLocal notificationSessionBeanLocal;
	@EJB private SystemParameterFacadeLocal systemParameterFacadeLocal;

	public NotificationFacade() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Find all Template Category
	 * @author yash.kapasi
	 * @return {@link List}<{@link NotificationCategoryVO}> data
	 * @throws SearchBLException
	 */
	@Override
	public List<NotificationCategoryVO> findAllTemplateCategory() throws SearchBLException {
		Logger.logTrace(MODULE,"inside findAllTemplateCategory");
		if(isTraceLevel()){
			logTrace(MODULE, "inside findAllTemplateCategory");
		}
		
		try{
			List<NotificationCategoryVO> notificationComboData = new ArrayList<NotificationCategoryVO>();
			List<DocumentCategory> documentCategories = notificationSessionBeanLocal.findAllTemplateCategory();
			if(documentCategories!=null && !documentCategories.isEmpty()){
				for(DocumentCategory documentCategory : documentCategories){
					
					NotificationCategoryVO categoryVO = new NotificationCategoryVO();
					categoryVO.setName(documentCategory.getName());
					categoryVO.setId(documentCategory.getDocumentCategoryId());
					if(documentCategory.getEnableEmail()=='Y') {
						categoryVO.setEmail(true);
					} else {
						categoryVO.setEmail(false);
					}
					if(documentCategory.getEnableSms()=='Y') {
						categoryVO.setSms(true);
					} else {
						categoryVO.setSms(false);
					}
					
					notificationComboData.add(categoryVO);
				}
			}
			return notificationComboData;
		}catch(SearchBLException e){
			if(isErrorLevel()){
				logError(MODULE, "Error Occured while findAllTemplateCategory. Reason " + e.getMessage());
			}
			throw new SearchBLException(e.getMessage(),e);
		}
	}

	
	/**
	 * Find all Message Tag by Document Category Id
	 * @author yash.kapasi
	 * @param Long documentCatId
	 * @return {@link List}<{@link MessageTagWrapperData}> messageComboData
	 * @throws SearchBLException
	 */
	@Override
	public List<MessageTagWrapperData> findMessageTagByDocCat(Long documentCatId) throws SearchBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside findMessageTagByDocCat");
		}
		
		try{
			List<MessageTagWrapperData> messageComboData = new ArrayList<MessageTagWrapperData>();
			DocumentCategory documentCategories = notificationSessionBeanLocal.findMessageTagByDocCat(documentCatId);
			if(documentCategories!=null){
				for(MessageTag messageTag : documentCategories.getMessageTags()){
					messageComboData.add(new MessageTagWrapperData(messageTag.getMessageTagId(),messageTag.getMessageText(),messageTag.getDescription(),messageTag.getMessageTag()));
				}
			}
			return messageComboData;
		}catch(SearchBLException e){
			if(isErrorLevel()){
				logError(MODULE, "Error Occured while findMessageTagByDocCat. Reason " + e.getMessage());
			}
			throw new SearchBLException(e.getMessage(),e);
		}
	}

	
	/**
	 * Creates Document Template in the system
	 * @author yash.kapasi
	 * @param {@link DocumentTemplateWrapperdata} templateWrapperdata
	 * @param {@link IBLSession} session
	 * @throws CreateBLException
	 */
	@Override
	public void createDocumentTemplate(DocumentTemplateWrapperdata templateWrapperdata,IBLSession session)
			throws CreateBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside createDocumentTemplate");
		}
			try {
				DocumentTemplate documentTemplate = new DocumentTemplate();
				documentTemplate = NotificationDataConversionUtils.prepareNotificationData(documentTemplate,templateWrapperdata,session);
				DocumentCategory documentCategory = notificationSessionBeanLocal.findMessageTagByDocCat(templateWrapperdata.getDocumentCategoryId());
				documentTemplate.setDocumentCategory(documentCategory);
				documentTemplate.setDocumentCategoryId(templateWrapperdata.getDocumentCategoryId());
				documentTemplate = notificationSessionBeanLocal.createDocumentTemplate(documentTemplate);
				
				//Create Notification Audit
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.NAME,documentTemplate.getName());
					
				addToAuditDynamicMessage(AuditConstants.CREATE_DOCUMENT_TEMPLATE, "Creating Document Template  ",AuditConstants.CREATE_AUDIT_TYPE, mapAudit, session);
				
			}catch(CreateBLException e) {
				if(isErrorLevel()){
					logError(MODULE, "Error Occured while CreateDocument Template. Reason " + e.getMessage());
				}
			} catch (SearchBLException e) {
				e.printStackTrace();
			}
		
		
	}

	
	/**
	 * Searches Document Template by name and Document Category
	 * @author yash.kapasi
	 * @param String name
	 * @param Long categoryId
	 * @return {@link List} <{@link SearchDocumentTemplateVO}> templateVOs
	 * @throws SearchBLException
	 */
	@Override
	public List<SearchDocumentTemplateVO> searchDocumentTemplate(String name,
			Long categoryId) throws SearchBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside searchDocumentTemplate");
		}
		
		try{
			List<SearchDocumentTemplateVO> templateVOs = new ArrayList<SearchDocumentTemplateVO>();
			List<DocumentTemplate> documentTemplates = null;
			documentTemplates = notificationSessionBeanLocal.searchDocumentTemplate(name,categoryId);
			if(documentTemplates!=null){
				for(DocumentTemplate documentTemplate : documentTemplates){
					SearchDocumentTemplateVO searchVO = new SearchDocumentTemplateVO();
					searchVO = NotificationDataConversionUtils.prepareSearchDocumentVO(searchVO,documentTemplate);
					templateVOs.add(searchVO);
				}
			}
			return templateVOs;
		}catch(SearchBLException e){
			if(isErrorLevel()){
				logError(MODULE, "Error Occured while searchDocumentTemplate. Reason " + e.getMessage());
			}
			throw new SearchBLException(e.getMessage(),e);
		}
	}

	
	/**
	 * Finds Document View Data by DocumentId
	 * @author yash.kapasi
	 * @param Long documentId
	 * @return {@link ViewDocumentTemplateVO} templateVO
	 * @throws SearchBLException
	 */
	@Override
	public ViewDocumentTemplateVO findDocumentViewData(Long documentId)
			throws SearchBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside findDocumentViewData");
		}
		
		try{
			ViewDocumentTemplateVO templateVO = null;
			DocumentTemplate documentTemplate = notificationSessionBeanLocal.findDocumentTemplateById(documentId);
			if(documentTemplate!=null) {
				templateVO = NotificationDataConversionUtils.prepareViewDocumentVO(documentTemplate);
			}
			
			return templateVO;
		}catch(SearchBLException e){
			if(isErrorLevel()){
				logError(MODULE, "Error Occured while findDocumentViewData. Reason " + e.getMessage());
			}
			throw new SearchBLException(e.getMessage(),e);
		}
	}

	
	/**
	 * Update Document Template Basic Details
	 * @author yash.kapasi
	 * @param {@link DocumentTemplateWrapperdata} wrapperdata
	 * @param {@link IBLSession} iblSession
	 * @throws UpdateBLException
	 */
	@Override
	public void updateDocumentTemplateBasicDetails(DocumentTemplateWrapperdata wrapperdata,IBLSession iblSession)
			throws UpdateBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside updateDocumentTemplate");
		}
		
		try{
			
			DocumentTemplate documentTemplate = notificationSessionBeanLocal.findDocumentTemplateById(wrapperdata.getDocumentId());
			
			AuditSummaryDetail auditSummaryDetail = AuditDataConversionUtilities.prepareDocumentTemplateUpdateBasicDetailsAudit(documentTemplate,wrapperdata);
			
			if(documentTemplate!=null) {
				documentTemplate = NotificationDataConversionUtils.prepareUpdateData(documentTemplate,wrapperdata,iblSession);
				notificationSessionBeanLocal.updateDocumentTemplate(documentTemplate);
				
				//Create Notification Audit
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.NAME,documentTemplate.getName());
					
				addToAuditDynamicMessage(AuditConstants.UPDATE_DOCUMENT_TEMPLATE_BASICDETAILS, "Updating Document Template Basic Details ",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,auditSummaryDetail.getAuditEntryWrapper(),  iblSession);
				
				
			}
			
			
		}catch(SearchBLException e){
			if(isErrorLevel()){
				logError(MODULE, "Error Occured while updateDocumentTemplate. Reason " + e.getMessage());
			}
			
		}
		
	}

	
	/**
	 * Find Document Template Data from Document Id
	 * @author yash.kapasi
	 * @param Long documentId
	 * @return {@link DocumentTemplateWrapperdata} wrapperdata
	 * @throws SearchBLException
	 */
	@Override
	public DocumentTemplateWrapperdata findDocumentTemplateData(Long documentId)
			throws SearchBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside findDocumentTemplateData");
		}
		
		try{
			DocumentTemplateWrapperdata wrapperdata = null;
			DocumentTemplate documentTemplate = notificationSessionBeanLocal.findDocumentTemplateById(documentId);
			if(documentTemplate!=null) {
				wrapperdata = NotificationDataConversionUtils.prepareUpdateDocumentData(wrapperdata,documentTemplate);
			}
			
			return wrapperdata;
		}catch(SearchBLException e){
			if(isErrorLevel()){
				logError(MODULE, "Error Occured while findDocumentTemplateData. Reason " + e.getMessage());
			}
			throw new SearchBLException(e.getMessage(),e);
		}
	}

	
	/**
	 * Update Document Template Data 
	 * @author yash.kapasi
	 * @param {@link DocumentTemplateWrapperdata} templateWrapperdata
	 * @param {@link IBLSession} blSession
	 * @throws UpdateBLException
	 */
	@Override
	public void updateDocumentTemplate(DocumentTemplateWrapperdata templateWrapperdata,
			IBLSession blSession) throws UpdateBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside updateDocumentTemplate");
		}
		
		try{
			
			DocumentTemplate documentTemplate = notificationSessionBeanLocal.findDocumentTemplateById(templateWrapperdata.getDocumentId());
			DocumentTemplateDetail templateDetail = notificationSessionBeanLocal.findDocumentTemplateDetailByDocId(documentTemplate.getDocumentTemplateId());
			SMSDocumentTemplateDetail smsDocumentTemplateDetail = notificationSessionBeanLocal.findSmsDocumentDetail(documentTemplate.getDocumentTemplateId());
			AuditSummaryDetail data = new AuditSummaryDetail();
			
			if(templateWrapperdata.getDocumentTemplateDetails()!=null && !templateWrapperdata.getDocumentTemplateDetails().isEmpty()) {
				
				Set<DocumentTemplateDetail> documentTemplateDetails = new HashSet<DocumentTemplateDetail>();
				for(DocumentTemplateDetailWrapperdata wrapperdata : templateWrapperdata.getDocumentTemplateDetails()) {
					if(templateDetail!=null) {
						templateDetail.setDocumentTemplate(documentTemplate);
						
						templateDetail.setSubject(wrapperdata.getSubject());
						templateDetail.setTemplate(wrapperdata.getTemplate());
						templateDetail.setMimeType(wrapperdata.getMimeType());
						
						data.appendChanged(AuditConstants.TBLMMAILDOCUMENTTEMPLATEDETAIL, AuditConstants.TBLMMAILDOCUMENTTEMPLATEDETAIL_SUBJECT,templateDetail.getSubject(),wrapperdata.getSubject());
						
						documentTemplateDetails.add(templateDetail);
					} else {
						templateDetail = new DocumentTemplateDetail();
						templateDetail.setDocumentTemplate(documentTemplate);
						
						templateDetail.setSubject(wrapperdata.getSubject());
						templateDetail.setTemplate(wrapperdata.getTemplate());
						templateDetail.setMimeType(wrapperdata.getMimeType());
						documentTemplateDetails.add(templateDetail);
					}
					
					
				}
				documentTemplate.setDocumentTemplateDetails(documentTemplateDetails);
				
			}
			
			if(templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas()!=null && !templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas().isEmpty()) {
				 Set<SMSDocumentTemplateDetail> smsDocumentTemplateDetails = new HashSet<SMSDocumentTemplateDetail>();
				for(SMSDocumentTemplateDetailWrapperData wrapperData : templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas()) {
					if(smsDocumentTemplateDetail!=null) {

						smsDocumentTemplateDetail.setDocumentTemplate(documentTemplate);
						
						smsDocumentTemplateDetail.setTemplate(wrapperData.getTemplate());
						smsDocumentTemplateDetail.setMimeType(wrapperData.getMimeType());
						smsDocumentTemplateDetails.add(smsDocumentTemplateDetail);
					} else {

						smsDocumentTemplateDetail = new SMSDocumentTemplateDetail();
						smsDocumentTemplateDetail.setDocumentTemplate(documentTemplate);
						
						smsDocumentTemplateDetail.setTemplate(wrapperData.getTemplate());
						smsDocumentTemplateDetail.setMimeType(wrapperData.getMimeType());
						smsDocumentTemplateDetails.add(smsDocumentTemplateDetail);
					}
				}
				documentTemplate.setSmsDocumentTemplateDetails(smsDocumentTemplateDetails);
			}
			
			if(documentTemplate!=null) {
//				documentTemplate = NotificationDataConversionUtils.prepareUpdateTemplateData(documentTemplate,wrapperdata,blSession);
				notificationSessionBeanLocal.updateDocumentTemplate(documentTemplate);
				
				//Create Notification Audit
				Map<String,Object> mapAudit = new HashMap<String, Object>();
				mapAudit.put(AuditTagConstant.NAME,documentTemplate.getName());
					
				addToAuditDynamicMessage(AuditConstants.UPDATE_DOCUMENT_TEMPLATE, "Updating Document Template  ",AuditConstants.UPDATE_AUDIT_TYPE, mapAudit,data.getAuditEntryWrapper(), blSession);
				
				
			}
			
			
		}catch(SearchBLException e){
			if(isErrorLevel()){
				logError(MODULE, "Error Occured while updateDocumentTemplate. Reason " + e.getMessage());
			}
			
		}
		
	}
	
	
	
	

	/*@Override
	public NotificationWSResponse sendNotification(NotificationWSRequest notificationWSRequest)
			throws Exception {
		try {
			
			String value = systemParameterFacadeLocal.getSystemParameterValue(SystemParameterConstants.DEFAULT_NOTIFICATION_ENGINE_URL);
			NotificationEngineUtils notificationEngineUtils = new NotificationEngineUtils(value);
			return notificationEngineUtils.sendNotification(notificationWSRequest);
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
			
		
	}*/

	@Override
	public List<ComboBoxData> findAllTemplateCategoryFromEngine()
			throws SearchBLException {
		if(isTraceLevel()){
			logTrace(MODULE, "inside findAllTemplateCategory");
		}
		
		/*try{
			
			String value = systemParameterFacadeLocal.getSystemParameterValue(SystemParameterConstants.DEFAULT_TEMPLATE_ENGINE_URL);
			TemplateEngineUtils engineUtils = new TemplateEngineUtils(value);
			// Set the request object inplace of null
			List<ComboBoxData> notificationComboData = engineUtils.retrieveCategories(null);
			
			return notificationComboData;
		}catch(SearchBLException e){
			if(isErrorLevel()){
				logError(MODULE, "Error Occured while findAllTemplateCategory. Reason " + e.getMessage());
			}
			throw new SearchBLException(e.getMessage(),e);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}*/
		return null;
		
	}


	@Override
/*	public String checkValidDateForTemplate(Long documentCategoryId,
			Timestamp validFormDate, Timestamp validToDate) {
*/		
	public String checkValidDateForTemplate(CheckValidDateForTemplateVO checkValidDateForTemplateVO) {

	try {
			String isValid = notificationSessionBeanLocal.checkValidDateForTemplate(checkValidDateForTemplateVO);
			return isValid;
		} catch (SearchBLException e) {
			return null;
		}
		
	}


	/**
	 * Send Notification Service 
	 * @author yash.kapasi
	 * @param {@link NotificationData} data
	 */
	@Override
	public void sendNotificationService(NotificationData data) {
		
		try {
			
			if(data!=null) {
				
				List<DocumentTemplate> documentTemplates = notificationSessionBeanLocal.findDocumentTemplateByAliasAndDate(data.getAlias(),getCurrentTimestamp());
				if(documentTemplates!=null && !documentTemplates.isEmpty()) {
					DocumentTemplate documentTemplate = documentTemplates.get(0);
					if(documentTemplate.getDocumentCategory().getEnableEmail()=='Y') {
						Set<DocumentTemplateDetail> emailTemplate = documentTemplate.getDocumentTemplateDetails();
						if(emailTemplate!=null && !emailTemplate.isEmpty()) {
							DocumentTemplateDetail emailDoc = emailTemplate.iterator().next();
							String body = new String(emailDoc.getTemplate());
							boolean result = NotificationUtil.sendEmailNotification(data.getToEmails(), data.getCcEmails(), body, emailDoc.getSubject(), data.getValueMap());
							
							//Notification Audit
							NotificationAudit notificationAudit = new NotificationAudit();
							notificationAudit.setTo(NotificationUtil.prepareCommaSeparatedStringfromList(data.getToEmails()));
							notificationAudit.setCc(NotificationUtil.prepareCommaSeparatedStringfromList(data.getCcEmails()));
							notificationAudit.setCreateDate(getCurrentTimestamp());
							notificationAudit.setDestincationSystem("EMAIL ALERT");
							notificationAudit.setDocumentCategoryId(emailDoc.getDocumentTemplate().getDocumentCategoryId());
							notificationAudit.setNotificationType("EMAIL");
							
							String emailBody = NotificationUtil.prepareEmailBody(data.getValueMap(),body);
							String emailSubject = NotificationUtil.prepareEmailBody(data.getValueMap(),emailDoc.getSubject());
							
							notificationAudit.setSubject(emailSubject);
							notificationAudit.setContent(emailBody.getBytes());
							
							if(result) {
								notificationAudit.setDeliveryStatus("SUCCESS");
							} else {
								notificationAudit.setDeliveryStatus("FAIL");
							}
							
							notificationSessionBeanLocal.createNotificationAudit(notificationAudit);
							
						}
					}
					else {
						Logger.logTrace(MODULE, "EMail is Disabled for the Document Category");
					}
				} else {
					Logger.logTrace(MODULE, "No Document Template found in this current timeframe");
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
