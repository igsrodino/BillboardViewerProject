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

public class BillboardController {
    private BillboardModel model;
    public BillboardController(BillboardModel model){
        this.model = model;

    }
    public String getBillboard(){

        // Call this.model.getBillboard() to populate the object.
        // Use the getter methods to build a Document object  https://mkyong.com/java/how-to-create-xml-file-in-java-dom/
        // Convert the Document to a string (gonna need to google it. Not complicated though)
        // Return the stringified xml.
        // Don't forget error handling (try catch, exceptions etc)
        this.model.getBillboard();
        String billboardXMLStringValue = null;
        try
        {
            //Creating the document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            //Creating the new document
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            //Create billboard root elements
            Element element = doc.createElement("billboard");
            doc.appendChild(element);
            element = doc.createElement("message");
            /*
            // set attribute to staff element
		Attr attr = doc.createAttribute("id");
		attr.setValue("1");
		staff.setAttributeNode(attr);
             */
            Attr attr = doc.createAttribute("background");
            attr.setValue(this.model.getBackground());
            element.setAttributeNode(attr);

            //Create message elements
            Element messageElement = doc.createElement("message");
            Attr attrType = doc.createAttribute("colour");
            attrType.setValue(this.model.getMessage_color());
            messageElement.setAttributeNode(attrType);
            messageElement.appendChild(doc.createTextNode(this.model.getMessage()));
            element.appendChild(messageElement);


            //Create picture elements
            Element pictureElement = doc.createElement("picture");
            Attr attrType1 = doc.createAttribute("url");
            attrType1.setValue(this.model.getUrl());
            pictureElement.setAttributeNode(attrType1);
            element.appendChild(pictureElement);

            //Create information element
            Element informationElement = doc.createElement("information");
            Attr attrType2 = doc.createAttribute("colour");
            attrType2.setValue(this.model.getInformation_color());
            informationElement.setAttributeNode(attrType2);
            informationElement.appendChild(doc.createTextNode(this.model.getInformation()));
            element.appendChild(informationElement);

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


    }

