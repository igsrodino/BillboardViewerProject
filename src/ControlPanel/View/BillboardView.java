package ControlPanel.View;

import javax.swing.*;
import java.awt.*;

/**
 * Getters and setters for Control Panel View.
 */
public class BillboardView {
    private JPanel containerPanel;
    private JPanel billboardPanel;
    private JList billboardList;
    private JTabbedPane billboardManager;
    private JButton deleteBillboardButton;
    private JButton saveChangesButton;
    private JButton uploadXMLButton;
    private JButton downloadXMLButton;
    private JTextField message;
    private JButton setMessageTextColourButton;
    private JTextField information;
    private JButton setInformationTextColourButton;
    private JTextField imageUrl;
    private JButton uploadImageButton;
    private JButton setBackgroundColourButton;
    private JLabel imageData;
    private JTextField billboardName;
    private JButton newBillboardButton;
    private JPanel previewPanel;
    private String imageSrcData;

    public String getBillboardName() {
        return billboardName.getText();
    }

    public void setImageData(String imageData) {
        this.imageData.setText(imageData);
    }

    public JList getBillboardList() {
        return billboardList;
    }

    public JButton getDeleteBillboardButton() {
        return deleteBillboardButton;
    }

    public JButton getSaveChangesButton() {
        return saveChangesButton;
    }

    public JButton getUploadXMLButton() {
        return uploadXMLButton;
    }

    public JButton getDownloadXMLButton() {
        return downloadXMLButton;
    }

    public String getMessage() {
        return message.getText();
    }

    public JButton getSetMessageTextColourButton() {
        return setMessageTextColourButton;
    }

    public String getInformation() {
        return information.getText();
    }

    public JButton getSetInformationTextColourButton() {
        return setInformationTextColourButton;
    }

    public String getImageUrl() {
        return imageUrl.getText();
    }

    public void setImageURL(String url) {
        this.imageUrl.setText(url);
    }

    public JButton getUploadImageButton() {
        return uploadImageButton;
    }

    public JButton getSetBackgroundColourButton() {
        return setBackgroundColourButton;
    }

    public JPanel getPanel() {
        return billboardPanel;
    }

    public void setMessageColor(String hex) {
        this.message.setForeground(Color.decode(hex));
    }

    public void setInformationColor(String hex) {
        this.information.setForeground(Color.decode(hex));
    }

    public JButton getNewBillboardButton() {
        return this.newBillboardButton;
    }

    public void setInformation(String s) {
        this.information.setText(s);
    }

    public void setMessage(String s) {
        this.message.setText(s);
    }

    public void setName(String s) {
        this.billboardName.setText(s);
    }

    /**
     * Switch cases for layout depending on if there are all messages,
     * images and information to display, or any combination of them.
     */
    public void setPreview() {
        // Sets the preview pane
        int typeCode = 0;
        if(this.getMessage().length() > 0){
            typeCode+=1;
        }
        if(this.getInformation().length() > 0){
            typeCode += 10;
        }
        if(this.getImageUrl().length() > 0 || (this.imageSrcData != null && this.imageSrcData.length() > 0)){
            typeCode += 100;
        }

        switch(typeCode){
            case 1:{
                // Message only
                this.previewPanel.setLayout(new GridLayout(3,0));
                JLabel message = new JLabel(this.getMessage());

                this.previewPanel.add(message);

                break;
            }
            case 10: {
                // Information only
                JLabel information = new JLabel(this.getInformation());
                this.previewPanel.add(information, BorderLayout.CENTER);
                break;
            }
            case 100: {
                // Picture only
                break;
            }
            case 101: {
                // Picture & message
                break;
            }
            case 11:{
                // Message and information
                break;
            }
            case 110: {
                // Picture and information
                break;
            }
            case 111: {
                // Picture, message, information
                break;
            }
            default: {
                break;
            }
        }
        previewPanel.revalidate();
        previewPanel.repaint();
        billboardManager.revalidate();
        billboardManager.repaint();
    }

    public void setImageSrcData(String pictureData) {
        this.imageSrcData = pictureData;
    }
}
