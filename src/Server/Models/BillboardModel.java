package Server.Models;
import Server.Utilities.Database;

import java.sql.ResultSet;


/*
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
    private String start_time;
    private String end_time;

    /* Constructs the Billboard object
    * @param dbConnection  allows access to the database via the connection established at server start*/
    public BillboardModel(Database dbConnection){
        this.dbConn = dbConnection;
        this.background = "";
        this.id = 0;
        this.message = "";
        this.message_color = "";
        this.url = "";
        this.data = "";
        this.information = "";
        this.information_color = "";
        this.start_time = "";
        this.end_time = "";
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
    public String getStart_time(){
        return this.start_time;
    }
    public String getEnd_time(){
        return this.end_time;
    }

    /*
    * Fetches the specified billboard
    * It will populate the Billboard model object with the billboard
    * data. This can then be accessed via the getter methods
    * */
    public void getBillboard(){
        // TODO: Change signature to public bool getBillboard(int billboardID) where billboardID is the id of the
        //  billboard to retrieve and the return type is the success or failure of retrieval
        ResultSet rs = this.dbConn.runSelectQuery("select * from test");
        try{
            while(rs.next()){
                this.id = rs.getInt("id");
                this.background = rs.getString("background");
                this.message = rs.getString("message");
                this.message_color = rs.getString("message_color");
                this.url = rs.getString("url");
                this.data = rs.getString("data");
                this.information = rs.getString("information");
                this.information_color = rs.getString("information_color");
                this.start_time = rs.getString("start_time");
                this.end_time = rs.getString("end_time");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void deleteBillboard(int billboardID){
        this.dbConn.runSelectQuery("delete * from test where Column1 = 1");
    }
}
