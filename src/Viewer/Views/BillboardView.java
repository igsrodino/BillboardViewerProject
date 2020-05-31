package Viewer.Views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

/**
 * Gui setup for Billboard screen.
 * Exposes several methods so the controller can update the view.
 */
public class BillboardView {
    private JFrame frame = new JFrame(); // Create and set up window frame
    private Container container = frame.getContentPane(); // Create a container in frame to insert <message> labels into
    private JLabel welcomeLabel;
    private JLabel checkoutLabel;
    private JLabel imageLabel;

    /**
     * Main method for attaching listener events and labels into frame.
     */
    public BillboardView() throws IOException {
        welcomeLabel = null;
        checkoutLabel = null;
        imageLabel = null;

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
     * Set up message label for GUI ready to add to frame.
     */
    private void welcomeLabel() {
        // Sets up text for <message> label
        welcomeLabel = new JLabel("", JLabel.CENTER); // Centers text in the center of Billboard (X axis)
        welcomeLabel.setForeground(Color.BLACK); // Changes font colour
        welcomeLabel.setFont(new Font("", Font.BOLD, 70));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 1000;

        //gbc.insets = new Insets(0,-500,0,-250);
        container.add(welcomeLabel, gbc); // Places label on top part of Billboard (Y axis)
    }

    /**
     * Set up image label for GUI ready to add to frame.
     */
    private void imageLabel() {
        // Sets up and display URL image for <picture> label
        ImageIcon image = new ImageIcon(); // Get image if it exists
        imageLabel = new JLabel(image, JLabel.CENTER); // New instance for image icon in a label
        imageLabel.setIcon(image); // Place image in label
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipady = 250;
        container.add(imageLabel, gbc); // Places label on middle part of Billboard (Y axis)
    }

    /**
     * Set up information label for GUI ready to add to frame.
     */
    private void checkoutLabel() {
        // Sets up text for <information> label
        checkoutLabel = new JLabel("", JLabel.CENTER); // Centers text in the center of Billboard (X axis)
        checkoutLabel.setForeground(Color.BLACK); // Changes font colour
        checkoutLabel.setFont(new Font("", Font.ITALIC, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 1000;
        container.add(checkoutLabel, gbc); // Places label on bottom part of Billboard (Y axis)
    }

    /**
     * Places all labels in frame.
     */
    private void createLabels() {
        // Set up billboard layout
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.WHITE); // Change background colour

        // Add labels
        welcomeLabel();
        imageLabel();
        checkoutLabel();
    }

    /**
     * Resizes image received from URL depending on dimensions.
     */
    public void setUrl (String url){
        try {
            URL imgUrl = new URL(url);
            ImageIO.read(imgUrl); // Checks to see if image exists, if not it displays
            ImageIcon image = new ImageIcon(new ImageIcon(imgUrl).getImage()); // Get image if it exists
            int height = image.getIconHeight();
            int width = image.getIconWidth();
            int newSize = 275;
            double rectangleRatio = 3.1;
            ImageIcon imageResized;
            if (height > width) {
                // If image is a standing rectangle (height larger than width)
                imageResized = new ImageIcon(new ImageIcon(imgUrl).getImage().getScaledInstance((int) (newSize/rectangleRatio), newSize, Image.SCALE_DEFAULT));
            }
            else if (height < width) {
                // If image is a rectangle
                imageResized = new ImageIcon(new ImageIcon(imgUrl).getImage().getScaledInstance((int) (newSize*rectangleRatio), newSize, Image.SCALE_DEFAULT));
            }
            else {
                // If image is a square
                imageResized = new ImageIcon(new ImageIcon(imgUrl).getImage().getScaledInstance(newSize, newSize, Image.SCALE_DEFAULT));
            }
            imageLabel.setIcon(imageResized);
            frame.revalidate();
            frame.repaint();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
    }

    /**
     * Resizes image received from data depending on dimensions.
     */
    public void setData (String data){
        try {
            String imgData = data;
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decodedByteArray = decoder.decode(imgData);

            // Checks to see if image exists, if not it displays
            ImageIcon image = new ImageIcon(decodedByteArray); // Get image if it exists
            int height = image.getIconHeight();
            int width = image.getIconWidth();

            int newSize = 275;
            double rectangleRatio = 3.1;
            ImageIcon imageResized;
            if (height > width) {
                // If image is a standing rectangle (height larger than width)
                imageResized =new ImageIcon(new ImageIcon(decodedByteArray).getImage().getScaledInstance((int) (newSize/rectangleRatio), newSize, Image.SCALE_DEFAULT));
            }
            else if (height < width) {
                // If image is a rectangle
                imageResized = new ImageIcon(new ImageIcon(decodedByteArray).getImage().getScaledInstance((int) (newSize*rectangleRatio), newSize, Image.SCALE_DEFAULT));
            }
            else {
                // If image is a square
                imageResized = new ImageIcon(new ImageIcon(decodedByteArray).getImage().getScaledInstance(newSize, newSize, Image.SCALE_DEFAULT));
            }
            imageLabel.setIcon(imageResized);
            frame.revalidate();
            frame.repaint();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
    }

    /**
     * Sets and returns message.
     */
    public void setMessage(String message) {
        welcomeLabel.setText(message);
    }

    /**
     * Sets and returns information.
     */
    public void setInformation(String information){
        checkoutLabel.setText(information);
    }

    /**
     * Sets and returns background colour.
     */
    public void setBackgroundColour(String backgroundColour){
        frame.getContentPane().setBackground(Color.decode(backgroundColour));
    }

    /**
     * Sets and returns information text colour.
     */
    public void setInformationColour(String informationColour){
        checkoutLabel.setForeground(Color.decode(informationColour));
    }

    /**
     * Sets and returns message text colour.
     */
    public void setMessageColour(String messageColour){
        welcomeLabel.setForeground(Color.decode(messageColour));
    }
}