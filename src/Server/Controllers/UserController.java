package Server.Controllers;

import Server.Models.UserModel;
import Server.Utilities.UserAuthentication;


import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import static Server.Utilities.UserAuthentication.generateSessionToken;
import static Server.Utilities.UserAuthentication.getSalt;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
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
    public String login(String username, String password) throws SQLException, InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String hashedpass = "";
        String hashedpass2 = "";

        if(this.getUserID(username) > -1)
        {
            hashedpass = this.model.getPassword(username);
            String salt = this.model.getSalt(username);
            //System.out.println(salt);
            hashedpass2 = UserAuthentication.generateHash(password, salt);

        }
        else {
            return  ("<response>\n" +
                    "    <type>fail</type>\n" +
                    "    <data></data>\n" +
                    "</response>");

        }


       if(UserAuthentication.compareHashes(hashedpass,hashedpass2)) {
           int userid = this.model.getUserID(username);
           String token = UserAuthentication.generateSessionToken(userid);

           return ("<response>\n" +
                   "    <type>success</type>\n" +
                   "    <data>\n" +
                   token+
                   "    </data>\n" +
                   "</response>");
       }
       else
           {
              return  ("<response>\n" +
                      "    <type>fail</type>\n" +
                      "    <data></data>\n" +
                      "</response>");

           }

    }

    /**
     * Removes the user's session, logging them out.
     * @param accessToken  the access token to invalidate
     * @return  a Response string
     */
    public String logout(String accessToken){
        UserAuthentication.invalidateSessionToken(accessToken);

        return " \"<response>\\n\" +\n" +
                "                  \"    <type>success</type>\\n\" +\n" +
                "                  \"    <data></data>\\n\" +\n" +
                "                  \"</response>\";";
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
    public String createUser(String username, String password, String Name) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, UnsupportedEncodingException {
        String salt = UserAuthentication.getSalt();
        String hashedpass = UserAuthentication.generateHash(password,salt);

      if(this.model.createuser(username, Name, hashedpass, salt)) {
          return "<response>\n" +
                  "    <type>success</type>\n" +
                  "    <data></data>\n" +
                  "</response>";
      }
      else {
              return "<response>\n" +
                      "    <type>fail</type>\n" +
                      "    <data></data>\n" +
                      "</response>";
          }

    }

    /**
     * Removes the given user from the system. This will also invalidate any existing access
     * tokens for the user
     * @param username the username of the user to delete
     * @return  a Response string
     */
    public String deleteUser(String username) throws SQLException {

       if(this.model.deleteUser(username))
       {
           return "<response>\n" +
                   "    <type>success</type>\n" +
                   "    <data></data>\n" +
                   "</response>";
       }
       else {
           return "<response>\n" +
                   "    <type>fail</type>\n" +
                   "    <data></data>\n" +
                   "</response>";
           }

    }

    /**
     * Gets a list of all the users in the system
     * @return  a Response string
     */
    public String listUsers(){

      String currentusers = UserAuthentication.listusers();

        return "<response>\n" +
                "    <type>success</type>\n" +
                "    <data>\n" +
                         currentusers       +
                "</response>";
    }

    public static String convertDocumentToString(Document doc){

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
     * @param requestedUserID
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
            else {
                type.appendChild(doc.createTextNode("failure"));
            }
            doc.appendChild(resp);
            resp.appendChild(data);
            resp.appendChild(type);
            permissionsXMLString = convertDocumentToString(doc);
        }
        catch (Exception e) {
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
    public String setUserPassword(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, SQLException {
        String salt = UserAuthentication.getSalt();
        String hashedpass = UserAuthentication.generateHash(password,salt);
        if(this.model.setUserPassword(username,hashedpass,salt ))
        { return("<response>\n" +
                "    <type>success</type>\n" +
                "    <data></data>\n" +
                "</response>");

        }
        else
            {return ("<response>\n" +
                    "    <type>fail</type>\n" +
                    "    <data></data>\n" +
                    "</response>");

            }
    }

    /**
     * Fetches a userID for a user
     * @param username  the user to retrieve the userID from
     * @return int  the userID, or -1 if user not found
     */
    public int getUserID(String username) throws SQLException {
        return(this.model.getUserID(username));
    }
}
