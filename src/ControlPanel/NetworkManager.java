package ControlPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class NetworkManager {
    private static String accessToken;

    /**
     * Makes a request to the server and returns the data element of the response.
     * @param type  the type of request to make
     * @param data  an array of Elements to include in the data tag
     * @return  returns the data element of the response
     */
    public static Element makeRequest(String type, Element[] data){
        Element responseData = null;
        try {
            Document request = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element reqElement = request.createElement("request");
            Element reqType = request.createElement("type");
            Element reqData = request.createElement("data");
            reqType.appendChild(request.createTextNode(type));
            reqElement.appendChild(reqType);

            // Insert the data
            for(int i = 0; i < data.length; i++){
                reqData.appendChild(data[i]);
            }
            reqElement.appendChild(reqData);
            if (!type.equals("login") && !type.equals("getBillboard")) {
                // Add the access token
                Element reqToken = request.createElement("token");
                reqToken.appendChild(request.createTextNode(accessToken));
                reqElement.appendChild(reqToken);
            }
            // Send the request, listen for response.

            // Extract and return the data from the response
        } catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return null;
        }
        return responseData;
    }

}
