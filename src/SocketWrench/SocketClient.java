package SocketWrench;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    private static String response = "";

    public SocketClient() {

    }
    public static void sendRequest(int port, String request){
        // Open a connection, send it, save response
        try
        {
            Scanner scn = new Scanner(System.in);

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket socket = new Socket(ip, port);

            // obtaining input and out streams

        }catch(Exception e) {
            e.printStackTrace();
        }
    };


    public static String getResponse(){
        return response;
    }
}
