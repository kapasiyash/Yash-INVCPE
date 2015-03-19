package com.elitecore.cpe.web.utils.dataprovider;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.elitecore.cpe.bl.delegates.system.systemparameter.SystemParameterBD;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.util.logger.Logger;

public class SystemParameterDataProvider implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String MODULE = "SYSTEMPARAMETER_DATAPROVIDER";


	public Map<String,String> getSystemParameterDetail() throws Exception{
			Logger.logTrace(MODULE, " getSystemParamterDetail() Called .");
		try{
			/*Map<String, String> map = new HashMap<String, String>();
			map.put("DEFAULT_DATE", "dd-MMM-yyyy");
			map.put("DEFAULT_DATE_TIME", "dd-MMM-yyyy HH:mm:ss");
			map.put("DEFAULT_PAGE_SIZE", "10");
			map.put("REOPEN_RECORDS", "2");
			
			return map;*/
			return new SystemParameterBD().getSystemParamterValue();
			
		}catch (Exception e) {
			Logger.logError(MODULE, " Error During getSystemParameterDetail. " +e.toString());
			e.printStackTrace();
			throw new TechnicalException();
		}
	}
}
