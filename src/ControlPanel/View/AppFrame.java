package ControlPanel.View;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

/**
 * Sets up view for Control Panel.
 */
public class AppFrame {
    private CardLayout cardLayout;
    private JPanel content;
    private JFrame frame;
    private LoginView loginView;
    private MainNav mainNav;

    /**
     * Initialize set up view for control panel.
     */
    public AppFrame(MainNav mainNav){
        this.mainNav = mainNav;
        this.frame = new JFrame("Control Panel");
        this.cardLayout = new CardLayout();
        this.content = new JPanel(new CardLayout());
        initialiseViews();
    }

    /**
     * Adds view in panel depending on content.
     */
    public void addView(JPanel view, String name){
        content.add(name, view);
    }

    /**
     * Initializes view for control panel.
     */
    public void initialiseViews() {
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(mainNav.getPanel(), BorderLayout.NORTH);
        frame.add(content, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1280, 720));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Changes view and layout.
     */
    public void changeView(String panelName){
        CardLayout cl = (CardLayout)(content.getLayout());
        cl.show(content, panelName);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Changes view and layout depending on the navState.
     */
    public void changeView(String panelName, boolean navState){
        CardLayout cl = (CardLayout)(content.getLayout());
        cl.show(content, panelName);
        mainNav.setVisibility(navState);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Gets and returns the frame.
     */
    public Frame getFrame() {
        return this.frame;
    }
}
