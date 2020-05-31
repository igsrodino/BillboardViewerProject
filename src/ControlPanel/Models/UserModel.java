package ControlPanel.Models;

/**
 * Getters for getCreator and getUserID.
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

    /**
     * Gets and returns creator.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Gets and returns user ID.
     */
    public int getUserID() {
        return userID;
    }
}
