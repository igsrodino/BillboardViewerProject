package Viewer.Models;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;

// Contains methods to call the server and return the response in an appropriate format
public class BillboardModel {
    private BillboardPOJO info;
    private static String response;
    private static Document responseXML;
    public BillboardModel(){
        this.info = new BillboardPOJO();
    }
    private String requestBillboard() throws IOException {
        Socket clientSocket = new Socket("localhost", 5050);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
        outToServer.writeUTF("<request>\n" +
                "    <type>getBillboard</type>\n" +
                "</request>");
        response = inFromServer.readUTF();
       // System.out.println("Server: "+ response);
        clientSocket.close();
        return response;
    }

    public void parseXML(String response) throws IOException {
        //File xmlFile = new File("src\\Viewer\\Views\\XMLExample.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(new InputSource(new StringReader(response)));
            doc.getDocumentElement().normalize();
//          NodeList nodeList = doc.getElementsByTagName("billboard");
            info = getInfo(doc.getElementsByTagName("billboard").item(0));
            System.out.println(doc.getElementsByTagName("billboard").item(0));
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
    public BillboardPOJO getBillboard() throws IOException {

        this.parseXML(this.requestBillboard());

        return info;
    }
}