package Server.Controllers;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import Server.Models.BillboardModel;

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
     * Useful for providing the viewer with a billboard, and to return a specific billboard to the control panel on
     * request
     * @param billboardID  the id of the billboard to retrieve
     * @return string containing the full XML response
     */
    public String getBillboard(int billboardID){

        // Call this.model.getBillboard() to populate the object.
        // Use the getter methods to build a Document object  https://mkyong.com/java/how-to-create-xml-file-in-java-dom/
        // Convert the Document to a string (gonna need to google it. Not complicated though)
        // Return the stringified xml.
        // Don't forget error handling (try catch, exceptions etc)

        boolean x = this.model.getBillboard(billboardID);
        System.out.println(this.model.getMessage());
        String billboardXMLStringValue = null;
        try
        {
            //Creating the document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            //Creating the new document
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            //Create billboard root elements
            Element root = doc.createElement("billboard");
            doc.appendChild(root);

            if(model.getBackground().length()>0 ){
                Attr attr = doc.createAttribute("background");
                attr.setValue(this.model.getBackground());
                root.setAttributeNode(attr);
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
                root.appendChild(messageElement);
            }



//            //Create picture elements
            if(model.getUrl().length() >0){
                Element pictureElement = doc.createElement("picture");
                Attr attrType1 = doc.createAttribute("url");
                attrType1.setValue(this.model.getUrl());
                pictureElement.setAttributeNode(attrType1);
                root.appendChild(pictureElement);
            } else if(model.getData().length() > 0){
                Element pictureElement = doc.createElement("picture");
                Attr attrType1 = doc.createAttribute("data");
                attrType1.setValue(this.model.getData());
                pictureElement.setAttributeNode(attrType1);
                root.appendChild(pictureElement);
            }
//
//            //Create information element
            if(model.getInformation().length() > 0){
                Element informationElement = doc.createElement("information");
                Attr attrType2 = doc.createAttribute("colour");
                attrType2.setValue(this.model.getInformation_color());
                informationElement.setAttributeNode(attrType2);
                informationElement.appendChild(doc.createTextNode(this.model.getInformation()));
                root.appendChild(informationElement);
            }

            //Transform document to XML string
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();

            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            billboardXMLStringValue = writer.getBuffer().toString();

        }catch(Exception e){
            System.out.println("Something went wrong.");}

        return billboardXMLStringValue;
    }





    /**
     * Gets a list of all the billboards in the system
     * @return string containing a full xml response, with the data element containing one or more billboard objects
     */
    public String getBillboardList(){
        // The BillboardModel listBillboards method will return a Document containing the billboard objects.
        // Build a new Document with it, adding in the proper response elements.
        // Return a stringified version of that.
        return "XML String";
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

