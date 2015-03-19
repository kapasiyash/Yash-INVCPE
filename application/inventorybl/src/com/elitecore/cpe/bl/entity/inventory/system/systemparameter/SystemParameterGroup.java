package com.elitecore.cpe.bl.entity.inventory.system.systemparameter;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Yash.Kapasi
 *
 */
@Entity
@Table(name="TBLMSYSTEMPARAMETERGROUP"
)
@NamedQueries({
	@NamedQuery(name = "SystemParameterGroup.findAllSystemParameterGroup", query = "select o from SystemParameterGroup o ")
})
public class SystemParameterGroup  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long systemParameterGroupId;
     private String name;
     private String alias;
     private String description;
     private char isVisible;
     private Set<SystemParameter> systemParameters = new HashSet<SystemParameter>(0);

    public SystemParameterGroup() {
    }

	
    public SystemParameterGroup(Long systemParameterGroupId, String name, String alias, char isVisible) {
        this.systemParameterGroupId = systemParameterGroupId;
        this.name = name;
        this.alias = alias;
        this.isVisible = isVisible;
    }
    public SystemParameterGroup(Long systemParameterGroupId, String name, String alias, String description, char isVisible, Set<SystemParameter> systemParameters) {
       this.systemParameterGroupId = systemParameterGroupId;
       this.name = name;
       this.alias = alias;
       this.description = description;
       this.isVisible = isVisible;
       this.systemParameters = systemParameters;
    }
   
     @Id 

    
    @Column(name="SYSTEMPARAMETERGROUPID")
    public Long getSystemParameterGroupId() {
        return this.systemParameterGroupId;
    }
    
    public void setSystemParameterGroupId(Long systemParameterGroupId) {
        this.systemParameterGroupId = systemParameterGroupId;
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

    
    @Column(name="ISVISIBLE")
    public char getIsVisible() {
        return this.isVisible;
    }
    
    public void setIsVisible(char isVisible) {
        this.isVisible = isVisible;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="systemParameterGroup")
    public Set<SystemParameter> getSystemParameters() {
        return this.systemParameters;
    }
    
    public void setSystemParameters(Set<SystemParameter> systemParameters) {
        this.systemParameters = systemParameters;
    }




}


