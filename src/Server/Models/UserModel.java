package Server.Models;

import Server.Utilities.Database;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
        this.permissions = new ArrayList<String>();
    }

    /**
     * Gets the username
     * @return username
     */
    public String getUsername(int ID){

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

    public String getPassword(String username) throws SQLException {
        String password = "";
        ResultSet rs = this.dbConn.runSelectQuery("SELECT password FROM cab302.users where users.username ='" + username + "'");
        while (rs.next()) {
            password = rs.getString("password");
        }

        return (password);
    }

    /**
     * Gets the salt
     * @return the salt
     */
    public String getSalt(String username) throws SQLException {

            String salt = "";
            ResultSet rs = this.dbConn.runSelectQuery("SELECT salt FROM cab302.users where users.username ='" + username + "'");
            while (rs.next()) {
                salt = rs.getString("salt");
            }
            return (salt);
        }

    /**
     * Gets the user id of the current user
     * @return  the user id or -1 if it wasn't found
     */

    public int getUserID(String username) throws SQLException {
        int useridcheck = -1;
        ResultSet rs = this.dbConn.runSelectQuery("SELECT id FROM cab302.users where users.username ='" + username + "'");
        while (rs.next()) {
            useridcheck = rs.getInt("id");
        }
        return useridcheck;

    }

    /**
     * Gets the permissions
     * @return list of permissions
     * @param userID
     */
    public ArrayList<String> getPermissions(int userID) throws SQLException {

        ResultSet rs = this.dbConn.runSelectQuery("select * from permissions where user= "+userID);

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

            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return permissions;

    }

    /**
     * Fetches a user based on their user id
     * @param userID  the userID of the user record to retrieve
     * @return  true if the record was loaded successfully, or false if it wasn't found
     */
    public boolean getUser(int userID) throws SQLException {
        int useridcheck = -1;
        ResultSet rs = this.dbConn.runSelectQuery("SELECT username FROM cab302.users where users.username ='" +userID+"'");
        while ( rs.next() ) {
            useridcheck = rs.getInt("username");
        }
        if(userID == useridcheck) {

            return(true);
        }
        else {
            return(false);
        }
    }

    /**
     * Fetches a user based on their username
     * @param username  the username of the user to retrieve
     * @return  true if the record was loaded successfully, or false if it wasn't found
     */
    public boolean getUser(String username) throws SQLException {

        String Usernamecheck = "";
        ResultSet rs = this.dbConn.runSelectQuery("SELECT username FROM cab302.users where users.username ='" +username+"'");
        while ( rs.next() ) {
            Usernamecheck = rs.getString("username");
        }
        if(username.equals(Usernamecheck)) {
            return(true);
        }
        else {
            return(false);
        }
    }

    /**
     * Sets the password of the given user
     * This just writes the record into the database. The provided password must already be
     * hashed in the UserController.setUserPassword() method before calling this method
     * Will update the internal user fields (username, password, etc)
     * @param Username the username of the user to update
     * @param password  the password hash to store in the database
     * @return  true if the update was a success, or false if it was not
     */
    public boolean setUserPassword(String Username, String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, SQLException {

        if(getUser(Username)) {
            this.dbConn.runUpdateQuery("update users set users.password = '" + password + "' where users.username = '" + Username + "'");
            this.dbConn.runUpdateQuery("update users set users.salt = '" + salt + "' where users.username = '" + Username + "'");
            return (true);
        }
        else {
            return (false);
            }

    }

    /**
     *
     * @param username the username of the new user
     * @param password unshased new password
     * @param name name of new user
     * @return if sucsessfull returns 1
     * @throws SQLException
     */
    public boolean createuser(String username , String name , String password, String salt) throws SQLException {

        if(!getUser(username)) {
            this.dbConn.runUpdateQuery("INSERT INTO users (username, password, name, salt) VALUES("+"'"+username+"',"+"'"+password+"',"+"'"+name+"','"+salt+"')");
            return (true);
        }
        else {
            return (false);
            }

    }

    /**
     *
     * @param Username
     * @return if successful
     */
    public boolean deleteUser(String Username) throws SQLException {

        if(getUser(Username)) {
            this.dbConn.runUpdateQuery("DELETE FROM users WHERE username = '"+Username+"'");
            return (true);
        }
        else {
            return (false);
        }

    }






    /**
     * Sets the user permissions for the given user.
     * This will destructively set permissions. Any existing permissions will be erased.
     * Will update the internal user fields (username, password, etc)
     * @param userID  the user id to set permissions for
     * @param permissions  the permissions to set
     * @return true if the operation was a success or false if it failed
     */
    public boolean setPermissions(int userID, int create_billboards, int edit_billboards, int schedule_billboards,
                                  int edit_users){

        ResultSet rs = this.dbConn.runSelectQuery("select * from permissions where user="+userID);
        int result = -1;
        try {
            System.out.print(rs.getFetchSize());
            rs.last();
            if (rs.getRow()>0){
                // Run an update query
                result = dbConn.runUpdateQuery("update permissions set create_billboards="+create_billboards+", edit_billboards="+edit_billboards
                        +", schedule_billboards="+schedule_billboards+", edit_users="+edit_users+" where user = " + userID);
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

    }
}
