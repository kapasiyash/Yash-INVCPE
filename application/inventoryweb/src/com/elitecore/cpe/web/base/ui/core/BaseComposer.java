package com.elitecore.cpe.web.base.ui.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;
import org.zkoss.zul.impl.InputElement;

import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.system.user.UserBD;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.web.base.ui.module.BaseComposerOperationImpl;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.core.listener.LoadCacheDataListener;

/**
 * @author yash.kapasi
 *  
 * */
public abstract class BaseComposer extends GenericForwardComposer<Window> {

	private static final long serialVersionUID = 1L;
	protected Div moduleContent;
	protected static final String DATE_FORMAT_ALIASNAME = "DEFAULT_DATE";
	protected static final String DATE_TIME_FORMAT_ALIASNAME = "DEFAULT_DATE_TIME";
	protected static final String PAGESIZE_ALIASNAME = "DEFAULT_PAGE_SIZE";
	public static final Map<String, HttpSession> webActiveSessionMap = Collections.synchronizedMap(new HashMap<String, HttpSession>());
	protected static final String SYSTEMPARAMETER = "SystemParameter";
	
	public static final String BD_SESSION_CONTEXT = "_bd-sessi0n-ctx###";
	
	public ComponentInfo doBeforeCompose(Page page, Component parent,ComponentInfo compInfo) {
		this.arg = Executions.getCurrent().getArg();
		return super.doBeforeCompose(page, parent, compInfo);
	}

	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		internalAfterCompose(comp);
	}
	
	public abstract void afterCompose(Window comp) throws ModuleInitializationException;
	
	protected void internalAfterCompose(Window comp) {
		try {
			afterCompose(comp);
		}catch (Throwable e) {
			comp.setVisible(false);
			e.printStackTrace();
		}
	}

	protected final void setModuleContent(Div moduleContent) {
		this.moduleContent = moduleContent;
	}

	public final String getContextPath() {
		return Executions.getCurrent().getContextPath();
	}
	
	public String getDateFormat() {
//		return "dd/mm/yyyy";
		return getSystemParamterValue(DATE_FORMAT_ALIASNAME);
	}

	public String getDateTimeFormat() {
		return getSystemParamterValue(DATE_TIME_FORMAT_ALIASNAME);
	}

	public int getPageSize() {
//		return 10;
		return Integer.parseInt(getSystemParamterValue(SystemParameterConstants.DEFAULT_PAGE_SIZE));//   Integer.valueOf(getSystemParamterValue(PAGESIZE_ALIASNAME));
	}
	
	@SuppressWarnings({"unused" })
	public static String getSystemParamterValue(String aliasName){	
			HttpSession session = (HttpSession)Sessions.getCurrent(true).getNativeSession();
	    	Map<String, String> sysMap = (Map<String, String>)session.getServletContext().getAttribute(LoadCacheDataListener.SYSPARAM);
	    	return sysMap.get(aliasName);
	 }
	
	protected final void setBDSessionContext(IBDSessionContext ibdSessionContext) {
		HttpSession session = (HttpSession) Sessions.getCurrent(true).getNativeSession();
		String username = ibdSessionContext.getBLSession().getUsername();		
			session.setAttribute(BD_SESSION_CONTEXT, ibdSessionContext);
			webActiveSessionMap.put(username,session);													
	}
	
	protected final IBDSessionContext getBDSessionContext() {
		HttpSession session = (HttpSession)Sessions.getCurrent(true).getNativeSession();
		IBDSessionContext ibdSessionContext = (IBDSessionContext)session.getAttribute(BD_SESSION_CONTEXT);
		return ibdSessionContext;
	}
	protected final IBDSessionContext getBDSessionContext(String username) {
		HttpSession session = webActiveSessionMap.get(username);
		IBDSessionContext ibdSessionContext = (IBDSessionContext)session.getAttribute(BD_SESSION_CONTEXT);
		return ibdSessionContext;
	}
	protected final ServletContext getBDServletContext(){
		HttpSession session = (HttpSession)Sessions.getCurrent(true).getNativeSession();
		return session.getServletContext();
	}


	
	protected final void doLogout(String username){	
		/*UserBD userBD = new UserBD(getBDSessionContext());
		try {
			userBD.doLogoutAudit();
		} catch (CreateBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}*/
		
		UserBD userBD = new UserBD(getBDSessionContext());
		userBD.doLogout();
		
		HttpSession session = webActiveSessionMap.get(username);
		System.out.println("WebMap :: "+webActiveSessionMap);
		webActiveSessionMap.remove(username);
		System.out.println("before removing attribute BD_SESSION_CONTEXT"+session);
		HttpSession session1 = (HttpSession)Sessions.getCurrent().getNativeSession();
		if(session1!=null) {
			session1.removeAttribute(BD_SESSION_CONTEXT);
			session1.invalidate();
			
			
			
		}
		if(session!=null){
			System.out.println("removing attribute BD_SESSION_CONTEXT");
//			if(session.isNew()) {
//			if(session.getAttribute(BD_SESSION_CONTEXT)!=null) {
//				session.removeAttribute(BD_SESSION_CONTEXT);
//			}
//				session.invalidate();
//			}
		}
	}
	
	public static class ComboItemDataRenderer implements ComboitemRenderer<ComboData>{

		private Combobox combobox ;
		private Long id;
		

		public ComboItemDataRenderer() {
			super();
		}
		public ComboItemDataRenderer(Combobox combobox, Long id) {
			super();
			this.combobox = combobox;
			this.id = id;
		}


		@Override
		public void render(Comboitem comboItem, ComboData comboData,int index) throws Exception {
			comboItem.setValue(comboData);
			comboItem.setLabel(comboData.getName());
			if(combobox !=null && id !=null && id.equals(comboData.getId())){
				combobox.setSelectedItem(comboItem);
			}
		}

	}
	
	public static class ComboBoxItemDataRenderer implements ComboitemRenderer<ComboBoxData>{
		@Override
		public void render(Comboitem comboItem, ComboBoxData comboBoxData,int index) throws Exception {
			comboItem.setValue(comboBoxData);
			comboItem.setLabel(comboBoxData.getName());
		}

	}
	
	public void onClick$btnCancel(Event event){
    	
    	if (moduleContent != null && moduleContent.getChildren() != null) {
    		moduleContent.getChildren().clear();
    	}
//    	Executions.createComponents(Pages.USER_DASHBOARD, (Component) moduleContent, null);
//    	Executions.createComponents(Pages.AR_EVENT, (Component) moduleContent, null);
    }
	
	 public void showErrorDialog(String errorMessage, Throwable error) {
	    	BaseComposerOperationImpl.showErrorDialog(errorMessage, error);
	    }
	    
	    public final void showErrorDialog(String errorMessage, Throwable error, Window parent) {
	    	BaseComposerOperationImpl.showErrorDialog(errorMessage, error, parent);
	    }
	    
	    public final void showSuccessDialog(String message) {
	    	Messagebox.show(message,"Informtion",Messagebox.OK,Messagebox.INFORMATION);
	    }
	
	    public static void resetComponents(Component focusElement, Component... components) {
			for (Component component : components) {
				if (component instanceof Textbox)
					((Textbox) component).setRawValue("");
				else if (component instanceof Combobox)
					((Combobox) component).setSelectedItem(null);
				else if (component instanceof Datebox)
					((Datebox) component).setRawValue(null);
				else if (component instanceof Label)
					((Label) component).setValue("");
				else if (component instanceof Longbox)
					((Longbox) component).setRawValue(null);
				else if (component instanceof Intbox)
					((Intbox) component).setRawValue(null);
				else if(component instanceof Radiogroup)
					((Radiogroup)component).setSelectedItem(null);
				component.invalidate();
			}
			((InputElement) focusElement).setFocus(true);
		}

	   public void sortComboDatas(List<ComboData> comboDatas ){
			Collections.sort(comboDatas,new Comparator<ComboData>() {

				@Override
				public int compare(ComboData o1, ComboData o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
	   }
	   
	   
	   public void sortComboBoxDatas(List<ComboBoxData> comboDatas ){
			Collections.sort(comboDatas,new Comparator<ComboBoxData>() {

				@Override
				public int compare(ComboBoxData o1, ComboBoxData o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
	   }
	   
	   
	public abstract class PagingListModel<T> extends AbstractListModel<T> implements Sortable<T>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int _startPageNumber;
		private int _totalPageSize;
		private int _itemStartNumber;

		 private int _size = -1;
		    private Object[] _cache;
		    private int _beginOffset;
		    private String _orderBy;
		    private boolean _ascending, _descending;
		    private Comparator _sorting;
		    
		    
		// internal use only
		private List<T> _items = new ArrayList<T>();

		public PagingListModel(int startPageNumber) {
			super();

			this._startPageNumber = startPageNumber;
			this._totalPageSize = getPageSize();
			this._itemStartNumber = startPageNumber * _totalPageSize;

			_items = (List<T>) getPageData(_itemStartNumber, _totalPageSize);
		}

		protected abstract List<T> getPageData(int itemStartNumber,
				int pageSize);

		public int getTotalSize() {
			return _items.size();
		}

		public T getElementAt(int index) {
			return (T) _items.get(index);
		}

		public int getSize() {
			return _items.size();
		}

		public int getStartPageNumber() {
			return this._startPageNumber;
		}

		
		public int get_totalPageSize() {
			return _totalPageSize;
		}

		public int getItemStartNumber() {
			return _itemStartNumber;
		}
		
		
		
		 /*@Override
		    public void sort(Comparator cmpr, boolean ascending) {
		        _cache = null; //purge cache
		        _size = -1; //so size will be reloaded
		        _descending = !(_ascending = ascending);
		        _orderBy = ((FieldComparator)cmpr).getRawOrderBy();
		        _sorting = cmpr;
		             //Here we assume sort="auto(fieldName)" is specified in ZUML, so cmpr is FieldComparator
		             //On other hand, if you specifies your own comparator, such as sortAscending="${mycmpr}",
		             //then, cmpr will be the comparator you assigned
		        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
		    }*/
		
		@Override
		public void sort(Comparator comparator, boolean flag) {
		   if (comparator instanceof FieldComparator) {
//		       _dir = flag ? 0 : 1;
		       _cache = null;
		       _orderBy = ((FieldComparator)comparator).getRawOrderBy();
		       fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
		    }
		}
		   
		 @Override
			public String getSortDirection(Comparator<T> arg0) {
				// TODO Auto-generated method stub
				return "natural";
			}
	}
	
	
}
