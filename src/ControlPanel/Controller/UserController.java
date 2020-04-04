package ControlPanel.Controller;

import ControlPanel.Models.UserModel;
import ControlPanel.View.LoginView;
import ControlPanel.View.MainFrame;

import javax.swing.*;

public class UserController {
    private MainFrame frame;
    private UserModel model;
    public UserController(MainFrame frame, UserModel model) {
        this.frame = frame;
        this.model = model;
        initController();
    }

    /**
     * Sets up all the event listeners for the User related panels
     */
    private void initController() {
    }

    /**
     * Attempts to login the user
     * @param username  the username to send to the server
     * @param password  the password to hash and send to the user
     * @return  true/false depending on the server response
     */
    private boolean login (String username, String password){
        //TODO: hash the password. Pull in the Server.Utilities.UserAuthentication class so you
        // can use the helpers in it.
        return false;
    }
}
