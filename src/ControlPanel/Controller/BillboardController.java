package ControlPanel.Controller;

import ControlPanel.Models.BillboardModel;
import ControlPanel.Utilities.XMLParser;
import ControlPanel.View.AppFrame;
import ControlPanel.View.BillboardView;
import ControlPanel.View.MainNav;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.Base64;

/**
 * Manages events in the Billboard Management views
 */
public class BillboardController {
    private BillboardView billboardView;
    private AppFrame frame;
    private BillboardModel model;
    private MainNav mainNav;
    private String imageName;

    public BillboardController(AppFrame frame, BillboardModel model, BillboardView billboardView, MainNav mainNav) {
        this.frame = frame;
        this.model = model;
        this.billboardView = billboardView;
        this.mainNav = mainNav;
        imageName = "";
        this.initController();
    }

    private void initController() {
        // Add event listeners
        mainNav.getBillboard().addActionListener(e -> this.frame.changeView("billboards"));
        // These will all call a set method on the model
        billboardView.getSetMessageTextColourButton().addActionListener(e -> this.setMessageColor(this.getColor()));
        billboardView.getSetInformationTextColourButton().addActionListener(e -> this.setInformationColor(this.getColor()));
        billboardView.getSetBackgroundColourButton().addActionListener(e -> this.setBackgroundColor(this.getColor()));
        billboardView.getUploadXMLButton().addActionListener(e -> this.getXML());
        billboardView.getUploadImageButton().addActionListener(e -> this.setUploadImage(this.getImageBase64()));

        // Add views to the card layout
        frame.addView(billboardView.getPanel(), "billboards");
    }

    /**
     * Sets a label to notify the user of the status of the image upload.
     *
     * @param imageBase64
     */
    private void setUploadImage(String imageBase64) {
        if (imageBase64.length() > 0) {
            // Calls the model to set the image data, and erase the URL field
            // this.model.setImageData(imageBase64);
            // this.model.setImageURL("");
            this.billboardView.setImageURL("");
            this.billboardView.setImageData("Image " + this.imageName + " uploaded successfully");
        } else {
            this.billboardView.setImageData("Image upload failed");
        }
    }

    /**
     * Updates the model and sets the message textbox color
     * @param hex  the color to set
     */
    private void setMessageColor(String hex){
        if(hex.length() > 0){
//            this.model.setMessageColour(hex);
            this.billboardView.setMessageColor(hex);
        }
    }
    /**
     * Updates the model and sets the information textbox color
     * @param hex  the color to set
     */
    private void setInformationColor(String hex){
        if(hex.length() > 0){
//            this.model.setInformationColour(hex);
            this.billboardView.setInformationColor(hex);
        }
    }
    /**
     * Updates the model
     * @param hex  the color to set
     */
    private void setBackgroundColor(String hex){
        if(hex.length() > 0){
//            this.model.setBackgroundColor(hex);
        }
    }
    /**
     * Allows the user to choose an image.
     *
     * @return returns the chosen image in Base64 format.
     */
    private String getImageBase64() {
        String base64Image = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png",
                "bmp", "jpeg");
        fileChooser.setFileFilter(filter);
        int dialogResult = fileChooser.showOpenDialog(this.billboardView.getPanel());
        File file = null;
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            this.imageName = file.getName();
        }

        try (FileInputStream imageInFile = new FileInputStream(file)) {
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            this.alertUser("Image not found", "Error");
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            this.alertUser("Something went wrong", "Error");
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }

    /**
     * Opens a color chooser dialog and returns the chosen color in hex format
     *
     * @return the color in hex format
     */
    private String getColor() {
        try {
            Color bg = JColorChooser.showDialog(null, "Set message text colour", null);
            String hex = Integer.toHexString(bg.getRGB() & 0xffffff);
            if (hex.length() < 6) {
                if (hex.length() == 5)
                    hex = "0" + hex;
                if (hex.length() == 4)
                    hex = "00" + hex;
                if (hex.length() == 3)
                    hex = "000" + hex;
            }
            return "#" + hex;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Allows the user to choose an xml file. Will notify the user if the xml file is invalid
     *
     * @return a string containing the valid xml file contents.
     */
    private void getXML() {
        StringBuilder xml = new StringBuilder();
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
            fileChooser.setFileFilter(filter);
            int dialogResult = fileChooser.showOpenDialog(this.billboardView.getPanel());
            if (dialogResult == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String st;
                while ((st = reader.readLine()) != null)
                    xml.append(st);
            }
        } catch (Exception e) {
            this.alertUser("XML is malformed", "Upload error");
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        String xmlString = xml.toString();
        if (XMLParser.isValidBillboard(xmlString)) {
            this.alertUser("XML is valid", "Upload success");
            // Parse xml and update model
            this.processBillboardXML(xmlString);
        } else {
            this.alertUser("XML is malformed", "Upload error");
        }
    }

    /**
     * Updates the billboard model with the contents of the xml string
     * @param xml
     */
    private void processBillboardXML(String xml){
        Element bb = XMLParser.getBillboard(xml);
        if(bb.getAttribute("background").length() > 0){
            System.out.println(bb.getAttribute("background"));
        }
        // Message tag
        Element currentElement = (Element) bb.getElementsByTagName("message").item(0);
        if(currentElement.getTextContent().length() > 0){
            String message = currentElement.getTextContent();
            System.out.println(message);
            if(currentElement.getAttribute("colour").length() > 0){
                String messageColor = currentElement.getAttribute("colour");
                System.out.println(messageColor);
            }
        }
        // Information tag
        currentElement = (Element) bb.getElementsByTagName("information").item(0);
        if(currentElement.getTextContent().length() > 0){
            System.out.println(currentElement.getTextContent());
            if(currentElement.getAttribute("colour").length() > 0){
                System.out.println(currentElement.getAttribute("colour"));
            }
        }
        // Picture tag
        currentElement = (Element) bb.getElementsByTagName("picture").item(0);
        if(currentElement.getAttribute("url").length() > 0){
            System.out.println(currentElement.getAttribute("url"));
        } else if (currentElement.getAttribute("data").length() > 0){
            System.out.println(currentElement.getAttribute("data"));
        }
    }

    /**
     * Creates an alert dialog with a custom title and message
     *
     * @param message the message to display
     * @param title   the title of the dialog
     */
    private void alertUser(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
