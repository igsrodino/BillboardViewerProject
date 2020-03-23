package Server.Controllers;

import Server.Models.BillboardModel;

import java.sql.ResultSet;

public class BillboardController {
    private BillboardModel model;
    public BillboardController(BillboardModel model){
        this.model = model;
    }
    public String getBillboard(){
        // Call this.model.getBillboard() to get a ResultSet.
        // Build a Document object with the resultset: https://mkyong.com/java/how-to-create-xml-file-in-java-dom/
        // Convert the Document to a string (gonna need to google it. Not complicated though)
        // Return the stringified xml.
        // Don't forget error handling (try catch, exceptions etc)
        ResultSet rs = this.model.getBillboard();
        int res = 0;
        try{
        while(rs.next()){
            res +=rs.getInt("Column1");
        }}catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "<billboard>xml</billboard>" + res;
    }

}
