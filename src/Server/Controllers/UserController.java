package Server.Controllers;

import Server.Models.UserModel;
import Server.Utilities.UserAuthentication;
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
     * @return  a Response string with the session token in the <data></data> element
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
        return UserAuthentication.isValidSessionToken(accessToken);
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
     * Removes the given user from the system. This will also invalidate any
     * @param username
     * @return
     */
    public String deleteUser(String username){
        return "Response";
    }
    // Permissions
}
