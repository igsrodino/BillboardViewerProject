package Viewer;

import Viewer.Controllers.BillboardController;
import Viewer.Models.BillboardModel;
import Viewer.Views.BillboardView;

import java.util.Timer;
import java.util.TimerTask;

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
                }}, 0, 2000);
        }
        catch (Exception e)
        {
            //error handling code
        }
    }
}
