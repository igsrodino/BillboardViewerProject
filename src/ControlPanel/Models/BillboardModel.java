package ControlPanel.Models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

import Viewer.Models.BillboardPOJO;
/**
 * Contains methods to request and receive data from the server.
 */
public class BillboardModel {
    private int currentBillboardIndex;
    private ArrayList<BillboardPOJO> billboards;

    public BillboardModel(){
        this.currentBillboardIndex = 0;
        this.billboards = new ArrayList<BillboardPOJO>();
    }

    /**
     * Requests the list of billboards from the server and loads them into the array
     */
    public void getBillboardList(){
    }

    /**
     * Sends the currently open billboard to the server
     */
    public boolean saveCurrentBillboard(){
        ArrayList<Element> elements = new ArrayList<Element>();
        // TODO: Call NetworkManager.generateElement(type, value) for each field, and add the
        //  result to elements.
        // TODO: Call NetworkManager.makeRequest(type, elements.toArray());
        // TODO: Check the returned type tag and return true/false on success/failure
        return false;
    }
    /**
     * Sends a request to delete the billboard. Removes the current billboard from the ArrayList
     */
    public boolean deleteCurrentBillboard() {
        return false;
    }

    /**
     * Creates a new billboard object and adds it to the Arraylist of billboards
     * @param creator  the creator's name
     * @param owner  the creator's user id
     * @return returns true/false on the success of the operation
     */
    public void createBillboard(String creator, int owner) {
    }


    /**
     * Gets the current billboard as a Document
     */
    public Element getCurrentBillboard(){
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element billboard = doc.createElement("billboard");
            // TODO: Using XMLHelpers.generateElement, create a full billboard object. Use checks
            //  to only add elements that are in use(

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void loadBillboard(int selectedIndex) {
        // TODO: change current billboard to the one in the index
    }
}
