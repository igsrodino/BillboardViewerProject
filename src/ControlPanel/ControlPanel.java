package ControlPanel;

import ControlPanel.Controller.MasterController;
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
            MainFrame frame = new MainFrame();
            MasterController masterController = new MasterController(billboardModel,
                    scheduleModel, userModel, frame);
            masterController.init();

            // Assemble all the pieces of the MVC
//            Model m = new Model("Sylvain", "Saurel");
//            View v = new View("MVC with SSaurel");
//            Controller c = new Controller(m, v);
//            c.initController();

        }
        catch (Exception e)
        {
            //error handling code
        }
    }
}
