package com.elitecore.cpe.bl.session.notification;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;

import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentCategory;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentTemplate;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentTemplateDetail;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.NotificationAudit;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.SMSDocumentTemplateDetail;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.ThresholdNotificationHistoryDetail;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.OrderData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.bl.vo.configuration.notification.CheckValidDateForTemplateVO;
import com.elitecore.cpe.bl.vo.order.OrderDetailVo;
import com.elitecore.cpe.util.logger.Logger;
/**
 * 
 * @author Yash.Kapasi
 *
 */

@Stateless
public class NotificationSessionBean extends BaseSessionBean implements NotificationSessionBeanLocal {

	private static final String MODULE = "NOTIFICATION-SB";

	public NotificationSessionBean() {
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentCategory> findAllTemplateCategory()
			throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findAllTemplateCategory");
		try {
			List<DocumentCategory>  documentCategories = getEntityManager().createNamedQuery("DocumentCategory.findAllDocumentCategory")
					.getResultList();
		 return documentCategories;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findAllTemplateCategory Reason" +e.getMessage());
			throw new SearchBLException("Find AllTemplateCategory operation failed, reason: " + e.getMessage(), e);
		}
	}

	@Override
	public DocumentCategory findMessageTagByDocCat(Long documentCatId)
			throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findMessageTagByDocCat");
		try {
			DocumentCategory  documentCategorie = (DocumentCategory) getEntityManager().createNamedQuery("DocumentCategory.findDocumentCategoryById")
					.setParameter("documentCategoryId", documentCatId)
					.getSingleResult();
		 return documentCategorie;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findMessageTagByDocCat Reason" +e.getMessage());
			throw new SearchBLException("Find MessageTagByDocCat operation failed, reason: " + e.getMessage(), e);
		}
	}

	@Override
	public DocumentTemplate createDocumentTemplate(
			DocumentTemplate documentTemplate) throws CreateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createDocumentTemplate");
		}
		
		Logger.logTrace(MODULE, "inside createDocumentTemplate ::::::::::::");
		
		try {			
			getEntityManager().persist(documentTemplate);
			getEntityManager().flush();
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning createDocumentTemplate");
			}
			
		}catch(Exception e) {
			

			
			e.printStackTrace();
				
			//getSessionContext().setRollbackOnly();
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("UKTBDOCTMP")){
	        			throw new CreateBLException("Name " + documentTemplate.getName() +" Already Exists");
	        	   } else {
	        	   }
	    	   }
	    	   if(isErrorLevel())
	    		   logError(MODULE, "Error in createDocumentTemplate Reason" +e.getMessage());
	    	   getSessionContext().setRollbackOnly();
	    	   
			throw new CreateBLException("Create DocumentTemplate  Operation Failed, Reason : " + e.getMessage(), e);
		}
		return documentTemplate;
	}

	@Override
	public void createDocumentTemplateDetail(
			DocumentTemplateDetail templateDetail) throws CreateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createDocumentTemplateDetail");
		}
		try {			
			getEntityManager().persist(templateDetail);
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning createDocumentTemplateDetail");
			}
			
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in createDocumentTemplateDetail Reason" +e.getMessage());
			getSessionContext().setRollbackOnly();
			throw new CreateBLException("Create DocumentTemplateDetail  Operation Failed, Reason : " + e.getMessage(), e);
		}
	
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentTemplate> searchDocumentTemplate(String name,
			Long categoryId) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  searchDocumentTemplate..... documentCategoryID. "+categoryId);
		try {
			String query = "select o from DocumentTemplate o where   upper(o.name) like :name ";
			if(categoryId!=null && categoryId!=0L) {
				query+=" and o.documentCategoryId='"+categoryId+"'";
			}
			query+=" ORDER BY o.createDate DESC";
			List<DocumentTemplate>  documentTemplates = (List<DocumentTemplate>)getEntityManager().createQuery(query)
					.setParameter("name", formatForUpperLikeSearch(name))
					.getResultList();
		 return documentTemplates;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in searchDocumentTemplate Reason" +e.getMessage());
			throw new SearchBLException("Find searchDocumentTemplate operation failed, reason: " + e.getMessage(), e);
		}
	}

	@Override
	public DocumentTemplate findDocumentTemplateById(Long documentId)
			throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findDocumentTemplateById");
		try {
			DocumentTemplate  documentTemplate = (DocumentTemplate) getEntityManager().createNamedQuery("DocumentTemplate.findDocumentTemplateById")
					.setParameter("documentTemplateId", documentId)
					.getSingleResult();
		 return documentTemplate;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findDocumentTemplateById Reason" +e.getMessage());
			throw new SearchBLException("Find DocumentTemplateById operation failed, reason: " + e.getMessage(), e);
		}
	}

	@Override
	public void updateDocumentTemplate(DocumentTemplate documentTemplate)
			throws UpdateBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateDocumentTemplate");
		}
		try {			
			getEntityManager().merge(documentTemplate);
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning updateDocumentTemplate");
			}
			
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in updateDocumentTemplate Reason" +e.getMessage());
			
			Object object = e.getCause();
	    	   if(object instanceof ConstraintViolationException){
	        	   ConstraintViolationException constraintViolationException = (ConstraintViolationException)e.getCause();
	        	   
	        	   if(constraintViolationException.getConstraintName().toUpperCase().contains("UKTBDOCTMP")){
	        			throw new UpdateBLException("Name " + documentTemplate.getName() +" Already Exists");
	        	   } else {
	        	   }
	    	   }
	    	   if(isErrorLevel())
	    		   logError(MODULE, "Error in createDocumentTemplate Reason" +e.getMessage());
	    	   getSessionContext().setRollbackOnly();
	    	   
			throw new UpdateBLException("Update DocumentTemplate  Operation Failed, Reason : " + e.getMessage(), e);
		}
		
	}

	@Override
	public DocumentTemplateDetail findDocumentTemplateDetailByDocId(Long documentTemplateId) throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findDocumentTemplateDetailByDocId");
		try {
			DocumentTemplateDetail  documentTemplate = (DocumentTemplateDetail) getEntityManager().createNamedQuery("DocumentTemplateDetail.findDocumentTemplateDetailById")
					.setParameter("documentTemplateId", documentTemplateId)
					.getSingleResult();
		 return documentTemplate;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findDocumentTemplateDetailByDocId Reason" +e.getMessage());
			throw new SearchBLException("Find DocumentTemplateDetailByDocId operation failed, reason: " + e.getMessage(), e);
		}
	}

	@Override
	public SMSDocumentTemplateDetail findSmsDocumentDetail(
			Long documentTemplateId) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "inside  findSmsDocumentDetail");
		try {
			SMSDocumentTemplateDetail  documentTemplate = (SMSDocumentTemplateDetail) getEntityManager().createNamedQuery("SMSDocumentTemplateDetail.findDocumentTemplateDetailById")
					.setParameter("documentTemplateId", documentTemplateId)
					.getSingleResult();
		 return documentTemplate;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findSmsDocumentDetail Reason" +e.getMessage());
			throw new SearchBLException("Find findSmsDocumentDetail operation failed, reason: " + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String checkValidDateForTemplate(CheckValidDateForTemplateVO checkValidDateForTemplate)
			throws SearchBLException {
		List<DocumentTemplate> documentTemplates=null;
		if(isTraceLevel()){
			logTrace(MODULE, "inside  checkValidDateForTemplate");
		}
			try {
			if(checkValidDateForTemplate.getFlag().equals("Update")){
				 documentTemplates =  getEntityManager().createNamedQuery("DocumentTemplate.searchDocumentTemplateByCategoryIdforUpdate").setParameter("documentCategoryId", checkValidDateForTemplate.getDocumentCategoryId())
				 .setParameter("documentTemplateId", checkValidDateForTemplate.getDocumentId()).setParameter("validFormDate", checkValidDateForTemplate.getValidFormDate()).setParameter("validToDate", checkValidDateForTemplate.getValidToDate()).getResultList();
	
			}
			else{ 
				documentTemplates =  getEntityManager().createNamedQuery("DocumentTemplate.searchDocumentTemplateByCategoryId").setParameter("documentCategoryId", checkValidDateForTemplate.getDocumentCategoryId())
					.setParameter("validFormDate", checkValidDateForTemplate.getValidFormDate()).setParameter("validToDate", checkValidDateForTemplate.getValidToDate()).getResultList();
			}
			Logger.logTrace(MODULE,"::::::documentTemplates:::"+documentTemplates);
		 if(documentTemplates!=null && !documentTemplates.isEmpty()) {
			 DocumentTemplate documentTemplate = documentTemplates.get(0);
			 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			 
			 String time = "Document Template with the same category already exists in timeframe " + dateFormat.format(documentTemplate.getValidFormDate()) + " to " + dateFormat.format(documentTemplate.getValidToDate());
			 
			 return time;
		 }
		 
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in checkValidDateForTemplate Reason" +e.getMessage());
			throw new SearchBLException("checkValidDateForTemplate operation failed, reason: " + e.getMessage(), e);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentTemplate> findDocumentTemplateByAliasAndDate(
			String alias, Timestamp currentTimestamp) throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findDocumentTemplateByAliasAndDate");
		try {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
			Date date = dateFormat.parse(dateFormat.format(currentTimestamp));
			
			//:date >= o.validFormDate and :date <= o.validToDate
			String sql = "select o from DocumentTemplate o where o.documentCategory.alias=:alias and :date >= TO_DATE( o.validFormDate,'DD-MM-YY')  and  :date <= TO_DATE( o.validToDate,'DD-MM-YY')";
			System.out.println("Parsed date :: "+date);
			
	/*		Calendar calendarFrom = Calendar.getInstance();
			calendarFrom.set(Calendar.HOUR_OF_DAY, 00);
			calendarFrom.set(Calendar.MINUTE, 00);
			calendarFrom.set(Calendar.SECOND, 00);
			
			Calendar calendarTo = Calendar.getInstance();
			calendarTo.set(Calendar.HOUR_OF_DAY, 23);
			calendarTo.set(Calendar.MINUTE, 59);
			calendarTo.set(Calendar.SECOND, 59);
			
			System.out.println("calendarFrom :: " + calendarFrom.getTime());
			
			System.out.println("calendarTo :: " +calendarTo.getTime());
*/			
			List<DocumentTemplate> documentTemplates =  getEntityManager().createQuery(sql)
					.setParameter("alias", alias)
					.setParameter("date", date)
					.getResultList();
		 Logger.logTrace(MODULE, ":::::::::documentTemplates::"+documentTemplates);
		 return documentTemplates;
		 
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findDocumentTemplateByAliasAndDate Reason" +e.getMessage());
			throw new SearchBLException("findDocumentTemplateByAliasAndDate operation failed, reason: " + e.getMessage(), e);
		}
		
	}

	@Override
	public NotificationAudit createNotificationAudit(
			NotificationAudit notificationAudit) throws CreateBLException {
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createNotificationAudit");
		}
		try {			
			getEntityManager().persist(notificationAudit);
			getEntityManager().flush();
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning createNotificationAudit");
			}
			
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in createNotificationAudit Reason" +e.getMessage());
			getSessionContext().setRollbackOnly();
			throw new CreateBLException("Create NotificationAudit  Operation Failed, Reason : " + e.getMessage(), e);
		}
		return notificationAudit;
	}

	@Override
	public ThresholdNotificationHistoryDetail createThresholdNotificationHistoryDetail(
			ThresholdNotificationHistoryDetail thresholdNotificationHistoryDetail)
			throws CreateBLException {
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside createThresholdNotificationHistoryDetail");
		}
		try {			
			 getEntityManager().persist(thresholdNotificationHistoryDetail);
			 getEntityManager().flush();
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning createThresholdNotificationHistoryDetail");
			}
			
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in createThresholdNotificationHistoryDetail Reason" +e.getMessage());
			getSessionContext().setRollbackOnly();
			throw new CreateBLException("Create createThresholdNotificationHistoryDetail  Operation Failed, Reason : " + e.getMessage(), e);
		}
		return thresholdNotificationHistoryDetail;
	}

	@Override
	public ThresholdNotificationHistoryDetail findNotificationHistoryById(
			Long notificationHistoryId) throws SearchBLException {
		
		if(isTraceLevel())
			logTrace(MODULE, "inside  findNotificationHistoryById");
		try {
			ThresholdNotificationHistoryDetail  historyDetail = (ThresholdNotificationHistoryDetail) getEntityManager().createNamedQuery("ThresholdNotificationHistoryDetail.findById")
					.setParameter("notificationHistoryId", notificationHistoryId)
					.getSingleResult();
		 return historyDetail;
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in findNotificationHistoryById Reason" +e.getMessage());
			throw new SearchBLException("Find NotificationHistoryById operation failed, reason: " + e.getMessage(), e);
		}

	}

	@Override
	public ThresholdNotificationHistoryDetail updateThresholdNotificationHistoryDetail(
			ThresholdNotificationHistoryDetail historyDetail)
			throws UpdateBLException {
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside updateThresholdNotificationHistoryDetail");
		}
		try {			
			getEntityManager().merge(historyDetail);
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning updateThresholdNotificationHistoryDetail");
			}
			
		}catch(Exception e) {			
			e.printStackTrace();
			if(isErrorLevel())
				logError(MODULE, "Error in updateThresholdNotificationHistoryDetail Reason" +e.getMessage());
	    	   getSessionContext().setRollbackOnly();
	    	   
			throw new UpdateBLException("Update ThresholdNotificationHistoryDetail  Operation Failed, Reason : " + e.getMessage(), e);
		}
		return historyDetail;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isNotificationEligible(Long thresholdID,
			Long resourceTypeId, Long resourceSubTypeId, Long itemId) {
	
		boolean result  = true;
		try {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
			String date = dateFormat.format(dateFormat.parse(dateFormat.format(getCurrentTimestamp())));
			
			String query = "select o from ThresholdNotificationHistoryDetail o where o.warehouseThresholdId=:warehouseThresholdId and o.resourceTypeId=:resourceTypeId and o.notificationSent ='Y' and TO_DATE(:date,'DD-MM-YY')=TRUNC( o.createDate)";
			
			if(resourceSubTypeId!=null) {
				query = query + " and o.resourceSubTypeId='"+resourceSubTypeId+"' ";
			} else {
				query = query + " and o.resourceSubTypeId is null ";
			}
			
			
			if(itemId!=null) {
				query = query + " and o.itemId='"+itemId+"' ";
			} else {
				query = query + " and o.itemId is null ";
			}
			
			
			List<ThresholdNotificationHistoryDetail> notificationDetailHistoryDatas = getEntityManager().createQuery(query)
			.setParameter("warehouseThresholdId", thresholdID)
			.setParameter("resourceTypeId", resourceTypeId)
			.setParameter("date", date)
			.getResultList();
			
			if(notificationDetailHistoryDatas!=null && !notificationDetailHistoryDatas.isEmpty()) {
				result = false;
			}
			
			
		}catch (NoResultException e) {
			result  = true;
		}catch (Exception e) {
			e.printStackTrace();
			result  = true;
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isEligiblePendingOrderNotification(
			OrderDetailVo orderDetailVo) throws SearchBLException {
		
		boolean result  = true;
		try {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
			String date = dateFormat.format(dateFormat.parse(dateFormat.format(getCurrentTimestamp())));
			
			String query = "select o from OrderAgentHistoryData o where o.orderId=:orderId and o.orderType=:orderType and TO_DATE(:date,'DD-MM-YY')=TRUNC( o.emailSendDate) ";
			
			
			List<OrderData> orderDatas = getEntityManager().createQuery(query)
			.setParameter("orderId", orderDetailVo.getOrderId())
			.setParameter("orderType", orderDetailVo.getOrderType())
			.setParameter("date", date)
			.getResultList();
			
			if(orderDatas!=null && !orderDatas.isEmpty()) {
				result = false;
			}
			
			
		}catch (NoResultException e) {
			result  = true;
		}catch (Exception e) {
			e.printStackTrace();
			result  = true;
		}
		
		return result;
	}
}
