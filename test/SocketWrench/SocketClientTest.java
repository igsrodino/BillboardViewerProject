package SocketWrench;

import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class SocketClientTest {

    private Socket clientSocket;
    private static String response = "";
    int port = 5050;

    @Test
    void testConnection () throws IOException {

        // RUN SERVER.JAVA BEFORE RUNNING TESTS //

        clientSocket = new Socket("localhost", port);
    }

    @Test
    void testWrongPorts () {
        assertThrows(ConnectException.class, () -> {
            clientSocket = new Socket("localhost", 4000);
            clientSocket = new Socket("localhost", 5049);
            clientSocket = new Socket("localhost", 5051);
            clientSocket = new Socket("localhost", 6050);
        });
    }

    @Test
    void sendRequest() throws IOException {
        clientSocket = new Socket("localhost", port);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
        outToServer.writeUTF("getBillboard");
        response = inFromServer.readUTF();
        clientSocket.close();
    }
}

