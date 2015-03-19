package com.elitecore.cpe.bl.agents.provider;

import com.elitecore.cpe.bl.agents.manager.AgentManager;
import com.elitecore.utility.agentframework.managers.IAgentManager;
import com.elitecore.utility.agentframework.providers.DefaultAgentManagerProvider;

public class AgentManagerProvider  extends DefaultAgentManagerProvider{
	public static final String MODULE="AGENT-MANAGER-PROVIDER";
	private IAgentManager localAgentManager;
	public AgentManagerProvider() {
		super();
		System.out.println("Initializing Agent Manager Provider");
	}

	public AgentManagerProvider(IAgentManager localAgentManager) {
		this.localAgentManager= localAgentManager;
	}
	
	
	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.providers.DefaultAgentManagerProvider#getAgentManager()
	 */
	@Override
	public IAgentManager getAgentManager() {
		if(this.localAgentManager== null){
			this.localAgentManager = new AgentManager();
		}
		return this.localAgentManager;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.providers.DefaultAgentManagerProvider#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return MODULE;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.providers.DefaultAgentManagerProvider#getProviderName()
	 */
	@Override
	public String getProviderName() {
		return MODULE;
	}
	
	
}
