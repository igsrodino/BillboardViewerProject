package Server.Models;
import Server.Utilities.Database;

import java.sql.ResultSet;


/*
* Contains the data retrieval methods.
* */
public class BillboardModel {
    private Database dbConn;
    public BillboardModel(Database dbConnection){
        // Gets passed the database connection on creation, which is then used to retrieve and set billboard specific
        // records.
        this.dbConn = dbConnection;
    }
    public ResultSet getBillboard(){
        // Returns a ResultSet containing the results of the query
        return this.dbConn.runQuery("select * from test");
    }
    public ResultSet deleteBillboard(int billboardID){
        return this.dbConn.runQuery("delete * from test where Column1 = 1");
    }
}
