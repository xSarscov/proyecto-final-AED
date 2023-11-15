package model;

/**
 *
 * @author Ernesto
 */
public class ModelReset {

    /**
     * @return the VerifyCode
     */
    public String getVerifyCode() {
        return VerifyCode;
    }

    /**
     * @param VerifyCode the VerifyCode to set
     */
    public void setVerifyCode(String VerifyCode) {
        this.VerifyCode = VerifyCode;
    }

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public ModelReset(int userID, String username, String email, String VerifyCode) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.VerifyCode = VerifyCode;
    }

    private int userID;
    private String username;
    private String email;
    private String VerifyCode;
}
