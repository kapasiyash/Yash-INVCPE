package com.elitecore.cpe.bl.session.notification;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Local;

import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentCategory;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentTemplate;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentTemplateDetail;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.NotificationAudit;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.SMSDocumentTemplateDetail;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.ThresholdNotificationHistoryDetail;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.configuration.notification.CheckValidDateForTemplateVO;
import com.elitecore.cpe.bl.vo.order.OrderDetailVo;
/**
 * @author Yash.Kapasi
 *
 */
@Local
public interface NotificationSessionBeanLocal {

	public List<DocumentCategory> findAllTemplateCategory() throws SearchBLException;

	public DocumentCategory findMessageTagByDocCat(Long documentCatId) throws SearchBLException;

	public DocumentTemplate createDocumentTemplate(DocumentTemplate documentTemplate) throws CreateBLException;

	public void createDocumentTemplateDetail(DocumentTemplateDetail templateDetail) throws CreateBLException;

	public List<DocumentTemplate> searchDocumentTemplate(String name,Long categoryId) throws SearchBLException;

	public DocumentTemplate findDocumentTemplateById(Long documentId) throws SearchBLException;

	public void updateDocumentTemplate(DocumentTemplate documentTemplate) throws UpdateBLException;

	public DocumentTemplateDetail findDocumentTemplateDetailByDocId(Long documentTemplateId) throws SearchBLException;

	public SMSDocumentTemplateDetail findSmsDocumentDetail(Long documentTemplateId) throws SearchBLException;

//	public String checkValidDateForTemplate(Long documentCategoryId,Timestamp validFormDate, Timestamp validToDate) throws SearchBLException;
	public String checkValidDateForTemplate(CheckValidDateForTemplateVO checkValidDateForTemplateVO) throws SearchBLException;
	public List<DocumentTemplate> findDocumentTemplateByAliasAndDate(String alias, Timestamp currentTimestamp) throws SearchBLException;
	
	
	public NotificationAudit createNotificationAudit(NotificationAudit notificationAudit) throws CreateBLException;

	public ThresholdNotificationHistoryDetail createThresholdNotificationHistoryDetail(ThresholdNotificationHistoryDetail thresholdNotificationHistoryDetail) throws CreateBLException;

	public ThresholdNotificationHistoryDetail findNotificationHistoryById(Long notificationHistoryId) throws SearchBLException;

	public ThresholdNotificationHistoryDetail updateThresholdNotificationHistoryDetail(ThresholdNotificationHistoryDetail historyDetail) throws UpdateBLException;

	public boolean isNotificationEligible(Long thresholdID,Long resourceTypeId, Long resourceSubTypeId, Long itemId) ;

	public boolean isEligiblePendingOrderNotification(OrderDetailVo orderDetailVo) throws SearchBLException;
	
}
