package ControlPanel;

import ControlPanel.Controller.BillboardController;
import ControlPanel.Controller.ScheduleController;
import ControlPanel.Controller.UserController;
import ControlPanel.Controller.ViewController;
import ControlPanel.Models.BillboardModel;
import ControlPanel.Models.ScheduleModel;
import ControlPanel.Models.UserModel;
import ControlPanel.View.LoginView;
import ControlPanel.View.AppFrame;
import ControlPanel.View.MainNav;


public class ControlPanel {
    public static void main(String args[]){

        try
        {
            // Instantiate the views
            LoginView loginView = new LoginView();
            MainNav mainNav = new MainNav();

            // Instantiate the view manager
            AppFrame appFrame = new AppFrame(mainNav);

            // Instantiate the models
            BillboardModel billboardModel = new BillboardModel();
            ScheduleModel scheduleModel = new ScheduleModel();
            UserModel userModel = new UserModel();

            // Start the controllers
            BillboardController billboardController = new BillboardController(appFrame, billboardModel);
            ScheduleController scheduleController = new ScheduleController(appFrame, scheduleModel);
            UserController userController = new UserController(appFrame, loginView, userModel);
            ViewController viewController = new ViewController(mainNav);

            // Start the views
//            appFrame.initialiseViews();
            appFrame.changeView("login");
        }
        catch (Exception e)
        {
            //error handling code
        }
    }
}
