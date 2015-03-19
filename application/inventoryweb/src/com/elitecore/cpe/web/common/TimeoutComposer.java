package com.elitecore.cpe.web.common;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Window;

public class TimeoutComposer extends GenericForwardComposer<Window>{
	
	private static final long serialVersionUID = 1L;

	
	public void onMainCreate(Event event) {

	}

	
	public void onClick$btnOK(Event event) {
		
		Executions.sendRedirect("/");
	}

	
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		Events.postEvent("onMainCreate", comp, null);
	}

}
