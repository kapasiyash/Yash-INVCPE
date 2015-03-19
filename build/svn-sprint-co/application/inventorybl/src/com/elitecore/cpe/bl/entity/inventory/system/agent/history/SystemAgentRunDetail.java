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

import com.elitecore.cpe.bl.entity.inventory.system.agent.SystemAgent;
import com.elitecore.cpe.bl.entity.inventory.system.agent.schedule.SystemAgentSchedule;

@Entity
@Table(name="TBLTAGENTRUNDETAIL")
@NamedQueries({ 
	@NamedQuery(name = "SystemAgentRunDetail.findAllSystemAgentRunDetail",query ="select o from SystemAgentRunDetail o "),
	@NamedQuery(name = "SystemAgentRunDetail.findSystemAgentRunDetailById",query ="select o from SystemAgentRunDetail o where o.agentrundetailid = :agentrundetailid"),
	@NamedQuery(name = "SystemAgentRunDetail.findAgentRunDetailBySystemAgent",query ="select o from SystemAgentRunDetail o where o.systemAgent.agentid = :systemAgent"),
	@NamedQuery(name = "SystemAgentRunDetail.findAgentRunDetailBySystemAgentSchedule",query ="select o from SystemAgentRunDetail o where o.systemAgentSchedule.agentscheduleid = :systemAgentSchedule"),
	@NamedQuery(name = "SystemAgentRunDetail.findAgentRunDetailBySystemAgentScheduleByDate",query ="select o from SystemAgentRunDetail o where o.systemAgentSchedule.agentscheduleid = :systemAgentSchedule and o.executionstartdate>=:executionstartdate and o.executionstopdate<=:executionstopdate")
	
})
public class SystemAgentRunDetail  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long agentrundetailid;
     private SystemAgent systemAgent;
     private String systemAgentId;
     private SystemAgentSchedule systemAgentSchedule;
     private Long systemAgentScheduleId;
     private String executiontypeid;
     private Long priority;
     private Timestamp executionstartdate;
     private Timestamp executionstopdate;
     private Long totalmasterentitieprocessed;
     private Long totalentitiesprocessed;
     private Long totalsuccessfulentities;
     private String parameterdetail;
     private String executionstatusid;
     private Long totalsuccessfulmasterentities;
     private String remarks;

    public SystemAgentRunDetail() {
    }


    
    
     public SystemAgentRunDetail( String systemAgentId,
			Long systemAgentScheduleId, String executiontypeid,
			Long priority, Timestamp executionstartdate,
			Timestamp executionstopdate, Long totalmasterentitieprocessed,
			Long totalentitiesprocessed, Long totalsuccessfulentities,
			String parameterdetail, String executionstatusid,
			Long totalsuccessfulmasterentities, String remarks) {
		super();
		this.systemAgentId = systemAgentId;
		this.systemAgentScheduleId = systemAgentScheduleId;
		this.executiontypeid = executiontypeid;
		this.priority = priority;
		this.executionstartdate = executionstartdate;
		this.executionstopdate = executionstopdate;
		this.totalmasterentitieprocessed = totalmasterentitieprocessed;
		this.totalentitiesprocessed = totalentitiesprocessed;
		this.totalsuccessfulentities = totalsuccessfulentities;
		this.parameterdetail = parameterdetail;
		this.executionstatusid = executionstatusid;
		this.totalsuccessfulmasterentities = totalsuccessfulmasterentities;
		this.remarks = remarks;
	}




	@SequenceGenerator(name="generator", sequenceName="TBLTAGENTRUNDETAIL_SEQ")@Id @GeneratedValue(strategy=SEQUENCE, generator="generator")

    
    @Column(name="AGENTRUNDETAILID")
    public Long getAgentrundetailid() {
        return this.agentrundetailid;
    }
    
    public void setAgentrundetailid(Long agentrundetailid) {
        this.agentrundetailid = agentrundetailid;
    }

    
    @Column(name="AGENTID")
    public String getSystemAgentId() {
		return systemAgentId;
	}

	public void setSystemAgentId(String systemAgentId) {
		this.systemAgentId = systemAgentId;
	}

	  @Column(name="AGENTSCHEDULEID")
	public Long getSystemAgentScheduleId() {
		return systemAgentScheduleId;
	}

	public void setSystemAgentScheduleId(Long systemAgentScheduleId) {
		this.systemAgentScheduleId = systemAgentScheduleId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AGENTID",insertable=false,updatable=false)
    public SystemAgent getSystemAgent() {
        return this.systemAgent;
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

    
    @Column(name="EXECUTIONTYPEID")
    public String getExecutiontypeid() {
        return this.executiontypeid;
    }
    
    public void setExecutiontypeid(String executiontypeid) {
        this.executiontypeid = executiontypeid;
    }

    
    @Column(name="PRIORITY")
    public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}


    

    @Column(name="EXECUTIONSTARTDATE")
    public Timestamp getExecutionstartdate() {
        return this.executionstartdate;
    }
    
    


	public void setExecutionstartdate(Timestamp executionstartdate) {
        this.executionstartdate = executionstartdate;
    }

    @Column(name="EXECUTIONSTOPDATE")
    public Timestamp getExecutionstopdate() {
        return this.executionstopdate;
    }
    
    public void setExecutionstopdate(Timestamp executionstopdate) {
        this.executionstopdate = executionstopdate;
    }

    
    @Column(name="TOTALMASTERENTITIEPROCESSED")
    public Long getTotalmasterentitieprocessed() {
        return this.totalmasterentitieprocessed;
    }
    
    public void setTotalmasterentitieprocessed(Long totalmasterentitieprocessed) {
        this.totalmasterentitieprocessed = totalmasterentitieprocessed;
    }

    
    @Column(name="TOTALENTITIESPROCESSED")
    public Long getTotalentitiesprocessed() {
        return this.totalentitiesprocessed;
    }
    
    public void setTotalentitiesprocessed(Long totalentitiesprocessed) {
        this.totalentitiesprocessed = totalentitiesprocessed;
    }

    
    @Column(name="TOTALSUCCESSFULENTITIES")
    public Long getTotalsuccessfulentities() {
        return this.totalsuccessfulentities;
    }
    
    public void setTotalsuccessfulentities(Long totalsuccessfulentities) {
        this.totalsuccessfulentities = totalsuccessfulentities;
    }

    
    @Column(name="PARAMETERDETAIL")
    public String getParameterdetail() {
        return this.parameterdetail;
    }
    
    public void setParameterdetail(String parameterdetail) {
        this.parameterdetail = parameterdetail;
    }

    
    @Column(name="EXECUTIONSTATUSID")
    public String getExecutionstatusid() {
        return this.executionstatusid;
    }
    
    public void setExecutionstatusid(String executionstatusid) {
        this.executionstatusid = executionstatusid;
    }

    
    @Column(name="TOTALSUCCESSFULMASTERENTITIES")
    public Long getTotalsuccessfulmasterentities() {
        return this.totalsuccessfulmasterentities;
    }
    
    public void setTotalsuccessfulmasterentities(Long totalsuccessfulmasterentities) {
        this.totalsuccessfulmasterentities = totalsuccessfulmasterentities;
    }

    
    @Column(name="REMARKS")
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }




	@Override
	public String toString() {
		return "SystemAgentRunDetail [agentrundetailid=" + agentrundetailid
				+ ", systemAgentId=" + systemAgentId
				+ ", systemAgentScheduleId=" + systemAgentScheduleId
				+ ", executiontypeid=" + executiontypeid + ", priority="
				+ priority + ", executionstartdate=" + executionstartdate
				+ ", executionstopdate=" + executionstopdate
				+ ", totalmasterentitieprocessed="
				+ totalmasterentitieprocessed + ", totalentitiesprocessed="
				+ totalentitiesprocessed + ", totalsuccessfulentities="
				+ totalsuccessfulentities + ", parameterdetail="
				+ parameterdetail + ", executionstatusid=" + executionstatusid
				+ ", totalsuccessfulmasterentities="
				+ totalsuccessfulmasterentities + ", remarks=" + remarks + "]";
	}




    
}


