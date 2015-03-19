package com.elitecore.cpe.core;

import java.io.Serializable;

/**
 * @author yash.kapasi
 *
 */
public interface IBDSessionContext extends Serializable {
	
	public IBLSession getBLSession();
	public SessionAccessControl getSessionAccessControl();	
	
}
