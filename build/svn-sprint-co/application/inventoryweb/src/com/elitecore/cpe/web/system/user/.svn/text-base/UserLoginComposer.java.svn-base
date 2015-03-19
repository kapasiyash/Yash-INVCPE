package com.elitecore.cpe.web.system.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.system.user.UserBD;
import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class UserLoginComposer extends BaseComposer {

	private static final long serialVersionUID = 1L;
	private static final String MODULE = "USER_LOGIN COMPOSER";
	private Textbox txtUsername;
	private Textbox txtPassword;
	
//	private Checkbox chkboxRememberMeId;
	private boolean userNameCookie,passwordCookie;
	private static final String LOGIN_COOKIE_USER_NAME="LOGIN_USER_COOKIE";
	private static final String LOGIN_COOKIE_PASSWORD_NAME="LOGIN_PASS_COOKIE";
	private static final int LOGIN_COOKIE_EXPIRE_TIME=1209600;
	public static final String BD_SESSION_CONTEXT = "_bd-sessi0n-ctx###";
	
	public UserLoginComposer() {
		super();
	}
	
	public void onMainCreate(Event event) {
		getLoginCookie();
//		this.txtUsername.focus(); // set the focus on UserName		
		Logger.logTrace(MODULE, "onMainCreate userNameCookie:"+userNameCookie+", passwordCookie:"+passwordCookie);
		if(userNameCookie && passwordCookie)
		{
			//onClick$btnLogin(event);
		}else{
//			txtUsername.setText("");		
//			txtPassword.setText("");		
		}
	}
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		Events.postEvent("onMainCreate", comp, null);
		txtUsername.focus();
		
		HttpSession httpSession = (HttpSession)Sessions.getCurrent().getNativeSession();
		System.out.println(httpSession);
		if(httpSession!=null && httpSession.getAttribute(BD_SESSION_CONTEXT)!=null) {
			System.out.println(httpSession.getAttribute(BD_SESSION_CONTEXT));
		System.out.println(getBDSessionContext());
			if(getBDSessionContext()!=null && getBDSessionContext().getBLSession()!=null) {
				System.out.println(getBDSessionContext().getBLSession());
				Executions.sendRedirect("/module/user-home.zul");
			}
		}
	}
	
	public void onOK$txtUsername(Event event){
		onClick$btnLogin(event);
	}
	
	public void onOK$txtPassword(Event event) {
		onClick$btnLogin(event);
	}
	
	public void onClick$btnLogin(Event event) {	
	
	   if (txtUsername.getText().length() == 0 || txtPassword.getText().length() == 0) {
        	Messagebox.show("Please enter  username/password and then press login button", "Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
        	resetScreen();
        	return;        
        }else{          
        	try {
        		IBDSessionContext sessionContext = new UserBD().doLogin(txtUsername.getText(), txtPassword.getText(), Executions.getCurrent().getRemoteAddr());
				if(sessionContext!=null){
					Logger.logTrace(MODULE, "Allowed Actions : " + sessionContext.getBLSession().getPermittedAction().size());
//					Logger.logTrace(MODULE, "Allowed Actions : " + sessionContext.getBLSession().getPermittedAction());
					setBDSessionContext(sessionContext);    		
					/*if(chkboxRememberMeId.isChecked()){
		                setLoginCookie();
		            }*/
					Executions.sendRedirect("/module/user-home.zul");
				
				}else{
					Messagebox.show("Invalid Userame / Password", "Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);	
				}
			} catch (AccessDeniedException e) {
				Messagebox.show(e.getMessage(), "Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);	
			
			} catch (TechnicalException e) {
					Messagebox.show(e.getMessage(), "Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);	
			} catch (SearchBLException e) {
				Messagebox.show(e.getMessage(), "Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);	
			}     	   	
        }				 
	}
	
	private void resetScreen() {
		txtPassword.setText("");
		txtUsername.focus();
	}
	
	public void onOK$mainWin(Event event) {
		onClick$btnLogin(event);
	}	
	
	private void setLoginCookie(){
		try {
	        	String userNameLoginCookieVal=txtUsername.getValue();
	        	String passwordLoginCookieVal=txtPassword.getValue();
	            HttpServletResponse response = (HttpServletResponse)Executions.getCurrent().getNativeResponse();
	            Cookie userNameLoginCookie = new Cookie(LOGIN_COOKIE_USER_NAME, userNameLoginCookieVal);
	            Cookie passwordLoginCookie = new Cookie(LOGIN_COOKIE_PASSWORD_NAME, passwordLoginCookieVal);
	            
	            userNameLoginCookie.setMaxAge(LOGIN_COOKIE_EXPIRE_TIME);
	            passwordLoginCookie.setMaxAge(LOGIN_COOKIE_EXPIRE_TIME);
	            response.addCookie(userNameLoginCookie);   
	            response.addCookie(passwordLoginCookie);
        } catch (Exception e) {
        	Logger.logError("LOGIN COMPOSER IN COOKIE", "" +e);
        }
	}
	
	private void getLoginCookie(){
		
		Cookie [] loginCookiesValues = ((HttpServletRequest)Executions.getCurrent().getNativeRequest()).getCookies();

		if( loginCookiesValues != null && loginCookiesValues.length>0){
			for(int i=0;i<loginCookiesValues.length;i++){
				if(LOGIN_COOKIE_USER_NAME!=null){
	        	String username,password;
	        		if(loginCookiesValues[i].getName() != null){
	        			if((loginCookiesValues[i].getName().equals(LOGIN_COOKIE_USER_NAME))){
		        			username=loginCookiesValues[i].getValue();
		        			if(username!=null && !"".equals(username)){
//		        				chkboxRememberMeId.setChecked(true);
		        				txtUsername.setValue(username);
		        				userNameCookie=true;
		        			}
	        			}
	        		}
	        		if((loginCookiesValues[i].getName() != null)){
	        			if((loginCookiesValues[i].getName().equals(LOGIN_COOKIE_PASSWORD_NAME))){
		        			password=loginCookiesValues[i].getValue();
		        			if(password!=null && !"".equals(password)){
		        				txtPassword.setValue(password);
		        				passwordCookie=true;
		        			}
	        			}
	        		}
				}
			}
		}
	}
}
