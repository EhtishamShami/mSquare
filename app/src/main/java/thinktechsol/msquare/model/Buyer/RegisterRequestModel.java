package thinktechsol.msquare.model.Buyer;

/**
 * Created by LENOVO on 7/19/2016.
 */

public class RegisterRequestModel {
    public String fName;
    public String lName;
    public String email;
    public String password;
    public String type;
    public String location;
    public String udid;


    public RegisterRequestModel(String fName, String lName, String email, String password, String type, String location, String udid) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.type = type;
        this.location = location;
        this.udid = udid;
    }
}
