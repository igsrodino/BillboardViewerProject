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

/**
 * Contains methods to call the server and return the response in an appropriate format
 *
 */
public class BillboardModel {
    private BillboardPOJO info;
    private static String response;

    public BillboardModel() {
        this.info = new BillboardPOJO();
    }

    /**
     * Requests current billboard from server.
     */
    private String requestBillboard() throws IOException {
        Socket clientSocket = new Socket("localhost", 5050);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
        outToServer.writeUTF("<request>\n" +
                "    <type>getBillboard</type>\n" +
                "</request>");
        response = inFromServer.readUTF();
        clientSocket.close();
        return response;
    }

    /**
     * Parses XML received into document so elements can be read.
     */
    public void parseXML(String response) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(new InputSource(new StringReader(response)));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("billboard");
            this.getInfo(nodeList);
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Breaks down nodes and sets methods with information, image or message to be displayed(if required).
     */
    public void getInfo(NodeList node) {
        this.info.setBackgroundColour("");
        this.info.setMessageColour("");
        this.info.setMessage("");
        this.info.setPictureURL("");
        this.info.setInformationColour("");
        this.info.setInformation("");
        this.info.setPictureData("");
        NodeList childNodes = node.item(0).getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.getNodeType() == nodeItem.ELEMENT_NODE) {
                Element element = (Element) nodeItem;
                if (element.getTagName() == "message") {
                    this.info.setMessage(element.getTextContent());
                    if (element.getAttribute("colour").length() > 0) {
                        this.info.setMessageColour(element.getAttribute("colour"));
                    }
                }
                if (element.getTagName() == "information") {
                    this.info.setInformation(element.getTextContent());
                    if (element.getAttribute("colour").length() > 0) {
                        this.info.setInformationColour(element.getAttribute("colour"));
                    }
                }
                if (element.getTagName() == "picture") {
                    if (element.getAttribute("url").length() > 0) {
                        this.info.setPictureURL(element.getAttribute("url"));
                    } else if (element.getAttribute("data").length() > 0) {
                        this.info.setPictureData(element.getAttribute("data"));
                    }
                }
            }
        }
        Element parentElement = (Element) node.item(0);
        if (parentElement.getNodeType() == parentElement.ELEMENT_NODE) {
            if (parentElement.getAttribute("background").length() > 0) {
                this.info.setBackgroundColour(parentElement.getAttribute("background"));
            }
        }
    }

    /**
     * Displays error message if no billboard is available and/or server is down.
     */
    public BillboardPOJO getBillboard(String errorBoard) {
        try{
            this.parseXML(errorBoard);

        }catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return this.info;
    }

    /**
     * Displays default advertising billboard if no billboard is available and there is no error.
     */
    public BillboardPOJO getBillboard() throws IOException {
        String board = this.requestBillboard();

        if (!board.contains("billboard-not-found")) {
            this.parseXML(board);
        } else {
            this.info.setMessage("Get your business in front of thousands");
            this.info.setInformation("Call us for a billboard today");
        }
        return this.info;
    }
}