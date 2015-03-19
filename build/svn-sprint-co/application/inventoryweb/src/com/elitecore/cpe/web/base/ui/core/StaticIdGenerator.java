package com.elitecore.cpe.web.base.ui.core;



import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zk.ui.sys.IdGenerator;

public class StaticIdGenerator implements IdGenerator, Serializable {
	private static final long serialVersionUID = 20130905100453L;

	private static final String ID_PREFIX = "z_";
	private static final String ID_NUM = "comp";

	public String nextComponentUuid(Desktop desktop, Component comp,
			ComponentInfo compInfo) {
		String number;
		if ((number = (String) desktop.getAttribute(ID_NUM)) == null) {
			number = "0";
			desktop.setAttribute(ID_NUM, number);
		}
		int i = Integer.parseInt(number);
		i++;
		desktop.setAttribute(ID_NUM, String.valueOf(i));
		return ComponentsCtrl.toAutoId(ID_PREFIX, i);
	}

	public String nextPageUuid(Page page) {
		final Desktop desktop = page.getDesktop();
		String number;
		if ((number = (String) desktop.getAttribute(ID_NUM)) == null) {
			number = "0";
			desktop.setAttribute(ID_NUM, number);
		}
		int i = Integer.parseInt(number);
		i++;
		desktop.setAttribute(ID_NUM, String.valueOf(i));
		return ComponentsCtrl.toAutoId(ID_PREFIX, i);
	}

	public String nextDesktopId(Desktop desktop) {
		return null;
	}

}