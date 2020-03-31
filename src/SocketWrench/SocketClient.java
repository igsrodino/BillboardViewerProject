package SocketWrench;

import java.io.*;
import java.net.Socket;

public class SocketClient {
    private static String response;

    public SocketClient() {

    }
    public static void sendRequest(int port, String request){
        // Open a connection, send it, save response
        try{
            Socket clientSocket = new Socket("localhost", port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.writeBytes(request.replace("\n", " ") + '\n');
            response = inFromServer.readLine();
            clientSocket.close();
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    };


    public static String getResponse(){
        return response.substring(2);
    }
}
