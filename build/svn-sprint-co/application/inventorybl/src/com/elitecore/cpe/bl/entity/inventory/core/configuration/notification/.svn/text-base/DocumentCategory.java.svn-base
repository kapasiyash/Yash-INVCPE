package com.elitecore.cpe.bl.entity.inventory.core.configuration.notification;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Hemang Rami
 */
@Entity
@Table(name="TBLMDOCUMENTCATEGORY"
)
@NamedQueries({
	@NamedQuery(name="DocumentCategory.findAllDocumentCategory", query="select o from DocumentCategory o"),
	@NamedQuery(name="DocumentCategory.findDocumentCategoryById", query="select o from DocumentCategory o where o.documentCategoryId= :documentCategoryId")
})
public class DocumentCategory  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long DocumentCategoryId;
     private String name;
     private String desccription;
     private String alias;
     private Character enableEmail;
     private Character enableSms;
     private Set<DocumentTemplate> documentTemplates = new HashSet<DocumentTemplate>(0);
     private Set<MessageTag> messageTags = new HashSet<MessageTag>(0);

    public DocumentCategory() {
    }

	
    public DocumentCategory(Long DocumentCategoryId, String name) {
        this.DocumentCategoryId = DocumentCategoryId;
        this.name = name;
    }
    public DocumentCategory(Long DocumentCategoryId, String name, String desccription, Set<DocumentTemplate> documentTemplates, Set<MessageTag> messageTags) {
       this.DocumentCategoryId = DocumentCategoryId;
       this.name = name;
       this.desccription = desccription;
       this.documentTemplates = documentTemplates;
       this.messageTags = messageTags;
    }
   
     @Id 

    
    @Column(name="DOCUMENTCATEGORYID")
    public Long getDocumentCategoryId() {
        return this.DocumentCategoryId;
    }
    
    public void setDocumentCategoryId(Long DocumentCategoryId) {
        this.DocumentCategoryId = DocumentCategoryId;
    }

    
    @Column(name="NAME")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="DESCCRIPTION")
    public String getDesccription() {
        return this.desccription;
    }
    
    public void setDesccription(String desccription) {
        this.desccription = desccription;
    }
    
    
    
    @Column(name="ALIAS")
    public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}

	
	
	
	 @Column(name="ENABLEEMAIL")
	public Character getEnableEmail() {
		return enableEmail;
	}


	public void setEnableEmail(Character enableEmail) {
		this.enableEmail = enableEmail;
	}

	 @Column(name="ENABLESMS")
	public Character getEnableSms() {
		return enableSms;
	}


	public void setEnableSms(Character enableSms) {
		this.enableSms = enableSms;
	}


@OneToMany(fetch=FetchType.LAZY, mappedBy="documentCategory")
    public Set<DocumentTemplate> getDocumentTemplates() {
        return this.documentTemplates;
    }
    
    public void setDocumentTemplates(Set<DocumentTemplate> documentTemplates) {
        this.documentTemplates = documentTemplates;
    }

@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="TBLRDOCCATEGORYMESSAGETAGREL", joinColumns = { 
        @JoinColumn(name="DOCUMENTCATEGORYID", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="MESSAGETAGID", nullable=false, updatable=false) })
    public Set<MessageTag> getMessageTags() {
        return this.messageTags;
    }
    
    public void setMessageTags(Set<MessageTag> messageTags) {
        this.messageTags = messageTags;
    }




}


