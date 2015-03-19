package com.elitecore.cpe.bl.entity.inventory.system.agent;


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

import com.elitecore.cpe.bl.entity.inventory.system.agent.schedule.SystemAgentSchedule;


@Entity
@Table(name="TBLSEXECUTIONTYPE"
)
@NamedQueries({ 
	@NamedQuery(name = "ExecutionType.findExecutionTypeById",query ="select o from ExecutionType o where o.executiontypeid = :executiontypeid")
})
public class ExecutionType  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String executiontypeid;
     private String name;
     private String alias;
     private String description;
     private Character systemgenerated;
     private Set<SystemAgent> systemAgents = new HashSet<SystemAgent>(0);
     private Set<SystemAgentSchedule> systemAgentSchedules = new HashSet<SystemAgentSchedule>(0);

    public ExecutionType() {
    }

	
    public ExecutionType(String executiontypeid, String name, String alias) {
        this.executiontypeid = executiontypeid;
        this.name = name;
        this.alias = alias;
    }
    public ExecutionType(String executiontypeid, String name, String alias, String description, Character systemgenerated, Set<SystemAgent> systemAgents, Set<SystemAgentSchedule> systemAgentSchedules) {
       this.executiontypeid = executiontypeid;
       this.name = name;
       this.alias = alias;
       this.description = description;
       this.systemgenerated = systemgenerated;
       this.systemAgents = systemAgents;
       this.systemAgentSchedules = systemAgentSchedules;
    }
   
     @Id 

    
    @Column(name="EXECUTIONTYPEID")
    public String getExecutiontypeid() {
        return this.executiontypeid;
    }
    
    public void setExecutiontypeid(String executiontypeid) {
        this.executiontypeid = executiontypeid;
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

    
    @Column(name="SYSTEMGENERATED")
    public Character getSystemgenerated() {
        return this.systemgenerated;
    }
    
    public void setSystemgenerated(Character systemgenerated) {
        this.systemgenerated = systemgenerated;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="executionType")
    public Set<SystemAgent> getSystemAgents() {
        return this.systemAgents;
    }
    
    public void setSystemAgents(Set<SystemAgent> systemAgents) {
        this.systemAgents = systemAgents;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="executionType")
    public Set<SystemAgentSchedule> getSystemAgentSchedules() {
        return this.systemAgentSchedules;
    }
    
    public void setSystemAgentSchedules(Set<SystemAgentSchedule> systemAgentSchedules) {
        this.systemAgentSchedules = systemAgentSchedules;
    }




}


