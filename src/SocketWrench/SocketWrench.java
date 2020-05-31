package SocketWrench;

import javax.swing.*;

/**
 * GUI for socketwrench to be able to send and get data from database.
 */
public class SocketWrench {
    public static void main(String args[]){
        try
        {
            WrenchGUI wrench = new WrenchGUI();
            JFrame frame = new JFrame("Socket Wrench");
            frame.setContentPane(wrench.getRootPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 500);
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            //error handling code
            System.err.println(e.getMessage());
        }
    }
}
