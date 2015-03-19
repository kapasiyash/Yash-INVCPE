package com.elitecore.cpe.bl.vo.configuration.notification;

import java.io.Serializable;
import java.sql.Timestamp;

public class CheckValidDateForTemplateVO  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String flag;
	private Long documentCategoryId;
	private Timestamp validFormDate;
	private Timestamp validToDate;
	private Long documentId;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	public Long getDocumentCategoryId() {
		return documentCategoryId;
	}
	public void setDocumentCategoryId(Long documentCategoryId) {
		this.documentCategoryId = documentCategoryId;
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
	@Override
	public String toString() {
		return "CheckValidDateForTemplateVO [flag=" + flag
				+ ", documentCategoryId=" + documentCategoryId
				+ ", validFormDate=" + validFormDate + ", validToDate="
				+ validToDate + ", documentId=" + documentId + "]";
	}


}
