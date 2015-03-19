package com.elitecore.cpe.bl.facade.batch;

import java.sql.Timestamp;

import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.vo.master.AttributeVO;

public class BatchManagementUtil {

	
	
	
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
}
