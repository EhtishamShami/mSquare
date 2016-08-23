package thinktechsol.msquare.model.Buyer;

/**
 * Created by LENOVO on 8/18/2016.
 */

public class BuyerLogin {
    public String location;
    public String status;
    public String linkedin;
    public String lName;
    public String udid;
    public String password;
    public String fName;
    public String id;
    public String twitter;
    public String email;
    public String facebook;
    public String datetime;
    public String thumb;

    public BuyerLogin(String location, String status, String linkedin, String lName, String udid, String password, String fName, String id, String twitter, String email, String facebook, String datetime, String thumb) {
        this.location = location;
        this.status = status;
        this.linkedin = linkedin;
        this.lName = lName;
        this.udid = udid;
        this.password = password;
        this.fName = fName;
        this.id = id;
        this.twitter = twitter;
        this.email = email;
        this.facebook = facebook;
        this.datetime = datetime;
        this.thumb = thumb;
    }
}
