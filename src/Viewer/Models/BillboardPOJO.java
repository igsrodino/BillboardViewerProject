package Viewer.Models;

/**
 * Contains getters and setters to retrieve information, image and message tags
 * from given from BillboardModel and be displayed in BillboardView
 */
public class BillboardPOJO {
    private String backgroundColour;
    private String messageColour;
    private String message;
    private String pictureURL;
    private String informationColour;
    private String information;
    private String pictureData;
    private int owner;
    private String creator;
    private String name;

    public BillboardPOJO(){
        this.backgroundColour = "";
        this.messageColour = "";
        this.message = "";
        this.pictureURL = "";
        this.informationColour = "";
        this.information = "";
        this.creator = "";
        this.owner = -1;
        this.name = "";
        this.pictureData = "";
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}