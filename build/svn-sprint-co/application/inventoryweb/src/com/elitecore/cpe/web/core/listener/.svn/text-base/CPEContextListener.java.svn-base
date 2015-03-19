package com.elitecore.cpe.web.core.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.elitecore.cpe.util.logger.Logger;

/**
 * cpe Context Listner.
 * @author yash.kapasi
 *
 */
public class CPEContextListener  implements ServletContextListener{

private static final String MODULE = "cpe-CTX"; 
	private LoadCacheDataListener dataListener;
	private ScheduledExecutorService executor;
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		Logger.logTrace(MODULE, " contextDestroyed() ");
		if(!executor.isShutdown()){
			System.out.println("Shutting down Executor as Context is about to destroy");
			executor.shutdownNow();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Logger.logInfo(MODULE, "    --  Application context initialization process for Crestel CPE  --    ");
		Logger.logInfo(MODULE, "    --  Elitecore Technologies Pvt. Ltd.  --    ");
		
		ServletContext ctx = event.getServletContext();
		try{
			dataListener = new LoadCacheDataListener(ctx);
			dataListener.run();
			scheduleReload();
		}catch (Exception e) {
			Logger.logError(MODULE, "Crestel CPE Application context initialization failed, reason: " + e.getMessage());
			Logger.logTrace(MODULE, e);
		}
		
	}
	
	private void scheduleReload(){
		System.out.println("scheduling thread for reloading.");
		executor =   Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(dataListener, 0, 10, TimeUnit.MINUTES);
	}

}
