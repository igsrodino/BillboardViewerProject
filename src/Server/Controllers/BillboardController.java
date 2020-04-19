package Server.Controllers;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import Server.Models.BillboardModel;
import org.w3c.dom.NodeList;

import java.sql.ResultSet;

/**
 * Provides access to billboards
 */
public class BillboardController {
    private BillboardModel model;

    /**
     * Constructor for the Billboard Controller
     * @param model  the Billboard model to use to access data.
     */
    public BillboardController(BillboardModel model){
        this.model = model;

    }



    /**
     * Gets the billboard to be displayed at the current time
     *      * Useful for providing the viewer with a billboard, and to return a specific billboard to the control panel on
     * request
     * @param billboardID  the id of the billboard to retrieve
     * @return string containing the full XML response
     */
    public String getBillboard(int billboardID){

        boolean status = this.model.getBillboard(billboardID);
        if(!status){
            return "<response>\n" +
                    "    <type>billboard-not-found</type>\n" +
                    "    <data></data>\n" +
                    "</response>";
        }

        String billboardXMLStringValue = null;
        try
        {
            //Creating the document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            //Creating the new document
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element resp = doc.createElement("response");
            Element data = doc.createElement("data");
            Element type = doc.createElement("type");
            type.appendChild(doc.createTextNode("success"));
            doc.appendChild(resp);
            resp.appendChild(type);
            resp.appendChild(data);
            //Create billboard root elements

            Element billboard = doc.createElement("billboard");
            data.appendChild(billboard);


            if(model.getBackground().length()>0 ){
                Attr attr = doc.createAttribute("background");
                attr.setValue(this.model.getBackground());
                billboard.setAttributeNode(attr);

            }

//            //Create message elements
            if(model.getMessage().length() >0){
                Element messageElement = doc.createElement("message");
                if(model.getMessage_color().length()>0){
                    Attr attrType = doc.createAttribute("colour");
                    attrType.setValue(this.model.getMessage_color());
                    messageElement.setAttributeNode(attrType);
                }
                messageElement.appendChild(doc.createTextNode(this.model.getMessage()));
                billboard.appendChild(messageElement);

            }



//            //Create picture elements
            if(model.getUrl().length() >0){
                Element pictureElement = doc.createElement("picture");
                Attr attrType1 = doc.createAttribute("url");
                attrType1.setValue(this.model.getUrl());
                pictureElement.setAttributeNode(attrType1);

                billboard.appendChild(pictureElement);

            } else if(model.getData().length() > 0){
                Element pictureElement = doc.createElement("picture");
                Attr attrType1 = doc.createAttribute("data");
                attrType1.setValue(this.model.getData());
                pictureElement.setAttributeNode(attrType1);

                billboard.appendChild(pictureElement);
            }
//
//            //Create information element
            if(model.getInformation().length() > 0){
                Element informationElement = doc.createElement("information");

                if(model.getInformation_color().length() > 0){
                    Attr attrType2 = doc.createAttribute("colour");
                    attrType2.setValue(this.model.getInformation_color());
                    informationElement.setAttributeNode(attrType2);
                }
                informationElement.appendChild(doc.createTextNode(this.model.getInformation()));
                billboard.appendChild(informationElement);

            }

            //Transform document to XML string
            billboardXMLStringValue = this.convertDocumentToString(doc);

        }catch(Exception e){
            System.out.println("Something went wrong.");}

        return billboardXMLStringValue;
    }



    private String convertDocumentToString(Document doc){
            // TODO: add error handling
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        StringWriter writer = new StringWriter();

        try {
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return writer.getBuffer().toString();
    }

    /**
     * Gets a list of all the billboards in the system
     * @return string containing a full xml response, with the data element containing one or more billboard objects
     */
    public String getBillboardList(){
        // The BillboardModel listBillboards method will return a Document containing the billboard objects.
        //Get all Elements and

        // Build a new Document with it, adding in the proper response elements.
        // Return a stringified version of that.
       /* DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        //Creating the new document
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element resp = doc.createElement("response");
        Element data = doc.createElement("data");
        Element type = doc.createElement("type");
        type.appendChild(doc.createTextNode("success"));
        doc.appendChild(resp);
        resp.appendChild(type);
        resp.appendChild(data);*/
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element resp = doc.createElement("response");
            Element data = doc.createElement("data");
            Element type = doc.createElement("type");


            Document billboard = this.model.listBillboards();
            if(billboard != null) {
                type.appendChild(doc.createTextNode("success"));
                NodeList billboardList = billboard.getDocumentElement().getChildNodes();
                for (int i = 0; i < billboardList.getLength(); i++) {
                    Element bb = (Element) billboardList.item(i);
                    data.appendChild(bb);
                }
                resp.appendChild(data);
            }
            else{
                type.appendChild(doc.createTextNode("failure"));
            }
            doc.appendChild(resp);
            resp.appendChild(type);

            // TODO: convert doc to string and return it
            String list_xml  = convertDocumentToString(doc);
            return list_xml;
        }catch(Exception e){

        }
        return "XML String";
        //Will need while loop

    }

    /**
     * Deletes the specified billboard from the database
     * @param billboardID  the id of the billboard to be deleted
     * @return string containing a response object that has an empty data element.
     */
    public String deleteBillboard(int billboardID){
//         Use the model.deleteBillboard() method
        return "Response";
    }

    /**
     * Creates a billboard with the provided information
     * @param background  a hex value representing the color of the background
     * @param message  the billboard message
     * @param message_color  the color of the message text
     * @param image  the url or base64 data string of the image
     * @param imageType  the type of image. The two valid options are: 'url' or 'data'
     * @param information  in depth details about the billboard message
     * @param information_color  the hex code for the information text color
     * @param owner  the id of the user creating the billboard
     * @return a stringified Document object containing a proper Response object.
     */
    public String createBillboard(String background, String message, String message_color, String image,
                                String imageType, String information, String information_color, int owner){
        return "Response";
    }
}

