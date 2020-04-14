package Viewer.Models;

public class BillboardPOJO {
    private String backgroundColour;
    private String messageColour;
    private String message;
    private String pictureURL;
    private String informationColour;
    private String information;
    private String pictureData;

public BillboardPOJO(){
    this.backgroundColour = "";
    this.messageColour = "";
    this.message = "";
    this.pictureURL = "";
    this.informationColour = "";
    this.information = "";
}

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public String getMessageColour() {
        return messageColour;
    }

    public void setMessageColour(String messageColour) {
        this.messageColour = messageColour;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = ("<html><center>" + message + "</center></html>");
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getInformationColour() {
        return informationColour;
    }

    public void setInformationColour(String informationColour) {
        this.informationColour = informationColour;
    }

    public String getInformation() {
        return this.information;
    }

    public void setInformation(String information) {
        this.information = ("<html><center>" + information + "</center></html>");
    }

    public String getPictureData() {
        return pictureData;
    }

    public void setPictureData(String pictureData) {
        this.pictureData = pictureData;
    }
}