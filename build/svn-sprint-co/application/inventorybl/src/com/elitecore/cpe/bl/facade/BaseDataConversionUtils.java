package com.elitecore.cpe.bl.facade;

import java.sql.Timestamp;
import java.util.Date;

import com.elitecore.cpe.core.base.BaseBL;

/**
 * @author yash.kapasi
 *
 */
public abstract class BaseDataConversionUtils extends BaseBL {

	@SuppressWarnings("unused")
	private static final String MODULE = "BASE_DATA_CONVERSION_UTILS";
	
	/**Returns current timestamp.
	 * @author yash.kapasi
	 * @return {@link Timestamp} currentTime
	 */
	protected static final  Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}
	
}