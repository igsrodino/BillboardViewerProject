package ControlPanel.View;

import javax.swing.*;
import java.awt.*;

/**
 * View for login screen.
 */
public class LoginView {
    private JPasswordField password;
    private JPanel containerPanel;
    private JTextField username;
    private JButton login;

    public JPanel getLoginPanel() {
        return loginPanel;
    }

    private JPanel loginPanel;

    public JButton getLogin() {
        return login;
    }

    public JPanel getContainerPanel() {
        return containerPanel;
    }

    public String getPassword() {
        return new String(password.getPassword());
    }

    public String getUsername() {
        return username.getText();
    }

    public void initPanel() {
        containerPanel.setMaximumSize(new Dimension(500, 500));
        containerPanel.revalidate();
    }
}
