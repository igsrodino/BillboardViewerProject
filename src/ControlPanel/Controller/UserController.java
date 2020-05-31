package ControlPanel.Controller;

import ControlPanel.Models.UserModel;
import ControlPanel.View.LoginView;
import ControlPanel.View.AppFrame;
import ControlPanel.View.MainNav;
import ControlPanel.View.UserView;

/**
 * Manages events in the User Administration views
 */
public class UserController {
    private LoginView loginView;
    private AppFrame frame;
    private UserModel model;
    private UserView userView;
    private MainNav mainNav;

    public UserController(AppFrame frame, UserModel model, LoginView loginView, UserView userView, MainNav mainNav) {
        this.frame = frame;
        this.model = model;
        this.loginView = loginView;
        this.userView = userView;
        this.mainNav = mainNav;
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

        mainNav.getUsers().addActionListener(e -> this.frame.changeView("users"));
        frame.addView(userView.getPanel(), "users");

        mainNav.getLogout().addActionListener(e->this.logout());
    }
    /**
     * Attempts to login the user
     * @param username  the username to send to the server
     * @param password  the password to hash and send to the user
     * @return  true/false depending on the server response
     */
    private boolean login (String username, String password){

        System.out.println(username + " - " + password);
        frame.changeView("billboards", true);
        return false;
    }

    private void logout(){
        System.out.println("Logged out");
        frame.changeView("login", false);
    }
}
