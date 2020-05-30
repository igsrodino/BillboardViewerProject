package ControlPanel.Models;

/**
 * Getters for getCreater and getUserID.
 */
public class UserModel {
    private String creator;
    private int userID;

    public UserModel() {
        this.creator = "Anonymous";
        this.userID = -1;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCreator() {
        return creator;
    }

    public int getUserID() {
        return userID;
    }
}
