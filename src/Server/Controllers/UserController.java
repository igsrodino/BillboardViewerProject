package Server.Controllers;

import Server.Models.UserModel;
import Server.Utilities.UserAuthentication;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides access to user operations
 */
public class UserController {
    private UserModel model;

    /**
     * Constructor
     * @param model  the UserModel object to use for data access
     */
    public UserController(UserModel model){
        this.model = model;
    }

    /**
     * Creates a session token for a valid user
     * @param username  the username of the person to log in
     * @param password  the password hash from the client
     * @return  a Response string with the session token in the data element
     */
    public String login(String username, String password){
        // Uses the UserAuthentication class to generate a session token
        return "Response";
    }

    /**
     * Removes the user's session, logging them out.
     * @param accessToken  the access token to invalidate
     * @return  a Response string
     */
    public String logout(String accessToken){
        // UserAuthentication.invalidateToken(accessToken)
        return "Response";
    }

    /**
     * Checks an access token to see if the user is currently logged in.
     * @param accessToken  the access token to check
     * @return  true if the user is logged in, false if they are not.
     */
    public boolean validateUserRequest(String accessToken){
        return UserAuthentication.isValidSessionToken(accessToken) >=0;
    }

    /**
     * Generates a new user
     * @param username  the username of the account
     * @param password  the password hash from the client
     * @return  a Response string
     */
    public String createUser(String username, String password){
        // Use UserAuthentication.generateHash(password, some random salt) to generate the
        // password hash
        // Use the model to store the details.
        return "Response";
    }

    /**
     * Removes the given user from the system. This will also invalidate any existing access
     * tokens for the user
     * @param username the username of the user to delete
     * @return  a Response string
     */
    public String deleteUser(String username){
        return "Response";
    }

    /**
     * Gets a list of all the users in the system
     * @return  a Response string
     */
    public String listUsers(){
        return "Response";
    }

    public static String convertDocumentToString(Document doc){
        // TODO: add error handling
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
     * Gets the permissions for the given user
     * @param username  the username to retrieve permissions for
     * @return  a Response string
     */
    public String getUserPermissions(int requestedUserID){
        String permissionsXMLString= "response";
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element resp = doc.createElement("response");
            Element data = doc.createElement("data");
            Element type = doc.createElement("type");

            ArrayList<String> permissions = this.model.getPermissions(requestedUserID);
            System.out.println(permissions);
            if(permissions != null && permissions.size() > 0){
                for (String permission : permissions){
                    data.appendChild(doc.createTextNode(permission+","));
                }
                type.appendChild(doc.createTextNode("success"));
            }
            else
            {
                type.appendChild(doc.createTextNode("failure"));
            }
            doc.appendChild(resp);
            resp.appendChild(data);
            resp.appendChild(type);
            permissionsXMLString = convertDocumentToString(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return permissionsXMLString;
    }


    /**
     * Checks to see if the given user has a specific permission
     * @param userID  the user ID to check
     * @param permission  the permission to check for
     * @return  true if the user has the permission, or false if they do not
     */
    public boolean checkPermission(int userID, String permission){
        // Call this.getUser(userID) to populate the fields
        if(userID < 0) return false;

        return false;
    }

    /**
     * Sets the permissions for a user
     * @param username  the username to set permissions for
     * @param permissions  a list of permissions to set
     * @return  a Response string
     */
    public String setPermissions(int requestedUserID, Document request){
        //return "Response";
        String set_permXMLString= "response";
        String[] permissions =
                request.getElementsByTagName("permissions").item(0).getTextContent().split(",");
        List<String> perm_list = Arrays.asList(permissions);
        int create_billboard = 0;
        int edit_billboards = 0;
        int schedule_billboards = 0;
        int edit_users = 0;
        if(perm_list.contains("create_billboards")){
            create_billboard = 1;
        }
        if(perm_list.contains("edit_billboards")){
            edit_billboards = 1;
        }
        if(perm_list.contains("schedule_billboards")){
            schedule_billboards = 1;
        }
        if(perm_list.contains("edit_users")){
            edit_users = 1;
        }


        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element resp = doc.createElement("response");
            Element data = doc.createElement("data");
            Element type = doc.createElement("type");
            boolean result = this.model.setPermissions(requestedUserID,create_billboard,edit_billboards,schedule_billboards,edit_users);
            if(result){
                type.appendChild(doc.createTextNode("success"));
            }
            else{
                type.appendChild(doc.createTextNode("failure"));
            }
           // ArrayList<String> permissions = this.model.getPermissions(requestedUserID);
//            System.out.println(permissions);
//            if(permissions != null && permissions.size() > 0){
//                for (String permission : permissions){
//                    data.appendChild(doc.createTextNode(permission+","));
//                }
//                type.appendChild(doc.createTextNode("success"));
//            }
//            else
//            {
//                type.appendChild(doc.createTextNode("failure"));
//            }
            doc.appendChild(resp);
            resp.appendChild(data);
            resp.appendChild(type);
            set_permXMLString = convertDocumentToString(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return set_permXMLString;
    }

    /**
     * Sets a user's password.
     * @param username  the user to reset the password for
     * @param password  the password hash from the client
     * @return  a Response object
     */
    public String setUserPassword(String username, String password){
        return "Response";
    }

    /**
     * Fetches a userID for a user
     * @param username  the user to retrieve the userID from
     * @return int  the userID, or -1 if user not found
     */
    public int getUserID(String username) {
        //return -1;
        return this.model.getUserID(username);
    }
}
