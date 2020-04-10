package ControlPanel.Controller;

import ControlPanel.Models.ScheduleModel;
import ControlPanel.View.AppFrame;
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
    public ScheduleController(AppFrame frame, ScheduleModel model, ScheduleView scheduleView, MainNav mainNav) {
        this.frame = frame;
        this.model = model;
        this.mainNav = mainNav;
        this.scheduleView = scheduleView;
        this.initController();
    }

    private void initController() {
        mainNav.getSchedule().addActionListener(e -> this.frame.changeView("schedule"));
        frame.addView(scheduleView.getPanel(), "schedule");
    }
}
