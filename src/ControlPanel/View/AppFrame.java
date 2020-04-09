package ControlPanel.View;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class AppFrame {
    private CardLayout cardLayout;
    private JPanel content;
    private JFrame frame;
    private LoginView loginView;
    private MainNav mainNav;

    public AppFrame(MainNav mainNav){
        this.frame = new JFrame("Control Panel");

        // Setup the CardLayout and content panel
        this.cardLayout = new CardLayout();
        this.content = new JPanel(new CardLayout());
        frame.getContentPane().setLayout(new BorderLayout());
        frame.add(content, BorderLayout.CENTER);
        this.mainNav = mainNav;
        frame.getContentPane().add(mainNav.getPanel(), BorderLayout.NORTH);
        mainNav.setVisibility(false);
    }
    public void addView(JPanel view, String name){
        content.add(name, view);
    }

    public void initialiseViews() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setPreferredSize(new Dimension(1280, 720));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void changeView(String panelName){
        CardLayout cl = (CardLayout)(content.getLayout());
        cl.show(content, panelName);
    }

    public void changeView(String panelName, boolean navState){
        CardLayout cl = (CardLayout)(content.getLayout());
        cl.show(content, panelName);
        mainNav.setVisibility(navState);
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
