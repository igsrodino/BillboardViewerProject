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
        this.mainNav = mainNav;
        this.frame = new JFrame("Control Panel");
        this.cardLayout = new CardLayout();
        this.content = new JPanel(new CardLayout());
        initialiseViews();
    }
    public void addView(JPanel view, String name){
        content.add(name, view);
    }

    public void initialiseViews() {
        frame.getContentPane().setLayout(new BorderLayout());
        frame.add(content, BorderLayout.CENTER);
        frame.getContentPane().add(mainNav.getPanel(), BorderLayout.NORTH);
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
