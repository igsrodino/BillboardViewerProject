package ControlPanel.Controller;

import ControlPanel.Models.BillboardModel;
import ControlPanel.View.AppFrame;
/**
 * Manages events in the Billboard Management views
 */
public class BillboardController {
    private AppFrame frame;
    private BillboardModel model;
    public BillboardController(AppFrame frame, BillboardModel model) {
        this.frame = frame;
        this.model = model;
    }

}
