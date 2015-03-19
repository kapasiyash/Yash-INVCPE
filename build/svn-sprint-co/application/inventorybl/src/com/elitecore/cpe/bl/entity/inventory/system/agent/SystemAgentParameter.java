package com.elitecore.cpe.bl.entity.inventory.system.agent;


import static javax.persistence.GenerationType.SEQUENCE;

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

import com.elitecore.cpe.bl.entity.inventory.core.expr.ConstraintExpression;


@Entity
@Table(name="TBLMAGENTPARAMETER"
)
@NamedQueries({ 
	@NamedQuery(name = "SystemAgentParameter.findSystemParameters",query ="select o from SystemAgentParameter o where o.systemAgent.agentid = :agentid")
})
public class SystemAgentParameter  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long agentparamid;
     private ConstraintExpression constraintExpression;
     private SystemAgent systemAgent;
     private String name;
     private String alias;
     private String customfieldtypeid;
     private String valuesource;
     private Long sequencenumber;
     private String defaultValue;
     private Character isreadonly;

    public SystemAgentParameter() {
    }

	
    public SystemAgentParameter(SystemAgent systemAgent, String name, String alias, String customfieldtypeid, Long sequencenumber) {
        this.systemAgent = systemAgent;
        this.name = name;
        this.alias = alias;
        this.customfieldtypeid = customfieldtypeid;
        this.sequencenumber = sequencenumber;
    }
    public SystemAgentParameter(ConstraintExpression constraintexpression, SystemAgent systemAgent, String name, String alias, String customfieldtypeid, String valuesource, Long sequencenumber) {
       this.constraintExpression = constraintexpression;
       this.systemAgent = systemAgent;
       this.name = name;
       this.alias = alias;
       this.customfieldtypeid = customfieldtypeid;
       this.valuesource = valuesource;
       this.sequencenumber = sequencenumber;
    }
   
     @SequenceGenerator(name="generator", sequenceName="TBLMAGENTPARAMETER_SEQ")@Id @GeneratedValue(strategy=SEQUENCE, generator="generator")

    
    @Column(name="AGENTPARAMID")
    public Long getAgentparamid() {
        return this.agentparamid;
    }
    
    public void setAgentparamid(Long agentparamid) {
        this.agentparamid = agentparamid;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CONSTRAINTEXPRESSIONID")

        public ConstraintExpression getConstraintExpression() {
    	return constraintExpression;
    }


    public void setConstraintExpression(ConstraintExpression constraintExpression) {
    	this.constraintExpression = constraintExpression;
    }


@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AGENTID", nullable=false)
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

    
    @Column(name="ALIAS")
    public String getAlias() {
        return this.alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }

    
    @Column(name="CUSTOMFIELDTYPEID")
    public String getCustomfieldtypeid() {
        return this.customfieldtypeid;
    }
    
    public void setCustomfieldtypeid(String customfieldtypeid) {
        this.customfieldtypeid = customfieldtypeid;
    }

    
    @Column(name="VALUESOURCE")
    public String getValuesource() {
        return this.valuesource;
    }
    
    public void setValuesource(String valuesource) {
        this.valuesource = valuesource;
    }

    
    @Column(name="SEQUENCENUMBER")
    public Long getSequencenumber() {
        return this.sequencenumber;
    }
    
    public void setSequencenumber(Long sequencenumber) {
        this.sequencenumber = sequencenumber;
    }

    @Column(name="DEFAULTVALUE")
	public String getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}


	@Column(name="ISREADONLY")
	public Character getIsreadonly() {
		return isreadonly;
	}


	public void setIsreadonly(Character isreadonly) {
		this.isreadonly = isreadonly;
	}

    


}


