package ControlPanel.View;

import javax.swing.*;

public class LoginView {
    private JPasswordField password;
    private JPanel loginPanel;
    private JTextField username;
    private JButton login;

    public JPanel getLoginPanel() {
        return loginPanel;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public JTextField getUsername() {
        return username;
    }

}
