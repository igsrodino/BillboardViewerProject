package Viewer;

import Viewer.Controllers.BillboardController;
import Viewer.Models.BillboardModel;
import Viewer.Views.BillboardErrorScreen;
import Viewer.Views.BillboardView;

import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Main method to run Billboard Viewer and update every 15 seconds
 *
 */
public class Viewer {
    public static void main(String args[]) throws MalformedURLException {
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
            new BillboardErrorScreen();
        }
    }
}
