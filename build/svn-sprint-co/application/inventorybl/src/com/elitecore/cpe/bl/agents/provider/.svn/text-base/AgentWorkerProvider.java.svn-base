package com.elitecore.cpe.bl.agents.provider;

import com.elitecore.cpe.bl.agents.AgentInitializationException;
import com.elitecore.cpe.bl.agents.worker.AgentWorker;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.utility.agentframework.IAgentSchedule;
import com.elitecore.utility.agentframework.providers.DefaultAgentWorkerProvider;
import com.elitecore.utility.agentframework.worker.IAgentWorker;

public class AgentWorkerProvider extends DefaultAgentWorkerProvider{
	private static final String MODULE="AGENT-WORKER-PROVIDER";
	public AgentWorkerProvider() {
		super();
		System.out.println("Initializing Agent Worker Provider");
		/*try {
			prepareAgentContext();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/*private void prepareAgentContext() throws Exception{
		 Properties props = System.getProperties();
	        
	        String systemJndiIp = props.getProperty("cpe.bl.ip");
	        String systemJndiPort = props.getProperty("cpe.bl.port");
	        
	        if(systemJndiIp != null && systemJndiPort != null && !"".equals(systemJndiIp.trim()) && !"".equals(systemJndiPort.trim())){
	            System.out.println("Load from system properties.");
	            
	            Properties properties = new Properties();
	            properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
	            properties.put("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");
	            properties.put("java.naming.provider.url", systemJndiIp + ":" + systemJndiPort);
	            
	            final Context initialContext = new InitialContext(properties);
	            
	            
	            ctx = new AgentContextImpl(initialContext);
	            
	        } else {
	            throw new Exception("System properties cpe.bl.ip & cpe.bl.port pointing to IP Address and Port of Application Server not set. Please use -Dcar.bl.ip=<IPADDRESS> -Dcar.bl.port=<PORT> in startup script.");
	        }
	}*/
	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.providers.DefaultAgentWorkerProvider#getAgentWorker(com.elitecore.utility.agentframework.IAgentSchedule)
	 */
	@Override
	public IAgentWorker getAgentWorker(IAgentSchedule agentSchedule) {
		Logger.logTrace(MODULE, "inside getAgentWorker");
		try {
			return new AgentWorker(agentSchedule);
		} catch (AgentInitializationException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.elitecore.utility.agentframework.providers.DefaultAgentWorkerProvider#getProviderName()
	 */
	@Override
	public String getProviderName() {
		return MODULE;
	}
	
	/*private class AgentContextImpl implements AgentRunContext{

	private  Context initialContext;
    	
    	private String userName;
    	
    	private String password;
    	
    	public AgentContextImpl(Context initialContext) {
    		this.initialContext=initialContext;
    		try{
    			userName = "suadmin";
    			password ="suadmin";
			}catch (Exception e){
				Logger.logTrace("AGENTCONTEXTIMPL", "Agent Username or Password not found in system parameter");
				e.printStackTrace();
			}
		}
    	
		@Override
		public Object lookup(Class<?> className) throws NamingException {
			Logger.logTrace(MODULE, "inside lookup " + className.getName());
			return initialContext.lookup("arapp/"+className.getSimpleName().substring(0, className.getSimpleName().lastIndexOf("Remote")) + "/remote-" + className.getName());
		}
    	
		@Override
		public String getAgentUsername(){
			return userName;
		}
		
		@Override
		public String getAgentPassword(){
			return password;
		}
		
	}*/
	
}
