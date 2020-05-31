package ControlPanel.Controller;

import ControlPanel.Models.BillboardModel;
import ControlPanel.Models.UserModel;
import ControlPanel.Utilities.XMLHelpers;
import ControlPanel.View.AppFrame;
import ControlPanel.View.BillboardView;
import ControlPanel.View.MainNav;
import Viewer.Models.BillboardPOJO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Sets up the main controller for the billboard
 */
public class BillboardController {
    private UserModel userModel;
    private BillboardView billboardView;
    private AppFrame frame;
    private BillboardModel model;
    private String imageName;
    private BillboardPOJO currentBillboard;
    private MainNav mainNav;
    private DefaultListModel list;

    public BillboardController(AppFrame frame, BillboardModel model, BillboardView billboardView
            , MainNav mainNav, UserModel userModel) {
        this.mainNav = mainNav;
        this.frame = frame;
        this.model = model;
        this.billboardView = billboardView;
        this.imageName = "";
        this.userModel = userModel;
        this.currentBillboard = new BillboardPOJO();
        this.list = new DefaultListModel();
        this.initController();
    }

    /**
     * Sets up the event listeners for the gui
     */
    private void initController() {
        this.billboardView.getBillboardList().setModel(list);
        // Event listeners
        mainNav.getBillboard().addActionListener(e -> this.frame.changeView("billboards"));
        billboardView.getNewBillboardButton().addActionListener(e -> this.addBillboard(userModel.getCreator(), userModel.getUserID()));
        billboardView.getBillboardList().addListSelectionListener(e->this.selectBillboard(e));
        billboardView.getSaveChangesButton().addActionListener(e -> this.saveBillboard());
        billboardView.getSetMessageTextColourButton().addActionListener(e -> this.setColor(this.getColor(), "message"));
        billboardView.getSetInformationTextColourButton().addActionListener(e -> this.setColor(this.getColor(), "information"));
        billboardView.getSetBackgroundColourButton().addActionListener(e -> this.setColor(this.getColor(), "background"));
        billboardView.getUploadImageButton().addActionListener(e -> this.setUploadImage(this.getImageBase64()));
        billboardView.getDeleteBillboardButton().addActionListener(e -> this.deleteBillboard());
        billboardView.getDownloadXMLButton().addActionListener(e-> this.downloadXML());
        billboardView.getUploadXMLButton().addActionListener(e -> this.getXML());
        // Add the view to the card layout
        frame.addView(billboardView.getPanel(), "billboards");
    }
    /**
     * Downloads the current billboard in xml form
     */
    private void downloadXML(){
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element bb = doc.createElement("billboard");
            if(currentBillboard.getBackgroundColour().length() > 0){
                bb.setAttribute("background", currentBillboard.getBackgroundColour());
            }
            if(currentBillboard.getMessage().length() > 0){
                Element mesg =
                        doc.createElement("message");
                mesg.setTextContent(currentBillboard.getMessage());
                if(currentBillboard.getMessageColour().length() > 0){
                    mesg.setAttribute("colour", currentBillboard.getMessageColour());
                }
                bb.appendChild(mesg);
            }
            if(currentBillboard.getInformation().length() > 0){
                Element info = doc.createElement("information");
                info.setTextContent(currentBillboard.getInformation());
                if(currentBillboard.getInformationColour().length() > 0){
                    info.setAttribute("colour", currentBillboard.getInformationColour());
                }
                bb.appendChild(info);
            }
            Element pic = null;
            if(currentBillboard.getPictureURL() != null || currentBillboard.getPictureData() != null){
                pic = doc.createElement("picture");
            }
            if(currentBillboard.getPictureURL() != null && currentBillboard.getPictureURL().length() > 0){
                pic.setAttribute("url", currentBillboard.getPictureURL());
                bb.appendChild(pic);
            } else if (currentBillboard.getPictureData() != null && currentBillboard.getPictureData().length() > 0){
                pic.setAttribute("data", currentBillboard.getPictureData());
                bb.appendChild(pic);
            }
            doc.appendChild(bb);
            String xml = XMLHelpers.documentToString(doc);
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
                int dialogResult = fileChooser.showSaveDialog(this.billboardView.getPanel());
                if (dialogResult == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    Files.write(Paths.get(selectedFile.getAbsolutePath()), xml.getBytes());
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.err.println(e.getStackTrace());
            }
        } catch (ParserConfigurationException e){
            alertUser("Something went wrong", "Error");
        }
    }
    /**
     * Deletes the current billboard
     */
    private void deleteBillboard() {
        int idx = billboardView.getBillboardList().getSelectedIndex();
        this.currentBillboard = new BillboardPOJO();
        if(idx >= 0){
            this.model.deleteBillboard(idx);
        }
        this.updateView(true);
        // Call model to delete billboard from server
    }

    /**
     * Sets the color
     * @param hex  the hex string to set
     * @param item  the billboard item to set color for
     */
    private void setColor(String hex, String item){
        if(hex.length() > 1) {
            switch (item){
                case "message":
                    this.currentBillboard.setMessage(this.billboardView.getMessage());
                    this.currentBillboard.setMessageColour(hex);
                    break;
                case "information":
                    this.currentBillboard.setInformation(this.billboardView.getInformation());
                    this.currentBillboard.setInformationColour(hex);
                    break;
                case "background":
                    this.currentBillboard.setBackgroundColour(hex);
                    break;
                default:
                    break;
            }
            this.updateView(false);
        }
    }
    /**
     * Fetches the selected billboard from the model
     */
    private void selectBillboard(ListSelectionEvent evt){
        if(!evt.getValueIsAdjusting()) {
            int idx = this.billboardView.getBillboardList().getSelectedIndex();
            if (idx > -1) {
                this.currentBillboard =
                        this.model.getBillboard(this.billboardView.getBillboardList().getSelectedIndex());
            }
            this.updateView(false);
        }
    }

    /**
     * Updates the view with the contents of currentBillboard;
     */
    private void updateView(boolean updateList){
        this.billboardView.setName(currentBillboard.getName());
        this.billboardView.setImageURL(currentBillboard.getPictureURL());
        this.billboardView.setInformation(currentBillboard.getInformation());
        this.billboardView.setInformationColor(currentBillboard.getInformationColour().length() > 0 ?
                currentBillboard.getInformationColour() :
                "#1B1E23" );
        this.billboardView.setMessage(currentBillboard.getMessage());
        this.billboardView.setMessageColor(currentBillboard.getMessageColour().length() > 0 ?
                currentBillboard.getMessageColour() :
                "#1B1E23" );
        this.billboardView.setPreview();
        if(updateList){
            int idx = this.billboardView.getBillboardList().getSelectedIndex();
            DefaultListModel newModel = model.getLocalList();
            list.clear();
            for(int i = 0; i < newModel.size(); i++){
                list.add(i, newModel.getElementAt(i));
            }
            this.billboardView.getBillboardList().setSelectedIndex(idx);
        }
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
        this.billboardView.getBillboardList().setSelectedIndex(index);
        this.updateView(true);
    }
    /**
     * Saves the current billboard
     */
    private void saveBillboard(){
        if(billboardView.getBillboardName().trim().length() <=0){
            this.alertUser("Billboard name cannot be empty", "Error");
            return;
        }
        currentBillboard.setName(billboardView.getBillboardName());
        currentBillboard.setInformation(billboardView.getInformation());
        currentBillboard.setMessage(billboardView.getMessage());
        currentBillboard.setPictureURL(billboardView.getImageUrl());

        // Check if we need to create a billboard or update an existing one.

        if(billboardView.getBillboardList().getSelectedIndex() < 0){
            int index = this.model.setBillboard(this.currentBillboard);
            this.billboardView.getBillboardList().setSelectedIndex(index);

        } else {
            this.model.setBillboard(this.billboardView.getBillboardList().getSelectedIndex(),
                    currentBillboard);
        }
        this.updateView(true);
        // Call model.saveBillboard to send to server
    }

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
    private void setUploadImage(String imageBase64){
        if(imageBase64.length() > 0){
            currentBillboard.setPictureData(imageBase64);
            this.billboardView.setImageURL("");
            this.billboardView.setImageData("Using image: "+this.imageName);
            this.billboardView.setImageSrcData(currentBillboard.getPictureData());
        }
    }

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
                this.currentBillboard = new BillboardPOJO();
                // Parse xml and update model
                Element bb = XMLHelpers.getDocument(xmlString);
                if(bb.getAttribute("background") != null){
                    currentBillboard.setBackgroundColour(bb.getAttribute("background"));
                }
                if(bb.getAttribute("owner") != null && bb.getAttribute("owner").length() > 0){
                    currentBillboard.setOwner(Integer.parseInt(bb.getAttribute("owner")));
                }
                if(bb.getAttribute("creator") != null){
                    currentBillboard.setCreator(bb.getAttribute("creator"));
                }
                NodeList children = bb.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    if(child.getNodeName() == "message"){
                        currentBillboard.setMessage(child.getTextContent());
                        if(child.getAttributes().getNamedItem("colour") != null){
                            currentBillboard.setMessageColour(child.getAttributes().getNamedItem(
                                    "colour").getTextContent());
                        }
                    }
                    if(child.getNodeName() == "information"){
                        currentBillboard.setInformation(child.getTextContent());
                        if(child.getAttributes().getNamedItem("colour") != null){
                            currentBillboard.setInformationColour(child.getAttributes().getNamedItem(
                                    "colour").getTextContent());
                        }
                    }
                    if(child.getNodeName() == "picture"){
                        if(child.getAttributes().getNamedItem("url") != null){
                            currentBillboard.setPictureURL(child.getAttributes().getNamedItem(
                                    "url").getTextContent());
                        }else if (child.getAttributes().getNamedItem("data") != null){
                            currentBillboard.setPictureData(child.getAttributes().getNamedItem(
                                    "data").getTextContent());
                        }
                    }
                }
            } else {
                this.alertUser("XML is malformed", "Upload error");
            }
        }
        this.updateView(false);
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
