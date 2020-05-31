package ControlPanel.Utilities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Contains utility methods for calling the server
 */
public class NetworkManager {
    private static String accessToken = "";
    private static int port = 5050;
    private static String address = "127.0.0.1";

    /**
     * Checks to see if the user has successfully logged in
     * @return  true if the user is logged in (has an access token) and false if not
     */
    public static boolean isLoggedIn(){
        return accessToken.length() > 0;
    }

    /**
     * Makes a request that doesn't require a data payload and returns the response
     * @param type  the type of request to make
     * @return  the data element of the response
     */
    public static Node makeRequest(String type){
        Node responseData = null;
        try {
            Document request = generateRequest(type);

            // Send the request, listen for response.
            Document response = getResponse(XMLHelpers.documentToString(request));

            // Extract and return the data from the response
            responseData = request.getDocumentElement();

        }
        catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return responseData;
    }

    /**
     * Makes a request with a data payload to the server and returns the data element of the
     * response.
     * @param type  the type of request to make
     * @param data  an array of Elements to include in the data tag
     * @return  returns the data element of the response
     */
    public static Node makeRequest(String type, Element[] data){
        Node responseData = null;
        try {
            Document request = generateRequest(type, data);

            // Send the request, listen for response.
            Document response = getResponse(XMLHelpers.documentToString(request));

            // Extract and return the data from the response
            responseData = request.getDocumentElement();

        } catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return responseData;
    }

    /**
     * Generates a request Document object
     * @param type  the type of request to generate
     * @return  the request Document
     */
    private static Document generateRequest(String type){
        Document request = null;
        try{
            request = getBaseRequestDocument(type);

            // Add the access token
            if (!type.equals("login") && !type.equals("getBillboard")) {
                request.getDocumentElement().appendChild(getAccessToken());
            }

        }catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return request;
    }

    /**
     * Generates a request with a data payload
     * @param type  the type of request to make
     * @param data  the data to include in the request
     * @return  the request Document
     */
    private static Document generateRequest(String type, Element[] data){
        Document request = null;
        try{
            request = getBaseRequestDocument(type);
            // Insert the data
            Element reqData = request.createElement("data");
            for(int i = 0; i < data.length; i++){
                reqData.appendChild(data[i]);
            }
            request.getDocumentElement().appendChild(reqData);
            // Add the access token
            if (!type.equals("login") && !type.equals("getBillboard")) {
                request.getDocumentElement().appendChild(getAccessToken());
            }

        }catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return request;
    }

    /**
     * Generates a request without a data payload
     * @param type  the request type
     * @return  the request Document
     */
    private static Document getBaseRequestDocument(String type) {
        Document request = null;
        try{
            DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element reqElement = request.createElement("request");
            Element reqType = request.createElement("type");

            reqType.appendChild(request.createTextNode(type));
            reqElement.appendChild(reqType);
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return request;
    }

    /**
     * Gets the token element with access token
     * @return  the token element
     */
    private static Element getAccessToken() {
        Element reqToken = null;
        try{
            Document request = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            reqToken = request.createElement("token");
            reqToken.appendChild(request.createTextNode(accessToken));
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return reqToken;
    }

    /**
     * Sends a string to the server and returns the response as a Document
     * @param request  the string to send
     * @return  the response Document
     */
    private static Document getResponse(String request){
        Document response = null;
        try{
            Socket clientSocket = new Socket(address, port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.writeBytes(request + '\n');
            String responseString = inFromServer.readLine();
            clientSocket.close();

            response = parseResponseString(responseString);
            // Save the access token, if present
            String token =
                    response.getDocumentElement().getElementsByTagName("token").item(0).getNodeValue();
            if(token.length() > 0){
                accessToken = token;
            }
        } catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return response;
    }

    /**
     * Creates a Document object from a string
     * @param response  the string to parse
     * @return  the response object
     */
    private static Document parseResponseString(String response){
        return null;
    }
}
