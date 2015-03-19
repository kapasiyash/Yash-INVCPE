package com.elitecore.cpe.bl.entity.inventory.core.configuration.notification;


import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Hemang Rami
 */
@Entity
@Table(name="TBLMMAILDOCUMENTTEMPLATEDETAIL"
)
@NamedQueries({
	@NamedQuery(name = "DocumentTemplateDetail.findDocumentTemplateDetailById", query = "select o from DocumentTemplateDetail o where o.documentTemplate.documentTemplateId = :documentTemplateId")
})
public class DocumentTemplateDetail  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long documenttemplatedetailid;
     private DocumentTemplate documentTemplate;
     private String subject;
     private  byte[] Template; // Replace it with blob. 
     private String mimeType;

    public DocumentTemplateDetail() {
    }

    public DocumentTemplateDetail(Long documenttemplatedetailid, DocumentTemplate documentTemplate, String subject,  byte[] Template, String mimeType) {
       this.documenttemplatedetailid = documenttemplatedetailid;
       this.documentTemplate = documentTemplate;
       this.subject = subject;
       this.Template = Template;
       this.mimeType = mimeType;
    }
     
     @SequenceGenerator(name="generator", sequenceName="SEQ_MAILDOCUMENTTEMPLATEDETAIL")
     @Id @GeneratedValue(strategy=SEQUENCE, generator="generator")
    @Column(name="DOCUMENTTEMPLATEDETAILID")
    public Long getDocumenttemplatedetailid() {
        return this.documenttemplatedetailid;
    }
    
    public void setDocumenttemplatedetailid(Long documenttemplatedetailid) {
        this.documenttemplatedetailid = documenttemplatedetailid;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DOCUMENTTEMPLATEID", nullable=false)
    public DocumentTemplate getDocumentTemplate() {
        return this.documentTemplate;
    }
    
    public void setDocumentTemplate(DocumentTemplate documentTemplate) {
        this.documentTemplate = documentTemplate;
    }

    
    @Column(name="SUBJECT")
    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    
    @Column(name="TEMPLATE")
    public byte[] getTemplate() {
        return this.Template;
    }
    
    public void setTemplate( byte[] Template) {
        this.Template = Template;
    }

    
    @Column(name="MIMETYPE")
    public String getMimeType() {
        return this.mimeType;
    }
    
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }


    


}


