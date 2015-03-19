package com.elitecore.cpe.bl.vo.system.agent;


/**
 * @author yash.kapasi
 *
 */
public class AgentServiceVO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean enable;
	
	public AgentServiceVO() {
		
	}
	
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
			this.enable = enable;
	}
}
