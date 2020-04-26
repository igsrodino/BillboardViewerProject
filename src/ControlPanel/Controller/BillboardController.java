package ControlPanel.Controller;

import ControlPanel.Models.BillboardModel;
import ControlPanel.Models.BillboardModelOld;
import ControlPanel.Models.UserModel;
import ControlPanel.Utilities.XMLHelpers;
import ControlPanel.View.AppFrame;
import ControlPanel.View.BillboardView;
import ControlPanel.View.MainNav;
import Viewer.Models.BillboardPOJO;
import com.sun.tools.javac.Main;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.Base64;

public class BillboardController {
    private UserModel userModel;
    private BillboardView billboardView;
    private AppFrame frame;
    private BillboardModel model;
    private String imageName;
    private BillboardPOJO currentBillboard;
    private MainNav mainNav;

    public BillboardController(AppFrame frame, BillboardModel model, BillboardView billboardView
            , MainNav mainNav, UserModel userModel) {
        this.mainNav = mainNav;
        this.frame = frame;
        this.model = model;
        this.billboardView = billboardView;
        this.imageName = "";
        this.userModel = userModel;
        this.currentBillboard = new BillboardPOJO();
        this.initController();
    }

    /**
     * Sets up the event listeners for the gui
     */
    private void initController() {
        // Event listeners
        mainNav.getBillboard().addActionListener(e -> this.frame.changeView("billboards"));
        billboardView.getNewBillboardButton().addActionListener(e -> this.addBillboard(userModel.getCreator(), userModel.getUserID()));
        billboardView.getBillboardList().addListSelectionListener(e-> this.selectBillboard());
        billboardView.getSaveChangesButton().addActionListener(e -> this.saveBillboard());
        billboardView.getSetMessageTextColourButton().addActionListener(e -> this.currentBillboard.setMessageColour(this.getColor()));
        billboardView.getSetInformationTextColourButton().addActionListener(e -> this.currentBillboard.setInformationColour(this.getColor()));
        billboardView.getSetBackgroundColourButton().addActionListener(e -> this.currentBillboard.setBackgroundColour(this.getColor()));

        // Add the view to the card layout
        frame.addView(billboardView.getPanel(), "billboards");
    }
    /**
     * Fetches the selected billboard from the model
     */
    private void selectBillboard(){
        int idx = billboardView.getBillboardList().getSelectedIndex();
        if(idx >= 0){
            this.currentBillboard =
                    model.getBillboard(billboardView.getBillboardList().getSelectedIndex());
            this.updateView();
        }
    }

    /**
     * Updates the view with the contents of currentBillboard;
     */
    private void updateView(){
        this.billboardView.setName(currentBillboard.getName());
        this.billboardView.setImageURL(currentBillboard.getPictureURL());
        this.billboardView.setInformation(currentBillboard.getInformation());
        this.billboardView.setMessage(currentBillboard.getMessage());
    }
    /**
     * Adds a billboard to the model, and updates the view
     * @param creator
     * @param owner
     */
    private void addBillboard(String creator, int owner){
        BillboardPOJO bb = new BillboardPOJO();
        bb.setName("New Billboard");
        bb.setCreator(creator);
        bb.setOwner(owner);
        this.currentBillboard = bb;
        int index = this.model.setBillboard(bb);
        this.billboardView.getBillboardList().setModel(this.model.getLocalList());
        this.billboardView.getBillboardList().setSelectedIndex(index);
    }
    /**
     * Saves the current billboard
     */
    private void saveBillboard(){
        currentBillboard.setName(billboardView.getBillboardName());
        currentBillboard.setInformation(billboardView.getInformation());
        currentBillboard.setMessage(billboardView.getMessage());
        currentBillboard.setPictureURL(billboardView.getImageUrl());

        // Check if we need to create a billboard or update an existing one.
        if(billboardView.getBillboardList().getSelectedIndex() < 0){
            this.model.setBillboard(currentBillboard);
            this.billboardView.getBillboardList().setModel(model.getLocalList());
        } else {
            this.model.setBillboard(this.billboardView.getBillboardList().getSelectedIndex(),
                    currentBillboard);
        }
        // TODO: Call model.saveBillboard to send to server
    }
    /**
     * Creates a colour picker and sets the message color
     */
    private void setMessageColour(){

    }
    /**
     * Creates a colour picker and sets the information color
     */
    private void setInformationColour(){}
    /**
     * Creates a colour picker and sets the background color
     */
    private void setBackgroundColour(){}

    /**
     * Allows the user to choose an image.
     * @return returns the chosen image in Base64 format or an empty string if they didn't select
     * a file.
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
        if(file != null){
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
        }

        return base64Image;
    }
    /**
     * Handles image file to base64 data.
     * @param imageBase64
     */
    private void setUploadImage(String imageBase64){}

    /**
     * Opens a color chooser dialog and returns the chosen color in hex format
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
        if(xmlString.length() > 0){
            if (XMLHelpers.isValidBillboard(xmlString)) {
                this.alertUser("XML is valid", "Upload success");
                // Parse xml and update model
                Element bb = XMLHelpers.getDocument(xmlString);
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
            } else {
                this.alertUser("XML is malformed", "Upload error");
            }
        }
    }

    /**
     * Creates a confirmation dialog
     * @param message  the message to display to the user
     * @return  returns true if the user clicked yes, false if they clicked no.
     */
    private boolean alertUser(String message){
        int x = JOptionPane.showConfirmDialog(null, message, "Are you sure?",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(x == 0){
            return true;
        }
        return false;
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
