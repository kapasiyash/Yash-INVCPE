package com.elitecore.cpe.bl.entity.inventory.system.audit;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLMMESSAGETEMPLATE")
public class MessageTemplateData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private int templateId;
	private Long actionId;
	private String template;
	private String supportedTags;
	
	@Id
	@Column(name = "TEMPLATEID")
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	
	@Column(name = "ACTIONID")
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	
	@Column(name = "TEMPLATE")
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	
	@Column(name = "SUPPORTEDTAGS")
	public String getSupportedTags() {
		return supportedTags;
	}
	public void setSupportedTags(String supportedTags) {
		this.supportedTags = supportedTags;
	}
	
	@Override
	public String toString() {
		return "MessageTemplateData [templateId=" + templateId + ", actionId="
				+ actionId + ", template=" + template + ", supportedTags="
				+ supportedTags + "]";
	}
	
	
	
	
}
