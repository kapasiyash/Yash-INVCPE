package com.elitecore.cpe.web.base.ui.core;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Sessions;

import com.elitecore.cpe.core.IBDSessionContext;

public class BaseDataProvider {
	
	
	protected final IBDSessionContext getBDSessionContext(){
			HttpSession session = (HttpSession)Sessions.getCurrent(true).getNativeSession();
			IBDSessionContext ibdSessionContext = (IBDSessionContext)session.getAttribute(BaseComposer.BD_SESSION_CONTEXT);
			return ibdSessionContext;
	}
	
	protected final Timestamp getCurrentTimestamp(){
		return new Timestamp(new Date().getTime());
	}
}
