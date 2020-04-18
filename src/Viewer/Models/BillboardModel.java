package Viewer.Models;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

// Contains methods to call the server and return the response in an appropriate format
public class BillboardModel {
    private BillboardPOJO info;

    public BillboardModel(){
        this.info = new BillboardPOJO();
    }

    public void parseXML(){
        File xmlFile = new File("src\\Viewer\\Views\\XMLExample.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
//          NodeList nodeList = doc.getElementsByTagName("billboard");
            info = getInfo(doc.getElementsByTagName("billboard").item(0));

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
    }

    public static BillboardPOJO getInfo(Node node){
        BillboardPOJO billboardInfo = new BillboardPOJO();
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.getNodeType() == nodeItem.ELEMENT_NODE) {
                Element element = (Element) nodeItem;

                if (element.getTagName() == "message" ) {
                    billboardInfo.setMessage(element.getTextContent());

                    if(element.getAttribute("colour").length() > 0){
                        billboardInfo.setMessageColour(element.getAttribute("colour"));
                    }
                }
                if (element.getTagName() == "information" ) {
                    System.out.println(element.getTextContent());
                    billboardInfo.setInformation(element.getTextContent());
                    if(element.getAttribute("colour").length() > 0){
                        billboardInfo.setInformationColour(element.getAttribute("colour"));
                    }
                }
                if (element.getTagName() == "picture"){
                    if (element.getAttribute("url").length() > 0) {
                        billboardInfo.setPictureURL(element.getAttribute("url"));
                    } else if (element.getAttribute("data").length() > 0){
                        billboardInfo.setPictureData(element.getAttribute("data"));
                    }
                }
            }
        }
        Element parentElement = (Element)((NodeList) node.getParentNode()).item(0);
        if(parentElement.getNodeType() == parentElement.ELEMENT_NODE){
            if(parentElement.getAttribute("background").length() > 0){
                billboardInfo.setBackgroundColour(parentElement.getAttribute("background"));
            }
        }
        return billboardInfo;
    }
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node nodeValue = nodeList.item(0);
        return nodeValue.getNodeValue();
    }



    public BillboardPOJO getBillboard(){
        parseXML();
        return info;
    }
}
