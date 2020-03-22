package Viewer.Controllers;

import Viewer.Models.BillboardModel;
import Viewer.Views.BillboardView;

public class BillboardController {
    private BillboardModel model;
    private BillboardView view;

    public BillboardController(BillboardModel model, BillboardView view){
        this.model = model;
        this.view = view;
    }
}
