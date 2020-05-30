package Server;

import Server.Controllers.ClientController;
import Server.Utilities.Database;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLSyntaxErrorException;

/**
* Server Controller and setup
* */
public class Server {
    private static int portNumber = 5050; // needs to be set from .props file?
    public static void main(String[] args){
        try
        {
            Database db = null;
            try{
                db = new Database();
            }catch(SQLSyntaxErrorException e){
                System.out.println("Database connection has failed: " + e.getMessage());
                System.exit(1);
            }
            // Start the server
            boolean listening = true;
            ServerSocket serverSocket = null;
            Socket clientSocket;
            try
            {
                serverSocket = new ServerSocket(portNumber);
                System.out.println("New Server has started listening on port: " + portNumber );
            }
            catch (IOException e)
            {
                System.out.println("Cannot listen on port: " + portNumber + ", Exception: " + e);
                System.exit(1);
            }
            // The main loop that directs incoming connections to the correct controllers

            while(listening){
                try{
                    // Accept incoming connection
                    clientSocket = serverSocket.accept();
                    System.out.println("New client request received: " + clientSocket);

                    // Obtain input and output streams
                    DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

                    // Create a handler for the request
                    ClientController client = new ClientController(clientSocket,inputStream, outputStream, db);
                    Thread t = new Thread(client);
                    t.start();
                } catch (IOException e) {
                    System.err.println("Could not close server socket. " + e.getMessage());
                    listening = false;
                }
            }
            // Shutdown the server cleanly
            try
            {
                System.out.println(" Closing down the server socket gracefully.");
                serverSocket.close();
                db.closeConnection();
            }
            catch (IOException e)
            {
                System.err.println("Could not close server socket. " + e.getMessage());
            }

        }
        catch (Exception e)
        {
            System.out.println("Error occurred");
        }
    }
}
