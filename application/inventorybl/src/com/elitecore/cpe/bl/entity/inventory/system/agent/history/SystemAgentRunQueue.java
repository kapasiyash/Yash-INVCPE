package com.elitecore.cpe.bl.entity.inventory.system.agent.history;


import static javax.persistence.GenerationType.SEQUENCE;

import java.sql.Timestamp;

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

import com.elitecore.cpe.bl.entity.inventory.system.agent.ExecutionStatus;
import com.elitecore.cpe.bl.entity.inventory.system.agent.ExecutionType;
import com.elitecore.cpe.bl.entity.inventory.system.agent.SystemAgent;
import com.elitecore.cpe.bl.entity.inventory.system.agent.schedule.SystemAgentSchedule;


@Entity
@Table(name="TBLTAGENTRUNQUEUE")
@NamedQueries({ 
	@NamedQuery(name = "SystemAgentRunQueue.findAgentRunInQueue",query ="select o from SystemAgentRunQueue o where ((:agentName = '%') or (upper(o.systemAgent.name) like :agentName) or o.executionstatusid=:executionstatusid) and ((:agentScheduleName = '%') or (upper(o.systemAgentSchedule.name) like :agentScheduleName) or o.executionstatusid=:executionstatusid) or o.executionstatusid=:executionstatusid) "),
	@NamedQuery(name = "SystemAgentRunQueue.findAgentRunInQueueByName",query ="select o from SystemAgentRunQueue o where ((:agentName = '%') or (upper(o.systemAgent.name) like :agentName)) and ((:agentScheduleName = '%') or (upper(o.systemAgentSchedule.name) like :agentScheduleName))  "),
	@NamedQuery(name = "SystemAgentRunQueue.findAgentRunInQueueByStatus",query ="select o from SystemAgentRunQueue o where  o.executionstatusid=:executionstatusid "),
	@NamedQuery(name = "SystemAgentRunQueue.findAgentRunInQueueById",query ="select o from SystemAgentRunQueue o where o.agentrunqueueid=:agentrunqueueid"),
	@NamedQuery(name = "SystemAgentRunQueue.findByAgentSchedule",query ="select o from SystemAgentRunQueue o where o.agentscheduleid=:agentscheduleid "),
	@NamedQuery(name = "SystemAgentRunQueue.searchAgentRun",query ="select o from SystemAgentRunQueue o where ((:agentName = '%') or (upper(o.systemAgent.name) like :agentName))  and ((:agentScheduleName = '%') or (upper(o.systemAgentSchedule.name) like :agentScheduleName)) and ((:executionstatus = '%') or (upper(o.executionstatusid) like :executionstatus)) "),
	@NamedQuery(name = "SystemAgentRunQueue.findAll",query ="select o from SystemAgentRunQueue o ")
	
	
})
public class SystemAgentRunQueue  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long agentrunqueueid;
     private Long agentscheduleid;
     private SystemAgentSchedule systemAgentSchedule;
     private Timestamp executionduedatetime;
     private String agentid;
     private SystemAgent systemAgent;
     
     private ExecutionType executionType;
     private String executiontypeid;
     private Long priority;
     private Long agentrundetailid;
     private String executionstatusid;

     private ExecutionStatus executionStatus;
     
    public SystemAgentRunQueue() {
    }

    public SystemAgentRunQueue(Long agentscheduleid, Timestamp executionduedatetime, String agentid, String executiontypeid, Long priority,  String executionstatusid) {
       this.agentscheduleid = agentscheduleid;
       this.executionduedatetime = executionduedatetime;
       this.agentid = agentid;
       this.executiontypeid = executiontypeid;
       this.priority = priority;
       this.executionstatusid = executionstatusid;
    }
   
     @SequenceGenerator(name="generator", sequenceName="TBLTAGENTRUNQUEUE_SEQ")@Id @GeneratedValue(strategy=SEQUENCE, generator="generator")

    
    @Column(name="AGENTRUNQUEUEID")
    public Long getAgentrunqueueid() {
        return this.agentrunqueueid;
    }
    
    public void setAgentrunqueueid(Long agentrunqueueid) {
        this.agentrunqueueid = agentrunqueueid;
    }
    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AGENTID",insertable=false,updatable=false)
    public SystemAgent getSystemAgent() {
		return systemAgent;
	}

	public void setSystemAgent(SystemAgent systemAgent) {
		this.systemAgent = systemAgent;
	}

	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AGENTSCHEDULEID",insertable=false,updatable=false)
    public SystemAgentSchedule getSystemAgentSchedule() {
        return this.systemAgentSchedule;
    }
    
    public void setSystemAgentSchedule(SystemAgentSchedule systemAgentSchedule) {
        this.systemAgentSchedule = systemAgentSchedule;
    }

    @Column(name="AGENTSCHEDULEID")
    public Long getAgentscheduleid() {
        return this.agentscheduleid;
    }
    
    public void setAgentscheduleid(Long agentscheduleid) {
        this.agentscheduleid = agentscheduleid;
    }

    @Column(name="EXECUTIONDUEDATETIME")
    public Timestamp getExecutionduedatetime() {
        return this.executionduedatetime;
    }
    
    public void setExecutionduedatetime(Timestamp executionduedatetime) {
        this.executionduedatetime = executionduedatetime;
    }

    
    @Column(name="AGENTID")
    public String getAgentid() {
        return this.agentid;
    }
    
    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    
    @Column(name="EXECUTIONTYPEID")
    public String getExecutiontypeid() {
        return this.executiontypeid;
    }
    
    public void setExecutiontypeid(String executiontypeid) {
        this.executiontypeid = executiontypeid;
    }

    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EXECUTIONTYPEID",insertable=false,updatable=false)
    public ExecutionType getExecutionType() {
		return executionType;
	}

	public void setExecutionType(ExecutionType executionType) {
		this.executionType = executionType;
	}

	@Column(name="PRIORITY")
    public Long getPriority() {
        return this.priority;
    }
    
    public void setPriority(Long priority) {
        this.priority = priority;
    }

    
    @Column(name="AGENTRUNDETAILID")
    public Long getAgentrundetailid() {
        return this.agentrundetailid;
    }
    
    public void setAgentrundetailid(Long agentrundetailid) {
        this.agentrundetailid = agentrundetailid;
    }


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EXECUTIONSTATUSID",insertable=false,updatable=false)
    public ExecutionStatus getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(ExecutionStatus executionStatus) {
		this.executionStatus = executionStatus;
	}

	@Column(name="EXECUTIONSTATUSID")
    public String getExecutionstatusid() {
        return this.executionstatusid;
    }
    
    public void setExecutionstatusid(String executionstatusid) {
        this.executionstatusid = executionstatusid;
    }

	@Override
	public String toString() {
		return "SystemAgentRunQueue [agentrunqueueid=" + agentrunqueueid
				+ ", agentscheduleid=" + agentscheduleid
				+ ", executionduedatetime=" + executionduedatetime
				+ ", agentid=" + agentid + ", executiontypeid="
				+ executiontypeid + ", priority=" + priority
				+ ", agentrundetailid=" + agentrundetailid
				+ ", executionstatusid=" + executionstatusid + "]";
	}


    


}


