package com.elitecore.cpe.bl.facade.notification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.elitecore.cpe.bl.data.notification.DocumentTemplateDetailWrapperdata;
import com.elitecore.cpe.bl.data.notification.DocumentTemplateWrapperdata;
import com.elitecore.cpe.bl.data.notification.SMSDocumentTemplateDetailWrapperData;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentTemplate;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.DocumentTemplateDetail;
import com.elitecore.cpe.bl.entity.inventory.core.configuration.notification.SMSDocumentTemplateDetail;
import com.elitecore.cpe.bl.exception.NoSuchControllerException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.facade.BaseDataConversionUtils;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.vo.configuration.notification.SearchDocumentTemplateVO;
import com.elitecore.cpe.bl.vo.configuration.notification.ViewDocumentTemplateVO;
import com.elitecore.cpe.core.IBLSession;
/**
 * 
 * @author Yash.Kapasi
 *
 */
public class NotificationDataConversionUtils extends BaseDataConversionUtils {

	public static DocumentTemplate prepareNotificationData(DocumentTemplate documentTemplate,DocumentTemplateWrapperdata templateWrapperdata, IBLSession session) {
		
		 
		
		documentTemplate.setCreateDate(getCurrentTimestamp());
		documentTemplate.setCreatedBy(session.getSessionUserId()+"");
		documentTemplate.setLastModifyDate(getCurrentTimestamp());
		documentTemplate.setLastModifyBy(session.getSessionUserId()+"");
		documentTemplate.setName(templateWrapperdata.getName());
		documentTemplate.setDescription(templateWrapperdata.getDescription());
		documentTemplate.setValidFormDate(templateWrapperdata.getValidFormDate());
		documentTemplate.setValidToDate(templateWrapperdata.getValidToDate());
		
		if(templateWrapperdata.getDocumentTemplateDetails()!=null && !templateWrapperdata.getDocumentTemplateDetails().isEmpty()) {
			Set<DocumentTemplateDetail> documentTemplateDetails = new HashSet<DocumentTemplateDetail>();
			for(DocumentTemplateDetailWrapperdata wrapperdata : templateWrapperdata.getDocumentTemplateDetails()) {
				DocumentTemplateDetail templateDetail = new DocumentTemplateDetail();
				templateDetail.setDocumentTemplate(documentTemplate);
				templateDetail.setSubject(wrapperdata.getSubject());
				templateDetail.setTemplate(wrapperdata.getTemplate());
				templateDetail.setMimeType(wrapperdata.getMimeType());
				documentTemplateDetails.add(templateDetail);
				
			}
			documentTemplate.setDocumentTemplateDetails(documentTemplateDetails);
			
		}
		
		if(templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas()!=null && !templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas().isEmpty()) {
			 Set<SMSDocumentTemplateDetail> smsDocumentTemplateDetails = new HashSet<SMSDocumentTemplateDetail>();
			for(SMSDocumentTemplateDetailWrapperData wrapperData : templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas()) {
				SMSDocumentTemplateDetail templateDetail = new SMSDocumentTemplateDetail();
				templateDetail.setDocumentTemplate(documentTemplate);
				templateDetail.setTemplate(wrapperData.getTemplate());
				templateDetail.setMimeType(wrapperData.getMimeType());
				smsDocumentTemplateDetails.add(templateDetail);
			}
			documentTemplate.setSmsDocumentTemplateDetails(smsDocumentTemplateDetails);
		}
		
		
		
		return documentTemplate;
	}

	public static SearchDocumentTemplateVO prepareSearchDocumentVO(SearchDocumentTemplateVO searchVO, DocumentTemplate documentTemplate) {
		
		searchVO.setDocumentId(documentTemplate.getDocumentTemplateId());
		searchVO.setName(documentTemplate.getName());
		searchVO.setDescription(documentTemplate.getDescription());
		searchVO.setCreatedDate(documentTemplate.getCreateDate());
		searchVO.setLastModifiedDate(documentTemplate.getLastModifyDate());
		searchVO.setValidFormDate(documentTemplate.getValidFormDate());
		searchVO.setValidToDate(documentTemplate.getValidToDate());
		if(documentTemplate.getDocumentCategory()!=null){
			searchVO.setDocumentCategory(documentTemplate.getDocumentCategory().getName());
			searchVO.setEnableEmail(documentTemplate.getDocumentCategory().getEnableEmail());
			searchVO.setEnableSms(documentTemplate.getDocumentCategory().getEnableSms());
		}
		
		return searchVO;
	}

	public static ViewDocumentTemplateVO prepareViewDocumentVO(DocumentTemplate documentTemplate) {
		
		ViewDocumentTemplateVO templateVO = new ViewDocumentTemplateVO();
		templateVO.setName(documentTemplate.getName());
		templateVO.setDescription(documentTemplate.getDescription());
		templateVO.setValidFrom(documentTemplate.getValidFormDate());
		templateVO.setValidTo(documentTemplate.getValidToDate());
		templateVO.setCategory(documentTemplate.getDocumentCategory().getName());
		templateVO.setCreatedDate(documentTemplate.getCreateDate());
		templateVO.setLastModifiedDate(documentTemplate.getLastModifyDate());
		try {
			templateVO.setCreatedBy(UserFactory.findUserById(documentTemplate.getCreatedBy()).getName());
			templateVO.setLastModifiedBy(UserFactory.findUserById(documentTemplate.getLastModifyBy()).getName());
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (NoSuchControllerException e) {
			e.printStackTrace();
		}
		
		
		
		return templateVO;
	}

	public static DocumentTemplate prepareUpdateData(DocumentTemplate documentTemplate,DocumentTemplateWrapperdata wrapperdata,IBLSession iblSession) {
		
		documentTemplate.setDescription(wrapperdata.getDescription());
		documentTemplate.setValidFormDate(wrapperdata.getValidFormDate());
		documentTemplate.setValidToDate(wrapperdata.getValidToDate());
		documentTemplate.setLastModifyBy(iblSession.getSessionUserId()+"");
		documentTemplate.setLastModifyDate(getCurrentTimestamp());
		
		return documentTemplate;
	}

	public static DocumentTemplateWrapperdata prepareUpdateDocumentData(DocumentTemplateWrapperdata wrapperdata,DocumentTemplate documentTemplate) {
		
		wrapperdata = new DocumentTemplateWrapperdata();
		wrapperdata.setDocumentId(documentTemplate.getDocumentTemplateId());
		wrapperdata.setDocumentCategoryId(documentTemplate.getDocumentCategoryId());
		
		//For Email
		if(documentTemplate.getDocumentTemplateDetails()!=null && !documentTemplate.getDocumentTemplateDetails().isEmpty()) {
			Set<DocumentTemplateDetail> emailTemplates = documentTemplate.getDocumentTemplateDetails();
			List<DocumentTemplateDetailWrapperdata> detailWrapperdatas = new ArrayList<DocumentTemplateDetailWrapperdata>();
			for(DocumentTemplateDetail templateDetail : emailTemplates) {
				DocumentTemplateDetailWrapperdata detailWrapperdata = new DocumentTemplateDetailWrapperdata();
				detailWrapperdata.setDocumentTemplateId(templateDetail.getDocumentTemplate().getDocumentTemplateId());
				detailWrapperdata.setMimeType(templateDetail.getMimeType());
				detailWrapperdata.setSubject(templateDetail.getSubject());
				detailWrapperdata.setTemplate(templateDetail.getTemplate());
				detailWrapperdatas.add(detailWrapperdata);
			}
			wrapperdata.setDocumentTemplateDetails(detailWrapperdatas);
		}
		
		
		//For SMS
		if(documentTemplate.getSmsDocumentTemplateDetails()!=null && !documentTemplate.getSmsDocumentTemplateDetails().isEmpty()) {
			Set<SMSDocumentTemplateDetail> smsTemplates = documentTemplate.getSmsDocumentTemplateDetails();
			List<SMSDocumentTemplateDetailWrapperData> wrapperDatas = new ArrayList<SMSDocumentTemplateDetailWrapperData>();
			for(SMSDocumentTemplateDetail smsTemplate : smsTemplates) {
				SMSDocumentTemplateDetailWrapperData detailWrapperData = new SMSDocumentTemplateDetailWrapperData();
				detailWrapperData.setDocumentTemplateId(smsTemplate.getDocumentTemplate().getDocumentTemplateId());
				detailWrapperData.setMimeType(smsTemplate.getMimeType());
				detailWrapperData.setTemplate(smsTemplate.getTemplate());
				wrapperDatas.add(detailWrapperData);
			}
			wrapperdata.setSmsDocumentTemplateDetailWrapperDatas(wrapperDatas);
		}
		
		
		return wrapperdata;
	}

	public static DocumentTemplate prepareUpdateTemplateData(DocumentTemplate documentTemplate,DocumentTemplateWrapperdata templateWrapperdata, IBLSession blSession) {
		
		if(templateWrapperdata.getDocumentTemplateDetails()!=null && !templateWrapperdata.getDocumentTemplateDetails().isEmpty()) {
			Set<DocumentTemplateDetail> documentTemplateDetails = new HashSet<DocumentTemplateDetail>();
			for(DocumentTemplateDetailWrapperdata wrapperdata : templateWrapperdata.getDocumentTemplateDetails()) {
				DocumentTemplateDetail templateDetail = new DocumentTemplateDetail();
				templateDetail.setDocumentTemplate(documentTemplate);
				templateDetail.setDocumenttemplatedetailid(wrapperdata.getDocumentDetailId());
				templateDetail.setSubject(wrapperdata.getSubject());
				templateDetail.setTemplate(wrapperdata.getTemplate());
				templateDetail.setMimeType(wrapperdata.getMimeType());
				documentTemplateDetails.add(templateDetail);
				
			}
			documentTemplate.setDocumentTemplateDetails(documentTemplateDetails);
			
		}
		
		if(templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas()!=null && !templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas().isEmpty()) {
			 Set<SMSDocumentTemplateDetail> smsDocumentTemplateDetails = new HashSet<SMSDocumentTemplateDetail>();
			for(SMSDocumentTemplateDetailWrapperData wrapperData : templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas()) {
				SMSDocumentTemplateDetail templateDetail = new SMSDocumentTemplateDetail();
				templateDetail.setDocumentTemplate(documentTemplate);
				templateDetail.setDocumenttemplatedetailid(wrapperData.getDocumentDetailId());
				templateDetail.setTemplate(wrapperData.getTemplate());
				templateDetail.setMimeType(wrapperData.getMimeType());
				smsDocumentTemplateDetails.add(templateDetail);
			}
			documentTemplate.setSmsDocumentTemplateDetails(smsDocumentTemplateDetails);
		}
		
		return documentTemplate;
	}

}
