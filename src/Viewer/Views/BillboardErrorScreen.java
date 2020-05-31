package Viewer.Views;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Creates an Error screen to be displayed in case there is no current Billboard
 * to display, or if connection with server failed
 */
public class BillboardErrorScreen{

    JFrame frame = new JFrame(); // Create and set up window frame

    /**
     * Main method for attaching listener events and labels into frame.
     */
    public BillboardErrorScreen() throws MalformedURLException {

        // Method for program to implement closure of Billboard when ESC Key or Mouse Click is pressed/clicked
        attachListenerEvents();

        // Method for creating all labels to attach to GUI frame
        createLabels();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Makes Billboard fit maximum size of screen
        frame.setUndecorated(true); // Makes 100% full screen, no frame.
        frame.setVisible(true); // Show Billboard
    }

    /**
     * Attach events to listen for mouseclick or escape key clicks.
     */
    private void attachListenerEvents() {
        Input input = new Input(frame); // Imports Input.java into new instance called input
        input.attachMouseEvent(); // Attach Mouse Listener to method
        input.attachESCKeyEvent(); // Attach ESC Key Listener to method
    }

    /**
     * Set up for GUI by creating labels and placing them in frame.
     */
    private void createLabels() throws MalformedURLException {
        Container container = frame.getContentPane(); // Create a container in frame to insert labels into
        container.setLayout(new BorderLayout()); // Set up BorderLayout to be used with positioning of labels in frame.
        container.setBackground(new Color(0x990000)); // Change background colour

        // Sets up text for <message> label
        JLabel welcomeLabel = new JLabel("Error loading screen...",
                JLabel.CENTER); // Centers text in the center of Billboard (X axis)
        welcomeLabel.setForeground(new Color(0xFFFF00)); // Changes font colour
        container.add(welcomeLabel, BorderLayout.NORTH); // Places label on top part of Billboard (Y axis)

        // Sets up and display URL image for <picture> label
        URL url = new URL("https://images.summitmedia-digital.com/esquiremagph/images/2019/05/14/Elections-Java-Script-Error.jpg");
        ImageIcon image = new ImageIcon(new ImageIcon(url).getImage());
        JLabel imageLabel = new JLabel(image);
        imageLabel.setIcon(image);
        container.add(imageLabel, BorderLayout.CENTER); // Places label on middle part of Billboard (Y axis)

        // Sets up text for <information> label
        JLabel checkoutLabel = new JLabel("Retrying Connection...",
                JLabel.CENTER); // Centers text in the center of Billboard (X axis)
        checkoutLabel.setForeground(new Color(0x00FFFF)); // Changes font colour
        container.add(checkoutLabel, BorderLayout.SOUTH); // Places label on bottom part of Billboard (Y axis)
    }
}
