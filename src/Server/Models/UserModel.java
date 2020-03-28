package Server.Models;

import Server.Utilities.Database;
/**
 * Contains the user data access methods
 * Allows access to user permissions, for validation
 * */
public class UserModel {
    private Database dbConn;
    /** Constructs the User object
    * @param dbConnection allows access to the database via the connection established at server start
     * */
    public UserModel(Database dbConnection){
        this.dbConn = dbConnection;
    }
}
