package com.elitecore.cpe.bl.entity.inventory.core.configuration.notification;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Hemang Rami
 */
@Entity
@Table(name="TBLMMESSAGETAG"
)
public class MessageTag  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long messageTagId;
     private String messageText;
     private String description;
     private String messageTag;
     private Set<DocumentCategory> documentCategories = new HashSet<DocumentCategory>(0);

    public MessageTag() {
    }

	
    public MessageTag(Long messageTagId, String messageText, String messageTag) {
        this.messageTagId = messageTagId;
        this.messageText = messageText;
        this.messageTag = messageTag;
    }
    public MessageTag(Long messageTagId, String messageText, String description, String messageTag, Set<DocumentCategory> documentCategories) {
       this.messageTagId = messageTagId;
       this.messageText = messageText;
       this.description = description;
       this.messageTag = messageTag;
       this.documentCategories = documentCategories;
    }
   
     @Id 

    
    @Column(name="MESSAGETAGID")
    public Long getMessageTagId() {
        return this.messageTagId;
    }
    
    public void setMessageTagId(Long messageTagId) {
        this.messageTagId = messageTagId;
    }

    
    @Column(name="MESSAGETEXT")
    public String getMessageText() {
        return this.messageText;
    }
    
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="MESSAGETAG")
    public String getMessageTag() {
        return this.messageTag;
    }
    
    public void setMessageTag(String messageTag) {
        this.messageTag = messageTag;
    }

@ManyToMany(fetch=FetchType.LAZY, mappedBy="messageTags")
    public Set<DocumentCategory> getDocumentCategories() {
        return this.documentCategories;
    }
    
    public void setDocumentCategories(Set<DocumentCategory> documentCategories) {
        this.documentCategories = documentCategories;
    }




}


