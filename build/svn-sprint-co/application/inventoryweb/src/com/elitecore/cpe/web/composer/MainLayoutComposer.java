package com.elitecore.cpe.web.composer;


import java.util.ArrayList;
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
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.A;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Panelchildren;
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
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.policy.CPECommonConstants;
import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleGroupData;
import com.elitecore.cpe.bl.delegates.system.internal.SystemInternalBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseComposer;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.DefaultActionUtil;


public class MainLayoutComposer extends BaseComposer {

	private static final long serialVersionUID = 1L;

	public static final String TASK_TYPE_ID = "_TaskTypeIdId";
	public static final String TASK_STEP_ID = "_TaskStepIdId";

	private Panelchildren leftNavPanelChildren;
	private Tabbox leftNavTab = new Tabbox();
//	private Div moduleContent;
	private Map<String, Object> mainComposerParams;
	private MenuItemTreeNode rootMenu = null;
	private MenuItemTreeNode level1Menu = null;
//	private MenuItemTreeNode level2Menu = null;
	private LinkedList leafMenuCache = null;
	private Tabpanel tabPannel;
	private Tabpanels tabPanels;
	private West westPanel;
	private Label userLabel;
//	private Label lastLoginLabel;
	private Image homeImg;
	private Image backImg;
	private Image forwardImg;
	private Image refreshImg;
	private Image stopImg;
	private Image printImg;
//	private Image aboutusImg;
//	private Image profileImg;
//	private Image helpImg;
	private Div topNavDiv;
	
	private List<A> moduleGroupAnchorList;
	public static final String IMAGE_ICON = "_ImageIcon";
	public static final String ACTIVE_IMAGE_ICON = "_ActiveImageIcon";

	private static final String MODULE = "_MainLayoutComposer";
	private A selectedA;
	
	private String strCurrentTarget = "user-home.zul";
	
	private void createTopNavigationPanel() {
		moduleGroupAnchorList = new ArrayList<A>();
		
		SystemInternalBD internalBD = new SystemInternalBD(getBDSessionContext());
		List<SystemModuleGroupData> moduleGroupDatas = null;
		
		try {
			moduleGroupDatas = internalBD.getSystemModuleData();
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
		
		A defaultAnchor = null;
		
		Ul ul = new Ul();
		Li li = new Li();
		A l = new A();

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
		
		
		List<Tab> lstTab=new LinkedList<Tab>();
		Tab tab = null;
		Tabpanel tabPannel = null;
		
		Tabpanels tabPanels = new Tabpanels();
		tabPanels.setStyle("border: 0px; padding: 0px; margin: 0px; background-color: #F5F5F5;");
		
		
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
				Executions.sendRedirect("user-home.zul");	

		    	List<Tab> lstTab=new LinkedList<Tab>();
				/*Tab tab = null;
				Tabpanel tabPannel = null;*/
				
				Tabpanels tabPanels = new Tabpanels();
				tabPanels.setStyle("border: 0px; padding: 0px; margin: 0px; background-color: #F5F5F5;");
				//Adding Default Actions. 
				for(SystemModuleData module : DefaultActionUtil.getDefaultModule()){
///*Edit*/					prepareTabsAndPanels(module, lstTab, tabPanels);
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
		

		if(moduleGroupDatas!=null && !moduleGroupDatas.isEmpty()){
			for(final SystemModuleGroupData moduleGroupData : moduleGroupDatas){
				li = new Li();
				Logger.logTrace(MODULE, moduleGroupData+"<--------");				
				
				l = new A();
								
				if(moduleGroupData.getIconUrl()!=null){
					l.setAttribute(IMAGE_ICON, moduleGroupData.getIconUrl());
					l.setImage(moduleGroupData.getIconUrl());			
				}


				
				if(moduleGroupData.getActiveIconUrl()!=null){
					l.setAttribute(ACTIVE_IMAGE_ICON, moduleGroupData.getActiveIconUrl());
					l.setHoverImage(moduleGroupData.getActiveIconUrl());			
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
						if(leftNavTab.getChildren()!=null){
							Components.removeAllChildren(leftNavTab);
							Components.removeAllChildren(moduleContent);
						}
//						createMenu(CPECommonConstants.ADMIN_PANEL);
						Executions.createComponents(Pages.USER_HOME, moduleContent,null);
						List<Tab> lstTab=new LinkedList<Tab>();

						Tabpanels tabPanels = new Tabpanels();
						tabPanels.setStyle("border: 0px; padding: 0px; margin: 0px; background-color: #F5F5F5;");
						
						if(moduleGroupData.getSystemModules()!=null && !moduleGroupData.getSystemModules().isEmpty()){
							for(SystemModuleData moduleData : moduleGroupData.getSystemModules()){
								
///*Edit*/								prepareTabsAndPanels(moduleData,lstTab,tabPanels);

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
		}
	
		
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
		
		
		moduleGroupAnchorList.add(l);
		topNavDiv.appendChild(ul);
		//end loop
		
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
	
	
	public void createMenu(String panel) {
//		Logger.logDebug(MODULE, "panel :"+panel);
		SystemInternalBD internalBD = new SystemInternalBD(getBDSessionContext());
		westPanel.setSplittable(false);
		westPanel.setSize("172px");
		
		leftNavPanelChildren.getChildren().clear();

		Hbox hbox = new Hbox();
		Menubar menuBar = new Menubar();
		menuBar.setOrient("vertical");
		menuBar.setAutodrop(true);
		Menu rootXMenu = null;		
		Menuitem rootXMenuSingleItem = null;
		Menu level1XMenu = null;
		Menupopup level1XMenuPopup = null;
		Menuitem level1XMenuSingleItem = null;
		Menupopup level2XMenuPopup = null;
		Menuitem level2XMenuSingleItem = null;
		

		leafMenuCache = new LinkedList();
		try {
			List<SystemModuleGroupData> moduleGroupList = internalBD.getSystemModuleData();
			if(moduleGroupList!=null && !moduleGroupList.isEmpty()) {

				for(SystemModuleGroupData moduleGroupData : moduleGroupList) {
					for(SystemModuleData moduleData : moduleGroupData.getSystemModules()) {
						
						if(moduleData!=null && moduleData.getPanel().equals(panel)) {
							rootXMenu = new Menu("" + moduleData.getName());
							rootXMenu.setId(moduleData.getName());
							level1XMenuPopup = new Menupopup();
							Menupopup menupopup = createMenuitems(moduleData.getSystemActions(), level1XMenuPopup);
							rootXMenu.appendChild(menupopup);
							menuBar.appendChild(rootXMenu);
						}
						
						
					}
				}
				
				
			
			}
			
			String logout = "Logout";
			
			rootXMenuSingleItem = new Menuitem(logout);
			rootXMenuSingleItem.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					System.out.println("Logout is called");
					doLogout();
				}
			});
			menuBar.appendChild(rootXMenuSingleItem);
			
			menuBar.setAutodrop(true);
			menuBar.setMold("default");
			hbox.appendChild(menuBar);
			leftNavPanelChildren.appendChild(hbox);
			Image brandLogoImage = new Image("/images/client_brand_logo.jpg");		
			Div delimDiv = new Div();
			delimDiv.setHeight("6px");		
			Div imgDiv = new Div();		
			imgDiv.setAlign("center");		
			imgDiv.appendChild(brandLogoImage);
			leftNavPanelChildren.appendChild(delimDiv);
			leftNavPanelChildren.appendChild(imgDiv);
			
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
	}
	
	public Menupopup createMenuitems(List<SystemActionData> systemActionData, Menupopup level1XMenuPopup) {
		
		if(systemActionData!=null && !systemActionData.isEmpty()) {
			for(SystemActionData actionData : systemActionData) {
			
				if(actionData.getEnableVisible().equals('Y')) {
					if(actionData.getChildActions()!=null && !actionData.getChildActions().isEmpty()) {
						
						Menu rootXMenu = new Menu("" + actionData.getName());
						rootXMenu.setId(actionData.getName());
						Menupopup level2XMenuPopup = new Menupopup();
						Boolean isChild = false;
						level2XMenuPopup = createSubMenuItems(actionData.getChildActions() , level2XMenuPopup,isChild);
						if(isChild) {
							rootXMenu.appendChild(level2XMenuPopup);
							level1XMenuPopup.appendChild(rootXMenu); 
						} else {
							Menuitem level1XMenuSingleItem = new Menuitem("" + actionData.getName());
							level1XMenuSingleItem.setId((actionData.getName()).replace(" ", "").replace("&", "").replace("/", "").replace("-", "").toLowerCase());
							final String tempURL =  actionData.getZulPageUrl()+"";						
							level1XMenuSingleItem.addEventListener(Events.ON_CLICK,
									new EventListener<Event>() {
								@Override
								public void onEvent(Event event) throws Exception {
									redirect("" + tempURL);
								}
							});	
							
							level1Menu = new MenuItemTreeNode(new MainMenuItem(actionData.getName()+ "",actionData.getZulPageUrl()+ "",actionData.getName()+ ""), null,false);
							leafMenuCache.add(level1Menu);
							
							level1XMenuPopup.appendChild(level1XMenuSingleItem);
						}
						
						
					} else {
						Menuitem level1XMenuSingleItem = new Menuitem("" + actionData.getName());
						level1XMenuSingleItem.setId((actionData.getName()).replace(" ", "").replace("&", "").replace("/", "").replace("-", "").toLowerCase());
						final String tempURL =  actionData.getZulPageUrl()+"";						
						level1XMenuSingleItem.addEventListener(Events.ON_CLICK,
								new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								redirect("" + tempURL);
							}
						});	
						
						level1Menu = new MenuItemTreeNode(new MainMenuItem(actionData.getName()+ "",actionData.getZulPageUrl()+ "",actionData.getName()+ ""), null,false);
						leafMenuCache.add(level1Menu);
						
						level1XMenuPopup.appendChild(level1XMenuSingleItem);
					}
				}
					
				}
		}
		return level1XMenuPopup;
	}
	
	
	public Menupopup createSubMenuItems(List<SystemActionData> systemActionData,Menupopup level2XMenuPopup,Boolean isChild) {
		
		for(SystemActionData actionData : systemActionData) {
			if(actionData.getEnableVisible().equals('Y')) {
			//	isChild = true;
				Menuitem level1XMenuSingleItem = new Menuitem("" + actionData.getName());
				level1XMenuSingleItem.setId((actionData.getName()).replace(" ", "").replace("&", "").replace("/", "").replace("-", "").toLowerCase());
				final String tempURL =  actionData.getZulPageUrl()+"";						
				level1XMenuSingleItem.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						redirect("" + tempURL);
					}
				});
				
				level1Menu = new MenuItemTreeNode(new MainMenuItem(actionData.getName()+ "",actionData.getZulPageUrl()+ "",actionData.getName()+ ""), null,false);
				leafMenuCache.add(level1Menu);
				
				level2XMenuPopup.appendChild(level1XMenuSingleItem);
			}
			
		}
		
		return level2XMenuPopup;
		
	}
	
	
	private void doLogout() {
		doLogout(getBDSessionContext().getBLSession().getUsername());
		Executions.sendRedirect("/");
//		Executions.sendRedirect("/logout-screen.zul");
	}
	

	

	public void onChanging$searchBox(InputEvent event) {
		String searchKeyWord = event.getValue().trim();

		westPanel.setSplittable(true);
		leftNavPanelChildren.getChildren().clear();
		leftNavTab.getChildren().clear();

		if (searchKeyWord.trim().equalsIgnoreCase("")) {
			
			/**
			 * EDit Search Box Menu
			 */
			
			createMenu(CPECommonConstants.USER_PANEL);
		} else {

			LinkedList<MainMenuItem> searchResult = searchFunction(searchKeyWord);

			Tab searchTab = new Tab("Search Result - [" + searchResult.size() + "]");

			tabPannel = new Tabpanel();
			tabPanels = new Tabpanels();

			if(searchResult.size() > 0) {
				rootMenu = new MenuItemTreeNode(new MainMenuItem("Search Result","", "Search Result", true), null);				
				
				for (MainMenuItem searchedItem : searchResult) {
					rootMenu.add(new MenuItemTreeNode(searchedItem, null, true));
				}
	
				tabPannel.appendChild(getTreeModel(rootMenu));
			} else {				
				Div searchDiv = new Div();
				Label searchLabel = new Label();
				searchLabel.setValue("0 Menu Items Found");
				searchLabel.setStyle("background-color: #005197; color: #FFFFFF; vertical-align: top;");
				searchDiv.appendChild(searchLabel);
				tabPannel.appendChild(searchDiv);
			}
			tabPanels.appendChild(tabPannel);

			Tabs leftNavTabs = new Tabs();
			leftNavTabs.appendChild(searchTab);

			leftNavTab.appendChild(leftNavTabs);
			leftNavTab.appendChild(tabPanels);
			leftNavPanelChildren.appendChild(leftNavTab);
		}
	}
	
	private LinkedList<MainMenuItem> searchFunction(String searchKeyWord) {
		LinkedList<MainMenuItem> searchResult = new LinkedList<MainMenuItem>();
		MenuItemTreeNode menuItemTreeNode = null;
		MainMenuItem mainMenuItem = null;
		for (int i = 0; i < leafMenuCache.size(); i++) {
			menuItemTreeNode = (MenuItemTreeNode) leafMenuCache.get(i);
			mainMenuItem = menuItemTreeNode.getData();
			if(searchKeyWord.toLowerCase().contains("*")) {				
//				WildCardMatcher matcher = new WildCardMatcher(searchKeyWord.toLowerCase());
//				if(matcher.matches(mainMenuItem.getLabel().toLowerCase()))
//				{
//					searchResult.add(mainMenuItem);
//				}
			}else if(mainMenuItem.getLabel().toLowerCase().contains(searchKeyWord.toLowerCase())) {
				searchResult.add(mainMenuItem);
			}
		}
		return searchResult;
	}

	private Tree getTreeModel(MenuItemTreeNode menuItemTreeNode) {

		Tree tree = new Tree();

		DefaultTreeModel<MainMenuItem> treeModel = new DefaultTreeModel<MainLayoutComposer.MainMenuItem>(
				menuItemTreeNode) {
			private static final long serialVersionUID = 1L;

			public boolean isLeaf(TreeNode<MainMenuItem> node) {
				return node.isLeaf();
			}
		};

		tree.setItemRenderer(new MenuItemTreeRenderer());
		tree.setModel(treeModel);
		tree.setClass("mainMenuTree");
		tree.setVflex(true);
		tree.setHflex("true");
		return tree;
	}

	private final class MenuItemTreeRenderer implements
			TreeitemRenderer<MenuItemTreeNode> {
		@Override
		public void render(final Treeitem treeItem, MenuItemTreeNode treeNode,
				int index) throws Exception {
			MenuItemTreeNode ctn = treeNode;
			MainMenuItem contact = (MainMenuItem) ctn.getData();
			Treerow dataRow = new Treerow();
			dataRow.setParent(treeItem);
			treeItem.setValue(ctn);
			if (!contact.isModule()) { // Contact Row
				Treecell treeCell = new Treecell(contact.getLabel());
				dataRow.appendChild(treeCell);
				dataRow.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								MenuItemTreeNode clickedNodeValue = (MenuItemTreeNode) ((Treeitem) event
										.getTarget().getParent()).getValue();

								if (moduleContent != null
										&& moduleContent.getChildren() != null) {
									moduleContent.getChildren().clear();
								}

								String appTypeId;
								String stepId;

								if (((MainMenuItem) clickedNodeValue.getData())
										.getId().contains("#")) {
									String[] params = ((MainMenuItem) clickedNodeValue
											.getData()).getId().split("#");
									appTypeId = params[1];
									stepId = params[2];
									mainComposerParams.put(
											MainLayoutComposer.TASK_TYPE_ID,
											Long.valueOf(appTypeId));
									mainComposerParams.put(
											MainLayoutComposer.TASK_STEP_ID,
											Long.valueOf(stepId));
								}
								
//								Iframe iFrame = new Iframe();

								String url = ((MainMenuItem) clickedNodeValue
										.getData()).getFile() + "";
								
								if (moduleContent != null
										&& moduleContent.getChildren() != null) {
									moduleContent.getChildren().clear();
								}
								
								strCurrentTarget = url;
								Executions.createComponents(url, moduleContent, null);
								
/*								iFrame.setWidth("100%");
								iFrame.setHeight("100%");	
								
								iFrame.setSrc(url);
								iFrame.setParent(moduleContent);*/

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

		public MainMenuItem(String id, String file, String label,
				boolean isModule) {
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
			return "[MenuItem:" + id + ", " + file + ']';
		}
	}

	private static class MenuItemTreeNode extends DefaultTreeNode<MainMenuItem> {

		private static final long serialVersionUID = 1L;
		private boolean isLeaf;

		public MenuItemTreeNode(MainMenuItem data,
				DefaultTreeNode<MainMenuItem>[] children) {
			this(data, children, false);
		}

		public MenuItemTreeNode(MainMenuItem data,
				DefaultTreeNode<MainMenuItem>[] children, boolean isLeaf) {
			super(data, children);
			this.isLeaf = isLeaf;
		}

		public boolean isLeaf() {
			return isLeaf;
		}
	}
	
	private void redirect(String target) {
		
		if (moduleContent != null
				&& moduleContent.getChildren() != null) {
			moduleContent.getChildren().clear();
		}
		
		strCurrentTarget = target;
		Executions.createComponents(target, moduleContent, null);
		/*Iframe iFrame = new Iframe();
		iFrame.setWidth("100%");
		iFrame.setHeight("100%");	
		iFrame.setSrc(target);
		iFrame.setParent(moduleContent);*/
	}
	
	
	
	private void refresh(String target) {
		
		if (moduleContent != null
				&& moduleContent.getChildren() != null) {
			moduleContent.getChildren().clear();
		}
		
		strCurrentTarget = target;
		if(!target.equalsIgnoreCase("user-home.zul")) {
			Executions.createComponents(target, moduleContent, null);
		}
	}
	
	private void setHeaderLabels() {

		userLabel.setValue("Welcome " + getBDSessionContext().getBLSession().getName());
	}
	
	
	private void addHeaderEventListers() {
		
		/* Home Image Event Listeners */
		homeImg.addEventListener(Events.ON_MOUSE_OVER,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				homeImg.setSrc("/images/home-hover.jpg");
			}
		});
		
		homeImg.addEventListener(Events.ON_MOUSE_OUT,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				homeImg.setSrc("/images/home.jpg");
			}
		});
		
		/* Back Image Event Listeners */
		backImg.addEventListener(Events.ON_MOUSE_OVER,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				backImg.setSrc("/images/back-hover.jpg");
			}
		});
		
		backImg.addEventListener(Events.ON_MOUSE_OUT,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				backImg.setSrc("/images/back.jpg");
			}
		});
		
		/* Forward Image Event Listeners */
		forwardImg.addEventListener(Events.ON_MOUSE_OVER,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				forwardImg.setSrc("/images/forward-hover.jpg");
			}
		});
		
		forwardImg.addEventListener(Events.ON_MOUSE_OUT,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				forwardImg.setSrc("/images/forward.jpg");
			}
		});
		
		/* Refresh Image Event Listeners */
		refreshImg.addEventListener(Events.ON_MOUSE_OVER,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				refreshImg.setSrc("/images/refresh-hover.jpg");
			}
		});
		
		refreshImg.addEventListener(Events.ON_MOUSE_OUT,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				refreshImg.setSrc("/images/refresh.jpg");
			}
		});
		
		refreshImg.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
//				Executions.sendRedirect("user-home.zul");
				refresh(strCurrentTarget);
			}
		});
		
		/* Stop Image Event Listeners */
		stopImg.addEventListener(Events.ON_MOUSE_OVER,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				stopImg.setSrc("/images/stop-hover.jpg");
			}
		});
		
		stopImg.addEventListener(Events.ON_MOUSE_OUT,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				stopImg.setSrc("/images/stop.jpg");
			}
		});
		
		/* Print Image Event Listeners */
		printImg.addEventListener(Events.ON_MOUSE_OVER,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				printImg.setSrc("/images/print-hover.jpg");
			}
		});
		
		printImg.addEventListener(Events.ON_MOUSE_OUT,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				printImg.setSrc("/images/print.jpg");
			}
		});
		
		/* About Us Image Event Listeners */
		/*aboutusImg.addEventListener(Events.ON_MOUSE_OVER,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				aboutusImg.setSrc("/images/aboutus-hover.jpg");
			}
		});
		
		aboutusImg.addEventListener(Events.ON_MOUSE_OUT,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				aboutusImg.setSrc("/images/aboutus.jpg");
			}
		});
		
		aboutusImg.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				redirect("/jsp/About.jsp");
			}
		});
		
		 Profile Image Event Listeners 
		profileImg.addEventListener(Events.ON_MOUSE_OVER,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				profileImg.setSrc("/images/profile-over.jpg");
			}
		});
		
		profileImg.addEventListener(Events.ON_MOUSE_OUT,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				profileImg.setSrc("/images/profile.jpg");
			}
		});
		
		profileImg.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				redirect("/jsp/Profile.jsp");
			}
		});
		
		 Help Image Event Listeners 
		helpImg.addEventListener(Events.ON_MOUSE_OVER,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				helpImg.setSrc("/images/help-hover.jpg");
			}
		});
		
		helpImg.addEventListener(Events.ON_MOUSE_OUT,
				new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				helpImg.setSrc("/images/help.jpg");
			}
		});*/
	}

	

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		if(getBDSessionContext()==null || getBDSessionContext().getBLSession()==null) {
			Executions.sendRedirect("/");
		} else {
			setHeaderLabels();
//			generateMenu();
			createMenu(CPECommonConstants.USER_PANEL);
			addHeaderEventListers();
			createTopNavigationPanel();
//			Executions.createComponents(Pages.ADMIN_HOME, moduleContent,null);
		}
		
		
	}
}