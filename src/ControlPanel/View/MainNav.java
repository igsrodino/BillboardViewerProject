package ControlPanel.View;

import javax.swing.*;
import java.awt.*;

/**
 * View for main navigation screen.
 */
public class MainNav {
    private JButton billboardManagementButton;
    private JButton billboardSchedulingButton;
    private JButton userAdministrationButton;
    private JButton logOutButton;
    private JPanel panel;

    public MainNav(){
        this.setVisibility(false);
    }
    public Component getPanel() {
        return panel;
    }

    public void setVisibility(boolean state) {
        panel.setVisible(state);
    }

    public JButton getUsers() {
        return userAdministrationButton;
    }

    public JButton getBillboard() {
        return billboardManagementButton;
    }

    public JButton getSchedule() {
        return billboardSchedulingButton;
    }

    public JButton getLogout() {
        return logOutButton;
    }
}
