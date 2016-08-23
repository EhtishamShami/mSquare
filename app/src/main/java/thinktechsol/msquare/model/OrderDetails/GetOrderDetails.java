package thinktechsol.msquare.model.OrderDetails;

import java.util.ArrayList;

import thinktechsol.msquare.model.Response.ProductImages;

/**
 * Created by Arshad Iqbal on 8/15/2016.
 */

public class GetOrderDetails {
    public String serviceRequestTime;
    public ArrayList<OrderDetails> orderDetails;
    public String staffId;
    public String lastModified;
    public String status;
    public String adminStatus;
    public SellerDetails sellerDetails;
    public BuyerDetails buyerDetails;
    public String dated;
    public String buyerId;
    public ArrayList<Messages> messages;
    public String id;
    public String sellerId;
    public String extraRemarks;

    public GetOrderDetails(String serviceRequestTime, ArrayList<OrderDetails> orderDetails, String staffId, String lastModified, String status, String adminStatus, SellerDetails sellerDetails, BuyerDetails buyerDetails, String dated, String buyerId, ArrayList<Messages> messages, String id, String sellerId, String extraRemarks) {
        this.serviceRequestTime = serviceRequestTime;
        this.orderDetails = orderDetails;
        this.staffId = staffId;
        this.lastModified = lastModified;
        this.status = status;
        this.adminStatus = adminStatus;
        this.sellerDetails = sellerDetails;
        this.buyerDetails = buyerDetails;
        this.dated = dated;
        this.buyerId = buyerId;
        this.messages = messages;
        this.id = id;
        this.sellerId = sellerId;
        this.extraRemarks = extraRemarks;
    }
}
