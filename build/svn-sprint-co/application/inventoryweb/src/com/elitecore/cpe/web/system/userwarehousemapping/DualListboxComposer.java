package com.elitecore.cpe.web.system.userwarehousemapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.elitecore.cpe.bl.data.common.ComboData;

public class DualListboxComposer extends Div implements IdSpace {
    private static final long serialVersionUID = 5183321186606483396L;
     
    @Wire
    private Listbox candidateLb;
    @Wire
    private Listbox chosenLb;
 
    private ListModelList<ComboData> candidateModel;
    private ListModelList<ComboData> chosenDataModel;
 
    public DualListboxComposer() {
        Executions.createComponents("/WEB-INF/pages/core/system/userwarehousemap/v_dualListbox.zul", this, null);
        Selectors.wireComponents(this, this, false);
        Selectors.wireEventListeners(this, this);
        chosenDataModel = new ListModelList<ComboData>();
        chosenLb.setModel(chosenDataModel);
        chosenDataModel.setMultiple(true);
    }
 
    @Listen("onClick = #chooseBtn")
    public void chooseItem() {
        Events.postEvent(new ChooseEvent(this, chooseOne()));
    }
 
    @Listen("onClick = #removeBtn")
    public void unchooseItem() {
        Events.postEvent(new ChooseEvent(this, unchooseOne()));
    }
 
    @Listen("onClick = #chooseAllBtn")
    public void chooseAllItem() {
        chosenDataModel.addAll(candidateModel);
        candidateModel.clear();
    }
 
    @Listen("onClick = #removeAllBtn")
    public void unchooseAll() {
        candidateModel.addAll(chosenDataModel);
        chosenDataModel.clear();
    }
 
 
 
    /**
     * Set new candidate ListModelList.
     *
     * @param candidate
     *            is the data of candidate list model
     */
    public void setModel(List<ComboData> candidate) {
        candidateLb.setModel(this.candidateModel = new ListModelList<ComboData>(candidate));
        this.candidateModel.setMultiple(true);
        chosenDataModel.clear();
    }
 
    /**
     * @return current chosen data list
     */
    public List<ComboData> getChosenDataList() {
        return new ArrayList<ComboData>(chosenDataModel);
    }
 
    public void chooseOne( Set<ComboData> comboDatas) {
        Set<ComboData> set = comboDatas;
        chosenDataModel.addAll(set);
        candidateModel.removeAll(set);
        Events.postEvent(new ChooseEvent(this, set));
    }
    
    private Set<ComboData> chooseOne() {
        Set<ComboData> set = candidateModel.getSelection();
        chosenDataModel.addAll(set);
        candidateModel.removeAll(set);
        return set;
    }
 
    private Set<ComboData> unchooseOne() {
        Set<ComboData> set = chosenDataModel.getSelection();
        candidateModel.addAll(set);
        chosenDataModel.removeAll(set);
        return set;
    }
 
    // Customized Event
    private static class ChooseEvent extends Event {
        private static final long serialVersionUID = -7334906383953342976L;
 
        public ChooseEvent(Component target, Set<ComboData> data) {
            super("onChoose", target, data);
        }
    }
}