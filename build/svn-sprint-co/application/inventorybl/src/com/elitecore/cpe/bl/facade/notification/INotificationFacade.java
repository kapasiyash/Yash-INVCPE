package com.elitecore.cpe.bl.facade.notification;

import java.sql.Timestamp;
import java.util.List;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.notification.DocumentTemplateWrapperdata;
import com.elitecore.cpe.bl.data.notification.MessageTagWrapperData;
import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.configuration.notification.CheckValidDateForTemplateVO;
import com.elitecore.cpe.bl.vo.configuration.notification.NotificationCategoryVO;
import com.elitecore.cpe.bl.vo.configuration.notification.SearchDocumentTemplateVO;
import com.elitecore.cpe.bl.vo.configuration.notification.ViewDocumentTemplateVO;
import com.elitecore.cpe.core.IBLSession;

public interface INotificationFacade {

	public List<NotificationCategoryVO> findAllTemplateCategory() throws SearchBLException;

	public List<MessageTagWrapperData> findMessageTagByDocCat(Long documentCatId) throws SearchBLException;

	public void createDocumentTemplate(DocumentTemplateWrapperdata templateWrapperdata,IBLSession iblSession) throws CreateBLException;

	public List<SearchDocumentTemplateVO> searchDocumentTemplate(String name,Long categoryId) throws SearchBLException;

	public ViewDocumentTemplateVO findDocumentViewData(Long documentId) throws SearchBLException;

	public void updateDocumentTemplateBasicDetails(DocumentTemplateWrapperdata wrapperdata,IBLSession session) throws UpdateBLException;

	public DocumentTemplateWrapperdata findDocumentTemplateData(Long documentId) throws SearchBLException;

	public void updateDocumentTemplate(DocumentTemplateWrapperdata templateWrapperdata,IBLSession blSession) throws UpdateBLException;
	
//	public NotificationWSResponse sendNotification(NotificationWSRequest notificationWSRequest) throws Exception;

	public List<ComboBoxData> findAllTemplateCategoryFromEngine() throws SearchBLException;

	//public String checkValidDateForTemplate(Long documentCategoryId,Timestamp validFormDate, Timestamp validToDate);
	public String checkValidDateForTemplate(CheckValidDateForTemplateVO checkValidDateForTemplateVO);
	public void sendNotificationService(NotificationData data);
	
}
