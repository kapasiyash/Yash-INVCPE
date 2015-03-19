package com.elitecore.cpe.web.system.user;

import java.io.Serializable;

import org.zkoss.zul.A;
import org.zkoss.zul.Window;

import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;

public class UserLogoutComposer  extends BaseModuleComposer implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String MODULE = "USER_LOGOUT_COMPOSER";
	
	private A loginPageUrl;
	
	@Override
	public void afterCompose(Window comp) {
		Logger.logTrace(MODULE, "inside afterCompose");
		if(getBDSessionContext()!=null && getBDSessionContext().getBLSession()!=null) {
			doLogout(getBDSessionContext().getBLSession().getUsername());
		}
		String url = "/";
		if(loginPageUrl!=null)
			loginPageUrl.setHref(url);
	}

}