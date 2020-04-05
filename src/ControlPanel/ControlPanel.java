package ControlPanel;

import ControlPanel.Controller.BillboardController;
import ControlPanel.Controller.ScheduleController;
import ControlPanel.Controller.UserController;
import ControlPanel.Models.BillboardModel;
import ControlPanel.Models.ScheduleModel;
import ControlPanel.Models.UserModel;
import ControlPanel.View.LoginView;
import ControlPanel.View.MainFrame;


public class ControlPanel {
    public static void main(String args[]){

        try
        {
            // Instantiate the views
            LoginView loginView = new LoginView();

            // Instantiate the view manager
            MainFrame mainFrame = new MainFrame();


            // Instantiate the models
            BillboardModel billboardModel = new BillboardModel();
            ScheduleModel scheduleModel = new ScheduleModel();
            UserModel userModel = new UserModel();

            // Start the controllers
            BillboardController billboardController = new BillboardController(mainFrame, billboardModel);
            ScheduleController scheduleController = new ScheduleController(mainFrame, scheduleModel);
            UserController userController = new UserController(mainFrame, loginView, userModel);

            // Start the views
            mainFrame.initialiseViews();
            mainFrame.showPanel("login");
        }
        catch (Exception e)
        {
            //error handling code
        }
    }
}
