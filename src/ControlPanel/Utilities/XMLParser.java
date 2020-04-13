package ControlPanel.Utilities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class XMLParser {
    public static boolean isValidBillboard(String xml){
        try{
            DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            return true;
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return false;
        }
    }
    public static Element getDocument(String xml){
        try{
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml))).getDocumentElement();
        }catch(IOException | ParserConfigurationException | SAXException  e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
    }
    /**
     * Converts a document to a string
     * @param request  the Document to convert
     * @return  the request as a string
     */
    public static String documentToString(Document request){
        String result;
        try{
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(request), new StreamResult(writer));
            result = writer.getBuffer().toString();
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return result;
    }
}
