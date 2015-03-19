package com.elitecore.cpe.bl.data.notification;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public class DocumentTemplateWrapperdata implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 private Long documentCategoryId;
	 private Long documentId;
     private String name;
     private String description;
     private Timestamp validFormDate;
     private Timestamp validToDate;
     private Long createdBy;
     private Timestamp createDate;
     private Long lastModifyBy;
     private Timestamp lastModifyDate;
     private List<DocumentTemplateDetailWrapperdata> documentTemplateDetails = new ArrayList<DocumentTemplateDetailWrapperdata>();
     private List<SMSDocumentTemplateDetailWrapperData> smsDocumentTemplateDetailWrapperDatas = new ArrayList<SMSDocumentTemplateDetailWrapperData>();
     
	public Long getDocumentCategoryId() {
		return documentCategoryId;
	}
	public void setDocumentCategoryId(Long documentCategoryId) {
		this.documentCategoryId = documentCategoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getValidFormDate() {
		return validFormDate;
	}
	public void setValidFormDate(Timestamp validFormDate) {
		this.validFormDate = validFormDate;
	}
	public Timestamp getValidToDate() {
		return validToDate;
	}
	public void setValidToDate(Timestamp validToDate) {
		this.validToDate = validToDate;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Long getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(Long lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	public Timestamp getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Timestamp lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public List<DocumentTemplateDetailWrapperdata> getDocumentTemplateDetails() {
		return documentTemplateDetails;
	}
	public void setDocumentTemplateDetails(
			List<DocumentTemplateDetailWrapperdata> documentTemplateDetails) {
		this.documentTemplateDetails = documentTemplateDetails;
	}
	public List<SMSDocumentTemplateDetailWrapperData> getSmsDocumentTemplateDetailWrapperDatas() {
		return smsDocumentTemplateDetailWrapperDatas;
	}
	public void setSmsDocumentTemplateDetailWrapperDatas(
			List<SMSDocumentTemplateDetailWrapperData> smsDocumentTemplateDetailWrapperDatas) {
		this.smsDocumentTemplateDetailWrapperDatas = smsDocumentTemplateDetailWrapperDatas;
	}
	public Long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	
	
     
     
     
}
