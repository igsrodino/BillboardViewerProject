package ControlPanel.Controller;

import ControlPanel.Models.ScheduleModel;
import ControlPanel.View.AppFrame;
/**
 * Manages events in the Billboard Scheduling views
 */
public class ScheduleController {
    private AppFrame frame;
    private ScheduleModel model;
    public ScheduleController(AppFrame frame, ScheduleModel model) {
        this.frame = frame;
        this.model = model;
    }
}
