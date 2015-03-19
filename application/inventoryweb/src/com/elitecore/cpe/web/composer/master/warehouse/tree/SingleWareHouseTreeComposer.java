package com.elitecore.cpe.web.composer.master.warehouse.tree;

import java.util.List;

import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Vlayout;

import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.master.warehouse.CreateWareHouseTreeVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class SingleWareHouseTreeComposer extends BaseModuleViewComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MODULE = "SINGLE_WAREHOUSE_TREE"; 
	private Tree moduleTree = null;
	private Vlayout singleTreeView;
	
	
	
	
	private void prepareWHTree() {
		
		WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
		try {
			CreateWareHouseTreeVO wareHouseTreeVO = wareHouseBD.findChildWareHouses(getViewEntityId());
			if(wareHouseTreeVO!=null) {
				prepareTree(wareHouseTreeVO);
			}
			
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
		
	}

	private void prepareTree(CreateWareHouseTreeVO wareHouseTreeVO) {
		
		moduleTree = new Tree();
		moduleTree.setZclass("z-dottree");
		
		Treechildren subTree = new Treechildren();
		
		Treeitem item = new Treeitem();
		Treerow row = new Treerow();
		Treecell cell = new Treecell(wareHouseTreeVO.getWareHouseName()+" ("+wareHouseTreeVO.getWareHouseTypeName()+")");
		cell.setStyle("color:black;");

		row.appendChild(cell);
		item.appendChild(row);
		Logger.logTrace(MODULE, "WareHouse Childs : "+wareHouseTreeVO.getChildWareHouses());
		Treechildren treechildren = prepareChildTree(wareHouseTreeVO.getChildWareHouses());
		if(treechildren!=null) {
			item.appendChild(treechildren);
		}
		
		subTree.appendChild(item);
		moduleTree.appendChild(subTree);
		
		singleTreeView.appendChild(moduleTree);
	}

	private Treechildren prepareChildTree(List<CreateWareHouseTreeVO> childWareHouses) {
		
		if(childWareHouses!=null && !childWareHouses.isEmpty()) {
			Treechildren subTree = new Treechildren();
			for(CreateWareHouseTreeVO houseTreeVO : childWareHouses) {
				
				Treeitem item = new Treeitem();
				Treerow row = new Treerow();
				Treecell cell = new Treecell(houseTreeVO.getWareHouseName()+" ("+houseTreeVO.getWareHouseTypeName()+")");
				cell.setStyle("color:black;");
				row.appendChild(cell);
				item.appendChild(row);
				Treechildren treechildren = prepareChildTree(houseTreeVO.getChildWareHouses());
				if(treechildren!=null) {
					item.appendChild(treechildren);
				}
				
				subTree.appendChild(item);
			}
			return subTree;
		}
		
		return null;
	}

	
	

	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		prepareWHTree();
		
	}

}
