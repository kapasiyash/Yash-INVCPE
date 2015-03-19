package com.elitecore.cpe.bl.ws.data.util;

import java.io.Serializable;

public class WsUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static final String STATUS_NEW="New";
	public static final String STATUS_INUSE="Allocated";

	
	public static final String RESERVE="Reserve";
	public static final String ALLOCATE="Allocate";
	
	public static final Integer RESERVE_INT=1;
	public static final Integer ALLOCATE_INT=2;
	
	public static final String RELEASE="Release";
	public static final Integer RELEASE_INT=1;


}
