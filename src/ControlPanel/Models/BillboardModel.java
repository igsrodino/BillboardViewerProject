package ControlPanel.Models;

import Viewer.Models.BillboardPOJO;

import javax.swing.*;
import java.util.ArrayList;

public class BillboardModel {
    private ArrayList<BillboardPOJO> billboards;

    public BillboardModel() {
        this.billboards = new ArrayList<BillboardPOJO>();
    }

    public ListModel getLocalList() {
        DefaultListModel model = new DefaultListModel();
        for (BillboardPOJO el : this.billboards) {
            model.addElement(el.getName());
        }
        return model;
    }

    /**
     * Adds a new billboard
     *
     * @param bb the billboard to add
     * @return  the index of the billboard
     */
    public int setBillboard(BillboardPOJO bb) {
        this.billboards.add(bb);
        return this.billboards.lastIndexOf(bb);
    }

    /**
     * Updates an existing billboard
     *
     * @param index  the index to update
     * @param bb  the billboard to insert
     * @return  the index of the billboard
     */
    public int setBillboard(int index, BillboardPOJO bb) {
        if (index < 0) {
            return -1;
        }
        this.billboards.set(index, bb);
        return index;
    }

    public BillboardPOJO getBillboard(int index) {
        if (index < 0) {
            return null;
        }
        return billboards.get(index);
    }

    public void deleteBillboard(int index) {
        if (index < 0) return;
        this.billboards.remove(index);
        // TODO: Network request to delete the billboard
    }

    public void saveBillboard(int index) {
        if (index < 0) return;
        //TODO: Network request to save the billboard;
    }

    public void getBillboardList() {
        // TODO: Network request to get list of billboards
        // TODO: Replace contents of billboards with list from server
    }
}
