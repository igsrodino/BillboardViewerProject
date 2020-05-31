package ControlPanel.Models;

import Viewer.Models.BillboardPOJO;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Different methods to alter billboards
 */
public class BillboardModel {
    private ArrayList<BillboardPOJO> billboards;

    public BillboardModel() {
        this.billboards = new ArrayList<BillboardPOJO>();
    }
    public DefaultListModel getLocalList(){
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
        return this.billboards.indexOf(bb);
    }

    /**
     * Updates an existing billboard
     *
     * @param index  the index to update
     * @param bb  the billboard to insert
     * @return  the index of the billboard
     */
    public int setBillboard(int index, BillboardPOJO bb) {
        if (index == -1) {
            return -1;
        }
        System.out.println("Model, set: " + index);
        this.billboards.set(index, bb);
        return index;
    }

    /**
     * Gets the billboard object at the specified index
     * @param index  the index to retrieve
     * @return  a billboard object
     */
    public BillboardPOJO getBillboard(int index) {
        if (index == -1) {
            return null;
        }
        return billboards.get(index);
    }

    /**
     * Deletes the billboard at the specified index
     * @param index  the index to delete
     */
    public void deleteBillboard(int index) {
        if (index < 0) return;
        this.billboards.remove(index);
    }

    /**
     * Saves the billboard at the index to the server
     * @param index  the index to save
     */
    public void saveBillboard(int index) {
        if (index < 0) return;
        //TODO: Network request to save the billboard.
    }

    /**
     * Fetches a list of billboards from the server
     */
    public void getBillboardList() {
        // TODO: Network request to get list of billboards
        // TODO: Replace contents of billboards with list from server
    }
}
