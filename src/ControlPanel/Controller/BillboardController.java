package ControlPanel.Controller;

import ControlPanel.Models.BillboardModel;
import ControlPanel.View.MainFrame;

public class BillboardController {
    private MainFrame frame;
    private BillboardModel model;
    public BillboardController(MainFrame frame, BillboardModel model) {
        this.frame = frame;
        this.model = model;
    }

}
