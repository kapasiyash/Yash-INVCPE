package com.elitecore.cpe.bl.entity.inventory.core.configuration.notification;


import static javax.persistence.GenerationType.SEQUENCE;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemAction;

/**
 * @author Hemang Rami
 */
@Entity
@Table(name="TBLMDOCUMENTTEMPLATE"
)
@NamedQueries({
	@NamedQuery(name = "DocumentTemplate.findDocumentTemplateById", query = "select o from DocumentTemplate o where o.documentTemplateId = :documentTemplateId"),
	@NamedQuery(name = "DocumentTemplate.findDocumentTemplateByCategoryId", query = "select o from DocumentTemplate o where ((upper(o.name) like :name) and o.documentCategoryId = :documentCategoryId) or ((upper(o.name) like :name) or o.documentCategoryId = :documentCategoryId)" ) ,
	@NamedQuery(name = "DocumentTemplate.searchDocumentTemplate", query = "select o from DocumentTemplate o where (upper(o.name) like :name)"),
	@NamedQuery(name = "DocumentTemplate.searchDocumentTemplateByCategoryId", query = "select o from DocumentTemplate o where o.documentCategoryId = :documentCategoryId and ((:validFormDate between o.validFormDate and o.validToDate) or (:validToDate between o.validFormDate and o.validToDate))"),
	@NamedQuery(name = "DocumentTemplate.searchDocumentTemplateByCategoryIdforUpdate", query = "select o from DocumentTemplate o where o.documentCategoryId = :documentCategoryId and ((:validFormDate between o.validFormDate and o.validToDate) or (:validToDate between o.validFormDate and o.validToDate)) and o.documentTemplateId not in(:documentTemplateId)"),

	
	//commented query
	/*	@NamedQuery(name = "DocumentTemplate.findDocumentTemplateByAliasAndDate", query = "select o from DocumentTemplate o where o.documentCategory.alias=:alias and ((o.validFormDate between :dateFrom  and :dateTo ) or (o.validToDate between :dateFrom  and :dateTo ))")
*/	@NamedQuery(name = "DocumentTemplate.findDocumentTemplateByAliasAndDate", query = "select o from DocumentTemplate o where o.documentCategory.alias=:alias and ((o.validFormDate >= :dateFrom and o.validFormDate <= :dateTo ) or (o.validToDate >= :dateFrom and o.validToDate <= :dateTo))")
})
public class DocumentTemplate  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long documentTemplateId;
     private DocumentCategory documentCategory;
     private Long documentCategoryId;
     private String name;
     private String description;
     private Timestamp validFormDate;
     private Timestamp validToDate;
     private String createdBy;
     private Timestamp createDate;
     private String lastModifyBy;
     private Timestamp lastModifyDate;
//     private Set<SystemAction> systemActions = new HashSet<SystemAction>(0);
     private Set<DocumentTemplateDetail> documentTemplateDetails = new HashSet<DocumentTemplateDetail>(0);
     private Set<SMSDocumentTemplateDetail> smsDocumentTemplateDetails = new HashSet<SMSDocumentTemplateDetail>(0);

    public DocumentTemplate() {
    }

	
    public DocumentTemplate(DocumentCategory documentCategory, String name, String description, Timestamp validFormDate, Timestamp validToDate, String createdBy, Timestamp createDate, String lastModifyBy, Timestamp lastModifyDate) {
        this.documentCategory = documentCategory;
        this.name = name;
        this.description = description;
        this.validFormDate = validFormDate;
        this.validToDate = validToDate;
        this.createdBy = createdBy;
        this.createDate = createDate;
        this.lastModifyBy = lastModifyBy;
        this.lastModifyDate = lastModifyDate;
    }
    public DocumentTemplate(DocumentCategory documentCategory, String name, String description, Timestamp validFormDate, Timestamp validToDate, String createdBy, Timestamp createDate, String lastModifyBy, Timestamp lastModifyDate, Set<DocumentTemplateDetail> documentTemplateDetails) {
       this.documentCategory = documentCategory;
       this.name = name;
       this.description = description;
       this.validFormDate = validFormDate;
       this.validToDate = validToDate;
       this.createdBy = createdBy;
       this.createDate = createDate;
       this.lastModifyBy = lastModifyBy;
       this.lastModifyDate = lastModifyDate;
//       this.systemActions = systemActions;
       this.documentTemplateDetails = documentTemplateDetails;
    }
   
     @SequenceGenerator(name="generator", sequenceName="SEQ_DOCUMENTTEMPLATE")
     @Id @GeneratedValue(strategy=SEQUENCE, generator="generator")

    @Column(name="DOCUMENTTEMPLATEID")
    public Long getDocumentTemplateId() {
        return this.documentTemplateId;
    }
    
    public void setDocumentTemplateId(Long documentTemplateID) {
        this.documentTemplateId = documentTemplateID;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DOCUMENTCATEGORYID", nullable=false,insertable=false,updatable=false)
    public DocumentCategory getDocumentCategory() {
        return this.documentCategory;
    }


    public void setDocumentCategory(DocumentCategory documentCategory) {
        this.documentCategory = documentCategory;
    }

    
    @Column(name="NAME", unique=true)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="VALIDFROMDATE")
    public Timestamp getValidFormDate() {
        return this.validFormDate;
    }
    
    public void setValidFormDate(Timestamp validFormDate) {
        this.validFormDate = validFormDate;
    }

    @Column(name="VALIDTODATE")
    public Timestamp getValidToDate() {
        return this.validToDate;
    }
    
    public void setValidToDate(Timestamp validToDate) {
        this.validToDate = validToDate;
    }

    
    @Column(name="CREATEDBY")
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name="CREATEDATE")
    public Timestamp getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    
    @Column(name="LASTMODIBY")
    public String getLastModifyBy() {
        return this.lastModifyBy;
    }
    
    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    @Column(name="LASTMODIDATE")
    public Timestamp getLastModifyDate() {
        return this.lastModifyDate;
    }
    
    public void setLastModifyDate(Timestamp lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

/*@OneToMany(fetch=FetchType.LAZY, mappedBy="documentTemplate")
    public Set<SystemAction> getSystemActions() {
        return this.systemActions;
    }
    
    public void setSystemActions(Set<SystemAction> systemActions) {
        this.systemActions = systemActions;
    }*/

@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, mappedBy="documentTemplate")
    public Set<DocumentTemplateDetail> getDocumentTemplateDetails() {
        return this.documentTemplateDetails;
    }
    
    public void setDocumentTemplateDetails(Set<DocumentTemplateDetail> documentTemplateDetails) {
        this.documentTemplateDetails = documentTemplateDetails;
    }
    
    
    
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, mappedBy="documentTemplate")
    public Set<SMSDocumentTemplateDetail> getSmsDocumentTemplateDetails() {
		return smsDocumentTemplateDetails;
	}


	public void setSmsDocumentTemplateDetails(
			Set<SMSDocumentTemplateDetail> smsDocumentTemplateDetails) {
		this.smsDocumentTemplateDetails = smsDocumentTemplateDetails;
	}


	@Column(name="DOCUMENTCATEGORYID")
	public Long getDocumentCategoryId() {
		return documentCategoryId;
	}


	public void setDocumentCategoryId(Long documentCategoryId) {
		this.documentCategoryId = documentCategoryId;
	}


	@Override
	public String toString() {
		return "DocumentTemplate [documentTemplateId=" + documentTemplateId
				+ ", documentCategory=" + documentCategory
				+ ", documentCategoryId=" + documentCategoryId + ", name="
				+ name + ", description=" + description + ", validFormDate="
				+ validFormDate + ", validToDate=" + validToDate
				+ ", createdBy=" + createdBy + ", createDate=" + createDate
				+ ", lastModifyBy=" + lastModifyBy + ", lastModifyDate="
				+ lastModifyDate + ", documentTemplateDetails="
				+ documentTemplateDetails + ", smsDocumentTemplateDetails="
				+ smsDocumentTemplateDetails + "]";
	}

	/*private User userCreate;
	
	private User userModified;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "LASTMODIBY", insertable = false, updatable = false)   
	public User getUserModified() {
		return userModified;
	}

	public void setUserModified(User userModified) {
		this.userModified = userModified;
	}
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATEDBY", insertable = false, updatable = false)
	public User getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(User userCreate) {
		this.userCreate = userCreate;
	}
    */

}


