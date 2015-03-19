package com.elitecore.cpe.bl.entity.inventory.system.agent.schedule;


import static javax.persistence.GenerationType.SEQUENCE;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.elitecore.cpe.bl.entity.inventory.system.agent.ExecutionStatus;
import com.elitecore.cpe.bl.entity.inventory.system.agent.ExecutionType;
import com.elitecore.cpe.bl.entity.inventory.system.agent.SystemAgent;
import com.elitecore.cpe.bl.entity.inventory.system.agent.history.SystemAgentRunDetail;

@Entity
@Table(name="TBLMAGENTSCHEDULE"
)
@NamedQueries({ 
	@NamedQuery(name = "SystemAgentSchedule.findAllSystemAgentSchedule",query ="select o from SystemAgentSchedule o"),
	@NamedQuery(name = "SystemAgentSchedule.findSystemAgentScheduleById",query ="select o from SystemAgentSchedule o where o.agentscheduleid = :agentscheduleid"),
	@NamedQuery(name = "SystemAgentSchedule.findAgentScheduleByExecutionType",query ="select o from SystemAgentSchedule o where o.executionType.executiontypeid = :executiontypeid"),
	@NamedQuery(name = "SystemAgentSchedule.findAgentScheduleBySystemAgent",query ="select o from SystemAgentSchedule o where o.systemAgent.agentid = :systemAgent"),
	@NamedQuery(name = "SystemAgentSchedule.findSystemAgentSchedule", query = "select o from SystemAgentSchedule o where ((:name = '%') or (upper(o.name) like :name)) and o.status=1 and ((:executionStatus = '%') or (upper(o.executionStatus.executionstatusid) like :executionStatus)) "),
	@NamedQuery(name = "SystemAgentSchedule.findSystemAgentScheduleByUniqueName", query = "select o from SystemAgentSchedule o where upper(o.name) = :name"),
	@NamedQuery(name = "SystemAgentSchedule.searchSystemScheduleByDate",query ="select o from SystemAgentSchedule o where o.agentscheduleid = :agentscheduleid and o.executionStartDate between :start and :stop"),
	@NamedQuery(name = "SystemAgentSchedule.findAllActiveSchedule",query ="select o from SystemAgentSchedule o where o.status = :status  and ( o.requiredNumberofExecutions =-1 or o.requiredNumberofExecutions > 0 )")
	
})
public class SystemAgentSchedule  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long agentscheduleid;
     private ExecutionType executionType;
     private SystemAgent systemAgent;
     private String name;
     private String description;
     private Long priority;
     
     private Long requiredNumberofExecutions;
     private Timestamp executionStartDate;
     
     private Long exeperiodinmin;
     private Timestamp lastexecutiondate;
     private String reasonforschedule;
     private String executionstatusid;
     private String ownedby;
     private Timestamp createdate;
     private String createdby;
     private Timestamp lastmodifieddate;
     private String lastmodifiedby;
     private String schedulePattern;
//     private PolicyStatus scheduleStatus;
     private Long status;
     private Set<SystemAgentScheduleParameter> systemAgentScheduleParameters = new HashSet<SystemAgentScheduleParameter>(0);
     private Set<SystemAgentRunDetail> systemAgentRunDetails = new HashSet<SystemAgentRunDetail>(0);
//     private Set<SystemAgentRunQueue> systemAgentRunQueue= new HashSet<SystemAgentRunQueue>(0);
     private ExecutionStatus executionStatus;

    public SystemAgentSchedule() {
    }

	
    public SystemAgentSchedule(SystemAgent systemAgent, String name, Long priority, Long exeperiodinmin, String ownedby, Timestamp createdate, String createdby, String lastmodifiedby) {
        this.systemAgent = systemAgent;
        this.name = name;
        this.priority = priority;
        this.exeperiodinmin = exeperiodinmin;
        this.ownedby = ownedby;
        this.createdate = createdate;
        this.createdby = createdby;
        this.lastmodifiedby = lastmodifiedby;
    }
    public SystemAgentSchedule(ExecutionType executionType, SystemAgent systemAgent, String name, String description, Long priority, Long exeperiodinmin, Timestamp lastexecutiondate, String reasonforschedule, String executionstatusid, String ownedby, Timestamp createdate, String createdby, Timestamp lastmodifieddate, String lastmodifiedby, Set<SystemAgentScheduleParameter> systemAgentScheduleParameters, Set<SystemAgentRunDetail> systemAgentRunDetails) {
       this.executionType = executionType;
       this.systemAgent = systemAgent;
       this.name = name;
       this.description = description;
       this.priority = priority;
       this.exeperiodinmin = exeperiodinmin;
       this.lastexecutiondate = lastexecutiondate;
       this.reasonforschedule = reasonforschedule;
       this.executionstatusid = executionstatusid;
       this.ownedby = ownedby;
       this.createdate = createdate;
       this.createdby = createdby;
       this.lastmodifieddate = lastmodifieddate;
       this.lastmodifiedby = lastmodifiedby;
       this.systemAgentScheduleParameters = systemAgentScheduleParameters;
       this.systemAgentRunDetails = systemAgentRunDetails;
    }
   
     @SequenceGenerator(name="generator", sequenceName="TBLMAGENTSCHEDULE_SEQ")@Id @GeneratedValue(strategy=SEQUENCE, generator="generator")

    
    @Column(name="AGENTSCHEDULEID")
    public Long getAgentscheduleid() {
        return this.agentscheduleid;
    }
    
    public void setAgentscheduleid(Long agentscheduleid) {
        this.agentscheduleid = agentscheduleid;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EXECUTIONTYPEID")
    public ExecutionType getExecutionType() {
        return this.executionType;
    }
    
    public void setExecutionType(ExecutionType executionType) {
        this.executionType = executionType;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AGENTID")
    public SystemAgent getSystemAgent() {
        return this.systemAgent;
    }
    
    public void setSystemAgent(SystemAgent systemAgent) {
        this.systemAgent = systemAgent;
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

    
    @Column(name="PRIORITY")
    public Long getPriority() {
        return this.priority;
    }
    
    public void setPriority(Long priority) {
        this.priority = priority;
    }

    
    @Column(name="EXEPERIODINMIN")
    public Long getExeperiodinmin() {
        return this.exeperiodinmin;
    }
    
    public void setExeperiodinmin(Long exeperiodinmin) {
        this.exeperiodinmin = exeperiodinmin;
    }

    @Column(name="LASTEXECUTIONDATE")
    public Timestamp getLastexecutiondate() {
        return this.lastexecutiondate;
    }
    
    public void setLastexecutiondate(Timestamp lastexecutiondate) {
        this.lastexecutiondate = lastexecutiondate;
    }

    
    @Column(name="REASONFORSCHEDULE")
    public String getReasonforschedule() {
        return this.reasonforschedule;
    }
    
    public void setReasonforschedule(String reasonforschedule) {
        this.reasonforschedule = reasonforschedule;
    }

    
    

    @Column(name="REQUIREDNUMBEROFEXECUTIONS")
    public Long getRequiredNumberofExecutions() {
		return requiredNumberofExecutions;
	}


	public void setRequiredNumberofExecutions(Long requiredNumberofExecutions) {
		this.requiredNumberofExecutions = requiredNumberofExecutions;
	}


	@Column(name="EXECUTIONSTARTDATE")
	public Timestamp getExecutionStartDate() {
		return executionStartDate;
	}


	public void setExecutionStartDate(Timestamp executionStartDate) {
		this.executionStartDate = executionStartDate;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EXECUTIONSTATUSID",insertable=false,updatable=false)
	public ExecutionStatus getExecutionStatus(){
		return this.executionStatus; 
	}
	
	public void setExecutionStatus(ExecutionStatus executionStatus){
		this.executionStatus =executionStatus;
	}

	@Column(name="EXECUTIONSTATUSID")
    public String getExecutionstatusid() {
        return this.executionstatusid;
    }
    
    public void setExecutionstatusid(String executionstatusid) {
        this.executionstatusid = executionstatusid;
    }

	 @Column(name="STATUSID")
	public Long getStatus() {
		return status;
	}


	public void setStatus(Long status) {
		this.status = status;
	}

	
	/*@ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="STATUSID", insertable=false,updatable=false)
	public PolicyStatus getScheduleStatus() {
		return scheduleStatus;
	}


	public void setScheduleStatus(PolicyStatus scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}*/
    
    @Column(name="OWNEDBY")
    public String getOwnedby() {
        return this.ownedby;
    }
    
    public void setOwnedby(String ownedby) {
        this.ownedby = ownedby;
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

    @Column(name="LASTMODIFIEDDATE")
    public Timestamp getLastmodifieddate() {
        return this.lastmodifieddate;
    }
    
    public void setLastmodifieddate(Timestamp lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    
    @Column(name="LASTMODIFIEDBY")
    public String getLastmodifiedby() {
        return this.lastmodifiedby;
    }
    
    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, mappedBy="systemAgentSchedule")
    public Set<SystemAgentScheduleParameter> getSystemAgentScheduleParameters() {
        return this.systemAgentScheduleParameters;
    }
    
    public void setSystemAgentScheduleParameters(Set<SystemAgentScheduleParameter> systemAgentScheduleParameters) {
        this.systemAgentScheduleParameters = systemAgentScheduleParameters;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="systemAgentSchedule")
    public Set<SystemAgentRunDetail> getSystemAgentRunDetails() {
        return this.systemAgentRunDetails;
    }
    
    public void setSystemAgentRunDetails(Set<SystemAgentRunDetail> systemAgentRunDetails) {
        this.systemAgentRunDetails = systemAgentRunDetails;
    }

   

    @Column(name="SCHEDULEPATTERN")
	public String getSchedulePattern() {
		return schedulePattern;
	}


	public void setSchedulePattern(String schedulePattern) {
		this.schedulePattern = schedulePattern;
	}

	/*@OneToMany(fetch=FetchType.LAZY,mappedBy="systemAgentSchedule", cascade=CascadeType.REFRESH)
	public Set<SystemAgentRunQueue> getSystemAgentRunQueue() {
		return systemAgentRunQueue;
	}


	public void setSystemAgentRunQueue(Set<SystemAgentRunQueue> systemAgentRunQueue) {
		this.systemAgentRunQueue = systemAgentRunQueue;
	}
*/


	





/*
	private User userCreate;
	
	private User userModified;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "LASTMODIFIEDBY", insertable = false, updatable = false)   
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
	}
*/

}


