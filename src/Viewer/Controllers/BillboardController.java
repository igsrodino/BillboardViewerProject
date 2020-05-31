package Viewer.Controllers;

import Viewer.Models.BillboardModel;
import Viewer.Models.BillboardPOJO;
import Viewer.Views.BillboardView;

import java.io.IOException;

/**
 * For updating the viewer in the case XML document has all or some information, image or message tags.
 */
public class BillboardController{
    private BillboardModel model;
    private BillboardView view;
    private BillboardPOJO bb;

    public BillboardController(BillboardModel model, BillboardView view){
        this.model = model;
        this.view = view;
        this.bb = null;
    }

    /**
     * Sets message, image or information to billboard depending on how many are received.
     */
    public void updateViewer() {
       try{
           bb = model.getBillboard();
       }catch(IOException e){
           String errorBoard = "<billboard background=\"#0000FF\">\n" +
                   "    <message colour=\"#FFFF00\">Network Error</message>\n" +
                   "    <information colour=\"#00FFFF\">Error message: " + e.getMessage() +
                   "</information>"+
                   "</billboard>";
           bb = model.getBillboard(errorBoard);
           view.setData("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=");
           System.err.println(e.getMessage());
           System.err.println(e.getStackTrace());

       }
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
    }
}
