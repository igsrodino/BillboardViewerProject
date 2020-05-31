package Server.Controllers;

import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import Server.Models.BillboardModel;

/**
 * Provides access to billboards
 */
public class BillboardController {
    private BillboardModel model;

    /**
     * Constructor for the Billboard Controller
     *
     * @param model the Billboard model to use to access data.
     */
    public BillboardController(BillboardModel model) {
        this.model = model;

    }


    /**
     * Gets the billboard to be displayed at the current time
     * * Useful for providing the viewer with a billboard, and to return a specific billboard to the control panel on
     * request
     *
     * @param billboardID the id of the billboard to retrieve
     * @return string containing the full XML response
     */
    public String getBillboard(int billboardID) {
        
        boolean status = this.model.getBillboard(billboardID);
        if (!status) {
            return "<response>\n" +
                    "    <type>billboard-not-found</type>\n" +
                    "    <data></data>\n" +
                    "</response>";
        }

        String billboardXMLStringValue = null;
        try {
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


            if (model.getBackground().length() > 0) {
                Attr attr = doc.createAttribute("background");
                attr.setValue(this.model.getBackground());
                billboard.setAttributeNode(attr);

            }

            //Create message elements
            if (model.getMessage().length() > 0) {
                Element messageElement = doc.createElement("message");
                if (model.getMessage_color().length() > 0) {
                    Attr attrType = doc.createAttribute("colour");
                    attrType.setValue(this.model.getMessage_color());
                    messageElement.setAttributeNode(attrType);
                }
                messageElement.appendChild(doc.createTextNode(this.model.getMessage()));
                billboard.appendChild(messageElement);

            }


            //Create picture elements
            if (model.getUrl().length() > 0) {
                Element pictureElement = doc.createElement("picture");
                Attr attrType1 = doc.createAttribute("url");
                attrType1.setValue(this.model.getUrl());
                pictureElement.setAttributeNode(attrType1);

                billboard.appendChild(pictureElement);

            } else if (model.getData().length() > 0) {
                Element pictureElement = doc.createElement("picture");
                Attr attrType1 = doc.createAttribute("data");
                attrType1.setValue(this.model.getData());
                pictureElement.setAttributeNode(attrType1);

                billboard.appendChild(pictureElement);
            }

            //Create information element
            if (model.getInformation().length() > 0) {
                Element informationElement = doc.createElement("information");

                if (model.getInformation_color().length() > 0) {
                    Attr attrType2 = doc.createAttribute("colour");
                    attrType2.setValue(this.model.getInformation_color());
                    informationElement.setAttributeNode(attrType2);
                }
                informationElement.appendChild(doc.createTextNode(this.model.getInformation()));
                billboard.appendChild(informationElement);

            }

            //Transform document to XML string
            billboardXMLStringValue = convertDocumentToString(doc);

        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }

        return billboardXMLStringValue;
    }

    /**
     * Converts received document into String format.
     */
    public static String convertDocumentToString(Document doc) {
        String result = "";
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
            return result;
        }

    }

    /**
     * Gets a list of all the billboards in the system
     *
     * @return string containing a full xml response, with the data element containing one or more billboard objects
     */
    public String getBillboardList() {
        String list_xml = "response";
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element resp = doc.createElement("response");
            Element data = doc.createElement("data");
            Element type = doc.createElement("type");


            Document billboard = this.model.listBillboards();
            if (billboard != null) {
                NodeList billboardList = billboard.getDocumentElement().getChildNodes();
                for (int i = 0; i < billboardList.getLength(); i++) {
                    Node bb = doc.importNode(billboardList.item(i), true);
                    data.appendChild(bb);
                }
                type.appendChild(doc.createTextNode("success"));
            } else {
                type.appendChild(doc.createTextNode("failure"));
            }
            doc.appendChild(resp);
            resp.appendChild(data);
            resp.appendChild(type);
            list_xml = convertDocumentToString(doc);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list_xml;
    }

    /**
     * Deletes the specified billboard from the database
     *
     * @param billboardID the id of the billboard to be deleted
     * @return string containing a response object that has an empty data element.
     */
    public String deleteBillboard(int billboardID) {
//      Use the model.deleteBillboard() method
        this.model.deleteBillboard(billboardID);

        String xmlString = "response";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element (response)
            Element rootElement = doc.createElement("response");
            doc.appendChild(rootElement);

            // type element
            Element type = doc.createElement("type");
            type.appendChild(doc.createTextNode("success"));

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            xmlString = writer.getBuffer().toString();

            return "<response>\n" +
                    "    <type>billboard-deleted</type>\n" +
                    "    <data></data>\n" +
                    "</response>";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlString;
    }

    /**
     * Creates a billboard with the provided information
     *
     * @return a stringified Document object containing a proper Response object.
     */
    public String createBillboard(Document request) throws ParserConfigurationException {
        int id = -1;
        String name = "";
        String background = "";
        String message = "";
        String message_color = "";
        String image = "";
        String imageType = "";
        String information = "";
        String information_color= "";
        int owner = -1;

        request.getDocumentElement().normalize();
        NodeList bbData = request.getElementsByTagName("data").item(0).getChildNodes();
        for (int i = 0; i < bbData.getLength(); i++) {
            if(bbData.item(i).getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) bbData.item(i);
                if(element.getTagName() == "id"){
                    id = Integer.parseInt(element.getTextContent());
                }
                if(element.getTagName() == "name"){
                    name = element.getTextContent();
                }
                if(element.getTagName() == "background"){
                    background = element.getTextContent();
                }
                if(element.getTagName() == "message"){
                    message = element.getTextContent();

                }
                if(element.getTagName() == "message_color"){
                    message_color = element.getTextContent();
                }
                if(element.getTagName() == "information"){
                    information = element.getTextContent();
                }
                if(element.getTagName() == "information_color"){
                    information_color = element.getTextContent();
                }
                if(element.getTagName() == "image"){
                    image = element.getTextContent();
                }
                if(element.getTagName() == "type"){
                    imageType = element.getTextContent();
                }
                if(element.getTagName() == "owner"){
                    owner = Integer.parseInt(element.getTextContent());
                }
            }
        }
        int resType = this.model.createBillboard(id, name, background, message, message_color,
                image, imageType, information, information_color, owner);
        return "<response>\n" +
                "    <type>"+(resType >= 1)+"</type>\n" +
                "    <data></data>\n" +
                "</response>";

    }
}

