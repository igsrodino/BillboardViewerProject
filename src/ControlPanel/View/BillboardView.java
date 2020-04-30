package ControlPanel.View;

import javax.swing.*;
import java.awt.*;

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

}
