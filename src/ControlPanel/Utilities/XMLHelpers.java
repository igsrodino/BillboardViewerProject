package ControlPanel.Utilities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public class XMLHelpers {
    /**
     * Generates an element with a specified type, text value, and attributes
     *
     * @param type  the tag name of the element
     * @param value the value of the element
     * @param  attributes  a map of attributes to add to the element
     * @return the element
     */
    public static Element generateElement(String type, String value,
                                          Map<String, String> attributes) {
        Element result = null;
        try {
            Document doc =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            result = doc.createElement(type);
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                result.setAttribute(entry.getKey(), entry.getValue());
            }
            result.appendChild(doc.createTextNode(value));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return result;
    }
    /**
     * Generates an element with a specified type and text value
     *
     * @param type  the tag name of the element
     * @param value the value of the element
     * @return the element
     */
    public static Element generateElement(String type, String value) {
        Element result = null;
        try {
            Document doc =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            result = doc.createElement(type);

            result.appendChild(doc.createTextNode(value));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return result;
    }

    /**
     * Checks if a string contains a valid xml object
     *
     * @param xml an xml string
     * @return true or false depending on whether the xml is valid
     */
    public static boolean isValidBillboard(String xml) {
        try {
            DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return false;
        }
    }

    /**
     * Takes a string containing xml, and returns the document element
     *
     * @param xml an xml string
     * @return the root document Element
     */
    public static Element getDocument(String xml) {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml))).getDocumentElement();
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
    }

    /**
     * Converts a document to a string
     *
     * @param request the Document to convert
     * @return the request as a string
     */
    public static String documentToString(Document request) {
        String result;
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(request), new StreamResult(writer));
            result = writer.getBuffer().toString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return result;
    }
}
