package ControlPanel.View;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private CardLayout cardLayout;
    private JPanel content;
    private JFrame frame;
    private OpeningScreen openingView;
    private LoginView loginView;

    public MainFrame(){
        this.frame = new JFrame("Control Panel");

        // Setup the CardLayout and content panel
        this.cardLayout = new CardLayout();
        this.content = new JPanel(new CardLayout());

        // Instantiate the rest of the views and add them to the content panel
        this.loginView = new LoginView();
        content.add("login", loginView.getLoginPanel());
        this.openingView = new OpeningScreen();
        content.add("openingView", openingView.getPanel());
        // Add the CardLayout to the frame

        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.add(content, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void showPanel(String panelName){
        cardLayout.show(content, panelName);
        content.revalidate();
        frame.revalidate();
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
        content.revalidate();
        frame.revalidate();
    }
}
