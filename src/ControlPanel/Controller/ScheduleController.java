package ControlPanel.Controller;

import ControlPanel.Models.BillboardModel;
import ControlPanel.Models.ScheduleModel;
import ControlPanel.View.AppFrame;
import ControlPanel.View.EditSchedulePopup;
import ControlPanel.View.MainNav;
import ControlPanel.View.ScheduleView;

/**
 * Manages events in the Billboard Scheduling views
 */
public class ScheduleController {
    private MainNav mainNav;
    private AppFrame frame;
    private ScheduleModel model;
    private ScheduleView scheduleView;
    private BillboardModel billboardModel;

    public ScheduleController(AppFrame frame, ScheduleModel model, ScheduleView scheduleView, MainNav mainNav, BillboardModel billboardModel) {
        this.frame = frame;
        this.model = model;
        this.mainNav = mainNav;
        this.scheduleView = scheduleView;
        this.billboardModel = billboardModel;
        this.initController();
    }

    private void initController() {
        // Event Listeners
        mainNav.getSchedule().addActionListener(e -> this.frame.changeView("schedule"));
        scheduleView.getScheduleBillboard().addActionListener(e -> this.editSchedule("Test", 0));
        // Populate the lists
        // Add view to card panel
        frame.addView(scheduleView.getPanel(), "schedule");
    }

    private void editSchedule(String day, int index){
            String[] result = EditSchedulePopup.display("Test").split(",");
            if(result[3] == "true"){
                // Delete the selected schedule

            } else {
                // Change the schedule
            }
    }
    private void addSchedule(){

    }
}
