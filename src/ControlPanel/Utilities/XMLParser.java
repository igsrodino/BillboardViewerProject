package ControlPanel.Utilities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

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
}
