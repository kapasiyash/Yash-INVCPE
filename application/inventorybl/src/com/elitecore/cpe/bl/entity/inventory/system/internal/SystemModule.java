package com.elitecore.cpe.bl.entity.inventory.system.internal;


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


@Entity
@Table(name="TBLISYSTEMMODULES"
)
@NamedQueries({
	@NamedQuery(name = "SystemModule.findAll", query = "select o from SystemModule o where o.moduleId >= 0"),
	@NamedQuery(name = "SystemModule.findById", query = "select o from SystemModule o where o.moduleId =:moduleId")
})
public class SystemModule  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long moduleId;
//     private SystemModuleGroup systemModuleGroup;
     private String name;
     private Long sequenceNumber;
     private String alias;
     private String description;
     private String panel;
     
     private Set<SystemAction> systemActions = new HashSet<SystemAction>(0);

    public SystemModule() {
    }

	
    public SystemModule(Long moduleId,  String name, Long sequenceNumber, String alias, String description) {
        this.moduleId = moduleId;
      
        this.name = name;
        this.sequenceNumber = sequenceNumber;
        this.alias = alias;
        this.description = description;
    }
    public SystemModule(Long moduleId,  String name, Long sequenceNumber, String alias, String description, Set<SystemAction> systemActions) {
       this.moduleId = moduleId;
       
       this.name = name;
       this.sequenceNumber = sequenceNumber;
       this.alias = alias;
       this.description = description;
       this.systemActions = systemActions;
    }
   
     @Id 
    @Column(name="MODULEID")
    public Long getModuleId() {
        return this.moduleId;
    }
    
    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

/*@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MODULEGROUPID", nullable=false)
    public SystemModuleGroup getSystemModuleGroup() {
        return this.systemModuleGroup;
    }
    
    public void setSystemModuleGroup(SystemModuleGroup systemModuleGroup) {
        this.systemModuleGroup = systemModuleGroup;
    }*/

    
    @Column(name="NAME")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="SEQUENCENUMBER")
    public Long getSequenceNumber() {
        return this.sequenceNumber;
    }
    
    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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
    
    @Column(name="PANEL")
    public String getPanel() {
		return panel;
	}


	public void setPanel(String panel) {
		this.panel = panel;
	}


	@OneToMany(fetch=FetchType.LAZY, mappedBy="systemModules")
    public Set<SystemAction> getSystemActions() {
        return this.systemActions;
    }
    
    public void setSystemActions(Set<SystemAction> systemActions) {
        this.systemActions = systemActions;
    }




}


