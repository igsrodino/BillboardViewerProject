package ControlPanel.Models;

import ControlPanel.Controller.UserController;
import ControlPanel.Utilities.NetworkManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

/**
 * Contains methods to request and receive data from the server.
 */
public class BillboardModel {
    // TODO: extend the billboard viewer POJO. Create an ArrayList of POJO objects to keep the
    //  billboard list in. Create get/set methods that allow the user to create, edit, delete
    //  billboards from the list

    // TODO: Add a way to track the currently open billboard internally, so a basic set of
    //  get/set methods can be exposed to the user. - currently open bb is loaded into the
    //  billboardPOJO fields, get/set methods are exposed automatically. This class adds network
    //  access, an array of bbs, and a loadBillboard(int id) method to open a billboard

    // TODO: create methods that request and receive data from the server.
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
     * Creates a new billboard object and adds it to the Arraylist of billboards
     * @param creator  the creator's name
     * @param owner  the creator's user id
     * @return returns true/false on the success of the operation
     */
    public void createBillboard(String creator, int owner) {
    }

    /**
     * Sends a request to delete the billboard. Removes the current billboard from the ArrayList
     */
    public boolean deleteCurrentBillboard() {
        return false;
    }

    /**
     * Gets the current billboard
     */
    public Document getCurrentBillboard(){
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element billboard = doc.createElement("billboard");
            // TODO: Add checks to create a Document with tags that are in use, ie length > 0
        }catch(Exception e){}
        return null;
    }
}
