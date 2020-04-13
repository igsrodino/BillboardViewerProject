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

    public JTextField getMessage() {
        return message;
    }

    public JButton getSetMessageTextColourButton() {
        return setMessageTextColourButton;
    }

    public JTextField getInformation() {
        return information;
    }

    public JButton getSetInformationTextColourButton() {
        return setInformationTextColourButton;
    }

    public JTextField getImageUrl() {
        return imageUrl;
    }
    public void setImageURL(String url){
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

    public void setMessageColor(String hex){
            this.message.setForeground(Color.decode(hex));
    }
    public void setInformationColor(String hex){
            this.information.setForeground(Color.decode(hex));
    }

}
