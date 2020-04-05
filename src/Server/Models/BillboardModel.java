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
    public BillboardModel(Database dbConnection){
        // Gets passed the database connection on creation, which is then used to retrieve and set billboard specific
        // records.
        this.dbConn = dbConnection;
        this.background = "#0000FF";
        this.id = 0;
        this.message = "Hello World";
        this.message_color = "#FFFF00";
        this.url = "https://e3.365dm.com/20/01/2048x1152/skynews-roger-federer-tennis_4889782.jpg?bypass-service-worker&20200112150901";
        this.data = "";
        this.information = "Enjoying my time writing XML files.";
        this.information_color = "#00FFFF";
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
    public void getBillboard(){
        // Returns a ResultSet containing the results of the query
        ResultSet rs = this.dbConn.runQuery("select * from test");
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
        this.dbConn.runQuery("delete * from test where Column1 = 1");
    }


}
