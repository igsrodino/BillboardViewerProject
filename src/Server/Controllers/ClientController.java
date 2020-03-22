package Server.Controllers;

import Server.Models.BillboardModel;
import Server.Models.ScheduleModel;
import Server.Models.UserModel;
import Server.Utilities.Database;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientController implements Runnable {
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private BillboardModel billboard;
    private UserModel user;
    private ScheduleModel schedule;

    public ClientController(Socket socket, DataInputStream inputSteam, DataOutputStream outputStream, Database dbConn){
        this.socket = socket;
        this.inputStream = inputSteam;
        this.outputStream = outputStream;
        this.billboard = billboard;
        this.user = user;
        this.schedule = schedule;

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
        * */
        try{
            this.outputStream.writeUTF("Great Success, Comrades\n");
            this.socket.close();
            this.inputStream.close();
            this.outputStream.close();
            return;
        } catch (IOException e) {
            System.err.println("Client handler failed: " + e.getMessage());
        }

    }
}
