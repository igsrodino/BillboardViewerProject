package SocketWrench;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI setup for socketwrench for setting default text and default response headers.
 */
public class WrenchGUI {
    private JPanel rootPanel;
    private JButton sendItButton;
    private JTextArea response;
    private JTextField address;
    private JTextField port;
    private JTextArea requestData;
    private SocketClient conn = new SocketClient();
    private String request;

    public WrenchGUI() {
        this.requestData.setText("<request>\n    <type>getBillboard</type>\n    " +
                "<data></data>\n</request" +
                ">");
        this.request = "<request><type>getBillboard</type><data></data></request>";
        sendItButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int portNum = 0;
                try{
                    portNum = Integer.parseInt(port.getText());
                } catch (Exception ex){
                    System.err.println(ex.getMessage());
                }

               request = requestData.getText();

                if(portNum<=0 ) {
                    response.setText(
                            "Invalid port, try 5050");
                } else {
                    conn.sendRequest(portNum, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+request);
                    response.setText(SocketClient.getResponse());
                }
            }
        });
    }

    public void setResponse(JTextArea response) {
        this.response = response;
    }

    public JButton getSendItButton() {
        return sendItButton;
    }

    public JTextField getPort() {
        return port;
    }

    public JTextArea getRequestData() {
        return requestData;
    }

    public JTextField getAddress() {
        return address;
    }

    public JPanel getRootPanel(){
        return this.rootPanel;
    }

}
