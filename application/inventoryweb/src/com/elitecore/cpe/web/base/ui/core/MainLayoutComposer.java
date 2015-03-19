package com.elitecore.cpe.web.base.ui.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.zhtml.Li;
import org.zkoss.zhtml.Ul;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleGroupData;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.utils.DefaultActionUtil;

public class MainLayoutComposer  extends BaseComposer{
	private static final long serialVersionUID = 1L;
	
	public static final String MAIN_COMPOSER_KEY = "_mainComposer";

	public static final String TASK_TYPE_ID = "_TaskTypeIdId";
	public static final String TASK_STEP_ID = "_TaskStepIdId";

	public static final String IMAGE_ICON = "_ImageIcon";
	public static final String ACTIVE_IMAGE_ICON = "_ActiveImageIcon";
	
	
	private static final String MODULE_GROUP = "_moduleGroup";
	
	
	private Tabbox leftNavTab;
	private Div moduleContent;	
	private Map<String, Object> mainComposerParams;
	
	private Div topNavDiv;
	private List<A> moduleGroupAnchorList;
	private Label userName;
	private A selectedA;
	
	public MainLayoutComposer() {
		this.mainComposerParams = new HashMap<String, Object>();
		this.mainComposerParams.put(MAIN_COMPOSER_KEY, this);
	}
	
	public Div getModuleContent() {
		return moduleContent;
	}
public String getLoggedInUserName(){
	return "suadmin";
}
	

public String getUserType(){
	return "admin";
}

public void onClick$btnLogout(Event event){
	doLogout(getBDSessionContext().getBLSession().getUsername());
	Executions.sendRedirect("/logout-screen.zul");

}
public void onClick$btnhome(Event event){
	Components.removeAllChildren(moduleContent);
//	Executions.createComponents(Pages.USER_DASHBOARD, moduleContent, mainComposerParams);		
	Executions.createComponents(Pages.AR_EVENT_ENTITY, moduleContent, mainComposerParams);
}

public void onClick$btnabout(Event event){
	Components.removeAllChildren(moduleContent);
//	Executions.createComponents("/WEB-INF/pages/system/dashboard/version-history.zul", (Component) moduleContent, null);		
}
@Override
public void afterCompose(Window window){
	if(getBDSessionContext().getBLSession() == null){	
		Executions.sendRedirect("/index.zul");
	}
	userName.setValue(getBDSessionContext().getBLSession().getName());
	createTopNavigationPanel();
	showDashBorard();
}

private void createTopNavigationPanel() {
	// TODO Auto-generated method stub
	moduleGroupAnchorList = new ArrayList<A>();
	
//	SystemInternalBD internalBD = new SystemInternalBD(getBDSessionContext());
	List<SystemModuleGroupData> moduleGroupDatas = null;
	
	/*try {
//		moduleGroupDatas = internalBD.getSystemModuleData();
//		Logger.logTrace(MODULE, "moduleGroupDatas :: " + moduleGroupDatas);
		
	} catch (SearchBLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TechnicalException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	
	
	A defaultAnchor = null;
	
	Ul ul = new Ul();
	//loop
	
	Li li = new Li();

	//Image img = new Image("/images/icons/home16.png");
	A l = new A();
	
//	l.appendChild(img);
	l.setImage("/images/icons/home16.png");
	l.setHoverImage("/images/icons/home16-active.png");
	l.setLabel("Home");
	l.setAttribute(IMAGE_ICON, "/images/icons/home16.png");
	l.setAttribute(ACTIVE_IMAGE_ICON, "/images/icons/home16-active.png");
	
	l.addEventListener(Events.ON_MOUSE_OVER, new EventListener<Event>() {
		@Override
		public void onEvent(Event event) throws Exception {
			mouseOverAnchorList((A) event.getTarget());
		}
	});
	
	
	//Added Home Menu by Kirtan.patel
	List<Tab> lstTab=new LinkedList<Tab>();
	Tab tab = null;
	Tabpanel tabPannel = null;
	
	Tabpanels tabPanels = new Tabpanels();
	tabPanels.setStyle("border: 0px; padding: 0px; margin: 0px; background-color: #F5F5F5;");
	
	
	//Adding Default Actions. 
	for(SystemModuleData module : DefaultActionUtil.getDefaultModule()){
		prepareTabsAndPanels(module, lstTab, tabPanels);
	}
	
	/** Ending Logic for Home Tab */
	
	
	//for rendering 
	Tabs leftNavTabs = new Tabs();
	for(Tab tabs:lstTab){
		tabs.setStyle("border-right: none");
		leftNavTabs.appendChild(tabs);
	}
	leftNavTab.appendChild(leftNavTabs);
	leftNavTab.appendChild(tabPanels);
	
	
	//end of home menus
	
	l.addEventListener(Events.ON_MOUSE_OUT, new EventListener<Event>() {
		@Override
		public void onEvent(Event event) throws Exception {
			mouseOutAnchorList();
		}
	});
	
	
	l.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
		@Override
		public void onEvent(Event event) throws Exception {
			// TODO Auto-generated method stub
			if(leftNavTab.getChildren()!=null){
				Components.removeAllChildren(leftNavTab);
			}
			Components.removeAllChildren(moduleContent);
	    	Executions.createComponents(Pages.AR_EVENT_ENTITY, (Component) moduleContent, mainComposerParams);	
	    	//added by kirtan.patel start line
	    	List<Tab> lstTab=new LinkedList<Tab>();
			/*Tab tab = null;
			Tabpanel tabPannel = null;*/
			
			Tabpanels tabPanels = new Tabpanels();
			tabPanels.setStyle("border: 0px; padding: 0px; margin: 0px; background-color: #F5F5F5;");
			//Adding Default Actions. 
			for(SystemModuleData module : DefaultActionUtil.getDefaultModule()){
				prepareTabsAndPanels(module, lstTab, tabPanels);
			}
			
			/** Ending Logic for Home Tab */
			//for rendering 
			Tabs leftNavTabs = new Tabs();
			for(Tab tabs:lstTab){
				tabs.setStyle("border-right: none");
				leftNavTabs.appendChild(tabs);
			}
			leftNavTab.appendChild(leftNavTabs);
			leftNavTab.appendChild(tabPanels);
	    	//end Line
	    	for (A a : moduleGroupAnchorList) {
				if (a.getClass() != null) {
					a.setClass(null);
						a.setImage(a.getAttribute(IMAGE_ICON)+"");
				}
			}

			((A) event.getTarget()).setClass("active");
			((A) event.getTarget()).setImage(((A) event.getTarget()).getAttribute(ACTIVE_IMAGE_ICON)+"");
			((A) event.getTarget()).setStyle("color:white");
			selectedA = (A) event.getTarget();
			
			
		}
	});
	
	li.appendChild(l);
	ul.appendChild(li);
	
	defaultAnchor = l;
	defaultAnchor.setClass("active");
	selectedA = l;
	l.setImage("/images/icons/home16-active.png");
	l.setStyle("color:white");
	moduleGroupAnchorList.add(l);
	

	/*if(moduleGroupDatas!=null && !moduleGroupDatas.isEmpty()){
		for(final SystemModuleGroupData moduleGroupData : moduleGroupDatas){
			li = new Li();
							
			if(moduleGroupData.getIconUrl()!=null){
//				img = new Image(moduleGroupData.getIconUrl());			
			}else{
	//			img = new Image("/images/icons/folder16.png");						
			}
			
			l = new A();
							
			if(moduleGroupData.getIconUrl()!=null){
				l.setAttribute(IMAGE_ICON, moduleGroupData.getIconUrl());
				l.setImage(moduleGroupData.getIconUrl());			
			}else{
				l.setAttribute(IMAGE_ICON, "/images/icons/folder16.png");
				l.setImage("/images/icons/folder16.png");						
			}

			
			if(moduleGroupData.getActiveIconUrl()!=null){
				l.setAttribute(ACTIVE_IMAGE_ICON, moduleGroupData.getActiveIconUrl());
				l.setHoverImage(moduleGroupData.getActiveIconUrl());			
			}else{
				l.setAttribute(ACTIVE_IMAGE_ICON, "/images/icons/folder16-active.png");
				l.setHoverImage("/images/icons/folder16-active.png");						
			}
											
			l.setLabel(moduleGroupData.getName());
			
			
			l.addEventListener(Events.ON_MOUSE_OVER, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					mouseOverAnchorList((A) event.getTarget());
				}
			});

			l.addEventListener(Events.ON_MOUSE_OUT, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					mouseOutAnchorList();
				}
			});
			
			
			l.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					if(leftNavTab.getChildren()!=null){
						Components.removeAllChildren(leftNavTab);
						Components.removeAllChildren(moduleContent);
					}
					
					List<Tab> lstTab=new LinkedList<Tab>();
					Tab tab;
					Tabpanel tabPannel;
					
					Tabpanels tabPanels = new Tabpanels();
					tabPanels.setStyle("border: 0px; padding: 0px; margin: 0px; background-color: #F5F5F5;");
					
					if(moduleGroupData.getSystemModules()!=null && !moduleGroupData.getSystemModules().isEmpty()){
						for(SystemModuleData moduleData : moduleGroupData.getSystemModules()){
							
							prepareTabsAndPanels(moduleData,lstTab,tabPanels);
							tab = new Tab(moduleData.getName());
							tab.setStyle("border-right: none;");
							tabPannel = new Tabpanel();
							tabPannel.setStyle("padding: 0px; margin: 0px; background-color: #F5F5F5; border: 0px;");
							
							MenuItemTreeNode root = new MenuItemTreeNode(new MainMenuItem("", "", moduleData.getName(), true), null);
							Collections.sort(moduleData.getSystemActions());
							if(moduleData.getSystemActions()!=null && !moduleData.getSystemActions().isEmpty()){
								for(SystemActionData actionData : moduleData.getSystemActions()){
									MenuItemTreeNode actionChild = new MenuItemTreeNode(new MainMenuItem(actionData.getActionAlias(),actionData.getZulPageUrl(), actionData.getName()),null, true);
									root.add(actionChild);
								}
							}
							Tree tree = new Tree();
							DefaultTreeModel<MainMenuItem> treeModel = new DefaultTreeModel<MainLayoutComposer.MainMenuItem>(root) {
								private static final long serialVersionUID = 1L;
									
								public boolean isLeaf(TreeNode<MainMenuItem> node) {
									return node.isLeaf();
								}
							};
							
							tree.setItemRenderer(new MenuItemTreeRenderer());
							tree.setModel(treeModel);
							tree.setClass("mainMenuTree");
							tree.setStyle("border: none;");
							tabPannel.appendChild(tree);
							
							lstTab.add(tab);
							tabPanels.appendChild(tabPannel);
						}
					}
					
					Tabs leftNavTabs = new Tabs();
					for(Tab tabs:lstTab){
						tabs.setStyle("border-right: none;");
						leftNavTabs.appendChild(tabs);
					}
					
					leftNavTab.appendChild(leftNavTabs);
					leftNavTab.appendChild(tabPanels);
					
					if(moduleGroupData.getHomeURL()!=null){
						Executions.createComponents(moduleGroupData.getHomeURL(), (Component) moduleContent, null);
					}
					
					for (A a : moduleGroupAnchorList) {
						if (a.getClass() != null) {
							a.setClass(null);
							a.setImage(a.getAttribute(IMAGE_ICON)+"");
						}
					}

					((A) event.getTarget()).setClass("active");
					((A) event.getTarget()).setImage(moduleGroupData.getIconUrl());						
					if(moduleGroupData.getActiveIconUrl()!=null){
						((A) event.getTarget()).setImage(moduleGroupData.getActiveIconUrl());			
					}else{
						((A) event.getTarget()).setImage("/images/icons/folder16-active.png");												
					}						
					((A) event.getTarget()).setStyle("color:white");
					selectedA = (A) event.getTarget();
					
				}
			});
			
			li.appendChild(l);
			ul.appendChild(li);
			
			moduleGroupAnchorList.add(l);
		}
	}*/
	
//	li = new Li();

//	img = new Image("/images/icons/logout16.png");
	l = new A();
	
//	l.appendChild(img);
	l.setImage("/images/icons/logout16.png");
	l.setHoverImage("/images/icons/logout16-active.png");
	l.setAttribute(IMAGE_ICON, "/images/icons/logout16.png");
	l.setAttribute(ACTIVE_IMAGE_ICON, "/images/icons/logout16-active.png");
	l.appendChild(new Label("Logout"));
	
	
	l.addEventListener(Events.ON_MOUSE_OVER, new EventListener<Event>() {
		@Override
		public void onEvent(Event event) throws Exception {
			mouseOverAnchorList((A) event.getTarget());
		}
	});

	l.addEventListener(Events.ON_MOUSE_OUT, new EventListener<Event>() {
		@Override
		public void onEvent(Event event) throws Exception {
			mouseOutAnchorList();
		}
	});
	
	
	li = new Li();

//	img = new Image("/images/icons/logout16.png");
	l = new A();
	
	l.setImage("/images/icons/logout16.png");
	l.setAttribute(IMAGE_ICON, "/images/icons/logout16.png");
	l.setAttribute(ACTIVE_IMAGE_ICON, "/images/icons/logout16.png");
	
	l.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
		@Override
		public void onEvent(Event event) throws Exception {
			// TODO Auto-generated method stub
			doLogout(getBDSessionContext().getBLSession().getUsername());
			Executions.sendRedirect("/logout-screen.zul");
		}
	});
	l.setLabel("Logout");
	li.appendChild(l);
	ul.appendChild(li);
	
	moduleGroupAnchorList.add(l);
	topNavDiv.appendChild(ul);
	//end loop
	
}
private void prepareTabsAndTabPanels(List<Tab> lstTab, Tab tab,
		Tabpanels tabPanels, Tabpanel tabPannel) {
	// TODO Auto-generated method stub
	
}

private void prepareTabsAndPanels(SystemModuleData moduleData, List<Tab> lstTab, Tabpanels tabPanels){
	Tab tab = new Tab(moduleData.getName());
	tab.setStyle("border-right: none;");
	Tabpanel tabPannel = new Tabpanel();
	tabPannel.setStyle("padding: 0px; margin: 0px; background-color: #F5F5F5; border: 0px;");
	
	MenuItemTreeNode root = new MenuItemTreeNode(new MainMenuItem("", "", moduleData.getName(), true), null);
	Collections.sort(moduleData.getSystemActions());
	if(moduleData.getSystemActions()!=null && !moduleData.getSystemActions().isEmpty()){
		for(SystemActionData actionData : moduleData.getSystemActions()){
			MenuItemTreeNode actionChild = new MenuItemTreeNode(new MainMenuItem(actionData.getActionAlias(),actionData.getZulPageUrl(), actionData.getName()),null, true);
			root.add(actionChild);
		}
	}
	Tree tree = new Tree();
	DefaultTreeModel<MainMenuItem> treeModel = new DefaultTreeModel<MainLayoutComposer.MainMenuItem>(root) {
		private static final long serialVersionUID = 1L;
			
		public boolean isLeaf(TreeNode<MainMenuItem> node) {
			return node.isLeaf();
		}
	};
	
	tree.setItemRenderer(new MenuItemTreeRenderer());
	tree.setModel(treeModel);
	tree.setClass("mainMenuTree");
	tree.setStyle("border: none;");
	tabPannel.appendChild(tree);
	
	lstTab.add(tab);
	tabPanels.appendChild(tabPannel);
}

private final class MenuItemTreeRenderer implements TreeitemRenderer<MenuItemTreeNode> {
	@Override
	public void render(final Treeitem treeItem, MenuItemTreeNode treeNode, int index)throws Exception {
		MenuItemTreeNode ctn = treeNode;
        MainMenuItem contact = (MainMenuItem) ctn.getData();
        Treerow dataRow = new Treerow();
        dataRow.setParent(treeItem);
        treeItem.setValue(ctn);            
        if (!contact.isModule()) { // Contact Row
        	Treecell treeCell = new Treecell(contact.getLabel());            	
            dataRow.appendChild(treeCell);                
            dataRow.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                @Override
                public void onEvent(Event event) throws Exception {
                	MenuItemTreeNode clickedNodeValue = (MenuItemTreeNode) ((Treeitem) event.getTarget().getParent()).getValue();
                	
        			if (moduleContent != null && moduleContent.getChildren() != null) {
        				moduleContent.getChildren().clear();
        			}            			            			            			
        			
        			
        			String appTypeId;
        			String stepId;
        			
        			if(((MainMenuItem) clickedNodeValue.getData()).getId().contains("#")){
        				String[] params =  ((MainMenuItem) clickedNodeValue.getData()).getId().split("#");            			            			
        				appTypeId = params[1];
        				stepId = params[2];
        				mainComposerParams.put(MainLayoutComposer.TASK_TYPE_ID, Long.valueOf(appTypeId));
        				mainComposerParams.put(MainLayoutComposer.TASK_STEP_ID, Long.valueOf(stepId));
        			}
        			
        			Executions.createComponents(((MainMenuItem) clickedNodeValue.getData()).getFile(), moduleContent, mainComposerParams);            			            			
                }
            });
        } else {            	
            dataRow.appendChild(new Treecell(contact.getLabel()));
        }
	}
}


public static class MainMenuItem implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String file;
	private String label;
	private boolean isModule;
	
	public MainMenuItem(String id, String file, String label) {
		this(id, file, label, false);
	}
	
	public MainMenuItem(String id, String file, String label, boolean isModule) {
		this.id = id;
		this.file = file;
		this.label = label;
		this.isModule = isModule;
	}
	
	public String getId() {
		return id;
	}
	public String getFile() {
		return file;
	}	
	public String getLabel() {
		return label;
	}	
	public boolean isModule() {
		return isModule;
	}
	public String toString() {
		return "[MenuItem:" + id +", "+file+']';
	}
}


private static class MenuItemTreeNode extends DefaultTreeNode<MainMenuItem> {
	
	private static final long serialVersionUID = 1L;
	private boolean isLeaf;
	
	public MenuItemTreeNode(MainMenuItem data, DefaultTreeNode<MainMenuItem>[] children) {
		this(data, children, false);
	}

	public MenuItemTreeNode(MainMenuItem data, DefaultTreeNode<MainMenuItem>[] children, boolean isLeaf) {
		super(data, children);
		this.isLeaf = isLeaf;
	}
	
    public boolean isLeaf() {
    	return isLeaf;
    }
}

public void showDashBorard() {
	Executions.createComponents(Pages.AR_EVENT_ENTITY, moduleContent, mainComposerParams);
}


private void mouseOverAnchorList(A selectedAnchor) {
	for (A a : moduleGroupAnchorList) {
		a.setClass(null);
		a.setStyle("color:black");
		a.setImage(a.getAttribute(IMAGE_ICON)+"");
	}
	selectedAnchor.setClass("active");
	selectedAnchor.setStyle("color:white");
	selectedAnchor.setImage(selectedAnchor.getAttribute(ACTIVE_IMAGE_ICON)+"");
}

private void mouseOutAnchorList() {
	for (A a : moduleGroupAnchorList) {
		a.setClass(null);
		a.setStyle("color:black");
		a.setImage(a.getAttribute(IMAGE_ICON)+"");
	}
	selectedA.setClass("active");
	selectedA.setStyle("color:white");
	selectedA.setImage(selectedA.getAttribute(ACTIVE_IMAGE_ICON)+"");
}

}
