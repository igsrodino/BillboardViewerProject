package Viewer.Controllers;

import Viewer.Models.BillboardModel;
import Viewer.Models.BillboardPOJO;
import Viewer.Views.BillboardView;

public class BillboardController {
    private BillboardModel model;
    private BillboardView view;
    private BillboardPOJO bb;

    public BillboardController(BillboardModel model, BillboardView view){
        this.model = model;
        this.view = view;
        this.bb = null;
    }
    public void startViewer(){
        // TODO: Create a while loop that calls model.getBillboard() every 15 seconds. Assign the
        //  return value to this.bb
        // TODO: Call BillboardPOJO getters on bb, and update the view with the returned data.
        //  Call view methods to do this.

    }
}
