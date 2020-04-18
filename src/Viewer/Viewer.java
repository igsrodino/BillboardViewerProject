package Viewer;

import Viewer.Controllers.BillboardController;
import Viewer.Models.BillboardModel;
import Viewer.Views.BillboardView;

public class Viewer {
    public static void main(String args[]){
        try
        {
            BillboardModel model = new BillboardModel();
            BillboardView view = new BillboardView();
            BillboardController controller = new BillboardController(model, view);
            controller.startViewer();

        }
        catch (Exception e)
        {
            //error handling code
        }
    }
}
