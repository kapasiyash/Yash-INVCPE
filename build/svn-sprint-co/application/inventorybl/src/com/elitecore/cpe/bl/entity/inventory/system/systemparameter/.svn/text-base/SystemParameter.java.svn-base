package com.elitecore.cpe.bl.entity.inventory.system.systemparameter;


import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.elitecore.cpe.bl.data.system.systemparameter.SystemParameterWrapperData;
import com.elitecore.cpe.bl.entity.inventory.core.expr.ConstraintExpression;


@Entity
@Table(name="TBLMSYSTEMPARAMETER"
)
@NamedQueries({ 
	@NamedQuery(name = "SystemParameter.findAllSystemParameter", query = "select o from SystemParameter o "),
	@NamedQuery(name = "SystemParameter.findSystemParameterByAlias", query = "select o from SystemParameter o where o.alias=:alias")
})
public class SystemParameter  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long systemParameterId;
//     private User user;
     private SystemParameterGroup systemParameterGroup;
     private SysPrmCustomFieldType sysPrmCustomFieldType;
     private String name;
     private String alias;
     private String value;
     private String valueSource;
     private String description;
     private char allowOverride;
     private Timestamp lastmodidate;
     private char isVisible;
	private ConstraintExpression constraintExpression;

    public SystemParameter() {
    }

	
    public SystemParameter(Long systemParameterId, String user, SystemParameterGroup systemParameterGroup, SysPrmCustomFieldType sysPrmCustomFieldType, String name, String alias, String value, char allowOverride, char isVisible) {
        this.systemParameterId = systemParameterId;
//        this.user = user;
        this.systemParameterGroup = systemParameterGroup;
        this.sysPrmCustomFieldType = sysPrmCustomFieldType;
        this.name = name;
        this.alias = alias;
        this.value = value;
        this.allowOverride = allowOverride;
        this.isVisible = isVisible;
    }
    public SystemParameter(Long systemParameterId, String user, SystemParameterGroup systemParameterGroup, SysPrmCustomFieldType sysPrmCustomFieldType, String name, String alias, String value, String valueSource, String description, char allowOverride, Timestamp lastmodidate, char isVisible) {
       this.systemParameterId = systemParameterId;
//       this.user = user;
       this.systemParameterGroup = systemParameterGroup;
       this.sysPrmCustomFieldType = sysPrmCustomFieldType;
       this.name = name;
       this.alias = alias;
       this.value = value;
       this.valueSource = valueSource;
       this.description = description;
       this.allowOverride = allowOverride;
       this.lastmodidate = lastmodidate;
       this.isVisible = isVisible;
    }
   
     @Id 

    
    @Column(name="SYSTEMPARAMETERID")
    public Long getSystemParameterId() {
        return this.systemParameterId;
    }
    
    public void setSystemParameterId(Long systemParameterId) {
        this.systemParameterId = systemParameterId;
    }

/*@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LASTMODIBY", nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
*/
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SYSTEMPARAMETERGROUPID", nullable=false)
    public SystemParameterGroup getSystemParameterGroup() {
        return this.systemParameterGroup;
    }
    
    public void setSystemParameterGroup(SystemParameterGroup systemParameterGroup) {
        this.systemParameterGroup = systemParameterGroup;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CUSTOMFIELDTYPEID", nullable=false)
    public SysPrmCustomFieldType getSysPrmCustomFieldType() {
        return this.sysPrmCustomFieldType;
    }
    
    public void setSysPrmCustomFieldType(SysPrmCustomFieldType sysPrmCustomFieldType) {
        this.sysPrmCustomFieldType = sysPrmCustomFieldType;
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

    
    @Column(name="VALUE")
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    
    @Column(name="VALUESOURCE")
    public String getValueSource() {
        return this.valueSource;
    }
    
    public void setValueSource(String valueSource) {
        this.valueSource = valueSource;
    }

    
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="ALLOWOVERRIDE")
    public char getAllowOverride() {
        return this.allowOverride;
    }
    
    public void setAllowOverride(char allowOverride) {
        this.allowOverride = allowOverride;
    }

    @Column(name="LASTMODIDATE")
    public Timestamp getLastmodidate() {
        return this.lastmodidate;
    }
    
    public void setLastmodidate(Timestamp lastmodidate) {
        this.lastmodidate = lastmodidate;
    }

    
    @Column(name="ISVISIBLE")
    public char getIsVisible() {
        return this.isVisible;
    }
    
    public void setIsVisible(char isVisible) {
        this.isVisible = isVisible;
    }

    
    @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
    @JoinColumn(name="CONSTRAINTEXPRESSIONID",insertable=false,updatable=false)
    public ConstraintExpression getConstraintExpression(){
    	return this.constraintExpression;
    }
    
    public void setConstraintExpression(ConstraintExpression constraintExpression){
    	this.constraintExpression=constraintExpression;
    }
    
 	public SystemParameterWrapperData prepareSystemParameterWrapperData() {
 		SystemParameterWrapperData systemParameterWrapperData = new SystemParameterWrapperData();
 		systemParameterWrapperData.setSystemParameterId(getSystemParameterId());
 		systemParameterWrapperData.setName(getName());
 		systemParameterWrapperData.setValue(getValue()); 		
 		systemParameterWrapperData.setLastmodidate(new Timestamp(getLastmodidate().getTime()));
 		systemParameterWrapperData.setIsVisible(getIsVisible());
 		systemParameterWrapperData.setDescription(getDescription());
 		systemParameterWrapperData.setAllowOverride(getAllowOverride());
 		systemParameterWrapperData.setAlias(getAlias());
 		if(getConstraintExpression()!=null){
 			systemParameterWrapperData.setRegEx(getConstraintExpression().getRegEx());
 		}
 		if(getSysPrmCustomFieldType()!=null){
 			systemParameterWrapperData.setCustomFieldTypeId(getSysPrmCustomFieldType().getCustomfieldtypeid());
 		}
 		return systemParameterWrapperData;
 	}
	


}


