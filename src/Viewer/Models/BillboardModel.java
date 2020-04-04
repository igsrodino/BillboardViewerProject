package Viewer.Models;

// Contains methods to call the server and return the response in an appropriate format
public class BillboardModel {
    public BillboardModel(){
        // TODO: Setup the model class with any socket related fields (address, and port come to
        //  mind)
    }

    public BillboardPOJO getBillboard(){
        // TODO: Open a socket to the server and request the current billboard (GetBillboard
        //  route). The returned string will contain a full response object, not just the
        //  billboard.
        // TODO: Parse the string into a Document
        // TODO: Create a BillboardPOJO (Plain old java object) from the "billboard" element
        // NodeList nodeList = doc.getElementsByTagName("billboard");
        // BillboardPOJO bb = new BillboardPOJO();
        // for (int i = 0; i < nodeList.getLength(); i++) {
        //            bb.add(getBillboard(nodeList.item(i)));
        //        }
        // TODO: Return the newly populated BillboardPOJO
        return null;
    }
    // TODO: Add in two helper methods to aid in parsing the Document into BillboardPOJO
    // Notice that the methods are not static
    // You'll need to add two private methods here, see https://www.journaldev.com/898/read-xml-file-java-dom-parser
    // Here's the method stubs, you'll need to convert them for the billboard object:
    // private Employee getEmployee(Node node) & private String getTagValue(String
    // tag, Element element)
    //
}
