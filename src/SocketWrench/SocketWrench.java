package SocketWrench;

import javax.swing.*;

public class SocketWrench {
    public static void main(String args[]){
        try
        {
            WrenchGUI wrench = new WrenchGUI();
            JFrame frame = new JFrame("Socket Wrench");
            frame.setContentPane(wrench.getRootPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            //error handling code
        }
    }
}
