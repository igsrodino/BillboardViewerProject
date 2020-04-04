package ControlPanel;

import ControlPanel.Models.BillboardModel;
import ControlPanel.Models.ScheduleModel;
import ControlPanel.Models.UserModel;
import ControlPanel.View.MainFrame;


public class ControlPanel {
    public static void main(String args[]){

        try
        {
            BillboardModel billboardModel = new BillboardModel();
            ScheduleModel scheduleModel = new ScheduleModel();
            UserModel userModel = new UserModel();

            MainFrame mainFrame = new MainFrame();
            mainFrame.showPanel("login");

        }
        catch (Exception e)
        {
            //error handling code
        }
    }
}
