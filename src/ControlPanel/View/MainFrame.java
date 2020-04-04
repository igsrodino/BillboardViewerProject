package ControlPanel.View;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private CardLayout cardLayout;
    private JPanel content;
    private JFrame frame;
    private OpeningScreen openingView;
    private LoginView loginView;

    public MainFrame(LoginView loginView){
        this.loginView = loginView;

        this.frame = new JFrame("Control Panel");

        // Setup the CardLayout and content panel
        this.cardLayout = new CardLayout();
        this.content = new JPanel(new CardLayout());

        // Instantiate the rest of the views and add them to the content panel
        content.add("login", this.loginView.getLoginPanel());
//        this.openingView = new OpeningScreen();
//        content.add("openingView", openingView.getPanel());

        frame.getContentPane().setLayout(new BorderLayout());
        frame.add(content, BorderLayout.CENTER);
    }
    public void addView(JPanel view){

    }

    public void initialiseViews() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void showPanel(String panelName){
        CardLayout cl = (CardLayout)(content.getLayout());
        cl.show(content, panelName);
//        content.revalidate();
//        frame.revalidate();
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
