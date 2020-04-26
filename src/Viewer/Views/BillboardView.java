package Viewer.Views;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Flow;

/* Gui stuff goes in this classes
 * Exposes several methods so the controller can update the view
 * */
public class BillboardView {
    private JFrame frame = new JFrame(); // Create and set up window frame
    private Container container = frame.getContentPane(); // Create a container in frame to insert <message> labels into
    private JLabel welcomeLabel;
    private JLabel checkoutLabel;
    private JLabel imageLabel;

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
    private void attachListenerEvents() {
        Input input = new Input(frame); // Imports Input.java into new instance called input
        input.attachMouseEvent(); // Attach Mouse Listener to method
        input.attachESCKeyEvent(); // Attach ESC Key Listener to method
    }
    private void createLabels() throws IOException {
        // frame.setLayout(new GridBagLayout());
        // LINE ABOVE IS CORRECT LAYOUT, THOUGH IT LAYS EVERYTHING HORIZONTALLY,
        // NOT VERTICALLY...NEED TO FIX AND DELETE LINE BELOW THIS COMMENT.
        frame.setLayout(new GridLayout(3, 1)); // THIS WORKS NOW BUT IF XML
        // IS MISSING INFORMATION, URL, OR MESSAGE TAG THEN ROWS DO NOT MOVE CLOSER TO THE CENTER


        frame.getContentPane().setBackground(Color.WHITE); // Change background colour

        // Sets up text for <message> label
        welcomeLabel = new JLabel("", JLabel.CENTER); // Centers text in the center of Billboard (X axis)
        welcomeLabel.setForeground(Color.BLACK); // Changes font colour
        welcomeLabel.setFont(new Font("", Font.BOLD, 70));
        container.add(welcomeLabel); // Places label on top part of Billboard (Y axis)

        // Sets up and display URL image for <picture> label
        ImageIcon image = new ImageIcon(); // Get image if it exists
        imageLabel = new JLabel(image, JLabel.CENTER); // New instance for image icon in a label
        imageLabel.setIcon(image); // Place image in label
        container.add(imageLabel); // Places label on middle part of Billboard (Y axis)

        // Sets up text for <information> label
        checkoutLabel = new JLabel("", JLabel.CENTER); // Centers text in the center of Billboard (X axis)
        checkoutLabel.setForeground(Color.BLACK); // Changes font colour
        checkoutLabel.setFont(new Font("", Font.ITALIC, 30));
        container.add(checkoutLabel); // Places label on bottom part of Billboard (Y axis)
    }
    public void setUrl (String url){
        try {
            URL imgUrl = new URL(url);
            ImageIO.read(imgUrl); // Checks to see if image exists, if not it displays
            ImageIcon image = new ImageIcon(new ImageIcon(imgUrl).getImage()); // Get image if it
            int height = image.getIconHeight();
            int width = image.getIconWidth();
            int newSize = 275;
            ImageIcon imageResized;
            if (height > width) {
                // If image is a standing rectangle (height larger than width)
                imageResized = new ImageIcon(new ImageIcon(imgUrl).getImage().getScaledInstance((int) (newSize/3.1), newSize, Image.SCALE_DEFAULT));
            }
            else if (height < width) {
                // If image is a rectangle
                imageResized = new ImageIcon(new ImageIcon(imgUrl).getImage().getScaledInstance((int) (newSize*3.1), newSize, Image.SCALE_DEFAULT));
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
    public void setMessage(String message) {
        welcomeLabel.setText(("<html><center>" + message + "</center></html>"));
    }
    public void setInformation(String information){
        checkoutLabel.setText(("<html><center>" + information + "</center></html>"));
    }
    public void setBackgroundColour(String backgroundColour){
        frame.getContentPane().setBackground(Color.decode(backgroundColour));
    }
    public void setInformationColour(String informationColour){
        checkoutLabel.setForeground(Color.decode(informationColour));
    }
    public void setMessageColour(String messageColour){
        welcomeLabel.setForeground(Color.decode(messageColour));
    }
}