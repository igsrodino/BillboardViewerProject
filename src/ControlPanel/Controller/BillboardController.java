package ControlPanel.Controller;

import ControlPanel.Models.BillboardModel;
import ControlPanel.View.AppFrame;
import ControlPanel.View.BillboardView;
import ControlPanel.View.MainNav;

import javax.swing.*;
import java.awt.*;

/**
 * Manages events in the Billboard Management views
 */
public class BillboardController {
    private BillboardView billboardView;
    private AppFrame frame;
    private BillboardModel model;
    private MainNav mainNav;

    public BillboardController(AppFrame frame, BillboardModel model, BillboardView billboardView, MainNav mainNav) {
        this.frame = frame;
        this.model = model;
        this.billboardView = billboardView;
        this.mainNav = mainNav;
        this.initController();
    }

    private void initController() {
        // Add event listeners
        mainNav.getBillboard().addActionListener(e -> this.frame.changeView("billboards"));
        billboardView.getSetMessageTextColourButton().addActionListener(e->this.getColor());

        // Add views to the card layout
        frame.addView(billboardView.getPanel(), "billboards");
    }

    private void getColor(){
        Color bg = JColorChooser.showDialog(null, "Set message text colour", null);
        String hex = Integer.toHexString(bg.getRGB() & 0xffffff);
        if(hex.length() < 6)
        {
            if(hex.length()==5)
                hex = "0" + hex;
            if(hex.length()==4)
                hex = "00" + hex;
            if(hex.length()==3)
                hex = "000" + hex;
        }
        hex = "#" + hex;

        System.out.println(hex);
    }
}
