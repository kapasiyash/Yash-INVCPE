package com.elitecore.cpe.bl.entity.inventory.core;


import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="TBLMPRIMARYKEY"
)

@NamedQueries({
	@NamedQuery(name = "PrimaryKey.findByAlias", query = "select o from PrimaryKey o where o.alias = :alias")
})
public class PrimaryKey  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long primaryKeyId;
     private String alias;
     private String preFix;
     private Long length;
     private String postFix;
     private Long currentValue;

    public PrimaryKey() {
    }

	
    public PrimaryKey(String alias, Long length, Long currentValue) {
        this.alias = alias;
        this.length = length;
        this.currentValue = currentValue;
    }
    public PrimaryKey(String alias, String preFix, Long length, String postFix, Long currentValue) {
       this.alias = alias;
       this.preFix = preFix;
       this.length = length;
       this.postFix = postFix;
       this.currentValue = currentValue;
    }
   
    
    @SequenceGenerator(name = "generator", sequenceName = "TBLMPRIMARYKEY_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @Column(name="PRIMARYKEYID")
    public Long getPrimaryKeyId() {
        return this.primaryKeyId;
    }
    
    


	public void setPrimaryKeyId(Long primaryKeyId) {
        this.primaryKeyId = primaryKeyId;
    }

    
    @Column(name="ALIAS", unique=true)
    public String getAlias() {
        return this.alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }

    
    @Column(name="PREFIX")
    public String getPreFix() {
        return this.preFix;
    }
    
    public void setPreFix(String preFix) {
        this.preFix = preFix;
    }

    
    @Column(name="LENGTH")
    public Long getLength() {
        return this.length;
    }
    
    public void setLength(Long length) {
        this.length = length;
    }

    
    @Column(name="POSTFIX")
    public String getPostFix() {
        return this.postFix;
    }
    
    public void setPostFix(String postFix) {
        this.postFix = postFix;
    }

    
    @Column(name="CURRENTVALUE")
    public Long getCurrentValue() {
        return this.currentValue;
    }
    
    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }


    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PrimaryKey [primaryKeyId=" + primaryKeyId + ", alias=" + alias
				+ ", preFix=" + preFix + ", length=" + length + ", postFix="
				+ postFix + ", currentValue=" + currentValue + "]";
	}

}


