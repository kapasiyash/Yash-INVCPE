package com.elitecore.cpe.bl.entity.inventory.system.agent;


import java.sql.Timestamp;
import java.util.HashSet;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.elitecore.cpe.bl.entity.inventory.system.agent.history.SystemAgentRunDetail;
import com.elitecore.cpe.bl.entity.inventory.system.agent.schedule.SystemAgentSchedule;


@Entity
@Table(name="TBLMAGENT"
)
@NamedQueries({ 
	@NamedQuery(name = "SystemAgent.findAllSystemAgent",query ="select o from SystemAgent o where o.isSchedulable='Y'"),
	@NamedQuery(name = "SystemAgent.findSystemAgentByName",query ="select o from SystemAgent o where ((:name = '%') or (upper(o.name) like :name))"),
	@NamedQuery(name = "SystemAgent.findSystemAgentById",query ="select o from SystemAgent o where o.agentid = :agentid"),
	@NamedQuery(name = "SystemAgent.findSystemAgentByExecutionType",query ="select o from SystemAgent o where o.executionType.executiontypeid = :executiontypeid")
})
public class SystemAgent  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String agentid;
     private ExecutionType executionType;
     private String name;
     private String description;
     private String implclass;
     private Long exeperiodinmin;
     private Long priority;
     private Character isparameterrequired;
     private String commonstatusid;
     private Character isvisible;
     private Character isactive;
     private Timestamp createdate;
     private String createdby;
     private Timestamp lastmodidate;
     private String lastmodiby;
     private Timestamp lastexetime;
     private Character isSchedulable;
    
     private Set<SystemAgentParameter> systemAgentParameters = new HashSet<SystemAgentParameter>(0);
     private Set<SystemAgentSchedule> systemAgentSchedules = new HashSet<SystemAgentSchedule>(0);
     private Set<SystemAgentRunDetail> systemAgentRunDetails = new HashSet<SystemAgentRunDetail>(0);
     private AgentParam agentParam ;

    public SystemAgent() {
    }

	
    public SystemAgent(String agentid, ExecutionType executionType, String name, String implclass, Long exeperiodinmin, Long priority, Character isvisible, Character isactive) {
        this.agentid = agentid;
        this.executionType = executionType;
        this.name = name;
        this.implclass = implclass;
        this.exeperiodinmin = exeperiodinmin;
        this.priority = priority;
        this.isvisible = isvisible;
        this.isactive = isactive;
    }
    public SystemAgent(String agentid, ExecutionType executionType, String name, String description, String implclass, Long exeperiodinmin, Long priority, Character isparameterrequired, String commonstatusid, Character isvisible, Character isactive, Timestamp createdate, String createdby, Timestamp lastmodidate, String lastmodiby, Timestamp lastexetime, Set<SystemAgentParameter> systemAgentParameters, Set<SystemAgentSchedule> systemAgentSchedules, Set<SystemAgentRunDetail> systemAgentRunDetails) {
       this.agentid = agentid;
       this.executionType = executionType;
       this.name = name;
       this.description = description;
       this.implclass = implclass;
       this.exeperiodinmin = exeperiodinmin;
       this.priority = priority;
       this.isparameterrequired = isparameterrequired;
       this.commonstatusid = commonstatusid;
       this.isvisible = isvisible;
       this.isactive = isactive;
       this.createdate = createdate;
       this.createdby = createdby;
       this.lastmodidate = lastmodidate;
       this.lastmodiby = lastmodiby;
       this.lastexetime = lastexetime;
       this.systemAgentParameters = systemAgentParameters;
       this.systemAgentSchedules = systemAgentSchedules;
       this.systemAgentRunDetails = systemAgentRunDetails;
    }
   
     @Id 

    
    @Column(name="AGENTID")
    public String getAgentid() {
        return this.agentid;
    }
    
    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EXECUTIONTYPEID", nullable=false)
    public ExecutionType getExecutionType() {
        return this.executionType;
    }
    
    public void setExecutionType(ExecutionType executionType) {
        this.executionType = executionType;
    }

    
    @Column(name="NAME")
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

    
    @Column(name="IMPLCLASS")
    public String getImplclass() {
        return this.implclass;
    }
    
    public void setImplclass(String implclass) {
        this.implclass = implclass;
    }

    
    @Column(name="EXEPERIODINMIN")
    public Long getExeperiodinmin() {
        return this.exeperiodinmin;
    }
    
    public void setExeperiodinmin(Long exeperiodinmin) {
        this.exeperiodinmin = exeperiodinmin;
    }

    
    @Column(name="PRIORITY")
    public Long getPriority() {
        return this.priority;
    }
    
    public void setPriority(Long priority) {
        this.priority = priority;
    }

    
    @Column(name="ISPARAMETERREQUIRED")
    public Character getIsparameterrequired() {
        return this.isparameterrequired;
    }
    
    public void setIsparameterrequired(Character isparameterrequired) {
        this.isparameterrequired = isparameterrequired;
    }

    
    @Column(name="COMMONSTATUSID")
    public String getCommonstatusid() {
        return this.commonstatusid;
    }
    
    public void setCommonstatusid(String commonstatusid) {
        this.commonstatusid = commonstatusid;
    }

    
    @Column(name="ISVISIBLE")
    public Character getIsvisible() {
        return this.isvisible;
    }
    
    public void setIsvisible(Character isvisible) {
        this.isvisible = isvisible;
    }

    
    @Column(name="ISACTIVE")
    public Character getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(Character isactive) {
        this.isactive = isactive;
    }

    @Column(name="CREATEDATE")
    public Timestamp getCreatedate() {
        return this.createdate;
    }
    
    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    
    @Column(name="CREATEDBY")
    public String getCreatedby() {
        return this.createdby;
    }
    
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    @Column(name="LASTMODIDATE")
    public Timestamp getLastmodidate() {
        return this.lastmodidate;
    }
    
    public void setLastmodidate(Timestamp lastmodidate) {
        this.lastmodidate = lastmodidate;
    }

    
    @Column(name="LASTMODIBY")
    public String getLastmodiby() {
        return this.lastmodiby;
    }
    
    public void setLastmodiby(String lastmodiby) {
        this.lastmodiby = lastmodiby;
    }

    @Column(name="LASTEXETIME")
    public Timestamp getLastexetime() {
        return this.lastexetime;
    }
    
    public void setLastexetime(Timestamp lastexetime) {
        this.lastexetime = lastexetime;
    }

    
    @Column(name="ISSCHEDULABLE")
    public Character getIsSchedulable() {
		return isSchedulable;
	}


	public void setIsSchedulable(Character isSchedulable) {
		this.isSchedulable = isSchedulable;
	}

	
	



@OneToMany(fetch=FetchType.LAZY, mappedBy="systemAgent")
    public Set<SystemAgentParameter> getSystemAgentParameters() {
        return this.systemAgentParameters;
    }
    
    public void setSystemAgentParameters(Set<SystemAgentParameter> systemAgentParameters) {
        this.systemAgentParameters = systemAgentParameters;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="systemAgent")
    public Set<SystemAgentSchedule> getSystemAgentSchedules() {
        return this.systemAgentSchedules;
    }
    
    public void setSystemAgentSchedules(Set<SystemAgentSchedule> systemAgentSchedules) {
        this.systemAgentSchedules = systemAgentSchedules;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="systemAgent")
    public Set<SystemAgentRunDetail> getSystemAgentRunDetails() {
        return this.systemAgentRunDetails;
    }
    
    public void setSystemAgentRunDetails(Set<SystemAgentRunDetail> systemAgentRunDetails) {
        this.systemAgentRunDetails = systemAgentRunDetails;
    }
    
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AGENTID")
    public AgentParam getAgentParams() {
		return agentParam;
	}


	public void setAgentParams(AgentParam agentParams) {
		this.agentParam = agentParams;
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
	}*/


}


