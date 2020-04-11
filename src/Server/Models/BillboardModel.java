package Server.Models;
import Server.Utilities.Database;
import org.w3c.dom.Document;

import java.sql.ResultSet;


/**
 * Contains the data retrieval methods.
 * */
public class BillboardModel {
    private Database dbConn;
    private int id;
    private String background;
    private String message;
    private String message_color;
    private String url;
    private String data;
    private String information;
    private String information_color;
    private int owner;

    /**
     * Constructs the Billboard object
     * @param dbConnection  allows access to the database via the connection established at server start
     * */
    public BillboardModel(Database dbConnection){
        this.dbConn = dbConnection;
        this.background = "#0000FF";
        this.id = 0;
        this.message = "Hello WOrld";
        this.message_color = "";
        this.url = "";
        this.data = "";
        this.information = "";
        this.information_color = "";

        this.owner = 0;

    }
    public int getId(){
        return this.id;
    }
    public String getBackground(){
        return this.background;
    }
    public String getMessage(){
        return this.message;
    }
    public String getMessage_color(){
        return this.message_color;
    }
    public String getUrl(){
        return this.url;
    }
    public String getData(){
        return this.data;
    }
    public String getInformation(){
        return this.information;
    }
    public String getInformation_color(){
        return this.information_color;
    }
    public int getOwner(){
        return this.owner;
    }

    /**
     * Fetches the specified billboard.
     * It will populate the Billboard model object with the
     * billboard data. This can then be
     * accessed via the getter methods
     * @param billboardID the billboard ID to retrieve
     * @return true if the operation was successful, or false if no billboard was found
     */
    public boolean getBillboard(int billboardID){
        boolean status = false;
        ResultSet rs =
                this.dbConn.runSelectQuery("select * from billboards where id = "+billboardID +" " + "order by id limit 1");
        try{
            while(rs.next()){
                this.owner = rs.getInt("owner") > 0 ? rs.getInt("owner"): -1;
                this.id = rs.getInt("id") > 0 ?rs.getInt("id") : -1;
                this.background = rs.getString("background") != null ?
                        rs.getString("background") : "";
                this.message = rs.getString("message") != null ? rs.getString("message") : "";
                this.message_color = rs.getString("message_color") != null ? rs.getString(
                        "message_color") : "";
                this.url = rs.getString("url") != null ? rs.getString("url") : "";
                this.data = rs.getString("data") != null ?rs.getString("data") : "";
                this.information = rs.getString("information") != null ?rs.getString("information"):
                        "";
                this.information_color = rs.getString("information_color") != null ?
                        rs.getString("information_color"):"";
            }
            status = true;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return status;
    }
    /**
    * Deletes the specified billboard
    * @param billboardID  the id of the billboard to delete
    * @return the number of rows affected by the delete (should always = either 1 or 0)
    * */
    public int deleteBillboard(int billboardID){
        return this.dbConn.runUpdateQuery("delete * from billboards where id = "+billboardID);
    }


    /**
    * Retrieves a complete list of all billboards
    * @return a Document object with the list of billboard objects contained in a root
     * data element
     * */
    public Document listBillboards(){
        return null;
    }

    /**
     * Creates a billboard with the provided information
     * @param background  a hex value representing the color of the background
     * @param message  the billboard message
     * @param message_color  the color of the message text
     * @param image  the url or base64 data string of the image
     * @param imageType  the type of image. The two valid options are: 'url' or 'data'
     * @param information  in depth details about the billboard message
     * @param information_color  the hex code for the information text color
     * @param owner  the id of the user creating the billboard
     * @return int representing the number of rows inserted
    * */
    public int createBillboard(String background, String message, String message_color, String image,
                                   String imageType, String information, String information_color, int owner){
        int result = 0;
        try{
            if(imageType == "url") {
                result = this.dbConn.runUpdateQuery("insert into billboards (background, message, message_color, url," +
                        " information, information_color, owner" + " ) " +
                        "values ("+background+", "+message+", "+message_color+", "+image+", "+information+", "+information_color+", "+owner+")");
            } else if(imageType == "data"){
                result = this.dbConn.runUpdateQuery("insert into billboards (background, message, message_color, " +
                        "data," +
                        " information, information_color, owner" + " ) " +
                        "values ("+background+", "+message+", "+message_color+", "+image+", "+information+", "+information_color+", "+owner+")");
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return result;
    }


}
