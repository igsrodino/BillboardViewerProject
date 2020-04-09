package ControlPanel.Controller;

import ControlPanel.View.MainNav;

/**
 * Manages changes between the three primary views (Billboard Management, Billboard Scheduling,
 * and User Administration).
 */
public class ViewController {
    private MainNav mainNav;

    /**
     * Starts the controller.
     * @param mainNav the main navigation toolbar
     */
    public ViewController(MainNav mainNav) {
        this.mainNav = mainNav;
        initController();
    }


    /**
     * Sets up event controllers for the view change buttons on the main navigation toolbar
     */
    private void initController(){
        mainNav.setVisibility(false);
    }
}
