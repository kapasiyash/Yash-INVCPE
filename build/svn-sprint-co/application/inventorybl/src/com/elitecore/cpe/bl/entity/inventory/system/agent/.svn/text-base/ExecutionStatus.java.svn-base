package com.elitecore.cpe.bl.entity.inventory.system.agent;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="TBLSEXECUTIONSTATUS")
@NamedQueries({ 
	@NamedQuery(name = "ExecutionStatus.findAllStatus",query ="select o from ExecutionStatus o")
})
public class ExecutionStatus  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String executionstatusid;
     private String name;
     private String alias;
     private String description;
     private Character systemgenerated;

    public ExecutionStatus() {
    }

	
    public ExecutionStatus(String executionstatusid, String name, String alias) {
        this.executionstatusid = executionstatusid;
        this.name = name;
        this.alias = alias;
    }
    public ExecutionStatus(String executionstatusid, String name, String alias, String description, Character systemgenerated) {
       this.executionstatusid = executionstatusid;
       this.name = name;
       this.alias = alias;
       this.description = description;
       this.systemgenerated = systemgenerated;
    }
   
     @Id 

    
    @Column(name="EXECUTIONSTATUSID")
    public String getExecutionstatusid() {
        return this.executionstatusid;
    }
    
    public void setExecutionstatusid(String executionstatusid) {
        this.executionstatusid = executionstatusid;
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




}


