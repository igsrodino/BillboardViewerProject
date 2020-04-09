package ControlPanel.View;

import javax.swing.*;
import java.awt.*;

public class MainNav {
    private JButton billboardManagementButton;
    private JButton billboardSchedulingButton;
    private JButton userAdministrationButton;
    private JButton logOutButton;
    private JPanel panel;

    public Component getPanel() {
        return panel;
    }

    public void setVisibility(boolean state) {
        panel.setVisible(state);
    }
}
