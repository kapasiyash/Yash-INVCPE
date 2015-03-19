package com.elitecore.cpe.core;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;

/**
 * @author yash.kapasi
 *
 */
public interface IBLSession extends Serializable {
	
	public String getSessionUserId();
	
	public String getName();

	public String getUsername();

	public String getUserType();

	public String getSessionId() ;

	public String getIpAddress() ;
	
	public boolean isPermittedAction(String actionAlias);
	
	public SystemActionData getSystemAction(String actionAlias);
	
	public List<SystemActionData> getPermittedAction();

	public String getPassword();
	
	public Set<Long> getUserWarehouseMappings();
	
}
