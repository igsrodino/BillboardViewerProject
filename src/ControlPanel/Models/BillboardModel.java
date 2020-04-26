package ControlPanel.Models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

import Viewer.Models.BillboardPOJO;
import org.w3c.dom.Node;

/**
 * Contains methods to request and receive data from the server.
 */
public class BillboardModel {
    private int currentBillboardIndex;
    private ArrayList<BillboardPOJO> billboards;

    public BillboardModel(){
        this.currentBillboardIndex = -1;
        this.billboards = new ArrayList<BillboardPOJO>();
    }

    /**
     * Requests the list of billboards from the server and loads them into the array
     */
    public void getBillboardList(){
    }

    /**
     * Sends the currently open billboard to the server
     * @return  true/false based on network
     */
    public boolean saveCurrentBillboard(){
        ArrayList<Element> elements = new ArrayList<Element>();
        BillboardPOJO bb = billboards.get(this.currentBillboardIndex);
        // TODO: Call XMLHelper.generateElement(type, value) for each field, and add the
        //  result to elements.
        // TODO: Call NetworkManager.makeRequest(type, elements.toArray());
        // TODO: Check the returned type tag and return true/false on success/failure
        return false;
    }
    /**
     * Sends a request to delete the billboard. Removes the current billboard from the ArrayList
     * @return  Returns true or false depending on the server response
     */
    public boolean deleteCurrentBillboard() {
        // TODO: Network request
        // TODO: Delete from local store
        return false;
    }

    /**
     * Creates a new billboard object and adds it to the Arraylist of billboards. Doesn't call
     * the server.
     * @param creator  the creator's name
     * @param owner  the creator's user id
     * @return returns true/false on the success of the operation
     */
    public void createBillboard(String creator, int owner) {
        BillboardPOJO bb = new BillboardPOJO();
        bb.setCreator(creator);
        bb.setOwner(owner);
        this.billboards.add(bb);
    }


    /**
     * Gets the current billboard as a Element. Used for the billboard preview
     */
    public Element getCurrentBillboard(){
        try{
            BillboardPOJO bb = this.billboards.get(this.currentBillboardIndex);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element billboard = doc.createElement("billboard");
            if(bb.getBackgroundColour().length() > 0){
                billboard.setAttribute("background", bb.getBackgroundColour());
            }
            if(bb.getInformation().length() > 0){
                Element information = doc.createElement("information");
                information.setTextContent(bb.getInformation());
                if(bb.getInformationColour().length() > 0){
                    information.setAttribute("colour", bb.getInformationColour());
                }
                billboard.appendChild(information);
            }
            if(bb.getMessage().length() > 0){
                Element message = doc.createElement("message");
                message.setTextContent(bb.getMessage());
                if(bb.getMessageColour().length() > 0){
                    message.setAttribute("colour", bb.getMessageColour());
                }
                billboard.appendChild(message);
            }
            if(bb.getPictureURL().length() > 0){
                Element picture = doc.createElement("picture");
                picture.setAttribute("url", bb.getPictureURL());
                billboard.appendChild(picture);
            } else if(bb.getPictureData().length() > 0){
                Element picture = doc.createElement("picture");
                picture.setAttribute("data", bb.getPictureData());
                billboard.appendChild(picture);
            }
            return billboard;
        }catch(Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Gets the billboard pojo at the index. Used to set the currently open billboard.
     * Used to change from one billboard to the next on list click event.
     * @param selectedIndex
     * @return
     */
    public BillboardPOJO getBillboardPOJO(int selectedIndex) {
        this.currentBillboardIndex = selectedIndex;
        return this.billboards.get(selectedIndex);
    }
}
