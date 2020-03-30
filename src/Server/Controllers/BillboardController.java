package Server.Controllers;

import Server.Models.BillboardModel;

import java.sql.ResultSet;

public class BillboardController {
    private BillboardModel model;

    public BillboardController(BillboardModel model){
        this.model = model;
    }

    public String getBillboard(){
        // Call this.model.getBillboard() to populate the object.
        // Use the getter methods to build a Document object  https://mkyong.com/java/how-to-create-xml-file-in-java-dom/
        // Convert the Document to a string (gonna need to google it. Not complicated though)
        // Return the stringified xml.
        // Don't forget error handling (try catch, exceptions etc)
        this.model.getBillboard();

        return "<billboard>xml</billboard>";
    }

}
