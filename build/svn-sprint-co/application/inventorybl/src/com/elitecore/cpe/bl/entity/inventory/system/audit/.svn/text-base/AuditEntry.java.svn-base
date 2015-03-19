package com.elitecore.cpe.bl.entity.inventory.system.audit;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TBLIAUDITENTRY")
@NamedQueries({ 
	@NamedQuery(name = "AuditEntry.searchAuditEntryBysysId",query ="select o from AuditEntry o where o.systemAuditId = :systemAuditId")
})
@SequenceGenerator( name="TBLIAUDITENTRY_SEQ", sequenceName="TBLIAUDITENTRY_SEQ",allocationSize=1)
public class AuditEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="TBLIAUDITENTRY_SEQ")
	@Column(name = "AUDITENTRYID")
	private Long auditInfoId;
	
	@Column(name = "SYSTEMAUDITID", insertable=false, updatable=false)
	 private Long systemAuditId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SYSTEMAUDITID", nullable=false)
    private SystemAudit systemAudit;

    @Column(name = "TABLENAME")
    private String tableName;

    @Column(name = "FIELDNAME")
    private String fieldName;

    @Column(name = "OLDVALUE")
    private String oldValue;

    @Column(name = "NEWVALUE")
    private String newValue;

    
    public AuditEntry() {

    }
    
    /**
     *  Sets auditInfoId
     */
    public void setAuditInfoId( Long auditInfoId ) {
        this.auditInfoId = auditInfoId;
    }

    /**
     *  Returns auditInfoId
     */
    public Long getAuditInfoId() {
        return this.auditInfoId;
    }

    /**
     *  Sets systemAuditId
     */
    public void setSystemAuditId( Long systemAuditId ) {
        this.systemAuditId = systemAuditId;
    }

    /**
     *  Returns systemAuditId
     */
    public Long getSystemAuditId() {
        return this.systemAuditId;
    }

    /**
     *  Sets tableName
     */
    public void setTableName( String tableName ) {
        this.tableName = tableName;
    }

    /**
     *  Returns tableName
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     *  Sets fieldName
     */
    public void setFieldName( String fieldName ) {
        this.fieldName = fieldName;
    }

    /**
     *  Returns fieldName
     */
    public String getFieldName() {
        return this.fieldName;
    }

    /**
     *  Sets oldValue
     */
    public void setOldValue( String oldValue ) {
        this.oldValue = oldValue;
    }

    /**
     *  Returns oldValue
     */
    public String getOldValue() {
        return this.oldValue;
    }

    /**
     *  Sets newValue
     */
    public void setNewValue( String newValue ) {
        this.newValue = newValue;
    }

    /**
     *  Returns newValue
     */
    public String getNewValue() {
        return this.newValue;
    }

    public SystemAudit getSystemAudit() {
		return systemAudit;
	}

	public void setSystemAudit(SystemAudit systemAudit) {
		this.systemAudit = systemAudit;
	}

	/**
     *  Returns audit string  for this entity class. 
     */
    public String toAuditString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("AuditInfoId = ");
        buffer.append(auditInfoId);
        buffer.append("SystemAuditId = ");
        buffer.append(systemAuditId);
        buffer.append("TableName = ");
        buffer.append(tableName);
        buffer.append("FieldName = ");
        buffer.append(fieldName);
        buffer.append("OldValue = ");
        buffer.append(oldValue);
        buffer.append("NewValue = ");
        buffer.append(newValue);
        buffer.append("]");
        return buffer.toString();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[AuditInfo: ");
        buffer.append("AuditInfoId = ");
        buffer.append(auditInfoId);
        buffer.append("SystemAuditId = ");
        buffer.append(systemAuditId);
        buffer.append("TableName = ");
        buffer.append(tableName);
        buffer.append("FieldName = ");
        buffer.append(fieldName);
        buffer.append("OldValue = ");
        buffer.append(oldValue);
        buffer.append("NewValue = ");
        buffer.append(newValue);
        buffer.append("]");
        return buffer.toString();
    }
}
