package com.elitecore.cpe.web.core.listener;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.utils.dataprovider.SystemParameterDataProvider;

/**
 * Loads data for cache.
 * @author yash.kapasi
 *
 */
public class LoadCacheDataListener implements Runnable {

	ServletContext ctx;
	private static final String MODULE = "CPE-CacheManager";  // Crestel CPE Cache Manager
	public static final String SYSPARAM = "SystemParameter";
	public static final String PLM_SERVICE = "PLMSERVICES";
	public LoadCacheDataListener(ServletContext sct) {
		this.ctx = sct;
		/*Timer timer = new Timer();
		timer.schedule(this,new Date(),60*10*1000);*/
	}

	@Override
	public void run()  {
		Logger.logInfo(MODULE, "Cache operation started");
		
		try{
			
			Map<String,String> systemParamterMap = getSystemParameterData();
			if(systemParamterMap != null && !systemParamterMap.isEmpty() ){
				ctx.setAttribute(SYSPARAM, systemParamterMap);
				Logger.logInfo(MODULE, "    ----    System Parameters Cached in Context    ----    ");
				StringWriter stringBuffer = new StringWriter();
				PrintWriter out = new PrintWriter(stringBuffer);
				for(Map.Entry<String, String> entry:systemParamterMap.entrySet()) {
					out.print(entry.getKey());
					out.print(" = ");
					out.println(entry.getKey());
				}
				Logger.logInfo(MODULE, "    ----    End of System Parameters Cached in Context    ----    ");

			} else {
				Logger.logWarn(MODULE, "Could not read system parameter configurations, the list is empty");
			}
			
			
			
		}catch (Exception e) {
			Logger.logError(MODULE, "Exception in run Method...");
		}
		
		Logger.logTrace(MODULE, " DataListener End.");

	}
	
	
	public Map<String,String> getSystemParameterData(){
		Map<String,String> sysparamter = new HashMap<String, String>();
		try{
			Logger.logInfo(MODULE, "Reading system parameter configrations");
			SystemParameterDataProvider sysdataProvider = new SystemParameterDataProvider();
			sysparamter = sysdataProvider.getSystemParameterDetail();
			Logger.logInfo(MODULE, "Read system parameter configuration operation completed");
			
		}catch (Exception e) {
			Logger.logError(MODULE, "Exception in getSystemParameterData Method...");
			Logger.logTrace(MODULE, e);
		}
		return sysparamter;
	}
	
	/*public List<ServiceDetail> loadPLMServices(){
		List<ServiceDetail> servicDetails  = new Vector<ServiceDetail>();
		try{
			Map<String,String> map = (Map<String, String>) ctx.getAttribute(SYSPARAM);
			if(map !=null && !map .isEmpty()){
				String plmServiceURL = map.get(SystemParameterConstants.PLM_SERVICE_URL);
				if(plmServiceURL!=null && !plmServiceURL.isEmpty()){
					
				}else{
					Logger.logWarn(MODULE, "Please configure PLM Service URL for caching PLM Services.");
				}
			}
		}catch(Exception ex){
			Logger.logError(MODULE, "Error Occured while Loading PLM Services. Possible reason. " + ex.getMessage());
		}
		return servicDetails;
	}*/
}
