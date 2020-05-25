package Server.Models;

import Server.Utilities.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public int getUserID(String username){

        ResultSet rs = this.dbConn.runSelectQuery("select * from users where username =\""+username+"\"");

        try{
            while(rs.next()){
                this.userID = rs.getInt("id");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(this.userID);
        return this.userID;
    }
    /**
     * Gets the permissions
     * @return list of permissions
     */
    public ArrayList<String> getPermissions(int userID) throws SQLException {
        //return this.permissions;
        //String name = getUsername(UserID)
        // int UserId =
        ResultSet rs = this.dbConn.runSelectQuery("select * from permissions where user= "+userID);
        // ResultSet rs = this.dbConn.runSelectQuery("select * from permissions where user= 47");
        //System.out.print(rs.getFetchSize());
        try{
            while(rs.next()){
                //System.out.println(rs.getInt("create_billboards"));
                if(rs.getBoolean("create_billboards")){
                    this.permissions.add("create_billboards");
                }
                if(rs.getBoolean("edit_billboards")){
                    this.permissions.add("edit_billboards");
                }
                if(rs.getBoolean("schedule_billboards")){
                    this.permissions.add("schedule_billboards");
                }
                if(rs.getBoolean("edit_users")){
                    this.permissions.add("edit_users");
                }
                //this.permissions.add(rs.getString("permission"));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        //this.permissions.add("edit_billboards");
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
    public boolean setPermissions(int userID, boolean create_billboards, boolean edit_billboards, boolean schedule_billboards,
                                  boolean edit_users){

        ResultSet rs = this.dbConn.runSelectQuery("select * from permissions where user="+userID);
        int result = -1;
        try {
            System.out.print(rs.getFetchSize());
            rs.last();
            if (rs.getRow()>0){
                // Run an update query
                result = dbConn.runUpdateQuery("update permissions set create_billboards="+create_billboards+", edit_billboards="+edit_billboards
                        +", schedule_billboards="+schedule_billboards+", edit_users="+edit_users);
            } else {
                // run an insert query
                result=dbConn.runUpdateQuery("insert into permissions(create_billboards,edit_billboards,schedule_billboards,edit_users) values("+
                        create_billboards+","+edit_billboards+","+schedule_billboards+","+edit_users+")");
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return result == 1;
//        boolean success = false;
//        try{
//            ArrayList<String> existing_perm = getPermissions(userID);
//            for(String perm : req_permissions){
//                if(existing_perm.contains(perm) == true){
//                    //permissions.set(permissions.indexOf(perm), perm);
//                    //this.permissions.remove(perm);
//                    //System.out.println("User already has this permission");
//                    success = false;
//                }
//                else{
//                    if(perm=="create_billboards"||perm=="edit_billboards"||perm=="schedule_billboards"||perm=="edit_users"){
//                        this.permissions.add(perm);
//
//                        success = true;
//                    }
//                    else{
//                        //System.out.println("This permission does not exist in the database");
//                        success = false;
//                    }
//
//                }
//
//            }
//           // return success;
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        finally{
//            System.out.println("finally block always executed for resource clean-up");
//            return success;
//        }

    }
}
