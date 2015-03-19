package com.elitecore.cpe.web.base.ui.module;

import org.zkoss.zul.Window;

import com.elitecore.cpe.web.base.ui.core.BaseComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;



/**
 * @author yash.kapasi
 *
 */
public class BaseModuleComposer extends BaseComposer {

	private static final long serialVersionUID = 1L;
	
	
	public static final String VIEW_ENTITY_ID_KEY = "_viewEntityId";
	public static final String VIEW_ENTITY_APP_ID_KEY = "_viewEntityAppId";
	public static final String VIEW_ENTITY_NODE_ID_KEY = "_viewEntityNodeId";
	public static final String VIEW_COMPOSER_KEY = "_baseViewComposer";
	
	
	
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		
		
		try {
			afterCompose(comp);
		}catch(Throwable t) {
			if(comp!=null)
			comp.setVisible(false);
			t.printStackTrace();
		}
	}
	
	protected void internalAfterCompose(Window comp) {
		// !important - null implementation to avoid multiple calls to afterCompose
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		// TODO Auto-generated method stub

	}
	
}
