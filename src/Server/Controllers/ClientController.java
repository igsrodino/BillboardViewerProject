package Server.Controllers;

import Server.Models.BillboardModel;
import Server.Utilities.Database;
import org.w3c.dom.Document;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientController implements Runnable {
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Database dbConn;

    public ClientController(Socket socket, DataInputStream inputSteam, DataOutputStream outputStream, Database dbConn){
        this.socket = socket;
        this.inputStream = inputSteam;
        this.outputStream = outputStream;
        this.dbConn = dbConn;
    }

    @Override
    public void run() {
        // Read the stream, figure out which controller to use, create, call, and return the result from the
        // controller method.
        /*
        * Read the stream, parse it. Create a model and controller, call the controller, return the result
        *
        * Sending looks like this.outputStream.writeUTF("great Success")
        * Close the connection with return;
        *
        * eg:
        * Document request = parseRequest(inputStream.read());
        * if(request.type == 'Billboard'){
        *   BillboardModel bbModel = new BillboardModel(dbConn);
        *   BillboardController bb = new BillboardController(bbModel);
        *
        *   if(request.task == 'getBillboard'){
        *       this.outputStream.writeUTF(bb.getBillboard());
        *   }
        * }
        *
        *
        * Requests must have their permissions validated with the User controller and their
        * access token validated (if it's required) with UserAuthentication.isValidSessionToken()
        * before calling the relevant controller method for the requested action
        *
        * */
        try{
            BillboardModel md = new BillboardModel(dbConn);
            BillboardController bb = new BillboardController(md);
            // billboardID is retrieved by using the Schedule controller to find out which billboard should
            // be displayed (Schedule.getCurrentBillboard())
            String re = bb.getBillboard(1);
            this.sendResponse(re);
            return;
        } catch(Exception e){
            System.err.println("Client has failed differently: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Checks the validity of an access token.
     * @param token  the access token to verify
     * @return  true if it is valid, false if it is not
     */
    public boolean checkAccessToken(String token){
        // Use the UserAuthentication methods to verify it.
        return false;
    }
    /**
     * Sends the Response string and closes the stream, ending the connection
     * @param response  the stringified xml Response object to send
     */
    private void sendResponse(String response){
        try{
            this.outputStream.writeUTF(response );
            this.socket.close();
            this.inputStream.close();
            this.outputStream.close();
        } catch(IOException e){
            System.err.println("Client handler failed: " + e.getMessage());
        }
    }

    /**
     * Processes a stringified XML Request object into a Document object
     * @param request  the stringified XML Request object
     * @return  a Document object containing the XML in the request string
     */
    private Document processRequestString(String request){
        return null;
    }
}
