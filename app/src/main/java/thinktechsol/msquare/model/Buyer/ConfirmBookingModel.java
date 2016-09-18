package thinktechsol.msquare.model.Buyer;

import java.util.ArrayList;

/**
 * Created by LENOVO on 7/19/2016.
 */

public class ConfirmBookingModel {
    public String sellerId;
    public String buyerId;
    public String extraRemarks;
    public String serviceRequestTime;
    public String staffId;
    public ArrayList<String> serviceId;
    public ArrayList<String> productId;
    public ArrayList<String> quantity;

    public ConfirmBookingModel(String sellerId, String buyerId, String extraRemarks, String serviceRequestTime, String staffId, ArrayList<String> serviceId, ArrayList<String> productId, ArrayList<String> quantity) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.extraRemarks = extraRemarks;
        this.serviceRequestTime = serviceRequestTime;
        this.staffId = staffId;
        this.serviceId = serviceId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
