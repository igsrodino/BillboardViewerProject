package ControlPanel.View;

import javax.swing.*;

public class BillboardView {
    private JPanel containerPanel;
    private JPanel billboardPanel;
    private JList billboardList;
    private JTabbedPane billboardManager;
    private JButton deleteBillboardButton;
    private JButton saveChangesButton;
    private JButton uploadXMLButton;
    private JButton downloadXMLButton;
    private JTextField textField1;
    private JButton setMessageTextColourButton;
    private JTextField textField2;
    private JButton setInformationTextColourButton;
    private JTextField textField3;
    private JButton uploadImageButton;
    private JButton setBackgroundColourButton;

    public JPanel getPanel() {
        return billboardPanel;
    }
}
