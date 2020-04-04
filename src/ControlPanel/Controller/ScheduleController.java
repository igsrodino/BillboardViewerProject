package ControlPanel.Controller;

import ControlPanel.Models.ScheduleModel;
import ControlPanel.View.MainFrame;

public class ScheduleController {
    private MainFrame frame;
    private ScheduleModel model;
    public ScheduleController(MainFrame frame, ScheduleModel model) {
        this.frame = frame;
        this.model = model;
    }
}
