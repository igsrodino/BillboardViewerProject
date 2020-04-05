package Server.Controllers;

import Server.Models.BillboardModel;
import Server.Utilities.Database;
import org.w3c.dom.Document;

import java.io.*;
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

    /**
     * Reads the incoming request and processes it
     * Requests must have their access token verified (if one is needed), and user permissions
     * validated before calling the requisite controller method.
     */
    @Override
    public void run() {

        try{

            // Read the request from the client
            StringBuilder request = new StringBuilder();
            BufferedReader clientRequest = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(clientRequest.ready() && (line = clientRequest.readLine()) != null) {
                request.append(line);
            }

            BillboardModel md = new BillboardModel(dbConn);
            BillboardController bb = new BillboardController(md);
            // billboardID is retrieved by using the Schedule controller to find out which billboard should
            // be displayed (Schedule.getCurrentBillboard())
            String re = bb.getBillboard(1);
            this.sendResponse(request.toString());
            return;
        } catch(Exception e){
            System.err.println("Client has failed differently: " + e.getMessage());
            e.printStackTrace();
            this.sendResponse("Error: "+e.getMessage());
        }
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
