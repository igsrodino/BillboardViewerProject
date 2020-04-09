package ControlPanel.View;

import javax.swing.*;
import java.awt.*;

public class MainView {
    private JPanel containerPanel;
    private JTabbedPane mainNav;
    private JPanel billboardScheduling;
    private JPanel billboardManagement;
    private JTabbedPane billboardTabs;
    private JButton addNewBillboardButton;
    private JPanel userAdministration;

    public JPanel getContainerPanel() {
        return containerPanel;
    }
    public void initPanel(){
        mainNav.setTabComponentAt(0,createTab("Billboard Management"));
        mainNav.setTabComponentAt(1,createTab("Billboard Scheduling"));
        mainNav.setTabComponentAt(2,createTab("User Administration"));
    }
    private JLabel createTab(String tabName){
        JLabel tab = new JLabel(tabName);
        tab.setPreferredSize(new Dimension(150, 50));
        return tab;
    }
}
