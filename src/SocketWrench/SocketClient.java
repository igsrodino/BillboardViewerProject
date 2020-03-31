package SocketWrench;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    private static StringBuilder response;

    public SocketClient() {

    }
    public static void sendRequest(int port, String request){
        // Open a connection, send it, save response

        try (Socket socket = new Socket("127.0.0.1", port))
        {

            InputStream input = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);

            int character;
            response = new StringBuilder();

            while ((character = reader.read()) != -1) {

                response.append((char) character);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

    };


    public static String getResponse(){
        return response.toString().substring(2);
    }
}
