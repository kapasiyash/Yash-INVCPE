package com.elitecore.cpe.bl.vo.system.agent;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yash.kapasi
 *
 */
public class SystemAgentVO implements Serializable { 

    private static final long serialVersionUID = 1L;

    private String agentId;
    private String name;
    private String description;
    private String implClass;
    private String exeSchedule;
    private Timestamp createDate;
    private Timestamp lastExecutionTime;
    private String isVisible;
    private String isActive;


    public SystemAgentVO() {

    }
    

    /**
     *  Sets agentId
     */
    public void setAgentId( String agentId ) {
        this.agentId = agentId;
    }

    /**
     *  Returns agentId
     */
    public String getAgentId() {
        return this.agentId;
    }

    /**
     *  Sets name
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     *  Returns name
     */
    public String getName() {
        return this.name;
    }

    /**
     *  Sets description
     */
    public void setDescription( String description ) {
        this.description = description;
    }

    /**
     *  Returns description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     *  Sets implClass
     */
    public void setImplClass( String implClass ) {
        this.implClass = implClass;
    }

    /**
     *  Returns implClass
     */
    public String getImplClass() {
        return this.implClass;
    }

    /**
     *  Sets exeSchedule
     */
    public void setExeSchedule( String exeSchedule ) {
        this.exeSchedule = exeSchedule;
    }

    /**
     *  Returns exeSchedule
     */
    public String getExeSchedule() {
        return this.exeSchedule;
    }

    /**
     *  Sets createDate
     */
    public void setCreateDate( Timestamp createDate ) {
        this.createDate = createDate;
    }

    /**
     *  Returns createDate
     */
    public Timestamp getCreateDate() {
        return this.createDate;
    }

    /**
     *  Sets lastExecutionTime
     */
    public void setLastExecutionTime( Timestamp lastExecutionTime ) {
        this.lastExecutionTime = lastExecutionTime;
    }

    /**
     *  Returns lastExecutionTime
     */
    public Timestamp getLastExecutionTime() {
        return this.lastExecutionTime;
    }

    /**
     *  Sets isVisible
     */
    public void setIsVisible( String isVisible ) {
        this.isVisible = isVisible;
    }

    /**
     *  Returns isVisible
     */
    public String getIsVisible() {
        return this.isVisible;
    }

    /**
     *  Sets isActive
     */
    public void setIsActive( String isActive ) {
        this.isActive = isActive;
    }

    /**
     *  Returns isActive
     */
    public String getIsActive() {
        return this.isActive;
    }

    /**
     *  Returns audit string  for this entity class. 
     */
    public String toAuditString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("AgentId = ");
        buffer.append(agentId);
        buffer.append("Name = ");
        buffer.append(name);
        buffer.append("Description = ");
        buffer.append(description);
        buffer.append("ImplClass = ");
        buffer.append(implClass);
        buffer.append("ExeSchedule = ");
        buffer.append(exeSchedule);
        buffer.append("CreateDate = ");
        buffer.append(createDate);
        buffer.append("LastExecutionTime = ");
        buffer.append(lastExecutionTime);
        buffer.append("IsVisible = ");
        buffer.append(isVisible);
        buffer.append("IsActive = ");
        buffer.append(isActive);
        buffer.append("]");
        return buffer.toString();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[SystemAgent: ");
        buffer.append("AgentId = ");
        buffer.append(agentId);
        buffer.append("Name = ");
        buffer.append(name);
        buffer.append("Description = ");
        buffer.append(description);
        buffer.append("ImplClass = ");
        buffer.append(implClass);
        buffer.append("ExeSchedule = ");
        buffer.append(exeSchedule);
        buffer.append("CreateDate = ");
        buffer.append(createDate);
        buffer.append("LastExecutionTime = ");
        buffer.append(lastExecutionTime);
        buffer.append("IsVisible = ");
        buffer.append(isVisible);
        buffer.append("IsActive = ");
        buffer.append(isActive);
        buffer.append("]");
        return buffer.toString();
    }

}
