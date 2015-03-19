package com.elitecore.cpe.bl.entity.inventory.system.agent.schedule;


import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TBLMAGENTSCHEDUALPARAMVALUE"
)
public class SystemAgentScheduleParameter  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long scheduleparamvalueid;
     private SystemAgentSchedule systemAgentSchedule;
     private String parameteralias;
     private String textvalue;
     private Timestamp createdate;

    public SystemAgentScheduleParameter() {
    }

	
    public SystemAgentScheduleParameter(String parameteralias, String textvalue) {
        this.parameteralias = parameteralias;
        this.textvalue = textvalue;
    }
    public SystemAgentScheduleParameter(SystemAgentSchedule systemAgentSchedule, String parameteralias, String textvalue, Timestamp createdate) {
       this.systemAgentSchedule = systemAgentSchedule;
       this.parameteralias = parameteralias;
       this.textvalue = textvalue;
       this.createdate = createdate;
    }
   
     @SequenceGenerator(name="generator", sequenceName="AGENTSCHEDUALPARAMVALUE_SEQ")@Id @GeneratedValue(strategy=SEQUENCE, generator="generator")

    
    @Column(name="SCHEDULEPARAMVALUEID")
    public Long getScheduleparamvalueid() {
        return this.scheduleparamvalueid;
    }
    
    public void setScheduleparamvalueid(Long scheduleparamvalueid) {
        this.scheduleparamvalueid = scheduleparamvalueid;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AGENTSCHEDULEID")
    public SystemAgentSchedule getSystemAgentSchedule() {
        return this.systemAgentSchedule;
    }
    
    public void setSystemAgentSchedule(SystemAgentSchedule systemAgentSchedule) {
        this.systemAgentSchedule = systemAgentSchedule;
    }

    
    @Column(name="PARAMETERALIAS")
    public String getParameteralias() {
        return this.parameteralias;
    }
    
    public void setParameteralias(String parameteralias) {
        this.parameteralias = parameteralias;
    }

    
    @Column(name="TEXTVALUE")
    public String getTextvalue() {
        return this.textvalue;
    }
    
    public void setTextvalue(String textvalue) {
        this.textvalue = textvalue;
    }

    @Column(name="CREATEDATE")
    public Timestamp getCreatedate() {
        return this.createdate;
    }
    
    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }




}


