package ControlPanel;

import ControlPanel.Controller.BillboardController;
import ControlPanel.Controller.ScheduleController;
import ControlPanel.Controller.UserController;
import ControlPanel.Models.BillboardModel;
import ControlPanel.Models.ScheduleModel;
import ControlPanel.Models.UserModel;
import ControlPanel.View.*;

/**
 * GUI setup for control panel.
 */
public class ControlPanel {
    public static void main(String args[]){

        try
        {
            // Instantiate the views
            LoginView loginView = new LoginView();
            MainNav mainNav = new MainNav();
            BillboardView billboardView = new BillboardView();
            ScheduleView scheduleView = new ScheduleView();
            UserView userView = new UserView();

            // Instantiate the view manager
            AppFrame appFrame = new AppFrame(mainNav);

            // Instantiate the models
            BillboardModel billboardModel = new BillboardModel();
            ScheduleModel scheduleModel = new ScheduleModel();
            UserModel userModel = new UserModel();

            // Start the controllers
            BillboardController billboardController = new BillboardController(appFrame,
                    billboardModel, billboardView, mainNav, userModel);
            ScheduleController scheduleController = new ScheduleController(appFrame,
                    scheduleModel, scheduleView, mainNav, billboardModel);
            UserController userController = new UserController(appFrame, userModel, loginView,
                    userView, mainNav);

            // Start the views
            appFrame.changeView("login");
        }
        catch (Exception e)
        {
            System.out.println("Error occurred");
        }
    }
}
