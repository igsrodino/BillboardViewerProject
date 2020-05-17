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
    public void startViewer() {
        bb = model.getBillboard();

        if (bb.getMessage().length() > 0) {
            view.setMessage(bb.getMessage());
        }
        if (bb.getMessageColour().length() > 0) {
            view.setMessageColour(bb.getMessageColour());
        }
        if (bb.getInformation().length() > 0) {
            view.setInformation(bb.getInformation());
        }
        if (bb.getInformationColour().length() > 0) {
            view.setInformationColour(bb.getInformationColour());
        }
        if (bb.getBackgroundColour().length() > 0) {
            view.setBackgroundColour(bb.getBackgroundColour());
        }
        if (bb.getPictureURL().length() > 0) {
            view.setUrl(bb.getPictureURL());
        }
        else if (bb.getPictureData().length() > 0) {
            view.setData(bb.getPictureData());
        }


        // TODO: Create a while loop that calls model.getBillboard() every 15 seconds. Assign the
        //  return value to this.bb
        // TODO: Call BillboardPOJO getters on bb, and update the view with the returned data.
        //  Call view methods to do this.
    }
}
