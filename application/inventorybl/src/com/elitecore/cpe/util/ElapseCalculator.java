package com.elitecore.cpe.util;

/**
 * 
 * Calculate Elapse time using this class. 
 *
 * @author yash.kapasi
 *
 */
public class ElapseCalculator {

	private long starttime;

	{
		starttime = System.currentTimeMillis();
	}
	
	public long getElapse(){
		return System.currentTimeMillis()-starttime;
	}
	
	public String getElapseMessage(){
		return String.format("elapse time : %d ms", getElapse());
	}

}
