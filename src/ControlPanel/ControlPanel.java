package ControlPanel;

import ControlPanel.Models.BillboardModel;
import ControlPanel.Models.ScheduleModel;
import ControlPanel.Models.UserModel;
import ControlPanel.View.LoginView;
import ControlPanel.View.MainFrame;

public class ControlPanel {
    public static void main(String args[]){
        try
        {
            BillboardModel billboardModel = new BillboardModel();
            ScheduleModel scheduleModel = new ScheduleModel();
            UserModel userModel = new UserModel();

            // MainFrame sets up the primary view frame/container. All other views are added to
            // it with the mainFrame.addPanel(name, panel) method. Panels can then be shown with
            // mainFrame.showPanel(panelName)

            MainFrame mainFrame = new MainFrame();
            // TODO: Instantiate views and add them to mainFrame with mainframe.addPanel
            //  (panelName, panel)
            mainFrame.showPanel("login");

        }
        catch (Exception e)
        {
            //error handling code
        }
    }
}
