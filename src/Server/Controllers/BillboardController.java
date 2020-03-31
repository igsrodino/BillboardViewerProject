package Server.Controllers;

import Server.Models.BillboardModel;

import java.sql.ResultSet;

/**
 * Provides access to billboards
 */
public class BillboardController {
    private BillboardModel model;

    /**
     * Constructor for the Billboard Controller
     * @param model  the Billboard model to use to access data.
     */
    public BillboardController(BillboardModel model){
        this.model = model;
    }

    /**
     * Gets the billboard to be displayed at the current time
     * Useful for providing the viewer with a billboard, and to return a specific billboard to the control panel on
     * request
     * @param billboardID  the id of the billboard to retrieve
     * @return string containing the full XML response
     */
    public String getBillboard(int billboardID){
        // Call this.model.getBillboard() to populate the object.
        // Use the getter methods to build a Document object  https://mkyong.com/java/how-to-create-xml-file-in-java-dom/
        // Convert the Document to a string (gonna need to google it. Not complicated though)
        // Return the stringified xml.
        // Don't forget error handling (try catch, exceptions etc)
        this.model.getBillboard(billboardID);
        return "<billboard>xml</billboard>";
    }

    /**
     * Gets a list of all the billboards in the system
     * @return string containing a full xml response, with the data element containing one or more billboard objects
     */
    public String getBillboardList(){
        // The BillboardModel listBillboards method will return a Document containing the billboard objects.
        // Build a new Document with it, adding in the proper response elements.
        // Return a stringified version of that.
        return "XML String";
    }

    /**
     * Deletes the specified billboard from the database
     * @param billboardID  the id of the billboard to be deleted
     * @return string containing a response object that has an empty data element.
     */
    public String deleteBillboard(int billboardID){
//         Use the model.deleteBillboard() method
        return "Response";
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
     * @return a stringified Document object containing a proper Response object.
     */
    public String createBillboard(String background, String message, String message_color, String image,
                                String imageType, String information, String information_color, int owner){
        return "Response";
    }

}
