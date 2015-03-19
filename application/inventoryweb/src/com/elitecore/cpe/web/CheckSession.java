package com.elitecore.cpe.web;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.Initiator;

import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseComposer;

public class CheckSession  implements Initiator{
	private static final String MODULE ="CHECK SESSION";
	@Override
	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		Session session = Executions.getCurrent().getSession();
		Logger.logTrace(MODULE, "Inside doInit Method");

		String url =(String) arg1.get("arg0"); //url to redirect if already logged in
		if(url==null){//specific for user-home

		if(session==null || !session.hasAttribute(BaseComposer.BD_SESSION_CONTEXT)){
		Executions.sendRedirect("/");

		}

		}else{
		if(session!=null && session.hasAttribute(BaseComposer.BD_SESSION_CONTEXT)){
		Executions.sendRedirect(url);

		}
	}
	}
	
}
