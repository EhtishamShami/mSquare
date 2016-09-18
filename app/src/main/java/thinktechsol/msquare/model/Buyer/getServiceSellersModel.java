package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad Iqbal on 30/06/2016.
 */

public class getServiceSellersModel {

    public String logo;
    public String status;
    public String tradeNo;
    public String documents;
    public String lName;
    public String companyName;
    public String password;
    public String fName;
    public String productRating;
    public String id;
    public String phoneNo;
    public String distance;
    public String email;
    public String address;
    public String description;
    public String activationCode;
    public String service;
    public String longitude;
    public String latitude;
    public String datetime;
    public String mobileNo;

    public getServiceSellersModel(String mobileNo, String logo, String status, String tradeNo, String documents, String lName, String companyName, String password, String fName, String productRating, String id, String phoneNo, String distance, String email, String address, String description, String activationCode, String service, String longitude, String latitude, String datetime) {
        this.mobileNo = mobileNo;
        this.logo = logo;
        this.status = status;
        this.tradeNo = tradeNo;
        this.documents = documents;
        this.lName = lName;
        this.companyName = companyName;
        this.password = password;
        this.fName = fName;
        this.productRating = productRating;
        this.id = id;
        this.phoneNo = phoneNo;
        this.distance = distance;
        this.email = email;
        this.address = address;
        this.description = description;
        this.activationCode = activationCode;
        this.service = service;
        this.longitude = longitude;
        this.latitude = latitude;
        this.datetime = datetime;
    }
}
