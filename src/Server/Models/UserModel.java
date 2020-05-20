package Server.Models;

import Server.Utilities.Database;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Contains the user data access methods
 * Allows access to user permissions, for validation
 * */
public class UserModel {
    private Database dbConn;
    private String username;
    private String password;
    private String salt;
    private int userID;
    private ArrayList<String> permissions;
    /** Constructs the User object
    * @param dbConnection allows access to the database via the connection established at server start
     * */
    public UserModel(Database dbConnection){
        this.dbConn = dbConnection;
        this.username = "";
        this.password = "";
        this.salt = "";
        this.userID = -1;
        this.permissions = null;
    }

    /**
     * Gets the username
     * @return username
     */
    public String getUsername(int ID){

        //return this.username;
        ResultSet rs = this.dbConn.runSelectQuery("select username from users where id =" + ID+")");
        try{
            while(rs.next()){
                this.username = rs.getString("username");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return this.username;
    }

    /**
     * Gets the password hash
     * @return password hash
     */
    public String getPassword(int ID){
        ResultSet rs = this.dbConn.runSelectQuery("select password from users where id =" + ID+")");
        try{
            while(rs.next()){
                this.password = rs.getString("password");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return this.password;
    }

    /**
     * Gets the salt
     * @return the salt
     */
    public String getSalt(int ID){
        //return this.salt;
        ResultSet rs = this.dbConn.runSelectQuery("select salt from users where id =" + ID+")");
        try{
            while(rs.next()){
                this.salt = rs.getString("password");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return this.salt;
    }

    /**
     * Gets the user id of the current user
     * @return  the user id or -1 if it wasn't found
     */
    public int getUserID(int ID){
        return this.userID;
//        ResultSet rs = this.dbConn.runSelectQuery("select * from users where id =" +ID);
//        //this.dbConn.runSelectQuery("select * from billboards where id = "+billboardID +" " + "order by id limit 1");
//        try{
//            while(rs.next()){
//                this. = rs.get
//            }
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }

    }
    /**
     * Gets the permissions
     * @return list of permissions
     * @param userID
     */
    public ArrayList<String> getPermissions(int userID){
        //return this.permissions;
        //String name = getUsername(UserID)
       // int UserId =
        ResultSet rs = this.dbConn.runSelectQuery("select permission from permissions where id = "+userID);
        try{
            while(rs.next()){
                this.permissions.add(rs.getString("permission"));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return permissions;
    }

    /**
     * Fetches a user based on their user id
     * @param userID  the userID of the user record to retrieve
     * @return  true if the record was loaded successfully, or false if it wasn't found
     */
    public boolean getUser(int userID){
        return false;
    }

    /**
     * Fetches a user based on their username
     * @param username  the username of the user to retrieve
     * @return  true if the record was loaded successfully, or false if it wasn't found
     */
    public boolean getUser(String username){
        return false;
    }

    /**
     * Sets the password of the given user
     * This just writes the record into the database. The provided password must already be
     * hashed in the UserController.setUserPassword() method before calling this method
     * Will update the internal user fields (username, password, etc)
     * @param userID the user id of the user to update
     * @param password  the password hash to store in the database
     * @return  true if the update was a success, or false if it was not
     */
    public boolean setUserPassword(int userID, String password){
        return false;
    }

    /**
     * Sets the user permissions for the given user.
     * This will destructively set permissions. Any existing permissions will be erased.
     * Will update the internal user fields (username, password, etc)
     * @param userID  the user id to set permissions for
     * @param permissions  the permissions to set
     * @return true if the operation was a success or false if it failed
     */
    public boolean setPermissions(int userID, String[] permissions){
        return false;
    }
}
