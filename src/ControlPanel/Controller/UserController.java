package ControlPanel.Controller;

import ControlPanel.Models.UserModel;
import ControlPanel.View.LoginView;
import ControlPanel.View.MainFrame;

public class UserController {
    private LoginView loginView;
    private MainFrame frame;
    private UserModel model;
    public UserController(MainFrame frame, LoginView loginView, UserModel model) {
        this.frame = frame;
        this.model = model;
        this.loginView = loginView;
        initController();
    }

    /**
     * Sets up all the event listeners for the User related panels and adds the panel to the card
     * layout. Views can be shown with frame.showPanel("panel-name")
     */
    private void initController() {
        loginView.initPanel();
        loginView.getLogin().addActionListener(e -> this.login(loginView.getUsername(),
                loginView.getPassword()));
        frame.addView(loginView.getContainerPanel(), "login");
    }
    /**
     * Attempts to login the user
     * @param username  the username to send to the server
     * @param password  the password to hash and send to the user
     * @return  true/false depending on the server response
     */
    private boolean login (String username, String password){
        //TODO: hash the password.
        System.out.println(username + " - " + password);
        frame.showPanel("mainMenu");
        return false;
    }
}
