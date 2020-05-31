package Viewer;

import Viewer.Controllers.BillboardController;
import Viewer.Models.BillboardModel;
import Viewer.Views.BillboardView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main method to run Billboard
 *
 */
public class Viewer {
    public static void main(String args[]){
        try
        {
            BillboardModel model = new BillboardModel();
            BillboardView view = new BillboardView();
            BillboardController controller = new BillboardController(model, view);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    controller.updateViewer();
                }}, 0, 15000);
        }
        catch (Exception e)
        {
            //error handling code
        }
    }
}
