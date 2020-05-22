package Server.Controllers;

import Server.Models.UserModel;
import Server.Utilities.UserAuthentication;

import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import static Server.Utilities.UserAuthentication.getSalt;

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
       //this.model.createuser()




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

    /**
     * Gets the permissions for the given user
     * @param username  the username to retrieve permissions for
     * @return  a Response string
     */
    public String getUserPermissions(String username){
        return "Response";
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
    public String setPermissions(String username, String[] permissions){
        return "Response";
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
       // System.out.println(username);
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
    public int getUserID(String username) {
        return -1;
    }
}
