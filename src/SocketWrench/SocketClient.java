package SocketWrench;

import java.io.*;
import java.net.Socket;

/**
 * For reading and writing from server to local.
 */
public class SocketClient {
    private static String response;

    public static void sendRequest(int port, String request){
        // Open a connection, send it, save response
        try {
            Socket clientSocket = new Socket("localhost", port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
            outToServer.writeUTF(request);

            response = inFromServer.readUTF();
            clientSocket.close();
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    };

    public static String getResponse(){
        if(response == null) return "Error: No data received";
        return response;
    }
}
