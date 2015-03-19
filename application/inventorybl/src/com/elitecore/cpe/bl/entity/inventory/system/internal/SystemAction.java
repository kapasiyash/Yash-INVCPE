package com.elitecore.cpe.bl.entity.inventory.system.internal;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;





@Entity
@NamedQueries({ 
	@NamedQuery(name = "SystemAction.findById",query ="select o from SystemAction o where o.actionId = :actionId "),
	@NamedQuery(name = "SystemAction.findAll",query ="select o from SystemAction o"),
	@NamedQuery(name = "SystemAction.findByAlias",query ="select o from SystemAction o where o.actionAlias = :actionAlias ")
	
})

@Table(name="TBLISYSTEMACTION"
)
public class SystemAction  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long actionId;
//     private UserType userType;
     private SystemModule systemModules;
     private Long parentActionId;
     private Long sequenceNumber;
     private String name;
     private String actionAlias;
     private String description;
     private Character isAuditable;
     private Character enableaudit;
     private Character enableNotification;
     private String pageUrl;
	 private SystemAction parentAction;
	 private Character enableVisible;
	 private Set<SystemAction> childActions;

    public SystemAction() {
    }

	
    public SystemAction(Long actionId, SystemModule systemModules, Long sequenceNumber, String name, String actionAlias, String description, Character isAuditable, Character enableaudit, Character enableNotification) {
        this.actionId = actionId;
        this.systemModules = systemModules;
        this.sequenceNumber = sequenceNumber;
        this.name = name;
        this.actionAlias = actionAlias;
        this.description = description;
        this.isAuditable = isAuditable;
        this.enableaudit = enableaudit;
        this.enableNotification = enableNotification;
    }
    public SystemAction(Long actionId,  SystemModule systemModules,  Long parentActionId, Long sequenceNumber, String name, String actionAlias, String description, Character isAuditable, Character enableaudit, Character enableNotification, String pageUrl) {
       this.actionId = actionId;
//       this.userType = userType;
       this.systemModules = systemModules;
       this.parentActionId = parentActionId;
       this.sequenceNumber = sequenceNumber;
       this.name = name;
       this.actionAlias = actionAlias;
       this.description = description;
       this.isAuditable = isAuditable;
       this.enableaudit = enableaudit;
       this.enableNotification = enableNotification;
       this.pageUrl = pageUrl;
    }
   
     @Id 

    
    @Column(name="ACTIONID")
    public Long getActionId() {
        return this.actionId;
    }
    
    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

/*@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="USERTYPEID")
    public UserType getUserType() {
        return this.userType;
    }
    
    public void setUserType(UserType userType) {
        this.userType = userType;
    }*/

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MODULEID", nullable=false)
    public SystemModule getSystemModules() {
        return this.systemModules;
    }
    
    public void setSystemModules(SystemModule systemModules) {
        this.systemModules = systemModules;
    }

    
    @Column(name="PARENTACTIONID")
    public Long getParentActionId() {
        return this.parentActionId;
    }
    
    public void setParentActionId(Long parentActionId) {
        this.parentActionId = parentActionId;
    }

    
    
    @Column(name="SEQUENCENUMBER")
    public Long getSequenceNumber() {
        return this.sequenceNumber;
    }
    
    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    
    @Column(name="NAME")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="ACTIONALIAS", unique=true)
    public String getActionAlias() {
        return this.actionAlias;
    }
    
    public void setActionAlias(String actionAlias) {
        this.actionAlias = actionAlias;
    }

    
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="ISAUDITABLE")
    public Character getIsAuditable() {
        return this.isAuditable;
    }
    
    public void setIsAuditable(Character isAuditable) {
        this.isAuditable = isAuditable;
    }

    
    @Column(name="ENABLEAUDIT")
    public Character getEnableaudit() {
        return this.enableaudit;
    }
    
    public void setEnableaudit(Character enableaudit) {
        this.enableaudit = enableaudit;
    }

    
    @Column(name="ENABLENOTIFICATION")
    public Character getEnableNotification() {
        return this.enableNotification;
    }
    
    public void setEnableNotification(Character enableNotification) {
        this.enableNotification = enableNotification;
    }

    
    @Column(name="PAGEURL")
    public String getPageUrl() {
        return this.pageUrl;
    }
    
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    
   	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PARENTACTIONID", insertable = false, updatable = false)
   	public SystemAction getParentAction() {
   		return parentAction;
   	}

   	public void setParentAction(SystemAction parentAction) {
   		this.parentAction = parentAction;
   	}
   	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentAction")
	public Set<SystemAction> getChildActions() {
		return childActions;
	}

	public void setChildActions(Set<SystemAction> childActions) {
		this.childActions = childActions;
	}

	@Column(name="ENABLEVISIBLE")
	public Character getEnableVisible() {
		return enableVisible;
	}


	public void setEnableVisible(Character enableVisible) {
		this.enableVisible = enableVisible;
	}
	
	
}


