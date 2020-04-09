package ControlPanel.Controller;

import ControlPanel.Models.ScheduleModel;
import ControlPanel.View.AppFrame;

public class ScheduleController {
    private AppFrame frame;
    private ScheduleModel model;
    public ScheduleController(AppFrame frame, ScheduleModel model) {
        this.frame = frame;
        this.model = model;
    }
}
