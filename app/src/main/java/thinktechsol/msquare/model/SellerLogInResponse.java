package thinktechsol.msquare.model;

/**
 * Created by Arshad.Iqbal on 5/24/2016.
 */
public class SellerLogInResponse {
    public String logo;
    public String status;
    public String documents;
    public String tradeNo;
    public String lName;
    public String companyName;
    public String password;
    public String fName;
    public String phoneNo;
    public String id;
    public String email;
    public String address;
    public String description;
    public String activationCode;
    public String service;
    public String longitude;
    public String latitude;
    public String datetime;
    public String mobileNo;
    public SellerLogInResponse(String logo, String status, String documents, String tradeNo, String lName, String companyName, String password, String fName, String phoneNo, String id, String email, String address, String description, String activationCode, String service, String longitude, String latitude, String datetime, String mobileNo) {
        this.logo = logo;
        this.status = status;
        this.documents = documents;
        this.tradeNo = tradeNo;
        this.lName = lName;
        this.companyName = companyName;
        this.password = password;
        this.fName = fName;
        this.phoneNo = phoneNo;
        this.id = id;
        this.email = email;
        this.address = address;
        this.description = description;
        this.activationCode = activationCode;
        this.service = service;
        this.longitude = longitude;
        this.latitude = latitude;
        this.datetime = datetime;
        this.mobileNo = mobileNo;
    }
}
