package ControlPanel.Controller;

import ControlPanel.Models.BillboardModel;
import ControlPanel.Models.ScheduleModel;
import ControlPanel.Models.UserModel;
import ControlPanel.View.MainFrame;

public class MasterController {
    BillboardModel billboardModel;
    ScheduleModel scheduleModel;
    UserModel userModel;
    MainFrame frame;
    public MasterController(BillboardModel bm, ScheduleModel sm, UserModel um, MainFrame mf){
        this.billboardModel = bm;
        this.scheduleModel = sm;
        this.userModel =um;
        this.frame = mf;
    }
    public void init() {
        // Starts the drawing?
    }
}
