package com.elitecore.cpe.bl.entity.inventory.system.systemparameter;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="TBLSSYSPRMCUSTOMFIELDTYPE"
)
public class SysPrmCustomFieldType  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customfieldtypeid;
     private String name;
     private String alias;
     private String description;
     private Set<SystemParameter> systemParameters = new HashSet<SystemParameter>(0);

    public SysPrmCustomFieldType() {
    }

	
    public SysPrmCustomFieldType(String customfieldtypeid, String name, String alias, String description) {
        this.customfieldtypeid = customfieldtypeid;
        this.name = name;
        this.alias = alias;
        this.description = description;
    }
    public SysPrmCustomFieldType(String customfieldtypeid, String name, String alias, String description, Set<SystemParameter> systemParameters) {
       this.customfieldtypeid = customfieldtypeid;
       this.name = name;
       this.alias = alias;
       this.description = description;
       this.systemParameters = systemParameters;
    }
   
     @Id 

    
    @Column(name="CUSTOMFIELDTYPEID")
    public String getCustomfieldtypeid() {
        return this.customfieldtypeid;
    }
    
    public void setCustomfieldtypeid(String customfieldtypeid) {
        this.customfieldtypeid = customfieldtypeid;
    }

    
    @Column(name="NAME")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="ALIAS")
    public String getAlias() {
        return this.alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }

    
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="sysPrmCustomFieldType")
    public Set<SystemParameter> getSystemParameters() {
        return this.systemParameters;
    }
    
    public void setSystemParameters(Set<SystemParameter> systemParameters) {
        this.systemParameters = systemParameters;
    }




}


