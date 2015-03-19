package com.elitecore.cpe.bl.session.system.systemparameter;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.elitecore.cpe.bl.entity.inventory.system.systemparameter.SystemParameter;
import com.elitecore.cpe.bl.entity.inventory.system.systemparameter.SystemParameterGroup;
import com.elitecore.cpe.bl.exception.SearchBLException;


@Local
public interface SystemParameterSessionBeanLocal extends Serializable {
	
	public List<SystemParameter> findAllSystemParameter() throws SearchBLException;
	public List<SystemParameterGroup> findAllSystemParameterGroup() throws SearchBLException;
	public SystemParameter findSystemParameterById(Long systemParameterId) throws SearchBLException;
	public SystemParameter updateSystemParameter(SystemParameter systemParameter) throws SearchBLException;
	public List<Object[]> findValueSource(String valueSource) throws SearchBLException;

	public SystemParameter getSystemParameter(String systemParameterAlias)	throws SearchBLException;
	
}
